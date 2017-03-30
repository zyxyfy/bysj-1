<%--
  Created by IntelliJ IDEA.
  User: zhan
  Date: 2016/3/15
  Time: 16:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>


<script type="text/javascript">
    /*显示当前老师的学生*/
    function getStudents(tutorId) {
        $("input[name='tutorIds']").each(function () {
            $("#replyStudents" + tutorId).hide();
            if ($("#tutorInput" + tutorId).prop("checked") == true) {
                $("#replyStudents" + tutorId).show();
            } else {
                $(".replyStudents" + tutorId).each(function () {
                    $(this).attr("checked", false);
                    $("input[name='replyStudentIds']").change();
                });
            }
        });
    }


    function confirmSubmit() {
        window.wxc.xcConfirm("确认提交？", "confirm", {
            onOk: function () {
                return true;
            }
        });
    }

    function submitStudent() {
        if ($("#description").prop("value") == null) {
            myAlert("请输入小组名称");
            return false;
        } else if ($("select[name='leadIds'] option select").attr("value") == 0) {
            myAlert("请选择小组组长");
            return false;
        }

        /*if ($("input[name='cancelStuIds']:checked").length != 0) {

         return true;
         } */

        else if ($("input[name='tutorIds']:checked").length == 0) {
            myAlert("请选择老师");
            return false;
        } else if ($("input[name='studentIds']:checked").length == 0 && $("input[name='cancelStuIds']:checked").length == 0) {
            myAlert("请选择学生");
            return false;
        }
        window.wxc.xcConfirm("确认提交？", "confirm", {
            onOk: function () {
                $("#formid").submit();
            }
        })
    }
</script>
<script type="text/javascript">
    $(document).ready(function () {
        getStudents();
        /*$("#sub11mitButton").click(function () {
            if ($("#description").prop("value") == null) {
                myAlert("请输入小组名称");
                return false;
            } else if ($("select[name='leadIds'] option select").attr("value") == 0) {
                myAlert("请选择小组组长");
                return false;
         } else if ($("input[name='cancelStuIds']:checked").length != 0) {
         return true;
            } else if ($("input[name='tutorIds']:checked").length == 0) {
                myAlert("请选择老师");
                return false;
            } else if ($("input[name='studentIds']:checked").length == 0) {
                myAlert("请选择学生");
                return false;
            }
            return true;
         });*/


        /*当复选框发生变化时，显示当前已经选择的学生的个数*/
        $("input[name='studentIds']").change(function () {
            var selectStudentCount = $("input[name='studentIds']:checked").length;
            $("#selectStudent").html("(已选择" + selectStudentCount + "个)")
        });


        /*点击老师下的全选*/
        $("input[name='tutorCheckbox']").change(function () {
            if ($(this).prop("checked")) {
                $(this).parent().find($("input[name='studentIds']")).prop("checked", true);
            } else {
                $(this).parent().find($("input[name='studentIds']")).prop("checked", false);
            }
            /*用于重新计算当前已选择的学生的个数*/
            $("input[name='studentIds']").change();
        });
    });


</script>

<form action="${actionURL}" method="post" id="formid">

    <input type="hidden" name="replyGroupId" value="${replyGroup.id}">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"
                aria-hidden="true">×
        </button>
        <h4 class="modal-title" id="myModalLabel">
            修改答辩小组
        </h4>
    </div>
    <div class="modal-body">
        <input type="hidden" name="replyGroupId" value="${replyGroup.id}">
        <dl>
            <dt>答辩小组名称：</dt>
            <dd>
                <input value="${replyGroup.description}" name="replyGroupName" id="description" class="form-control"
                       required/>
            </dd>
            <dt>小组组长：</dt>
            <dd>
                <%--<form:select path="leader.name" items="${tutors}"/>--%>
                <select id="leadIds" required="required" name="leaderId" class="form-control required">
                    <option value="0">请选择组长</option>
                    <c:forEach items="${tutors}" var="tutor">
                        <option value="${tutor.id}" label="${tutor.name}"
                                <c:if test="${replyGroup.leader==tutor}">selected="selected" </c:if>></option>
                    </c:forEach>
                </select>
            </dd>
            <dt>答辩老师：</dt>
            <dd>
                <c:forEach items="${tutors}" var="tutor">
                    <%--列出可供选择的学生的个数--%>
                    <c:set var="studentsCount" value="0"/>
                    <%--对老师的所有学生进行遍历--%>
                    <c:forEach items="${tutor.student}" var="student">
                        <%--给学生分配答辩小组的条件：学生的课题不为空，之前没有选择答辩小组，评阅人和指导老师都允许答辩或是学生所在的答辩小组为当前的答辩小组 --%>
                        <c:if test="${student.graduateProject!=null&&student.graduateProject.replyGroup==null&&student.graduateProject.commentByReviewer.qualifiedByReviewer&&student.graduateProject.commentByTutor.qualifiedByTutor}">
                            <c:set var="studentsCount" value="${studentsCount+1}"/>
                        </c:if>
                    </c:forEach>
                    <%--如果没有可以选择的学生，则不能选中--%>
                    <label style="width: auto">
                        <input type="checkbox" id="tutorInput${tutor.id}" value="${tutor.id}"
                               studentsCount="${studentsCount}" name="tutorIds"
                               onclick="getStudents(${tutor.id})"
                        <c:forEach items="${replyGroup.members}" var="members">
                               <c:if test="${members.id==tutor.id}">checked </c:if>
                        </c:forEach>  >${tutor.name}(<span id="checkBoxByGroup${tutor.id}">${studentsCount}</span>)
                    </label>
                </c:forEach>
            </dd>
            <%--用于列出所选老师下的学生--%>
            <dt>
                答辩学生：<span style="color: grey">(选中提交可删除对应学生)</span><c:choose>
                <c:when test="${students !=null}">
                    <c:forEach items="${students}" var="student">
                        <input type="checkbox" name="cancelStuIds" value="${student.id}">
                        ${student.name}&nbsp;&nbsp;
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <span class="label label-default" id="selectStudent">请先选择答辩老师</span>
                </c:otherwise>
            </c:choose>
            </dt>
            <dd>
                <c:forEach items="${tutors}" var="tutor">
                    <c:if test="${fn:length(tutor.student) != 0}">
                        <div id="replyStudents${tutor.id}" style="display: none">
                                ${tutor.name}老师所带的学生:<input type="checkbox" id="tutorCheckBox${tutor.id}"
                                                            name="tutorCheckbox">全选&nbsp;&nbsp;
                            <c:forEach items="${tutor.student}" var="student">
                                <c:if test="${student.graduateProject!=null&&student.graduateProject.replyGroup==null&&student.graduateProject.commentByReviewer.qualifiedByReviewer&&student.graduateProject.commentByTutor.qualifiedByTutor}">
                                    <input type="checkbox" id="replyStudents${student.id}"
                                           name="studentIds"
                                           value="${student.id}">${student.name}&nbsp;
                                </c:if>
                            </c:forEach>
                        </div>
                    </c:if>
                </c:forEach>
            </dd>
        </dl>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default"
                data-dismiss="modal">关闭
        </button>
        <button type="button" id="btn" onclick="submitStudent()" class="btn btn-primary">
            提交更改
        </button>
    </div>
</form>


