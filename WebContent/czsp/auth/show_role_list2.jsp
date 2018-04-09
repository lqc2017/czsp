<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.TreeMap"%>
<%@ page import="czsp.common.util.DicUtil"%>
<%@ page import="czsp.common.Constants"%>
<%@ page import="czsp.user.model.UserInfo"%>
<%@ page import="org.nutz.dao.entity.Record"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色列表</title>
<link href="/czsp/static/css/bootstrap/bootstrap.min.css"
	rel="stylesheet">

<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/bootstrap/bootstrap.min.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>
<%
	//Map objMap = (HashMap) request.getAttribute("obj");
	UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
	if(userInfo == null)
		userInfo = new UserInfo();
%>
<body>
	<input id="userId" type="hidden"  value="<%=userInfo.getUserId() %>"/>
	<table class="table table-bordered">
		<thead>
			<tr>
				<th>角色名</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<%
				Map<String, Record> map = (TreeMap<String, Record>) DicUtil.getInstance().getDicMap()
						.get(Constants.DIC_AHTU_ROLE_NO);
				for (String key : map.keySet()) {
					String roleId = map.get(key).get("id").toString();
			%>
			<tr>
				<td title="<%=roleId%>"><a href="/czsp/auth/pmsDetail/<%=roleId%>"><%=map.get(key).get("name")%></a></td>
				<td>
					<button name="userList">查看</button>
				</td>
			</tr>
			<%
				}
			%>
		</tbody>
	</table>


	<script type="text/javascript">
		//人员按钮绑定
		$("button[name='userList']").bind("click", function() {
			var tr = $(this).parents("tr");
			var roleId = tr.children("td:first").attr("title");
			
			window.location.href = UserURLPrefix + '/selectByRole?roleId=' + roleId;
		})
		
	</script>
</body>
</html>