package com.newview.bysj.service.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newview.bysj.domain.Degree;
import com.newview.bysj.domain.Department;
import com.newview.bysj.domain.Employee;
import com.newview.bysj.domain.ProTitle;
import com.newview.bysj.domain.Role;
import com.newview.bysj.domain.User;
import com.newview.bysj.domain.UserRole;
import com.newview.bysj.service.DegreeService;
import com.newview.bysj.service.DepartmentService;
import com.newview.bysj.service.EmployeeService;
import com.newview.bysj.service.ProTitleService;
import com.newview.bysj.service.RoleService;
import com.newview.bysj.service.UserRoleService;
import com.newview.bysj.service.UserService;

public class TestEmployeeService {
    EmployeeService employeeService;
    ProTitleService proTitleService;
    DegreeService degreeService;
    DepartmentService departmentService;
    RoleService roleService;
    UserRoleService userRoleService;
    UserService userService;

    @Before
    public void init() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        employeeService = (EmployeeService) ac.getBean("employeeService");
        proTitleService = (ProTitleService) ac.getBean("proTitleService");
        degreeService = (DegreeService) ac.getBean("degreeService");
        departmentService = (DepartmentService) ac.getBean("departmentService");
        roleService = (RoleService) ac.getBean("roleService");
        userRoleService = (UserRoleService) ac.getBean("userRoleService");
        userService = (UserService) ac.getBean("userService");
    }

    @Test
    public void save() {
        Employee employee = new Employee();
        employee.setDegree(degreeService.uniqueResult("description", "博士"));
        employee.setProTitle(proTitleService.uniqueResult("description", "副教授"));
        employeeService.save(employee);
    }

    @Test
    public void setSex() {
        Employee employee = employeeService.findById(131072);
        employee.setSex("女");
        employeeService.saveOrUpdate(employee);
    }

    @Test
    public void setTutor() {
        ProTitle proTitle = proTitleService.findById(1);
        Employee em = employeeService.findById(1);
        em.setProTitle(proTitle);
        employeeService.update(em);
    }

    @Test
    public void setDegree() {
        Degree degree = degreeService.findById(1);
        Employee em = employeeService.findById(1);
        em.setDegree(degree);
        employeeService.update(em);

    }

    @Test
    public void setDepartment() {
        Employee em = employeeService.findById(7);
        Department dm = departmentService.findById(1);
        em.setDepartment(dm);
        employeeService.update(em);
        employeeService.save(em);
    }

    //设置系统管理员
    @Test
    public void setAdmin() {
        UserRole userRole = new UserRole();
        userRole.setRole(roleService.findById(13));
        userRole.setUser(userService.findById(1));
        userRoleService.save(userRole);
    }
}
