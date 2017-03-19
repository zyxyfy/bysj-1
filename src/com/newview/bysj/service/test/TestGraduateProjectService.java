package com.newview.bysj.service.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;

import com.newview.bysj.domain.Department;
import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.domain.OpenningReport;
import com.newview.bysj.domain.PaperProject;
import com.newview.bysj.domain.Student;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.service.DepartmentService;
import com.newview.bysj.service.GraduateProjectService;
import com.newview.bysj.service.MainTutorageService;
import com.newview.bysj.service.OpenningReportService;
import com.newview.bysj.service.StudentService;
import com.newview.bysj.service.TutorService;

public class TestGraduateProjectService {
    GraduateProjectService graduateProjectService;
    StudentService studentService;
    TutorService tutorService;
    MainTutorageService mainTutorageService;
    DepartmentService departmentService;
    OpenningReportService openningReportService;

    @Before
    public void init() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        graduateProjectService = (GraduateProjectService) ac.getBean("graduateProjectService");
        studentService = (StudentService) ac.getBean("studentService");
        tutorService = (TutorService) ac.getBean("tutorService");
        mainTutorageService = (MainTutorageService) ac.getBean("mainTutorageService");
        departmentService = (DepartmentService) ac.getBean("departmentService");
        openningReportService = (OpenningReportService) ac.getBean("openningReportService");
    }

    @Test
    public void setProposer() {
        Tutor proposer = tutorService.findById(2);
        GraduateProject g1 = graduateProjectService.findById(1);
        g1.setProposer(proposer);
        graduateProjectService.update(g1);
        graduateProjectService.save(g1);
    }

    @Test
    public void save() {
        GraduateProject graduateProject = new GraduateProject();
        graduateProjectService.save(graduateProject);
    }

    @Test
    public void setStudent() {
        Student s1 = studentService.findById(32846);
        GraduateProject g1 = graduateProjectService.findById(2);
        g1.setStudent(s1);
        graduateProjectService.update(g1);
        graduateProjectService.save(g1);
        /*Student s2=studentService.findById(17);
		GraduateProject g2=graduateProjectService.findById(2);
		g2.setStudent(s2);
		graduateProjectService.update(g2);
		graduateProjectService.save(g2);*/
    }

    @Test
    public void find() {
        Tutor tutor = tutorService.findById(1);
        Page<GraduateProject> graduateProjects = graduateProjectService.getPageByMainTutorage(tutor, 1, 10);
        for (GraduateProject graduateProject : graduateProjects) {
            System.out.println(graduateProject.getTitle());
        }
    }

    @Test
    public void delete() {
        GraduateProject graduateProject = graduateProjectService.findById(4);
        Student student = graduateProject.getStudent();
        if (student != null) {
            System.out.println("==============");
        } else {
            graduateProjectService.deleteObject(graduateProject);
            ;
        }
    }

    @Test
    public void getAuditByDirector() {
        Tutor tutor = tutorService.findById(1);
        Department department = departmentService.findById(1);
        Page<GraduateProject> graduateProjects = graduateProjectService.getPageByAuditedDirector(tutor, department, 1, 10, true);
        for (GraduateProject graduateProject : graduateProjects) {
            System.out.println("------------" + graduateProject.getTitle());
        }
    }

    @Test
    public void deleteGraduateProject() {
        GraduateProject gp = graduateProjectService.findById(4);
        ((PaperProject) gp).setOpenningReport(null);
        graduateProjectService.saveOrUpdate(gp);
        OpenningReport op = openningReportService.findById(2);
        op.setPaperProject(null);
        openningReportService.saveOrUpdate(op);
        openningReportService.deleteObject(op);
        graduateProjectService.deleteObject(gp);
    }

}
