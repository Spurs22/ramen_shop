<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>주문내역</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/paginate.css" type="text/css">
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

        .table-list .num {
            width: 40px;
            color: #787878;
        }

        .table-list .delivery {
            width: 70px;
            color: #787878;
        }

        .table-list .date {
            width: 130px;
            color: #787878;
        }

        .table-list .addr {
            width: 300px;
            color: #787878;
        }

        .table-list .receiveName {
            width: 100px;
            color: #787878;
        }

        .table-list .status {
            width: 70px;
            color: #787878;
        }

        .table-list .price {
            width: 100px;
            color: #787878;
        }

        .table-list .tel {
            width: 170px;
            color: #787878;
        }

        .table-header {
            display: grid;
            grid-template-columns: 5% 10% 10% 10% 28% 15% 12% 10%;
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
            grid-template-columns: 5% 10% 10% 10% 28% 15% 12% 10%;
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
    
    function ordercancel(orderId)  {
    	let url = "${pageContext.request.contextPath}/mypage/orderCancel?orderId="+orderId;
    	location.href= url;
    }

</script>
<body>
<div class="whole-container" style="width:1150px;">

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

			<div><h2><i class="bi bi-receipt"></i> 주문 내역 </h2></div>

<%--					<c:forEach var="dto" items="${list}" varStatus="status">--%>
<%--						<tr>--%>
<%--							<td><a class="href"--%>
<%--								   onclick="location.href='${pageContext.request.contextPath}/mypage/articleorderlist.do?orderBundleId=${dto.orderBundleId}';">${dto.orderBundleId}&gt;</a>--%>
<%--							</td>--%>
<%--							<td>${dto.deliveryId}</td>--%>
<%--							<td>${dto.receiveName}</td>--%>
<%--							<td>${dto.createdDate}</td>--%>
<%--							<td>${dto.postNum} ${dto.address2} ${dto.address1}</td>--%>
<%--							<td>${dto.tel}</td>--%>
<%--							<td>${dto.totalPrice}원</td>--%>

<%--							<c:choose>--%>
<%--								<c:when test="${dto.statusName == '배송완료'}">--%>
<%--									<td>${dto.statusName}--%>
<%--										<button type="button">주문취소</button>--%>
<%--									</td>--%>
<%--								</c:when>--%>

<%--								<c:when test="${dto.statusName == '결제완료'}">--%>
<%--									<td>${dto.statusName}</td>--%>
<%--								</c:when>--%>

<%--								<c:when test="${dto.statusName == '배송중'}">--%>
<%--									<td>${dto.statusName}</td>--%>
<%--								</c:when>--%>
<%--							</c:choose>--%>

<%--						</tr>--%>
<%--					</c:forEach>--%>




			<div>
				<div style="margin: 10px 0">
					<div> ${dataCount}개 (${page}/${total_page} 페이지) </div>
				</div>

				<div style="display: flex; flex-direction: column; gap: 10px">
					<div style="" class="table-header bg-light">
						<div class="item">번호</div>
						<div class="item">송장번호</div>
						<div class="item">받는 사람</div>
						<div class="item">주문일</div>
						<div class="item" style="padding: 0 10px">주소</div>
						<div class="item">전화번호</div>
						<div class="item">결제금액</div>
						<div class="item">주문상태</div>
					</div>

<%--					<c:forEach var="dto" items="${list}" varStatus="status">--%>
<%--						<div style="" class="table-main" onclick="location.href='${pageContext.request.contextPath}/recipe/recipe.do?id=${dto.id}'">--%>
<%--							<div>${dataCount - (page-1) * size - status.index} </div>--%>
<%--							<img class="recipe-img" src="${pageContext.request.contextPath}/resource/picture/${dto.picture == null ? 'default2.png' : dto.picture}">--%>
<%--							<div>${dto.subject}</div>--%>
<%--							<div>${dto.hitCount}</div>--%>
<%--							<div><i class="fa-solid fa-heart" style="color: red;"> </i>${dto.recipeLikeCount}</div>--%>
<%--						</div>--%>
<%--					</c:forEach>--%>

					<c:forEach var="dto" items="${list}" varStatus="status">
						<div style="" class="table-main" onclick="location.href='${pageContext.request.contextPath}/mypage/articleorderlist.do?orderBundleId=${dto.orderBundleId}'">
							<div class="item">${dto.orderBundleId}</div>
							<div class="item" style="padding: 0 5px;" >
<%--									${dto.deliveryId}--%>
								<button class="btn btn-outline-secondary" style="font-size: 13px">송장<br>확인</button>
							</div>
							<div class="item">${dto.receiveName}</div>
							<div class="item">${dto.createdDate}</div>
							<div class="item" style="padding: 0 10px">[${dto.postNum}] ${dto.address1} ${dto.address2}</div>
							<div class="item">${dto.tel}</div>
							<div class="item">${dto.totalPrice}원</div>
							<c:choose>
								<c:when test="${dto.statusName == '배송완료'}">
									<div>배송완료</div>
								</c:when>

								<c:when test="${dto.statusName == '결제완료'}">
									<div>결제완료<button class="btn btn-outline-primary" type="button">주문취소</button></div>
								</c:when>

								<c:when test="${dto.statusName == '배송중'}">
									<div>배송중</div>
								</c:when>
								
								<c:when test="${dto.statusName == '주문취소'}">
									<div>주문취소</div>
								</c:when>
							</c:choose>
						</div>
					</c:forEach>

					<div class="page-navigation">
						${dataCount == 0 ? "등록된 게시물이 없습니다." : paging}
					</div>
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
