<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>관리자</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">

	<style>
	.whole-container{
		width: 80%;
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
			<form action="location.href='${pageContext.request.contextPath}/admin/main.do';">
	
				<div>
					<div>
						<button type="button" onclick="location.href='${pageContext.request.contextPath}/admin/deliverymanagement.do';">배송관리</button>
					</div>
					
					<div>
						<button type="button" onclick="location.href='${pageContext.request.contextPath}/admin/ordermanagement.do';">주문관리</button>
					</div>
					
					<div>
						<button type="button" onclick="location.href='${pageContext.request.contextPath}/admin/sales_statistics.do';">매출통계</button>
					</div>
				</div>
			
				<div>
				<h3>주문배송</h3>
				</div> 
			
				 <hr>
			 
			 
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
						<c:forEach var="orderBundle" items="${orderBundlelist}">
							<tr>
								<td>${orderBundle.orderBundleId}</td>
								<td>${orderBundle.createdDate}</td>
								<td>${orderBundle.userEmail}</td>
								<td>${orderBundle.tel}</td>
								<td>${orderBundle.receiveName}</td>
								<td>${orderBundle.statusName}</td>
								<td>
									<input type="text" name="deliveryId" value="${deliveryId}" class="form-control">
									<button type="button" class="btn" onclick="registration()">입력</button>
								</td>
								<td>${orderBundle.totalPrice}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

			<div>
				${paging}
			</div>
			
			</form>
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
