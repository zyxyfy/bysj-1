<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/WEB-INF/jsps/includeURL.jsp"%>
<div class="panel panel-primary">
	<div class="container">
		<div class="row">
			<div class="col-md-4">标题：${mail.title}</div>
			<div class="col-md-4">发件人：${mail.addressor.name}</div>
			<div class="col-md-4">时间：${mail.addressTime.time}</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				正文：${mail.content }
			</div>
		</div>
	</div>
</div>