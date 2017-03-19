package com.newview.bysj.service.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;

import com.newview.bysj.domain.Department;
import com.newview.bysj.domain.ReplyGroup;
import com.newview.bysj.service.DepartmentService;
import com.newview.bysj.service.ReplyGroupService;

public class TestReplyGroupService {
    ReplyGroupService replyGroupService;
    DepartmentService departmentService;

    @Before
    public void init() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        replyGroupService = (ReplyGroupService) ac.getBean("replyGroupService");
        departmentService = (DepartmentService) ac.getBean("departmentService");
    }

    @Test
    public void getGroup() {
        Department department = departmentService.findById(1);
        Page<ReplyGroup> replyGroups = replyGroupService.getReplyGroupByDepartment(department, null, 1, 2);
        System.out.println("========" + replyGroups);
    }

}
