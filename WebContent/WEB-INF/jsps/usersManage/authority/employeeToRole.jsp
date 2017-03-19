
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/includeURL.jsp"%>

<div class="container-fluid" style="width: 100%">
	<div class="row-fluid">
		<ul class="breadcrumb">
			<li>用户管理</li>
			<li>教师-职位管理<span class="divider">/</span>
			</li>
		</ul>
	</div>

	<div class="row-fluid">
		<form class="form-inline" action="${actionUrl}" method="get">
			<div class="col-md-6">
				<div class="form-group ">
					<label for="name">教师姓名：</label> <input type="text"
						class="form-control " id="name" name="name" placeholder="请输入教师姓名">
					<label for="num">职工号：</label> <input type="text"
						class="form-control " id="no" name="no" placeholder="请输入职工号">
				</div>
			</div>
			<div class="col-md-6">
				<sec:authorize ifAnyGranted="ROLE_COLLEGE_ADMIN">
					<select class="form-control" id="schoolSelect" name="schoolSelect"
						onchange="schoolOnSelect()">
						<%--需要从后台传递一个schoolList的参数，里面存放了所有的学院--%>
						<option value="0">--请选择学院--</option>
						<c:forEach items="${schoolList}" var="school">
							<option value="${school.id }" class="selectSchool">${school.description}</option>
						</c:forEach>
					</select>
					<select class="form-control " id="departmentSelect"
						style="width: 150px" name="departmentSelect"></select>
				</sec:authorize>
				<sec:authorize ifAnyGranted="ROLE_SCHOOL_ADMIN"
					ifNotGranted="ROLE_COLLEGE_ADMIN">
					<select name="departmentSelect">
						<option value="0">--请选择教研室--</option>
						<c:forEach items="${departmentList}" var="department">
							<option value="${department.id}">${department.description}</option>
						</c:forEach>
					</select>
				</sec:authorize>
				<button type="submit" class="btn btn-primary btn-sm ">检索</button>
			</div>
		</form>
		<br> <br>
	</div>
	<br>
	<div class="row-fluid">
		<table
			class="table table-striped table-bordered table-hover datatable">
			<thead>
				<tr>
					<th>教师姓名</th>
					<th>职工号</th>
					<th>性別</th>
					<th>现有角色</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			<c:choose>
				<c:when test="${empty employeeList}">
					<div class="alert alert-warning alert-dismissable" role="alert">
						<button class="close" type="button" data-dismiss="alert">&times;</button>
						没有可以显示的信息
					</div>
				</c:when>
				<c:otherwise>
					<c:forEach items="${employeeList}" var="employee">
						<tr id="employee${employee.id}">
							<td>${employee.name}</td>
							<td>${employee.no}</td>
							<td>${employee.sex}</td>
							<td id="employeeRole${employee.id}"><c:forEach
									items="${employee.user.userRole}" var="userRole">
								${userRole.role.description}&nbsp;
							</c:forEach></td>
							<td><a class="btn btn-warning btn-xs"
								   href="<c:url value='/setEmployeeToRole.html?employeeId=${employee.id}'/>"
								   data-toggle="modal" data-target="#modifyTeacher"> <i
									class="icon-edit"></i> 设置
							</a></td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>

			</tbody>
		</table>
	</div>
	<div class="row-fluid">
		<%@include file="/WEB-INF/jsps/page/pageBar.jsp"%>
	</div>
	<div class="modal fade" id="modifyTeacher" tabindex="-1" role="dialog"
		aria-hidden="true" aria-labelledby="modelOpeningReportTime">
		<div class="modal-dialog modal-lg">
			<div class="modal-content"></div>
		</div>
	</div>
</div>
