<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>计划</title>
<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>
<%@ page import="czsp.user.model.UserInfo"%>
<body>
	<%
		UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
		if (userInfo != null) {
	%>
	当前人员：<%=userInfo.getName()%>(<%=userInfo.getUserId()%>)
	<br />
	<%
		} else {
			userInfo = new UserInfo();
		}
	%>
	<form action="/czsp/plan/create">
		<input type="hidden" id="createUserId" name="createUserId" value="<%=userInfo.getUserId()%>" /> 
		<label for="planName">规划名称</label>：
		<input type="text" id="planName" name="planName" class="required" />
		<label for="phases">规划环节</label>：<label id="phasesLabel"></label>
		<input type="hidden" id="phases" name="phases" class="required" />
		<button type="button" name="select">选择</button>
		&nbsp <input type="submit" value="新建计划">
	</form>
	<br />
	<br />
	<br />
	<button name="ativate">激活</button>

	<table border="1">
		<tr>
			<th>计划ID</th>
			<th>申请ID</th>
			<th>规划名称</th>
			<th>当前实例ID</th>
			<th>创建时间</th>
			<th>创建人ID</th>
			<th>操作</th>
		</tr>
		<c:forEach var="info" items="${obj.infoList}">
			<tr>
				<td>${info.planId}</td>
				<td>${info.appId}</td>
				<td>${info.planName}</td>
				<td>${info.instanceId}</td>
				<td><fmt:formatDate value="${info.createTime}" type="both" /></td>
				<td>${info.createUserId}</td>
				<td><button name="instance">查看实例</button><button>启动计划</button></td>
			</tr>
		</c:forEach>
	</table>

	<script type="text/javascript">
		$("button[name='instance']").bind("click", function() {
			var tr = $(this).parents("tr");
			var planId = tr.children("td:first").text();
			
			location.href = '/czsp/workflow/showInstance/'+planId;
		})
		
		$("button[name='new']").bind("click", function() {

			$.ajax({
				url : '/czsp/user/create',
				dataType : 'json',
				type : 'GET',
				success : function(re) {
					resultPrompt(re, false);
				}
			});
		})
		
		$("button[name='ativate']").bind("click", function() {
			$.ajax({
				url : '/czsp/user/ativate',
				dataType : 'json',
				type : 'GET',
				success : function(re) {
					resultPrompt(re);
				}
			});
		})

		
		$("form").submit(function(e) {
			if ($("#createUserId").val() == "null") {
				alert("请先登录！");
				e.preventDefault();
				return;
			}
			if (!validate()) {
				e.preventDefault();
			}
		})
		
		var moduleMappingUrl = "/czsp/workflow";
		$("button[name='select']").bind("click", function() {
			var url = moduleMappingUrl + '/selectPhases';
			if($("#phases").val() != undefined)
				url = moduleMappingUrl + '/selectPhases?phaseIds='+$("#phases").val();
			
			window.open(url,"选择环节","top=100,left=400,width=250,height=200,resizable=no");
		})
	</script>
</body>
</html>