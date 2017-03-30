<%--
  Created by IntelliJ IDEA.
  User: 张战
  Date: 2016/6/2
  Time: 11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>

<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal"
            aria-hidden="true">×
    </button>
    <h4 class="modal-title" id="myModalLabel1">
        附表11：
        山东建筑大学毕业设计（论文）答辩提问及成绩汇总表
        <br>
        题目名称：${graduateProject.title}
        <br>
        班级：${graduateProject.student.studentClass.description}&nbsp;&nbsp;学生姓名：${graduateProject.student.name}&nbsp;&nbsp;学号:${graduateProject.student.no}
        ${tutor}的评审
    </h4>
</div>
<div class="modal-body">
    <c:set value="${replyGroupMemberScore}" var="replyGroupMemberScore"/>
    <table class="table">
        <tbody>
        <tr>
            <td>提问的内容1</td>
            <td>
                <c:choose>
                    <c:when test="${empty replyGroupMemberScore.questionByTutor_1}">
                        <p style="color: red">无</p>
                    </c:when>
                    <c:otherwise>
                        ${replyGroupMemberScore.questionByTutor_1}
                    </c:otherwise>
                </c:choose>
            </td>
            <td>答辩学生回答的评语1</td>
            <td>
                <c:choose>
                    <c:when test="${empty replyGroupMemberScore.responseRemarkByTutor_1}">
                        <p style="color: red">无</p>
                    </c:when>
                    <c:otherwise>
                        ${replyGroupMemberScore.responseRemarkByTutor_1}
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td>提问的内容2</td>
            <td>
                <c:choose>
                    <c:when test="${empty replyGroupMemberScore.questionByTutor_2}">
                        <p style="color: red">无</p>
                    </c:when>
                    <c:otherwise>
                        ${replyGroupMemberScore.questionByTutor_2}
                    </c:otherwise>
                </c:choose>
            </td>
            <td>答辩学生回答的评语2</td>
            <td>
                <c:choose>
                    <c:when test="${empty replyGroupMemberScore.responseRemarkByTutor_2}">
                        <p style="color: red">无</p>
                    </c:when>
                    <c:otherwise>
                        ${replyGroupMemberScore.responseRemarkByTutor_2}
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td>提问的内容3</td>
            <td>
                <c:choose>
                    <c:when test="${empty replyGroupMemberScore.questionByTutor_3}">
                        <p style="color: red">无</p>
                    </c:when>
                    <c:otherwise>
                        ${replyGroupMemberScore.questionByTutor_3}
                    </c:otherwise>
                </c:choose>
            </td>
            <td>答辩学生回答的评语3</td>
            <td>
                <c:choose>
                    <c:when test="${empty replyGroupMemberScore.responseRemarkByTutor_3}">
                        <p style="color: red">无</p>
                    </c:when>
                    <c:otherwise>
                        ${replyGroupMemberScore.responseRemarkByTutor_3}
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td colspan="1.5">论文与实物的质量评分（0-10分）</td>
            <td colspan="0.5">${replyGroupMemberScore.qualityScoreByGroupTutor}</td>
            <td colspan="1.5">完成任务书规定的要求与水平评分（0-10分)</td>
            <td colspan="0.5">${replyGroupMemberScore.completenessScoreByGroupTutor}</td>
        </tr>
        <tr>
            <td colspan="1.5">论文内容的答辩陈述评分（0-10分）</td>
            <td colspan="0.5">${replyGroupMemberScore.replyScoreByGroupTutor}</td>
            <td colspan="1.5">回答问题的正确性评分（0-10分）</td>
            <td colspan="0.5">${replyGroupMemberScore.correctnessScoreByGroupTutor}</td>
        </tr>
        </tbody>
    </table>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-default"
            data-dismiss="modal">关闭
    </button>
</div>

