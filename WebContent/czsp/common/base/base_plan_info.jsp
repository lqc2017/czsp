<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page import="czsp.common.Constants"%>
<body>
	<table border="1">
		<c:set var="planInfo" value="${obj.planInfo}" />
		<c:set var="planApp" value="${obj.planApp}" />
		<c:set var="dicUtil" value="${obj.dicUtil}" />
		<c:set var="Constants" value="${obj.constants}" />
		<tr>
			<td><label for="planName">规划名称</label></td>
			<td>${planInfo.planName}</td>
		</tr>
		<tr>
			<td><label for="phases">规划环节</label></td>
			<td>${planApp.phases}</td>
		</tr>
		<tr>
			<td><label for="qxId">区县</label></td>
			<td>${dicUtil.getItemName(Constants.DIC_QX_NO,planInfo.qxId)}</td>
		</tr>
		<tr>
			<td><label for="townName">村镇</label></td>
			<td>${planInfo.townName}</td>
		</tr>
		<tr>
			<td><label for="planArea">规划面积</label></td>
			<td>${planInfo.planArea}</td>
		</tr>
		<tr>
			<td><label for="designDepartment">设计部门</label></td>
			<td>${planInfo.designDepartment}</td>
		</tr>
		<tr>
			<td><label for="designContactName">设计部门联系人</label></td>
			<td>${planInfo.designContactName}</td>
		</tr>
		<tr>
			<td><label for="designContactWay">设计部门联系方式</label></td>
			<td>${planInfo.designContactWay}</td>
		</tr>
	</table>
</body>
</html>