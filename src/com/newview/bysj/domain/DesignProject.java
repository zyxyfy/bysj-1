package com.newview.bysj.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 设计题目
 */
@Entity
@DiscriminatorValue(value = "designProject")
@DynamicInsert(true)
@DynamicUpdate(true)
public class DesignProject extends GraduateProject {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DesignProject() {
        super.setCategory("设计题目");
    }

}
