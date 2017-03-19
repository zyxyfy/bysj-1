package com.newview.bysj.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 督导报告
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "superviseReportRank")
@DynamicInsert(true)
@DynamicUpdate(true)
public class SupervisionReport implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /*
     *
     */
    private String url;
    /*
     *
     */
    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor issuer;
    /*
     *
     */
    private Date calendar;

    public SupervisionReport() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Tutor getIssuer() {
        return issuer;
    }

    public void setIssuer(Tutor issuer) {
        this.issuer = issuer;
    }

    public Date getCalendar() {
        return calendar;
    }

    public void setCalendar(Date calendar) {
        this.calendar = calendar;
    }


}
