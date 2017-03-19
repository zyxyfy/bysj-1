<%@ page import="com.newview.bysj.domain.PaperProject" %>
<%@ page import="java.awt.print.Paper" %>
<%--
  Created by IntelliJ IDEA.
  User: zhan
  Date: 2016/4/9
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsps/includeURL.jsp"%>

<div class="container-fluid" style="width: 100%">
	<div class="row-fluid">
		<ul class="breadcrumb">
			<li>督导</li>
			<li>查看论文明细表<span class="divider">/</span>
			</li>
		</ul>
	</div>
	<div class="row">
		<div class="col-md-6">
			<form class="form-inline" action="${actionURL}" method="get">
				<div class="form-group ">
					<label for="name">课题名称：</label> <input type="text" value="${title}"
						class="form-control " name="title" id="name" placeholder="请输入要查询的课题的名称">
					<button type="submit" class="btn btn-primary">
						<i class="icon-search"></i> 查询
					</button>
				</div>
			</form>
		</div>
	</div>
	<br>
	<%--<div class="row">
		&lt;%&ndash;如果弹出的模态框需要获取后台传过来的数据，即先请求后台，再将请求到的数据放在jsp中进行渲染，则需要书写以下代码&ndash;%&gt;
		&lt;%&ndash;在<a>标签中添加href属性，并添加和模态框相关的属性，则点击此按钮时会先请求后台，然后后台的数据将会返回到某一个jsp页面进行渲染。后台返回的那个jsp页面将会添加到此jsp页面中id为addTeacher的模态框中的class为modal-content的标签中&ndash;%&gt;
		<a href="employeeAdd.html" class="btn btn-primary btn-sm"
			data-toggle="modal" data-target="#addTeacher"> <i
			class="icon-reply-all"></i> 全部题目
		</a> <a href="employeeAdd.html" class="btn btn-primary btn-sm"
			data-toggle="modal" data-target="#addTeacher"> <i
			class="icon-coffee"></i> 设计题目
		</a> <a href="employeeAdd.html" class="btn btn-primary btn-sm"
			data-toggle="modal" data-target="#addTeacher"> <i
			class="icon-apple"></i> 论文题目
		</a>
	</div>--%>
	<br>

	<div class="row-fluid">
		<table
			class="table table-striped table-bordered table-hover datatable">
			<thead>
				<tr>
					<th>学号</th>
					<th>姓名</th>
					<th>课题</th>
					<th>类别</th>
					<th>任务书</th>
					<th>开题报告</th>
					<th>工作进程表</th>
					<th>指导老师评审表</th>
					<th>评阅人评审表</th>
					<th>答辩小组意见表</th>
					<th>指导记录表</th>
					<th>详情</th>
					<th>终稿</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty graduateProjectList}">
						<div class="alert alert-warning alert-dismissable" role="alert">
							<button class="close" type="button" data-dismiss="alert">&times;</button>
							没有任何学生选择课题
						</div>
					</c:when>
					<c:otherwise>
						<c:forEach items="${graduateProjectList}" var="graduateProject">
							<tr>
								<td>${graduateProject.student.no}</td>
								<td>${graduateProject.student.name}</td>
								<td>${graduateProject.title}</td>
								<td>${graduateProject.category}</td>
								<%--下载任务书--%>
								<td><c:choose>
										<c:when test="${empty graduateProject.taskDoc}">
											<span class="label label-warning">无</span>
										</c:when>
										<c:otherwise>
											<a class="btn btn-primary btn-xs"
												href="<%=basePath%>tutor/downLoadTaskDoc.html?taskDocId=${graduateProject.taskDoc.id}"><i
												class="icon-download"></i> 下载 </a>
										</c:otherwise>
									</c:choose></td>
								<%--下载开题报告--%>
								<td><c:choose>
										<c:when test="${graduateProject.category =='设计题目'}">
											<span style="color: red">设计无开题报告</span>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${graduateProject==null}">
													<span class="label label-warning">无</span>
												</c:when>
												<c:otherwise>
													<a class="btn btn-primary btn-xs"
														href="<%=basePath%>downloadOpenningReportByGraduateProjectId.html?projectId=${graduateProject.id}"><i
														class="icon-download"></i>下载 </a>
												</c:otherwise>
											</c:choose>

										</c:otherwise>
										<%--                                    <c:when test="${true}">
                                                                            <span class="label label-warning">无</span>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <a class="btn btn-primary btn-xs" href="<%=basePath%>downloadOpenningReport.html?openningReportId=${graduateProject}"><i class="icon-download"></i>下载 </a>
                                                                        </c:otherwise>--%>
									</c:choose></td>
								<td><c:choose>
										<c:when test="${empty graduateProject.schedules}">
											<span class="label label-warning">无</span>
										</c:when>
										<c:otherwise>
											<a href="#" data-toggle="modal" data-target="#viewModal"
												class="btn btn-primary btn-xs"><i class="icon-coffee"></i>查看
											</a>
										</c:otherwise>
									</c:choose></td>
								<%--指导老师评审--%>
								<td><c:choose>
										<c:when test="${empty graduateProject.commentByTutor}">
											<span class="label label-warning">无</span>
										</c:when>
										<c:otherwise>
											<a class="btn btn-primary btn-xs"
												href="<%=basePath%>viewEvaluate.html?projectId=${graduateProject.id}"
												data-toggle="modal" data-target="#viewModal"><i
												class="icon-coffee"></i>查看 </a>
											<a class="btn btn-primary btn-xs" target="_blank"
												href="<%=basePath%>evaluate/chiefTutor/printReport.html?reportId=${graduateProject.id}"><i
												class="icon-print"></i>打印 </a>
										</c:otherwise>
									</c:choose></td>
								<%--评阅人评审--%>
								<td><c:choose>
										<c:when test="${empty graduateProject.commentByReviewer}">
											<span class="label label-warning">无</span>
										</c:when>
										<c:otherwise>
											<a class="btn btn-primary btn-xs"
												href="<%=basePath%>viewReviewerEvaluate.html?projectId=${graduateProject.id}"
												data-toggle="modal" data-target="#viewModal"><i
												class="icon-coffee"></i>查看 </a>
											<a class="btn btn-primary btn-xs" target="_blank"
												href="<%=basePath%>evaluate/reviewer/getReport.html?projectId=${graduateProject.id}"><i
												class="icon-print"></i>打印 </a>
										</c:otherwise>
									</c:choose></td>
								<%--答辩小组意见--%>
								<td><c:choose>
										<c:when test="${empty graduateProject.commentByGroup}">
											<span class="label label-warning">无</span>
										</c:when>
										<c:otherwise>
											<a class="btn btn-primary btn-xs"
												href="<%=basePath%>viewGroupEvaluate.html?projectId=${graduateProject.id}"
												data-toggle="modal" data-target="#viewModal"><i
												class="icon-coffee"></i>查看</a>
											<a class="btn btn-primary btn-xs" target="_blank"
												href="<%=basePath%>evaluate/replyGroup/getReport.html?projectId=${graduateProject.id}"><i
												class="icon-print"></i>打印 </a>
										</c:otherwise>
									</c:choose></td>
								<td><c:choose>
										<c:when test="${empty graduateProject.guideRecord}">
											<span class="label label-warning">无</span>
										</c:when>
										<c:otherwise>
											<a class="btn btn-primary btn-xs" data-toggle="modal" href=""
												data-target="#viewModal"><i class="icon-coffee"></i>查看 </a>
										</c:otherwise>
									</c:choose></td>
								<td><a class="btn btn-primary btn-xs"
									href="<%=basePath%>process/showDetail.html?graduateProjectId=${graduateProject.id}"
									data-toggle="modal" data-target="#viewModal"><i
										class="icon-coffee"></i>显示详情 </a></td>
								<td><c:choose>
										<c:when test="${empty graduateProject.finalDraft}">
											<span class="label label-warning">无</span>
										</c:when>
										<c:otherwise>
											<a class="btn btn-primary btn-xs"
												href="<%=basePath%>student/download/finalDraft.html?graduateProjectId=${graduateProject.id}"><i
												class="icon-download"></i>下载</a>
										</c:otherwise>
									</c:choose></td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
	<div class="row">
		<%@include file="/WEB-INF/jsps/page/pageBar.jsp"%>
	</div>

	<%--如果需要在模态框中加载另一个jsp，则需要书写以下的div--%>
	<div class="modal fade" id="viewModal" tabindex="-1" role="dialog"
		aria-hidden="true" aria-labelledby="modelOpeningReportTime">
		<div class="modal-dialog">
			<div class="modal-content">
				<%--此处会填充另外一个jsp的内容--%>
			</div>
		</div>
	</div>
</div>