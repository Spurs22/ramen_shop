<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
	<title>Title</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">

	<style>
	
	table{
		width:100%;
	}
	
	th{
		font-size: 20px;
		width: 40px;
		padding-bottom: 20px;
	}
	
	td{
		border-top: 2px solid gray;
		border-right: 1px solid #eee;
		border-bottom: 1px solid gray;
	}
	
	.btn {
		border-radius: 10px;
		background: lightgray;
	}
	
	.btn:hover{
		background: gray;
	}
	
	.main-container{
		overflow: auto;
	}
	
	
	</style>
	
	<script src="https://code.jquery.com/jquery-3.6.4.js"></script>
	<script type="text/javascript">
	$(function(){
		$("#chkAll").click(function(){
			if($(this).is(":checked")) {
				$("input[name=productIds]").prop("checked", true);
			} else {
				$("input[name=productIds]").prop("checked", false);
			}
		});
		
		$("#btnDeleteList").click(function(){
			let cnt = $("input[name=productIds]:checked").length;
			if(cnt === 0) {
				alert("삭제할 물품을 먼저 선택하세요.");
				return false;
			}
			
			if(confirm("선택한 물품을 삭제 하시겠습니까 ?")) {
				const f = document.listForm;
				f.action="${pageContext.request.contextPath}/cart/list-delete.do";
				f.submit();
			}
		});
		
		$("#btnOrder").click(function(){
			let cnt = $("input[name=productIds]:checked").length;
			if(cnt === 0) {
				alert("결제할 물품을 먼저 선택하세요.");
				return false;
			}
			
			if(confirm("선택한 물품을 결제 하시겠습니까 ?")) {
				const f = document.listForm;
				f.action="${pageContext.request.contextPath}/order/order.do";
				f.submit();
			}
		});
	});
	</script>
</head>
<body>
<div class="whole-container">

	<header>
		<jsp:include page="/WEB-INF/views/fragment/menubar.jsp"/>
	</header>

	<div class="main-container shadow-lg">
		<div class="product-container">
			<form name="listForm" method="post">
			<table>
				<thead>
				<tr>
					<th class="chk">
						<input type="checkbox" name="chkAll" id="chkAll">        
					</th>
					<th>전체 선택</th>
				</tr>
				</thead>
				
				<tbody>
					<c:forEach var="cart" items="${list}">
						<tr>
							<td>
								<input type="checkbox" name="productIds" value="${cart.productId}">
							</td>
							<td><img class="product-img" src="${pageContext.request.contextPath}/resource/picture/1.png"></td>
							<td>${cart.productId}</td>
							<td>${cart.quantity}</td>
							<td>상품금액<br> ${cart.createdDate}</td>
							<td>${cart.price*cart.quantity}</td>
						</tr>
					</c:forEach>
					
					<c:if test="${dataCount == 0}">
						<tr>		
							<td>등록된 게시물이 없습니다.</td>
						</tr>		
					</c:if>
				</tbody>
			</table>
					<br>
					<button type="button" class="btn" id="btnDeleteList">삭제</button>
					<button type="button" class="btn" id="btnOrder">결제</button>
			</form>
		</div>
	</div>


</div>
</body>
</html>
