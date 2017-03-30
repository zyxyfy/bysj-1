<%--
  Created by IntelliJ IDEA.
  User: 张战
  Date: 2016/4/8
  Time: 15:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>
<div class="modal-header">
    <h4 class="modal-title" id="myModalLabel">
        附表8：
        山东建筑大学毕业设计（论文）评阅人评审表
        <br>
        题目名称：${graduateProject.title}
        <br>
        班级：${graduateProject.student.studentClass.description}&nbsp;&nbsp;学生姓名：${graduateProject.student.name}&nbsp;&nbsp;学号:${graduateProject.student.no}
    </h4>
</div>
<div class="modal-body">
    <table class="table" border="1" width="500px">
        <c:set var="graduateProject" value="${graduateProject}"/>
        <tr>
            <td>
                难易程度
            </td>
            <td>
                <c:choose>
                    <c:when test="${graduateProject.commentByReviewer.reviewerEvaluationDifficulty=='2'}">
                        <span class="label label-success">适中</span>
                    </c:when>
                    <c:when test="${graduateProject.commentByReviewer.reviewerEvaluationDifficulty=='1'}">
                        <span class="label label-warning">偏难</span>
                    </c:when>
                    <c:when test="${graduateProject.commentByReviewer.reviewerEvaluationDifficulty=='3'}">
                        <span class="label label-warning">偏易</span>
                    </c:when>
                    <c:otherwise>
                        <span class="label label-danger">过易</span>
                    </c:otherwise>
                </c:choose>
                <%--<form:select
                        name="reviewerEvaluationDifficulty"
                        path="commentByReviewer.reviewerEvaluationDifficulty"
                        id="reviewerEvaluationDifficulty" class="combox">
                  <form:option value="2" label="适中"/>
                  <form:option value="1" label="偏难"/>
                  <form:option value="3" label="偏易"/>
                  <form:option value="4" label="过易"/>
                </form:select>--%>
            </td>
            <td>
                工作量
            </td>
            <td>
                <c:choose>
                    <c:when test="${graduateProject.commentByReviewer.reviewerEvaluationWordload=='2'}">
                        <span class="label label-success">适中</span>
                    </c:when>
                    <c:when test="${graduateProject.commentByReviewer.reviewerEvaluationWordload=='1'}">
                        <span class="label label-warning">偏大</span>
                    </c:when>
                    <c:when test="${graduateProject.commentByReviewer.reviewerEvaluationWordload=='3'}">
                        <span class="label label-warning">偏小</span>
                    </c:when>
                    <c:otherwise>
                        <span class="label label-danger">过少</span>
                    </c:otherwise>
                </c:choose>
                <%--<form:select
                        name=""
                        path="commentByReviewer.reviewerEvaluationWordload"
                        id="reviewerEvaluationWordload" class="combox">
                  <form:option value="2" label="适中"/>
                  <form:option value="1" label="偏大"/>
                  <form:option value="3" label="便少"/>
                  <form:option value="4" label="过少"/>
                </form:select>--%>
            </td>
        </tr>
        <tr>
            <td>
                说明书（论文）</br>版面质量
            </td>
            <td>
                <c:choose>
                    <c:when test="${graduateProject.commentByReviewer.reviewerEvaluationPrintingQuality=='2'}">
                        <span class="label label-success">良</span>
                    </c:when>
                    <c:when test="${graduateProject.commentByReviewer.reviewerEvaluationPrintingQuality=='1'}">
                        <span class="label label-success">优</span>
                    </c:when>
                    <c:when test="${graduateProject.commentByReviewer.reviewerEvaluationPrintingQuality=='3'}">
                        <span class="label label-success">中</span>
                    </c:when>
                    <c:otherwise>
                        <span class="label label-warning">差</span>
                    </c:otherwise>
                </c:choose>
                <%--<form:select
                        name=""
                        path="commentByReviewer.reviewerEvaluationPrintingQuality"
                        id="reviewerEvaluationPrintingQuality" class="combox">
                  <form:option value="2" label="良"/>
                  <form:option value="1" label="优"/>
                  <form:option value="3" label="中"/>
                  <form:option value="4" label="差"/>
                </form:select>--%>
            </td>
            <td>
                图样质量
            </td>
            <td>
                <c:choose>
                    <c:when test="${graduateProject.commentByReviewer.reviewerEvaluationDiagramQuality=='2'}">
                        <span class="label label-success">良</span>
                    </c:when>
                    <c:when test="${graduateProject.commentByReviewer.reviewerEvaluationDiagramQuality=='1'}">
                        <span class="label label-success">优</span>
                    </c:when>
                    <c:when test="${graduateProject.commentByReviewer.reviewerEvaluationDiagramQuality=='3'}">
                        <span class="label label-success">中</span>
                    </c:when>
                    <c:otherwise>
                        <span class="label label-warning">差</span>
                    </c:otherwise>
                </c:choose>
                <%--<form:select
                        name=""
                        path="commentByReviewer.reviewerEvaluationDiagramQuality"
                        id="reviewerEvaluationDiagramQuality" class="combox">
                  <form:option value="2" label="良"/>
                  <form:option value="1" label="优"/>
                  <form:option value="3" label="中"/>
                  <form:option value="4" label="差"/>
                </form:select>--%>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                实物性能
            </td>
            <td colspan="2">
                <c:choose>
                    <c:when test="${graduateProject.commentByReviewer.reviewerEvaluationProductQuality=='1'}">
                        <span class="label label-success">符合指标要求</span>
                    </c:when>
                    <c:when test="${graduateProject.commentByReviewer.reviewerEvaluationProductQuality=='2'}">
                        <span class="label label-success">基本符合指标要求</span>
                    </c:when>
                    <c:when test="${graduateProject.commentByReviewer.reviewerEvaluationProductQuality=='3'}">
                        <span class="label label-warning">较差</span>
                    </c:when>
                </c:choose>
                <%--<form:select
                        name=""
                        path="commentByReviewer.reviewerEvaluationProductQuality"
                        id="reviewerEvaluationProductQuality" class="combox">
                  <form:option value="1" label="符合指标要求"/>
                  <form:option value="2" label="基本符合指标要求"/>
                  <form:option value="3" label="较差"/>
                </form:select>--%>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                论文（设计）质量评分（正确性、条理性、规范性、</br>合理性、清晰、工作量）
            </td>
            <td colspan="2"> <%--path 对应 commandName所代表的对象的一个属性 --%>
                ${graduateProject.commentByReviewer.qualityScore}
                <%--<form:select
                        name="commentByReviewerQualityScore"
                        path="commentByReviewer.qualityScore"
                        id="commentByReviewerQualityScore" class="combox">
                  <form:option value="" label="--请选择--"/>
                  <form:options items="${defaultGrades}"/>
                </form:select>--%>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                成果的技术水平（应用性和创新性）
            </td>
            <td colspan="2"> <%--path 对应 commandName所代表的对象的一个属性 --%>
                ${graduateProject.commentByReviewer.achievementScore}
                <%--<form:select
                        name="commentByReviewerAchievementScore"
                        path="commentByReviewer.achievementScore"
                        id="commentByReviewerAchivementScore" class="combox">
                  <form:option value="" label="--请选择--"/>
                  <form:options items="${defaultGrades}"/>
                </form:select>--%>
            </td>
        </tr>
        <tr>
            <td colspan="1">评阅人的评语</td>
            <td colspan="3">
                <textarea readonly class="form-control">${graduateProject.commentByReviewer.remarkByReviewer}</textarea>
            </td>
        </tr>
        <tr>
            <td colspan="1">评审结论：</td>
            <td colspan="3">
                <c:choose>
                    <c:when test="${graduateProject.commentByReviewer.qualifiedByReviewer}">
                        <label class="label label-success">通过评审</label>
                    </c:when>
                    <c:otherwise>
                        <label class="label label-warning">未通过</label>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </table>
</div>
<div class="modal-footer">
    <a class="btn btn-default" href="<%=basePath%>evaluate/replyGroup/projectsToEvaluate.html">关闭</a>
</div>



