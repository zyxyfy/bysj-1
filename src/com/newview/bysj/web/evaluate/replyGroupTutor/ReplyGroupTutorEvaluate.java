package com.newview.bysj.web.evaluate.replyGroupTutor;

import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.domain.ReplyGroup;
import com.newview.bysj.domain.ReplyGroupMemberScore;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.reports.ReplyTutorRemarkAndGrade;
import com.newview.bysj.web.baseController.BaseController;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created 2016/6/2,9:39.
 * Author 张战.
 */
@Controller
@RequestMapping("evaluate/replyGroupTutor")
public class ReplyGroupTutorEvaluate extends BaseController {

    /**
     * 查看当前课题所在答辩小组的答辩老师的评审
     *
     * @param projectId 要查看课题的id
     * @param modelMap  存储需要在jsp查看的数据
     * @return jsp
     */
    @RequestMapping(value = "/viewReplyGroupTutorEvaluate.html", method = RequestMethod.GET)
    public String viewReplyGroupTutorEvaluate(Integer projectId, ModelMap modelMap) {

        //以下的四个参数用于临时存储从数据库获取的数据，进行非空判断并添加到集合中
        Integer qualityScoreByGroupTutor;
        Integer completenessScoreByGroupTutor;
        Integer replyScoreByGroupTutor;
        Integer correctnessScoreByGroupTutor;

        //以下四个集合用于临时存储同一个评分选项中各个老师的评分，便于计算平均分
        List<Integer> qualityScoreByGroupTutorList = new ArrayList<>();
        List<Integer> completenessScoreByGroupTutorList = new ArrayList<>();
        List<Integer> replyScoreByGroupTutorList = new ArrayList<>();
        List<Integer> correctnessScoreByGroupTutorList = new ArrayList<>();

        //获取要查看的课题
        GraduateProject graduateProject = graduateProjectService.findById(projectId);
        //获取该课题所在的答辩小组
        ReplyGroup replyGroup = graduateProject.getReplyGroup();
        //如果该课题所在的答辩小组的老师都已打分，则计算各项的平均分
        if (replyGroup != null) {
            //获取小组答辩成员的分数
            List<ReplyGroupMemberScore> replyGroupMemberScoreList = replyGroup.getReplyGroupMemberScoreList();
            if (replyGroupMemberScoreList != null) {
                for (ReplyGroupMemberScore replyGroupMemberScore : replyGroupMemberScoreList) {
                    //获取论文与实物的质量评分，如果不为空则添加到集合中去
                    qualityScoreByGroupTutor = replyGroupMemberScore.getQualityScoreByGroupTutor();
                    if (qualityScoreByGroupTutor != null) {
                        qualityScoreByGroupTutorList.add(qualityScoreByGroupTutor);
                    }
                    //获取完成任务书规定的要求与水平评分，如果不为空则添加到集合中去
                    completenessScoreByGroupTutor = replyGroupMemberScore.getCompletenessScoreByGroupTutor();
                    if (completenessScoreByGroupTutor != null) {
                        completenessScoreByGroupTutorList.add(completenessScoreByGroupTutor);
                    }
                    //获取论文内容的答辩陈述评分，如果不为空则添加到集合中
                    replyScoreByGroupTutor = replyGroupMemberScore.getReplyScoreByGroupTutor();
                    if (replyScoreByGroupTutor != null) {
                        replyScoreByGroupTutorList.add(replyScoreByGroupTutor);
                    }
                    //    获取回答问题的正确性评分，如果不为空则添加到集合中
                    correctnessScoreByGroupTutor = replyGroupMemberScore.getCorrectnessScoreByGroupTutor();
                    if (correctnessScoreByGroupTutor != null) {
                        correctnessScoreByGroupTutorList.add(correctnessScoreByGroupTutor);
                    }
                }
                //计算各项的平均分，并添加到Model中,在jsp中获取
                modelMap.put("avgQualityScoreByGroupTutor", this.getAvgByList(qualityScoreByGroupTutorList));
                modelMap.put("avgCompletenessScoreByGroupTutor", this.getAvgByList(completenessScoreByGroupTutorList));
                modelMap.put("avgReplyScoreByGroupTutor", this.getAvgByList(replyScoreByGroupTutorList));
                modelMap.put("avgCorrectnessScoreByGroupTutor", this.getAvgByList(correctnessScoreByGroupTutorList));

            }
        }
        modelMap.put("graduateProject", graduateProject);
        modelMap.put("replyGroup", replyGroup);
        return "evaluate/replyGroupTutorEvaluate/replyTutorEvaluateList";
    }

    //根据集合中的数据，计算出此集合中数值的平均值，小数点后面保留一位小数
    public Double getAvgByList(List<Integer> intList) {
        /*double avgNum = 0.0;
        for (Integer num : intList) {
            avgNum = avgNum + num;
        }
        //
        double v = avgNum / intList.size() + avgNum % intList.size();
        BigDecimal bd = new BigDecimal(v);
        //使用四舍五入来取值
        bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
        String str = bd.toString();*/
        Integer sum = 0;
        for (Integer num : intList) {
            sum = sum + num;
        }
        //被除数
        BigDecimal bigDecimal = new BigDecimal(sum);
        //除数
        BigDecimal divisor = new BigDecimal(intList.size());
        if (divisor.intValue() == 0) {
            return null;
        }
        //得到的商,结果四舍五入，保留一位小数
        BigDecimal newNum = bigDecimal.divide(divisor, 1, BigDecimal.ROUND_HALF_UP);
        return Double.parseDouble(newNum.toString());
    }

    /**
     * 生成答辩小组成员对学生的评审表
     *
     * @param model                   用于存储数据
     * @param replyGroupMemberScoreId 需要查看的答辩小组成员的评审表
     * @param httpSession             用于获取路径
     * @return 输出报表
     */
    @RequestMapping("viewReport.html")
    public String viewReport(Model model, Integer replyGroupMemberScoreId, HttpSession httpSession) {
        //获取要打印报表的答辩小组成员的打分情况
        ReplyGroupMemberScore replyGroupMemberScore = replyGroupMemberScoreService.findById(replyGroupMemberScoreId);
        //创建一个对象，用于填充报表的数据
        ReplyTutorRemarkAndGrade replyTutorRemarkAndGrade = new ReplyTutorRemarkAndGrade();
        List<ReplyTutorRemarkAndGrade> replyTutorRemarkAndGradeList = new ArrayList<>();
        //设置所在的学院
        replyTutorRemarkAndGrade.setSchool(replyGroupMemberScore.getGraduateProject().getStudent().getStudentClass().getMajor().getDepartment().getSchool().getDescription());
        //设置所在的专业
        replyTutorRemarkAndGrade.setMajor(replyGroupMemberScore.getGraduateProject().getStudent().getStudentClass().getMajor().getDescription());
        //设置学生的姓名
        replyTutorRemarkAndGrade.setStudentName(replyGroupMemberScore.getGraduateProject().getStudent().getName());
        //学生学号
        replyTutorRemarkAndGrade.setStudentNo(replyGroupMemberScore.getGraduateProject().getStudent().getNo());
        //学生班级
        replyTutorRemarkAndGrade.setStudentClass(replyGroupMemberScore.getGraduateProject().getStudent().getStudentClass().getDescription());
        //课题标题
        replyTutorRemarkAndGrade.setTitle(replyGroupMemberScore.getGraduateProject().getTitle());
        //副标题
        replyTutorRemarkAndGrade.setSubTitle(replyGroupMemberScore.getGraduateProject().getSubTitle());
        //提问的问题1
        replyTutorRemarkAndGrade.setQuestionByTutor_1(replyGroupMemberScore.getQuestionByTutor_1());
        //提问的问题2
        replyTutorRemarkAndGrade.setQuestionByTutor_2(replyGroupMemberScore.getQuestionByTutor_2());
        //提问的问题3
        replyTutorRemarkAndGrade.setQuestionByTutor_3(replyGroupMemberScore.getQuestionByTutor_3());
        //对问题1的评价
        replyTutorRemarkAndGrade.setResponseRemarkByTutor_1(replyGroupMemberScore.getResponseRemarkByTutor_1());
        //对问题2的评价
        replyTutorRemarkAndGrade.setResponseRemarkByTutor_2(replyGroupMemberScore.getResponseRemarkByTutor_2());
        //对问题3的评价
        replyTutorRemarkAndGrade.setResponseRemarkByTutor_3(replyGroupMemberScore.getResponseRemarkByTutor_3());
        //论文与实物质量评分
        replyTutorRemarkAndGrade.setQualityScoreByGroupTutor(replyGroupMemberScore.getQualityScoreByGroupTutor());
        //完成任务的要求与水平评分
        replyTutorRemarkAndGrade.setCompletenessScoreByGroupTutor(replyGroupMemberScore.getCompletenessScoreByGroupTutor());
        //论文内容的答辩陈述评分
        replyTutorRemarkAndGrade.setReplyScoreByGroupTutor(replyGroupMemberScore.getReplyScoreByGroupTutor());
        //回答问题的正确性评分
        replyTutorRemarkAndGrade.setCorrectnessScoreByGroupTutor(replyGroupMemberScore.getCorrectnessScoreByGroupTutor());
        //答辩小组意见
        replyTutorRemarkAndGrade.setRemarkByGroupTutor(replyGroupMemberScore.getRemarkByGroupTutor());
        //获取审核的日期
        Calendar calendar = replyGroupMemberScore.getSubmittedDate();
        //年份
        replyTutorRemarkAndGrade.setYear(CommonHelper.getYearByCalendar(calendar));
        //月份
        replyTutorRemarkAndGrade.setMonth(CommonHelper.getMonthByCalendar(calendar));
        //天数
        replyTutorRemarkAndGrade.setDay(CommonHelper.getDayOfMontyByCalendar(calendar));
        //获取系统的根路径，用来得到没有上传签名照时的空白照片
        String basePath = CommonHelper.getRootPath(httpSession);
        //老师有没有上传签名照片
        if (replyGroupMemberScore.getTutor().getSignPictureURL() == null) {
            replyTutorRemarkAndGrade.setSignature(basePath + "/images/signature/open.png");
            replyTutorRemarkAndGrade.setSignature_txt(replyGroupMemberScore.getTutor().getName());
        } else {
            replyTutorRemarkAndGrade.setSignature(CommonHelper.getUploadPath(httpSession) + replyGroupMemberScore.getTutor().getSignPictureURL());
            replyTutorRemarkAndGrade.setSignature_txt(" ");
        }
        replyTutorRemarkAndGradeList.add(replyTutorRemarkAndGrade);
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(replyTutorRemarkAndGradeList);
        //设置路径
        model.addAttribute("url", "/WEB-INF/reports/ReplyQuestion_Grade.jrxml");
        model.addAttribute("format", "pdf");
        model.addAttribute("jrMainDataSource", jrDataSource);
        return "iReportView";
    }

}
