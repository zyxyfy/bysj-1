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
@Table(name = "tutorAsMemberToReplyGroup")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TutorAsMemberToReplyGroup implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 答辩小组人员
     * 多对一
     *
     * @generated
     */
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Tutor member;
    /**
     * 答辩小组
     * 多对一
     *
     * @generated
     */
    @ManyToOne
    @JoinColumn(name = "replyGroup_id")
    private ReplyGroup replyGroup;

    public TutorAsMemberToReplyGroup() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tutor getMember() {
        return member;
    }

    public void setMember(Tutor member) {
        this.member = member;
    }

    public ReplyGroup getReplyGroup() {
        return replyGroup;
    }

    public void setReplyGroup(ReplyGroup replyGroup) {
        this.replyGroup = replyGroup;
    }


}
