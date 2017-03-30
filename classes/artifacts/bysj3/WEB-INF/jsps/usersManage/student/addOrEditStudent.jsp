<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/includeURL.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal">
		<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
	</button>
	<h4 class="modal-title">添加/修改学生</h4>
</div>
<div class="modal-body">
	<form:form commandName="student" action="${postActionUrl}"
		method="post" class="form-horizontal">
		<form:input type="hidden" path="id" value="${student.id}" />
		<div class="form-group">
			<label for="inputName" class="col-sm-2">学号：</label>
			<div class="col-sm-4">
				<form:input type="text" class="form-control" id="inputName"
					path="no" />
			</div>
		</div>
		<div class="form-group">
			<label for="inputNum" class="col-sm-2">姓名：</label>
			<div class="col-sm-4">
				<form:input type="text" class="form-control" id="inputNum"
					path="name" />
			</div>
		</div>
		<div class="form-group">
			<label for="inlineRadioOptions" class="col-sm-2">性别：</label>
			<div class="col-sm-4">
				<form:radiobutton path="sex" value="男" name="sex"
					id="sex1" />
				男
				<form:radiobutton path="sex" value="女" name="sex"
					id="sex2" />
				女
			</div>
		</div>
		<div class="form-group">
			<label for="inputNum" class="col-sm-2">班级：</label>
			<div class="col-md-3">
				<%--需要从后台传递一个schoolList的参数，里面存放了所有的学院--%>
				<select class="form-control" id="schoolSelectModal"
					name="schoolSelect" onchange="schoolOnSelectModal()" >
					<option value="0">--请选择学院(必填)--</option>
					<c:forEach items="${schoolList}" var="school">
						<option value="${school.id }" class="selectSchool" >${school.description}</option>
					</c:forEach>
				</select>
			</div>

			<div class="col-md-3">
				<select class="form-control " id="departmentSelectModal"
					name="departmentSelect" onchange="departmentOnSelectModal()" >
					<option value="0">--请选择教研室(必填)--</option> 
				</select>
			</div>
			<div class="col-md-3">
				<select class="form-control" id="studentClassModal"
					name="studentClassSelect">
					<option value="0">--请选择班级(必填)--</option>
				</select>
			</div>
		</div>

		<div class="form-group">
			<label for="inputEmail3" class="col-sm-2">联系电话：</label>
			<div class="col-sm-4">
				<form:input type="text" class="form-control" id="inputEmail3"
					path="contact.moblie" />
			</div>
		</div>
		<div class="form-group">
			<label for="inputEmail3" class="col-sm-2">QQ：</label>
			<div class="col-sm-4">
				<form:input type="text" class="form-control" id="inputEmail3"
					path="contact.qq" />
			</div>
		</div>
		<div class="form-group">
			<label for="inputEmail3" class="col-sm-2">邮箱: </label>
			<div class="col-sm-4">
				<form:input type="email" class="form-control" id="inputEmail3"
					path="contact.email" />
			</div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			<button type="submit" class="btn btn-primary">保存</button>
		</div>
	</form:form>

</div>


