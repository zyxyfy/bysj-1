package com.newview.bysj.service.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newview.bysj.domain.Degree;
import com.newview.bysj.service.DegreeService;

public class TestDegreeService {
    DegreeService degreeService;

    @Before
    public void init() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        degreeService = (DegreeService) ac.getBean("degreeService");
    }

    @Test
    public void save() {
        Degree degree = new Degree();
        degreeService.save(degree);
    }

    @Test
    public void findBySchool() {

    }

}
