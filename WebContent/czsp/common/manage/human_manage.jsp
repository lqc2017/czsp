<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="czsp.common.Constants"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>字典管理</title>
<link href="/czsp/static/css/bootstrap/bootstrap.min.css"
	rel="stylesheet">

<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/bootstrap/bootstrap.min.js"></script>
<script src="/czsp/static/js/common.js"></script>
<style type="text/css">
/* Custom Styles */
ul.nav-tabs {
	width: 140px;
	margin-top: 20px;
	border-radius: 4px;
	border: 1px solid #ddd;
	box-shadow: 0 1px 4px rgba(0, 0, 0, 0.067);
}

ul.nav-tabs li {
	margin: 0;
	border-top: 1px solid #ddd;
}

ul.nav-tabs li:first-child {
	border-top: none;
}

ul.nav-tabs li a {
	margin: 0;
	padding: 8px 16px;
	border-radius: 0;
}

ul.nav-tabs li.active a, ul.nav-tabs li.active a:hover {
	color: #fff;
	background: #0088cc;
	border: 1px solid #0088cc;
}

ul.nav-tabs li:first-child a {
	border-radius: 4px 4px 0 0;
}

ul.nav-tabs li:last-child a {
	border-radius: 0 0 4px 4px;
}

ul.nav-tabs.affix {
	top: 30px; /* Set the top position of pinned element */
}
</style>
</head>
<body>
	<div class="col-xs-2" id="leftMenu">
		<ul class="nav nav-tabs nav-stacked">
			<li style="active">
				<a href="javascript:;">人员管理</a>
			</li>
			<li>
				<a href="javascript:;">角色管理</a>
			</li>
			<li>
				<a href="javascript:;">权限管理</a>
			</li>
		</ul>
	</div>

	<div class="col-xs-8" style="margin-top: 20px">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">人员管理</h3>
			</div>
			<div class="panel-body">
				<iframe id="list-frame" width="100%" frameborder="0" style="height:460px;" src="/czsp/user/list"></iframe>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		$("ul li a").bind("click", function() {
			//$("#leftMenu").find("li").removeClass("active");
			
			//$(this).closest("li").addClass("active");
			var url = $(this).attr("url");
			
			$(".panel-title").empty();
			$(".panel-title").append($(this).text());
			
			//$("#list-frame").attr("src",url);
		})
	</script>
</body>
</html>