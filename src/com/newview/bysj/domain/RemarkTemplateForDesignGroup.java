package com.newview.bysj.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 设计题目答辩小组的评语模板
 */
@Entity
@DiscriminatorValue(value = "remarkTemplateForDesignGroup")
@DynamicInsert(true)
@DynamicUpdate(true)
public class RemarkTemplateForDesignGroup extends RemarkTemplate {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public RemarkTemplateForDesignGroup() {
        super.setCategory("设计-答辩小组课题评语条目");
    }

}
