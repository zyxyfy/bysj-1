package com.newview.bysj.web.projectDetail;

import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.exception.MessageException;
import com.newview.bysj.web.baseController.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created 2016/4/2,13:05.
 * Author 张战.
 */

@Controller
@RequestMapping("process")
public class ProjectDetailController extends BaseController {
    @RequestMapping(value = "/showDetail.html", method = RequestMethod.GET)
    public String showDetail(Integer graduateProjectId, ModelMap modelMap) {
        GraduateProject graduateProject = graduateProjectService.findById(graduateProjectId);
        if (graduateProject == null) {
            throw new MessageException("对应的课题不存在");
        }
        modelMap.put("graduateProject", graduateProject);
        return "projectDetails/detailForProject";
    }
}
