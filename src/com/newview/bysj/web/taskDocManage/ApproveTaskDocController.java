package com.newview.bysj.web.taskDocManage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.newview.bysj.domain.Audit;
import com.newview.bysj.domain.TaskDoc;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;

@Controller
public class ApproveTaskDocController extends BaseController {
    @RequestMapping("/approveTaskDoc")
    public String list(ModelMap modelMap, Integer pageNo, Integer pageSize) {
        modelMap.put("pageNo", pageNo);
        modelMap.put("pageSize", pageSize);
        //根据jsp中不同角色跳转到不同的对应方法
        return "taskDoc/redirectToRole";
    }

    //通过教研室主任获取任务书
    @RequestMapping("getTaskDocsByDirector")
    public String getDocByDepartment(HttpSession httpSession, HttpServletRequest httpServletRequest, ModelMap modelMap, Integer pageNo, Integer pageSize, Boolean approve) {
        //获取当前用户
        Tutor director = tutorService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        //获取教研室主任的任务书
        Page<TaskDoc> taskDoc = taskDocService.getAuditedTaskDocByDirector(director, approve, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, taskDoc, "taskDoces", CommonHelper.getRequestUrl(httpServletRequest), taskDoc.getTotalElements());
        modelMap.addAttribute("actionUrl", CommonHelper.getRequestUrl(httpServletRequest));
        return "taskDoc/approveTaskDoc";
    }

    //通过院长获取任务书
    @RequestMapping("getTaskDocsByDean")
    public String getDocByDean(HttpSession httpSession, HttpServletRequest httpServletRequest, ModelMap modelMap, Integer pageNo, Integer pageSize, Boolean approve) {
        //获取当前用户
        Tutor Dean = tutorService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        //获取院长的任务书
        Page<TaskDoc> taskDoc = taskDocService.getAuditedTaskDocByDean(Dean, approve, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, taskDoc, "taskDoces", CommonHelper.getRequestUrl(httpServletRequest), taskDoc.getTotalElements());
        modelMap.addAttribute("actionUrl", CommonHelper.getRequestUrl(httpServletRequest));
        return "taskDoc/approveTaskDoc";
    }

    //通过院长，教研室主任获取任务书
    @RequestMapping("getTaskDocsByDirectorAndDean")
    public String getDocByDepartmentandDean(HttpSession httpSession, HttpServletRequest httpServletRequest, ModelMap modelMap, Integer pageNo, Integer pageSize, Boolean approve) {
        //获取当前用户
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        //获取院长教研室主任的任务书
        Page<TaskDoc> taskDoc = taskDocService.getAuditedTaskDocByDirectorAndDean(tutor, approve, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, taskDoc, "taskDoces", CommonHelper.getRequestUrl(httpServletRequest), taskDoc.getTotalElements());
        modelMap.addAttribute("actionUrl", CommonHelper.getRequestUrl(httpServletRequest));
        return "taskDoc/approveTaskDoc";
    }

    //教研室主任审核通过
    @RequestMapping(value = "approveTaskDocByDepartment.html")
    public void approvedByDirector(HttpServletResponse httpServletResponse, HttpSession httpSession, Integer taskDocId) {
        //获取当前用户
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentTutor(httpSession).getId());
        updateApprovedByDirector(taskDocId, tutor, true);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }

    //院长审核通过
    @RequestMapping(value = "approveTaskDocByDean.html")
    public void approvedByDean(HttpServletResponse httpServletResponse, HttpSession httpSession, Integer taskDocId) {
        //获取当前用户
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        updateApprovedByDean(taskDocId, tutor, true);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }

    //教研室主任和院长审核通过
    @RequestMapping(value = "approveTaskDocByDirectorAndDean.html")
    public void approvedByDirectorAndDean(HttpServletResponse httpServletResponse, HttpSession httpSession, Integer taskDocId) {
        //获取当前用户
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        updateApprovedByDirector(taskDocId, tutor, true);
        updateApprovedByDean(taskDocId, tutor, true);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }

    //教研室主任退回
    @RequestMapping("rejectTaskDocByDepartment.html")
    public void rejectByDirector(HttpServletResponse httpServletResponse, HttpSession httpSession, Integer taskDocId) {
        //获取当前用户
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentTutor(httpSession).getId());
        updateApprovedByDirector(taskDocId, tutor, false);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }

    //院长退回
    @RequestMapping("rejectTaskDocByDean")
    public void rejectByDean(HttpServletResponse httpServletResponse, HttpSession httpSession, Integer taskDocId) {
        //获取当前用户
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        updateApprovedByDean(taskDocId, tutor, false);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }

    //院长教研室主任退回
    @RequestMapping(value = "rejectTaskDocByDirectorAndDean.html")
    public void rejectByDirectorAndDean(HttpServletResponse httpServletResponse, HttpSession httpSession, Integer taskDocId) {
        //获取当前用户
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        updateApprovedByDirector(taskDocId, tutor, false);
        updateApprovedByDean(taskDocId, tutor, false);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }

    //修改院长的audit
    public void updateApprovedByDean(Integer taskDocId, Tutor tutor, Boolean approve) {
        //获取要审核的任务书
        TaskDoc taskDoc = taskDocService.findById(taskDocId);
        Audit auditByDean = taskDoc.getAuditByBean();
        //修改审核状态
        auditByDean.setApprove(approve);
        //修改审核日期
        auditByDean.setAuditDate(CommonHelper.getNow());
        //修改审核人
        auditByDean.setAuditor(tutor);
        //更新审核状态
        auditService.update(auditByDean);
        //对更新状态进行保存
        auditService.save(auditByDean);
    }

    //修改教研室主任的audit
    public void updateApprovedByDirector(Integer taskDocId, Tutor tutor, Boolean approve) {
        //获取要审核的任务书
        TaskDoc taskDoc = taskDocService.findById(taskDocId);
        Audit auditByDirector = taskDoc.getAuditByDepartmentDirector();
        //修改审核状态
        auditByDirector.setApprove(approve);
        //设置审核日期
        auditByDirector.setAuditDate(CommonHelper.getNow());
        //设置审核人
        auditByDirector.setAuditor(tutor);
        //更新审核状态
        auditService.update(auditByDirector);
        //对更新的审核状态进行保存
        auditService.save(auditByDirector);
    }

}
