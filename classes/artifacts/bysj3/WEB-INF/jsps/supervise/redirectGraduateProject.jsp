<%--
  Created by IntelliJ IDEA.
  User: zhan
  Date: 2016/4/9
  Time: 17:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>

<%--最高角色是校级管理员--%>
<sec:authorize ifAllGranted="ROLE_COLLEGE_ADMIN">
    <c:redirect url="/supervisor/school/projectsToSupervisorByCollege.html?pageNo=${pageNo}&pageSize=${pageSize}"/>
</sec:authorize>
<%--最高角色是院级管理员或督导员--%>
<sec:authorize ifNotGranted="ROLE_COLLEGE_ADMIN" ifAnyGranted="ROLE_DEPARTMENT_DIRECTOR,ROLE_DEPARTMENT_SUPERVISOR">
    <c:redirect url="/supervisor/school/projectsToSupervisorBySchool.html?pageNo=${pageNo}&pageSize=${pageSize}"/>
</sec:authorize>
