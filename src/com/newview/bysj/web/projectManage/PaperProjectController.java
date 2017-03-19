package com.newview.bysj.web.projectManage;

import com.newview.bysj.domain.PaperProject;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

/**
 * Created 2016/2/24,15:32.
 * Author 张战.
 */
@Controller
@RequestMapping("process")
public class PaperProjectController extends BaseController {

    @RequestMapping(value = "/addOrEditPaperProject.html", method = RequestMethod.GET)
    public String addOrEditPaperProject(ModelMap modelMap, HttpSession httpSession, Integer editId, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException {
        if (editId == null) {
            PaperProject paperProject = new PaperProject();
            paperProject.setYear(CommonHelper.getYear());
            modelMap.put("project", paperProject);
            graduateProjectService.toAddOrUpdateProject(httpSession, modelMap, editId);
        } else {
            graduateProjectService.toAddOrUpdateProject(httpSession, modelMap, editId);
        }
        httpServletRequest.setCharacterEncoding("GBK");
        modelMap.put("actionUrl", httpServletRequest.getRequestURI());
        modelMap.put("schoolList", schoolService.findAll());
        return "editProject/editProject";
    }

    @RequestMapping(value = "/addOrEditPaperProject.html", method = RequestMethod.POST)
    public String addOrEditPaperProject(HttpSession httpSession, Integer year, Integer majorId, @ModelAttribute("toEditProject") PaperProject paperProject) {
        graduateProjectService.addOrUpdateProject(paperProject, httpSession, year, majorId);
        return "redirect:/process/myProjects.html";
    }
}
