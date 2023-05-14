<%@ page import="java.util.List" %>
<%@ page import="com.DTO.Product" %>
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
			grid-template-columns: 30% 20% 35% 15%;
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
            grid-template-columns: 30% 20% 35% 15%;
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
            height: 30px;
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
			<div style="display: flex; flex-direction: row; gap: 5px" >
				<%--				<input type="text" style="width: 150px; padding: 0 5px">--%>
				<%--				<button class="btn btn-primary">검색</button>--%>
			</div>

			<div style="display: flex; flex-direction: row; gap: 5px">
				<button type="button" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#selectProductModal">상품 선택</button>
			</div>

		</div>

		<div class="content-container" style="flex: 1; justify-content: end">



			<form method="post" enctype="multipart/form-data" class="main-content-card" action="">
				<div style="display: flex; flex-direction: row; gap: 10px; justify-content: space-between">
					<div class="input-group">
						<span class="input-group-text" style="width: 85px; "><span style="margin: auto">상품명</span></span>
						<textarea class="form-control product-info" aria-label="With textarea" name="content" id="productName" disabled ></textarea>
					</div>

					<div class="input-group">
						<span class="input-group-text" style="width: 85px;"><span style="margin: auto">카테고리</span></span>
						<textarea class="form-control product-info" aria-label="With textarea" name="content" id="category" disabled ></textarea>
					</div>

					<div class="input-group">
						<span class="input-group-text" style="width: 85px"><span style="margin: auto">재고</span></span>
						<textarea class="form-control product-info" aria-label="With textarea" name="content" id="quantity" disabled ></textarea>
					</div>
				</div>

				<div class="input-group">
					<input type="file" class="form-control" id="inputGroupFile04" aria-describedby="inputGroupFileAddon04"
						   aria-label="Upload" accept="image/jpeg,image/png,image/gif" name="picture">
				</div>

				<div class="input-group">
					<span class="input-group-text">상세 설명</span>
					<textarea class="form-control" aria-label="With textarea" name="content" ></textarea>
				</div>

				<input type="hidden" name="formType" value="">
				<input type="hidden" name="postId" value="">

							<div class="selected-product">

							</div>


				<div class="w-100 text-end" style="margin-top: 20px">
					<button type="submit" class="btn btn-primary">Submit</button>
				</div>
			</form>

		</div>
	</div>


	<!-- Modal -->
	<div class="modal fade" id="selectProductModal" tabindex="-1" aria-labelledby="selectProductLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="selectProductLabel">Modal title</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body" style="height: 400px; overflow: auto">
					<div class="product-card-container">
					 	<div class="shadow-sm product-card-menu bg-secondary">
							<div>상품명</div>
							<div>카테고리</div>
							<div>가격</div>
							<div>재고</div>
						</div>

						<c:forEach var="product" items="${products}">
							<div class="shadow-sm product-card" onclick="selectProduct(${product.productId}, '${product.name}', ${product.price}, ${product.remainQuantity}, ${product.category})">
								<div>${product.name}</div>
								<div>${product.category}</div>
								<div>${product.price}</div>
								<div>${product.remainQuantity}</div>
							</div>
						</c:forEach>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
				</div>
			</div>
		</div>
	</div>

</div>

<script>
	let categoryInput = document.getElementById('category');
    let quantityInput = document.getElementById('quantity');
    let productNameInput = document.getElementById('productName');

    var modal = new bootstrap.Modal(document.getElementById('selectProductModal'), {
        keyboard: false
    })

    function selectProduct(productId, name, price, remainQuantity, category) {
        // alert(productId + '' + name + '' + price + '' + remainQuantity + '' + category)
        categoryInput.value = category;
        productNameInput.value = name;
        quantityInput.value = remainQuantity;
		alert("상품이 선택되었습니다.")
        modal.hide();
    }
</script>

<script>
    $(document).ready(function () {
        selectMenu(menuIndex)
    })
</script>
</body>
</html>
