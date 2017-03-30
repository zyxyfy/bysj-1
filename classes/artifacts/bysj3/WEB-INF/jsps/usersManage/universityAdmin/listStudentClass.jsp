<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/includeURL.jsp"%>
<script type="text/javascript">
	function deleteStudentClass(studentClassId){
		//var confirmDelete=window.confirm("确认删除？");
		window.wxc.xcConfirm("确认删除？","confirm",{
			onOk: function(){
				$.ajax({
					url:'/bysj3/usersManage/deleteStudentClass.html',
					type:'GET',
					dataType:'json',
					data:{"studentClassId":studentClassId},
					success:function(data){
						$("#studentClassRow"+studentClassId).remove();
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
				url:'/bysj3/usersManage/deleteStudentClass.html',
				type:'GET',
				dataType:'json',
				data:{"studentClassId":studentClassId},
				success:function(data){
					$("#studentClassRow"+studentClassId).remove();
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
</script>
<div class="container-fluid" style="width: 100%">
	<div class="row-fluid">
		<ul class="breadcrumb">
			<li>用户管理</li>
			<li>查看班级<span class="divider">/</span>
			</li>
		</ul>
	</div>
	<br>
	<div class="row">
		<a class="btn btn-primary  btn-sm" href="/bysj3/usersManage/addStudentClass.html?majorId=${majorId}"
			data-toggle="modal" data-target="#addorEditStudentClass">
			<i class="icon-external-link"></i> 添加班级
		</a>
	</div>
	<br>
	<div class="row-fluid">
		<table
			class="table table-striped table-bordered table-hover datatable">
			<thead>
				<tr>
					<th>班级</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty studentClasses}">
						<div class="alert alert-warning alert-dismissable" role="alert">
							<button class="close" type="button" data-dismiss="alert">&times;</button>
							没有数据
						</div>
					</c:when>
					<c:otherwise>
						<c:forEach items="${studentClasses}" var="studentClass">
							<tr id="studentClassRow${studentClass.id}">
								<td>${studentClass.description}</td>
								<td><a class="btn btn-danger btn-xs" onclick="deleteStudentClass(${studentClass.id})"> <i
										class="icon-remove "></i> 删除
								</a>
									<a class="btn btn-warning btn-xs"  href="/bysj3/usersManage/editStudentClass.html?studentClassId=${studentClass.id}&&majorId=${majorId}"
										data-toggle="modal" data-target="#addorEditStudentClass">
										<i class="icon-edit"></i> 修改
									</a>
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
	<div class="modal fade" id="addorEditStudentClass" tabindex="-1" role="dialog"
		aria-hidden="true" aria-labelledby="modelOpeningReportTime">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
	</div>
	
</div>