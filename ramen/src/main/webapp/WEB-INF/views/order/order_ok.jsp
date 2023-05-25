<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
	<title>Title</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v6.4.0/css/all.css">
	<style>
	.Okdiv{
		display:flex; 
		flex-direction:column; 
		justify-content:center; 
		width:100%; 
		height: 150px;
	}
	
	#OkImg{
		padding-top: 40px;
		height:100px;
		margin-bottom:15px; 
		text-align:center; 
		display:block;
		font-size: 100px;
	}
	
	.OkText{
		flex:1;
		text-align:center; 
		width:100%; 
		font-weight:bold;
	}
	
	.OkText2{
		text-align:center; 
		width:100%; 
		font-size:13px;
		padding-top:5px;
	}

	tr{
		border-bottom: 1px solid #D8D8D8; 
	}
	
	.tdname{
		border-top : 1px solid #D8D8D8;
		width:30%;
		padding:20px;
		background: #eee;
	}
	
	.tdvalue{
		border-top : 1px solid #D8D8D8;
		width:70%;
		padding:10px;
	}
	
	hr{
		border: 2px solid gray;
	}
	.title{
		font-weight: bold;
		font-size: 20px;
		padding-top:40px;
	}	
	
	.item1, .item2{
		padding-bottom: 10px;
	}
	
	</style>
</head>
<script>
	// let menuIndex = 5
</script>
<body>
<div class="whole-container">

	<header>
		<jsp:include page="/WEB-INF/views/fragment/menubar.jsp"/>
	</header>

	<div class="main-container shadow-lg">
		<div class="content-container">
			<div class="Okdiv">
				<i class="fa-solid fa-list-check fa-lg" id="OkImg" style="color:#eee"></i>
				<div class="OkText"> 결제가 완료 되었습니다.  </div>
				<div class="OkText2">감사합니다 :)</div>
			</div>	
			<br>
			
			<p class="title"> 주문 정보 </p>
			<hr>
			<table>
				<tr>
					<td class="tdname"> 주문 번호 </td> 
					<td class="tdvalue">${orderId} </td>
				</tr>
				<tr>
					<td class="tdname"> 총 가격 </td>
					<td class="tdvalue"> <fmt:formatNumber value="${totalPrice}"/> </td>
				</tr>
			</table>
			
			 
			<p class="title"> 주문 결제</p>
				<hr>
					<table>
						<thead>
								<tr>
									<th class="item1">상품이미지</th>
									<th class="item2">품명</th>
									<th class="item2">수량</th>
									<th class="item2">소계</th>
								</tr>
						</thead>

							<c:forEach var="order" items="${list2}">
								<tr>
									<td class="itemtd">
									<img class="product-img"
												src="${pageContext.request.contextPath}/resource/picture/${order.picture}"
												style="height: 100px;">
									</td>
									<td class="itemtd">${order.productName}</td>
									<td class="itemtd">${order.quantity}</td>
									<td class="itemtd">${order.price}</td>
								</tr>
							</c:forEach>
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
