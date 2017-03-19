package com.newview.bysj.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 设计题目导师的评语模板
 */
@Entity
@DiscriminatorValue(value = "remarkTemplateForDesignTutor")
@DynamicInsert(true)
@DynamicUpdate(true)
public class RemarkTemplateForDesignTutor extends RemarkTemplate {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public RemarkTemplateForDesignTutor() {
        super.setCategory("设计-导师课题评语条目");
    }

}
