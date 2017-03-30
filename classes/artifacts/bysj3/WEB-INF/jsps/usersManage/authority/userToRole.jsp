<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/includeURL.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal">
		<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
	</button>
	<h4 class="modal-title">设置教师角色</h4>
</div>
<div class="modal-body">
	<div class="container">
		<script type="text/javascript">
				/* 单项解除的函数 */
				function noRole() {
					if ($("#selectedRoleList").find("option:selected").text() == "") {
						myAlert("请选择需要移除的角色！");
						return false;
					}
					var ownRoleId = $("#selectedRoleList").find("option:selected").val();
					var ownRole = $("#selectedRoleList").find("option:selected").text();
					$("#selectedRoleList").find("option:selected").remove();
					$("select[name='unSelectedRoleList']").append(
							"<option value='"+ownRoleId+"'>" + ownRole
									+ "</option>");
				}

				/* 解除全部 */
				function allNoRole() {
					$("#selectedRoleList option").each(
							function() {
								$("select[name='unSelectedRoleList']").append(
										"<option value='" + $(this).val()
												+ "'>" + $(this).text()
												+ "</option>");
								$(this).remove();
							});
				}

				/* 单项添加的函数 */
				function ownRole() {
					if ($("#unSelectedRoleList").find("option:selected").text() == "") {
						myAlert("请选择需要添加的角色");
						return false;
					}
					var selectId = $("#unSelectedRoleList option:selected")
							.val();
					var noRoled = $("#unSelectedRoleList option:selected")
							.text();
					$("#unSelectedRoleList option:selected").remove();
					$("#selectedRoleList").append(
							"<option value='"+selectId+"'>" + noRoled
									+ "</option>");
				}

				/* 分配全部 */
				function allOwnRole() {
					$("#unSelectedRoleList option").each(
							function() {
								$("select[name='selectedRoleList']").append(
										"<option value='" + $(this).val()
												+ "'>" + $(this).text()
												+ "</option>");
								$(this).remove();
							});
				}
				
				/* 点击提交按钮，将所选择的角色传递到后台 */
				function submitRole(employId){
					var selectrolestr = null;
					var selectroleid = null;
					var i =0;
					$("#selectedRoleList option").each(function(){
						if(i==0){
							selectrolestr = $(this).text();
							selectroleid = $(this).val();
						}else{
							selectrolestr =selectrolestr+","+ $(this).text();
							selectroleid = selectroleid + "," +$(this).val();
						}
						i++;
					});
					var unselectrolestr = null;
					var unselectroleid = null;
					var a =0;
					$("#unSelectedRoleList option").each(function(){
						if(a==0){
							unselectrolestr = $(this).text();
							unselectroleid = $(this).val();
						}else{
							unselectrolestr =unselectrolestr+","+ $(this).text();
							unselectroleid = unselectroleid + "," +$(this).val();
						}
						a++;
					});

					$.ajax({
						url:'/bysj3/setEmployeeToRole.html',
						data:{"selectedRoleList":selectrolestr,"selectRoleId":selectroleid,"unselectedRoleList":unselectrolestr,"unselectedRoleId":unselectroleid,"employeeId":employId},
						dataType:'json',
						type:'POST',
						success:function(){
							myAlert("修改成功");
							return location.href = "/bysj3/usersManage/employeeToRole.html";
							return true;
						},
						error:function(){
							myAlert("修改失败，请稍后再试");
							return false;
						}
					});
				} 
			</script>

		<div class="row">
			<div class="col-xs-3">
				<p class="text-center">已拥有角色</p>
			</div>
			<div class="col-xs-2">
				<p class="text-center">操作</p>
			</div>
			<div class="col-xs-3">
				<p class="text-center">未拥有角色</p>
			</div>
		</div>

		<div class="row">
			<div class="col-xs-3">
				<select id="selectedRoleList" name="selectedRoleList" multiple
					class="form-control input-large">

					<c:forEach items="${ownRoles}" var="ownRole">
						<option value="${ownRole.id}">${ownRole.description}</option>
					</c:forEach>
				</select>
			</div>


			<div class="col-xs-2">
				<p class="text-center">
					<button class="btn btn-default btn-sm" onclick="ownRole()">
						<i class=" icon-angle-left"></i> 单项分配
					</button>
					<br>
					<button class="btn btn-default btn-sm" onclick="noRole()">
						<i class=" icon-angle-right"> 单项解除</i>
					</button>
					<br>
					<button class="btn btn-default btn-sm" onclick="allOwnRole()">
						<i class=" icon-double-angle-left"> 分配全部</i>
					</button>
					<br>
					<button class="btn btn-default btn-sm" onclick="allNoRole()">
						<i class=" icon-double-angle-right"> 解除全部</i>
					</button>
				</p>
			</div>
			<div class="col-xs-3">
				<select id="unSelectedRoleList" name="unSelectedRoleList" multiple
					class="form-control input-large">
					<c:forEach items="${noOwnRoles}" var="noOwnRole">
						<option value="${noOwnRole.id}">${noOwnRole.description}</option>
					</c:forEach>
				</select>
			</div>
		</div>
	</div>

</div>
<div class="modal-footer">
	<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	<button type="submit" class="btn btn-primary"
		onclick="submitRole(${employeeId})">提交</button>
</div>
