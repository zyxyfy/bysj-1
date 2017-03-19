package com.newview.bysj.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ManyToAny;

@Entity
@DiscriminatorValue(value = "collegeRankSuperviseReport")
@DynamicInsert(true)
@DynamicUpdate(true)
public class SchoolSupervisionReport extends SupervisionReport {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;


    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
