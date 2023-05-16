<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">

	<style>

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
			<h3> 결제가 완료되었습니다. 감사합니다 :) <h3>
			<table>
				<tr>
					<td>주문 번호 : ${orderId} </td>
				</tr>
				<tr>
					<td>총 가격 : ${totalPrice} </td>
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
