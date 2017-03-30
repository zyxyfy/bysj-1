<%--
  Created by IntelliJ IDEA.
  User: 张战
  Date: 2016/5/31
  Time: 20:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>

<div class="container-fluid" style="width: 100%">

    <div class="row-fluid">
        <ul class="breadcrumb">
            <li>打印论文附件</li>
            <li>查看工作进程表<span class="divider">/</span>
            </li>
        </ul>
    </div>
    <a class="btn btn-default" href="<%=basePath%>print/printAttachmentOfProject.html"><i class="icon-backward"></i> 返回</a>
    <hr>
    <c:choose>
        <c:when test="${empty scheduleList}">
            <div class="alert alert-warning alert-dismissable" role="alert">
                <button class="close" type="button" data-dismiss="alert">&times;</button>
                没有可以显示的数据
            </div>
        </c:when>
        <c:otherwise>
            <table class="table table-striped table-bordered table-hover datatable">
                <thead>
                <tr>
                    <th>时间</th>
                    <th>应完成的工作内容</th>
                    <th>评语</th>
                    <th>审核日期</th>
                    <th>是否通过</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${scheduleList}" var="schedule" varStatus="index">
                    <c:set value="${schedule.audit}" var="scheduleAudit"/>
                    <tr>
                        <td>
                            <fmt:formatDate value="${schedule.beginTime.time}" pattern="yyyy/MM/dd"/>   ----   <fmt:formatDate
                                value="${schedule.endTime.time}" pattern="yyyy/MM/dd"/>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${empty schedule.content}">
                                    <p style="color:red">未填写</p>
                                </c:when>
                                <c:otherwise>
                                    <textarea readonly>${schedule.content}</textarea>
                                </c:otherwise>
                            </c:choose>

                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${empty scheduleAudit.remark}">
                                    <p style="color: red">未审核</p>
                                </c:when>
                                <c:otherwise>
                                    <textarea readonly>${scheduleAudit.remark}</textarea>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${empty scheduleAudit.auditDate}">
                                    <p style="color: red">未审核</p>
                                </c:when>
                                <c:otherwise>
                                    <fmt:formatDate value="${scheduleAudit.auditDate.time}" pattern="yyyy/MM/dd"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${empty scheduleAudit.approve}">
                                    <p style="color: red">未审核</p>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${scheduleAudit.approve ==true}">
                                        <label class="label label-success">通过</label>
                                    </c:if>
                                    <c:if test="${scheduleAudit.approve==false}">
                                        <label class="label label-warning">未通过</label>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </c:otherwise>
    </c:choose>

</div>

