package com.newview.bysj.service.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;

import com.newview.bysj.domain.VisitingEmployee;
import com.newview.bysj.service.DegreeService;
import com.newview.bysj.service.DepartmentService;
import com.newview.bysj.service.ProTitleService;
import com.newview.bysj.service.VisitingEmployeeService;

public class TestVisitingEmployeeService {
    VisitingEmployeeService visitingEmployeeService;
    DegreeService degreeService;
    DepartmentService departmentService;
    ProTitleService proTitleService;

    @Before
    public void init() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        visitingEmployeeService = (VisitingEmployeeService) ac.getBean("visitingEmployeeService");
        degreeService = (DegreeService) ac.getBean("degreeService");
        departmentService = (DepartmentService) ac.getBean("departmentService");
        proTitleService = (ProTitleService) ac.getBean("proTitleService");

    }

    @Test
    public void save() {
        VisitingEmployee v = new VisitingEmployee();
        visitingEmployeeService.save(v);
    }

    @Test
    public void saveEmployee() {
        VisitingEmployee vm = new VisitingEmployee();
        vm.setName("李伟");
        vm.setNo("200001");
        vm.setSex("男");
        vm.setDegree(degreeService.findById(1));
        vm.setProTitle(proTitleService.findById(1));
        vm.setDepartment(departmentService.findById(1));
        visitingEmployeeService.save(vm);
    }

    @Test
    public void update() {
        VisitingEmployee vm = visitingEmployeeService.findById(98304);
        vm.setName("王伟");
        vm.setNo("200002");
        vm.setSex("男");
        vm.setDegree(degreeService.findById(2));
        vm.setProTitle(proTitleService.findById(2));
        vm.setDepartment(departmentService.findById(1));
        visitingEmployeeService.update(vm);
    }

    @Test
    public void get() {
        Page<VisitingEmployee> result = visitingEmployeeService.getVisitingEmployees(null, "尹", 1, 10);
        for (VisitingEmployee vs : result) {
            System.out.println("no======" + vs.getNo());
        }
    }
}
