package com.newview.bysj.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 评语项
 */
@Entity
@Table(name = "remarkTemplateItems")
@DynamicInsert(true)
@DynamicUpdate(true)
public class RemarkTemplateItems implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 评语序号,用来设置各评语项
     *
     * @generated
     */
    private Integer itemIndex;
    /**
     * 评语项前面的文本
     *
     * @generated
     */
    private String preText;
    /**
     * 评语项后面的文本
     *
     * @generated
     */
    private String postText;
    /**
     * 评语模板
     * 多对一
     *
     * @generated
     */
    @ManyToOne
    @JoinColumn(name = "remarkTemplate_id")
    private RemarkTemplate remarkTemplate;
    /**
     * 评语项中的可选项，可以只有文本，没有选项
     * 一对多
     *
     * @generated
     */
    @OneToMany(mappedBy = "remarkTemplateItems", cascade = CascadeType.ALL)
    private List<RemarkTemplateItemsOption> remarkTemplateItemsOption;

    public RemarkTemplateItems() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemIndex() {
        return itemIndex;
    }

    public void setItemIndex(Integer itemIndex) {
        this.itemIndex = itemIndex;
    }

    public String getPreText() {
        return preText;
    }

    public void setPreText(String preText) {
        this.preText = preText;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public RemarkTemplate getRemarkTemplate() {
        return remarkTemplate;
    }

    public void setRemarkTemplate(RemarkTemplate remarkTemplate) {
        this.remarkTemplate = remarkTemplate;
    }

    public List<RemarkTemplateItemsOption> getRemarkTemplateItemsOption() {
        return remarkTemplateItemsOption;
    }

    public void setRemarkTemplateItemsOption(
            List<RemarkTemplateItemsOption> remarkTemplateItemsOption) {
        this.remarkTemplateItemsOption = remarkTemplateItemsOption;
    }


}
