<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/includeURL.jsp"%>
<script type="text/javascript">
	function auditStageAchievement(){
		myAlert("没有阶段成果，暂不能审核")
		
	}
</script>
<div class="container-fluid" style="width: 100%">
	<div class="row-fluid">
		<ul class="breadcrumb">
			<li>指导流程<span class="divider">/</span>
			</li>
			<li>审阅各阶段成果<span class="divider">/</span>
			</li>
		</ul>
	</div>

	<div class="row">
		<table
			class="table table-striped table-bordered table-hover datatable">
			<thead>
				<tr>
					<th>课题题目</th>
					<th>副标题</th>
					<th>年份</th>
					<th>类别</th>
					<th>班级</th>
					<th>学号</th>
					<th>学生姓名</th>
					<th>详情</th>
					<th>成果</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty graduateProjects}">
					<tr>

						<div class="alert alert-warning alert-dismissable" role="alert">
							<button class="close" type="button" data-dismiss="alert">&times;</button>
							没有可以显示的数据
						</div>
						
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach items="${graduateProjects}" var="graduateProject">
							<tr>
								<td>${graduateProject.title }</td>
								<c:choose>
									<c:when test="${empty graduateProject.subTitle}">
										<td></td>
									</c:when>
									<c:otherwise>
										<td>${graduateProject.subTitle}</td>
									</c:otherwise>
								</c:choose>
								<td>${graduateProject.year}</td>
								<td>${graduateProject.category }</td>
								<td>${graduateProject.student.studentClass.description }</td>
								<td>${graduateProject.student.no }</td>
								<td>${graduateProject.student.name }</td>
								<td><a class="btn btn-primary btn-xs"
									href="/bysj3/process/showDetail.html?graduateProjectId=${graduateProject.id}"
									data-toggle="modal" data-target="#showDetail">显示细节 </a></td>
								<td>${fn:length(graduateProject.stageAchievement)}</td>
								<td>
									<c:if test="${graduateProject.stageAchievement!=null}">
										<a class="btn btn-danger btn-xs"
											href="auditStageAchievement.html?graduateProjectId=${graduateProject.id}"><span>查看阶段成果</span></a>
									</c:if>
									<c:if test="${graduateProject.stageAchievement==null}">
										<a class="btn btn-danger btn-xs" onclick="auditStageAchievement()">查看阶段成果</a>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</c:otherwise>

				</c:choose>
			</tbody>
		</table>
	</div>
	<div class="row">
		<%@ include file="/WEB-INF/jsps/page/pageBar.jsp"%>
	</div>
	<div class="modal fade" id="addGuideRecord" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
	</div>
	<div class="modal fade" id="showDetail" tabindex="-1" role="dialog"
	aria-hidden="true" aria-labelledby="modelOpeningReportTime">
	<div class="modal-dialog">
		<div class="modal-content"></div>
	</div>
	</div>
</div>