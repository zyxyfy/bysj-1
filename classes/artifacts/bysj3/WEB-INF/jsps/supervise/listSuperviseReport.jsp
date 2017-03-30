<%--
  Created by IntelliJ IDEA.
  User: zhan
  Date: 2016/4/23
  Time: 16:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>

<script type="text/javascript">
    /*删除督导报告的函数*/
    function delReport(reportId) {
        window.wxc.xcConfirm("确认删除？", "confirm", {
            onOk: function () {
                $.ajax({
                    url: '/bysj3/process/delSuperReport.html',
                    data: {"reportId": reportId},
                    dataType: 'json',
                    type: 'post',
                    success: function () {
                        $("#superReport" + reportId).remove();
                        window.wxc.xcConfirm("删除成功", "success");
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
    /*上传文件时的回调函数*/
    function checkUploadFile() {
        //获取上传的文件名
        var fileName = $("#uploadFile").val();
        //获取文件的扩展名
        var excuName = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length);
        //判断上传文件的类型
        if (excuName == "docx" || excuName == "doc" || excuName == "rar" || excuName == "zip") {
            window.wxc.xcConfirm("确认提交？", "confirm", {
                onOk: function () {
                    return true;
                }
            });
        } else {
            window.wxc.xcConfirm("请选择word格式的文件或压缩文件", "info");
            return false;
        }

    }
</script>
<div class="container-fluid" style="width: 100%">
    <div class="row-fluid">
        <ul class="breadcrumb">
            <li>督导</li>
            <li>提交督导报告<span class="divider">/</span>
            </li>
        </ul>
    </div>

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
    <form action="<%=basePath%>process/uploadSupervisionReport.html" method="post" enctype="multipart/form-data"
          onsubmit="return checkUploadFile()">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-3">
                    <input type="file" id="uploadFile" class="form-control" name="superReportFile"
                           placeholder="请选择需要上传的文件" required>
                </div>
                <div class="col-md-1">
                    <button style="margin-left: 10px" class="btn btn-default" type="submit"><i class="icon-upload"></i>上传
                    </button>

                </div>
            </div>
        </div>

    </form>
    <br>

    <div class="row-fluid">
        <table
                class="table table-striped table-bordered table-hover datatable">
            <thead>
            <tr>
                <th></th>
                <th>所属学院</th>
                <th>上传者</th>
                <th>上传日期</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${empty supervisionReportList}">
                    <%--如果表格中要列出数据并没有内容，可以使用以下的方法--%>
                   <%-- <div class="alert alert-warning alert-dismissable" role="alert">
                        <button class="close" type="button" data-dismiss="alert">&times;</button>
                        没有可以显示的数据
                    </div>--%>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${supervisionReportList}" var="supervisionReport" varStatus="status">
                        <tr id="superReport${supervisionReport.id}">
                            <td>${status.index+1}</td>
                            <td>${supervisionReport.school.description}</td>
                            <td>${supervisionReport.issuer.name}</td>
                            <td>
                                <fmt:parseDate value="${supervisionReport.calendar}" type="date"/>
                            </td>
                            <td>
                                <a class="btn btn-default btn-xs"
                                   href="<%=basePath%>process/downloadSupervisionReport.html?reportId = ${supervisionReport.id}"><i
                                        class="icon-download"></i>下载</a>
                                <a class="btn btn-danger btn-xs" onclick="delReport(${supervisionReport.id})"><i
                                        class="icon-remove"></i>删除 </a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
        <c:choose>
        <c:when test="${empty supervisionReportList}">
            <%--如果表格中要列出数据并没有内容，可以使用以下的方法--%>
            <div class="alert alert-warning alert-dismissable" role="alert">
                <button class="close" type="button" data-dismiss="alert">&times;</button>
                没有可以显示的数据
            </div>
        </c:when>
        </c:choose>
    </div>
    <div class="row">
        <%@include file="/WEB-INF/jsps/page/pageBar.jsp" %>
    </div>

</div>