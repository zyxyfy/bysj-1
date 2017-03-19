<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsps/includeURL.jsp" %>
<script type="text/javascript">
	function deleteOpenningReport(){
		//var confirmDelete=window.confirm("确认删除？");
        window.wxc.xcConfirm("确认删除？","confirm",{
            onOk: function(){
                $.ajax({
                    url:'openningReport/deleteOpenningReport.html',
                    type:'GET',
                    dataType:'json',
                    success:function(data){
                        window.location='student/uploadOpenningReport.html';
                    },
                    error:function(){
                        myAlert("删除失败,请稍后再试");
                    }
                });
            }});
		/*if(confirmDelete){
         $.ajax({
         url:'openningReport/deleteOpenningReport.html',
         type:'GET',
         dataType:'json',
         success:function(data){
         window.location='student/uploadOpenningReport.html';
         },
         error:function(){
         myAlert("网络错误，删除失败");
         }
         });
         }*/
	}
</script>
<div class="container-fluid" style="width: 100%">
    <div class="row-fluid">
        <ul class="breadcrumb">
            <li>毕业设计流程</li>
            <li>上传开题报告<span class="divider">/</span>
            </li>
        </ul>
    </div>
    <div class="row-fluid">
        <div class="col-md-1">
            <label>上传开题报告:</label>
        </div>
        <script type="text/javascript">
            function confirmFile() {
                var updateFile = $("#openningReportFile").val();
                var excuName = updateFile.substring(updateFile.indexOf(".") + 1, updateFile.length);
                if (excuName == "doc" || excuName == "docx") {
                    window.wxc.xcConfirm("确认提交？", "confirm", {
                        onOk: function () {
                            $("#checkvalue").submit();
                        }
                    })
                } else {
                    myAlert("请选择word格式的文件");
                    return false;
                }
            }
        </script>
        <c:if test="${paperProject.openningReport.url==null}">
            <c:url value="/openningReport/openningReportuploaded.html" var="uploadOpenningReport"/>
            <form action="${uploadOpenningReport}" class="form-inline" id="checkvalue"
                  enctype="multipart/form-data" method="post">
                <div class="form-group">
                    <input type="hidden" name="paperProjectId" value="${paperProject.id}">
                    <input class="form-control" type="file" id="openningReportFile" name="openningReportFile" required>
                    <button type="button" onclick="confirmFile()" class="btn btn-default btn-sm">上传</button>
                </div>
            </form>
            <h4><font color="red"><span>上传既送审，驳回前不能修改</span></font></h4>
        </c:if>
    </div>
    <div class="row">
        <table
                class="table table-striped table-bordered table-hover datatable">
            <thead>
            <tr>
                <th>题目名称</th>
                <th>老师姓名</th>
                <th>指导老师审核状态</th>
                <th>教研室审核状态</th>
                <th>操作</th>
                <th>课题详情</th>

            </tr>
            </thead>
            <tbody>
            <tr>
                <c:choose>
                    <c:when test="${empty paperProject.subTitle}">
                        <td>${paperProject.title}</td>
                    </c:when>
                    <c:otherwise>
                        <td>${paperProject.title}——${paperProject.subTitle}</td>
                    </c:otherwise>
                </c:choose>
                <td>${paperProject.proposer.name}</td>
                <td><c:choose>
                    <c:when
                            test="${paperProject.openningReport.submittedByStudent==true}">
                        <c:if test="${paperProject.openningReport.auditByTutor==null}">审核中，请耐心等待</c:if>
                        <c:if test="${paperProject.openningReport.auditByTutor.approve==true}">通过</c:if>
                        <c:if test="${paperProject.openningReport.auditByTutor.approve==false}">驳回</c:if>
                    </c:when>
                    <c:otherwise>
                        未审核
                    </c:otherwise>
                </c:choose></td>
                <td><c:choose>
                    <c:when
                            test="${paperProject.openningReport.submittedByStudent==true&&paperProject.openningReport.auditByTutor.approve==true}">
                        <c:if
                                test="${paperProject.openningReport.auditByDepartmentDirector==null}">审核中，请耐心等待</c:if>
                        <c:if
                                test="${paperProject.openningReport.auditByDepartmentDirector.approve==true}">通过</c:if>
                        <c:if
                                test="${paperProject.openningReport.auditByDepartmentDirector.approve==false}">驳回</c:if>
                    </c:when>
                    <c:otherwise>
                        未审核
                    </c:otherwise>
                </c:choose></td>
                <td><c:choose>
                    <c:when test="${paperProject.openningReport.url==null }">
                        未上传
                    </c:when>
                    <c:otherwise>
                        <a class="btn btn-default btn-sm"
                           href="<c:url value="/student/openningReport/downloadOpenningReport.html?paperProjectId=${paperProject.id}"/>">下载</a>
                        <c:if
                                test="${paperProject.openningReport.auditByTutor.approve==false}">
                            <%-- <a class="btn btn-default btn-sm"
                               href="<c:url value="openningReport/deleteOpenningReport.html"/>">删除</a> --%>
                               <a class="btn btn-default btn-sm" onclick="deleteOpenningReport()">删除</a>
                        </c:if>
                    </c:otherwise>
                </c:choose></td>
                <td><a class="btn btn-primary btn-xs"
                       href="/bysj3/process/showDetail.html?graduateProjectId=${paperProject.id}" type="button"
                       data-toggle="modal" data-target="#showDetail">显示细节 </a>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<div class="modal fade" id="showDetail" tabindex="-1" role="dialog"
     aria-hidden="true" aria-labelledby="modelOpeningReportTime">
    <div class="modal-dialog">
        <div class="modal-content"></div>
    </div>
</div>