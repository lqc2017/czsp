<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>计划列表</title>
<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>
<body>
	<div style="width:1000px">
		<jsp:include page="/czsp/common/base/cur_user_message.jsp" flush="true" />
		<button name="add" style="float: right;">新增计划</button>
		<table border='1' style="width:1000px;">
			<tr>
				<th>区县</th>
				<th>规划名称</th>
				<th>村镇</th>
				<th>规划面积(km²)</th>
				<th>创建日期</th>
				<th>创建人</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
			<c:forEach var="info" items="${obj.infoList}">
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
					<td><c:if test="${info.status eq '0'}"><button name="edit">修改</button>
					
					</c:if>
					<c:if test="${info.status eq '0'}"> <button name="launch">启动</button></c:if>
					<button name="del">删除</button>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<script type="text/javascript">
		//修改键绑定
		$("button[name='edit']").bind("click", function() {
			var tr = $(this).parents("tr");
			var planId = tr.children("td:eq(1)").attr("title");
			
			window.open(PlanURLPrefix+'/edit/'+planId,"修改计划");
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