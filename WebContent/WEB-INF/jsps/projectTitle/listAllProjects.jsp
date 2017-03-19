<%--
  Created by IntelliJ IDEA.
  User: 张战
  Date: 2016/2/19
  Time: 21:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@include file="/WEB-INF/jsps/includeURL.jsp" %>

<script type="text/javascript">
    /*
     $("#addDesignModel").modal({backdrop:'static',keyboard:false})
     $("#addPaperModel").modal({backdrop:'static',keyboard:false})*/

    $("#123").on("hidden.bs.modal", function() {
        $(this).removeData("bs.modal");
    });

    function cloneProject(projectId) {
        $.ajax({
            url: '/bysj3/process/cloneProjectById.html',
            data: {"cloneId": projectId},
            dataType: 'json',
            type: 'get',
            success: function (data) {
                myAlert("克隆成功");
                window.location = '/bysj3/process/myProjects.html';
                return true;
            },
            error: function () {
                myAlert("克隆失败，请稍后再试");
                return false;
            }
        });
    }

    function delProject(delId) {
        //var confirmDel = window.confirm("确认删除？");
        window.wxc.xcConfirm("确认删除","confirm",{
            onOk: function(){
                $.ajax({
                    url: '/bysj3/process/delProject.html',
                    data: {"delId": delId},
                    dataType: 'json',
                    type: 'POST',
                    success: function (data) {
                        $("#project" + delId).remove();
                        /*var projectCount = $("#viewCount").text();
                         $("#viewCount").html(projectCount - 1);*/
                        changPageCount();
                        myAlert("删除成功！");
                        return true;
                    },
                    error: function () {
                        myAlert("网络故障，请稍后再试！");
                        return false;
                    }
                });
            }});
       /* if (confirmDel) {
            $.ajax({
                url: '/bysj3/process/delProject.html',
                data: {"delId": delId},
                dataType: 'json',
                type: 'POST',
                success: function (data) {
                    $("#project" + delId).remove();
                    *//*var projectCount = $("#viewCount").text();
                     $("#viewCount").html(projectCount - 1);*//*
                    changPageCount();
                    myAlert("删除成功！");
                    return true;
                },
                error: function () {
                    myAlert("您要删除的课题已经分配给学生,取消分配之后才能删除！");
                    return false;
                }
            });
        }*/

    }

    /*function cloneProject(cloneId){
     $.ajax({
     url:'process/cloneProjectById',
     data:{"cloneId":cloneId},
     dataType:'json',
     type:'POST',
     success:function (data){
     $("#project"+cloneId).copy();

     }
     });
     }*/
</script>

<div class="container-fluid" style="width: 100%">
    <%--<div class="panel-heading">
        <h5 class="panel-title">查看现有题目</h5>
    </div>--%>
    <c:choose>
        <c:when test="${ACTION_EDIT_PROJECT!=null&&ACTION_EDIT_PROJECT==true}">
            <div class="row-fluid">
                <ul class="breadcrumb">
                    <li>选题流程</li>
                    <li>更改老师毕业设计<span class="divider">/</span>
                    </li>
                </ul>
            </div>
        </c:when>
        <c:otherwise>
            <div class="row-fluid">
                <ul class="breadcrumb">
                    <li>选题流程</li>
                    <li>我申报的题目<span class="divider">/</span>
                    </li>
                </ul>
            </div>
        </c:otherwise>
    </c:choose>

    <div class="row">
        <%--<div class="row">
            <div class="col-md-4">
                题目：
                <form class="form-control" action="${actionUrl}">
                    <input class="form-control" type="text" name="title" placeholder="在此输入查询的题目...">
                    <button class="btn btn-primary" type="submit">查询</button>
                </form>
            </div>
        </div>--%>
        <form action="${actionUrl}" class="form-inline" role="form">
            <div class="form-group">
                题目：
                <%--value=${title}用来获取当前查询题目的名称 --%>
                <input type="text" class="form-control" name="title" value="${title}" required
                       placeholder="在此输入查询的题目...">
                <button class="btn btn-primary btn-sm" type="submit"><i class="icon-search"></i> 查询</button>
            </div>

        </form>
        <hr>
        <c:choose>
            <c:when test="${ifShowAll == '1' }">
                <div class="form-group">
                    <a
                            <c:if test="${viewProjectTitle=='all'}">class="btn btn-primary btn-sm" </c:if>
                            <c:if test="${viewProjectTitle!='all'}">class="btn btn-default btn-sm"</c:if>
                            href="<%=basePath%>process/listProjects.html">
                        <span class="glyphicon glyphicon-home">查看全部题目</span>
                            <%-- <i class="icon-home"></i>查看全部题目--%>

                    </a>
                    <a
                            <c:if test="${viewProjectTitle=='design'}">class="btn btn-primary btn-sm" </c:if>
                            <c:if test="${viewProjectTitle!='design'}">class="btn btn-default btn-sm"</c:if>
                            href="<%=basePath%>process/listDesignProjects.html">
                        <span class="glyphicon glyphicon-adjust">查看设计题目</span>
                            <%--<i class="icon-coffee"></i>查看设计题目--%>
                    </a>
                    <a
                            <c:if test="${viewProjectTitle=='paper'}">class="btn btn-primary btn-sm" </c:if>
                            <c:if test="${viewProjectTitle!='paper'}">class="btn btn-default btn-sm"</c:if>
                            class="btn btn-default"
                            href="<%=basePath%>process/listPaperProjects.html">
                        <span class="glyphicon glyphicon-adjust">查看论文题目</span>
                            <%--<i class="icon-desktop"></i>查看论文题目--%>
                    </a>
                </div>

            </c:when>
            <c:when test="${ifShowAll=='0'}">
                <div class="form-group">
                    <a
                            <c:if test="${viewProjectTitle=='all'}">class="btn btn-primary btn-sm" </c:if>
                            <c:if test="${viewProjectTitle!='all'}">class="btn btn-default btn-sm"</c:if>
                            href="<%=basePath%>process/myProjects.html">
                        <span class="glyphicon glyphicon-home">查看全部题目</span>
                    </a>
                    <a
                            <c:if test="${viewProjectTitle=='design'}">class="btn btn-primary btn-sm" </c:if>
                            <c:if test="${viewProjectTitle!='design'}">class="btn btn-default btn-sm"</c:if>
                            href="<%=basePath%>process/listMyDesignProjects.html">
                        <span class="glyphicon glyphicon-adjust">查看设计题目</span>
                    </a>
                    <a
                            <c:if test="${viewProjectTitle=='paper'}">class="btn btn-primary btn-sm" </c:if>
                            <c:if test="${viewProjectTitle!='paper'}">class="btn btn-default btn-sm"</c:if>
                            href="<%=basePath%>process/listMyPaperProjects.html">
                        <span class="glyphicon glyphicon-adjust">查看论文题目</span>
                    </a>
                        <%--<a class="btn btn-default" href="test.html" data-toggle="modal" data-target="#test">
                            <span class="glyphicon-book">测试</span>
                        </a>--%>
                    <br><br>
                    <c:choose>
                        <c:when test="${ABLE_TO_UPDATE==1}">
                            <a href="<%=basePath%>process/addOrEditDesignProject.html" data-toggle="modal"
                               id="addDesignModel"
                               data-backdrop="static" data-keyboard="false"
                               data-target="#editProjectModal"
                               class="btn btn-primary btn-sm">
                                <span class="glyphicon glyphicon-plus">添加设计题目</span>
                            </a>
                            <a href="<%=basePath%>process/addOrEditPaperProject.html" data-toggle="modal"
                               id="addPaperModel"
                               data-backdrop="static" data-keyboard="false"
                               data-target="#editProjectModal"
                               class="btn btn-primary btn-sm">
                                <span class="glyphicon glyphicon-plus">添加论文题目</span>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <label class="label label-warning">当前时间不在审报题目的时间范围内</label>
                        </c:otherwise>
                    </c:choose>

                </div>

            </c:when>
        </c:choose>

        <hr>
        <%--<div class="modal fade in" id="test" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">


                </div>
            </div>
        </div>--%>
            <table class="table table-striped table-bordered table-hover datatable">
            <thead>
            <tr>
                <th>提交状态</th>
                <th>题目名称</th>
                <th>副标题</th>
                <th>年份</th>
                <th>类别</th>
                <th>出题教师</th>
                <th>审核状态</th>

                <c:choose>
                    <%--更改老师的毕业设计需要显示操作这一列--%>
                    <c:when test="${ACTION_EDIT_PROJECT!=null&&ACTION_EDIT_PROJECT==true}">
                        <th>操作</th>
                    </c:when>
                    <%--我申报的题目需要显示操作这一列--%>
                    <c:when test="${ifShowAll=='0'}">
                        <th>操作</th>
                    </c:when>
                </c:choose>
                <%--1和0分别代表查询所有题目和查询我的题目--%>
                <c:if test="${ifShowAll=='0'||ifShowAll=='1'}">
                    <th>详情</th>
                </c:if>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${gradateProjectNum == 0}">
                    <div class="alert alert-warning alert-dismissable" role="alert">
                        <button class="close" type="button" data-dismiss="alert">&times;</button>
                        没有可以显示的数据
                    </div>

                </c:when>
                <c:otherwise>
                    <c:forEach items="${listProjects}" var="project">
                        <tr id="project${project.id}">
                            <td>
                                <c:choose>
                                    <c:when test="${project.proposerSubmitForApproval==true}">
                                        已送审
                                    </c:when>
                                    <c:otherwise>
                                        <a class="btn btn-default" href="#">送审</a>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                    ${project.title}
                            </td>
                            <td>
                                    ${empty(project.subTitle)?'':project.subTitle}
                            </td>
                            <td>
                                    ${empty(project.year)?'未设置':project.year}
                            </td>
                            <td>
                                    ${empty(project.category)?'未设置':project.category}
                            </td>
                            <td>
                                    ${project.proposer.name}
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${project.auditByDirector.approve==true}">
                                        <span class="label label-success">已通过</span>
                                    </c:when>
                                    <c:when test="${project.auditByDirector.approve==false}">
                                        <span class="label label-warning">已退回</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="label label-info">在审，请耐心等待</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <c:choose>
                                <%--在更改老师的毕业设计中显示修改--%>
                                <c:when test="${ACTION_EDIT_PROJECT!=null&&ACTION_EDIT_PROJECT==true}">
                                    <td>
                                        <a class="btn btn-default btn-xs"
                                           href="<%=basePath%>process/updateProjectByTutor.html?editId=${project.id}"
                                           data-toggle="modal"
                                           data-target="#editProjectModal"><i
                                                class="icon-edit"></i>修改</a>
                                        <a class="btn btn-primary btn-xs" type="button"
                                           href="<%=basePath%>process/showDetail.html?graduateProjectId=${project.id}"
                                           data-toggle="modal" data-target="#viewProjectModal"><i
                                                class="icon-coffee"></i>显示详情
                                        </a>
                                    </td>
                                </c:when>
                                <%--ifShowAll这是用来显示我申报题目的更改--%>
                                <c:when test="${ifShowAll=='0'}">
                                    <td>
                                        <c:choose>
                                            <c:when test="${ABLE_TO_UPDATE==1}">
                                                <a class="btn btn-default btn-xs" data-toggle="modal" id="123"
                                                   data-target="#editProjectModal"
                                                   href="<%=basePath%>process/editProject.html?editId=${project.id}"><i
                                                        class="icon-edit"></i>修改</a>
                                                <a class="btn btn-warning btn-xs" onclick="delProject(${project.id})"><i
                                                        class="icon-remove"></i>删除</a>
                                                <button class="btn btn-primary btn-xs"
                                                        onclick="cloneProject(${project.id})"><i
                                                        class="icon-copy"></i>克隆
                                                </button>
                                            </c:when>
                                            <c:otherwise>
                                                <span style="color: red;">不在修改时间范围内</span>
                                            </c:otherwise>
                                        </c:choose>

                                    </td>
                                </c:when>
                            </c:choose>
                            <c:if test="${ifShowAll=='0'||ifShowAll=='1'}">
                                <td>
                                    <a class="btn btn-primary btn-xs"
                                       href="<%=basePath%>process/showDetail.html?graduateProjectId=${project.id}"
                                       data-toggle="modal" data-target="#viewProjectModal"><i class="icon-coffee"></i>显示详情
                                    </a>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>

            <%@include file="/WEB-INF/jsps/page/pageBar.jsp" %>
        <div id="editProjectModal" class="modal fade in" tabindex="-1" aria-hidden="true" aria-labelledby="myLabelModal"
             role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <%--content中的内容由相应的jsp自动填充--%>
                        <%--编辑课题的模态框--%>
                </div>
            </div>
        </div>

            <div id="viewProjectModal" class="modal fade in" aria-hidden="true" aria-labelledby="myViewLabelModal"
                 role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <%--content中的内容由相应的jsp自动填充--%>
                        <%--查看课题细节的模态框--%>
                </div>
            </div>
            </div>


    </div>
</div>

