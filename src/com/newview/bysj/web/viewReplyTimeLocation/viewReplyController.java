package com.newview.bysj.web.viewReplyTimeLocation;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.domain.Student;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;

@Controller
public class viewReplyController extends BaseController {
    @RequestMapping("student/viewReplyClassRoom")
    public String viewReplyClassRoom(HttpSession httpSession, ModelMap modelMap) {
        Student student = studentService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        if (student.getGraduateProject() == null) {
            modelMap.addAttribute("message", "尚未分配毕业设计题目，请尽快联系指导老师");
            return "student/openningReport/noOpenningReport";
        } else {
            if (student.getGraduateProject().getReplyGroup() == null || student.getGraduateProject().getReplyGroup().getClassRoom() == null) {
                modelMap.addAttribute("message", "答辩小组尚未确定答辩地点，请耐心等待");
                return "student/openningReport/noOpenningReport";
            } else {
                GraduateProject graduateProject = student.getGraduateProject();
                modelMap.addAttribute("graduateProject", graduateProject);
            }
        }
        return "student/viewReplyClassRoom";
    }
}
