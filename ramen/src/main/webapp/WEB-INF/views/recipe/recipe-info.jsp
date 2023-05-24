<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String productIds = request.getParameter("productIds");
	String quantities = request.getParameter("quantities");
%>
<html>
<head>
	<title>${dto.subject}</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">

	<style>
        tr > td {
            text-align: center;
        }

        tr > th {
            background-color: #DFE2E6;
        }

        .content-table > tr {
            padding: 15px;
        }

        .right {
            text-align: right;
        }

        .content-text {
            width: 100%;
            min-height: 200px;
            padding: 20px;
            resize: none;
            margin-top: 20px;
            margin-bottom: 10px;
            /*border: none;*/
        }

        .content-text:focus {
            outline: none;
        }

        .content-reply {
            width: 100%;
            height: 70px;
            padding: 10px;
            resize: none;
            border-radius: 5px;
			margin-bottom: 15px;
        }

        .content-reply {
            outline: none;
        }

        .pnbtn {
            color: black;
            text-decoration: none;
        }

        .rightbtn {
            color: black;
            text-decoration: none;
            display: flex;
            justify-content: center;
            align-items: center;

        }

        .pnbtn:hover {
            cursor: pointer;
        }

        .pnbtn:active {
            color: #777;
        }

        .btn {
            border: 1px solid black;
        }

        .btn:hover {
            border: 1px solid black;
        }

        .btnSendRecipeLike:hover > i {
            color: #dc6c6a;
        }

        .recipe-board-header {
            background: #efefef;
            display: flex;
            flex-direction: row;
            justify-content: space-between;
			align-items: center;
			padding: 10px 20px;
			border-radius: 5px;
        }
		.recipe-subject {
			margin-top: 20px;
			width: 100%;
			text-align: center;
			font-size: 22px;
			font-weight: 500;
        }

        .recipe-img {
			width: 100%;
			height: 600px;
			object-fit: cover;
			border-radius: 10px;
        }
        
        .product-container {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            grid-auto-rows: 200px;
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
            justify-content: space-between;
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
            border-radius: 10px;
            border: 1px solid gray;
        }

        .product-item:hover {
            cursor: pointer;
            background: #eeeeee;
        }

        .cartbtn {
			width: 100%;
        }

		.cartbtn:hover {
            background: #eeeeee;
		}

        .recipe-label {
            width: 100%;

            padding: 20px 20px 0 20px;
            font-weight: 500;
            font-size: 18px;

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
		<div class="content-container">
			<div class="" style="width: 100%; display: flex; flex-direction: column; gap: 10px">
				<div class="recipe-board-header">
					<div>작성자 : ${dto.nickname}</div>


					<div style="display: flex; flex-direction: row; gap: 10px; justify-content: space-between; align-items: center">
						<div>${dto.createdDate}</div>
						<div><i class="fa-solid fa-eye"></i> ${dto.hitCount}</div>
						<c:choose>
							<c:when test="${sessionScope.member!=null}">
								<button type="button" class="btn btnSendRecipeLike" title="좋아요" style="border: none; padding-left: 0"><i
										class="fa-solid fa-heart" id="likeBtn"></i> <span
										id="recipeLikeCount">${dto.recipeLikeCount}</span></button>
							</c:when>
							<c:otherwise>
								<button type="button" class="btn btnSendRecipeLike" title="좋아요" style="border: none; padding-left: 0"
										disabled="disabled"><i
										class="fa-solid fa-heart" id="likeBtn"></i> <span
										id="recipeLikeCount">${dto.recipeLikeCount}</span></button>
							</c:otherwise>
						</c:choose>
					</div>
				</div>

				<div class="recipe-subject">${dto.subject}</div>

				<hr>
					<img class="recipe-img" src="${pageContext.request.contextPath}/resource/picture/${dto.picture == null ? 'default2.png' : dto.picture}">
				<hr>

				<div class="recipe-label" style="padding-bottom: 0">조합 상품</div>
				<div class="product-container">
					<c:forEach var="recipe" items="${list}">
						<div class="product-item" style="position:relative;">
							<img class="product-img" src="${pageContext.request.contextPath}/resource/picture/${recipe.picture == null ? "default2.png" : recipe.picture}">
							${recipe.name}
							<div style="background: #f5cf73; border-radius: 20px; position: absolute; right: -8px; top: -8px; width: 50px; text-align: center; padding: 5px 8px; border: 1px solid black; font-weight: 550">x ${recipe.quantity}</div>
						</div>

					</c:forEach>
				</div>

				<div style="" class="recipe-label">
					<c:choose>
						<c:when test="${empty list}">
							<%--								<br>장바구니에 담기 <button type="button" class="btn cartbtn" disabled="disabled"><i class="fa-solid fa-cart-arrow-down"></i></button>--%>
						</c:when>
						<c:when test="${empty sessionScope.member}">
							<%--								<br>장바구니에 담기--%>
							<button type="button" class="btn cartbtn" onclick="reqlogin();"><i
									class="fa-solid fa-cart-arrow-down"></i></button>
						</c:when>
						<c:otherwise>
							<button type="button" class="btn cartbtn" onclick="sendToCart();"><i
									class="fa-solid fa-cart-arrow-down"></i></button>
						</c:otherwise>
					</c:choose>
				</div>

				<hr>
				<div class="recipe-label">레시피 설명</div>
				<textarea class="content-text" readonly="readonly">${dto.content}</textarea>

				<%-- 관리자 검증할때 세션에서 롤갖고 쓰셈 --%>
				<div style="display: flex; flex-direction: row; gap: 5px">
					<c:if test="${sessionScope.member.userNickname==dto.nickname}">
						<button type="button" class="btn"
								onclick="location.href='${pageContext.request.contextPath}/recipe/update.do?id=${dto.id}';">
							수정
						</button>
					</c:if>

					<c:if test="${sessionScope.member.userNickname==dto.nickname || sessionScope.member.userRoll==1}">
						<button type="button" class="btn"
								onclick="deleteRecipe();">
							삭제
						</button>
					</c:if>
				</div>

				<div style="display: flex; justify-content: space-between">

					<c:choose>
						<c:when test="${not empty preReadDto}">
							<a class="pnbtn"
							   href="${recipeUrl}${recipeUrl.contains('keyword') ? '&' : '?'}id=${preReadDto.id}"><i
									class="fa-solid fa-caret-left"></i>&nbsp;${preReadDto.subject}</a>
						</c:when>
						<c:otherwise>
							<a></a>
						</c:otherwise>
					</c:choose>


					<c:choose>
						<c:when test="${not empty nextReadDto}">
							<a class="pnbtn rightbtn"
							   href="${recipeUrl}${recipeUrl.contains('keyword') ? '&' : '?'}id=${nextReadDto.id}">${nextReadDto.subject}&nbsp;<i
									class="fa-solid fa-caret-right"></i></a>
						</c:when>
						<c:otherwise>
							<a></a>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<hr>
			<div class="reply">
				<form name="replyForm" method="post">
					<div class="form-header">
						<span style="font-weight: bold"><i class="fa-regular fa-comment"></i> 댓글 <span
								id="replyCount">${replyCount}개</span></span>
					</div>

					<div style="width: 100%; text-align: right; margin-top: 10px">
						<c:choose>
							<c:when test="${empty sessionScope.member}">
								<textarea name="content" class="content-reply" placeholder="댓글을 달기 위해 로그인 해주세요." readonly=readonly></textarea>
							</c:when>

							<c:otherwise>
								<textarea name="content" class="content-reply" placeholder="댓글을 입력해주세요."></textarea>
							</c:otherwise>
						</c:choose>

						<c:if test="${empty sessionScope.member}">
<%--							<button type='button' class='btn btn-outline-secondary' onclick="reqlogin();">댓글 등록</button>--%>
						</c:if>

						<c:if test="${not empty sessionScope.member}">
							<button type='button' class='btn btn-outline-secondary btnSendReply'>댓글 등록</button>
						</c:if>
					</div>

				</form>
				<div id="listReply"></div>
			</div>
		</div>
	</div>
</div>

<script>
    $(document).ready(function () {
        selectMenu(menuIndex)
    })

    function deleteRecipe() {
        if (confirm("게시글을 삭제하시겠습니까 ? ")) {
            location.href = '${pageContext.request.contextPath}/recipe/delete.do?id=${dto.id}';
        }
    }

    function reqlogin() {
        if (confirm("로그인이 필요한 서비스입니다.\n로그인 하시겠습니까 ? ")) {
            location.href = '${pageContext.request.contextPath}/member/login.do';
        }
        return false;
    }

    function noAccess() {
        alert("권한이 없습니다.");
        return false;
    }

</script>

<script>
    let likeStatus = ${likeStatus};
    let likeBtn = document.getElementById('likeBtn');

    $(document).ready(function () {
        if (!likeStatus) $('#likeBtn').css("color", likeColor)
    });

    let likeColor = '#dc6c6a';
    let defaultColor = "#000000"

    function changeLikeStatus() {
        if (likeStatus) {
            // 좋아요 취소
            // 1. 상태변경
            likeStatus = !likeStatus
            // 2. 색 변경
            $('#likeBtn').css("color", likeColor)
            console.log(likeStatus + " 좋아요 취소")

        } else {
            // 좋아요 요청
            // 1. 상태변경
            likeStatus = !likeStatus
            // 2. 색 변경
            $('#likeBtn').css("color", defaultColor)
            console.log(likeStatus + " 좋아요 요청")
        }
    }

    function login() {
        location.href = "${pageContext.request.contextPath}/recipe/login.do";
    }

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
        $(".btnSendRecipeLike").click(function () {
            let msg = likeStatus ? "게시글에 공감하십니까 ?" : "게시글 공감을 취소하시겠습니까 ?";

            if (!confirm(msg)) {
                return false;
            }

            let url = "${pageContext.request.contextPath}/recipe/like";
            let id = "${dto.id}";
            let qs = "id=" + id;


            const fn = function (data) {
                let state = data.state;
                if (state === true) {
                    console.log(likeStatus)

                    changeLikeStatus();

                    let count = data.recipeLikeCount;
                    $("#recipeLikeCount").text(count);
                } else if (state === "liked") {
                    alert("좋아요는 한번만 가능합니다.");
                }
            };

            ajaxFun(url, "post", qs, "json", fn);
        });
    });

    let replyNum = $(this).attr("data-replyNum");
    listReplyAnswer(replyNum);

    // 댓글 등록
    $(function () {
        $(".btnSendReply").click(function () {
            if (!confirm("댓글을 등록하시겠습니까 ? ")) {
                return false;
            }

            let id = "${dto.id}";
            const $tb = $(this).closest("div");
            let content = $tb.find("textarea").val().trim();

            if (!content) {
                alert("댓글을 입력해주세요.");
                $tb.find("textarea").focus();
                return false;
            }

            content = encodeURIComponent(content);

            let url = "${pageContext.request.contextPath}/recipe/add-comment.do";
            let qs = "id=" + id + "&content=" + content;

            const fn = function (data) {
                $tb.find("textarea").val("");

                let state = data.state;
                if (state === "true") {
                    listReplyAnswer(replyNum);
                } else if (state === "false") {
                    alert('댓글 등록 실패');
                }
            }
            ajaxFun(url, "post", qs, "json", fn);
        });
    });


    // 댓글 삭제
    $(function () {
        $("body").on("click", ".deleteReply", function () {
            if (!confirm("댓글을 삭제하시겠습니까 ? ")) {
                return false;
            }

            let replyNum = $(this).attr("data-replyNum");

            let url = "${pageContext.request.contextPath}/recipe/drop-comment.do";
            let query = "replyNum=" + replyNum;

            const fn = function (data) {
                let state = data.state;
                if (state === "true") {
                    listReplyAnswer(replyNum);
                } else if (state === "false") {
                    alert('댓글 삭제 실패');
                }
            };

            ajaxFun(url, "post", query, "json", fn);
        });
    });

    // 댓글 리스트
    function listReplyAnswer(replyNum) {
        let url = "${pageContext.request.contextPath}/recipe/add-comment_ok.do";
        let query = "id=${dto.id}";
        let selector = "#listReply";

        const fn = function (data) {
            $(selector).html(data);
        };

        ajaxFun(url, "get", query, "text", fn);
    }

    let productIds = '${productIds}';
    let quantities = '${quantities}';

    // 장바구니에 추가
    function sendToCart() {
        $.ajax({
            url: "${pageContext.request.contextPath}/cart/recipe_add.do",
            type: "POST",
            dataType: "json",
            data: {productIds: productIds, quantities: quantities},
            success: function (data) {
                let state = data.state;
                if (state == true) {
                    if (confirm('장바구니에 담았습니다! 장바구니로 이동하시겠습니까 ?')) {
                        location.href = "${pageContext.request.contextPath}/cart/list.do";
                    }
                } else {
                    alert(data.error + '의 재고가 부족합니다.');
                }
            },
            error: function (xhr, status, error) {
                console.error(error);
            }
        });
    }

</script>
</body>
</html>
