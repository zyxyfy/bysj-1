package com.newview.bysj.service.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newview.bysj.domain.Actor;
import com.newview.bysj.domain.Mail;
import com.newview.bysj.service.ActorService;
import com.newview.bysj.service.MailService;
import com.newview.bysj.service.StudentService;
import com.newview.bysj.service.TutorService;
import com.newview.bysj.service.UserService;

public class TestMailService {
    MailService mailService;
    ActorService actorService;
    UserService userService;
    TutorService tutorService;
    StudentService studentService;

    @SuppressWarnings("resource")
    @Before
    public void init() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        mailService = (MailService) ac.getBean("mailService");
        studentService = (StudentService) ac.getBean("studentService");
        actorService = (ActorService) ac.getBean("actorService");
    }

    @Test
    public void save() {
        List<Actor> addresses = new ArrayList<Actor>();
        addresses.add(actorService.findById(3));
        addresses.add(actorService.findById(4));
        Mail mail = new Mail();
        mail.setAddresses(addresses);
        mailService.save(mail);
    }
}
