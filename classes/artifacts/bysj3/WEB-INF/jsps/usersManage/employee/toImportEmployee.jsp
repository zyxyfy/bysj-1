<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/includeURL.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:url value="/importEmployeesFromExcel.html" var="actionURL"></c:url>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal">
		<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
	</button>
	<h4 class="modal-title">导入Excel教师信息表</h4>
</div>
<form action="${actionURL}" enctype="multipart/form-data"
	method="post">
	<div class="modal-body">
		<div class="form-group">
			<input type="file" name="file" class="required">
			<p class="help-block">请选择要导入的教师EXCEL表</p>
			<p class="help-block" style="color:red">导入成功后该对话框会自动消失，数据导入过程请耐心等待</p>
		</div>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		<button type="submit" class="btn btn-primary">导入</button>
	</div>
</form>