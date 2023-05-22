<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String productIds = request.getParameter("productIds");
	String quantities = request.getParameter("quantities");
%>
<html>
<head>
	<title>Title</title>
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
            padding: 10px;
            resize: none;
            margin-top: 10px;
            margin-bottom: 10px;
            border-left: none;
            border-right: none;
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
        }

        .content-reply {
            outline: none;
        }

        .pnbtn {
            color: black;
            text-decoration: none;
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
        
	</style>
</head>
<script>
    let menuIndex = 3
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
        	if(! confirm("댓글을 등록하시겠습니까 ? ")) {
				return false;
			}
        	
            let id = "${dto.id}";
            const $tb = $(this).closest("table");
            let content = $tb.find("textarea").val().trim();

            if (!content) {
            	alert("댓글을 입력해주세요.");
                $tb.find("textarea").focus();
                return false;
            }

            content = encodeURIComponent(content);

            let url = "${pageContext.request.contextPath}/recipe/add-comment.do";
            let qs = "id=" + id + "&content=" + content;

            const fn = function(data) {
                $tb.find("textarea").val("");

                let state = data.state;
                if(state === "true") {
                	listReplyAnswer(replyNum);
                } else if (state === "false") {
                	alert('댓글 등록 실패');
                }
            }
            ajaxFun(url, "post", qs, "json", fn);
        });
    });
    
    
    // 댓글 삭제
    $(function() {
		$("body").on("click", ".deleteReply", function() {
			if(! confirm("댓글을 삭제하시겠습니까 ? ")) {
				return false;
			}
			
			let replyNum = $(this).attr("data-replyNum");
			
			let url = "${pageContext.request.contextPath}/recipe/drop-comment.do";
			let query = "replyNum="+replyNum;
			
			const fn = function(data) {
				let state = data.state;
				if(state === "true") {
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
		
		const fn = function(data) {
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
	        data: { productIds: productIds, quantities: quantities },
	        success: function(data) {
	        	let state = data.state;
	        	if(state == "true") {
		        	if(confirm('장바구니에 담았습니다! 장바구니로 이동하시겠습니까 ?')) {
			        	location.href = "${pageContext.request.contextPath}/cart/list.do";
		        	}
	        	}
	        },
	        error: function(xhr, status, error) {
	            console.error(error);
	        }
	    });
	}

</script>
<body>
<div class="whole-container">

	<header>
		<jsp:include page="/WEB-INF/views/fragment/menubar.jsp"/>
	</header>

	<div class="main-container shadow-lg">
		<div class="content-container">
			<table class="content-table" style="width: 100%;">
				<tr>
					<th style="width: 55%; padding-left: 20px">
						${dto.nickname}
					</th>
					<th style="text-align: right; width: 30%">
						조회수 : ${dto.hitCount} 회
					</th>
					<th style="text-align: right; padding-right: 20px; width: 15%">
						<c:choose>
							<c:when test="${sessionScope.member!=null}">
								<button type="button" class="btn btnSendRecipeLike" title="좋아요" style="border: none;"><i
								class="fa-solid fa-heart" id="likeBtn"></i>&nbsp;&nbsp;<span
								id="recipeLikeCount">${dto.recipeLikeCount}</span></button>
							</c:when>
							<c:otherwise>
								<button type="button" class="btn btnSendRecipeLike" title="좋아요" style="border: none;" disabled="disabled"><i
								class="fa-solid fa-heart" id="likeBtn"></i>&nbsp;&nbsp;<span
								id="recipeLikeCount">${dto.recipeLikeCount}</span></button>
							</c:otherwise>
						</c:choose>
					</th>
				</tr>
				<tr>
					<td colspan=3 style="font-size: 25px; font-weight: bold; padding-top: 20px;">
						${dto.subject}
					</td>
				</tr>
				<tr>
					<td colspan=3>
						<hr>
						<img src="${pageContext.request.contextPath}/resource/picture/1.png">
						<hr>
					</td>
				</tr>
				<c:forEach var="recipe" items="${list}">
					<tr>	
						<td style="width: 25%; text-align: right; padding-right: 20px;">
							${recipe.name}
						</td>
						<td style="width: 50%; text-align: left" colspan="2">
							${recipe.quantity} 개
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="3">
						<c:choose>
							<c:when test="${empty list}">
								<br>장바구니에 담기 <button type="button" class="btn cartbtn" disabled="disabled"><i class="fa-solid fa-cart-arrow-down"></i></button>
							</c:when>
							<c:when test="${empty sessionScope.member}">
								<br>장바구니에 담기 <button type="button" class="btn cartbtn" onclick="reqlogin();"><i class="fa-solid fa-cart-arrow-down"></i></button>
							</c:when>
							<c:otherwise>
								<br>장바구니에 담기 <button type="button" class="btn cartbtn" onclick="sendToCart();"><i class="fa-solid fa-cart-arrow-down"></i></button>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td colspan=3 style="text-align: left;">
						<textarea class="content-text" readonly="readonly">${dto.content}</textarea>
					</td>
				</tr>
				<tr>
					<td style="text-align: left">
						<c:choose>
							<c:when test="${sessionScope.member.userNickname==dto.nickname || sessionScope.member.userNickname=='관리자'}">
								<button type="button" class="btn"
										onclick="location.href='${pageContext.request.contextPath}/recipe/update.do?id=${dto.id}';">
									수정
								</button>
							</c:when>
							<c:when test="${sessionScope.member.userNickname!=dto.nickname || sessionScope.member.userNickname!='관리자'}">
								<button type="button" class="btn"
										onclick="noAccess();">
									수정
								</button>
							</c:when>
							<c:otherwise>
								<button type="button" class="btn" onclick="reqlogin();">수정</button>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${sessionScope.member.userNickname==dto.nickname || sessionScope.member.userNickname=='관리자'}">
								<button type="button" class="btn"
										onclick="deleteRecipe();">
									삭제
								</button>
							</c:when>
							<c:when test="${sessionScope.member.userNickname!=dto.nickname || sessionScope.member.userNickname!='관리자'}">
								<button type="button" class="btn"
										onclick="noAccess();">
									삭제
								</button>
							</c:when>
							<c:otherwise>
								<button type="button" class="btn" onclick="reqlogin();">삭제</button>
							</c:otherwise>
						</c:choose>
					</td>
					<td colspan=2 style="width: 30%; text-align: right; padding: 10px;">
						등록일 : ${dto.createdDate}
					</td>
				</tr>
				<tr>
					<td style="text-align: left;">
						<c:if test="${not empty preReadDto}">
							<a class="pnbtn"
							   href="${recipeUrl}${recipeUrl.contains('keyword') ? '&' : '?'}id=${preReadDto.id}"><i
									class="fa-solid fa-caret-left"></i>&nbsp;${preReadDto.subject}</a>
						</c:if>
					</td>
					<td>
					</td>
					<td style="text-align: right;">
						<c:if test="${not empty nextReadDto}">
							<a class="pnbtn"
							   href="${recipeUrl}${recipeUrl.contains('keyword') ? '&' : '?'}id=${nextReadDto.id}">${nextReadDto.subject}&nbsp;<i
									class="fa-solid fa-caret-right"></i></a>
						</c:if>
					</td>
				</tr>
			</table>
			<hr>
			<div class="reply">
				<form name="replyForm" method="post">
					<div class="form-header">
						<span style="font-weight: bold"><i class="fa-regular fa-comment"></i> 댓글 <span id="replyCount">${replyCount}개</span></span>
					</div>

					<table style="width: 100%">
						<tr>
							<td>
								<textarea name="content" class="content-reply" placeholder="댓글을 입력해주세요."></textarea>
							</td>
						</tr>
						<tr>
							<td class='right'>
								<c:if test="${empty sessionScope.member}">
									<button type='button' class='btn' onclick="reqlogin();">댓글 등록</button>
								</c:if>
								<c:if test="${not empty sessionScope.member}">
									<button type='button' class='btn btnSendReply'>댓글 등록</button>
								</c:if>
							</td>
						</tr>
					</table>

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
		if(confirm("게시글을 삭제하시겠습니까 ? ")) {
	    	location.href='${pageContext.request.contextPath}/recipe/delete.do?id=${dto.id}';
		}
	}
    
    function reqlogin() {
		alert("로그인이 필요합니다.");
		return false;
	}
    
    function noAccess() {
		alert("권한이 없습니다.");
		return false;
	}
</script>
</body>
</html>
