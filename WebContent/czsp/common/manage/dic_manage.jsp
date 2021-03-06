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
			<li>
				<a href="javascript:;" data-toggle="collapse" data-target="#auth">权限字典</a>
				<div id="auth" class="collapse">
					<ul class="nav sub">
						<li><a href="javascript:;" dicId="<%=Constants.DIC_AHTU_ROLE_NO %>">&nbsp&nbsp角色</a></li>
						<li><a href="javascript:;" dicId="<%=Constants.DIC_AHTU_DEPT_NO %>">&nbsp&nbsp部门</a></li>
					</ul>
				</div>
			</li>
			<li>
				<a href="javascript:;" data-toggle="collapse" data-target="#wf">流程字典</a>
				<div id="wf" class="collapse">
					<ul class="nav sub">
						<li><a href="javascript:;" dicId="<%=Constants.DIC_WF_PHASE_NO %>">&nbsp&nbsp环节</a></li>
						<li><a href="javascript:;" dicId="<%=Constants.DIC_WF_NODE_NO %>">&nbsp&nbsp节点</a></li>
					</ul>
				</div>
			</li>
			<li>
				<a href="javascript:;" data-toggle="collapse" data-target="#qx">区县字典</a>
				<div id="qx" class="collapse">
					<ul class="nav sub">
						<li><a href="javascript:;" dicId="<%=Constants.DIC_QX_NO %>">&nbsp&nbsp区县</a></li>
						<li><a href="javascript:;" dicId="<%=Constants.DIC_QX_CZ_NO %>">&nbsp&nbsp村镇</a></li>
					</ul>
				</div>
			</li>
		</ul>
	</div>

	<div class="col-xs-8" style="margin-top: 20px">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">字典管理</h3>
			</div>
			<div class="panel-body">
				<iframe id="list-frame" width="100%" frameborder="0" style="height:460px;"></iframe>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		$("div.collapse li a").bind("click", function() {
			$("#leftMenu").find("li").removeClass("active");
			
			$(this).closest("li").addClass("active");
			var dicId = $(this).attr("dicId");
			
			$(".panel-title").empty();
			$(".panel-title").append("字典管理("+$(this).text().trim()+")");
			
			$("#list-frame").attr("src",CommURLPrefix+"/dicList/"+dicId);
		})
	</script>
</body>
</html>