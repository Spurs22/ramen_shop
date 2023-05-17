<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
	
	table{
		border-top: 1.8px solid #A2A2A2;
	}
	
	tr{
		border-bottom: 1px solid #B4B4B4; 
	}
	
	.tdname{
		width:30%;
		padding:20px;
		background: #eee;
	}
	
	.tdvalue{
		width:70%;
		padding:10px;
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
				<i class="fa-solid fa-list-check fa-lg" id="OkImg"></i>
				<div class="OkText"> 결제가 완료 되었습니다.  </div>
				<div class="OkText2">감사합니다 :)</div>
			</div>	
			<br>
			
			<div> 주문 요약 정보  </div>
			<br>
			<table>
				
				
				<tr>
					<td class="tdname"> 주문 번호 </td>
					<td class="tdvalue">${orderId} </td>
				</tr>
				<tr>
					<td class="tdname"> 총 가격 </td>
					<td class="tdvalue"> ${totalPrice} </td>
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
