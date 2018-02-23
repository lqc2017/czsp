<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>daoTest</title>
<script src="/static/js/jquery.js"></script>
</head>
<body>
	<ul>
		<c:forEach var="node" items="${obj.wfNodes}">
			<li>${node.nodeName}</li>
		</c:forEach>
	</ul>
	<button name="createInstance">新建流程</button>

	<table>
		<tr>
			<th>id</th>
			<th>name</th>
			<th>oper</th>
		</tr>
		<c:forEach var="user" items="${obj.users}">
			<tr>
				<td>${user.id}</td>
				<td>${user.name}</td>
				<td><button name="edt">edit</button>
					<button name="del">delete</button></td>
			</tr>
		</c:forEach>
	</table>

	<script type="text/javascript">
	$("button[name='createInstance']").bind("click",function(){
		
		$.ajax({
		    url:'/createInstance',
		    dataType:'json',
		    type : 'GET',
		    success:function(re){
		        console.log(re.result);
		        if(re.result=='success')
		        	location.reload();
		    }
		});
	})

	$("button[name='del']").bind("click",function(){
		var tr = $(this).parents("tr");
		var uId = tr.children("td:first").text();
		
		$.ajax({
		    url:'/delete/'+uId,
		    dataType:'json',
		    type : 'GET',
		    success:function(re){
		        console.log(re.result);
		        if(re.result=='success')
		        	location.reload();
		    }
		});
	})
	
	$("button[name='edt']").bind("click",function(){
		var tr = $(this).parents("tr");
		var uId = tr.children("td:first").text();
		var uName = tr.children("td:eq(1)").text();
		tr.children("td").remove();
		tr.append("<td>"+uId+"</td>");
		tr.append("<td><input type='text' name='name' value='"+uName+"'/></td>");
		tr.append("<td><button name='upd' uId='"+uId+"'>update</button></td>");
		
		tr.find("button").bind("click",{id:uId},update)
	})
	
	$("button[name='add']").bind("click",function(){
		var uName = $("input[name='newName']").val();
		var data = {name:uName}
		$.ajax({
		    url:'/insert',
		    "data": data,
		    dataType:'json',
		    type : 'POST',
		    success:function(re){
		        console.log(re.result);
		        if(re.result=='success')
		        	location.reload();
		    }
		});
	})
	
	function update(event){
		var tr = $(this).parents("tr");
		var uName = tr.find("input[name='name']").val();
		var data = {id:event.data.id,name:uName}
		console.log(data);
		
		$.ajax({
		    url:'/update',
		    "data": data,
		    dataType:'json',
		    type : 'POST',
		    success:function(re){
		        console.log(re.result);
		        if(re.result=='success')
		        	location.reload();
		    }
		});
	}
</script>
</html>