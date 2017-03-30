<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/WEB-INF/jsps/includeURL.jsp" %>
<script type="text/javascript">

    $(function () {
        $("#mail").empty();
        $("#mail").append("<table class='table table-bordered table-striped'>" +
        "<tbody>" +
        "<tr>" +
        "<th>发布时间</th>"
        + "<td> <fmt:formatDate value='${mail.addressTime.time}' pattern='yyyy-MM-dd HH:mm:ss'/></td>"
        + "</tr>"
        + "<tr>"
        + " <th>发布者</th>"
        + "<td>${mail.addressor.name} </td>"
        + "</tr>"
        + "<tr>"
        + " <th>内容</th>"
        + " <td>${mail.content}</td>"
        + " </tr>"
        + " <tr> <th>附件</th><c:choose><c:when test='${mail.attachment.url==null}'><td>无附件</td></c:when><c:otherwise><td><a  class='btn btn-primary' href='downloadMail.html?mailId=${mail.id}'>点击下载</a></td></c:otherwise></c:choose></tr> " +
        "</tbody>"
        + "</table>");
    })

</script>
<div class="modal-header">
    <h4 class="modal-title" id="myModalLabel">
        邮件详情
    </h4>
</div>
<div class="modal-body" id="mail">
    <table class="table table-bordered table-striped">
        <tbody>
        <tr>
            <th>发布时间</th>
            <td><fmt:formatDate value="${mail.addressTime.time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
        <tr>
            <th>发布者</th>
            <td>${mail.addressor.name} </td>
        </tr>
        <tr>
            <th>内容</th>
            <td width="auto">${mail.content}</td>
        </tr>
        <tr>

            <th>附件</th>
            <c:choose>
                <c:when test="${empty mail.attachment}">
                    <td>无附件</td>
                </c:when>
                <c:otherwise>
                    <td><a class="btn btn-primary" href="downloadMail.html?mailId=${mail.id}">点击下载</a></td>
                </c:otherwise>
            </c:choose>

        </tr>
        </tbody>
    </table>
</div>
<div class="modal-footer">
    <a type="button" class="btn btn-default" href="<%=basePath%>notice/noticesToMe.html">关闭
    </a>
</div>