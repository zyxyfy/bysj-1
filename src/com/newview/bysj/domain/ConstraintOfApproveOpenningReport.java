package com.newview.bysj.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 审核开题报时间
 */
@Entity
@DiscriminatorValue(value = "ConstaintOfApproveOpenningReport")
@DynamicInsert(true)
@DynamicUpdate(true)
public class ConstraintOfApproveOpenningReport extends Constraint {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 是否进行自动处理
     */
    private Boolean defualtApproved;

    public Boolean getDefualtApproved() {
        return defualtApproved;
    }

    public void setDefualtApproved(Boolean defualtApproved) {
        this.defualtApproved = defualtApproved;
    }

    @Override
    public String toString() {
        return "ConstraintOfAddProject [id=" + getId() + ", department="
                + getDepartment().getDescription() + ", startTime=" + getStartTime().getTime() + ", endTime="
                + getEndTime().getTime() + "]";
    }


}
