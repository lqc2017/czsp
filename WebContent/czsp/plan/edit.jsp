<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>计划</title>
<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>
<%@ page import="czsp.user.model.UserInfo"%>
<body>
	<div>
		<jsp:include page="/czsp/common/base/cur_user_message.jsp" flush="true"/>
		
		<input type="hidden" id="curUserId" name="curUserId" value="${userInfo.userId}" /> 
		<form id="updateForm">
		<c:set var="plan" value="${obj.planInfo}"/>
		<input type="hidden" name="planId" value="${plan.planId}">
		<table border="1">
				<tr>
					<td><label for="planName">规划名称</label></td>
					<td><input type="text" id="planName" name="planName" class="required" value="${plan.planName}"/></td>
					
					<td><label for="qxId">区县</label></td>
					<td>${obj.dicQx[plan.qxId].name}</td>
				</tr>
				
				<tr>
					<td><label for="phases">规划环节</label></td>
					<td>${plan.planApp.phases}</td>
					
					<td><label for="designDepartment">设计部门</label></td>
					<td><input type="text" id="designDepartment" name="designDepartment" value="${plan.designDepartment}"/></td>
				</tr>
				
				<tr>
					<td><label for="townName">村镇</label></td>
					<td><label id="townLabel">${plan.townName}</label>
						<input type="hidden" id="townId" name="townId" class="required"  value="${plan.townId}"/>
						<input type="hidden" id="townName" name="townName" class="required" value="${plan.townName}"/>
						<button type="button" name="selectTown">选择</button>
					</td>
					
					<td><label for="designContactName">设计部门联系人</label></td>
					<td><input type="text" id="designContactName" name="designContactName" value="${plan.designContactName}"/></td>
				</tr>
				
				<tr>
					<td><label for="planArea">规划面积</label></td>
					<td><input type="text" id="planArea" name="planArea" class="required" value="${plan.planArea}"/></td>
					
					<td><label for="designContactWay">设计部门联系方式</label></td>
					<td><input type="text" id="designContactWay" name="designContactWay" value="${plan.designContactWay}"/></td>
				</tr>

				<tr>
					<td><label for="finishDate">预计办结日期</label></td>
					<td><input type="text" id="finishDate" name="finishDate" value='<fmt:formatDate value="${plan.finishDate}" type="date" />'/></td>
					<td colspan="2"></td>
				</tr>

				<tr>
					<td><label for="note">备注</label></td>
					<td colspan="3"><textarea id="note" name="note" style="width: 95%;">${plan.note}</textarea></td>
				</tr>
			</table>
		</form>
		<button name="save">保存</button>
		<button name="change">选择用户</button>
	</div>

	<script type="text/javascript">
		initPage();
		
		//选择人员键绑定
		$("button[name='change']").bind("click", function() {
			window.open(UserURLPrefix + '/change',"选择人员",
					"top=100,left=400,width=500,height=400,resizable=no");
		})
		
		//保存键绑定
		$("button[name='save']").bind("click",function(){
			if ($("#curUserId").val() == "") {
				alert("请先登录！");
				return;
			}
			if (!validate()) {
				return;
			}
			
			var data = $("form#updateForm").serializeObject();
			console.log(JSON.stringify(data));
			$.ajax({
			    url: PlanURLPrefix + '/update',
			    data: JSON.stringify(data), // 注意要转为json,除非data本身就是json字符串
			    dataType:'json',
			    type : 'POST',
			    success:function(re){
			        console.log(re.result);
			        if (re.result == 'success'){
						alert("success!");
						location.href = PlanURLPrefix + "/list";
					}else
						alert("message : " + re.message);
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