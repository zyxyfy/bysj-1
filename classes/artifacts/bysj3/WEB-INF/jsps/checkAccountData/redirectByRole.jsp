<%--
  Created by IntelliJ IDEA.
  User: 张战
  Date: 2016/4/22
  Time: 23:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>

<%--最高角色是院级管理员--%>
<sec:authorize ifAllGranted="ROLE_SCHOOL_ADMIN" ifNotGranted="ROLE_DEPARTMENT_DIRECTOR">
    <c:redirect url="/dataAccount/checkDataAccountByDean.html"/>
</sec:authorize>

<%--最高角色是教研室主任--%>
<sec:authorize ifAllGranted="ROLE_DEPARTMENT_DIRECTOR" ifNotGranted="ROLE_SCHOOL_ADMIN">
    <c:redirect url="/dataAccount/checkDataAccountByDepartment.html"/>
</sec:authorize>

<%--同时拥有院级管理员和教研室主任两个角色--%>
<sec:authorize ifAllGranted="ROLE_SCHOOL_ADMIN,ROLE_DEPARTMENT_DIRECTOR">
    <c:redirect url="/dataAccount/checkDataAccountByDean.html"/>
</sec:authorize>