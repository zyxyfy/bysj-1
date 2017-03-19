package com.newview.bysj.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 阶段成果
 */
@Entity
@DiscriminatorValue(value = "stageAchievement")
@DynamicInsert(true)
@DynamicUpdate(true)
public class StageAchievement extends Attachment {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 教师评语
     *
     * @generated
     */
    private String remark;
    /**
     * 多对一
     *
     * @generated
     */
    @ManyToOne
    @JoinColumn(name = "graduateProject_id")
    private GraduateProject graduateProject;
    /**
     * 用于重新获取stageAchievement
     */
    private Integer num;

    public StageAchievement() {
        super();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public GraduateProject getGraduateProject() {
        return graduateProject;
    }

    public void setGraduateProject(GraduateProject graduateProject) {
        this.graduateProject = graduateProject;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

}
