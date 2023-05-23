<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
	<title>주문관리</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">

	<style>
	.whole-container{
		width: 80%;
	}
	</style>
	
	<script type="text/javascript">
	
	
	function changeList(status) {
		location.href = "${pageContext.request.contextPath}/admin/ordermanagement.do?status=" + status;
	}
	
	function searchList(){
		const f = document.searchForm;
		f.submit();
	}
	
	</script>
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
			<div>
				<ul class="nav nav-pills nav-fill">
				  <li class="nav-item">
				    <a class="nav-link active" aria-current="page" onclick="changeList(1)" href="#">결제완료</a>
				  </li>
				  <li class="nav-item">
				    <a class="nav-link" onclick="changeList(2)" href="#">배송중</a>
				  </li>
				  <li class="nav-item">
				    <a class="nav-link" onclick="changeList(3)" href="#">배송완료</a>
				  </li>
				  <li class="nav-item">
				    <a class="nav-link" onclick="changeList(4)" href="#">주문취소</a>
				  </li>
				</ul>
			</div>

			
		
			<table class="table">
				<tr>
					<td align="center">
							<div>
								<nav class="navbar bg-light">
								  <div class="container-fluid">
								    <form name="searchForm" action="${pageContext.request.contextPath}/admin/ordermanagement.do" method="post">
									      	<select name="condition" class="form-select">
												<option value="userEmail" ${condition=="userEmail"?"selected='selected'":""}>회원</option>
												<option value="orderBundleId" ${condition=="orderBundleId"?"selected='selected'":""}>주문번호</option>
											</select>
								      <input type="text" name="keyword" value="${keyword}" class="form-control me-2" aria-label="Search">
								      <input type="hidden" name="size" value="${size}">
								      <button type="button" class="btn btn-outline-success" onclick="searchList()" type="submit">검색</button>
								    </form>
								  </div>
								</nav>
							</div>
					</td>
				</tr>
			</table>
		
		
			<table class="table">
				<tr>
					<td>
						${dataCount}개(${page}/${total_page}페이지)
					</td>
				</tr>
			</table>
			
			<table class="table table-border table-list">
				<thead>
					<tr>
						<th class="orderBundleId">주문번호</th>
						<th class="createdDate">주문일</th>
						<th class="userEmail">주문자이메일</th>
						<th class="tel">전화번호</th>
						<th class="receiveName">받는분</th>
						<th class="statusName">주문상태</th>
						<th class="deliveryId">송장번호</th>
						<th class="totalPrice">합계</th>
					</tr>
				</thead>
				
				<tbody>
					<c:forEach var="orderBundle" items="${orderBundlelist}" >
						<tr>
							<td >
								<a href="${articleUrl}&num=${orderBundle.orderBundleId}" class="text-reset">${orderBundle.orderBundleId}</a>
							</td>
							<td>${orderBundle.createdDate}</td>
							<td>${orderBundle.userEmail}</td>
							<td>${orderBundle.tel}</td>
							<td>${orderBundle.receiveName}</td>
							<td>${orderBundle.statusName}</td>
							<td>${orderBundle.deliveryId}</td>
							<td>${orderBundle.totalPrice}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			${paging}
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