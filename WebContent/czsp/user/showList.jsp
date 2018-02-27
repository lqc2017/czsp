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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人员</title>
<script src="/czsp/static/js/jquery.js"></script>
</head>
<%
	Map map = (HashMap) request.getAttribute("obj");
%>
<body>
	<form action="/czsp/user/create">
		部门： <select name="departmentId">
			<%
				List<Record> departments = (List<Record>) map.get("departments");
				for (Record department : departments) {
			%>
			<option value="<%=department.get("id")%>"><%=department.get("name")%></option>
			<%
				}
			%>
		</select> &nbsp 姓名： <input type="text" name="name" /> &nbsp
		<input type="submit" value="新建用户">
	</form>
	<br />
	<br />
	<br />
	<table border="1">
		<tr>
			<th>用户ID</th>
			<th>姓名</th>
			<th>部门名称</th>
			<th>角色名称</th>
			<th>操作</th>
		</tr>
		<%
			List<UserInfo> users = (List<UserInfo>) map.get("users");
			for (UserInfo user : users) {
		%>
		<tr>
			<td><%=user.getUserId()%></td>
			<td><%=user.getName()%></td>
			<td><%=DicUtil.getInstance().getItemName(Constants.DIC_AHTU_DEPT_NO, user.getDepartmentId())%></td>
			<td><%=user.getRoleId()%></td>
			<td><button name="grant">授予角色</button>
				<button name="del">删除</button></td>
		</tr>
		<%
			}
		%>
	</table>


	<script type="text/javascript">
		$("button[name='new']").bind("click", function() {

			$.ajax({
				url : '/czsp/user/create',
				dataType : 'json',
				type : 'GET',
				success : function(re) {
					console.log(re.result);
					if (re.result == 'success')
						location.reload();
				}
			});
		})
	</script>
</body>
</html>