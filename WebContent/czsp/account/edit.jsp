<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账号</title>
<link href="/czsp/static/css/bootstrap/bootstrap.min.css"
	rel="stylesheet">

<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/bootstrap/bootstrap.min.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>
<body>
	<div style="padding-left:10%; padding-right:20%;">
		<form id="updateForm">
		
		<c:set var="accountInfo" value="${obj.accountInfo}"/>
		<input type="hidden" name="userName" value="${accountInfo.userName}">
		<table class="table table-bordered">
				<tr>
					<th><label for="planName">用户名</label></th>
					<td>${accountInfo.userName}</td>
				</tr>
				
				<tr>
					<th><label for="password">密码</label></th>
					<td><input type="password" id="password" name="password" class="required" value="${accountInfo.password}"/></td>
				</tr>

				<tr>
					<th><label for="confirmPassword">确认密码</label></th>
					<td><input type="password" id="confirmPassword" name="confirmPassword" class="required"/></td>
				</tr>
				
				<tr>
					<th><label for="isAvailable">是否可用</label></th>
					<td>
						<select name="isAvailable" id="isAvailable" class="required">
							<option value="1" <c:if test="${accountInfo.isAvailable eq '1'}">selected="selected"</c:if>>是</option>
							<option value="0" <c:if test="${accountInfo.isAvailable eq '0'}">selected="selected"</c:if>>否</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<th><label>创建时间</label></th>
					<td><fmt:formatDate value="${accountInfo.createTime}" type="both" /></td>
				</tr>
				
				<tr>
					<th><label>最近修改时间</label></th>
					<td><fmt:formatDate value="${accountInfo.updateTime}" type="both" /></td>
				</tr>
			</table>
		</form>
		
		<button class="btn btn-default btn-sm" style="float:right;" type="button" name="cancel" onclick="history.go(-1)">取消</button>
		<button class="btn btn-default btn-sm" name="save" style="float:right;;margin-right:10px;">保存</button>
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
			
			var confirmPassword = $("#confirmPassword").val();
			var password = $("#password").val();
			if(confirmPassword != password){
				alert("密码不一致！");
				return;
			}
			
			var data = $("form#updateForm").serializeObject();
			console.log(JSON.stringify(data));
			$.ajax({
			    url: AcctURLPrefix + '/update',
			    data: JSON.stringify(data), // 注意要转为json,除非data本身就是json字符串
			    dataType:'json',
			    type : 'POST',
			    success:function(re){
			    	resultPrompt(re,true,false,"修改成功！",AcctURLPrefix+"/list");
			    }
			});
		})
		
	</script>
</body>
</html>