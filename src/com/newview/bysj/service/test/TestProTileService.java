package com.newview.bysj.service.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newview.bysj.domain.ProTitle;
import com.newview.bysj.service.ProTitleService;

public class TestProTileService {
    ProTitleService proTitleService;

    @Before
    public void init() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        proTitleService = (ProTitleService) ac.getBean("proTitleService");
    }

    @Test
    public void save() {
        ProTitle pr = new ProTitle();
        proTitleService.save(pr);
    }

}
