package com.newview.bysj.service.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newview.bysj.domain.ProjectType;
import com.newview.bysj.service.ProjectTypeService;

public class TestProjectTypeService {
    ProjectTypeService projectTypeService;

    @Before
    public void init() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        projectTypeService = (ProjectTypeService) ac.getBean("projectTypeService");

    }

    @Test
    public void save() {
        ProjectType pro = new ProjectType();
        projectTypeService.save(pro);
    }

}
