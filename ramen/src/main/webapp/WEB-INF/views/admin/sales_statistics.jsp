<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>기간별 매출통계</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">

	<style>

	</style>
</head>
<script>
    let menuIndex = 9
</script>
<body>
<div class="whole-container">

	<header>
		<jsp:include page="/WEB-INF/views/fragment/menubar.jsp"/>
	</header>

	<div class="main-container shadow-lg">
		<div class="content-container">
		<form action="location.href='${pageContext.request.contextPath}/admin/sales_statistics.do}'">
			<div> 
				<button type='button' class='btn btnperiod'>전체</button>
				<button type='button' class='btn btnperiod'>1일</button>
				<button type='button' class='btn btnperiod'>1개월</button>
				<button type='button' class='btn btnperiod'>6개월</button>
				<button type='button' class='btn btnperiod'>1년</button>
			</div>
			
			<div>
			<p>
			상품번호 | 상품명 | 판매수량 | 총매출합계
			</p>
			<c:forEach var="sals" items="${os}">
				<p>
				${sals.productid}
				${sals.productname}
				${sals.sumquantity}
				${sals.sumfinal_price}
				</p>
			</c:forEach>
			</div>
			</form>
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