package com.newview.bysj.domain;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 答辩小组
 */
@Entity
@Table(name = "replyGroup")
@DynamicInsert(true)
@DynamicUpdate(true)
public class
ReplyGroup implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * @generated
     */
    private String description;
    /**
     * @generated
     */
    @Version
    private Integer version;
    /**
     * 答辩时间
     *
     * @generated
     */
    @Embedded
    private ReplyTime replyTime;
    /**
     * 用来获取保存后的答辩小组，因为jpa保存后没有返回对应列的ID
     */
    private Integer num;
    /**
     * 一对多
     *
     * @generated
     */
    @OneToMany(mappedBy = "replyGroup")
    private List<GraduateProject> graduateProject;
    /**
     * 教研室
     * 多对一
     *
     * @generated
     */
    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonIgnore
    private Department department;
    /**
     * 答辩小组成员
     * 多对多
     *
     * @generated
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tutorAsMemberToReplyGroup", joinColumns = {@JoinColumn(name = "replyGroup_id")},
            inverseJoinColumns = {@JoinColumn(name = "member_id")})
    @JsonIgnore
    private List<Tutor> members;

    /**
     * 答辩小组组长
     * 多对一
     *
     * @generated
     */
    @ManyToOne
    @JoinColumn(name = "leader_id")
    private Tutor leader;
    /**
     * 一对多
     */
    @OneToMany(mappedBy = "replyGroup")
    @JsonIgnore
    private List<Student> student;
    /**
     * 答辩地点
     */
    @ManyToOne
    @JoinColumn(name = "classRoom_id")
    private ClassRoom classRoom;

    @OneToMany
    @JoinColumn(name = "replyGroupMemberScore_id")
    private List<ReplyGroupMemberScore> replyGroupMemberScoreList;

    public ReplyGroup() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVersion() {
        return version;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<GraduateProject> getGraduateProject() {
        return graduateProject;
    }

    public void setGraduateProject(List<GraduateProject> graduateProject) {
        this.graduateProject = graduateProject;
    }

    @JsonBackReference
    public Department getDepartment() {
        return department;
    }

    @JsonBackReference
    public void setDepartment(Department department) {
        this.department = department;
    }

    @JsonBackReference
    public List<Tutor> getMembers() {
        return members;
    }

    public void setMembers(List<Tutor> members) {
        this.members = members;
    }

    public Tutor getLeader() {
        return leader;
    }

    public void setLeader(Tutor leader) {
        this.leader = leader;
    }

    public List<Student> getStudent() {
        return student;
    }

    public void setStudent(List<Student> student) {
        this.student = student;
    }

    public ReplyTime getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(ReplyTime replyTime) {
        this.replyTime = replyTime;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }


    public List<ReplyGroupMemberScore> getReplyGroupMemberScoreList() {
        return replyGroupMemberScoreList;
    }

    public void setReplyGroupMemberScoreList(List<ReplyGroupMemberScore> replyGroupMemberScoreList) {
        this.replyGroupMemberScoreList = replyGroupMemberScoreList;
    }
}
