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
	<title>Recipe</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">

	<style>
        .product-container {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            grid-auto-rows: 170px;
            padding: 20px;
            height: 300px;
			gap: 3px;            
			overflow: auto;
			justify-content: center;
        }

        .product-item {
            width: 100%;
            height: 100%;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            padding: 15px;
            transition: 0.5s;
            background: #ffffff;
            border: 0.3px solid gray;
        }

        .product-img {
            width: 100%;
            height: 100px;
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
		.table-container {
			width: 100%;
		}
	
		.quantity-btn {
			border: none;
			background-color: transparent;
			cursor: pointer;
			width: 30px;
			height: 30px;
		}
	
		.product-quantity {
            border: none;
            outline: none;
			text-align: center;
			width: 50px;
        }

		
		.contenttb { width: 100%; padding: 20px; }
		.contenttxt { border: 1px solid #DFE2E6; outline: none; width: 100%; height: 200px; resize: none; padding: 10px; border-radius: 5px; background: #ffffff;}
		.subjecttxt { border: 1px solid #DFE2E6; outline: none; width: 100%; height: 40px; padding: 10px; border-radius: 5px; background: #ffffff;}
		
		.quantity-btn { border: 1px solid lightgray; border-radius: 3px;}
		
		.cart-box {
			
		}
		.select-product {
			display: grid;
            grid-template-columns: repeat(3, 1fr);
            grid-auto-rows: 250px;
            padding: 30px;
            min-height: 100px;
			gap: 10px;            
			justify-content: center;
			align-content: center;
		}
		
		.cart-product {
			position: relative;
			width: 100%;
            height: 100%;
            border-radius: 10px;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            align-items: center;
			align-content: center;
            padding: 20px 30px;
            transition: 0.5s;
            background: #ffffff;
            border: 1px solid #f8f8ff;
		}
		
		.delete-btn {
			position: absolute;
			top: 10px;
			right: 10px;
			border: none;
			background-color: transparent;
			color: gray;
			right: 0;
			margin-right: 5px;
		}
		
		.delete-p {
			width: 100%;
		}
	
		.fa-xmark {
			font-size: 18px;
		}
	
		.fa-xmark {
			transform: scale(1.1);
			color: navy;
			cursor: pointer;
		}
		.cart-name {
			font-weight: 600;
			margin-bottom: 5px;
		}
		
		.cart-quantity {
			display: block;
			margin-bottom: 5px;
		}
		
		.quantity-btn:hover {
			background: #f8f8ff;
		}
		
		.fa-minus {
			color: #6f798B;
		}
		.fa-plus {
			color: #6f798B;
		}
	</style>
</head>
<script>
    let menuIndex = 3
</script>

<body>
<div class="whole-container">

	<header>
		<jsp:include page="/WEB-INF/views/fragment/menubar.jsp"/>
	</header>

	<div class="main-container shadow-lg">
		<div class="write-step1">	
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
	
				<div style="display: flex; flex-direction: row; gap: 5px">
					<button type="button" class="btn btn-warning btnwrite1toggle" onclick="">다음</button>
				</div>
			</div>
	
			<div class="product-container" id="resultForm"></div>
			<hr>
			
			
		</div>
		
		<%-- 구분 --%>
		
		<div class="write-step2" style="display: none;">
		<!-- <form name="recipeForm" method="post" enctype="multipart/form-data"> -->
		<form name="recipeForm" method="post">
			<table class="contenttb">
				<tr>
					<td>
						<div style="display: flex; flex-direction: row; gap: 5px">
							<button type="button" class="btn btn-warning btnwrite2toggle">이전</button>
						</div>
					</td>
					<td style="text-align: right;">
						<div style="display: flex; flex-direction: row; gap: 5px; justify-content: flex-end;">
							<button class="btn btn-secondary" type="button" onclick="sendOk();">${mode =='update' ? '수정' : '등록' }</button>
							<c:if test="${mode == 'update'}">
								<input type="hidden" name="recipeId" value="${recipeId}">
							</c:if>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="2" style="padding: 15px; font-size: 20px; font-weight: bold;">제목</td>
				</tr>
				<tr>
					<td colspan="2"><input class="subjecttxt" type="text" name="subject" value="${dto.subject}"></td>
				</tr>
				<tr>
					<td colspan="2" style="padding: 15px; font-size: 20px; font-weight: bold;">내용</td>
				</tr>
				<tr>
					<td colspan="2"><textarea class="contenttxt" name="content" id="content">${dto.content}</textarea></td>
				</tr>
				<tr>
					<td colspan="2" style="padding: 15px; font-size: 20px; font-weight: bold;">사진</td>
				</tr>
				<tr>
					<td colspan="2"><input type="file" class="form-control" aria-label="Upload" accept="image/jpeg,image/png,image/gif" name="picture" id="productImgInput"></td>
				</tr>
			</table>
			
		</form>
		</div>
		
		<div class="cart-box">
			<div class="select-product"></div>
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
        selectedCategory = button.value;
		getList();
    }

    $(document).ready(function() {
        $("#searchInput").on("input", function() {
            getList();
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
                    let userCardTemplate = `<a class="product-item shadow" onclick="addToCart('`+post.productId+`', `+post.price+`, '`+post.productName+`','`+post.picture+`')">`

					if (post.picture == null) {
						userCardTemplate += `<img class="product-img" src="${pageContext.request.contextPath}/resource/picture/default2.png"/>`
					} else {
						userCardTemplate += `<img class="product-img" src="${pageContext.request.contextPath}/resource/picture/` + post.picture + `"/>`
					}

                    userCardTemplate += `<div style="margin-top: 5px; font-weight: 600">` + post.productName + `</div>
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
    
    $(function() {
        $(".btnwrite1toggle").on("click", function() {
			if(addedProducts.length < 2) {
				alert('2가지 이상의 상품을 조합해주세요.');
				return;
			}
        	$(".write-step1").hide();
          	$(".write-step2").show();
        });

        $(".btnwrite2toggle").on("click", function() {
          	$(".write-step2").hide();
          	$(".write-step1").show();
        });
      });
    
    function sendOk() {
		const f = document.recipeForm;
		let str;
		
		if(productIds == null || productIds.size < 1) {
			alert('상품을 등록해주세요.');
			return;
		}
		
		if(addedProducts.length < 2) {
			alert('2가지 이상의 상품을 조합해주세요.');
			return;
		}
		
		str = f.subject.value.trim();
		if(!str) {
			alert("제목을 입력하세요.");
			f.subject.focus();
			return;
		}
		
		str = f.content.value.trim();
		if(!str) {
			alert("내용을 입력하세요.");
			f.content.focus();
			return;
		}
		
		let formData = new FormData(f);
        formData.append("productIds", productIds);
        formData.append("quantities",quantities);
		
		$.ajax({
	        url: "${pageContext.request.contextPath}/recipe/${mode}_ok.do",
	        type: "POST",
	        dataType: "json",
	        processData: false,
	        contentType: false,
	        data: formData,
	        <!-- data: { subject: f.subject.value, content: f.content.value, productIds: productIds, quantities: quantities, recipeId: f.id.value }, -->
	        success: function(data) {
	        	let state = data.state;
	        	if(state == "true") {
		        	window.location.href = "${pageContext.request.contextPath}/recipe/recipe-list.do";
	        	}
	        },
	        error: function(xhr, status, error) {
	            console.error(error);
	        }
	    });
	    
		f.submit();
	}
    
    let addedProducts = [];
    
    function addToCart(productId, price, name, picture) {
    	// 이미 추가된 상품인지 체크
    	var isAlreadyAdded = addedProducts.some(function(product) {
			return product.productId === productId;
		});
		
		if (isAlreadyAdded) {
			alert('이미 추가된 상품입니다.');
			return;
		}
    	
    	let out = "";
		// 체크 포인트
		out += "<div class='cart-product shadow xm'>";
			out += "<p class='delete-p'><button type='button' class='delete-btn'><i class='fa-solid fa-xmark'></i></button></p>";
			out += "<img class='product-img' src='${pageContext.request.contextPath}/resource/picture/${"+picture+" == null ? 'default2.png' : "+picture+"}'>";
			out += "<p class='cart-name'>" + name + "</p>";
			out += "<div style='display: flex; flex-direction: row; justify-content: space-between; width: 100%'>"
				out += "<button type='button' class='quantity-btn minus' data-product-id='" + productId + "'><i class='fa-solid fa-minus'></i></button>";
				out += "<input name='product-quantity' class='product-quantity' value='1' readonly='readonly'>";
        		// out += "<input name='product-quantity' value='1' readonly='readonly' style='text-align: center; width: 30px'>";
				out += "<button type='button' class='quantity-btn plus' data-product-id='" + productId + "'><i class='fa-solid fa-plus'></i></button>";
			out += "</div>"
			out += "<input type='hidden' class='product-id' name='productId' value='" + productId + "'>";
	    out += "</div>";

	    $(".select-product").append(out);
		
		var product = {
	        productId: productId,
	        quantity: 1,
	        picture : picture
	    };
	
		addedProducts.push(product);
		
		makeString();
		
		console.log(addedProducts);
    }
    
    
    // 레시피 문자열로 넘겨주기
    var productIds
    var quantities
    
    function makeString() {
    	productIds = addedProducts.map(function(product) {
	        return product.productId;
	    }).join(',');

	    quantities = addedProducts.map(function(product) {
	        return product.quantity;
	    }).join(',');
	    
	    console.log(productIds);
	}

    // 레시피 상품 삭제
    $(document).on("click", ".delete-btn", function() {
		$(this).closest("div").remove();
		
		const productId = $(this).closest("div").find(".productId").val();
		const index = addedProducts.findIndex(product => product.id === productId);
		
		if (index !== -1) {
			addedProducts.splice(index, 1);
		}
	});
    
    $(document).on("click", ".minus", function() {
        let div = $(this).closest("div");
        let quantityInput = div.find('.product-quantity');
        let value = parseInt(quantityInput.val());

        if (value > 1) {
            value--;
            quantityInput.val(value);

            let productId = div.find('.quantity-btn').data('product-id');
            updateProductQuantity(productId.toString(), value.toString());
        }

        console.log(addedProducts);
        makeString();
    });

    $(document).on("click", ".plus", function() {
        let div = $(this).closest("div");
        let quantityInput = div.find('.product-quantity');
        let value = parseInt(quantityInput.val());

        if (value < 10) {
            value++;
            quantityInput.val(value);

            let productId = div.find('.quantity-btn').data('product-id');
            updateProductQuantity(productId.toString(), value.toString());
        }

        console.log(addedProducts);
        makeString();
    });

    function updateProductQuantity(productId, quantity) {
        let product = addedProducts.find(item => item.productId === productId);

        if (product) {
            product.quantity = quantity;
        } else {
            addedProducts.push({ productId, quantity });
        }
    }
    
</script>
<footer>
	<jsp:include page="/WEB-INF/views/fragment/footer.jsp"/>
</footer>
</body>
</html>
