<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>관리자</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">

	<style>
	.whole-container{
		width: 80%;
	}
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
			<form action="location.href='${pageContext.request.contextPath}/admin/main.do}'">
	
				<div>
					<button type="button" onclick="location.href='${pageContext.request.contextPath}/admin/deliverymanagement.do';">배송관리</button>
				</div>
				
				<div>
					<button type="button" onclick="location.href='${pageContext.request.contextPath}/admin/ordermanagement.do';">주문관리</button>
				</div>
				
				<div>
					<button type="button" onclick="location.href='${pageContext.request.contextPath}/admin/sales_statistics.do';">매출통계</button>
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
