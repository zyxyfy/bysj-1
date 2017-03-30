<%--
  Created by IntelliJ IDEA.
  User: zhan
  Date: 2016/4/3
  Time: 9:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/jsps/includeURL.jsp"%>

<script type="text/javascript">
	/* $('.form_datetime').datetimepicker({
	 format: 'yyyy-mm-dd ',
	 language:  'zh-CN',
	 weekStart : 1,
	 todayBtn : 1,
	 autoclose : 1,
	 todayHighlight : 1,
	 startView : 2,
	 forceParse : 0,

	 showMeridian : 1
	 }); */

	$('.form_date').datetimepicker({
		format : 'yyyy/mm/dd ',
		language : 'zh-CN',
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		minView : 2,
		forceParse : 0,
		startDate : "2016/03/12"

	});
	//$('#datetimepicker').datetimepicker('setStartDate', new Date());

	function checkvalue() {
		var start = $("#starttime").val();
		var end = $("#endtime").val();
		var startYear = start.split("/")[0];
		var startMonth = start.split("/")[1];
		var startMonth_Day = start.split("/")[2];
		var endYear = end.split("/")[0];
		var endMonth = end.split("/")[1];
		var endMonth_Day = end.split("/")[2];
		var startNum = startYear + startMonth + startMonth_Day;
		var endNum = endYear + endMonth + endMonth_Day;
		if (startNum > endNum) {
			myAlert("结束时间应晚于开始时间");
			return false;
		}
		return true;
	}
</script>


<form action="${actionUrl}" onsubmit="return checkvalue()" method="post">
	<div class="modal-header">
		<h4 class="modal-title" id="myModalLabel">添加/编辑时间</h4>
	</div>
	<div class="modal-body">
		<div class="form-group">
			<label for="dtp_input1" class="col-md-2 control-label">开始时间：</label>

			<div class="input-group date form_date col-md-6" data-date=""
				data-date-format="dd MM yyyy " data-link-field="dtp_input1">
				<input id="starttime" class="form-control" size="16" type="text" required placeholder="例如：2016/01/01"
					   value="${projectTimeSpanStartTime}" name="startTime"> <span
					class="input-group-addon"><span
					class="glyphicon glyphicon-remove"></span></span> <span
					class="input-group-addon"><span
					class="glyphicon glyphicon-th"></span></span>
			</div>
			<input type="hidden" id="dtp_input1" value="" /><br />
		</div>
		<div class="form-group">
			<label for="dtp_input2" class="col-md-2 control-label">结束时间：</label>

			<div class="input-group date form_date col-md-6" data-date=""
				data-date-format="dd MM yyyy" data-link-field="dtp_input2">
				<!-- data-link-format="yyyy-mm-dd" -->
				<input id="endtime" class="form-control" size="16" type="text" required placeholder="例如：2016/01/01"
					   value="${projectTimeSpanEndTime}" name="endTime">
				<!-- readonly -->
				<span class="input-group-addon"><span
					class="glyphicon glyphicon-remove"></span></span> <span
					class="input-group-addon"><span
					class="glyphicon glyphicon-calendar"></span></span>
			</div>
			<input type="hidden" id="dtp_input2" value="" /><br />
		</div>
	</div>
	<div class="modal-footer">
		<a type="button" class="btn btn-default" href="<%=basePath%>setTime.html">关闭
		</a>
		<button type="submit" class="btn btn-primary">提交更改</button>
	</div>
</form>
