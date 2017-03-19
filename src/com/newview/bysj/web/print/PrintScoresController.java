package com.newview.bysj.web.print;

import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.exception.MessageException;
import com.newview.bysj.web.baseController.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created 2016/3/1,16:08.
 * Author 张战.
 */
@Controller
public class PrintScoresController extends BaseController {

    //打印成绩评定册
    @RequestMapping(value = "print/printScores.html", method = RequestMethod.GET)
    public String printScores() {

        return "print/printScores";
    }

    @RequestMapping("/teacher/viewScores.html")
    public String showScore(Integer projectId, ModelMap modelMap) {
        if (projectId == null) {
            throw new MessageException("获取课题的id失败");
        } else {
            GraduateProject graduateProject = graduateProjectService.findById(projectId);
            modelMap.put("project", graduateProject);
            return "projectScore/showScore";
        }
    }


    @RequestMapping("/teacher/viewScore.html")
    public String showScore(Integer projectId, ModelMap modelMap, Integer pageNo, Integer pageSize) {
        if (projectId == null) {
            throw new MessageException("获取课题的id失败");
        } else {
            GraduateProject graduateProject = graduateProjectService.findById(projectId);
            modelMap.put("project", graduateProject);
            modelMap.put("pageNo", pageNo);
            modelMap.put("pageSize", pageSize);
            return "projectScore/showSco";
        }
    }

}
