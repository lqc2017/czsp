<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>案件列表</title>
<link href="/czsp/static/css/bootstrap/bootstrap.min.css"
	rel="stylesheet">

<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/bootstrap/bootstrap.min.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="czsp.user.model.UserInfo"%>
<%@ page import="czsp.plan.model.view.VplanWfDetail"%>
<%@ page import="czsp.common.Constants"%>
<%@ page import="org.nutz.dao.entity.Record"%>
<%
	Map map = (HashMap) request.getAttribute("obj");
	VplanWfDetail planCondition = (VplanWfDetail) map.get("planCondition");
	if (planCondition == null)
		planCondition = new VplanWfDetail();
%>
<body>
		<div style="padding-left:10%; padding-right:20%;">
			<form id="searchFrom" class="form-inline" action="/czsp/plan/query">
				<div class="form-group">
					<label for="createYear" class="control-label">年份</label>： 
					<select id="createYear" name="createYear" class="form-control">
						<option id="default" value="">请选择</option>
						<%
							List<String> yearList = (List<String>) map.get("yearList");
							for (String year : yearList) {
						%>
						<option value="<%=year%>"
							<%if (planCondition.getCreateYear() != null && year.equals(planCondition.getCreateYear())) {%>
							selected="selected" <%}%>><%=year%>年</option>
						<%
							}
						%>
					</select>
				</div>
				
				<div class="form-group">
					<label for="name" class="control-label">规划名称</label>： 
					
					<input type="text" id="planName" class="form-control" name="planName" size="5" value="${obj.planCondition.planName}" />
				</div> 
				
				<div class="form-group">
					<label for="qxId" class="control-label">区县</label>： 
					<select id="qxId" name="qxId" class="form-control">
						<option id="default" value="">请选择</option>
						<%
							List<Record> qxList = (List<Record>) map.get("qxList");
							for (Record qx : qxList) {
						%>
						<option value="<%=qx.get("id")%>"
							<%if (planCondition.getQxId() != null && qx.get("id").equals(planCondition.getQxId())) {%>
							selected="selected" <%}%>><%=qx.get("name")%></option>
						<%
							}
						%>
					</select> 
				</div>
				
				<div class="form-group">
					<label for="status" class="control-label">状态</label>： 
					<select id="status" name="status" class="form-control">
						<option id="default" value="">请选择</option>
						<option value="0"
							<%if (planCondition.getStatus() != null && "0".equals(planCondition.getStatus())) {%>
							selected="selected" <%}%>>未流转</option>
						<option value="1"
							<%if (planCondition.getStatus() != null && "1".equals(planCondition.getStatus())) {%>
							selected="selected" <%}%>>流转中</option>
						<option value="2"
							<%if (planCondition.getStatus() != null && "2".equals(planCondition.getStatus())) {%>
							selected="selected" <%}%>>已办结</option>
					</select>
				</div>
				
				<input name="pageNumber" type="hidden" value="${obj.pagination.pager.pageNumber}"/>
				
				&nbsp <button class="btn btn-default btn-sm" name="search" type="button">查询</button>
				&nbsp <button class="btn btn-default btn-sm" name="reset" type="button">重置</button>
			</form>
			<br/>
			
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
				<c:set var="pagination" value="${obj.pagination}" />
				<c:forEach var="info" items="${pagination.list}">
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
						<td><button class="btn btn-default btn-xs" name="detail">查看</button></td>
					</tr>
				</c:forEach>
			</table>
		</div>
		
		<!-- 分页 -->
		<jsp:include page="/czsp/common/pagination.jsp" flush="true"/>
	
	

	<script type="text/javascript">
		initPage();
		
		//查看详细信息按钮绑定
		$("button[name='detail']").bind("click", function() {
			var tr = $(this).parents("tr");
			var planId = tr.children("td:eq(1)").attr("title");
			
			window.open(PlanURLPrefix+'/detail/'+planId,"详细信息");
		})
	</script>
</body>
</html>