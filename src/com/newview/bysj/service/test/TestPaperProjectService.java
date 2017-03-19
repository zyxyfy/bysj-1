package com.newview.bysj.service.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;

import com.newview.bysj.domain.Audit;
import com.newview.bysj.domain.Department;
import com.newview.bysj.domain.PaperProject;
import com.newview.bysj.domain.School;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.service.AuditService;
import com.newview.bysj.service.DepartmentService;
import com.newview.bysj.service.MainTutorageService;
import com.newview.bysj.service.MajorService;
import com.newview.bysj.service.OpenningReportService;
import com.newview.bysj.service.PaperProjectService;
import com.newview.bysj.service.SchoolService;
import com.newview.bysj.service.StudentService;
import com.newview.bysj.service.TutorService;

public class TestPaperProjectService {
    PaperProjectService paperProjectService;
    TutorService tutorService;
    MajorService majorService;
    OpenningReportService openningReportService;
    AuditService auditService;
    DepartmentService departmentService;
    SchoolService schoolService;
    StudentService studentService;
    MainTutorageService mainTutorageService;

    @Before
    public void init() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        paperProjectService = (PaperProjectService) ac.getBean("paperProjectService");
        tutorService = (TutorService) ac.getBean("tutorService");
        majorService = (MajorService) ac.getBean("majorService");
        auditService = (AuditService) ac.getBean("auditService");
        openningReportService = (OpenningReportService) ac.getBean("openningReportService");
        departmentService = (DepartmentService) ac.getBean("departmentService");
        schoolService = (SchoolService) ac.getBean("schoolService");
        studentService = (StudentService) ac.getBean("studentService");
        mainTutorageService = (MainTutorageService) ac.getBean("mainTutorageService");
    }

    @Test
    public void save() {
        PaperProject pp = new PaperProject();
        pp.setYear(CommonHelper.getYear());
        pp.setTitle("论文题目123");
        paperProjectService.save(pp);
        PaperProject pp1 = new PaperProject();
        pp1.setYear(CommonHelper.getYear());
        pp1.setTitle("456论文题目123");
        paperProjectService.save(pp1);
    }

    @Test
    public void setRelated() {
        PaperProject pp = paperProjectService.findById(1);
        pp.setMajor(majorService.findById(1));
        pp.setProposer(tutorService.findById(1));
        //pp.setOpenningReport(openningReportService.findById(1));
        pp.setAuditByDirector(new Audit(true));
        pp.setProposerSubmitForApproval(true);
        paperProjectService.update(pp);
    }

    @Test
    public void setMainTutorage() {
        PaperProject pp = paperProjectService.findById(1);
        pp.setMainTutorage(mainTutorageService.findById(1));
        paperProjectService.update(pp);
    }

    @Test
    public void setStudent() {
        PaperProject pp = paperProjectService.findById(1);
        pp.setStudent(studentService.findById(16));
        paperProjectService.update(pp);
    }

    @Test
    public void paperProjectByProposer() {
        Tutor proposer = tutorService.findById(3);
        Page<PaperProject> result = paperProjectService.getPaperProjectByProposer(proposer, 1, 2);
        for (PaperProject pp : result) {
            System.out.println(pp.getTitle());
        }
    }

    @Test
    public void paperProjectByMainTutorage() {
        Tutor mainTutorage = tutorService.findById(3);
        Page<PaperProject> result = paperProjectService.getPaperProjectByMainTutorage(mainTutorage, 1, 2);
        for (PaperProject pp : result) {
            System.out.println(pp.getTitle());
        }
    }

    @Test
    public void paperProjectByDepartment() {
        Department department = departmentService.findById(1);
        Page<PaperProject> result = paperProjectService.getPaperProjectByDepartment(department, 1, 2);
        for (PaperProject pp : result) {
            System.out.println(pp.getTitle());
        }
    }

    @Test
    public void getPaperProjectByMainTutorageAndDepartment() {
        Tutor maintutorage = tutorService.findById(3);
        Page<PaperProject> result = paperProjectService.getPaperProjectByDepartment(maintutorage, 1, 2);
        for (PaperProject pp : result) {
            System.out.println(pp.getTitle());
        }
    }

    @Test
    public void getPaperProjectBySchool() {
        School school = schoolService.findById(2);
        Page<PaperProject> result = paperProjectService.getPaperProjectBySchool(school, 1, 2);
        for (PaperProject pp : result) {
            System.out.println(pp.getTitle());
        }
    }

    @Test
    public void getPaperProjectBySchoolAndTitle() {
        School school = schoolService.findById(2);
        Page<PaperProject> result = paperProjectService.getPaperProjectBySchoolAndTitle(school, "2", 1, 2);
        for (PaperProject pp : result) {
            System.out.println(pp.getTitle());
        }
    }

    @Test
    public void getPaperProjectByTitle() {
        Page<PaperProject> result = paperProjectService.getPaperProjectByTitle("2", 1, 2);
        for (PaperProject pp : result) {
            System.out.println(pp.getTitle());
        }
    }

    @Test
    public void getPaperProjectByDepartment() {
        Tutor tutor = tutorService.findById(3);
        Page<PaperProject> result = paperProjectService.getPaperProjectByDepartment(tutor, 1, 2);
        for (PaperProject pp : result) {
            System.out.println(pp.getTitle());
        }
    }

}
