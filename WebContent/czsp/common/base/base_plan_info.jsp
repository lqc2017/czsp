<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<%@ page import="czsp.common.Constants"%>
<body>
	<table border="1" style="width:60%;">
		<c:set var="planInfo" value="${obj.planInfo}" />
		<c:set var="dicUtil" value="${obj.dicUtil}" />
		<c:set var="Constants" value="${obj.constants}" />
		<tr>
			<th width="15%"><label for="planName">规划名称</label></th>
			<td>${planInfo.planName}</td>
			
			<th width="20%"><label for="qxId">区县</label></th>
			<td>${dicUtil.getItemName(Constants.DIC_QX_NO,planInfo.qxId)}</td>
		</tr>
		<tr>
			<th><label for=curPhase>当前环节</label></th>
			<td>${dicUtil.getItemName(Constants.DIC_WF_PHASE_NO,planInfo.planApp.curPhase)}</td>
			
			<th><label for="curNode">当前节点</label></th>
			<td>${dicUtil.getItemName(Constants.DIC_WF_NODE_NO,planInfo.planApp.curNode)}</td>
		</tr>
		<tr>
			<th><label for="townName">村镇</label></th>
			<td>${planInfo.townName}</td>
			
			<th><label for="designDepartment">设计部门</label></th>
			<td>${planInfo.designDepartment}</td>
		</tr>
		
		<tr>
			<th><label for="planArea">规划面积</label></th>
			<td>${planInfo.planArea}km²</td>
			
			<th><label for="designContactName">设计部门联系人</label></th>
			<td>${planInfo.designContactName}</td>
		</tr>
		
		<tr>
			<th><label for="finishDate">预计办结日期</label></th>
			<td><fmt:formatDate value="${planInfo.finishDate}" type="date" /></td>
			
			<th><label for="designContactWay">设计部门联系方式</label></th>
			<td>${planInfo.designContactWay}</td>
		</tr>

		<tr>
			<th><label for="note">备注</label></th>
			<td colspan="3">${planInfo.note}</td>
		</tr>
	</table>
</body>
</html>