<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
    function checkText() {
        var s = $("#remark").val().length;
        if (s > 200) {
            myAlert("字数不能大于200字");
        } else {
            window.wxc.xcConfirm("确认提交？", "confirm", {
                onOk: function () {
                    $("#uploadRemark").submit();
                }
            })
        }
    }
</script>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal"
            aria-hidden="true">×
    </button>
    <div class="row">
        <div class="col-sm-3">
            <h5 class="modal-title" id="myModalLabel">添加或修改评语</h5>
        </div>
        <div class="col-sm-6">
            <span style="color:grey;">(200字以内)</span>
        </div>
    </div>

</div>
<form action="${actionUrl}" method="post" id="uploadRemark">
    <input type="hidden" id="stageAchievementId" name="stageAchievementId" value="${stageAchievement.id}"/>
    <div class="modal-body">
        <textarea name="remark" id="remark" cols="50" rows="8"
                  class="form-control">${stageAchievement.remark }</textarea>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭
        </button>
        <button type="button" onclick="checkText()" class="btn btn-primary">提交更改</button>
    </div>
</form>

