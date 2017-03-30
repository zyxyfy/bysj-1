<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/includeURL.jsp"%>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal">
		<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
	</button>
	<h4 class="modal-title">添加职工</h4>
</div>
<div class="modal-body">
	<form:form commandName="visitingEmployee" action="${postActionUrl}" method="post"  class="form-horizontal" role="form">
		<form:input type="hidden" path="id" value="${visitingEmployee.id}"/>
		<div class="form-group">
			<label for="inputName" class="col-sm-2">姓名：</label>
			<div class="col-sm-4">
				<form:input type="text" class="form-control" id="inputName" path="name"/>
			</div>
		</div>
		<div class="form-group">
			<label for="inputNum" class="col-sm-2">职工号：</label>
			<div class="col-sm-4">
				<form:input type="text" class="form-control" id="inputNum" path="no"/>
			</div>
		</div>
		<div class="form-group">
			<label for="inlineRadioOptions" class="col-sm-2">性别：</label>
			<div class="col-sm-4">
				<form:radiobutton path="sex" value="男" name="sex" id="sex"/>男
				<form:radiobutton path="sex" value="女" name="sex" id="sex"/>女
			</div>
		</div>
		<div class="form-group">
			<label for="inputEmail3" class="col-sm-2">职位：</label>
			<div class="col-sm-4">
				 <select class="form-control" name="proTitle">
					<option value="0">--请选择职位--</option>
					<c:forEach items="${proTitle}" var="proTitle">
						<option value="${proTitle.description}" <c:if test="${proTitle.description==visitingEmployee.proTitle.description}">selected</c:if>>${proTitle.description}</option>
					</c:forEach>
				</select> 
				
			</div>
		</div>

		 <div class="form-group" >
			<label for="inputEmail4" class="col-sm-2">学位：</label>
			<div class="col-sm-4">
				<select class="form-control" name="degree">
					<option value="0">--请选择学位--</option>
					<c:forEach items="${degree}" var="degree">
						<option value="${degree.description}" <c:if test="${degree.description==visitingEmployee.degree.description}">selected</c:if>>${degree.description}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group">
		<label for="inputEmail5" class="col-sm-2">教研室：</label>	
			<div class="col-md-6">
				<select class="form-control" id="schoolSelectModal"
				 onchange="schoolOnSelectModal()"  required>
					<option value="0">--请选择学院--</option>
					<c:forEach items="${schoolList}" var="school">
						<option value="${school.id}" class="selectSchool" <c:if test="${school.id==visitingEmployee.department.school.id}">selected</c:if>>${school.description}</option>
					</c:forEach>
				</select>
				<select class="form-control " id="departmentSelectModal"
					name="departmentId" required>
					<c:if test="${edited}">
						<c:forEach items="${departmentList}" var="department">
							<option value="${department.id }"
								<c:if test='${currentDepartment.id==department.id}'>selected</c:if>>${department.description}</option>
						</c:forEach>
					</c:if>
				</select>
			</div>
		</div>
		<h4 align="center">个人详细资料</h4>
		<div class="form-group">
			<label for="inputEmail6" class="col-sm-2">公司：</label>
			<div class="col-sm-4">
				<form:input type="text" class="form-control" path="company"/>
			</div>
		</div>
		<div class="form-group">
			<label for="inputEmail7" class="col-sm-2">小语种：</label>
			<div class="col-sm-4">
				<form:input type="text" class="form-control" path="foreignLanguage" />
			</div>
		</div>
		<div class="form-group">
			<label for="inputEmail8" class="col-sm-2">毕业院校：</label>
			<div class="col-sm-4">
				<form:input type="text" class="form-control" path="graduateFrom" />
			</div>
		</div>
		<div class="form-group">
			<label for="inputEmail9" class="col-sm-2">工作经验：</label>
			<div class="col-sm-4">
				<form:input type="text" class="form-control" path="experience" />
			</div>
		</div>
		<div class="form-group">
			<label for="inputEmail10" class="col-sm-2">专业：</label>
			<div class="col-sm-4">
				<form:input type="text" class="form-control" path="speciality" />
			</div>
		</div>
		<div class="form-group">
			<label for="inputEmail11" class="col-sm-2">计划：</label>
			<div class="col-sm-4">
				<form:input type="text" class="form-control" path="plan" />
			</div>
		</div>
		<div class="form-group">
			<label for="inputEmail12" class="col-sm-2">联系电话：</label>
			<div class="col-sm-4">
				<form:input type="text" class="form-control" path="contact.moblie" />
			</div>
		</div>
		<div class="form-group">
			<label for="inputEmail13" class="col-sm-2">QQ：</label>
			<div class="col-sm-4">
				<form:input type="text" class="form-control" path="contact.qq" />
			</div>
		</div>
		<div class="form-group">
			<label for="inputEmail14" class="col-sm-2">邮箱：</label>
			<div class="col-sm-4">
				<form:input type="email" class="form-control" path="contact.email" />
			</div>
		</div>
		
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			<button type="submit" class="btn btn-primary">保存</button>
		</div>
	</form:form>
</div>
