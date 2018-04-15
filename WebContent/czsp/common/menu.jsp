<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>计划</title>
<link href="/czsp/static/css/bootstrap/bootstrap.min.css" rel="stylesheet">

<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/bootstrap/bootstrap.min.js"></script>
<script src="/czsp/static/js/common.js"></script>

</head>
<body>
	<nav class="navbar navbar-default" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">村庄规划</a>
			</div>
			<div>
				<ul class="nav navbar-nav">
					<c:if test="${userInfo.permission.containsKey('1000')}">
						<li><a href="javascript:;"
							url="/czsp/plan/list?qxId=${userInfo.qxId}">新建计划</a></li>
					</c:if>
					<c:if test="${userInfo.permission.containsKey('2000')}">
						<li><a href="javascript:;" url="/czsp/plan/auditList">业务办理</a></li>
					</c:if>

					<c:if test="${userInfo.permission.containsKey('3000')}">
						<li class="dropdown"><a class="dropdown-toggle"
							data-toggle="dropdown" href="javascript:;">案件查询 <span
								class="caret"></span>
						</a>
							<ul class="dropdown-menu">
								<c:if test="${userInfo.permission.containsKey('3100')}">
									<li><a href="javascript:;" url="/czsp/plan/query">信息监控</a></li>
									<li class="divider"></li>
								</c:if>
								<c:if test="${userInfo.permission.containsKey('3200')}">
									<li><a href="javascript:;" url="/czsp/plan/retrieveList">可回收案件</a></li>
								</c:if>
								<c:if test="${userInfo.permission.containsKey('3300')}">
									<li><a href="javascript:;" url="/czsp/plan/toSignList">待签收案件</a></li>
								</c:if>
							</ul></li>
					</c:if>

					<c:if test="${userInfo.permission.containsKey('4000')}">
						<li class="dropdown"><a class="dropdown-toggle"
							data-toggle="dropdown" href="javascript:;">综合管理 <span
								class="caret"></span>
						</a>
							<ul class="dropdown-menu">
								<c:if test="${userInfo.permission.containsKey('4400')}">
									<li><a href="javascript:;" url="/czsp/account/list">用户管理</a></li>
									<li class="divider"></li>
								</c:if>
								<c:if test="${userInfo.permission.containsKey('4100')}">
									<li><a href="javascript:;" url="/czsp/common/humanManage">人员管理</a></li>
								</c:if>
								<c:if test="${userInfo.permission.containsKey('4200')}">
									<li><a href="javascript:;" url="/czsp/common/wfManage">流程管理</a></li>
								</c:if>
								<c:if test="${userInfo.permission.containsKey('4300')}">
									<li class="divider"></li>
									<li><a href="javascript:;" url="/czsp/common/dicManage">字典管理</a></li>
								</c:if>
							</ul></li>
					</c:if>
				</ul>

				<jsp:include page="/czsp/common/base/cur_user_message.jsp" flush="true" />
			</div>
		</div>
	</nav>
	
	<script type="text/javascript">
		$("a").bind("click", function() {
			var url = $(this).attr("url");
			$("li").removeClass("active");
			$(this).closest("li").addClass("active");
			if (url)
				$("#main-frame").attr("src", url);
		})
	</script>
</body>
</html>