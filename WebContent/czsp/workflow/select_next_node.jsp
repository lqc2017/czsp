<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="czsp.workflow.model.WfRoute"%>
<%@ page import="czsp.workflow.model.view.VwfNodeDetail"%>
<%@ page import="czsp.workflow.model.WfHisInstance"%>
<%@ page import="czsp.workflow.model.WfCurInstance"%>
<%@ page import="czsp.common.util.DicUtil"%>
<%@ page import="czsp.common.Constants"%>
<%@ page import="czsp.user.model.UserInfo"%>
<%@ page import="org.nutz.dao.entity.Record"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选节点</title>
<link href="/czsp/static/css/bootstrap/bootstrap.min.css"
	rel="stylesheet">

<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/bootstrap/bootstrap.min.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>

<%
Map map = (HashMap)request.getAttribute("obj");
VwfNodeDetail nodeDetail = (VwfNodeDetail)map.get("nodeDetail");
WfHisInstance hisInstance = (WfHisInstance)map.get("hisInstance");
WfCurInstance curInstance = (WfCurInstance)map.get("instance");
%>
<body>
	<input id="curInstanceId" type="hidden" value="${obj.instance.instanceId}"/>
	<div class="well well-sm">
		当前环节：<%=DicUtil.getInstance().getItemName(Constants.DIC_WF_PHASE_NO,nodeDetail.getPhaseId())%>&nbsp&nbsp
		当前节点：<%=DicUtil.getInstance().getItemName(Constants.DIC_WF_NODE_NO,nodeDetail.getNodeId())%>
	</div>	
	
	<br /> 
	<form style="padding:0 30px;" class="form-inline">
		<div class="form-group">
			<label for="nextNode" class="control-label">下一节点</label>：
			<select id="nextNode" name="nextNode" class="form-control required">
				<option value="">请选择</option>
				<%
					List<WfRoute> routes = (List<WfRoute>) map.get("routes");
					for (WfRoute route : routes) {
						String nextNodeId = route.getRouteId().substring(0, 4) + route.getNextNode();
				%>
				<option name="normal" value="<%=route.getRouteId()%>">
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
					if (hisInstance != null && !hisInstance.getNodeId().endsWith("00") && hisInstance.getInstanceNo().equals(curInstance.getInstanceNo())) {
				%>
				<option name="retreat" value="<%=hisInstance.getInstanceId() %>"><%=DicUtil.getInstance().getItemName(Constants.DIC_WF_NODE_NO, hisInstance.getNodeId())%>(回退)
				</option>
				<%
					}
					if(nodeDetail != null && nodeDetail.getIsStart() == null){
				%>
				<option name="circulate" value="<%=nodeDetail.getNodeId() %>"><%=DicUtil.getInstance().getItemName(Constants.DIC_WF_NODE_NO, nodeDetail.getNodeId())%>(流转)
				<%
					}
				%>
			</select>
		</div>
		
		<div class="form-group">
			<label for="nextUser" class="control-label">办理人员：</label>
			<select name="nextUser" name="nextUser" class="form-control">
				<option value=''>请选择</option>
			</select>
		</div>
	</form>
	<br />
	<div style="padding:0 190px;">
		<button class="btn btn-success btn-sm" name="confirm">确定</button>
		<button class="btn btn-default btn-sm" style="float:right;" name="cancel">取消</button>
	</div>

	<script type="text/javascript">
		//确认键绑定
		$("button[name='confirm']").bind("click",function() {
			if (!validate())
				return;

			var param = $("select[name='nextNode']").val();
			var curInstanceId = $("#curInstanceId").val();
			var nextUser = $("select[name='nextUser']");
			var target = "";
			var jsonStrData = "";
			var userIdArray = new Array();
			var selected = $("select[name='nextNode']").find("option:selected");
			
			//办理意见
			var opinionForm = $("form#opinionForm", window.opener.document);
			
			//初始化操作类型
			var opType = selected.attr("name");
			
			//初始化待签收用户(如果等于空加入所有该角色的用户)
			if(nextUser.val() == ''){
				nextUser.find("option[value!='']").each(function(){
					userIdArray.push($(this).val());
				});
			}else{
				userIdArray.push(nextUser.val());
			}
			
			if(userIdArray.length == 0 && opType == "circulate"){
				alert("不存在可流转人员！");
				return;
			}
			
			//此方法复用性低待修改
			if(userIdArray.length == 0){
				var s = new String(selected.text());
				if(s.indexOf("结束")<0){
					alert("暂无下一节点办理人员！");
					return;
				}
			}
			
			//初始化参数和地址
			var moduleMappingUrl = WfURLPrefix;
			if(opType=="retreat"){
				target = moduleMappingUrl + "/retreat";
				opinionForm.find("input[name='opType']").val("回退");
				jsonStrData = {
							hisInstanceId : param,
							curInstanceId : curInstanceId
						};
			}else if(opType=="circulate"){
				target = moduleMappingUrl + "/circulate";
				opinionForm.find("input[name='opType']").val("流转");
				jsonStrData = {
						curInstanceId : curInstanceId,
						todoUserId : userIdArray.toString()
					};
			}else{
				target = moduleMappingUrl + "/submit";
				opinionForm.find("input[name='opType']").val("提交");
				jsonStrData = {
							routeId : param,
							curInstanceId : curInstanceId,
							todoUserId : userIdArray.toString()
						};
			}
			
			var opnionData = opinionForm.serializeObject();
			//alert(JSON.stringify(opnionData));
			
			//存入办理意见
			$.ajax({
			    url: PlanURLPrefix + '/save',
			    data: JSON.stringify(opnionData),
			    dataType:'json',
			    type : 'POST',
			    success:function(re){
			        if(re.result == "success"){
			        	//流程提交
			        	$.ajax({
							url : target,
							dataType : 'json',
							data : JSON.stringify(jsonStrData),
							type : 'POST',
							success : function(re) {
								console.log(re.result);
								if (re.result == 'success')
									window.opener.location = PlanURLPrefix+"/auditList";
								else
									alert("message : " + re.message);
								window.close();
							} 
						});
			        }else{
			        	alert("办理意见保存失败");
			        }
			    }
			}); 

		})

		//取消键绑定
		$("button[name='cancel']").bind("click", function() {
			//window.opener.location.reload();
			window.close()
		})
		
		//加载人员列表事件绑定
		$("select[name='nextNode']").bind("change",function() {
			$("select[name='nextUser']").children().remove();
			
			var opType = $("select[name='nextNode']").find("option:selected").attr("name");
			var nextUser = $("select[name='nextUser']");
			
			var moduleMappingUrl = WfURLPrefix;
			if(opType=="retreat")
				target = moduleMappingUrl + "/getNextUserList?hisInstanceId=" + $("select[name='nextNode']").val();
			else if(opType=="circulate"){
				target = moduleMappingUrl + "/getNextUserList?curInstanceId=" + $("#curInstanceId").val();
				nextUser.append("<option value=''>请选择</option>");
			}else{
				target = moduleMappingUrl + "/getNextUserList?routeId=" + $("select[name='nextNode']").val() 
						+ "&curInstanceId=" + $("#curInstanceId").val();
				nextUser.append("<option value=''>请选择</option>");
			}
			target += "&opType=" + opType;
			
			//alert(target);
			$.ajax({
				url : target,
				dataType : 'json',
				type : 'GET',
				success : function(re) {
					console.log(re.result);
					if (re.result == 'success'){
						var userInfoList = re.userInfos;
						for(var i=0;i<userInfoList.length;i++){
							nextUser.append("<option value='"+userInfoList[i].userId+"'>"
									+userInfoList[i].name+"</option>");
						}
					}
				} 
			});
		});
	</script>
</body>
</html>