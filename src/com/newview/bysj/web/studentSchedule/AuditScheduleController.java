package com.newview.bysj.web.studentSchedule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.newview.bysj.domain.Audit;
import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.domain.Schedule;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;

@Controller
public class AuditScheduleController extends BaseController {

    //毕业设计审核工作进程表的显示页面
    @RequestMapping("/process/checkStudentSchedule.html")
    public String scheduleProject(HttpSession httpSession, HttpServletRequest httpServletRequest, ModelMap modelMap, Integer pageNo, Integer pageSize) {
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        Page<GraduateProject> graduateProject = graduateProjectService.getPageByMainTutorage(tutor, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, graduateProject, "graduateProjects", CommonHelper.getRequestUrl(httpServletRequest), graduateProject.getTotalElements());
        return "scheduleProject/scheduleOfProject";
    }

    //显示需要审核的工作进程表细节
    @RequestMapping("process/showDetailSchedules.html")
    public String showDetailSchedule(ModelMap modelMap, HttpServletRequest httpServletRequest, Integer graduateProjectId, Integer pageNo, Integer pageSize) {
        //获取需要审核的工作进程表
        Page<Schedule> schedule = scheduleService.getPageByGraduateProject(graduateProjectId, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, schedule, "schedules", CommonHelper.getRequestUrl(httpServletRequest), schedule.getTotalElements());
        //modelMap.addAttribute(Common.DISPLAY_PATTERN, "ROLE_TUTOR");
        //httpServletRequest.setAttribute(Common.DISPLAY_PATTERN, "ROLE_TUTOR");
        return "scheduleProject/showDetailSchedule";
    }

    //填写评价内容
    @RequestMapping(value = "process/addOrEditScheduleRemark", method = RequestMethod.GET)
    public String addOrEditScheduleRemark(Integer scheduleId, ModelMap modelMap, HttpSession httpSession, HttpServletRequest httpServletRequest) {
        Tutor auditor = tutorService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        Schedule schedule = scheduleService.findById(scheduleId);
        //添加审核对象
        if (schedule.getAudit() == null) {
            Audit audit = new Audit();
            audit.setAuditor(auditor);
            schedule.setAudit(audit);
            //更新schedule
            scheduleService.update(schedule);
            //对更新状态进行保存
            scheduleService.save(schedule);
        }
        modelMap.addAttribute("schedule", schedule);
        modelMap.addAttribute("actionUrl", CommonHelper.getRequestUrl(httpServletRequest));
        modelMap.addAttribute("addorEditScheduleRemark", schedule.getAudit().getRemark());
        return "scheduleProject/addOrEditScheduleRemark";
    }

    /**
     * 添加或修改工作进程表的post方法
     *
     * @param scheduleId 修改的进程表的id
     * @param content    评价的内容
     */
    @RequestMapping(value = "process/addOrEditScheduleRemark", method = RequestMethod.POST)
    public String addOrEditScheduleRemark(Integer scheduleId, String content) {
        Schedule schedule = scheduleService.findById(scheduleId);
        Audit audit = schedule.getAudit();
        audit.setApprove(null);
        audit.setRemark(null);
        audit.setAuditDate(null);
        audit.setApprove(true);
        audit.setRemark(content);
        audit.setAuditDate(CommonHelper.getNow());
        //更新audit
        auditService.update(audit);
        //对更新后的状态进行保存
        auditService.save(audit);
        //CommonHelper.buildSimpleJson(httpServletResponse);
        return "redirect:/process/checkStudentSchedule.html";
    }
}
