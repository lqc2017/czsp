<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="czsp.plan.model.PlanInfo"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>实例信息</title>
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
	<div style="padding-left:10%; padding-right:20%;">
		<!-- 基本信息 -->
		<jsp:include page="/czsp/common/base/base_plan_info.jsp" flush="true" />
		
		<!-- 扩展信息填写 -->
		<jsp:include page="/czsp/plan/base/extended_information.jsp" flush="true" />
		
		<!-- 办理意见 -->
		<jsp:include page="/czsp/plan/base/audit_opinion.jsp" flush="true" />
		
		<!-- 按钮栏 -->
		<jsp:include page="/czsp/plan/base/base_button.jsp" flush="true" />
	</div>
	<script type="text/javascript">
		$(function() {
			var ifm = document.getElementById("process-frame");
			ifm.height = document.documentElement.clientHeight;
		})
	</script>
</body>
</html>