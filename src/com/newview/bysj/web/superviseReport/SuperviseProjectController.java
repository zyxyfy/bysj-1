package com.newview.bysj.web.superviseReport;

import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by zhan on 2016/4/9.
 */
@Controller
@RequestMapping("supervisor/school")
public class SuperviseProjectController extends BaseController {

    //根据当前用户的角色定位到不同的方法
    @RequestMapping("/projectsToSupervisor.html")
    public String allProjectToSupervise(ModelMap modelMap, Integer pageNo, Integer pageSize) {
        modelMap.put("pageNo", pageNo);
        modelMap.put("pageSize", pageSize);
        return "supervise/redirectGraduateProject";
    }

    /**
     * 最高角色是校级管理员
     *
     * @param modelMap           用于存放需要在jsp中获取的数据
     * @param pageNo             当前页
     * @param pageSize           每页的条数
     * @param httpServletRequest 用于获取当前请求的url
     * @param title              搜索的题目名称
     * @return jsp
     */
    @RequestMapping("/projectsToSupervisorByCollege.html")
    public String projectToSuperviseByCollege(ModelMap modelMap, Integer pageNo, Integer pageSize, HttpServletRequest httpServletRequest, String title) {
        // Tutor tutor = tutorService.findById(CommonHelper.getCurrentTutor(httpSession).getId());
        Page<GraduateProject> graduateProjectPage = graduateProjectService.getpagesByTitle(title, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, graduateProjectPage, "graduateProjectList", httpServletRequest.getRequestURI(), graduateProjectPage.getTotalElements());
        modelMap.put("actionURL", httpServletRequest.getRequestURI());
        if (title != null) {
            modelMap.put("title", title);
        }
        return "supervise/projectsToSupervise";
    }

    /**
     * 最高角色是院级管理员，获取当前用户所在学院的所有已被学生选择的课题
     *
     * @param title              要检索的题目的名称
     * @param modelMap           用于存放需要在jsp中获取的数据
     * @param pageNo             当前页
     * @param pageSize           每页的条数
     * @param httpServletRequest 用于获取当前请求的url
     * @param httpSession        用于获取当前的用户
     * @return jsp
     */
    @RequestMapping("/projectsToSupervisorBySchool.html")
    public String projectToSuperviseBySchool(String title, ModelMap modelMap, Integer pageNo, Integer pageSize, HttpServletRequest httpServletRequest, HttpSession httpSession) {
        //获取当前的tutor
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentTutor(httpSession).getId());
        //获取学院下已被学生选择的课题
        Page<GraduateProject> graduateProjectPage = graduateProjectService.getPagesBySchoolAndTitle(tutor.getDepartment().getSchool(), title, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, graduateProjectPage, "graduateProjectList", httpServletRequest.getRequestURI(), graduateProjectPage.getTotalElements());
        modelMap.put("actionURL", httpServletRequest.getRequestURI());
        //如果检索的题目不为空，则添加到model中，以便于在jsp的搜索框中显示所检索的题目
        if (title != null) {
            modelMap.put("title", title);
        }
        return "supervise/projectsToSupervise";
    }


}
