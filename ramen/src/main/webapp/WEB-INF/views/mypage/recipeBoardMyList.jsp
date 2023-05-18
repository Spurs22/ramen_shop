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

.table-list .subject { color: #787878; }
.table-list .name { width: 100px; color: #787878; }
.table-list .date { width: 100px; color: #787878; }
.table-list .hit { width: 70px; color: #787878; }
.table-list .heart { width: 70px; color: #787878; }
</style>

</head>
<script>
    let menuIndex = 10
    
    function searchList() {
    	const f = document.searchForm;
    	f.submit();
    }
</script>

<body>
<div class="whole-container">

	<header>
		<jsp:include page="/WEB-INF/views/fragment/menubar.jsp"/>
	</header>

	<div class="main-container shadow-lg">
		<div class="content-container">
			<div><h2> 내가 작성한 조합레시피 글</h2></div>
			
			<div>
				<table>
					<tr>
						<td width="50%"> ${dataCount}개(${page}/${total_page} 페이지) </td>
						<td align="right">&nbsp;</td>
					</tr>	
				</table>
				
				<table>
					<thead>
					<tr class="table-list">
						<th class="subject"> 제목 </th>
						<th class="date"> 작성일 </th>
						<th class="hit"> 조회수 </th>
						<th class="heart"> 좋아요 수 </th>
					</tr>
					</thead>
					
					<tbody>
						<c:forEach var="dto" items="${recipeBoardMyList}" varStatus="status">
						<tr>
							<td>${dataCount - (page-1) * size - status.index} </td>
							<td class="left">
								<a href="${articleUrl}&id=${dto.id}">${dto.subject}</a>
							</td>
							<td>${dto.createDate}</td>
							<td>${dto.hitCount}</td>
							<td><i class="fa-solid fa-heart" style="color: red;"></i>${dto.recipeLikeCount}</td>
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
