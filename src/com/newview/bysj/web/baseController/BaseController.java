package com.newview.bysj.web.baseController;

import com.newview.bysj.dao.ProjectTypeDao;
import com.newview.bysj.service.*;
import com.sun.beans.editors.DoubleEditor;
import com.sun.beans.editors.IntegerEditor;
import com.sun.beans.editors.LongEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 所有controller类的父类，用于注入service层的类
 */
public abstract class BaseController {

    // 通过protected来限定只能被不同包中的子类来使用
    @Autowired
    protected ActorService actorService;
    @Autowired
    protected AttachmentService attachmentService;
    @Autowired
    protected AuditService auditService;
    @Autowired
    protected ClassRoomService classRoomService;
    @Autowired
    protected ConstraintOfApproveOpenningReportService constraintOfApproveOpenningReportService;
    @Autowired
    protected ConstraintOfProposeProjectService constraintOfProposeProjectService;
    @Autowired
    protected ConstraintService constraintService;
    @Autowired
    protected CoTutorageService coTutorageService;
    @Autowired
    protected DegreeService degreeService;
    @Autowired
    protected DepartmentService departmentService;
    @Autowired
    protected DesignProjectService designProjectService;
    @Autowired
    protected EmployeeService employeeService;
    @Autowired
    protected GraduateProjectService graduateProjectService;
    @Autowired
    protected GuideRecordService guideRecordService;
    @Autowired
    protected HolidayAndVacationService holidayAndVacationService;
    @Autowired
    protected MajorService majorService;
    @Autowired
    protected MailAddressesService mailAddressesService;
    @Autowired
    protected MailService mailService;
    @Autowired
    protected MainTutorageService mainTutorageService;
    @Autowired
    protected OpenningReportService openningReportService;
    @Autowired
    protected PaperProjectService paperProjectService;
    @Autowired
    protected ProjectFidelityService projectFidelityService;
    @Autowired
    protected ProjectFromService projectFromService;
    @Autowired
    protected ProjectTimeSpanService projectTimeSpanService;
    @Autowired
    protected ProjectTypeDao projectTypeService;
    @Autowired
    protected ProTitleService proTitleService;
    @Autowired
    protected ProvinceExcellentProjectService provinceExcellentProjectService;
    @Autowired
    protected QuizService quizService;
    @Autowired
    protected RemarkTemplateForDesignGroupService remarkTemplateForDesignGroupService;
    @Autowired
    protected RemarkTemplateForDesignReviewerService remarkTemplateForDesignReviewerService;
    @Autowired
    protected RemarkTemplateForDesignTutorService remarkTemplateForDesignTutorService;
    @Autowired
    protected RemarkTemplateForPaperGroupService remarkTemplateForPaperGroupService;
    @Autowired
    protected RemarkTemplateForPaperReviewerService remarkTemplateForPaperReviewerService;
    @Autowired
    protected RemarkTemplateForPaperTutorService remarkTemplateForPaperTutorService;
    @Autowired
    protected RemarkTemplateItemsOptionService remarkTemplateItemsOptionService;
    @Autowired
    protected RemarkTemplateItemsService remarkTemplateItemsService;
    @Autowired
    protected RemarkTemplateService remarkTemplateService;
    @Autowired
    protected ReplyGroupMemberScoreService replyGroupMemberScoreService;
    @Autowired
    protected ReplyGroupService replyGroupService;
    @Autowired
    protected ResourceService resourceService;
    @Autowired
    protected RoleResourceService roleResourceService;
    @Autowired
    protected RoleService roleService;
    @Autowired
    protected ScheduleService scheduleService;
    @Autowired
    protected SchoolExcellentProjectService schoolExcellentProjectService;
    @Autowired
    protected SchoolService schoolService;
    @Autowired
    protected SchoolSupervisionReportService schoolSupervisionReportService;
    @Autowired
    protected StageAchievementService stageAchievementService;
    @Autowired
    protected StudentClassService studentClassService;
    @Autowired
    protected TaskDocService taskDocService;
    @Autowired
    protected StudentService studentService;
    @Autowired
    protected TutorageService tutorageService;
    @Autowired
    protected TutorAsMemberToReplyGroupService tutorAsMemberToReplyGroupService;
    @Autowired
    protected TutorService tutorService;
    @Autowired
    protected UserRoleService userRoleService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected ValidityService validityService;
    @Autowired
    protected VisitingEmployeeService visitingEmployeeService;

    /**
     * 使用注解对表单标签进行绑定
     *
     * @param binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(int.class, new IntegerEditor());
        binder.registerCustomEditor(double.class, new DoubleEditor());
        binder.registerCustomEditor(long.class, new LongEditor());
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss"), true));
    }
}
