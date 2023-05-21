<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>주문관리</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">

	<style>

	</style>
</head>
<script>
    let menuIndex = 6
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
						<th class="orderBundleId">주문번호</th>
						<th class="deliveryId">송장번호</th>
						<th class="createdDate">주문일</th>
						<th class="receiveName">받는분</th>
						<th class="tel">전화번호</th>
						<th class="postNum">우편번호</th>
						<th class="address1">주소1</th>
						<th class="address2">주소2</th>
						<th class="userEmail">사용자이메일</th>
					</tr>
				</thead>
				
				<tbody>
					<c:forEach var="orderBundle" items="${list}" varStatus="status"> <!-- list수정 필요 -->
						<tr>
							<td>${dataCount - (page-1) * size - status.index}</td>
							<!--
							<td class="left">
								<a href="${articleUrl}&num=${dto.num}">${dto.subject}</a>
							</td>
							  -->
							<td>${ob.orderBundleId}</td>
							<td>${ob.deliveryId}</td>
							<td>${ob.createdDate}</td>
							<td>${ob.receiveName}</td>
							<td>${ob.tel}</td>
							<td>${ob.postNum}</td>
							<td>${ob.address1}</td>
							<td>${ob.address2}</td>
							<td>${ob.userEmail}</td>
							<!-- orderitem 출력하기.. List 출력 -->
						</tr>
					</c:forEach>
				</tbody>
			</table>

			<table class="table">
				<tr>
					<td width="100">
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/admin/deliverymanagement.do';" title="송장번호 등록">송장번호 등록</button>
						<!-- 버튼 눌렀을 때 서버 넘기기? -->
					</td>
				</tr>
			</table>
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