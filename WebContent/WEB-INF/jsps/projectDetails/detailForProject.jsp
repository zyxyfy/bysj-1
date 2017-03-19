<%--
  Created by IntelliJ IDEA.
  User: 张战
  Date: 2016/3/31
  Time: 9:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>
<%--

<script type="text/javascript">
$(function(){
	$("#detail").empty();
	$("#detail").html(
			"<table class='table table-striped table-bordered table-hover datatable'>"
    +"<tr>"
    +"<td>课题名称：</td>"
    +"<td>${graduateProject.title}</td>"
    +"</tr>"
    +"<tr>"
    +"<td>副标题：</td>"
    +"<td>${graduateProject.subTitle}</td>"
    +"</tr>"
    +"<tr>"
    +"<td>指导老师：</td>"
    +"<td>${graduateProject.proposer.name}</td>"
    +"<td>职称/学位：</td>"
    +"<td><c:choose><c:when test='${graduateProject.proposer.proTitle.description==null}'>暂无</c:when><c:otherwise>${graduateProject.proposer.proTitle.description}</c:otherwise></c:choose>/<c:choose><c:when test='${graduateProject.proposer.degree.description==null}'>暂无</c:when><c:otherwise>${graduateProject.proposer.degree.description}</c:otherwise></c:choose></td>"
    +"</tr>"
    +"<tr>"
    +"<td>课题类别：</td>"
    +"<td>${graduateProject.category}</td>"
    +"<td>课题类型：</td>"
    +"<td>${graduateProject.projectType.description}</td>"
    +"</tr>"
    +"<tr>"
    +"<td>课题性质：</td>"
    +"<td>${graduateProject.projectFidelity.description}</td>"
    +"<td>题目来源：</td>"
    +"<td>${graduateProject.projectFrom.description}</td>"
    +"</tr>"
    +"<tr>"
    +"<td>限选专业：</td>"
    +"<td>${graduateProject.major.description}</td>"
    +"</tr>"
    +"<tr>"
    +"<td>设计（论文）工作内容：</td>"
    +"<td>${graduateProject.content}</td>"
    +"</tr>"
    +"<tr>"
    +"<td>设计（论文）基本要求：</td>"
    +"<td>${graduateProject.basicRequirement}</td>"
    +"</tr>"
    +"<tr>"
    +"<td>所需基本技能：</td>"
    +"<td>${graduateProject.basicSkill}</td>"
    +"</tr>"
    +"<tr>"
    +"<td>主要参考资料及文献</td>"
    +"<td>${graduateProject.reference}</td>"
    +"</tr>"
    +"</table>"
    );
	
})



</script>
--%>


<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal"
            aria-hidden="true">×
    </button>
    <h4 class="modal-title" id="myModalLabel">
        课题详情
    </h4>
</div>

<div class="modal-body" id="detail" style="width:auto;">
    <table class="table table-striped table-bordered table-hover datatable">
        <tr>
            <td>课题名称：</td>
            <td>${graduateProject.title}</td>
        </tr>
        <tr>
            <td>副标题：</td>
            <c:choose>
                <c:when test="${empty graduateProject.subTitle}">
                    <td></td>
                </c:when>
                <c:otherwise>
                    <td>${graduateProject.subTitle}</td>
                </c:otherwise>
            </c:choose>
        </tr>
        <tr>
            <td>指导老师：</td>
            <td>${graduateProject.proposer.name}</td>
        </tr>
        <tr>

            <td>职称/学位：</td>
            <td>
                <c:choose>
                    <c:when test="${graduateProject.proposer.proTitle.description==null}">
                        暂无
                    </c:when>
                    <c:otherwise>
                        ${graduateProject.proposer.proTitle.description}
                    </c:otherwise>
                </c:choose>
                /
                <c:choose>
                    <c:when test="${graduateProject.proposer.degree.description==null}">
                        暂无
                    </c:when>
                    <c:otherwise>
                        ${graduateProject.proposer.degree.description}
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td>课题类别：</td>
            <td>${graduateProject.category}</td>
        </tr>
        <tr>
            <td>课题性质：</td>
            <td>${graduateProject.projectFidelity.description}</td>
        </tr>
        <tr>

            <td>课题类型：</td>
            <td>${graduateProject.projectType.description}</td>
        </tr>
        <tr>

            <td>题目来源：</td>
            <td>${graduateProject.projectFrom.description}</td>
        </tr>
        <tr>
            <td>限选专业：</td>
            <td>${graduateProject.major.description}</td>
        </tr>
        <tr>
            <td>设计（论文）工作内容：</td>
            <td><textarea cols="60" rows="7">${graduateProject.content}</textarea></td>
        </tr>
        <tr>
            <td>设计（论文）基本要求：</td>
            <td><textarea cols="60" rows="7">${graduateProject.basicRequirement}</textarea></td>
        </tr>
        <tr>
            <td>所需基本技能：</td>
            <td><textarea cols="60" rows="7">${graduateProject.basicSkill}</textarea></td>
        </tr>
        <tr>
            <td>主要参考资料及文献</td>
            <td><textarea cols="60" rows="7">${graduateProject.reference}</textarea></td>
        </tr>
    </table>
</div>
<div class="modal-footer">
    <button type="submit" class="btn btn-default"
            data-dismiss="modal">关闭
    </button>
</div>
