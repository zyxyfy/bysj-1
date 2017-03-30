<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>
<!DOCTYPE html>
<html lang="en" class="no-js">
<script type="text/javascript">
    function changeCode() {
        var changImg = $("#checkImage");
        changImg.hide();
        changImg.prop("src", "<%=basePath%>kaptcha/getKaptchaImage.html?id=" + Math.random()).fadeIn(500);
        event.cancelBubble = true;
    }
</script>
<head>
    <meta charset="utf-8">
    <title>毕业论文管理系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- CSS -->
    <link rel="stylesheet" href="css/reset.css">
    <link rel="stylesheet" href="css/supersized.css">
    <link rel="stylesheet" href="css/style.css">

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

</head>

<body oncontextmenu="return false">

<div class="alert" style="display: none">
    <h2>消息</h2>

    <div class="alert_con">
        <p id="ts"></p>

        <p style="line-height: 70px">
            <a class="btn">确定</a>
        </p>
    </div>
</div>


<div id="tbl_header" class="tbl_header" style="top: 60px; left: 50px;">
    <div id="tbl_header_logo" class="tbl_header_logo">
        <img src="<%=basePath%>img/logo4.png">
    </div>
    <div id="tbl_header_seu" class="tbl_header_seu">
        <span class="tbl_header_mhr">毕业论文管理</span>
    </div>
</div>
<div class="login"
     style="background: url(<%=basePath%>img/dlbj.png) no-repeat center;">
    <%-- <img style="top: 180px;" src="<%=basePath%>img/dlbj.png" height="420px"
        width="400px"> --%>
    <h1>Login</h1>
    <c:url var="action" value="login.action"/>
    <%--<p id="errorInfo"
   style="margin-top: 219px; margin-left: 640px; color: red">${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}&nbsp;${error}</p>--%>

    <form action="${action}" method="post">
        <div style="color: red">
            <%--${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}--%>
            <%--<br>--%>
            ${error}
        </div>
        <div>
            <input type="text" name="username" class="username"
                   placeholder="Username" autocomplete="off"/>
        </div>
        <div>
            <input type="password" name="password" class="password"
                   placeholder="Password" oncontextmenu="return false"
                   onpaste="return false"/>
        </div>
        <div>
            <input name="identifyingCode" type="text" maxlength="4"
                   style="width: 100px" id="checkNumber" autocomplete="off" placeholder="验证码"/> <span
                id="checkImageSpan"> <img onclick="changeCode()"
                                          src="<%=basePath%>kaptcha/getKaptchaImage.html" id="checkImage"/></span><a
                href="#" onclick="changeCode()">换一张</a>
        </div>
        <input id="submit" type="submit" value="登录" class="btn btn-success"/>
    </form>
</div>
<div id="div_footer" class="navbar-fixed-bottom">
    <p>技术支持：信管开发团队&nbsp;&nbsp;版权所有&nbsp;&nbsp;&nbsp;&nbsp;技术支持邮箱：80808@seu.edu.cn&nbsp;&nbsp;&nbsp;&nbsp;109000473@seu.edu.cn</p>
</div>

<!-- Javascript -->
<script src="http://apps.bdimg.com/libs/jquery/1.6.4/jquery.min.js"
        type="text/javascript"></script>
<script src="js/supersized.3.2.7.min.js"></script>
<script src="js/supersized-init.js"></script>
<script>
    $(".btn").click(function () {
        is_hide();
    })
    var u = $("input[name=username]");
    var p = $("input[name=password]");
    $("#submit").live('click', function () {
        if (u.val() == '' || p.val() == '') {
            $("#ts").html("用户名或密码不能为空~");
            is_show();
            return false;
        } else {
            var reg = /^[0-9A-Za-z]+$/;
            if (!reg.exec(u.val())) {
                $("#ts").html("用户名错误");
                is_show();
                return false;
            }
        }
    });
    window.onload = function () {
        $(".connect p").eq(0).animate({
            "left": "0%"
        }, 600);
        $(".connect p").eq(1).animate({
            "left": "0%"
        }, 400);
    }
    function is_hide() {
        $(".alert").animate({
            "top": "-40%"
        }, 300)
    }
    function is_show() {
        $(".alert").show().animate({
            "top": "45%"
        }, 300)
    }
</script>
</body>
</html>

