<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/includeURL.jsp"%>
<script type="text/javascript">
	function approveExcellentProject(graduateProjectId){
		window.wxc.xcConfirm("确认通过？", "confirm", {
			onOk: function () {
				$.ajax({
					url: '/bysj3/projects/approveSchoolExcellentProject.html',
					type: 'GET',
					dataType: 'json',
					data: {"graduateProjectId": graduateProjectId},
					success: function (data) {
						$("#status" + graduateProjectId).html("<label class='label label-success'> 通过</label>");
						$("#audit" + graduateProjectId).html("<a id='cancel${graduateProject.id}' class='btn btn-warning btn-xs' onclick='cancelExcellentProject(" + graduateProjectId + ")'>驳回</a>")
						window.wxc.xcConfirm("已通过", "success");
						return true;
					},
					error: function () {
						window.wxc.xcConfirm("修改失败,请稍后再试", "error");
						return false;
					}
				});
			}
		})
		/*if(confirmApprove){
			$.ajax({
		 url:'/bysj3/projects/approveSchoolExcellentProject.html',
				type:'GET',
				dataType:'json',
				data:{"graduateProjectId":graduateProjectId},
				success:function(data){
					$("#status"+graduateProjectId).html("<p>通过</p>");
					$("#audit"+graduateProjectId).html("<a id='cancel${graduateProject.id}' class='btn btn-primary btn-xs' onclick='cancelExcellentProject("+graduateProjectId+")'>驳回</a>")
					myAlert("修改成功");
					return true;
				},
				error:function(){
					myAlert("网络错误，修改失败")
				}
			});
		 }*/
	}
	function cancelExcellentProject(graduateProjectId){
		//var confirmCancel =window.confirm("确认驳回？");
		window.wxc.xcConfirm("确认驳回？", "confirm", {
			onOk: function () {
				$.ajax({
					url: '/bysj3/projects/cancelSchoolExcellentProject.html',
					type: 'GET',
					dataType: 'json',
					data: {"graduateProjectId": graduateProjectId},
					success: function (data) {
						$("#status" + graduateProjectId).html("<label class='label label-warning'>驳回</label>");
						$("#audit" + graduateProjectId).html("<a id='cancel${graduateProject.id}' class='btn btn-success btn-xs' onclick='approveExcellentProject(" + graduateProjectId + ")'>通过</a>")
						//myAlert("修改成功");
						window.wxc.xcConfirm("已驳回", "success");
						return true;
					},
					error: function () {
						//myAlert("网络故障，修改失败");
						window.wxc.xcConfirm("驳回失败,请稍后再试");
						return false;
					}
				});
			}
		});
		/*if(confirmCancel){
			$.ajax({
		 url:'/bysj3/projects/cancelSchoolExcellentProject.html',
				type:'GET',
				dataType:'json',
				data:{"graduateProjectId":graduateProjectId},
				success:function(data){
					$("#status"+graduateProjectId).html("<p>驳回</p>");
					$("#audit"+graduateProjectId).html("<a id='cancel${graduateProject.id}' class='btn btn-primary btn-xs' onclick='approveExcellentProject("+graduateProjectId+")'>通过</a>")
					myAlert("修改成功");
					return true;
				},
				error:function(){
					myAlert("网络故障，修改失败");
					return false;
				}
			})
		 }*/
	}
</script>
<div class="container-fluid" style="width: 100%">
	<div class="row-fluid">
		<ul class="breadcrumb">
			<li>论文管理</li>
			<li>指定校优<span class="divider">/</span>
			</li>
		</ul>
	</div>
	<br>
	<div class="row">
		<div class="col-md-8">
			<form class="form-inline" role="form" action="${actionUrl}"
				method="post">
				<div class="form-group ">
					<label for="title">题目名称</label> <input type="text"
						class="form-control " id="title" name="title"
						placeholder="请输入题目名称" value="${title}">
				</div>
				<div class="form-group">
					<label for="tutorName">教师姓名</label> <input type="text"
						class="form-control " id="tutorName" name="tutorName"
						placeholder="请输入教师姓名" value="${tutorName}">
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
					<th>指定校级优秀</th>
					<th>操作</th>
					<th>详情</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty graduateProjects}">
						<!-- <div class="alert alert-warning alert-dismissable" role="alert">
							<button class="close" type="button" data-dismiss="alert">&times;</button>
							当前没有要显示数据
						</div> -->
					</c:when>
					<c:otherwise>
						<c:forEach items="${graduateProjects}" var="graduateProject">
							<tr>
								<td>${graduateProject.student.no}</td>
								<td>${graduateProject.student.name}</td>
								<td>${graduateProject.student.studentClass.description}</td>
								<td>${graduateProject.student.studentClass.major.description}</td>
								<td>
									<c:choose>
										<c:when test="${empty graduateProject.getTotalScores()}">
											<span style="color: red;">未生成</span>
										</c:when>
										<c:otherwise>
											${graduateProject.getTotalScores()}
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${empty graduateProject.subTitle}">
											${graduateProject.title}
										</c:when>
										<c:otherwise>
											${graduateProject.title}——${graduateProject.subTitle}
										</c:otherwise>
									</c:choose>
								</td>
								<td>${graduateProject.category}</td>
								<td>${graduateProject.proposer.name}</td>
								<td>${empty graduateProject.proposer.proTitle?"无":graduateProject.proposer.proTitle.description}/${empty graduateProject.proposer.degree?"无":graduateProject.proposer.degree.description}</td>
								<td id="status${graduateProject.id}"><c:choose>
										<c:when
											test="${graduateProject.recommended==true&&graduateProject.schoolExcellentProject!=null}">
											<label class="label label-success">优秀</label>
										</c:when>
										<c:otherwise>否</c:otherwise>
									</c:choose></td>
								<td id="audit${graduateProject.id}"><c:choose>
										<c:when
											test="${graduateProject.recommended==true&&graduateProject.schoolExcellentProject!=null}">
											<%-- <a id="cancel${graduateProject.id}" class="btn btn-primary btn-xs"
										href="cancelSchoolExcellentProject.html?graduateProjectId=${graduateProject.id}">驳回</a> --%>
											<a id="cancel${graduateProject.id}"
												class="btn btn-warning btn-xs"
												onclick="cancelExcellentProject(${graduateProject.id})">驳回</a>
										</c:when>
										<c:otherwise>
											<%-- <a id="approve${graduateProject.id}" class="btn btn-primary btn-xs"
										href="approveSchoolExcellentProject.html?graduateProjectId=${graduateProject.id}">通过</a> --%>
											<a id="approve${graduateProject.id}"
												class="btn btn-success btn-xs"
												onclick="approveExcellentProject(${graduateProject.id})">通过</a>
										</c:otherwise>
									</c:choose></td>
								<td><a class="btn btn-primary btn-xs"
									href="/bysj3/process/showDetail.html?graduateProjectId=${graduateProject.id}"
									data-toggle="modal" data-target="#showDetail">显示细节 </a></td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		<c:choose>
			<c:when test="${empty graduateProjects}">
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

