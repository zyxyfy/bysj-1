package com.newview.bysj.service.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;

import com.newview.bysj.domain.Audit;
import com.newview.bysj.domain.OpenningReport;
import com.newview.bysj.domain.PaperProject;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.service.OpenningReportService;
import com.newview.bysj.service.PaperProjectService;
import com.newview.bysj.service.TutorService;

public class TestOpenningReportService {
    OpenningReportService openningReportService;
    TutorService tutorService;
    PaperProjectService paperProjectService;

    @Before
    public void init() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        openningReportService = (OpenningReportService) ac.getBean("openningReportService");
        tutorService = (TutorService) ac.getBean("tutorService");
        paperProjectService = (PaperProjectService) ac.getBean("paperProjectService");
    }

    @Test
    public void save() {
        PaperProject paperProject = paperProjectService.findById(4);
        OpenningReport op = new OpenningReport();
        op.setAuditByDepartmentDirector(new Audit(true));
        op.setAuditByTutor(new Audit(true));
        op.setSubmittedByStudent(true);
        op.setPaperProject(paperProject);
        openningReportService.save(op);
    }

    @Test
    public void setPaperProject() {
        OpenningReport op = openningReportService.findById(1);
        op.setPaperProject(paperProjectService.findById(1));
        openningReportService.update(op);
    }

    @Test
    public void getAuditedOpenningReportByTutor() {
        Tutor tutor = tutorService.findById(1);
        Page<OpenningReport> result = openningReportService.getAuditedOpenningReportByTutor(tutor, true, 1, 2);
        for (OpenningReport op : result) {
            System.out.println("========" + op.getId());
        }
    }

    @Test
    public void getAuditedOpenningReportByDirector() {
        Tutor tutor = tutorService.findById(1);
        Page<OpenningReport> result = openningReportService.getAuditedOpenningReportByDirector(tutor, true, 1, 2);
        for (OpenningReport op : result) {
            System.out.println("=========" + op.getId());
        }
    }

    @Test
    public void getAuditOpenningReportByTutorAndDirector() {
        Tutor tutor = tutorService.findById(1);
        Page<OpenningReport> result = openningReportService.getAuditOpenningReportByTutorAndDirector(tutor, true, 1, 2);
        for (OpenningReport op : result) {
            System.out.println("========" + op.getId());
        }
    }

    @Test
    public void delete() {
        OpenningReport openningReport = openningReportService.findById(3);
        openningReport.setPaperProject(null);
        PaperProject paperProject = paperProjectService.findById(1);
        paperProject.setOpenningReport(null);
        openningReportService.saveOrUpdate(openningReport);
        paperProjectService.saveOrUpdate(paperProject);
        openningReportService.deleteObject(openningReport);
    }

}
