package com.newview.bysj.domain;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "opinion")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Opinion implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * @generated
     */
    private String content;
    /**
     * @generated
     */
    private String url;
    /**
     * 发布时间
     *
     * @generated
     */
    private Calendar issueDate;
    /**
     * 发布者
     *
     * @generated
     */
    @ManyToOne
    @JoinColumn(name = "issuer_id")
    private Tutor issuer;

    public Opinion() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Calendar getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Calendar issueDate) {
        this.issueDate = issueDate;
    }

    public Tutor getIssuer() {
        return issuer;
    }

    public void setIssuer(Tutor issuer) {
        this.issuer = issuer;
    }


}
