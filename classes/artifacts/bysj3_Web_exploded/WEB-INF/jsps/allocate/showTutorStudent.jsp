<%--
  Created by IntelliJ IDEA.
  User: zhan
  Date: 2016/4/13
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal"
            aria-hidden="true">×
    </button>
    <h4 class="modal-title" id="myModalLabel">
        ${tutor.name}老师的学生
    </h4>
    <h5>共${count}个学生,已有${selectProject}人选择课题</h5>
</div>
<div class="modal-body">
    <table class="table table-striped table-bordered table-hover datatable">
        <thead>
        <tr>
            <th>学号</th>
            <th>姓名</th>
            <th>班级</th>
            <th>课题</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${studentList==null}">
                <h4>未匹配学生</h4>
            </c:when>
            <c:otherwise>
                <c:forEach items="${studentList}" var="student">
                    <tr>
                        <td>${student.no}</td>
                        <td>${student.name}</td>
                        <td>${student.studentClass.description}</td>
                        <td>
                            <c:choose>
                                <c:when test="${student.graduateProject==null}">
                                    未选择课题
                                </c:when>
                                <c:otherwise>
                                    ${student.graduateProject.title}
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        </tbody>

    </table>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-default"
            data-dismiss="modal">关闭
    </button>
</div>
