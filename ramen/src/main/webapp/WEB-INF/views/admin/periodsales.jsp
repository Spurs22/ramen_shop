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
			<div> 
				<button type='button' class='btn btnperiod'>전체</button>
				<button type='button' class='btn btnperiod'>1일</button>
				<button type='button' class='btn btnperiod'>1개월</button>
				<button type='button' class='btn btnperiod'>6개월</button>
				<button type='button' class='btn btnperiod'>1년</button>
			</div>
			<div>
				${os.sumquantity}
				${os.sumprice}
				${os.sumfinal_price}
				${os.productid}
				${os.productname}
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