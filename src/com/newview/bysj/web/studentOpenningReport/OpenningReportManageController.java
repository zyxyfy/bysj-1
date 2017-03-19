package com.newview.bysj.web.studentOpenningReport;

import com.newview.bysj.domain.*;
import com.newview.bysj.exception.MessageException;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@Controller
public class OpenningReportManageController extends BaseController {

    private static final Logger logger = Logger.getLogger(OpenningReportManageController.class);

    //审核开题报告的方法
    @RequestMapping("openningReportList")
    public String openningReportList(ModelMap modelMap, Integer pageNo, Integer pageSize) {
        modelMap.put("pageNo", pageNo);
        modelMap.put("pageSize", pageSize);
        //根据jsp不同角色跳转到不同的方法
        return "openningReport/redirectOpenningReport";
    }

    //获取主指导审核的开题报告
    @RequestMapping("getOpenningReportsByTutor")
    public String getByMainTutorage(HttpSession httpSession, HttpServletRequest httpServletRequest, ModelMap modelMap, Integer pageNo, Integer pageSize) {
        //获取当前用户
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        //筛选开题报告
        Page<PaperProject> paperProjects = paperProjectService.getPaperProjectByMainTutorage(tutor, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, paperProjects, "paperProjects", CommonHelper.getRequestUrl(httpServletRequest), paperProjects.getTotalElements());
        modelMap.addAttribute("actionUrl", CommonHelper.getRequestUrl(httpServletRequest));
        modelMap.addAttribute("queryReport", "/bysj3/queryGetOpenningReportsByTutor.html");
        return "openningReport/listOpenningReport";
    }


    //获取主指导审核的开题报告，用于检索使用
    @RequestMapping("queryGetOpenningReportsByTutor")
    public String getByMainTutorage(HttpSession httpSession, HttpServletRequest httpServletRequest, ModelMap modelMap, Integer pageNo, Integer pageSize, Boolean approve) {
        //获取当前用户
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        //筛选开题报告
        Page<OpenningReport> openningReport = openningReportService.getAuditedOpenningReportByTutor(tutor, approve, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, openningReport, "openningReports", CommonHelper.getRequestUrl(httpServletRequest), openningReport.getTotalElements());
        //modelMap.addAttribute("actionUrl",CommonHelper.getRequestUrl(httpServletRequest));
        modelMap.addAttribute("queryReport", "/bysj3/queryGetOpenningReportsByTutor.html");
        modelMap.addAttribute("actionUrl", "/bysj3/getOpenningReportsByTutor.html");
        return "openningReport/listOpenningReport";
    }


    //------------------------------------------
    //获取教研室主任审核开题报告的所有课题
    @RequestMapping("getOpenningReportsByDirector")
    public String getByDirector(HttpSession httpSession, HttpServletRequest httpServletRequest, ModelMap modelMap, Integer pageNo, Integer pageSize) {
        //获取当前用户
        Tutor director = tutorService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        //筛选开题报告
        Page<PaperProject> paperProjects = paperProjectService.getPaperProjectByDepartment(director, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, paperProjects, "paperProjects", CommonHelper.getRequestUrl(httpServletRequest), paperProjects.getTotalElements());
        modelMap.addAttribute("actionUrl", CommonHelper.getRequestUrl(httpServletRequest));
        modelMap.addAttribute("queryReport", "/bysj3/queryGetOpenningReportsByDirector.html");
        return "openningReport/listOpenningReport";
    }

    //获取教研室主任审核的开题报告
    @RequestMapping("queryGetOpenningReportsByDirector")
    public String getByDirector(HttpSession httpSession, HttpServletRequest httpServletRequest, ModelMap modelMap, Integer pageNo, Integer pageSize, Boolean approve) {
        //获取当前用户
        Tutor director = tutorService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        //筛选开题报告
        Page<OpenningReport> openningReport = openningReportService.getAuditedOpenningReportByDirector(director, approve, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, openningReport, "openningReports", CommonHelper.getRequestUrl(httpServletRequest), openningReport.getTotalElements());
        //modelMap.addAttribute("actionUrl",CommonHelper.getRequestUrl(httpServletRequest));
        modelMap.addAttribute("queryReport", "/bysj3/queryGetOpenningReportsByDirector.html");
        modelMap.addAttribute("actionUrl", "/bysj3/getOpenningReportsByDirector.html");

        return "openningReport/listOpenningReport";
    }
    //-------------------------------------

    //有教研室主任和指导老师两个角色的所有论文课题
    @RequestMapping("getOpenningReportsByTutorAndDirector")
    public String getByDirectorAndTutor(HttpSession httpSession, HttpServletRequest httpServletRequest, ModelMap modelMap, Integer pageNo, Integer pageSize) {
        //获取当前用户
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        //筛选开题报告
        Page<PaperProject> paperProjects = paperProjectService.getPaperProjectByMainTutorageAndDepartment(tutor, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, paperProjects, "paperProjects", CommonHelper.getRequestUrl(httpServletRequest), paperProjects.getTotalElements());
        modelMap.addAttribute("actionUrl", CommonHelper.getRequestUrl(httpServletRequest));
        modelMap.addAttribute("queryReport", "/bysj3/queryGetOpenningReportsByTutorAndDirector.html");
        return "openningReport/listOpenningReport";
    }

    //有教研室主任和指导老师两个角色的开题报告
    @RequestMapping("queryGetOpenningReportsByTutorAndDirector")
    public String getByDirectorAndTutor(HttpSession httpSession, HttpServletRequest httpServletRequest, ModelMap modelMap, Integer pageNo, Integer pageSize, Boolean approve) {
        //获取当前用户
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        //筛选开题报告
        Page<OpenningReport> openningReport = openningReportService.getAuditOpenningReportByTutorAndDirector(tutor, approve, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, openningReport, "openningReports", CommonHelper.getRequestUrl(httpServletRequest), openningReport.getTotalElements());
        //modelMap.addAttribute("actionUrl",CommonHelper.getRequestUrl(httpServletRequest));
        modelMap.addAttribute("actionUrl", "/bysj3/getOpenningReportsByTutorAndDirector.html");
        modelMap.addAttribute("queryReport", "/bysj3/queryGetOpenningReportsByTutorAndDirector.html");
        return "openningReport/listOpenningReport";
    }


    //-----------------


    //主指导审核通过
    @RequestMapping("approveOpenningReportByTutor.html")
    public void approvedByMainTutor(HttpSession httpSession, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, Integer openningReportId) {
        //获取当前用户
        Tutor currentTutor = tutorService.findById(CommonHelper.getCurrentTutor(httpSession).getId());
        approvedByMainTutorage(currentTutor, openningReportId, true);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }

    //教研室主任审核通过
    @RequestMapping("approveOpenningReportByDirector.html")
    public void approvedByDepartment(HttpSession httpSession, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, Integer openningReportId) {
        //获取当前用户
        Tutor currentTutor = tutorService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        approvedByDirector(currentTutor, openningReportId, true);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }

    //教研室主任与主指导审核通过
    @RequestMapping("approveOpenningReportByDirectorAndDean.html")
    public void approvedByDirectorAndTutor(HttpSession httpSession, HttpServletResponse httpServletReponse, HttpServletRequest httpServletRequest, Integer openningReportId) {
        //获取当前用户
        Tutor currentTutor = tutorService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        approvedByDirector(currentTutor, openningReportId, true);
        approvedByMainTutorage(currentTutor, openningReportId, true);
    }

    //指导老师退回
    @RequestMapping("rejectOpenningReportByTutor.html")
    public void rejectByMaintTutorage(HttpSession httpSession, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, Integer openningReportId) {
        //获取当前用户
        Tutor currentTutor = tutorService.findById(CommonHelper.getCurrentTutor(httpSession).getId());
        approvedByMainTutorage(currentTutor, openningReportId, false);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }

    //教研室主任退回
    @RequestMapping("rejectOpenningReportByDirector.html")
    public void rejectByDirector(HttpSession httpSession, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, Integer openningReportId) {
        //获取当前用户
        Tutor currentTutor = tutorService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        approvedByDirector(currentTutor, openningReportId, false);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }

    //教研室主任和指导老师退回
    @RequestMapping("rejectOpenningReportByDirectorAndDean.html")
    public void rejectByDirectorAndDean(HttpSession httpSession, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, Integer openningReportId) {
        //获取当前用户
        Tutor currentTutor = tutorService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        approvedByDirector(currentTutor, openningReportId, false);
        approvedByMainTutorage(currentTutor, openningReportId, false);
    }

    //修改教研室主任的audit
    public void approvedByDirector(Tutor currentTutor, Integer openningReportId, Boolean approved) {
        OpenningReport openningReport = openningReportService.findById(openningReportId);
        Audit auditByDirector = openningReport.getAuditByDepartmentDirector();
        //修改审核状态
        auditByDirector.setApprove(approved);
        //设置审核日期
        auditByDirector.setAuditDate(CommonHelper.getNow());
        //设置审核人
        auditByDirector.setAuditor(currentTutor);
        //更新审核对象
        auditService.update(auditByDirector);
        //保存更新后的对象
        auditService.save(auditByDirector);

    }

    //修改主指导的audit
    public void approvedByMainTutorage(Tutor currentTutor, Integer openningReportId, Boolean approved) {
        OpenningReport openningReport = openningReportService.findById(openningReportId);
        Audit audit = openningReport.getAuditByTutor();
        //修改审核状态
        audit.setApprove(approved);
        //设置审核日期
        audit.setAuditDate(CommonHelper.getNow());
        //设置审核人
        audit.setAuditor(currentTutor);
        //更新审核对象
        auditService.update(audit);
        //保存更新后的审核对象
        auditService.save(audit);
    }

    //下载开题报告
    @RequestMapping("downloadOpenningReport")
    public ResponseEntity<byte[]> download(Integer openningReportId, HttpSession httpSession) throws IOException {
        OpenningReport openningReport = openningReportService.findById(openningReportId);
        File file = new File(openningReport.getUrl());
        Student student = openningReport.getPaperProject().getStudent();
        /*String extendName ;
		try {
			extendName = file.getName().substring(file.getName().lastIndexOf("."));
		} catch (Exception e) {
			extendName = ".doc";
		}*/
        String fileName = "开题报告-" + student.getNo() + "-" + student.getName() + "-" + file.getName();
        return CommonHelper.download(httpSession, openningReport.getUrl(), fileName);
    }

    //根据课题的id来下载
    @RequestMapping("downloadOpenningReportByGraduateProjectId")
    public ResponseEntity<byte[]> downloadByGraduateProjectId(Integer projectId, HttpSession httpSession) throws IOException {
        GraduateProject graduateProject = graduateProjectService.findById(projectId);
        PaperProject paperProject = null;
        if (graduateProject instanceof PaperProject) {
            paperProject = (PaperProject) graduateProject;
        } else {
            throw new MessageException("类型转换失败！");
        }

        Integer openningReportId;
        if (paperProject.getOpenningReport() != null) {
            openningReportId = paperProject.getOpenningReport().getId();
        } else {
            return null;
        }
        return this.download(openningReportId, httpSession);
    }
}
