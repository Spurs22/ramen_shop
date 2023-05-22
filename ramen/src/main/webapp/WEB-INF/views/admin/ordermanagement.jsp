<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
	function changeList() {
	    const f = document.listForm;
	    f.page.value="1";
	    f.action="${pageContext.request.contextPath}/admin/ordermanagement.do";
	    f.submit();
	}
	
	function changeList(statusId) {
		location.href = "${pageContext.request.contextPath}/admin/ordermanagement.do?statusId=" + statusId;
	}
	
	function searchList(){
		const f = document.searchForm;
		f.submit();
	}
	
	</script>
</head>
<script>
    let menuIndex = 6
</script>
<body>
<div class="whole-container">

	<header>
		<jsp:include page="/WEB-INF/views/fragment/menubar.jsp"/>
	</header>

	<div class="main-container shadow-lg">
		<div class="content-container">
			<div> 
				<button type='button' class='btn btnstatus' onclick="changeList(1)">결제완료</button>
				<button type='button' class='btn btnstatus' onclick="changeList(2)">배송중</button>
				<button type='button' class='btn btnstatus' onclick="changeList(3)">배송완료</button>
				<button type='button' class='btn btnstatus' onclick="changeList(4)">주문취소</button>
			</div>
		
			<table class="table">
				<tr>
					<td align="center">
					<div>
						<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/admin/ordermanagement.do';"><i class="bi bi-arrow-clockwise"></i></button>
					</div>
						<!-- 검색 폼 -->
						<form name="searchForm" action="${pageContext.request.contextPath}/admin/ordermanagement.do" method="post">
							<select name="condition" class="form-select">
								<option value="userEmail" ${condition=="userEmail"?"selected='selected'":""}>회원</option>
								<option value="orderBundleId" ${condition=="orderBundleId"?"selected='selected'":""}>주문번호</option>
							</select>
							
							<div>
							<input type="text" name="keyword" value="${keyword}" class="form-control">
							</div>
							
							<button type="button" class="btn" onclick="searchList();">검색</button>
							
							<div class="col-auto p-1">
								<input type="hidden" name="size" value="${size}">
								<button type="button" class="btn btn-light" onclick="searchList()"> <i class="bi bi-search"></i> </button>
							</div>
						
						</form>
					</td>
				</tr>
			</table>
		
		
			<table class="table">
				<tr>
					<td>
						${dataCount}개(${page}/${total_page} 페이지)
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
			<!-- 페이징하기 -->${paging}
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