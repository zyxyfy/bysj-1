package com.newview.bysj.web.provinceExcellentProject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.domain.ProvinceExcellentProject;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;

@Controller
public class AssignProvinceExcellentProject extends BaseController {
    //指定省优初界面和查询功能
    @RequestMapping("projects/saveProvenceExcellentProjects")
    public String list(HttpServletRequest httpServletRequest, HttpSession httpSession, ModelMap modelMap, Integer pageNo, Integer pageSize, String title, String tutorName) {
        Page<GraduateProject> graduateProject = graduateProjectService.getPagesForProvinceExcellentCandidate(title == null ? null : title, tutorName == null ? null : tutorName, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, graduateProject, "graduateProjets", CommonHelper.getRequestUrl(httpServletRequest), graduateProject.getTotalElements());
        modelMap.addAttribute("title", title);
        modelMap.addAttribute("tutorName", tutorName);
        modelMap.addAttribute("actionUrl", CommonHelper.getRequestUrl(httpServletRequest));
        return "excellentProjects/assginProvinceExcellentProjects";
    }

    //确定推为省优
    @RequestMapping("projects/approveProvinceExcellentProject")
    public void approveExcellentProject(Integer graduateProjectId, HttpServletResponse httpServletResponse) {
        GraduateProject graduateProject = graduateProjectService.findById(graduateProjectId);
        ProvinceExcellentProject provinceExcellentProject = new ProvinceExcellentProject();
        provinceExcellentProject.setGraudateProject(graduateProject);
        provinceExcellentProjectService.save(provinceExcellentProject);
        //重新获取保存对象，否则更新graduateProject会出错
        provinceExcellentProject = provinceExcellentProjectService.uniqueResult("graudateProject", GraduateProject.class, graduateProject);
        graduateProject.setProvinceExcellentProject(provinceExcellentProject);
        graduateProjectService.saveOrUpdate(graduateProject);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }

    //取消省优
    @RequestMapping("projects/cancelProvinceExcellentProject")
    public void cancelProvinceExcellentProject(Integer graduateProjectId, HttpServletResponse httpServletResponse) {
        GraduateProject graduateProject = graduateProjectService.findById(graduateProjectId);
        ProvinceExcellentProject provinceExcellentProject = graduateProject.getProvinceExcellentProject();
        provinceExcellentProject.setGraudateProject(null);
        graduateProject.setProvinceExcellentProject(null);
        provinceExcellentProjectService.deleteObject(provinceExcellentProject);
        graduateProjectService.saveOrUpdate(graduateProject);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }


}
