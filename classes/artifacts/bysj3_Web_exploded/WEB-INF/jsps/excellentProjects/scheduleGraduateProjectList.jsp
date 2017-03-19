<%--
  Created by IntelliJ IDEA.
  User: zhan
  Date: 2016/4/30
  Time: 10:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>
<script type="text/javascript">
    /*点击驳回的函数*/
    function backSchoolExcellent(projectId) {
        window.wxc.xcConfirm("确认驳回？", "confirm", {
            onOk: function () {
                $.ajax({
                    url: '/bysj3/director/backProject.html',
                    data: {"projectId": projectId},
                    dataType: 'json',
                    type: 'post',
                    success: function () {
                        $("#projectRecommended" + projectId).html("无");
                        $("#projectOperation" + projectId).html("<a class='btn btn-primary btn-xs' onclick='passSchoolExcellent(" + projectId + ")'><i class='icon icon-paste'></i>推优</a>");
                        window.wxc.xcConfirm("驳回成功！", "success");
                        return true;
                    },
                    error: function () {
                        window.wxc.xcConfirm("驳回失败！", "error");
                        return false;
                    }
                });
            }
        })
    }

    /*点击推优的函数*/
    function passSchoolExcellent(projectId) {
        window.wxc.xcConfirm("确认推优？", "confirm", {
            onOk: function () {
                $.ajax({
                    url: '/bysj3/director/passProject.html',
                    data: {"projectId": projectId},
                    dataType: 'json',
                    type: 'post',
                    success: function () {
                        $("#projectRecommended" + projectId).html("<label class='label label-success'>优秀</label>");
                        $("#projectOperation" + projectId).html("<a class='btn btn-warning btn-xs' onclick='backSchoolExcellent(" + projectId + ")'><i class='icon icon-backward'></i>驳回</a>");
                        window.wxc.xcConfirm("推优成功！", "success");
                        return true;
                    },
                    error: function () {
                        window.wxc.xcConfirm("推优失败", "error");
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
            <li>论文管理</li>
            <li>毕业论文明细表<span class="divider">/</span>
            </li>
        </ul>
    </div>

    <%-- <div class="row">
        <div class="col-md-8">
            <form class="form-inline" action="${actionUrl}" role="form">
                <div class="form-group ">
                    <label for="name">学生姓名</label> <input type="text"
                                                          class="form-control " name="studentName" id="name" placeholder="请输入学生姓名">
                </div>
                <div class="form-group">
                    <label for="title">学号</label> <input type="text"
                                                         class="form-control " id="title" name="studentNo" placeholder="请输入学号">
                </div>
                <button type="submit" class="btn btn-primary btn-sm " data-toggle="modal"
                        data-target="#addOrEditProjectTimeSpan">查询
                </button>
            </form>
        </div>
    </div> --%>
    <%--为了不使导出按钮与输入框紧密相连，故添加br--%>
    <%-- <br>
    <div class="row">
        <a href="<%=basePath%>director/exportScheduleProject.html" class="btn btn-primary btn-sm"> <i
                class="icon-expand"></i> 导出excel
        </a>
    </div>
    <br> --%>

    <div class="row-fluid">
        <table
                class="table table-striped table-bordered table-hover datatable">
            <thead>
            <tr>
                <th align="center">学号</th>
                <th align="center">姓名</th>
                <th align="center">班级</th>
                <th align="center">专业</th>
                <th align="center">成绩</th>

                <th align="center">毕业设计题目</th>
                <th align="center">类别</th>
                <th align="center">题目类型</th>
                <th align="center">题目性质</th>
                <th align="center">题目来源</th>
                <th align="center">教师姓名</th>

                <th align="center">职称/学位</th>

                <th align="center">校优秀候选状态</th>
                <th align="center">操作</th>
                <th align="center">详情</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${empty graduateProjectList}">
                    <div class="alert alert-warning alert-dismissable" role="alert">
                        <button class="close" type="button" data-dismiss="alert">&times;</button>
                        没有可以显示的数据
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${graduateProjectList}" var="graduateProject">
                        <tr id="project${graduateProject.id}">
                                <%--学号--%>
                            <td>${empty graduateProject.student?"未分配学生":graduateProject.student.no}</td>
                                <%--姓名--%>
                            <td>${empty graduateProject.student?"未分配学生":graduateProject.student.name}</td>
                                <%--班级--%>
                            <td>${empty graduateProject.student?"未分配学生":graduateProject.student.studentClass.description}</td>
                                <%--专业 --%>
                                    <td>${empty graduateProject.student?"未分配学生":graduateProject.student.studentClass.major.description}</td>
                                <%--成绩 --%>
                                    <td>
                                        <c:choose>
                                            <c:when test="${empty graduateProject.commentByTutor.getTotleScoreTutor() || empty graduateProject.commentByReviewer.getTotalScoreReviewer()||graduateProject.commentByGroup.getTotalScoreGroup()==0.0}">
                                                <p title="指导老师、评阅人和答辩组长必须全部评分才能显示分数" style="color: red">未评分</p>
                                            </c:when>
                                            <c:otherwise>
                                                ${graduateProject.getTotalScores()}
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                <%--毕业设计题目--%>
                                    <td>
                                        <c:choose>
                                            <c:when test="${empty graduateProject.subTitle}">
                                                ${graduateProject.title}
                                            </c:when>
                                            <c:otherwise>
                                                ${graduateProject.title}——${graduateProject.subTitle}
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                <%--类别--%>
                            <td>${graduateProject.category}</td>
                                <%--题目类型--%>
                            <td>${graduateProject.projectType.description}</td>
                                <%--题目性质 --%>
                            <td>${graduateProject.projectFidelity.description}</td>
                                <%--题目来源--%>
                            <td>${graduateProject.projectFrom.description}</td>
                                <%--教师姓名 --%>
                            <td>${graduateProject.proposer.name}</td>
                                <%--职称/学位--%>
                            <td>${empty graduateProject.proposer.proTitle?"无":graduateProject.proposer.proTitle.description}/${empty graduateProject.proposer.degree?"无":graduateProject.proposer.degree.description}</td>
                                <%--校优秀候选状态--%>
                                    <td id="projectRecommended${graduateProject.id}">
                                <c:choose>
                                    <c:when test="${graduateProject.recommended==true}">
                                        <label class="label label-success">优秀</label>
                                    </c:when>
                                    <c:otherwise>
                                        无
                                    </c:otherwise>
                                </c:choose>
                            </td>
                                <%--操作--%>
                                    <td id="projectOperation${graduateProject.id}">
                                <c:choose>
                                    <%--如果已推荐校优，则显示驳回按钮--%>
                                    <c:when test="${graduateProject.recommended==true&&graduateProject.schoolExcellentProject==null}">
                                        <a class="btn btn-warning btn-xs"
                                           onclick="backSchoolExcellent(${graduateProject.id})"><i
                                                class="icon icon-backward"></i> 驳回</a>
                                    </c:when>
                                    <%--如果已经指定，则不进行操作--%>
                                    <c:when test="${graduateProject.schoolExcellentProject!=null}">
                                        <label class="label label-success">校优</label>
                                    </c:when>
                                    <%--如果还没有推荐，则显示推优--%>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${graduateProject.getTotalScores()>=90}">
                                                <a class="btn btn-primary btn-xs"
                                                   onclick="passSchoolExcellent(${graduateProject.id})"><i
                                                        class="icon icon-forward"></i> 推优</a>
                                            </c:when>
                                            <c:otherwise>
                                                <span style="color: red;">条件不符合</span>
                                            </c:otherwise>
                                        </c:choose>

                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <a class="btn btn-primary btn-xs"
                                   href="<%=basePath%>process/showDetail.html?graduateProjectId=${graduateProject.id}"
                                   data-toggle="modal" data-target="#viewProjectModal"><i
                                        class="icon-coffee"></i>显示详情</a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>
    <div class="row">
        <%@include file="/WEB-INF/jsps/page/pageBar.jsp" %>
    </div>

    <%--如果需要在模态框中加载另一个jsp，则需要书写以下的div--%>
    <div class="modal fade" id="viewProjectModal" tabindex="-1" role="dialog"
         aria-hidden="true" aria-labelledby="modelOpeningReportTime">
        <div class="modal-dialog">
            <div class="modal-content">
                <%--此处会填充另外一个jsp的内容--%>
            </div>
        </div>
    </div>
</div>
