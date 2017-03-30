<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsps/includeURL.jsp" %>

<div class="container-fluid" style="width: 100%">
    <div class="row-fluid">
        <ul class="breadcrumb">
            <li>毕业设计流程</li>
            <li>查看答辩地点<span class="divider">/</span>
            </li>
        </ul>
    </div>
</div>
<div class="row-fluid">
    <table class="table table-striped table-bordered table-hover datatable">
        <thead>
        <tr>
            <th align="center" width="20%">题目名称</th>
            <th align="center">副标题</th>
            <th align="center">年份</th>
            <th align="center">类别</th>
            <th align="center">答辩开始时间</th>
            <th align="center">答辩结束时间</th>
            <th align="center">答辩地点</th>
            <th align="center">课题详情</th>
        </tr>
        </thead>
        <tbody>

        <tr>
            <td>${graduateProject.title}</td>
            <c:choose>
                <c:when test="${empty graduateProject.subTitle}">
                    <td></td>
                </c:when>
                <c:otherwise>
                    <td>${graduateProject.subTitle}</td>
                </c:otherwise>
            </c:choose>
            <td>${graduateProject.year}</td>
            <td>${graduateProject.category}</td>
            <td><fmt:formatDate
                    value="${graduateProject.replyGroup.replyTime.beginTime.time}"
                    pattern="yyyy/MM/dd"/></td>
            <td><fmt:formatDate
                    value="${graduateProject.replyGroup.replyTime.endTime.time}"
                    pattern="yyyy/MM/dd"/></td>
            <td>${graduateProject.replyGroup.classRoom.description}</td>
            <td><a class="btn btn-primary btn-sm"
                   data-toggle="modal" data-target="#content" href="<%=basePath%>process/showDetail.html?graduateProjectId=${graduateProject.id}"> <i
                    class="icon-coffee"></i> 显示详情
            </a></td>
        </tr>
        </tbody>
    </table>
</div>

<div class="modal fade" id="content" tabindex="-1" role="dialog"
     aria-hidden="true" aria-labelledby="modelOpeningReportTime">
    <div class="modal-dialog">
        <div class="modal-content">

        </div>
    </div>
</div>
