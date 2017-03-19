package com.newview.bysj.initialize.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.newview.bysj.domain.RoleResource;
import com.newview.bysj.domain.UserRole;

@Entity
@Table(name = "role")
@DynamicInsert(true)
@DynamicUpdate(true)
public class RoleWithFk implements Serializable {

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
    @Column(length = 40)
    private String no;
    /**
     * @generated
     */
    private String description;
    /**
     * @generated
     */
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<UserRole> userRoles;
    /**
     * @generated
     */
    @OneToMany(mappedBy = "role", cascade = CascadeType.REFRESH)
    private List<RoleResource> roleResources;

    @ManyToOne
    @JoinColumn(name = "roleHandler_id")
    private RoleWithFk roleHandler;
    /**
     * @generated
     */
    @OneToMany(mappedBy = "roleHandler", cascade = CascadeType.ALL)
    private List<RoleWithFk> roleHandleds;
    /**
     * @generated
     */
    private Integer roleHandlerId;

    public RoleWithFk() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public List<RoleResource> getRoleResources() {
        return roleResources;
    }

    public void setRoleResources(List<RoleResource> roleResources) {
        this.roleResources = roleResources;
    }

    public RoleWithFk getRoleHandler() {
        return roleHandler;
    }

    public void setRoleHandler(RoleWithFk roleHandler) {
        this.roleHandler = roleHandler;
    }

    public List<RoleWithFk> getRoleHandleds() {
        return roleHandleds;
    }

    public void setRoleHandleds(List<RoleWithFk> roleHandleds) {
        this.roleHandleds = roleHandleds;
    }

    public Integer getRoleHandlerId() {
        return roleHandlerId;
    }

    public void setRoleHandlerId(Integer roleHandlerId) {
        this.roleHandlerId = roleHandlerId;
    }


}
