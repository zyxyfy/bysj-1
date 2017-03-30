<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/includeURL.jsp"%>
<script type="text/javascript">
	function deleteSchool(schoolId){
		//var confirmDelete=window.confirm("确认删除？");
		window.wxc.xcConfirm("确认删除？","confirm",{
			onOk: function(){
				$.ajax({
					url:'/bysj3/usersManage/deleteSchool.html',
					type:'GET',
					dataType:'json',
					data:{"schoolId":schoolId},
					success:function(data){
						$("#schoolRow"+schoolId).remove();
						myAlert("删除成功");
						return true;
					},
					error:function(data){
						myAlert("请删除该学院下的教研室后，再删除学院");
						return false;
					}
				});
			}});
		/*if(confirmDelete){
			$.ajax({
				url:'/bysj3/usersManage/deleteSchool.html',
				type:'GET',
				dataType:'json',
				data:{"schoolId":schoolId},
				success:function(data){
					$("#schoolRow"+schoolId).remove();
					myAlert("删除成功");
					return true;
				},
				error:function(data){
					myAlert("请删除该学院下的教研室后，再删除学院");
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
			<li>基础信息管理<span class="divider">/</span>
			</li>
		</ul>
	</div>

	<div class="row">
		<form action="/bysj3/usersManage/selectSchool.html" method="post" class="form-inline" >
			<div class="form-group">
				<label for="description">学院</label>
				<input id="description" type="text" class="form-control " name="description" placeholder="请输入学院名称">
			</div>
			<div class="form-group">
				<button type="submit" class="btn btn-primary btn-sm ">查询</button>
			</div>
		</form>
	</div>
	<br>
	<div class="row">
		<a class="btn btn-primary btn-sm"
			href="/bysj3/usersManage/addSchool.html" data-toggle="modal"
			data-target="#addorEditSchool"> <i class="icon-plus"></i> 添加学院
		</a>
	</div>
	<br>
	<div class="row-fluid">
		<table
			class="table table-striped table-bordered table-hover datatable">
			<thead>
				<tr>
					<th>学院</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty schools}">
						<div class="alert alert-warning alert-dismissable" role="alert">
							<button class="close" type="button" data-dismiss="alert">&times;</button>
							没有数据
						</div>
					</c:when>
					<c:otherwise>
						<c:forEach items="${schools}" var="school">
							<tr id="schoolRow${school.id}">
								<td>${school.description}</td>
								<td>
								<a class="btn btn-danger btn-xs" onclick="deleteSchool(${school.id})"><i class="icon-remove "></i>删除</a>
									<a class="btn btn-warning btn-xs" href="/bysj3/usersManage/editSchool.html?schoolId=${school.id}"
										data-toggle="modal" data-target="#addorEditSchool">
										<i class="icon-edit"></i> 修改
									</a>
									<a class="btn btn-success btn-xs" href="/bysj3/usersManage/listDepartment.html?schoolId=${school.id}">
										<i class="icon-lock"></i> 查看或添加教研室
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
	<div class="modal fade" id="addorEditSchool" tabindex="-1" role="dialog"
		aria-hidden="true" aria-labelledby="modelOpeningReportTime">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
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
	<div class="modal fade" id="modifyTeacher" tabindex="-1" role="dialog"
		aria-hidden="true" aria-labelledby="modelOpeningReportTime">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
	</div>
</div>