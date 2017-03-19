package com.newview.bysj.web.changeProject;

import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.exception.MessageException;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.allocateProjectStudent.QueryCondition;
import com.newview.bysj.web.baseController.BaseController;
import com.newview.bysj.web.projectCommonHelp.ProjectHelper;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 更改老师毕业设计的controller
 * Created 2016/3/1,13:31.
 * Author 张战.
 */
@Controller
@RequestMapping("process")
public class ChangProjectController extends BaseController {

    //private static final Logger logger = Logger.getLogger(ChangProjectController.class);


    /**
     * 修改教师的毕业设计
     *
     * @param httpSession    当前会话，用于获取当前用户
     * @param modelMap       map集合，用于存储需要在jsp获取的数据
     * @param pageNo         当前页
     * @param pageSize       每页的条数
     * @param queryCondition 查询的集合
     * @return jsp
     */
    @RequestMapping(value = "/changeProjectOfTutor.html", method = RequestMethod.GET)
    public String changProject(HttpSession httpSession, ModelMap modelMap, Integer pageNo, Integer pageSize, QueryCondition queryCondition, String title, HttpServletRequest httpServletRequest) {
        //获取当前tutor
        Tutor tutor = CommonHelper.getCurrentTutor(httpSession);
        //根据查询条件，获取当前用户所在教研室的所有课题
        Page<GraduateProject> graduateProjectPage = graduateProjectService.getPageByLimit(tutor, pageNo, pageSize, queryCondition.projectQuery(title));
        CommonHelper.pagingHelp(modelMap, graduateProjectPage, "listProjects", httpServletRequest.getRequestURI(), graduateProjectPage.getTotalElements());
        //用于在jsp中显示课题的数目
        modelMap.put("gradateProjectNum", graduateProjectPage.getContent().size());

        /*
        下面的语句有待优化！！
         */


        //因为我申报的题目和更改老师的毕业设计用的是同一个jsp，以下属性用来控制功能是显示
        modelMap.put("showOfTutor", "1");
        //用来区分功能
        ProjectHelper.ACTION_EDIT_PROJECT(modelMap, true);
        return "projectTitle/listAllProjects";
    }


    /**
     * 修改课题的get方法
     *
     * @param editId             要修改的课题的id
     * @param httpSession        当前会话
     * @param modelMap           map集合，存储需要在jsp中获取的数据
     * @param httpServletRequest 对浏览器的响应
     * @return editProject/editProject.jsp
     */
    @RequestMapping(value = "/updateProjectByTutor.html", method = RequestMethod.GET)
    public String updateGraduateByTutor(Integer editId, HttpSession httpSession, ModelMap modelMap, HttpServletRequest httpServletRequest) {
        //获取要修改的课题
        GraduateProject graduateProject = graduateProjectService.findById(editId);
        if (graduateProject != null) {
            graduateProjectService.toAddOrUpdateProject(httpSession, modelMap, editId);
        } else {

            /*
            建议对抛出的异常进行处理
             */
            throw new MessageException("获取课题失败");
        }
        modelMap.put("actionUrl", httpServletRequest.getRequestURI());
        return "editProject/editProject";
    }

    /**
     * 修改课题的提交方法
     *
     * @param httpSession     当前会话
     * @param year            修改的年份
     * @param majorId         为题目设置的限选专业
     * @param graduateProject spring自动绑定的表单对象
     * @return 重定向到更改都是毕业设计的界面
     */
    @RequestMapping(value = "/updateProjectByTutor.html", method = RequestMethod.POST)
    public String updateGraduateByTutor(HttpSession httpSession, Integer year, Integer majorId, @ModelAttribute("toEditProject") GraduateProject graduateProject) {
        graduateProjectService.addOrUpdateProject(graduateProject, httpSession, year, majorId);
        return "redirect:/process/changeProjectOfTutor.html";
    }

    /**
     * 查看细节
     *
     * 和查看我的题目共用了一个方法，方法的具体位置为：MyProjectListController下的viewProject方法
     */
    /*@RequestMapping(value = "/viewProject")
    public String viewProject(Integer projectId,ModelMap modelMap){
        GraduateProject graduateProject = graduateProjectService.findById(projectId);
        GraduateProjectHelper.viewProjectAddToModel(modelMap,graduateProject);
        return "projectTitle/viewProject";
    }*/

}
