<%--
  Created by IntelliJ IDEA.
  User: 张战
  Date: 2016/5/15
  Time: 18:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>

<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal"
            aria-hidden="true">×
    </button>
    <h4 class="modal-title" id="myModalLabel">
        ${project.student.name}的成绩
    </h4>

    <p style="color: red">（各项满分10分）</p>
</div>
<div class="modal-body">
    <c:set value="${project.commentByTutor}" var="tutorComment"/>
    <c:set value="${project.commentByReviewer}" var="reviewerComment"/>
    <c:set value="${project.commentByGroup}" var="groupComment"/>
    <%--指导老师评审commentByTutor--%>
    <table
            class="table table-striped table-bordered table-hover datatable">
        <thead>
        <tr>
            <th>评审类型</th>
            <th>条目</th>
            <th>评分类型</th>
            <th>分数</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td rowspan="4">指导老师评审</td>
            <td>1</td>
            <td>基本理论、基本知识、基本技能和外语水平</td>
            <%--基本理论、基本知识、基本技能和外语水平--%>
            <td>
                <c:choose>
                    <c:when test="${empty tutorComment.basicAblityScore}">
                        未评分
                    </c:when>
                    <c:otherwise>
                        ${tutorComment.basicAblityScore}
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td>2</td>
            <td>工作量、工作态度</td>
            <td>
                <c:choose>
                    <c:when test="${empty tutorComment.workLoadScore}">
                        未评分
                    </c:when>
                    <c:otherwise>
                        ${tutorComment.workLoadScore}
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td>3</td>
            <td>独立工作能力、分析与解决问题的能力</td>
            <td>
                <c:choose>
                    <c:when test="${empty tutorComment.workAblityScore}">
                        未评分
                    </c:when>
                    <c:otherwise>
                        ${tutorComment.workAblityScore}
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td>4</td>
            <td>完成任务情况及水平</td>
            <td>
                <c:choose>
                    <c:when test="${empty tutorComment.achievementLevelScore}">
                        未评分
                    </c:when>
                    <c:otherwise>
                        ${tutorComment.achievementLevelScore}
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td rowspan="2">
                评阅人评审
            </td>
            <td>5</td>
            <td>设计（论文）质量评分</td>
            <td>
                <c:choose>
                    <c:when test="${empty reviewerComment.qualityScore}">
                        未评分
                    </c:when>
                    <c:otherwise>
                        ${reviewerComment.qualityScore}
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td>6</td>
            <td>成果的技术水平</td>
            <td>
                <c:choose>
                    <c:when test="${empty reviewerComment.achievementScore}">
                        未评分
                    </c:when>
                    <c:otherwise>
                        ${reviewerComment.achievementScore}
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td rowspan="4">答辩小组评审</td>
            <td>7</td>
            <td>论文与实物的质量</td>
            <td>
                <c:choose>
                    <c:when test="${empty groupComment.qualityScore}">
                        未评分
                    </c:when>
                    <c:otherwise>
                        ${groupComment.qualityScore}
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td>8</td>
            <td>完成任务书规定的要求与水平评分</td>
            <td>
                <c:choose>
                    <c:when test="${empty groupComment.completenessScore}">
                        未评分
                    </c:when>
                    <c:otherwise>
                        ${groupComment.completenessScore}
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td>9</td>
            <td>论文内容的答辩陈述评分</td>
            <td>
                <c:choose>
                    <c:when test="${empty groupComment.replyScore}">
                        未评分
                    </c:when>
                    <c:otherwise>
                        ${groupComment.replyScore}
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td>10</td>
            <td>回答问题的正确性评分</td>
            <td>
                <c:choose>
                    <c:when test="${empty groupComment.correctnessSocre}">
                        未评分
                    </c:when>
                    <c:otherwise>
                        ${groupComment.correctnessSocre}
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td colspan="3" align="center">总分</td>
            <td>
                ${project.getTotalScores()}
            </td>
        </tr>
        <tr>
            <td colspan="4" align="center">
                <c:if test="${project.commentByGroup==null||project.commentByGroup.submittedDate==null||project.commentByReviewer==null||project.commentByReviewer.submittedDate==null||project.commentByTutor==null||project.commentByTutor.submittedDate==null}">
                    <span style="color: grey">(成绩评价未完成，不能打印)</span>
                </c:if>
            </td>
        </tr>
        <tr>
            <c:choose>
                <c:when test="${project.commentByGroup==null||project.commentByGroup.submittedDate==null||project.commentByReviewer==null||project.commentByReviewer.submittedDate==null||project.commentByTutor==null||project.commentByTutor.submittedDate==null}">
                    <td colspan="4" align="center"><a disabled="true" target="_blank" class="btn btn-primary"
                                                      href="<%=basePath%>student/viewReport.html?projectId=${project.id}"><i
                            class="icon-print"></i> 打印</a></td>
                </c:when>
                <c:otherwise>
                    <td colspan="4" align="center"><a target="_blank" class="btn btn-primary"
                                                      href="<%=basePath%>student/viewReport.html?projectId=${project.id}"><i
                            class="icon-print"></i> 打印</a></td>
                </c:otherwise>
            </c:choose>
        </tr>
        </tbody>
    </table>
</div>
