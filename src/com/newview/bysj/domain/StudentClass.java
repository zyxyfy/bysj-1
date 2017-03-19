package com.newview.bysj.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 班级
 */
@Entity
@Table(name = "studentClass")
@DynamicInsert(true)
@DynamicUpdate(true)
public class StudentClass implements Serializable {

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
    @JsonIgnore
    private String no;
    /**
     * @generated
     */
    private String description;
    /**
     * 专业
     * 多对一
     *
     * @generated
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major;
    /**
     * 学生
     * 一对多
     *
     * @generated
     */
    @JsonIgnore
    @OneToMany(mappedBy = "studentClass")
    private List<Student> student;

    public StudentClass() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonBackReference
    public Major getMajor() {
        return major;
    }

    @JsonBackReference
    public void setMajor(Major major) {
        this.major = major;
    }

    @JsonBackReference
    public List<Student> getStudent() {
        return student;
    }

    public void setStudent(List<Student> student) {
        this.student = student;
    }

}
