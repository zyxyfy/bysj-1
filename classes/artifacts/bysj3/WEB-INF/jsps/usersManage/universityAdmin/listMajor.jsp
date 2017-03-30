<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/includeURL.jsp"%>
<script type="text/javascript">
	 function deleteMajor(majorId){
		//var confirmDelete=window.confirm("确认删除？");
		 window.wxc.xcConfirm("确认删除？","confirm",{
			 onOk: function(){
				 $.ajax({
					 url:'/bysj3/usersManage/deleteMajor.html',
					 type:'GET',
					 dataType:'json',
					 data:{"majorId":majorId},
					 success:function(data){
						 $("#majorRow"+majorId).remove();
						 myAlert("删除成功");
						 return true;
					 },
					 error:function(){
						 myAlert("删除该专业下的班级后，再删除专业");
						 return false;
					 }
				 });
			 }});
		/*if(confirmDelete){
			$.ajax({
				url:'/bysj3/usersManage/deleteMajor.html',
				type:'GET',
				dataType:'json',
				data:{"majorId":majorId},
				success:function(data){
					$("#majorRow"+majorId).remove();
					myAlert("删除成功");
					return true;
				},
				error:function(){
					myAlert("删除该专业下的班级后，再删除专业");
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
			<li>查看专业<span class="divider">/</span>
			</li>
		</ul>
	</div>
	<br>
	<div class="row">
		<a class="btn btn-primary btn-sm"
			href="/bysj3/usersManage/addMajor.html?departmentId=${departmentId}" data-toggle="modal"
			data-target="#addorEditMajor"> <i class="icon-plus"></i> 添加专业
		</a>
	</div>
	<br>
	<div class="row-fluid">
		<table
			class="table table-striped table-bordered table-hover datatable">
			<thead>
				<tr>
					<th>专业</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty majors}">
						<div class="alert alert-warning alert-dismissable" role="alert">
							<button class="close" type="button" data-dismiss="alert">&times;</button>
							没有数据
						</div>
					</c:when>
					<c:otherwise>
						<c:forEach items="${majors}" var="major">
							<tr id="majorRow${major.id }">
								<td>${major.description}</td>
								<td><a class="btn btn-danger btn-xs" onclick="deleteMajor(${major.id})"> <i
										class="icon-remove "></i> 删除
								</a>
									<a class="btn btn-warning btn-xs" href="/bysj3/usersManage/editMajor.html?majorId=${major.id}&&departmentId=${departmentId}"
										data-toggle="modal" data-target="#addorEditMajor">
										<i class="icon-edit"></i> 修改
									</a>
									<a class="btn btn-success btn-xs" href="/bysj3/usersManage/listStudentClass.html?majorId=${major.id}">
										<i class="icon-lock"></i> 查看或添加班级
									</a></td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
	<div class="modal fade" id="addorEditMajor" tabindex="-1" role="dialog"
		aria-hidden="true" aria-labelledby="modelOpeningReportTime">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
	</div>
	<div class="modal fade" id="addorEditStudentClass" tabindex="-1" role="dialog"
		aria-hidden="true" aria-labelledby="modelOpeningReportTime">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
	</div>
</div>