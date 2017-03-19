package com.newview.bysj.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 题目来源
 */
@Entity
@Table(name = "projectFrome")
@DynamicInsert(true)
@DynamicUpdate(true)
public class ProjectFrom implements Serializable {

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
    private String description;
    /**
     * 毕业论文
     * 一对多
     *
     * @generated
     */
    @OneToMany(mappedBy = "projectFrom")
    private List<GraduateProject> graduateProject;

    public ProjectFrom() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonBackReference
    public List<GraduateProject> getGraduateProject() {
        return graduateProject;
    }

    public void setGraduateProject(List<GraduateProject> graduateProject) {
        this.graduateProject = graduateProject;
    }

}
