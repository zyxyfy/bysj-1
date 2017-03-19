<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsps/includeURL.jsp" %>
<script type="text/javascript">
    function checkText() {
        var text = $("#evaluateText").val();
        if (text.length > 80) {
            myAlert("字数在40字以内！");
            return false;
        } else {
            window.wxc.xcConfirm("确认提交？", "confirm", {
                onOk: function () {
                    $("#submitEvaluate").submit();
                }
            })
        }
    }
</script>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal"
            aria-hidden="true">×
    </button>
    <h4 class="modal-title" id="myModalLabel">
        审核
    </h4>
</div>

<form action="${actionUrl}" id="submitEvaluate" method=post>
<div class="modal-body">
    
		<div>

            <input name="scheduleId" type="hidden" value="${schedule.id}"/>

            <p style="float: left">填写评价内容</p>

            <p style="color: #808080;float: left">(40字以内)</p>

            <textarea name="content" id="evaluateText" rows="10" cols="70"
                      class="form-control required">${addorEditScheduleRemark}</textarea>
		</div>
	
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-default"
            data-dismiss="modal">关闭
    </button>
    <button type="button" onclick="checkText()" class="btn btn-primary">
        提交更改
    </button>
</div>
</form>