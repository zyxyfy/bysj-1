
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/includeURL.jsp"%>
<script type="text/javascript">
	function approveTaskDocByDirectorAndDean(taskDocId){
		window.wxc.xcConfirm("确认通过？","confirm",{
			onOk: function(){
				$.ajax({
					url:'approveTaskDocByDirectorAndDean.html',
					type:'GET',
					dateType:'json',
					data:{"taskDocId":taskDocId},
					success:function(data){
						$("#departmentOfDepartmentAndDean"+taskDocId).html("<p>通过</p>");			
						$("#deanOfDepartmentAndDean"+taskDocId).html("<p>通过</p>");
						$("#directorAndDean"+taskDocId).html("<a id='rejectOfDirectorAndDean${taskDoc.id}' class='btn btn-danger btn-xs' onclick='rejectTaskDocByDirectorAndDean("+taskDocId+")'>退回</a>");
						myAlert("修改成功");
						return true;
					},
					error:function(){
						myAlert("修改失败,请稍后再试");
						return false;
					}
				});
			}});
		
		// var confirmApprove = window.confirm("确认通过？");
		/* if (confirmApprove) {
			$.ajax({
				url:'approveTaskDocByDirectorAndDean.html',
				type:'GET',
				dateType:'json',
				data:{"taskDocId":taskDocId},
				success:function(data){
					$("#departmentOfDepartmentAndDean"+taskDocId).html("<p>通过</p>");			
					$("#deanOfDepartmentAndDean"+taskDocId).html("<p>通过</p>");
					$("#directorAndDean"+taskDocId).html("<a id='rejectOfDirectorAndDean${taskDoc.id}' class='btn btn-danger btn-xs' onclick='rejectTaskDocByDirectorAndDean("+taskDocId+")'>退回</a>");
					myAlert("修改成功");
					return true;
				},
				error:function(){
					myAlert("网络错误，修改失败");
					return false;
				}
			});
		} */
	}
	function rejectTaskDocByDirectorAndDean(taskDocId){
		window.wxc.xcConfirm("确认退回？","confirm",{
			onOk: function(){
				$.ajax({
					url:'rejectTaskDocByDirectorAndDean.html',
					type:'GET',
					dateType:'json',
					data:{"taskDocId":taskDocId},
					success:function(data){
						$("#departmentOfDepartmentAndDean"+taskDocId).html("<p>退回</p>");			
						$("#deanOfDepartmentAndDean"+taskDocId).html("<p>退回</p>");		
						$("#directorAndDean"+taskDocId).html("<a id='approveOfDirectAndDean${taskDoc.id}' class='btn btn-danger btn-xs' onclick='approveTaskDocByDirectorAndDean("+taskDocId+")'>通过</a>");
						myAlert("修改成功");
						return true;
					},
					error:function(){
						myAlert("修改失败,请稍后再试");
						return false;
					}
				});
			}});
		/* var confirmReject=window.confirm("确认退回？");
		if(confirmReject){
			$.ajax({
				url:'rejectTaskDocByDirectorAndDean.html',
				type:'GET',
				dateType:'json',
				data:{"taskDocId":taskDocId},
				success:function(data){
					$("#departmentOfDepartmentAndDean"+taskDocId).html("<p>退回</p>");			
					$("#deanOfDepartmentAndDean"+taskDocId).html("<p>退回</p>");		
					$("#directorAndDean"+taskDocId).html("<a id='approveOfDirectAndDean${taskDoc.id}' class='btn btn-danger btn-xs' onclick='approveTaskDocByDirectorAndDean("+taskDocId+")'>通过</a>");
					myAlert("修改成功");
					return true;
				},
				error:function(){
					myAlert("网络错误，修改失败");
					return false;
				}
			});
		} */
	}
	function approveTaskDocByDean(taskDocId){
		window.wxc.xcConfirm("确认通过？","confirm",{
			onOk: function(){
				$.ajax({
					url:'approveTaskDocByDean.html',
					type:'GET',
					dateType:'json',
					data:{"taskDocId":taskDocId},
					success:function(data){
						$("#statusOfDean"+taskDocId).html("<p>通过</p>");
						$("#dean"+taskDocId).html("<a id='rejectOfDean${taskDoc.id}' class='btn btn-danger btn-xs' onclick='rejectTaskDocByDean("+taskDocId+")'>退回</a>")
						myAlert("修改成功");
						return true;
					},
					error:function(){
						myAlert("修改失败,请稍后再试");
						return false;
					}
				});
			}});
		//var confirmApprove=window.confirm("确认通过？")
		/* if(confirmApprove){
			$.ajax({
				url:'approveTaskDocByDean.html',
				type:'GET',
				dateType:'json',
				data:{"taskDocId":taskDocId},
				success:function(data){
					$("#statusOfDean"+taskDocId).html("<p>通过</p>");
					$("#dean"+taskDocId).html("<a id='rejectOfDean${taskDoc.id}' class='btn btn-danger btn-xs' onclick='rejectTaskDocByDean("+taskDocId+")'>退回</a>")
					myAlert("修改成功");
					return true;
				},
				error:function(){
					myAlert("网络错误，修改失败");
					return false;
				}
			});
		} */
	}
	function rejectTaskDocByDean(taskDocId){
		window.wxc.xcConfirm("确认退回？","confirm",{
			onOk: function(){
				$.ajax({
					url:'rejectTaskDocByDean.html',
					type:'GET',
					dateType:'json',
					data:{"taskDocId":taskDocId},
					success:function(data){
						$("#statusOfDean"+taskDocId).html("<p>退回</p>");
						$("#dean"+taskDocId).html("<a id='approveOfDean${taskDoc.id}' class='btn btn-danger btn-xs' onclick='approveTaskDocByDean("+taskDocId+")'>通过</a>")
						myAlert("修改成功");
						return true;
					},
					error:function(){
						myAlert("修改失败,请稍后再试");
						return false;
					}
					
				});
			}});
		//var confirmReject=window.confirm("确认退回？");
		/* if(confirmReject){
			$.ajax({
				url:'rejectTaskDocByDean.html',
				type:'GET',
				dateType:'json',
				data:{"taskDocId":taskDocId},
				success:function(data){
					$("#statusOfDean"+taskDocId).html("<p>退回</p>");
					$("#dean"+taskDocId).html("<a id='approveOfDean${taskDoc.id}' class='btn btn-danger btn-xs' onclick='approveTaskDocByDean("+taskDocId+")'>通过</a>")
					myAlert("修改成功");
					return true;
				},
				error:function(){
					myAlert("网络错误，修改失败");
					return false;
				}
				
			});
		} */
	}
	function approveTaskDocByDepartment(taskDocId){
		window.wxc.xcConfirm("确认通过？","confirm",{
			onOk: function(){
				$.ajax({
					url:'approveTaskDocByDepartment.html',
					type:'GET',
					dateType:'json',
					data:{"taskDocId":taskDocId},
					success:function(data){
						$("#statusOfDepartment"+taskDocId).html("<p>通过</p>");
						$("#director"+taskDocId).html("<a id='rejectOfDirector${taskDoc.id}' class='btn btn-danger btn-xs' onclick='rejectTaskDocByDepartment("+taskDocId+")'>退回</a>")
						myAlert("修改成功");
						return true;
					},
					error:function(){
						myAlert("修改失败,请稍后再试");
						return false;
					}
				});
			}});
		//var confirmApprove=window.confirm("确认通过？");
		/* if(confirmApprove){
			$.ajax({
				url:'approveTaskDocByDepartment.html',
				type:'GET',
				dateType:'json',
				data:{"taskDocId":taskDocId},
				success:function(data){
					$("#statusOfDepartment"+taskDocId).html("<p>通过</p>");
					$("#director"+taskDocId).html("<a id='rejectOfDirector${taskDoc.id}' class='btn btn-danger btn-xs' onclick='rejectTaskDocByDepartment("+taskDocId+")'>退回</a>")
					myAlert("修改成功");
					return true;
				},
				error:function(){
					myAlert("网络错误，修改失败");
					return false;
				}
			});
		} */
	}
	function rejectTaskDocByDepartment(taskDocId){
		window.wxc.xcConfirm("确认退回？","confirm",{
			onOk: function(){
				$.ajax({
					url:'rejectTaskDocByDepartment.html',
					type:'GET',
					dateType:'json',
					data:{"taskDocId":taskDocId},
					success:function(data){
						$("#statusOfDepartment"+taskDocId).html("<p>退回</p>");
						$("#director"+taskDocId).html("<a id='approveOfDirector${taskDoc.id}' class='btn btn-danger btn-xs' onclick='approveTaskDocByDepartment("+taskDocId+")'>通过</a>")
						myAlert("修改成功");
						return true;
					},
					error:function(){
						myAlert("修改失败,请稍后再试");
						return false;
					}
				});	
			}});
		//var confirmReject=window.confirm("确认退回？");
		/* if(confirmReject){
			$.ajax({
				url:'rejectTaskDocByDepartment.html',
				type:'GET',
				dateType:'json',
				data:{"taskDocId":taskDocId},
				success:function(data){
					$("#statusOfDepartment"+taskDocId).html("<p>退回</p>");
					$("#director"+taskDocId).html("<a id='approveOfDirector${taskDoc.id}' class='btn btn-danger btn-xs' onclick='approveTaskDocByDepartment("+taskDocId+")'>通过</a>")
					myAlert("修改成功");
					return true;
				},
				error:function(){
					myAlert("网络错误，修改失败");
					return false;
				}
			});
		} */
	}
</script>
<div class="container-fluid" style="width: 100%">
	<div class="row-fluid">
		<ul class="breadcrumb">
			<li>指导流程</li>
			<li>审核任务书<span class="divider">/</span>
			</li>
		</ul>
	</div>

<div>
	<a href="${actionUrl}" class="btn btn-primary" title="审核任务书"><span>全部任务书</span></a>
	<a href="${actionUrl}?approve=true" class="btn btn-success" title="审核任务书"><span>已通过</span></a>
	<a href="${actionUrl}?approve=false" class="btn btn-warning" title="审核任务书"><span>未通过</span></a>
</div>
<br>
	<div class="row-fluid">
		<table
			class="table table-striped table-bordered table-hover datatable">
			<thead>
				<tr>
					<th>题目名称</th>
					<th>副标题</th>
					<th>学生姓名</th>
					<th>学号</th>
					<th>班级</th>
					<!-- 拥有教研室主任的角色 -->
					<sec:authorize ifAllGranted="ROLE_DEPARTMENT_DIRECTOR" ifNotGranted="ROLE_DEAN">
						<th>状态</th>
					</sec:authorize>
					<!-- 拥有院长的角色 -->
					<sec:authorize ifAllGranted="ROLE_DEAN" ifNotGranted="ROLE_DEPARTMENT_DIRECTOR">
						<th>状态</th>
					</sec:authorize>
					<!-- 同时拥有院长和教研室主任的角色 -->
					<sec:authorize ifAllGranted="ROLE_DEAN,ROLE_DEPARTMENT_DIRECTOR">
						<th>教研室主任审核状态</th>
						<th>院长审核状态</th>
					</sec:authorize>
					<th>详情</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			<c:choose>
				<c:when test="${empty taskDoces}">
						<%--<div class="alert alert-warning alert-dismissable" role="alert">
							<button class="close" type="button" data-dismiss="alert">&times;</button>
							没有可以显示的数据
						</div>--%>
					</c:when>
					<c:otherwise>
				<c:forEach items="${taskDoces}" var="taskDoc">
					<tr class="taskDocRow${taskDoc.id}">
						<td class="taskDocTitle${taskDoc.id}">${taskDoc.graduateProject.title}</td>
						<c:choose>
							<c:when test="${empty taskDoc.graduateProject.subTitle}">
								<td></td>
							</c:when>
							<c:otherwise>
								<td class="taskDocSubTitle${taskDoc.id}">${taskDoc.graduateProject.subTitle}</td>
							</c:otherwise>
						</c:choose>
						<td class="studentName${taskDoc.id}">${taskDoc.graduateProject.student.name}</td>
						<td class="studentNo${taskDoc.id }">${taskDoc.graduateProject.student.no}</td>				
						<td class="studentClass${taskDoc.id}">${taskDoc.graduateProject.student.studentClass.description}</td>
						<!-- 教研室主任的状态 -->
						<sec:authorize ifAllGranted="ROLE_DEPARTMENT_DIRECTOR" ifNotGranted="ROLE_DEAN">
						<td id="statusOfDepartment${taskDoc.id}">
						<c:if test="${taskDoc.auditByDepartmentDirector.approve==true }">通过</c:if>
						<c:if test="${taskDoc.auditByDepartmentDirector.approve==false}">退回</c:if>
						</td>
						</sec:authorize>
						<!-- 院长的状态 -->
						<sec:authorize ifAllGranted="ROLE_DEAN" ifNotGranted="ROLE_DEPARTMENT_DIRECTOR">
						<td id="statusOfDean${taskDoc.id}">
						<c:if test="${taskDoc.auditByBean.approve==true }">通过</c:if>
						<c:if test="${taskDoc.auditByBean.approve==false}">退回</c:if>
						</td>
						</sec:authorize>
						<!-- 同时拥有院长和教研室主任角色的审核状态 -->
						<sec:authorize ifAllGranted="ROLE_DEAN,ROLE_DEPARTMENT_DIRECTOR">
						<td id="departmentOfDepartmentAndDean${taskDoc.id}">
						<c:if test="${taskDoc.auditByDepartmentDirector.approve==true }">通过</c:if>
						<c:if test="${taskDoc.auditByDepartmentDirector.approve==false}">退回</c:if>
						</td>
						<td id="deanOfDepartmentAndDean${taskDoc.id}">
						<c:if test="${taskDoc.auditByBean.approve==true }">通过</c:if>
						<c:if test="${taskDoc.auditByBean.approve==false}">退回</c:if>
						</td>
						</sec:authorize>
						<td><a class="btn btn-primary btn-xs"
								href="/bysj3/process/showDetail.html?graduateProjectId=${taskDoc.graduateProject.id}"
								data-toggle="modal" data-target="#showDetail">显示细节 </a></td>
						
						<!-- 只拥有教研室主任的角色 -->
						<sec:authorize ifAllGranted="ROLE_DEPARTMENT_DIRECTOR" ifNotGranted="ROLE_DEAN">
						<td id="director${taskDoc.id}">
						<c:if test="${taskDoc.auditByDepartmentDirector.approve==false}">
						<%-- <a class="btn btn-danger btn-xs" href="approveTaskDocByDepartment.html?taskDocId=${taskDoc.id}">通过</a> --%>
						<a id="approveofDirector${taskDoc.id}" class="btn btn-danger btn-xs" onclick="approveTaskDocByDepartment${taskDoc.id}">通过</a>
						</c:if>
						<c:if test="${taskDoc.auditByDepartmentDirector.approve==true}">
						<%-- <a class="btn btn-danger btn-xs" href="rejectTaskDocByDepartment.html?taskDocId=${taskDoc.id}">退回</a> --%>
						<a id="rejectOfDirector${taskDoc.id}" class="btn btn-danger btn-xs" onclick="rejectTaskDocByDepartment${taskDoc.id}">退回</a>
						</c:if>
						</td>
						</sec:authorize>
						<!-- 只拥有院长的角色 -->
						<sec:authorize ifAllGranted="ROLE_DEAN" ifNotGranted="ROLE_DEPARTMENT_DIRECTOR">
						<td id="dean${taskDoc.id}">
						<c:if test="${taskDoc.auditByBean.approve==false}">
						<%-- <a class="btn btn-danger btn-xs" href="approveTaskDocByDean.html?taskDocId=${taskDoc.id}">通过</a> --%>
						<a id="approveOfDean${taskDoc.id}" class="btn btn-danger btn-xs" onclick="approveTaskDocByDean(${taskDoc.id})">通过</a>
						</c:if>
						<c:if test="${taskDoc.auditByBean.approve==true}">
						<%-- <a class="btn btn-danger btn-xs" href="rejectTaskDocByDean.html?taskDocId=${taskDoc.id}">退回</a> --%>
						<a id="rejectOfDean${taskDoc.id}" class="btn btn-danger" onclick="rejectTaskDocByDean(${taskDoc.id})">退回</a>
						</c:if>
						</td>
						</sec:authorize>
						<!-- 同时拥有院长和教研室主任角色 -->
						<sec:authorize ifAllGranted="ROLE_DEAN,ROLE_DEPARTMENT_DIRECTOR">
						<td id="directorAndDean${taskDoc.id}">
						<c:if test="${taskDoc.auditByBean.approve==false&&taskDoc.auditByDepartmentDirector.approve==false}">
						<a id="approveOfDirectAndDean${taskDoc.id}" class="btn btn-danger btn-xs" onclick="approveTaskDocByDirectorAndDean(${taskDoc.id})"><span>通过</span></a>
						</c:if>
						<c:if test="${taskDoc.auditByBean.approve==true&&taskDoc.auditByDepartmentDirector.approve==true}">
						<a id="rejectOfDirectorAndDean${taskDoc.id}" class="btn btn-danger btn-xs" onclick="rejectTaskDocByDirectorAndDean(${taskDoc.id})"><span>退回</span></a>
						</c:if>
						</td>
						</sec:authorize>
					</tr>
				</c:forEach>
				</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		<c:choose>
			<c:when  test="${empty taskDoces}">
				<div class="alert alert-warning alert-dismissable" role="alert">
					<button class="close" type="button" data-dismiss="alert">&times;</button>
					没有可以显示的数据
				</div>
			</c:when>
		</c:choose>
	</div>
	<div class="row">
		<%@ include file="/WEB-INF/jsps/page/pageBar.jsp"%>
	</div>
	<div class="modal fade" id="showDetail" tabindex="-1" role="dialog"
	aria-hidden="true" aria-labelledby="modelOpeningReportTime">
	<div class="modal-dialog">
		<div class="modal-content"></div>
	</div>
</div>
</div>
