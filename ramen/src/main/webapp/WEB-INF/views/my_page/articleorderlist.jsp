<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">

	<style>
	.table-list thead > tr:first-child{ background: #f8f8f8; }
	.table-list th, .table-list td { text-align: center; height: 70px; }
	.table-list .left { text-align: left; padding-left: 5px; }
	
	.table-list .num { width: 150px; color: #787878; }
	.table-list .product { width: 150px; color: #787878; }
	.table-list .price { width: 200px; color: #787878; }
	.table-list .quantity { width: 100px; color: #787878; }
	.table-list .total { width: 250px; color: #787878; }
	.table-list .status { width: 150px; color: #787878; }
	.table-list .clickre { color: black; }
	</style>
</head>
<script>
    let menuIndex = 5
</script>
<body>
<div class="whole-container" style="width: 1000px;">

	<header>
		<jsp:include page="/WEB-INF/views/fragment/menubar.jsp"/>
	</header>

	<div class="main-container shadow-lg">
		<div class="content-container">
			<div><h2> 주문 상세 </h2></div>
			
			<div>
				<table  class="table-list">
					<thead>
					<tr class="table-list">
						<th class="num">주문 번호</th>
						<th class="product">제품 번호</th>
						<th class="price">제품 가격</th>
						<th class="quantity">제품 개수</th>
						<th class="total">총 금액</th>
						<th class="status">배송상태</th>
					</tr>
					</thead>
					
					<tbody>
						<c:forEach var="dto" items="${list}" varStatus="status">
						<tr>
							<td>${dto.orderItemId}</td>
							<td>${dto.productId}</td>
							<td>${dto.price}원</td>
							<td>${dto.quantity}개</td>
							<td>${dto.totalPrice}원</td>
							<c:choose>
								<c:when test="${dto.statusName == '배송완료'}">
									<td>${dto.statusName}<a class="clickre" href="#">리뷰작성하러가기</a></td>
								</c:when>
								
								<c:when test="${dto.statusName == '배송중'}">
									<td>${dto.statusName}</td>
								</c:when>
								
								<c:when test="${dto.statusName == '결제완료'}">
									<td>${dto.statusName}</td>
								</c:when>
							</c:choose>
							
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

<script>
    $(document).ready(function () {
        selectMenu(menuIndex)
    })
</script>
<footer>
	<jsp:include page="/WEB-INF/views/fragment/footer.jsp"/>
</footer>
</body>
</html>
