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
    let menuIndex = 5
</script>
<body>
<div class="whole-container">

	<header>
		<jsp:include page="/WEB-INF/views/fragment/menubar.jsp"/>
	</header>

	<div class="main-container shadow-lg">
		<div class="content-container">
			<div class="sub-menu">
		<table>
			<tr>
				<td width="50%"> ${likedataCount}개(${page}/${total_page} 페이지) </td>
				<td align="right">&nbsp;</td>
			</tr>
		</table>
		
		<table>
			<thead>
			<tr class="table-list">
				<th class="subject"> 이름 </th>
				<th class="subject"> 이름 </th>
			</tr>
			</thead>
			
			<tbody>
				<c:forEach var="dto" items="${list}" varStatus="status">
				<tr>
					<td class=left>
						<a href="${pageContext.request.contextPath}/product/product.do">${dto.productId}</a>
					</td>
					<td>${dto.productName}</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		
	
		
		<div class="page-navigation">
			${dataCount == 0 ? "등록된 게시물이 없습니다." : paging}
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
