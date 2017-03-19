<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsps/includeURL.jsp" %>
<script type="text/javascript">
    var studentIds = "";
    /*var studentCount = $("input[name='studentCheckBox']").length;
     $("#studentCount").text(studentCount+"个");*/

    function allocateStudent(tutorId) {
        var student = $("input[name='studentCheckBox']:checked").length;
        if (student == 0 || student == null) {
            myAlert("请选择学生！");
        } else {
            window.wxc.xcConfirm("确认匹配选中的老师和学生？", "confirm", {
                onOk: function () {
                    $.ajax({
                        url: '/bysj3/process/allocateStudents.html',
                        type: 'POST',
                        dataType: 'json',
                        data: {"stuIds": studentIds, "tutorId": tutorId},
                        success: function (data) {
                            var noSelect = $("input[name='studentCheckBox']").length - $("input[name='studentCheckBox']:checked").length;
                            $("#studentSelect").html("学生，共" + noSelect + "个");
                            //删除所在行的父标签
                            $("input[name='studentCheckBox']:checked").parent().parent().parent().remove();

                            $("#studentCount").html("，已选择0个");
                            myAlert("匹配成功！");
                            return true;
                        },
                        error: function () {
                            myAlert("匹配失败,请稍后再试");
                            return false;
                        }
                    });
                }
            });


        }
    }
    /*匹配学生*/
    function selectStudent(studentId) {
        var selectStudent = $("#selectStudentRow" + studentId);
        var selectedStudent = selectStudent.find("input[name='studentCheckBox']");
        if (selectedStudent.prop("checked")) {//判断是否被选中
            selectedStudent.prop("checked", true);//设置复选框被选中
            //console.log("设置复选框选中");
        } else {
            selectedStudent.prop("checked", false);//取消复选框的选中状态
            //console.log("设置复选框取消选中");
        }
        //获取当前选中的学生数
        var studentCounts = $("input[name='studentCheckBox']:checked").length;
        if (studentCounts != null && studentCounts != 0) {
            $("#studentCount").html("，已选择" + studentCounts + "个");
        } else {
            $("#studentCount").html("，已选择0个");
        }
        studentIds = studentIds + "," + studentId;
    }


    /*  动态获取heading的宽度*/

    function getContentSize() {
        var wh = document.getElementById("tutorPanel").clientWidth
        /*console.log(wh) 控制台打印高度 */
        wh = wh + "px";
        document.getElementById("ph1").style.width = wh;
        document.getElementById("ph2").style.width = wh;
        /* document.getElementById( "tutorPanel" ).style.height = ph; */
        /*  document.getElementById( "studentPanel" ).style.height = ph;  */

    }
    window.onload = getContentSize;
    window.onresize = getContentSize;

</script>
<style type="text/css">
    #ph1 {
        position: fixed;
        width: 100px;
        height: 40px;
    }

    #ph2 {
        position: fixed;
        width: 100px;
        height: 40px;
    }

    #tutorPanel {
        height: 400px;
        overflow-y: auto;
        overflow-x: hidden;
    }

    #studentPanel {
        height: 400px;
        overflow-y: auto;
        overflow-x: hidden;
    }
</style>

<div class="container-fluid" style="width: 100%">
    <div class="row-fluid">
        <ul class="breadcrumb">
            <li>选题流程</li>
            <li>教研室主任分配学生<span class="divider">/</span>
            </li>
        </ul>
    </div>
    <div class="row">
        <div class="col-md-4">
            <a class="btn btn-primary btn-sm" href="<%=basePath%>process/allocateStudents.html?hasTutor=${true}"
               data-toggle="modal" data-target="#allocatedStudents"> 已分配学生
            </a>
            <a class="btn btn-primary btn-sm" href="<%=basePath%>process/allocateStudents.html">未分配学生</a>
        </div>
    </div>
    <br>

    <form class="form-inline" role="form" action="${actionUrl}">
        <div class="form-group">
            <label for="name">学生姓名:</label><input type="text" value="${name}" name="name"
                                                  class="form-control" id="name" placeholder="请输入学生姓名">

            <label for="name">学生学号:</label><input type="text" value="${no}" name="no"
                                                  class="form-control" id="no" placeholder="请输入学号">
            <button type="submit" class="btn btn-primary  btn-sm"><i class="icon-search"></i> 查询</button>
        </div>
    </form>

    <br>

    <div class="row">
        <!-- 教师信息面板 -->
        <div class="col-md-6">
            <div class="panel panel-default" id="tutorPanel">
                <!-- Default panel contents -->
                <div class="panel-heading" id="ph1">教研室老师,共${tutorCount}个</div>
                <!-- Table -->
                <table class="table " style="margin-top: 40px;">
                    <thead>
                    <tr>
                        <th>教师姓名</th>
                        <th>职工号</th>
                        <th>选择</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:choose>
                        <c:when test="${tutorList != null}">
                            <c:forEach items="${tutorList}" var="tutor">
                                <tr>
                                    <td>${tutor.name}</td>
                                    <td>${tutor.no}</td>
                                    <td><a class="btn btn-warning btn-xs"
                                           onclick="allocateStudent(${tutor.id})">匹配</a>
                                        <a data-toggle="modal"
                                           data-target="#viewTutorStudent"
                                           class="btn btn-info btn-xs"
                                           href="getTutorOfStudent.html?tutorId=${tutor.id}">显示</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <%--<c:otherwise>
                            <span class="label label-default">没有可以显示的教师</span>
                        </c:otherwise>--%>
                    </c:choose>
                    </tbody>
                </table>
                <c:choose>
                    <c:when test="${tutorList = null}">
                        <div class="alert alert-warning alert-dismissable" role="alert">
                            <button class="close" type="button" data-dismiss="alert">&times;</button>
                            没有可以显示的教师
                        </div>
                    </c:when>
                </c:choose>
            </div>
        </div>
        <!--  未分配学生面板-->
        <div class="col-md-6">
            <div class="panel panel-default" id="studentPanel">
                <!-- Default panel contents -->
                <div class="panel-heading" id="ph2">
                    <span id="studentSelect">学生，共${studentCount}个</span><span
                        id="studentCount">，已选择0个</span>
                </div>

                <!-- Table  为了使table的表头不被固定的面板heading所覆盖，为table设置上边距margin-top-->

                <table class="table" style="margin-top: 40px;">
                    <thead>
                    <tr>
                        <th>选择</th>
                        <th>班级</th>
                        <th>姓名</th>
                        <th>学号</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:choose>
                        <c:when test="${studentList!=null}">
                            <c:forEach items="${studentList}" var="student">
                                <tr id="selectStudentRow${student.id}">
                                    <td><p class="text-center">
                                        <input onclick="selectStudent(${student.id})"
                                               type="checkbox" value="${student.id}"
                                               name="studentCheckBox">
                                    </p></td>
                                    <td onclick="selectStudent(${student.id})">${student.studentClass.description}</td>
                                    <td onclick="selectStudent(${student.id})">${student.name}</td>
                                    <td onclick="selectStudent(${student.id})">${student.no}</td>
                                </tr>
                            </c:forEach>
                        </c:when>
                    </c:choose>
                    </tbody>
                </table>
            </div>
        </div>

    </div>
</div>

<div class="modal fade" id="allocatedStudent" tabindex="-1"
     role="dialog" aria-hidden="true"
     aria-labelledby="modelOpeningReportTime">
    <div class="modal-dialog">
        <div class="modal-content"></div>
    </div>
</div>


<div class="modal fade" id="viewTutorStudent" tabindex="-1"
     role="dialog" aria-hidden="true"
     aria-labelledby="modelOpeningReportTime">
    <div class="modal-dialog">
        <div class="modal-content"></div>
    </div>
</div>

<div class="modal fade" id="allocatedStudents" tabindex="-1"
     role="dialog" aria-hidden="true"
     aria-labelledby="modelOpeningReportTime">
    <div class="modal-dialog">
        <div class="modal-content"></div>
    </div>
</div>