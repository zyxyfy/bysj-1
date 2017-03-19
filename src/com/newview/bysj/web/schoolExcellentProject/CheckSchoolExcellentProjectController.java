package com.newview.bysj.web.schoolExcellentProject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.newview.bysj.domain.SchoolExcellentProject;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class CheckSchoolExcellentProjectController extends BaseController {
    //查看校优的初界面和查询功能
    @RequestMapping("projects/listSchoolExcellentProjects")
    public String list(HttpServletRequest httpServletRequest, ModelMap modelMap, String title, String tutorName, Integer pageNo, Integer pageSize) {
        Page<SchoolExcellentProject> currentPage = schoolExcellentProjectService.getPagesSchoolExcellentProjectBySchoolAmin(title == null ? "" : title, tutorName == null ? "" : tutorName, null, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, currentPage, "schoolExcellentProjects", CommonHelper.getRequestUrl(httpServletRequest), currentPage.getTotalElements());
        modelMap.addAttribute("title", title);
        modelMap.addAttribute("tutorName", tutorName);
        modelMap.addAttribute("actionUrl", CommonHelper.getRequestUrl(httpServletRequest));
        return "excellentProjects/checkSchoolExcellentProjects";
    }

    //教研室主任确定省优候选人资格
    @RequestMapping("projects/approveProvinceExcellentProjectByDirector")
    public void approveExcellentProjectByDirector(Integer schoolExcellentProjectId, HttpServletResponse httpServletResponse) {
        SchoolExcellentProject schoolExcellentProject = schoolExcellentProjectService.findById(schoolExcellentProjectId);
        schoolExcellentProject.setRecommended(true);
        schoolExcellentProjectService.saveOrUpdate(schoolExcellentProject);
        CommonHelper.buildSimpleJson(httpServletResponse);

    }

    //教研室主任取消省优候选人资格
    @RequestMapping("projects/cancelProvinceExcellentProjectByDirector")
    public void cancelExcellentProjectByDirecor(Integer schoolExcellentProjectId, HttpServletResponse httpServeltResponse) {
        SchoolExcellentProject schoolExcellentProject = schoolExcellentProjectService.findById(schoolExcellentProjectId);
        if (schoolExcellentProject.getGraduateProject().getProposerSubmitForApproval() != null) {
        } else {
            schoolExcellentProject.setRecommended(null);
            schoolExcellentProjectService.saveOrUpdate(schoolExcellentProject);
        }
        CommonHelper.buildSimpleJson(httpServeltResponse);
    }
}
