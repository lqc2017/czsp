<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>村镇规划编制审批系统</title>
<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>
<body>
	<jsp:include page="/czsp/common/base/cur_user_message.jsp" flush="true"/>
	<jsp:include page="/czsp/common/menu.jsp" flush="true" />
	<iframe id="main-frame" width="100%" frameborder="0" src="" height="625px"></iframe>

	<script type="text/javascript">
		/* $(function() {
			var ifm = document.getElementById("main-frame");
			ifm.height = document.documentElement.clientHeight;
		}) */
	</script>
</body>
</html>