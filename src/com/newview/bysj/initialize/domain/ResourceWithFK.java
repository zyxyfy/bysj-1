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

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.newview.bysj.domain.RoleResource;

@Entity
@Table(name = "resource")
@DynamicInsert(true)
@DynamicUpdate(true)
public class ResourceWithFK implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * @generated
     */
    @Column(length = 80)
    private String description;
    /**
     * @generated
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * @generated
     */
    @OneToMany(mappedBy = "resource", cascade = CascadeType.ALL)
    private List<RoleResource> roleResoures;

    /**
     * @generated 子菜单
     * 注意：此方法已经被其它类调用
     */
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<ResourceWithFK> child;
    /*
     * 父菜单
     */
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ResourceWithFK parent;

    /**
     * @generated
     */
    @Column(length = 80)
    private String no;

    /**
     * @generated
     */

    private Integer parentId;


    public ResourceWithFK() {
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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<RoleResource> getRoleResoures() {
        return roleResoures;
    }

    public void setRoleResoures(List<RoleResource> roleResoures) {
        this.roleResoures = roleResoures;
    }

    public List<ResourceWithFK> getChild() {
        return child;
    }

    public void setChild(List<ResourceWithFK> child) {
        this.child = child;
    }

    public ResourceWithFK getParent() {
        return parent;
    }

    public void setParent(ResourceWithFK parent) {
        this.parent = parent;
    }


}
