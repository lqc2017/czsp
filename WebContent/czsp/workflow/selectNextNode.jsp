<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="czsp.workflow.model.WfRoute"%>
<%@ page import="czsp.workflow.model.view.VwfNodeDetail"%>
<%@ page import="czsp.common.util.DicUtil"%>
<%@ page import="czsp.common.Constants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选节点</title>
<script src="/czsp/static/js/jquery.js"></script>
</head>

<%
Map map = (HashMap)request.getAttribute("obj");
VwfNodeDetail nodeDetail = (VwfNodeDetail)map.get("nodeDetail");
%>
<body>
	<input id="instanceId" type="hidden" value="${obj.instance.instanceId}"/>
	当前环节：<%=DicUtil.getInstance().getItemName(Constants.DIC_WF_PHASE_NO,nodeDetail.getPhaseId())%>
	<br /> 下一节点：
	<select name="nextNode">
		<option value="">请选择</option>
		<%
		List<WfRoute> routes = (List<WfRoute>)map.get("routes");
		for(WfRoute route : routes){ 
			String nextNodeId = route.getRouteId().substring(0,4) + route.getNextNode();
		%>
		<option value="<%=route.getRouteId() %>">
		<%=DicUtil.getInstance().getItemName(Constants.DIC_WF_NODE_NO,nextNodeId)%>
		<%if("1".equals(route.getIsTesong())){ %>(特送)
		<%}else{ %>(默认)
		</option>
		<%}}%>
	</select> 办理人员：
	<select><option>请选择</option></select>
	<br />
	<button name="confirm">确定</button>
	<button name="cancel">取消</button>

	<script type="text/javascript">
		$("button[name='confirm']").bind("click",function() {
			if (!validate())
				return;

			var routeId = $("select[name='nextNode']").val();
			var instanceId = $("#instanceId").val();

			$.ajax({
				url : '/czsp/submit?routeId=' + routeId
						+ "&instanceId=" + instanceId,
				dataType : 'json',
				type : 'GET',
				success : function(re) {
					console.log(re.result);
					if (re.result == 'success')
						alert("success!");
					else
						alert("message:" + re.message);
					window.opener.location.reload(); 
					window.close();
				}
			});

		})

		$("button[name='cancel']").bind("click", function() {

			window.close()
		})

		function validate() {
			var nextNode = $("select[name='nextNode']").val();
			if (nextNode != "")
				return true;
			else{
				alert("请选择下一环节")
				return false;
			}
		}
	</script>
</body>
</html>