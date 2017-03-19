<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsps/includeURL.jsp" %>

<%--<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal">
		<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
	</button>
	<h4 class="modal-title">填写工作内容</h4>
</div>
<div class="modal-body">
	<form action="${actionUrl}" method="POST" class="pageForm required-validate">
	<div>
	<input name="scheduleId" id="scheduleId" type="hidden" value="${schedule.id }"/>
	<h2>填写工作内容:</h2>
	<textarea name="content" rows="10"  cols="70" id ="scheduleContent" class="required">${content}</textarea>
	</div>
	<div>
		<ul>
			<li><div><button type="submit" id="submitSchedule">提交</button></div></li>
			<li><div class="button"><button type="button" class="close">取消</button></div></li>
		</ul>
	</div> 
</form>		
</div>--%>
<script type="text/javascript">
    $("#scheduleContent").click(function () {
        $(this).select();
    })

    function submitConfirm() {
        var contentLength = $("#scheduleContent").val().length;
        if (contentLength > 200) {
            myAlert("字数应不超过200字");
            return false;
        }
        return window.confirm("确认提交？");
    }
</script>
<form action="${actionUrl}" method="post" onsubmit="return submitConfirm()">
    <input name="scheduleId" id="scheduleId" type="hidden" value="${schedule.id }"/>

    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"
                aria-hidden="true">×
        </button>
        <h4 class="modal-title" id="myModalLabel">
            填写工作内容<span style="color: #808080;font-size: smaller">(200字以内)</span>
        </h4>
    </div>
    <div class="modal-body">
        <textarea class="form-control" name="content" rows="10" cols="70" placeholder="请填写工作内容"
                  id="scheduleContent">${content}</textarea>
    </div>
    <div class="modal-footer">
        <a href="<%=basePath%>student/writeSchedule.html" type="button" class="btn btn-default">关闭
        </a>
        <button type="submit" class="btn btn-primary">
            提交更改
        </button>
    </div>
</form>