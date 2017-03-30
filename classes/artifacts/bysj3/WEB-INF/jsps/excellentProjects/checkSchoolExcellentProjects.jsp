<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/includeURL.jsp"%>
<script type="text/javascript">
	function backProject(projectId) {
		window.wxc.xcConfirm("确认驳回？", "confirm", {
			onOk: function () {
				$.ajax({
					url: '/bysj3/projects/cancelProvinceExcellentProjectByDirector.html',
					data: {"schoolExcellentProjectId": projectId},
					dataType: 'json',
					type: 'get',
					success: function () {
						$("#status" + projectId).html("<label class='label label-success'>否</label>");
						$("#audit" + projectId).html("<a class='btn btn-primary btn-xs' onclick='passProject(" + projectId + ")'><i class='icon-forward'></i>推优</a>");

					}
				});
			}
		});
	}
	function passProject(projectId){
		window.wxc.xcConfirm("确认推优？","confirm",{
			onOk:function(){
				$.ajax({
					url: '/bysj3/projects/approveProvinceExcellentProjectByDirector.html?',
					data:{"schoolExcellentProjectId":projectId}	,
					dataType: 'json',
					type:"get",
					success:function(){
						$("#status" + projectId).html("<label class='label label-success'>优秀</label>");
						$("#audit" + projectId).html("<a class='btn btn-primary btn-xs' onclick='backProject(" + projectId + ")'><i class='icon-forward'></i>驳回</a>");
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
			<li>查看校优毕业论文<span class="divider">/</span>
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
					<th>省优候选状态</th>
					<th>操作</th>
					<th>详情</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty schoolExcellentProjects}">
					</c:when>
					<c:otherwise>
						<c:forEach items="${schoolExcellentProjects}"
							var="excellentProject">
							<tr>
								<td>${excellentProject.graduateProject.student.no}</td>
								<td>${excellentProject.graduateProject.student.name}</td>
								<td>${excellentProject.graduateProject.student.studentClass.description}</td>
								<td>${excellentProject.graduateProject.student.studentClass.major.description}</td>
								<td>${excellentProject.graduateProject.totalScore}</td>
								<td>${excellentProject.graduateProject.title}</td>
								<td>${excellentProject.graduateProject.category}</td>
								<td>${excellentProject.graduateProject.proposer.name}</td>
								<td>${empty excellentProject.graduateProject.proposer.proTitle?"无":excellentProject.graduateProject.proposer.proTitle.decription}/${empty excellentProject.graduateProject.proposer.degree?"无":excellentProject.graduateProject.proposer.degree.description}</td>
								<td id="status${excellentProject.id}"><c:choose>
									<c:when test="${excellentProject.recommended==true}"><label
											class="label label-success">优秀</label> </c:when>
										<c:otherwise><label
											class="label label-success">否</label> </c:otherwise>
									</c:choose></td>
								<td id="audit${excellentProject.id}"><c:choose>
									<c:when test="${excellentProject.recommended==true}">
										<c:if
											test="${excellentProject.graduateProject.provinceExcellentProject==null}">
											<a class="btn btn-primary btn-xs"
											   onclick="backProject(${excellentProject.id})"><i class="icon icon-backward"></i> 驳回</a>
										</c:if>
									</c:when> 
									<c:otherwise>
										<a class="btn btn-primary btn-xs" onclick="passProject(${excellentProject.id})"><i class="icon icon-forward"></i> 推优</a>
									</c:otherwise></c:choose></td>
								<td><a class="btn btn-primary btn-xs"
									href="/bysj3/process/showDetail.html?graduateProjectId=${excellentProject.graduateProject.id}"
									data-toggle="modal" data-target="#showDetail">显示细节 </a></td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		<c:choose>
		<c:when test="${empty schoolExcellentProjects}">
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
