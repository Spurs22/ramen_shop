<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

		.main-container {
            padding-bottom: 40px;
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
            display: flex;
            flex-direction: column;
            gap: 35px;
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
        }

		.product-name-preview {
            font-weight: 600; font-size: 20px
        }

        input[type="number"]::-webkit-outer-spin-button,
        input[type="number"]::-webkit-inner-spin-button {
            -webkit-appearance: none;
            margin: 0;
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
				<button type="button" class="btn btn-outline-secondary" style="width: 90px" onclick="location.href='${pageContext.request.contextPath}/product/list'">뒤로가기</button>
			</div>
		</div>

		<div class="content-container" style="flex: 1;">
			<form method="post" class="main-content-card" enctype="multipart/form-data" action="${pageContext.request.contextPath}/product/${mode}-product" id="form">
				<div style="display: flex; flex-direction: row; gap: 10px; justify-content: space-between;">
					<div class="input-group">
						<div class="input-group-text" style="width: 85px;"><span style="margin: auto">상품명</span></div>
						<input class="form-control product-info" name="name" id="productName" value="${mode == "post" ? "" : product.name}">
						<button class="btn btn-outline-secondary" type="button" id="productNameCheckBtn">중복 검사</button>
					</div>
				</div>
				<input type="hidden" name="id" value="${mode.equals("post") ? "" : product.productId}">

				<div style="display: flex; flex-direction: row; gap: 10px; justify-content: space-between;">
					<div class="input-group">
						<div class="input-group-text" style="width: 85px;"><span style="margin: auto">카테고리</span></div>
						<select class="form-select form-control product-info" name="category" id="category">
							<option selected value="0">카테고리 선택</option>
							<option value="1">봉지라면</option>
							<option value="2">컵라면</option>
							<option value="3">토핑</option>
						</select>
					</div>

					<div class="input-group">
						<div class="input-group-text" style="width: 85px;"><span style="margin: auto">재고</span></div>
						<input type="number" class="form-control product-info" id="quantityInput" name="quantity" value="${mode == "post" ? "" : product.remainQuantity}">
					</div>
				</div>

				<div class="input-group" style="flex: 1">
					<input type="file" class="form-control" aria-label="Upload" accept="image/jpeg,image/png,image/gif" name="picture" id="productImgInput">
				</div>

				<div style="width: 100%; border-bottom: 1px solid #DFE2E6"></div>

				<div style="height: 210px; display: flex; flex-direction: row; gap: 40px;">
					<img src="${pageContext.request.contextPath}/resource/picture/${mode == "post" ? "default2.png" : product.picture}"
						 style="height: 100%; width: 210px; object-fit: cover; border: 1px solid #DFE2E6"
						 id="productImg">
					<div style="display: grid; height: 100%; grid-template-columns: 70px 25px 150px; grid-auto-rows: 50px; align-items: center; align-content: center">
						<div class="product-name-preview">상품명</div><span class="product-name-preview">:</span><span class="product-name-preview" id="name-preview"></span>
						<div style="font-size: 16px">카테고리</div><span>:</span><span id="category-preview"></span>
						<div style="font-size: 16px">재고</div><span>:</span><span id="quantity-preview"></span>
					</div>
				</div>

				<div style="text-align: right">
					<button type="button" class="btn btn-success" style="width: 100px; margin-top: 20px" id="submitButton">${mode.equals('post') ? '등록 완료' : '수정 완료'}</button>
				</div>
			</form>
		</div>
	</div>
</div>

<footer>
	<jsp:include page="/WEB-INF/views/fragment/footer.jsp"/>
</footer>

<script>
    let submitBtn = document.getElementById('submitButton');
    let form = document.getElementById('form');
    let productNameInput = document.getElementById('productName');
    let productIdInput = document.getElementById('productId');
    let productImgInput = document.getElementById('productImgInput')
    let productImg = document.getElementById('productImg');

    let categoryInput = $('#category');
    let quantityInput = $('#quantityInput')

    let productId = null;
    let nameCheckStatus = false;

    // 유효성 검사


    $(function () {
        if (${mode.equals('edit')}) {
            let categoryName = '${product.category.label}';
            console.log(categoryName)

            categoryInput.find('[value=${product.category.value}]').attr('selected', true)
            nameCheckStatus = true;
            let $productName = $('#productName');
            $productName.attr('readOnly', true)
			$productName.css('background-color', 'F8F9FA')

			$('#name-preview').text($productName.val())
            $('#quantity-preview').text('${product.remainQuantity}')
            $('#category-preview').text(categoryName)
        }
    });


    // 유효성 검사

    productImgInput.addEventListener('change', function () {
        const reader = new FileReader();
        reader.onload = ({ target }) => {
            productImg.src = target.result;
        };
        reader.readAsDataURL(productImgInput.files[0]);
    });


    // 폼 전송 함수
    submitBtn.addEventListener('click', function () {

		if (!nameCheckStatus) {
			alert('아이디 중복 검사를 먼저 해주세요.')
			return
		}

		if (Number(categoryInput.val()) === 0) {
			alert('카테고리를 선택해주세요.')
			categoryInput.focus()
			return
		}

		if (!isDigitNumber(quantityInput.val())) {
			alert('재고를 입력해 주세요.\n한자리 이상의 정수만 입력 가능합니다.')
			quantityInput.focus()
			return
		}

		if (${mode.equals('post')}) {
			if (!confirm("등록 하시겠습니까?")) return;
		} else {
			if (!confirm("수정 하시겠습니까?")) return;
		}

        form.submit();
    });

    $(function () {
		// 카테고리 미리보기
        categoryInput.change(function () {
            let $categoryPreview = $('#category-preview');

            if (Number(categoryInput.val()) === 0) {
                $categoryPreview.text('')
			} else {
                $categoryPreview.text(categoryInput.find('[value=' + categoryInput.val() + ']').text())
            }
        });

        $("#productNameCheckBtn").click(function () {
            let url = "${pageContext.request.contextPath}/product/valid-product-name}";
            let name = $('#productName')

			// 상품명이 빈 문자열일때 리턴
            if (name.val().trim() === '') {
                alert('상품명을 입력해 주세요.')
				name.focus()
				return
            }

            let qs = "name=" + name.val();

            const fn = function (data) {
                let state = data.state;
                if (state === true) {
                    name.attr('readOnly', true);
                    name.css('background-color', '#F8F9FA')

                    nameCheckStatus = true
					$('#name-preview').text(name.val())
                    alert('사용 가능한 상품명입니다.')
                } else {
                    alert('이미 존재하는 상품명입니다.')
                }
            };
            ajaxFun(url, "post", qs, "json", fn);
        });

        quantityInput.change(function () {
            $('#quantity-preview').text(quantityInput.val());
        });

        function ajaxFun(url, method, query, dataType, fn) {
            $.ajax({
                type: method,
                url: url,
                data: query,
                dataType: dataType,
                success: function (data) {
                    fn(data);
                },
                beforeSend: function (jqXHR) {
                    jqXHR.setRequestHeader("AJAX", true); // 사용자 정의 헤더
                },
                error: function (jqXHR) {
                    if (jqXHR.status === 403) {
                        login();
                        return false;
                    } else if (jqXHR.status === 400) {
                        alert("요청 처리가 실패 했습니다.");
                        return false;
                    }
                }
            });
        }
    });
</script>

<script>
    $(function () {
        selectMenu(menuIndex)
    })
</script>
</body>
</html>
