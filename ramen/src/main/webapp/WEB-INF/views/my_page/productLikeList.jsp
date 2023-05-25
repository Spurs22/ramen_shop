<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--
  Created by IntelliJ IDEA.
  User: kun
  Date: 2023/05/14
  Time: 2:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">

	<style>
	
		.table-list thead > tr:first-child{ background: #f8f8f8; }
		.table-list th, .table-list td { text-align: center; }
		.table-list .left { text-align: left; padding-left: 5px; }
		
		.table-list .num { width: 150px; color: #787878; }
		.table-list .subject { width: 100px; color: #787878; }
		.table-list .date { width: 300px; color: #787878; }
		.table-list .hit { width: 70px; color: #787878; }
		.table-list .heart { width: 70px; color: #787878; }


        .product-container {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            grid-auto-rows: 300px;
            padding: 20px;
            gap: 30px;
            height: 90%;
			overflow: auto;
        }

        .product-item {
            width: 100%;
            height: 100%;
            border-radius: 10px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            gap: 10px;
            padding: 15px;
            transition: 0.5s;
            background: #ffffff;
            border: 1px solid #DFE2E6;
        }

        .product-img {
            width: 100%;
            height: 150px;
            object-fit: cover;
            border-radius: 3px;
			border: 1px solid gray;
        }

        .product-item:hover {
            cursor: pointer;
            background: #e8e8e8;
            /*filter: brightness(95%);*/
        }

		a {
			text-decoration: none;
			color: black;
		}

        .starBundle-comment {
            display: grid;
            font-size: 17px;
            width: 140px;
            grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
            justify-content: center;
            align-items: center;
            text-align: center;
            color: #F0974E;
        }

        .product-container {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            grid-auto-rows: 300px;
            padding: 20px;
            gap: 30px;
            height: 90%;
            overflow: auto;
        }

        .product-img {
            width: 100%;
            height: 150px;
            object-fit: cover;
            border-radius: 3px;
            border: 1px solid gray;
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
		<div class="sub-menu w-100">
			<div style="display: flex; flex-direction: column; width: 100%">
				<div class="btn-group" role="group" aria-label="Basic outlined example" style="height: 40px">
					<button class="btn btn-primary" onclick="location.href='${pageContext.request.contextPath}/mypage/productLikeList.do'"> 내가 찜 한 상품 </button>
					<button class="btn btn-outline-primary" onclick="location.href='${pageContext.request.contextPath}/mypage/recipeLikeList.do'"> 내가 좋아요 한 레시피 </button>
					<button class="btn btn-outline-primary" onclick="location.href='${pageContext.request.contextPath}/mypage/recipeBoardMyList.do'"> 내가 작성한 글 </button>
					<button class="btn btn-outline-primary" onclick="location.href='${pageContext.request.contextPath}/mypage/orderMyList.do'"> 나의 주문내역 </button>
				</div>
			</div>
		</div>

		<div class="content-container">
			<div class="product-container" id="resultForm">
				<c:forEach var="item" items="${list}" varStatus="status">
					<a class="product-item shadow" href="${pageContext.request.contextPath}/product/post-board?id=${item.product.productId}">
						<img class="product-img" src="${pageContext.request.contextPath}/resource/picture/${item.product.picture == null ? "default2.png" : item.product.picture}"/>
						<div style="margin-top: 5px; font-weight: 600">${item.product.name}</div>
						<div style="color: #5d5d5d">${item.price} 원</div>
						<div class="starBundle-comment"></div>
					</a>
				</c:forEach>
			</div>
		</div>
	</div>
</div>

<script>
    $(document).ready(function () {
        selectMenu(menuIndex)
    })
</script>

<script>
    let category = document.getElementsByName('category');

    let selectedCategory = 1

    function clickCategory(button) {
        selectedCategory = button.value
		getList()
    }

    function generateStars(rating) {
        let starsHTML = '';
        let first = Math.floor(rating);
        let second = rating % 1;

        for (let i = 1; i <= first; i++) {
            starsHTML += `<i class="fa-solid fa-star"></i>`;
        }

        if (first !== 5) {
            if (second !== 0) {
                starsHTML += `<i class="fa-solid fa-star-half-stroke"></i>`;
            } else {
                starsHTML += `<i class="fa-regular fa-star"></i>`;
            }

            for (let i = 1; i <= 4 - first; i++) {
                starsHTML += `<i class="fa-regular fa-star"></i>`;
            }
        }
        return starsHTML;
    }

</script>
<footer>
	<jsp:include page="/WEB-INF/views/fragment/footer.jsp"/>
</footer>
</body>
</html>


<%--			<table class="table-list">--%>
<%--				<thead>--%>
<%--					<tr class="table-list">--%>
<%--						<th class="num"> 번호 </th>--%>
<%--						<th class="num"> 라면 이름 </th>--%>
<%--						<th class="subject"> 가격 </th>--%>
<%--						<th class="date"> 찜한 날짜 </th>--%>
<%--						<th class="heart">찜</th>--%>
<%--					</tr>--%>
<%--				</thead>--%>
<%--					--%>
<%--				<tbody>--%>
<%--					<c:forEach var="item" items="${list}" varStatus="status">--%>
<%--						<tr>--%>
<%--							<td class="num">${status.index}</td>--%>
<%--							<td class="num">${item.product.name}</td>--%>
<%--							<td class="subject">${item.price}원</td>--%>
<%--							<td class="date">${item.createdDate}</td>--%>
<%--							<td><a href="${pageContext.request.contextPath}/product/post-board?id=${item.product.productId}"><i class="fa-solid fa-heart" style="color: red;"> </i></a></td>--%>
<%--						</tr>--%>
<%--					</c:forEach>--%>
<%--				</tbody>--%>
<%--			</table>--%>
