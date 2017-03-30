<%--
  Created by IntelliJ IDEA.
  User: 张战
  Date: 2016/4/9
  Time: 22:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>

<script type="text/javascript">

    function confirmUpdate() {
        var qq = $("#qq").val();
        if (!/^1[0-9]{5,13}$/.test(qq)) {
            window.wxc.xcConfirm("请输入合法的QQ", "warning");
            return false;
        }
        var mobile = $("#mobile").val();
        if (!/^1[0-9]{10}$/.test(mobile)) {
            window.wxc.xcConfirm("请输入合法的手机号", "warning");
            return false;
        }

        var email = $("#email").val();
        if (!/^(\w)+@(\w){1,8}\.(\w){2,3}$/.test(email)) {
            window.wxc.xcConfirm("请输入合法的邮箱", "warning");
            return false;
        }
        window.wxc.xcConfirm("确认修改？", "confirm", {
            onOk: function () {
                $("#contact").submit();
            }
        });

    }
</script>
<%--onsubmit=" return confirmUpdate()"--%>
<form:form commandName="contact" enctype="multipart/form-data" action="${actionURL}">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"
                aria-hidden="true">×
        </button>
        <h4 class="modal-title" id="myModalLabel">
            修改个人信息
        </h4>
    </div>
    <div class="modal-body">
        编号：${actor.no}
        姓名：${actor.name}
        性别：${actor.sex}<br>
        QQ:<form:input path="qq" id="qq" cssClass="form-control"/>
        Moblie:<form:input path="moblie" id="mobile" cssClass="form-control"/>
        Email:<form:input path="email" id="email" cssClass="form-control"/>
        上传签名照：<input class="form-control" type="file" name="signatureFile" accept="image/*" placeholder="请选择自己的签名照片">
        <br>
        已上传的签名照：<img src="<%=basePath%>viewSignatureImg.html" height="200px" width="300px"/>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default"
                data-dismiss="modal">关闭
        </button>
        <button type="button" class="btn btn-primary" onclick="confirmUpdate()">
            提交更改
        </button>
    </div>
</form:form>
