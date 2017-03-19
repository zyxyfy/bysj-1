<%--
  Created by IntelliJ IDEA.
  User: 张战
  Date: 2016/3/19
  Time: 19:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/jsps/includeURL.jsp"%>
<script type="text/javascript">
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
</script>
<form action="${actionURL}" method="post">
	<%--在这个项目中，一般都需要将模态框放入到form表单中，因为大部分模态框提交后需要请求后台进行处理--%>
	<%--这是模态框的头部--%>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">
			<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
		</button>
		<h4 class="modal-title">设置答辩时间地点</h4>
	</div>
	<%--这是模态框显示内容的主体部分--%>
	<div class="modal-body">
		<input type="hidden" value="${replyGroupId}" name="replyGroupId">

		<p>
			<span>答辩地点：</span> <label> <select class="form-control"
				name="classRoomId" id="classRoomId">
					<option label="请选择" value="0">--请选择--</option>
					<c:forEach items="${classList}" var="studentClass">
						<option value="${studentClass.id}"
							<c:if
                                        test="${studentClass==replyGroup.classRoom}">selected="selected" </c:if>>${studentClass.description}</option>
					</c:forEach>
			</select>
			</label>
		</p>
		<%--  <p>
            <span>开始时间：</span>
            <label>
                <input name="startDate" type="text" class="form-control" placeholder="格式：yyyy/MM/dd"
                       value="<fmt:formatDate value="${replyGroup.replyTime.beginTime.time}" pattern="yyyy/MM/dd"/>">
            </label>
        </p>

        <p>
            <span>结束时间：</span>
            <label>
                <input name="endDate" type="text" class="form-control" placeholder="格式：yyyy/MM/dd"
                       value="<fmt:formatDate value="${replyGroup.replyTime.endTime.time}" pattern="yyyy/MM/dd"/>">
            </label>
        </p>
         --%>
		<div class="form-group">
			<label for="dtp_input1" class="col-md-2 control-label">开始时间：</label>

			<div class="input-group date form_date col-md-6" data-date=""
				data-date-format="dd MM yyyy " data-link-field="dtp_input1">
				<fmt:formatDate value="${replyGroup.replyTime.beginTime.time}" pattern="yyyy/MM/dd" var="startTime"/>
				<input class="form-control" size="16" type="text"
					   value="${startTime}" name="startTime">
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
				data-date-format="dd MM yyyy" data-link-field="dtp_input2">
				<!-- data-link-format="yyyy-mm-dd" -->
				<fmt:formatDate value="${replyGroup.replyTime.endTime.time}" pattern="yyyy/MM/dd" var="endTime"/>
				<input class="form-control" size="16" type="text"
					   value="${endTime}" name="endTime">
				<!-- readonly -->
				<span class="input-group-addon"><span
					class="glyphicon glyphicon-remove"></span></span> <span
					class="input-group-addon"><span
					class="glyphicon glyphicon-calendar"></span></span>
			</div>
			<input type="hidden" id="dtp_input2" value="" /><br />
		</div>
	</div>
	<%--这是模态框的尾部--%>
	<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		<button type="submit" class="btn btn-primary">保存</button>
	</div>
</form>
