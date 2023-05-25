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
            width: 70%;
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
		.cart-box > tr {
			display: flex;
			justify-content: center;
			align-items: center;
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
		.quantity-cell {
			display: flex;
			align-items: center;
			justify-content: center;
		}
	
		.quantity-btn {
			border: none;
			background-color: transparent;
			cursor: pointer;
			width: 30px;
			height: 30px;
		}
	
		.delete-btn {
			border: none;
			background-color: transparent;
			color: gray;
			cursor: pointer;
		}
	
		.delete-btn:hover {
			color: navy;
		}
	
		.fa-trash-can {
			font-size: 18px;
		}
	
		.fa-trash-can:hover {
			transform: scale(1.1);
		}
		
		
		.cart-box table {
		    margin: 0px auto;
		    
		}
		.cart-box tr {
		    height: 40px;
		}
		.hightlight { background: #f6f6f6; border-top: 2px solid black; border-bottom: 2px solid black; text-align: center;}
		.product-quantity{ border: none; width: 40px; text-align: center; outline: none;}
		
		
		.contenttb { width: 100%; padding: 20px; }
		.contenttxt { border: 1px solid #DFE2E6; outline: none; width: 100%; height: 200px; resize: none; padding: 10px; border-radius: 5px; background: #f6f6f6;}
		.subjecttxt { border: 1px solid #DFE2E6; outline: none; width: 100%; height: 40px; padding: 10px; border-radius: 5px; background: #f6f6f6;}
		
		.quantity-btn { border: 1px solid gray; border-radius: 3px;}
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
					<label class="btn btn-outline-primary" for="btnradio1">봉지 라면</label>
	
					<input type="radio" class="btn-check" name="category" id="btnradio2" autocomplete="off" value="2"
						   onclick="clickCategory(this)">
					<label class="btn btn-outline-primary" for="btnradio2">컵 라면</label>
	
					<input type="radio" class="btn-check" name="category" id="btnradio3" autocomplete="off" value="3"
						   onclick="clickCategory(this)">
					<label class="btn btn-outline-primary" for="btnradio3">토핑</label>
				</div>
	
				<div style="display: flex; flex-direction: row; gap: 5px" >
					<input type="text" class="search-box" id="searchInput">
					<button class="btn btn-primary" id="searchButton">검색</button>
				</div>
	
				<div style="display: flex; flex-direction: row; gap: 5px">
					<button type="button" class="btn btn-success btnwrite1toggle" onclick="">다음</button>
				</div>
			</div>
	
			<div class="product-container" id="resultForm">
				<c:forEach var="post" items="${posts}">
					<a class="product-item" onclick="addToCart('${post.product.productId}', '${post.price}', '${post.product.name}')">
						<img class="product-img" src="${pageContext.request.contextPath}/resource/picture/${post.product.picture == null ? "default2.png" : post.product.picture}">
						<div style="margin-top: 5px; font-weight: 750; font-size: 13px;">${post.product.name}</div>
						<div style="color: #5d5d5d; font-size: 11px;">${post.price}원</div>
					</a>
				</c:forEach>
			</div>
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
							<button type="button" class="btn btn-success btnwrite2toggle">이전</button>
						</div>
					</td>
					<td style="text-align: right;">
						<div style="display: flex; flex-direction: row; gap: 5px; justify-content: flex-end;">
							<button class="btn btn-primary" type="button" onclick="sendOk();">${mode =='update' ? '수정' : '등록' }</button>
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
			<table>
				<tr class='hightlight'>
					<td style="width:200px;">상품명</td>
					<td style="width:100px;">수량</td>
					<td style="width:100px;">삭제</td>
				</tr>
				<tbody class="select-product"></tbody>
			</table>
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
                    let userCardTemplate = `
                    		<a class="product-item" onclick="addToCart(`+post.productId+`, `+post.price+`, '`+post.productName+`')">
								<img class="product-img" src="${pageContext.request.contextPath}/resource/picture/${post.product.picture == null ? 'default2.png' : post.product.picture}">
								<div style="margin-top: 5px; font-weight: 750; font-size: 13px;">` + post.productName + `</div>
								<div style="color: #5d5d5d; font-size: 11px;">` + post.price + `원</div>
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
    
    function addToCart(productId, price, name) {
    	// 이미 추가된 상품인지 체크
    	var isAlreadyAdded = addedProducts.some(function(product) {
			return product.productId === productId;
		});
		
		if (isAlreadyAdded) {
			alert('이미 추가된 상품입니다.');
			return;
		}
    	
    	let out = "";
    	
    	out += "<tr style='border-bottom: 1px solid gray;' data-product-id='" + productId + "'>";
		out += "<td style='width: 200px; padding-left: 16px;'>"+ name +"</td>"	
		out += "<td style='width: 100px;' class='quantity-cell'>";
		out += "<button type='button' class='quantity-btn minus'>&lt;</button>";
		out += "<span class='quantity-value'><input type='number' name='product-quantity' class='product-quantity' value='1' readonly='readonly'></span>";
		out += "<button type='button' class='quantity-btn plus'>&gt;</button>";
		out += "<input type='hidden' class='product-id' name='productId' value='" + productId + "'>";
		out += "</td>";
		out += "<td style='text-align: center; width:100px'>";
		out += "<button type='button' class='delete-btn'><i class='fa-solid fa-trash-can'></i></button>";		
		out += "</td>";
		out += "</tr>";
		
		$(".select-product").append(out);
		
		var product = {
		    productId: productId,
		    quantity: $('.product-quantity').val()
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
		$(this).closest("tr").remove();
		
		const productId = $(this).closest("tr").find(".productId").val();
		const index = addedProducts.findIndex(product => product.id === productId);
		
		if (index !== -1) {
			addedProducts.splice(index, 1);
		}
	});
    
    $(document).on("click", ".minus", function() {
    	let tr = $(this).closest("tr");
        let quantityInput = tr.find('.product-quantity');
        let value = parseInt(quantityInput.val());
    	
        if (value > 1) {
            value--;
            quantityInput.val(value);
            
            let productId = tr.data('productId'); // tr 요소에서 productId 값을 가져옴
            updateProductQuantity(productId.toString(), value.toString());
        }
        
        console.log(addedProducts);
        makeString();
	});
    
    
    // 수량 조절
    $(document).on("click", ".plus", function() {
    	let tr = $(this).closest("tr");
        let quantityInput = tr.find('.product-quantity');
        let value = parseInt(quantityInput.val());
        
        if (value < 10) {
            value++;
            quantityInput.val(value);
            
            let productId = tr.data('productId'); // tr 요소에서 productId 값을 가져옴
            updateProductQuantity(productId.toString(), value.toString());
        }
        
        console.log(addedProducts);
        makeString();
	});
    
    function updateProductQuantity(productId, quantity) {
        // addedProducts 배열에서 productId에 해당하는 객체를 찾음
        let product = addedProducts.find(item => item.productId === productId);
        
        if (product) {
            product.quantity = quantity; // 수량을 변경
        } else {
            // productId에 해당하는 객체가 없는 경우 새로운 객체를 addedProducts 배열에 추가
            addedProducts.push({ productId, quantity });
        }
    }
    
</script>
</body>
</html>
