package com.newview.bysj.web.reviewerManage;

import com.newview.bysj.domain.*;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;
import org.apache.log4j.Logger;
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
 * Created 2016/3/1,14:06.
 * Author 张战.
 */
@Controller
@RequestMapping("process")
public class ReviewerManageController extends BaseController {

    private static final Logger logger = Logger.getLogger(ReviewerManageController.class);


    /**
     * 列出当前用户所在教研室的所有课题，并可以通过此方法对课题进行检索
     *
     * @param httpSession  当前会话，用于获取当前的用户
     * @param modelMap     map集合，用于存储需要在jsp中获取的数据
     * @param pageNo       当前页
     * @param pageSize     每页的条数
     * @param reviewerName 评阅人的名字，用于通过名字进行检索的功能
     * @param point        是否指定，通过此参数来获取已指定评阅人的课题和未指定评阅人的课题
     * @param title        课题的title,用于通过title进行检索
     * @return process/reviewerList.jsp
     */
    @RequestMapping(value = "/reviewerManage.html", method = RequestMethod.GET)
    public String reviewer(HttpServletRequest httpServletRequest, HttpSession httpSession, ModelMap modelMap, Integer pageNo, Integer pageSize, String reviewerName, String point, String title) {
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentTutor(httpSession).getId());
        //获取本教研室下所有的老师
        List<Tutor> tutorList = tutor.getDepartment().getTutor();
        //移除自己,指定评阅人不能指定自己
        tutorList.remove(tutor);
        Page<GraduateProject> graduateProjectPage = graduateProjectService.getPageByTitleAndReviewer(null, reviewerName, point, tutor.getDepartment().getId(), pageNo, pageSize);
        modelMap.put("pageCount", graduateProjectPage.getContent().size());
        if (title != null) {
            modelMap.put("title", title);
        }
        if (reviewerName != null) {
            modelMap.put("reviewerName", reviewerName);
        }
        modelMap.put("tutorList", tutorList);
        modelMap.put("actionUrl", httpServletRequest.getRequestURI());
        CommonHelper.pagingHelp(modelMap, graduateProjectPage, "graduateProjectList", httpServletRequest.getRequestURI(), graduateProjectPage.getTotalElements());
        return "process/reviewerList";
    }

    /**
     * 添加或修改评阅人的方法
     *
     * @param reviewerId          指定的评阅人的id,用于获取评阅人
     * @param graduateProjectId   课题的id,用于获取课题
     * @param httpServletResponse response对象，用于给浏览器返回json数据
     */
    @RequestMapping(value = "/addOrEditReviewer.html", method = RequestMethod.POST)
    public void addOrEditReviewer(Integer reviewerId, Integer graduateProjectId, HttpServletResponse httpServletResponse) {
        Tutor tutor = tutorService.findById(reviewerId);
        GraduateProject graduateProject = graduateProjectService.findById(graduateProjectId);
        //给课题设置评阅人
        graduateProject.setReviewer(tutor);
        graduateProjectService.update(graduateProject);
        User user = tutor.getUser();
        Role role = roleService.uniqueResult("description", "评阅人");
        UserRole userRole = new UserRole();
        //设置当前用户的角色
        userRole.setRole(role);
        userRole.setUser(user);
        userRoleService.saveOrUpdate(userRole);
        //给浏览器返回json数据
        CommonHelper.buildSimpleJson(httpServletResponse);
    }

    /**
     * 根据课题的id删除课题对应的评阅人
     *
     * @param graduateProjectId   要删除评阅人的课题的id
     * @param httpServletResponse 用于给浏览器返回响应信息
     */
    @RequestMapping(value = "/delReviewerByProjectId.html", method = RequestMethod.POST)
    public void delReviewer(Integer graduateProjectId, HttpServletResponse httpServletResponse) {
        GraduateProject graduateProject = graduateProjectService.findById(graduateProjectId);
        //将课题对应的评阅人置为空
        graduateProject.setReviewer(null);
        graduateProjectService.saveOrUpdate(graduateProject);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }

  /*  public String searchGraduateProject(String title,String reviewerName){
        graduateProjectService.getPageByTitleAndReviewer(title,reviewerName,)
        return null;
    }*/
}
