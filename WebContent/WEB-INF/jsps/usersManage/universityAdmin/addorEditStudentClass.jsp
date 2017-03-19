<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/includeURL.jsp"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal">
		<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
	</button>
	<h4 class="modal-title">编辑班级</h4>
</div>
<div class="modal-body">
	<form class="form-horizontal" role="form" action="${postActionUrl}"
		method="post">
		<input type="hidden" id="studentClassId" name="editId" value="${studentClass.id}"/>
		<div class="form-group">
		<label for="inputEmail5" class="col-sm-2">专业：</label>
		<input type="text" id="majorDescription" name="majorDescription" value="${major.description}">
		<input type="hidden" id="majorId" name="majorId" value="${major.id }"/>
		</div>
		<div class="form-group">
			<label for="inputName" class="col-sm-2">班级名称：</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="inputName"
					name="description" value="${studentClass.description}">
			</div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			<button type="submit" class="btn btn-primary">保存</button>
		</div>
	</form>
</div>
