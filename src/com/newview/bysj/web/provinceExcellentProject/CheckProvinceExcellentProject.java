package com.newview.bysj.web.provinceExcellentProject;


import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.newview.bysj.domain.ProvinceExcellentProject;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;

@Controller
public class CheckProvinceExcellentProject extends BaseController {
    @RequestMapping("projects/listProvenceExcellentProjects.html")
    public String checkProvinceExcellentProject(ModelMap modelMap, HttpServletRequest httpServletReuqest, Integer pageNo, @ModelAttribute("title") String title, @ModelAttribute("tutorName") String tutorName, Integer pageSize) {
        //显示省优课题和查询省优课题
        Page<ProvinceExcellentProject> currentPage = provinceExcellentProjectService.getPagesProvinceExcellentProjectsBySchoolAdmin(title == null ? "" : title, tutorName == null ? "" : tutorName, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, currentPage, "provinceExcellentProjects", CommonHelper.getRequestUrl(httpServletReuqest), currentPage.getTotalElements());
        modelMap.addAttribute("title", title);
        modelMap.addAttribute("tutorName", tutorName);
        modelMap.addAttribute("actionUrl", CommonHelper.getRequestUrl(httpServletReuqest));
        return "excellentProjects/checkProvinceExcellentProjects";
    }
}
