<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsps/includeURL.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="container-fluid" style="width: 100%">
	<div>
		<h3><label
				class="label label-default">课题信息：标题--${graduateProject.title}，副标题--${empty (graduateProject.subTitle)?"":graduateProject.subTitle}</label>
		</h3>
		<%--<p>${graduateProject.title}</p>
		<p>${graduateProject.subTitle}</p>--%>
		<div>
			<h3><label
					class="label label-default">学生信息：姓名--${graduateProject.student.name}，班级--${graduateProject.student.studentClass.description}，学号--${graduateProject.student.no}</label>
			</h3>
			<a class="btn btn-warning"
			   href="stageAchievements.html" title="审阅各阶段成果"><i class="icon-backward"></i> 返回</a>
		</div>
	</div>
	<hr>
	<div class="row">
		<table
			class="table table-striped table-bordered table-hover datatable">
			<thead>
				<tr>
					<th>上传时间</th>
					<th>教师评语</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			<c:choose>
				<c:when test="${empty stageAchievements}">
					<div class="alert alert-warning alert-dismissable" role="alert">
						<button class="close" type="button" data-dismiss="alert">&times;</button>
						没有可以显示的信息
					</div>
				</c:when>
				<c:otherwise>
					<c:forEach items="${stageAchievements}" var="stageAchievement">
						<tr>
							<td><fmt:formatDate
									value="${stageAchievement.issuedDate.time}" type="date"
									dateStyle="long"/></td>
							<td><c:if test="${stageAchievement.remark!=null}">${stageAchievement.remark}</c:if>
								<c:if test="${stageAchievement.remark==null}">
									<a class="btn btn-primary btn-xs"
									   href="writeRemark.html?stageAchievementId=${stageAchievement.id}" type="button"
									   data-toggle="modal" data-target="#writeRemark">添加评语 </a>
								</c:if></td>
							<td><a class="button"
								   href="download/stageAchievement.html?stageAchievementId=${stageAchievement.id }"><span>下载</span></a>
								<c:if test="${stageAchievement.remark != null}">
									<a class="btn btn-primary btn-xs"
									   href="editRemark.html?stageAchievementId=${stageAchievement.id }" type="button"
									   data-toggle="modal" data-target="#writeRemark">修改评语 </a>
								</c:if></td>
						</tr>
					</c:forEach>

				</c:otherwise>
			</c:choose>
			<%--<c:forEach items="${stageAchievements}" var="stageAchievement">
                <tr>
                    <td><fmt:formatDate
                            value="${stageAchievement.issuedDate.time}" type="date"
                            dateStyle="long" /></td>
                    <td><c:if test="${stageAchievement.remark!=null}">${stageAchievement.remark}</c:if>
                        <c:if test="${stageAchievement.remark==null}">
                            <a class="btn btn-primary btn-xs" href="writeRemark.html?stageAchievementId=${stageAchievement.id}" type="button"
                                data-toggle="modal" data-target="#writeRemark">添加评语 </a>
                        </c:if></td>
                    <td><a class="button"
                        href="download/stageAchievement.html?stageAchievementId=${stageAchievement.id }"><span>下载</span></a>
                        <c:if test="${stageAchievement.remark != null}">
                            <a class="btn btn-primary btn-xs" href="editRemark.html?stageAchievementId=${stageAchievement.id }" type="button"
                                data-toggle="modal" data-target="#writeRemark">修改评语 </a>
                        </c:if></td>
                </tr>
            </c:forEach>--%>
			</tbody>
		</table>
	</div>
	<%--<div class="row">
		<%@ include file="/WEB-INF/jsps/page/pageBar.jsp"%>
	</div>--%>
	<div class="modal fade" id="writeRemark" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
	</div>
</div>