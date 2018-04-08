<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色列表</title>
<link href="/czsp/static/css/bootstrap/bootstrap.min.css"
	rel="stylesheet">

<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/bootstrap/bootstrap.min.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>
<body>
	<input id="roleId" type="hidden" value="${obj.roleId}"/>
	<table id="permissionTable" class="table table-bordered">
		<thead>
			<tr>
				<th width="20%">角色名称</th>
				<th width="60%">权限</th>
				<th width="20%">操作</th>
			</tr>
		</thead>
		<tbody>
			<tr>
			<td style="text-align:center;vertical-align:middle;" 
				<c:if test="${obj.permissionRoleList.size() ge 1}">rowspan="${obj.permissionRoleList.size()}"</c:if>>
				${obj.roleName}
			</td>
				<c:choose>
					<c:when test="${obj.permissionRoleList.size() eq 0}">
						<td>暂无权限对象绑定</td><td></td>
					</c:when>
					<c:otherwise>
						<c:forEach var="permissionRole" end="0" items="${obj.permissionRoleList}">
								<td class="oId" title="${permissionRole.value.objectId}">${permissionRole.value.objectName}</td>
								<td><button name="del">删除</button></td>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tr>
			<c:if test="${obj.permissionRoleList.size() ge 1}">
				<c:forEach var="permissionRole" begin="1" items="${obj.permissionRoleList}">
					<tr>
						<td class="oId" title="${permissionRole.value.objectId}">${permissionRole.value.objectName}</td>
						<td><button name="del">删除</button></td>
					</tr>
				</c:forEach>
			</c:if>

			<tr>
				<td></td>
				<td></td>
				<td><button name="add" onclick="add(this)">添加</button></td>
			</tr>
		</tbody>
	</table>
	<button name="back" onclick="history.go(-1)">返回</button>

	<script type="text/javascript">
		//获取未绑定的其他权限
		function getOtherPermissions(){
			var roleId = $("#roleId").val();
			var poList = new Array();
			
			$.ajax({
				url : AuthURLPrefix + '/getOtherPermissions/'+roleId,
				dataType : 'json',
				type : 'GET',
				async : false,
				success : function(re) {
					console.log(re.result);
			        if (re.result == 'success'){
			        	poList = re.poList;
					}else
						alert("message : " + re.message);
				}
			});
			
			return poList;
		}
		
		//删除按钮点击事件
		$("button[name='del']").bind("click",function(){
			var roleId = $("#roleId").val();
			var objectId = $(this).closest("tr").find("td.oId").attr("title");
			
			$.ajax({
				url : AuthURLPrefix + "/deletPmsRole?roleId="+roleId+"&objectId="+objectId,
				dataType : 'json',
				type : 'GET',
				success : function(re) {
					resultPrompt(re,true,true,"删除成功");
				} 
			});
		})
		
		//添加按钮点击事件
		function add(ele){
			var poList = getOtherPermissions();
			var addBtn = $(ele);
			
			var td = addBtn.closest("td");
			
			var tr = td.closest("tr");
			var permissionTd = tr.find("td:eq(1)");
			if(poList.length == 0){
				alert("该角色已获得所有权限！");
				return;
			}else{
				permissionTd.append("<select name='objectId'></select>");
				for(var i=0;i<poList.length;i++){
					permissionTd.children("select").append("<option value='"+poList[i].objectId+"'>"+poList[i].objectName+"</option>");
				}
				
				td.children().remove();
			}
			
			var opTd = td;
			opTd.append('<button name="confirm" onclick="confirm(this)">确认</button>');
			opTd.append('&nbsp<button name="cancel" onclick="cancel(this)">取消</button>');
		}
		
		//确认按钮点击事件
		function confirm(ele){
			var confirmBtn = $(ele);
			var tr = confirmBtn.closest("tr");
			
			var roleId = $("#roleId").val();
			var objectId = tr.children("td:eq(1)").children("select[name='objectId']").val();
			
			$.ajax({
				url : AuthURLPrefix + "/addPmsRole?roleId="+roleId+"&objectId="+objectId,
				dataType : 'json',
				type : 'GET',
				success : function(re) {
					resultPrompt(re,true,true,"保存成功");
				} 
			});
		}
		
		//取消按钮点击事件
		function cancel(ele){
			var cancelBtn = $(ele);
			var tr = cancelBtn.closest("tr");
			
			var td = tr.children("td:eq(1)");
			td.children().remove();
			
			var td = cancelBtn.closest("td");
			td.children().remove();
			td.append('<button name="add" onclick="add(this)">添加</button>');
		}
	</script>
</body>
</html>