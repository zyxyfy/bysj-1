<%--
  Created by IntelliJ IDEA.
  User: zhan
  Date: 2016/3/31
  Time: 16:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>

<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal"
            aria-hidden="true">×
    </button>
    <h4 class="modal-title" id="myModalLabel">
        查看评语模版,模版名称：（${remarkTemplate.title}）
    </h4>
</div>
<div class="modal-body">
    <form>
        <c:choose>
            <c:when test="${remarkTemplate==null}">
                <div class="alert alert-warning alert-dismissable" role="alert">
                    <button class="close" type="button" data-dismiss="alert">&times;</button>
                    没有可以显示的模版评语选项
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach items="${remarkTemplate.remarkTemplateItems}" var="remarkTemplateItem">
                    <span style="width: auto;font-size:18px;line-height: 20px">${remarkTemplateItem.preText}</span>
                    <select style="width: auto">
                        <c:forEach items="${remarkTemplateItem.remarkTemplateItemsOption}"
                                   var="remarkTemplateItemsOption">
                            <option value="${remarkTemplateItemsOption.no}">${remarkTemplateItemsOption.itemOption}</option>
                        </c:forEach>
                    </select>
                    <span style="width: auto;font-size:18px;line-height: 20px">${remarkTemplateItem.postText}</span>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </form>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-default"
            data-dismiss="modal">关闭
    </button>
</div>
