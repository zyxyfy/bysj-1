package com.newview.bysj.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DiscriminatorValue(value = "remarkTemplateForPaperReviewer")
@DynamicInsert(true)
@DynamicUpdate(true)
public class RemarkTemplateForPaperReviewer extends RemarkTemplate {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public RemarkTemplateForPaperReviewer() {
        super.setCategory("论文-评阅人课题评语条目");
    }

}
