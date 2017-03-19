package com.newview.bysj.xls;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newview.bysj.exception.DatabaseException;
import com.newview.bysj.service.EmployeeService;
import com.newview.bysj.service.UserService;


public class SheetDbTest {
    SheetDb excelDb;
    UserService userService;
    EmployeeService employeeService;

    @Before
    public void init() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        excelDb = new SheetDb(new File("E://TeacherList.xls"));
        userService = (UserService) ac.getBean("userService");
        employeeService = (EmployeeService) ac.getBean("employeeService");
    }

    @Test
    public void test() throws Exception {
        excelDb.print();
        System.out.println(excelDb.getCell(2, "role"));
    }

    @Test
    public void saveToDatabase() throws FileNotFoundException, IOException, DatabaseException {
        while (excelDb.next()) {
            System.out.print(excelDb.getCell("教师编号"));
            System.out.print(",");
            System.out.print(excelDb.getCell("姓名"));
            System.out.print(",");
            System.out.print(excelDb.getCell("性别"));
            System.out.println(",");
            System.out.println(excelDb.getCell("所属教研室"));
            //User user=new User(excelDb.getCell("password"),excelDb.getCell("userName"),excelDb.getCell("role"));
            //userService.save(user);
        }


    }


}
