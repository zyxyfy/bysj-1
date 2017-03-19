package com.newview.bysj.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 角色类
 */
@Entity
@Table(name = "role")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Role implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 40)
    private String no;
    /**
     * 角色汉字表示
     *
     * @ganareted
     */
    @Column(length = 40)
    private String description;
    /**
     * 角色英文表示
     *
     * @ganareted
     */
    @Column(name = "roleName", unique = true)
    private String roleName;
    /**
     * 角色管理者
     * 多对一
     */
    @ManyToOne
    private Role roleHandler;
    /**
     * 角色被管理者
     * 一对多
     */
    @OneToMany(mappedBy = "roleHandler", cascade = CascadeType.ALL)
    private List<Role> roleHandleds;
    /**
     * 用户角色
     * 一对多
     *
     * @ganareted
     */
    @OneToMany(mappedBy = "role", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<UserRole> userRole;
    /**
     * 角色资源
     * 一对多
     *
     * @generated
     */
    @OneToMany(mappedBy = "role", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<RoleResource> roleResource;

    public Role() {
        super();
    }

    public Role(String description, String roleName) {
        this.description = description;
        this.roleName = roleName;
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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<UserRole> getUserRole() {
        return userRole;
    }

    public void setUserRole(List<UserRole> userRole) {
        this.userRole = userRole;
    }

    public Role getRoleHandler() {
        return roleHandler;
    }

    public void setRoleHandler(Role roleHandler) {
        this.roleHandler = roleHandler;
    }

    public List<Role> getRoleHandleds() {
        return roleHandleds;
    }

    public void setRoleHandleds(List<Role> roleHandleds) {
        this.roleHandleds = roleHandleds;
    }

    public List<RoleResource> getRoleResource() {
        return roleResource;
    }

    public void setRoleResource(List<RoleResource> roleResource) {
        this.roleResource = roleResource;
    }

    @Override
    public String toString() {
        return "Role [id=" + id + ", no=" + no + ", description=" + description + ", roleName=" + roleName
                + ", userRole=" + userRole + "]";
    }


}
