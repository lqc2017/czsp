<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程</title>
<script src="/czsp/static/js/jquery.js"></script>
</head>
<%@ page import="czsp.common.util.DicUtil"%>
<%@ page import="czsp.common.Constants"%>
<body>
	<ul>
		<c:forEach var="node" items="${obj.wfNodes}">
			<li>${node.nodeName}</li>
		</c:forEach>
	</ul>
	<button name="new">新建流程</button>

	<table border="1">
		<tr>
			<th>流程ID</th>
			<th>流程编号</th>
			<th>节点ID</th>
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
				<td>${instance.nodeId}</td>
				<td>${instance.ifRetrieve}</td>
				<td>${instance.ifSign}</td>
				<td>${instance.ifValid}</td>
				<td><fmt:formatDate value="${instance.createTime}" type="both"/></td>
				<td><button name="submit">提交</button>
					<button name="del">删除</button></td>
			</tr>
		</c:forEach>
	</table>
	<br/>
	<table border="1">
		<tr>
			<th>流程ID</th>
			<th>流程编号</th>
			<th>节点ID</th>
			<th>创建时间</th>
			<th>结束时间</th>
			<th>操作</th>
		</tr>
		<c:forEach var="instance" items="${obj.wfHisInstances}">
			<tr>
				<td>${instance.instanceId}</td>
				<td>${instance.instanceNo}</td>
				<td>${instance.nodeId}</td>
				<td><fmt:formatDate value="${instance.createTime}" type="both"/></td>
				<td><fmt:formatDate value="${instance.finishTime}" type="both"/></td>
				<td>
					<button name="delHis">删除</button></td>
			</tr>
		</c:forEach>
	</table>

	<script type="text/javascript">
		$("button[name='new']").bind("click", function() {

			$.ajax({
				url : '/czsp/createInstance',
				dataType : 'json',
				type : 'GET',
				success : function(re) {
					console.log(re.result);
					if (re.result == 'success')
						location.reload();
				}
			});
		})

		$("button[name='submit']").bind("click", function() {
			var tr = $(this).parents("tr");
			var instanceNo = tr.children("td:eq(1)").text();
			var instanceId = tr.children("td:first").text();

			window.open("/czsp/selectNextNode?instanceId=" + instanceId,"","top=100,left=100,width=500,height=200");
		})

		$("button[name='del']").bind("click", function() {
			var tr = $(this).parents("tr");
			var instanceId = tr.children("td:first").text();

			$.ajax({
				url : '/czsp/deleteInstance/' + instanceId,
				dataType : 'json',
				type : 'GET',
				success : function(re) {
					console.log(re.result);
					if (re.result == 'success')
						location.reload();
				}
			});
		})

		$("button[name='edt']")
				.bind(
						"click",
						function() {
							var tr = $(this).parents("tr");
							var uId = tr.children("td:first").text();
							var uName = tr.children("td:eq(1)").text();
							tr.children("td").remove();
							tr.append("<td>" + uId + "</td>");
							tr
									.append("<td><input type='text' name='name' value='"+uName+"'/></td>");
							tr
									.append("<td><button name='upd' uId='"+uId+"'>update</button></td>");

							tr.find("button").bind("click", {
								id : uId
							}, update)
						})

		$("button[name='add']").bind("click", function() {
			var uName = $("input[name='newName']").val();
			var data = {
				name : uName
			}
			$.ajax({
				url : '/insert',
				"data" : data,
				dataType : 'json',
				type : 'POST',
				success : function(re) {
					console.log(re.result);
					if (re.result == 'success')
						location.reload();
				}
			});
		})

		function update(event) {
			var tr = $(this).parents("tr");
			var uName = tr.find("input[name='name']").val();
			var data = {
				id : event.data.id,
				name : uName
			}
			console.log(data);

			$.ajax({
				url : '/update',
				"data" : data,
				dataType : 'json',
				type : 'POST',
				success : function(re) {
					console.log(re.result);
					if (re.result == 'success')
						location.reload();
				}
			});
		}
	</script>
</body>
</html>