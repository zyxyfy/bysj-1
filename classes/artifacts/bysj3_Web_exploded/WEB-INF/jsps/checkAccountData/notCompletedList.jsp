<%--
  Created by IntelliJ IDEA.
  User: zhan
  Date: 2016/4/23
  Time: 15:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsps/includeURL.jsp" %>

<form action="<%=basePath%>dataAccount/checkDataAccount.html" method="get">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"
                aria-hidden="true">×
        </button>
        <h4 class="modal-title" id="myModalLabel">
            ${description},总数：${fn:length(graduateProjectList)}
        </h4>
    </div>
    <div class="modal-body">
        <div class="row-fluid">
            <table class="table table-striped table-bordered table-hover datatable">
                <thead>
                <tr>
                    <th></th>
                    <th>课题名称</th>
                    <th>指导老师</th>
                    <th>学号</th>
                    <th>姓名</th>
                    <th>班级</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${empty graduateProjectList}">
                        <div class="alert alert-warning alert-dismissable" role="alert">
                            <button class="close" type="button" data-dismiss="alert">&times;</button>
                            没有可以显示的信息
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${graduateProjectList}" var="graduateProject" varStatus="status">
                            <tr>
                                <td>${status.index+1}</td>
                                <td>${graduateProject.title}</td>
                                <td>${graduateProject.mainTutorage.tutor.name}</td>
                                <td>${graduateProject.student.no}</td>
                                <td>${graduateProject.student.name}</td>
                                <td>${graduateProject.student.studentClass.description}</td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
    </div>
    <div class="modal-footer">
        <button type="submit" class="btn btn-default">关闭
        </button>
    </div>
</form>
