<%--
  Created by IntelliJ IDEA.
  User: 张战
  Date: 2016/5/16
  Time: 19:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>

<script type="text/javascript">
    /*添加资源*/
    function addResource(resourceId, roleId) {
        window.wxc.xcConfirm("确认添加？", "confirm", {
            onOk: function () {
                $.ajax({
                    url: '/bysj3/usersManage/addResource.html',
                    data: {"roleId": roleId, "resourceId": resourceId},
                    dataType: 'json',
                    type: 'post',
                    success: function () {
                        myAlert("添加成功");
                        window.location = '/bysj3/usersManage/universityAdmin/roleToResource.html';
                        return true;
                    },
                    error: function () {
                        window.wxc.xcConfirm("添加失败", "error");
                        return false;
                    }
                });
            }
        })
    }
</script>

<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal"
            aria-hidden="true">×
    </button>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-2">
                <h4 class="modal-title" id="myModalLabel">
                    添加资源
                </h4>
            </div>
            <div class="col-md-5">
                <p style="color: red">（点击对应的功能即可添加）</p>
            </div>
        </div>
    </div>


</div>
<div class="modal-body">
    <p style="color: #808080">当前角色未拥有的功能</p>
    <c:choose>
        <c:when test="${empty resourceList}">
            <div class="alert alert-warning alert-dismissable" role="alert">
                <button class="close" type="button" data-dismiss="alert">&times;</button>
                没有可以显示的信息
            </div>
        </c:when>
        <c:otherwise>
            <c:forEach items="${resourceList}" var="resource">
                <a title="点击即可添加" style="margin-bottom: 5px;margin-right: 5px" class="btn btn-default"
                   onclick="addResource(${resource.id},${roleId})">${resource.description}</a>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>
