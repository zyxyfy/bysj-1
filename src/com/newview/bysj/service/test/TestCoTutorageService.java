package com.newview.bysj.service.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newview.bysj.domain.CoTutorage;
import com.newview.bysj.domain.VisitingEmployee;
import com.newview.bysj.service.CoTutorageService;
import com.newview.bysj.service.VisitingEmployeeService;

public class TestCoTutorageService {
    CoTutorageService coTutorageService;
    VisitingEmployeeService visitingEmployeeService;

    @Before
    public void init() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        visitingEmployeeService = (VisitingEmployeeService) ac.getBean("visitingEmployeeService");
        coTutorageService = (CoTutorageService) ac.getBean("coTutorageService");
    }

    @Test
    public void save() {
        CoTutorage co = new CoTutorage();
        VisitingEmployee vm = visitingEmployeeService.findById(98304);
        co.setTutor(vm);
        coTutorageService.save(co);
    }
}
