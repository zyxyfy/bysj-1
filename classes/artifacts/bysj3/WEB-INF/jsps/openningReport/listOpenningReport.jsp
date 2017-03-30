<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsps/includeURL.jsp" %>
<script type="text/javascript">
    //教研室主任和院长角色的确认对话框-通过功能
    function approveOpenningReportByDirectorAndDean(openningReportId) {
        //xcconfirm插件确认对话框
        window.wxc.xcConfirm("确认通过？", "confirm", {
            onOk: function () {
                $.ajax({
                    url: 'approveOpenningReportByDirectorAndDean.html',
                    type: 'GET',
                    dateType: 'json',
                    data: {"openningReportId": openningReportId},
                    success: function (data) {
                        $("#tutorOfTutorAndDirectior" + openningReportId).html("<p>通过</p>");
                        $("#directorOfTutorAndDirector" + openningReportId).html("<p>通过</p>");
                        $("#btn" + openningReportId).html("<a id='rejectOfTutorAndDirector${openningReport.id}' class='btn btn-danger btn-xs' onclick='rejectOpenningReportByDirectorAndDean(" + openningReportId + ")'>退回</a>");
                        addDownload(openningReportId);
                        myAlert("修改成功");
                        return true;
                    },
                    error: function () {
                        myAlert("修改失败，请稍后再试");
                        return false;
                    }
                });
            }
        });

    }

    //下载按钮取消显示
    function cancelDownload() {
        var load = $("#downloadReport");
        load.html("")
    }

    //显示下载按钮
    function addDownload(reportId) {
        var load = $("#downloadReport");
        /*<a id="downloadReport"
         href="downloadOpenningReport.html?openningReportId=
        ${openningReport.id}"
         class="btn btn-xs">*/
        /*<span><i class="icon icon-download"></i> 下载</span>*/
        load.html("<a id='downloadReport' href='downloadOpenningReport.html?openningReportId=" + reportId + "'class='btn btn-xs'><span><i class='icon icon-download'></i> 下载</span></a>");
    }

    //教研室主任和院长角色的确认对话框-退回功能
    function rejectOpenningReportByDirectorAndDean(openningReportId) {
        window.wxc.xcConfirm("确认退回？", "confirm", {
            onOk: function () {
                $.ajax({
                    url: 'rejectOpenningReportByDirectorAndDean.html',
                    type: 'GET',
                    dateType: 'json',
                    data: {"openningReportId": openningReportId},
                    success: function (data) {
                        $("#tutorOfTutorAndDirectior" + openningReportId).html("<p>退回</p>");
                        $("#directorOfTutorAndDirector" + openningReportId).html("<p>退回</p>");
                        $("#btn" + openningReportId).html("<a id='approveOfTutorAndDirector${openningReport.id}' class='btn btn-success btn-xs' onclick='approveOpenningReportByDirectorAndDean(" + openningReportId + ")'>通过</a>");
                        cancelDownload();
                        myAlert("修改成功");
                        return true;
                    },
                    error: function () {
                        myAlert("修改失败，请稍后再试");
                        return false;
                    }
                });
            }

        });

    }
    //教研室主任唯一角色登录时的确认对话框-通过功能
    function approveOpenningReportByDirector(openningReportId) {
        window.wxc.xcConfirm("确认通过？", "confirm", {
            onOk: function () {
                $.ajax({
                    url: 'approveOpenningReportByDirector.html',
                    type: 'GET',
                    dateType: 'json',
                    data: {"openningReportId": openningReportId},
                    success: function (data) {
                        $("#statusOfDirector" + openningReportId).html("<p>通过</p>");
                        $("#director" + openningReportId).html("<a id='rejectOfDirector${openningReport.id}' class='btn btn-danger btn-xs' onclick='rejectOpenningReportByDirector(" + openningReportId + ")'>退回</a>");
                        addDownload(openningReportId);
                        myAlert("修改成功");
                        return true;
                    },
                    error: function () {
                        myAlert("修改失败，请稍后再试");
                        return false;
                    }
                });
            }
        });
    }
    //教研室主任唯一角色登录时的确认对话框-退回功能
    function rejectOpenningReportByDirector(openningReportId) {
        window.wxc.xcConfirm("确认退回？", "confirm", {
            onOk: function () {
                $.ajax({
                    url: 'rejectOpenningReportByDirector.html',
                    type: 'GET',
                    dateType: 'json',
                    data: {"openningReportId": openningReportId},
                    success: function (data) {
                        $("#statusOfDirector" + openningReportId).html("<p>退回</p>");
                        $("#director" + openningReportId).html("<a id='approveOfDirector${openningReport.id}' class='btn btn-success btn-xs' onclick='approveOpenningReportByDirector(" + openningReportId + ")'>通过</a>");
                        cancelDownload();
                        myAlert("修改成功");
                        return true;
                    },
                    error: function () {
                        myAlert("修改失败，请稍后再试");
                        return false;
                    }
                });
            }
        });

    }
    //教师角色登录时的确认对话框-通过功能
    function approveOpenningReportByTutor(openningReportId) {
        window.wxc.xcConfirm("确认通过？", "confirm", {
            onOk: function () {
                $.ajax({
                    url: 'approveOpenningReportByTutor.html',
                    type: 'GET',
                    dateType: 'json',
                    data: {"openningReportId": openningReportId},
                    success: function (data) {
                        $("#statusOfTutor" + openningReportId).html("<p>通过</p>");
                        $("#tutor" + openningReportId).html("<a id='rejectOfTutor${openningReport.id}' class='btn btn-danger btn-xs' onclick='rejectOpenningReportByTutor(" + openningReportId + ")'>退回</a>");
                        addDownload(openningReportId);
                        myAlert("修改成功");
                        return true;
                    },
                    error: function () {
                        myAlert("修改失败，请稍后再试");
                        return false;
                    }
                });
            }
        });
    }
    //教师角色登录时的确认对话框-退回功能
    function rejectOpenningReportByTutor(openningReportId) {
        window.wxc.xcConfirm("确认退回？", "confirm", {
            onOk: function () {
                $.ajax({
                    url: 'rejectOpenningReportByTutor.html',
                    type: 'GET',
                    dateType: 'json',
                    data: {"openningReportId": openningReportId},
                    success: function (data) {
                        $("#statusOfTutor" + openningReportId).html("<p>退回</p>");
                        //$("#tutor" + openningReportId).html("<a id='approveOfTutor${openningReport.id}' class='btn btn-success btn-xs' onclick='approveOpenningReportByTutor(" + openningReportId + ")'>通过</a>");
                        $("#tutor" + openningReportId).html("");
                        $("#download" + openningReportId).html("");
                        //cancelDownload();
                        myAlert("修改成功");
                        return true;
                    },
                    error: function () {
                        myAlert("修改失败，请稍后再试");
                        return false;
                    }
                });
            }
        });

    }
</script>
<div class="container-fluid" style="width: 100%">
    <div class="row-fluid">
        <ul class="breadcrumb">
            <li>指导流程</li>
            <li>审核开题报告<span class="divider">/</span>
            </li>
        </ul>
    </div>

    <div>
        <a href="${actionUrl}" class="btn btn-primary" title="审核开题报告"><span>全部论文课题</span></a>
        <a href="${queryReport}?approve=true" class="btn btn-success"
           title="审核开题报告"><span>已通过</span></a> <a
            href="${queryReport}?approve=false" class="btn btn-warning"
            title="审核开题报告"><span>未通过</span></a>
    </div>
    <br>
    <div class="row-fluid">
        <table
                class="table table-striped table-bordered table-hover datatable">
            <thead>
            <tr>
                <th>操作</th>
                <!-- 拥有主指导教师角色，但不拥有教研室主任角色 -->
                <sec:authorize ifAllGranted="ROLE_TUTOR"
                               ifNotGranted="ROLE_DEPARTMENT_DIRECTOR">
                    <th>主指导审核状态</th>
                </sec:authorize>
                <!-- 拥有教研室角色，但不拥有主指导教师角色 -->
                <sec:authorize ifAllGranted="ROLE_DEPARTMENT_DIRECTOR"
                               ifNotGranted="ROLE_TUTOR">
                    <th>教研室主任审核状态</th>
                </sec:authorize>
                <!-- 同时拥有主指导教师和教研室主任角色 -->
                <sec:authorize ifAllGranted="ROLE_TUTOR,ROLE_DEPARTMENT_DIRECTOR">
                    <th>主指导审核状态</th>
                    <th>教研室主任审核状态</th>
                </sec:authorize>

                <th>主指导姓名</th>
                <th>题目名称</th>
                <th>副标题</th>
                <th>学生姓名</th>
                <th>学生学号</th>
                <th>查看开题报告</th>
                <th>详情</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${empty openningReports}">
                    <c:choose>
                        <c:when test="${empty paperProjects}">
                            <div class="alert alert-warning alert-dismissable" role="alert">
                                <button class="close" type="button" data-dismiss="alert">&times;</button>
                                没有可以显示的数据
                            </div>
                        </c:when>
                        <c:otherwise>
                            <%--openningReport--%>
                            <c:forEach items="${paperProjects}" var="paperProject">
                                <c:choose>
                                    <c:when test="${empty paperProject.openningReport}">
                                        <tr class="openningReportRow${paperProject.id}">
                                            <!-- 只拥有指导老师的角色 的操作-->
                                            <sec:authorize ifAllGranted="ROLE_TUTOR"
                                                           ifNotGranted="ROLE_DEPARTMENT_DIRECTOR">
                                                <td><label class="label label-warning">未提交</label></td>
                                            </sec:authorize>
                                            <!-- 只拥有教研室主任的角色的操作 -->
                                            <sec:authorize ifAllGranted="ROLE_DEPARTMENT_DIRECTOR"
                                                           ifNotGranted="ROLE_TUTOR">
                                                <td><label class="label label-warning">未提交</label></td>
                                            </sec:authorize>
                                            <!--同时拥有指导老师和教研室主任两个角色的操作 -->
                                            <sec:authorize
                                                    ifAllGranted="ROLE_TUTOR,ROLE_DEPARTMENT_DIRECTOR">
                                                <td><label class="label label-warning">未提交</label></td>
                                            </sec:authorize>

                                            <!-- 只拥有指导老师的角色 的状态-->
                                            <sec:authorize ifAllGranted="ROLE_TUTOR"
                                                           ifNotGranted="ROLE_DEPARTMENT_DIRECTOR">
                                                <td></td>
                                            </sec:authorize>
                                            <!-- 只拥有教研室主任的角色的状态 -->
                                            <sec:authorize ifAllGranted="ROLE_DEPARTMENT_DIRECTOR"
                                                           ifNotGranted="ROLE_TUTOR">
                                                <td></td>
                                            </sec:authorize>
                                            <!--同时拥有指导老师和教研室主任两个角色的状态 -->
                                            <sec:authorize
                                                    ifAllGranted="ROLE_TUTOR,ROLE_DEPARTMENT_DIRECTOR">
                                                <td></td>
                                                <td></td>
                                            </sec:authorize>

                                            <td class="openningReportMainTutorageName${paperProject.id}">${paperProject.mainTutorage.tutor.name}</td>
                                            <td class="openningReportTitle${paperProject.id}">${paperProject.title}</td>
                                            <td class="openningReportSubTitle${paperProject.id}">${paperProject.subTitle}</td>
                                            <td class="studentName${paperProject.id}">${paperProject.student.name}</td>
                                            <td class="studentNo${paperProject.id }">${paperProject.student.no}</td>


                                            <td>未提交</td>
                                            <td><a class="btn btn-primary btn-xs"
                                                   href="/bysj3/process/showDetail.html?graduateProjectId=${paperProject.id}"
                                                   data-toggle="modal" data-target="#showDetail">显示细节 </a></td>
                                        </tr>

                                    </c:when>
                                    <c:otherwise>
                                        <c:set value="${paperProject.openningReport}" var="openningReport"/>
                                        <tr class="openningReportRow${openningReport.id}">
                                            <!-- 只拥有指导老师的角色 的操作-->
                                            <sec:authorize ifAllGranted="ROLE_TUTOR"
                                                           ifNotGranted="ROLE_DEPARTMENT_DIRECTOR">
                                                <td id="tutor${openningReport.id}"><c:if
                                                        test="${openningReport.auditByTutor.approve==null}">
                                                    <%-- <a class="btn btn-danger btn-xs" href="approveOpenningReportByTutor.html?openningReportId=${openningReport.id}">通过</a> --%>
                                                    <a id="approveOfTutor${openningReport.id}"
                                                       class="btn btn-success btn-xs"
                                                       onclick="approveOpenningReportByTutor(${openningReport.id})">通过</a>
                                                </c:if> <c:if test="${openningReport.auditByTutor.approve==true}">
                                                    <%-- <a class="btn btn-danger btn-xs" href="rejectOpenningReportByTutor.html?openningReportId=${openningReport.id}">退回</a> --%>
                                                    <a id="rejectOfTutor${openningReport.id}"
                                                       class="btn btn-danger btn-xs"
                                                       onclick="rejectOpenningReportByTutor(${openningReport.id})">退回</a>
                                                </c:if></td>
                                            </sec:authorize>
                                            <!-- 只拥有教研室主任的角色的操作 -->
                                            <sec:authorize ifAllGranted="ROLE_DEPARTMENT_DIRECTOR"
                                                           ifNotGranted="ROLE_TUTOR">
                                                <td id="director${openningReport.id }"><c:if
                                                        test="${openningReport.auditByDepartmentDirector.approve==false}">
                                                    <%-- <a class="btn btn-danger btn-xs" href="approveOpenningReportByDirector.html?openningReportId=${openningReport.id}">通过</a> --%>
                                                    <a id="approveOfDirector${openningReport.id}"
                                                       class="btn btn-success btn-xs"
                                                       onclick="approveOpenningReportByDirector(${openningReport.id})">通过</a>
                                                </c:if> <c:if
                                                        test="${openningReport.auditByDepartmentDirector.approve==true}">
                                                    <%-- <a class="btn btn-danger btn-xs" href="rejectOpenningReportByDirector.html?openningReportId=${openningReport.id}">退回</a> --%>
                                                    <a id="rejectOfDirector${openningReport.id}"
                                                       class="btn btn-danger btn-xs"
                                                       onclick="rejectOpenningReportByDirector(${openningReport.id})">退回</a>
                                                </c:if></td>
                                            </sec:authorize>
                                            <!--同时拥有指导老师和教研室主任两个角色的操作 -->
                                            <sec:authorize
                                                    ifAllGranted="ROLE_TUTOR,ROLE_DEPARTMENT_DIRECTOR">
                                                <td id="btn${openningReport.id}"><c:if
                                                        test="${openningReport.auditByDepartmentDirector.approve==false&&openningReport.auditByTutor.approve==false}">
                                                    <%-- <a class="btn btn-danger btn-xs"  href="approveOpenningReportByDirectorAndDean.html?openningReportId=${openningReport.id}"><span>通过</span></a> --%>
                                                    <a id="approveOfTutorAndDirector${openningReport.id}"
                                                       class="btn btn-success btn-xs"
                                                       onclick="approveOpenningReportByDirectorAndDean(${openningReport.id})">通过</a>
                                                </c:if> <c:if
                                                        test="${openningReport.auditByDepartmentDirector.approve==true&&openningReport.auditByTutor.approve==true}">
                                                    <%-- <a class="btn btn-danger btn-xs"  href="rejectOpenningReportByDirectorAndDean.html?openningReportId=${openningReport.id}"><span>退回</span></a> --%>
                                                    <a id="rejectOfTutorAndDirector${openningReport.id}"
                                                       class="btn btn-danger btn-xs"
                                                       onclick="rejectOpenningReportByDirectorAndDean(${openningReport.id})">退回</a>
                                                </c:if></td>
                                            </sec:authorize>

                                            <!-- 只拥有指导老师的角色 的状态-->
                                            <sec:authorize ifAllGranted="ROLE_TUTOR"
                                                           ifNotGranted="ROLE_DEPARTMENT_DIRECTOR">
                                                <td id="statusOfTutor${openningReport.id}"><c:if
                                                        test="${openningReport.auditByTutor.approve==false}">退回</c:if>
                                                    <c:if test="${openningReport.auditByTutor.approve==true}">通过</c:if>
                                                </td>
                                            </sec:authorize>
                                            <!-- 只拥有教研室主任的角色的状态 -->
                                            <sec:authorize ifAllGranted="ROLE_DEPARTMENT_DIRECTOR"
                                                           ifNotGranted="ROLE_TUTOR">
                                                <td id="statusOfDirector${openningReport.id }"><c:if
                                                        test="${openningReport.auditByDepartmentDirector.approve==false}">退回</c:if>
                                                    <c:if
                                                            test="${openningReport.auditByDepartmentDirector.approve==true}">通过</c:if>
                                                </td>
                                            </sec:authorize>
                                            <!--同时拥有指导老师和教研室主任两个角色的状态 -->
                                            <sec:authorize
                                                    ifAllGranted="ROLE_TUTOR,ROLE_DEPARTMENT_DIRECTOR">
                                                <td id="tutorOfTutorAndDirectior${openningReport.id}"><c:if
                                                        test="${openningReport.auditByTutor.approve==false}">退回</c:if>
                                                    <c:if test="${openningReport.auditByTutor.approve==true}">通过</c:if>
                                                </td>
                                                <td id="directorOfTutorAndDirector${openningReport.id}"><c:if
                                                        test="${openningReport.auditByDepartmentDirector.approve==false}">退回</c:if>
                                                    <c:if
                                                            test="${openningReport.auditByDepartmentDirector.approve==true}">通过</c:if>
                                                </td>
                                            </sec:authorize>

                                            <td class="openningReportMainTutorageName${openningReport.id}">${openningReport.paperProject.mainTutorage.tutor.name}</td>
                                            <td class="openningReportTitle${openningReport.id}">${openningReport.paperProject.title}</td>
                                            <td class="openningReportSubTitle${openningReport.id}">${openningReport.paperProject.subTitle}</td>
                                            <td class="studentName${openningReport.id}">${openningReport.paperProject.student.name}</td>
                                            <td class="studentNo${openningReport.id }">${openningReport.paperProject.student.no}</td>


                                            <td id="download${openningReport.id}"><a id="downloadReport1"
                                                                                     href="downloadOpenningReport.html?openningReportId=${openningReport.id}"
                                                                                     class="btn btn-xs">

                                                    <%--auditByDepartmentDirector    auditByTutor   approve--%>
                                                    <%--根据不同的角色，显示不同的按钮--%>
                                                    <%--主指导角色--%>
                                                <sec:authorize ifAllGranted="ROLE_TUTOR"
                                                               ifNotGranted="ROLE_DEPARTMENT_DIRECTOR">
                                                    <c:if test="${openningReport.auditByTutor.approve}">
                                                        <span><i class="icon icon-download"></i> 下载</span>
                                                    </c:if>
                                                </sec:authorize>
                                                <!-- 拥有教研室角色，但不拥有主指导教师角色 -->
                                                <sec:authorize ifAllGranted="ROLE_DEPARTMENT_DIRECTOR"
                                                               ifNotGranted="ROLE_TUTOR">
                                                    <c:if test="${openningReport.auditByDepartmentDirector.approve}">
                                                        <span><i class="icon icon-download"></i> 下载</span>
                                                    </c:if>
                                                </sec:authorize>
                                                <!-- 同时拥有主指导教师和教研室主任角色 -->
                                                <sec:authorize ifAllGranted="ROLE_TUTOR,ROLE_DEPARTMENT_DIRECTOR">
                                                    <c:if test="${openningReport.auditByTutor.approve&&openningReport.auditByDepartmentDirector.approve}">
                                                        <span><i class="icon icon-download"></i> 下载</span>
                                                    </c:if>
                                                </sec:authorize>

                                            </a></td>
                                            <td><a class="btn btn-primary btn-xs"
                                                   href="/bysj3/process/showDetail.html?graduateProjectId=${openningReport.paperProject.id}"
                                                   data-toggle="modal" data-target="#showDetail">显示细节 </a></td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${openningReports}" var="openningReport">
                        <tr class="openningReportRow${openningReport.id}">
                            <!-- 只拥有指导老师的角色 的操作-->
                            <sec:authorize ifAllGranted="ROLE_TUTOR"
                                           ifNotGranted="ROLE_DEPARTMENT_DIRECTOR">
                                <td id="tutor${openningReport.id}"><c:if
                                        test="${openningReport.auditByTutor.approve==false}">
                                    <%-- <a class="btn btn-danger btn-xs" href="approveOpenningReportByTutor.html?openningReportId=${openningReport.id}">通过</a> --%>
                                    <a id="approveOfTutor${openningReport.id}"
                                       class="btn btn-success btn-xs"
                                       onclick="approveOpenningReportByTutor(${openningReport.id})">通过</a>
                                </c:if> <c:if test="${openningReport.auditByTutor.approve==true}">
                                    <%-- <a class="btn btn-danger btn-xs" href="rejectOpenningReportByTutor.html?openningReportId=${openningReport.id}">退回</a> --%>
                                    <a id="rejectOfTutor${openningReport.id}"
                                       class="btn btn-danger btn-xs"
                                       onclick="rejectOpenningReportByTutor(${openningReport.id})">退回</a>
                                </c:if></td>
                            </sec:authorize>
                            <!-- 只拥有教研室主任的角色的操作 -->
                            <sec:authorize ifAllGranted="ROLE_DEPARTMENT_DIRECTOR"
                                           ifNotGranted="ROLE_TUTOR">
                                <td id="director${openningReport.id }"><c:if
                                        test="${openningReport.auditByDepartmentDirector.approve==false}">
                                    <%-- <a class="btn btn-danger btn-xs" href="approveOpenningReportByDirector.html?openningReportId=${openningReport.id}">通过</a> --%>
                                    <a id="approveOfDirector${openningReport.id}"
                                       class="btn btn-success btn-xs"
                                       onclick="approveOpenningReportByDirector(${openningReport.id})">通过</a>
                                </c:if> <c:if
                                        test="${openningReport.auditByDepartmentDirector.approve==true}">
                                    <%-- <a class="btn btn-danger btn-xs" href="rejectOpenningReportByDirector.html?openningReportId=${openningReport.id}">退回</a> --%>
                                    <a id="rejectOfDirector${openningReport.id}"
                                       class="btn btn-danger btn-xs"
                                       onclick="rejectOpenningReportByDirector(${openningReport.id})">退回</a>
                                </c:if></td>
                            </sec:authorize>
                            <!--同时拥有指导老师和教研室主任两个角色的操作 -->
                            <sec:authorize
                                    ifAllGranted="ROLE_TUTOR,ROLE_DEPARTMENT_DIRECTOR">
                                <td id="btn${openningReport.id}"><c:if
                                        test="${openningReport.auditByDepartmentDirector.approve==false&&openningReport.auditByTutor.approve==false}">
                                    <%-- <a class="btn btn-danger btn-xs"  href="approveOpenningReportByDirectorAndDean.html?openningReportId=${openningReport.id}"><span>通过</span></a> --%>
                                    <a id="approveOfTutorAndDirector${openningReport.id}"
                                       class="btn btn-success btn-xs"
                                       onclick="approveOpenningReportByDirectorAndDean(${openningReport.id})">通过</a>
                                </c:if> <c:if
                                        test="${openningReport.auditByDepartmentDirector.approve==true&&openningReport.auditByTutor.approve==true}">
                                    <%-- <a class="btn btn-danger btn-xs"  href="rejectOpenningReportByDirectorAndDean.html?openningReportId=${openningReport.id}"><span>退回</span></a> --%>
                                    <a id="rejectOfTutorAndDirector${openningReport.id}"
                                       class="btn btn-danger btn-xs"
                                       onclick="rejectOpenningReportByDirectorAndDean(${openningReport.id})">退回</a>
                                </c:if></td>
                            </sec:authorize>

                            <!-- 只拥有指导老师的角色 的状态-->
                            <sec:authorize ifAllGranted="ROLE_TUTOR"
                                           ifNotGranted="ROLE_DEPARTMENT_DIRECTOR">
                                <td id="statusOfTutor${openningReport.id}"><c:if
                                        test="${openningReport.auditByTutor.approve==false}">退回</c:if>
                                    <c:if test="${openningReport.auditByTutor.approve==true}">通过</c:if>
                                </td>
                            </sec:authorize>
                            <!-- 只拥有教研室主任的角色的状态 -->
                            <sec:authorize ifAllGranted="ROLE_DEPARTMENT_DIRECTOR"
                                           ifNotGranted="ROLE_TUTOR">
                                <td id="statusOfDirector${openningReport.id }"><c:if
                                        test="${openningReport.auditByDepartmentDirector.approve==false}">退回</c:if>
                                    <c:if
                                            test="${openningReport.auditByDepartmentDirector.approve==true}">通过</c:if>
                                </td>
                            </sec:authorize>
                            <!--同时拥有指导老师和教研室主任两个角色的状态 -->
                            <sec:authorize
                                    ifAllGranted="ROLE_TUTOR,ROLE_DEPARTMENT_DIRECTOR">
                                <td id="tutorOfTutorAndDirectior${openningReport.id}"><c:if
                                        test="${openningReport.auditByTutor.approve==false}">退回</c:if>
                                    <c:if test="${openningReport.auditByTutor.approve==true}">通过</c:if>
                                </td>
                                <td id="directorOfTutorAndDirector${openningReport.id}"><c:if
                                        test="${openningReport.auditByDepartmentDirector.approve==false}">退回</c:if>
                                    <c:if
                                            test="${openningReport.auditByDepartmentDirector.approve==true}">通过</c:if>
                                </td>
                            </sec:authorize>

                            <td class="openningReportMainTutorageName${openningReport.id}">${openningReport.paperProject.mainTutorage.tutor.name}</td>
                            <td class="openningReportTitle${openningReport.id}">${openningReport.paperProject.title}</td>
                            <td class="openningReportSubTitle${openningReport.id}">${openningReport.paperProject.subTitle}</td>
                            <td class="studentName${openningReport.id}">${openningReport.paperProject.student.name}</td>
                            <td class="studentNo${openningReport.id }">${openningReport.paperProject.student.no}</td>


                            <td id="download${openningReport.id}"><a id="downloadReport2"
                                                                     href="downloadOpenningReport.html?openningReportId=${openningReport.id}"
                                                                     class="btn btn-xs">

                                    <%--auditByDepartmentDirector    auditByTutor   approve--%>
                                    <%--根据不同的角色，显示不同的按钮--%>
                                    <%--主指导角色--%>
                                <sec:authorize ifAllGranted="ROLE_TUTOR"
                                               ifNotGranted="ROLE_DEPARTMENT_DIRECTOR">
                                    <c:if test="${openningReport.auditByTutor.approve}">
                                        <span><i class="icon icon-download"></i> 下载</span>
                                    </c:if>
                                </sec:authorize>
                                <!-- 拥有教研室角色，但不拥有主指导教师角色 -->
                                <sec:authorize ifAllGranted="ROLE_DEPARTMENT_DIRECTOR"
                                               ifNotGranted="ROLE_TUTOR">
                                    <c:if test="${openningReport.auditByDepartmentDirector.approve}">
                                        <span><i class="icon icon-download"></i> 下载</span>
                                    </c:if>
                                </sec:authorize>
                                <!-- 同时拥有主指导教师和教研室主任角色 -->
                                <sec:authorize ifAllGranted="ROLE_TUTOR,ROLE_DEPARTMENT_DIRECTOR">
                                    <c:if test="${openningReport.auditByTutor.approve&&openningReport.auditByDepartmentDirector.approve}">
                                        <span><i class="icon icon-download"></i> 下载</span>
                                    </c:if>
                                </sec:authorize>

                            </a></td>
                            <td><a class="btn btn-primary btn-xs"
                                   href="/bysj3/process/showDetail.html?graduateProjectId=${openningReport.paperProject.id}"
                                   data-toggle="modal" data-target="#showDetail">显示细节 </a></td>
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
    <div class="modal fade" id="showDetail" tabindex="-1" role="dialog"
         aria-hidden="true" aria-labelledby="modelOpeningReportTime">
        <div class="modal-dialog">
            <div class="modal-content"></div>
        </div>
    </div>
</div>

