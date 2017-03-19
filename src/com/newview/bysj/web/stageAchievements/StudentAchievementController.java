package com.newview.bysj.web.stageAchievements;

import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.domain.StageAchievement;
import com.newview.bysj.domain.Student;
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
import java.io.IOException;

@Controller
public class StudentAchievementController extends BaseController {
    private static Integer num = 0;

    private Integer addNum() {
        num = num + 1;
        return num;
    }

    @RequestMapping("student/stageAchievements")
    public String uploadStudentAchievement(HttpSession httpSession, ModelMap modelMap, Integer pageNo, Integer pageSize, HttpServletRequest httpServletRequest) {
        //当前学生
        Student student = studentService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        //获取学生课题
        GraduateProject graduateProject = student.getGraduateProject();
        if (graduateProject == null) {
            modelMap.put("message", "未选择课题，请联系指导老师");
            return "student/stageAchievement/stageAchievements";
        }
        //筛选阶段成果
        Page<StageAchievement> stageAchievements = stageAchievementService.getPageByGraduateProjects(graduateProject, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, stageAchievements, "stageAchievements", httpServletRequest.getRequestURI(), stageAchievements.getTotalElements());

        return "student/stageAchievement/stageAchievements";
    }

    //Integer num=0;
    @RequestMapping(value = "student/uploadStageAchievement", method = RequestMethod.POST)
    public String uploadStudentAchievement(HttpSession httpSession, HttpServletResponse httpServletResponse, MultipartFile stageAchievementFile) {
        //当前学生
        Student student = studentService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        //获取课题
        GraduateProject graduateProject = student.getGraduateProject();
        String fileNameExtension = stageAchievementFile.getOriginalFilename().substring(stageAchievementFile.getOriginalFilename().lastIndexOf("."));
        String url = CommonHelper.fileUpload(stageAchievementFile, httpSession, "stageAchievement", "阶段成果-" + student.getName() + student.getNo() + CommonHelper.getCurrentDateByPatter("yyyyMMdd") + fileNameExtension);
        //新建阶段成果
        StageAchievement achievement = new StageAchievement();
        //设置基本信息
        achievement.setIssuedDate(CommonHelper.getNow());
        achievement.setGraduateProject(graduateProject);
        achievement.setNum(this.addNum());
        achievement.setUrl(url);
        //更新
        stageAchievementService.saveOrUpdate(achievement);
        return "redirect:/student/stageAchievements.html";
    }

    //删除
    @RequestMapping(value = "student/deleteStageAchievement")
    public void delete(HttpSession httpSession, HttpServletResponse httpServletResponse, Integer stageAchievementId) {
        StageAchievement stageAchievement = stageAchievementService.findById(stageAchievementId);
        CommonHelper.delete(httpSession, stageAchievement.getUrl());
        stageAchievementService.deleteById(stageAchievementId);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }

    //下载
    @RequestMapping(value = "student/download/stageAchievement")
    public ResponseEntity<byte[]> download(Integer stageAchievementId, HttpSession httpSession) throws IOException {

        StageAchievement stageAchievement = stageAchievementService.findById(stageAchievementId);
        Student student = stageAchievement.getGraduateProject().getStudent();
        String extendName = stageAchievement.getFileName().substring(stageAchievement.getFileName().lastIndexOf("."));
        String fileName = "阶段成果" + student.getName() + student.getNo() + extendName;
        return CommonHelper.download(httpSession, stageAchievement.getUrl(), fileName);
    }
}
