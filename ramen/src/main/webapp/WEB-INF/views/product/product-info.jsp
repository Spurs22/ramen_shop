<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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


        .product-img {
            width: 100%;
            height: 150px;
            object-fit: cover;
            border-radius: 5px;
        }

        .sub-menu button {
            border-radius: 5px;
        }

        .sub-menu {
            width: 100%;
            height: 40px;
            display: flex;
            flex-direction: row;
            gap: 5px;
            justify-content: space-between;
            margin-bottom: 10px;
            padding: 0 20px;
        }

		.product-name {
			font-size: 30px;
			font-weight: 650;
		}

		.product-price {
			font-size: 25px;
			font-weight: 500;
		}

		.product-price span {
			font-size: 18px;
		}

		.product-content-container {
			background: rgba(241, 241, 241, 0.8);
			height: 800px;
			padding: 10px;
		}

		.product-content {
			height: 100%;
		}
	</style>
</head>
<script>
    let menuIndex = 2
</script>
<body>
<div class="whole-container">

	<header>
		<jsp:include page="/WEB-INF/views/fragment/menubar.jsp"/>
	</header>

	<div class="main-container shadow-lg">

		<div class="sub-menu">
			<div style="display: flex; flex-direction: row; gap: 5px" >
<%--				<input type="text" style="width: 150px; padding: 0 5px">--%>
<%--				<button class="btn btn-primary">검색</button>--%>
			</div>

			<div style="display: flex; flex-direction: row; gap: 5px">
				<button class="btn btn-success">상품 수정</button>
				<button class="btn btn-success">상품 삭제</button>
			</div>
		</div>

		<div class="content-container">
			<div style="display: flex; flex-direction: row; gap: 50px">
				<div style="width: 50%; background: #c5c5c5; aspect-ratio: 1/1"></div>

				<div style="display: flex; flex-direction: column; gap: 15px; align-items: end; flex: 1; justify-content: space-between">
					<div class="product-createdDate">${post.createdDate}</div>
					<div class="product-name">${post.productName}</div>
					<div class="product-price">${post.price}<span>원</span></div>
					<div class="product-rating">${post.rating}</div>

					<div style="display: flex; flex-direction: column; width: 100%; gap: 5px">
						<div style="display: flex; flex-direction: row; gap: 5px">
							<button class="btn btn-success" style="flex: 1">장바구니</button>
							<button class="btn btn-success" style="flex: 1">구매하기</button>
						</div>
						<button class="btn btn-secondary w-100">상품이 포함된 레시피 조회</button>
					</div>
				</div>

			</div>

			<div class="product-content-container">
				<div class="product-content">${post.content}</div>
			</div>

		</div>
	</div>

	<%--
	<div>
		<p>${post.productId}</p>
		<p>${post.productName}</p>
		<p>${post.writerId}</p>
		<p>${post.content}</p>
		<p>${post.createdDate}</p>
	</div>

	--%>


</div>

<script>
    $(document).ready(function () {
        selectMenu(menuIndex)
    })
</script>
</body>
</html>
