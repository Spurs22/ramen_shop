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
			min-height: 800px;
			padding: 15px;
			margin-top: 50px;
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
				<button class="btn btn-primary" onclick="location.href='${pageContext.request.contextPath}/product/edit?id=${post.product.productId}'">상품 수정</button>
				<button class="btn btn-danger">상품 삭제</button>
			</div>
		</div>

		<div class="content-container">
			<div style="display: flex; flex-direction: row; gap: 50px">
				<div style="width: 50%; background: #c5c5c5; aspect-ratio: 1/1">
					<img class="w-100 h-100" src="${pageContext.request.contextPath}/resource/picture/1.png">
				</div>

				<div style="display: flex; flex-direction: column; gap: 15px; align-items: end; flex: 1; justify-content: space-between">
					<div class="product-createdDate">${post.createdDate}</div>
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
					<div class="product-price">${post.price}<span>원</span></div>

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
						<div style="display: flex; flex-direction: row; gap: 5px">
							<button class="btn btn-success" style="flex: 1">장바구니</button>
							<button class="btn btn-success" style="flex: 1">구매하기</button>
						</div>
						<button class="btn btn-secondary w-100">상품이 포함된 레시피 조회</button>
					</div>
				</div>
			</div>

			<div class="product-content-container">
				<div class="product-content">${post.content}</div>
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

						<div class="comment-img-container">
							<img src="${pageContext.request.contextPath}/resource/picture/1.png">
							<img src="${pageContext.request.contextPath}/resource/picture/1.png">
							<img src="${pageContext.request.contextPath}/resource/picture/1.png">
						</div>
					</div>

				</c:forEach>
			</div>
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

    // 게시물 좋아요
    $(function () {
        $("#likeBtn").click(function () {
            if (${memberId == null}) {
                alert("로그인 후 이용해주세요.")
				return
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
    });

</script>

</body>
</html>
