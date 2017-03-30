<%--
  Created by IntelliJ IDEA.
  User: 张战
  Date: 2016/3/1
  Time: 14:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsps/includeURL.jsp" %>


<script type="text/javascript">
    $("#viewEvaluate").on("hidden", function () {
        $(this).removeData("modal");
    });
</script>

<div class="container-fluid" style="width: 100%">
    <div class="row-fluid">
        <ul class="breadcrumb">
            <li>评审流程</li>
            <c:if test="${EVALUATE_DISP=='REPLY_ADMIN'}">
                <li>答辩小组意见表<span class="divider">/</span>
                </li>
            </c:if>
            <c:if test="${EVALUATE_DISP=='REPLY_REVIEWER'}">
                <li>评阅人评审表<span class="divider">/</span>
                </li>
            </c:if>
            <c:if test="${EVALUATE_DISP=='REPLY_TUTOR'}">
                <li>指导教师评审表<span class="divider">/</span>
                </li>
            </c:if>
        </ul>
    </div>

    <c:if test="${EVALUATE_DISP=='REPLY_ADMIN'}">
        <div class="row">
            <div class="col-md-6">
                <form class="form-inline" role="form" action="<%=basePath%>evaluate/replyGroup/projectsToEvaluate.html">
                    <div class="form-group ">
                        <label for="name">题目名称：</label> <input type="text" value="${title}" name="title" required
                                                               class="form-control " placeholder="请输入题目名称">
                        <button type="submit" name="title" class="btn btn-primary btn-sm"><i class="icon-search"> 检索</i>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </c:if>
    <c:if test="${EVALUATE_DISP=='REPLY_REVIEWER'}">
        <div class="row">
            <div class="col-md-6">
                <form class="form-inline" role="form" action="<%=basePath%>evaluate/reviewer/projectsToEvaluate.html">
                    <div class="form-group ">
                        <label for="name">题目名称：</label> <input type="text" value="${title}" name="title" required
                                                               class="form-control " placeholder="请输入题目名称">
                        <button type="submit" name="title" class="btn btn-default"><i class="icon-search"> 检索</i>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </c:if>
    <c:if test="${EVALUATE_DISP=='REPLY_TUTOR'}">
        <div class="row">
            <div class="col-md-6">
                <form class="form-inline" role="form" action="<%=basePath%>evaluate/chiefTutor/projectsToEvaluate.html">
                    <div class="form-group ">
                        <label for="name">题目名称：</label> <input type="text" value="${title}" name="title" required
                                                               class="form-control " id="name" placeholder="请输入题目名称">
                        <button type="submit" name="title" class="btn btn-default"><i class="icon-search"> 检索</i>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </c:if>
    <br>

    <div class="row-fluid">
        <table
                class="table table-striped table-bordered table-hover datatable">
            <thead>
            <tr>
                <th>学号</th>
                <th>姓名</th>
                <th>题目名称</th>
                <th>年份</th>
                <th>类别</th>
                <th>论文终稿</th>
                <c:if test="${EVALUATE_DISP=='REPLY_ADMIN'}">
                    <th>答辩小组评审</th>
                    <th>指导教师评审表</th>
                    <th>评阅人评审表</th>
                    <th>小组答辩老师评审表</th>
                </c:if>
                <c:if test="${EVALUATE_DISP=='REPLY_REVIEWER'}">
                    <th>评阅人评审</th>
                    <th>指导教师评审表</th>
                </c:if>
                <c:if test="${EVALUATE_DISP=='REPLY_TUTOR'}">
                    <th>指导教师评审</th>
                </c:if>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${empty graduateProjectEvaluate}">
                    <%--<div class="alert alert-warning alert-dismissable" role="alert">
                        <button class="close" type="button" data-dismiss="alert">&times;</button>
                        没有可以显示的信息
                    </div>--%>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${graduateProjectEvaluate}" var="graduateProjectEvaluate">
                        <tr>
                            <td>${graduateProjectEvaluate.student.no}</td>
                            <td>${graduateProjectEvaluate.student.name}</td>
                            <td>${graduateProjectEvaluate.title}</td>
                            <td>${graduateProjectEvaluate.year}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${graduateProjectEvaluate.category==null}">
                                        <span class="label label-default">未设置</span>
                                    </c:when>
                                    <c:otherwise>
                                        ${graduateProjectEvaluate.category}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <c:set var="finalDraftAvailable" value="${graduateProjectEvaluate.finalDraft}"/>
                            <td>
                                <c:choose>
                                    <c:when test="${empty finalDraftAvailable}">
                                        <span class="label label-warning">未提交</span>
                                    </c:when>
                                    <c:otherwise>
                                        <a class="btn btn-default btn-xs"
                                           href="<%=basePath%>student/download/finalDraft.html?graduateProjectId=${graduateProjectEvaluate.id}"><i
                                                class="icon-download"></i>下载</a>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                                <%--答辩组长的评审--%>
                            <c:if test="${EVALUATE_DISP=='REPLY_ADMIN'}">
                                <td>
                                    <c:choose>
                                        <%--已经提交了终稿--%>
                                        <c:when test="${not empty finalDraftAvailable}">
                                            <c:choose>
                                                <%--如果已经提交，则显示总分数--%>
                                                <c:when test="${graduateProjectEvaluate.commentByGroup.submittedByGroup}">
                                                    <a class="btn btn-default btn-xs" data-toggle="modal"
                                                       data-target="#addOrEditEvaluate"
                                                       href="<%=basePath%>evaluate/replyGroup/evaluateProject.html?evaluateProjectId=${graduateProjectEvaluate.id}"><i
                                                            class="icon-edit"></i>修改</a>
                                                    <a class="btn btn-default btn-xs" target="_blank"
                                                       href="<%=basePath%>evaluate/replyGroup/getReport.html?projectId=${graduateProjectEvaluate.id}"><i
                                                            class="icon-print"></i>打印</a>
                                                    <c:set var="adminTotleScore"
                                                           value="${graduateProjectEvaluate.commentByGroup.qualityScore+graduateProjectEvaluate.commentByGroup.completenessScore+graduateProjectEvaluate.commentByGroup.replyScore+graduateProjectEvaluate.commentByGroup.correctnessSocre}"/>
                                                    <span class="label label-primary">总分：${adminTotleScore}</span>
                                                    <c:choose>
                                                        <c:when test="${graduateProjectEvaluate.commentByGroup.qualifiedByGroup}">
                                                            <span class="label label-success">通过答辩</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="label label-warning">未通过答辩</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    <%--指导老师和评阅人评审通过答辩组长才可进行评审--%>
                                                    <c:if test="${graduateProjectEvaluate.commentByTutor.qualifiedByTutor&&graduateProjectEvaluate.commentByReviewer.qualifiedByReviewer}">
                                                        <a class="btn btn-default btn-xs" data-toggle="modal"
                                                           data-target="#addOrEditEvaluate"
                                                           href="<%=basePath%>evaluate/replyGroup/evaluateProject.html?evaluateProjectId=${graduateProjectEvaluate.id}"><i
                                                                class="icon-edit"></i>评审</a>
                                                    </c:if>

                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="label label-warning">未提交终稿，不能执行评审操作</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <%--查看指导老师评审表--%>
                                <td>
                                    <c:choose>
                                        <c:when test="${graduateProjectEvaluate.commentByTutor!=null}">
                                            <a class="btn btn-default btn-xs" data-toggle="modal"
                                               href="<%=basePath%>replyGroupViewTutorEvaluate.html?projectId=${graduateProjectEvaluate.id}"
                                               data-target="#viewEvaluate"><i class="icon-coffee"></i>查看</a>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="label label-warning"><i
                                                    class="icon-check-empty"></i>暂无评审</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <%--查看评阅人评审表--%>
                                <td>
                                    <c:choose>
                                        <c:when test="${graduateProjectEvaluate.commentByReviewer!=null}">
                                            <a class="btn btn-default btn-xs"
                                               href="<%=basePath%>replyGroupViewReviewerEvaluate.html?projectId=${graduateProjectEvaluate.id}"
                                               data-toggle="modal" data-target="#viewEvaluate"><i
                                                    class="icon-coffee"></i>查看</a>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="label label-warning"><i
                                                    class="icon-check-empty"></i>暂无评审 </span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <%--各答辩老师评审表--%>
                                <td>
                                    <a class="btn btn-default btn-xs"
                                       href="<%=basePath%>evaluate/replyGroupTutor/viewReplyGroupTutorEvaluate.html?projectId=${graduateProjectEvaluate.id}"><i
                                            class="icon icon-folder-open"></i> 查看</a>
                                </td>
                            </c:if>
                                <%--最高角色是评阅人，不是答辩组长--%>
                            <c:if test="${EVALUATE_DISP=='REPLY_REVIEWER'}">
                                <td>
                                    <c:choose>
                                        <%--如果已经提交了终稿--%>
                                        <c:when test="${not empty finalDraftAvailable}">
                                            <c:choose>
                                                <%--如果已经进行的评审，则显示成绩--%>
                                                <c:when test="${graduateProjectEvaluate.commentByReviewer.submittedByReviewer}">
                                                    <a class="btn btn-default btn-xs"
                                                       href="<%=basePath%>evaluate/reviewer/evaluateProject.html?graduateProjectId=${graduateProjectEvaluate.id}"
                                                       data-toggle="modal"
                                                       data-target="#addOrEditEvaluate"><i class="icon-edit"></i>修改</a>
                                                    <a class="btn btn-default btn-xs"
                                                       href="<%=basePath%>evaluate/reviewer/getReport.html?projectId=${graduateProjectEvaluate.id}"
                                                       target="_blank"><i
                                                            class="icon-print"></i>打印</a>
                                                    <c:set var="totalScore"
                                                           value="${graduateProjectEvaluate.commentByReviewer.achievementScore+graduateProjectEvaluate.commentByReviewer.qualityScore}"/>
                                                    <span class="label label-info">总分：${totalScore}</span>
                                                    <c:choose>
                                                        <c:when test="${graduateProjectEvaluate.commentByReviewer.qualifiedByReviewer}">
                                                            <span class="label label-success">允许答辩</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="label label-warning">不允许答辩</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    <%--指导老师评审通过评阅人才可评审--%>
                                                    <c:if test="${graduateProjectEvaluate.commentByTutor.qualifiedByTutor}">
                                                        <a class="btn btn-primary btn-xs"
                                                           href="<%=basePath%>evaluate/reviewer/evaluateProject.html?graduateProjectId=${graduateProjectEvaluate.id}"
                                                           data-toggle="modal" data-target="#addOrEditEvaluate"><i
                                                                class="icon-coffee"></i> 评审</a>
                                                    </c:if>
                                                    <c:if test="${!graduateProjectEvaluate.commentByTutor.qualifiedByTutor}">
                                                        <p style="color:red;"></p>
                                                    </c:if>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="label label-warning">未提交终稿，不能进行评审操作</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <%--如果指导教师已经提交了评审表，则显示--%>
                                        <c:when test="${graduateProjectEvaluate.commentByTutor!=null}">
                                            <a class="btn btn-default btn-xs" data-toggle="modal"
                                               data-target="#viewEvaluate"
                                               href="<%=basePath%>reviewerViewTutorEvaluate.html?projectId=${graduateProjectEvaluate.id}"><i
                                                    class="icon-check"></i>查看</a>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="label label-warning"><i
                                                    class="icon-check-empty"></i> 暂无评审</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </c:if>
                                <%--最高角色是指导老师的评审--%>
                            <c:if test="${EVALUATE_DISP=='REPLY_TUTOR'}">
                                <td>
                                    <c:choose>
                                        <%--是否已经提交了终稿--%>
                                        <c:when test="${not empty finalDraftAvailable}">
                                            <c:choose>
                                                <%--是否已经提交了评审，如果已经提交，则显示成绩等相关信息--%>
                                                <c:when test="${not empty graduateProjectEvaluate.commentByTutor.qualifiedByTutor}">
                                                    <a class="btn btn-default btn-xs"
                                                       href="<%=basePath%>evaluate/chiefTutor/evaluateProject.html?projectIdToEvaluate=${graduateProjectEvaluate.id}"
                                                       data-toggle="modal" data-target="#addOrEditEvaluate"><i
                                                            class="icon-edit"></i>修改</a>
                                                    <a class="btn btn-default btn-xs" target="_blank"
                                                       href="<%=basePath%>evaluate/chiefTutor/printReport.html?reportId=${graduateProjectEvaluate.id}"><i
                                                            class="icon-print"></i>打印</a>
                                                    <span class="label label-info">
                                                        <c:set var="tutorTotalScore"
                                                               value="${graduateProjectEvaluate.commentByTutor.basicAblityScore+graduateProjectEvaluate.commentByTutor.achievementLevelScore+graduateProjectEvaluate.commentByTutor.workLoadScore+graduateProjectEvaluate.commentByTutor.workAblityScore}"/>
                                                        总分：${tutorTotalScore}
                                                    </span>
                                                    <c:choose>
                                                        <%--是否允许答辩--%>
                                                        <c:when test="${graduateProjectEvaluate.commentByTutor.qualifiedByTutor}">
                                                            <span class="label label-success">同意答辩</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="label label-warning">不同意答辩</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    <a class="btn btn-default btn-xs" data-toggle="modal"
                                                       data-target="#addOrEditEvaluate"
                                                       href="<%=basePath%>evaluate/chiefTutor/evaluateProject.html?projectIdToEvaluate=${graduateProjectEvaluate.id}"><i
                                                            class="icon-coffee"></i>评审</a>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="label label-warning">未提交终稿，不能执行评审操作</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
        <c:choose>
            <c:when test="${empty graduateProjectEvaluate}">
                <div class="alert alert-warning alert-dismissable" role="alert">
                    <button class="close" type="button" data-dismiss="alert">&times;</button>
                    没有可以显示的信息
                </div>
            </c:when>
        </c:choose>
    </div>


    <div class="row">
        <%@include file="/WEB-INF/jsps/page/pageBar.jsp" %>
    </div>

    <div class="modal fade" data-backdrop="static" data-keyboard="false" id="addOrEditEvaluate" tabindex="-1"
         role="dialog"
         aria-hidden="true" aria-labelledby="modelOpeningReportTime">
        <div class="modal-dialog">
            <div class="modal-content">
                <%--此处会填充另外一个jsp的内容--%>
            </div>
        </div>
    </div>


    <%--查看指导老师的评审--%>
    <div class="modal fade" data-backdrop="static" data-keyboard="false" id="viewEvaluate" tabindex="-1" role="dialog"
         aria-hidden="true" aria-labelledby="modelOpeningReportTime">
        <div class="modal-dialog">
            <div class="modal-content">
                <%--此处会填充另外一个jsp的内容--%>
            </div>
        </div>
    </div>
    <%--&lt;%&ndash;评阅人的评审&ndash;%&gt;
    <div class="modal fade" id="reviewerEvaluate" tabindex="-1" role="dialog"
         aria-hidden="true" aria-labelledby="modelOpeningReportTime">
        <div class="modal-dialog">
            <div class="modal-content">
                &lt;%&ndash;此处会填充另外一个jsp的内容&ndash;%&gt;
            </div>
        </div>
    </div>

    <div class="modal fade" id="reviewerEvaluate" tabindex="-1" role="dialog"
         aria-hidden="true" aria-labelledby="modelOpeningReportTime">
        <div class="modal-dialog">
            <div class="modal-content">
                &lt;%&ndash;此处会填充另外一个jsp的内容&ndash;%&gt;
            </div>
        </div>
    </div>--%>
</div>
