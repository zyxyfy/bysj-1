<%--
  Created by IntelliJ IDEA.
  User: 张战
  Date: 2016/5/5
  Time: 21:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>
<script type="text/javascript">
    function sendMail(mailId) {
        checkText();
        var content = $("#mailContent").val();
        window.wxc.xcConfirm("确认回复？", "confirm", {
            onOk: function () {
                $.ajax({
                    url: '/bysj3/notice/replyMail.html',
                    data: {"parentMailId": mailId, "content": content},
                    dataType: 'json',
                    type: 'post',
                    success: function () {
                        window.wxc.xcConfirm("回复成功", "success");
                        window.location = "/bysj3/notice/noticesToMe.html";
                        return true;
                    },
                    error: function () {
                        window.wxc.xcConfirm("回复失败，请稍后再试", "error");
                        return false;
                    }
                });
            }
        })
    }
    function checkText() {
        /*if($("#mailTitle").val().length>30){
         window.wxc.xcConfirm("标题的字数在30字内！","warning");
         return false;
         }*/
        if ($("#mailContent").val().length > 200) {
            window.wxc.xcConfirm("内容的字数在200字内！", "warning");
            return false;
        }
    }
</script>

<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal"
            aria-hidden="true">×
    </button>
    <h4 class="modal-title" id="myModalLabel">
        回复<span style="font-weight: bold ">${mail.addressor.name}</span>的邮件
    </h4>
</div>
<div class="modal-body">

    <%-- <dt>标题：<span style="color: #808080;font-size: smaller">(30字以内)</span></dt>
     <dd>
         <input name="title" value="" type="text" id="mailTitle" class="form-control"
                required="required" min="2"/>
     </dd>--%>

    <dt>内容：<span style="color: #808080;font-size: smaller">(200字以内)</span></dt>

    <dd>
        <textarea id="mailContent" class="form-control" rows="6"
                  cols="63"></textarea>
    </dd>
    <hr>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-2">
                <p>发布时间：</p>

            </div>
            <div class="col-md-5">
                <p><fmt:formatDate value="${mail.addressTime.time}" pattern="yyyy-MM-dd HH:mm:ss"/></p>

            </div>
        </div>
    </div>
    <hr>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-2">
                <p>发布者:</p>

            </div>
            <div class="col-md-2">
                <p>${mail.addressor.name}</p>
            </div>
        </div>
    </div>
    <hr>
    <div style="margin-left: 18px">
        <p>内容：</p>
            <textarea readonly class="form-control" rows="6"
                      cols="63">${mail.content}</textarea>
    </div>

</div>
<div class="modal-footer">
    <button type="button" class="btn btn-default"
            data-dismiss="modal">关闭
    </button>
    <button type="button" onclick="sendMail(${mail.id})" class="btn btn-primary">
        提交更改
    </button>
</div>