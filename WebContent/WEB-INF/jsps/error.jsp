<%--
  Created by IntelliJ IDEA.
  User: zhan
  Date: 2017/3/21
  Time: 17:26:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" %>
<%@include file="includeURL.jsp"%>
<html>
<head>
    <title>错误页面</title>
</head>
<body>
<div style="margin-top: 20px;margin-left: 20px">
    <span style="color: grey;font-size: xx-large">哎呀，出错啦！</span>
    <hr/>
    <span style="color: grey;">错误信息：${e}</span>
    <hr/>
    <span style="color: grey;">
    详细信息：
    <c:forEach items="${detail}" var="info">
        ${info}<br/>
    </c:forEach>
</span>
</div>

</body>
</html>
