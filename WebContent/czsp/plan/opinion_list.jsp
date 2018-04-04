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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>办理意见列表</title>
<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>
<body>
	<div style="width: 1000px">
		<table border='1' style="width: 1000px;">
		<c:set var="dicUtil" value="${obj.dicUtil}" />
			<thead>
				<tr>
					<th width="10%">节点</th>
					<th width="10%">办理人</th>
					<th width="65%">意见</th>
					<th width="15%">提交时间</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach var="opinion" items="${obj.opinionList}">
					<tr>
						<td>${dicUtil.getItemName(Constants.DIC_WF_NODE_NO,opinion.nodeId)}</td>
						<td>${opinion.createBy}</td>
						<td>${opinion.opinionContent}</td>
						<td><fmt:formatDate value="${opinion.updateTime}" type="both" /></td>
					</tr>

				</c:forEach>
			</tbody>
		</table>
	</div>

	<script type="text/javascript">
		
	</script>
</body>
</html>