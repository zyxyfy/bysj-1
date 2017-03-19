package com.newview.bysj.web.print;

import com.newview.bysj.domain.CommentByGroup;
import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.domain.Student;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.reports.ScoreReportCommitments;
import com.newview.bysj.web.baseController.BaseController;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created 2016/5/5,19:35.
 * Author 张战.
 */
@Controller
public class ScoreReportController extends BaseController {

    @RequestMapping("student/viewScores.html")
    public String viewScore(HttpSession httpsession, ModelMap modelMap) {
        Student student = (Student) CommonHelper.getCurrentActor(httpsession);
        GraduateProject graduateProject = graduateProjectService.uniqueResult("student", Student.class, student);
        modelMap.put("graduateProject", graduateProject);
        return "studentLookScore/showScoreReport";
    }


    @RequestMapping("student/viewReport.html")
    public String viewReport(Integer projectId, ModelMap modelMap) {
        // 评阅人
        Integer reviewerQualityScore;
        Integer reviewerAchivementScore;
        // 答辩小组
        Double groupQualityScore;
        Double groupCompletenessScore;
        Double groupReplyScore;
        Double groupCorrectnessScore;
        // 指导教师
        Integer workLoadScore;
        Integer basicAbilityScore;
        Integer workAbilityScore;
        Integer achievementLevelScore;
        // 创建报表用数据list
        List<ScoreReportCommitments> listData = new ArrayList<ScoreReportCommitments>();
        ScoreReportCommitments scoreReportCommitment = new ScoreReportCommitments();
        // 获取当前的graduateProject和
        GraduateProject graduateProject = graduateProjectService.findById(projectId);

        if (graduateProject.getCommentByTutor() == null) {
            workLoadScore = basicAbilityScore = achievementLevelScore = workAbilityScore = 0;
        } else {
            workLoadScore = (graduateProject.getCommentByTutor()
                    .getWorkLoadScore() == null) ? 0 : graduateProject
                    .getCommentByTutor().getWorkLoadScore();
            basicAbilityScore = (graduateProject.getCommentByTutor().getBasicAblityScore()
                    == null) ? 0 : graduateProject
                    .getCommentByTutor().getBasicAblityScore();
            achievementLevelScore = (graduateProject.getCommentByTutor()
                    .getAchievementLevelScore() == null) ? 0 : graduateProject
                    .getCommentByTutor().getAchievementLevelScore();
            workAbilityScore = (graduateProject.getCommentByTutor()
                    .getWorkAblityScore() == null) ? 0 : graduateProject
                    .getCommentByTutor().getWorkAblityScore();
        }

        if (graduateProject.getCommentByReviewer() == null) {
            reviewerQualityScore = reviewerAchivementScore = 0;
        } else {
            reviewerQualityScore = (graduateProject.getCommentByReviewer()
                    .getQualityScore() == null) ? 0 : graduateProject
                    .getCommentByReviewer().getQualityScore();
            reviewerAchivementScore = (graduateProject.getCommentByReviewer()
                    .getAchievementScore() == null) ? 0 : graduateProject
                    .getCommentByReviewer().getAchievementScore();
        }

        CommentByGroup commentByGroup = graduateProject.getCommentByGroup();
        if (commentByGroup == null) {
            groupCompletenessScore = groupCorrectnessScore = groupReplyScore = groupQualityScore = 0.0;
        } else {
            groupCompletenessScore = (commentByGroup.getCompletenessScore() == null) ? 0.0
                    : commentByGroup.getCompletenessScore();
            groupCorrectnessScore = (commentByGroup.getCorrectnessSocre() == null) ? 0.0
                    : commentByGroup.getCorrectnessSocre();
            groupReplyScore = (commentByGroup.getReplyScore() == null) ? 0.0
                    : commentByGroup.getReplyScore();
            groupQualityScore = (commentByGroup.getQualityScore() == null) ? 0.0
                    : commentByGroup.getQualityScore();
        }
        // 有答辩小组评审的情况下，填充年月日
        Calendar tutorSubmittedDate = graduateProject.getCommentByTutor()
                .getSubmittedDate();
        Calendar replyGroupSubmittedDate = commentByGroup
                .getSubmittedDate();
        Calendar reviewerSubmittedDate = graduateProject
                .getCommentByReviewer().getSubmittedDate();
        if (tutorSubmittedDate != null) {
            // 设置年月日
            scoreReportCommitment.setYear(tutorSubmittedDate
                    .get(Calendar.YEAR));
            scoreReportCommitment.setMonth(tutorSubmittedDate
                    .get(Calendar.MONTH) + 1);
            scoreReportCommitment.setDay(replyGroupSubmittedDate
                    .get(Calendar.DATE));
        } else if (replyGroupSubmittedDate != null) {
            scoreReportCommitment.setYear(replyGroupSubmittedDate
                    .get(Calendar.YEAR));
            scoreReportCommitment.setMonth(replyGroupSubmittedDate
                    .get(Calendar.MONTH) + 1);
            scoreReportCommitment.setDay(replyGroupSubmittedDate
                    .get(Calendar.DATE));
        } else if (reviewerSubmittedDate != null) {
            scoreReportCommitment.setYear(replyGroupSubmittedDate
                    .get(Calendar.YEAR));
            scoreReportCommitment.setMonth(replyGroupSubmittedDate
                    .get(Calendar.MONTH) + 1);
            scoreReportCommitment.setDay(replyGroupSubmittedDate
                    .get(Calendar.DATE));
        } else {
            scoreReportCommitment.setYear(CommonHelper.getNow().get(
                    Calendar.YEAR));
            scoreReportCommitment.setMonth(CommonHelper.getNow().get(
                    Calendar.MONTH) + 1);
            scoreReportCommitment.setDay(CommonHelper.getNow().get(
                    Calendar.DATE));
        }

        if (graduateProject.getSubTitle().length() == 0) {
            scoreReportCommitment.setTitle(graduateProject.getTitle());
        } else {
            scoreReportCommitment.setTitle(graduateProject.getTitle() + "— —"
                    + graduateProject.getSubTitle());
        }
        scoreReportCommitment.setStudentClass(graduateProject.getStudent()
                .getStudentClass().getDescription());
        scoreReportCommitment.setName(graduateProject.getStudent().getName());
        scoreReportCommitment.setNumber(graduateProject.getStudent().getNo());
        scoreReportCommitment.setWorkLoadScore(workLoadScore);
        scoreReportCommitment.setBasicAbilityScore(basicAbilityScore);
        scoreReportCommitment.setAchievementLevelScore(achievementLevelScore);
        scoreReportCommitment.setWorkAbilityScore(workAbilityScore);
        scoreReportCommitment.setQualityScore(reviewerQualityScore);
        scoreReportCommitment.setAchivementScore(reviewerAchivementScore);
        scoreReportCommitment.setGroupCompletenessScore(groupCompletenessScore);
        scoreReportCommitment.setGroupCorrectnessScore(groupCorrectnessScore);
        if (groupReplyScore == null) {
            scoreReportCommitment.setGroupReplyScore(0.0);
        } else {
            scoreReportCommitment.setGroupReplyScore(groupReplyScore);
        }
        scoreReportCommitment.setGroupQualityScore(groupQualityScore);

        //如果未给出总分，则显示成绩为0.0
        if (graduateProject.getTotalScores() == null) {
            scoreReportCommitment.setSum(0.0);
        } else {
            double sum = graduateProject.getTotalScores();
            scoreReportCommitment.setSum(sum);
        }

        listData.add(scoreReportCommitment);
        JRDataSource data = new JRBeanCollectionDataSource(
                listData);
        /*Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("ScoreReportCommitments", data);
        modelAndView = new ModelAndView("ScoreReport", parameterMap);*/
        modelMap.addAttribute("url", "/WEB-INF/reports/ScoreReport.jrxml");
        modelMap.addAttribute("format", "pdf");
        modelMap.addAttribute("jrMainDataSource", data);
        return "iReportView";
    }
}
