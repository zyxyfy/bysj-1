package com.newview.bysj.service.test;

import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;

import com.newview.bysj.domain.Department;
import com.newview.bysj.domain.Employee;
import com.newview.bysj.domain.Major;
import com.newview.bysj.domain.Student;
import com.newview.bysj.domain.StudentClass;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.domain.User;
import com.newview.bysj.service.DepartmentService;
import com.newview.bysj.service.EmployeeService;
import com.newview.bysj.service.MajorService;
import com.newview.bysj.service.StudentClassService;
import com.newview.bysj.service.StudentService;
import com.newview.bysj.service.TutorService;
import com.newview.bysj.service.UserService;
import com.newview.bysj.service.VisitingEmployeeService;

public class TestStudentService {
    StudentService studentService;
    UserService userService;
    EmployeeService employeeService;
    StudentClassService studentClassService;
    VisitingEmployeeService visitingEmployeeService;
    TutorService tutorService;
    MajorService majorService;
    DepartmentService departmentService;

    @Before
    public void init() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        studentService = (StudentService) ac.getBean("studentService");
        userService = (UserService) ac.getBean("userService");
        employeeService = (EmployeeService) ac.getBean("employeeService");
        studentClassService = (StudentClassService) ac.getBean("studentClassService");
        visitingEmployeeService = (VisitingEmployeeService) ac.getBean("visitingEmployeeService");
        tutorService = (TutorService) ac.getBean("tutorService");
        majorService = (MajorService) ac.getBean("majorService");
        departmentService = (DepartmentService) ac.getBean("departmentService");
    }

    @Test
    public void save() {
        Student s1 = new Student("1", "123");
        studentService.save(s1);
        Student s2 = new Student("2", "345");
        studentService.save(s2);
    }

    @Test
    public void setUser() {
        Student student = studentService.findById(1);
        //需要先判断获取的student是否为空
        System.out.println(student.getName());
        User user = userService.findById(1);
        System.out.println(user.getPassword());
        student.setUser(user);
        studentService.update(student);
    }

    @Test
    public void setTutor() {
        /*Student student=studentService.findById(1);
		
		student.setTutor(em);
		studentService.update(student);*/
        Employee em = employeeService.findById(5);
        System.out.println("=======" + em.getName());
        Student student1 = studentService.findById(22);
        System.out.println("*********" + student1.getName());
        student1.setTutor(em);
        studentService.update(student1);
        studentService.saveOrUpdate(student1);
    }

    //给学生设置班级
    @Test
    public void setStudentClass() {
        List<Student> students = studentService.findAll();
        StudentClass studentClass = studentClassService.findById(3);
        for (Student student : students) {
            student.setStudentClass(studentClass);
            studentService.save(student);
        }
    }

    @Test
    public void find() {
        Page<Student> students = studentService.getStudentByMajor(1, 1, 10);
        for (Student student : students) {
            System.out.println("student=" + student.getName());
        }
    }

    @Test
    public void getStudent() {
        Page<Student> students = studentService.getStudentByDepartment(null, null, 2, 1, 20);
        for (Student student : students) {
            System.out.println("name=====" + student.getName());
        }
    }

}
