<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html style="height:100%;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人员列表</title>
<link href="/czsp/static/css/bootstrap/bootstrap.min.css" rel="stylesheet">

<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/bootstrap/bootstrap.min.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>
<%
%>
<body style="height:100%;">
	<div style="padding-left:10%; padding-right:20%;">
		<table class="table table-hover">
			<tr>
				<th width="40%">登录名</th>
				<th width="40%">是否有效</th>
				<th width="20%">操作</th>
			</tr>
			<c:set var="pagination" value="${obj.pagination}" />
			<c:forEach var="account" items="${pagination.list}">
			
			<tr>
				<td>${account.userName}</td>
				<td><c:if test="${account.isAvailable eq '1'}">是</c:if>
				<c:if test="${account.isAvailable == '0'}">否</c:if></td>
				<td><button class="btn btn-default btn-sm" name="edit">修改</button></td>
			</tr>
			</c:forEach>
		</table>
	</div>
	
	<!-- 分页 -->
	<jsp:include page="/czsp/common/pagination.jsp" flush="true"/>

	<script type="text/javascript">
		//修改键绑定
		$("button[name='edit']").bind("click", function() {
			var tr = $(this).parents("tr");
			var userName = tr.children("td:eq(0)").text();
			
			location.href= AcctURLPrefix+'/edit/'+userName;
		})
	</script>
</body>
</html>