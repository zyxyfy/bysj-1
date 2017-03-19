package com.newview.bysj.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 职称
 */
@Entity
@Table(name = "proTitle")
@DynamicInsert(true)
@DynamicUpdate(true)
public class ProTitle implements Serializable {

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
    @Column(length = 20)
    private String description;
    /**
     * @generated
     */
    private String no;
    /**
     * 导师
     * 一对多
     *
     * @generated
     */
    @JsonIgnore
    @OneToMany(mappedBy = "proTitle")
    private List<Tutor> tutor;

    public ProTitle() {
        super();
    }

    public ProTitle(String description) {
        this.description = description;
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

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public List<Tutor> getTutor() {
        return tutor;
    }

    public void setTutor(List<Tutor> tutor) {
        this.tutor = tutor;
    }


}
