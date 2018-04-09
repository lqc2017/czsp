<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="czsp.user.model.UserInfo"%>
<%@ page import="czsp.plan.model.PlanInfo"%>
<%@ page import="czsp.plan.model.PlanApp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>详细信息</title>
<link href="/czsp/static/css/bootstrap/bootstrap.min.css"
	rel="stylesheet">

<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/bootstrap/bootstrap.min.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>
<%
	Map map = (HashMap) request.getAttribute("obj");
	PlanInfo planInfo = (PlanInfo) map.get("planInfo");
	if(planInfo == null)
		planInfo = new PlanInfo();
%>
<body>
	<div style="padding-left:10%; padding-right:20%; padding-top:20px;">
		<ul id="myTab" class="nav nav-tabs">
			<li class="active"><a href="#baseInformation" data-toggle="tab">案件信息</a>
			</li>
			<li><a href="#auditOpinion" data-toggle="tab">办理意见</a>
			</li>
			<c:if test="${userInfo.permission.containsKey('20001003')}">
				<li><a href="#wfInformation" data-toggle="tab">流程信息</a></li>
			</c:if>
			<li><a href="#resourceTree" data-toggle="tab">资源树</a>
			</li>
		</ul>
		<div id="myTabContent" class="tab-content">
			<div class="tab-pane fade in active" id="baseInformation">
				<!-- 基本信息 -->
				<jsp:include page="/czsp/common/base/base_plan_info.jsp" flush="true" />
			</div>
			<div class="tab-pane fade" id="auditOpinion">
				<!-- 办理意见 -->
				<jsp:include page="/czsp/plan/opinion_list.jsp" flush="true" />
			</div>
			<div class="tab-pane fade" id="resourceTree">
				<!-- 资源树 -->
			</div>
			<c:if test="${userInfo.permission.containsKey('20001003')}">
				<div class="tab-pane fade" id="wfInformation">
					<!-- 流程信息 -->
					<iframe id="process-frame" width="100%" frameborder="0" src="/czsp/wf/showInstance/<%=planInfo.getPlanId() %>"></iframe>
				</div>
			</c:if>
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			var ifm = document.getElementById("process-frame");
			ifm.height = document.documentElement.clientHeight;
		})
	</script>
</body>
</html>