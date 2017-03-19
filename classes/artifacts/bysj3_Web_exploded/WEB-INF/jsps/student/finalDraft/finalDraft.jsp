<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsps/includeURL.jsp" %>
<script type="text/javascript">   
	function deleteFinalDraft(graduateProjectId) {
		var confirmDelete = window.confirm("确认删除？");
		if (confirmDelete) {
			$.ajax({
				url : '/bysj3/student/deleteFinalDraft.html',
				type : 'GET',
				dataType : 'json',
				data : {
					"graduateProjectId" : graduateProjectId
				},
				success : function(data) {
					//alert("删除成功");
					window.location="/bysj3/student/finalDraft.html";
					return true;
				},
				error : function() {
                    alert("删除失败，请稍后再试")
				}
			});
		}
	}

    function uploadFinalDraft() {
        window.wxc.xcConfirm("确认提交？", "confirm", {
            onOk: function () {
                $("#updateFinal").submit();
            }
        });
    }
    /*function u2ploadFinalDraft(){
		var confirmUpload=window.confirm("确认提交？");
		if(confirmUpload){
			return true;
		}
     }*/
</script>
<div class="container-fluid" style="width: 100%">
    <div class="row-fluid">
        <ul class="breadcrumb">
            <li>毕业设计流程</li>
            <li>上传终稿<span class="divider">/</span>
            </li>
        </ul>
    </div>
    <c:choose>
        <c:when test="${not empty message}">
            <h3 style="color: red">${message}</h3>
        </c:when>
        <c:otherwise>
            <div id="uploadFileTest" name="test" class="row-fluid">
                <c:url value="/student/uploadFinalDraft.html" var="actionUrl"/>
                <c:if test="${graduateProject.finalDraft == null}">
                    <div class="col-md-1">
                        <label>上传终稿:</label>
                    </div>
                    <form action="${actionUrl}" method="post" id="updateFinal" enctype="multipart/form-data">
                        <div class="col-md-3">
                            <div class="form-group">
                                <input type="hidden" name="graduateProjectId"
                                       value="${graduateProject.id}">
                                <input type="file" class="form-control"
                                       name="finalDraftFile" id="final" required>
                            </div>
                        </div>
                        <div class="col-md-1">
                            <input type="button" onclick="uploadFinalDraft()" class="btn btn-default" value="提交"/>
                        </div>
                    </form>
                </c:if>
            </div>
            <div class="row">
                <table
                        class="table table-striped table-bordered table-hover datatable">
                    <thead>
                    <tr>
                        <th>题目名称</th>
                        <th>副标题</th>
                        <th>班级</th>
                        <th>学生姓名</th>
                        <th>学号</th>
                        <th>指导老师</th>
                        <th>操作</th>
                        <th>详情</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>${graduateProject.title }</td>
                        <c:choose>
                            <c:when test="${empty graduateProject.subTitle}">
                                <td></td>
                            </c:when>
                            <c:otherwise>
                                <td>${graduateProject.subTitle }</td>
                            </c:otherwise>
                        </c:choose>
                        <td>${graduateProject.student.studentClass.description }</td>
                        <td>${graduateProject.student.name }</td>
                        <td>${graduateProject.student.no }</td>
                        <td>${graduateProject.mainTutorage.tutor.name }</td>
                        <td id="status${graduateProject.id}">
                            <c:if test="${graduateProject.finalDraft != null}">
                                <div>                                
                                    <a class="btn btn-primary btn-xs" href="<%=basePath%>student/download/finalDraft.html?graduateProjectId=${graduateProject.id}">下载</a>
                                    <a class="btn btn-primary btn-xs" onclick="deleteFinalDraft(${graduateProject.id})"><span>删除</span></a>                      
                                </div>
                            </c:if>
                            <c:if test="${graduateProject.finalDraft == null}">
                                未上传
                            </c:if></td>
                        <td><a class="btn btn-primary btn-xs"
                               href="/bysj3/process/showDetail.html?graduateProjectId=${graduateProject.id}"
                               type="button"
                               data-toggle="modal" data-target="#showDetail">显示细节 </a></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>
</div>
<div class="modal fade" id="showDetail" tabindex="-1" role="dialog"
     aria-hidden="true" aria-labelledby="modelOpeningReportTime">
    <div class="modal-dialog">
        <div class="modal-content"></div>
    </div>
</div>