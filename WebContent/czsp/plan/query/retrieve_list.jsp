<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>计划列表</title>
<link href="/czsp/static/css/bootstrap/bootstrap.min.css"
	rel="stylesheet">

<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/bootstrap/bootstrap.min.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>
<%@ page import="czsp.common.Constants"%>
<body>
	<div style="padding-left:10%; padding-right:20%;">
		<table class="table table-bordered">
			<tr>
				<th>区县</th>
				<th>规划名称</th>
				<th>村镇</th>
				<th>当前环节</th>
				<th>当前节点</th>
				<th>当前办理人</th>
				<th>创建日期</th>
				<th>是否办结</th>
				<th>操作</th>
			</tr>
			<c:set var="dicUtil" value="${obj.dicUtil}" />
			<c:set var="Constants" value="${obj.constants}" />
			<c:forEach var="info" items="${obj.infoList}">
				<tr>
					<td>${dicUtil.getItemName(Constants.DIC_QX_NO,info.qxId)}</td>
					<td title="${info.planId}">${info.planName}</td>
					<td>${info.townName}</td>
					<td>${dicUtil.getItemName(Constants.DIC_WF_PHASE_NO,info.curPhase)}</td>
					<td>${dicUtil.getItemName(Constants.DIC_WF_NODE_NO,info.curNode)}</td>
					<td><c:if test="${info.signUserName eq null}">暂无</c:if>
					<c:if test="${info.signUserName ne null}">${info.signUserName}</c:if>
					</td>
					<td><fmt:formatDate value="${info.createTime}" type="date" /></td>
					<td><c:if test="${info.status eq '0'}">未流转</c:if>
					<c:if test="${info.status eq '1'}">流转中</c:if>
					<c:if test="${info.status eq '2'}">办结</c:if></td>
					<td><button class="btn btn-default btn-xs" name="detail">查看</button>
					<button class="btn btn-warning btn-xs" name="retrieve" instanceId="${info.instanceId}">回收</button></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<script type="text/javascript">
		//查看详细信息按钮绑定
		$("button[name='detail']").bind("click", function() {
			var tr = $(this).parents("tr");
			var planId = tr.children("td:eq(1)").attr("title");
			
			window.open(PlanURLPrefix+'/detail/'+planId,"详细信息");
		})
		
		//回收按钮绑定
		$("button[name='retrieve']").bind("click", function() {
			var instanceId = $(this).attr("instanceId");
			
			$.ajax({
			    url: WfURLPrefix + '/retrieve/'+instanceId,
			    dataType:'json',
			    type : 'GET',
			    success:function(re){
			        resultPrompt(re,true,true,"回收成功");
			    }
			}); 
		})
	</script>
</body>
</html>