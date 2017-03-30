<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsps/includeURL.jsp" %>
<script>
    function approveguideRecord() {
        myAlert("没有指导记录，暂不能审核");
    }
</script>
<div class="container-fluid" style="width: 100%">
    <div class="row-fluid">
        <ul class="breadcrumb">
            <li>指导流程</li>
            <li>审核指导记录<span class="divider">/</span>
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
                            <td>${graduateProject.title}</td>
                            <td>${graduateProject.student.no}</td>
                            <td>${graduateProject.student.name }</td>
                            <td>
                                <c:choose>
                                    <c:when test="${fn:length(graduateProject.guideRecord)!=0}">
                                        <a class="btn btn-success btn-xs"
                                           href="<%=basePath%>tutor/showDetailGuideRecord.html?graduateProjectId=${graduateProject.id}"
                                           data-toggle="modal" data-target="#approveGuideRecord">审核</a>
                                    </c:when>
                                    <c:otherwise>
                                        <%--<a class="btn btn-danger btn-xs" onclick="approveguideRecord()">审核</a>--%>
                                        <label class="label label-warning"> 未提交记录</label>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${fn:length(graduateProject.guideRecord)}</td>
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
    <div class="modal fade" id="approveGuideRecord" tabindex="-1" role="dialog"
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