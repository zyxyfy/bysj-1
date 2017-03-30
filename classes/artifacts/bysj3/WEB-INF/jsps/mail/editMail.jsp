<%--
  Created by IntelliJ IDEA.
  User: 张战
  Date: 2016/2/16
  Time: 22:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/jsps/includeURL.jsp" %>

<div class="panel panel-primary">
    <div class="panel-heading">
        <h5 class="panel-title">编辑邮件</h5>
    </div>
    <div class="panel-body">
        <form:form action="${actionUrl}" enctype="multipart/form-data" commandName="mail">
        <input type="hidden" value="${mailIdToEdit}" name="mailIdToEdit"/>
            <dl>
                <dt>标题</dt>
                <dd>
                    <form:input type="text" path="title"/>
                </dd>
                <dt>内容</dt>
                <dd>
                    <form:textarea path="content" rows="5" cols="63"/>
                </dd>
                <dt>附件</dt>
                <dd>
                    <input type="file" class="form-control" name="mailAttachment">
                </dd>
            </dl>
            <input class="btn btn-default" type="submit" value="修改">
        </form:form>
    </div>
</div>
