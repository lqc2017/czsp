<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<body>
	<br/>
	<form id="opinionForm">
		<table class="table table-bordered">
			<tr>
				<th width="15%" style="text-align:center;vertical-align:middle;">办理意见</th>
				<td>
					<c:choose>
						<c:when test="${obj.planOpinion == null}" >
							<c:set var="planInfo" value="${obj.planInfo}" />
							<input type="hidden" name="planId" value="${planInfo.planId }"/>
							<input type="hidden" name="nodeId" value="${planInfo.planApp.curNode }"/>
							<input type="hidden" name="phaseId" value="${planInfo.planApp.curPhase }"/>
							<input type="hidden" name="instanceId" value="${planInfo.instanceId }"/>
							<input type="hidden" name="opType"/>
							<input type="hidden" name="createBy" value="${userInfo.userId}"/>
							<textarea name="opinionContent" style="width:98%;height:100px;"></textarea>
						</c:when>
						<c:otherwise>
							<c:set var="opinion" value="${obj.planOpinion}" />
							<input type="hidden" name="opinionId" value="${opinion.opinionId }"/>
							<input type="hidden" name="opType"/>
							<textarea name="opinionContent" style="width:98%;height:100px;">${opinion.opinionContent}</textarea>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>