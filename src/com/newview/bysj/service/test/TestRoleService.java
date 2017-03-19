package com.newview.bysj.service.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;

import com.newview.bysj.domain.Employee;
import com.newview.bysj.domain.Role;
import com.newview.bysj.domain.UserRole;
import com.newview.bysj.service.EmployeeService;
import com.newview.bysj.service.RoleResourceService;
import com.newview.bysj.service.RoleService;
import com.newview.bysj.service.UserService;

public class TestRoleService {
    RoleService roleService;
    RoleResourceService roleResourceService;
    UserService userService;
    EmployeeService employeeService;

    @Before
    public void init() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        roleService = (RoleService) ac.getBean("roleService");
        roleResourceService = (RoleResourceService) ac.getBean("roleResourceService");
        userService = (UserService) ac.getBean("userService");
        employeeService = (EmployeeService) ac.getBean("employeeService");
    }

    @Test
    public void save() {
        Role role1 = new Role("学生", "STUDENT");
        roleService.save(role1);
        Role r2 = new Role("导师", "TUTOR");
        roleService.save(r2);
    }

    @Test
    public void num() {
        System.out.println(roleService.findById(1).getDescription());
        System.out.println("num======================");
        System.out.println(roleService.countAll(Role.class));
    }

    @Test
    public void unique() {
        System.out.println(roleService.uniqueResult("id", 1).getDescription());
    }

    @Test
    public void pageQuery() {
        Page<Role> role = roleService.pageQuery(0, roleService.countAll(Role.class));
        for (Role r : role) {
            System.out.println(r.getDescription());
        }
    }

    @Test
    public void test() {
        Employee admin = employeeService.findById(1);
        List<Role> roles = new ArrayList<Role>();
        for (UserRole userRole : admin.getUser().getUserRole()) {
            if (userRole.getRole().getRoleName() == "ROLE_COLLEGE_ADMIN") {
                roles.addAll(userRole.getRole().getRoleHandleds());
                continue;
            } else if (userRole.getRole().getRoleName() == "ROLE_SCHOOL_ADMIN") {
                roles.addAll(userRole.getRole().getRoleHandleds());
                continue;
            } else if (userRole.getRole().getRoleName() == "ROLE_DEPARTMENT_ADMIN") {
                roles.addAll(userRole.getRole().getRoleHandleds());
                continue;
            }
        }
        for (Role role : roles) {
            System.out.print("roles:" + role.getDescription());
        }
        Employee employee = employeeService.findById(1);
        List<UserRole> userRoles = employee.getUser().getUserRole();
        List<Role> ownRoles = new ArrayList<Role>();
        for (UserRole userRole : userRoles) {
            for (Role role : roles) {
                if (userRole.getRole().getRoleHandler() != null && userRole.getRole().equals(role)) {
                    ownRoles.add(userRole.getRole());
                }
            }
        }
        System.out.println("ownRoles==========" + ownRoles);
    }

    @Test
    public void getRoleHandles() {
        Role role = roleService.findById(1);
        List<Role> roles = role.getRoleHandleds();
        System.out.println("role==" + roles);
    }
}
