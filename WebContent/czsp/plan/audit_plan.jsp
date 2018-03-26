<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="czsp.user.model.UserInfo"%>
<%@ page import="czsp.plan.model.PlanInfo"%>
<%@ page import="czsp.plan.model.PlanApp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>实例信息</title>
<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>
<%
	Map map = (HashMap) request.getAttribute("obj");
	PlanInfo planInfo = (PlanInfo) map.get("planInfo");
	if(planInfo == null)
		planInfo = new PlanInfo();
%>
<body>
	<!-- 基本信息 -->
	<jsp:include page="/czsp/common/base/base_plan_info.jsp" flush="true" />
	
	<!-- 扩展信息填写 -->
	<jsp:include page="/czsp/plan/base/extended_information.jsp" flush="true" />
	
	<!-- 办理意见 -->
	<jsp:include page="/czsp/plan/base/audit_opinion.jsp" flush="true" />
	
	<!-- 按钮栏 -->
	<jsp:include page="/czsp/plan/base/base_button.jsp" flush="true" />

	<script type="text/javascript">
		$(function() {
			var ifm = document.getElementById("process-frame");
			ifm.height = document.documentElement.clientHeight;
		})
	</script>
</body>
</html>