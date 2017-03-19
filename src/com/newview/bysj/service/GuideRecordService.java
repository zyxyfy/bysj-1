package com.newview.bysj.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.AuditDao;
import com.newview.bysj.dao.GuideRecordDao;
import com.newview.bysj.dao.HolidayAndVacationDao;
import com.newview.bysj.domain.Audit;
import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.domain.GuideDay;
import com.newview.bysj.domain.GuideRecord;
import com.newview.bysj.domain.HolidayAndVacation;
import com.newview.bysj.domain.ProjectTimeSpan;
import com.newview.bysj.domain.ReplyTime;
import com.newview.bysj.domain.Student;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.jpaRepository.MyRepository;
import com.newview.bysj.myAnnotation.MethodDescription;

@Service("guideRecordService")
public class GuideRecordService extends BasicService<GuideRecord, Integer> {
    private Lock lock = new ReentrantLock();
    GuideRecordDao guideRecordDao;
    @Autowired
    HolidayAndVacationDao holidayAndVacationDao;
    @Autowired
    AuditDao auditDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<GuideRecord, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        guideRecordDao = (GuideRecordDao) basicDao;
    }

    @MethodDescription("添加学生指导记录")
    public void addGuideRecord(Student student) {
        GraduateProject graduateProject = student.getGraduateProject();
        this.createGuideRecord(graduateProject);
    }

    public List<GuideRecord> createGuideRecord(GraduateProject graduateProject) {
        lock.lock();
        //获取毕业设计时间
        ProjectTimeSpan projectTimeSpan = graduateProject.getStudent().getStudentClass().getMajor().getDepartment().getProjectTimeSpan();
        Calendar projectStartTime = projectTimeSpan.getBeginTime();
        Calendar now = CommonHelper.getNow();
        Calendar projectEndTime = null;
        if (graduateProject.getReplyGroup() == null || graduateProject.getReplyGroup().getReplyTime() == null) {
            projectEndTime = projectTimeSpan.getEndTime();
        } else {
            ReplyTime replyTime = graduateProject.getReplyGroup().getReplyTime();
            if (projectTimeSpan.getEndTime().after(replyTime.getBeginTime())) {
                Calendar time = Calendar.getInstance();
                time.setTime(replyTime.getBeginTime().getTime());
                time.set(Calendar.DATE, time.get(Calendar.DATE) - 1);
                CommonHelper.setEndCalendarHMS(time);
                projectEndTime = time;
            } else {
                projectEndTime = projectTimeSpan.getEndTime();
            }
        }
        if (projectEndTime.getTime().before(now.getTime()) || projectEndTime.getTime().equals(now.getTime())) {
            now = projectEndTime;
        }
        GuideDay guideDay = graduateProject.getGuideDay();
        Calendar time = Calendar.getInstance();
        time.setTime(projectStartTime.getTime());
        //查询以前的指导记录表以供删除使用
        List<GuideRecord> oldGuideRecords = graduateProject.getGuideRecord();
        //新的指导记录
        List<GuideRecord> newGuideRecords = new ArrayList<GuideRecord>();
        while (time.getTime().before(now.getTime()) || time.getTime().equals(now.getTime())) {
            //判断是否是知道记录日期（一周所所选四天）
            if (time.get(Calendar.DAY_OF_WEEK) == guideDay.getFirst()) {
                isSetGuideRecord(time, oldGuideRecords, graduateProject);
            } else if (time.get(Calendar.DAY_OF_WEEK) == guideDay.getSecond()) {
                isSetGuideRecord(time, oldGuideRecords, graduateProject);
            } else if (time.get(Calendar.DAY_OF_WEEK) == guideDay.getThird()) {
                isSetGuideRecord(time, oldGuideRecords, graduateProject);
            } else if (time.get(Calendar.DAY_OF_WEEK) == guideDay.getFourth()) {
                isSetGuideRecord(time, oldGuideRecords, graduateProject);
            }
            time.add(Calendar.DATE, 1);
        }
        lock.unlock();
        return newGuideRecords;

    }

    @MethodDescription("判断是否 上一次自动生成指导记录的时间到当前时间 并且除去节假日或出差时间 进行保存")
    public void isSetGuideRecord(Calendar time, List<GuideRecord> oldGuideRecords, GraduateProject graduateProject) {
        if (!isHolidayAndVacation(time) && !isRepeatFromOld(oldGuideRecords, time)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time.getTime());
            //生成指导记录好 审核者(报题主指导)即通过审核
            Audit auditedByTutor = new Audit();
            auditedByTutor.setApprove(true);
            auditedByTutor.setAuditDate(calendar);
            auditedByTutor.setAuditor(graduateProject.getMainTutorage().getTutor());
            auditDao.save(auditedByTutor);
            //属性设置和建立即送审
            GuideRecord guideRecord = new GuideRecord();
            guideRecord.setSubmittedByStudent(true);
            guideRecord.setAuditedByTutor(auditedByTutor);
            guideRecord.setTime(calendar);
            guideRecord.setGraduateProject(graduateProject);
            guideRecord.setDescription("请填写指导内容");
            guideRecordDao.save(guideRecord);
        }
    }

    @MethodDescription("判断是否是节假日时间")
    public Boolean isHolidayAndVacation(Calendar time) {
        List<HolidayAndVacation> holidayAndVacations = holidayAndVacationDao.findAll();
        if (holidayAndVacations == null || holidayAndVacations.size() == 0) {
            return false;
        }
        for (HolidayAndVacation holidayAndVacation : holidayAndVacations) {
            if (time.getTime().after(holidayAndVacation.getBeginTime().getTime()) && time.getTime().before(holidayAndVacation.getEndTime().getTime())
                    || time.getTime().equals(holidayAndVacation.getBeginTime().getTime()) || time.getTime().equals(holidayAndVacation.getEndTime())) {
                return true;
            } else {
                continue;
            }
        }
        return false;
    }

    @MethodDescription("判断是否以前自动生成过")
    public Boolean isRepeatFromOld(List<GuideRecord> oldGuideRecords, Calendar time) {
        if (oldGuideRecords == null || oldGuideRecords.size() == 0) {
            return false;
        }
        for (GuideRecord guideRecord : oldGuideRecords) {
            if (time.getTime().equals(guideRecord.getTime().getTime())) {
                return true;
            } else {
                continue;
            }
        }
        return false;
    }

    @MethodDescription("检测是否在毕业设计时间之后")
    public Boolean isAfterProjectTimeSpan(Student student, Calendar time) {
        ProjectTimeSpan projectTimeSpan = student.getStudentClass().getMajor().getDepartment().getProjectTimeSpan();
        Calendar endTime = projectTimeSpan.getEndTime();
        if (time.getTime().equals(endTime.getTime()) || time.getTime().after(endTime.getTime())) {
            return true;
        }
        return false;
    }

    @MethodDescription("检测是否在毕业设计时间之前")
    public Boolean isBeforeProjectTimeSpan(Student student, Calendar time) {
        ProjectTimeSpan projectTimeSpan = student.getStudentClass().getMajor().getDepartment().getProjectTimeSpan();
        Calendar startTime = projectTimeSpan.getBeginTime();
        if (time.getTime().before(startTime.getTime())) {
            return true;
        }
        return false;
    }

    @MethodDescription("根据学生报题获取指导记录表")
    public Page<GuideRecord> getPageByStudentProject(Integer graduateProjectId, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<GuideRecord> result = guideRecordDao.findAll(new Specification<GuideRecord>() {
            @Override
            public Predicate toPredicate(Root<GuideRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate c1 = cb.equal(root.get("graduateProject").get("id").as(Integer.class), graduateProjectId);
                query.where(cb.and(c1));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.DESC, "id")));
        return result;
    }

    @MethodDescription("根据学生报题获取指导记录表")
    public Page<GuideRecord> getPageSubmittedByStudentProject(Integer graduateProjectId, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<GuideRecord> result = guideRecordDao.findAll(new Specification<GuideRecord>() {
            @Override
            public Predicate toPredicate(Root<GuideRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate c1 = cb.equal(root.get("graduateProject").get("id").as(Integer.class), graduateProjectId);
                Predicate c2 = cb.equal(root.get("submittedByStudent").as(Boolean.class), true);
                query.where(cb.and(c1));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.DESC, "id")));
        return result;
    }

}
