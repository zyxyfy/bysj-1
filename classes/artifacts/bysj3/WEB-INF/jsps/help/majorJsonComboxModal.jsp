<%--
  Created by IntelliJ IDEA.
  User: 张战
  Date: 2016/4/16
  Time: 22:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-2">
            <%--需要从后台传递一个schoolList的参数，里面存放了所有的学院--%>
            <select class="form-control" id="schoolSelectModal" name="schoolSelect" onchange="schoolOnSelectModal()">
                <option value="0">--请选择学院--</option>
                <c:forEach items="${schoolList}" var="school">
                    <option value="${school.id }"
                            class="selectSchool">${school.description}</option>
                </c:forEach>
            </select>
        </div>

        <div class="col-md-2">
            <select class="form-control " id="departmentSelectModal" name="departmentSelect"
                    onchange="departmentOnSelectToMajorModal()">
                <option value="0">--请选择教研室--</option>
            </select>
        </div>
        <div class="col-md-2">
            <select class="form-control" id="majorModal" name="majorSelect">
                <option value="0">--请选择专业--</option>
            </select>
        </div>
        <div class="col-md-2">
            <button type="submit" class="btn btn-default " data-toggle="modal"
                    data-target="#addOrEditProjectTimeSpan">查询
            </button>
        </div>
    </div>
</div>
