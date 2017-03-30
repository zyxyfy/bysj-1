<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/includeURL.jsp"%>
<script type="text/javascript">
	function deleteStudent(studentId){
		window.wxc.xcConfirm("确认删除？","confirm",{
			onOk: function(){
				$.ajax({
					url:'/bysj3/usersManage/deleteStudent.html',
					type:'GET',
					dataType:'json',
					data:{"studentId":studentId},
					success:function(data){
						$("#studentRow"+studentId).remove();
						myAlert("删除成功");
						return true;
					},
					error:function(){
						myAlert("删除失败,请稍后再试");
						return false;
					}			
				});
			}});
		//var confirmDelete=window.confirm("确认删除？");
		/* if(confirmDelete){
			$.ajax({
				url:'/bysj3/usersManage/deleteStudent.html',
				type:'GET',
				dataType:'json',
				data:{"studentId":studentId},
				success:function(data){
					$("#studentRow"+studentId).remove();
					myAlert("删除成功");
					return true;
				},
				error:function(){
					myAlert("网络错误，删除失败");
					return false;
				}			
			});
		} */
	}
	function resetPassword(studentId){
		//var confirmReset=window.confirm("确认重置？");
		window.wxc.xcConfirm("确认重置？","confirm",{
			onOk: function(){
				$.ajax({
					url:'/bysj3/usersManage/resetPassword.html?',
					type:'GET',
					dataType:'json',
					data:{"studentId":studentId},
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

	}/*if(confirmReset){
	 $.ajax({
	 url:'/bysj3/usersManage/resetPassword.html?',
	 type:'GET',
	 dataType:'json',
	 data:{"studentId":studentId},
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
</script>
<div class="container-fluid" style="width: 100%">
	<div class="row-fluid">
		<ul class="breadcrumb">
			<li>用户管理</li>
			<li>查看学生信息<span class="divider">/</span>
			</li>
		</ul>
	</div>
	<div class="row">
		<form class="form-inline" role="form" action="${actionUrl}" method="get">
			<!-- <div class="col-md-6"> -->
				<div class="form-group ">
					<label for="name">学生姓名</label> <input type="text"
						class="form-control " id="name" name="name" placeholder="请输入学生姓名"> 
					<label for="stuNum">学号</label> <input type="text" class="form-control "
						id="stuNum" name="no" placeholder="请输入学号">
				</div>
			<!-- </div>
			<div class="col-md-6"> -->
				<sec:authorize ifAnyGranted="ROLE_COLLEGE_ADMIN">
					<%--需要从后台传递一个schoolList的参数，里面存放了所有的学院--%>
					<select class="form-control" id="schoolSelectModal"
						name="schoolSelect" onchange="schoolOnSelectModal()">
						<option value="0">--请选择学院--</option>
						<c:forEach items="${schoolList}" var="school">
							<option value="${school.id }" class="selectSchool">${school.description}</option>
						</c:forEach>
					</select>
					<select class="form-control " id="departmentSelectModal"
						name="departmentSelect" onchange="departmentOnSelectModal()">
						<option value="0">--请选择教研室--</option>
					</select>
					<select class="form-control" id="studentClassModal"
						name="studentClassSelect">
						<option value="0">--请选择班级--</option>
						<c:forEach items="${studentClassList}" var="studentClass">
							<option value="${studentClass.id }" >${studentClass.description}</option>
						</c:forEach>
					</select>
				</sec:authorize>
				<sec:authorize ifAnyGranted="ROLE_SCHOOL_ADMIN"
					ifNotGranted="ROLE_COLLEGE_ADMIN">
					<select class="form-control " id="departmentSelect"
						name="departmentSelect" onchange="departmentOnSelect()">
						<%--需要从后台传递一个departmentList的参数，里面存放了当前用户所在学院的所有教研室--%>
						<option value="0">--请选择教研室--</option>
						<c:forEach items="${departmentList}" var="department">
							<option value="${department.id}" class="selectDepartment">${department.description}</option>
						</c:forEach>
					</select>
					<select class="form-control" id="studentClass"
						name="studentClassSelect">
						<option value="0">--请选择班级--</option>
					</select>
				</sec:authorize>
				<sec:authorize ifAnyGranted="ROLE_DEPARTMENT_DIRECTOR"
					ifNotGranted="ROLE_SCHOOL_ADMIN,ROLE_COLLEGE_ADMIN">
					<select class="form-control" id="studentClass"
						name="studentClassId">
						<option value="0">--请选择班级--</option>
						<c:forEach items="${majorList}" var="major">
							<c:forEach items="${major.studentClass}" var="studentClass">
								<option value="${studentClass.id}">${studentClass.description}</option>
							</c:forEach>
						</c:forEach>
					</select>
				</sec:authorize>
				<button type="submit" class="btn btn-primary btn-sm "><i class="icon-search"></i>查询</button>
			<!-- </div> -->
		</form>
	</div>
	<br>

	<div class="row">
		<sec:authorize ifAnyGranted="ROLE_DEPARTMENT_DIRECTOR"
			ifNotGranted="ROLE_SCHOOL_ADMIN,ROLE_COLLEGE_ADMIN">
			<a class="btn btn-primary btn-sm" href="/bysj3/usersManage/department/studentAdd.html"
				data-toggle="modal" data-target="#addOrEditStudent"><i
				class="icon-plus"></i> 添加新学生</a>
		</sec:authorize>
		<sec:authorize ifAnyGranted="ROLE_SCHOOL_ADMIN"
			ifNotGranted="ROLE_COLLEGE_ADMIN">
			<a class="btn btn-primary btn-sm" href="/bysj3/usersManage/school/studentAdd.html"
				data-toggle="modal" data-target="#addOrEditStudent"><i
				class="icon-plus"></i> 添加新学生</a>
		</sec:authorize>
		<sec:authorize ifAnyGranted="ROLE_COLLEGE_ADMIN">
			<a class="btn btn-primary btn-sm" href="/bysj3/usersManage/college/studentAdd.html"
				data-toggle="modal" data-target="#addOrEditStudent"><i
				class="icon-plus"></i> 添加新学生</a>
		</sec:authorize>
		<a class="btn btn-primary btn-sm" href="<c:url value='/upLoadStudent.html'/>"
			data-toggle="modal" data-target="#editExcel"><i class="icon-plus"></i>导入学生Excel</a>
	</div>
	<br>
	<div class="row-fluid">
		<table
			class="table table-striped table-bordered table-hover datatable">
			<thead>
				<tr>
					<th>学号</th>
					<th>姓名</th>
					<th>班级</th>
					<th>所属教研室</th>
					<th>联系电话</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty studentList}">
						<div class="alert alert-warning alert-dismissable" role="alert">
							<button class="close" type="button" data-dismiss="alert">&times;</button>
							没有数据
						</div>
					</c:when>
					<c:otherwise>
						<c:forEach items="${studentList}" var="student">
							<tr id="studentRow${student.id}">
								<td id="studentNo${student.id}">${student.no}</td>
								<td id="studentName${student.id}">${student.name}</td>
								<td id="studentClass${student.id}">${student.studentClass.description}</td>
								<td id="studentDepartment${student.id }">${student.studentClass.major.department.description}</td>
								<td id="studentMoblie${student.id }">${student.contact.moblie}</td>
								<td><a class="btn btn-danger btn-xs"
									onclick="deleteStudent(${student.id})">删除</a> <a
									href="/bysj3/usersManage/${edit}/studentEdit.html?studentId=${student.id}"
									class="btn btn-warning btn-xs" data-toggle="modal"
									data-target="#addOrEditStudent"> <i class="icon-edit"></i>
										修改
								</a> <a class="btn btn-success btn-xs" onclick="resetPassword(${student.id})">
										<i class="icon-lock"></i> 重置密码				
								</a></td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>

			</tbody>
		</table>
	</div>

	<div class="modal fade" id="addOrEditStudent" tabindex="-1"
		role="dialog" aria-hidden="true"
		aria-labelledby="modelOpeningReportTime">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
	</div>
	<div class="modal fade" id="editExcel" tabindex="-1" role="dialog"
		aria-hidden="true" aria-labelledby="modelOpeningReportTime">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
	</div>
</div>
<div class="row">
	<%@include file="/WEB-INF/jsps/page/pageBar.jsp"%>
</div>