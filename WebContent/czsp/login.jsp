<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>村镇规划编制审批系统</title>
<link href="/czsp/static/css/bootstrap/bootstrap.min.css"
	rel="stylesheet">

<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/bootstrap/bootstrap.min.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>
<body>
	<div style="width: 400px; margin-top: 150px; margin-left: auto; margin-right: auto;">
		<div class="panel panel-info">
			<div class="panel-heading">
				<h3 class="panel-title">用户登录</h3>
			</div>
			<div class="panel-body">
				<form id="loginForm" method="post" style="padding: 0 30px;" action="/czsp/validate">
					<c:if test="${obj.info !=null}">
						<div class="alert alert-warning" >
							<p>${obj.info}</p>
						</div>
					</c:if>
					
					<c:if test="${info !=null}">
						<div class="alert alert-warning" >
							<p>${info}</p>
						</div>
					</c:if>
					
					<div class="form-group">
						<label for="userName" class="control-label">登录名</label>：
						<input type="text" required class="form-control" id="userName" name="userName" placeholder="请输入用户名">
					</div>
					
					<div class="form-group">
						<label for="password" class="control-label">密码</label>：
						<input type="password" required class="form-control" id="password" name="password" placeholder="请输入密码">
					</div>
					
					<div class="form-group">
						<label class="control-label">验证码</label>：
						<div class="input-group">
							<input type="text" class="form-control" id="verifyCode" placeholder="验证码">
							<span class="input-group-addon"><img id="imgVerify" src="" alt="点击更换验证码"  onclick="getVerify(this);"></span>
						</div>
					</div>
					
					<div class="col-md-4 col-md-offset-4">
						<button name="login" type="button" class="btn btn-info">登陆</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$(document).ready(function(){
			 $("#imgVerify").attr("src","/czsp/getVerify?"+Math.random());
			 
			 $("button[name='login']").bind("click",function(){
				 checkSum();
			 })
		});
		
		//获取验证码
		function getVerify(obj){
		    obj.src = "/czsp/getVerify?"+Math.random();
		}
		
		//校验验证码
		function checkSum(){
		    var verifyCode = $("#verifyCode").val();
		    var userName = $("#userName").val();
		    var password = $("#password").val();
		    
		    if(userName==null || userName==""){
		    	alert("请输入用户名！");
		    	return;
		    }
		    if(password==null || password==""){
		    	alert("请输入密码！");
		    	return;
		    }
		    
		    if(verifyCode!=null && verifyCode!=""){
		    	verifyCode = verifyCode.toUpperCase();//将输入的字母全部转换成大写
		        $.ajax({
		            url : "/czsp/checkVerify",
		            data: {verifyCode:verifyCode},
		            success : function(re) {
		                if(re.result == "success"){
		                    $("form#loginForm").submit();//提交表单
		                }else{
		                    alert(re.message);
		                }
		            }
		        });
		    }else{
		    	alert("请输入验证码");
		    }
		}
	</script>
</body>
</html>