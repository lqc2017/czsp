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
<title>环节列表</title>
<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>
<%
	Map map = (HashMap) request.getAttribute("obj");
	UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
	if (userInfo == null)
		userInfo = new UserInfo();
%>
<body>
	当前区县:<%=DicUtil.getInstance().getItemName(Constants.DIC_QX_NO, userInfo.getQxId())%>
	<br/>
	<table border="1">
		<tr>
			<th>序号</th>
			<th>村镇名称</th>
			<th>村镇代码</th>
			<th>操作</th>
		</tr>
		<%
			List<Record> czList = (List<Record>) map.get("czList");
			for (int i = 0; i < czList.size(); i++) {
		%>
		<tr>
			<td title="<%=czList.get(i).get("id") %>"><%=i+1 %></td>
			<td><%=czList.get(i).get("name") %></td>
			<td><%=czList.get(i).get("code") %></td>
			<td><button name="select">选择</button></td>
		</tr>
		<%
			}
		%>
	</table>

	<script type="text/javascript">
	$("button[name='select']").bind("click", function() {
		var tr = $(this).parents("tr");
		var townId = tr.children("td:eq(0)").attr("title");
		var townName = tr.children("td:eq(1)").html();
		
		alert(townId+" "+townName);
		
		if (window.opener && !window.opener.closed) {
			//设置label和input
			var label = $("#townLabel", window.opener.document);
			label.empty();
			label.append(townName);
			
			var townIdInput = $("#townId", window.opener.document);
			townIdInput.val(townId);
			
			var townNameInput = $("#townName", window.opener.document);
			townNameInput.val(townName);
			window.close();
		}
		
		/* $.ajax({
			url : WfURLPrefix+'/test',
			dataType : 'json',
			data : {"phaseIds" : idArray.toString()},
			type : 'POST',
			success : function(re) {
				window.opener.
			}
		}); */
	})
	</script>
</body>
</html>