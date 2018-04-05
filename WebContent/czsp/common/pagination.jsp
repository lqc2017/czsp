<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分页</title>
<link href="/czsp/static/css/bootstrap/bootstrap.min.css"
	rel="stylesheet">

<script src="/czsp/static/js/jquery.js"></script>
<script src="/czsp/static/js/bootstrap/bootstrap.min.js"></script>
<script src="/czsp/static/js/common.js"></script>
</head>
<body>
	<div>
		<c:set var="pager" value="${obj.pagination.pager}" />
		<ul class="pagination">
			<li><a href="javascript:;">&laquo;</a></li>
			<c:choose>
				<c:when test="${pager.pageCount <= 5}">
					<c:forEach begin="1" end="${pager.pageCount}" varStatus="i">
						<li <c:if test="${pager.pageNumber == i.index}">class="active"</c:if>>
							<a href="javascript:;">${i.index}</a>
						</li>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${pager.pageNumber - 2 < 1}">
							<c:forEach begin="1" end="5" varStatus="i">
								<li <c:if test="${pager.pageNumber == i.index}">class="active"</c:if>>
									<a href="javascript:;">${i.index}</a>
								</li>
							</c:forEach>
						</c:when>
						<c:when test="${pager.pageNumber + 2 > pager.pageCount}">
							<c:forEach begin="${pager.pageCount - 4}" end="${pager.pageCount}" varStatus="i">
								<li <c:if test="${pager.pageNumber == i.index}">class="active"</c:if>>
									<a href="javascript:;">${i.index}</a>
								</li>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<c:forEach begin="${pager.pageNumber - 2}" end="${pager.pageNumber + 2}" varStatus="i">
								<li <c:if test="${pager.pageNumber == i.index}">class="active"</c:if>>
									<a href="javascript:;">${i.index}</a>
								</li>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
			<li><a href="javascript:;">&raquo;</a></li>
		</ul>
	</div>
	
	<script type="text/javascript">
		$("ul.pagination li[class!='active'] a").bind("click",function(){
			var pageNumber = $(this).text();
			$("input[name='pageNumber']").val(pageNumber);
			
			$("form#searchFrom").submit();
		})
	</script>
</body>
</html>