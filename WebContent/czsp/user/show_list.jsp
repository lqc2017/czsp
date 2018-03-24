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
<script src="/czsp/static/js/common.js"></script>
</head>
<%
	Map map = (HashMap) request.getAttribute("obj");
%>
<body>
	<form action="/czsp/user/create">
		<label for="departmentId">部门</label>：
		 <select id="departmentId" name="departmentId" class="required">
		 	<option value="">请选择</option>
			<%
				List<Record> departments = (List<Record>) map.get("departments");
				for (Record department : departments) {
			%>
			<option value="<%=department.get("id")%>"><%=department.get("name")%></option>
			<%
				}
			%>
		</select> 
		
		&nbsp <label for="name">姓名</label>： <input type="text" id="name" name="name" class="required"/> 
			
		&nbsp<label for="qxId">区县</label>：
		 <select id="qxId" name="qxId" class="required">
		 	<option value="">请选择</option>
			<%
				List<Record> qxList = (List<Record>) map.get("qxList");
				for (Record qx : qxList) {
			%>
			<option value="<%=qx.get("id")%>"><%=qx.get("name")%></option>
			<%
				}
			%>
		</select>
		
		&nbsp <input type="submit" value="新建用户">
	</form>
	<br />
	<br />
	<br />
	<table border="1">
		<tr>
			<th>用户ID</th>
			<th>姓名</th>
			<th>部门名称</th>
			<th>区县</th>
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
			<td><%=DicUtil.getInstance().getItemName(Constants.DIC_QX_NO, user.getQxId())%></td>
			<td><%=user.getRoleId()%></td>
			<td><button name="grant">授予角色</button>
				<button name="del">删除</button></td>
		</tr>
		<%
			}
		%>
	</table>


	<script type="text/javascript">
		//授权键绑定
		$("button[name='grant']").bind("click", function() {
			var tr = $(this).parents("tr");
			var userId = tr.children("td:first").text();

			window.open(AuthURLPrefix + "/roleList?userId=" + userId,
					"角色列表", "top=100,left=400,width=500,height=400,resizable=no");
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
		
		//添加人员事件绑定
		$("form").submit(function(e) {
			if (!validate()) {
				e.preventDefault();
			}
		});
	</script>
</body>
</html>