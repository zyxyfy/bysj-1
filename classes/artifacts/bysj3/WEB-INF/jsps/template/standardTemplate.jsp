<%--
  Created by IntelliJ IDEA.
  User: zhan
  Date: 2016/3/15
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>

<%--禁用模态框点击空白处关闭 ： data-backdrop="static" data-keyboard="false"--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsps/includeURL.jsp" %>

<div class="container-fluid" style="width: 100%">
    <div class="row-fluid">
        <ul class="breadcrumb">
            <li>模块A</li>
            <li>功能B<span class="divider">/</span>
            </li>
        </ul>
    </div>

    <div class="row">
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
    </div>

    <div class="row">
        <%--如果弹出的模态框需要获取后台传过来的数据，即先请求后台，再将请求到的数据放在jsp中进行渲染，则需要书写以下代码--%>
        <%--在<a>标签中添加href属性，并添加和模态框相关的属性，则点击此按钮时会先请求后台，然后后台的数据将会返回到某一个jsp页面进行渲染。后台返回的那个jsp页面将会添加到此jsp页面中id为addTeacher的模态框中的class为modal-content的标签中--%>
        <a href="employeeAdd.html" class="btn btn-primary btn-sm"
           data-toggle="modal" data-target="#addTeacher"> <i
                class="icon-plus"></i> 添加新教師
        </a>


        <%--如果需要在本页面中弹出一个模态框，则需要书写以下代码--%>
        <button class="btn btn-primary  btn-sm" type="button"
                data-toggle="modal" data-target="#editExcel">
            导入教師Excel <i class="icon-external-link"></i>
        </button>
        <%--下面容器是一个模态框--%>
        <div class="modal fade" id="editExcel" tabindex="-1" role="dialog"
             aria-hidden="true" aria-labelledby="modelOpeningReportTime">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="upLoadEmployee.html" method="post">
                        <%--在这个项目中，一般都需要将模态框放入到form表单中，因为大部分模态框提交后需要请求后台进行处理--%>
                        <%--这是模态框的头部--%>
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">
                                <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
                            </button>
                            <h4 class="modal-title">在此处输入模态框的标题</h4>
                        </div>
                        <%--这是模态框显示内容的主体部分--%>
                        <div class="modal-body">
                            在此处输入模态框中的内容
                        </div>
                        <%--这是模态框的尾部--%>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                            <button type="submit" class="btn btn-primary">保存</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <br>

    <div class="row-fluid">
        <table
                class="table table-striped table-bordered table-hover datatable">
            <thead>
            <tr>
                <th>职工</th>
                <th>姓名</th>
                <th>性別</th>
                <th>职称</th>
                <th>所属教研室</th>
                <th>联系电话</th>
                <th>学位</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>000000</td>
                <td>张三</td>
                <td>男</td>
                <td>教授</td>
                <td>信管教研室</td>
                <td>88888888</td>
                <td>硕士</td>
                <td>
                    <a class="btn btn-danger btn-xs" href="#"> <i
                            class="icon-remove "></i> 删除
                    </a> <a href="employeeEdit.html?employeeId=${employee.id}"
                            class="btn btn-warning btn-xs" type="button" data-toggle="modal"
                            data-target="#modifyTeacher"> <i class="icon-edit"></i> 修改
                </a>
                </td>
            </tr>
            <tr>
                <td>000000</td>
                <td>张三</td>
                <td>男</td>
                <td>教授</td>
                <td>信管教研室</td>
                <td>88888888</td>
                <td>硕士</td>
                <td>
                    <a class="btn btn-danger btn-xs" href="#"> <i
                            class="icon-remove "></i> 删除
                    </a> <a href="employeeEdit.html?employeeId=${employee.id}"
                            class="btn btn-warning btn-xs" type="button" data-toggle="modal"
                            data-target="#modifyTeacher"> <i class="icon-edit"></i> 修改
                </a>
                </td>
            </tr>
            <tr>
                <td>000000</td>
                <td>张三</td>
                <td>男</td>
                <td>教授</td>
                <td>信管教研室</td>
                <td>88888888</td>
                <td>硕士</td>
                <td>
                    <a class="btn btn-danger btn-xs" href="#"> <i
                            class="icon-remove "></i> 删除
                    </a> <a href="employeeEdit.html?employeeId=${employee.id}"
                            class="btn btn-warning btn-xs" type="button" data-toggle="modal"
                            data-target="#modifyTeacher"> <i class="icon-edit"></i> 修改
                </a>
                </td>
            </tr>
            <tr>
                <td>000000</td>
                <td>张三</td>
                <td>男</td>
                <td>教授</td>
                <td>信管教研室</td>
                <td>88888888</td>
                <td>硕士</td>
                <td>
                    <a class="btn btn-danger btn-xs" href="#"> <i
                            class="icon-remove "></i> 删除
                    </a> <a href="employeeEdit.html?employeeId=${employee.id}"
                            class="btn btn-warning btn-xs" type="button" data-toggle="modal"
                            data-target="#modifyTeacher"> <i class="icon-edit"></i> 修改
                </a>
                </td>
            </tr>
            <tr>
                <td>000000</td>
                <td>张三</td>
                <td>男</td>
                <td>教授</td>
                <td>信管教研室</td>
                <td>88888888</td>
                <td>硕士</td>
                <td>
                    <a class="btn btn-danger btn-xs" href="#"> <i
                            class="icon-remove "></i> 删除
                    </a> <a href="employeeEdit.html?employeeId=${employee.id}"
                            class="btn btn-warning btn-xs" type="button" data-toggle="modal"
                            data-target="#modifyTeacher"> <i class="icon-edit"></i> 修改
                </a>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="row">
        <ul class="pagination pagination-centered  pagination-sm ">
            <li><a href="#">&larr; 第一页</a></li>
            <li><a href="#">11</a></li>
            <li><a href="#">12</a></li>
            <li class="active"><a href="#">13</a></li>
            <li><a href="#">14</a></li>
            <li><a href="#">15</a></li>
            <li class="disabled"><a href="#">最后一页&larr; </a></li>
        </ul>
    </div>

    <%--如果需要在模态框中加载另一个jsp，则需要书写以下的div--%>
    <div class="modal fade" id="addTeacher" tabindex="-1" role="dialog"
         aria-hidden="true" aria-labelledby="modelOpeningReportTime">
        <div class="modal-dialog">
            <div class="modal-content">
                <%--此处会填充另外一个jsp的内容--%>
            </div>
        </div>
    </div>
</div>