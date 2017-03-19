<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/includeURL.jsp"%>

<script type="text/javascript">
	function approve(graduateProjectId,whetherApprove){
		/*var confirmProject = window.confirm("确认通过？");
		if (confirmProject) {
			transform(graduateProjectId,whetherApprove);
		}*/
		window.wxc.xcConfirm("确认修改？","confirm",{
			onOk: function(){
				transform(graduateProjectId,whetherApprove);
			}});
	}
	
	function back(graduateProjectId,whetherApprove){
		/*var confirmProject = window.confirm("确认退回？");
		if (confirmProject) {
			transform(graduateProjectId,whetherApprove);
		}*/
		window.wxc.xcConfirm("确认修改？","confirm",{
			onOk: function(){
				transform(graduateProjectId,whetherApprove);
			}});

	}
	
	function transform(graduateProjectId,whetherApprove){
		$.ajax({
			url:"<c:url value='/process/approveOrBack.html'/>",
			data:{"projectId":graduateProjectId,"whetherApprove":whetherApprove},
			dataType:'json',
			type:'post',
			success:function(data){
				if(whetherApprove==true){
					$("#ifApproveShow"+graduateProjectId).html("<span class='label label-success'>通过</span>");
					$("#approve").hide();
					$("#back").show();
				}else{
					$("#ifApproveShow"+graduateProjectId).html("<span class='label label-warning'>未通过</span>");
					$("#back").hide();
					$("#approve").show();
				}
				myAlert("设置成功！");
				return true;
			},
			error:function(){
				myAlert("网络故障，请稍后再试");
				return false;
			}
		});
	}
</script>

<div class="container-fluid" style="width: 100%">

	<div class="row-fluid">
		<h2>
			<i class="icon-bell icon-large"></i> 审核老师题目
		</h2>
		<div>
			<ul class="breadcrumb">
				<li>基本功能：</li>
				<li>选题流程 <span class="divider">/</span>
				</li>
				<li>审核老师题目 <span class="divider">/</span>
				</li>

			</ul>
		</div>
	</div>
	<div class="row">
		<c:set var="approve" value="${true}"></c:set>
		<c:set var="notApprove" value="${false}"></c:set>
		<a href="/process/approveProjectsOfTutor.html" class="btn btn-primary btn-sm" type="button">全部</a>
		<a href="/process/approveProjectsOfTutor.html?approve=${approve}" class="btn btn-primary btn-sm" type="button">已通过</a>
		<a href="/process/approveProjectsOfTutor.html?approve=${notApprove}" class="btn btn-primary btn-sm" type="button">未通过</a>
	</div>
	<br>
	<div class="row">
		<div class="col-md-7">
			<form class="form-inline" role="form">
				<div class="form-group">
					<label for="name">老师姓名</label> <input type="text"
						class="form-control" id="name" placeholder="请输入老师姓名">
				</div>
				<div class="form-group">
					<label for="title">题目</label> <input type="text"
						class="form-control" id="title" placeholder="请输入要查询的题目">
				</div>
			</form>
		</div>
		<div class="col-md-5"></div>
		<button type="submit" class="btn btn-default">检索</button>



	</div>
	<br>
	<div class="row-fluid">
		<table
			class="table table-striped table-bordered table-hover datatable">
			<thead>
				<tr>
					<th>题目名称</th>
					<th>老师姓名</th>
					<th>类别</th>
					<th>类型</th>
					<th>性质</th>
					<th>年份</th>
					<th>审核状态</th>
					<th>操作</th>
					<th>详情</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${graduateProjectEvaluate}" var="graduateProject">
					<c:choose>
						<c:when test="${contentSize==0}">
							<span class="label label-info">当前没有可以浏览的数据</span>
						</c:when>
						<c:otherwise>
							<tr id="graduateProjectCol${graduateProject.id}">
								<!-- 标题 -->
								<td>${graduateProject.title}</td>
								<!-- 老师姓名 -->
								<td>${graduateProject.proposer.name}</td>
								<!-- 类别 -->
								<td>${graduateProject.projectType.description}</td>
								<!-- 类型 -->
								<td>${graduateProject.category}</td>
								<!-- 性质 -->
								<td>${graduateProject.projectFidelity.description}</td>
								<!-- 年份 -->
								<td>${graduateProject.year }</td>
								<!-- 审核状态 -->
								<td id="ifApproveShow${graduateProject.id}"><c:choose>
										<c:when
											test="${graduateProject.auditByDirector.approve==true }">
											<span class="label label-success">通过</span>
										</c:when>
										<c:when
											test="${graduateProject.auditByDirector.approve==false }">
											<span class="label label-warning">未通过</span>
										</c:when>
										<c:otherwise>
											<span class="label label-info">未审核</span>
										</c:otherwise>
									</c:choose></td>
								<!-- 操作，退回或通过 -->
								<td><c:choose>
										<c:when
											test="${graduateProject.auditByDirector.approve==true}">
											<a id="back" class="btn btn-default"
												onclick="back(${graduateProject.id},'false')">退回</a>
										</c:when>
										<%-- <c:when test="${graduateProject.auditByDirector.approve==false}">
										
									</c:when> --%>
										<c:otherwise>
											<a id="approve" class="btn btn-default"
												onclick="approve(${graduateProject.id},'true')">通过</a>
										</c:otherwise>
									</c:choose></td>
								<!-- 详情 -->
								<td><a class="btn btn-default" href="" toggle-data="modal"
									toggle-target="#myModal">显示细节</a></td>
							</tr>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div id="myModal" class="modal fade in" aria-hidden="true"
		aria-labelledby="myLabelModal" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<%--content中的内容由相应的jsp自动填充--%>
			</div>
		</div>
	</div>
	<!-- <div class="row">
		<ul class="pagination pagination-centered  pagination-sm ">
			<li><a href="#">&larr; 第一页</a></li>
			<li><a href="#">11</a></li>
			<li><a href="#">12</a></li>
			<li class="active"><a href="#">13</a></li>
			<li><a href="#">14</a></li>
			<li><a href="#">15</a></li>
			<li class="disabled"><a href="#">最后一页&larr; </a></li>
		</ul>
	</div> -->
	
</div>