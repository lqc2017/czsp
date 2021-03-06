<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="czsp.common.util.DicUtil"%>
<%@ page import="czsp.common.Constants"%>
<%@ page import="czsp.workflow.model.WfPhase"%>
<%@ page import="czsp.plan.model.view.VplanWfDetail"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>待办列表</title>
<link href="/czsp/static/css/bootstrap/bootstrap.min.css"
	rel="stylesheet">

<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/bootstrap/bootstrap.min.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>
<%
	Map map = (HashMap) request.getAttribute("obj");
%>
<body>
	<div style="padding-left:10%; padding-right:20%;">
		<table class="table table-striped">
			<thead>
				<tr>
					<th>案件名称</th>
					<%
						List<WfPhase> phases = (List<WfPhase>) map.get("phases");
						for (int i = 0; i < phases.size(); i++) {
					%>
						<th><%=phases.get(i).getPhaseName()%></th>
					<%}%>
				</tr>
			</thead>
			
			<tbody>
				<%
					List<VplanWfDetail> infoList = (List<VplanWfDetail>) map.get("infoList");
					for (int i = 0; i < infoList.size(); i++) {
				%>
					<tr>
						<td><%=infoList.get(i).getPlanName() %></td>
						<%
							for (int j = 0; j < phases.size(); j++) {
						%>
							<td>
								<%if(infoList.get(i).getCurPhase().equals(phases.get(j).getPhaseId())){%>
								<a href="/czsp/plan/audit/<%=infoList.get(i).getPlanId() %>">
									<%=DicUtil.getInstance().getItemName(Constants.DIC_WF_NODE_NO, infoList.get(i).getCurNode())%>
								</a>
								<%}else{ %>/<%}%>
							</td>
						<%}%>
					</tr>
				<%
					}
				%>
			</tbody>
		</table>
	</div>

	<script type="text/javascript">
	</script>
</body>
</html>