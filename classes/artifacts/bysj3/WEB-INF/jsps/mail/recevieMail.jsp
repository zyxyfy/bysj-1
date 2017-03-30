<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsps/includeURL.jsp"%>
<script type="text/javascript">
	/*function delMail(delId) {
		$.confirm("确认提交 ？");
		var confirmDel = window.confirm("确认删除？");
		if (confirmDel) {
			$.ajax({});
		}
	}*/
</script>
<div class="container-fluid" style="width: 100%">
	<div class="row-fluid">
		<ul class="breadcrumb">
			<li>通知</li>
			<li>发给我的通知<span class="divider">/</span>
			</li>
		</ul>
	</div>
	<h2>
		<i class="icon-bell icon-large"></i> 我收到的通知
	</h2>
	<table class="table table-striped table-bordered table-hover datatable">
		<thead>
			<tr>
				<th>标题</th>
				<th>发布时间</th>
				<th>发件人</th>
				<th>操作</th>
				<th>是否已读</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${mailNum>0}">
					<c:forEach items="${recevieMailList}" var="recevieMail">
						<tr>
							<td>${recevieMail.mail.title }</td>
							<td><fmt:formatDate
									value="${recevieMail.mail.addressTime.time}"
									pattern="yyy-MM-dd  HH:mm:ss" /></td>
							<td>${recevieMail.mail.addressor.name}</td>
							<td><a href="<%=basePath%>notice/receiveViewMail.html?mailId=${recevieMail.mail.id}"
								   class="btn btn-primary btn-xs" data-toggle="modal"
								   data-target="#viewMail"><i class="icon-desktop"></i> 详情</a>
								<button class="btn btn-default btn-xs" data-toggle="modal" data-target="#viewMail"
										href="<%=basePath%>notice/replyMail.html?parentMailId=${recevieMail.mail.id}">
									<i class="icon-mail-reply">回复</i>
								</button>
							</td>
							<td><c:choose>
									<c:when test="${recevieMail.isRead==null}">
										<span class="label label-warning"><i class="icon-eye-close"></i> 未读</span>
									</c:when>
									<c:otherwise>
										<span class="label label-success"><i class="icon-eye-open"></i> 已读</span>
									</c:otherwise>
								</c:choose></td>
						</tr>
					</c:forEach>
				</c:when>
				<%-- <c:otherwise>
					<div class="alert alert-warning alert-dismissable" role="alert">
						<button class="close" type="button" data-dismiss="alert">&times;</button>
						您当前没有收到任何消息！
					</div>
				</c:otherwise> --%>
			</c:choose>
		</tbody>
	</table>
	<c:choose>
		<c:when test="${mailNum<=0}">
			<div class="alert alert-warning alert-dismissable" role="alert">
				<button class="close" type="button" data-dismiss="alert">&times;</button>
				您当前没有收到任何消息！
			</div>
		</c:when>
	</c:choose>
</div>
<div class="modal fade" id="viewMail" tabindex="-1" role="dialog"
	aria-hidden="true" aria-labelledby="modelOpeningReportTime">
	<div class="modal-dialog">
		<div class="modal-content"></div>
	</div>
</div>

<%@include file="/WEB-INF/jsps/page/pageBar.jsp" %>