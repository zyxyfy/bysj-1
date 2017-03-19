package com.newview.bysj.service.test;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newview.bysj.domain.Department;
import com.newview.bysj.domain.ProjectTimeSpan;
import com.newview.bysj.service.DepartmentService;
import com.newview.bysj.service.ProjectTimeSpanService;

public class TestProjectTimeSpanService {
    ProjectTimeSpanService projectTimeSpanService;
    DepartmentService departmentService;

    @Before
    public void init() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        projectTimeSpanService = (ProjectTimeSpanService) ac.getBean("projectTimeSpanService");
        departmentService = (DepartmentService) ac.getBean("departmentService");
    }

    @Test
    public void save() {
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2016, 03, 01);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2016, 04, 01);
        ProjectTimeSpan projectTimeSpan = new ProjectTimeSpan();
        projectTimeSpan.setBeginTime(beginTime);
        projectTimeSpan.setEndTime(endTime);
        projectTimeSpan.setYear("2016");
        projectTimeSpanService.save(projectTimeSpan);
    }

    @Test
    public void setProjectTimeSpan() {
        Department department = departmentService.findById(1);
        ProjectTimeSpan projectTimeSpan = projectTimeSpanService.findById(1);
        department.setProjectTimeSpan(projectTimeSpan);
        departmentService.update(department);
        departmentService.save(department);
    }
}
