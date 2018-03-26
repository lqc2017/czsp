<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="czsp.common.util.DicUtil"%>
<%@ page import="czsp.common.Constants"%>
<%@ page import="czsp.user.model.UserInfo"%>
<%@ page import="czsp.workflow.model.WfPhase"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>实例信息</title>
<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>
<%
	Map map = (HashMap) request.getAttribute("obj");
%>
<body>
	<input id="curInstanceId" type="hidden" value="${obj.curInstance.instanceId}"/>
	<table border="1">
		<tbody>
			<tr>
				<td>实例ID</td>
				<td>${obj.curInstance.instanceId}</td>
			</tr>
			
			<tr>
				<td>实例编号</td>
				<td>${obj.curInstance.instanceNo}</td>
			</tr>
			
			<tr>
				<td>当前节点ID</td>
				<td>${obj.curInstance.nodeId}</td>
			</tr>
			
			<tr>
				<td>当前节点名称</td>
				<td>${obj.dicWfNode[obj.curInstance.nodeId].name}</td>
			</tr>
			
			<tr>
				<td>是否可回收</td>
				<td><c:if test="${obj.curInstance.ifRetrieve eq '0' }">不可回收</c:if>
					<c:if test="${obj.curInstance.ifRetrieve eq '1' }">可回收</c:if></td>
			</tr>
			
			<tr>
				<td>是否已签收</td>
				<td><c:if test="${obj.curInstance.ifSign eq '0' }">未签收</c:if>
					<c:if test="${obj.curInstance.ifSign eq '1' }">已签收</c:if></td>
			</tr>
			
			<tr>
				<td>是否有效</td>
				<td><c:if test="${obj.curInstance.ifValid eq '0' }">无效</c:if>
					<c:if test="${obj.curInstance.ifValid eq '1' }">有效</c:if></td>
			</tr>
			
			<tr>
				<td>创建时间</td>
				<td><fmt:formatDate value="${obj.curInstance.createTime}" type="both" /></td>
			</tr>
			
			<tr>
				<td>待办用户ID</td>
				<td>${obj.curInstance.todoUserId}</td>
			</tr>
			
			<tr>
				<td>签收人ID</td>
				<td>${obj.curInstance.signUserId}</td>
			</tr>
		</tbody>
	</table>
	<br/><br/><br/>
	
	<table border="1">
		<tr>
			<th>流程ID</th>
			<th>流程编号</th>
			<th>节点ID</th>
			<th>节点名称</th>
			<th>签收人ID</th>
			<th>创建时间</th>
			<th>结束时间</th>
			<th>操作</th>
		</tr>
		<c:forEach var="instance" items="${obj.hisInstances}">
			<tr>
				<td>${instance.instanceId}</td>
				<td>${instance.instanceNo}</td>
				<td>${instance.nodeId}</td>
				<td>${obj.dicWfNode[instance.nodeId].name}</td>
				<td>${instance.signUserId}</td>
				<td><fmt:formatDate value="${instance.createTime}" type="both" /></td>
				<td><fmt:formatDate value="${instance.finishTime}" type="both" /></td>
				<td>
				</td>
			</tr>
		</c:forEach>
	</table>

	<script type="text/javascript">
	</script>
</body>
</html>