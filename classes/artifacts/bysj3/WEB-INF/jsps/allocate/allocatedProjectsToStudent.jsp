<%--
  Created by IntelliJ IDEA.
  User: zhan
  Date: 2016/4/5
  Time: 9:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/jsps/includeURL.jsp"%>

<div class="modal-header">
	<h4 class="modal-title" id="myModalLabel">已分配学生</h4>
</div>
<div class="modal-body">
	<script type="text/javascript">
        function cancelAllocate(graduateProjectId) {
          window.wxc.xcConfirm("确认取消？","confirm",{
    			onOk: function(){

    				$.ajax({
                        url: '/bysj3/process/cancelGraduateProject.html',
                        data: {"graduateProjectId": graduateProjectId},
                        dataType: 'json',
                        type: 'POST',
                        success: function (data) {
                            $("#allocatedStudentTr" + graduateProjectId).remove();
                            myAlert("取消成功");
                            return true;
                        },
                        error: function () {
							myAlert("请稍后再试，请稍后再试");
                            return false;
                        }
                    });
    				
    			}});
        }
    </script>
	<table class="table table-striped table-bordered table-hover datatable">
		<thead>
			<tr>
				<th>班级</th>
				<th>学号</th>
				<th>姓名</th>
				<th>课题名称</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${graduateProjectSize==null}">
					<!--   <div class="alert alert-warning alert-dismissable" role="alert">
                    <button class="close" type="button" data-dismiss="alert">&times;</button>
                    没有已分配的学生
                </div> -->
				</c:when>
				<c:otherwise>
					<c:forEach items="${graduateProjectList}" var="graduateProject">
						<tr id="allocatedStudentTr${graduateProject.id}">
							<td>${graduateProject.student.studentClass.description}</td>
							<td>${graduateProject.student.no}</td>
							<td>${graduateProject.student.name}</td>
							<td>${graduateProject.title}</td>
							<td>
								<button onclick="cancelAllocate(${graduateProject.id})"
									class="btn btn-warning">
									<i class="icon-remove"></i> 取消匹配
								</button>
							</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	<c:choose>
		<c:when test="${graduateProjectSize==null}">
			 <div class="alert alert-warning alert-dismissable" role="alert">
                    <button class="close" type="button" data-dismiss="alert">&times;</button>
                    没有已分配的学生
                </div> 
		</c:when>
	</c:choose>
</div>
<div class="modal-footer">
	<a type="submit" class="btn btn-primary"
		href="<%=basePath%>process/allocateProjectsToStudents.html"> 关闭 </a>
</div>
