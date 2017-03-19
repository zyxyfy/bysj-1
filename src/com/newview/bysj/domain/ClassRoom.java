package com.newview.bysj.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 答辩地点，如博文101
 */
@Entity
@Table(name = "classRoom")
@DynamicInsert(true)
@DynamicUpdate(true)
public class ClassRoom implements Serializable {

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
    private String description;
    /**
     * 答辩小组
     * 一对多
     *
     * @generated
     */
    @Transient
    @JsonIgnore
    private List<ReplyGroup> replyGroup;
    /**
     * 指导记录
     * 一对多
     *
     * @generated
     */
    @OneToMany(mappedBy = "classRoom", cascade = CascadeType.ALL)
    private List<GuideRecord> guideRecord;

    public ClassRoom() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ReplyGroup> getReplyGroup() {
        return replyGroup;
    }

    public void setReplyGroup(List<ReplyGroup> replyGroup) {
        this.replyGroup = replyGroup;
    }

    public List<GuideRecord> getGuideRecord() {
        return guideRecord;
    }

    public void setGuideRecord(List<GuideRecord> guideRecord) {
        this.guideRecord = guideRecord;
    }


}
