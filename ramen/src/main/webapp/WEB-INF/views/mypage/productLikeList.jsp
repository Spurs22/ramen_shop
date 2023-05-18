<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">

<style>
.table-list thead > tr:first-child{ background: #f8f8f8; }
.table-list th, .table-list td { text-align: center; }
.table-list .left { text-align: left; padding-left: 5px; }

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
</style>
</head>
<script>
    let menuIndex = 10
</script>
<body>
<div class="whole-container">

	<header>
		<jsp:include page="/WEB-INF/views/fragment/menubar.jsp"/>
	</header>

	<div class="main-container shadow-lg">
		<div class="content-container">
			<div class="sub-menu">
				<form name="radioForm" action="${pageContext.request.contextPath}/mypage/productLikeList.do" method="post">
					<div class="btn-group" role="group" aria-label="Basic radio toggle button group">
						<input type="radio" class="btn-check" name="btnradio" id="btnradio1" value="btnradio1" autocomplete="off" checked>
						<label class="btn btn-outline-primary" for="btnradio1">
							<a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/mypage/productLikeList.do"> 내가 찜 한 상품 </a>
						</label>
		
						<input type="radio" class="btn-check" name="btnradio" id="btnradio2" value="btnradio2" autocomplete="off" onclick="clickBtnradio(this)">
						<label class="btn btn-outline-primary" for="btnradio2">
							<a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/mypage/recipeLikeList.do"> 내가 좋아요 한 레시피 </a>
						</label>
						
						<input type="radio" class="btn-check" name="btnradio" id="btnradio3" value="btnradio3" autocomplete="off" onclick="clickBtnradio(this)">
						<label class="btn btn-outline-primary" for="btnradio3">
							<a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/mypage/recipeBoardMyList.do"> 내가 작성한 조합레시피 글 </a>
						</label>
						
						<input type="radio" class="btn-check" name="btnradio" id="btnradio4" value="btnradio4" autocomplete="off" onclick="clickBtnradio(this)">
						<label class="btn btn-outline-primary" for="btnradio4">
							<a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/mypage/orderMyList.do"> 나의 주문내역 </a>
						</label>
					</div>
				</form>
		<table>
			<tr>
				<td width="50%"> ${dataCount}개(${page}/${total_page} 페이지) </td>
				<td align="right">&nbsp;</td>
			</tr>
		</table>
		
		<table>
			<c:forEach var="dto" items="${productLikeList}" varStatus="status">
				<td>${dto.name}</td>
				<td>${dto.createDate}</td>
			</c:forEach>
		</table>
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
