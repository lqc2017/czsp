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
	<ul class="nav nav-pills">
		<c:if test="${userInfo.permission.containsKey('test')}"><li><a href="javascript:;" url="/czsp/plan/list">新建计划</a></li></c:if>
		<li><a href="javascript:;" url="/czsp/plan/auditList">业务办理</a></li>
		<li class="dropdown">
			<a class="dropdown-toggle" data-toggle="dropdown" href="javascript:;">案件查询
				<span class="caret"></span>
			</a>
			<ul class="dropdown-menu">
				<li><a href="javascript:;" url="/czsp/plan/query">信息监控</a></li>
				<li class="divider"></li>
				<li><a href="javascript:;" url="/czsp/plan/retrieveList">可回收案件</a></li>
				<li><a href="javascript:;" url="/czsp/plan/toSignList">待签收案件</a></li>
			</ul>
				
		</li>
		<li class="dropdown">
			<a class="dropdown-toggle" data-toggle="dropdown" href="javascript:;">综合管理
				<span class="caret"></span>
			</a>
			<ul class="dropdown-menu">
				<li><a href="javascript:;" url="/czsp/common/humanManage">人员管理</a></li>
				<li><a href="javascript:;" url="/czsp/wf/list">流程管理</a></li>
				<li class="divider"></li>
				<li><a href="javascript:;" url="/czsp/common/dicManage">字典管理</a></li>
			</ul>
		</li>
	</ul>
	<script type="text/javascript">
		$("a").bind("click", function() {
			var url = $(this).attr("url");
			if(url)
				$("#main-frame").attr("src", url);
		})
	</script>
</body>
</html>