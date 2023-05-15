<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">

	<style>
		tr > td {text-align: center;}
		tr > th {background-color: #DFE2E6; }
		.content-table > tr {padding: 15px;}
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
			<table class="content-table" style="width: 100%">
				<tr>
					<th style="width: 60%; padding-left: 20px">
						${dto.nickname}
					</th>
					<th style="text-align: right">
						조회수 : ${dto.hitCount} 회
					</th>
					<th style="text-align: right; padding-right: 20px">
						❤ ${dto.recipeLikeCount}
					</th>
				</tr>
				<tr>
					<td colspan=3>
						${dto.subject}
					</td>
				</tr>
				<tr>
					<td colspan=3>
						<img src="${pageContext.request.contextPath}/resource/picture/1.png">
					</td>
				</tr>
				<tr>
					<td colspan=3>
					<%--
						<c:forEach var="recipe" items="${list}">
							${list.productId} <br>
							${list.quantity} <br>
						</c:forEach>
						 --%>
					</td>
				</tr>
				<tr>
					<td colspan=3>
						${dto.content}
					</td>
				</tr>
				<tfoot>
				<tr>
					<td colspan=2 style="text-align: left">
						<button>수정</button>
						<button>삭제</button>
					</td>
					<td>
						${dto.createdDate}
					</td>
				</tr>
				</tfoot>
			</table>
			
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
