<%--
  Created by IntelliJ IDEA.
  User: zhan
  Date: 2016/4/3
  Time: 9:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>
<!-- datetimepicker -->
<script type="text/javascript">
	$('.form_date').datetimepicker({
		format: 'yyyy/mm/dd ', /*  控制input输出的格式*/
		language:  'zh-CN',   /* 调用中文格式 */
		weekStart : 1,       /*  一周从哪一天开始。0（星期日）到6（星期六）*/
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,/*  */
		minView : 2,/* 日期时间选择器所能够提供的最精确的时间选择视图  
		0  for hour view
		1  for the day view
		2  for month view 
		3  for the 12-month overview
		4 or 'decade' for the 10-year overview.  */
		forceParse : 0,
	    startDate: "2016/04/12"
		
	});

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

        <h4 class="modal-title" id="myModalLabel">
            设置节假日
        </h4>
    </div>
    <div class="modal-body">
         <div class="form-group">
				<label for="dtp_input1" class="col-md-2 control-label">开始时间：</label>
				<div class="input-group date form_date col-md-6" data-date=""
					 data-link-field="dtp_input1"><!--  data-date-format="dd MM yyyy "-->
					<input id="starttime" class="form-control" size="16" type="text" value="" name="startTime" required
						   placeholder="例如：2016/01/01">
					<span class="input-group-addon"><span
						class="glyphicon glyphicon-remove"></span></span> <span
						class="input-group-addon"><span
						class="glyphicon glyphicon-th"></span></span>
				</div>
				<input type="hidden" id="dtp_input1" value="" /><br />
			</div>
			<div class="form-group">
				<label for="dtp_input2" class="col-md-2 control-label">结束时间：</label>
				<div class="input-group date form_date col-md-6" data-date=""
					data-date-format="dd MM yyyy" data-link-field="dtp_input2"
					><!-- data-link-format="yyyy-mm-dd" -->
					<input id="endtime" class="form-control" size="16" type="text" value="" name="endTime" required
						   placeholder="例如：2016/01/01">
					<span class="input-group-addon"><span
						class="glyphicon glyphicon-remove"></span></span> <span
						class="input-group-addon"><span
						class="glyphicon glyphicon-calendar"></span></span>
				</div>
				<input type="hidden" id="dtp_input2" value="" /><br />
			</div>
                          
                          原因：
		<textarea class="form-control" rows="5" content="${description}" cols="63" name="description"
				  required></textarea>
        <br>
    </div>
    <div class="modal-footer">
		<a type="button" class="btn btn-default" href="<%=basePath%>setTime.html"
		>关闭
		</a>
        <button type="submit" class="btn btn-primary">
            提交更改
        </button>
    </div>

</form>