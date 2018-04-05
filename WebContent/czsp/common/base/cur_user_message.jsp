<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	%><button name="change">切换</button><br />
	<script type="text/javascript">
		//选择人员键绑定
		$("button[name='change']").bind("click", function() {
			window.open(UserURLPrefix + '/change',"选择人员",
					"top=100,left=400,width=500,height=400,resizable=no");
		})
	</script>
</body>
</html>