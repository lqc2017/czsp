<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程</title>
<link href="/czsp/static/css/bootstrap/bootstrap.min.css"
	rel="stylesheet">

<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/bootstrap/bootstrap.min.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>
<body>
	<c:forEach var="wfPhase" items="${obj.wfPhases}">
		<ul class="list-group">
			<li class="list-group-item active">${wfPhase.phaseName}</li>
			<c:forEach var="node" items="${wfPhase.nodeList}">
				<li data-toggle="collapse" data-target="#${node.nodeId}" class="list-group-item">${node.nodeName}</li>
					<div id="${node.nodeId}" class="panel-collapse collapse">
						<div class="panel-body">
							<table class="table">
								<tbody>
									<tr>
										<th>节点id</th>
										<td>${node.nodeId}</td>
									</tr>
									
									<tr>
										<th>流程编号</th>
										<td>${node.wfCurNode}</td>
									</tr>
									
									<tr>
										<th>起始节点</th>
										<td><c:choose>
											<c:when test="${node.isStart eq '1'}">是</c:when>
											<c:otherwise>否</c:otherwise>
										</c:choose></td>
									</tr>
									
									<tr>
										<th>结束节点</th>
										<td><c:choose>
											<c:when test="${node.isEnd eq '1'}">是</c:when>
											<c:otherwise>否</c:otherwise>
										</c:choose></td>
									</tr>
									
									<tr>
										<th>节点操作角色</th>
										<td>
										<c:choose>
											<c:when test="${!node.roleId.contains(',')}">${obj.dicRole[node.roleId].name}</c:when>
											<c:otherwise>
												<c:forTokens items="${node.roleId}" delims="," var="role">
												   <c:out value="${obj.dicRole[role].name}"/>
												</c:forTokens>
											</c:otherwise>
										</c:choose>
										</td>
									</tr>
									
									<tr>
										<th>区县人员操作节点</th>
										<td><c:choose>
											<c:when test="${node.isQxOp eq '1'}">是</c:when>
											<c:otherwise>否</c:otherwise>
										</c:choose></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
			</c:forEach>
		</ul>
	</c:forEach>
	<script type="text/javascript">
		
	</script>
</body>
</html>