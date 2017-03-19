package com.newview.bysj.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 教师申报题目的时间
 */
@Entity
@DiscriminatorValue(value = "constraintOfProposeProject")
@DynamicInsert(true)
@DynamicUpdate(true)
public class ConstraintOfProposeProject extends Constraint {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "ConstraintOfProposeProject [id=" + getId() + ", department="
                + getDepartment().getDescription() + ", startTime=" + getStartTime().getTime() + ", endTime="
                + getEndTime().getTime() + "]";
    }


}
