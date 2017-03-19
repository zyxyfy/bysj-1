package com.newview.bysj.domain;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 校内老师
 */
@Entity
@Table(name = "employee")
//子表中保存所有属性，包括父类的属性
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DynamicInsert(true)
@DynamicUpdate(true)
public class Employee extends Tutor {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public Employee() {
        super();
    }


}
