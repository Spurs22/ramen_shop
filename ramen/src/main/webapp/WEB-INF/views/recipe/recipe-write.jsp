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
        .product-container {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            grid-auto-rows: 170px;
            padding: 20px;
            height: 50%;
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
					<button class="btn btn-success btnwrite1toggle" onclick="">다음</button>
				</div>
			</div>
	
			<div class="product-container" id="resultForm">
				<c:forEach var="post" items="${posts}">
					<a class="product-item" href="${pageContext.request.contextPath}/recipe/write.do?id=${post.product.productId}">
						<img class="product-img" src="${pageContext.request.contextPath}/resource/picture/1.png">
						<div style="margin-top: 5px; font-weight: 750; font-size: 13px;">${post.product.name}</div>
						<div style="color: #5d5d5d; font-size: 11px;">${post.price}원</div>
					</a>
				</c:forEach>
			</div>
			<hr>
			<div class="cart-box">
				<table>
					<tr>
						<td>봉지라면</td>
						<td>신라면</td>
						<td><button type="button">-</button> 2 <button type="button"> + </button></td>
						<td><button type="button"><i class="fa-solid fa-trash-can"></i></button>
					</tr>
				</table>
			</div>
			
		</div>
		
		<!-- 구분 -->
		
		<div class="write-step2" style="display: none;">
			<table>
				<tr>
					<td>
						<div style="display: flex; flex-direction: row; gap: 5px">
							<button class="btn btn-success btnwrite2toggle">이전</button>
						</div>
					</td>
					<td>
						<div style="display: flex; flex-direction: row; gap: 5px">
							<button class="btn btn-primary" type="button" onclick="sendOk();">${mode =='update' ? '수정' : '등록' }</button>
							<c:if test="${mode == 'update' }">
								<input type="hidden" name="id" value="">
							</c:if>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="2">제목</td>
				</tr>
				<tr>
					<td colspan="2"><input type="text" name="subject" value=""></td>
				</tr>
				<tr>
					<td colspan="2">내용</td>
				</tr>
				<tr>
					<td colspan="2"><textarea name="content" id="content"></textarea></td>
				</tr>
			</table>
			<table>
				<tr>
					<td>봉지라면</td>
					<td>신라면</td>
					<td>2</td>
				</tr>
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
                    let userCardTemplate = `
                            <a class="product-item" href="${pageContext.request.contextPath}/recipe/write.do?id=` + post.productId + `">
								<img class="product-img" src="${pageContext.request.contextPath}/resource/picture/1.png">
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
    
    
</script>
</body>
</html>
