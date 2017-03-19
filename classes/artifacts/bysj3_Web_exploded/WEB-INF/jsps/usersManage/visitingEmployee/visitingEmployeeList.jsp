<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/includeURL.jsp"%>
<script type="text/javascript">
	function deleteVisitingEmployee(visitingEmployeeId){
		//var confirmDelete =window.confirm("确认删除？");
		window.wxc.xcConfirm("确认删除？","confirm",{
			onOk: function(){
				$.ajax({
					url:'/bysj3/usersManage/visitingEmployeeDelete.html',
					type:'GET',
					dataType:'json',
					data:{"visitingEmployeeId":visitingEmployeeId},
					success:function(data){
						$("#visitingEmployeeRow"+visitingEmployeeId).remove();
						myAlert("删除成功");
						return true;
					},
					error:function(){
						myAlert("删除失败,请稍后再试");
						return false;
					}
				});
			}});
		/*if(confirmDelete){
			$.ajax({
				url:'/bysj3/usersManage/visitingEmployeeDelete.html',
				type:'GET',
				dataType:'json',
				data:{"visitingEmployeeId":visitingEmployeeId},
				success:function(data){
					$("#visitingEmployeeRow"+visitingEmployeeId).remove();
					myAlert("删除成功");
					return true;
				},
				error:function(){
					myAlert("网络错误，删除失败");
					return false;
				}
			});
		}*/
	}
	function resetPassword(visitingEmployeeId){
		//var confirmReset =window.confirm("确认重置？");
		window.wxc.xcConfirm("确认重置？","confirm",{
			onOk: function(){
				$.ajax({
					url:'/bysj3/usersManage/resetVisitingEmployeePassword.html',
					type:'GET',
					dataType:'json',
					data:{"visitingEmployeeId":visitingEmployeeId},
					success:function(data){
						myAlert("重置成功");
						return true;
					},
					error:function(){
						myAlert("重置失败,请稍后再试");
						return false;
					}
				});
			}});
		/*if(confirmReset){
			$.ajax({
				url:'/bysj3/usersManage/resetVisitingEmployeePassword.html',
				type:'GET',
				dataType:'json',
				data:{"visitingEmployeeId":visitingEmployeeId},
				success:function(data){
					myAlert("重置成功");
					return true;
				},
				error:function(){
					myAlert("网络错误，重置失败");
					return false;
				}
			});
		}*/
	}
</script>
<div class="container-fluid" style="width: 100%">
	<div class="row-fluid">

		<ul class="breadcrumb">
			<li>用户管理</li>
			<li>查看校外教师信息<span class="divider">/</span>
			</li>
		</ul>
	</div>
	<div class="row">
		<form action="${actionUrl}" method="post" class="form-inline">
			<div class="form-group">
				<label for="name">教师姓名</label> <input type="text"
					class="form-control " name="name" placeholder="请输入教师姓名">
			</div>
			<div class="form-group">
				<label for="title">职工号</label> <input type="text"
					class="form-control " name="no" placeholder="请输入职工号">
			</div>
			<sec:authorize ifAnyGranted="ROLE_COLLEGE_ADMIN">				
				<div class="form-group">
					<label for="name">学院</label>
					<select class="form-control" id="schoolSelect" name="schoolId"
						onchange="schoolOnSelect()">
						<option value="0">--请选择学院--</option>
						<c:forEach items="${schoolList}" var="school">
							<option value="${school.id }" class="selectSchool">${school.description}</option>
						</c:forEach>
					</select>
				</div>			
				<div class="form-group">
					<label for="name">教研室</label>
					<select class="form-control " id="departmentSelect" name="departmentId"></select>
				</div>
			</sec:authorize>
			<sec:authorize ifAnyGranted="ROLE_SCHOOL_ADMIN"
				ifNotGranted="ROLE_COLLEGE_ADMIN">
			<div class="form-group">
				<label for="name">教研室</label>
				<select name="departmentId">
					<option value="0">--请选择教研室--</option>
					<c:forEach items="${departments}" var="department">
						<option value="${department.id}">${department.id}</option>
					</c:forEach>
				</select>
				</div>
			</sec:authorize>
				<button type="submit" class="btn btn-default ">查询</button>
		</form>
	</div>
	<br />
	<div class="row-fluid">
		<sec:authorize ifAnyGranted="ROLE_DEPARTMENT_DIRECTOR"
			ifNotGranted="ROLE_SCHOOL_ADMIN,ROLE_COLLEGE_ADMIN">
			<a class="btn btn-primary btn-sm" href="/bysj3/usersManage/department/visitingEmployeeAdd.html"
				data-toggle="modal" data-target="#addTeacher"><i
				class="icon-plus"></i> 添加新教師</a>
		</sec:authorize>
		<sec:authorize ifAnyGranted="ROLE_SCHOOL_ADMIN"
			ifNotGranted="ROLE_COLLEGE_ADMIN">
			<a class="btn btn-primary btn-sm" href="/bysj3/usersManage/school/visitingEmployeeAdd.html"
				data-toggle="modal" data-target="#addTeacher"><i
				class="icon-plus"></i> 添加新教師</a>
		</sec:authorize>
		<sec:authorize ifAnyGranted="ROLE_COLLEGE_ADMIN">
			<a class="btn btn-primary btn-sm" href="/bysj3/usersManage/college/visitingEmployeeAdd.html"
				data-toggle="modal" data-target="#addTeacher"><i
				class="icon-plus"></i> 添加新教师</a>
		</sec:authorize>
	</div>
	<br>
	<div class="row-fluid">
		<table
			class="table table-striped table-bordered table-hover datatable">
			<thead>
				<tr>
					<th>教师姓名</th>
					<th>职工号</th>
					<th>性别</th>
					<th>所属教研室</th>
					<th>所属公司</th>
					<th>职位</th>
					<th>学位</th>
					<th>电话</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty visitingEmployees}">

					</c:when>
					<c:otherwise>
						<c:forEach items="${visitingEmployees}" var="visitingEmployee">
							<tr id="visitingEmployeeRow${visitingEmployee.id}">
								<td>${visitingEmployee.name}</td>
								<td>${visitingEmployee.no }</td>
								<td>${visitingEmployee.sex}</td>
								<td>${visitingEmployee.department.description}</td>
								<td>${visitingEmployee.company}</td>
								<td>${visitingEmployee.proTitle.description}</td>
								<td>${visitingEmployee.degree.description}</td>
								<td>${visitingEmployee.contact.moblie}</td>
								<td><a class="btn btn-danger btn-xs"
									onclick="deleteVisitingEmployee(${visitingEmployee.id})">删除</a>
									<a
									href="/bysj3/usersManage/${edit}/visitingEmployeeEdit.html?visitingEmployeeId=${visitingEmployee.id}"
									class="btn btn-warning btn-xs" type="button"
									data-toggle="modal" data-target="#modifyTeacher"> <i
										class="icon-edit"></i> 修改
								</a> <a class="btn btn-success btn-xs"
									onclick="resetPassword(${visitingEmployee.id})"> <i
										class="icon-lock"></i>重置密码
								</a></td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>

			</tbody>
		</table>
<c:choose>
	<c:when test="${empty visitingEmployees}">
		<div class="alert alert-warning alert-dismissable" role="alert">
			<button class="close" type="button" data-dismiss="alert">&times;</button>
			没有数据
		</div>
	</c:when>
	</c:choose>
	</div>
	<!-- 分页 -->
	<div class="row">
		<p class="text-center"><%@ include
				file="/WEB-INF/jsps/page/pageBar.jsp"%></p>
	</div>
	<div class="modal fade" id="addTeacher" tabindex="-1" role="dialog"
		aria-hidden="true" aria-labelledby="modelOpeningReportTime">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
	</div>
	<div class="modal fade" id="modifyTeacher" tabindex="-1" role="dialog"
		aria-hidden="true" aria-labelledby="modelOpeningReportTime">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
	</div>
</div>
