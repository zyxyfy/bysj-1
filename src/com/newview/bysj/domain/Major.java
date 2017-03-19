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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 专业
 */
@Entity
@Table(name = "major")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Major implements Serializable {

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
    private String no;
    /**
     * @generated
     */
    private String description;
    /**
     * 教研室
     * 多对一
     *
     * @generated
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    /**
     * 一对多
     *
     * @generated
     */
    @JsonIgnore
    @OneToMany(mappedBy = "major")
    private List<GraduateProject> graduateProject;
    /**
     * 学生班级
     * 一对多
     *
     * @generated
     */
    @JsonIgnore
    @OneToMany(mappedBy = "major", cascade = CascadeType.ALL)
    private List<StudentClass> studentClass;
    /**
     * 期限
     * 一对一
     *
     * @generated
     */
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "validity_id")
    private Validity validity;

    public Major() {
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
    public Department getDepartment() {
        return department;
    }

    @JsonBackReference
    public void setDepartment(Department department) {
        this.department = department;
    }

    @JsonBackReference
    public List<GraduateProject> getGraduateProject() {
        return graduateProject;
    }

    public void setGraduateProject(List<GraduateProject> graduateProject) {
        this.graduateProject = graduateProject;
    }

    public Validity getValidity() {
        return validity;
    }

    public void setValidity(Validity validity) {
        this.validity = validity;
    }

    public List<StudentClass> getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(List<StudentClass> studentClass) {
        this.studentClass = studentClass;
    }


}
