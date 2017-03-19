package com.newview.bysj.service.test;

import com.newview.bysj.domain.Tutor;
import com.newview.bysj.service.TutorService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;

public class TestTutorService {
    TutorService tutorService;

    @Before
    public void init() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        tutorService = (TutorService) ac.getBean("tutorService");
    }

    @Test
    public void getDirector() {
        Tutor tutor = tutorService.findById(1);
        Page<Tutor> tutors = tutorService.getDirectors(tutor);
        for (Tutor t : tutors) {
            System.out.println("tutorname===============" + t.getName());
        }
    }

    @Test
    public void getditector() {
        Tutor tutor = tutorService.findById(1);
        System.out.println(tutorService.getDirector(tutor).getName());
    }


}
