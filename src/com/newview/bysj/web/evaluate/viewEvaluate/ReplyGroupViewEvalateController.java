package com.newview.bysj.web.evaluate.viewEvaluate;

import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.web.baseController.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created 2016/5/30,18:47.
 * Author 张战.
 */
@Controller
public class ReplyGroupViewEvalateController extends BaseController {

    /**
     * 查看指导老师评审表
     *
     * @param projectId 要查看的评审表对应课题的id
     * @param modelMap  存储需要在jsp中获取的数据
     * @return jsp
     */
    @RequestMapping(value = "/replyGroupViewTutorEvaluate.html", method = RequestMethod.GET)
    public String viewTutorEvaluate(Integer projectId, ModelMap modelMap) {
        GraduateProject graduateProject = graduateProjectService.findById(projectId);
        modelMap.put("graduateProject", graduateProject);
        return "evaluate/replyGroupViewEvaluate/viewTutorEvaluate";
    }

    @RequestMapping("/replyGroupViewReviewerEvaluate.html")
    public String viewReviewerEvaluate(Integer projectId, ModelMap modelMap) {
        GraduateProject graduateProject = graduateProjectService.findById(projectId);
        modelMap.put("graduateProject", graduateProject);
        return "evaluate/replyGroupViewEvaluate/viewReviewerEvaluate";
    }
}

