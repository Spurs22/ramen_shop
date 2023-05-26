<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>주문관리</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/paginate.css" type="text/css">
	<style>
	.whole-container{
		width: 80%;
	}
	</style>
	<script>

	</script>
</head>
<script>
    let menuIndex = 5
</script>
<body>
<div class="whole-container">

	<header>
		<jsp:include page="/WEB-INF/views/fragment/menubar.jsp"/>
	</header>

	<div class="main-container shadow-lg">
		<div class="content-container">
			<div style="margin-bottom: 20px"><button class="btn btn-outline-secondary" onclick="history.back()">뒤로가기</button></div>
			
			<br>
			
			<h4>주문정보</h4>
			<hr>
			<table class="table table-border table-list" style="text-align:center;">
				<thead>
					<tr>
						<th class="orderBundleId">주문번호</th>
						<th class="createdDate">주문일</th>
						<th class="deliveryId">송장번호</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>${orderBundlelist.orderBundleId}</td>
						<td>${orderBundlelist.createdDate}</td>
						<td>${orderBundlelist.deliveryId}</td>
					</tr>
				</tbody>
			</table>
			

			<table class="table table-border table-list" style="text-align:center;">
				<thead>
					<tr>
						<th class="receiveName">받는분</th>
						<th class="tel">전화번호</th>
						<th class="postNum">우편번호</th>
						<th class="address1">기본주소</th>
						<th class="address2">상세주소</th>
						<th class="userEmail">사용자이메일</th>
						<th class="userEmail">주문상태</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>${orderBundlelist.receiveName}</td>
						<td>${orderBundlelist.tel}</td>
						<td>${orderBundlelist.postNum}</td>
						<td>${orderBundlelist.address1}</td>
						<td>${orderBundlelist.address2}</td>
						<td>${orderBundlelist.userEmail}</td>
						<td>${orderBundlelist.statusName}</td>
					</tr>
				</tbody>
			</table>
			<br>
			<br>
			<h4>주문상품정보</h4>
			<hr>
			<table class="table table-border table-list" style="text-align:center;">
				<thead>
					<tr>
						<th class="productName">상품명</th>
						<th class="price">가격</th>
						<th class="quantity">수량</th>
						<th class="totalPrice">합계</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="orderitems" items="${orderBundlelist.orderItems}">
						<tr>
							<td>${orderitems.productName}</td>
							<td>${orderitems.price}</td>
							<td>${orderitems.quantity}</td>
							<td>${orderitems.totalPrice}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

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