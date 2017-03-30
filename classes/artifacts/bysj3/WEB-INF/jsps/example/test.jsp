<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>已跳转</h1>
<table>
	<tbody>
		<c:if test="${empty noOwnRoles}">
			<span>没有成功获取list集合</span>
		</c:if>
		<c:forEach items="${noOwnRoles}" var="role">
			<td>${role.description}</td>
		</c:forEach>
	</tbody>
</table>
</body>
</html>