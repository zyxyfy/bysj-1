<%--
  Created by IntelliJ IDEA.
  User: zhan
  Date: 2016/3/14
  Time: 13:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>

<%--如果表格中要列出数据并没有内容，可以使用以下的方法--%>
<div class="alert alert-warning alert-dismissable" role="alert">
    <button class="close" type="button" data-dismiss="alert">&times;</button>
    在这里书写需要显示的信息
</div>


<!-- 如果点击某个按键要弹出一个模态框，则要添加两个属性 -->
<a class="btn btn-primary btn-sm"
           data-toggle="modal" data-target="#targetId"> <i
                class="icon-plus"></i> 按键的名称
        </a>
        
<!-- 如果点击某个按钮，需要先经过后台，然后再弹出模态框，则需要添加一个href属性 -->
<a href="example.html" class="btn btn-primary btn-sm"
           data-toggle="modal" data-target="#addTeacher"> <i
                class="icon-plus"></i> 添加新教師
        </a>


<%--在当前页面中加载别一个页面的模态框--%>
<div class="modal fade" id="editExcel" tabindex="-1" role="dialog"
     aria-hidden="true" aria-labelledby="modelOpeningReportTime">
    <div class="modal-dialog">
        <div class="modal-content">
        </div>
    </div>
</div>

<%--如果需要将jsp中的内容在模态中显示，则jsp应该以如下的格式书写--%>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal"
            aria-hidden="true">×
    </button>
    <h4 class="modal-title" id="myModalLabel">
        模态框（Modal）标题
    </h4>
</div>
<div class="modal-body">
    显示内容的主体部分
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-default"
            data-dismiss="modal">关闭
    </button>
    <button type="button" class="btn btn-primary">
        提交更改
    </button>
</div>


