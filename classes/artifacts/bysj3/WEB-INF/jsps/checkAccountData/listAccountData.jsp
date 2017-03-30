<%--
  Created by IntelliJ IDEA.
  User: zhan
  Date: 2016/4/23
  Time: 9:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsps/includeURL.jsp" %>

<div class="container-fluid" style="width: 100%">
    <div class="row-fluid">
        <ul class="breadcrumb">
            <li>数据统计</li>
            <li>数据统计<span class="divider">/</span>
            </li>
        </ul>
    </div>

    <span style="color: red">点击对应的比例，可以查看未完成的课题汇总</span>
    <%-- <div class="row">
       <div class="col-md-6">
         <form class="form-inline" role="form">
           <div class="form-group ">
             <label for="name">教师姓名</label> <input type="text"
                                                   class="form-control " id="name" placeholder="请输入教师姓名">
           </div>
           <div class="form-group">
             <label for="title">职工号</label> <input type="text"
                                                   class="form-control " id="title" placeholder="请输入职工号">
           </div>
         </form>
       </div>
       <div class="col-md-2">
         <select class="form-control" id="schoolSelect">
           <option value="0">--请选择学院--</option>
           <c:forEach items="${schoolList}" var="school">
             <option value="${school.id }" onclick="getDepartmentBySchoolId(${school.id})"
                     class="selectSchool">${school.description}</option>
           </c:forEach>
         </select>
       </div>

       <div class="col-md-2">
         <select class="form-control " id="departmentSelect"></select>
       </div>
       <div class="col-md-2">
         <button type="submit" class="btn btn-default " data-toggle="modal"
                 data-target="#addOrEditProjectTimeSpan">查询
         </button>
       </div>
     </div>--%>

    <%-- <div class="row">
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
       <div class="modal fade" id="editExcel" tabindex="-1" role="dialog"
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
       </div>
     </div>--%>
    <br>

    <div class="row-fluid">
        <table
                class="table table-striped table-bordered table-hover datatable">
            <thead>
            <tr>
                <th rowspan="2" align="center">教研室</th>
                <th colspan="3" align="center">任务书</th>
                <th colspan="3" align="center">开题报告</th>
                <th colspan="3" align="center">工作进程表</th>
                <th colspan="3" align="center">指导老师评审表</th>
                <th colspan="3" align="center">评阅人评审表</th>
                <th colspan="3" align="center">答辩小组评审表</th>
            </tr>
            <tr>
                <%--任务书--%>
                <th>已完成</th>
                <th>应完成</th>
                <th>完成比例</th>
                <%--开题报告--%>
                <th>已完成</th>
                <th>应完成</th>
                <th>完成比例</th>
                <%--工作进程表--%>
                <th>已完成</th>
                <th>应完成</th>
                <th>完成比例</th>
                <%--指导老师评审表--%>
                <th>已完成</th>
                <th>应完成</th>
                <th>完成比例</th>
                <%--评阅人评审表--%>
                <th>已完成</th>
                <th>应完成</th>
                <th>完成比例</th>
                <%--答辩小组意见表--%>
                <th>已完成</th>
                <th>应完成</th>
                <th>完成比例</th>

            </tr>
            </thead>
            <tbody>
            <c:forEach items="${departmentDataList}" var="departmentData">
                <tr>
                    <td>${departmentData.department.description}</td>
                        <%--任务书--%>
                    <td>${departmentData.taskDocData.alreadyCompleteCount}</td>
                    <td>${departmentData.taskDocData.shouldCompleteCount}</td>
                    <td>
                        <a data-toggle="modal" data-target="#showDetail"
                           href="<%=basePath%>dataAccount/notCompletedTaskDoc.html?departmentId=${departmentData.department.id}">${departmentData.taskDocData.completionRate}</a>
                    </td>
                        <%--开题报告--%>
                    <td>${departmentData.openningReportData.alreadyCompleteCount}</td>
                    <td>${departmentData.openningReportData.shouldCompleteCount}</td>
                    <td><a data-toggle="modal" data-target="#showDetail"
                           href="<%=basePath%>dataAccount/notCompletedOpenningReport.html?departmentId=${departmentData.department.id}">${departmentData.openningReportData.completionRate}</a>
                    </td>
                        <%--工作进程表--%>
                    <td>${departmentData.schudelData.alreadyCompleteCount}</td>
                    <td>${departmentData.schudelData.shouldCompleteCount}</td>
                    <td><a data-toggle="modal" data-target="#showDetail"
                           href="<%=basePath%>dataAccount/notCompletedSchedule.html?departmentId=${departmentData.department.id}">${departmentData.schudelData.completionRate}</a>
                    </td>
                        <%--指导老师评审表--%>
                    <td>${departmentData.tutorEvaluate.alreadyCompleteCount}</td>
                    <td>${departmentData.tutorEvaluate.shouldCompleteCount}</td>
                    <td><a data-toggle="modal" data-target="#showDetail"
                           href="<%=basePath%>dataAccount/notCompletedTutorEvaluate.html?departmentId=${departmentData.department.id}">${departmentData.tutorEvaluate.completionRate}</a>
                    </td>
                        <%--评阅人评审表--%>
                    <td>${departmentData.reviewerEvaluateData.alreadyCompleteCount}</td>
                    <td>${departmentData.reviewerEvaluateData.shouldCompleteCount}</td>
                    <td><a data-toggle="modal" data-target="#showDetail"
                           href="<%=basePath%>dataAccount/notCompletedReviewerEvaluate.html?departmentId=${departmentData.department.id}">${departmentData.reviewerEvaluateData.completionRate}</a>
                    </td>
                        <%--答辩小组意见表--%>
                    <td>${departmentData.replyGroupEvaluateData.alreadyCompleteCount}</td>
                    <td>${departmentData.replyGroupEvaluateData.shouldCompleteCount}</td>
                    <td><a data-toggle="modal" data-target="#showDetail"
                           href="<%=basePath%>dataAccount/notCompletedGroupEvaluate.html?departmentId=${departmentData.department.id}">${departmentData.replyGroupEvaluateData.completionRate}</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <%--如果需要在模态框中加载另一个jsp，则需要书写以下的div--%>
    <div class="modal fade" id="showDetail" tabindex="-1" role="dialog"
         aria-hidden="true" aria-labelledby="modelOpeningReportTime">
        <div class="modal-dialog">
            <div class="modal-content"></div>
        </div>
    </div>
</div>