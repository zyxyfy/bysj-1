package com.newview.bysj.web.projectCommonHelp;

import com.newview.bysj.domain.DesignProject;
import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.domain.PaperProject;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.service.ConstraintOfProposeProjectService;
import com.newview.bysj.service.DesignProjectService;
import com.newview.bysj.service.GraduateProjectService;
import com.newview.bysj.service.PaperProjectService;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;

/**
 * 本类是project的帮助类，主要是抽取出一些共同的逻辑
 * Created 2016/2/23,15:37.
 * Author 张战.
 */

public class ProjectHelper {

    private static final String SHOW_NAME = "ifShowAll";
    private static final String ABLE_TO_UPDATE = "ABLE_TO_UPDATE";
    private static final Boolean ACTION_EDIT_PROJECT = false;

    /**
     * 将需要显示的元素的名称添加到model中
     *
     * @param modelMap map集合
     * @param showAll  列出所有的则为1，只列出自己的则为0
     */
    public static void display(ModelMap modelMap, Integer showAll) {
        modelMap.put(SHOW_NAME, showAll);
    }

    /**
     * 因为我申报的题目和修改老师的毕业设计共用的是一个jsp，所以以这个静态变量来区分是哪一个功能
     *
     * @param modelMap
     * @param ifEdit
     */
    public static void ACTION_EDIT_PROJECT(ModelMap modelMap, Boolean ifEdit) {
        modelMap.put("ACTION_EDIT_PROJECT", ifEdit);
    }

    /**
     * 用来设置维护题目的时间是否在允许的时间之间
     *
     * @param tutor                             用来传给service层，获取对应的教研室
     * @param modelMap                          添加在modelMap集合中，在jsp中获取
     * @param constraintOfProposeProjectService 用来获取当前时间是否在允许的时间之间
     */
    public static void setMyProjectDisplay(Tutor tutor, ModelMap modelMap, ConstraintOfProposeProjectService constraintOfProposeProjectService) {
        //在允许的时间之间则为true，否则为false
        boolean ableToUpdate = constraintOfProposeProjectService.isAbleToUpdateProject(tutor);
        //在jsp通过1和0来代表true和false
        if (ableToUpdate)
            modelMap.put(ABLE_TO_UPDATE, 1);
        else
            modelMap.put(ABLE_TO_UPDATE, 0);
    }

    //获取当前教研室所有课题的数量
    public static void addAllProjectNumToModel(ModelMap modelMap, GraduateProjectService graduateProjectService) {
        modelMap.put("projectCount", graduateProjectService.countAll(GraduateProject.class));
    }

    //获取当前教研室所有设计题目的数量
    public static void addAllDesignNumToModel(ModelMap modelMap, DesignProjectService designProjectService) {
        modelMap.put("designCount", designProjectService.countAll(DesignProject.class));
    }

    //获取当前教研室所有论文题目的数量
    public static void addAllPaperNumToModel(ModelMap modelMap, PaperProjectService paperProjectService) {
        modelMap.put("paperCount", paperProjectService.countAll(PaperProject.class));
    }

    //获取当前页的所有题目的条数
    public static void addProjectPageNumToModel(ModelMap modelMap, Page<GraduateProject> graduateProjectPage) {
        modelMap.put("graduateProjectNum", graduateProjectPage.getContent().size());
    }

    //获取当前页的设计题目的条数
    public static void addDesignPageNumToModel(ModelMap modelMap, Page<DesignProject> designProjectPage) {
        modelMap.put("graduateProjectNum", designProjectPage.getContent().size());
    }

    //获取当前页的论文题目的条数
    public static void addPaperPageNumToModel(ModelMap modelMap, Page<PaperProject> paperProjectPage) {
        modelMap.put("graduateProjectNum", paperProjectPage.getContent().size());
    }
}
