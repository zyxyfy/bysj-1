package com.newview.bysj.service.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;

import com.newview.bysj.domain.Audit;
import com.newview.bysj.domain.Department;
import com.newview.bysj.domain.TaskDoc;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.service.AuditService;
import com.newview.bysj.service.DepartmentService;
import com.newview.bysj.service.GraduateProjectService;
import com.newview.bysj.service.StudentClassService;
import com.newview.bysj.service.TaskDocService;
import com.newview.bysj.service.TutorService;

public class TestTaskDocService {
    TaskDocService taskDocService;
    TutorService tutorService;
    GraduateProjectService graduateProjectService;
    AuditService auditService;
    StudentClassService studentClassService;
    DepartmentService departmentService;

    @Before
    public void init() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        graduateProjectService = (GraduateProjectService) ac.getBean("graduateProjectService");
        tutorService = (TutorService) ac.getBean("tutorService");
        auditService = (AuditService) ac.getBean("auditService");
        taskDocService = (TaskDocService) ac.getBean("taskDocService");
        studentClassService = (StudentClassService) ac.getBean("studentClassService");
        departmentService = (DepartmentService) ac.getBean("departmentService");
    }

    @Test
    public void save() {
        TaskDoc taskDoc = new TaskDoc();
        taskDoc.setAuditByBean(new Audit(true));
        taskDoc.setAuditByDepartmentDirector(new Audit(true));
        taskDoc.setAuditByTutor(new Audit(true));
        //taskDoc.setGraduateProject(graduateProjectService.findById(1));
        taskDocService.save(taskDoc);
        System.out.println(taskDocService.findById(taskDoc.getId()));

    }

    @Test
    public void get() {

    }

    @Test
    public void delete() {
        TaskDoc taskDoc = taskDocService.findById(7);
        taskDoc.setGraduateProject(null);
        taskDocService.saveOrUpdate(taskDoc);
        taskDocService.deleteObject(taskDoc);
    }

    @Test
    public void audit() {
        //获取要审核的任务书
        TaskDoc taskDoc = taskDocService.findById(2);
        Audit auditByDirector = taskDoc.getAuditByDepartmentDirector();
        //审核状态设为通过
        auditByDirector.setApprove(false);
        //设置审核日期
        auditByDirector.setAuditDate(CommonHelper.getNow());
        //设置审核人
        auditByDirector.setAuditor(tutorService.findById(1));
        //更新审核状态
        auditService.update(auditByDirector);
        //对更新的审核状态进行保存
        auditService.save(auditByDirector);
    }

}
