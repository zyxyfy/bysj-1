<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsps/includeURL.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    function deleteEmployee(employeeId) {
        window.wxc.xcConfirm("确认删除？", "confirm", {
            onOk: function () {
                $.ajax({
                    url: '/bysj3/usersManage/employeeDelete.html',
                    type: 'GET',
                    dataType: 'json',
                    data: {"employeeId": employeeId},
                    success: function (data) {
                        $("#employeeRow" + employeeId).remove();
                        myAlert("删除成功");
                        return true;
                    },
                    error: function () {
                        myAlert("删除失败，请稍后再试")
                        return false;
                    }
                });
            }
        });

    }
    function resetPassword(employeeId) {
        window.wxc.xcConfirm("确认重置？", "confirm", {
            onOk: function () {
                $.ajax({
                    url: '/bysj3/usersManage/resetpassWord.html',
                    type: 'GET',
                    dataType: 'json',
                    data: {"employeeId": employeeId},
                    success: function (data) {
                        myAlert("重置成功");
                        return true;
                    },
                    error: function () {
                        myAlert("重置失败，请稍后再试");
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
            <li>用户管理</li>
            <li>查看教师信息<span class="divider">/</span>
            </li>
        </ul>
    </div>
    <div class="row">
        <form action="${actionUrl}" class="form-inline" method="get">
            <div class="col-md-6">
                <div class="form-group">
                    <label for="name">教师姓名</label> <input type="text" id="name"
                                                          class="form-control " name="name" placeholder="请输入教师姓名">
                    <label
                            for="title">职工号</label> <input type="text" id="title" class="form-control "
                                                           name="no" placeholder="请输入职工号">
                </div>
            </div>
            <div class="col-md-6">
                <sec:authorize ifAnyGranted="ROLE_COLLEGE_ADMIN">
                    <label for="schoolSelect">学院</label>
                    <select class="form-control" id="schoolSelect" name="schoolId"
                            onchange="schoolOnSelect()">
                        <option value="0">--请选择学院--</option>
                        <c:forEach items="${schoolList}" var="school">
                            <option value="${school.id }" class="selectSchool">${school.description}</option>
                        </c:forEach>
                    </select>

                    <label for="schoolSelect">教研室</label>
                    <select class="form-control " id="departmentSelect"
                            name="departmentId"></select>
                </sec:authorize>
                <sec:authorize ifAnyGranted="ROLE_SCHOOL_ADMIN"
                               ifNotGranted="ROLE_COLLEGE_ADMIN">
                    <select class="form-control" name="departmentId">
                        <option value="0">-请选择教研室--</option>
                        <c:forEach items="${departments}" var="department">
                            <option value="${department.id}">${department.description}</option>
                        </c:forEach>
                    </select>
                </sec:authorize>
                <button type="submit" class="btn btn-default ">查询</button>
            </div>
        </form>
    </div>
    <br>
    <div class="row">
        <sec:authorize ifAnyGranted="ROLE_DEPARTMENT_DIRECTOR"
                       ifNotGranted="ROLE_SCHOOL_ADMIN,ROLE_COLLEGE_ADMIN">
            <a class="btn btn-primary btn-sm"
               href="/bysj3/usersManage/department/employeeAdd.html"
               data-toggle="modal" data-target="#addTeacher"><i
                    class="icon-plus"></i> 添加新教師</a>
        </sec:authorize>
        <sec:authorize ifAnyGranted="ROLE_SCHOOL_ADMIN"
                       ifNotGranted="ROLE_COLLEGE_ADMIN">
            <a class="btn btn-primary btn-sm"
               href="/bysj3/usersManage/school/employeeAdd.html"
               data-toggle="modal" data-target="#addTeacher"><i
                    class="icon-plus"></i> 添加新教師</a>
        </sec:authorize>
        <sec:authorize ifAnyGranted="ROLE_COLLEGE_ADMIN">
            <a class="btn btn-primary btn-sm"
               href="/bysj3/usersManage/college/employeeAdd.html"
               data-toggle="modal" data-target="#addTeacher"><i
                    class="icon-plus"></i> 添加新教师</a>
        </sec:authorize>
        <a class="btn btn-primary btn-sm"
           href="<c:url value='/upLoadEmployee.html'/>" data-toggle="modal"
           data-target="#editExcel"><i class="icon-plus"></i>导入教师Excel</a>
    </div>
    <br>


    <div class="row-fluid">
        <table
                class="table table-striped table-bordered table-hover datatable">
            <thead>
            <tr>
                <th>职工</th>
                <th>姓名</th>
                <th>性別</th>
                <th>职称</th>
                <th>所属教研室</th>
                <th>联系电话</th>
                <th>学位</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${not empty employeeList}">
                    <c:forEach items="${employeeList}" var="employee">
                        <tr id="employeeRow${employee.id}">
                            <td class="employeeNo${employee.id }">${employee.no }</td>
                            <td class="employeeName${employee.id}">${employee.name }</td>
                            <td class="employeeSex${employee.id}">${employee.sex }</td>
                            <td class="employeeProTitle${employee.id }">${employee.proTitle.description }</td>
                            <td class="employeeDepartment${employee.id}">${employee.department.description}</td>
                            <td class="employeeMoblie${employee.id }">${employee.contact.moblie }</td>
                            <td class="employeeDegree${employee.id}">${employee.degree.description}</td>
                            <td>
                                    <%--教研室主任没有修改和删除的权限--%>
                                <sec:authorize ifAnyGranted="ROLE_SCHOOL_ADMIN,ROLE_COLLEGE_ADMIN">
                                    <a class="btn btn-danger btn-xs"
                                       onclick="deleteEmployee(${employee.id})">删除</a> <a
                                        href="/bysj3/usersManage/${edit}/employeeEdit.html?employeeId=${employee.id}"
                                        class="btn btn-warning btn-xs" type="button"
                                        data-toggle="modal" data-target="#modifyTeacher"> <i
                                        class="icon-edit"></i> 修改
                                </a>
                                </sec:authorize>
                                <a class="btn btn-success btn-xs"
                                   onclick="resetPassword(${employee.id})"> <i
                                        class="icon-lock"></i> 重置密码
                                </a></td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-warning alert-dismissable" role="alert">
                        <button class="close" type="button" data-dismiss="alert">&times;</button>
                        没有数据
                    </div>
                </c:otherwise>
            </c:choose>

            </tbody>
        </table>
    </div>
    <!-- 分页 -->
    <div class="row">
        <p class="text-center">
            <%@ include
                    file="/WEB-INF/jsps/page/pageBar.jsp" %>
        </p>
    </div>

    <div class="modal fade" id="addTeacher" tabindex="-1" role="dialog"
         aria-hidden="true" aria-labelledby="modelOpeningReportTime">
        <div class="modal-dialog">
            <div class="modal-content"></div>
        </div>
    </div>
    <div class="modal fade" id="modifyTeacher" tabindex="-1" role="dialog"
         aria-hidden="true" aria-labelledby="modelOpeningReportTime">
        <div class="modal-dialog">
            <div class="modal-content"></div>
        </div>
    </div>
    <div class="modal fade" id="editExcel" tabindex="-1" role="dialog"
         aria-hidden="true" aria-labelledby="modelOpeningReportTime">
        <div class="modal-dialog">
            <div class="modal-content"></div>
        </div>
    </div>
</div>