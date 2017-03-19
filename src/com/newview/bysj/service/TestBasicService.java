package com.newview.bysj.service;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestBasicService {
    protected ActorService actorService;
    protected AttachmentService attachmentService;
    protected AuditService auditService;
    protected BackUpService backUpService;
    protected ClassRoomService classRoomService;
    protected ConstraintService constraintService;
    protected ConstraintOfApproveOpenningReportService constraintOfApproveOpenningReportService;
    protected ConstraintOfProposeProjectService constraintOfPrposeProjectService;
    protected CoTutorageService coTutorageService;
    protected DegreeService degreeService;
    protected DepartmentService departmentService;
    protected DesignProjectService designProjectService;
    protected EmployeeService employeeService;
    protected GraduateProjectService graduateProjectService;
    protected GuideDayService guideDayService;
    protected GuideRecordService guideRecordService;
    protected HolidayAndVacationService holidayAndVacationService;
    protected MailService mailService;
    protected MailAddressesService mailAddressesService;
    protected MainTutorageService mainTutorageService;
    protected MajorService majorService;
    protected OpenningReportService openningReportService;
    protected OpinionService opinionService;
    protected PaperProjectService paperProjectService;
    protected ProjectFidelityService projectFidelityService;
    protected ProjectFromService projectFromService;
    protected ProjectTimeSpanService projectTimeSpanService;
    protected ProjectTypeService projectTypeService;
    protected ProTitleService proTitleService;
    protected ProvinceExcellentProjectService provinceExcellentProjectService;
    protected QuizService quizService;
    protected RemarkTemplateService remarkTemplateService;
    protected RemarkTemplateItemsService remarkTemplateItemsService;
    protected RemarkTemplateItemsOptionService remarkTemplateItemsOptionService;
    protected RemarkTemplateForDesignGroupService remarkTemplateForDesignGroupService;
    protected RemarkTemplateForDesignReviewerService remarkTemplateForDesignReviewerService;
    protected RemarkTemplateForDesignTutorService remarkTemplateForDesignTutorService;
    protected RemarkTemplateForPaperGroupService remarkTemplateForPaperGroupService;
    protected RemarkTemplateForPaperReviewerService remarkTemplateForPaperReviewerService;
    protected RemarkTemplateForPaperTutorService remarkTemplateForPaperTutorService;
    protected ReplyGroupMemberScoreService replyGroupMemberScoreService;
    protected ReplyGroupService replyGroupService;
    protected ResourceService resourceService;
    protected RoleResourceService roleResourceService;
    protected RoleService roleService;
    protected ScheduleService scheduleService;
    protected SchoolExcellentProjectService schoolExcellentProjectService;
    protected SchoolService schoolService;
    protected StageAchievementService stageAchievementService;
    protected StudentClassService studentClassService;
    protected StudentService studentService;
    protected TaskDocService taskDocService;
    protected TutorageService tutorageService;
    protected TutorAsMemberToReplyGroupService tutorAsMemberToReplyGroupService;
    protected TutorService tutorService;
    protected UserService userService;
    protected UserRoleService userRoleService;
    protected ValidityService validityService;
    protected VisitingEmployeeService visitingEmployeeService;
    protected EntityManagerFactory entityManagerFactory;

    public TestBasicService() {
    }

    public void initilize() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        actorService = (ActorService) ac.getBean("actorService");
        attachmentService = (AttachmentService) ac.getBean("attachmentService");
        auditService = (AuditService) ac.getBean("auditService");
        backUpService = (BackUpService) ac.getBean("backUpService");
        classRoomService = (ClassRoomService) ac.getBean("classRoomService");
        constraintOfApproveOpenningReportService = (ConstraintOfApproveOpenningReportService) ac.getBean("constraintOfApproveOpenningReportService");
        constraintOfPrposeProjectService = (ConstraintOfProposeProjectService) ac.getBean("constraintOfProposeProjectService");
        constraintService = (ConstraintService) ac.getBean("constraintService");
        coTutorageService = (CoTutorageService) ac.getBean("coTutorageService");
        degreeService = (DegreeService) ac.getBean("degreeService");
        departmentService = (DepartmentService) ac.getBean("departmentService");
        designProjectService = (DesignProjectService) ac.getBean("designProjectService");
        employeeService = (EmployeeService) ac.getBean("employeeService");
        graduateProjectService = (GraduateProjectService) ac.getBean("graduateProjectService");
        guideDayService = (GuideDayService) ac.getBean("guideDayService");
        guideRecordService = (GuideRecordService) ac.getBean("guideRecordService");
        holidayAndVacationService = (HolidayAndVacationService) ac.getBean("holidayAndVacationService");
        mailAddressesService = (MailAddressesService) ac.getBean("mailAddressesService");
        mailService = (MailService) ac.getBean("mailService");
        mainTutorageService = (MainTutorageService) ac.getBean("mainTutorageService");
        majorService = (MajorService) ac.getBean("majorService");
        openningReportService = (OpenningReportService) ac.getBean("openningReportService");
        opinionService = (OpinionService) ac.getBean("opinionService");
        paperProjectService = (PaperProjectService) ac.getBean("paperProjectService");
        projectFidelityService = (ProjectFidelityService) ac.getBean("projectFidelityService");
        projectFromService = (ProjectFromService) ac.getBean("projectFromService");
        projectTimeSpanService = (ProjectTimeSpanService) ac.getBean("projectTimeSpanService");
        projectTypeService = (ProjectTypeService) ac.getBean("projectTypeService");
        proTitleService = (ProTitleService) ac.getBean("proTitleService");
        provinceExcellentProjectService = (ProvinceExcellentProjectService) ac.getBean("provinceExcellentProjectService");
        quizService = (QuizService) ac.getBean("quizService");
        remarkTemplateService = (RemarkTemplateService) ac.getBean("remarkTemplateService");
        remarkTemplateItemsService = (RemarkTemplateItemsService) ac.getBean("remarkTemplateItemsService");
        remarkTemplateItemsOptionService = (RemarkTemplateItemsOptionService) ac.getBean("remarkTemplateItemsOptionService");
        remarkTemplateForDesignGroupService = (RemarkTemplateForDesignGroupService) ac.getBean("remarkTemplateForDesignGroupService");
        remarkTemplateForDesignReviewerService = (RemarkTemplateForDesignReviewerService) ac.getBean("remarkTemplateForDesignReviewerService");
        remarkTemplateForDesignTutorService = (RemarkTemplateForDesignTutorService) ac.getBean("remarkTemplateForDesignTutorService");
        remarkTemplateForPaperGroupService = (RemarkTemplateForPaperGroupService) ac.getBean("remarkTemplateForPaperGroupService");
        remarkTemplateForPaperReviewerService = (RemarkTemplateForPaperReviewerService) ac.getBean("remarkTemplateForPaperReviewerService");
        remarkTemplateForPaperTutorService = (RemarkTemplateForPaperTutorService) ac.getBean("remarkTemplateForPaperTutorService");
        replyGroupMemberScoreService = (ReplyGroupMemberScoreService) ac.getBean("replyGroupMemberScoreService");
        replyGroupService = (ReplyGroupService) ac.getBean("replyGroupService");
        resourceService = (ResourceService) ac.getBean("resourceService");
        roleResourceService = (RoleResourceService) ac.getBean("roleResourceService");
        roleService = (RoleService) ac.getBean("roleService");
        scheduleService = (ScheduleService) ac.getBean("scheduleService");
        schoolExcellentProjectService = (SchoolExcellentProjectService) ac.getBean("schoolExcellentProjectService");
        schoolService = (SchoolService) ac.getBean("schoolService");
        stageAchievementService = (StageAchievementService) ac.getBean("stageAchievementService");
        studentClassService = (StudentClassService) ac.getBean("studentClassService");
        studentService = (StudentService) ac.getBean("studentService");
        taskDocService = (TaskDocService) ac.getBean("taskDocService");
        tutorageService = (TutorageService) ac.getBean("tutorageService");
        tutorAsMemberToReplyGroupService = (TutorAsMemberToReplyGroupService) ac.getBean("tutorAsMemberToReplyGroupService");
        tutorService = (TutorService) ac.getBean("tutorService");
        userRoleService = (UserRoleService) ac.getBean("userRoleService");
        userService = (UserService) ac.getBean("userService");
        validityService = (ValidityService) ac.getBean("validityService");
        visitingEmployeeService = (VisitingEmployeeService) ac.getBean("visitingEmployeeService");
        entityManagerFactory = (EntityManagerFactory) ac.getBean("entityManagerFactory");
    }


}
