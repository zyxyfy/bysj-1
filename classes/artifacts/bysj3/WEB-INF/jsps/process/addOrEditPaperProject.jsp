<%--
  Created by IntelliJ IDEA.
  User: zhan
  Date: 2016/3/7
  Time: 15:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>
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
