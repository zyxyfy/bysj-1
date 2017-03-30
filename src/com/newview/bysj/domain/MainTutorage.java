package com.newview.bysj.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;


/**
 * 主指导
 */
@Entity
@DiscriminatorValue(value = "mainTutorage")
@DynamicInsert(true)
@DynamicUpdate(true)
// TODO: 2017/3/26  转移到graduateproject中，增加一个tutor
public class MainTutorage extends Tutorage {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 导师
     * 多对一
     *
     * @generated
     */
    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;
    /**
     * 毕业论文
     * 一对一
     *
     * @generated
     */
    @OneToOne
    @JoinColumn(name = "graduateProjectForMail_id")
    private GraduateProject graduateProject;

    public MainTutorage() {
        super();
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public GraduateProject getGraduateProject() {
        return graduateProject;
    }

    public void setGraduateProject(GraduateProject graduateProject) {
        this.graduateProject = graduateProject;
    }


}
