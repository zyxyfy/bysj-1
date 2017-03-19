<%--
  Created by IntelliJ IDEA.
  User: zhan
  Date: 2016/3/27
  Time: 17:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsps/includeURL.jsp" %>

<script type="text/javascript">
    function delRemarkTemplate(remarkTemplateId) {
    	window.wxc.xcConfirm("确认删除？","confirm",{
			onOk: function(){
				$.ajax({
	                url: '/bysj3/evaluate/delRemarkTemplate.html',
	                type: 'POST',
	                data: {"delId": remarkTemplateId},
	                dataType: 'json',
	                success: function (data) {
	                    $("#remarkTemplateTr" + remarkTemplateId).remove();
	                    myAlert("删除成功！");
	                    return true;
	                },
	                error: function () {
                        myAlert("删除失败，请稍后再试");
	                    return false;
	                }
	            });
			}});
        /* var del = window.confirm("确认删除？");
        if (del) {
            $.ajax({
                url: '/bysj3/evaluate/delRemarkTemplate.html',
                type: 'POST',
                data: {"delId": remarkTemplateId},
                dataType: 'json',
                success: function (data) {
                    $("#remarkTemplateTr" + remarkTemplateId).remove();
                    myAlert("删除成功！");
                    return true;
                },
                error: function () {
                    myAlert("出现网络错误，请稍后再试");
                    return false;
                }
            });
        } */
    }
</script>

<div class="container-fluid" style="width: 100%">
    <div class="row-fluid">
        <ul class="breadcrumb">
            <li>评审流程</li>
            <li>设置评语模版<span class="divider">/</span>
            </li>
        </ul>
    </div>


    <div class="row">
        <div class="form-group">
            <span class="label label-info">设计题目：</span>
            <a href="<%=basePath%>evaluate/setDesignRemarkForTutor.html" class="btn btn-primary btn-sm"> <i
                    class="icon-plus"></i>指导教师评语条目
            </a>
            <a href="<%=basePath%>evaluate/setDesignRemarkForReviewer.html" class="btn btn-primary btn-sm"> <i
                    class="icon-plus"></i>评阅人评语条目
            </a>
            <a href="<%=basePath%>evaluate/setDesignRemarkForGroup.html" class="btn btn-primary btn-sm"> <i
                    class="icon-plus"></i>答辩小组评语条目
            </a>

            <span class="label label-info">论文题目</span>
            <a href="<%=basePath%>evaluate/setPaperRemarkForTutor.html" class="btn btn-primary btn-sm"> <i
                    class="icon-plus"></i>指导教师评语条目
            </a>
            <a href="<%=basePath%>evaluate/setPaperRemarkForReviewer.html" class="btn btn-primary btn-sm"> <i
                    class="icon-plus"></i>评阅人评语条目
            </a>
            <a href="<%=basePath%>evaluate/setPaperRemarkTemplateForGroup.html" class="btn btn-primary btn-sm"> <i
                    class="icon-plus"></i>答辩小组评语条目
            </a>
        </div>
    </div>
    <br>

    <div class="row-fluid">
        <table
                class="table table-striped table-bordered table-hover datatable">
            <thead>
            <tr>
                <th>评语名称</th>
                <th>设置用户</th>
                <th>类型</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${remarkTemplateSize>0}">
                    <c:forEach items="${remarkTemplateList}" var="remarkTemplate">
                        <tr id="remarkTemplateTr${remarkTemplate.id}">
                            <td>${remarkTemplate.title}</td>
                            <td>${remarkTemplate.builder.name}</td>
                            <td>${remarkTemplate.category}</td>
                            <td>
                                <a href="<%=basePath%>evaluate/viewRemarkTemplate.html?templateId=${remarkTemplate.id}"
                                   data-toggle="modal"
                                   data-target="#viewRemarkTemplate" class="btn btn-default btn-xs"><i class="icon-coffee"></i>查看</a>
                                <button class="btn btn-danger btn-xs" onclick="delRemarkTemplate(${remarkTemplate.id})"><i
                                        class="icon-remove"></i> 删除
                                </button>
                                <a class="btn btn-warning btn-xs"
                                   href="<%=basePath%>evaluate/editRemarkTemplate.html?editId=${remarkTemplate.id}"><i
                                        class="icon-edit"></i>修改</a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <%-- <c:otherwise>
                    <div class="alert alert-warning alert-dismissable" role="alert">
                        <button class="close" type="button" data-dismiss="alert">&times;</button>
                        没有可以显示的模版信息
                    </div>
                </c:otherwise> --%>
            </c:choose>
            </tbody>
        </table>
        <c:choose>
        <c:when test="${remarkTemplateSize<=0}">
        <div class="alert alert-warning alert-dismissable" role="alert">
                        <button class="close" type="button" data-dismiss="alert">&times;</button>
                        没有可以显示的模版信息
                    </div>
        </c:when>
        </c:choose>
    </div>
    <div class="row">
        <%@include file="/WEB-INF/jsps/page/pageBar.jsp" %>
    </div>

    <%--如果需要在模态框中加载另一个jsp，则需要书写以下的div--%>
    <div class="modal fade" id="viewRemarkTemplate" tabindex="-1" role="dialog"
         aria-hidden="true" aria-labelledby="modelOpeningReportTime">
        <div class="modal-dialog">
            <div class="modal-content">
                <%--此处会填充另外一个jsp的内容--%>
            </div>
        </div>
    </div>
</div>