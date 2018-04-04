<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="czsp.common.Constants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>字典列表</title>
<link href="/czsp/static/css/bootstrap/bootstrap.min.css"
	rel="stylesheet">

<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/bootstrap/bootstrap.min.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>
<body>
	<table class="table table-bordered">
		<thead>
			<tr>
				<th>字典主键</th>
				<th>名称</th>
				<th>字典代码</th>
			</tr>
		</thead>
		<c:forEach var="record" items="${obj.dicList}">
			<tr>
				<td>${record.get("id")}</td>
				<td>${record.get("name")}</td>
				<td>${record.get("code")}</td>
			</tr>
		</c:forEach>
	</table>

	<script type="text/javascript">
		
	</script>
</body>
</html>