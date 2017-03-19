package com.newview.bysj.web.studentSchedule;

import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.domain.ProjectTimeSpan;
import com.newview.bysj.domain.Schedule;
import com.newview.bysj.domain.Student;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;
import com.newview.bysj.web.projectHelper.GraduateProjectHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class StudentScheduleController extends BaseController {

    //private static final Logger logger = Logger.getLogger(StudentScheduleController.class);

    @RequestMapping("student/writeSchedule.html")
    public String writerShedule(HttpSession httpSession, ModelMap modelMap) {
        //获取当前用户
        Student student = studentService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        //获取时间间隔
        ProjectTimeSpan projectTimeSpan = student.getStudentClass().getMajor().getDepartment().getProjectTimeSpan();
        List<Schedule> schedules;

        if (student.getGraduateProject() == null) {
            modelMap.put("message", "你尚未选择毕业设计题目，请选择后再填写工作进程表");
        } else if (student.getGraduateProject().getSchedules().size() == 0) {
            modelMap.addAttribute("ifShowSchedule", "0");
        } else {
            schedules = student.getGraduateProject().getSchedules();
            modelMap.addAttribute("ifShowSchedule", "1");
            modelMap.addAttribute("schedules", schedules);
        }
        //用来判断教研室主任是否设置了时间
        Integer hasTimeSpan = (projectTimeSpan == null) ? 0 : 1;
        if (projectTimeSpan != null) {
            GraduateProjectHelper.timeInProjectTimeSpan(modelMap, projectTimeSpan);
        }
        modelMap.addAttribute("hasTimeSpan", hasTimeSpan);


        return "student/schedule/writeSchedule";
    }

    //手动生成工作进程表
    @RequestMapping("student/addSchedule")
    public String addSchedule(HttpSession httpSession, ModelMap modelMap) {
        Student student = studentService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        //logger.error("studentName : " + student.getName());
        scheduleService.addStudentSchedules(student);
        return "redirect:writeSchedule.html";
    }

    //添加工作进程表内容
    @RequestMapping(value = "student/addScheduleContent", method = RequestMethod.GET)
    public String addScheduleContent(Integer scheduleId, ModelMap modelMap, HttpServletRequest httpServletRequest) {
        Schedule schedule = scheduleService.findById(scheduleId);
        modelMap.addAttribute("schedule", schedule);
        modelMap.addAttribute("content", schedule.getContent());
        modelMap.addAttribute("actionUrl", CommonHelper.getRequestUrl(httpServletRequest));
        return "student/schedule/addScheduleContent";
    }

    @RequestMapping(value = "student/addScheduleContent", method = RequestMethod.POST)
    public String addScheduleContent(HttpSession httpSession, String content, Integer scheduleId, HttpServletResponse httpServletResponse) {
        Schedule schedule = scheduleService.findById(scheduleId);
        schedule.setContent(null);
        schedule.setContent(content);
        scheduleService.update(schedule);
        //将修改状态持久化到数据库
        scheduleService.save(schedule);
        return "redirect:writeSchedule.html";
    }

    /**
     * 查看工作进程表
     *
     * @param modelMap  存储需要在jsp中获取的数据
     * @param projectId 课题的id
     * @return jsp
     */
    @RequestMapping("student/viewSchedule.html")
    public String viewSchedule(ModelMap modelMap, Integer projectId) {
        GraduateProject graduateProject = graduateProjectService.findById(projectId);
        //将获取到的工作进程的集合添加到List集合中，在jsp中获取
        List<Schedule> scheduleList = graduateProject.getSchedules();

        modelMap.put("scheduleList", scheduleList);
        return "scheduleProject/viewSchedule";
    }

}
