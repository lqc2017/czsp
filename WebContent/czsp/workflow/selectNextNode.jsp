<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="czsp.workflow.model.WfRoute"%>
<%@ page import="czsp.workflow.model.view.VwfNodeDetail"%>
<%@ page import="czsp.workflow.model.WfHisInstance"%>
<%@ page import="czsp.common.util.DicUtil"%>
<%@ page import="czsp.common.Constants"%>
<%@ page import="czsp.user.model.UserInfo"%>
<%@ page import="org.nutz.dao.entity.Record"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选节点</title>
<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>

<%
Map map = (HashMap)request.getAttribute("obj");
VwfNodeDetail nodeDetail = (VwfNodeDetail)map.get("nodeDetail");
WfHisInstance hisInstance = (WfHisInstance)map.get("hisInstance");
%>
<body>
	<input id="instanceId" type="hidden" value="${obj.instance.instanceId}"/>
	<%
	UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
	if(userInfo!=null){ %>
	当前人员：<%=userInfo.getName() %><br/>
	<%} %>
	当前环节：<%=DicUtil.getInstance().getItemName(Constants.DIC_WF_PHASE_NO,nodeDetail.getPhaseId())%>&nbsp&nbsp
	当前节点：<%=DicUtil.getInstance().getItemName(Constants.DIC_WF_NODE_NO,nodeDetail.getNodeId())%>
	<br /> <label for="nextNode">下一节点</label>：
	<select id="nextNode" name="nextNode" class="required">
		<option value="">请选择</option>
		<%
			List<WfRoute> routes = (List<WfRoute>) map.get("routes");
			for (WfRoute route : routes) {
				String nextNodeId = route.getRouteId().substring(0, 4) + route.getNextNode();
		%>
		<option value="<%=route.getRouteId()%>">
			<%=DicUtil.getInstance().getItemName(Constants.DIC_WF_NODE_NO, nextNodeId)%>
			<%
				if ("1".equals(route.getIsTesong())) {
			%>(特送)
			<%
				} else {
			%>(默认)
		</option>
		<%
			}
			}
			if (hisInstance != null && !hisInstance.getNodeId().endsWith("00")) {
		%>
		<option id="retreat" value="<%=hisInstance.getInstanceId() %>"><%=DicUtil.getInstance().getItemName(Constants.DIC_WF_NODE_NO, hisInstance.getNodeId())%>(回退)
		</option>
		<%
			}
		%>
	</select> <label for="nextUser">办理人员：</label>
	<select id="nextUser" name="nextUser" class="required"><option>请选择</option></select>
	<br />
	<button name="confirm">确定</button>
	<button name="cancel">取消</button>

	<script type="text/javascript">
		$("button[name='confirm']").bind("click",function() {
			if (!validate())
				return;

			var param = $("select[name='nextNode']").val();
			var instanceId = $("#instanceId").val();
			var nextUserId = $("select[name='nextUser']").val();
			var target = "";
			
			if($("select[name='nextNode']").find("option:selected").attr("id")=="retreat")
				target = "/czsp/retreat?hisInstanceId=" + param + "&instanceId=" + instanceId + "&todoUserId=" + nextUserId;
			else
				target = "/czsp/submit?routeId=" + param + "&instanceId=" + instanceId + "&todoUserId=" + nextUserId;

			$.ajax({
				url : target,
				dataType : 'json',
				type : 'GET',
				success : function(re) {
					console.log(re.result);
					if (re.result == 'success')
						window.opener.location.reload();
					else
						alert("message : " + re.message);
					window.close();
				} 
			});

		})

		$("button[name='cancel']").bind("click", function() {

			window.close()
		})
	</script>
</body>
</html>