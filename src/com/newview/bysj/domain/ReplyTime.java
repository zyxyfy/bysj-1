package com.newview.bysj.domain;

import java.util.Calendar;

import javax.persistence.Embeddable;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Embeddable
@DynamicInsert(true)
@DynamicUpdate(true)
public class ReplyTime {
    /**
     * @generated
     */
    private Calendar beginTime;
    /**
     * @generated
     */
    private Calendar EndTime;


    public ReplyTime() {
        super();
    }

    public ReplyTime(Calendar beginTime, Calendar endTime) {
        this.beginTime = beginTime;
        EndTime = endTime;
    }

    public Calendar getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Calendar beginTime) {
        this.beginTime = beginTime;
    }

    public Calendar getEndTime() {
        return EndTime;
    }

    public void setEndTime(Calendar endTime) {
        EndTime = endTime;
    }


}
