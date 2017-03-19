package com.newview.bysj.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * 论文题目
 */
@Entity
@DiscriminatorValue(value = "paperProject")
@DynamicInsert(true)
@DynamicUpdate(true)
public class PaperProject extends GraduateProject {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @OneToOne
    @JoinColumn(name = "openningReport_id")
    private OpenningReport openningReport;

    public PaperProject() {
        super.setCategory("论文题目");
    }

    public OpenningReport getOpenningReport() {
        return openningReport;
    }

    public void setOpenningReport(OpenningReport openningReport) {
        this.openningReport = openningReport;
    }

}
