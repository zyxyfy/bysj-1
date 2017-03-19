package com.newview.bysj.web.checkAcountComplete;

import com.newview.bysj.domain.*;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 用于统计每个教研室毕业设计的完成情况
 * Created 2016/4/22,18:34.
 * Author 张战.
 */
public class DepartmentAcountCompleted {

    //private static final Logger logger = Logger.getLogger(DepartmentAcountCompleted.class);

    /**
     * 教研室
     */
    private Department department;
    /**
     * 教研室下所有的课题数目
     */
    private Integer departmentGraduateProjectCount = 0;
    /**
     * 任务书数据统计
     */
    private DataBean taskDocData = new DataBean();
    /**
     * 开题报告数据统计
     */
    private DataBean openningReportData = new DataBean();
    /**
     * 工作进程表数据统计
     */
    private DataBean schudelData = new DataBean();

    /**
     * 指导教师评审表数据统计
     */
    private DataBean tutorEvaluate = new DataBean();

    /**
     * 评阅人评审表数据统计
     */
    private DataBean reviewerEvaluateData = new DataBean();

    /**
     * 答辩小组数据统计
     */
    private DataBean replyGroupEvaluateData = new DataBean();

    public DepartmentAcountCompleted() {

    }

    /**
     * 构造函数，用来统计课题的完成情况
     *
     * @param graduateProjectList 课题集合
     * @param department          教研室
     */
    public DepartmentAcountCompleted(List<GraduateProject> graduateProjectList, Department department) {
        super();
        this.department = department;
        this.departmentGraduateProjectCount = graduateProjectList.size();
        this.taskDocData.setShouldCompleteCount(graduateProjectList.size());
        this.tutorEvaluate.setShouldCompleteCount(graduateProjectList.size());
        this.reviewerEvaluateData.setShouldCompleteCount(graduateProjectList.size());
        this.replyGroupEvaluateData.setShouldCompleteCount(graduateProjectList.size());
        //对课题进行判断
        for (GraduateProject graduateProject : graduateProjectList) {
            //任务书的判断
            TaskDoc taskDoc = graduateProject.getTaskDoc();
            //任务书不为空且已经被审核且审核已经被通过
            if (taskDoc != null && taskDoc.getAuditByBean() != null && taskDoc.getAuditByBean().getApprove() != null && taskDoc.getAuditByBean().getApprove())
                taskDocData.setAlreadyCompleteCount();
            //开题报告的判断
            if (graduateProject.getClass().equals(PaperProject.class)) {
                //如果是论文课题就+1
                PaperProject paperProject = (PaperProject) graduateProject;
                openningReportData.setShouldCompleteCount();
                //如果已经提交了开题报告且开题报告不为空且已经被审核且审核已经被通过
                if (paperProject.getOpenningReport() != null && paperProject.getOpenningReport().getAuditByDepartmentDirector() != null && paperProject.getOpenningReport().getAuditByDepartmentDirector().getApprove() != null && paperProject.getOpenningReport().getAuditByDepartmentDirector().getApprove()) {
                    openningReportData.setAlreadyCompleteCount();
                }

            }
            //工作进程表的判断
            schudelData.setShouldCompleteCount(schudelData.getShouldCompleteCount() + graduateProject.getSchedules().size());
            for (Schedule schedule : graduateProject.getSchedules()) {
                //工作进程表不为空且已经被审核且审核已经被通过
                if (schedule.getAudit() != null && schedule.getAudit().getApprove() != null && schedule.getAudit().getApprove()) {
                    schudelData.setAlreadyCompleteCount();
                }
            }
            //指导教师评审表判断
            //课题的指导老师评审不为空且已经提交
            if (graduateProject.getCommentByTutor() != null && graduateProject.getCommentByTutor().getSubmittedByTutor() != null && graduateProject.getCommentByTutor().getSubmittedByTutor())
                tutorEvaluate.setAlreadyCompleteCount();
            //评阅人评审表判断
            //课题的评阅人不为空且已经提交
            if (graduateProject.getCommentByReviewer() != null && graduateProject.getCommentByReviewer().getSubmittedByReviewer() != null && graduateProject.getCommentByReviewer().getSubmittedByReviewer())
                reviewerEvaluateData.setAlreadyCompleteCount();
            //答辩小组评审表判断
            //课题的答辩小组不为空且已经提交
            if (graduateProject.getCommentByGroup() != null && graduateProject.getCommentByGroup().getSubmittedByGroup() != null && graduateProject.getCommentByGroup().getSubmittedByGroup())
                replyGroupEvaluateData.setAlreadyCompleteCount();
        }
        this.division();
    }


    public Integer getDepartmentGraduateProjectCount() {
        return departmentGraduateProjectCount;
    }

    public void setDepartmentGraduateProjectCount(Integer departmentGraduateProjectCount) {
        this.departmentGraduateProjectCount = departmentGraduateProjectCount;
    }

    public DataBean getTaskDocData() {
        return taskDocData;
    }

    public void setTaskDocData(DataBean taskDocData) {
        this.taskDocData = taskDocData;
    }

    public DataBean getOpenningReportData() {
        return openningReportData;
    }

    public void setOpenningReportData(DataBean openningReportData) {
        this.openningReportData = openningReportData;
    }

    public DataBean getSchudelData() {
        return schudelData;
    }

    public void setSchudelData(DataBean schudelData) {
        this.schudelData = schudelData;
    }

    public DataBean getTutorEvaluate() {
        return tutorEvaluate;
    }

    public void setTutorEvaluate(DataBean tutorEvaluate) {
        this.tutorEvaluate = tutorEvaluate;
    }

    public DataBean getReviewerEvaluateData() {
        return reviewerEvaluateData;
    }

    public void setReviewerEvaluateData(DataBean reviewerEvaluateData) {
        this.reviewerEvaluateData = reviewerEvaluateData;
    }

    public DataBean getReplyGroupEvaluateData() {
        return replyGroupEvaluateData;
    }

    public void setReplyGroupEvaluateData(DataBean replyGroupEvaluateData) {
        this.replyGroupEvaluateData = replyGroupEvaluateData;
    }

    /**
     * 获取完成的百分比
     */
    public void division() {
        this.openningReportData.setCompletionRate();
        this.replyGroupEvaluateData.setCompletionRate();
        this.schudelData.setCompletionRate();
        this.taskDocData.setCompletionRate();
        this.reviewerEvaluateData.setCompletionRate();
        this.tutorEvaluate.setCompletionRate();
    }


    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
