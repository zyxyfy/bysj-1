package com.newview.bysj.service;

import java.sql.ResultSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestReplyGroupMemberScore {

    @Autowired
    ReplyGroupMemberScoreService replyGroupMemberScoreService;

    @Before
    public void init() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        replyGroupMemberScoreService = (ReplyGroupMemberScoreService) ac.getBean("replyGroupMemberScoreService");
    }

    @Test
    public void getResourceByRoleName() throws Exception {
        ResultSet results = replyGroupMemberScoreService.getAllValuas();
        int i = results.findColumn("max(in_dex)");


        System.out.println(results.getInt(0) + "=-====");
    }
}
