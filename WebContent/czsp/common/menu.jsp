<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>计划</title>
<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>
<body>
	<ul class="nav nav-pills">
		<li class="active"><a href="javascript:;" url="/czsp/plan/list">新建计划</a></li>
		<li><a href="#">业务办理</a></li>
		<li><a href="#">查询统计</a></li>
		<li class="dropdown"><a class="dropdown-toggle"
			data-toggle="dropdown" href="javascript:;" url="/czsp/plan/query">案件查询<span
				class="caret"></span>
		</a>
			<ul class="dropdown-menu">
				<li><a href="#">待办案件</a></li>
				<li class="divider"></li>
				<li><a href="#">逾期</a></li>
			</ul></li>
	</ul>
	<script type="text/javascript">
		$("a").bind("click", function() {
			var url = $(this).attr("url");
			$("#main-frame").attr("src", url);
		})
	</script>
</body>
</html>