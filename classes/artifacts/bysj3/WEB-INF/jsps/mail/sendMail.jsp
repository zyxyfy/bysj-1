<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsps/includeURL.jsp"%>

<script type="text/javascript">
	function delMail(delId) {
		window.wxc.xcConfirm("确认删除？", "confirm", {
			onOk: function () {
				$.ajax({
					url: '/bysj3/notice/delMail.html',
					data: {"mailId": delId},
					dataType: 'json',
					type: 'post',
					success: function () {
						$("#sendMail" + delId).remove();
						$("#viewCount").html($("#viewCount").text() - 1);
						window.wxc.xcConfirm("删除成功", "success");
						return true;
					},
					error: function () {
						window.wxc.xcConfirm("请稍后再试，请稍后再试", "error");
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
			<li>通知</li>
			<li>我发布的通知<span class="divider">/</span>
			</li>
		</ul>
	</div>
	<div class="row">
		<div class="form-group">
			<a href="<%=basePath%>notice/sendMail.html" type="button" class="btn btn-primary btn-sm">
				<span><i class="icon-edit"></i> 写邮件</span>
			</a>

		</div>
		<table
			class="table table-striped table-bordered table-hover datatable">
			<thead>
				<tr>
					<th>标题</th>
					<th>发布时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${mailNum>0}">
						<c:forEach items="${sendMailList}" var="sendMail">
							<tr id="sendMail${sendMail.id}">
								<td>${sendMail.title}</td>
								<td><fmt:formatDate value="${sendMail.addressTime.time}"
										pattern="yyyy-MM-dd  HH:mm:ss" /></td>
								<td>
									<a href="<%=basePath%>notice/sendViewMail.html?mailId=${sendMail.id}"
									   class="btn btn-default btn-xs" data-toggle="modal"
									   data-target="#viewMail">详细信息</a>
									<a href="<%=basePath%>notice/editMail.html?mailIdToEdit=${sendMail.id}" type="button"
									   class="btn btn-default  btn-xs">
										<span class="glyphicon glyphicon-pencil">编辑邮件</span>
									</a>
									<button onclick="delMail(${sendMail.id})" type="button"
											class="btn btn-danger  btn-xs">
										<span class="glyphicon glyphicon-remove">删除邮件</span>
									</button>

								</td>
							</tr>
						</c:forEach>
					</c:when>
					<%-- <c:otherwise>
						<div class="alert alert-warning alert-dismissable" role="alert">
							<button class="close" type="button" data-dismiss="alert">&times;</button>
							没有发送的邮件
						</div>
					</c:otherwise> --%>
				</c:choose>
			</tbody>
		</table>
		<c:choose>
			<c:when test="${mailNum<=0}">
				<div class="alert alert-warning alert-dismissable" role="alert">
					<button class="close" type="button" data-dismiss="alert">&times;</button>
					没有发送的邮件
				</div>
			</c:when>
		</c:choose>
	</div>
</div>

<div class="modal fade" id="viewMail" tabindex="-1" role="dialog"
	aria-hidden="true" aria-labelledby="modelOpeningReportTime">
	<div class="modal-dialog">
		<div class="modal-content"></div>
	</div>
</div>

<%@include file="/WEB-INF/jsps/page/pageBar.jsp" %>