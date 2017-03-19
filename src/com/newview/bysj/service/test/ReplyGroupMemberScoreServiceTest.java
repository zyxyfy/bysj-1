package com.newview.bysj.service.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newview.bysj.domain.ReplyGroupMemberScore;
import com.newview.bysj.service.ReplyGroupMemberScoreService;

public class ReplyGroupMemberScoreServiceTest {

    ReplyGroupMemberScoreService replyGroupMemberScoreService;

    @Before
    public void init() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        replyGroupMemberScoreService = (ReplyGroupMemberScoreService) ac.getBean("replyGroupMemberScoreService");
    }

    @Test
    public void save() {
        for (int i = 1; i < 1000; i++) {
            ReplyGroupMemberScore replyGroupMemberScores = new ReplyGroupMemberScore();
            replyGroupMemberScores.setIn_dex(50 + i);
            replyGroupMemberScoreService.save(replyGroupMemberScores);
        }
    }

    @Test
    public void index() {
        System.out.println("---------");
        long begin = System.currentTimeMillis();

        Integer index = replyGroupMemberScoreService.findIn_dex();
        long end = System.currentTimeMillis();
        System.out.println("index===" + index + "time=========" + (end - begin));

    }


}
