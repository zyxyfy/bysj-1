<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsps/includeURL.jsp"%>

<script type="text/javascript">
	function checkContent() {
		var contentLength = $("#guideRecordContentArea").val().length;
		if (contentLength > 200) {
			myAlert("字数应不超过200字");
			return false;
		}
        window.wxc.xcConfirm("确认提交？", "confirm", {
            onOk: function () {
                $("#uploadrecord").submit();
            }
        })
	}

    $('.form_date').datetimepicker({
        format: 'yyyy/mm/dd ',
        language: 'zh-CN',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0,
        startDate: "2016/03/12"

    });
</script>
<form action="${actionUrl}" method="post" id="uploadrecord">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">×</button>
        <h4 class="modal-title" id="myModalLabel">填写指导记录</h4>
	</div>
	<div class="modal-body">
		<input type="hidden" name="guideRecordId" id="guideRecordId"
			value="${editGuideRecord.id}" />

		<p>
			<!-- 测试 -->
		<div class="form-group">
			<label for="dtp_input1" class="col-md-1 control-label">时间：</label>
            <div class="input-group date form_date col-md-6"
				data-date="" data-date-format="dd MM yyyy "
				data-link-field="dtp_input1">
                <input class="form-control" size="16" type="text"
					value="${time}" name="time">
					<span class="input-group-addon"><span
					class="glyphicon glyphicon-remove"></span></span> <span
					class="input-group-addon"><span
					class="glyphicon glyphicon-th"></span></span>
            </div>
            <input type="hidden" id="dtp_input1" value="" /><br />
        </div>
        </p>
		
        <p>
            <label>地点：</label> <select id="guideRecordClassRoom"
				name="classRoomId">
            <c:forEach items="${classRooms}" var="classRoom">
                <option value="${classRoom.id }"
						label="${classRoom.description}"
						<c:if test="${classRoom.id == editGuideRecord.classRoom.id}">
                            selected
                        </c:if>>${classRoom.description}</option>
            </c:forEach>
        </select>
        </p>
        <p>
            <label>指导内容：</label><span
				style="font-size: smaller;color: #808080">（200字以内）</span>
			<textarea cols="80" rows="5" name="description"
				id="guideRecordContentArea">${editGuideRecord.description}</textarea>
        </p>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default"
			data-dismiss="modal">关闭
        </button>
        <button type="button" onclick="checkContent()" class="btn btn-primary">提交更改</button>
    </div>
</form>

