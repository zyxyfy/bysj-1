package com.newview.bysj.domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "graduateProjectCategory")
@DynamicInsert(true)
@DynamicUpdate(true)
public class GraduateProject implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String category;
    /*
     * 终稿
     */
    private String finalDraft;
    @Version
    private Integer version;
    //选题表：年份
    private Integer year;
    //选题表：标题
    @Column(length = 50)
    private String title;
    //选题表：副标题
    @Column(length = 50)
    private String subTitle;
    //选题表：工作内容
    @Column(length = 300)
    private String content;
    //选题表：基本要求
    @Column(length = 300)
    private String basicRequirement;
    //选题表：基本技能
    @Column(length = 300)
    private String basicSkill;
    //选题表：参考文献
    @Column(length = 500)
    private String reference;
    //报题：送审
    @JsonIgnore
    private Boolean proposerSubmitForApproval;
    /*
     * 总分
     */
    private Integer totalScore;
    /*
     * 推荐
     */
    @JsonIgnore
    private Boolean recommended;

    /*
     * 专业
     * 多对一
     */
    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major;
    /*
     * 论文申报者
     * 多对一
     */
    @ManyToOne
    @JoinColumn(name = "proposer_id")
    private Tutor proposer;
    /*
     * 题目来源
     * 多对一
     */
    @ManyToOne
    @JoinColumn(name = "projectFrom_id")
    private ProjectFrom projectFrom;
    /*
     * 题目性质
     * 多对一
     */
    @ManyToOne
    @JoinColumn(name = "projectFidelity_id")
    private ProjectFidelity projectFidelity;
    /*
     * 题目类型
     * 多对一
     */
    @ManyToOne
    @JoinColumn(name = "projectType_id")
    private ProjectType projectType;
    /*
     * 学生
     * 一对一
     */
    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;
    /*
     * 任务书
     * 一对一
     */
    @JsonIgnore
    @OneToOne(mappedBy = "graduateProject")
    private TaskDoc taskDoc;

    /*
     * 答辩小组
     * 多对一
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "replyGroup_id")
    private ReplyGroup replyGroup;
    /*
     * 评价阶段：指导教师评审表
     */
    @JsonIgnore
    @Embedded
    private CommentByTutor commentByTutor;
    /*
     * 时间
     * 一对多
     */
    @JsonIgnore
    @OneToMany(mappedBy = "graduateProject", cascade = CascadeType.ALL)
    private List<Schedule> schedules;
    /*
     * 主指导
     * 一对一
     */
    @JsonIgnore
    @OneToOne
    private MainTutorage mainTutorage;
    /*
     * 合作指导
     * 一对多
     */
    @JsonIgnore
    @OneToMany(mappedBy = "graduateProject", cascade = CascadeType.ALL)
    private List<CoTutorage> coTutorage;
    /*
	 *答辩小组意见 
	 */

    @JsonIgnore
    @Embedded
    private CommentByGroup commentByGroup;
	/*
	 * 教研室主任审核题目
	 * 一对一
	 */

    @OneToOne(cascade = CascadeType.ALL)
    private Audit auditByDirector;
    /*
     * 答辩委员会审核题目
     * 一对一
     */
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Audit totalScoreAuditByChairman;
    /*
     * 评阅人
     * 多对一
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private Tutor reviewer;
    /*
     * 评分阶段：评阅人评阅结论
     * 一对一
     */
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Audit auditByReviewer;
    /*
     *评分阶段： 指导教师评阅结论
     * 一对一
     */
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Audit auditByTutor;
    /*
     * 评阅人审评表
     */
    @JsonIgnore
    @Embedded
    private CommentByReviewer commentByReviewer;

    /*
     * 指导记录
     * 一对多
     */
    @JsonIgnore
    @OneToMany(mappedBy = "graduateProject", cascade = CascadeType.ALL)
    private List<GuideRecord> guideRecord;
    /*
     * 指导时间
     * 一对一，单向
     */
    @JsonIgnore
    @OneToOne
    private GuideDay guideDay;

    public GuideDay getGuideDay() {
        return guideDay;
    }

    public void setGuideDay(GuideDay guideDay) {
        this.guideDay = guideDay;
    }

    /*
     * 答辩小组成员的答辩分数
     * 一对多
     */
    @OneToMany(mappedBy = "graduateProject", cascade = CascadeType.ALL)
    private List<ReplyGroupMemberScore> replyGroupMemberScores;

    /*
     * 考察
     * 一对多
     */
    @JsonIgnore
    @OneToMany(mappedBy = "graduateProject", cascade = CascadeType.ALL)
    private List<Quiz> quiz;
    /*
     * 阶段成果
     * 一对多
     */
    @JsonIgnore
    @OneToMany(mappedBy = "graduateProject", cascade = CascadeType.ALL)
    private List<StageAchievement> stageAchievement;
    /*
     * 校优毕业论文
     * 一对一
     */
    @JsonIgnore
    @OneToOne(mappedBy = "graduateProject", cascade = CascadeType.ALL)
    private SchoolExcellentProject schoolExcellentProject;
    /*
     * 省优毕业论文
     * 一对一
     */
    @JsonIgnore
    @OneToOne(mappedBy = "graudateProject", cascade = CascadeType.ALL)
    private ProvinceExcellentProject provinceExcellentProject;

    public GraduateProject() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFinalDraft() {
        return finalDraft;
    }

    public void setFinalDraft(String finalDraft) {
        this.finalDraft = finalDraft;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBasicRequirement() {
        return basicRequirement;
    }

    public void setBasicRequirement(String basicRequirement) {
        this.basicRequirement = basicRequirement;
    }

    public String getBasicSkill() {
        return basicSkill;
    }

    public void setBasicSkill(String basicSkill) {
        this.basicSkill = basicSkill;
    }

    public String getReference() {
        return reference;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Boolean getProposerSubmitForApproval() {
        return proposerSubmitForApproval;
    }

    public void setProposerSubmitForApproval(Boolean proposerSubmitForApproval) {
        this.proposerSubmitForApproval = proposerSubmitForApproval;
    }

    public Boolean getRecommended() {
        return recommended;
    }

    public void setRecommended(Boolean recommended) {
        this.recommended = recommended;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public Tutor getProposer() {
        return proposer;
    }

    public void setProposer(Tutor proposer) {
        this.proposer = proposer;
    }

    public ProjectFrom getProjectFrom() {
        return projectFrom;
    }

    public void setProjectFrom(ProjectFrom projectFrom) {
        this.projectFrom = projectFrom;
    }

    public ProjectFidelity getProjectFidelity() {
        return projectFidelity;
    }

    public void setProjectFidelity(ProjectFidelity projectFidelity) {
        this.projectFidelity = projectFidelity;
    }

    public ProjectType getProjectType() {
        return projectType;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public TaskDoc getTaskDoc() {
        return taskDoc;
    }

    public void setTaskDoc(TaskDoc taskDoc) {
        this.taskDoc = taskDoc;
    }

    public ReplyGroup getReplyGroup() {
        return replyGroup;
    }

    public void setReplyGroup(ReplyGroup replyGroup) {
        this.replyGroup = replyGroup;
    }

    public CommentByTutor getCommentByTutor() {
        return commentByTutor;
    }

    public void setCommentByTutor(CommentByTutor commentByTutor) {
        this.commentByTutor = commentByTutor;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public MainTutorage getMainTutorage() {
        return mainTutorage;
    }

    public void setMainTutorage(MainTutorage mainTutorage) {
        this.mainTutorage = mainTutorage;
    }

    public List<CoTutorage> getCoTutorage() {
        return coTutorage;
    }

    public void setCoTutorage(List<CoTutorage> coTutorage) {
        this.coTutorage = coTutorage;
    }

    public CommentByGroup getCommentByGroup() {
        return commentByGroup;
    }

    public void setCommentByGroup(CommentByGroup commentByGroup) {
        this.commentByGroup = commentByGroup;
    }

    public Audit getAuditByDirector() {
        return auditByDirector;
    }

    public void setAuditByDirector(Audit auditByDirector) {
        this.auditByDirector = auditByDirector;
    }

    public Audit getTotalScoreAuditByChairman() {
        return totalScoreAuditByChairman;
    }

    public void setTotalScoreAuditByChairman(Audit totalScoreAuditByChairman) {
        this.totalScoreAuditByChairman = totalScoreAuditByChairman;
    }

    public Tutor getReviewer() {
        return reviewer;
    }

    public void setReviewer(Tutor reviewer) {
        this.reviewer = reviewer;
    }

    public Audit getAuditByReviewer() {
        return auditByReviewer;
    }

    public void setAuditByReviewer(Audit auditByReviewer) {
        this.auditByReviewer = auditByReviewer;
    }

    public Audit getAuditByTutor() {
        return auditByTutor;
    }

    public void setAuditByTutor(Audit auditByTutor) {
        this.auditByTutor = auditByTutor;
    }

    public CommentByReviewer getCommentByReviewer() {
        return commentByReviewer;
    }

    public void setCommentByReviewer(CommentByReviewer commentByReviewer) {
        this.commentByReviewer = commentByReviewer;
    }

    public List<GuideRecord> getGuideRecord() {
        return guideRecord;
    }

    public void setGuideRecord(List<GuideRecord> guideRecord) {
        this.guideRecord = guideRecord;
    }


    public List<ReplyGroupMemberScore> getReplyGroupMemberScores() {
        return replyGroupMemberScores;
    }

    public void setReplyGroupMemberScores(
            List<ReplyGroupMemberScore> replyGroupMemberScores) {
        this.replyGroupMemberScores = replyGroupMemberScores;
    }

    public List<Quiz> getQuiz() {
        return quiz;
    }

    public void setQuiz(List<Quiz> quiz) {
        this.quiz = quiz;
    }

    public List<StageAchievement> getStageAchievement() {
        return stageAchievement;
    }

    public void setStageAchievement(List<StageAchievement> stageAchievement) {
        this.stageAchievement = stageAchievement;
    }

    public SchoolExcellentProject getSchoolExcellentProject() {
        return schoolExcellentProject;
    }

    public void setSchoolExcellentProject(
            SchoolExcellentProject schoolExcellentProject) {
        this.schoolExcellentProject = schoolExcellentProject;
    }

    public ProvinceExcellentProject getProvinceExcellentProject() {
        return provinceExcellentProject;
    }

    public void setProvinceExcellentProject(
            ProvinceExcellentProject provinceExcellentProject) {
        this.provinceExcellentProject = provinceExcellentProject;
    }

    public Double getTotalScores() {
        Double num = 0.0;
        if (this.getCommentByTutor() != null)
            num += commentByTutor.getTotleScoreTutor();
        if (this.getCommentByReviewer() != null)
            num += commentByReviewer.getTotalScoreReviewer();
        if (this.getCommentByGroup() != null)
            num += commentByGroup.getTotalScoreGroup();
        return num;
    }

}
