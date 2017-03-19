<%@include file="/WEB-INF/jsps/includeURL.jsp" %>
<%--
  Created by IntelliJ IDEA.
  User: zhan
  Date: 2016/3/5
  Time: 15:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">
        <span aria-hidden="true">&times;</span><span
            class="sr-only">Close</span>
    </button>
    <h4 class="modal-title">已分配学生</h4>
</div>
<div class="modal-body">
    <table
            class="table table-striped table-bordered table-hover datatable">
        <thead>
        <tr>
            <th>序号</th>
            <th>班级</th>
            <th>学号</th>
            <th>姓名</th>
            <th>课题</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${student==null}">
                <span class="label label-default">
                            该老师没有匹配任何学生
                </span>
            </c:when>
            <c:otherwise>
                <c:forEach items="${studentList}" var="student">
                    <tr>
                        <td>${student.id}</td>
                        <td>${student.studentClass}</td>
                        <td>${student.no}</td>
                        <td>${student.name}</td>
                        <td>
                            <c:choose>
                                <c:when test="${student.graduateProject==null}">
                                    <span class="label label-default">没有选择课题</span>
                                </c:when>
                                <c:otherwise>
                                    ${student.graduateProject}
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>

        <%--<tr>
            <td>01</td>
            <td>信管131</td>
            <td>2013021600</td>
            <td>小明</td>
            <td>无</td>
        </tr>
        <tr>
            <td>02</td>
            <td>信管131</td>
            <td>2013021601</td>
            <td>小丽</td>
            <td>无</td>
        </tr>--%>

        </tbody>
    </table>
</div>
