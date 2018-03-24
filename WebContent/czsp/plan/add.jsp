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
	<jsp:include page="/czsp/common/base/cur_user_message.jsp" flush="true"/>
	
	<form action="/czsp/plan/create" method="post">
	<input type="hidden" id="createUserId" name="createUserId" value="${userInfo.userId}" /> 
	<table border="1">
			<tr>
				<td><label for="planName">规划名称</label></td>
				<td><input type="text" id="planName" name="planName" class="required" /></td>
			</tr>
			<tr>
				<td><label for="phases">规划环节</label></td>
				<td><label id="phasesLabel"></label>
					<input type="hidden" id="phases" name="phases" class="required" />
					<button type="button" name="selectPhase">选择</button>
				</td>
			</tr>
			<tr>
				<td><label for="qxId">区县</label></td>
				<td><input type="hidden" id="qxId" name="qxId" class="required" value="<c:if test='${userInfo != null }'>${userInfo.qxId}</c:if>"/>
				<c:if test="${userInfo != null }">${obj.dicQx[userInfo.qxId].name}</c:if></td>
			</tr>
			<tr>
				<td><label for="townName">村镇</label></td>
				<td><label id="townLabel"></label>
					<input type="hidden" id="townId" name="townId" class="required" />
					<input type="hidden" id="townName" name="townName" class="required" />
					<button type="button" name="selectTown">选择</button>
				</td>
			</tr>
			<tr>
				<td><label for="planArea">规划面积</label></td>
				<td><input type="text" id="planArea" name="planArea" class="required" /></td>
			</tr>
			<tr>
				<td><label for="designDepartment">设计部门</label></td>
				<td><input type="text" id="designDepartment" name="designDepartment"/></td>
			</tr>
			<tr>
				<td><label for="designContactName">设计部门联系人</label></td>
				<td><input type="text" id="designContactName" name="designContactName"/></td>
			</tr>
			<tr>
				<td><label for="designContactWay">设计部门联系方式</label></td>
				<td><input type="text" id="designContactWay" name="designContactWay" /></td>
			</tr>
		</table>
		
		<input type="submit" value="新建计划">
	</form>
	<button name="change">选择用户</button>

	<script type="text/javascript">
		//初始化必填框
		initPage();
		
		//选择人员键绑定
		$("button[name='change']").bind("click", function() {
			window.open(UserURLPrefix + '/change',"选择人员",
					"top=100,left=400,width=500,height=400,resizable=no");
		})
		
		//表单提交事件绑定
		$("form").submit(function(e) {
			if ($("#createUserId").val() == "") {
				alert("请先登录！");
				e.preventDefault();
				return;
			}
			if (!validate()) {
				e.preventDefault();
			}
		})
		
		//选择环节键绑定
		$("button[name='selectPhase']").bind("click", function() {
			var moduleMappingUrl = WfURLPrefix;
			
			var url = moduleMappingUrl + '/selectPhases';
			if($("#phases").val() != undefined)
				url = moduleMappingUrl + '/selectPhases?phaseIds='+$("#phases").val();
			
			window.open(url,"选择环节","top=100,left=400,width=250,height=200,resizable=no");
		})
		
		//选择村镇键绑定
		$("button[name='selectTown']").bind("click", function() {
			if($("#createUserId").val() == "") {
				alert("请先登录！");
				return;
			}
			
			window.open(CommURLPrefix + '/selectTown',"选择村镇",
			"top=100,left=400,width=500,height=400,resizable=no");
		})
	</script>
</body>
</html>