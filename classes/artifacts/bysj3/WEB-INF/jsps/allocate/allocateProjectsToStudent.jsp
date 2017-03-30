<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsps/includeURL.jsp" %>
<script type="text/javascript">
    var getStudentId = "";
    //分配题目
    function allocateStudent(graduateProjectId) {
        var studentNo = $("input[name='studentRadio']").length;
        var allocatedStudentNo = parseInt($("#allocatedStudentCount").text());
        getStudentId = $("input[name='studentRadio']:checked").val();
        if (studentNo == 0) {
            myAlert("请选择学生！");
        } else {
            window.wxc.xcConfirm("确认匹配选中的课题和学生？","confirm",{
    			onOk: function(){
    				$.ajax({
                        url: '/bysj3/process/allocateProjectsToStudents.html',
                        type: 'POST',
                        dataType: 'json',
                        data: {"studentId": getStudentId, "graduateProjectId": graduateProjectId},
                        success: function (data) {
                            $("#graduateProjectTr" + graduateProjectId).remove();
                            $("#selectStudentTr" + getStudentId).remove();
                            $("#selectStudentPanel").html(studentNo - 1);//studentNo学生的数量
                            $("#selectTutorPanel").html($(".graduateProjectNo").length);
                            //console.log("graduateProjectNo.length-1等于");
                            //console.log($(".graduateProjectNo").length);
                            $("#allocatedStudentCount").html(allocatedStudentNo + 1);
                            myAlert("匹配成功");
                            return true;
                        },
                        error: function () {
                            myAlert("匹配失败，请稍后再试");
                            return false;
                        }
                    });
    				
    			}});
        }

    }

    //选择学生
    function selectStudentTd(studentId) {
        var studentTr = $("#selectStudentTr" + studentId);
        var student = studentTr.find("input[name='studentRadio']");
        if (student.prop("checked")) {
            student.prop("checked", true);
            console.log("被选中");
        } else {
            student.prop("checked", false);
            console.log("没被选中");
        }
        getStudentId = student.val();
    }


    function getContentSize() {
        var wh1 = document.getElementById("tutorPanel").clientWidth
        var wh2 = document.getElementById("studentPanel").clientWidth
        /*console.log(wh) 控制台打印高度 */
        wh1 = wh1 + "px";
        wh2 = wh2 + "px";
        document.getElementById("ph1").style.width = wh1;
        document.getElementById("ph2").style.width = wh2;
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
            <li>指导老师分配题目<span class="divider">/</span>
            </li>
        </ul>
    </div>
    <div class="row">

            <a class="btn btn-success btn-sm" id="allocatedProject" type="button"
               href="<%=basePath%>process/allocateProjectsToStudents.html?ifSelected='true'"
           data-toggle="modal" data-target="#allocatedStudent">已分配学生和课题(<span
                id="allocatedStudentCount">${allocatedStudentCount}</span>个)
        </a>
    </div>
    <br>
    <form class="form-inline" action="${actionUrl}" role="form" method="get">
                <div class="form-group">
                    <label for="name">学生姓名:</label><input type="text" name="name" value="${name}"
                                                          class="form-control" id="name" placeholder="请输入学生姓名">

                    <label for="name">学生学号:</label><input type="text" name="no" value="${no}"
                                                          class="form-control" id="no" placeholder="请输入学号">

                    <button type="submit" class="btn btn-primary btn-sm"><i class="icon-search"></i> 查询</button>
                </div>
            </form>
    <br>
    <div class="row-fluid">
        <div class="col-md-8">
            <div class="panel panel-default" id="tutorPanel">
                <!-- Default panel contents -->
                <div class="panel-heading" id="ph1">未分配课题，共<span id="selectTutorPanel">${graduateProjectSize}</span>个
                </div>
                <!-- Table -->
                <table class="table" style="margin-top:40px;">
                    <thead>
                    <tr>
                        <th>标题（及副标题）</th>
                        <th>年份</th>
                        <th>类别</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:choose>
                        <c:when test="${graduateProjectSize>0}">
                            <c:forEach items="${graduateProjectList}" var="graduateProject">
                                <tr class="graduateProjectNo" id="graduateProjectTr${graduateProject.id}">
                                    <td>
                                        <c:choose>
                                            <c:when test="${empty graduateProject.subTitle}">
                                                ${graduateProject.title}
                                            </c:when>
                                            <c:otherwise>
                                                ${graduateProject.title}——${graduateProject.subTitle}
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${graduateProject.year}</td>
                                    <td>${graduateProject.category}</td>
                                    <td><a class="btn btn-warning btn-xs" type="button"
                                           onclick="allocateStudent(${graduateProject.id})"><i class="icon-plus"></i>匹配</a>
                                        <a class="btn btn-info btn-xs" type="button"
                                           href="<%=basePath%>process/showDetail.html?graduateProjectId=${graduateProject.id}"
                                           data-toggle="modal" data-target="#viewProject"><i class="icon-coffee"></i>显示</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <td>
                                <div class="alert alert-warning alert-dismissable" role="alert">
                                    <button class="close" type="button" data-dismiss="alert">&times;</button>
                                    当前没有任何课题！
                                </div>
                            </td>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="col-md-4">
            <div class="panel panel-default" id="studentPanel">
                <!-- Default panel contents -->
                <div class="panel-heading" id="ph2">未分配学生，共<span id="selectStudentPanel">${studentSize}</span>个</div>
                <!-- Table -->
                <table class="table " style="margin-top:40px;">
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
                        <c:when test="${studentSize>0}">
                            <c:forEach items="${studentList}" var="student">
                                <tr id="selectStudentTr${student.id}">
                                    <td><p class="text-center">
                                        <input type="radio" name="studentRadio" value="${student.id}" onclick="selectStudentTd(${student.id})">
                                    </p></td>
                                    <td onclick="selectStudentTd(${student.id})">${student.studentClass.description}</td>
                                    <td onclick="selectStudentTd(${student.id})">${student.name}</td>
                                    <td onclick="selectStudentTd(${student.id})">${student.no}</td>
                                </tr>
                            </c:forEach>
                        </c:when>
                    </c:choose>

                    </tbody>
                </table>
                <c:choose>
                    <c:when test="${studentSize<=0}">
                        <div class="alert alert-warning alert-dismissable" role="alert">
                            <button class="close" type="button" data-dismiss="alert">&times;</button>
                            您当前没有收到任何消息！
                        </div>
                    </c:when>
                </c:choose>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="allocatedStudent" tabindex="-1"
     role="dialog" aria-hidden="true"
     aria-labelledby="modelOpeningReportTime">
    <div class="modal-dialog">
        <div class="modal-content">

        </div>
    </div>
</div>

<div class="modal fade" id="viewProject" tabindex="-1"
     role="dialog" aria-hidden="true"
     aria-labelledby="modelOpeningReportTime">
    <div class="modal-dialog">
        <div class="modal-content">

        </div>
    </div>
</div>