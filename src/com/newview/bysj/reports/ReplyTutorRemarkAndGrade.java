package com.newview.bysj.reports;

/**
 * 用来封装答辩提问及成绩汇总表的数据
 * Created 2016/6/1,16:01.
 * Author 张战.
 */
public class ReplyTutorRemarkAndGrade {
    //学院
    private String school;
    //专业----
    private String major;
    //学生姓名
    private String studentName;
    //学生学号
    private String studentNo;
    //学生班级
    private String studentClass;
    //标题
    private String title;
    //副标题
    private String subTitle;
    //提问的问题1
    private String questionByTutor_1;
    //提问的问题2
    private String questionByTutor_2;
    //提问的问题3
    private String questionByTutor_3;
    //对问题1回答的评价
    private String responseRemarkByTutor_1;
    //对问题2回答的评价
    private String responseRemarkByTutor_2;
    //对问题3回答的评价
    private String responseRemarkByTutor_3;
    //论文与实物的质量评分
    private Integer qualityScoreByGroupTutor;
    //完成任务书规定的要求与水平评分
    private Integer completenessScoreByGroupTutor;
    //论文内容的答辩陈述评分
    private Integer replyScoreByGroupTutor;
    //回答问题正确性评分
    private Integer correctnessScoreByGroupTutor;
    //答辩小组意见
    private String remarkByGroupTutor;
    //年份
    private Integer year;
    //月份
    private Integer month;
    //天
    private Integer day;
    //老师签名（文字）
    private String signature;
    //老师签名照（图片）
    private String signature_txt;


    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String sutdentNo) {
        this.studentNo = studentNo;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getQuestionByTutor_1() {
        return questionByTutor_1;
    }

    public void setQuestionByTutor_1(String questionByTutor_1) {
        this.questionByTutor_1 = questionByTutor_1;
    }

    public String getQuestionByTutor_2() {
        return questionByTutor_2;
    }

    public void setQuestionByTutor_2(String questionByTutor_2) {
        this.questionByTutor_2 = questionByTutor_2;
    }

    public String getQuestionByTutor_3() {
        return questionByTutor_3;
    }

    public void setQuestionByTutor_3(String questionByTutor_3) {
        this.questionByTutor_3 = questionByTutor_3;
    }

    public String getResponseRemarkByTutor_1() {
        return responseRemarkByTutor_1;
    }

    public void setResponseRemarkByTutor_1(String responseRemarkByTutor_1) {
        this.responseRemarkByTutor_1 = responseRemarkByTutor_1;
    }

    public String getResponseRemarkByTutor_2() {
        return responseRemarkByTutor_2;
    }

    public void setResponseRemarkByTutor_2(String responseRemarkByTutor_2) {
        this.responseRemarkByTutor_2 = responseRemarkByTutor_2;
    }

    public String getResponseRemarkByTutor_3() {
        return responseRemarkByTutor_3;
    }

    public void setResponseRemarkByTutor_3(String responseRemarkByTutor_3) {
        this.responseRemarkByTutor_3 = responseRemarkByTutor_3;
    }

    public Integer getQualityScoreByGroupTutor() {
        return qualityScoreByGroupTutor;
    }

    public void setQualityScoreByGroupTutor(Integer qualityScoreByGroupTutor) {
        this.qualityScoreByGroupTutor = qualityScoreByGroupTutor;
    }

    public Integer getCompletenessScoreByGroupTutor() {
        return completenessScoreByGroupTutor;
    }

    public void setCompletenessScoreByGroupTutor(Integer completenessScoreByGroupTutor) {
        this.completenessScoreByGroupTutor = completenessScoreByGroupTutor;
    }

    public Integer getReplyScoreByGroupTutor() {
        return replyScoreByGroupTutor;
    }

    public void setReplyScoreByGroupTutor(Integer replyScoreByGroupTutor) {
        this.replyScoreByGroupTutor = replyScoreByGroupTutor;
    }

    public Integer getCorrectnessScoreByGroupTutor() {
        return correctnessScoreByGroupTutor;
    }

    public void setCorrectnessScoreByGroupTutor(Integer correctnessScoreByGroupTutor) {
        this.correctnessScoreByGroupTutor = correctnessScoreByGroupTutor;
    }

    public String getRemarkByGroupTutor() {
        return remarkByGroupTutor;
    }

    public void setRemarkByGroupTutor(String remarkByGroupTutor) {
        this.remarkByGroupTutor = remarkByGroupTutor;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSignature_txt() {
        return signature_txt;
    }

    public void setSignature_txt(String signature_txt) {
        this.signature_txt = signature_txt;
    }
}
