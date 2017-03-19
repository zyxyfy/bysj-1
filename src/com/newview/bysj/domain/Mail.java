package com.newview.bysj.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 通知
 */
@Entity
@Table(name = "mail")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Mail implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 发布时间
     *
     * @generated
     */
    private Calendar addressTime;
    /**
     * 发布内容
     *
     * @generated
     */
    private String Content;
    /**
     * 标题
     *
     * @generated
     */
    private String title;
    /**
     * 版本
     *
     * @generated
     */
    @Version
    @Column(name = "version")
    private Integer version;
    /**
     * 发布者
     * 多对一
     *
     * @generated
     */
    @ManyToOne
    @JoinColumn(name = "addressor_id")
    private Actor addressor;
    /**
     * 接收者
     * 多对多
     *
     * @generated
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "mailAddresses", joinColumns = {@JoinColumn(name = "mail_id")},
            inverseJoinColumns = {@JoinColumn(name = "addressee_id")})
    private List<Actor> addresses;
    /**
     * 回复信息，自关联
     * 通过自关联实现私人邮件或者群发功能
     *
     * @generated
     */
    @OneToMany(mappedBy = "parentMail")
    private List<Mail> replyMail;
    /**
     * 发布的信息，自关联
     *
     * @generated
     */
    @ManyToOne
    @JoinColumn(name = "parentMail_id")
    private Mail parentMail;
    /**
     * 附件
     * 一对一
     *
     * @generated
     */
    @OneToOne
    private Attachment attachment;

    public Mail() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Calendar getAddressTime() {
        return addressTime;
    }

    public void setAddressTime(Calendar addressTime) {
        this.addressTime = addressTime;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Actor getAddressor() {
        return addressor;
    }

    public void setAddressor(Actor addressor) {
        this.addressor = addressor;
    }

    public List<Actor> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Actor> addresses) {
        this.addresses = addresses;
    }


    public List<Mail> getReplyMail() {
        return replyMail;
    }

    public void setReplyMail(List<Mail> replyMail) {
        this.replyMail = replyMail;
    }

    public Mail getParentMail() {
        return parentMail;
    }

    public void setParentMail(Mail parentMail) {
        this.parentMail = parentMail;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }


}
