<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsps/includeURL.jsp" %>
<script type="text/javascript">
    $(function () {
        $("[data-toggle='popover']").popover();
    });
</script>
<div class="container-fluid" style="width: 100%">
    <div class="row-fluid">
        <ul class="breadcrumb">
            <li>毕业设计流程</li>
            <li>填写工作进程表<span class="divider">/</span>
            </li>
        </ul>
    </div>
    <c:if test="${not empty message}">
        <h3 style="color: red">${message}</h3>
    </c:if>
    <c:choose>
        <%--   0表示没有提交工作进程表  --%>
        <c:when test="${ifShowSchedule==0}">
            <div class="row">
                <div class="alert alert-warning alert-dismissable" role="alert">
                    <button class="close" type="button" data-dismiss="alert">&times;</button>
                    您尚未提交工作进程表！
                </div>
            </div>
            <div class="row">

                <c:if test="${hasTimeSpan==0}">
                    <h2 style="color: red;">教研室主任未设置毕业设计时间，不能提交工作进程表</h2>
                </c:if>
                <c:if test="${hasTimeSpan==1}">
                    <c:if test="${inTime==true}">
                        <a class="btn btn-default" href="<%=basePath%>student/addSchedule.html">添加工作进程表</a>
                    </c:if>
                    <c:if test="${inTime==false}">
                        <h3 style="color: red">当前时间不允许填写工作进程表</h3>

                        <h3 style="color: red">教研室主任设计的毕业设计时间：${beginTime}-----${endTime}</h3>
                    </c:if>
                </c:if>
            </div>
        </c:when>
        <%--  1表示已经提交了工作进程表--%>
        <c:when test="${ifShowSchedule==1}">
            <table class="table table-striped table-bordered table-hover datatable">
                <thead>
                <tr>
                    <th>开始时间</th>
                    <th>结束时间</th>
                    <th>应完成的工作内容</th>
                    <th>检查情况</th>
                    <th>操作</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${schedules}" var="schedule">
                    <tr class="scheduleRow${schedule.id}">
                        <td><fmt:formatDate value="${schedule.beginTime.time}"
                                            type="date" dateStyle="long"/></td>
                        <td><fmt:formatDate value="${schedule.endTime.time}"
                                            type="date" dateStyle="long"/></td>
                        <td>
                            <c:choose>
                                <c:when test="${fn:length(schedule.content)>30}">
                                    <div id="showDetail">
                                            ${fn:substring(schedule.content,0,30)}...
                                                <button type="button" data-toggle="modal"
                                                        data-target="#showDetail${schedule.id}">
                                            内容详情
                                                </button>

                                                <div class="modal fade" id="showDetail${schedule.id}" tabindex="-1"
                                                     role="dialog"
                                                     aria-hidden="true" aria-labelledby="modelOpeningReportTime">
                                                    <div class="modal-dialog">
                                                        <div class="modal-content">
                                                                <%--在这个项目中，一般都需要将模态框放入到form表单中，因为大部分模态框提交后需要请求后台进行处理--%>
                                                                <%--这是模态框的头部--%>
                                                            <div class="modal-header">
                                                                <button type="button" class="close"
                                                                        data-dismiss="modal">
                                                                    <span aria-hidden="true">&times;</span><span
                                                                        class="sr-only">Close</span>
                                                                </button>
                                                                <h4 class="modal-title">详细信息</h4>
                                                            </div>
                                                                <%--这是模态框显示内容的主体部分--%>
                                                            <div class="modal-body">
                                                            <textarea class="form-control" rows="7"
                                                                      readonly> ${schedule.content}
                                                            </textarea>
                                                            </div>
                                                                <%--这是模态框的尾部--%>
                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-default"
                                                                        data-dismiss="modal">关闭
                                                                </button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                    </div>
                                </c:when>
                                <c:otherwise>
                                    ${schedule.content}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${schedule.audit.remark}</td>
                        <td><a
                                href="<%=basePath%>student/addScheduleContent.html?scheduleId=${schedule.id}"
                                class="btn btn-default" data-toggle="modal" data-target="#addScheduleContent"><span>填写工作内容</span></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
    </c:choose>
    <div class="modal fade" id="addScheduleContent" tabindex="-1"
         role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content"></div>
        </div>
    </div>
</div>