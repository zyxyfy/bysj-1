package com.newview.bysj.web.projectManage;

import com.newview.bysj.domain.DesignProject;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created 2016/2/24,15:32.
 * Author 张战.
 */

@Controller
@RequestMapping("process")
public class DesignProjectController extends BaseController {

    private static final Logger logger = Logger.getLogger(DesignProjectController.class);

    @RequestMapping(value = "/addOrEditDesignProject.html", method = RequestMethod.GET)
    public String addOrEditDesignProject(HttpSession httpSession, ModelMap modelMap, Integer editId, HttpServletRequest httpServletRequest) {
        if (editId == null) {
            DesignProject designProject = new DesignProject();
            designProject.setYear(CommonHelper.getYear());
            //添加到modelmap中，供graduateProjectService.toAddOrUpdateProject方法获取
            modelMap.put("project", designProject);
            //新增加的，没有id，所以传null
            graduateProjectService.toAddOrUpdateProject(httpSession, modelMap, null);
        } else {
            graduateProjectService.toAddOrUpdateProject(httpSession, modelMap, editId);
        }
        modelMap.put("actionUrl", httpServletRequest.getRequestURI());
        modelMap.put("schoolList", schoolService.findAll());
        return "editProject/editProject";
    }

    @RequestMapping(value = "/addOrEditDesignProject.html", method = RequestMethod.POST)
    public String addOrEditDesignProject(@ModelAttribute("toEditProject") DesignProject toEditProject, HttpSession httpSession, Integer year, Integer majorId) {

        graduateProjectService.addOrUpdateProject(toEditProject, httpSession, year, majorId);
        return "redirect:/process/myProjects.html";
    }


}
