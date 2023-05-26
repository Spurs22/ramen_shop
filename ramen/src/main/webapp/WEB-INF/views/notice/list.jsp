<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/paginate.css" type="text/css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/board2.css" type="text/css">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">

	<style type="text/css">
	.text-reset { text-decoration: none; color : black; }
	</style>

</head>
<script type="text/javascript">
    let menuIndex = 4

    function changeList() {
        const f = document.listForm;
        f.page.value = "1";
        f.action = "${pageContext.request.contextPath}/notice/list.do";
        f.submit();
    }

    function searchList() {
        const f = document.searchForm;
        f.submit();
    }

    <c:if test="${sessionScope.member.userRoll == 1}">
    $(function () {
        $("#chkAll").click(function () {
            if ($(this).is(":checked")) {
                $("input[name=ids]").prop("checked", true);
            } else {
                $("input[name=ids]").prop("checked", false);
            }
        });

        $("#btnDeleteList").click(function () {
            let cnt = $("input[name=ids]:checked").length;
            if (cnt === 0) {
                alert('삭제할 게시물을 먼저 선택하세요');
                return false;
            }

            if (confirm("선택한 게시물을 삭제 하시겠습니까?")) {
                const f = document.listForm;
                f.action = "${pageContext.request.contextPath}/notice/deleteList.do";
                f.submit();
            }
        });
    });
    </c:if>

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
					<button class="btn btn-primary"
							onclick="location.href='${pageContext.request.contextPath}/notice/list.do'">공지사항
					</button>
					<button class="btn btn-outline-primary"
							onclick="location.href='${pageContext.request.contextPath}/qna/list.do'">문의사항
					</button>
				</div>
			</div>
		</div>

		<div class="content-container">
			<div class="body-title">
				<h3><i class="bi bi-pin-fill"></i>  공지사항</h3>
			</div>

			<div class="body-main">
				<form name="listForm" method="post">
					<div class="row board-list-header">
						<div class="col-auto">
							<a>
							<c:if test="${sessionScope.member.userRoll==1}">
								<button type="button" class="btn btn-light" id="btnDeleteList" title="삭제">
									<i class="bi bi-trash3-fill"></i>
								</button>
							</c:if>
							</a>
						</div>
						<div class="col-auto" style="text-align: center; margin-right: 290px;">
							${dataCount}개(${page}/${total_page}페이지)
						</div>
						<div style="" class="col-auto">
							<c:if test="${dataCount!=0}">
								<select name="size" class="form-select" onchange="changeList();">
									<option value="5"  ${size==5 ? "selected='selected' ":""}>5개씩 출력</option>
									<option value="10" ${size==10 ? "selected='selected' ":""}>10개씩 출력</option>
									<option value="20" ${size==20 ? "selected='selected' ":""}>20개씩 출력</option>
									<option value="30" ${size==30 ? "selected='selected' ":""}>30개씩 출력</option>
									<option value="50" ${size==50 ? "selected='selected' ":""}>50개씩 출력</option>
								</select>
							</c:if>
							<input type="hidden" name="page" value="${page}">
							<input type="hidden" name="condition" value="${condition}">
							<input type="hidden" name="keyword" value="${keyword}">
						</div>
					</div>

					<table class="table table-hover board-list">
						<thead class="table-light">
						<tr>
							<c:if test="${sessionScope.member.userRoll==1}">
								<th class="chk">
									<input type="checkbox" name="chkAll" id="chkAll">
								</th>
							</c:if>
							<th class="num">번호</th>
							<th class="subject">제목</th>
							<th class="name"> 작성자</th>
							<th class="date">작성일</th>
							<th class="hit">조회수</th>
						</tr>
						</thead>

						<tbody>
						<c:forEach var="dto" items="${listNotice}">
							<tr>
								<c:if test="${sessionScope.member.userRoll==1}">
									<td>
										<input type="checkbox" class="form-check-input" name="ids" value="${dto.id}">
									</td>
								</c:if>
								<td><span class="badge bg-primary">공지</span></td>
								<td class="left">
									<a href="${articleUrl}&id=${dto.id}" class="text-reset">${dto.subject}</a>
								</td>
								<td>${dto.nickname}</td>
								<td style="width: 150px;">${dto.createdDate}</td>
								<td>${dto.hitCount}</td>
							</tr>
						</c:forEach>

						<c:forEach var="dto" items="${list}" varStatus="status">
							<tr>
								<c:if test="${sessionScope.member.userRoll==1}">
									<td>
										<input type="checkbox" class="form-check-input" name="ids" value="${dto.id}">
									</td>
								</c:if>
								<td>${dataCount - (page-1) * size - status.index}</td>
								<td class="left">
									<a href="${articleUrl}&id=${dto.id}" class="text-reset">${dto.subject}</a>
									<c:if test="${dto.gap<1}"><img style="width: 30px;"
																   src="${pageContext.request.contextPath}/resource/picture/new2.gif"></c:if>
								</td>
								<td>${dto.nickname}</td>
								<td>${dto.createdDate}</td>
								<td>${dto.hitCount}</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</form>

				<div class="page-navigation">
					${dataCount == 0 ? "등록된 게시물이 없습니다." : paging}
				</div>


				<form class="" name="searchForm" style="display: flex; flex-direction: row; width: 100%; justify-content: space-between; margin-top: 10px" action="${pageContext.request.contextPath}/notice/list.do" method="post">

					<button type="button" class="btn btn-light" style="background: #206CD9; color: white;"
							onclick="location.href='${pageContext.request.contextPath}/notice/list.do';">
							<i class="bi bi-arrow-clockwise"></i></button>

					<div style="display: flex; flex-direction: row; justify-content: center; gap: 10px">
						<select name="condition" class="form-select" style="width: 130px">
							<option value="all"      ${condition=="all"?"selected='selected'":"" }>제목+내용</option>
							<option value="createdDate"  ${condition=="createdDate"?"selected='selected'":"" }>등록일</option>
							<option value="subject"  ${condition=="subject"?"selected='selected'":"" }>제목</option>
							<option value="content"  ${condition=="content"?"selected='selected'":"" }>내용</option>
						</select>

						<input type="text" name="keyword" value="${keyword}" class="form-control">

						<input type="hidden" name="size" value="${size}">
						<button style="background: #206CD9; color: white;" type="button" class="btn btn-light" onclick="searchList()"><i class="bi bi-search"></i></button>
					</div>

					<c:choose>
						<c:when test="${sessionScope.member.userRoll == 1}">
							<button style="background: #206CD9; color: white;" type="button" class="btn btn-light"
									onclick="location.href='${pageContext.request.contextPath}/notice/write.do';">글올리기
							</button>
						</c:when>

						<c:otherwise>
							<div></div>
						</c:otherwise>
					</c:choose>
				</form>
			</div>
		</div>
	</div>
</div>

<script>
    $(document).ready(function () {
        selectMenu(menuIndex)
    })
</script>
<footer>
	<jsp:include page="/WEB-INF/views/fragment/footer.jsp"/>
</footer>
</body>
</html>

