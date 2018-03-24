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
	
	<!-- 流程信息 -->
	<iframe id="external-frame" width="100%" frameborder="0" src="/czsp/wf/showInstance/<%=planInfo.getPlanId() %>"></iframe>

	<script type="text/javascript">
	
		function setIframeHeight(iframe) {
			if (iframe) {
				var iframeWin = iframe.contentWindow
						|| iframe.contentDocument.parentWindow;
				if (iframeWin.document.body) {
					iframe.height = iframeWin.document.documentElement.scrollHeight
							|| iframeWin.document.body.scrollHeight;
				}
			}
		};

		window.onload = function() {
			setIframeHeight(document.getElementById('external-frame'));
		};
	</script>
</body>
</html>