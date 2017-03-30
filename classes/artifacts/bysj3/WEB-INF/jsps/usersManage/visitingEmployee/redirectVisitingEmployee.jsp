<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/includeURL.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

  
<!-- 最高角色是研室主任 -->
<sec:authorize ifAnyGranted="ROLE_DEPARTMENT_DIRECTOR" ifNotGranted="ROLE_SCHOOL_ADMIN,ROLE_COLLEGE_ADMIN">
		<c:redirect url="/usersManage/department/visitingEmployee.html?pageNo=${pageNo}&pageSize=${pageSize}"/>
</sec:authorize>

<!-- 最高角色是院级管理员 --> 
<sec:authorize ifAnyGranted="ROLE_SCHOOL_ADMIN" ifNotGranted="ROLE_COLLEGE_ADMIN">
		<c:redirect url="/usersManage/school/visitingEmployee.html?pageNo=${pageNo}&pageSize=${pageSize}"/>
</sec:authorize>

<!-- 最高角色是校级管理员 -->
<sec:authorize ifAnyGranted="ROLE_COLLEGE_ADMIN">
	<c:redirect url="/usersManage/college/visitingEmployee.html?pageNo=${pageNo}&pageSize=${PageSize}"></c:redirect>
</sec:authorize>

