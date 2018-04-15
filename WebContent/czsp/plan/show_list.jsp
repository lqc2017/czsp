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
<body>
	<div style="padding-left:10%; padding-right:20%;">
		<form id="searchFrom" class="form-inline" action="/czsp/plan/list">
			<div class="form-group">
					<label for="createYear" class="control-label">年份</label>： 
					<select id="createYear" name="createYear" class="form-control">
						<option id="default" value="">请选择</option>
						<c:set var="yearList" value="${obj.yearList}" />
						<c:set var="planCondition" value="${obj.planCondition}" />
						<c:forEach var="year" items="${yearList}">
							${planCondition.createYear}
							<option value="${year}"
								<c:if test="${planCondition.createYear ne null && planCondition.createYear eq year}">
								selected="selected" </c:if>>${year}年</option>
						</c:forEach>
					</select>
			</div>
			<c:if test="${userInfo.qxId ne '00'}">
				<input name="qxId" type="hidden" value="${userInfo.qxId}"/>
			</c:if>
			<input name="pageNumber" type="hidden" value="${obj.pagination.pager.pageNumber}"/>
			
			&nbsp <button class="btn btn-default btn-sm" name="search" type="button">查询</button>
			&nbsp <button class="btn btn-default btn-sm" name="reset" type="button">重置</button>
		</form>
	</div>
	
	<div style="padding-left:10%; padding-right:20%;">
		<c:if test="${userInfo.permission.containsKey('411001')}">
			<button class="btn btn-success btn-sm" name="add" style="float: right;">新增计划</button>
		</c:if>
		<table class="table table-bordered">
			<tr>
				<th width="8%">区县</th>
				<th width="28%">规划名称</th>
				<th width="10%">村镇</th>
				<th width="13%">规划面积(km²)</th>
				<th width="10%">创建日期</th>
				<th width="12%">创建人</th>
				<th width="7%">状态</th>
				<th width="12%">操作</th>
			</tr>
			<c:set var="pagination" value="${obj.pagination}" />
			<c:forEach var="info" items="${pagination.list}">
				<tr>
					<td>${obj.dicQx[info.qxId].name}</td>
					<td title="${info.planId}">${info.planName}</td>
					<td>${info.townName}</td>
					<td>${info.planArea}</td>
					<td><fmt:formatDate value="${info.createTime}" type="date" /></td>
					<td>${info.createUserName}</td>
					<td><c:if test="${info.status eq '0'}">未流转</c:if>
					<c:if test="${info.status eq '1'}">流转中</c:if>
					<c:if test="${info.status eq '2'}">办结</c:if></td>
					<td>
					<c:if test="${userInfo.permission.containsKey('411003')}">
						<c:if test="${info.status eq '0'}"><button class="btn btn-default btn-xs" name="edit">修改</button>
					</c:if>
					
					</c:if>
					<c:if test="${userInfo.permission.containsKey('411002')}">
						<c:if test="${info.status eq '0'}"> <button name="launch">启动</button></c:if>
					</c:if>
					<c:if test="${userInfo.permission.containsKey('411004')}">
						<button class="btn btn-danger btn-xs" name="del">删除</button>
					</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	<!-- 分页 -->
	<jsp:include page="/czsp/common/pagination.jsp" flush="true"/>

	<script type="text/javascript">
		initPage();
		//修改键绑定
		$("button[name='edit']").bind("click", function() {
			var tr = $(this).parents("tr");
			var planId = tr.children("td:eq(1)").attr("title");
			
			//window.open(PlanURLPrefix+'/edit/'+planId,"修改计划");
			location.href= PlanURLPrefix+'/edit/'+planId;
		})
		
		//添加键绑定
		$("button[name='add']").bind("click", function() {
			location.href= PlanURLPrefix+'/add';
		})
		
		//启动键绑定
		$("button[name='launch']").bind("click", function() {
			var tr = $(this).parents("tr");
			var planId = tr.children("td:eq(1)").attr("title");
			
			$.ajax({
				url : '/czsp/plan/launch?planId='+planId,
				dataType : 'json',
				type : 'GET',
				success : function(re) {
					resultPrompt(re);
				}
			});
		})
		
		//删除案件键绑定
		$("button[name='del']").bind("click", function() {
			var tr = $(this).parents("tr");
			var planId = tr.children("td:eq(1)").attr("title");

			$.ajax({
				url : PlanURLPrefix+'/delete/' + planId,
				dataType : 'json',
				type : 'GET',
				success : function(re) {
					console.log(re.result);
			        if (re.result == 'success'){
						alert("success!");
						location.href = PlanURLPrefix + "/list";
					}else
						alert("message : " + re.message);
				}
			});
		})
	</script>
</body>
</html>