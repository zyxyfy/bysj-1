package com.newview.bysj.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "user")
@DynamicInsert(true)
@DynamicUpdate(true)
@JsonIgnoreProperties(value = {"actor", "userRole"})
public class User implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 用户名不能重复
     *
     * @generated
     */
    @Column(length = 20, unique = true)
    private String username;
    @Column(length = 32)
    /**
     * @param password 写入MD5密文
     * @generated
     */
    private String password;
    private Integer loginTime;
    @Column(length = 15)
    private String lastLoginIp;
    private Calendar lastLoginTime;
    private Boolean constraintOfLogin;

    /**
     * 用户 一对一
     *
     * @generated
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Actor actor;
    /**
     * 用户角色 一对多
     *
     * @generated
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserRole> userRole;

    public User() {
        super();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Integer loginTime) {
        this.loginTime = loginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Calendar getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Calendar lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Boolean getConstraintOfLogin() {
        return constraintOfLogin;
    }

    public void setConstraintOfLogin(Boolean constraintOfLogin) {
        this.constraintOfLogin = constraintOfLogin;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public List<UserRole> getUserRole() {
        return userRole;
    }

    public void setUserRole(List<UserRole> userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", loginTime=" + loginTime
                + ", lastLoginIp=" + lastLoginIp + ", lastLoginTime=" + lastLoginTime + ", constraintOfLogin="
                + constraintOfLogin + ", actor=" + actor + ", userRole=" + userRole + "]";
    }


}
