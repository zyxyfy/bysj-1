package com.newview.bysj.service.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newview.bysj.service.ResourceService;

public class TestReourceService {
    ResourceService resourceService;

    @Before
    public void init() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        resourceService = (ResourceService) ac.getBean("resourceService");
    }

    @Test
    public void getResourceByRoleName() {
        List<String> results = resourceService.getResourceByRoleName("ROLE_COLLEGE_ADMIN");
        for (String result : results) {
            System.out.println("====" + result);
        }
    }

}
