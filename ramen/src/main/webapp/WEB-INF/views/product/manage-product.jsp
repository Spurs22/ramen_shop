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

		.content-container {

        }

        .sub-menu button {
            border-radius: 5px;
        }

        .sub-menu {
            width: 100%;
            height: 100px;
            display: flex;
            flex-direction: column;
            gap: 5px;
            justify-content: space-between;
            margin-bottom: 10px;
            padding: 0 20px;
        }

        textarea {
            height: 200px;
        }

        .product-card {
            width: 100%;
            height: 120px;
            display: grid;
            grid-template-columns: 20% 30% 20% 18% 12%;
            border-radius: 5px;
            text-align: center;
            padding: 0 5px;
            align-items: center;
            border: 1px solid #eaeaea;
            transition: 0.5s;
			align-content: center;
        }

        .product-card-menu {
            width: 100%;
            height: 50px;
            display: grid;
            grid-template-columns: 20% 30% 25% 10% 15%;
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
			<div style="display: flex; flex-direction: row; gap: 5px; justify-content: space-between; width: 100%">
				<button type="button" class="btn btn-outline-secondary" style="width: 90px" onclick="location.href='${pageContext.request.contextPath}/product/list'">뒤로가기</button>
				<input type="text" style="width: 150px; padding: 0 5px">
<%--								<button class="btn btn-primary">검색</button>--%>
			</div>

			<div class="shadow-sm product-card-menu bg-secondary">
				<div>상품 이미지</div>
				<div>상품명</div>
				<div>카테고리</div>
				<div>재고</div>
			</div>

		</div>

		<div class="content-container" style="flex: 1; ">
			<div class="product-card-container">
				<c:forEach var="product" items="${products}">
					<div class="shadow-sm product-card">
						<div style="height: 100%">
							<img src="${pageContext.request.contextPath}/resource/picture/${product.picture}" style="height: 100px; width: 100px; object-fit: cover; border: 1px solid lightgray; border-radius: 5px"/>
						</div>
						<div>${product.name}</div>
						<div>${product.category.label}</div>
						<div>${product.remainQuantity}</div>
						<div style="display: flex; flex-direction: column; gap: 5px; width: 100%; padding: 5px">
							<button class="btn btn-outline-secondary" onclick="location.href='${pageContext.request.contextPath}/product/edit-product?id=' + ${product.productId}">수정</button>
							<button class="btn btn-outline-danger">삭제</button>
						</div>
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
