package com.newview.bysj.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 角色资源
 */
@Entity
@Table(name = "roleresource")
@DynamicInsert(true)
@DynamicUpdate(true)
public class RoleResource implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 角色
     * 多对一
     *
     * @generated
     */
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    /**
     * 资源
     * 多对一
     *
     * @generated
     */
    @ManyToOne
    @JoinColumn(name = "resource_id")
    private Resource resource;
    /**
     * 期限
     * 一对一
     *
     * @generated
     */
    @OneToOne(mappedBy = "roleResource")
    private Validity validity;

    public RoleResource() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Validity getValidity() {
        return validity;
    }

    public void setValidity(Validity validity) {
        this.validity = validity;
    }


}
