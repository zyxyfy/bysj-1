<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

 <!-- 拥有教师角色，但不拥有教研室主任角色 -->
<sec:authorize ifAllGranted="ROLE_TUTOR" ifNotGranted="ROLE_DEPARTMENT_DIRECTOR">
		<c:redirect url="getOpenningReportsByTutor.html?pageNo=${pageNo}&pageSize=${pageSiz}"/>
</sec:authorize>
<!-- 拥有教研室主任角色，但不拥有教师角色 -->
<sec:authorize ifAllGranted="ROLE_DEPARTMENT_DIRECTOR" ifNotGranted="ROLE_TUTOR">
		<c:redirect url="getOpenningReportsByDirector.html?pageNo=${pageNo}&pageSize=${pageSiz}"/>
</sec:authorize>
<!-- 同时拥有教师和教研室主任角色 -->
<sec:authorize ifAllGranted="ROLE_TUTOR,ROLE_DEPARTMENT_DIRECTOR">
	<c:redirect url="getOpenningReportsByTutorAndDirector.html?pageNo=${pageNo}&pageSize=${pageSiz}"></c:redirect>
</sec:authorize> 
	