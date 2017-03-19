<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsps/includeURL.jsp" %>
<script type="text/javascript">
    function deleteStageAchievement(stageAchievementId) {
       // var confirmDelete = window.confirm("确认删除？");
        window.wxc.xcConfirm("确认删除？","confirm",{
            onOk: function(){
                $.ajax({
                    url: '/bysj3/student/deleteStageAchievement.html',
                    type: 'GET',
                    dateType: 'json',
                    data: {"stageAchievementId": stageAchievementId},
                    success: function (data) {
                        $("#stageAchievementRow" + stageAchievementId).remove();
                        window.location = '/bysj3/student/stageAchievements.html';
                        myAlert("删除成功");
                        return true;
                    },
                    error: function () {
                        myAlert("删除失败,请稍后再试");
                        return false;
                    }
                });
            }});
        /*if (confirmDelete) {
            $.ajax({
                url: '/bysj3/student/deleteStageAchievement.html',
                type: 'GET',
                dateType: 'json',
                data: {"stageAchievementId": stageAchievementId},
                success: function (data) {
                    $("#stageAchievementRow" + stageAchievementId).remove();
                    window.location = '/bysj3/student/stageAchievements.html';
                    myAlert("删除成功");
                    return true;
                },
                error: function () {
                    myAlert("网络错误，删除失败");
                    return false;
                }
            });
        }*/
    }

    function checkFile() {
        var filen = $("#stageAchievementFile").val();
        var fileName = filen.substring(filen.indexOf(".") + 1, filen.length);
        if (fileName == "doc" || fileName == "docx" || fileName == "zip" || fileName == "rar") {

            window.wxc.xcConfirm("确认上传？", "confirm", {
                onOk: function () {
                    $("#uploadAchievement").submit();
                }
            })
        } else {
            myAlert("请选择压缩文件或word类型的文件");
            return false;
        }
    }
</script>
<div class="container-fluid" style="width: 100%">
    <div class="row-fluid">
        <ul class="breadcrumb">
            <li>毕业设计流程</li>
            <li>上传阶段成果<span class="divider">/</span>
            </li>
        </ul>
    </div>
    <c:choose>
        <c:when test="${not empty message}">
            <h3 style="color: red">${message}</h3>
        </c:when>
        <c:otherwise>
            <div class="row-fluid">
                <div class="col-md-1">
                    <label>上传阶段成果:</label>
                </div>
                <c:url value="/student/uploadStageAchievement.html" var="actionUrl"/>
                <form action="${actionUrl}" method="post" enctype="multipart/form-data" id="uploadAchievement">
                    <div class="col-md-3">
                        <div class="form-group">
                            <input type="file" id="stageAchievementFile" class="form-control"
                                   name="stageAchievementFile" required>
                        </div>
                    </div>
                    <div class="col-md-1">
                        <button class="btn btn-default" type="button" onclick="checkFile()">提交</button>
                    </div>
                </form>
            </div>
            <div class="row">
                <table
                        class="table table-striped table-bordered table-hover datatable">
                    <thead>
                    <tr>
                        <th>上传时间</th>
                        <th>教师评语</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${stageAchievements}" var="stageAchievement">
                        <tr id="stageAchievementRow${stageAchievement.id}">
                            <td><fmt:formatDate value="${stageAchievement.issuedDate.time}"
                                                type="date" dateStyle="long"/></td>
                            <c:if test="${stageAchievement.remark!=null}">
                                <td>${stageAchievement.remark}</td>
                            </c:if>
                            <c:if test="${stageAchievement.remark==null}">
                                <td>无</td>
                            </c:if>
                            <td>
                                <c:if test="${stageAchievement.remark == null}">
                                    <%-- <c:url value="deleteStageAchievement.html?stageAchievementId=${stageAchievement.id }" var="deleteUrl"/>
                                    <a class="btn btn-default" href="${deleteUrl }"><span>删除</span></a> --%>
                                    <a class="btn btn-default" onclick="deleteStageAchievement(${stageAchievement.id})">删除</a>
                                </c:if>
                                <a class="btn btn-default"
                                   href="download/stageAchievement.html?stageAchievementId=${stageAchievement.id }"><span>下载</span></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <%@include file="/WEB-INF/jsps/page/pageBar.jsp" %>
        </c:otherwise>
    </c:choose>
</div>