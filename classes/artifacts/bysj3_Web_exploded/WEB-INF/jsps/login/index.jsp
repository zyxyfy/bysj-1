<%@ page import="java.util.Map" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="/WEB-INF/jsps/includeURL.jsp" %>

    <title>毕业管理系统</title>
    <script type="text/javascript">
        /*  获取浏览器的高度和宽度，并且为导航栏和右边的页面分配高度*/
        function getContentSize() {
            var wh = document.documentElement.clientHeight;
            var cw = document.documentElement.clientWidth;
            var nw = document.getElementById("navigation").clientWidth;
            var pw = (cw - nw) + "px";
            var eh = 150;
            var oh = 100;
            nw = nw + "px";
            ch = (wh - eh) + "px";
            ph = (wh - oh) + "px";
            document.getElementById("page").style.height = ch;//根据id找到右边的页面page，更改style属性值height
            document.getElementById("navigation").style.height = ph;//根据id找到左边的页面navigation，更改style属性值height
            document.getElementById("footer").style.width = pw;//根据id修改footer的宽度
            document.getElementById("footer").style.marginLeft = nw;//根据id修改footer的左边距
        }

        window.onload = getContentSize;
        window.onresize = getContentSize;

        /*  设置加载页面*/
        //获取浏览器页面可见高度和宽度
        var _PageHeight = document.documentElement.clientHeight, _PageWidth = document.documentElement.clientWidth;
        //计算loading框距离顶部和左部的距离（loading框的宽度为215px，高度为61px）
        var _LoadingTop = _PageHeight > 61 ? (_PageHeight - 61) / 2 : 0, _LoadingLeft = _PageWidth > 215 ? (_PageWidth - 215) / 2
                : 0;
        //在页面未加载完毕之前显示的loading Html自定义内容
        var _LoadingHtml = '<div id="loadingDiv" style="position:absolute;left:0;width:100%;height:'
                + _PageHeight
                + 'px;top:0;background:#f3f8ff;opacity:0.8;filter:alpha(opacity=80);z-index:10000;"><div style="position: absolute; cursor1: wait; left: '
                + _LoadingLeft
                + 'px; top:'
                + _LoadingTop
                + 'px; width: auto; height: 57px; line-height: 57px; padding-left: 50px; padding-right: 5px;  border: 2px solid #95B8E7; color: #696969; font-family:\'Microsoft YaHei\';"><i class="icon-spinner icon-spin"></i> 页面加载中，请等待...</div></div>';
        //呈现loading效果
        document.write(_LoadingHtml);

        //监听加载状态改变
        document.onreadystatechange = completeLoading;

        //加载状态为complete时移除loading效果
        function completeLoading() {
            if (document.readyState == "complete") {
                var loadingMask = document.getElementById('loadingDiv');
                loadingMask.parentNode.removeChild(loadingMask);
            }
        }
    </script>
    <script type="text/javascript">
        function logout() {
            window.wxc.xcConfirm("确认退出登录？", "confirm", {
                onOk: function () {
                    window.location.href = "login.html";
                }
            });
            /*var confirmlog = window.confirm("确认退出登录？");
            if (confirmlog) {
                window.location.href = "login.html";
             }*/
        }

        function checkPassword() {
            var oldpw = $("#oldPassword").val();
            var newpw = $("#newPassword").val();
            var confirmpw = $("#confirmNewPassword").val();
            if (newpw != confirmpw) {
                myAlert("两次输入的新密码不一致");
                return false;
            }
            window.wxc.xcConfirm("确认修改？","confirm",{
    			onOk: function(){
    				$.ajax({
                        url: '/bysj3/updatePassword.html',
                        data: {"oldPassword": oldpw, "newPassword": newpw},
                        type: 'POST',
                        success: function (data) {
                            myAlert(data);
                            $("#myModal").hide(800);
                            location.reload();
                        },
                        error: function () {
                            myAlert("网络故障，请稍后再试");
                            location.reload();
                        }
                    });
    			}});
           /*  var update = window.confirm("确认修改？");
            if (update) {
                $.ajax({
                    url: '/bysj3/updatePassword.html',
                    data: {"oldPassword": oldpw, "newPassword": newpw},
                    type: 'POST',
                    success: function (data) {
                        myAlert(data);
                        $("#myModal").hide(800);
                        return true;
                    },
                    error: function () {
                        myAlert("网络故障，请稍后再试");
                        return false;
                    }
                });
            } */
        }
    </script>


    <script type="text/javascript">
        function onloadTest(urlId) {
            var getUrl = $("#childUrl" + urlId).text();
            //$("#url"+urlId).attr("href",getUrl);
            $.ajax({
                beforeSend: function () {
                    //window.location=getUrl;
                    getContentSize();
                    var _PageHeight = document.documentElement.clientHeight, _PageWidth = document.documentElement.clientWidth;
                    //计算loading框距离顶部和左部的距离（loading框的宽度为215px，高度为61px）
                    var _LoadingTop = _PageHeight > 61 ? (_PageHeight - 61) / 2 : 0, _LoadingLeft = _PageWidth > 215 ? (_PageWidth - 215) / 2
                            : 0;
                    var _LoadingHtml = '<div id="loadingDiv" style="position:absolute;left:0;width:100%;height:'
                            + _PageHeight
                            + 'px;top:0;background:#f3f8ff;opacity:0.8;filter:alpha(opacity=80);z-index:10000;"><div style="position: absolute; cursor1: wait; left: '
                            + _LoadingLeft
                            + 'px; top:'
                            + _LoadingTop
                            + 'px; width: auto; height: 57px; line-height: 57px; padding-left: 50px; padding-right: 5px;  border: 2px solid #95B8E7; color: #696969; font-family:\'Microsoft YaHei\';"><i class="icon-spinner icon-spin"></i> 页面加载中，请等待...</div></div>';
                    $("#onLoadTest").html(_LoadingHtml);
                    $("#onLoadTest").show();

                },
                //发送请求的地址
                url: getUrl,
                //请求完成后的处理
                success: function (data) {
                    $("#demoTest").attr("srcdoc", data);
                    $("#onLoadTest").hide();
                    return true;
                }
            });
        }
    </script>
    
</head>
<body style="background: #FFFFFF">
<div id="onLoadTest" style="display: none"></div>
<div class="container" style="width: 100%">
    <div class="banner "
         style="height: 100px; width:100%;background:url(<%=basePath%>img/banner3.png) no-repeat right #337ab7;">
        <div class="b1"
             style="height: 60px; width:100%; "></div>
        <!--  background:url(<%=basePath%>img/banner2.png) no-repeat center #000066-->
        <div class="b2"
             style="height: 40px; width: 100%; ">
            <!-- <div class="navbar-custom-menu" id="topMeau"> -->
            <div id="topMeau">
               <!--  <ul class="nav navbar-nav"> -->
                <ul class="list-inline">
                    <li><a href="#"><i class="icon-user icon-large"></i>
                        欢迎登录：${user.username},${user.actor.name} </a></li>
                    <li><a data-toggle="modal" data-target="#myModal"> <i class=" icon-lock"></i> 修改密码
                    </a></li>
                  <sec:authorize ifNotGranted="ROLE_STUDENT"> 
                        <li><a href="/bysj3/updateInfo.html" data-toggle="modal" data-target="#updateInfo" > <i
                                class="icon-edit"></i> 修改个人信息
                        </a></li><!-- href="/bysj3/updateInfo.html"  -->
                    </sec:authorize> 
                    <li><a href="#"> <i class="icon-building"></i> 登录次数：${user.loginTime}次
                    </a></li>
                    <li><a onclick="logout()"> <i class="icon-desktop"></i> 退出
                    </a></li>
                </ul>
            </div>
            <!--测试结束  -->
        </div>
    </div>

    <div class="row">
        <div class="col-md-2" id="navigation">
            <div class="panel-group" id="accordion">
                <ul class="nav nav-pills nav-stacked  navbar-default" id="navb">
                    <!-- 全部的导航栏就这一个foreach，,你需要在这里更改样式 -->
                    <c:forEach items="${parentResourceList}" var="parentResource">
                        <li><a data-toggle="collapse" data-parent="#accordion"
                               href="#collapse${parentResource.id }"> <c:choose>
                            <c:when test="${parentResource.id == 1}">
                                <span class="glyphicon glyphicon-bell">${parentResource.description}</span>
                            </c:when>
                            <c:when test="${parentResource.id == 4}">
                                <span class="glyphicon glyphicon-book">${parentResource.description}</span>
                            </c:when>
                            <c:when test="${parentResource.id == 12}">
                                <span class="glyphicon glyphicon-check">${parentResource.description}</span>
                            </c:when>
                            <c:when test="${parentResource.id == 19}">
                                <span class="glyphicon glyphicon-pencil">${parentResource.description}</span>
                            </c:when>
                            <c:when test="${parentResource.id == 27}">
                                <span class="glyphicon glyphicon-file">${parentResource.description}</span>
                            </c:when>
                            <c:when test="${parentResource.id == 44}">
                                <span class="glyphicon glyphicon-user">${parentResource.description}</span>
                            </c:when>
                            <c:when test="${parentResource.id == 33}">
                                <span class="glyphicon glyphicon-list-alt">${parentResource.description}</span>
                            </c:when>
                            <c:when test="${parentResource.id == 53}">
                                <span class="glyphicon glyphicon-eye-open">${parentResource.description}</span>
                            </c:when>
                            <c:when test="${parentResource.id == 40}">
                                <span class="glyphicon glyphicon-list">${parentResource.description}</span>
                            </c:when>
                            <c:when test="${parentResource.id == 51}">
                                <span class="glyphicon glyphicon-stats">${parentResource.description}</span>
                            </c:when>
                        </c:choose> <i class=" icon-angle-left"
                                       style="font-size: 10px; float: right"></i>
                        </a>

                            <div id="collapse${parentResource.id}" style="background: #2c3b41"
                                 class="panel-collapse collapse">
                                <c:forEach items="${childResourceList}" var="childResource">
                                    <c:if test="${childResource.parent == parentResource}">
                                        <div class="panel-body">
                                            <a style="display: none"
                                               id="childUrl${childResource.id}">${childResource.url}</a>
                                                <%-- <a onclick="onloadTest(${childResource.id})"
                                                    id="url${childResource.id}"
                                                    target="navTab" id="pa"
                                                    style="background: #2c3b41;border-left: 3px solid transparent;">
                                                     <i class=" icon-circle-blank "></i>&nbsp&nbsp&nbsp${childResource.description}
                                                 </a>--%>
                                            <a href="${childResource.url}"
                                               id="url${childResource.id}"
                                               target="navTab" id="pa"
                                               style="background: #2c3b41;border-left: 3px solid transparent;">
                                                <i class=" icon-circle-blank "></i>&nbsp&nbsp&nbsp${childResource.description}
                                            </a>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>

        </div>
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog"
             aria-hidden="true" aria-labelledby="modelOpeningReportTime">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="/bysj3/updatePassword.html" method="post" onsubmit="return checkPassword()">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"
                                    aria-hidden="true">×
                            </button>
                            <h4 class="modal-title" id="myModalLabel">
                                修改密码
                            </h4>
                        </div>
                        <div class="modal-body">
                            原密码：<input type="password" id="oldPassword" required class="form-control"
                                       name="oldPassword">
                            新密码：<input type="password" id="newPassword" required class="form-control"
                                       name="newPassword">
                            确认密码：<input type="password" id="confirmNewPassword" required class="form-control"
                                        name="confirmNewPassword">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default"
                                    data-dismiss="modal">关闭
                            </button>
                            <button type="button" onclick="checkPassword()" class="btn btn-primary">
                                提交更改
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="modal fade" id="updateInfo" tabindex="-1" role="dialog"
             aria-hidden="true" aria-labelledby="modelOpeningReportTime">
            <div class="modal-dialog">
                <div class="modal-content">
               

                </div>
            </div>
        </div>
        <div class="col-md-10" id="page">
            <div id="test"></div>
            <iframe name="navTab" height="1200px;" width="100%" frameborder="0" id="demoTest"
                    src="notice/noticesToMe.html"></iframe>
                    <!--frameborder规定是否显示框架周围的边框，0不显示  -->
        </div>
        <div id="footer" style="background: #ecf0f5"
             class="navbar-fixed-bottom">
            <div class="container" align="center">
                <p class="muted credit" style="color: #666666">
                    信管开发团队 and <a href="http://ryanfait.com/sticky-footer/"
                                  style="color: #666666">
                    techjoy，</a>在线人数：<% Map<String, String> online = (Map<String, String>) application.getAttribute("online");
                    Integer size = online.size();%><%=size%>人
                </p>
            </div>
        </div>
    </div>
</div>
</body>
</html>