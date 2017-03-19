package com.newview.bysj.web.studentPrint;

import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.domain.Student;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created 2016/5/5,17:20.
 * Author 张战.
 */
@Controller
@RequestMapping("print")
public class PrintProjectCoverController extends BaseController {

    //老师和学生打印论文附件封面的方法

    public void printProjectCover(ModelMap modelMap, HttpSession httpSession) {
        try {
            Student student = (Student) CommonHelper.getCurrentActor(httpSession);
            GraduateProject graduateProject = graduateProjectService.uniqueResult("student", student);
            List<GraduateProject> graduateProjectList = new ArrayList<>();
            graduateProjectList.add(graduateProject);
            modelMap.put("projectList", graduateProjectList);
        } catch (Exception e) {

        }
    }
}
