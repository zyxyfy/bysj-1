package com.newview.bysj.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 论文题目答辩小组的评语模板
 */
@Entity
@DiscriminatorValue(value = "remarkTemplateForPaperGroup")
@DynamicInsert(true)
@DynamicUpdate(true)
public class RemarkTemplateForPaperGroup extends RemarkTemplate {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public RemarkTemplateForPaperGroup() {
        super.setCategory("论文-答辩小组课题评语条目");
    }

}
