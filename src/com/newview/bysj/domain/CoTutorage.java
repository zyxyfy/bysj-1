package com.newview.bysj.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 合作指导
 */
@Entity
@DiscriminatorValue(value = "coTutorage")
@DynamicInsert(true)
@DynamicUpdate(true)
public class CoTutorage extends Tutorage {

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
     * 多对一
     *
     * @generated
     */
    @ManyToOne
    @JoinColumn(name = "graduateProjectForCoTutorage_id")
    private GraduateProject graduateProject;

    public CoTutorage() {
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
