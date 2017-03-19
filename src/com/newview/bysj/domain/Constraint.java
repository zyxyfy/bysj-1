package com.newview.bysj.domain;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 设置时间的父类
 */
@Entity
@Table(name = "contraintTable")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

@DiscriminatorColumn(name = "constraintCategory", length = 80)
@DynamicInsert(true)
@DynamicUpdate(true)
public class Constraint implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 开始时间
     *
     * @generated
     */
    private Calendar startTime;
    /**
     * 结束时间
     *
     * @generated
     */
    private Calendar endTime;
    /**
     * 教研室
     * 一对一
     *
     * @generated
     */
    @OneToOne
    private Department department;

    public Constraint() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    @JsonBackReference
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Constraint [id=" + id + ", startTime=" + startTime
                + ", endTime=" + endTime + ", department=" + department + "]";
    }

}
