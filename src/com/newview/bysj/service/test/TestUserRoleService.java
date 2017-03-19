package com.newview.bysj.service.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newview.bysj.domain.Role;
import com.newview.bysj.domain.User;
import com.newview.bysj.domain.UserRole;
import com.newview.bysj.service.RoleService;
import com.newview.bysj.service.UserRoleService;
import com.newview.bysj.service.UserService;

public class TestUserRoleService {
    UserService userService;
    UserRoleService userRoleService;
    RoleService roleService;

    @Before
    public void init() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        userService = (UserService) ac.getBean("userService");
        userRoleService = (UserRoleService) ac.getBean("userRoleService");
        roleService = (RoleService) ac.getBean("roleService");
    }

    @Test
    public void test() {
        UserRole userRole = new UserRole();
        userRole.setRole(roleService.findById(1));
        userRole.setUser(userService.findById(1));
        userRoleService.save(userRole);

    }

    @Test
    public void insertRole() {
        User user = userService.findById(16);
        Role role = roleService.findById(1);
        userRoleService.insertRole(user, role);
    }

    @Test
    public void get() {
        User user = userService.findById(1);
        List<UserRole> userRoles = userRoleService.list("user", User.class, user);
        for (UserRole userRole : userRoles) {
            System.out.println(userRole.getRole().getRoleName());
        }
    }

}
