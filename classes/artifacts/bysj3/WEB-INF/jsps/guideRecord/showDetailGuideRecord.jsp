<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsps/includeURL.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
    function tutorPassForApproval(guideRecordId) {
    	 window.wxc.xcConfirm("确认通过？","confirm",{
 			onOk: function(){
 				$.ajax({
 	                url: '/bysj3/tutor/tutorPassForApproval.html',
 	                type: 'GET',
 	                dateType: 'json',
 	                data: {"guideRecordId": guideRecordId},
 	                success: function (data) {
 	                    $("#status" + guideRecordId).html("<p>通过</p>");
 	                    $("#goRecord" + guideRecordId).hide();
 	                    $("#backRecord" + guideRecordId).show();
 	                    myAlert("修改成功");
 	                    return true;
 	                },
 	                error: function (data) {
 	                    myAlert("网络故障，修改失败");
 	                    return false;
 	                }

 	            });	
 			}});
        //var confirmApprove = window.confirm("确认通过？");
        /* if (confirmApprove) {
            $.ajax({
                url: '/bysj3/tutor/tutorPassForApproval.html',
                type: 'GET',
                dateType: 'json',
                data: {"guideRecordId": guideRecordId},
                success: function (data) {
                    $("#status" + guideRecordId).html("<p>通过</p>");
                    $("#goRecord" + guideRecordId).hide();
                    $("#backRecord" + guideRecordId).show();
                    myAlert("修改成功");
                    return true;
                },
                error: function (data) {
                    myAlert("网络故障，修改失败");
                    return false;
                }

            });
        } */
    }
    function tutorRejectForApproval(guideRecordId) {
    	window.wxc.xcConfirm("确认驳回？","confirm",{
 			onOk: function(){
 				$.ajax({
 	                url: '/bysj3/tutor/tutorRejectForApproval.html',
 	                type: 'GET',
 	                dateType: 'json',
 	                data: {"guideRecordId": guideRecordId},
 	                success: function (data) {
 	                    $("#status" + guideRecordId).html("<p>驳回</p>");
 	                    $("#backRecord" + guideRecordId).hide();
 	                    $("#goRecord" + guideRecordId).show();
 	                    myAlert("修改成功");
 	                    return true;
 	                },
 	                error: function (data) {
 	                    myAlert("网络故障，修改失败");
 	                    return false;
 	                }

 	            });
 			}});
        //var confirmReject = window.confirm("确认驳回？");
        /* if (confirmReject) {
            $.ajax({
                url: '/bysj3/tutor/tutorRejectForApproval.html',
                type: 'GET',
                dateType: 'json',
                data: {"guideRecordId": guideRecordId},
                success: function (data) {
                    $("#status" + guideRecordId).html("<p>驳回</p>");
                    $("#backRecord" + guideRecordId).hide();
                    $("#goRecord" + guideRecordId).show();
                    myAlert("修改成功");
                    return true;
                },
                error: function (data) {
                    myAlert("网络故障，修改失败");
                    return false;
                }

            });
        } */
    }
</script>
<form action="<%=basePath%>tutor/approveGuideRecord.html" method="get">
    <div class="modal-header">
        <h4 class="modal-title">审核</h4>
    </div>
    <div class="modal-body">
        <div class="row-fluid">
            <table
                    class="table table-striped table-bordered table-hover datatable">
                <thead>
                <tr>
                    <th>审核状态</th>
                    <th>指导时间</th>
                    <th>指导地点</th>
                    <th>指导内容</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${guideRecords}" var="guideRecord">
                    <tr class="guideRecordRow${guideRecord.id}">
                        <td id="status${guideRecord.id}">
                            <c:choose>
                                <c:when test="${guideRecord.auditedByTutor.approve==true}">通过</c:when>
                                <c:when test="${guideRecord.auditedByTutor.approve==false}">未通过</c:when>
                                <c:otherwise>
                                    未审核
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td><fmt:formatDate value="${guideRecord.time.time}" type="date" dateStyle="long"/></td>
                        <td>${guideRecord.classRoom.description}</td>
                        <td>${guideRecord.description}</td>
                        <td>
                            <c:choose>
                                <c:when test="${guideRecord.auditedByTutor.approve==true}">
                                    <div id="backRecord${guideRecord.id}">
                                        <a class="btn btn-danger btn-xs"
                                           onclick="tutorRejectForApproval(${guideRecord.id})">驳回</a>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div id="goRecord${guideRecord.id}">
                                        <a class="btn btn-success btn-xs"
                                           onclick="tutorPassForApproval(${guideRecord.id})">通过</a>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <
    <div class="modal-footer">
        <button type="submit" class="btn btn-default">关闭
        </button>
</div>
</form>