package com.newview.bysj.domain;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

@Entity
@Table(name = "replyGroupMemberScore")
public class ReplyGroupMemberScore implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * @generated 论文与实物的质量评分（0-10分）
     */
    @Column(name = "qualityScoreByGroupTutor")
    private Integer qualityScoreByGroupTutor;
    /**
     * @generated 完成任务书规定的要求与水平评分（0-10分)
     */
    @Column(name = "completenessScoreByGroupTutor")
    private Integer completenessScoreByGroupTutor;
    /**
     * @generated 论文内容的答辩陈述评分（0-10分）
     */
    @Column(name = "replyScoreByGroupTutor")
    private Integer replyScoreByGroupTutor;

    /**
     * @generated 回答问题的正确性评分（0-10分）
     */
    @Column(name = "correctnessScoreByGroupTutor")
    private Integer correctnessScoreByGroupTutor;

    @Column(name = "remarkByGroupTutor")
    private String remarkByGroupTutor;

    @Column(name = "questionByTutor_1")
    private String questionByTutor_1;

    @Column(name = "questionByTutor_2")
    private String questionByTutor_2;

    @Column(name = "questionByTutor_3")
    private String questionByTutor_3;

    @Column(name = "responseRemarkByTutor_1")
    private String responseRemarkByTutor_1;

    @Column(name = "responseRemarkByTutor_2")
    private String responseRemarkByTutor_2;

    @Column(name = "responseRemarkByTutor_3")
    private String responseRemarkByTutor_3;

    @Column(name = "studentResponse_1")
    private String studentResponse_1;

    @Column(name = "studentResponse_2")
    private String studentResponse_2;

    @Column(name = "studentResponse_3")
    private String studentResponse_3;

    @Column(name = "studentId")
    private Integer studentId;

    @Column(name = "graduateProjectId")
    private Integer graduateProjectId;

    @Column(name = "tutorId")
    private Integer tutor_id;

    @Column(name = "replyGroupId")
    private Integer replyGroup_id;
    /**
     * @generated
     */
    @Column(name = "commentByGroupSubmittedByTutor")
    private Boolean submitted;

    //审核日期
    @Column(name = "commentFromGroupSubmittedDate")
    private Calendar submittedDate;

    //加上下划线，防止和关键字冲突
    @Column(name = "in_dex")
    private Integer in_dex;
    /**
     * 多对一
     *
     * @generated
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "graduateProject_id")
    private GraduateProject graduateProject;
    /**
     * 多对一
     *
     * @generated
     */
    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    @ManyToOne
    @JoinColumn(name = "replyGroup_id")
    @JsonIgnore
    private ReplyGroup replyGroup;


    public ReplyGroupMemberScore() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Boolean getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Boolean submitted) {
        this.submitted = submitted;
    }

    public Calendar getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Calendar submittedDate) {
        this.submittedDate = submittedDate;
    }

    @JsonBackReference
    public GraduateProject getGraduateProject() {
        return graduateProject;
    }

    public void setGraduateProject(GraduateProject graduateProject) {
        this.graduateProject = graduateProject;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }


    public void setIn_dex(Integer in_dex) {
        this.in_dex = in_dex;
    }

    public Integer getIn_dex() {
        return in_dex;
    }

    public Integer getQualityScoreByGroupTutor() {
        return qualityScoreByGroupTutor;
    }

    public void setQualityScoreByGroupTutor(Integer qualityScoreByGroupTutor) {
        this.qualityScoreByGroupTutor = qualityScoreByGroupTutor;
    }

    public Integer getCompletenessScoreByGroupTutor() {
        return completenessScoreByGroupTutor;
    }

    public void setCompletenessScoreByGroupTutor(Integer completenessScoreByGroupTutor) {
        this.completenessScoreByGroupTutor = completenessScoreByGroupTutor;
    }

    public Integer getReplyScoreByGroupTutor() {
        return replyScoreByGroupTutor;
    }

    public void setReplyScoreByGroupTutor(Integer replyScoreByGroupTutor) {
        this.replyScoreByGroupTutor = replyScoreByGroupTutor;
    }

    public Integer getCorrectnessScoreByGroupTutor() {
        return correctnessScoreByGroupTutor;
    }

    public void setCorrectnessScoreByGroupTutor(Integer correctnessScoreByGroupTutor) {
        this.correctnessScoreByGroupTutor = correctnessScoreByGroupTutor;
    }

    public String getQuestionByTutor_1() {
        return questionByTutor_1;
    }

    public void setQuestionByTutor_1(String questionByTutor_1) {
        this.questionByTutor_1 = questionByTutor_1;
    }

    public String getQuestionByTutor_2() {
        return questionByTutor_2;
    }

    public void setQuestionByTutor_2(String questionByTutor_2) {
        this.questionByTutor_2 = questionByTutor_2;
    }

    public String getQuestionByTutor_3() {
        return questionByTutor_3;
    }

    public void setQuestionByTutor_3(String questionByTutor_3) {
        this.questionByTutor_3 = questionByTutor_3;
    }

    public String getResponseRemarkByTutor_1() {
        return responseRemarkByTutor_1;
    }

    public void setResponseRemarkByTutor_1(String responseRemarkByTutor_1) {
        this.responseRemarkByTutor_1 = responseRemarkByTutor_1;
    }

    public String getResponseRemarkByTutor_2() {
        return responseRemarkByTutor_2;
    }

    public void setResponseRemarkByTutor_2(String responseRemarkByTutor_2) {
        this.responseRemarkByTutor_2 = responseRemarkByTutor_2;
    }

    public String getResponseRemarkByTutor_3() {
        return responseRemarkByTutor_3;
    }

    public void setResponseRemarkByTutor_3(String responseRemarkByTutor_3) {
        this.responseRemarkByTutor_3 = responseRemarkByTutor_3;
    }

    public String getStudentResponse_1() {
        return studentResponse_1;
    }

    public void setStudentResponse_1(String studentResponse_1) {
        this.studentResponse_1 = studentResponse_1;
    }

    public String getStudentResponse_2() {
        return studentResponse_2;
    }

    public void setStudentResponse_2(String studentResponse_2) {
        this.studentResponse_2 = studentResponse_2;
    }

    public String getStudentResponse_3() {
        return studentResponse_3;
    }

    public void setStudentResponse_3(String studentResponse_3) {
        this.studentResponse_3 = studentResponse_3;
    }

    @JsonBackReference
    public ReplyGroup getReplyGroup() {
        return replyGroup;
    }

    public void setReplyGroup(ReplyGroup replyGroup) {
        this.replyGroup = replyGroup;
    }

    public String getRemarkByGroupTutor() {
        return remarkByGroupTutor;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getGraduateProjectId() {
        return graduateProjectId;
    }

    public void setGraduateProjectId(Integer graduateProjectId) {
        this.graduateProjectId = graduateProjectId;
    }

    public Integer getTutor_id() {
        return tutor_id;
    }

    public void setTutor_id(Integer tutor_id) {
        this.tutor_id = tutor_id;
    }

    public Integer getReplyGroup_id() {
        return replyGroup_id;
    }

    public void setReplyGroup_id(Integer replyGroup_id) {
        this.replyGroup_id = replyGroup_id;
    }

    public void setRemarkByGroupTutor(String remarkByGroupTutor) {
        this.remarkByGroupTutor = remarkByGroupTutor;
    }
}
