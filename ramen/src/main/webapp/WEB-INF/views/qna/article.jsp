<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/board2.css" type="text/css">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
	<style>
	.body-main { max-width: 700px; }

	.table-article tr > td { padding-left: 5px; padding-right: 5px; }
	
	.text-reset { 
		text-decoration: none; color : black; 
	}
	
	.point :hover { cursor: pointer; }

	</style>
	
</head>
<script>
    let menuIndex = 4
    
    <c:if test="${sessionScope.member.memberId==dto.memberId || sessionScope.member.userRoll==1}">
		function deleteBoard() {
		    if(confirm("게시글을 삭제 하시 겠습니까 ? ")) {
		        let query = "id=${dto.id}&${query}";
		        let url = "${pageContext.request.contextPath}/qna/delete.do?" + query;
		    	location.href = url;
		    }
		}
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
					<button class="btn btn-outline-primary"
							onclick="location.href='${pageContext.request.contextPath}/notice/list.do'">공지사항
					</button>
					<button class="btn btn-primary"
							onclick="location.href='${pageContext.request.contextPath}/qna/list.do'">문의사항
					</button>
				</div>
			</div>
		</div>
		<div class="content-container">
			<main>
			<div class="container body-container">
			    
			    <div class="body-main">
					<table class="table">
						<thead>
							<tr>
								<td colspan="2" align="center">
									<c:if test="${dto.depth!=0}">[Re] </c:if>
									<h4>${dto.subject}</h4> 
								</td>
							</tr>
						</thead>
						
						<tbody>
							<tr>
								<td width="50%">
									작성자 : ${dto.nickname}
								</td>
								<td align="right">
									${dto.createdDate} | 조회 ${dto.hitCount}
								</td>
							</tr>
							
							<tr>
								<td colspan="2" valign="top" height="200" style="font-size: 20px;">
									${dto.content}
								</td>
							</tr>
							
							<tr>
								<td colspan="2">
									이전글  :
									<c:if test="${not empty preReadDto}">
										<a class="text-reset" href="${pageContext.request.contextPath}/qna/article.do?${query}&id=${preReadDto.id}">${preReadDto.subject}</a>
										 <a class="point"><i class="bi bi-chevron-double-up"></i></a>
									</c:if>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									다음글 :
									<c:if test="${not empty nextReadDto}">
										<a class="text-reset" href="${pageContext.request.contextPath}/qna/article.do?${query}&id=${nextReadDto.id}">${nextReadDto.subject}</a>
										<a class="point"><i class="bi bi-chevron-double-down"></i></a>
									</c:if>
								</td>
							</tr>
						</tbody>
					</table>
					
					<table class="table table-borderless">
						<tr>
							<td width="50%">
								<c:if test="${sessionScope.member.userRoll==1}">
									<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/qna/reply.do?id=${dto.id}&page=${page}';">답변</button>
								</c:if>
								<c:choose>
									<c:when test="${sessionScope.member.memberId==dto.memberId}">
										<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/qna/update.do?id=${dto.id}&page=${page}';">수정</button>
									</c:when>
									<c:when test="${sessionScope.member.userRoll != 1 && sessionScope.member.memberId != dto.memberId}">
										<input type="hidden" class="btn btn-light" disabled="disabled">
									</c:when>
								</c:choose>
						    	
								<c:choose>
						    		<c:when test="${sessionScope.member.memberId==dto.memberId || sessionScope.member.userRoll==1}">
						    			<button type="button" class="btn btn-light" onclick="deleteBoard();">삭제</button>
						    		</c:when>
						    		<c:when test="${sessionScope.member.userRoll != 1 && sessionScope.member.memberId != dto.memberId}">
						    			<input type="hidden" class="btn btn-light" disabled="disabled">
						    		</c:when>
						    	</c:choose>
							</td>
							<td class="text-end">
								<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/qna/list.do?${query}';">리스트</button>
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
<footer>
	<jsp:include page="/WEB-INF/views/fragment/footer.jsp"/>
</footer>
</body>
</html>
