<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>주문상세</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

	<style>
		.table-list {
			padding: 5px;
        }

        .table-list thead > tr:first-child {
            background: #f8f8f8;
        }

        .table-list th, .table-list td {
            text-align: center;
            height: 70px;
        }

        .table-list .left {
            text-align: left;
            padding-left: 5px;
        }

        .href {
            text-decoration: none;
            color: gray;
        }



        .table-header {
            display: grid;
            grid-template-columns: 10% 20% 15% 17% 10% 10% 18%;
            width: 100%;
            justify-content: center;
            align-items: center;
            align-content: center;
            text-align: center;
            border: 1px solid black;
            padding: 10px;
            border-radius: 5px;
            /*background: #e6f1e8;*/
        }

        .table-main {
            display: grid;
            grid-template-columns: 10% 20% 15% 17% 10% 10% 18%;
            width: 100%;
            justify-content: center;
            align-items: center;
            align-content: center;
            text-align: center;
            border: 1px solid black;
            padding: 10px;
            border-radius: 5px;
            grid-template-rows: 100%;
            height: 110px;
            color: black;
        }

        .table-main:hover {
            cursor: pointer;
            background: rgba(161, 161, 161, 0.15);
        }

		.item {
			padding: 2px;
		}
	</style>
</head>
<script>
    let menuIndex = 5
</script>
<body>
<div class="whole-container" style="width: 1000px;">

	<header>
		<jsp:include page="/WEB-INF/views/fragment/menubar.jsp"/>
	</header>

	
	<div class="main-container shadow-lg">
		
	  <div class="content-container">
		<div class="sub-menu w-100">
			<div style="display: flex; flex-direction: column; width: 100%">
				<div class="btn-group" role="group" aria-label="Basic outlined example" style="height: 40px">
					<button class="btn btn-outline-primary" onclick="location.href='${pageContext.request.contextPath}/mypage/productLikeList.do'"> 내가 찜 한 상품 </button>
					<button class="btn btn-outline-primary" onclick="location.href='${pageContext.request.contextPath}/mypage/recipeLikeList.do'"> 내가 좋아요 한 레시피 </button>
					<button class="btn btn-outline-primary" onclick="location.href='${pageContext.request.contextPath}/mypage/recipeBoardMyList.do'"> 내가 작성한 글 </button>
					<button class="btn btn-primary" onclick="location.href='${pageContext.request.contextPath}/mypage/orderMyList.do'"> 나의 주문내역 </button>
				</div>
			</div>
		</div>
			<div><h2><i class="bi bi-receipt"></i> 주문 상세 </h2></div>
			
		 <div>
			<div style="display: flex; flex-direction: column; gap: 10px">
				<div class="table-header bg-light">
					<div class="item">주문 번호</div>
					<div class="item">이미지</div>
					<div class="item">제품 이름</div>
					<div class="item">제품 가격</div>
					<div class="item">제품 개수</div>
					<div class="item">총 금액</div>
					<div class="item">배송상태</div>
				</div>
					
				<c:forEach var="dto" items="${list}" varStatus="status">
				  <div class="table-main">
					<div class="item">${dto.orderItemId}</div>
					<div class="item"><img style="width: 20%;" class="product-img" src="${pageContext.request.contextPath}/resource/picture/${dto.picture == null ? 'default2.png' : dto.picture}"></div>
					<div class="item">${dto.productName}</div>
					<div class="item">${dto.price}원</div>
					<div class="item">${dto.quantity}개</div>
					<div class="item">${dto.totalPrice}원</div>
					<div class="item">
						<c:choose>
							<c:when test="${dto.statusName == '배송완료'}">
								<div>배송완료<a class="clickre" href="${pageContext.request.contextPath}/product/review-form?product-id=${dto.productId}&order-id=${dto.orderItemId}"><br>리뷰작성</a></div>
							</c:when>

							<c:when test="${dto.statusName == '배송중'}">
								<div>배송중</div>
							</c:when>

							<c:when test="${dto.statusName == '결제완료'}">
								<div>결제완료</div>
							</c:when>
							
							<c:when test="${dto.statusName == '결제취소'}">
								<div>결제취소</div>
							</c:when>
						</c:choose>
					</div>
					</div>
				</c:forEach>
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
<footer>
	<jsp:include page="/WEB-INF/views/fragment/footer.jsp"/>
</footer>
</body>
</html>
