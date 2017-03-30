<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsps/includeURL.jsp" %>

<script type="text/javascript">

    /*提交时的确认对话框*/
    function confirmSubmit(projectId) {
        var projectval = $("#taskDoc" + projectId).val();
        var subval = projectval.substring(projectval.indexOf(".") + 1, projectval.length);
        if (subval == "doc" || subval == "docx") {
            window.wxc.xcConfirm("确认提交？", "confirm", {
                onOk: function () {
                    $("#updateTaskDoc" + projectId).submit();
                }
            })
        } else {
            myAlert("请选择word格式的文件");
            return false;
        }
    }


    function delTaskDoc(taskDocId) {
        window.wxc.xcConfirm("确认删除？","confirm",{
            onOk: function(){
                $.ajax({
                    url: '/bysj3/tutor/deleteTaskDoc.html',
                    type: 'GET',
                    dataType: 'json',
                    data: {"taskDocId": taskDocId},
                    success: function (data) {
                        myAlert("删除成功");
                        window.location = '/bysj3/tutor/taskDocManage.html';
                        return true;
                    },
                    error: function () {
                        myAlert("删除失败,请稍后再试");
                        return false;
                    }
                });
            }});
       /* if (confirmDel) {
            $.ajax({
                url: '/bysj3/tutor/deleteTaskDoc.html',
                type: 'GET',
                dataType: 'json',
                data: {"taskDocId": taskDocId},
                success: function (data) {
                    myAlert("删除成功");
                    window.location = '/bysj3/tutor/taskDocManage.html';
                    return true;
                },
                error: function () {
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
            <li>指导流程</li>
            <li>下达任务书<span class="divider">/</span>
            </li>
        </ul>
    </div>
    <br>

    <div class="row-fluid">
        <table
                class="table table-striped table-bordered table-hover datatable">
            <thead>
            <tr>
                <th>题目名称</th>
                <th>课题类型</th>
                <th>班级</th>
                <th>学生姓名</th>
                <th>学号</th>
                <th>指导老师</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${empty graduateProjects}">
                    <div class="alert alert-warning alert-dismissable" role="alert">
                        <button class="close" type="button" data-dismiss="alert">&times;</button>
                        没有可以显示的数据
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${graduateProjects}" var="graduateProject">
                        <tr id="graduateProjectRow${graduateProject.id}">
                            <td class="graduateProjectTitle${graduateProject.id}">

                                <c:choose>
                                    <c:when test="${empty graduateProject.subTitle}">
                                        ${graduateProject.title}
                                    </c:when>
                                    <c:otherwise>
                                        ${graduateProject.title}——${graduateProject.subTitle}
                                    </c:otherwise>
                                </c:choose>

                            </td>
                            <td>${graduateProject.category}</td>
                            <td class="studentClass${graduateProject.id}">${graduateProject.student.studentClass.description}</td>
                            <td class="studentName${graduateProject.id}">${graduateProject.student.name}</td>
                            <td class="studentNo${graduateProject.id }">${graduateProject.student.no}</td>
                            <td class="proposer${graduateProject.id }">${graduateProject.proposer.name}</td>
                            <td class="status${graduateProject.id }"><c:choose>
                                <c:when test="${graduateProject.taskDoc!=null}">
                                    <c:choose>
                                        <c:when
                                                test="${graduateProject.taskDoc.auditByDepartmentDirector.approve||graduateProject.taskDoc.auditByBean.approve }">
                                            通过
                                        </c:when>
                                        <c:otherwise>
                                            驳回
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    未下达
                                </c:otherwise>
                            </c:choose></td>
                            <td><c:choose>
                                <c:when test="${graduateProject.taskDoc!=bull}">
                                    <div id="updatedTaskDoc${graduateProject.id}">
                                        <a class="btn btn-primary btn-xs"
                                           href="<%=basePath%>tutor/downLoadTaskDoc.html?taskDocId=${graduateProject.taskDoc.id }">
                                            <i class="icon-download"></i> 下载
                                        </a>
                                        <!-- href="deleteTaskDoc.html?taskDocId=${graduateProject.taskDoc.id}" -->
                                        <a class="btn btn-warning btn-xs"
                                           onclick="delTaskDoc(${graduateProject.taskDoc.id})">
                                            <i class="icon-remove-sign"></i> 删除
                                        </a>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <form action="<%=basePath%>tutor/taskDocUpLoad.html" method="post"
                                          id="updateTaskDoc${graduateProject.id}"
                                          enctype="multipart/form-data">
                                        <input type="hidden" name="graduateProjectId" value="${graduateProject.id}"/>

                                        <div class="container-fluid">
                                            <div class="row">
                                                <div class="col-md-9">
                                                    <input type="file" class="form-control"
                                                           id="taskDoc${graduateProject.id}" name="taskDocAttachment"
                                                           required/>
                                                </div>
                                                <div class="col-md-3">
                                                    <button style="margin-left: 10px;margin-top: 4px" type="button"
                                                            class="btn btn-danger btn-xs"
                                                            onclick="confirmSubmit(${graduateProject.id})">上传任务书
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </c:otherwise>
                            </c:choose></td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>
    <div class="row">
        <%@ include file="/WEB-INF/jsps/page/pageBar.jsp" %>
    </div>

</div>