package com.newview.bysj.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 评语模板
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "category")
@DynamicInsert(true)
@DynamicUpdate(true)
public class RemarkTemplate implements Serializable {

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
    private String title;
    /**
     * @generated
     */
    @Transient
    private String category;
    /**
     * @generated
     */
    private Boolean defaultRemarkTemplate;
    /**
     * 评语项
     * 一对多
     *
     * @generated
     */
    @OneToMany(mappedBy = "remarkTemplate", cascade = CascadeType.ALL)
    private List<RemarkTemplateItems> remarkTemplateItems;

    /**
     * 创建模板的老师
     * 多对一
     *
     * @generated
     */
    @ManyToOne
    @JoinColumn(name = "builder_id")
    private Tutor builder;
    /**
     * 创建模板的教研室
     * 多对一
     *
     * @generated
     */
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public RemarkTemplate() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getDefaultRemarkTemplate() {
        return defaultRemarkTemplate;
    }

    public void setDefaultRemarkTemplate(Boolean defaultRemarkTemplate) {
        this.defaultRemarkTemplate = defaultRemarkTemplate;
    }

    public Tutor getBuilder() {
        return builder;
    }

    public void setBuilder(Tutor builder) {
        this.builder = builder;
    }

    @JsonBackReference
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<RemarkTemplateItems> getRemarkTemplateItems() {
        return remarkTemplateItems;
    }

    public void setRemarkTemplateItems(List<RemarkTemplateItems> remarkTemplateItems) {
        this.remarkTemplateItems = remarkTemplateItems;
    }


}
