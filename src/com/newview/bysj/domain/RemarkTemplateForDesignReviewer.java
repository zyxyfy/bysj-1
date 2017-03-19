package com.newview.bysj.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 设计题目评阅人评语模板
 */
@Entity
@DiscriminatorValue(value = "remarkTemplateForDesignReviewer")
@DynamicInsert(true)
@DynamicUpdate(true)
public class RemarkTemplateForDesignReviewer extends RemarkTemplate {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public RemarkTemplateForDesignReviewer() {
        super.setCategory("设计-评阅人课题评语条目");
    }

}
