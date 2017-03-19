package com.newview.bysj.web.superviseReport;

import com.newview.bysj.domain.SchoolSupervisionReport;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.exception.MessageException;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

/**
 * Created by zhan on 2016/4/9.
 */
@Controller
@RequestMapping
public class SubmitSuperviseReportController extends BaseController {

    private static final Logger logger = Logger.getLogger(SubmitSuperviseReportController.class);

    //提交督导报告的方法
    @RequestMapping("/process/submitSuperviseReport.html")
    public String superviseReport(ModelMap modelMap, HttpSession httpSession, Integer pageNo, Integer pageSize, HttpServletRequest httpServletRequest) {
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentTutor(httpSession).getId());
        Page<SchoolSupervisionReport> schoolSupervisionReportPage = schoolSupervisionReportService.getPageByTutor(tutor, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, schoolSupervisionReportPage, "supervisionReportList", httpServletRequest.getRequestURI(), schoolSupervisionReportPage.getTotalElements());
        return "supervise/listSuperviseReport";
    }

    //上传督导报告的方法
    @RequestMapping(value = "/process/uploadSupervisionReport.html", method = RequestMethod.POST)
    public String uploadSuperviseReport(MultipartFile superReportFile, HttpSession httpSession, HttpServletResponse httpServletResponse) {
        //获取上传报告的用户
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentTutor(httpSession).getId());
        if (superReportFile != null && superReportFile.getSize() > 0) {
            //获取文件名
            String fileName = System.currentTimeMillis() + tutor.getDepartment().getSchool().getDescription() + superReportFile.getOriginalFilename();
            //获取上传的路径
            String url = CommonHelper.fileUpload(superReportFile, httpSession, "superReport", fileName);
            SchoolSupervisionReport schoolSupervisionReport = new SchoolSupervisionReport();
            schoolSupervisionReport.setSchool(tutor.getDepartment().getSchool());
            schoolSupervisionReport.setCalendar(CommonHelper.getNow().getTime());
            schoolSupervisionReport.setIssuer(tutor);
            schoolSupervisionReport.setUrl(url);
            schoolSupervisionReportService.saveOrUpdate(schoolSupervisionReport);
        } else {
            throw new MessageException("获取上传的文件失败");
        }
        return "redirect:/process/submitSuperviseReport.html";
    }

    @RequestMapping("/process/delSuperReport.html")
    public void delSuperReport(Integer reportId, HttpServletResponse httpServletResponse, HttpSession httpSession) {
        //获取要删除的督导报告
        SchoolSupervisionReport delReport = schoolSupervisionReportService.findById(reportId);
        if (delReport.getUrl() == null) {
            CommonHelper.delete(httpSession, delReport.getUrl());
        }
        schoolSupervisionReportService.deleteById(reportId);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }

    //下载督导报告的方法
    @RequestMapping("/process/downloadSupervisionReport.html")
    public ResponseEntity<byte[]> downLoadSuperReport(Integer reportId, HttpSession httpSession) throws IOException {
        //获取要下载的督导报告
        SchoolSupervisionReport schoolSupervisionReport = schoolSupervisionReportService.findById(reportId);
        return CommonHelper.download(httpSession, schoolSupervisionReport.getUrl(), new File(schoolSupervisionReport.getUrl()).getName());
    }
}
