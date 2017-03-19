package com.newview.bysj.domain;

import java.io.Serializable;
import java.util.Collection;
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

/**
 * @generated
 */
@Entity
@Table(name = "resource")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Resource implements Serializable {

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
     * @generated 子菜单注意：此方法已经被其它类调用
     */
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Resource> child;

    /**
     * 父菜单
     */
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Resource parent;

    /**
     * @generated
     */
    @Column(length = 80)
    private String url;
    @Column(length = 6)
    private String no;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    @Column(length = 80)
    private String rel;

    /**
     * @generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     * @generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @generated
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     * @generated
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @generated
     */

    public Collection<RoleResource> getRoleResoures() {
        return roleResoures;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Resource> getChild() {
        return child;
    }

    public void setChild(List<Resource> child) {
        this.child = child;
    }

    public void setRoleResoures(List<RoleResource> roleResoures) {
        this.roleResoures = roleResoures;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Resource other = (Resource) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public Resource getParent() {
        return parent;
    }

    public void setParent(Resource parent) {
        this.parent = parent;
    }

}