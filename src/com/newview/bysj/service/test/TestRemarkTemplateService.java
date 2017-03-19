package com.newview.bysj.service.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newview.bysj.domain.RemarkTemplate;
import com.newview.bysj.service.RemarkTemplateService;

public class TestRemarkTemplateService {

    RemarkTemplateService remarkTemplateService;

    @Before
    public void init() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        remarkTemplateService = (RemarkTemplateService) ac.getBean("remarkTemplateService");
    }

    @Test
    public void save() {
        RemarkTemplate rm = new RemarkTemplate();
        remarkTemplateService.save(rm);
    }
}
