<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/includeURL.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 拥有教研室主任角色，但不拥有院长角色 -->
<sec:authorize ifAllGranted="ROLE_DEPARTMENT_DIRECTOR" ifNotGranted="ROLE_DEAN">
		<c:redirect url="getTaskDocsByDirector.html?pageNo=${pageNo}&pageSize=${pageSize}"/>
</sec:authorize> 

<!-- 拥有院长角色，但不拥有教研室主任角色 -->
<sec:authorize ifAllGranted="ROLE_DEAN" ifNotGranted="ROLE_DEPARTMENT_DIRECTOR">
		<c:redirect url="getTaskDocsByDean.html?pageNo=${pageNo}&pageSize=${pageSize}"/>
</sec:authorize>
<!-- 同时拥有教研室主任和院长角色 -->
<sec:authorize ifAllGranted="ROLE_DEAN,ROLE_DEPARTMENT_DIRECTOR">
	<c:redirect url="getTaskDocsByDirectorAndDean.html?pageNo=${pageNo}&pageSize=${pageSize}"></c:redirect>
</sec:authorize> 