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
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人员列表</title>
<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>
<%
	Map map = (HashMap) request.getAttribute("obj");
	UserInfo userCondition = (UserInfo) map.get("userCondition");
	if (userCondition == null)
		userCondition = new UserInfo();
%>
<body>
	<form action="/czsp/user/change">
		<label for="departmentId">部门</label>： <select id="departmentId" name="departmentId" class="required">
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
		</select> &nbsp <label for="name">姓名</label>： 
		
		<input type="text" id="name" name="name" size="5" class="required" value="${obj.userInfo.name}" /> 
		
		&nbsp<label for="qxId">区县</label>： <select id="qxId" name="qxId" class="required">
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
		
			&nbsp <input type="submit"value="查询">
	</form>
	<br />
	<br />
	<br />
	<table border="1">
		<tr>
			<th>姓名</th>
			<th>部门名称</th>
			<th>操作</th>
		</tr>
		<%
			List<UserInfo> users = (List<UserInfo>) map.get("users");
			for (UserInfo user : users) {
		%>
		<tr>
			<td title="<%=user.getUserId() %>"><%=user.getName()%></td>
			<td><%=DicUtil.getInstance().getItemName(Constants.DIC_AHTU_DEPT_NO, user.getDepartmentId())%></td>
			<td><button name="select">选择</button></td>
		</tr>
		<%
			}
		%>
	</table>
	
	<script type="text/javascript">
		//选择人员键绑定
		$("button[name='select']").bind("click", function() {
			var tr = $(this).parents("tr");
			var userId = tr.children("td:first").attr("title");

			$.ajax({
				url : UserURLPrefix + '/select?userId='+userId,
				dataType : 'json',
				type : 'GET',
				success : function(re) {
					console.log(re.result);
					if (re.result == 'success')
						window.opener.location.reload();
					else
						alert(re.message);
					window.close();
				}
			});
		})
		
	</script>
</body>
</html>