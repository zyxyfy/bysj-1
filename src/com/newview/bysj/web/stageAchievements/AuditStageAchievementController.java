package com.newview.bysj.web.stageAchievements;

import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.domain.StageAchievement;
import com.newview.bysj.domain.Student;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@Controller
public class AuditStageAchievementController extends BaseController {

    //审阅各阶段成果的初界面
    @RequestMapping(value = "stageAchievements.html", method = RequestMethod.GET)
    public String approveAchievements(HttpSession httpSession, HttpServletRequest httpServletRequest, ModelMap modelMap, Integer pageNo, Integer pageSize) {
        //获取当前用户
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        //获取作为主指导的课题
        Page<GraduateProject> graduateProjects = graduateProjectService.getPageByMainTutorage(tutor, pageNo, pageSize);
        //分页
        CommonHelper.pagingHelp(modelMap, graduateProjects, "graduateProjects", CommonHelper.getRequestUrl(httpServletRequest), graduateProjects.getTotalElements());
        return "stageAchievement/stageAchievementList";
    }

    //审核阶段成果的详细界面
    @RequestMapping(value = "auditStageAchievement")
    public String auditStageAchievement(HttpServletRequest httpServletRequest, Integer graduateProjectId, ModelMap modelMap, Integer pageNo, Integer pageSize) {
        GraduateProject graduateProject = graduateProjectService.findById(graduateProjectId);
        //根据课题获取要审核的阶段成果
        Page<StageAchievement> stageAchievements = stageAchievementService.getPageByGraduateProjects(graduateProject, pageNo, 100);
        //分页
        CommonHelper.pagingHelp(modelMap, stageAchievements, "stageAchievements", CommonHelper.getRequestUrl(httpServletRequest), stageAchievements.getTotalElements());
        modelMap.addAttribute("graduateProject", graduateProject);
        return "stageAchievement/auditStageAchievement";
    }

    //填写审核阶段成果的评语
    @RequestMapping(value = "writeRemark", method = RequestMethod.GET)
    public String toWriteRemark(Integer stageAchievementId, ModelMap modelMap, HttpServletRequest httpServletRequest) {
        StageAchievement stageAchievement = stageAchievementService.findById(stageAchievementId);
        modelMap.addAttribute("stageAchievement", stageAchievement);
        modelMap.addAttribute("actionUrl", CommonHelper.getRequestUrl(httpServletRequest));
        return "stageAchievement/writeRemark";
    }

    @RequestMapping(value = "writeRemark", method = RequestMethod.POST)
    public String WriteRemark(HttpServletResponse httpServletResponse, String remark, Integer stageAchievementId) {
        StageAchievement stageAchievement = stageAchievementService.findById(stageAchievementId);
        Integer graduateProjectId = stageAchievement.getGraduateProject().getId();
        stageAchievement.setRemark(remark);
        stageAchievementService.update(stageAchievement);
        //对更新状态进行保存
        stageAchievementService.save(stageAchievement);
        //CommonHelper.buildSimpleJson(httpServletResponse);
        return "redirect:/auditStageAchievement.html?graduateProjectId=" + graduateProjectId;
    }

    //修改审核状态的评语
    @RequestMapping(value = "editRemark", method = RequestMethod.GET)
    public String editRemark(Integer stageAchievementId, String remark, ModelMap modelMap, HttpServletRequest httpServletRequest) {
        StageAchievement stageAchievement = stageAchievementService.findById(stageAchievementId);
        modelMap.addAttribute("stageAchievement", stageAchievement);
        modelMap.addAttribute("actionUrl", CommonHelper.getRequestUrl(httpServletRequest));
        return "stageAchievement/writeRemark";
    }

    @RequestMapping(value = "editRemark", method = RequestMethod.POST)
    public String editRemark(Integer stageAchievementId, String remark, HttpServletResponse httpServletResponse) {
        StageAchievement stageAchievement = stageAchievementService.findById(stageAchievementId);
        Integer graduateProjectId = stageAchievement.getGraduateProject().getId();
        //System.out.println("id=="+graduateProjectId);
        stageAchievement.setRemark(remark);
        stageAchievementService.update(stageAchievement);
        //对更新状态进行保存
        stageAchievementService.save(stageAchievement);
        //CommonHelper.buildSimpleJson(httpServletResponse);
        return "redirect:/auditStageAchievement.html?graduateProjectId=" + graduateProjectId;
    }

    //下载阶段成果
    @RequestMapping("download/stageAchievement")
    public ResponseEntity<byte[]> download(Integer stageAchievementId, HttpSession httpSession) throws IOException {
        Student student;
        String name;
        StageAchievement stageAchievement = stageAchievementService.findById(stageAchievementId);
        File file = new File(stageAchievement.getUrl());

        if (stageAchievement.getGraduateProject() != null) {
            if (stageAchievement.getGraduateProject().getStudent() != null) {
                student = stageAchievement.getGraduateProject().getStudent();
                name = file.getName().substring(0, file.getName().lastIndexOf(".")) + "——" + student.getNo() + "-" + student.getName() + file.getName().substring(file.getName().lastIndexOf("."));
            } else {
                name = file.getName().substring(0, file.getName().lastIndexOf(".")) + "——" + "未获取" + file.getName().substring(file.getName().lastIndexOf("."));
            }
        } else {
            name = file.getName().substring(0, file.getName().lastIndexOf(".")) + "——" + "未获取" + file.getName().substring(file.getName().lastIndexOf("."));
        }
        return CommonHelper.download(httpSession, stageAchievement.getUrl(), name);
    }
}
