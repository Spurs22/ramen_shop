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

	<style type="text/css">
	.body-main { max-width: 700px; }
	
	.table-list thead > tr:first-child{ background: #f8f8f8; }
	.table-list th, .table-list td { text-align: center; }
	.table-list .left { text-align: left; padding-left: 5px; }
	
	.table-list .num { width: 60px; color: #787878; }
	.table-list .subject { color: #787878; }
	.table-list .name { width: 100px; color: #787878; }
	.table-list .date { width: 100px; color: #787878; }
	.table-list .hit { width: 70px; color: #787878; }
	
	.subject-a { text-decoration :none; color : black; }
	.subject-a :hover {cursor: pointer;}

	</style>
	
</head>
<script type="text/javascript">
    let menuIndex = 4
    
    function serachList() {
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
			<main>
				<div class="container body-container">
				    <div class="body-title">
						<h2><i class="bi bi-clipboard-check"></i> 질문과 답변 </h2>
				    </div>
				    
				    <div class="body-main">
				    	<div class="row board-list-header">
				    		<div class="col-auto me-auto">${dataCount}개(${page}/${total_page} 페이지)</div>
		            		<div class="col-auto">&nbsp;</div>
				    	</div>
				    	
				    	<table class="table talbe-hover table-list">
						<thead class="table-light">
							<tr>
								<th class="num">번호</th>
								<th class="subject">제목</th>
								<th class="name">작성자</th>
								<th class="date">작성일</th>
								<th class="hit">조회수</th>
							</tr>
						</thead>
						
						<tbody>
							<c:forEach var="dto" items="${list}" varStatus="status">
							<tr>
							  <td>${dataCount - (page-1) * size - status.index}</td>
							  <td class="left">
							  	 <c:forEach var="n" begin="1" end="${dto.depth}">&nbsp;&nbsp;</c:forEach>
							  	 <c:if test="${dto.depth!=0}">└&nbsp;</c:if>
							     <a href="${articleUrl}&id=${dto.id}" class="text-reset">${dto.subject}</a>
							  </td>
							  <td>${sessionScope.member.userNickname}</td>
							  <td>${dto.createdDate}</td>
							  <td>${dto.hitCount}</td>
							</tr>
							</c:forEach>						
						</tbody>
						</table>
						
						<div class="page-navigation">
							${dataCount == 0 ? "등록된 게시물이 없습니다." : paging}
						</div>
						
						<div class="row board-list-footer">
						  <div class="col">
						    <button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/qna/list.do';" title="새로고침"><i class="bi bi-arrow-clockwise"></i></button>
						  </div>
						  <div class="col-6 text-center">
						  	<form class="row" name="searchForm" action="${pageContext.request.contextPath}/qna/list.do" method="post">
						  	  <div class="col-auto p-1">
						  	    <select name="condition" class="form-select">
						  	    	<option value="all"      ${condition=="all"?"selected='selected'":"" }>제목+내용</option>
									<option value="userName" ${condition=="sessionScope.member.userNickname"?"selected='selected'":"" }>작성자</option>
									<option value="createdDate"  ${condition=="createdDate"?"selected='selected'":"" }>등록일</option>
									<option value="subject"  ${condition=="subject"?"selected='selected'":"" }>제목</option>
									<option value="content"  ${condition=="content"?"selected='selected'":"" }>내용</option>
						  	    </select>
						  	  </div>
						  	  <div class="col-auto p-1">
						  	  	<input type="text" name="keyword" value="${keyword}" class="form-control">
						  	  </div>
						  	  <div class="col-auto p-1">
						  	  	<button type="button" class="btn btn-light" onclick="searchList()"> <i class="bi bi-search"></i> </button>
						  	  </div>
						  	</form>
						  </div>
						  <div class="col text-end">
						    <button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/qna/write.do';">글올리기</button>
						  </div>
						</div>
					</div>
				  </div>
			</main>
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
