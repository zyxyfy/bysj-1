package com.newview.bysj.domain;

import java.io.Serializable;

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
@Table(name = "mailAddresses")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MailAddresses implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 是否已阅读
     *
     * @generated
     */
    private Boolean isRead;
    /**
     * 通知
     * 多对一
     *
     * @generated
     */
    @ManyToOne
    @JoinColumn(name = "mail_id")
    private Mail mail;
    /**
     * 接收者
     * 多对一
     *
     * @generated
     */
    @ManyToOne
    @JoinColumn(name = "addressee_id")
    private Actor actor;

    public MailAddresses() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }


}
