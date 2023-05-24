<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">

	<style>
	.body-main { max-width: 700px; }

	.table-article tr > td { padding-left: 5px; padding-right: 5px; }
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
		<div class="content-container">
			<main>
			<div class="container body-container">
			    <div class="body-title">
					<h2><i class="fas fa-chalkboard-teacher"></i> 질문과 답변 </h2>
			    </div>
			    
			    <div class="body-main mx-auto">
					<table class="table table-border table-article">
						<thead>
							<tr>
								<td colspan="2" align="center">
									<c:if test="${dto.depth!=0 }">[Re] </c:if>
									${dto.subject}
								</td>
							</tr>
						</thead>
						
						<tbody>
							<tr>
								<td width="50%">
									이름 : ${sessionScope.member.userNickname}
								</td>
								<td align="right">
									${dto.createdDate} | 조회 ${dto.hitCount}
								</td>
							</tr>
							
							<tr>
								<td colspan="2" valign="top" height="200">
									${dto.content}
								</td>
							</tr>
							
							<tr>
								<td colspan="2">
									이전글 :
									<c:if test="${not empty preReadDto}">
										<a href="${pageContext.request.contextPath}/qna/article.do?${query}&id=${preReadDto.id}">${preReadDto.subject}</a>
									</c:if>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									다음글 :
									<c:if test="${not empty nextReadDto}">
										<a href="${pageContext.request.contextPath}/qna/article.do?${query}&id=${nextReadDto.id}">${nextReadDto.subject}</a>
									</c:if>
								</td>
							</tr>
						</tbody>
					</table>
					
					<table class="table">
						<tr>
							<td width="50%">
								<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/qna/reply.do?id=${dto.id}&page=${page}';">답변</button>
								<c:choose>
									<c:when test="${sessionScope.member.memberId==dto.memberId}">
										<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/qna/update.do?id=${dto.id}&page=${page}';">수정</button>
									</c:when>
									<c:otherwise>
										<button type="button" class="btn" disabled="disabled">수정</button>
									</c:otherwise>
								</c:choose>
						    	
								<c:choose>
						    		<c:when test="${sessionScope.member.memberId==dto.memberId || sessionScope.member.userRoll==1}">
						    			<button type="button" class="btn" onclick="deleteBoard();">삭제</button>
						    		</c:when>
						    		<c:otherwise>
						    			<button type="button" class="btn" disabled="disabled">삭제</button>
						    		</c:otherwise>
						    	</c:choose>
							</td>
							<td align="right">
								<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/qna/list.do?${query}';">리스트</button>
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