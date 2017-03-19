package com.newview.bysj.service.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newview.bysj.domain.Role;
import com.newview.bysj.domain.RoleResource;
import com.newview.bysj.service.RoleResourceService;
import com.newview.bysj.service.RoleService;

public class TestRoleResourceService {
    RoleResourceService roleResourceService;
    RoleService roleService;

    @Before
    public void inint() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        roleResourceService = (RoleResourceService) ac.getBean("roleResourceService");
        roleService = (RoleService) ac.getBean("roleService");
    }

    @Test
    public void findRoleResource() {
        Role role = roleService.findById(6);
        List<RoleResource> rr = roleResourceService.list("role", Role.class, role);
        for (RoleResource roleResource : rr) {
            System.out.println(roleResource.getResource().getDescription());
        }
    }


}
