<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>매출통계</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/paginate.css" type="text/css">
	<style>
	.whole-container{
		width: 80%;
	}
	</style>
	
	<script type="text/javascript">
	function changeList(proid) {
		location.href = "${pageContext.request.contextPath}/admin/sales_statistics.do?proid=" + proid;
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
					<button class="btn btn-outline-primary"
							onclick="location.href='${pageContext.request.contextPath}/admin/ordermanagement.do?status=1'">주문관리
					</button>
					<button class="btn btn-primary"
							onclick="location.href='${pageContext.request.contextPath}/admin/sales_statistics.do'">매출통계
					</button>
				</div>
			</div>
		</div>

		<div class="sub-menu w-100">
			<div style="display: flex; flex-direction: column; width: 100%">
				<div class="btn-group status-group" role="group" aria-label="Basic outlined example"
					 style="height: 40px">
					<button class="btn btn-outline-secondary" onclick="changeList(0)">전체</button>
					<button class="btn btn-outline-secondary" onclick="changeList(1)">1일</button>
					<button class="btn btn-outline-secondary" onclick="changeList(2)">1개월</button>
					<button class="btn btn-outline-secondary" onclick="changeList(3)">6개월</button>
					<button class="btn btn-outline-secondary" onclick="changeList(4)">1년</button>
				</div>
			</div>
		</div>

		<div class="content-container" style="text-align:center;">
				<table class="table table-border table-list" style="text-align:center;">
				<thead>
					<tr>
						<th class="productid">상품번호</th>
						<th class="productname">상품사진</th>
						<th class="productname">상품명</th>
						<th class="sumquantity">판매수량</th>
						<th class="sumfinal_price">총매출합계</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="sals" items="${os}">
						<tr>
							<td style="text-align:center;">${sals.productid}</td>
							<td style="text-align:center;"><img class="product-img" src="${pageContext.request.contextPath}/resource/picture/${sals.picture}" style="height: 100px;"></td>
							<td style="text-align:center;">${sals.productname}</td>
							<td style="text-align:center;">${sals.sumquantity}</td>
							<td style="text-align:center;">${sals.sumfinal_price}</td>
						</tr>
					</c:forEach>
				</tbody>
				</table>	
			</div>

		</div>
	</div>


<script>
    $(document).ready(function () {
        selectMenu(menuIndex)

        let proid = '${proid}';
        console.log(proid)

        let children = $('.status-group').children();
        let c = $(children).eq(proid);
        $(c).removeClass('btn-outline-secondary')
        $(c).addClass('btn-secondary')
    })
</script>
<footer>
	<jsp:include page="/WEB-INF/views/fragment/footer.jsp"/>
</footer>
</body>
</html>