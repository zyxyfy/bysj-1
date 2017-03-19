package com.newview.bysj.web.evaluate.viewEvaluate;

import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.web.baseController.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created 2016/5/30,18:59.
 * Author 张战.
 */
@Controller
public class ReviewerViewEvaluateController extends BaseController {

    @RequestMapping(value = "/reviewerViewTutorEvaluate.html", method = RequestMethod.GET)
    public String viewTutorEvaluate(Integer projectId, ModelMap modelMap) {
        GraduateProject graduateProject = graduateProjectService.findById(projectId);
        modelMap.put("graduateProject", graduateProject);
        return "evaluate/reviewerViewEvaluate/viewTutorEvaluate";
    }

}
