package com.newview.bysj.domain;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 指导记录
 */
@Entity
@Table(name = "guideRecord")
@DynamicInsert(true)
@DynamicUpdate(true)
public class GuideRecord implements Serializable {

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
    private Boolean submittedByStudent;
    /**
     * @generated
     */
    private Calendar time;
    /**
     * 多对一
     *
     * @generated
     */
    @ManyToOne
    @JoinColumn(name = "classRoom_id")
    private ClassRoom classRoom;
    /**
     * 多对一
     *
     * @generated
     */
    @ManyToOne
    @JoinColumn(name = "graduateProject_id")
    private GraduateProject graduateProject;
    /**
     * 导师审核指导记录
     * 一对一
     *
     * @generated
     */
    @OneToOne(cascade = CascadeType.ALL)
    private Audit auditedByTutor;

    public GuideRecord() {
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

    public Boolean getSubmittedByStudent() {
        return submittedByStudent;
    }

    public void setSubmittedByStudent(Boolean submittedByStudent) {
        this.submittedByStudent = submittedByStudent;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public GraduateProject getGraduateProject() {
        return graduateProject;
    }

    public void setGraduateProject(GraduateProject graduateProject) {
        this.graduateProject = graduateProject;
    }

    public Audit getAuditedByTutor() {
        return auditedByTutor;
    }

    public void setAuditedByTutor(Audit auditedByTutor) {
        this.auditedByTutor = auditedByTutor;
    }


}
