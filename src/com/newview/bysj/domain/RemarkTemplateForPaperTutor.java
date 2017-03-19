package com.newview.bysj.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 论文题目导师的评语模板
 */
@Entity
@DiscriminatorValue(value = "remarkTemplateForPaperTutor")
@DynamicInsert(true)
@DynamicUpdate(true)
public class RemarkTemplateForPaperTutor extends RemarkTemplate {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public RemarkTemplateForPaperTutor() {
        super.setCategory("论文-导师课题评语条目");
    }

}
