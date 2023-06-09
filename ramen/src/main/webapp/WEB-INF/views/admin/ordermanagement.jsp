<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
	<title>주문관리</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/paginate.css" type="text/css">
	<style>
        .whole-container {
            width: 80%;
        }
	</style>

	<script type="text/javascript">


        function changeList(status) {
            location.href = "${pageContext.request.contextPath}/admin/ordermanagement.do?status=" + status;
        }

        function searchList() {
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
		<div class="sub-menu w-100">
			<div style="display: flex; flex-direction: column; width: 100%">
				<div class="btn-group" role="group" aria-label="Basic outlined example" style="height: 40px">
					<button class="btn btn-outline-primary"
							onclick="location.href='${pageContext.request.contextPath}/admin/deliverymanagement.do?status=1'">배송관리
					</button>
					<button class="btn btn-primary"
							onclick="location.href='${pageContext.request.contextPath}/admin/ordermanagement.do?status=1'">주문관리
					</button>
					<button class="btn btn-outline-primary"
							onclick="location.href='${pageContext.request.contextPath}/admin/sales_statistics.do'">매출통계
					</button>
				</div>
			</div>
		</div>

		<div class="sub-menu w-100">
			<div style="display: flex; flex-direction: column; width: 100%">
				<div class="btn-group status-group" role="group" aria-label="Basic outlined example"
					 style="height: 40px">
					<button class="btn btn-outline-secondary" onclick="changeList(1)">결제완료</button>
					<button class="btn btn-outline-secondary" onclick="changeList(2)">배송중</button>
					<button class="btn btn-outline-secondary" onclick="changeList(3)">배송완료</button>
					<button class="btn btn-outline-secondary" onclick="changeList(4)">주문취소</button>
				</div>
			</div>
		</div>
<!-- 
		<table class="table">
			<tr>
				<td align="center">
					<div>
						<form name="searchForm" style="display: flex; flex-direction: row; width: 100%; height: 50px; gap: 10px"
							  action="${pageContext.request.contextPath}/admin/ordermanagement.do"
							  method="post">
							<select name="condition" class="form-select" style="width: 100px">
								<option value="userEmail" ${condition=="userEmail"?"selected='selected'":""}>
									회원
								</option>
								<option value="orderBundleId" ${condition=="orderBundleId"?"selected='selected'":""}>
									주문번호
								</option>
							</select>
							<input type="text" name="keyword" value="${keyword}" class="form-control"
								   aria-label="Search" style="flex: 1">
							<input type="hidden" name="size" value="${size}">
							<button type="button" class="btn btn-outline-success" onclick="searchList()" style="width: 100px">검색</button>
						</form>
					</div>
				</td>
			</tr>
		</table>
 -->

		<table class="table">
			<tr>
				<td>
					${dataCount}개(${page}/${total_page}페이지)
				</td>
			</tr>
		</table>

		<table class="table table-border table-list" style="text-align:center;">
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
				<tr style="cursor: pointer;" onclick="location.href='${pageContext.request.contextPath}/admin/ordermanagement_detail.do?orderId=${orderBundle.orderBundleId}&page=${page}';">
					<td >
							${orderBundle.orderBundleId}
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
		<div class="page-navigation">
			${dataCount == 0 ? "등록된 게시물이 없습니다." : paging}
		</div>
	</div>
</div>


<script>
    $(document).ready(function () {
        selectMenu(menuIndex)

        let status = 1;

        if (${status != 0}) {
            status = ${status}
        }

        let children = $('.status-group').children();
        let c = $(children).eq(status - 1);
        $(c).removeClass('btn-outline-secondary')
        $(c).addClass('btn-secondary')
    })
</script>
<footer>
	<jsp:include page="/WEB-INF/views/fragment/footer.jsp"/>
</footer>
</body>
</html>