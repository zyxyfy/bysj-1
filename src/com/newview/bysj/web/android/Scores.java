package com.newview.bysj.web.android;

/**
 * 每个答辩成员对课题的打分
 * Created 2016/5/1,11:10.
 * Author 张战.
 */
public class Scores {

    /**
     * 只给以下的属性提供的get方法，并不需要提供set方法
     */

    private Integer studentId;
    private Integer graduateProjectId;
    //完成任务规定的要求水平得分
    private Integer completenessScoreByGroup;
    //回答问题的正确性评分
    private Integer correctnessScoreByGroup;
    //论文实物的质量性评分qualityScoreBtGroup
    private Integer qualityScoreByGroup;
    //论文内容的答辩陈述评分
    private Integer replyScoreByGroup;
    //每次修改成绩时改变
    private Integer tutor_id;
    //答辩老师的评语
    private String remarkByGroupTutor;
    //所在答辩小组的id
    private Integer replyGroup_id;
    private String questionByTutor_1;
    private String questionByTutor_2;
    private String questionByTutor_3;
    private String responseRemarkByTutor_1;
    private String responseRemarkByTutor_2;
    private String responseRemarkByTutor_3;
    private String studentResponse_1;
    private String studentResponse_2;
    private String studentResponse_3;

    public Scores() {
    }


    public Integer getStudentId() {
        return studentId;
    }

    public Integer getGraduateProjectId() {
        return graduateProjectId;
    }

    public Integer getCompletenessScoreByGroup() {
        return completenessScoreByGroup;
    }

    public Integer getCorrectnessScoreByGroup() {
        return correctnessScoreByGroup;
    }

    public Integer getQualityScoreByGroup() {
        return qualityScoreByGroup;
    }

    public Integer getReplyScoreByGroup() {
        return replyScoreByGroup;
    }


    public Integer getTutor_id() {
        return tutor_id;
    }

    @Override
    public String toString() {
        return "Scores{" +
                ", studentId=" + studentId +
                ", tutor_id=" + tutor_id +
                ", graduateProjectId=" + graduateProjectId +
                ", completenessScoreByGroup=" + completenessScoreByGroup +
                ", correctnessScoreByGroup=" + correctnessScoreByGroup +
                ", qualityScoreByGroup=" + qualityScoreByGroup +
                ", replyScoreByGroup=" + replyScoreByGroup +
                ", remarkByGroupTutor='" + remarkByGroupTutor + '\'' +
                ", replyGroup_id=" + replyGroup_id +
                '}';
    }
    /*public void setQualified(boolean qualified) {
        this.qualified = qualified;
    }*/

    public Integer getReplyGroup_id() {
        return replyGroup_id;
    }

    public String getQuestionByTutor_1() {
        return questionByTutor_1;
    }

    public String getQuestionByTutor_2() {
        return questionByTutor_2;
    }

    public String getQuestionByTutor_3() {
        return questionByTutor_3;
    }

    public String getResponseRemarkByTutor_1() {
        return responseRemarkByTutor_1;
    }

    public String getResponseRemarkByTutor_2() {
        return responseRemarkByTutor_2;
    }

    public String getResponseRemarkByTutor_3() {
        return responseRemarkByTutor_3;
    }

    public String getStudentResponse_1() {
        return studentResponse_1;
    }

    public String getStudentResponse_2() {
        return studentResponse_2;
    }

    public String getStudentResponse_3() {
        return studentResponse_3;
    }

    public String getRemarkByGroupTutor() {
        return remarkByGroupTutor;
    }
}
