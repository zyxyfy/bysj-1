package com.newview.bysj.web.studentOpenningReport;

import com.newview.bysj.domain.*;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@Controller
public class StudentUploadOpenningReport extends BaseController {
    @RequestMapping("student/uploadOpenningReport")
    public String uploadOpenningReport(HttpSession httpSession, ModelMap modelMap) {
        Student student = studentService.findById(((Student) CommonHelper.getCurrentActor(httpSession)).getId());
        if (student.getGraduateProject() == null) {
            modelMap.addAttribute("message", "目前没有课题，请尽快与指导老师联系");
        } else {
            GraduateProject graduateProject = student.getGraduateProject();
            //instanceof判断其左边对象是否为其右边类的实例;判断继承中的子类的实例是否为父类的实现
            if (graduateProject instanceof PaperProject) {
                if (graduateProject.getTaskDoc() == null) {
                    modelMap.addAttribute("message", "指导老师还没有上传任务书请耐心等待");
                } else {
                    if (graduateProject.getTaskDoc().getAuditByDepartmentDirector() == null || graduateProject.getTaskDoc().getAuditByDepartmentDirector().getApprove() == null) {
                        modelMap.addAttribute("message", "任务书正在审核中，请耐心等待");
                    }
                    if (graduateProject.getTaskDoc().getAuditByDepartmentDirector().getApprove() == false) {
                        modelMap.addAttribute("message", "教研室主任审核任务书未通过，请耐心等待");
                    }
                    //跳转到添加开题报告的界面
                    modelMap.addAttribute("student", student);
                    modelMap.addAttribute("paperProject", (PaperProject) graduateProject);
                    return "student/openningReport/uploadOpenningReport";
                }

            } else {
                modelMap.addAttribute("message", "设计题目不需要上传开题报告");
            }
        }
        return "student/openningReport/noOpenningReport";
    }

    //上传开题报告
    @RequestMapping(value = "openningReport/openningReportuploaded.html", method = RequestMethod.POST)
    public String upload(@RequestParam("openningReportFile") MultipartFile openningReportFile, HttpSession httpSession, HttpServletResponse httpServletResponse, Integer paperProjectId) {
        PaperProject paperProject = paperProjectService.findById(paperProjectId);
        OpenningReport openningReport = null;
        Audit auditByDepartmentDirector = null;
        Audit auditByTutor = null;
        if (paperProject.getOpenningReport() == null) {
            //新建开题报告
            openningReport = new OpenningReport();
            openningReport.setPaperProject(paperProject);
            openningReportService.save(openningReport);
            //保存后需要重新获取openingReport，否则paperProject更新会出错
            openningReport = openningReportService.uniqueResult("paperProject", PaperProject.class, paperProject);
            paperProject.setOpenningReport(openningReport);
            //更新paperProject
            paperProjectService.update(paperProject);
            //将更新状态进行保存
            paperProjectService.save(paperProject);
        } else {
            openningReport = paperProject.getOpenningReport();
        }
        //教研室主任审核
        if (paperProject.getOpenningReport().getAuditByDepartmentDirector() == null) {
            auditByDepartmentDirector = new Audit();
            auditByDepartmentDirector.setApprove(true);
            auditByDepartmentDirector.setAuditDate(CommonHelper.getNow());
            auditService.save(auditByDepartmentDirector);
            openningReport.setAuditByDepartmentDirector(auditByDepartmentDirector);
            openningReportService.update(openningReport);
            //对更新状态进行保存
            openningReportService.save(openningReport);
        }
        //指导教师审核
        if (paperProject.getOpenningReport().getAuditByTutor() == null) {
            auditByTutor = new Audit();
            auditByTutor.setApprove(true);
            auditByTutor.setAuditDate(CommonHelper.getNow());
            auditService.save(auditByTutor);
            openningReport.setAuditByTutor(auditByTutor);
            openningReportService.update(openningReport);
            //对更新状态进行保存
            openningReportService.save(openningReport);
        }

        String folderName = "openningReport";
        String extendName = openningReportFile.getOriginalFilename().substring(openningReportFile.getOriginalFilename().lastIndexOf("."));
        Student student = paperProject.getStudent();
        String fileName = student.getName() + student.getNo() + extendName;
        String url = CommonHelper.fileUpload(openningReportFile, httpSession, folderName, fileName);
        openningReport.setSubmittedByStudent(true);
        openningReport.setUrl(url);
        openningReportService.update(openningReport);
        //对更新状态进行保存
        openningReportService.save(openningReport);
        paperProject.setOpenningReport(openningReport);
        paperProjectService.update(paperProject);
        //对更新状态进行保存
        paperProjectService.save(paperProject);
        //CommonHelper.buildSimpleJson(httpServletResponse);
        return "redirect:/student/uploadOpenningReport.html";
    }

    //下载开题报告
    @RequestMapping("student/openningReport/downloadOpenningReport.html")
    public ResponseEntity<byte[]> download(HttpSession httpSession, Integer paperProjectId) throws IOException {
        OpenningReport openningReport = paperProjectService.findById(paperProjectId).getOpenningReport();
        String url = openningReport.getUrl();
        File file = new File(url);
        return CommonHelper.download(httpSession, url, file.getName());
    }

    //删除开题报告
    @RequestMapping("openningReport/deleteOpenningReport.html")
    public void deleteOpenningReport(HttpSession httpSession, HttpServletResponse httpServletResponse) {
        Student student = studentService.findById(((Student) CommonHelper.getCurrentActor(httpSession)).getId());
        PaperProject paperProject = (PaperProject) student.getGraduateProject();
        OpenningReport openningReport = (paperProject.getOpenningReport());
        if (openningReport.getUrl() != null) {
            CommonHelper.delete(httpSession, openningReport.getUrl());
        }
        //取消openningReport与paperProject的双向关联
        openningReport.setPaperProject(null);
        openningReportService.saveOrUpdate(openningReport);
        paperProject.setOpenningReport(null);
        paperProjectService.saveOrUpdate(paperProject);
        openningReportService.deleteObject(openningReport);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }
}
