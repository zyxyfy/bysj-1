package com.newview.bysj.service.test;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.domain.Schedule;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.service.GraduateProjectService;
import com.newview.bysj.service.ScheduleService;

public class TestScheduleService {
    ScheduleService scheduleService;
    GraduateProjectService graduateProjectService;

    @Before
    public void init() {
        @SuppressWarnings("resource")
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        scheduleService = (ScheduleService) ac.getBean("scheduleService");
        graduateProjectService = (GraduateProjectService) ac.getBean("graduateProjectService");
    }

    @Test
    public void save() {
        Calendar begin = Calendar.getInstance();
        begin.set(2016, 4, 19);
        Calendar end = Calendar.getInstance();
        end.set(2016, 5, 1);
        GraduateProject g1 = graduateProjectService.findById(1);
        Schedule schedule = new Schedule();
        schedule.setBeginTime(begin);
        schedule.setContent("工作进程表1");
        schedule.setGraduateProject(g1);
        schedule.setEndTime(end);
        scheduleService.save(schedule);
        GraduateProject g2 = graduateProjectService.findById(1);
        Schedule schedule1 = new Schedule();
        schedule1.setBeginTime(begin);
        schedule1.setContent("工作进程表2");
        schedule1.setGraduateProject(g2);
        schedule1.setEndTime(end);
        scheduleService.save(schedule1);

    }
}
