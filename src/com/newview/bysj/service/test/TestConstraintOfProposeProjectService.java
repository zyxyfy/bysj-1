package com.newview.bysj.service.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newview.bysj.domain.ConstraintOfProposeProject;
import com.newview.bysj.domain.Department;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.service.ConstraintOfProposeProjectService;
import com.newview.bysj.service.DepartmentService;
import com.newview.bysj.service.TutorService;

public class TestConstraintOfProposeProjectService {
    ConstraintOfProposeProjectService constraintOfProposeProjectService;
    DepartmentService departmentService;
    TutorService tutorService;

    @Before
    public void init() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        constraintOfProposeProjectService = (ConstraintOfProposeProjectService) ac.getBean("constraintOfProposeProjectService");
        tutorService = (TutorService) ac.getBean("tutorService");
        departmentService = (DepartmentService) ac.getBean("departmentService");
    }

    @Test
    public void save() {
        ConstraintOfProposeProject cp = new ConstraintOfProposeProject();
        constraintOfProposeProjectService.save(cp);
    }

    @Test
    public void setDepartment() {
        ConstraintOfProposeProject cp = constraintOfProposeProjectService.findById(1);
        Department department = departmentService.findById(1);
        cp.setDepartment(department);
        constraintOfProposeProjectService.update(cp);
    }

    @Test
    public void isAbleToUpdateProject() {
        Tutor tutor = tutorService.findById(3);
        System.out.println("****************" + tutor.getName());
        Department department = tutor.getDepartment();
        System.out.println("&&&&&&&&&&" + department.getId());
        ConstraintOfProposeProject constraintOfProposeProject = constraintOfProposeProjectService.uniqueResult("department", Department.class, tutor.getDepartment());
        System.out.println("=============" + constraintOfProposeProject.getId());

    }

    @Test
    public void get() {
        Tutor tutor = tutorService.findById(1);
        //	ConstraintOfProposeProject cp=
    }

}
