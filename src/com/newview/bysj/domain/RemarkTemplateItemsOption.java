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
@Table(name = "remarkTemplateItemsOption")
@DynamicInsert(true)
@DynamicUpdate(true)
public class RemarkTemplateItemsOption implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 选项，如“很好”，“一般”，“较差”
     *
     * @generated
     */
    private String itemOption;
    /**
     * 选项编号，排序用
     *
     * @generated
     */
    private Integer no;
    /**
     * 评语项（文本）
     * 多对一
     *
     * @generated
     */
    @ManyToOne
    @JoinColumn(name = "remarkTemplateItems_id")
    private RemarkTemplateItems remarkTemplateItems;

    public RemarkTemplateItemsOption() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemOption() {
        return itemOption;
    }

    public void setItemOption(String itemOption) {
        this.itemOption = itemOption;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public RemarkTemplateItems getRemarkTemplateItems() {
        return remarkTemplateItems;
    }

    public void setRemarkTemplateItems(RemarkTemplateItems remarkTemplateItems) {
        this.remarkTemplateItems = remarkTemplateItems;
    }


}
