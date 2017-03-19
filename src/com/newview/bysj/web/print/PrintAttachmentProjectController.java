package com.newview.bysj.web.print;

import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.domain.PaperProject;
import com.newview.bysj.domain.Student;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.reports.CoverReportCommitments;
import com.newview.bysj.web.baseController.BaseController;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created 2016/3/1,14:33.
 * Author 张战.
 */
@Controller
@RequestMapping("print")
public class PrintAttachmentProjectController extends BaseController {

    private Logger logger = Logger.getLogger(PrintAttachmentProjectController.class);

    //学生和老师打印论文附件的方法
    @RequestMapping(value = "/printAttachmentOfProject.html", method = RequestMethod.GET)
    public String printAttachmentOfProject(String category, ModelMap modelMap, HttpSession httpSession, Integer pageNo, Integer pageSize, HttpServletRequest httpServletRequest, String title) {
        try {
            Student student = (Student) CommonHelper
                    .getCurrentActor(httpSession);
            List<GraduateProject> projectList = new ArrayList<>();
            GraduateProject project = graduateProjectService.uniqueResult("student", Student.class, student);
            projectList.add(project);
            modelMap.put("graduateProjectList", projectList);
            return "print/printAttachmentOfProject";
        } catch (Exception e) {

            HashMap<String, String> hashMap = new HashMap<>();
            if (title != null) {
                hashMap.put("title", title);
            }
            Tutor tutor = (Tutor) CommonHelper.getCurrentActor(httpSession);
            //通过课题的类型和title来筛选课题
            Page<GraduateProject> graduateProjectPage = graduateProjectService.getPagesByMainTutorageWithConditionsAndCategory(category, tutor, pageNo, pageSize, hashMap);
            CommonHelper.pagingHelp(modelMap, graduateProjectPage, "graduateProjectList", httpServletRequest.getRequestURI(), graduateProjectPage.getTotalElements());
            modelMap.put("actionUrl", httpServletRequest.getRequestURI());
            modelMap.put("selectPrint", true);
            return "print/printAttachmentOfProject";
        }
    }


    @RequestMapping(value = "/showCover.html", method = RequestMethod.GET)
    public String showCover(Integer id, ModelMap modelMap) {
        // 创建报表用数据list
        List<CoverReportCommitments> listData = new ArrayList<>();
        CoverReportCommitments coverReportCommitment = new CoverReportCommitments();
        // 获取当前的graduateProject
        GraduateProject graduateProject = graduateProjectService.findById(id);
        coverReportCommitment.setProjectCategory("本科"
                + graduateProject.getCategory());
        Boolean isPaper = graduateProject instanceof PaperProject;
        if (isPaper) {
            coverReportCommitment.setHead_1("本科毕业论文附件");
            coverReportCommitment.setHead_2("本科毕业论文成绩评定册");
        } else {
            coverReportCommitment.setHead_1("本科毕业设计附件");
            coverReportCommitment.setHead_2("本科毕业设计成绩评定册");
        }
        coverReportCommitment.setTitle(graduateProject.getTitle());
        String subTitle = "——" + graduateProject.getSubTitle();
        if (graduateProject.getSubTitle().length() == 0) {
            coverReportCommitment.setSubTitle(" ");
        } else {
            coverReportCommitment.setSubTitle(subTitle);
        }
        coverReportCommitment.setStudentClass(graduateProject.getStudent()
                .getStudentClass().getMajor().getDescription());
        coverReportCommitment
                .setStudentNo(graduateProject.getStudent().getNo());
        coverReportCommitment.setStudentName(graduateProject.getStudent()
                .getName());
        coverReportCommitment.setTutorName(graduateProject.getProposer()
                .getName());
        if (graduateProject.getProposer().getProTitle() == null) {
            coverReportCommitment.setTutorCheif("  ");
        } else {
            coverReportCommitment.setTutorCheif(graduateProject.getProposer()
                    .getProTitle().getDescription());
        }

        listData.add(coverReportCommitment);
        JRDataSource data = new JRBeanCollectionDataSource(
                listData);
       /* Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("CoverReportCommitments", data);
        modelAndView = new ModelAndView("CoverCommitmentsReport", parameterMap);*/
        modelMap.addAttribute("url", "/WEB-INF/reports/CoverReport.jrxml");
        modelMap.addAttribute("format", "pdf");
        modelMap.addAttribute("jrMainDataSource", data);
        return "iReportView";

    }
}
