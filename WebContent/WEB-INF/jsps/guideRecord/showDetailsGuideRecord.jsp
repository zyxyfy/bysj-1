<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsps/includeURL.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<form action="<%=basePath%>print/printAttachmentOfProject.html?pageNo=${noPage}&pageSize=${sizePage}" method="get">
    <div class="modal-header">
        <h4 class="modal-title">查看指导记录</h4>
    </div>
    <div class="modal-body">
        <div class="row-fluid">
            <table
                    class="table table-striped table-bordered table-hover datatable">
                <thead>
                <tr>
                    <th>审核状态</th>
                    <th>指导时间</th>
                    <th>指导地点</th>
                    <th>指导内容</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${guideRecords}" var="guideRecord">
                    <tr class="guideRecordRow${guideRecord.id}">
                        <td id="status${guideRecord.id}">
                            <c:choose>
                                <c:when test="${guideRecord.auditedByTutor.approve==true}"><label
                                        class="label label-success">通过</label></c:when>
                                <c:when test="${guideRecord.auditedByTutor.approve==false}"><label
                                        class="label label-warning">未通过</label></c:when>
                                <c:otherwise>
                                    未审核
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td><fmt:formatDate value="${guideRecord.time.time}" type="date" dateStyle="long"/></td>
                        <td>${guideRecord.classRoom.description}</td>
                        <td>${guideRecord.description}</td>

                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <
    <div class="modal-footer">
        <button type="submit" class="btn btn-default">关闭
        </button>
    </div>
</form>