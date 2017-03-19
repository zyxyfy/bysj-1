package com.newview.bysj.service.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newview.bysj.domain.Major;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.service.DepartmentService;
import com.newview.bysj.service.MajorService;
import com.newview.bysj.service.TutorService;

public class TestMajorService {
    MajorService majorService;
    DepartmentService departmentService;
    TutorService tutorService;

    @Before
    public void init() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        majorService = (MajorService) ac.getBean("majorService");
        departmentService = (DepartmentService) ac.getBean("departmentService");
        tutorService = (TutorService) ac.getBean("tutorService");
    }

    @Test
    public void save() {
        Major m1 = new Major();
        m1.setDescription("信息管理");
        m1.setDepartment(departmentService.findById(1));
        majorService.save(m1);
        Major m2 = new Major();
        m2.setDescription("工程造价");
        m2.setDepartment(departmentService.findById(2));
        majorService.save(m2);
        Major m3 = new Major();
        m3.setDescription("土木工程");
        m3.setDepartment(departmentService.findById(3));
        majorService.save(m3);

    }

    @Test
    public void getTutorDefaultMajor() {
        Tutor tutor = tutorService.findById(1);
        Major major = majorService.getTutorDefaultMajor(tutor);
        System.out.println("=============" + major.getDescription());
    }

}
