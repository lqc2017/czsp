<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程</title>
<link href="/czsp/static/css/bootstrap/bootstrap.min.css"
	rel="stylesheet">

<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/bootstrap/bootstrap.min.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>
<body>
	<div style="width:1000px;">
		<form action="/czsp/wf/hisList" method="get">
			<input type="text" name="instanceNo"/>
			<input class="btn btn-default btn-sm" type="submit" value="查询"/>
		</form><hr/>
		<table class="table table-bordered">
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
						<button class="btn btn-danger btn-sm" name="del">删除</button>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<script type="text/javascript">
	</script>
</body>
</html>