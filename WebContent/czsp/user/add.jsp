<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人员添加</title>
<link href="/czsp/static/css/bootstrap/bootstrap.min.css"
	rel="stylesheet">

<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/bootstrap/bootstrap.min.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>
<%@ page import="czsp.user.model.UserInfo"%>
<body>
	<div style="padding-left:10%; padding-right:20%;">
		<form id="addForm" action="/czsp/plan/create" method="post">
		<input type="hidden" id="createUserId" name="createUserId" value="${userInfo.userId}" /> 
		<table class="table table-bordered">
				<tr>
					<th width="20%"><label for="name">姓名</label></th>
					<td width="30%"><input type="text" id="name" name="name" class="required"/> </td>
					
					<th width="20%"><label for="sex">性别</label></th>
					<td width="30%"><select id="sex" name="sex" class="required">
							<option value="">请选择</option>
							<option value="0">男</option>
							<option value="1">女</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<th><label for="qxId">区县</label></th>
					<td>
						<select id="qxId" name="qxId" class="required">
					 		<option value="">请选择</option>
							<c:forEach var="qx" items="${obj.qxList}">
								<option value="${qx.id}">${qx.name}</option>
							</c:forEach>
						</select>
					</td>
					
					<th><label for="departmentId">部门</label></th>
					<td>
						<select id="departmentId" name="departmentId">
						 	<option value="">请选择</option>
							<c:forEach var="department" items="${obj.departments}">
								<option value="${department.id}">${department.name}</option>
							</c:forEach>
						</select> 
					</td>
				</tr>
				
				<tr>
					<th width="20%"><label for="idCard">身份证号</label></th>
					<td width="30%"><input type="text" id="idCard" name="idCard" class="required"/> </td>
					
					<th width="20%"><label for="phoneNumber">电话号码</label></th>
					<td width="30%"><input type="text" id="phoneNumber" name="phoneNumber" class="required"/></td>
				</tr>
				
			</table>
			
			<button style="float:right;" type="button" name="cancel" onclick="history.go(-1)">取消</button>
			<button style="float:right;margin-right:10px;" name="create" type="button">新增人员</button>
			
		</form>
	</div>

	<script type="text/javascript">
		//初始化必填框
		initPage();
		
		//新增人员按钮绑定
		$("button[name='create']").bind("click",function(){
			if (!validate()) {
				return;
			}
			
			var data = $("form#addForm").serializeObject();
			console.log(JSON.stringify(data));
			$.ajax({
			    url: UserURLPrefix + '/create',
			    data: JSON.stringify(data), 
			    dataType:'json',
			    type : 'POST',
			    success:function(re){
			    	resultPrompt(re,true,false,"添加人员成功！",UserURLPrefix+"/list");
			    }
			});
		})
	</script>
</body>
</html>