<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/paginate.css" type="text/css">

<style>
.table-list thead > tr:first-child{ background: #f8f8f8; }
.table-list th, .table-list td { text-align: center; height: 70px; }
.table-list .left { text-align: left; padding-left: 5px; }
.href { text-decoration: none; color: gray; }

.table-list .num { width: 40px; color: #787878; }
.table-list .delivery { width: 70px; color: #787878; }
.table-list .date { width: 130px; color: #787878; }
.table-list .addr { width: 300px; color: #787878; }
.table-list .receiveName { width: 100px; color: #787878; }
.table-list .status { width: 70px; color: #787878; }
.table-list .price { width: 100px; color: #787878; }
.table-list .tel { width: 170px; color: #787878; }
</style>

</head>
<script>
    let menuIndex = 5
 
</script>
<body>
<div class="whole-container" style="width:1150px;">

	<header>
		<jsp:include page="/WEB-INF/views/fragment/menubar.jsp"/>
	</header>

	<div class="main-container shadow-lg">
		<div class="content-container">
			<div><h2> 내 주문 내역</h2></div>
			
			<div>
				<table>
					<tr>
						<td width="50%"> ${dataCount}개(${page}/${total_page} 페이지) </td>
						<td align="right">&nbsp;</td>
					</tr>
				</table>
				
				<table class="table-list">
					<thead>
					<tr class="table-list">
						<th class="num">번호</th>
						<th class="delivery">송장번호</th>
						<th class="receiveName">받는 사람</th>
						<th class="date">주문일</th>
						<th class="addr">주소</th>
						<th class="tel">전화번호</th>
						<th class="price">총 금액</th>
						<th class="status">주문상태</th>
					</tr>
					</thead>
					
					<tbody>
						<c:forEach var="dto" items="${list}" varStatus="status">
						<tr>
							<td><a class="href" onclick="location.href='${pageContext.request.contextPath}/mypage/articleorderlist.do?orderBundleId=${dto.orderBundleId}';">${dto.orderBundleId}&gt;</a></td>
							<td>${dto.deliveryId}</td>
							<td>'${dto.receiveName}' 님</td>
							<td>${dto.createdDate}</td>
							<td>${dto.postNum} ${dto.address2} ${dto.address1}</td>
							<td>${dto.tel}</td>							
							<td>${dto.totalPrice}원</td>
							
							<c:choose>
								<c:when test="${dto.statusName == '배송완료'}">
									<td>${dto.statusName}<button type="button">주문취소</button></td>
								</c:when>
								
								<c:when test="${dto.statusName == '결제완료'}">
									<td>${dto.statusName}</td>
								</c:when>
								
								<c:when test="${dto.statusName == '배송중'}">
									<td>${dto.statusName}</td>
								</c:when>
							</c:choose>

						</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<div class="page-navigation">
					${dataCount == 0 ? "등록된 게시물이 없습니다." : paging}
				</div>
				
			</div>
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
