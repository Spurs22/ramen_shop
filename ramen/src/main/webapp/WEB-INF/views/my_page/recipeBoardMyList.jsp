<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/paginate.css" type="text/css">

<style>
	.table-list thead > tr:first-child{ background: #f8f8f8; }
	.table-list th, .table-list td { text-align: center; }
	.table-list .left { text-align: left; padding-left: 5px; }

	.table-list .num { width: 50px; color: #787878; }
	.table-list .subject { width: 150px; color: #787878; }
	.table-list .date { width: 200px; color: #787878; }
	.table-list .hit { width: 70px; color: #787878; }
	.table-list .heart { width: 70px; color: #787878; }

	.table-header {
		display: grid;
        grid-template-columns: 10% 20% 30% 25% 15%;
		width: 100%;
		justify-content: center;
		align-items: center;
		align-content: center;
		text-align: center;
		border: 1px solid black;
		padding: 10px;
		border-radius: 5px;
		/*background: #e6f1e8;*/
	}

	.table-main {
		display: grid;
		grid-template-columns: 10% 20% 30% 25% 15%;
		width: 100%;
		justify-content: center;
		align-items: center;
		align-content: center;
		text-align: center;
		border: 1px solid black;
		padding: 10px;
		border-radius: 5px;
		grid-template-rows: 100%;
		height: 130px;
		color: black;
	}

	.table-main:hover {
        cursor: pointer;
		background: rgba(161, 161, 161, 0.15);
    }

	.recipe-img {
		height: 100%;
		width: 100%;
		object-fit: cover;
	}
</style>

</head>
<script>
    let menuIndex = 5
    
</script>

<body>
	<div class="whole-container">

		<header>
			<jsp:include page="/WEB-INF/views/fragment/menubar.jsp"/>
		</header>

		<div class="main-container shadow-lg">
			<div class="sub-menu w-100">
				<div style="display: flex; flex-direction: column; width: 100%">
					<div class="btn-group" role="group" aria-label="Basic outlined example" style="height: 40px">
						<button class="btn btn-outline-primary" onclick="location.href='${pageContext.request.contextPath}/mypage/productLikeList.do'"> 내가 찜 한 상품 </button>
						<button class="btn btn-outline-primary" onclick="location.href='${pageContext.request.contextPath}/mypage/recipeLikeList.do'"> 내가 좋아요 한 레시피 </button>
						<button class="btn btn-primary" onclick="location.href='${pageContext.request.contextPath}/mypage/recipeBoardMyList.do'"> 내가 작성한 글 </button>
						<button class="btn btn-outline-primary" onclick="location.href='${pageContext.request.contextPath}/mypage/orderMyList.do'"> 나의 주문내역 </button>
					</div>
				</div>
			</div>

			<div class="content-container">
				<div>
					<div style="margin: 10px 0">
						${dataCount}개 (${page}/${total_page} 페이지)
					</div>

					<div style="display: flex; flex-direction: column; gap: 10px">
						<div style="" class="table-header bg-light">
							<div class="num">글 번호</div>
							<div class="date">이미지</div>
							<div class="subject">제목</div>
							<div class="hit">조회수</div>
							<div class="heart">좋아요</div>
						</div>

						<c:forEach var="dto" items="${list}" varStatus="status">
							<div style="" class="table-main" onclick="location.href='${pageContext.request.contextPath}/recipe/recipe.do?id=${dto.id}'">
								<div>${dto.id}</div>
								<div>
									<img class="product-img" style="width: 100%;" src="${pageContext.request.contextPath}/resource/picture/${dto.picture == null ? "default2.png" : dto.picture}"/>
								</div>
								<div>${dto.subject}</div>
								<div>${dto.hitCount}</div>
								<div><i class="fa-solid fa-heart" style="color: red;"> </i>${dto.recipeLikeCount}</div>
							</div>
						</c:forEach>

						<div class="page-navigation">
							${dataCount == 0 ? "등록된 게시물이 없습니다." : paging}
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<script>
    $(document).ready(function () {
        selectMenu(menuIndex)
    })
</script>
<footer>
	<jsp:include page="/WEB-INF/views/fragment/footer.jsp"/>
</footer>
</body>
</html>
