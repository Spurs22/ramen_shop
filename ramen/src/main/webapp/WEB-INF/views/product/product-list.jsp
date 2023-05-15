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
            border-radius: 5px;
        }

        .product-item:hover {
            cursor: pointer;
            background: #e8e8e8;
            /*filter: brightness(95%);*/
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
			margin-bottom: 20px;
			padding: 0 20px;
        }

		a {
			text-decoration: none;
			color: black;
		}

        .search-box {
            border: 1px solid #DFE2E6;
            border-radius: 5px;
            width: 150px;
            padding: 0 5px;

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

			<div class="btn-group" role="group" aria-label="Basic radio toggle button group">
				<input type="radio" class="btn-check" name="btnradio" id="btnradio1" autocomplete="off" checked>
				<label class="btn btn-outline-primary" for="btnradio1">봉지 라면</label>

				<input type="radio" class="btn-check" name="btnradio" id="btnradio2" autocomplete="off">
				<label class="btn btn-outline-primary" for="btnradio2">컵 라면</label>

				<input type="radio" class="btn-check" name="btnradio" id="btnradio3" autocomplete="off">
				<label class="btn btn-outline-primary" for="btnradio3">토핑</label>

			</div>

			<div style="display: flex; flex-direction: row; gap: 5px" >
				<input type="text" class="search-box">
				<button class="btn btn-primary">검색</button>
			</div>

			<div style="display: flex; flex-direction: row; gap: 5px">
<%--				<button class="btn btn-success" onclick="location.href='${pageContext.request.contextPath}/product/post-product-form'">상품 등록</button>--%>
				<button class="btn btn-success" onclick="location.href='${pageContext.request.contextPath}/product/post-form'">상품 등록</button>
			</div>
		</div>

		<div class="product-container">
			<c:forEach var="post" items="${posts}">
				<a class="product-item shadow" href="${pageContext.request.contextPath}/product/post?id=${post.productId}">
					<img class="product-img" src="${pageContext.request.contextPath}/resource/picture/1.png">
					<div style="margin-top: 5px; font-weight: 750">${post.productName}</div>
					<div style="color: #5d5d5d">${post.price}원</div>
					<div>${post.rating}</div>
				</a>
			</c:forEach>
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
