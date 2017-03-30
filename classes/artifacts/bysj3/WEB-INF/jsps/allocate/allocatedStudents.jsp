<%--
  Created by IntelliJ IDEA.
  User: 张战
  Date: 2016/4/14
  Time: 17:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/jsps/includeURL.jsp"%>


<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-hidden="true">×</button>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-4">
				<h4 class="modal-title" id="myModalLabel">
					已分配学生，共${studentCount}个</h4>
			</div>
			<div class="col-md-5">
				<p style="color: red">(已分配课题的学生不能删除！)</p>
			</div>
		</div>
	</div>


</div>
<script type="text/javascript">

    function cancelStudent() {
        var studentId = null;
        var i = 0;
        $("input[type='checkbox']:checked").each(function () {
            if (i == 0) {
                studentId = $(this).val() + ",";
            } else {
                studentId += $(this).val();
                studentId += ",";
            }
            i++;
        });
     
        //去掉最后一个逗号
        studentId = studentId.substring(0, studentId.length - 1);
        window.wxc.xcConfirm("确认删除已分配的学生？","confirm",{
 			onOk: function(){
        $.ajax({
            url: '/bysj3/process/delStudents.html',
            data: {"stuIds": studentId},
            dataType: 'json',
            type: 'post',
            success: function () {
                myAlert("取消成功");
                window.location = '/bysj3/process/allocateStudents.html';
                return true;
            },
            error: function () {
				myAlert("删除失败，请稍后再试");
                return false;
            }
        });
 			}});
    }

    function showStudentList(tutorId) {
        $("#showStudent" + tutorId).hide(300);
        $("#hideStudent" + tutorId).show(600);
        $("#tutorStudent" + tutorId).show(900);
    }

    function hideStudentList(tutorId) {
        $("#tutorStudent" + tutorId).hide(300);
        $("#hideStudent" + tutorId).hide(600);
        $("#showStudent" + tutorId).show(900);
    }

    /*function selectStudent(studentId) {
     var selectCheckbox = $("#studentCheckbox" + studentId);
     alert(selectCheckbox.prop("checked"));
     if (selectCheckbox.prop("checked")) {
     selectCheckbox.attr("checked", false);
     } else {
     selectCheckbox.attr("checked", true);
     }
     }*/
</script>
<div class="modal-body">
	<span style="color: #808080">请选择需要删除的学生</span> <br>
	<c:choose>
		<c:when test="${studentCount>0}">
			<c:forEach items="${tutorList}" var="tutor">
				<c:if test="${not empty tutor.student}">
					<div class="container-fluid">
						<div class="row">
							<div class="col-md-3">
								<c:set var="tutorStudent" value="0" />
								<c:forEach items="${tutor.student}" var="student">
									<c:set var="tutorStudent" value="${tutorStudent+1}" />
								</c:forEach>
								${tutor.name}(${tutorStudent}个)
							</div>
							<div class="col-md-3">
								<button class="btn btn-default" id="showStudent${tutor.id}"
									onclick="showStudentList(${tutor.id})">显示</button>
								<button class="btn btn-warning" id="hideStudent${tutor.id}"
									onclick="hideStudentList(${tutor.id})" style="display: none">隐藏
								</button>
							</div>
						</div>
					</div>
					<div class="tutorStudentList" id="tutorStudent${tutor.id}"
						style="display: none">
						<c:forEach items="${tutor.student}" var="student">
							<input type="checkbox" value="${student.id}"
								   id="studentCheckbox${student.id}"><i class="icon icon-remove-circle"></i>
							<span>${student.name} <c:choose>
									<c:when test="${empty student.graduateProject}">
                                    (课题：无)&nbsp;&nbsp;&nbsp;
                                </c:when>
									<c:otherwise>
                                    (课题：有)&nbsp;&nbsp;&nbsp;
                                </c:otherwise>
								</c:choose></span>
						</c:forEach>
					</div>
					<br>
				</c:if>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<div class="alert alert-warning alert-dismissable" role="alert">
				<button class="close" type="button" data-dismiss="alert">&times;</button>
				没有已分配的学生
			</div>
		</c:otherwise>
	</c:choose>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-default" data-dismiss="modal">关闭
	</button>
	<button type="button" onclick="cancelStudent()" class="btn btn-primary">
		提交更改</button>
</div>
