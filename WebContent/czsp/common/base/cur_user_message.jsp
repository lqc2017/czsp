<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<%@ page import="czsp.user.model.UserInfo"%>
<body>
	<%
		UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
		if (userInfo != null) {
	%>
	当前人员：<%=userInfo.getName()%>(<%=userInfo.getUserId()%>)
	<%
		} else {
			userInfo = new UserInfo();
		}
	%>
	<input name="userId" id="userId" type="hidden" value="<%=userInfo.getUserId()%>"/>
	<c:if test="${userInfo.permission.containsKey('100')}">
		<button name="change">切换</button>&nbsp
	</c:if>
	<button name="logout">退出</button><br />
	<script type="text/javascript">
		$(function(){
			if($("#userId").val()=="null"){
				//alert("我要跳转了");
				location.href = "/czsp/login";
			}
		});
		//切换键绑定
		$("button[name='change']").bind("click", function() {
			window.open(UserURLPrefix + '/change',"选择人员",
					"top=100,left=400,width=500,height=400,resizable=no");
		})
		//登出键绑定
		$("button[name='logout']").bind("click", function() {
			location.href =  "/czsp/logout";
		})
	</script>
</body>
</html>