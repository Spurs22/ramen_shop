<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Recipe</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">

	<style>
		.product-container {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            grid-auto-rows: 250px;
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
            justify-content: center;
            align-items: center;
            gap: 3px;
            padding: 15px;
            transition: 0.5s;
            background: #ffffff;
            border: 1px solid #DFE2E6;
        }

        .product-img {
            width: 100%;
            height: 150px;
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
        
        select:hover {cursor: pointer;}
        select option:hover {cursor: pointer;}
        
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
		<table>
			<tr>
				<td>순번</td>
				<td>제목</td>
				<td>작성자</td>
				<td>등록일</td>
				<td>조회 수</td>
				<td>좋아요 수</td>
			</tr>
			<c:forEach var="vo" items="${list}">
				<tr>
					<td>순번</td>
					<td>${vo.subject}</td>
					<td>${vo.nickname}</td>
					<td>${vo.createdDate}</td>
					<td>${vo.hitCount}</td>
					<td>${vo.recipeLikeCount}</td>	
				</tr>
			</c:forEach>
		</table>
	</div>
</div>

<script>
    $(document).ready(function () {
        selectMenu(menuIndex)
    })
</script>
</body>
</html>
