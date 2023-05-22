<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>주문관리</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">

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
			 
			<table class="table">
				<tr>
					<td>
						${dataCount}개(${page}/${total_page} 페이지)
					</td>
				</tr>
			</table>
			
			<table class="table table-border table-list">
				<thead>
					<tr>
						<td class="orderBundleId">주문번호</td>
						<td class="createdDate">주문일</td>
						<td class="deliveryId">송장번호</td>
						<td class="receiveName">받는분</td>
						<td class="tel">전화번호</td>
						<td class="postNum">우편번호</td>
						<td class="address1">주소1</td>
						<td class="address2">주소2</td>
						<td class="userEmail">사용자이메일</td>
						<td class="userEmail">주문상태</td>
					</tr>
					<tr>
						<td>${orderBundlelist.orderBundleId}</td>
						<td>${orderBundlelist.createdDate}</td>
						<td>${orderBundlelist.deliveryId}</td>
						<td>${orderBundlelist.receiveName}</td>
						<td>${orderBundlelist.tel}</td>
						<td>${orderBundlelist.postNum}</td>
						<td>${orderBundlelist.address1}</td>
						<td>${orderBundlelist.address2}</td>
						<td>${orderBundlelist.userEmail}</td>
						<td>${orderBundlelist.statusName}</td>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<th class="orderItemId">주문상세번호</th>
						<th class="productName">상품명</th>
						<th class="price">가격</th>
						<th class="quantity">수량</th>
						<th class="finalPrice">합계</th>
					</tr>
					
					<c:forEach var="orderBundle" items="${orderBundlelist}">
						<tr>
							<td>${orderBundle.orderItemId}</td>
							<td>${orderBundle.productName}</td>
							<td>${orderBundle.price}</td>
							<td>${orderBundle.quantity}</td>
							<td>${orderBundle.finalPrice}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

			<table class="table">
				<tr>
					<td class="text-end">
							<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/admin/ordermanagement.do?${query}';">리스트</button>
					</td>
				</tr>
			</table>
			${paging}
		</div>
	</div>
</div>

<script>
    $(document).ready(function () {
        selectMenu(menuIndex)
    })
</script>
</body>
</html>