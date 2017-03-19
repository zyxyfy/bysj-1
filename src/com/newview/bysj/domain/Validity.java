package com.newview.bysj.domain;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 期限
 */
@Entity
@Table(name = "validity")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Validity implements Serializable {

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
    private Calendar beginTime;
    /**
     * @generated
     */
    private Calendar endTime;
    /**
     * 专业
     * 一对一
     *
     * @generated
     */
    @OneToOne(mappedBy = "validity", cascade = CascadeType.ALL)
    private Major major;
    /**
     * 角色资源
     * 一对一
     *
     * @generated
     */
    @OneToOne
    @JoinColumn(name = "roleReource_id")
    private RoleResource roleResource;

    public Validity() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Calendar getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Calendar beginTime) {
        this.beginTime = beginTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public RoleResource getRoleResource() {
        return roleResource;
    }

    public void setRoleResource(RoleResource roleResource) {
        this.roleResource = roleResource;
    }


}
