package com.newview.bysj.service.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newview.bysj.domain.Actor;
import com.newview.bysj.domain.Department;
import com.newview.bysj.domain.Mail;
import com.newview.bysj.service.ActorService;
import com.newview.bysj.service.DepartmentService;
import com.newview.bysj.service.MailService;

public class TestActorService {
    ActorService actorService;
    MailService mailService;

    @Before
    public void init() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        actorService = (ActorService) ac.getBean("actorService");
        mailService = (MailService) ac.getBean("mailService");
    }

    @Test
    public void setMail() {
        Actor addresses = actorService.findById(1);
        List<Mail> mails = new ArrayList<Mail>();
        mails.add(mailService.findById(1));
        mails.add(mailService.findById(2));
        addresses.setReceiveMail(mails);
        actorService.update(addresses);
    }

}
