<%--
  Created by IntelliJ IDEA.
  User: zhan
  Date: 2016/4/30
  Time: 21:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>

<script type="text/javascript">
    /*删除角色中的资源*/
    function delRole(roleId, resourceId) {
        window.wxc.xcConfirm("确认删除该角色的资源？", "confirm", {
            onOk: function () {
                $.ajax({
                    url: '/bysj3/usersManage/delResource.html',
                    data: {"roleId": roleId, "resourceId": resourceId},
                    dataType: 'json',
                    type: 'get',
                    success: function () {
                        $("#delResource" + roleId + resourceId).remove();
                        window.wxc.xcConfirm("删除成功", "success");
                        return true;
                    },
                    error: function () {
                        window.wxc.xcConfirm("删除失败", "error");
                        return false;
                    }
                });
            }
        });
    }

    /*添加角色中的资源*//*
     function addRole(roleId) {
     window.wxc.xcConfirm("确认添加资源？","confirm",{
     onOk: function () {
     $.ajax({
     url:'/bysj3/usersManage/addResource.html',
     data:{"roleId":roleId},

     });
     }
     });
     }*/
</script>
<div class="container-fluid" style="width: 100%">
    <div class="row-fluid">
        <ul class="breadcrumb">
            <li>用户管理</li>
            <li>用户资源分配<span class="divider">/</span>
            </li>
        </ul>
    </div>

    <br>

    <div class="row-fluid">
        <table
                class="table table-striped table-bordered table-hover datatable">
            <thead>
            <tr>
                <th>角色</th>
                <th>资源</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${empty roleList}">
                    <div class="alert alert-warning alert-dismissable" role="alert">
                        <button class="close" type="button" data-dismiss="alert">&times;</button>
                        没有可以显示的数据
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${roleList}" var="role">
                        <tr>
                            <td>${role.description}</td>
                            <td>
                                <div class="form-group">
                                    <c:forEach items="${role.roleResource}" var="roleResource">
                                        <button id="delResource${role.id}${roleResource.resource.id}"
                                                style="margin-bottom: 5px"
                                                class="btn btn-default btn-sm"
                                                onclick="delRole(${role.id},${roleResource.resource.id})">${roleResource.resource.description}<i
                                                class="icon-remove-sign"></i></button>
                                    </c:forEach>
                                    <a id="addRole${role.id}" data-toggle="modal" data-target="#addTeacher"
                                       href="<%=basePath%>usersManage/addResource.html?roleId=${role.id}"
                                       class="btn btn-primary btn-sm"><i
                                            class="icon-plus"></i> 添加
                                    </a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>
    <%@include file="/WEB-INF/jsps/page/pageBar.jsp" %>

    <%--如果需要在模态框中加载另一个jsp，则需要书写以下的div--%>
    <div class="modal fade" id="addTeacher" tabindex="-1" role="dialog"
         aria-hidden="true" aria-labelledby="modelOpeningReportTime">
        <div class="modal-dialog">
            <div class="modal-content">
                <%--此处会填充另外一个jsp的内容--%>
            </div>
        </div>
    </div>
</div>