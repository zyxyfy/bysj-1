package com.newview.bysj.domain;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 审核类，本类不关联被审核的对象，审核对象单向关联审核类
 */
@Entity
@Table(name = "audit")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Audit implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 审核是否通过，true表示通过，false表示不通过
     *
     * @generated
     */
    private Boolean approve;
    /**
     * 审核日期
     *
     * @generated
     */
    @JsonIgnore
    private Calendar auditDate;
    /**
     * 评语，最多80字符
     *
     * @generated
     */
    @JsonIgnore
    @Column(length = 80)
    private String remark;
    /**
     * 审核者
     * 多对一
     *
     * @generated
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "auditor_id")
    private Tutor auditor;

    public Audit() {
        super();
    }

    public Audit(Boolean approve) {
        this.approve = approve;
    }

    public Audit(Boolean approve, Calendar auditDate) {
        this.approve = approve;
        this.auditDate = auditDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getApprove() {
        return approve;
    }

    public void setApprove(Boolean approve) {
        this.approve = approve;
    }

    public Calendar getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Calendar auditDate) {
        this.auditDate = auditDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Tutor getAuditor() {
        return auditor;
    }

    public void setAuditor(Tutor auditor) {
        this.auditor = auditor;
    }


}
