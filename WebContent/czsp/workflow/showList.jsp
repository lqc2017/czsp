<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程</title>
<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>
<%@ page import="czsp.common.util.DicUtil"%>
<%@ page import="czsp.common.Constants"%>
<%@ page import="java.util.Enumeration"%>
<%@ page import="czsp.user.model.UserInfo"%>
<body>
	<ul>
		<c:forEach var="node" items="${obj.wfNodes}">
			<li>${node.nodeName}</li>
		</c:forEach>
	</ul>
	<br/>
	<%
	UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
	if(userInfo!=null){ %>
	当前人员：<%=userInfo.getName() %>(<%=userInfo.getUserId() %>)<br/>
	<%} else{
		userInfo = new UserInfo();	
	}
	%>
	<button name="new">新建流程</button>
	&nbsp
	<button name="activate">激活</button>
	&nbsp
	<button name="clear">清除(session)</button>
	&nbsp
	<button name="change">变更用户</button>

	<table border="1">
		<tr>
			<th>流程ID</th>
			<th>流程编号</th>
			<th>节点ID</th>
			<th>待办人ID</th>
			<th>签收人ID</th>
			<th>是否可回收</th>
			<th>是否已签收</th>
			<th>是否有效</th>
			<th>创建时间</th>
			<th>操作</th>
		</tr>
		<c:forEach var="instance" items="${obj.wfCurInstances}">
			<tr>
				<td>${instance.instanceId}</td>
				<td>${instance.instanceNo}</td>
				<td>${obj.dicWfNode[instance.nodeId].name}</td>
				<td>${instance.todoUserId}</td>
				<td>${instance.signUserId}</td>
				<td>${instance.ifRetrieve}</td>
				<td>${instance.ifSign}</td>
				<td>${instance.ifValid}</td>
				<td><fmt:formatDate value="${instance.createTime}" type="both" /></td>
				<td>
				<c:if test="${userInfo.userId != null && obj.dicWfNode[instance.nodeId].first ne '1' &&
								((instance.ifSign eq '0' && fn:contains(instance.todoUserId, userInfo.userId))
								||(instance.ifSign eq '1' && instance.signUserId eq userInfo.userId)
								|| instance.todoUserId == null) }">
				<button name="submit">提交</button>
					<button name="del">删除</button></c:if></td>
			</tr>
		</c:forEach>
	</table>
	<br />
	<table border="1">
		<tr>
			<th>流程ID</th>
			<th>流程编号</th>
			<th>节点ID</th>
			<th>签收人ID</th>
			<th>创建时间</th>
			<th>结束时间</th>
			<th>操作</th>
		</tr>
		<c:forEach var="instance" items="${obj.wfHisInstances}">
			<tr>
				<td>${instance.instanceId}</td>
				<td>${instance.instanceNo}</td>
				<td>${obj.dicWfNode[instance.nodeId].name}</td>
				<td>${instance.signUserId}</td>
				<td><fmt:formatDate value="${instance.createTime}" type="both" /></td>
				<td><fmt:formatDate value="${instance.finishTime}" type="both" /></td>
				<td>
					<button name="delHis">删除</button>
				</td>
			</tr>
		</c:forEach>
	</table>

	<script type="text/javascript">
		$("button[name='new']").bind("click", function() {

			$.ajax({
				url : '/czsp/workflow/createInstance',
				dataType : 'json',
				type : 'GET',
				success : function(re) {
					resultPrompt(re,false);
				}
			});
		})

		$("button[name='activate']").bind("click", function() {
			$.ajax({
				url : '/czsp/user/activate',
				dataType : 'json',
				type : 'GET',
				success : function(re) {
					resultPrompt(re);
				}
			});
		})
		
		$("button[name='clear']").bind("click", function() {
			$.ajax({
				url : '/czsp/user/clear',
				dataType : 'json',
				type : 'GET',
				success : function(re) {
					resultPrompt(re,false);
				}
			});
		})
		
		$("button[name='change']").bind("click", function() {
			window.open('/czsp/user/change',"选择人员",
					"top=100,left=400,width=500,height=400,resizable=no");
		})

		$("button[name='submit']").bind("click",function() {
			var tr = $(this).parents("tr");
			var instanceNo = tr.children("td:eq(1)").text();
			var instanceId = tr.children("td:first").text();

			window.open("/czsp/workflow/selectNextNode?instanceId=" + instanceId,
						"提交页面", "top=100,left=100,width=500,height=200,resizable=no");
		})

		$("button[name='del']").bind("click", function() {
			var tr = $(this).parents("tr");
			var instanceId = tr.children("td:first").text();

			$.ajax({
				url : '/czsp/workflow/deleteInstance/' + instanceId,
				dataType : 'json',
				type : 'GET',
				success : function(re) {
					resultPrompt(re,false);
				}
			});
		})
	</script>
</body>
</html>