package com.newview.bysj.web.studentFinalDraft;

import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.domain.Student;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Controller
public class StudentFinalDraftController extends BaseController {
    @RequestMapping("student/finalDraft")
    public String toUploadfinalDraft(ModelMap modelMap, HttpSession httpSession, Integer pageNo, Integer pageSize) {
        //获取当前学生
        Student student = studentService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        if (student.getGraduateProject() == null) {
            modelMap.put("message", "没有选择课题，请联系指导老师");
            return "student/finalDraft/finalDraft";
        }
        GraduateProject graduateProject = student.getGraduateProject();
        modelMap.addAttribute("graduateProject", graduateProject);
        return "student/finalDraft/finalDraft";
    }

    //上传
    @RequestMapping("student/uploadFinalDraft")
    public String uploadFinalDraft(HttpServletResponse httpServletResponse, MultipartFile finalDraftFile, HttpSession httpSession, Integer graduateProjectId) {
        //获取课题
        GraduateProject graduateProject = graduateProjectService.findById(graduateProjectId);
        if (finalDraftFile != null && finalDraftFile.getSize() > 0) {
            String folderName = "finalDraft";
            String extendName = finalDraftFile.getOriginalFilename().substring(finalDraftFile.getOriginalFilename().lastIndexOf("."));
            Student student = graduateProject.getStudent();
            String fileName = student.getName() + student.getNo() + extendName;
            String url = CommonHelper.fileUpload(finalDraftFile, httpSession, folderName, fileName);
            graduateProject.setFinalDraft(url);
        }
        graduateProjectService.update(graduateProject);
        //对更新状态进行保存
        graduateProjectService.save(graduateProject);
        return "redirect:/student/finalDraft.html";
    }

    //下载
    @RequestMapping("student/download/finalDraft")
    public ResponseEntity<byte[]> downLoadFinalDraft(HttpSession httpSession, Integer graduateProjectId) throws IOException {
        //获取课题
        GraduateProject graduateProject = graduateProjectService.findById(graduateProjectId);
        Student student = graduateProject.getStudent();
        File file = new File(graduateProject.getFinalDraft());
        String fileName = "终稿-" + student.getNo() + "-" + student.getName() + "-" + graduateProject.getTitle();
        if (graduateProject.getSubTitle() != null && !Objects.equals(graduateProject.getSubTitle(), "")) {
            fileName = fileName + "-" + graduateProject.getSubTitle();
        }
        try {
            fileName = fileName + file.getName().substring(file.getName().lastIndexOf("."));
        } catch (Exception e) {
            fileName = fileName + ".doc";
        }
        return CommonHelper.download(httpSession, graduateProject.getFinalDraft(), fileName);
    }

    //删除
    @RequestMapping("student/deleteFinalDraft")
    public void delete(HttpServletResponse httpServletResponse, Integer graduateProjectId, HttpSession httpSession) {
        GraduateProject graduateProject = graduateProjectService.findById(graduateProjectId);
        //删除终稿
        CommonHelper.delete(httpSession, graduateProject.getFinalDraft());
        graduateProject.setFinalDraft(null);
        graduateProjectService.update(graduateProject);
        //对更新状态进行保存
        graduateProjectService.save(graduateProject);
        CommonHelper.buildSimpleJson(httpServletResponse);

    }
}
