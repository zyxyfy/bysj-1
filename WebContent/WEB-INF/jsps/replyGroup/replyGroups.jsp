<%--
  Created by IntelliJ IDEA.
  User: 张战
  Date: 2016/3/1
  Time: 14:32
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/includeURL.jsp"%>

<script type="text/javascript">
    function delReplyGroup(replyGroupId) {
    	window.wxc.xcConfirm("确认删除？","confirm",{
			onOk: function(){
				$.ajax({
	                url: '/bysj3/process/delReplyGroupById.html',
	                data: {"replyGroupId": replyGroupId},
	                dataType: 'json',
	                type: 'POST',
	                success: function (data) {
	                    $("#replyGroupList" + replyGroupId).remove();
	                    myAlert("删除成功");
	                    return true;
	                },
	                error: function () {
						myAlert("删除失败，请稍后再试");
	                    return false;
	                }
	            });
			}});
        /* var confirmDel = window.confirm("确认删除？");
        if (confirmDel) {
            $.ajax({
                url: '/bysj3/process/delReplyGroupById.html',
                data: {"replyGroupId": replyGroupId},
                dataType: 'json',
                type: 'POST',
                success: function (data) {
                    $("#replyGroupList" + replyGroupId).remove();
                    myAlert("删除成功");
                    return true;
                },
                error: function () {
                    myAlert("网络错误，请稍后再试");
                    return false;
                }
            });
        } */
    }
</script>

<div class="container-fluid" style="width: 100%">
	<div class="row-fluid">
		<ul class="breadcrumb">
			<li>评审流程</li>
			<li>答辩分组安排<span class="divider">/</span>
			</li>
		</ul>
	</div>

	<div class="row">
		<div class="col-md-8">
			<form class="form-inline" role="form" method="get"
				action="${actionURL}">
				<div class="form-group ">
					<label for="name">小组名称：</label>
					<c:choose>
						<c:when test="${groupName==null}">
							<input type="text" name="replyGroupName" class="form-control "
								   id="name" placeholder="小组名称">
						</c:when>
						<c:otherwise>
							<input type="text" name="replyGroupName" class="form-control "
								id="name" placeholder="${groupName}">
						</c:otherwise>
					</c:choose>
					<button type="submit" class="btn btn-primary btn-sm">
						<i class="icon-search"></i>检索
					</button>
				</div>
			</form>
		</div>
		
	</div>
<br>
	<div class="row">
		<a href="<%=basePath%>process/addReplyGroup.html"
			class="btn btn-primary btn-sm" data-toggle="modal"
			data-target="#addOrEditReplyGroup"> <i class="icon-plus"></i>
			添加小组
		</a>
	</div> 
	<br>
	<div class="row-fluid">
		<table
			class="table table-striped table-bordered table-hover datatable">
			<thead>
				<tr>
					<th>小组名称</th>
					<th>小组组长</th>
					<th>答辩老师</th>
					<th>答辩学生</th>
					<th>所属教研室</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty replyGroupList}">
					</c:when>
					<c:otherwise>
						<c:forEach items="${replyGroupList}" var="replyGroup">
							<tr id="replyGroupList${replyGroup.id}">
								<td>${replyGroup.description}</td>
								<td>${replyGroup.leader.name}</td>
								<%--答辩老师--%>
								<td><c:forEach items="${replyGroup.members}" var="tutor">
                                    ${tutor.name} &nbsp;&nbsp;
                                </c:forEach></td>
								<%--答辩学生--%>
								<td><c:forEach items="${replyGroup.graduateProject}"
										var="graduateProject">
                                    ${graduateProject.student.name}&nbsp;&nbsp;
                                </c:forEach></td>
								<td>${replyGroup.department.description}</td>
								<td>
									<button class="btn btn-danger btn-xs"
										onclick="delReplyGroup(${replyGroup.id})">
										<i class="icon-remove"></i> 删除
									</button> <a class="btn btn-warning btn-xs"
									href="<%=basePath%>process/editReplyGroup.html?groupId=${replyGroup.id}"
									data-toggle="modal" data-target="#addOrEditReplyGroup"><i class="icon-edit"></i> 修改</a>
								</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>

			</tbody>
		</table>
		<c:choose>
			<c:when test="${empty replyGroupList}">
				<div class="alert alert-warning alert-dismissable" role="alert">
					<button class="close" type="button" data-dismiss="alert">&times;</button>
					没有可以显示的答辩小组的信息
				</div>
			</c:when>
		</c:choose>
	</div>
	<div class="row">
		<%--<ul class="pagination pagination-centered  pagination-sm ">
            <li><a href="#">&larr; 第一页</a></li>
            <li><a href="#">11</a></li>
            <li><a href="#">12</a></li>
            <li class="active"><a href="#">13</a></li>
            <li><a href="#">14</a></li>
            <li><a href="#">15</a></li>
            <li class="disabled"><a href="#">最后一页&larr; </a></li>
        </ul>--%>
		<%@include file="/WEB-INF/jsps/page/pageBar.jsp"%>
	</div>

	<%--添加小组jsp填充的地方 --%>
	<div class="modal fade" data-backdrop="static" data-keyboard="false" id="addOrEditReplyGroup" tabindex="-1"
		 role="dialog" aria-hidden="true"
		 aria-labelledby="modelOpeningReportTime">
		<div class="modal-dialog">
			<div class="modal-content">
				<%--此处会填充另外一个jsp的内容--%>
			</div>
		</div>
	</div>
</div>

