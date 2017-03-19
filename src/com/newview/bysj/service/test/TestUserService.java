package com.newview.bysj.service.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newview.bysj.domain.Student;
import com.newview.bysj.domain.User;
import com.newview.bysj.service.StudentService;
import com.newview.bysj.service.UserService;

public class TestUserService {
    UserService userService;
    StudentService studentService;

    @Before
    public void init() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        userService = (UserService) ac.getBean("userService");
        studentService = (StudentService) ac.getBean("studentService");
    }

    @Test
    public void save() {
        //user级联保存actor
        Student student = new Student("201302", "杨一");
        User u1 = new User("201302", "201302");
        u1.setActor(student);
        student.setUser(u1);
        userService.save(u1);
    }

    @Test
    public void testUniqueResoult() {
        User user = userService.uniqueResult("username", "020081");
        System.out.println(user.getId());
    }

}
