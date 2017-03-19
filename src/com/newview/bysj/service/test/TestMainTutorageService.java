package com.newview.bysj.service.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newview.bysj.domain.Employee;
import com.newview.bysj.domain.MainTutorage;
import com.newview.bysj.service.EmployeeService;
import com.newview.bysj.service.MainTutorageService;
import com.newview.bysj.service.VisitingEmployeeService;

public class TestMainTutorageService {
    MainTutorageService mainTutorageService;
    EmployeeService employeeService;

    @Before
    public void init() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        mainTutorageService = (MainTutorageService) ac.getBean("mainTutorageService");
        employeeService = (EmployeeService) ac.getBean("employeeService");


    }

    @Test
    public void save() {
        //maintutorage只能关联em和vm中的一个
        MainTutorage m = new MainTutorage();
        Employee em = employeeService.findById(1);
        m.setTutor(em);
        mainTutorageService.save(m);
    }

    @Test
    public void setTutorage() {
        MainTutorage m = mainTutorageService.findById(1);
        m.setTutor(employeeService.findById(1));
        mainTutorageService.update(m);
    }

}
