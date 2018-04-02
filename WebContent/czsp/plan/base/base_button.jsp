<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
	<c:set var="planInfo" value="${obj.planInfo}" />
	<input id="planId" type="hidden" value="${planInfo.planId}"/>
	<input id="curInstanceId" type="hidden" value="${planInfo.instanceId}"/>
	<button name="detail">流程信息</button>
	<c:if test="${planInfo.curInstance.ifSign eq '0'}"><button name="sign">签收</button></c:if>
	<c:if test="${planInfo.curInstance.ifSign eq '1' && userInfo.userId == planInfo.curInstance.signUserId}">
	<button name="submit">提交</button>
	</c:if>
	<button name="save">保存</button>
	
	<script type="text/javascript">
		//流程提交键绑定
		$("button[name='submit']").bind("click",function() {
			var instanceId = $("#curInstanceId").val();
			window.open(WfURLPrefix+"/selectNextNode?instanceId=" + instanceId,
						"提交页面", "top=100,left=100,width=500,height=200,resizable=no");
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
				        console.log(re.result);
				        if (re.result == 'success'){
							alert("签收成功!");
							location.reload();
						}else
							alert("message : " + re.message);
				    }
				});
			}
		})
	</script>
</body>
</html>