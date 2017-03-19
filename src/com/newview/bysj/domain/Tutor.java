package com.newview.bysj.domain;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tutor")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DynamicInsert(true)
@DynamicUpdate(true)
@JsonIgnoreProperties
/**
 * 所有教师类型的抽象父类
 *
 */
public abstract class Tutor extends Actor {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * @generated
     */
    private String signPictureURL;
    /**
     * 教研室
     * 多对一
     *
     * @generated
     */
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    /**
     * 职称
     * 多对一
     *
     * @generated
     */
    @ManyToOne
    @JoinColumn(name = "proTitle_id")
    private ProTitle proTitle;
    /**
     * 学位
     * 多对一
     *
     * @generated
     */
    @ManyToOne
    @JoinColumn(name = "degree_id")
    private Degree degree;
    /**
     * 主指导
     * 一对多
     *
     * @generated
     */
    @OneToMany(mappedBy = "tutor")
    @JsonIgnore
    private List<MainTutorage> mainTutorage;
    /**
     * 合作指导
     * 一对多
     *
     * @generated
     */
    @OneToMany(mappedBy = "tutor")
    @JsonIgnore
    private List<CoTutorage> coTutorage;

    /**
     * 学生
     * 多对一
     *
     * @generated
     */
    @OneToMany(mappedBy = "tutor")
    private List<Student> student;
    /**
     * 申报的题目
     * 一对多
     *
     * @generated
     */
    @OneToMany(mappedBy = "proposer")
    @JsonIgnore
    private List<GraduateProject> proposedGraduateProject;

    public List<GraduateProject> getProposedGraduateProject() {
        return proposedGraduateProject;
    }

    @JsonBackReference
    public void setProposedGraduateProject(List<GraduateProject> proposedGraduateProject) {
        this.proposedGraduateProject = proposedGraduateProject;
    }

    @JsonBackReference
    public List<ReplyGroup> getTutorAsLeaderToReplyGroups() {
        return tutorAsLeaderToReplyGroups;
    }

    public void setTutorAsLeaderToReplyGroups(List<ReplyGroup> tutorAsLeaderToReplyGroups) {
        this.tutorAsLeaderToReplyGroups = tutorAsLeaderToReplyGroups;
    }

    /**
     * 一对多
     *
     * @generated
     */
    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL)
    private List<GraduateProject> graduateProjectToReview;
    /**
     * 答辩小组
     * 多对多
     *
     * @generated
     */
    @ManyToMany(mappedBy = "members", cascade = CascadeType.ALL)
    private List<ReplyGroup> replyGroup;
    /**
     * 答辩小组
     * 一对多
     *
     * @generated
     */
    @OneToMany(mappedBy = "leader")
    private List<ReplyGroup> tutorAsLeaderToReplyGroups;
    /**
     * 评论模板
     * 一对多
     *
     * @generated
     */
    @OneToMany(mappedBy = "builder", cascade = CascadeType.ALL)
    private List<RemarkTemplate> remarkTemplates;

    public Tutor() {
        super();
    }

    public Tutor(String no, String name) {
        super(no, name);
    }

    public String getSignPictureURL() {
        return signPictureURL;
    }

    public void setSignPictureURL(String signPictureURL) {
        this.signPictureURL = signPictureURL;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public ProTitle getProTitle() {
        return proTitle;
    }

    public void setProTitle(ProTitle proTitle) {
        this.proTitle = proTitle;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    @JsonBackReference
    public List<MainTutorage> getMainTutorage() {
        return mainTutorage;
    }

    public void setMainTutorage(List<MainTutorage> mainTutorage) {
        this.mainTutorage = mainTutorage;
    }

    @JsonBackReference
    public List<CoTutorage> getCoTutorage() {
        return coTutorage;
    }

    public void setCoTutorage(List<CoTutorage> coTutorage) {
        this.coTutorage = coTutorage;
    }

    @JsonBackReference
    public List<Student> getStudent() {
        return student;
    }

    public void setStudent(List<Student> student) {
        this.student = student;
    }

    @JsonBackReference
    public List<GraduateProject> getGraduateProjectToReview() {
        return graduateProjectToReview;
    }

    public void setGraduateProjectToReview(
            List<GraduateProject> graduateProjectToReview) {
        this.graduateProjectToReview = graduateProjectToReview;
    }

    @JsonBackReference
    public List<ReplyGroup> getReplyGroup() {
        return replyGroup;
    }

    public void setReplyGroup(List<ReplyGroup> replyGroup) {
        this.replyGroup = replyGroup;
    }

    @JsonBackReference
    public List<RemarkTemplate> getRemarkTemplates() {
        return remarkTemplates;
    }

    public void setRemarkTemplates(List<RemarkTemplate> remarkTemplates) {
        this.remarkTemplates = remarkTemplates;
    }


}
