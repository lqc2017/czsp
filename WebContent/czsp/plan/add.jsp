<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>计划</title>
<link href="/czsp/static/css/bootstrap/bootstrap.min.css" rel="stylesheet">
<link href="/czsp/static/css/bootstrap-datetimepicker/bootstrap-datetimepicker.min.css" rel="stylesheet">

<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/bootstrap/bootstrap.min.js"></script>
<script src="/czsp/static/js/bootstrap-datetimepicker/bootstrap-datetimepicker.min.js"></script>
<script src="/czsp/static/js/bootstrap-datetimepicker/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>
<%@ page import="czsp.user.model.UserInfo"%>
<body>
	<div style="padding-left:10%; padding-right:20%;">
		<form action="/czsp/plan/create" method="post">
		<input type="hidden" id="createUserId" name="createUserId" value="${userInfo.userId}" /> 
		<table class="table table-bordered">
				<tr>
					<th><label for="planName">规划名称</label></th>
					<td><input type="text" id="planName" name="planName" class="required" /></td>
					
					<th><label for="qxId">区县</label></th>
					<td><input type="hidden" id="qxId" name="qxId" class="required" value="<c:if test='${userInfo != null }'>${userInfo.qxId}</c:if>"/>
					<c:if test="${userInfo != null }">${obj.dicQx[userInfo.qxId].name}</c:if></td>
				</tr>
				
				<tr>
					<th><label for="phases">规划环节</label></th>
					<td><label id="phasesLabel"></label>
						<input type="hidden" id="phases" name="phases" class="required" />
						<button type="button" name="selectPhase">选择</button>
					</td>
					
					<th><label for="designDepartment">设计部门</label></th>
					<td><input type="text" id="designDepartment" name="designDepartment"/></td>
				</tr>
				
				<tr>
					<th><label for="townName">村镇</label></th>
					<td><label id="townLabel"></label>
						<input type="hidden" id="townId" name="townId"/>
						<input type="hidden" id="townName" name="townName" class="required" />
						<button type="button" name="selectTown">选择</button>
					</td>
					
					<th><label for="designContactName">设计部门联系人</label></th>
					<td><input type="text" id="designContactName" name="designContactName"/></td>
				</tr>
				
				<tr>
					<th><label for="planArea">规划面积</label></th>
					<td><input type="text" id="planArea" name="planArea" class="required" /></td>
					
					<th><label for="designContactWay">设计部门联系方式</label></th>
					<td><input type="text" id="designContactWay" name="designContactWay" /></td>
				</tr>
				
				<tr>
					<th><label for="finishDate">预计办结日期</label></th>
					<td>
						<div class="input-group col-md-7">
							<input type="text" readonly class="form-control form_date" id="finishDate" name="finishDate" >
							<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
						</div>
					</td>
					<td colspan="2"></td>
				</tr>
				
				<tr>
					<th><label for="note">备注</label></th>
					<td colspan="3"><textarea id="note" name="note" style="width:95%;"></textarea></td>
				</tr>
			</table>
			
			<button style="float:right;" type="button" name="cancel" onclick="history.go(-1)">取消</button>
			<input style="float:right;margin-right:10px;" type="submit" value="新建计划">
		</form>
	</div>

	<script type="text/javascript">
		//初始化必填框
		initPage();
		
		//初始化日期控件
		$('.form_date').datetimepicker({
			language : 'zh-CN',
			weekStart : 1,
			todayBtn : 1,
			autoclose : 1,
			todayHighlight : 1,
			startView : 2,
			minView : 2,
			forceParse : 0,
			format : 'yyyy-mm-dd'
		});
		
		//清空日期按钮绑定
		$(".glyphicon-remove").closest(".input-group-addon").bind("click",function(){
			$("#finishDate").val("");
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