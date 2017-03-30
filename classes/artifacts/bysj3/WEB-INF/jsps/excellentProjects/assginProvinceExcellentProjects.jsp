<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/includeURL.jsp"%>
<script type="text/javascript">
	function approveExcellentProject(graduateProjectId){
		//var confirmApprove =window.confirm("确认推优？");
		window.wxc.xcConfirm("确认推优？", "confirm", {
			onOk: function () {
				$.ajax({
					url: '/bysj3/projects/approveProvinceExcellentProject.html',
					type: 'GET',
					dataType: 'json',
					data: {"graduateProjectId": graduateProjectId},
					success: function (data) {
						$("#status" + graduateProjectId).html("<label class='label label-success'>优秀</label>");
						$("#audit" + graduateProjectId).html("<a id='cancel${graduateProject.id}' class='btn btn-primary btn-xs' onclick='cancelExcellentProject(" + graduateProjectId + ")'>驳回</a>")
						//myAlert("修改成功");
						window.wxc.xcConfirm("已推优", "success");
						return true;
					},
					error: function () {
						//myAlert("网络错误，修改失败");
						window.wxc.xcConfirm("推优失败,请稍后再试", "error");
						return false;
					}
				});
			}
		})
	}
	function cancelExcellentProject(graduateProjectId){
		//var confirmCancel =window.confirm("确认驳回？");
		window.wxc.xcConfirm("确认驳回？", "confirm", {
			onOk: function () {
				$.ajax({
					url: '/bysj3/projects/cancelProvinceExcellentProject.html',
					type: 'GET',
					dataType: 'json',
					data: {"graduateProjectId": graduateProjectId},
					success: function (data) {
						$("#status" + graduateProjectId).html("<label class='label label-success'>驳回</label>");
						$("#audit" + graduateProjectId).html("<a id='cancel${graduateProject.id}' class='btn btn-primary btn-xs' onclick='approveExcellentProject(" + graduateProjectId + ")'>通过</a>")
						//myAlert("修改成功");
						window.wxc.xcConfirm("驳回成功", "success");
						return true;
					},
					error: function () {
						//myAlert("网络故障，修改失败");
						window.wxc.xcConfirm("驳回失败,请稍后再试", "error");
						return false;
					}
				});
			}
		});
	}
</script>
<div class="container-fluid" style="width: 100%">
	<div class="row-fluid">
		<ul class="breadcrumb">
			<li>论文管理</li>
			<li>指定省优<span class="divider">/</span>
			</li>
		</ul>
	</div>
	<br>
	<div class="row">
		<div class="col-md-8">
			<form class="form-inline" role="form"
				action="${actionUrl}">
				<div class="form-group ">
					<label for="title">题目名称</label> <input type="text"
						class="form-control " id="title" name="title" placeholder="请输入题目名称"
						value="${title}">
				</div>
				<div class="form-group">
					<label for="tutorName">教师姓名</label> <input type="text"
						class="form-control " id="tutorName" name="tutorName" placeholder="请输入教师姓名"
						value="${tutorName}">
				</div>
				&nbsp
					<button type="submit" class="btn btn-primary btn-sm">查询</button>

			</form>
		</div>
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
					<th>专业</th>
					<th>成绩</th>
					<th>题目</th>
					<th>类别</th>
					<th>教师姓名</th>
					<th>职称/学位</th>
					<th>指定省级优秀</th>
					<th>操作</th>
					<th>详情</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty graduateProjets}">
					</c:when>
					<c:otherwise>
						<c:forEach items="${graduateProjets}" var="graduateProject">
							<tr>
								<td>${graduateProject.student.no}</td>
								<td>${graduateProject.student.name}</td>
								<td>${graduateProject.student.studentClass.description}</td>
								<td>${graduateProject.student.studentClass.major.description}</td>
								<td>${graduateProject.totalScore}</td>
								<td>${graduateProject.title}</td>
								<td>${graduateProject.category}</td>
								<td>${graduateProject.proposer.name}</td>
								<td>${graduateProject.proposer.proTitle}/${graduateProject.proposer.degree}</td>
								<td id="status${graduateProject.id}"><c:choose>
										<c:when
											test="${graduateProject.provinceExcellentProject!=null}"><label class="label label-success">优秀</label></c:when>
										<c:otherwise><label class="label label-success">否</label></c:otherwise>
									</c:choose></td>
								<td id="audit${graduateProject.id}"><c:choose>
										<c:when
											test="${graduateProject.provinceExcellentProject!=null}">
											 <a id="cancel${graduateProject.id}" class="btn btn-primary btn-xs" onclick="cancelExcellentProject(${graduateProject.id})">驳回</a> 
										</c:when>
										<c:otherwise>
											<a id="approve${graduateProject.id}" class="btn btn-primary btn-xs" onclick="approveExcellentProject(${graduateProject.id})">指定</a>
										</c:otherwise>
									</c:choose></td>
								<td><a class="btn btn-primary btn-xs"
									href="/bysj3/process/showDetail.html?graduateProjectId=${graduateProject.id}"
									data-toggle="modal" data-target="#showDetail">显示细节 </a></td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
		</table>
<c:choose>
	<c:when test="${empty graduateProjets}">
		<div class="alert alert-warning alert-dismissable" role="alert">
			<button class="close" type="button" data-dismiss="alert">&times;</button>
			当前没有要显示数据
		</div>
	</c:when>
</c:choose>
	</div>
	<div class="row">
		<%@ include file="/WEB-INF/jsps/page/pageBar.jsp"%>
	</div>
	<div class="modal fade" id="showDetail" tabindex="-1" role="dialog"
		aria-hidden="true" aria-labelledby="modelOpeningReportTime">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
	</div>
</div>

