<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<body>
	<div style="float:right;">
		<c:set var="planInfo" value="${obj.planInfo}" />
		<input id="planId" type="hidden" value="${planInfo.planId}"/>
		<input id="curInstanceId" type="hidden" value="${planInfo.instanceId}"/>
		<button class="btn btn-default btn-sm" name="detail">流程信息</button>
		<c:if test="${planInfo.curInstance.ifSign eq '0'}"><button class="btn btn-warning btn-sm" name="sign">签收</button></c:if>
		<c:if test="${planInfo.curInstance.ifSign eq '1' && userInfo.userId == planInfo.curInstance.signUserId}">
			<button class="btn btn-default btn-sm" name="submit">提交</button>
			<button class="btn btn-default btn-sm" name="save">保存</button>
		</c:if>
		<button class="btn btn-default btn-sm" name="back" type="button" onclick="history.go(-1)">返回</button>
	</div>
	<script type="text/javascript">
		//流程提交键绑定
		$("button[name='submit']").bind("click",function() {
			var instanceId = $("#curInstanceId").val();
			window.open(WfURLPrefix+"/selectNextNode?instanceId=" + instanceId,
						"提交页面", "top=100,left=100,width=500,height=300,resizable=no");
		})
		
		//详细信息按钮绑定
		$("button[name='detail']").bind("click", function() {
			var planId = $("#planId").val();
			
			window.open(PlanURLPrefix+'/detail/'+planId,"详细信息");
		})
		
		//签收按钮绑定
		$("button[name='sign']").bind("click", function() {
			var c = confirm("确认签收此案件？");
			var instanceId = $("#curInstanceId").val();
			if(c){
				$.ajax({
				    url: WfURLPrefix + '/sign?instanceId='+instanceId,
				    dataType:'json',
				    type : 'GET',
				    success:function(re){
				    	resultPrompt(re,true,true,"签收成功");
				    }
				});
			}
		})
		
		//保存按钮绑定
		$("button[name='save']").bind("click", function() {
			var instanceId = $("#curInstanceId").val();
			$("form#opinionForm").find("input[name='opType']").val("暂存");
			
			var data = $("form#opinionForm").serializeObject();
			//alert(JSON.stringify(data));
			$.ajax({
			    url: PlanURLPrefix + '/save',
			    data: JSON.stringify(data),
			    dataType:'json',
			    type : 'POST',
			    success:function(re){
			        resultPrompt(re,true,true,"保存意见成功");
			    }
			}); 
		})
	</script>
</body>
</html>