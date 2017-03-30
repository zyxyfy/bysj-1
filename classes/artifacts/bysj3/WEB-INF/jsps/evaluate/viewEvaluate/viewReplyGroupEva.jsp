<%--

打印论文附件查看答辩小组意见的页面

  Created by IntelliJ IDEA.
  User: 张战
  Date: 2016/5/24
  Time: 17:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>

<form action="<%=basePath%>print/printAttachmentOfProject.html?pageNo=${pageNo}&pageSize=${pageSize}">

    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"
                aria-hidden="true">×
        </button>
        <h4 class="modal-title" id="myModalLabel">
            附表9：
            山东建筑大学毕业设计（论文）答辩小组意见表
            <br>
            <c:choose>
                <c:when test="${empty graduateProject.subTitle}">
                    题目名称：${graduateProject.title}
                </c:when>
                <c:otherwise>
                    题目名称：${graduateProject.title}——${graduateProject.subTitle}
                </c:otherwise>
            </c:choose>
            <br>
            班级：${graduateProject.student.studentClass.description}&nbsp;&nbsp;学生姓名：${graduateProject.student.name}&nbsp;&nbsp;学号:${graduateProject.student.no}
        </h4>
    </div>
    <div class="modal-body">
        <table class="table" border="1" width="500px">
            <c:set var="graduateProject" value="${graduateProject}"/>
            <tr>
                <td>
                    完成任务书规定的要求与水平评分 评分（0-10分）
                </td>
                <td>
                    <c:set value="${graduateProject.commentByGroup.completenessScore}" var="completenessScore"/>
                    <c:choose>
                        <c:when test="${completenessScore<'6'}">
                            <span class="label label-danger">${completenessScore}</span>
                        </c:when>
                        <c:when test="${completenessScore>='6'&&completenessScore<='8'}">
                            <span class="label label-warning">${completenessScore}</span>
                        </c:when>
                        <c:when test="${completenessScore>'8'}">
                            <span class="label label-success">${completenessScore}</span>
                        </c:when>
                    </c:choose>
                </td>
                <td>
                    论文与实物的质量 评分（0-10分）
                </td>
                <td>
                    <c:set value="${graduateProject.commentByGroup.qualityScore}" var="qualityScore"/>

                    <c:choose>
                        <c:when test="${qualityScore<'6'}">
                            <span class="label label-danger">${qualityScore}</span>
                        </c:when>
                        <c:when test="${qualityScore>='6'&&qualityScore<'8'}">
                            <span class="label label-warning">${qualityScore}</span>
                        </c:when>
                        <c:when test="${qualityScore>='8'}">
                            <span class="label label-success">${qualityScore}</span>
                        </c:when>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td>
                    论文内容的答辩陈述评分
                </td>
                <td>
                    <c:set value="${graduateProject.commentByGroup.replyScore}" var="replyScore"/>

                    <c:choose>
                        <c:when test="${replyScore<'6'}">
                            <span class="label label-danger">${replyScore}</span>
                        </c:when>
                        <c:when test="${replyScore>='6'&&replyScore<'8'}">
                            <span class="label label-warning">${replyScore}</span>
                        </c:when>
                        <c:otherwise>
                            <span class="label label-success">${replyScore}</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    回答问题的正确性评分
                </td>
                <td>
                    <c:set value="${graduateProject.commentByGroup.correctnessSocre}" var="correctnessSocre"/>

                    <c:choose>
                        <c:when test="${correctnessSocre<'6'}">
                            <span class="label label-danger">${correctnessSocre}</span>
                        </c:when>
                        <c:when test="${correctnessSocre>='6'&&correctnessSocre<'8'}">
                            <span class="label label-warning">${correctnessSocre}</span>
                        </c:when>
                        <c:otherwise>
                            <span class="label label-success">${correctnessSocre}</span>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td colspan="1">答辩小组的评语</td>
                <td colspan="3">
                    <textarea readonly class="form-control">${graduateProject.commentByGroup.remarkByGroup}</textarea>
                </td>
            </tr>
            <tr>
                <td colspan="1">是否通过答辩</td>
                <td colspan="3">
                    <c:choose>
                        <c:when test="${graduateProject.commentByGroup.qualifiedByGroup}">
                            <span class="label label-success">已通过答辩</span>
                        </c:when>
                        <c:otherwise>
                            <span class="label label-danger">未通过答辩</span>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </table>
    </div>
    <div class="modal-footer">
        <button class="button" type="submit">关闭</button>
    </div>
</form>




