package com.newview.bysj.domain;

import java.io.Serializable;

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
 * 校优毕业论文
 */
@Entity
@Table(name = "schoolExcellentProject")
@DynamicInsert(true)
@DynamicUpdate(true)
public class SchoolExcellentProject implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 是否推优
     *
     * @generated
     */
    private Boolean recommended;
    /**
     * 一对一
     *
     * @generated
     */
    @OneToOne
    @JoinColumn(name = "graduateProject_id")
    private GraduateProject graduateProject;

    public SchoolExcellentProject() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getRecommended() {
        return recommended;
    }

    public void setRecommended(Boolean recommended) {
        this.recommended = recommended;
    }

    public GraduateProject getGraduateProject() {
        return graduateProject;
    }

    public void setGraduateProject(GraduateProject graduateProject) {
        this.graduateProject = graduateProject;
    }


}
