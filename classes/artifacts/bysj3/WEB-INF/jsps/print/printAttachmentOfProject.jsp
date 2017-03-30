<%@ page import="com.newview.bysj.domain.GraduateProject" %>
<%@ page import="com.newview.bysj.domain.PaperProject" %><%--
  Created by IntelliJ IDEA.
  User: 张战
  Date: 2016/3/1
  Time: 14:38
  To change this template use File | Settings | File Templates.
--%>
<%--
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsps/includeURL.jsp"%>--%>
<%--
<div>
    <hr>
    用于获取我的课题
    <form action="/bysj3/getGraduateProjectByTutorId.json" method="post">
        <input type="text" name="id" id="tutorId1"/> <input
            type="submit" value="获取"/>
    </form>
    <hr>
    获取通知
    <form action="/bysj3/mail.json" method="post">
        <input id="tutorId" name="id" placeholder="请输入用户的id"> <input
            type="submit" value="提交">
    </form>

    <hr>
    获取我的学生
    <form action="/bysj3/myStudent.json" method="post">
        <input name="id" placeholder="请输入用户的id"> <input
            type="submit" value="提交">
    </form>
    <hr>
    获取当前用户所在的答辩小组
    <form action="/bysj3/getReplyGroupByTutor.json" method="post">
        <input name="id" placeholder="请输入用户的id"> <input
            type="submit" value="提交">
    </form>
    <hr>
</div>

--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsps/includeURL.jsp" %>

<div class="container-fluid" style="width: 100%">
    <div class="row-fluid">
        <ul class="breadcrumb">
            <li>评审流程</li>
            <li>打印论文附件<span class="divider">/</span>
            </li>
        </ul>
    </div>

    <sec:authorize ifAllGranted="ROLE_TUTOR">
        <div class="row">
                <%--打印论文附件表中根据论文的类型进行筛选--%>
            <div class="col-md-3">
                <a class="btn btn-primary btn-xs" href="<%=basePath%>print/printAttachmentOfProject.html"><i
                        class="icon-desktop"></i>全部题目 </a>
                <a class="btn btn-primary btn-xs" href="<%=basePath%>print/printAttachmentOfProject.html?category=论文题目"><i
                        class="icon-desktop"></i>论文题目 </a>
                <a class="btn btn-primary btn-xs" href="<%=basePath%>print/printAttachmentOfProject.html?category=设计题目"><i
                        class="icon-desktop"></i>设计题目 </a>
            </div>
            <div class="col-md-6">
                <form class="form-inline" role="form" action="${actionUrl}">
                    <div class="form-group ">
                        <label for="title">课题名称</label> <input type="text" name="title" value="${title}"
                                                               class="form-control " id="title">
                    </div>
                    <button class="btn btn-default" type="submit"><i class="icon-search"></i>查询</button>
                </form>
            </div>
        </div>
    </sec:authorize>
    <br/>
    <span style="color: grey">提示：如开题报告点击下载出现空白页面，说明此学生未上传开题报告，返回即可</span>
    <br/>
    <%--<div class="row">
        &lt;%&ndash;如果弹出的模态框需要获取后台传过来的数据，即先请求后台，再将请求到的数据放在jsp中进行渲染，则需要书写以下代码&ndash;%&gt;
        &lt;%&ndash;在<a>标签中添加href属性，并添加和模态框相关的属性，则点击此按钮时会先请求后台，然后后台的数据将会返回到某一个jsp页面进行渲染。后台返回的那个jsp页面将会添加到此jsp页面中id为addTeacher的模态框中的class为modal-content的标签中&ndash;%&gt;
        <a href="employeeAdd.html" class="btn btn-primary btn-sm"
           data-toggle="modal" data-target="#addTeacher"> <i
                class="icon-plus"></i> 添加新教師
        </a>


        &lt;%&ndash;如果需要在本页面中弹出一个模态框，则需要书写以下代码&ndash;%&gt;
        <button class="btn btn-primary  btn-sm" type="button"
                data-toggle="modal" data-target="#editExcel">
            导入教師Excel <i class="icon-external-link"></i>
        </button>
        &lt;%&ndash;下面容器是一个模态框&ndash;%&gt;
        &lt;%&ndash;<div class="modal fade" id="editExcel" tabindex="-1" role="dialog"
             aria-hidden="true" aria-labelledby="modelOpeningReportTime">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="upLoadEmployee.html" method="post">
                        &lt;%&ndash;在这个项目中，一般都需要将模态框放入到form表单中，因为大部分模态框提交后需要请求后台进行处理&ndash;%&gt;
                        &lt;%&ndash;这是模态框的头部&ndash;%&gt;
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">
                                <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
                            </button>
                            <h4 class="modal-title">在此处输入模态框的标题</h4>
                        </div>
                        &lt;%&ndash;这是模态框显示内容的主体部分&ndash;%&gt;
                        <div class="modal-body">
                            在此处输入模态框中的内容
                        </div>
                        &lt;%&ndash;这是模态框的尾部&ndash;%&gt;
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                            <button type="submit" class="btn btn-primary">保存</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>&ndash;%&gt;
    </div>--%>
    <br>

    <div class="row-fluid">
        <table
                class="table table-striped table-bordered table-hover datatable">
            <thead>
            <tr>
                <th align="center" width="5%">学号</th>
                <th align="center" width="4%">姓名</th>
                <th align="center" width="4%">成绩</th>
                <th align="center" width="10%">课题</th>
                <th align="center" width="4%">指导教师</th>

                <th align="center" width="4%">任务书</th>
                <th align="center" width="5%">开题报告</th>
                <th align="center" width="4%">终稿</th>

                <th align="center" width="5%">工作进程表</th>
                <th align="center" width="6%">指导记录表</th>
                <sec:authorize ifAnyGranted="ROLE_TUTOR">
                    <th align="center" width="7%">指导老师评审表</th>
                    <th align="center" width="6%">评阅人评审表</th>
                    <th align="center" width="7%">答辩小组意见表</th>
                </sec:authorize>
                <th align="center" width="6%">论文附件封面</th>
                <th align="center" width="5%">详情</th>
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
                    <c:forEach items="${graduateProjectList}" var="project">
                        <tr class="projectRow${project.id}">
                            <td>${project.student.no}</td>
                            <td>${project.student.name}</td>
                            <td>
                                <a class="button" data-toggle="modal" data-target="#addTeacher"
                                   href="<%=basePath%>teacher/viewScore.html?projectId=${project.id}&pageNo=${pageNo}&pageSize=${pageSize}"><span>查看</span></a>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${empty project.subTitle}">
                                        ${project.title}
                                    </c:when>
                                    <c:otherwise>
                                        ${project.title}——${project.subTitle}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${empty(project.mainTutorage.tutor.name)?'无':project.mainTutorage.tutor.name}</td>
                            <!-- 任务书 -->
                            <td>
                                <c:choose>
                                    <c:when test="${project.taskDoc != null}">
                                        <c:choose>
                                            <c:when test="${project.taskDoc.auditByDepartmentDirector.approve || graduateProject.taskDoc.auditByBean.approve}">
                                                <a class="button"
                                                   href="<%=basePath%>tutor/downLoadTaskDoc.html?taskDocId=${project.taskDoc.id}"
                                                   id="taskDocToDownload"><span><i
                                                        class="icon-download"></i> 下载</span></a>
                                            </c:when>
                                            <c:otherwise>
                                                退回
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        无
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <!-- 开题报告 -->
                            <td>
                                <c:choose>
                                    <%--因为graduateProject的属性中没有openningReport，所以目前先用终稿来代替--%>
                                    <c:when test="${fn:contains(project.category, '论文')}">
                                        <c:if test="${project.finalDraft!=null}">
                                            <a class="button"
                                               href="<%=basePath%>downloadOpenningReportByGraduateProjectId.html?projectId=${project.id}"><span><i
                                                    class="icon-download"></i> 下载</span></a>
                                        </c:if>

                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${fn:contains(project.category,'论文')}">
                                                无
                                            </c:when>
                                            <c:otherwise>
                                                无
                                            </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <!-- 终稿 -->
                            <td>
                                <c:choose>
                                    <c:when test="${project.finalDraft != null}">
                                        <a class="button"
                                           href="<%=basePath%>student/download/finalDraft.html?graduateProjectId=${project.id}"><span><i
                                                class="icon-download"></i> 下载</span></a>
                                    </c:when>
                                    <c:otherwise>
                                        无
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <!-- 工作进程表  打印未实现-->
                            <td style="width:2%">
                                <c:choose>
                                    <c:when test="${fn:length(project.schedules)>0}">
                                        <a class="button" align="center"
                                           href="<%=basePath%>student/viewSchedule.html?projectId=${project.id}"
                                           title="查看工作进程表"><span>查看</span></a>
                                    </c:when>
                                    <c:otherwise>
                                        无
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <!-- 指导记录表  打印未实现-->
                            <td>
                                <c:choose>
                                    <c:when test="${fn:length(project.guideRecord)==0}">
                                        <p>无</p>
                                    </c:when>
                                    <c:otherwise>
                                        <%--<a class="button"
                                           href="<%=basePath%>evaluate/chiefTutor/printReport.html?reportId=${project.id}"
                                           target="_blank" title="查看指导记录">
                                            <c:if test="${selectPrint != null}">
                                                &lt;%&ndash;<disp:booleanDisplay value="${selectPrint}" toDisplay="<span>打印</span>,<span>查看</span>"/></a>&ndash;%&gt;
                                                <c:choose>
                                                    <c:when test="${selectPrint}">
                                                        <span><i class="icon-print"></i> 打印</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span>查看</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>
                                            <c:if test="${selectPrint == null}">
                                                <span>查看</span>
                                            </c:if>
                                        </a>--%>
                                        <a href="<%=basePath%>tutor/showDetailsGuideRecord.html?graduateProjectId=${project.id}&noPage=${pageNo}&sizePage=${pageSize}"
                                           data-toggle="modal" data-target="#addTeacher">查看</a>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <sec:authorize ifAnyGranted="ROLE_TUTOR">
                                <!-- 指导老师评审表 -->
                                <td>
                                    <c:choose>
                                        <c:when test="${project.commentByTutor.submittedByTutor}">
                                            <a class="button"
                                            href="<%=basePath%>evaluate/chiefTutor/printReport.html?reportId=${project.id}"
                                            target="_blank">
                                            <c:if test="${selectPrint != null}">

                                                <c:choose>
                                                    <c:when test="${selectPrint}">
                                                        <span><i class="icon-print"></i> 打印</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span>查看</span>
                                                    </c:otherwise>
                                                </c:choose>
                                                </a>
                                            </c:if>
                                            <c:if test="${selectPrint == null}">
                                                <span>查看</span></a>
                                            </c:if>
                                        </c:when>
                                        <c:otherwise>
                                            无
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <!-- 评阅人评审表 -->
                                <td>
                                    <c:choose>
                                        <c:when test="${project.commentByReviewer.submittedByReviewer}">
                                            <a class="button"
                                            href="evaluate/reviewer/getReport.html?projectId=${project.id}"
                                            target="_blank">
                                            <c:if test="${selectPrint != null}">
                                                <%--<disp:booleanDisplay value="${selectPrint}"
                                                                     toDisplay="<span>打印</span>,<span>查看</span>"/></a>--%>
                                                <c:choose>
                                                    <c:when test="${selectPrint}">
                                                        <span><i class="icon-print"></i> 打印</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span>查看</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>
                                            <c:if test="${selectPrint == null}">
                                                <span>查看</span></a>
                                            </c:if>
                                        </c:when>
                                        <c:otherwise>
                                            无
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <!-- 答辩小组意见表 -->
                                <td>
                                    <c:choose>
                                        <c:when test="${project.commentByGroup.submittedByGroup}">
                                            <a class="button" data-toggle="modal" data-target="#addTeacher"
                                               href="<%=basePath%>viewGroupEva.html?projectId=${project.id}&pageNo=${pageNo}&pageSize=${pageSize}"
                                               target="_blank"><span>查看</span></a>
                                        </c:when>
                                        <c:otherwise>
                                            无
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </sec:authorize>
                            <!-- 论文附件封面 -->
                            <td style="width:7%" align="center">
                                <a class="button" href="<%=basePath%>print/showCover.html?id=${project.id}"
                                   target="_blank"><span><i class="icon-print"></i> 打印</span></a>
                            </td>
                            <!--详情  -->
                            <td>
                                <div>
                                    <a class="button"
                                       href="<%=basePath%>process/showDetail.html?graduateProjectId=${project.id}"
                                       data-toggle="modal" data-target="#addTeacher">
                                        <span>细节</span>
                                    </a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>
    <div class="row">
        <%@ include file="/WEB-INF/jsps/page/pageBar.jsp" %>
    </div>

    <%--如果需要在模态框中加载另一个jsp，则需要书写以下的div--%>
    <div class="modal fade" data-backdrop="static" data-keyboard="false" id="addTeacher" tabindex="-1" role="dialog"
         aria-hidden="true" aria-labelledby="modelOpeningReportTime">
        <div class="modal-dialog">
            <div class="modal-content">
                <%--此处会填充另外一个jsp的内容--%>
            </div>
        </div>
    </div>
</div>