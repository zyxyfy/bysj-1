package com.newview.bysj.service.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newview.bysj.domain.ConstraintOfProposeProject;
import com.newview.bysj.domain.Department;
import com.newview.bysj.domain.School;
import com.newview.bysj.service.ConstraintOfProposeProjectService;
import com.newview.bysj.service.DepartmentService;
import com.newview.bysj.service.SchoolService;

public class TestDepartmentService {
    DepartmentService departmentService;
    SchoolService schoolService;
    ConstraintOfProposeProjectService constraintOfProposeProjectService;

    @Before
    public void init() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        departmentService = (DepartmentService) ac.getBean("departmentService");
        schoolService = (SchoolService) ac.getBean("schoolService");
        constraintOfProposeProjectService = (ConstraintOfProposeProjectService) ac.getBean("constraintOfProposeProjectService");
    }

    @Test
    public void save() {
        School school = schoolService.findById(2);
        Department dm1 = new Department();
        dm1.setDescription("信管教研室");
        dm1.setSchool(school);
        departmentService.save(dm1);
        Department dm2 = new Department();
        dm2.setDescription("造价教研室");
        dm2.setSchool(school);
        departmentService.save(dm2);
        School school1 = schoolService.findById(1);
        Department dm3 = new Department();
        dm3.setDescription("土木教研室");
        dm3.setSchool(school1);
        departmentService.save(dm3);
    }

    @Test
    public void setConstraint() {
        ConstraintOfProposeProject cp = constraintOfProposeProjectService.findById(1);
        Department dm = departmentService.findById(1);
        dm.setConstraintOfProposeProject(cp);
        departmentService.update(dm);
    }

}
