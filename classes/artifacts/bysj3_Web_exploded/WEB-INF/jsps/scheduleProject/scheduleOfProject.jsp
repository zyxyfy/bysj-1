<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsps/includeURL.jsp" %>
<script type="text/javascript">
    function showDetailSchedule() {
        myAlert("没有工作进程表，不能进行审核");
    }
</script>
<div class="container-fluid" style="width: 100%">
    <div class="row-fluid">
        <ul class="breadcrumb">
            <li>指导流程</li>
            <li>审核工作进程表<span class="divider">/</span>
            </li>
        </ul>
    </div>
    <br>

    <div class="row-fluid">
        <table
                class="table table-striped table-bordered table-hover datatable">
            <thead>
            <tr>
                <th>题目</th>
                <th>学生学号</th>
                <th>学生姓名</th>
                <th>审核</th>
                <th>审核条数</th>
                <th>显示</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${empty graduateProjects}">
                    <div class="alert alert-warning alert-dismissable" role="alert">
                        <button class="close" type="button" data-dismiss="alert">&times;</button>
                        没有可以显示的数据
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${graduateProjects}" var="graduateProject">
                        <tr class="graduateProjectRow${graduateProject.id}">
                            <c:choose>
                                <c:when test="${empty graduateProject.subTitle}">
                                    <td>${graduateProject.title}</td>
                                </c:when>
                                <c:otherwise>
                                    <td>${graduateProject.title}——${graduateProject.subTitle}</td>
                                </c:otherwise>
                            </c:choose>
                            <td>${graduateProject.student.no}</td>
                            <td>${graduateProject.student.name }</td>
                            <td>
                                <c:choose>
                                    <c:when test="${fn:length(graduateProject.schedules)!=0}">
                                        <a class="btn btn-success btn-xs"
                                           href="<%=basePath%>process/showDetailSchedules.html?graduateProjectId=${graduateProject.id}">审核</a>
                                    </c:when>
                                    <c:otherwise>
                                        <%--<a class="btn btn-danger btn-xs" onclick="showDetailSchedule()">审核</a>--%>
                                        <label class="label label-warning">未提交</label>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${fn:length(graduateProject.schedules)}</td>
                            <td>
                                <a class="btn btn-primary btn-xs"
                                   href="/bysj3/process/showDetail.html?graduateProjectId=${graduateProject.id}"
                                   data-toggle="modal" data-target="#showDetail">显示细节 </a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>
    <div class="row">
        <%@ include file="/WEB-INF/jsps/page/pageBar.jsp" %>
    </div>
    <div class="modal fade" id="approveSchedule" tabindex="-1" role="dialog"
         aria-hidden="true" aria-labelledby="modelOpeningReportTime">
        <div class="modal-dialog">
            <div class="modal-content"></div>
        </div>
    </div>
    <div class="modal fade" id="showDetail" tabindex="-1" role="dialog"
         aria-hidden="true" aria-labelledby="modelOpeningReportTime">
        <div class="modal-dialog">
            <div class="modal-content"></div>
        </div>
    </div>
</div>