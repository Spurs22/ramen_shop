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
.table-list th, .table-list td { text-align: center; }
.table-list .left { text-align: left; padding-left: 5px; }

.table-list .num { width: 50px; color: #787878; }
.table-list .delivery { width: 150px; color: #787878; }
.table-list .date { width: 200px; color: #787878; }
.table-list .receiveName { width: 70px; color: #787878; }
.table-list .price { width: 70px; color: #787878; }
</style>

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
			<div><h2> 내 주문 내역</h2></div>
			
			<div>
				<table>
					<tr>
						<td width="50%"> ${dataCount}개(${page}/${total_page} 페이지) </td>
						<td align="right">&nbsp;</td>
					</tr>
				</table>
				
				<table>
					<thead>
					<tr class="table-list">
						<th class="num">주문번호</th>
						<th class="delivery">송장번호</th>
						<th class="date">주문일</th>
						<th class="receiveName">주문상태</th>
						<th class="price">총 금액</th>
					</tr>
					</thead>
					
					<tbody>
						<c:forEach var="dto" items="${list}" varStatus="status">
						<tr>
							<td>${dataCount - (page-1) * size - status.index}
								<a href="${articleUrl}&memberId=${dto.memberId}">&gt;</a>
							</td>
							<td>${dto.deliveryId}</td>
							<td>${dto.createdDate}</td>
							<td>${dto.statusName}</td>
							<td>${dto.totalPrice}</td>
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
