package com.newview.bysj.domain;

import com.newview.bysj.helper.CommonHelper;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Calendar;

/**
 * 指导教师审评表
 */
@Embeddable
@DynamicInsert(true)
@DynamicUpdate(true)
public class CommentByTutor {
    /**
     * 基本理论、基本知识、基本技能和外语水平
     */
    @Column(name = "basicAblityScoreByTutor")
    private Integer basicAblityScore;
    /**
     * 工作量、工作态度
     */
    @Column(name = "workLoadScoreByTutor")
    private Integer workLoadScore;
    /**
     * 独立工作能力、分析与解决问题的能力
     */
    @Column(name = "workAblityScoreByTutor")
    private Integer workAblityScore;
    /**
     * 完成任务情况及水平
     */
    @Column(name = "achievementLevelScoreByTutor")
    private Integer achievementLevelScore;
    /**
     * 提交审评表
     */
    @Column(name = "submittedByTutor")
    private Boolean submittedByTutor;
    /**
     * 审核日期
     */
    @Column(name = "submittedDateByTutor")
    private Calendar submittedDate;
    /**
     * 是否允许答辩
     */
    @Column(name = "qualifiedByTutor")
    private Boolean qualifiedByTutor;
    /**
     * 评语
     */
    @Column(name = "remarkByTutor")
    private String remark;

    //累计旷课时间
    private Integer tutorEvaluteAttendance;

    public void setTutorEvaluteAttendance(Integer tutorEvaluteAttendance) {
        this.tutorEvaluteAttendance = tutorEvaluteAttendance;
    }

    public Integer getTutorEvaluteAttendance() {
        return tutorEvaluteAttendance;
    }


    //软、硬完成情况
    private Boolean tutorEvaluteHasSoftHardWare;

    public void setTutorEvaluteHasSoftHardWare(Boolean tutorEvaluteHasSoftHardWare) {
        this.tutorEvaluteHasSoftHardWare = tutorEvaluteHasSoftHardWare;
    }

    public Boolean getTutorEvaluteHasSoftHardWare() {
        return this.tutorEvaluteHasSoftHardWare;
    }

    /**
     * 任务完成情况：是否完成
     */
    private Boolean tutorEvaluteHasCompleteProject;
    /**
     * 论文字数
     */
    private Integer tutorEvaluteDissertationWordCount;
    /**
     * 有无中期检查报告
     */
    private Boolean tutorEvaluteHasMiddleExam;
    /**
     * 有无外文资料翻译
     */
    private Boolean tutorEvaluteHasTranslationMaterail;
    /**
     * 外语资料翻译的字数
     */
    private Integer tutorEvaluteHasTranslationWordCount;
    /**
     * 有无中、英摘要
     */
    private Boolean tutorEvaluteHasTwoAbstract;
    /**
     * 指导教师评审表的评审表，有可能是合作导师
     */
    @ManyToOne
    @JoinColumn(name = "tutorCommenter_id")
    private Tutor tutorCommenter;

    public CommentByTutor() {
        super();
    }

    public Integer getBasicAblityScore() {
        return basicAblityScore;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setBasicAblityScore(Integer basicAblityScore) {
        this.basicAblityScore = basicAblityScore;
    }

    public Integer getWorkLoadScore() {
        return workLoadScore;
    }

    public void setWorkLoadScore(Integer workLoadScore) {
        this.workLoadScore = workLoadScore;
    }

    public Integer getWorkAblityScore() {
        return workAblityScore;
    }

    public void setWorkAblityScore(Integer workAblityScore) {
        this.workAblityScore = workAblityScore;
    }

    public Integer getAchievementLevelScore() {
        return achievementLevelScore;
    }

    public void setAchievementLevelScore(Integer achievementLevelScore) {
        this.achievementLevelScore = achievementLevelScore;
    }

    public Boolean getSubmittedByTutor() {
        return submittedByTutor;
    }

    public void setSubmittedByTutor(Boolean submittedByTutor) {
        this.submittedByTutor = submittedByTutor;
    }

    public Calendar getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Calendar submittedDate) {
        this.submittedDate = submittedDate;
    }


    public Boolean getQualifiedByTutor() {
        return qualifiedByTutor;
    }

    public void setQualifiedByTutor(Boolean qualifiedByTutor) {
        this.qualifiedByTutor = qualifiedByTutor;
    }

    public Boolean getTutorEvaluteHasCompleteProject() {
        return tutorEvaluteHasCompleteProject;
    }

    public void setTutorEvaluteHasCompleteProject(
            Boolean tutorEvaluteHasCompleteProject) {
        this.tutorEvaluteHasCompleteProject = tutorEvaluteHasCompleteProject;
    }

    public Integer getTutorEvaluteDissertationWordCount() {
        return tutorEvaluteDissertationWordCount;
    }

    public void setTutorEvaluteDissertationWordCount(
            Integer tutorEvaluteDissertationWordCount) {
        this.tutorEvaluteDissertationWordCount = tutorEvaluteDissertationWordCount;
    }

    public Boolean getTutorEvaluteHasMiddleExam() {
        return tutorEvaluteHasMiddleExam;
    }

    public void setTutorEvaluteHasMiddleExam(Boolean tutorEvaluteHasMiddleExam) {
        this.tutorEvaluteHasMiddleExam = tutorEvaluteHasMiddleExam;
    }

    public Boolean getTutorEvaluteHasTranslationMaterail() {
        return tutorEvaluteHasTranslationMaterail;
    }

    public void setTutorEvaluteHasTranslationMaterail(
            Boolean tutorEvaluteHasTranslationMaterail) {
        this.tutorEvaluteHasTranslationMaterail = tutorEvaluteHasTranslationMaterail;
    }

    public Integer getTutorEvaluteHasTranslationWordCount() {
        return tutorEvaluteHasTranslationWordCount;
    }

    public void setTutorEvaluteHasTranslationWordCount(
            Integer tutorEvaluteHasTranslationWordCount) {
        this.tutorEvaluteHasTranslationWordCount = tutorEvaluteHasTranslationWordCount;
    }

    public Boolean getTutorEvaluteHasTwoAbstract() {
        return tutorEvaluteHasTwoAbstract;
    }

    public void setTutorEvaluteHasTwoAbstract(Boolean tutorEvaluteHasTwoAbstract) {
        this.tutorEvaluteHasTwoAbstract = tutorEvaluteHasTwoAbstract;
    }

    public Tutor getTutorCommenter() {
        return tutorCommenter;
    }

    public void setTutorCommenter(Tutor tutorCommenter) {
        this.tutorCommenter = tutorCommenter;
    }


    public Integer getTotleScoreTutor() {
        return CommonHelper.getSumInteger(this.basicAblityScore, this.workLoadScore, this.workAblityScore, this.achievementLevelScore);
    }

}
