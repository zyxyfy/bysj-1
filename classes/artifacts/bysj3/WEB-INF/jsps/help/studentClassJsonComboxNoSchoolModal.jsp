<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: zhan
  Date: 2016/4/9
  Time: 14:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--此级联限于在模态框中使用--%>
<%--如果当前用户没有选择学院的权限，则只能通过选择教研室来获取班级，需要将此代码放在form表单中，后台才能通过name属性获取数据--%>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-2">
            <select class="form-control " id="departmentSelectModal" name="departmentSelect"
                    onchange="departmentOnSelectModal()">
                <%--需要从后台传递一个departmentList的参数，里面存放了当前用户所在学院的所有教研室--%>
                <option value="0">--请选择教研室--</option>
                <c:forEach items="${departmentList}" var="department">
                    <option value="${department.id}" class="selectDepartment">${department.description}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col-md-2">
            <select class="form-control" id="studentClassModal" name="studentClassSelect">
                <option value="0">--请选择班级--</option>
            </select>
        </div>
        <div class="col-md-2">
            <button type="submit" class="btn btn-default " data-toggle="modal"
                    data-target="#addOrEditProjectTimeSpan">查询
            </button>
        </div>
    </div>
</div>

