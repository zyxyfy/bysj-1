<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/includeURL.jsp"%>
<script type="text/javascript">
	function deleteDepartment(departmentId){
		//var confirmDelete=window.confirm("确认删除？");
		window.wxc.xcConfirm("确认删除？","confirm",{
			onOk: function(){
				$.ajax({
					url:'/bysj3/usersManage/deleteDepartment.html',
					type:'GET',
					dataType:'json',
					data:{"departmentId":departmentId},
					success:function(data){
						$("#departmentRow"+departmentId).remove();
						myAlert("删除成功");
						return true;
					},
					error:function(){
						myAlert("请删除该教研室下的专业后，再删除教研室");
						return false;
					}
				});
			}});
		/*if(confirmDelete){
			$.ajax({
				url:'/bysj3/usersManage/deleteDepartment.html',
				type:'GET',
				dataType:'json',
				data:{"departmentId":departmentId},
				success:function(data){
					$("#departmentRow"+departmentId).remove();
					myAlert("删除成功");
					return true;
				},
				error:function(){
					myAlert("请删除该教研室下的专业后，再删除教研室");
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
			<li>查看教研室<span class="divider">/</span>
			</li>
		</ul>
	</div>
	<br>
	<div class="row">
		<a class="btn btn-primary btn-sm"
			href="/bysj3/usersManage/addDepartment.html?schoolId=${schoolId}" data-toggle="modal"
			data-target="#addorEditDepartment"> <i class="icon-plus"></i> 添加教研室
		</a>
	</div>
	<br>
	<div class="row-fluid">
		<table
			class="table table-striped table-bordered table-hover datatable">
			<thead>
				<tr>
					<th>教研室</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty departments}">
						<div class="alert alert-warning alert-dismissable" role="alert">
							<button class="close" type="button" data-dismiss="alert">&times;</button>
							没有数据
						</div>
					</c:when>
					<c:otherwise>
						<c:forEach items="${departments}" var="department">
							<tr id="departmentRow${department.id }">
								<td>${department.description}</td>
								<td>
								<a class="btn btn-danger btn-xs" onclick="deleteDepartment(${department.id })"><i class="icon-remove "></i>删除</a>
									<a class="btn btn-warning btn-xs" href="/bysj3/usersManage/editDepartment.html?departmentId=${department.id}&&schoolId=${schoolId}"
										data-toggle="modal" data-target="#addorEditDepartment">
										<i class="icon-edit"></i> 修改
									</a>
									<a class="btn btn-success btn-xs" href="/bysj3/usersManage/listMajor.html?departmentId=${department.id}">
										<i class="icon-lock"></i> 查看或添加专业
									</a></td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
	<!-- 分页 -->
	<div class="row">
		<%@include file="/WEB-INF/jsps/page/pageBar.jsp"%>
	</div>
	<div class="modal fade" id="addorEditDepartment" tabindex="-1" role="dialog"
		aria-hidden="true" aria-labelledby="modelOpeningReportTime">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
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