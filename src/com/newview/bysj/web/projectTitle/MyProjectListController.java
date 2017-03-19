package com.newview.bysj.web.projectTitle;

import com.newview.bysj.domain.DesignProject;
import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.domain.PaperProject;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.exception.MessageException;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;
import com.newview.bysj.web.projectCommonHelp.ProjectHelper;
import com.newview.bysj.web.projectHelper.GraduateProjectHelper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * Created 2016/2/21,10:44.
 * Author 张战.
 */

@Controller
@RequestMapping("process")
public class MyProjectListController extends BaseController {


    //private static final Logger logger = Logger.getLogger(MyProjectListController.class);

    @RequestMapping(value = "/myProjects.html", method = RequestMethod.GET)
    public String listMyProjectsGet(ModelMap modelMap, HttpSession httpSession, String title, Integer pageNo, Integer pageSize, HttpServletRequest httpServletRequest) {
        return listMyProjectsPost(modelMap, httpSession, title, pageNo, pageSize, httpServletRequest);
    }

    /**
     * 用来获取我申报的题目
     *
     * @param modelMap           需要是jsp中获取的数据
     * @param httpSession        用于获取当前的用户
     * @param title              用于检索的题目的名称
     * @param pageNo             当前页
     * @param pageSize           每页的条数
     * @param httpServletRequest 用于获取请求的路径
     * @return
     */
    @RequestMapping(value = "/myProjects.html", method = RequestMethod.POST)
    public String listMyProjectsPost(ModelMap modelMap, HttpSession httpSession, String title, Integer pageNo, Integer pageSize, HttpServletRequest httpServletRequest) {
        Tutor tutor = CommonHelper.getCurrentTutor(httpSession);
        HashMap<String, String> condition = new HashMap<>();
        if (title != null) {
            condition.put("title", title.trim());
            //用于在Jsp中显示搜索的题目的名称
            modelMap.put("title", title.trim());
        }
        Page<GraduateProject> graduateProjectPage = graduateProjectService.getPagesByProposerWithConditions(tutor, pageNo, pageSize, condition);
        CommonHelper.pagingHelp(modelMap, graduateProjectPage, "listProjects", httpServletRequest.getRequestURI(), graduateProjectPage.getTotalElements());
        modelMap.put("actionUrl", httpServletRequest.getRequestURI());
        GraduateProjectHelper.viewDesignOrPaper(modelMap, GraduateProjectHelper.VIEW_ALL);
        modelMap.put("gradateProjectNum", graduateProjectPage.getContent().size());
        //用于在jsp中根据不同的条件来设置不同的路径
        ProjectHelper.display(modelMap, 0);
        //用于设置当前时间是否在允许的修改时间之间
        ProjectHelper.setMyProjectDisplay(tutor, modelMap, constraintOfProposeProjectService);
        return "projectTitle/listAllProjects";
    }


    /**
     * 列出此用户的设计题目
     *
     * @param modelMap    用于存储需要被列出的设计题目
     * @param httpSession 当前会话,用于获取tutor
     * @param pageNo      当前页
     * @param pageSize    每页的条数
     * @return jsp页面
     */
    @RequestMapping("/listMyDesignProjects.html")
    public String listMyDesignProjectsGet(ModelMap modelMap, HttpSession httpSession, Integer pageNo, Integer pageSize) {
        Tutor tutor = CommonHelper.getCurrentTutor(httpSession);
        Page<DesignProject> designProjectPage = designProjectService.getDesignProjectByProposer(tutor, pageNo, pageSize);
        CommonHelper.paging(modelMap, designProjectPage, "listProjects");
        //用于在jsp中根据不同的条件来设置不同的路径
        ProjectHelper.display(modelMap, 0);
        //用于设置当前时间是否在允许的修改时间之间
        ProjectHelper.setMyProjectDisplay(tutor, modelMap, constraintOfProposeProjectService);
        GraduateProjectHelper.viewDesignOrPaper(modelMap, GraduateProjectHelper.VIEW_DESIGN);
        return "projectTitle/listAllProjects";
    }

    /**
     * 列出此用户的论文题目
     *
     * @param modelMap    用于存储被列出的论文题目
     * @param httpSession 当前会话
     * @param pageNo      当前页
     * @param pageSize    每页的条数
     * @return jsp页面
     */
    @RequestMapping("/listMyPaperProjects.html")
    public String listMyPaperProjectsGet(ModelMap modelMap, HttpSession httpSession, Integer pageNo, Integer pageSize) {
        Tutor tutor = CommonHelper.getCurrentTutor(httpSession);
        Page<PaperProject> paperProjectPage = paperProjectService.getPaperProjectByProposer(tutor, pageNo, pageSize);
        CommonHelper.paging(modelMap, paperProjectPage, "listProjects");//用于在jsp中根据不同的条件来设置不同的路径
        ProjectHelper.display(modelMap, 0);
        //用于设置当前时间是否在允许的修改时间之间
        ProjectHelper.setMyProjectDisplay(tutor, modelMap, constraintOfProposeProjectService);
        GraduateProjectHelper.viewDesignOrPaper(modelMap, GraduateProjectHelper.VIEW_PAPAER);
        return "projectTitle/listAllProjects";
    }


    /**
     * 查看论文的详细情况
     *
     * @param viewId   需要查看的论文的id,用于获取对应的论文
     * @param modelMap 用于存储被查看的论文，在jsp中获取
     * @return jsp页面
     */
    @RequestMapping("/viewProject.html")
    public String viewProject(Integer viewId, ModelMap modelMap) {
        GraduateProject graduateProject = graduateProjectService.findById(viewId);
        //modelMap.put("graduateProject", graduateProject);
        GraduateProjectHelper.viewProjectAddToModel(modelMap, graduateProject);
        return "projectView/viewProject";
    }

    /**
     * 删除论文
     *
     * @param delId               需要删除的论文的id
     * @param httpServletResponse 用于对浏览器返回数据
     */
    @RequestMapping(value = "/delProject.html", method = RequestMethod.POST)
    public void delProject(Integer delId, HttpServletResponse httpServletResponse, HttpSession httpSession) {
        graduateProjectService.delete(delId);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }

    @RequestMapping(value = "/editProject.html", method = RequestMethod.GET)
    public String editProject(Integer editId, HttpSession httpSession, ModelMap modelMap, HttpServletRequest httpServletRequest) {
        GraduateProject graduateProject = graduateProjectService.findById(editId);
        if (graduateProject != null) {
            graduateProjectService.toAddOrUpdateProject(httpSession, modelMap, editId);
        } else {
            throw new MessageException("获取课题失败");
        }
        modelMap.put("actionUrl", httpServletRequest.getRequestURI());
        return "editProject/editProject";
    }

    @RequestMapping(value = "/editProject.html", method = RequestMethod.POST)
    public String editProject(HttpSession httpSession, Integer year, Integer majorId, @ModelAttribute("toEditProject") GraduateProject graduateProject) {
        graduateProjectService.addOrUpdateProject(graduateProject, httpSession, year, majorId);
        return "redirect:/process/myProjects.html";
    }

    /**
     * 克隆课题
     *
     * @param cloneId     需要克隆的课题的id
     * @param httpSession 给cloneProject方法传递参数
     */
    @RequestMapping(value = "/cloneProjectById.html")
    public void cloneProjectById(Integer cloneId, HttpSession httpSession, HttpServletResponse httpServletResponse) {
        GraduateProject graduateProject = new GraduateProject();
        graduateProjectService.cloneProject(httpSession, cloneId, graduateProject);
        //return "redirect:/process/myProjects.html";
        CommonHelper.buildSimpleJson(httpServletResponse);
    }

}
