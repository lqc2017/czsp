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
	<div style="width:1300px;">
		<form action="/czsp/wf/curList" method="get">
			<input type="text" name="instanceId"/>
			<input class="btn btn-default btn-sm" type="submit" value="查询"/>
		</form><hr/>
		<table class="table table-bordered">
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
					<td><button class="btn btn-warning btn-xs" name="adjust">调整</button></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	<script type="text/javascript">
		//流程提交键绑定
		$("button[name='adjust']").bind("click",function() {
			var tr = $(this).parents("tr");
			var instanceNo = tr.children("td:eq(1)").text();
			var instanceId = tr.children("td:first").text();

			window.open(WfURLPrefix+"/selectNextNode?instanceId=" + instanceId,
						"提交页面", "top=100,left=100,width=500,height=200,resizable=no");
		})
	</script>
</body>
</html>