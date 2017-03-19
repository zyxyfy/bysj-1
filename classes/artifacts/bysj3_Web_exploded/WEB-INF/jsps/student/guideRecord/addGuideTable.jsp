<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/jsps/includeURL.jsp"%>
<script>
	function deleteGuideRecord(guideRecordId){
		//var confirmDelete=window.confirm("确认删除？");
		window.wxc.xcConfirm("确认修改？","confirm",{
			onOk: function(){
				$.ajax({
					url:'deleteGuideRecord.html',
					type:'GET',
					dataType:'json',
					data:{"guideRecordId":guideRecordId},
					success:function(data){
						$("#guideRecordRow"+guideRecordId).remove()
						myAlert("删除成功");
						return true;
					},
					error:function(){
						myAlert("删除失败,请稍后再试");
						return false;
					}

				});
			}});
	/*	if(confirmDelete){
			$.ajax({
				url:'deleteGuideRecord.html',
				type:'GET',
				dataType:'json',
				data:{"guideRecordId":guideRecordId},
				success:function(data){
					$("#guideRecordRow"+guideRecordId).remove()
					myAlert("删除成功");
					return true;
				},
				error:function(){
					myAlert("网络错误，删除失败");
					return false;
				}
				
			});
		}*/
	}
</script>
<div class="container-fluid" style="width: 100%">
	<div class="row-fluid">
		<ul class="breadcrumb">
			<li>毕业设计流程</li>
			<li>填写指导记录表<span class="divider">/</span>
			</li>
			<li>添加指导记录表<span class="divider">/</span>
			</li>
		</ul>
	</div>
	<div>
		<c:choose>
			<c:when test="${ifHaveOperation==0}">
				<center>
					<h1>
						<font color="red" id="guideRecordMessage"><span>${message }</span></font>
					</h1>
				</center>
			</c:when>
			<c:otherwise>
				<c:url value="/student/addGuideRecord.html" var="actionUrl"/>
				<a class="btn btn-primary btn-xs" href="${actionUrl}"
					type="button" data-toggle="modal" data-target="#addGuideRecord">手工添加一条
				</a>
				<center>
					<font color="red"><span>${message}</span></font>
				</center>
			</c:otherwise>
		</c:choose>

	</div>
	<br>
	<div class="row">
		<table
			class="table table-striped table-bordered table-hover datatable">
			<thead>
				<tr>
					<th>提交状态</th>
					<th>时间</th>
					<th>地点</th>
					<th>指导内容</th>
					<th>学生签字</th>
					<th>指导老师签字</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${guideRecords}" var="guideRecord">
					<tr id="guideRecordRow${guideRecord.id}">
						<td><c:choose>
								<c:when
									test="${guideRecord.submittedByStudent==true&&guideRecord.auditedByTutor.approve==null}">已送审</c:when>
								<c:when test="${guideRecord.auditedByTutor.approve=true}">通过</c:when>
								<c:otherwise>
									<a class=""
										href="studentSubmitForGuideRecord.html?guideRecordId=${guideRecord.id}">送审</a>
								</c:otherwise>
							</c:choose></td>
						<td><fmt:formatDate value="${guideRecord.time.time}"
								type="date" dateStyle="long" /></td>
						<td>${guideRecord.classRoom.description}</td>
						<td>${guideRecord.description}</td>
						<td><c:choose>
								<c:when test="${guideRecord.submittedByStudent==true}">${guideRecord.graduateProject.student.name}</c:when>
								<c:otherwise>&nbsp;</c:otherwise>
							</c:choose></td>
						<td><c:choose>
								<c:when test="${guideRecord.auditedByTutor.approve==true}">${guideRecord.graduateProject.mainTutorage.tutor.name}</c:when>
								<c:when test="${guideRecord.auditedByTutor.approve==false}">退回</c:when>
								<c:otherwise>&nbsp;</c:otherwise>
							</c:choose></td>
						<td><%-- <a class="btn btn-danger btn-xs" href="deleteGuideRecord.html?guideRecordId=${guideRecord.id}">
								<i class="icon-remove "></i> 删除
						</a>  --%>
						<a class="btn btn-danger btn-xs" onclick="deleteGuideRecord(${guideRecord.id})">删除</a>
						
						
						
						<a href="<%=basePath%>student/editGuideRecord.html?guideRecordId=${guideRecord.id}"
							class="btn btn-warning btn-xs"  data-toggle="modal"
							data-target="#modify"> <i class="icon-edit"></i> 修改
						</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</div>
	<div class="modal fade" id="addGuideRecord" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
	</div>
	<div class="modal fade" id="modify" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
	</div>
</div>