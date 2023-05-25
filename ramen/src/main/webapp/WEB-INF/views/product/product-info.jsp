<%@ page import="com.DTO.ProductBoard" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
			/*background: rgba(241, 241, 241, 0.8);*/
			border: 1px solid gray;
			/*min-height: 800px;*/
			padding: 15px;
			margin-top: 15px;
		}

		.product-comment-container {
			margin-top: 10px;
			margin-bottom: 30px;
            display: flex;
            flex-direction: column;
			gap: 5px;
        }

		.product-content {
			height: 100%;
		}

		.comment {
			width: 100%;
			/*height: 70px;*/
			/*border-radius: 5px;*/
			display: flex;
			flex-direction: column;
			padding: 30px;
			border: 1px solid gray;
			gap: 10px;
        }

		.comment-name {
			font-weight: 650;
			font-size: 18px;
		}

		.comment-content {
			margin: 20px 0;
        }

		.comment-bundle {
			display: flex;
			flex-direction: row;
			gap: 15px;
			justify-content: space-between;
			align-items: end;
		}

		.comment-rating i {
			color: #F0974E;
		}

        .comment-img-container {
			display: flex;
			flex-direction: row;
			gap: 10px;
			overflow: auto;
			height: 100px;
        }
		.comment-img-container img {
			aspect-ratio: 1/1;
			border: 1px solid gray;
		}

        .starBundle-main {
            display: grid;
			font-size: 20px;
            width: 160px;
            grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
            justify-content: center;
            align-items: center;
            text-align: center;
            color: #F0974E;
			/*height: 30px;*/
        }

        .starBundle-comment {
			margin-top: 5px;
            display: grid;
            font-size: 17px;
            width: 140px;
            grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
            justify-content: left;
            align-items: start;
            text-align: start;
            color: #F0974E;
        }

        .flex-container {
			width: 100%;
			display: flex;
			flex-direction: row;
			justify-content: space-between;
			align-items: center;
			padding-left: 20px;
        }

        .like-full {
            /*color: rgba(255, 132, 144, 0);*/
            text-shadow: none;
            transition: 0.3s;
        }

        .like-full:hover {
            color: #CB444A;
            cursor: pointer;
        }

        .like {
            text-shadow: none;
            /*color: rgba(197, 197, 197, 0.67);*/
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
<%--				<input type="text" style="width: 150px; padding: 0 5px">--%>
<%--				<button class="btn btn-primary">검색</button>--%>
			</div>

			<div style="display: flex; flex-direction: row; gap: 5px">
				<c:if test="${sessionScope.member.userRoll == 1}">
					<button class="btn btn-outline-secondary" onclick="location.href='${pageContext.request.contextPath}/product/edit-board?id=${post.product.productId}'">글 수정</button>
					<button class="btn btn-secondary" onclick="deleteBtn()">글 삭제</button>
				</c:if>
			</div>
		</div>

		<div class="content-container">
			<div style="display: flex; flex-direction: row; gap: 50px">
				<div style="width: 50%; aspect-ratio: 1/1; padding: 30px; border: 1px solid black; border-radius: 3px">
					<img class="w-100 h-100 product-img" src="${pageContext.request.contextPath}/resource/picture/${post.product.picture == null ? "default2.png" : post.product.picture}" style="object-fit: scale-down">
				</div>

				<div style="display: flex; flex-direction: column; gap: 15px; align-items: end; flex: 1; ">
					<div class="product-createdDate">${post.createdDate}</div>
					<div class="" style="">${post.product.category.label}</div>
					<div class="flex-container">
						<div style="position: relative">
							<div style="position: absolute; right: -20px; text-align: center;">
								<i class="fa-solid fa-heart fa-2xl like" id="like"></i>
							</div>
							<div style="position: absolute; right: -20px; text-align: center;">
								<i class="fa-solid fa-heart fa-2xl like-full" id="likeBtn"></i>
							</div>
						</div>
						<div class="product-name">${post.product.name}</div>
					</div>
					<div class="product-price" style="width: 100%; text-align: right" >
						<span style="font-size: 25px; font-weight: 650;">${post.price}</span>
						<span>원</span>
					</div>

					<div class="starBundle-main">
						<c:set var="rating" value="${post.rating}"/>
<%--						<fmt:formatNumber value="${rating}" pattern="#.#" var="rating"/>--%>
						<c:set var="first" value="${fn:substringBefore(rating, '.')}"/>
						<c:set var="second" value="${fn:substringAfter(rating, '.')}"/>

						<!-- 3.4라면 1~3자리까지 꽉찬 별로 채움 -->
						<c:if test="${!first.equals('0')}">
							<c:forEach begin="1" end="${first}">
								<i class="fa-solid fa-star"></i>
							</c:forEach>
						</c:if>

						<c:if test="${!first.equals('5')}">
							<!-- 소숫점 숫자가 0이 아니라면 반별 -->
							<c:if test="${!second.equals('0')}">
								<i class="fa-solid fa-star-half-stroke"></i>
							</c:if>
							<!-- 0이라면 빈별 -->
							<c:if test="${second.equals('0')}">
								<i class="fa-regular fa-star"></i>
							</c:if>

							<!-- 5 - (앞자리+1) -->
							<c:if test="${!first.equals('4')}">
								<c:forEach begin="1" end="${4-first}">
									<i class="fa-regular fa-star"></i>
								</c:forEach>
							</c:if>
						</c:if>

					</div>


					<div style="display: flex; flex-direction: column; width: 100%; gap: 5px">
<%--						<div style="display: flex; flex-direction: row; gap: 5px">--%>
<%--							<button class="btn btn-success" style="flex: 1" data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">장바구니</button>--%>
<%--							<button class="btn btn-success" style="flex: 1">구매하기</button>--%>
<%--						</div>--%>

							<div style="border: 1px solid black; border-radius: 5px; padding: 10px; margin: 10px 0; gap: 15px; display: flex; flex-direction: column">
								<div style="display: flex; flex-direction: row; gap: 5px;  justify-content: right" id="quantityBundle">
									<button class="btn btn-dark btnChange minus" onclick="$.clickChangeBtn(this)">-</button>
									<input type="number" id="cartQuantityInput" style="width: 50px; text-align: center" value="1">
									<button class="btn btn-dark btnChange plus" onclick="$.clickChangeBtn(this)">+</button>
								</div>

								<div style="width: 100%; text-align: right" >
									<span>남은 수량 : </span>
									<span style="font-size: 16px;" id="remainQuantity"></span>
								</div>

								<div style="width: 100%; text-align: right" >
									<span style="font-size: 25px; font-weight: 650;" id="productPrice"></span>
									<span>원</span>
								</div>

								<div class="w-100" style="display: flex; gap: 5px">
									<button class="btn btn-outline-dark w-50" id="addCartBtn">장바구니</button>
									<button class="btn btn-outline-dark w-50" id="orderBtn">구매하기</button>
								</div>
							</div>
						<button class="btn btn-secondary w-100" onclick="location.href='${pageContext.request.contextPath}/recipe/contain-post?id=${post.product.productId}'">상품이 포함된 레시피 조회</button>
					</div>
				</div>
			</div>

			<div style="font-size: 20px; font-weight: 650; margin-top: 50px">
				상품 설명
			</div>
			<div class="product-content-container">
				<div class="product-content">
					<c:forEach var="img" items="${post.imgList}">
						<img src="${pageContext.request.contextPath}/resource/picture/${img}" style="width: 100%; object-fit: cover">
					</c:forEach>
					${post.content}
				</div>

			</div>

			<div style="font-size: 20px; font-weight: 650; margin-top: 30px">
				상품 리뷰
			</div>
			<div class="product-comment-container">

				<c:if test="${comments.size() == 0}">
					<div>등록된 댓글이 없습니다.</div>
				</c:if>

				<c:forEach var="comment" items="${comments}">
					<div class="comment">
						<div class="comment-bundle">
							<div class="comment-name">
								${comment.username}
							</div>

							<div class="comment-date">
								${comment.createdDate}
							</div>
						</div>

						<div class="starBundle-comment">
							<c:set var="rating" value="${comment.rating}"/>
							<c:set var="first" value="${fn:substringBefore(rating, '.')}"/>
							<c:set var="second" value="${fn:substringAfter(rating, '.')}"/>

							<!-- 3.4라면 1~3자리까지 꽉찬 별로 채움 -->
							<c:if test="${!first.equals('0')}">
								<c:forEach begin="1" end="${first}">
									<i class="fa-solid fa-star"></i>
								</c:forEach>
							</c:if>

							<c:if test="${!first.equals('5')}">
								<!-- 소숫점 숫자가 0이 아니라면 반별 -->
								<c:if test="${!second.equals('0')}">
									<i class="fa-solid fa-star-half-stroke"></i>
								</c:if>
								<!-- 0이라면 빈별 -->
								<c:if test="${second.equals('0')}">
									<i class="fa-regular fa-star"></i>
								</c:if>

								<!-- 5 - (앞자리+1) -->
								<c:if test="${!first.equals('4')}">
									<c:forEach begin="1" end="${4-first}">
										<i class="fa-regular fa-star"></i>
									</c:forEach>
								</c:if>
							</c:if>

						</div>

						<div class="comment-content">
							${comment.content}
						</div>

<%--						<div class="comment-img-container">--%>
<%--							<img src="${pageContext.request.contextPath}/resource/picture/1.png">--%>
<%--							<img src="${pageContext.request.contextPath}/resource/picture/1.png">--%>
<%--							<img src="${pageContext.request.contextPath}/resource/picture/1.png">--%>
<%--						</div>--%>
					</div>

				</c:forEach>
			</div>
		</div>
	</div>
</div>

<footer>
	<jsp:include page="/WEB-INF/views/fragment/footer.jsp"/>
</footer>

<script>
    $(document).ready(function () {
        selectMenu(menuIndex)
        //품절일 때
        if (${post.product.remainQuantity == 0}) {
            $('#productPrice').text('품절');
            console.log($('#productPrice').next().text(''))
            $('#quantityBundle').css('display', 'none');
            // $('#remainQuantity').parent().css('display', 'none')
            $('#remainQuantity').text(0)
			$('#addCartBtn').parent().css('display', 'none')

        } else {
            $('#productPrice').text(${post.price})
            $('#remainQuantity').text(${post.product.remainQuantity} - 1)
        }
    });

    function deleteBtn() {
        if (confirm('게시글을 삭제하시겠습니까?\n게시글과 관련된 모든 글(댓글, 좋아요, 등) 이 삭제됩니다.')) {
            location.href='${pageContext.request.contextPath}/product/delete-board?id=${post.product.productId}';
        }
    }
</script>

<script>
    let likeLine = document.getElementById('like')
    let likeCnt = document.getElementById('likeCount');
    const NONE_LIKE_COLOR = 'rgba(197, 197, 197, 0.67)';
    const LIKE_COLOR = '#CB444A';

    let likeStatus = ${likeStatus};
    let likeBtn = document.getElementById('likeBtn');
    let currentColor;

    $('#likeBtn').mouseout(function () {
        $('#likeBtn').css("color", currentColor)
    });

    function setBtnColor() {
        if (likeStatus) currentColor = LIKE_COLOR;
		else currentColor = NONE_LIKE_COLOR;
        likeBtn.style.color = currentColor;
	}

    $(document).ready(function () {
		setBtnColor()
    });


    function changeLikeStatus() {
        likeStatus = !likeStatus;
        setBtnColor()
        likeBtn.style.color = currentColor;
    }

    likeBtn.addEventListener('mouseover', function () {
        likeBtn.style.filter = 'brightness(80%)';
        likeBtn.style.color = LIKE_COLOR;
    })

    likeBtn.addEventListener('mouseout', function () {
		likeBtn.style.filter = 'brightness(100%)';
        likeBtn.style.color = currentColor
    })

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

    // 게시물 찜
    $(function () {
        $("#likeBtn").click(function () {
            if (${memberId == null}) {
                if (confirm("로그인이 필요한 서비스 입니다.\n로그인 페이지로 이동하시겠습니까?")) {
                    $(location).attr('href', '${pageContext.request.contextPath}/member/login.do')
                    return;
                } else {
                    return;
                }
            }

            let msg = likeStatus ? "상품을 찜목록에서 제거합니다." : "상품을 찜목록에 추가합니다.";

            if (!confirm(msg)) {
                return false;
            }

            let url = "${pageContext.request.contextPath}/product/like";
            let id = "${post.product.productId}";
            let qs = "id=" + id;

            const fn = function (data) {
                let state = data.state;
                if (state === true) {
                    console.log(likeStatus)
                    changeLikeStatus();
                }
            };

            ajaxFun(url, "post", qs, "json", fn);
        });


        $.checkQuantity = function (cartQuantityInput) {
            <%--let cartQuantityInput--%>
            <%--let remainQuantity = ${post.product.remainQuantity};--%>

            console.log(remainQuantity);
            return cartQuantityInput.val() <= remainQuantity;
        };

        $.clickChangeBtn = function (obj) {
            let min = 1;
            let cartQuantityInput = $('#cartQuantityInput');
            let remainQuantity = ${post.product.remainQuantity};
            let changeNum = cartQuantityInput.val();
            let price = ${post.price};

			if ($(obj).hasClass('plus')) {
				changeNum++;
			} else {
				changeNum--;
			}

            if (changeNum > remainQuantity) {
                alert('최대 수량보다 많이 담을 수 없습니다.')
            } else if (changeNum < min) {
                alert('1개보다 적게 담을 수 없습니다.')
            } else {
                cartQuantityInput.val(changeNum);
                $('#productPrice').text(changeNum * price)
                $('#remainQuantity').text(${post.product.remainQuantity} - changeNum)
            }
        };

		$("#addCartBtn").click(function () {
            if (${memberId == null}) {
                if (confirm("로그인이 필요한 서비스 입니다.\n로그인 페이지로 이동하시겠습니까?")) {
                    $(location).attr('href', '${pageContext.request.contextPath}/member/login.do')
                    return;
                } else {
                    return;
                }
            }

			let msg = "상품을 장바구니에 저장합니다."

			if (!confirm(msg)) {
				return false;
			}

			let url = "${pageContext.request.contextPath}/product/add-cart";
			let id = "${post.product.productId}";

			let quantity = $('#cartQuantityInput').val();
			let qs = "id=" + id + "&quantity=" + quantity;

			const fn = function (data) {
				let state = data.state;
				if (state === true) {
                    if (confirm("장바구니에 저장되었습니다.\n장바구니로 이동하시겠습니까?")) {
                        $(location).attr('href', '${pageContext.request.contextPath}/cart/list.do')
                    }
				}
			};

			ajaxFun(url, "post", qs, "json", fn);
		});

        $("#orderBtn").click(function () {
            if (${memberId == null}) {
                if (confirm("로그인이 필요한 서비스 입니다.\n로그인 페이지로 이동하시겠습니까?")) {
                    $(location).attr('href', '${pageContext.request.contextPath}/member/login.do')
                    return;
                } else {
                    return;
                }
            }

            let msg = "상품을 주문합니다."

            if (!confirm(msg)) {
                return false;
            }

            let url = "${pageContext.request.contextPath}/order/order-one.do";
            let productIds = []

            let id = "${post.product.productId}";
            productIds.push(id);
            let quantity = $('#cartQuantityInput').val();

            let qs = "quantity=" + quantity + "&productIds=" + productIds;

            let fullPath = url + '?' + qs

            $(location).attr('href', fullPath);
        });
    });
</script>
</body>
</html>
