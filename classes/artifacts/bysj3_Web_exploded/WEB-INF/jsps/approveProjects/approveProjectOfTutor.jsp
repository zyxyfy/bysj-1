<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/includeURL.jsp"%>

<script type="text/javascript">

    /*通过课题的函数*/
    function approveGraduate(graduateProjectId) {
       /*  var confirmApprove = window.confirm("是否通过？");
        if (confirmApprove) {
            transform(graduateProjectId, true);
        } */
        window.wxc.xcConfirm("是否通过？","confirm",{
			onOk: function(){
				transform(graduateProjectId, true);
			}});
    }

    /*退回课题的函数*/
    function backGraduate(graduateProjectId) {
    	window.wxc.xcConfirm("是否退回？","confirm",{
			onOk: function(){
				transform(graduateProjectId, false);
			}});
        /* var confirmBack = window.confirm("是否退回？");
        if (confirmBack) {
            transform(graduateProjectId, false);
        } */
    }

    /*用来设置通过或者退回*/
    function transform(graduateProjectId, whetherApprove) {
        $.ajax({
            url: "<c:url value='/process/approveOrBack.html'/>",
            data: {"projectId": graduateProjectId, "ifApprove": whetherApprove},
            dataType: 'json',
            type: 'POST',
            success: function (data) {
                if (whetherApprove) {
                    $("#ifApproveShow" + graduateProjectId).html("<span class='label label-success'>通过</span>");
                    $("#btn" + graduateProjectId).html("<a class='btn btn-warning btn-xs' onclick='backGraduate(${graduateProject.id})'>退回</a>");

                } else {
                    $("#ifApproveShow" + graduateProjectId).html("<span class='label label-warning'>未通过</span>");
                    $("#btn" + graduateProjectId).html("<a class='btn btn-success btn-xs' onclick='backGraduate(${graduateProject.id})'>通过</a>");
                }
                myAlert("设置成功！");
                return true;
            },
            error: function () {
                myAlert("网络故障，请稍后再试");
                return false;
            }
        });
    }
</script>

<div class="container-fluid" style="width: 100%">

	<div class="row-fluid">
		
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
		<c:set var="approve" value="${true}" />
		<c:set var="notApprove" value="${false}" />
		<a href="<%=basePath%>process/approveProjectsOfTutor.html"
			class="btn btn-primary btn-sm" type="button">全部</a> <a
			href="<%=basePath%>process/approveProjectsOfTutor.html?approve=${approve}"
			class="btn btn-success btn-sm" type="button">已通过</a> <a
			href="<%=basePath%>process/approveProjectsOfTutor.html?approve=${notApprove}"
			class="btn btn-warning btn-sm" type="button">未通过</a>
	</div>
	<br>

	<div class="row">
		<div class="col-md-7">
			<form class="form-inline form-group" method="post" role="form"
				action="<%=basePath%>process/searchProjectByNameAndTitle.html">
				<div class="form-group">
					<label for="name">老师姓名</label> <input type="text"
						class="form-control" id="name" name="tutorName"
						placeholder="请输入老师姓名">
				</div>
				<div class="form-group">
					<label for="title">题目</label> <input type="text"
						class="form-control" id="title" name="title"
						placeholder="请输入要查询的题目">
				</div>
				<button type="submit" class="btn btn-primary btn-sm">
					<i class="icon-search"></i> 检索
				</button>
			</form>
		</div>
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
				<c:choose>
					<c:when test="${contentSize==0}">
					</c:when>
					<c:otherwise>
						<c:forEach items="${graduateProjectEvaluate}"
							var="graduateProject">
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
								<td id="btn${graduateProject.id}"><c:choose>
										<c:when
											test="${graduateProject.auditByDirector.approve==true}">
											<a id="backBtn${graduateProject.id}"
												class="btn btn-warning btn-xs"
												onclick="backGraduate(${graduateProject.id})">退回</a>
										</c:when>
										<c:otherwise>
											<a id="approveBtn${graduateProject.id}"
												class="btn btn-success btn-xs"
												onclick="approveGraduate(${graduateProject.id})">通过</a>
										</c:otherwise>
									</c:choose></td>
								<!-- 详情 -->
								<td><a class="btn btn-primary btn-xs"
									href="<%=basePath%>process/showDetail.html?graduateProjectId=${graduateProject.id}"
									data-toggle="modal" data-target="#graduateProjectDetail"><i
										class="icon-coffee"></i>显示细节</a></td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		<c:choose>
		<c:when test="${contentSize==0}">
			<div class="alert alert-warning alert-dismissable" role="alert">
				<button class="close" type="button" data-dismiss="alert">&times;</button>
				没有可以显示的课题
			</div>
		</c:when>
		</c:choose>
	</div>

</div>
<div id="graduateProjectDetail" class="modal fade in" aria-hidden="true"
	aria-labelledby="myLabelModal" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<%--content中的内容由相应的jsp自动填充--%>
		</div>
	</div>
</div>

<%@include file="/WEB-INF/jsps/page/pageBar.jsp"%>