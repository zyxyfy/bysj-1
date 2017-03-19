package com.newview.bysj.service;

import com.newview.bysj.dao.ScheduleDao;
import com.newview.bysj.domain.*;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.jpaRepository.MyRepository;
import com.newview.bysj.myAnnotation.MethodDescription;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service("scheduleService")
public class ScheduleService extends BasicService<Schedule, Integer> {

    private static final Logger logger = Logger.getLogger(ScheduleService.class);
    private Lock lock = new ReentrantLock();
    @Autowired
    private GraduateProjectService graduateProjectService;
    ScheduleDao scheduleDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<Schedule, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        scheduleDao = (ScheduleDao) basicDao;
    }

    @MethodDescription("添加学生工作进程表")
    public void addStudentSchedules(Student student) {
        GraduateProject graduateProject = student.getGraduateProject();
        this.createSchedules(graduateProject, 2);
    }

    @MethodDescription("查看课题对应的工作进程表")
    public List<Schedule> hasSchedules(GraduateProject graduateProject) {
        return this.list("graduateProject", GraduateProject.class, graduateProject);

    }

    @MethodDescription("通过课题获得工作进程表")
    public Page<Schedule> getPageByGraduateProject(Integer graduateProjectId, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<Schedule> result = scheduleDao.findAll(new Specification<Schedule>() {
            @Override
            public Predicate toPredicate(Root<Schedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate condition = cb.equal(root.get("graduateProject").get("id").as(Integer.class), graduateProjectId);
                query.where(condition);
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.DESC, "id")));
        return result;
    }

    /*
     * 根据教研室主任设计开始的时间和结束时间，结合时间段的周数，产生一个工作进程集合
     * @param graduateProject 对应的课题
     * @param weeksNumForItem 一个工作进程时长的周数(多少周产生一行工作进程)
     * @return Schedule集合
     */
    public void createSchedules(GraduateProject graduateProject, Integer weekNumForItem) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        //如果有工作进程表安排就返回null
        if (this.hasSchedules(graduateProject).size() != 0) {
            return;
        }
        lock.lock();
        //获取本教研室毕业设计的开始时间和结束时间
        ProjectTimeSpan projectTimeSpan = graduateProject.getStudent().getStudentClass().getMajor().getDepartment().getProjectTimeSpan();
        //获取开始时间
        Calendar projectStartTime = projectTimeSpan.getBeginTime();
//        logger.error("projectStartTime:" + simpleDateFormat.format(projectStartTime.getTime()));
        Calendar projectEndTime = null;
        //当没有答辩小组或小组没有答辩时间，用毕业设计的结束时间
        if (graduateProject.getReplyGroup() == null || graduateProject.getReplyGroup().getReplyTime() == null) {
            projectEndTime = projectTimeSpan.getEndTime();
//            logger.error("projectEndTime:" + simpleDateFormat.format(projectEndTime.getTime()));
        } else {
            //获取答辩小组的答辩时间
            ReplyTime replyTime = graduateProject.getReplyGroup().getReplyTime();
//            logger.info("replytime " + simpleDateFormat.format(replyTime.getBeginTime().getTime()) + "    endTime  :  " + simpleDateFormat.format(replyTime.getEndTime().getTime()));
            //判断毕业设计结束时间是否在答辩开始时间之后
            if (projectStartTime.after(replyTime.getBeginTime())) {
                //为true，毕业设计结束时间为答辩开始时间前一天的23:59:59
                Calendar time = Calendar.getInstance();
                //将时间设置为答辩的开始时间
                time.setTime(replyTime.getBeginTime().getTime());
                //设置时间为答辩开始的前一天
                time.set(Calendar.DATE, time.get(Calendar.DATE) - 1);
                //将结束日期设置为当天的23:59:59
                CommonHelper.setEndCalendarHMS(time);
                projectEndTime = time;
            } else {
                //为false，projectEndTime为毕业设计结束时间
                projectEndTime = projectTimeSpan.getEndTime();
            }
        }
        //总体天数,不足一天也按一天算
        long offset = (projectEndTime.getTimeInMillis() - projectStartTime.getTimeInMillis()) % (1000 * 3600 * 24) == 0 ? (projectEndTime.getTimeInMillis() - projectStartTime.getTimeInMillis()) / (1000 * 3600 * 24) : (projectEndTime.getTimeInMillis() - projectStartTime.getTimeInMillis()) / (1000 * 3600 * 24) + 1;
        //Assert.assertEquals(15, offset);
        //logger.error("offset:   ==="+offset);
        //总体周数，不足一周也按一周算
        int weekSpan = (int) (offset % 7 == 0 ? offset / 7 : offset / 7 + 1);
        //可以产生记录的总条数，总体周数是否为weekNumForItem（现在是以2周为一个周期）周的整数倍，如果是则取整计算总记录的条数
        //如果不是，则剩余的天数也产生一条记录
        int itemNum = weekSpan % weekNumForItem == 0 ? weekSpan / weekNumForItem : weekSpan / weekNumForItem + 1;
        //克隆一个开始时间，供操作
        Calendar cloneTime = (Calendar) projectStartTime.clone();
        //先减去一个阶段的天数，方便后面的循环操作
        //cloneTime.add(Calendar.DAY_OF_YEAR, -(weekNumForItem * 7));
        //两周一个阶段，每个阶段的天数
        int daysOfSpan = weekNumForItem * 7;
        List<Schedule> schedules = new ArrayList<>();
        for (int i = 0; i < itemNum; i++) {
            //加一个阶段的天数
            //cloneTime.add(Calendar.DAY_OF_YEAR, weekNumForItem * 7);
            Schedule schedule = new Schedule();
            //设置本阶段开始时间
            schedule.setBeginTime((Calendar) cloneTime.clone());
            //增加一个阶段的天数-1，一个工作进程表的时长
            cloneTime.add(Calendar.DAY_OF_YEAR, +(daysOfSpan - 1));
            //如果本条件结束时间晚于毕业设计的结束时间，则以毕业设计结束时间为准
            if (cloneTime.after(projectEndTime)) {
                cloneTime = (Calendar) projectEndTime.clone();
            }
            //设置结束时间
            schedule.setEndTime((Calendar) cloneTime.clone());
            //设置工作内容默认提示
            schedule.setContent("未填写");
            //关联工作进程表
            schedule.setGraduateProject(graduateProject);
            //保存工作进程表
            Schedule schedule1 = scheduleDao.save(schedule);
            //将schedule添加到集合中
            schedules.add(schedule1);
            //将时间重新设置为本阶段的结束时间+1，即下一阶段的开始时间
            cloneTime.add(Calendar.DAY_OF_YEAR, +1);
        }
        graduateProject.setSchedules(schedules);
        graduateProjectService.update(graduateProject);
        lock.unlock();
        //return schedules;
    }
}
