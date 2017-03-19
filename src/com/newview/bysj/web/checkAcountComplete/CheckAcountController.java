package com.newview.bysj.web.checkAcountComplete;

import com.newview.bysj.domain.*;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 数据统计的controller
 * Created 2016/4/22,22:31.
 * Author 张战.
 */
@Controller
@RequestMapping("dataAccount")
public class CheckAcountController extends BaseController {


    //private static final Logger logger = Logger.getLogger(CheckAcountController.class);

    @RequestMapping("/checkDataAccount.html")
    public String redirectRole() {
        return "checkAccountData/redirectByRole";
    }


    /**
     * 查看数据统计，最高角色是院级管理员
     *
     * @param modelMap    map集合，存储需要在jsp中获取的数据
     * @param httpSession 当前会话
     * @return checkAccountData/listAccountData.jsp
     */
    @RequestMapping("/checkDataAccountByDean.html")
    public String getDataAccountByDean(ModelMap modelMap, HttpSession httpSession) {
        //得到当前tutor
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentTutor(httpSession).getId());
        //获取当前老师所在学院的所有教研室
        List<Department> departmentList = departmentService.getDepartmentBySchoolNoPage(tutor.getDepartment().getSchool());
        //存放每个教研室毕业设计的完成情况
        List<DepartmentAcountCompleted> departmentAcountCompletedList = new ArrayList<>();
        //对所有的教研室进行遍历
        for (Department department : departmentList) {
            //获取该教研室下的所有课题
            List<GraduateProject> graduateProjectList = graduateProjectService.getPageByDepartment(department);
            //获取该教研室毕业设计的完成情况
            DepartmentAcountCompleted departmentAcountCompleted = new DepartmentAcountCompleted(graduateProjectList, department);
            //添加到集合中
            departmentAcountCompletedList.add(departmentAcountCompleted);
        }
        modelMap.put("departmentDataList", departmentAcountCompletedList);
        return "checkAccountData/listAccountData";
    }


    /**
     * 查看数据统计，最高角色是教研室主任
     *
     * @param modelMap    map集合，存储需要在jsp中获取的数据
     * @param httpSession 当前会话
     * @return checkAccountData/listAccountData.jsp
     */
    @RequestMapping("/checkDataAccountByDepartment.html")
    public String getDataAccountByDepartment(ModelMap modelMap, HttpSession httpSession) {
        //获取当前tutor
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentTutor(httpSession).getId());
        //获取当前老师所在教研室的所有课题
        List<GraduateProject> graduateProjectList = graduateProjectService.getPageByDepartment(tutor.getDepartment());
        //统计毕业设计的完成情况
        DepartmentAcountCompleted departmentAcountCompleted = new DepartmentAcountCompleted(graduateProjectList, tutor.getDepartment());
        List<DepartmentAcountCompleted> departmentAcountCompletedList = new ArrayList<>();
        departmentAcountCompletedList.add(departmentAcountCompleted);
        modelMap.put("departmentDataList", departmentAcountCompletedList);
        return "checkAccountData/listAccountData";
    }


    /**
     * 未完成的任务书
     *
     * @param departmentId 需要查看未完成的任务书所在教研室的id
     * @param modelMap     Map集合，存储需要在jsp中获取的数据
     * @return checkAccountData/notCompletedList.jsp
     */
    @RequestMapping("/notCompletedTaskDoc.html")
    public String getNotCompletedTaskDoc(Integer departmentId, ModelMap modelMap) {
        Department department = departmentService.findById(departmentId);
        //用于存放未完成的课题
        Set<GraduateProject> graduateProjectSet = new HashSet<>();



        /*

        建议对当教研室为空时，增加相应的处理

         */
        if (department != null) {
            //获取当前教研室所有的课题
            List<GraduateProject> graduateProjectList = graduateProjectService.getPageByDepartment(department);
            for (GraduateProject graduateProject : graduateProjectList) {
                if (graduateProject.getStudent() != null) {
                    TaskDoc taskDoc = graduateProject.getTaskDoc();
                    //任务书未完成的条件：没有上传任务书，任务书没有被院长审核，任务书没有被院长审核通过
                    if (taskDoc == null || taskDoc.getAuditByBean() == null || taskDoc.getAuditByBean().getApprove() == null || !taskDoc.getAuditByBean().getApprove())
                        graduateProjectSet.add(graduateProject);
                }
            }
            modelMap.put("description", "未完成的任务书");
            modelMap.put("graduateProjectList", graduateProjectSet);
        }

        return "checkAccountData/notCompletedList";
    }


    /**
     * 未完成的开题报告
     *
     * @param departmentId 需要查看的开题报告所在教研室的id
     * @param modelMap     map集合，存储需要在jsp中获取的数据
     * @return checkAccountData/notCompletedList.jsp
     */
    @RequestMapping("/notCompletedOpenningReport.html")
    public String getNotCompletedOpenningReport(Integer departmentId, ModelMap modelMap) {
        Department department = departmentService.findById(departmentId);
        //用于存放未完成的开题报告的课题
        Set<GraduateProject> graduateProjects = new HashSet<>();
        if (department != null) {
            //获取该教研室下的所有课题
            List<GraduateProject> graduateProjectList = graduateProjectService.getPageByDepartment(department);
            //遍历所有的课题，找出未完成开题报告的课题
            for (GraduateProject graduateProject : graduateProjectList) {
                //判断当前课题是否是论文课题
                if (graduateProject.getClass().equals(PaperProject.class)) {
                    PaperProject paperProject = (PaperProject) graduateProject;
                    //获取该课题对应的开题报告
                    OpenningReport openningReport = paperProject.getOpenningReport();
                    //如果开题报告为空，或是未审核或是审核未通过，都视为未完成
                    if (openningReport == null || openningReport.getAuditByDepartmentDirector() == null || openningReport.getAuditByDepartmentDirector().getApprove() == null || !openningReport.getAuditByDepartmentDirector().getApprove())
                        graduateProjects.add(paperProject);
                }
            }
            modelMap.put("graduateProjectList", graduateProjects);
            modelMap.put("description", "未完成的开题报告");
        }

        return "checkAccountData/notCompletedList";
    }


    /**
     * 未完成的工作进程表
     *
     * @param departmentId 需要查看的工作进程表所在教研室的id
     * @param modelMap     map集合，用于存放需要在jsp中获取的数据
     * @return jsp
     */
    /*
    此方法的逻辑和上面的方法相似，此处不再详细写
     */
    @RequestMapping("/notCompletedSchedule.html")
    public String notCompletedScheudle(Integer departmentId, ModelMap modelMap) {
        Department department = departmentService.findById(departmentId);
        Set<GraduateProject> graduateProjectSet = new HashSet<>();
        if (department != null) {
            List<GraduateProject> graduateProjectList = graduateProjectService.getPageByDepartment(department);
            for (GraduateProject graduateProject : graduateProjectList) {
                List<Schedule> scheduleList = graduateProject.getSchedules();
                for (Schedule schedule : scheduleList) {
                    if (schedule.getAudit() == null || schedule.getAudit().getApprove() == null || !schedule.getAudit().getApprove()) {
                        graduateProjectSet.add(graduateProject);
                    }
                }
            }
            modelMap.put("graduateProjectList", graduateProjectSet);
            modelMap.put("description", "未完成的工作进程表");
        }

        return "checkAccountData/notCompletedList";
    }


    /**
     * 未完成的指导老师评审表
     *
     * @param departmentId 需要查看评审表所在教研室的id
     * @param modelMap     map集合，存储需要在jsp中获取的数据
     * @return jsp
     */
     /*
    此方法的逻辑和上面的方法相似，此处不再详细写
     */
    @RequestMapping("/notCompletedTutorEvaluate.html")
    public String notCompletedTutorEvaluate(Integer departmentId, ModelMap modelMap) {
        Department department = departmentService.findById(departmentId);
        Set<GraduateProject> graduateProjectSet = new HashSet<>();
        if (department != null) {
            List<GraduateProject> graduateProjectList = graduateProjectService.getPageByDepartment(department);
            for (GraduateProject graduateProject : graduateProjectList) {
                CommentByTutor commentByTutor = graduateProject.getCommentByTutor();
                if (commentByTutor == null || commentByTutor.getSubmittedByTutor() == null || !commentByTutor.getSubmittedByTutor())
                    graduateProjectSet.add(graduateProject);
            }
            modelMap.put("graduateProjectList", graduateProjectList);
            modelMap.put("description", "未完成的指导老师评审表");
        }
        return "checkAccountData/notCompletedList";
    }


    /**
     * 未完成的评阅人评审表
     *
     * @param departmentId 需要查看评审表所在教研室的id
     * @param modelMap     map集合，存储需要在jsp中获取的数据
     * @return jsp
     */
    /*
    此方法的逻辑和上面的方法相似，此处不再详细写
     */
    @RequestMapping("/notCompletedReviewerEvaluate.html")
    public String notCompletedReviewerEvaluate(Integer departmentId, ModelMap modelMap) {
        Department department = departmentService.findById(departmentId);
        Set<GraduateProject> graduateProjectSet = new HashSet<>();
        if (department != null) {
            List<GraduateProject> graduateProjectList = graduateProjectService.getPageByDepartment(department);
            for (GraduateProject graduateProject : graduateProjectList) {
                CommentByReviewer commentByReviewer = graduateProject.getCommentByReviewer();
                if (commentByReviewer == null || commentByReviewer.getSubmittedByReviewer() == null || !commentByReviewer.getSubmittedByReviewer())
                    graduateProjectSet.add(graduateProject);
            }
            modelMap.put("graduateProjectList", graduateProjectSet);
            modelMap.put("description", "未完成的评阅人评审表");
        }
        return "checkAccountData/notCompletedList";
    }


    /**
     * 未完成的答辩小组意见表
     *
     * @param departmentId 需要查看的意见表所在教研室的id
     * @param modelMap     map集合，用于存储需要在jsp中获取的数据
     * @return jsp
     */
     /*
    此方法的逻辑和上面的方法相似，此处不再详细写
     */
    @RequestMapping("/notCompletedGroupEvaluate.html")
    public String notCompletedGroupEvaluate(Integer departmentId, ModelMap modelMap) {
        Department department = departmentService.findById(departmentId);
        Set<GraduateProject> graduateProjectSet = new HashSet<>();
        if (department != null) {
            List<GraduateProject> graduateProjectList = graduateProjectService.getPageByDepartment(department);
            for (GraduateProject graduateProject : graduateProjectList) {
                CommentByGroup commentByGroup = graduateProject.getCommentByGroup();
                if (commentByGroup == null || commentByGroup.getSubmittedByGroup() == null || !commentByGroup.getSubmittedByGroup())
                    graduateProjectSet.add(graduateProject);
            }
            modelMap.put("graduateProjectList", graduateProjectSet);
            modelMap.put("description", "未完成的答辩小组意见表");
        }
        return "checkAccountData/notCompletedList";
    }


}
