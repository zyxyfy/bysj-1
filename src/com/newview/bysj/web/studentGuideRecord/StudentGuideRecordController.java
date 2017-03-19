package com.newview.bysj.web.studentGuideRecord;

import com.newview.bysj.domain.*;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Controller
public class StudentGuideRecordController extends BaseController {
    private static final Logger logger = Logger.getLogger(StudentGuideRecordController.class);

    @RequestMapping(value = "/student/writeGuideTable.html", method = RequestMethod.GET)
    public String writeGuideTable(HttpSession httpSession, HttpServletRequest httpServletRequest, ModelMap modelMap, Integer pageNo, Integer pageSize, Integer error) {
        //获取当前用户
        Student student = studentService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        String message = null;
        //是否有毕业设计题目
        if (student.getGraduateProject() == null) {
            modelMap.addAttribute("message", "尚未分配毕业设计题目，尽快联系指导老师");
            return "student/openningReport/noOpenningReport";
        }
        //是否有毕业设计时间
        if (student.getStudentClass().getMajor().getDepartment().getProjectTimeSpan() == null) {
            modelMap.addAttribute("message", "请等待教研室主任设置毕业设计时间");
            return "student/openningReport/noOpenningReport";
        }
        //是否有指导日期
        if (student.getGraduateProject().getGuideDay() == null) {
            modelMap.addAttribute("hasGuideDay", "0");
        } else {
            modelMap.addAttribute("hasGuideDay", "1");
            modelMap.addAttribute("guideDay", student.getGraduateProject().getGuideDay());
        }
        if (error != null) {
            if (error == 1) {
                message = "该日期为节假日，不能添加指导记录表";
            }
            if (error == 2) {
                message = "该日期指导记录表已存在，不能添加指导记录表";
            }
            if (error == 3) {
                message = "该日期毕业设计尚未开始，不能添加指导记录";
            }
            if (error == 4) {
                message = "该日期毕业设计已结束，不能添加指导记录表";
            }
        }
        //判断是否在毕业设计的时间内
        Calendar endTime = student.getStudentClass().getMajor().getDepartment().getProjectTimeSpan().getEndTime();
        Calendar nowTime = CommonHelper.getNow();
        if (nowTime.equals(endTime) || nowTime.after(endTime)) {
            message = "毕业设计时间已经结束,不能执行添加操作！";
            modelMap.addAttribute("ifHaveOperation", "0");
        }
        //获取guideRecord
        Integer graduateProjectId = student.getGraduateProject().getId();
        Page<GuideRecord> guideRecords = guideRecordService.getPageByStudentProject(graduateProjectId, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, guideRecords, "guideRecords", CommonHelper.getRequestUrl(httpServletRequest), guideRecords.getTotalElements());
        modelMap.addAttribute("message", message);
        modelMap.addAttribute("actionUrl", CommonHelper.getRequestUrl(httpServletRequest));
        return "student/guideRecord/addGuideTable";
    }

    //手动添加一条
    @RequestMapping(value = "student/addGuideRecord", method = RequestMethod.GET)
    public String addGuideRecord(ModelMap modelMap, HttpServletRequest httpServletRequest) {
        List<ClassRoom> classRooms = classRoomService.findAll();
        modelMap.addAttribute("classRooms", classRooms);
        modelMap.addAttribute("actionUrl", CommonHelper.getRequestUrl(httpServletRequest));
        return "student/guideRecord/addOrEditGuideRecord";
    }

    @RequestMapping(value = "student/addGuideRecord", method = RequestMethod.POST)
    public String addGuideRecord(HttpSession httpSession, ModelMap modelMap, String time, String description, Integer classRoomId) {
        //获取当前用户
        Student student = studentService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        GraduateProject graduateProject = student.getGraduateProject();
        List<GuideRecord> oldGuideRecords = graduateProject.getGuideRecord();
        Calendar calendarTime = CommonHelper.getCalendarByString(time);
        int error = 0;
        if (guideRecordService.isHolidayAndVacation(calendarTime)) {
            error = 1;
        } else if (guideRecordService.isRepeatFromOld(oldGuideRecords, calendarTime)) {
            error = 2;
        } else if (guideRecordService.isBeforeProjectTimeSpan(student, calendarTime)) {
            error = 3;
        } else if (guideRecordService.isAfterProjectTimeSpan(student, calendarTime)) {
            error = 4;
        } else if (error == 0) {
            ClassRoom classRoom = classRoomService.findById(classRoomId);
            Audit auditByTutor = new Audit();
            auditByTutor.setApprove(true);
            auditByTutor.setAuditDate(calendarTime);
            auditByTutor.setAuditor(student.getGraduateProject().getMainTutorage().getTutor());
            GuideRecord guideRecord = new GuideRecord();
            guideRecord.setSubmittedByStudent(true);
            guideRecord.setClassRoom(classRoom);
            guideRecord.setAuditedByTutor(auditByTutor);
            guideRecord.setDescription(description);
            guideRecord.setTime(calendarTime);
            guideRecord.setGraduateProject(graduateProject);
            guideRecordService.save(guideRecord);
        }
        return "redirect:writeGuideTable.html?error=" + error;
    }

    //修改
    @RequestMapping(value = "student/editGuideRecord", method = RequestMethod.GET)
    public String editGuideRecord(ModelMap modelMap, HttpServletRequest httpServletRequest, Integer guideRecordId) {
        GuideRecord guideRecord = guideRecordService.findById(guideRecordId);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        modelMap.addAttribute("time", simpleDateFormat.format(guideRecord.getTime().getTime()));
        modelMap.addAttribute("editGuideRecord", guideRecord);
        List<ClassRoom> classRoom = classRoomService.findAll();
        modelMap.addAttribute("classRooms", classRoom);
        modelMap.addAttribute("actionUrl", CommonHelper.getRequestUrl(httpServletRequest));
        return "student/guideRecord/addOrEditGuideRecord";
    }

    @RequestMapping(value = "student/editGuideRecord", method = RequestMethod.POST)
    public String editGuideRecord(HttpSession httpSession, HttpServletResponse httpServletResponse, String time, String description, Integer guideRecordId, Integer classRoomId) {
        Student student = studentService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        Calendar calendar = CommonHelper.getCalendarByString(time);
        GuideRecord guideRecord = guideRecordService.findById(guideRecordId);
        List<GuideRecord> guideRecords = student.getGraduateProject().getGuideRecord();
        guideRecords.remove(guideRecord);
        if (guideRecordService.isHolidayAndVacation(calendar)) {
            CommonHelper.buildSimpleJson(httpServletResponse, "1");
        } else if (guideRecordService.isRepeatFromOld(guideRecords, calendar)) {
            CommonHelper.buildSimpleJson(httpServletResponse, "2");
        } else if (guideRecordService.isBeforeProjectTimeSpan(student, calendar)) {
            CommonHelper.buildSimpleJson(httpServletResponse, "3");
        } else if (guideRecordService.isAfterProjectTimeSpan(student, calendar)) {
            CommonHelper.buildSimpleJson(httpServletResponse, "4");
        } else {

        }
        guideRecord.setClassRoom(null);
        guideRecord.setDescription(null);
        guideRecord.setTime(null);
        guideRecord.setClassRoom(classRoomService.findById(classRoomId));
        guideRecord.setDescription(description);
        guideRecord.setTime(calendar);
        guideRecordService.saveOrUpdate(guideRecord);
        //logger.error("=============");
        return "redirect:/student/writeGuideTable.html";
    }

    //删除
    @RequestMapping("student/deleteGuideRecord")
    public void delete(HttpServletResponse httpServletResponse, Integer guideRecordId) {
        GuideRecord guideRecord = guideRecordService.findById(guideRecordId);
        guideRecordService.deleteObject(guideRecord);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }

    @RequestMapping("/studentSubmitForGuideRecord")
    public void studentToSubmit(HttpServletResponse httpServletResponse, Integer guideRecordId) {
        GuideRecord guideRecord = guideRecordService.findById(guideRecordId);
        guideRecord.setSubmittedByStudent(true);
        if (guideRecord.getAuditedByTutor() != null) {
            Audit auditByTutor = guideRecord.getAuditedByTutor();
            Integer auditId = auditByTutor.getId();
            guideRecord.setAuditedByTutor(null);
            auditService.deleteById(auditId);
        }
        guideRecordService.update(guideRecord);
        //对更新状态进行保存
        guideRecordService.save(guideRecord);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }
}
