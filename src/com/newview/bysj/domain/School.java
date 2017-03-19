package com.newview.bysj.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "school")
@DynamicInsert(true)
@DynamicUpdate(true)
public class School implements Serializable {

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
     * 院长
     * 一对一
     *
     * @generated
     */
    @OneToOne
    @JoinColumn(name = "chairman_id")
    private Tutor chairman;
    /**
     * 答辩委员会主任
     * 一对一
     *
     * @generated
     */
    @OneToOne
    @JoinColumn(name = "dean_id")
    private Tutor dean;
    /**
     * 教研室
     * 一对多
     *
     * @generated
     */
    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    private List<Department> department;

    public School() {
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

    public Tutor getChairman() {
        return chairman;
    }

    public void setChairman(Tutor chairman) {
        this.chairman = chairman;
    }

    public Tutor getDean() {
        return dean;
    }

    public void setDean(Tutor dean) {
        this.dean = dean;
    }

    @JsonBackReference
    public List<Department> getDepartment() {
        return department;
    }

    public void setDepartment(List<Department> department) {
        this.department = department;
    }


}
