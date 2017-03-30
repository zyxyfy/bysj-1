<%--
  Created by IntelliJ IDEA.
  User: 张战
  Date: 2016/3/1
  Time: 14:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsps/includeURL.jsp" %>

<script type="text/javascript">
    /*点击显示要执行的函数*/
    function toShow(graduateProjectId) {
        $("#showSelect" + graduateProjectId).hide();
        $("#delReviewer" + graduateProjectId).hide(150);
        $("#showUpdate" + graduateProjectId).hide(300);

        $("#hideSelect" + graduateProjectId).show(300);
        $("#selectReviewTutor" + graduateProjectId).show(600);
        $("#addReviewBtn" + graduateProjectId).show(900);
    }

    /*点击隐藏要执行的函数*/
    function toHide(graduateProjectId) {
        $("#addReviewBtn" + graduateProjectId).hide();
        $("#hideSelect" + graduateProjectId).hide();
        $("#selectReviewTutor" + graduateProjectId).hide(300);
        $("#showSelect" + graduateProjectId).show(300);
        $("#delReviewer" + graduateProjectId).show(600);
        $("#showUpdate" + graduateProjectId).show(900);


    }

    //    添加或修改评阅人的函数
    function addOrEditReviewer(graduateProjectId) {
        /*获取选择的评阅人的id*/
        var reviewerId = $("#selectReviewTutor" + graduateProjectId).val();
        /*获取选择的评阅人的名字*/
        var tutorName = $("#selectReviewTutor" + graduateProjectId).find("option:selected").text();
        //var confirmAdd = window.confirm("确认指定" + tutorName + "为评阅人？");
        window.wxc.xcConfirm("确认指定" + tutorName + "为评阅人？", "confirm", {
            onOk: function () {
                //if (confirmAdd) {
                $.ajax({
                    url: '/bysj3/process/addOrEditReviewer.html',
                    data: {"reviewerId": reviewerId, "graduateProjectId": graduateProjectId},
                    dataType: 'json',
                    type: 'POST',
                    success: function (data) {
                        $("#addOrEditReviewerName" + graduateProjectId).text(tutorName);
                        $("#addReviewBtn" + graduateProjectId).hide();
                        $("#hideSelect" + graduateProjectId).hide();
                        $("#selectReviewTutor" + graduateProjectId).hide();
                        //$("#showSelect" + graduateProjectId).show(1000);
                        /* $("#delReviewer" + graduateProjectId).show();
                         $("#showUpdate" + graduateProjectId).show();*/
                        $("#addOrEdit" + graduateProjectId).html("<button class='btn btn-default btn-xs' id='delReviewer" + graduateProjectId + "' onclick='delReview(" + graduateProjectId + ")'>删除</button> <button class='btn btn-default btn-xs' id='showUpdate" + graduateProjectId + "' onclick='toShow(" + graduateProjectId + ")'>修改</button><button class='btn btn-warning btn-xs' id='hideSelect" + graduateProjectId + "'onclick='toHide(" + graduateProjectId + ")' style='display: none'>隐藏 </button> <select id='selectReviewTutor" + graduateProjectId + "' name='selectReviewTutor'style='display: none'> <option value='0'>--请选择--</option><c:choose><c:when test='${empty tutorList}'> <p style='color: red;'>没有可以选择的评阅人</p></c:when><c:otherwise><c:forEach items='${tutorList}' var='tutor'> <option value='${tutor.id}'>${tutor.name}</option></c:forEach></c:otherwise></c:choose> </select> <button style='display: none' id='addReviewBtn" + graduateProjectId + "'class='btn btn-default btn-xs'onclick='addOrEditReviewer(" + graduateProjectId + ")'>指定 </button>");
                        //toHide(graduateProjectId);
                        myAlert("指定成功");
                        return true;
                    },
                    error: function (data) {
                        myAlert("网络故障，请稍后再试");
                        return false;
                    }
                });
                //}
            }
        });
    }


    function delReview(graduateProjectId) {
        window.wxc.xcConfirm("确认删除指定的评阅人？", "confirm", {
            onOk: function () {
                $.ajax({
                    url: '/bysj3/process/delReviewerByProjectId.html',
                    data: {"graduateProjectId": graduateProjectId},
                    dataType: 'json',
                    type: 'post',
                    success: function () {
//                        将评阅人那一列置为空
                        $("#addOrEditReviewerName" + graduateProjectId).html("<span class='label label-warning'>未指定</span>");
//                        $("#delReviewer"+graduateProjectId).hidden();
//                        $("#showUpdate"+graduateProjectId).hidden();
//                        将指定评阅人那一列变成设置
                        $("#addOrEdit" + graduateProjectId).html("<button class='btn btn-default btn-xs' id='showSelect" + graduateProjectId + "' onclick='toShow(" + graduateProjectId + ")'>设置<button><button class='btn btn-warning btn-xs' id='hideSelect" + graduateProjectId + "'onclick='toHide(" + graduateProjectId + ")' style='display: none'>隐藏 </button> <select id='selectReviewTutor" + graduateProjectId + "' name='selectReviewTutor'style='display: none'> <option value='0'>--请选择--</option><c:choose><c:when test='${empty tutorList}'> <p style='color: red;'>没有可以选择的评阅人</p></c:when><c:otherwise><c:forEach items='${tutorList}' var='tutor'> <option value='${tutor.id}'>${tutor.name}</option></c:forEach></c:otherwise></c:choose> </select> <button style='display: none' id='addReviewBtn" + graduateProjectId + "'class='btn btn-default btn-xs'onclick='addOrEditReviewer(" + graduateProjectId + ")'>指定 </button>");
                        return true;
                    },
                    error: function () {
                        window.wxc.xcConfirm("删除失败，请稍后再试", "error");
                        return false;
                    }
                });
            }
        });
    }
</script>
<div class="container-fluid" style="width: 100%">
    <div class="row-fluid">
        <ul class="breadcrumb">
            <li>评审流程</li>
            <li>评阅人管理<span class="divider">/</span>
            </li>
        </ul>
    </div>
    <div class="row">

        <form action="${actionUrl}" class="form-inline" role="form">
            <div class="form-group">
                <%--题目：
                <input class="form-control" name="title" value="${title}"/>--%>
                评阅人：
                <input class="form-control" name="reviewerName" value="${reviewerName}"/>
                <button class="btn btn-primary btn-sm" type="submit"><i class="icon-search"></i>查询</button>
            </div>

        </form>
        <br>
        <a class="btn btn-primary" href="/bysj3/process/reviewerManage.html"><i class="icon-coffee"></i> 显示所有</a>
        <hr>
        <table class="table table-striped table-bordered table-hover datatable">
            <thead>
            <tr>
                <th>题目</th>
                <th>副标题</th>
                <th>出题者</th>
                <th>类别</th>
                <th>类型</th>
                <th>性质</th>
                <th>终稿状态</th>
                <th>评阅人</th>
                <th>指定评阅人</th>
                <th>详情</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${pageCount>0}">
                    <c:forEach items="${graduateProjectList}" var="graduateProject">
                        <tr>
                            <td>${graduateProject.title}</td>
                            <c:choose>
                                <c:when test="${empty graduateProject.subTitle}">
                                    <td></td>
                                </c:when>
                                <c:otherwise>
                                    <td>${graduateProject.subTitle}</td>
                                </c:otherwise>
                            </c:choose>
                            <td>${graduateProject.proposer.name}</td>
                            <td>${graduateProject.projectType.description}</td>
                            <td>${graduateProject.category}</td>
                            <td>${graduateProject.projectFidelity.description}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${empty graduateProject.finalDraft}">
                                        <span class="label label-warning">无</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="label label-success">有</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td id="addOrEditReviewerName${graduateProject.id}">
                                <c:choose>
                                    <c:when test="${empty graduateProject.reviewer}">
                                        <span class="label label-warning">未指定</span>
                                    </c:when>
                                    <c:otherwise>
                                        ${graduateProject.reviewer.name}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td id="addOrEdit${graduateProject.id}">
                                    <%--对按钮的名称进行判断，如果之前没有评阅人，则显示设置，如果已经选择的评阅人，则显示修改--%>
                                <c:choose>
                                    <c:when test="${empty graduateProject.reviewer}">
                                        <button class="btn btn-default btn-xs" id="showSelect${graduateProject.id}"
                                                onclick="toShow(${graduateProject.id})">设置
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <button class="btn btn-default btn-xs" id="delReviewer${graduateProject.id}"
                                                onclick="delReview(${graduateProject.id})">删除
                                        </button>
                                        <button class="btn btn-default btn-xs" id="showUpdate${graduateProject.id}"
                                                onclick="toShow(${graduateProject.id})">修改
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                                    <%--隐藏按钮默认不显示--%>
                                <button class="btn btn-warning btn-xs" id="hideSelect${graduateProject.id}"
                                        onclick="toHide(${graduateProject.id})" style="display: none">隐藏
                                </button>
                                <select id="selectReviewTutor${graduateProject.id}" name="selectReviewTutor"
                                        style="display: none">
                                    <option value="0">--请选择--</option>
                                    <c:choose>
                                        <c:when test="${empty tutorList}">
                                            <p style="color: red;">没有可以选择的评阅人</p>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach items="${tutorList}" var="tutor">
                                                <option value="${tutor.id}">${tutor.name}</option>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </select>
                                <button style="display: none" id="addReviewBtn${graduateProject.id}"
                                        class="btn btn-default btn-xs"
                                        onclick="addOrEditReviewer(${graduateProject.id})">指定
                                </button>
                            </td>
                            <td>
                                <a href="/bysj3/process/showDetail.html?graduateProjectId=${graduateProject.id}"
                                   data-toggle="modal" data-target="#showProjectDetail"
                                   class="btn btn-primary btn-xs"><i class="icon-coffee"></i>显示详情 </a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-warning alert-dismissable" role="alert">
                        <button class="close" type="button" data-dismiss="alert">&times;</button>
                        没有学生选择课题！
                    </div>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>

        <div id="editProjectModal" class="modal fade in" aria-hidden="true" aria-labelledby="myLabelModal"
             role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <%--content中的内容由相应的jsp自动填充--%>
                    <%--编辑课题的模态框--%>
                </div>
            </div>
        </div>

        <div id="viewProjectModal" class="modal fade in" aria-hidden="true" aria-labelledby="myViewLabelModal"
             role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <%--content中的内容由相应的jsp自动填充--%>
                    <%--查看课题细节的模态框--%>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/jsps/page/pageBar.jsp" %>
<div class="modal fade" id="showProjectDetail" tabindex="-1" role="dialog"
     aria-hidden="true" aria-labelledby="modelOpeningReportTime">
    <div class="modal-dialog">
        <div class="modal-content">
        </div>
    </div>
</div>