<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsps/includeURL.jsp"%>
<script type="text/javascript">
  
    function delHoliday(delHolidyId) {
        //var confirmDel = window.confirm("确认删除？");
		window.wxc.xcConfirm("确认删除？","confirm",{
			onOk: function() {
				// if (confirmDel) {
				$.ajax({
					url: 'delHolidayTime.html',
					data: {"delId": delHolidyId},
					type: 'POST',
					dataType: 'json',
					success: function (data) {
						$("#holidayDel" + delHolidyId).remove();
						myAlert("删除成功");
						return true;
					},
					error: function () {
						myAlert("网络故障，请稍后再试");
						return false;
					}
				});
			}});
        //}
    }


	function advicetime() {
		myAlert("开始时间应为周一");
	}
</script>

<div class="container-fluid" style="width: 100%">
	<div class="row-fluid">
		<ul class="breadcrumb">
			<li>选题流程流程</li>
			<li>时间设置<span class="divider">/</span>
			</li>
		</ul>
	</div>
	<%--如果是教研室主任则显示--%>
	<div class="row">
		<div class="col-md-3" style=" text-align:center; ">
			<c:choose>
				<c:when test="${ifShowProposeProjectTime == 0}">
					<a href="addOrEditProposeProjectTime.html" class="btn btn-danger"
						data-toggle="modal" data-target="#addOrEditProposeProjectTime">
						<span class="glyphicon glyphicon-plus"><h5>设置教师申报题目时间</h5></span>
					</a>
				</c:when>
				<c:when test="${ifShowProposeProjectTime ==1}">
					<p>教师申报题目时间</p>
					<p>(${proposeProjectStartTime}---${proposeProjectEndTime})</p>
					<a href="addOrEditProposeProjectTime.html" class="btn btn-success"
						data-toggle="modal" data-target="#addOrEditProposeProjectTime"
						title="修改申报题目时间"> <i class="icon-edit"></i> 修改
					</a>
				</c:when>
			</c:choose>
		</div>
		<div class="col-md-3" style=" text-align:center; ">
			<c:choose>
				<c:when test="${ifShowOpeningReportTime == 0}">
					<a href="addOrEditOpeningReportTime.html" class="btn btn-danger"
						data-toggle="modal" data-target="#addOrEditOpeningReportTime">
						<span class="glyphicon glyphicon-plus"><h5>设置审核开题报告时间</h5></span>
					</a>
				</c:when>
				<c:when test="${ifShowOpeningReportTime ==1}">
					<!-- <span class="label label-success"> -->
					<p>审核开题报告时间</p>
					<p>(${openingReportStartTime}---${openingReportEndTime})</p>
					<a class="btn btn-success" data-toggle="modal"
						href="addOrEditOpeningReportTime.html"
						data-target="#addOrEditOpeningReportTime" title="修改开题报告时间"><i
						class="icon-edit"></i> 修改</a>
				</c:when>
			</c:choose>
		</div>
		<div class="col-md-3" style=" text-align:center; ">
			<c:choose>
				<c:when test="${ifShowProjectTimeSpan == 0}">
					<a onmouseup="advicetime()" href="addOrEditProjectTimeSpan.html" class="btn btn-danger" id="time"
					   data-toggle="modal" data-target="#addOrEditProjectTimeSpan"> <span
							class="glyphicon glyphicon-plus"><h5>设置毕业设计起始时间</h5></span>
					</a>

				</c:when>
				<c:when test="${ifShowProjectTimeSpan ==1}">
					<p>设置毕业设计起始时间</p>

					<p>(${projectTimeSpanStartTime}---${projectTimeSpanEndTime})</p>
					<a class="btn btn-success" data-toggle="modal"
						href="addOrEditProjectTimeSpan.html"
						data-target="#addOrEditProjectTimeSpan" title="修改教研室主任毕业设置时间"><i
						class="icon-edit"></i> 修改</a>
				</c:when>
			</c:choose>
		</div>
		<div class="col-md-3" style=" text-align:center; ">
				<a href="addOrEditHolidayTime.html" class="btn btn-info"
					data-toggle="modal" data-target="#addOrEditHolidayTime"> <span
					class="glyphicon glyphicon-plus"><h5>添加节假日</h5></span>
				</a>
		</div>
	</div>
	<br>
	<div class="row">
		<table
			class="table table-striped  table-bordered table-hover datatable">
			<!--table-striped  -->
			<thead>
				<tr>
					<th>开始时间</th>
					<th>结束时间</th>
					<th>原因</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${ifShowHolidayTime == 1}">
						<c:forEach items="${holidayList}" var="holiday">
							<tr id="holidayDel${holiday.id}">
								<td><fmt:formatDate value="${holiday.beginTime.time}"
										type="date" dateStyle="long" /></td>
								<td><fmt:formatDate value="${holiday.endTime.time}"
										type="date" dateStyle="long" /></td>
								<td>${holiday.description}</td>
								<td><a class="label label-success" data-toggle="modal"
									href="addOrEditHolidayTime.html"
									data-target="#addOrEditHolidayTime" title="修改节假日"><span
										class="glyphicon glyphicon-edit">修改</span> </a> &nbsp&nbsp
									<a
									class="label label-warning" onclick="delHoliday(${holiday.id})"
									title="删除节假日"><span class="glyphicon glyphicon-remove">删除</span>
								</a></td>
							</tr>
						</c:forEach>
					</c:when>
				</c:choose>
			</tbody>
		</table>
		<!--  判断有没有节假日的提示信息要写在table外边-->
		<c:choose>
			<c:when test="${ifShowHolidayTime == 0}">
				<div class="alert alert-warning alert-dismissable" role="alert">
					<button class="close" type="button" data-dismiss="alert">&times;</button>
					没有节假日可以在此显示
				</div>
			</c:when>
		</c:choose>
	</div>

	<%--通过class的fade属性来设置淡入淡出效果  aria-labelledby用来指定模态框的标题--%>
	<%--设置老师申报题目时间的表单--%>
	<div id="addOrEditProposeProjectTime" data-backdrop="static" data-keyboard="false" class="modal fade" tabindex="-1"
		 role="dialog" aria-hidden="true"
		 aria-labelledby="modelProposeProjectTime">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
	</div>
	<%--设置开题报告时间 --%>
	<div id="addOrEditOpeningReportTime" data-backdrop="static" data-keyboard="false" class="modal fade" tabindex="-1"
		 role="dialog" aria-hidden="true"
		 aria-labelledby="modelOpeningReportTime">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
	</div>

	<%--设置毕业设计时间--%>
	<div id="addOrEditProjectTimeSpan" data-backdrop="static" data-keyboard="false" class="modal fade" tabindex="-1"
		 role="dialog" aria-hidden="true"
		 aria-labelledby="modelProjectTimeSpan">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
	</div>

	<%--设置节假日--%>
	<div id="addOrEditHolidayTime" data-backdrop="static" data-keyboard="false" class="modal fade" tabindex="-1"
		 role="dialog" aria-hidden="true"
		 aria-labelledby="modelOpeningReportTime">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
	</div>
</div>