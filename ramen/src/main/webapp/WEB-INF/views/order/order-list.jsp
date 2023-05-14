<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
	<title>Title</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">

	<style>
	</style>
	
	<script type="text/javascript">
	</script>
</head>
<body>
<div class="whole-container">

	<header>
		<jsp:include page="/WEB-INF/views/fragment/menubar.jsp"/>
	</header>

	<div class="main-container shadow-lg">
		<div class="product-container">
			<form name="listForm" method="post">
			<table>
				<thead>
				<tr>
					<th>상품코드</th>
					<th>상품사진</th>
					<th>상품개수</th>
					<th>상품 추가 날짜</th>
				</tr>
				</thead>
				
				<tbody>
					<c:forEach var="cart" items="${list}">
						<tr>
							<td>${cart.productId}</td>
							<td>상품사진</td>
							<td>${cart.quantity}</td>
							<td>${cart.createdDate}</td>
						</tr>
					</c:forEach>
					
					<c:if test="${dataCount == 0}">
						<tr>		
							<td>등록된 게시물이 없습니다.</td>
						</tr>		
					</c:if>
				</tbody>
				
			</table>
			</form>
		</div>
	</div>


</div>
</body>
</html>
