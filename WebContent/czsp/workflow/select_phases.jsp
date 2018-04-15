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
<%@ page import="czsp.workflow.model.WfPhase"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>环节列表</title>
<link href="/czsp/static/css/bootstrap/bootstrap.min.css"
	rel="stylesheet">

<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/bootstrap/bootstrap.min.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>
<%
	Map map = (HashMap) request.getAttribute("obj");
	String phaseIds = "";
	if(map.get("phaseIds") != null)
		phaseIds = map.get("phaseIds").toString();
	System.out.println(phaseIds);
%>
<body>
	<table class="table table-bordered">
		<tr>
			<th></th>
			<th>序号</th>
			<th>环节名称</th>
		</tr>
		<%
			List<WfPhase> phases = (List<WfPhase>) map.get("phases");
			for (int i = 0; i < phases.size(); i++) {
		%>
		<tr>
			<td><input name="phaseId" type="checkbox" value="<%=phases.get(i).getPhaseId() %>" 
			<%
			if(phaseIds.contains(phases.get(i).getPhaseId())){ %>checked<% }%>/></td>
			<td><%=i+1 %></td>
			<td><%=phases.get(i).getPhaseName() %></td>
		</tr>
		<%
			}
		%>
	</table>
	<button class="btn btn-success btn-sm" style="float:right;" name="confirm">确定</button>


	<script type="text/javascript">
	//确认环节键绑定
	$("button[name='confirm']").bind("click", function() {
		var idArray = new Array();
		var nameArray = new Array();
		$("input[name='phaseId']:checkbox:checked").each(function(){ 
			idArray.push($(this).val());
			var tr = $(this).parents("tr");
			nameArray.push(tr.children("td:last").text());
		});
		
		if (window.opener && !window.opener.closed) {
			//设置label和input
			var label = $("#phasesLabel", window.opener.document);
			label.empty();
			label.append(nameArray.toString());
			
			var input = $("#phases", window.opener.document);
			input.val(idArray.toString());
			window.close();
		}
		
	})
	</script>
</body>
</html>