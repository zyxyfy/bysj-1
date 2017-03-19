package com.newview.bysj.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 校外职工
 */
@Entity
@Table(name = "visitingEmployee")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DynamicInsert(true)
@DynamicUpdate(true)
public class VisitingEmployee extends Tutor {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 公司
     *
     * @generated
     */
    @Column(length = 40)
    private String company;
    /**
     * 毕业学院
     *
     * @generated
     */
    @Column(length = 40)
    private String graduateFrom;
    /**
     * 外语
     *
     * @generated
     */
    @Column(length = 40)
    private String foreignLanguage;
    /**
     * 专业
     *
     * @generated
     */
    @Column(length = 80)
    private String speciality;
    /**
     * 经验
     *
     * @generated
     */
    @Column(length = 80)
    private String experience;
    /**
     * 计划
     *
     * @generated
     */
    @Column(length = 80)
    private String plan;

    public VisitingEmployee() {
        super();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getGraduateFrom() {
        return graduateFrom;
    }

    public void setGraduateFrom(String graduateFrom) {
        this.graduateFrom = graduateFrom;
    }

    public String getForeignLanguage() {
        return foreignLanguage;
    }

    public void setForeignLanguage(String foreignLanguage) {
        this.foreignLanguage = foreignLanguage;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }


}
