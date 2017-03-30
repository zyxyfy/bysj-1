<%--
  Created by IntelliJ IDEA.
  User: 张战
  Date: 2016/3/28
  Time: 10:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>

<script type="text/javascript">
    /*定义此变量的目的是在进行添加评语模版前，获取已有的评语模版的数量*/
    var trCount = 0;

    /*用来存储加载的行的id*/
    var modalId = 0;


    /*得到已有的评语模版的数量*/
    function getOldItems() {
        return $(".itemTr").length;
    }

    function addLineNumber() {
        var title = $("#remarktitle").val().length;
        if (title == 0) {
            myAlert("请输入评语模版的名称");
            return false;
        }
        var lineNumber = $("input[name='lineNumber']");
        lineNumber.val($(".itemTr").length);
        window.wxc.xcConfirm("确认提交？","confirm",{
			onOk: function(){
                $("#setRemarkTemplate").submit();
			}});
    }
    /*点击新建评语模版要执行的函数*/
    function addItem(itemCount) {
        $("#noShowInfo").remove();
        //得到已有的评语模版的条数
        trCount = getOldItems();
        //获取需要动态添加的元素
        var itemTr = $(".itemList");
        var index;
        /*因为index的值要动态获取，所以写在for循环中*/
        /*var td1 = "<td><input class='form-control' id='delRemarkTemplateItem" + index + "' name='remarkTemplateItem[" + index + "].preText'></td>";
         var td2 = "<td><input type='hidden' name='remarkTemplateItem[" + index + "].id'><input readonly class='form-control required' type='text' name='remarkTemplateItem[,].itemOptions'><a data-toggle='modal' data-target='#modalItemOptions' class='btn btn-default' ><i class='icon-plus'></i>添加评语选项</a></td>";
         var td3 = "<td><input name='remarkTemplateItem[" + index + "].postText' class='form-control'></td>";
         var td4 = "<td><button class='btn btn-warning' type='button' onclick='delItem(index)'><i class='icon-remove-sign'></i>删除</button></td>";*/
        //data-target='#modalItemOptions'
        for (var i = 0; i < itemCount; i++) {
            index = i + trCount;
            itemTr.append("<tr class='itemTr' id='remarkItemsIr" + index + "'>" + "<td><input class='form-control' id='delRemarkTemplateItem" + index + "' name='remarkTemplateItem[" + index + "]preText'></td>" + "<td><input type='hidden' name='remarkTemplateItem[" + index + "].id'><input readonly class='form-control' required id='itemOptions" + index + "' type='text' name='remarkTemplateItem[" + index + "]itemOptions'><a data-toggle='modal' onclick='loadModel(" + index + ")' data-indexD='" + index + "' class='btn btn-default' ><i class='icon-plus'></i>添加评语选项</a></td>" + "<td><input name='remarkTemplateItem[" + index + "]postText' class='form-control'></td>" + "<td><button class='btn btn-warning' type='button' onclick='delItem(this)'><i class='icon-remove-sign'></i>删除</button></td>" + "</tr>");
        }
    }

    /*因为在加载模态框时需要获取加载的是哪一行的数据，所以不能通过data-target属性来指定，需要为按钮增加一个点击事件，并获取相应的index的值*/
    function loadModel(index) {
        modalId = index;
        $("#modalItemOptions").modal();
    }

    /*删除对应的评语模版*/
    function delItem(delThis) {
        $(delThis).parent().parent().remove();
    }

    /*删除对应的评语选项*/
    function removeItemOption(delThis) {
        $(delThis).text = "";
        $(delThis).parent().parent().parent().children().remove();
    }

    /*获取评语模版中评语选项的文本框*/
    function getRemarkItemText(getModalId) {
        var remarkItemText = $("#itemOptions" + getModalId);
        return remarkItemText;
    }

    /*添加评语选项*/
    function addItemOption() {
        var itemOptionTr = $(".itemOptionBody");
        var td1 = "<td><input class='form-control' name='remarkTemplateItemOption'></td>";
        var td2 = "<td><button class='btn btn-warning'><i class='icon-remove-sign' onclick='removeItemOption(this)'></i></button></td>";
        itemOptionTr.append("<tr class='itemOptionTr'>" + td1 + td2 + "</tr>");
    }

    /*添加评语选项的提交*/
    function submitItemOption() {
        var inputText = "";
        $("input[name='remarkTemplateItemOption']").each(function () {
            inputText = inputText + "," + $(this).val();
        });
        var itemOptions = inputText.substring(1, inputText.length);
        var remarkItem = getRemarkItemText(modalId);
        remarkItem.val(itemOptions);
        emplyTr();
    }

    /*关闭模态框时自动清空添加的评语选项*/
    function emplyTr() {
        $(".itemOptionTr").remove();
    }
</script>

<div class="container-fluid" style="width: 100%">
    <div class="row-fluid">
        <ul class="breadcrumb">
            <li>设置${remarkTemplate.category}</li>
        </ul>
    </div>
    <br>

    <div class="row-fluid">
        <form action="${actionURL}" method="post" id="setRemarkTemplate">
            <input type="hidden" name="lineNumber" value=""> <input
                type="hidden" name="remarkTemplateId" value="${remarkTemplate.id}">

            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-2">
                        <div class="btn-group">
                            <button type="button" class="btn btn-primary dropdown-toggle"
                                    data-toggle="dropdown">
                                <i class="icon-plus"></i> 新建评语模版<span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu" role="menu">
                                <li><a onclick="addItem(1)">创建1个</a></li>
                                <li><a onclick="addItem(2)">创建2个</a></li>
                                <li><a onclick="addItem(3)">创建3个</a></li>
                                <li><a onclick="addItem(4)">创建4个</a></li>
                                <li><a onclick="addItem(5)">创建5个</a></li>
                                <li><a onclick="addItem(6)">创建6个</a></li>
                                <li><a onclick="addItem(7)">创建7个</a></li>
                                <li><a onclick="addItem(8)">创建8个</a></li>
                                <li><a onclick="addItem(9)">创建9个</a></li>
                                <li><a onclick="addItem(10)">创建10个</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-md-2">
                        <input name="title" id="remarktitle" placeholder="请输入评语模版的名称" value="${remarkTemplate.title}"
                               class="form-control" required>
                    </div>
                </div>
            </div>
            <br>
            <table
                    class="table table-striped table-bordered table-hover datatable">
                <thead>
                <tr>
                    <th>选项前的文字</th>
                    <th>评语选项</th>
                    <th>选项后的文字</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody class="itemList">
                <c:choose>
                    <c:when test="${remarkTemplate.remarkTemplateItems==null}">
                        <div class="alert alert-warning alert-dismissable"
                             id="noShowInfo" role="alert">
                            <button class="close" type="button" data-dismiss="alert">&times;</button>
                            没有可以显示的评语模版
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${remarkTemplate.remarkTemplateItems}"
                                   var="remarkTemplateItem" varStatus="status">
                            <tr class="itemTr">
                                <td nowrap="nowrap"><input
                                        name="remarkTemplateItem[${status.index}]preText"
                                        class="form-control" value="${remarkTemplateItem.preText}">
                                </td>
                                <td nowrap="nowrap"><input type="hidden" readonly
                                                           name="remarkTemplateItem[${status.index}].id"> <c:set
                                        var="itemOptionStr" value=""/> <c:forEach
                                        items="${remarkTemplateItem.remarkTemplateItemsOption}"
                                        var="remarkTemplateItemsOption">
                                    <c:set var="itemOptionStr"
                                           value="${itemOptionStr},${remarkTemplateItemsOption.itemOption}"/>
                                </c:forEach> <%--去除第一个逗号--%> <c:set var="itemOptionStr"
                                                                    value="${fn:substring(itemOptionStr, 1, fn:length(itemOptionStr))}"/>
                                    <input readonly class="form-control required" id="itemOptions${status.index}"
                                           value="${itemOptionStr}" type="text"
                                           name="remarkTemplateItem[${status.index}]itemOptions">
                                    <a data-toggle="modal" onclick="loadModel(${status.index})" class="btn btn-default"><i
                                            class="icon-plus"></i>添加评语选项</a></td>
                                <td nowrap="nowrap"><input name="remarkTemplateItem[${status.index}]postText"
                                                           value="${remarkTemplateItem.postText}" class="form-control"
                                                           type="text"></td>
                                <td nowrap="nowrap">
                                    <button class="btn btn-warning" onclick="delItem(this)"><i
                                            class="icon-remove-sign"></i>删除
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
            <hr>
            <input class="btn btn-primary" style="float: right;margin-left: 10px" type="button"
                   onclick="addLineNumber()" value="提交">
            <a href="setRemarkTemplate.html" class="btn btn-warning" style="float: right">取消</a>
        </form>
    </div>
    <div class="modal fade" id="modalItemOptions" tabindex="-1"
         role="dialog" aria-hidden="true"
         aria-labelledby="modelOpeningReportTime">
        <div class="modal-dialog">
            <div class="modal-content">

                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-hidden="true">×
                    </button>
                    <h4 class="modal-title" id="myModalLabel">添加评语</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <button class="btn btn-default" onclick="addItemOption()">
                            添加评语选项
                        </button>
                    </div>

                    <table class="table">
                        <thead>
                        <tr>
                            <th>评语选项</th>
                            <th>删除</th>
                        </tr>
                        </thead>
                        <tbody class="itemOptionBody">

                        </tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" onclick="emplyTr()" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="submitItemOption()">
                        提交更改
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
