<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page import="czsp.common.Constants"%>
<body>
	<table border="1" style="width:60%;">
		<c:set var="planInfo" value="${obj.planInfo}" />
		<c:set var="dicUtil" value="${obj.dicUtil}" />
		<c:set var="Constants" value="${obj.constants}" />
		<tr>
			<td width="15%"><label for="planName">规划名称</label></td>
			<td>${planInfo.planName}</td>
			
			<td width="20%"><label for="qxId">区县</label></td>
			<td>${dicUtil.getItemName(Constants.DIC_QX_NO,planInfo.qxId)}</td>
		</tr>
		<tr>
			<td><label for=curPhase>当前环节</label></td>
			<td>${dicUtil.getItemName(Constants.DIC_WF_PHASE_NO,planInfo.planApp.curPhase)}</td>
			
			<td><label for="curNode">当前节点</label></td>
			<td>${dicUtil.getItemName(Constants.DIC_WF_NODE_NO,planInfo.planApp.curNode)}</td>
		</tr>
		<tr>
			<td><label for="townName">村镇</label></td>
			<td>${planInfo.townName}</td>
			
			<td><label for="designDepartment">设计部门</label></td>
			<td>${planInfo.designDepartment}</td>
		</tr>
		
		<tr>
			<td><label for="planArea">规划面积</label></td>
			<td>${planInfo.planArea}km²</td>
			
			<td><label for="designContactName">设计部门联系人</label></td>
			<td>${planInfo.designContactName}</td>
		</tr>
		
		<tr>
			<td><label for="finishDate">预计办结日期</label></td>
			<td><fmt:formatDate value="${planInfo.finishDate}" type="date" /></td>
			
			<td><label for="designContactWay">设计部门联系方式</label></td>
			<td>${planInfo.designContactWay}</td>
		</tr>

		<tr>
			<td><label for="note">备注</label></td>
			<td colspan="3">${planInfo.note}</td>
		</tr>
	</table>
</body>
</html>