package com.newview.bysj.service.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.domain.GuideRecord;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.service.ClassRoomService;
import com.newview.bysj.service.GraduateProjectService;
import com.newview.bysj.service.GuideRecordService;
import com.newview.bysj.service.StudentService;

public class TestGuideRecordService {
    GuideRecordService guideRecordService;
    StudentService studentService;
    GraduateProjectService graduateProjectService;
    ClassRoomService classRoomService;

    @Before
    public void init() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        guideRecordService = (GuideRecordService) ac.getBean("guideRecordService");
        studentService = (StudentService) ac.getBean("studentService");
        graduateProjectService = (GraduateProjectService) ac.getBean("graduateProjectService");
        classRoomService = (ClassRoomService) ac.getBean("classRoomService");
    }

    @Test
    public void addGuideRecord() {
        GraduateProject graduateProject = graduateProjectService.findById(1);
        GuideRecord guideRecord = new GuideRecord();
        guideRecord.setGraduateProject(graduateProject);
        guideRecord.setDescription("指导记录1");
        guideRecord.setClassRoom(classRoomService.findById(1));
        guideRecord.setSubmittedByStudent(true);
        guideRecord.setTime(CommonHelper.getNow());
        guideRecordService.save(guideRecord);
        GuideRecord guideRecord1 = new GuideRecord();
        guideRecord1.setGraduateProject(graduateProject);
        guideRecord1.setDescription("指导记录2");
        guideRecord1.setClassRoom(classRoomService.findById(1));
        guideRecord1.setSubmittedByStudent(true);
        guideRecord1.setTime(CommonHelper.getNow());
        guideRecordService.save(guideRecord1);

    }

}
