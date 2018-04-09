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
	<div>
	<br />
	<form id="searchFrom" class="form-inline" action="/czsp/user/list">
		<div class="form-group">
			<label class="control-label" for="departmentId">部门</label>：
				<select id="departmentId" class="form-control" name="departmentId">
					<option value="">请选择</option>
					<%
						List<Record> departments = (List<Record>) map.get("departments");
						for (Record department : departments) {
					%>
					<option value="<%=department.get("id")%>"
						<%if (userCondition.getDepartmentId() != null && department.get("id").equals(userCondition.getDepartmentId())) {%>
						selected="selected" <%}%>><%=department.get("name")%></option>
					<%
						}
					%>
				</select>
		</div>
		
		<div class="form-group">
			<label class="control-label" for="name" >姓名</label>： 
			<input class="form-control" type="text" id="name" name="name" size="5" value="${obj.userCondition.name}" />
		</div>
		
		<div class="form-group">
			<label class="control-label" for="qxId">区县</label>：
			<select class="form-control" id="qxId" name="qxId">
				<option value="">请选择</option>
				<%
					List<Record> qxList = (List<Record>) map.get("qxList");
					for (Record qx : qxList) {
				%>
				<option value="<%=qx.get("id")%>"
					<%if (userCondition.getQxId() != null && qx.get("id").equals(userCondition.getQxId())) {%>
					selected="selected" <%}%>><%=qx.get("name")%></option>
				<%
					}
				%>
			</select>
		</div>
		
		<input name="pageNumber" type="hidden" value="${obj.pagination.pager.pageNumber}"/>
		
			&nbsp <button name="search" type="button">查询</button>
			&nbsp <button name="reset" type="button">重置</button>
	</form>
	<br /><button style="float:right" name="add" type="button">新增</button>
	<table class="table table-hover">
		<tr>
			<th>用户ID</th>
			<th>姓名</th>
			<th>部门名称</th>
			<th>区县</th>
			<th>角色名称</th>
			<th>操作</th>
		</tr>
		<c:set var="dicUtil" value="${obj.dicUtil}" />
		<c:set var="Constants" value="${obj.constants}" />
		<c:set var="pagination" value="${obj.pagination}" />
		<c:forEach var="user" items="${pagination.list}">
		
		<tr>
			<td>${user.userId}</td>
			<td>${user.name}</td>
			<td>${dicUtil.getItemName(Constants.DIC_AHTU_DEPT_NO,user.departmentId)}</td>
			<td>${dicUtil.getItemName(Constants.DIC_QX_NO,user.qxId)}</td>
			<td>${user.roleId}</td>
			<td><button name="grant">授予角色</button>
				<button name="del">删除</button></td>
		</tr>
		</c:forEach>
	</table>
	</div>
	
	<!-- 分页 -->
	<jsp:include page="/czsp/common/pagination.jsp" flush="true"/>

	<script type="text/javascript">
		initPage();
		
		//授权键绑定
		$("button[name='grant']").bind("click", function() {
			var tr = $(this).parents("tr");
			var userId = tr.children("td:first").text();

			window.open(AuthURLPrefix + "/roleList?userId=" + userId,
					"角色列表", "top=100,left=400,width=450,height=400,resizable=no");
		})
		
		//删除人员键绑定
		$("button[name='del']").bind("click", function() {
			var tr = $(this).parents("tr");
			var userId = tr.children("td:first").text();

			$.ajax({
				url : UserURLPrefix + '/delete/' + userId,
				dataType : 'json',
				type : 'GET',
				success : function(re) {
					console.log(re.result);
					if (re.result == 'success')
						location.reload();
				}
			});
		})
		
		//添加人员按钮绑定
		$("button[name='add']").bind("click",function(e) {
			location.href = UserURLPrefix + "/add";
		});
	</script>
</body>
</html>