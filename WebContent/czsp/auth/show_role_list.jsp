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
<title>角色列表</title>
<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>
<%
	Map objMap = (HashMap) request.getAttribute("obj");
	UserInfo userInfo = (UserInfo) objMap.get("userInfo");
%>
<body>
	<input id="userId" type="hidden"  value="<%=userInfo.getUserId() %>"/>
	<table border="1">
		<thead>
			<tr>
				<th>角色名</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<%
				Map<String, Record> map = (HashMap<String, Record>) DicUtil.getInstance().getDicMap()
						.get(Constants.DIC_AHTU_ROLE_NO);
				for (String key : map.keySet()) {
					String roleId = map.get(key).get("id").toString();
			%>
			<tr>
				<td title="<%=roleId%>"><a href="javascript:;"><%=map.get(key).get("name")%></a></td>
				<td>
				<%
					if (userInfo.getRoleId() != null && userInfo.getRoleId().contains(roleId)) {
				%>
				<button name="remove">取消关联</button> <%
				 	} else {
				 %>
					<button name="associate">关联</button> <%
				 	}
				 %></td>
			</tr>
			<%
				}
			%>
		</tbody>
	</table>


	<script type="text/javascript">
		//关联键绑定
		$("button[name='associate']").bind("click", function() {
			var tr = $(this).parents("tr");
			var roleId = tr.children("td:first").attr("title");
			var userId = $("#userId").val();
	
			$.ajax({
				url : AuthURLPrefix + '/associate?roleId=' + roleId + "&userId=" + userId,
				dataType : 'json',
				type : 'GET',
				success : function(re) {
					console.log(re.result);
			        if (re.result == 'success'){
						alert("success!");
						window.opener.location.reload();
						window.location.reload();
					}else
						alert("message : " + re.message);
				}
			});
		})
		
		//取消关联键绑定
		$("button[name='remove']").bind("click", function() {
			var tr = $(this).parents("tr");
			var roleId = tr.children("td:first").attr("title");
			var userId = $("#userId").val();
	
			$.ajax({
				url : AuthURLPrefix + '/remove?roleId=' + roleId + "&userId=" + userId,
				dataType : 'json',
				type : 'GET',
				success : function(re) {
					console.log(re.result);
			        if (re.result == 'success'){
						alert("success!");
						window.opener.location.reload();
						window.location.reload();
					}else
						alert("message : " + re.message);
				}
			});
		})
	</script>
</body>
</html>