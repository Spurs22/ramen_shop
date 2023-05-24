<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">

	<style>

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
		<div class="content-container">
<%--			<h3 style="margin-bottom: 30px">마이페이지</h3>--%>

			<div style="display: flex; flex-direction: column">
				<div class="btn-group" role="group" aria-label="Basic outlined example" style="height: 40px">
					<button class="btn btn-outline-primary" onclick="location.href='${pageContext.request.contextPath}/mypage/productLikeList.do'"> 내가 찜 한 상품 </button>
					<button class="btn btn-outline-primary" onclick="location.href='${pageContext.request.contextPath}/mypage/recipeLikeList.do'"> 내가 좋아요 한 레시피 </button>
					<button class="btn btn-outline-primary" onclick="location.href='${pageContext.request.contextPath}/mypage/recipeBoardMyList.do'"> 내가 작성한 글 </button>
					<button class="btn btn-outline-primary" onclick="location.href='${pageContext.request.contextPath}/mypage/orderMyList.do'"> 나의 주문내역 </button>
				</div>
				<div style="background: #c5c5c5">

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
</body>
</html>
