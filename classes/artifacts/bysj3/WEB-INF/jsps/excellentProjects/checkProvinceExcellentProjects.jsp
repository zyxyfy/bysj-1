<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/includeURL.jsp"%>
<div class="container-fluid" style="width: 100%">
	<div class="row-fluid">
		<ul class="breadcrumb">
			<li>论文管理</li>
			<li>查看省优毕业论文<span class="divider">/</span>
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
					<th>详情</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty provinceExcellentProjects}">
					<%--	<div class="alert alert-warning alert-dismissable" role="alert">
							<button class="close" type="button" data-dismiss="alert">&times;</button>
							当前没有要显示数据
						</div>--%>
					</c:when>
					<c:otherwise>
						<c:forEach items="${provinceExcellentProjects}"
							var="excellentProject">
							<tr>
								<td>${excellentProject.graudateProject.student.no}</td>
								<td>${excellentProject.graudateProject.student.name}</td>
								<td>${excellentProject.graudateProject.student.studentClass.description}</td>
								<td>${excellentProject.graudateProject.student.studentClass.major.description}</td>
								<td>${excellentProject.graudateProject.totalScore}</td>
								<td>${excellentProject.graudateProject.title}</td>
								<td>${excellentProject.graudateProject.category}</td>
								<td>${excellentProject.graudateProject.proposer.name}</td>
								<td>${excellentProject.graudateProject.proposer.proTitle.decription}/${excellentProject.graudateProject.proposer.degree.description}</td>
								<td><a class="btn btn-primary btn-xs"
									href="/bysj3/process/showDetail.html?graduateProjectId=${excellentProject.graudateProject.id}"
									data-toggle="modal" data-target="#showDetail">显示细节 </a></td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
<c:choose>
	<c:when test="${empty provinceExcellentProjects}">
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
