<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%-- <%@ taglib uri="http://www.techjoy.com/tag/display" prefix="disp"%> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/security/tags"
           prefix="sec" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<head>
    <link type="text/css"
          href="<%=basePath%>/bootstrap/css/bootstrap.min.css" rel="stylesheet"
          media="screen">
    <link href="<%=basePath%>/date/css/bootstrap-datetimepicker.min.css"
          rel="stylesheet" media="screen">

    <!--glyphicons 图标  font-awesome    -->
    <link type="text/css"
          href="<%=basePath%>/bootstrap/fonts/glyphicons-halflings-regular.eot"
          rel="stylesheet"/>
    <link type="text/css"
          href="<%=basePath%>/bootstrap/fonts/glyphicons-halflings-regular.ttf"
          rel="stylesheet"/>
    <link type="text/css"
          href="<%=basePath%>/bootstrap/fonts/glyphicons-halflings-regular.woff"
          rel="stylesheet"/>
    <link type="text/css"
          href="<%=basePath%>/font-awesome/css/font-awesome.min.css"
          rel="stylesheet"/>
    <!--  时间范围选择器-->
    <%-- <link rel="stylesheet" type="text/css" media="all"
        href="<%=basePath%>dateTime/daterangepicker-bs3.css" /> --%>
    <!--icheck  -->
    <link type="text/css" href="<%=basePath%>/iCheck/blue.css"
          rel="stylesheet"/>
    <link type="text/css" href="<%=basePath%>/iCheck/aero.css"
          rel="stylesheet"/>
    <link type="text/css" href="<%=basePath%>/css/xcConfirm.css" rel="stylesheet">

    <link type="text/css" href="<%=basePath%>/css/index.css" rel="stylesheet"/>


    <script type="text/javascript"
            src="<%=basePath%>/jquery/jquery-1.9.1.min.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=basePath%>/static/easyui/jquery-ui.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="<%=basePath%>/static/easyui/jquery.dialog.js" charset="utf-8"></script>
    <script type="text/javascript"
            src="<%=basePath%>/bootstrap/js/bootstrap.min.js"></script>
    <!--  datetimepicker-->
    <script type="text/javascript"
            src="<%=basePath%>/date/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
    <script type="text/javascript"
            src="<%=basePath%>/date/js/locales/bootstrap-datetimepicker.fr.js"
            charset="UTF-8"></script>
    <script type="text/javascript"
            src="<%=basePath%>/date/js/locales/bootstrap-datetimepicker.zh-CN.js"
            charset="UTF-8"></script>
    <!--daterangepicker  -->
    <%-- <script type="text/javascript" src="<%=basePath%>dateTime/moment.js"></script>
    <script type="text/javascript"src="<%=basePath%>dateTime/daterangepicker.js"></script> --%>


    <script type="text/javascript" src="<%=basePath%>/js/myAlert.js"></script>
    <script type="text/javascript" src="<%=basePath%>/iCheck/icheck.min.js"></script>
    <%--在普通的页面中需要使用的级联函数--%>
    <script type="text/javascript" src="<%=basePath%>/js/jsoninfo.js"></script>
    <%--在模态框中需要使用的级联函数--%>
    <script type="text/javascript" src="<%=basePath%>/js/modaljsoninfo.js"></script>
    <%--自定义的弹出框--%>
    <script type="text/javascript" src="<%=basePath%>/js/xcConfirm.js"></script>

    <%--引入easyui--%>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/static/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/static/easyui/themes/icon.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/css/docs.css"/>
    <script type="text/javascript" src="<%=basePath%>/static/easyui/jquery.easyui.min.js"></script>
    <%--<script type="text/javascript" src="<%=basePath%>/static/extJs.js" charset="utf-8"></script>--%>

</head>