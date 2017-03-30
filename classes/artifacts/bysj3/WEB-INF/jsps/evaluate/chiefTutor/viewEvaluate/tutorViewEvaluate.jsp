<%--
  Created by IntelliJ IDEA.
  User: zhan
  Date: 2016/6/5
  Time: 22:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal"
            aria-hidden="true">×
    </button>
    <h4 class="modal-title" id="myModalLabel">
        查看指导老师评审表
        <br>

        题目名称：${graduateProject.title}
        <br>
        班级：${graduateProject.student.studentClass.description}&nbsp;&nbsp;学生姓名：${graduateProject.student.name}&nbsp;&nbsp;学号:${graduateProject.student.no}
    </h4>
</div>
<div class="modal-body">
    <c:set var="graduateProject" value="${graduateProject}"/>
    <table class="table" border="1">
        <tr height="30">
            <td>
                任务完成情况
            </td>
            <td>
                <c:choose>
                    <c:when test="${graduateProject.commentByTutor.tutorEvaluteHasCompleteProject}">
                        <span class="label label-success">已完成</span>
                    </c:when>
                    <c:otherwise>
                        <span class="label label-warning">未完成</span>
                    </c:otherwise>
                </c:choose>
                <%--<form:select
                        name="tutorEvaluteHasCompleteProject"
                        path="commentByTutor.tutorEvaluteHasCompleteProject"
                        id="tutorEvaluteHasCompleteProject" class="combox">
                    <form:option value="true" label="完成"/>
                    <form:option value="false" label="未完成"/>
                </form:select>--%>
            </td>
            <td>
                论文字数
            </td>
            <td>
                ${graduateProject.commentByTutor.tutorEvaluteDissertationWordCount}
                <%--<form:input path="" type="text"
                            class="required digits"/>--%>
            </td>
        </tr>

        <tr height="30">
            <td>
                中期检查报告
            </td>
            <td>
                <c:choose>
                    <c:when test="${graduateProject.commentByTutor.tutorEvaluteHasMiddleExam}">
                        <span class="label label-success">有</span>
                    </c:when>
                    <c:otherwise>
                        <span class="label label-warning">无</span>
                    </c:otherwise>
                </c:choose>
                <%--<form:select
                        path="commentByTutor.tutorEvaluteHasMiddleExam"
                        id="tutorEvaluteHasMiddleExam" class="combox">
                    <form:option value="true" label="有"/>
                    <form:option value="false" label="无"/>
                </form:select>--%>
            </td>
            <td>
                外文资料翻译
            </td>
            <td>
                <c:choose>
                    <c:when test="${graduateProject.commentByTutor.tutorEvaluteHasTranslationMaterail}">
                        <span class="label label-success">有</span>
                    </c:when>
                    <c:otherwise>
                        <span class="label label-warning">无</span>
                    </c:otherwise>
                </c:choose>
                <%--<form:select
                        path="commentByTutor.tutorEvaluteHasTranslationMaterail"
                        id="tutorEvaluteHasTranslationMaterail" class="combox">
                    <form:option value="true" label="有"/>
                    <form:option value="false" label="无"/>
                </form:select>--%>
            </td>
        </tr>
        <tr height="30">
            <td>
                外文资料翻译字数
            </td>
            <td>
                ${graduateProject.commentByTutor.tutorEvaluteHasTranslationWordCount}
                <%--<form:input path=""
                            class="required digits"/>--%>
            </td>
            <td>
                中、英文摘要
            </td>
            <td>
                <c:choose>
                    <c:when test="${graduateProject.commentByTutor.tutorEvaluteHasTwoAbstract}">
                        <span class="label label-success">有</span>
                    </c:when>
                    <c:otherwise>
                        <span class="label label-warning">无</span>
                    </c:otherwise>
                </c:choose>
                <%--<form:select
                        path="commentByTutor.tutorEvaluteHasTwoAbstract"
                        id="tutorEvaluteHasTwoAbstract" class="combox">
                    <form:option value="true" label="有"/>
                    <form:option value="false" label="无"/>
                </form:select>--%>
            </td>
        </tr>
        <%--<tr height="30">
            &lt;%&ndash;<td>
                软、硬件完成情况
            </td>
            <td>
                <form:select
                        path="commentByTutor.tutorEvaluteHasSoftHardWare"
                        id="tutorEvaluteHasSoftHardWare" class="combox">
                    <form:option value="true" label="完成"/>
                    <form:option value="false" label="未完成"/>
                </form:select>
            </td>&ndash;%&gt;
            <td>
                累计旷课时间（小时）
            </td>
            <td>
                <form:input path="commentByTutor.tutorEvaluteAttendance" type="text" value="0"
                            class="required digits"/>
            </td>
        </tr>--%>
        <tr height="30">
            <td colspan="2">
                基本理论、基本知识、基本技能和外语水平
            </td>
            <td colspan="2"> <%--path 对应 commandName所代表的对象的一个属性 --%>
                ${graduateProject.commentByTutor.basicAblityScore}
                <%--<form:select
                        name="commentByTutorBasicAbilityScore"
                        path="commentByTutor.basicAblityScore"
                        id="commentByTutorBasicAbilityScore" class="combox">
                    <form:option value="" label="--请选择--"/>
                    <form:options items="${defaultGrades}"/>
                </form:select>--%>
            </td>
        </tr>
        <tr height="30">
            <td colspan="2">
                工作量、工作态度
            </td>
            <td colspan="2"> <%--path 对应 commandName所代表的对象的一个属性 --%>
                ${graduateProject.commentByTutor.workLoadScore}
                <%--<form:select
                        name="commentByTutorworkLoadScore"
                        path="commentByTutor.workLoadScore"
                        id="commentByTutorworkLoadScore" class="combox">
                    <form:option value="" label="--请选择--"/>
                    <form:options items="${defaultGrades}"/>
                </form:select>--%>
            </td>
        </tr>

        <tr height="30">
            <td colspan="2">
                独立工作能力、分析与解决问题的能力
            </td>
            <td colspan="2"> <%--path 对应 commandName所代表的对象的一个属性 --%>
                ${graduateProject.commentByTutor.workAblityScore}
                <%--<form:select
                        name="commentByTutorworkAbilityScore"
                        path="commentByTutor.workAblityScore"
                        id="commentByTutorworkAbilityScore" class="combox">
                    <form:option value="" label="--请选择--"/>
                    <form:options items="${defaultGrades}"/>
                </form:select>--%>
            </td>
        </tr>

        <tr>
            <td colspan="2">
                完成任务情况及水平
            </td>
            <td colspan="2"> <%--path 对应 commandName所代表的对象的一个属性 --%>
                ${graduateProject.commentByTutor.achievementLevelScore}
                <%--<form:select
                        name="commentByTutorachievementLevelScore"
                        path="commentByTutor.achievementLevelScore"
                        id="commentByTutorachievementLevelScore" class="combox">
                    <form:option value="" label="--请选择--"/>
                    <form:options items="${defaultGrades}"/>
                </form:select>--%>
            </td>
        </tr>
        <tr>
            <td colspan="1">指导老师的评语</td>
            <td colspan="3">
                <textarea class="form-control" readonly>${graduateProject.commentByTutor.remark}</textarea>
            </td>
        </tr>
    </table>
    </table>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-default"
            data-dismiss="modal">关闭
    </button>
</div>

