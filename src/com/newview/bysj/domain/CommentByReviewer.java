package com.newview.bysj.domain;

import com.newview.bysj.helper.CommonHelper;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Calendar;

@Embeddable
@DynamicInsert(true)
@DynamicUpdate(true)
/**
 * 评阅人审评表
 */
public class CommentByReviewer {
    /**
     * 评分阶段：设计（论文）质量评分（正确性、条理性、 规范性、合理性、清晰、工作量）（0—10分）
     */
    @Column(name = "qualityScoreByReviewer")
    private Integer qualityScore;
    /**
     * 评分阶段：成果的技术水平（实用性和创新性）（0—10分）
     */
    @Column(name = "achievementScoreByReviewer")
    private Integer achievementScore;
    /**
     * 评价
     */
    @Column(name = "remarkByReviewer", length = 500)
    private String remarkByReviewer;
    /**
     * 审核日期
     */
    @Column(name = "submittedDateByReviewer")
    private Calendar submittedDate;
    /**
     * 审核
     */
    @Column(name = "submittedByReviewer")
    private Boolean submittedByReviewer;
    /**
     * 是否允许答辩
     */
    @Column(name = "qualifiedByReviewer")
    private Boolean qualifiedByReviewer;

    /**
     * 难易程度
     */
    private String reviewerEvaluationDifficulty;
    /**
     * 工作量
     */
    private String reviewerEvaluationWordload;
    /**
     * 说明书（论文）版面质量
     */
    private String reviewerEvaluationPrintingQuality;
    /**
     * 图样质量
     */
    private String reviewerEvaluationDiagramQuality;
    /**
     * 实物性能
     */
    private String reviewerEvaluationProductQuality;

    public CommentByReviewer() {
        super();
    }

    public Integer getQualityScore() {
        return qualityScore;
    }

    public void setQualityScore(Integer qualityScore) {
        this.qualityScore = qualityScore;
    }

    public Integer getAchievementScore() {
        return achievementScore;
    }

    public void setAchievementScore(Integer achievementScore) {
        this.achievementScore = achievementScore;
    }


    public Calendar getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Calendar submittedDate) {
        this.submittedDate = submittedDate;
    }


    public String getRemarkByReviewer() {
        return remarkByReviewer;
    }

    public void setRemarkByReviewer(String remarkByReviewer) {
        this.remarkByReviewer = remarkByReviewer;
    }

    public Boolean getSubmittedByReviewer() {
        return submittedByReviewer;
    }

    public void setSubmittedByReviewer(Boolean submittedByReviewer) {
        this.submittedByReviewer = submittedByReviewer;
    }

    public Boolean getQualifiedByReviewer() {
        return qualifiedByReviewer;
    }

    public void setQualifiedByReviewer(Boolean qualifiedByReviewer) {
        this.qualifiedByReviewer = qualifiedByReviewer;
    }

    public String getReviewerEvaluationDifficulty() {
        return reviewerEvaluationDifficulty;
    }

    public void setReviewerEvaluationDifficulty(String reviewerEvaluationDifficulty) {
        this.reviewerEvaluationDifficulty = reviewerEvaluationDifficulty;
    }

    public String getReviewerEvaluationWordload() {
        return reviewerEvaluationWordload;
    }

    public void setReviewerEvaluationWordload(String reviewerEvaluationWordload) {
        this.reviewerEvaluationWordload = reviewerEvaluationWordload;
    }

    public String getReviewerEvaluationPrintingQuality() {
        return reviewerEvaluationPrintingQuality;
    }

    public void setReviewerEvaluationPrintingQuality(
            String reviewerEvaluationPrintingQuality) {
        this.reviewerEvaluationPrintingQuality = reviewerEvaluationPrintingQuality;
    }

    public String getReviewerEvaluationDiagramQuality() {
        return reviewerEvaluationDiagramQuality;
    }

    public void setReviewerEvaluationDiagramQuality(
            String reviewerEvaluationDiagramQuality) {
        this.reviewerEvaluationDiagramQuality = reviewerEvaluationDiagramQuality;
    }

    public String getReviewerEvaluationProductQuality() {
        return reviewerEvaluationProductQuality;
    }

    public void setReviewerEvaluationProductQuality(
            String reviewerEvaluationProductQuality) {
        this.reviewerEvaluationProductQuality = reviewerEvaluationProductQuality;
    }

    public Integer getTotalScoreReviewer() {
        return CommonHelper.getSumInteger(this.qualityScore, this.achievementScore);
    }

}
