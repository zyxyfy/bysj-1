<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/includeURL.jsp"%>
<div class="container-fluid" style="width: 100%">
	<div class="row-fluid">
		<ul class="breadcrumb">
			<li>选题流程</li>
			<li>更改老师毕业设计<span class="divider">/</span>
			</li>
		</ul>
	</div>

	<div class="row">
		<div class="col-md-4">
			<form class="form-inline" role="form">
				<div class="form-group">
					<label for="name">题目名称</label> <input type="text"
						class="form-control" id="name">
				</div>
			</form>
		</div>
		<div class="col-md-4">
			<button class="btn btn-primary btn-sm" type="button">检索</button>
		</div>
	</div>
	<div class="row">
		<button class="btn btn-primary btn-sm" type="button">查看全部题目</button>
	</div>

	<div class="row">

		<table
			class="table table-striped table-bordered table-hover datatable">
			<thead>
				<tr>
					<th>提交状态</th>
					<th>题目名称</th>
					<th>副标题</th>
					<th>年份</th>
					<th>类别</th>
					<th>出题教师</th>
					<th>审核状态</th>
					<th>操作</th>
					<th>详情</th>
				</tr>
			</thead>
			<tbody>
			<c:choose>
				<c:when test="${graduateProjectList!=null}">
					<c:forEach items="${graduateProjectList}" var="graduateProject">
						<tr>
							<td></td>
						</tr>
					</c:forEach>
				</c:when>
			</c:choose>

			</tbody>
		</table>
	</div>
</div>