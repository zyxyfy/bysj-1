package com.newview.bysj.service.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;

import com.newview.bysj.domain.School;
import com.newview.bysj.service.SchoolService;

public class TestSchoolService {

    SchoolService schoolService;

    @Before
    public void init() {
        @SuppressWarnings("resource")
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        schoolService = (SchoolService) ac.getBean("schoolService");
    }

    @Test
    public void queryTest() {
        Page<School> schools = schoolService.pageQuery(1, 10, "土木");
        for (School school : schools) {
            System.out.println("descirption========" + school.getDescription());
        }
    }

    @Test
    public void find() {
        List<Object[]> result = schoolService.findByProperty(School.class, "description");
        List<String> list = new ArrayList<>();
        //List<String[]> ins = (List<String[]>)result;
        for (int i = 0; i < result.size(); i++) {
            Object[] obj = result.get(i);
            System.out.println(result.get(i));
            System.out.println("-------");
        }


        //Object[] ex = result.get(1);
        //System.out.println(res.);

    }
}
