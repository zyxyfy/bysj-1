package com.newview.bysj.web.evaluate.replyGroup;

import com.newview.bysj.domain.*;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.other.Constants;
import com.newview.bysj.reports.ReplyGroupCommitments;
import com.newview.bysj.web.baseController.BaseController;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 答辩小组评审的controller
 * Created by zhan on 2016/3/21.
 */
@Controller
@RequestMapping("evaluate/replyGroup")
public class GroupProjectsToEvaluateController extends BaseController {


    /**
     * 答辩小组意见表的get方法
     *
     * @param title              检索的课题的名称
     * @param httpSession        当前会话
     * @param modelMap           map集合，存放需要在jsp中获取的数据
     * @param pageNo             当前页
     * @param pageSize           每页的条数
     * @param httpServletRequest 当前请求
     * @return jsp
     */
    @RequestMapping(value = "/projectsToEvaluate.html", method = RequestMethod.GET)
    public String replyEvaluateGet(String title, HttpSession httpSession, ModelMap modelMap, Integer pageNo, Integer pageSize, HttpServletRequest httpServletRequest) {
        /*
        与搜索共用同一个方法！
         */
        return this.replyEvaluatePost(httpSession, title, modelMap, pageNo, pageSize, httpServletRequest);
    }

    /**
     * 根据答辩小组和题目名称获取全部的课题
     *
     * @param httpSession        用于获取当前的用户
     * @param title              课题的名称
     * @param modelMap           用于存储需要在jsp获取的数据
     * @param pageNo             当前页
     * @param pageSize           每页的条数
     * @param httpServletRequest 用于获取浏览器请求的url
     * @return jsp
     */
    @RequestMapping(value = "/projectsToEvaluate.html", method = RequestMethod.POST)
    public String replyEvaluatePost(HttpSession httpSession, String title, ModelMap modelMap, Integer pageNo, Integer pageSize, HttpServletRequest httpServletRequest) {
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentTutor(httpSession).getId());
        //用于存储查询的条件
        HashMap<String, String> condition = new HashMap<>();
        if (title != null) {
            //（临时方案）检索的题目会多出一个逗号
            title = title.substring(0, title.length() - 1);
            condition.put("title", title);
            //用于在搜索框中显示当前搜索的题目的名称
            modelMap.put("title", title);
        }
        Page<GraduateProject> graduateProjectPage = graduateProjectService.getPagesByReplyGroupLeaderWitdConditionMap(tutor, condition, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, graduateProjectPage, "graduateProjectEvaluate", httpServletRequest.getRequestURI(), graduateProjectPage.getTotalElements());
        modelMap.put("actionURL", httpServletRequest);
        //设置显示模式，用来区分是哪一个角色的评审
        modelMap.put(Constants.EVALUATE_DISP, Constants.REPLY_ADMIN);
        return "evaluate/projectEvaluateList";
    }


    /**
     * 答辩组长评审操作的get方法
     *
     * @param httpSession        当前会话
     * @param modelMap           map集合，存储需要在jsp中获取的数据
     * @param evaluateProjectId  需要评审的课题的id
     * @param httpServletRequest 当前请求
     * @return jsp
     */
    @RequestMapping(value = "/evaluateProject.html", method = RequestMethod.GET)
    public String evaluateProject(HttpSession httpSession, ModelMap modelMap, Integer evaluateProjectId, HttpServletRequest httpServletRequest) {
        //获取对应的课题
        GraduateProject graduateProject = graduateProjectService.findById(evaluateProjectId);
        //获取该用户的模板
        Tutor tutor = CommonHelper.getCurrentTutor(httpSession);
        //创建一个用来存放评审模版的集合
        Collection<RemarkTemplate> remarkTemplateCollection = new HashSet<>();
        //判断是论文题目还是设计题目
        if (graduateProject.getClass() == PaperProject.class) {
            remarkTemplateCollection.addAll(remarkTemplateForPaperGroupService.list("department", Department.class, tutor.getDepartment()));
        }
        if (graduateProject.getClass() == DesignProject.class) {
            remarkTemplateCollection.addAll(remarkTemplateForDesignGroupService.list("department", Department.class, tutor.getDepartment()));
        }
        //评审是否通过，如果为空，则不通过
        if (graduateProject.getCommentByGroup() != null) {
            modelMap.put("isQualified", graduateProject.getCommentByGroup().getQualifiedByGroup());
        } else {
            modelMap.put("isQualified", false);
        }
        //评审模版
        modelMap.put("remarkTemplates", remarkTemplateCollection);
        //默认分数
        modelMap.put("defaultGrades", CommonHelper.getDefaultGrades());
        modelMap.put("actionURL", httpServletRequest.getRequestURI());
        //需要评审的课题
        modelMap.put("graduateProject", graduateProject);
        return "evaluate/replyGroup/EditOrAddEvaluate";
    }

    /**
     * 评审操作的提交方法
     *
     * @param httpSession         当前会话
     * @param qualified           是否通过评审
     * @param remark              对该课题的评语
     * @param graduateProject     评审的课题，spring表单绑定的对象
     * @param httpServletResponse 当前参数方法体中没有用到
     * @return jsp
     */
    @RequestMapping(value = "/evaluateProject.html", method = RequestMethod.POST)
    public String evaluateProjectPost(HttpSession httpSession, Boolean qualified, String remark, GraduateProject graduateProject, HttpServletResponse httpServletResponse) {
        //获取当前tutor
        Tutor tutor = CommonHelper.getCurrentTutor(httpSession);
        //获取指定的课题
        GraduateProject graduateProjectToEvaluate = graduateProjectService.findById(graduateProject.getId());
        //判断当前课题是否已被答辩小组评审
        if (graduateProjectToEvaluate.getCommentByGroup() == null) {
            CommentByGroup commentByGroup = new CommentByGroup();
            this.saveEvaluateByGroup(qualified, graduateProject, graduateProjectToEvaluate, commentByGroup, tutor, remark);
        } else {
            CommentByGroup commentByGroup = graduateProjectToEvaluate.getCommentByGroup();
            this.saveEvaluateByGroup(qualified, graduateProject, graduateProjectToEvaluate, commentByGroup, tutor, remark);
        }

        //重定向到一个方法
        return "redirect:projectsToEvaluate.html";
    }


    /**
     * 保存评审的方法
     *
     * @param qualified                 是否通过
     * @param graduateProject           评审之后的课题
     * @param graduateProjectToEvaluate 在数据库中需要评审的课题
     * @param commentByGroup            答辩组长的评审
     * @param tutor                     当前tutor
     * @param remark                    评语
     */
    private void saveEvaluateByGroup(Boolean qualified, GraduateProject graduateProject, GraduateProject graduateProjectToEvaluate, CommentByGroup commentByGroup, Tutor tutor, String remark) {
        commentByGroup.setLeader(tutor);
        if (graduateProjectToEvaluate.getCommentByGroup().getSubmittedDate() == null) {
            commentByGroup.setSubmittedDate(CommonHelper.getNow());
        }
        //保存相关的分数
        commentByGroup.setCompletenessScore(graduateProject.getCommentByGroup().getCompletenessScore());
        commentByGroup.setCorrectnessSocre(graduateProject.getCommentByGroup().getCorrectnessSocre());
        commentByGroup.setQualityScore(graduateProject.getCommentByGroup().getQualityScore());
        commentByGroup.setReplyScore(graduateProject.getCommentByGroup().getReplyScore());
        //保存状态
        commentByGroup.setQualifiedByGroup(qualified);
        commentByGroup.setSubmittedByGroup(true);
        commentByGroup.setRemarkByGroup(remark.replaceAll("\\s*|\t|\r|\n", ""));
        graduateProjectToEvaluate.setCommentByGroup(commentByGroup);
        //更新数据
        graduateProjectService.saveOrUpdate(graduateProjectToEvaluate);
    }

    /**
     * 生成报表的方法
     *
     * @param httpServletRequest 对浏览器的响应
     * @param projectId          课题的id
     * @param model              map集合，存储需要在jsp中获取的数据
     * @return 报表
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    /*
    此方法抛出了三个未处理的异常，建议对这些异常进行处理！！！

     */
    @RequestMapping("/getReport.html")
    public String getEvaluateReport(HttpServletRequest httpServletRequest, Integer projectId, Model model) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //创建用于存放报表数据的集合
        List<ReplyGroupCommitments> replyGroupCommitmentsList = new ArrayList<>();
        ReplyGroupCommitments replyGroupCommitments = new ReplyGroupCommitments();
        //获取课题
        GraduateProject graduateProject = graduateProjectService.findById(projectId);
        //设置相关的分数
        replyGroupCommitments.setCorrectnessScore(graduateProject.getCommentByGroup().getCorrectnessSocre());
        replyGroupCommitments.setCompletenessScore(graduateProject.getCommentByGroup().getCompletenessScore());
        replyGroupCommitments.setQualityScoreOfCommentByGroup(graduateProject.getCommentByGroup().getQualityScore());
        replyGroupCommitments.setReplyScore(graduateProject.getCommentByGroup().getReplyScore());
        //获取日期
        Calendar calendar = graduateProject.getCommentByGroup().getSubmittedDate();
        //获取审核的日期
        replyGroupCommitments.setYear(CommonHelper.getYearByCalendar(calendar));
        replyGroupCommitments.setMonth(CommonHelper.getMonthByCalendar(calendar));
        replyGroupCommitments.setDay(CommonHelper.getDayOfMontyByCalendar(calendar));
        replyGroupCommitments.setRemarkCommentByGroup(graduateProject.getCommentByGroup().getRemarkByGroup());
        //如果没有副标题，则只显示标题
        if (graduateProject.getSubTitle().length() == 0) {
            replyGroupCommitments.setTitle(graduateProject.getTitle());
        } else {
            replyGroupCommitments.setTitle(graduateProject.getTitle() + "----" + graduateProject.getSubTitle());
        }
        replyGroupCommitments.setStudentClass(graduateProject.getStudent().getStudentClass().getDescription());
        //如果对应的课题学生没有姓名，则显示无名氏
        if (graduateProject.getStudent().getName() == null) {
            replyGroupCommitments.setName("无名氏");
        } else {
            replyGroupCommitments.setName(graduateProject.getStudent().getName());
        }
        //学生的学号
        replyGroupCommitments.setNumber(graduateProject.getStudent().getNo());

        Tutor[] tutors = new Tutor[6];
        int index = 0;
        //获取该课题所在答辩小组的
        List<Tutor> tutorAsMemberInReplyGroup = graduateProject.getReplyGroup().getMembers();
        for (Tutor tutor : tutorAsMemberInReplyGroup) {
            tutors[index] = tutor;
            index++;
        }
        String basePath = CommonHelper.getRootPath(httpServletRequest.getSession());
        String openPng = basePath + "/images/signature/open.png";


        //因为老师签名的方法共有6个，每个方法的方法名只有最后一个数字不同，所以考虑循环的方式来调用方法
        //小组成员的签字文件，答辩小组成员的个数最多为6个
        for (int i = 0; i < 6; i++) {
            int num = i + 1;
            //获取replyGroupCommitments对应的字节码文件
            Class clazz = replyGroupCommitments.getClass();
            if (tutors[i] != null) {
                //如果答辩成员的签名照不为空，则显示签名的照片
                if (tutors[i].getSignPictureURL() != null) {
                    //通过反射来获取某个方法
                    Method setSignature = clazz.getMethod("setSignature_" + num, String.class);
                    //通过反射来调用方法
                    setSignature.invoke(replyGroupCommitments, CommonHelper.getUploadPath(httpServletRequest.getSession()) + tutors[i].getSignPictureURL());
                    Method setSignature_txt = clazz.getMethod("setSignature_txt_" + num, String.class);
                    setSignature_txt.invoke(replyGroupCommitments, " ");
                } else {
                    Method setSignatre = clazz.getMethod("setSignature_" + num, String.class);
                    setSignatre.invoke(replyGroupCommitments, openPng);
                    Method setSignatrue_txt = clazz.getMethod("setSignature_txt_" + num, String.class);
                    setSignatrue_txt.invoke(replyGroupCommitments, tutors[i].getName());
                }

            }
            //如果没有答辩成员，则设置为空的字符串
            else {
                Method setSignature = clazz.getMethod("setSignature_" + num, String.class);
                setSignature.invoke(replyGroupCommitments, openPng);
                Method setSignature_txt = clazz.getMethod("setSignature_txt_" + num, String.class);
                setSignature_txt.invoke(replyGroupCommitments, " ");
            }
        }
        replyGroupCommitmentsList.add(replyGroupCommitments);
        //创建要生成报表的数据源
        JRDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(replyGroupCommitmentsList);
        model.addAttribute("url", "/WEB-INF/reports/ReplyGroupReport.jrxml");
        //设置格式为pdf
        model.addAttribute("format", "pdf");
        //将要打印的数据添加到map中
        model.addAttribute("jrMainDataSource", jrBeanCollectionDataSource);
        return "iReportView";
    }


}
