<%--
  Created by IntelliJ IDEA.
  User: zhan
  Date: 2016/4/9
  Time: 14:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%--在模态框中通过学院来获取教研室，复制以下代码到相应的jsp中--%>
<%--需要从后台传递名为schoolList的所有学院的集合--%>
<div class="row">
    <div class="col-md-6">
        <select class="form-control" id="schoolSelectModal"
                onchange="schoolOnSelectModal()">
            <option value="0">--请选择学院--</option>
            <c:forEach items="${schoolList}" var="school">
                <option value="${school.id}" class="selectSchool">${school.description}</option>
            </c:forEach>
        </select>
    </div>
    <div class="col-md-6">
        <select class="form-control " id="departmentSelectModal" name="departmentSelect"></select>
    </div>
</div>
<select class="form-control " id="departmentSelectModal"
					name="departmentId">
					<c:if test="${showDepartment}">
						<c:forEach items="${departmentList}" var="department">
							<option value="${department.id }" <c:if test='${currentDepartment.id==department.id}'>selected</c:if>>${department.description}</option>
						</c:forEach>
					</c:if>
					</select>
