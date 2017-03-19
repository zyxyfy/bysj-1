package com.newview.bysj.web.schoolExcellentProject;

import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.other.Common;
import com.newview.bysj.web.baseController.BaseController;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by zhan on 2016/4/30.
 */
@Controller
@RequestMapping("director")
public class ScheduleGraduateProjectList extends BaseController {

    /**
     * 获取毕业论文明细表的方法
     *
     * @param modelMap           存放需要在jsp中获取的集合
     * @param httpSession        用于获取当前的用户
     * @param pageNo             当前页
     * @param pageSize           每页的条数
     * @param httpServletRequest 用于分页时获取当前的请求路径
     * @return jsp
     */
    @RequestMapping("/scheduleOfGraduateProject.html")
    public String graduateProjectList(ModelMap modelMap, HttpSession httpSession, Integer pageNo, Integer pageSize, HttpServletRequest httpServletRequest, String studentName, String studentNo) {
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentTutor(httpSession).getId());
        Page<GraduateProject> graduateProjectPage = graduateProjectService.getPageByDepartmentWithStudent(tutor, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, graduateProjectPage, "graduateProjectList", httpServletRequest.getRequestURI(), graduateProjectPage.getTotalElements());
        modelMap.put("actionUrl", httpServletRequest.getRequestURI());
        return "excellentProjects/scheduleGraduateProjectList";
    }

    /**
     * 用于推荐为校优课题的方法
     *
     * @param projectId           要推荐的校优课题的id
     * @param httpServletResponse 用于给浏览器返回json数据
     */
    @RequestMapping(value = "/passProject.html", method = RequestMethod.POST)
    public void passGraduateProject(Integer projectId, HttpServletResponse httpServletResponse) {
        //获取需要推荐为校优的课题
        GraduateProject schoolExcellentProject = graduateProjectService.findById(projectId);
        schoolExcellentProject.setRecommended(true);
        graduateProjectService.saveOrUpdate(schoolExcellentProject);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }

    /**
     * 用于驳回校优课题的方法
     *
     * @param projectId           要驳回校优课题的id
     * @param httpServletResponse 用于给浏览器返回json数据
     */
    @RequestMapping(value = "/backProject.html", method = RequestMethod.POST)
    public void backGraduateProject(Integer projectId, HttpServletResponse httpServletResponse) {
        //获取需要驳回的课题
        GraduateProject backExcellentSchoolProject = graduateProjectService.findById(projectId);
        //取消关联的属性
        backExcellentSchoolProject.setRecommended(null);
        graduateProjectService.saveOrUpdate(backExcellentSchoolProject);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }

    //导出excel的方法
    @RequestMapping("/exportScheduleProject.html")
    public String exportExcel(HttpSession httpSession, ModelMap modelMap) {
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentTutor(httpSession).getId());
        //获取需要导出的课题
        List<GraduateProject> graduateProjectList = graduateProjectService.getProjectListByDepartmentWithStudent(tutor);
        modelMap.put("department", tutor.getDepartment());
        modelMap.put("projectList", graduateProjectList);
        return "projectListExcel";
    }


}
