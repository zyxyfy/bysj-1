package com.newview.bysj.service.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;

import com.newview.bysj.domain.Actor;
import com.newview.bysj.domain.Mail;
import com.newview.bysj.domain.MailAddresses;
import com.newview.bysj.other.PageCondition;
import com.newview.bysj.service.ActorService;
import com.newview.bysj.service.MailAddressesService;
import com.newview.bysj.service.MailService;

public class TestMailAddressesService {
    MailAddressesService mailAddressesService;
    ActorService actorService;
    MailService mailService;

    @Before
    public void init() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        mailAddressesService = (MailAddressesService) ac.getBean("mailAddressesService");
        actorService = (ActorService) ac.getBean("actorService");
        mailService = (MailService) ac.getBean("mailService");
    }

    @Test
    public void find() {
        Actor addresses = actorService.findById(1);
        PageCondition pageCondition = new PageCondition(1, 5);
        Page<MailAddresses> mailAddresses = mailAddressesService.getPageByAddresse(addresses, pageCondition);
        for (MailAddresses ma : mailAddresses) {
            System.out.println("=============" + ma.getMail().getId());
        }
    }

    @Test
    public void notReadNumber() {
        Actor addresses = actorService.findById(1);
        Integer num = mailAddressesService.getNotReadMailNumber(addresses);
        System.out.println("================" + num);
    }

    @Test
    public void setHasRead() {
        Actor addresses = actorService.findById(1);
        Mail mail = mailService.findById(1);
        mailAddressesService.setHasRead(mail, addresses);
        System.out.println("=================" + mailAddressesService.findById(1).getIsRead());
    }

}
