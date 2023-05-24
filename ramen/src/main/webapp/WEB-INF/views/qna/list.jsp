<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/paginate.css" type="text/css">

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
						<h2><i class="fas fa-chalkboard-teacher"></i> 질문과 답변 </h2>
				    </div>
				    
				    <div class="body-main mx-auto">
						<table class="table">
							<tr>
								<td width="50%">
									${dataCount}개(${page}/${total_page} 페이지)
								</td>
								<td align="right">&nbsp;</td>
							</tr>
						</table>
						
						<table class="table table-border table-list">
						<thead>
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
								<a href="${articleUrl}&id=${dto.id}" class="subject-a">${dto.subject}</a>
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
			
			<table class="table">
				<tr>
					<td width="100">
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/qna/list.do';" title="새로고침"><i class="fa-solid fa-arrow-rotate-right"></i></button>
					</td>
					<td align="center">
						<form name="searchForm" action="${pageContext.request.contextPath}/qna/list.do" method="post">
							<select name="condition" class="form-select">
								<option value="all"      ${condition=="all"?"selected='selected'":"" }>제목+내용</option>
								<option value="userName" ${condition=="sessionScope.member.userNickname"?"selected='selected'":"" }>작성자</option>
								<option value="createdDate"  ${condition=="createdDate"?"selected='selected'":"" }>등록일</option>
								<option value="subject"  ${condition=="subject"?"selected='selected'":"" }>제목</option>
								<option value="content"  ${condition=="content"?"selected='selected'":"" }>내용</option>
							</select>
							<input type="text" name="keyword" value="${keyword}" class="form-control">
							<button type="button" class="btn" onclick="searchList();">검색</button>
						</form>
					</td>
					<td align="right" width="100">
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/qna/write.do';">글올리기</button>
					</td>
				</tr>
			</table>	
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
</body>
</html>
