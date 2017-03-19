<%--
  Created by IntelliJ IDEA.
  User: 张战
  Date: 2016/3/22
  Time: 21:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>
<script type="text/javascript">
    function submitConfirm() {
        //return window.confirm("确认提交？");
        window.wxc.xcConfirm("确认提交？", "confirm", {
            onOk: function () {
                return true;
            },
            onCancel: function () {
                return false;
            }
            /*onClose:function(){
             return false;
             }*/
        });
    }

    $("#remarkTemplateSelect").change(
            function () {
//            将显示区域的文字清空
                $("#remarkByTutorTextareaToShow").html(" ");
//            获取选择的模版
                var remarkString = $("#remarkTemplateItem" + this.val());
                $("#remarkByTutorTextareaToShow").html(remarkString);
            });

    $("#submitText").click(function () {
        if ($("#commentByGroupCompletenessScore").val() == null) {
            myAlert("请选择完成任务书规定的要求与水平评分");
            return false;
        } else if ($("#commentByGroupQualityScore").val() == null) {
            myAlert("请选择论文与实物的质量评分");
            return false;
        } else if ($("#commentByGroupeReplyScore").val() == null) {
            myAlert("请选择论文内容的答辩陈述评分");
            return false;
        } else if ($("#commentByGroupCorrectnessScore").val() == null) {
            myAlert("请选择回答问题的正确性评分");
            return false;
        }
        var remarkByTutorTextarea = $("#remarkByTutorTextareaToShow");
        remarkByTutorTextarea.find("select").each(function () {
            $(this).replaceWith($(this).val());
        });
        remarkByTutorTextarea.find("span").each(function () {
            $(this).replaceWith($(this).html());
        });
        $("#remarkByGroup").val(remarkByTutorTextarea.text());
    });

</script>

<form:form commandName="graduateProject" action="${actionURL }" onsubmit="return submitConfirm()" method="post"
           class="pageForm required-validate">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"
                aria-hidden="true">×
        </button>
        <h4 class="modal-title" id="myModalLabel">
            附表9：
            山东建筑大学毕业设计（论文）答辩小组意见表
        </h4>
        <h4>
            题目名称：${graduateProject.title}
            <br>
            班级：${graduateProject.student.studentClass.description}&nbsp;&nbsp;学生姓名：${graduateProject.student.name}&nbsp;&nbsp;学号:${projectToEvaluate.student.no}
        </h4>
    </div>
    <div class="modal-body">


        <div class="pageContent">

            <div class="pageFormContent nowrap" layoutH="150">
                <input name="id" type="hidden" value="${graduateProject.id }"/>

                <table class="table" border="1" width="500px">
                    <tr>
                        <td>
                            完成任务书规定的要求与水平评分
                        </td>
                        <td> <%--path 对应 commandName所代表的对象的一个属性 --%>
                            <form:select
                                    name="commentByGroupCompletenessScore"
                                    path="commentByGroup.completenessScore"
                                    id="commentByGroupCompletenessScore" class="combox">

                                <form:options items="${defaultGrades}"/>
                            </form:select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            论文与实物的质量评分
                        </td>
                        <td> <%--path 对应 commandName所代表的对象的一个属性 --%>
                            <form:select
                                    name="commentByGroupQualityScore"
                                    path="commentByGroup.qualityScore"
                                    id="commentByGroupQualityScore" class="combox">
                                <form:options items="${defaultGrades}"/>
                            </form:select>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            论文内容的答辩陈述评分
                        </td>
                        <td> <%--path 对应 commandName所代表的对象的一个属性 --%>
                            <form:select
                                    name="commentByGroupeReplyScore"
                                    path="commentByGroup.replyScore"
                                    id="commentByGroupeReplyScore" class="combox">
                                <form:options items="${defaultGrades}"/>
                            </form:select>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            回答问题的正确性评分
                        </td>
                        <td> <%--path 对应 commandName所代表的对象的一个属性 --%>
                            <form:select
                                    name="commentByGroupCorrectnessSocre"
                                    path="commentByGroup.correctnessSocre"
                                    id="commentByGroupCorrectnessScore" class="combox">
                                <form:options items="${defaultGrades}"/>
                            </form:select>
                        </td>
                    </tr>
                </table>
                <!-- 评语区 -->
                <dl>
                    <dt>选择模板：
                        <select id="remarkTemplateSelect">
                            <option label="请选择评审模板" value="0" selected></option>
                            <c:forEach items="${remarkTemplates }" var="remarkTemplate">
                                <option label="${remarkTemplate.title }" value="${remarkTemplate.id }">
                                </option>
                            </c:forEach>
                        </select>
                    </dt>
                    <dd>
                        <!-- 用于表单提交的textarea -->
					<textarea id="remarkByGroup" rows="10" cols="60" name="remark" style="display:none;">
					</textarea>
                        <!-- 用于显示的textarea -->
                        <div id="remarkByTutorTextareaToShow" contenteditable="true" class="form-control"
                             style="width:560px;height:156px;border:1px solid black;font-size:18px;overflow-y:scroll;overflow-x:hidden;">
                                ${graduateProject.commentByGroup.remarkByGroup}
                        </div>
                        <c:forEach items="${remarkTemplates }" var="remarkTemplate">
                            <!-- 评语内容 -->
                            <div id="remarkTemplateItem${remarkTemplate.id }" style="display:none">
                                <div style="font-size:18px;">
                                    <c:forEach items="${remarkTemplate.remarkTemplateItems }" var="remarkTemplateItem">
                                        <span style="float:left;font-size:18px;line-height: 20px">${remarkTemplateItem.preText}</span>
                                        <select style="float: left;height: 20px">
                                            <c:forEach items="${remarkTemplateItem.remarkTemplateItemsOption}"
                                                       var="remarkTemplateItemOption">
                                            <option label="${remarkTemplateItemOption.itemOption }"
                                                    value="${remarkTemplateItemOption.itemOption }">

                                                </c:forEach>
                                        </select>
                                        <span style="float:left;font-size:18px;line-height: 20px">${remarkTemplateItem.postText }</span>
                                    </c:forEach>
                                </div>
                            </div>
                        </c:forEach>
                    </dd>
                </dl>
                <dl>
                    <dd>是否通过答辩：</dd>
                    <dt style="width:300px">
                        <input type="radio" name="qualified" id="commentByTutorQualified" value="true"
                                <c:if test="${isQualified}">
                                    checked
                                </c:if>
                        />通过答辩
                        <input type="radio" name="qualified" id="commentByTutorQualified1" value="false"
                                <c:if test="${!isQualified}">
                                    checked
                                </c:if>
                        /><span style="color: red">不通过答辩</span>
                    </dt>
                </dl>
            </div>
        </div>

    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default"
                data-dismiss="modal">关闭
        </button>
        <button type="submit" class="btn btn-primary" id="submitText">
            提交更改
        </button>
    </div>
</form:form>