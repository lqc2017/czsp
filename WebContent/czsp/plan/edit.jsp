<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>计划</title>
<link href="/czsp/static/css/bootstrap/bootstrap.min.css"
	rel="stylesheet">

<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/bootstrap/bootstrap.min.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>
<%@ page import="czsp.user.model.UserInfo"%>
<body>
	<div style="padding-left:10%; padding-right:20%;">
		<input type="hidden" id="curUserId" name="curUserId" value="${userInfo.userId}" />
		<input type="hidden" id="qxId" name="qxId" value="${userInfo.qxId}" /> 
		<form id="updateForm">
		<c:set var="plan" value="${obj.planInfo}"/>
		<input type="hidden" name="planId" value="${plan.planId}">
		<table class="table table-bordered">
				<tr>
					<th><label for="planName">规划名称</label></th>
					<td><input type="text" id="planName" name="planName" class="required" value="${plan.planName}"/></td>
					
					<th><label for="qxId">区县</label></th>
					<td>${obj.dicQx[plan.qxId].name}</td>
				</tr>
				
				<tr>
					<th><label for="phases">规划环节</label></th>
					<td>${plan.planApp.phases}</td>
					
					<th><label for="designDepartment">设计部门</label></th>
					<td><input type="text" id="designDepartment" name="designDepartment" value="${plan.designDepartment}"/></td>
				</tr>
				
				<tr>
					<th><label for="townName">村镇</label></th>
					<td><label id="townLabel">${plan.townName}</label>
						<input type="hidden" id="townId" name="townId" class="required"  value="${plan.townId}"/>
						<input type="hidden" id="townName" name="townName" class="required" value="${plan.townName}"/>
						<button type="button" name="selectTown">选择</button>
					</td>
					
					<th><label for="designContactName">设计部门联系人</label></th>
					<td><input type="text" id="designContactName" name="designContactName" value="${plan.designContactName}"/></td>
				</tr>
				
				<tr>
					<th><label for="planArea">规划面积</label></th>
					<td><input type="text" id="planArea" name="planArea" class="required" value="${plan.planArea}"/></td>
					
					<th><label for="designContactWay">设计部门联系方式</label></th>
					<td><input type="text" id="designContactWay" name="designContactWay" value="${plan.designContactWay}"/></td>
				</tr>

				<tr>
					<th><label for="finishDate">预计办结日期</label></th>
					<td><input type="text" id="finishDate" name="finishDate" class="required" value='<fmt:formatDate value="${plan.finishDate}" type="date" />'/></td>
					<td colspan="2"></td>
				</tr>

				<tr>
					<th><label for="note">备注</label></th>
					<td colspan="3"><textarea id="note" name="note" style="width: 95%;">${plan.note}</textarea></td>
				</tr>
			</table>
		</form>
		
		<button style="float:right;" type="button" name="cancel" onclick="history.go(-1)">取消</button>
		<button name="save" style="float:right;;margin-right:10px;">保存</button>
	</div>

	<script type="text/javascript">
		initPage();
		
		//保存键绑定
		$("button[name='save']").bind("click",function(){
			if ($("#curUserId").val() == "") {
				alert("请先登录！");
				return;
			}
			if (!validate()) {
				return;
			}
			
			var qxId = $("#qxId").val();
			var data = $("form#updateForm").serializeObject();
			console.log(JSON.stringify(data));
			$.ajax({
			    url: PlanURLPrefix + '/update',
			    data: JSON.stringify(data), // 注意要转为json,除非data本身就是json字符串
			    dataType:'json',
			    type : 'POST',
			    success:function(re){
			    	resultPrompt(re,true,false,"修改成功！",PlanURLPrefix+"/list?qxId="+qxId);
			    }
			});
		})
		
		//选择村镇键绑定
		$("button[name='selectTown']").bind("click", function() {
			if($("#curUserId").val() == "") {
				alert("请先登录！");
				return;
			}
			window.open(CommURLPrefix + '/selectTown',"选择村镇",
			"top=100,left=400,width=500,height=400,resizable=no");
		})
		
		
	</script>
</body>
</html>