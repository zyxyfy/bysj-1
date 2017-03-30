<%--
  Created by IntelliJ IDEA.
  User: 张战
  Date: 2016/5/5
  Time: 19:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>

<div class="container-fluid" style="width: 100%">
    <div class="row-fluid">
        <ul class="breadcrumb">
            <li>查看论文成绩</li>
            <li>查看资料和成绩<span class="divider">/</span>
            </li>
        </ul>
    </div>

    <div class="row-fluid">
        <c:set value="${graduateProject.commentByTutor}" var="projectCommentTutor"/>
        <c:set value="${graduateProject.commentByReviewer}" var="projectCommentReviewer"/>
        <c:set value="${graduateProject.commentByGroup}" var="projectCommentGroup"/>
        <table
                class="table table-striped table-bordered table-hover datatable">
            <tr>
                <td rowspan="4">指导教师评分</td>
                <td>1</td>
                <td> 基本理论、基本知识、基本技能和外语水平（0-10分）</td>
                <td>${empty (projectCommentTutor.basicAblityScore)?"未评分":projectCommentTutor.basicAblityScore}</td>
            </tr>
            <tr>
                <td>2</td>
                <td>工作量、工作态度（0-10分）</td>
                <td>${empty (projectCommentTutor.workLoadScore)?"未评分":projectCommentTutor.workLoadScore}</td>
            </tr>
            <tr>
                <td>3</td>
                <td>独立工作能力、分析与解决问题的能力（0-10分）</td>
                <td>${empty (projectCommentTutor.workAblityScore)?"未评分":projectCommentTutor.workAblityScore}</td>
            </tr>
            <tr>
                <td>4</td>
                <td>完成任务情况及水平（0-10分）</td>
                <td>${empty (projectCommentTutor.achievementLevelScore)?"未评分":projectCommentTutor.achievementLevelScore}</td>
            </tr>
            <tr>
                <td rowspan="2">评阅人评分</td>
                <td>1</td>
                <td>设计（论文）质量评分（0-10分）</td>
                <td>${empty (projectCommentReviewer.qualityScore)?"未评分":projectCommentReviewer.qualityScore}</td>
            </tr>
            <tr>
                <td>2</td>
                <td>成果的技术水平（实用性和创新性）（0-10分）</td>
                <td>${empty (projectCommentReviewer.achievementScore)?"未评分":projectCommentReviewer.achievementScore}</td>
            </tr>
            <tr>
                <td rowspan="4">答辩小组评分</td>
                <td>1</td>
                <td>论文与实物的质量 评分（0-10分）</td>
                <td>${projectCommentGroup.qualityScore==0.0?"未评分":projectCommentGroup.qualityScore}</td>
            </tr>
            <tr>
                <td>2</td>
                <td>完成任务书规定的要求与水平评分（0-10分）</td>
                <td>${projectCommentGroup.completenessScore==0.0?"未评分":projectCommentGroup.completenessScore}</td>
            </tr>
            <tr>
                <td>3</td>
                <td>论文内容的答辩陈述评分（0-10分）</td>
                <td>${projectCommentGroup.replyScore==0.0?"未评分":projectCommentGroup.replyScore}</td>
            </tr>
            <tr>
                <td>4</td>
                <td>回答问题的正确性评分（0-10分）</td>
                <td>${projectCommentGroup.correctnessSocre==0.0?"未评分":projectCommentGroup.correctnessSocre}</td>
            </tr>
            <tr>
                <td colspan="2">总分(满分：100)</td>
                <td colspan="2">${graduateProject.getTotalScores()==0.0?"未评分":graduateProject.getTotalScores()}</td>
            </tr>
            <tr>

                <c:choose>
                    <c:when test="${empty graduateProject.commentByTutor||empty graduateProject.commentByReviewer||empty graduateProject.commentByGroup}">
                        <td colspan="4" align="center">
                            <span style="color: red">评分未完成，不能打印</span>
                        </td>
                    </c:when>
                    <c:otherwise>
                        <td colspan="4" align="center">
                            <a class="btn btn-success " target="_blank"
                               href="<%=basePath%>student/viewReport.html?projectId=${graduateProject.id}"><i
                                    class="icon-print"></i> 打印</a>
                        </td>
                    </c:otherwise>
                </c:choose>
            </tr>
        </table>
    </div>
</div>
