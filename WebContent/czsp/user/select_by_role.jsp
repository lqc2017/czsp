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
<%@ page import="org.nutz.dao.entity.Record"%>
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
	Map map = (HashMap) request.getAttribute("obj");
	UserInfo userCondition = (UserInfo) map.get("userCondition");
	if (userCondition == null)
		userCondition = new UserInfo();
%>
<body style="height:100%;">
	<div class="well well-sm"><a href="javascript:;" onclick="history.go(-1)">角色</a>->${obj.roleName}</div>
	<div>
	<form id="searchFrom" class="form-inline" action="/czsp/user/selectByRole">
		<input name="roleId" type="hidden" value="${obj.userCondition.roleId}"/>
		<input name="pageNumber" type="hidden" value="${obj.pagination.pager.pageNumber}"/>
	</form>
	<br />
	<table class="table table-hover">
		<tr>
			<th width="20">用户ID</th>
			<th width="40%">姓名</th>
			<th width="20%">区县</th>
			<th width="20%">操作</th>
		</tr>
		<c:set var="dicUtil" value="${obj.dicUtil}" />
		<c:set var="Constants" value="${obj.constants}" />
		<c:set var="pagination" value="${obj.pagination}" />
		<c:forEach var="user" items="${pagination.list}">
		
		<tr>
			<td>${user.userId}</td>
			<td>${user.name}</td>
			<td>${dicUtil.getItemName(Constants.DIC_QX_NO,user.qxId)}</td>
			<td><button  class="btn btn-danger btn-sm" name="remove">解除关联</button></td>
		</tr>
		</c:forEach>
	</table>
	</div>
	
	<!-- 分页 -->
	<jsp:include page="/czsp/common/pagination.jsp" flush="true"/>

	<script type="text/javascript">
		//解除关联键绑定
		$("button[name='remove']").bind("click", function() {
			var tr = $(this).parents("tr");
			var roleId = $("input[name='roleId']").val();
			var userId = tr.children("td:first").text();
	
			$.ajax({
				url : AuthURLPrefix + '/remove?roleId=' + roleId + "&userId=" + userId,
				dataType : 'json',
				type : 'GET',
				success : function(re) {
					resultPrompt(re,true,true,"解除关联成功");
				}
			});
		})
	</script>
</body>
</html>