package com.newview.bysj.web.taskDocManage;

import com.newview.bysj.domain.*;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Controller
public class TaskDocManageController extends BaseController {

    //下达任务书的方法
    @RequestMapping(value = "tutor/taskDocManage.html", method = RequestMethod.GET)
    public String uploadTaskDoc(HttpSession httpSession, HttpServletRequest httpServletRequest, ModelMap modelMap, Integer pageNo, Integer pageSize) {
        //获取当前用户
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        //获取已经分配学生的课题
        Page<GraduateProject> graduateProject = graduateProjectService.getPagesByProposerIfStudenSelected(tutor, true, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, graduateProject, "graduateProjects", CommonHelper.getRequestUrl(httpServletRequest), graduateProject.getTotalElements());
        return "taskDoc/uploadTaskDoc";
    }

    //上传任务书
    @RequestMapping(value = "/tutor/taskDocUpLoad.html", method = RequestMethod.POST)
    public String upload(HttpServletResponse httpServletResponse, HttpSession httpSession, MultipartFile taskDocAttachment, Integer graduateProjectId) {
        GraduateProject graduateProject = graduateProjectService.findById(graduateProjectId);
        TaskDoc taskDoc = null;
        if (graduateProject.getTaskDoc() == null) {
            taskDoc = new TaskDoc();
            taskDoc.setGraduateProject(graduateProject);
            //上传即审核通过
            Audit auditByDepartmentDirector = new Audit(true, CommonHelper.getNow());
            taskDoc.setAuditByDepartmentDirector(auditByDepartmentDirector);
            Audit auditByBean = new Audit(true, CommonHelper.getNow());
            taskDoc.setAuditByBean(auditByBean);
            taskDocService.save(taskDoc);
            //重新获取保存后的任务书，否则graduateProject更新会出错
            taskDoc = taskDocService.uniqueResult("graduateProject", GraduateProject.class, graduateProject);
            //graduateProjectService.update(graduateProject);
        } else {//当审核状态为驳回时，已经存在任务书
            taskDoc = graduateProject.getTaskDoc();
            //删除原来的任务书文件
            CommonHelper.delete(httpSession, taskDoc.getUrl());
            //重新设置审核状态为通过
            Audit auditByBean = taskDoc.getAuditByBean();
            auditByBean.setApprove(true);
            auditByBean.setAuditDate(CommonHelper.getNow());

            Audit auditByDepartmentDirector = taskDoc.getAuditByDepartmentDirector();
            auditByDepartmentDirector.setApprove(true);
            auditByDepartmentDirector.setAuditDate(CommonHelper.getNow());

            auditService.update(auditByBean);
            //将更新后的auditByBean保存到数据库
            auditService.save(auditByBean);
            auditService.update(auditByDepartmentDirector);
            //将更新后的auditByDepartment保存到数据库
            auditService.save(auditByDepartmentDirector);
        }
        //上传任务书附件
        String fodlerName = "taskDoc";//文件夹的名字
        Student student = graduateProject.getStudent();
        //获取文件的扩展名
        String extend = taskDocAttachment.getOriginalFilename().substring(taskDocAttachment.getOriginalFilename().lastIndexOf("."));
        String fileName = student.getName() + student.getNo() + "任务书" + extend;
        String url = CommonHelper.fileUpload(taskDocAttachment, httpSession, fodlerName, fileName);
        taskDoc.setUrl(url);
        taskDocService.update(taskDoc);
        //对更新状态进行保存
        taskDocService.save(taskDoc);
        graduateProject.setTaskDoc(taskDoc);
        //对更新状态进行保存
        graduateProjectService.update(graduateProject);
        return "redirect:taskDocManage.html";
    }

    //下载任务书
    @RequestMapping(value = "tutor/downLoadTaskDoc")
    public ResponseEntity<byte[]> downLoadTaskDoc(HttpSession httpSession, Integer taskDocId) throws IOException {
        TaskDoc taskDoc = taskDocService.findById(taskDocId);
        Student student = taskDoc.getGraduateProject().getStudent();
        String fileName = "任务书-" + student.getNo() + "-" + student.getName() + "-" + taskDoc.getGraduateProject().getTitle();
        File taskFile = new File(taskDoc.getUrl());
        String extendName = taskFile.getName().substring(taskFile.getName().lastIndexOf("."));
        if (taskDoc.getGraduateProject().getSubTitle() != null && !Objects.equals(taskDoc.getGraduateProject().getSubTitle(), "")) {
            fileName = fileName + "-" + taskDoc.getGraduateProject().getSubTitle() + extendName;
        } else {
            fileName = fileName + extendName;
        }
        String url = taskDoc.getUrl();
        return CommonHelper.download(httpSession, url, fileName);
    }

    //删除任务书
    @RequestMapping(value = "tutor/deleteTaskDoc")
    public void deleteTaskDoc(HttpServletResponse httpServletResponse, HttpSession httpSession, Integer taskDocId) {
        taskDocService.deleteTaskDoc(taskDocId, httpSession);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }
}
