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
 * 省优毕业论文
 */
@Entity
@Table(name = "provinceExcellentProject")
@DynamicInsert(true)
@DynamicUpdate(true)
public class ProvinceExcellentProject implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 一对一
     *
     * @generated
     */
    @OneToOne
    @JoinColumn(name = "graduateProject_id")
    private GraduateProject graudateProject;

    public ProvinceExcellentProject() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GraduateProject getGraudateProject() {
        return graudateProject;
    }

    public void setGraudateProject(GraduateProject graudateProject) {
        this.graudateProject = graudateProject;
    }


}
