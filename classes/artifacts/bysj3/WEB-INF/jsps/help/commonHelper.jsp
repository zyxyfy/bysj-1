<%--
  Created by IntelliJ IDEA.
  User: zhan
  Date: 2016/4/22
  Time: 16:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>


在项目中需要经常用到的一些链接


1.查看论文的详细信息
<a class="btn btn-default" href="<%=basePath%>process/showDetail.html?graduateProjectId=${projectId}">详细信息</a>


2.任务书下载
<a class="btn btn-default" href="<%=basePath%>tutor/downLoadTaskDoc.html?taskDocId=${taskDocId}">下载任务书</a>


3.开题报告下载
<a class="btn btn-default" href="<%=basePath%>downloadOpenningReport.html?openningReportId=${reportId}">下载开题报告</a>


4.终稿下载
<a class="btn btn-default" href="<%=basePath%>student/download/finalDraft.html?graduateProjectId=${projectId}">下载终稿</a>


5.查看指导记录表


6.查看指导老师评审表
<a class="btn btn-default" href="<%=basePath%>viewEvaluate.html?projectId=${graduateProjectEvaluate.id}">查看指导老师评审表</a>


7.查看评阅人评审表
<a class="btn btn-default" href="<%=basePath%>viewReviewerEvaluate.html?projectId=${graduateProjectEvaluate.id}">查看评阅人评审表</a>

8.查看答辩小组意见表
<a class="btn btn-default" href="<%=basePath%>viewGroupEvaluate.html?projectId=${graduateProject.id}">查看答辩小组评审表</a>

9.显示课题详情
<a class="btn btn-primary btn-xs" type="button"
   href="<%=basePath%>process/showDetail.html?graduateProjectId=${project.id}" data-toggle="modal"
   data-target="#viewProjectModal"><i class="icon-coffee"></i> 显示详情</a>
显示课题需要一个模态框，需要在显示详情的页面中加入如下代码
<div id="viewProjectModal" class="modal fade in" aria-hidden="true" aria-labelledby="myViewLabelModal"
     role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <%--content中的内容由相应的jsp自动填充--%>
            <%--查看课题细节的模态框--%>
        </div>
    </div>
</div>

10.查看工作进程表

