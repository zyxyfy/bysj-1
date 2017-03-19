package com.newview.bysj.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 开题报告
 */
@Entity
@Table(name = "openningReport")
@DynamicInsert(true)
@DynamicUpdate(true)
public class OpenningReport implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 附件文件名
     *
     * @generated
     */
    private String url;
    /**
     * 学生提交标志
     *
     * @generated
     */
    private Boolean submittedByStudent;
    /**
     * 导师审核
     * 一对一
     *
     * @generated
     */
    @OneToOne(cascade = CascadeType.ALL)
    private Audit auditByTutor;
    /**
     * 教研室主任审核
     * 一对一
     *
     * @generated
     */
    @OneToOne(cascade = CascadeType.ALL)
    private Audit auditByDepartmentDirector;
    /**
     * 论文题目,双向关联
     * 一对一
     *
     * @generated
     */
    @OneToOne
    private PaperProject paperProject;

    public OpenningReport() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getSubmittedByStudent() {
        return submittedByStudent;
    }

    public void setSubmittedByStudent(Boolean submittedByStudent) {
        this.submittedByStudent = submittedByStudent;
    }

    public Audit getAuditByTutor() {
        return auditByTutor;
    }

    public void setAuditByTutor(Audit auditByTutor) {
        this.auditByTutor = auditByTutor;
    }

    public Audit getAuditByDepartmentDirector() {
        return auditByDepartmentDirector;
    }

    public void setAuditByDepartmentDirector(Audit auditByDepartmentDirector) {
        this.auditByDepartmentDirector = auditByDepartmentDirector;
    }

    @JsonBackReference
    public PaperProject getPaperProject() {
        return paperProject;
    }

    public void setPaperProject(PaperProject paperProject) {
        this.paperProject = paperProject;
    }


}
