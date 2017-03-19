package com.newview.bysj.web.studentGuideRecord;

import com.newview.bysj.domain.Audit;
import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.domain.GuideRecord;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class AuditGuideRecordController extends BaseController {
    //审核指导记录的初界面
    @RequestMapping("tutor/approveGuideRecord")
    public String approvedGuideRecord(HttpSession httpSession, HttpServletRequest httpServletRequest, ModelMap modelMap, Integer pageNo, Integer pageSize) {
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        Page<GraduateProject> graduateProject = graduateProjectService.getPageByMainTutorage(tutor, pageNo, pageSize);
        //分页
        CommonHelper.pagingHelp(modelMap, graduateProject, "graduateProjects", CommonHelper.getRequestUrl(httpServletRequest), graduateProject.getTotalElements());
        return "guideRecord/guideRecordList";
    }

    //需要审核的指导记录的细节
    @RequestMapping("tutor/showDetailGuideRecord")
    public String showDetailGuideRecord(HttpServletRequest httpServletRequest, Integer graduateProjectId, ModelMap modelMap, Integer pageNo, Integer pageSize) {
        Page<GuideRecord> guideRecord = guideRecordService.getPageSubmittedByStudentProject(graduateProjectId, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, guideRecord, "guideRecords", CommonHelper.getRequestUrl(httpServletRequest), guideRecord.getTotalElements());
        modelMap.addAttribute("projectId", graduateProjectId);
        //modelMap.addAttribute(Common.DISPLAY_PATTERN,"ROLE_TUTOR");
        return "guideRecord/showDetailGuideRecord";
    }

    //打印论文附件，查看指导记录表的方法
    @RequestMapping("tutor/showDetailsGuideRecord")
    public String showDetailsGuideRecord(HttpServletRequest httpServletRequest, Integer graduateProjectId, ModelMap modelMap, Integer pageNo, Integer pageSize, Integer noPage, Integer sizePage) {
        Page<GuideRecord> guideRecord = guideRecordService.getPageSubmittedByStudentProject(graduateProjectId, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, guideRecord, "guideRecords", CommonHelper.getRequestUrl(httpServletRequest), guideRecord.getTotalElements());
        modelMap.addAttribute("projectId", graduateProjectId);
        modelMap.put("noPage", noPage);
        modelMap.put("sizePage", sizePage);
        //modelMap.addAttribute(Common.DISPLAY_PATTERN,"ROLE_TUTOR");
        return "guideRecord/showDetailsGuideRecord";
    }

    //将指导记录的审核状态设为通过
    @RequestMapping("tutor/tutorPassForApproval")
    public void toApproveGuideRecord(HttpSession httpSession, Integer guideRecordId, HttpServletResponse httpServletResponse) {
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        GuideRecord guideRecord = guideRecordService.findById(guideRecordId);
        Audit audit = null;
        if (guideRecord.getAuditedByTutor() == null) {
            audit = new Audit();
        } else {
            audit = guideRecord.getAuditedByTutor();
        }
        audit.setApprove(true);
        audit.setAuditDate(CommonHelper.getNow());
        audit.setAuditor(tutor);
        auditService.saveOrUpdate(audit);
        guideRecord.setAuditedByTutor(audit);
        //更新guideRecord
        guideRecordService.update(guideRecord);
        //对更新状态进行保存
        guideRecordService.save(guideRecord);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }

    //将指导记录的审核状态设为不通过
    @RequestMapping("tutor/tutorRejectForApproval")
    public void toCancelGuideRecord(HttpSession httpSession, Integer guideRecordId, HttpServletResponse httpServletResponse) {
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        GuideRecord guideRecord = guideRecordService.findById(guideRecordId);
        Audit audit = null;
        if (guideRecord.getAuditedByTutor() == null) {
            audit = new Audit();
        } else {
            audit = guideRecord.getAuditedByTutor();
        }
        audit.setApprove(false);
        audit.setAuditDate(CommonHelper.getNow());
        audit.setAuditor(tutor);
        auditService.saveOrUpdate(audit);
        guideRecord.setAuditedByTutor(audit);
        //更新guideRecord
        guideRecordService.update(guideRecord);
        //对更新状态进行保存
        guideRecordService.save(guideRecord);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }
    /*//重定向到显示细节
	@RequestMapping()
	public void 
*/
}
