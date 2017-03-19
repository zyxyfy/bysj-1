<%--
  Created by IntelliJ IDEA.
  User: 张战
  Date: 2016/3/19
  Time: 14:44
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<div class="container-fluid" style="width: 100%">
    <div class="row-fluid">
        <ul class="breadcrumb">
            <li>评审流程</li>
            <li>设置答辩时间地点<span class="divider">/</span>
            </li>
        </ul>
    </div>

    <div class="row">
        <form class="form-inline" role="form" action="<%=basePath%>replyGroups/setReplyGroup.html">
            <div class="form-group ">
                <label for="name">小组名称：</label> <input type="text" value="${title}"
                                                       class="form-control" name="title" id="name"
                                                       placeholder="请输入小组名称">
                <button type="submit" class="btn btn-default"><i class="icon-search"></i> 查询</button>
            </div>
        </form>
    </div>
    <br>
    <div class="row">
        <a class="btn btn-primary" href="<%=basePath%>replyGroups/setReplyGroup.html"><i class="icon-reply-all"></i>
            显示所有小组</a>
    </div>

    <br>

    <div class="row-fluid">
        <table
                class="table table-striped table-bordered table-hover datatable">
            <thead>
            <tr>
                <th>小组名称</th>
                <th>小组组长</th>
                <th>答辩老师</th>
                <th>答辩学生</th>
                <th>开始时间</th>
                <th>结束时间</th>
                <th>答辩地点</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${empty replyGroupList}">
                    
                </c:when>
                <c:otherwise>
                    <c:forEach items="${replyGroupList}" var="replyGroup">
                        <tr>
                            <td>${replyGroup.description}</td>
                            <td>${replyGroup.leader.name}</td>
                            <td>
                                <c:forEach items="${replyGroup.members}" var="member">
                                    ${member.name} &nbsp;&nbsp;
                                </c:forEach>
                            </td>
                            <td>
                                <c:forEach items="${replyGroup.graduateProject}" var="graduateProject">
                                    ${graduateProject.student.name}&nbsp;&nbsp;
                                </c:forEach>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${replyGroup.replyTime.beginTime.time==null}">
                                        未设置
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:formatDate value="${replyGroup.replyTime.beginTime.time}"
                                                        pattern="yyyy/MM/dd"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${replyGroup.replyTime.endTime.time==null}">
                                        未设置
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:formatDate value="${replyGroup.replyTime.endTime.time}"
                                                        pattern="yyyy/MM/dd"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${replyGroup.classRoom.description==null}">
                                        未设置
                                    </c:when>
                                    <c:otherwise>
                                        ${replyGroup.classRoom.description}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${replyGroup.replyTime==null||replyGroup.classRoom==null}">
                                        <a class="btn btn-default" data-toggle="modal" data-target="#setTimeClassRoom"
                                           href="<%=basePath%>replyGroups/setTimeAndClassRoom.html?replyGroupId=${replyGroup.id}"><i
                                                class="icon-plus"></i> 设置答辩时间地点</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a class="btn btn-warning btn-xs" data-toggle="modal" data-target="#setTimeClassRoom"
                                           href="<%=basePath%>replyGroups/setTimeAndClassRoom.html?replyGroupId=${replyGroup.id}"><i
                                                class="icon-edit"></i>修改</a>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
         <c:choose>
                <c:when test="${empty replyGroupList}">
                    <div class="alert alert-warning alert-dismissable" role="alert">
                        <button class="close" type="button" data-dismiss="alert">&times;</button>
                        没有可显示的答辩小组信息
                    </div>
                </c:when>
                </c:choose>
    </div>
    <div class="row">
        <%@include file="/WEB-INF/jsps/page/pageBar.jsp" %>
    </div>

    <%--如果需要在模态框中加载另一个jsp，则需要书写以下的div--%>
    <div class="modal fade" id="setTimeClassRoom" tabindex="-1" role="dialog"
         aria-hidden="true" aria-labelledby="modelOpeningReportTime">
        <div class="modal-dialog">
            <div class="modal-content">
                <%--此处会填充另外一个jsp的内容--%>
            </div>
        </div>
    </div>
</div>