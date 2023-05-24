<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
            border-radius: 3px;
			border: 1px solid gray;
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

        .radio-btn:hover {
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
				<input type="radio" class="btn-check" name="category" id="btnradio1" autocomplete="off" value="1"
					   onclick="clickCategory(this)" checked>
				<label class="btn btn-outline-secondary radio-btn" for="btnradio1">봉지 라면</label>

				<input type="radio" class="btn-check" name="category" id="btnradio2" autocomplete="off" value="2"
					   onclick="clickCategory(this)">
				<label class="btn btn-outline-secondary radio-btn" for="btnradio2">컵 라면</label>

				<input type="radio" class="btn-check" name="category" id="btnradio3" autocomplete="off" value="3"
					   onclick="clickCategory(this)">
				<label class="btn btn-outline-secondary radio-btn" for="btnradio3">토핑</label>
			</div>

			<div style="display: flex; flex-direction: row; gap: 5px" >
				<input type="text" class="search-box" id="searchInput">
			</div>

			<c:if test="${sessionScope.member.userRoll == 1}">
				<div class="btn-group">
					<button type="button" class="btn btn-outline-secondary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
						상품 관리
					</button>
					<ul class="dropdown-menu">
						<li><a class="dropdown-item" href="${pageContext.request.contextPath}/product/add-form">상품 등록</a></li>
						<li><a class="dropdown-item" href="${pageContext.request.contextPath}/product/post-form">상품 상세페이지 등록</a></li>
						<li><hr class="dropdown-divider"></li>
						<li><a class="dropdown-item" href="${pageContext.request.contextPath}/product/manage-product">상품 관리</a></li>
					</ul>
				</div>
			</c:if>
		</div>

		<div class="product-container" id="resultForm"></div>
	</div>
</div>

<script>
    $(document).ready(function () {
        selectMenu(menuIndex)
		getList()
    })
</script>

<script>
    let category = document.getElementsByName('category');

    let selectedCategory = 1

    function clickCategory(button) {
        selectedCategory = button.value
		getList()
    }

    $(document).ready(function() {
        $("#searchInput").on("input", function() {
            getList()
        });
    });

    function getList() {
        let keyword = $('#searchInput').val();
        // let keyword = $(this).val();

        $.ajax({
            url: "${pageContext.request.contextPath}/product/search",
            type: "GET",
            dataType: "json",
            data: { keyword: keyword, category: selectedCategory },
            success: function(data) {
                // 조회된 회원 정보 출력
                console.log(data);
                // let posts = data;
                let resultForm = $('#resultForm');
                resultForm.empty();

                console.log(Array.isArray(data));

                // resultForm2.css('display','none')

                $.each(data, function(i, post) {
                    let userCardTemplate = `<a class="product-item shadow" href="${pageContext.request.contextPath}/product/post-board?id=` + post.productId + `">`

					if (post.picture == null) {
						userCardTemplate += `<img class="product-img" src="${pageContext.request.contextPath}/resource/picture/default2.png"/>`
					} else {
						userCardTemplate += `<img class="product-img" src="${pageContext.request.contextPath}/resource/picture/` + post.picture + `"/>`
					}

                    userCardTemplate += `<div style="margin-top: 5px; font-weight: 600">` + post.productName + `</div>
								<div style="color: #5d5d5d">` + post.price + `원</div>
								<div class="starBundle-comment">` +
									generateStars(post.rating)
								+ `</div>
							</a>
                        `;
                    resultForm.append(userCardTemplate);
                });

            },
            error: function(xhr, status, error) {
                console.error(error);
            }
        });
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
</body>
</html>
