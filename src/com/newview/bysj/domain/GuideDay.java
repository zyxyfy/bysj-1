package com.newview.bysj.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 */
@Entity
@Table(name = "guideDay")
@DynamicInsert(true)
@DynamicUpdate(true)
public class GuideDay implements Serializable {

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
    private Integer first;
    /**
     * @generated
     */
    private Integer second;
    /**
     * @generated
     */
    private Integer third;
    /**
     * @generated
     */
    private Integer fourth;

    public GuideDay() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFirst() {
        return first;
    }

    public void setFirst(Integer first) {
        this.first = first;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    public Integer getThird() {
        return third;
    }

    public void setThird(Integer third) {
        this.third = third;
    }

    public Integer getFourth() {
        return fourth;
    }

    public void setFourth(Integer fourth) {
        this.fourth = fourth;
    }


}
