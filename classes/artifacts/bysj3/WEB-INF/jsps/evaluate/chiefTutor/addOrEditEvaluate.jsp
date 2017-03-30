<%--
  Created by IntelliJ IDEA.
  User: 张战
  Date: 2016/3/25
  Time: 9:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsps/includeURL.jsp" %>
<script type="text/javascript">
    $("#remarkTemplateSelect").change(function () {
        //清空
        $("#remarkByTutorTextareaToShow").html("");
        var remarkTemplateItem = $("#remarkTemplateItem" + $(this).val());
        /* 将选择的模板显示到textarea中 */
        $("#remarkByTutorTextareaToShow").html(remarkTemplateItem.html());
    });
    $("#submitButton").click(function () {
        //判断有否有评分
        if ($("#commentByTutorBasicAbilityScore").val() == "") {
            myAlert("填写评分");
            return false;
        } else if ($("#commentByTutorworkLoadScore").val() == "") {
            myAlert("填写评分");
            return false;
        } else if ($("#commentByTutorachievementLevelScore").val() == "") {
            myAlert("填写评分");
            return false;
        } else if ($("#commentByTutorworkLoadScore").val() == "") {
            myAlert("填写评分");
            return false;
        } /*else if ($("#tutorEvaluteHasCompleteProject").val() == "") {
            myAlert("请选择任务完成情况");
            return false;
        } else if ($("#tutorEvaluteHasMiddleExam").val() == "") {
            myAlert("请选择是否有中期报告");
            return false;
        } else if ($("#tutorEvaluteHasTranslationMaterail").val() == "") {
            myAlert("请选择是否有外文资料翻译");
            return false;
        } else if ($("#tutorEvaluteHasTwoAbstract").val() == "") {
            myAlert("请选择是否有中、英文摘要");
            return false;
        }
        else if ($("#tutorEvaluteHasSoftHardWare").val() == "") {
            myAlert("请选择是否有软、硬件完成情况");
            return false;
        } else if ($("#commentByTutorQualified").val() == "") {
            myAlert("请选择是否允许答辩！");
            return false;
         } */ else if ($("input:radio[name='commentByTutorQualified']:checked").val() == null) {
            myAlert("请选择评审结论");
            return false;
        }
        //替换div模板中的组件
        var remarkToShow = $("#remarkByTutorTextareaToShow");
        /*if (remarkToShow.html() == " ") {
            myAlert("请填写评语！");
            return false;
         }*/
        remarkToShow.find("select").each(function () {
            $(this).replaceWith($(this).val());
        });
        remarkToShow.find("span").each(function () {
            $(this).replaceWith($(this).html());
        });
        $("#remarkByTutor").val(remarkToShow.text());
    });
</script>
<form:form commandName="graduateProject" action="${actionURL }" onsubmit="" method="post"
           class="pageForm required-validate">
    <input type="hidden" name="graduateProjectId" value="${graduateProject.id}">

    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"
                aria-hidden="true">×
        </button>
        <h4 class="modal-title" id="myModalLabel1">
            附表7：
            山东建筑大学毕业设计（论文）指导教师评审表
            <br>

            题目名称：${graduateProject.title}
            <br>
            班级：${graduateProject.student.studentClass.description}&nbsp;&nbsp;学生姓名：${graduateProject.student.name}&nbsp;&nbsp;学号:${graduateProject.student.no}
        </h4>
    </div>
    <div class="modal-body">

        <div class="pageFormContent nowrap" layoutH="150">
            <input name="id" type="hidden" value="${projectToEvaluate.id }"/>
            <table class="table" border="1" width="500px">
                <tr height="30">
                    <td>
                        任务完成情况
                    </td>
                    <td>
                        <form:select
                                name="tutorEvaluteHasCompleteProject"
                                path="commentByTutor.tutorEvaluteHasCompleteProject"
                                id="tutorEvaluteHasCompleteProject" class="combox">
                            <form:option value="true" label="完成"/>
                            <form:option value="false" label="未完成"/>
                        </form:select>
                    </td>
                    <td>
                        论文字数
                    </td>
                    <td>
                        <form:input path="commentByTutor.tutorEvaluteDissertationWordCount" type="text"
                                    class="required digits"/>
                    </td>
                </tr>

                <tr height="30">
                    <td>
                        中期检查报告
                    </td>
                    <td>
                        <form:select
                                path="commentByTutor.tutorEvaluteHasMiddleExam"
                                id="tutorEvaluteHasMiddleExam" class="combox">
                            <form:option value="true" label="有"/>
                            <form:option value="false" label="无"/>
                        </form:select>
                    </td>
                    <td>
                        外文资料翻译
                    </td>
                    <td>
                        <form:select
                                path="commentByTutor.tutorEvaluteHasTranslationMaterail"
                                id="tutorEvaluteHasTranslationMaterail" class="combox">
                            <form:option value="true" label="有"/>
                            <form:option value="false" label="无"/>
                        </form:select>
                    </td>
                </tr>
                <tr height="30">
                    <td>
                        外文资料翻译字数
                    </td>
                    <td>
                        <form:input path="commentByTutor.tutorEvaluteHasTranslationWordCount"
                                    class="required digits"/>
                    </td>
                    <td>
                        中、英文摘要
                    </td>
                    <td>
                        <form:select
                                path="commentByTutor.tutorEvaluteHasTwoAbstract"
                                id="tutorEvaluteHasTwoAbstract" class="combox">
                            <form:option value="true" label="有"/>
                            <form:option value="false" label="无"/>
                        </form:select>
                    </td>
                </tr>
                <tr height="30">
                    <td colspan="2">
                        基本理论、基本知识、基本技能和外语水平
                    </td>
                    <td colspan="2"> <%--path 对应 commandName所代表的对象的一个属性 --%>
                        <form:select
                                name="commentByTutorBasicAbilityScore"
                                path="commentByTutor.basicAblityScore"
                                id="commentByTutorBasicAbilityScore" class="combox">

                            <form:options items="${defaultGrades}"/>
                        </form:select>
                    </td>
                </tr>
                <tr height="30">
                    <td colspan="2">
                        工作量、工作态度
                    </td>
                    <td colspan="2"> <%--path 对应 commandName所代表的对象的一个属性 --%>
                        <form:select
                                name="commentByTutorworkLoadScore"
                                path="commentByTutor.workLoadScore"
                                id="commentByTutorworkLoadScore" class="combox">

                            <form:options items="${defaultGrades}"/>
                        </form:select>
                    </td>
                </tr>

                <tr height="30">
                    <td colspan="2">
                        独立工作能力、分析与解决问题的能力
                    </td>
                    <td colspan="2"> <%--path 对应 commandName所代表的对象的一个属性 --%>
                        <form:select
                                name="commentByTutorworkAbilityScore"
                                path="commentByTutor.workAblityScore"
                                id="commentByTutorworkAbilityScore" class="combox">

                            <form:options items="${defaultGrades}"/>
                        </form:select>
                    </td>
                </tr>

                <tr>
                    <td colspan="2">
                        完成任务情况及水平
                    </td>
                    <td colspan="2"> <%--path 对应 commandName所代表的对象的一个属性 --%>
                        <form:select
                                name="commentByTutorachievementLevelScore"
                                path="commentByTutor.achievementLevelScore"
                                id="commentByTutorachievementLevelScore" class="combox">

                            <form:options items="${defaultGrades}"/>
                        </form:select>
                    </td>
                </tr>
            </table>
            <!-- 评语区 -->
            <dl>
                <dt>
                    选择模板：
                    <select id="remarkTemplateSelect">
                        <option label="请选择评审模板" value="0"></option>
                        <c:forEach items="${remarkTemplates}" var="remarkTemplate">
                            <option label="${remarkTemplate.title }" value="${remarkTemplate.id }">
                            </option>
                        </c:forEach>
                    </select>
                </dt>
                <dd>
                    <!-- 用于表单提交的textarea -->
					<textarea id="remarkByTutor" rows="10" cols="60" name="remark" style="display:none;">
					</textarea>
                    <!-- 用于显示的textarea -->
                    <div id="remarkByTutorTextareaToShow" contenteditable="true"
                         style="width:560px;height:156px;border:1px solid black;font-size:18px;overflow-y:scroll;overflow-x:hidden;"
                            >
                            ${graduateProject.commentByTutor.remark}
                    </div>
                    <c:forEach items="${remarkTemplates}" var="remarkTemplate">
                        <!-- 评语内容 -->
                        <div id="remarkTemplateItem${remarkTemplate.id }" style="display:none">
                            <div style="font-size:18px;">
                                <c:forEach items="${remarkTemplate.remarkTemplateItems }" var="remarkTemplateItem">
                                    <%--选项前的文字--%>
                                    <span style="float:left;font-size:18px;line-height: 20px">${remarkTemplateItem.preText}</span>
                                    <select style="float: left;height: 20px">
                                        <c:forEach items="${remarkTemplateItem.remarkTemplateItemsOption}"
                                                   var="remarkTemplateOption">
                                            <option label="${remarkTemplateOption.itemOption}">${remarkTemplateOption.itemOption}</option>
                                        </c:forEach>
                                    </select>
                                    <%--选项后的文字--%>
                                    <span style="float:left;font-size:18px;line-height: 20px">${remarkTemplateItem.postText }</span>
                                </c:forEach>
                            </div>
                        </div>
                    </c:forEach>
                </dd>
            </dl>
            <dl>
                <dd>评审结论:</dd>
                <dt style="width:300px">
                    <input type="radio" name="commentByTutorQualified" id="commentByTutorQualified" value="true"
                            <c:if test="${isQualified}">
                                checked
                            </c:if>
                            />同意参加答辩
                    <input type="radio" name="commentByTutorQualified" id="commentByTutorQualified1" value="false"
                            <c:if test="${!isQualified}">
                                checked
                            </c:if>
                    /><span style="color: red">不同意参加答辩</span>
                </dt>
            </dl>
        </div>
    </div>
    <div class="modal-footer">
        <button type="submit" id="submitButton" class="btn btn-primary">
            提交更改
        </button>
    </div>


</form:form>

