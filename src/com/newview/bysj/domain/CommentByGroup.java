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
 * 答辩小组评审表
 */
@Embeddable
@DynamicInsert(true)
@DynamicUpdate(true)
public class CommentByGroup {
    /**
     * 论文与实物的质量 评分（0-10分）
     */
    @Column(name = "qualityScoreByGroup", columnDefinition = "double(5,2) default '0.00'")
    private Double qualityScore;
    /**
     * 完成任务书规定的要求与水平评分（0-10分）
     */
    @Column(name = "completenessScoreByGroup", columnDefinition = "double(5,2) default '0.00'")
    private Double completenessScore;
    /**
     * 论文内容的答辩陈述评分（0-10分）
     */
    @Column(name = "replyScoreByGroup", columnDefinition = "double(5,2) default '0.00'")
    private Double replyScore;
    /**
     * 回答问题的正确性评分（0-10分）
     */
    @Column(name = "correctnessScore", columnDefinition = "double(5,2) default'0.00'")
    private Double correctnessSocre;
    /**
     * 评价
     */
    @Column(name = "remarkByGroup", length = 500)
    private String remarkByGroup;
    /**
     * 提交
     */
    @Column(name = "submittedByGroup")
    private Boolean submittedByGroup;
    /**
     * 提交日期
     */
    @Column(name = "submittedDateByGroup")
    private Calendar submittedDate;
    /**
     * 是否通过答辩
     */
    @Column(name = "qualifiedByGroup")
    private Boolean qualifiedByGroup;
    /**
     * 答辩小组组长
     * 多对一
     */
    @ManyToOne
    @JoinColumn(name = "groupLeader_id")
    private Tutor leader;

    public CommentByGroup() {
        super();
    }

    public Double getQualityScore() {
        return qualityScore;
    }

    public void setQualityScore(Double qualityScore) {
        this.qualityScore = qualityScore;
    }

    public Double getCompletenessScore() {
        return completenessScore;
    }

    public void setCompletenessScore(Double completenessScore) {
        this.completenessScore = completenessScore;
    }

    public Double getReplyScore() {
        return replyScore;
    }

    public void setReplyScore(Double replyScore) {
        this.replyScore = replyScore;
    }

    public Double getCorrectnessSocre() {
        return correctnessSocre;
    }

    public void setCorrectnessSocre(Double correctnessSocre) {
        this.correctnessSocre = correctnessSocre;
    }


    public String getRemarkByGroup() {
        return remarkByGroup;
    }

    public void setRemarkByGroup(String remarkByGroup) {
        this.remarkByGroup = remarkByGroup;
    }

    public Boolean getSubmittedByGroup() {
        return submittedByGroup;
    }

    public void setSubmittedByGroup(Boolean submittedByGroup) {
        this.submittedByGroup = submittedByGroup;
    }

    public Boolean getQualifiedByGroup() {
        return qualifiedByGroup;
    }

    public void setQualifiedByGroup(Boolean qualifiedByGroup) {
        this.qualifiedByGroup = qualifiedByGroup;
    }

    public Tutor getLeader() {
        return leader;
    }

    public void setLeader(Tutor leader) {
        this.leader = leader;
    }

    public Calendar getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Calendar submittedDate) {
        this.submittedDate = submittedDate;
    }

    public Double getTotalScoreGroup() {
        return CommonHelper.getSumDouble(this.qualityScore, this.completenessScore, this.replyScore, this.correctnessSocre);
    }


}
