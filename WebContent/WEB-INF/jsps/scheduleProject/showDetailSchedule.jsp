<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsps/includeURL.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%--<div>
    <h3><label
            class="label label-default">课题信息：标题--${graduateProject.title}，副标题--${empty (graduateProject.subTitle)?"无":graduateProject.subTitle}</label>
    </h3>
    &lt;%&ndash;<p>${graduateProject.title}</p>
    <p>${graduateProject.subTitle}</p>&ndash;%&gt;
    <div>
        <h3><label
                class="label label-default">学生信息：姓名--${graduateProject.student.name}，班级--${graduateProject.student.studentClass.description}，学号--${graduateProject.student.no}</label>
        </h3>
        <a class="btn btn-warning"
           href="stageAchievements.html" title="审阅各阶段成果"><i class="icon-backward"></i> 返回</a>
    </div>
</div>--%>
<hr>
<a href="<%=basePath%>process/checkStudentSchedule.html" class="btn btn-warning"><i class="icon-backward"></i>返回 </a>
<hr>

<div class="row-fluid">
    <table class="table table-striped table-bordered table-hover datatable">
        <thead>
        <tr>
            <th>开始时间</th>
            <th>结束时间</th>
            <th>完成的工作内容</th>
            <th>审核情况</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${schedules}" var="schedule">
            <tr class="scheduleRow${schedule.id}">
                <td><fmt:formatDate value="${schedule.beginTime.time}" type="date" dateStyle="long"/></td>
                <td><fmt:formatDate value="${schedule.endTime.time}" type="date" dateStyle="long"/></td>

                <td><c:choose>
                    <c:when test="${fn:length(schedule.content)<40}">
                        ${schedule.content}
                    </c:when>
                    <c:otherwise>
                        <textarea rows="5" cols="30">${schedule.content}</textarea>
                    </c:otherwise>
                </c:choose></td>
                <td>${schedule.audit.remark}</td>
                <td>
                    <c:choose>
                        <c:when test="${schedule.content=='请填写工作内容'}">未提交工作内容暂不能审核</c:when>
                        <c:when test="${schedule.audit.approve==true}">
                            <a class="btn btn-danger btn-xs" data-toggle="modal" data-target="#addorEditApproveRemark"
                               href="<%=basePath%>process/addOrEditScheduleRemark.html?scheduleId=${schedule.id}">修改</a>
                        </c:when>
                        <c:otherwise>
                            <a class="btn btn-danger btn-xs"
                               href="<%=basePath%>process/addOrEditScheduleRemark.html?scheduleId=${schedule.id}"
                               data-toggle="modal" data-target="#addorEditApproveRemark">填写评语并确认通过</a>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="modal fade" id="addorEditApproveRemark" tabindex="-1" role="dialog"
     aria-hidden="true" aria-labelledby="modelOpeningReportTime">
    <div class="modal-dialog">
        <div class="modal-content"></div>
    </div>
</div>

