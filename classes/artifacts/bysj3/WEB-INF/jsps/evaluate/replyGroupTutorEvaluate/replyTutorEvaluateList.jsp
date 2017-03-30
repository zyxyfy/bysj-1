<%--
  Created by IntelliJ IDEA.
  User: 张战
  Date: 2016/6/1
  Time: 20:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>
<div class="container-fluid" style="width: 100%">
    <div class="row-fluid">
        <ul class="breadcrumb">
            <li>答辩小组意见表</li>
            <li>查看各答辩小组老师评审表<span class="divider">/</span>
            </li>
        </ul>
    </div>

    <a class="btn btn-primary" href="<%=basePath%> evaluate/replyGroup/projectsToEvaluate.html"><i
            class="icon icon-backward"></i> 返回</a>
    <hr>
    <label class="label label-primary">所在答辩小组名称：</label>&nbsp;${replyGroup.description}&nbsp;&nbsp;<label
        class="label label-primary">学生：</label>&nbsp;${graduateProject.student.name}&nbsp;&nbsp;<label
        class="label label-primary">课题名称：</label>&nbsp;<c:choose><c:when
        test="${empty graduateProject.title}">空</c:when><c:otherwise>${graduateProject.title}</c:otherwise></c:choose>----<c:choose><c:when
        test="${empty graduateProject.subTitle}"></c:when><c:otherwise>${graduateProject.subTitle}</c:otherwise></c:choose>
    <hr>
    <p style="color: red">答辩小组各答辩老师的评审汇总表</p>
    <table class="table table-striped table-bordered table-hover datatable">
        <thead>
        <tr>
            <th>答辩老师</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${empty graduateProject.replyGroup}">
                <div class="alert alert-warning alert-dismissable" role="alert">
                    <button class="close" type="button" data-dismiss="alert">&times;</button>
                    没有可以显示的信息
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach items="${graduateProject.replyGroup.members}" var="tutor">
                    <tr>
                            <%--答辩老师和评审之前要对应--%>
                        <td>
                                ${tutor.name}
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${empty graduateProject.replyGroupMemberScores}">
                                    <p style="color: red">未评审</p>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${graduateProject.replyGroupMemberScores}"
                                               var="replyGroupMemberScore">
                                        <c:choose>
                                            <%--用来判断老师的姓名和答辩小组成员老师的姓名相等，则显示出来--%>
                                            <c:when test="${fn:startsWith(replyGroupMemberScore.tutor.name,tutor.name)&&fn:endsWith(replyGroupMemberScore.tutor.name,tutor.name)}">
                                                <a class="btn btn-default" data-toggle="modal" data-target="#showDetail"
                                                   href=""><i class="icon icon-coffee"></i> 查看</a>
                                            </c:when>
                                            <c:otherwise>
                                                <p style="color: red">未评审</p>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
    <hr>
    <p style="color: red">建议各项评分（所有答辩老师各项评分的平均分）</p>
    <table class="table">
        <tr>
            <td><i class="icon icon-forward"></i> 论文与实物的质量评分(0-10分)</td>
            <td>
                <c:choose>
                    <c:when test="${empty avgQualityScoreByGroupTutor}">
                        <p style="color: red">未生成</p>
                    </c:when>
                    <c:otherwise>
                        ${avgQualityScoreByGroupTutor}
                    </c:otherwise>
                </c:choose>
            </td>
            <td><i class="icon icon-forward"></i> 完成任务书规定的要求与水平评分(0-10分)</td>
            <td>
                <c:choose>
                    <c:when test="${empty avgCompletenessScoreByGroupTutor}">
                        <p style="color: red">未生成</p>
                    </c:when>
                    <c:otherwise>
                        ${avgCompletenessScoreByGroupTutor}
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td><i class="icon icon-forward"></i> 论文内容的答辩陈述评分(0-10分)</td>
            <td>
                <c:choose>
                    <c:when test="${empty avgReplyScoreByGroupTutor}">
                        <p style="color: red">未生成</p>
                    </c:when>
                    <c:otherwise>
                        ${avgReplyScoreByGroupTutor}
                    </c:otherwise>
                </c:choose>
            </td>
            <td><i class="icon icon-forward"></i> 回答问题的正确性评分(0-10分)</td>
            <td>
                <c:choose>
                    <c:when test="${empty avgCorrectnessScoreByGroupTutor}">
                        <p style="color: red">未生成</p>
                    </c:when>
                    <c:otherwise>
                        ${avgCorrectnessScoreByGroupTutor}
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </table>
</div>


<%--用来显示老师的评审模态框--%>
<div class="modal fade" id="showDetail" tabindex="-1" role="dialog"
     aria-hidden="true" aria-labelledby="modelOpeningReportTime">
    <div class="modal-dialog">
        <div class="modal-content">
        </div>
    </div>
</div>