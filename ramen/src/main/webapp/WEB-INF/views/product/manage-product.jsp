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

        .main-content-card {
            text-align: left;
            background: white;
            border-radius: 20px;
            /*min-height: 400px;*/
            width: 100%;
        }

        textarea {
            height: 200px;
        }

        .product-card {
            width: 100%;
            height: 50px;
            display: grid;
            grid-template-columns: 45% 40% 15%;
            border-radius: 5px;
            text-align: center;
            padding: 0 5px;
            align-items: center;
            border: 1px solid #eaeaea;
            transition: 0.5s;
        }

        .product-card-menu {
            width: 100%;
            height: 50px;
            display: grid;
            grid-template-columns: 45% 40% 15%;
            border-radius: 5px;
            text-align: center;
            padding: 0 5px;
            align-items: center;
            border: 1px solid #eaeaea;
            transition: 0.5s;
            color: white;
        }

        .product-card-container {
            display: flex;
            flex-direction: column;
            gap: 10px;
            width: 100%;
        }

        .product-card:hover {
            background: #e7e7e7;
            cursor: pointer;
        }

        .selected-product {
            height: 100px;
            border: 1px solid #DFE2E6;
            border-radius: 8px;
            margin-top: 20px;
        }

        .product-info {
            text-align: center;
        }

        .input-group {
            margin-top: 35px;
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

	<div class="main-container shadow-lg" style="padding: 70px 70px 110px 70px">

		<div class="sub-menu">
			<div style="display: flex; flex-direction: row; gap: 5px">
				<%--				<input type="text" style="width: 150px; padding: 0 5px">--%>
				<%--				<button class="btn btn-primary">검색</button>--%>
			</div>

			<div style="display: flex; flex-direction: row; gap: 5px">
				<button type="button" class="btn btn-warning" data-bs-toggle="modal"
						data-bs-target="#selectProductModal">상품 선택
				</button>
			</div>
		</div>

		<div class="content-container" style="flex: 1; justify-content: end">
			<div class="product-card-container">
				<div class="shadow-sm product-card-menu bg-secondary">
					<div>상품명</div>
					<div>카테고리</div>
					<div>재고</div>
				</div>

				<c:forEach var="product" items="${products}">
					<div class="shadow-sm product-card" onclick="selectProduct(${product.productId}, '${product.name}', ${product.remainQuantity}, '${product.category.label}')">
						<div>${product.name}</div>
						<div>${product.category.label}</div>
						<div>${product.remainQuantity}</div>
					</div>
				</c:forEach>
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
