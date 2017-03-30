package com.newview.bysj.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * 工作进程表 
 */
// TODO: 2017/3/26    并发问题 ，同时点击！！！！！！ 
@Entity
@Table(name = "schedule")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Schedule implements Serializable {

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
     * 开始时间
     *
     * @generated
     */
    private Calendar beginTime;
    /**
     * 结束时间
     *
     * @generated
     */
    private Calendar endTime;
    /**
     * 审核
     *
     * @generated
     */
    @OneToOne(cascade = CascadeType.ALL)
    private Audit audit;
    /**
     * 多对一
     *
     * @generated
     */
    @ManyToOne
    @JoinColumn(name = "graduateProject_id")
    private GraduateProject graduateProject;

    public Schedule() {
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

    public Calendar getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Calendar beginTime) {
        this.beginTime = beginTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public Audit getAudit() {
        return audit;
    }

    public void setAudit(Audit audit) {
        this.audit = audit;
    }

    public GraduateProject getGraduateProject() {
        return graduateProject;
    }

    public void setGraduateProject(GraduateProject graduateProject) {
        this.graduateProject = graduateProject;
    }


}
