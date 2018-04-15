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
	<button class="btn btn-success btn-sm" style="float:right;" name="add" onclick="add()">添加</button>
	<table id="permissionTable" class="table table-bordered">
		<thead>
			<tr>
				<th width="20%">权限</th>
				<th width="40%">名称</th>
				<th width="20%">类型</th>
				<th width="20%">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="permission" items="${obj.permissionList }">
				<tr>
					<td>${permission.objectId}</td>
					<td>${permission.objectName}</td>
					<td>${obj.dicPT[permission.objectType].name}</td>
					<td><button class="btn btn-default btn-sm" name="edit" onclick="edit(this,'${permission.objectName}','${obj.dicPT[permission.objectType].name}')">编辑</button></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>


	<script type="text/javascript">
		var typeList = new Array();
		//加载类型字典	
		function initTypeDic(){
			$.ajax({
				url : CommURLPrefix + '/getPermissionType',
				dataType : 'json',
				type : 'GET',
				async : false,
				success : function(re) {
					console.log(re.result);
			        if (re.result == 'success'){
			        	typeList = re.ptList;
					}else
						alert("message : " + re.message);
				}
			});
		}
		
		//添加按钮点击事件
		function add(){
			if(typeList == null || typeList == undefined || typeList.length==0 || typeList == ''){
				initTypeDic();
			}
			
			$("#permissionTable").find("tbody").prepend("<tr>"+
					"<td></td>"+
					"<td></td>"+
					"<td></td>"+
					"<td></td>"+
				"</tr>");
			
			var tr = $("#permissionTable").find("tbody").children("tr:first");
			
			var idTd = tr.children("td:eq(0)");
			idTd.append("<input name='objectId' type='text' size='10' />");
			
			var nameTd = tr.children("td:eq(1)");
			nameTd.append("<input name='objectName' type='text' size='10'/>");
			
			var typeTd = tr.children("td:eq(2)");
			typeTd.append("<select name='objectType'></select>");
			for(var i=0;i<typeList.length;i++){
        		typeTd.children("select").append("<option value='"+typeList[i].id+"'>"+typeList[i].name+"</option>");
			}
			
			var opTd = tr.children("td:last");
			opTd.append('<button class="btn btn-success btn-sm" name="confirm" onclick="confirm(this)">确定</button>');
			opTd.append('&nbsp<button class="btn btn-danger btn-sm" name="del" onclick="del(this)">删除</button>');
		}
		
		//删除按钮点击事件
		function del(ele){
			var delbtn = $(ele);
			var tr = delbtn.closest("tr");
			tr.remove();
		}
		
		//编辑按钮点击事件
		function edit(ele,objectName,objectType){
			//加载类型字典
			if(typeList == null || typeList == undefined || typeList.length==0 || typeList == ''){
				initTypeDic();
			}
			
			var editbtn = $(ele);
			var tr = editbtn.closest("tr");
			var objectId = tr.children("td:first").text();
			
			var nameTd = tr.children("td:eq(1)");
			nameTd.empty();
			nameTd.append("<input name='objectName' type='text' size='10' value='"+objectName+"'/>");
			
			var typeTd = tr.children("td:eq(2)");
			typeTd.empty();
			typeTd.append("<select name='objectType'></select>");
			for(var i=0;i<typeList.length;i++){
        		typeTd.children("select").append("<option value='"+typeList[i].id+"'>"+typeList[i].name+"</option>");
			}
			
			var td = editbtn.closest("td");
			td.children().remove();
			td.append('<button class="btn btn-default btn-sm" name="save" onclick="save(this)">保存</button>&nbsp'
					+'<button class="btn btn-default btn-sm" name="cancel" onclick="cancel(this,\''+objectName+'\',\''+objectType+'\')">取消</button>');
		}
		
		//取消按钮点击事件
		function cancel(ele,objectName,objectType){
			var cancelBtn = $(ele);
			var tr = cancelBtn.closest("tr");
			
			var nameTd = tr.children("td:eq(1)");
			nameTd.empty();
			nameTd.append(objectName);
			
			var typeTd = tr.children("td:eq(2)");
			typeTd.empty();
			typeTd.append(objectType);
			
			var td = cancelBtn.closest("td");
			td.children().remove();
			td.append('<button class="btn btn-default btn-sm" name="edit" onclick="edit(this,\''+objectName+'\',\''+objectType+'\')">编辑</button>');
		}
		
		//保存按钮点击事件
		function save(ele){
			var saveBtn = $(ele);
			var tr = saveBtn.closest("tr");
			
			var objectId = tr.children("td:eq(0)").text();
			var objectName = tr.children("td:eq(1)").children("input[name='objectName']").val();
			var objectType = tr.children("td:eq(2)").children("select[name='objectType']").val();
			
			var po = {
					objectId : objectId,
					objectName : objectName,
					objectType : objectType
			};
			
			$.ajax({
				url : AuthURLPrefix + "/editPermission",
				dataType : 'json',
				data : JSON.stringify(po),
				type : 'POST',
				success : function(re) {
					resultPrompt(re,true,true,"保存成功");
				} 
			});
		}
		
		//确认按钮点击事件
		function confirm(ele){
			var confirmBtn = $(ele);
			var tr = confirmBtn.closest("tr");
			
			var objectId = tr.children("td:eq(0)").children("input[name='objectId']").val();
			var objectName = tr.children("td:eq(1)").children("input[name='objectName']").val();
			var objectType = tr.children("td:eq(2)").children("select[name='objectType']").val();
			
			var po = {
					objectId : objectId,
					objectName : objectName,
					objectType : objectType
			};
			
			$.ajax({
				url : AuthURLPrefix + "/addPermission",
				dataType : 'json',
				data : JSON.stringify(po),
				type : 'POST',
				success : function(re) {
					resultPrompt(re,true,true,"保存成功");
				} 
			});
		}
	</script>
</body>
</html>