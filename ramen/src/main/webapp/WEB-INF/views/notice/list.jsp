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
.sub-menu button { border-radius: 5px; }

</style>

</head>
<script type="text/javascript">
    let menuIndex = 4
    
    function changeList() {
    	const f = document.listForm;
    	f.page.value = "1";
    	f.action="${pageContext.request.contextPath}/notice/list.do";
    	f.submit();
    }
    
    function searchList() {
    	const f = document.searchForm;
    	f.submit();
    }
    
    <c:if test="${sessionScope.member.userRoll == 1}">
    	$(function(){
    		$("#chkAll").click(function(){
    			if($(this).is(":checked")) {
    				$("input[name=ids]").prop("checked", true);
    			} else {
    				$("input[name=ids]").prop("checked", false);
    			}
    		});
    		
    		$("#btnDeleteList").click(function(){
    			let cnt = $("input[name=ids]:checked").length;
    			if (cnt === 0) {
    				alert('삭제할 게시물을 먼저 선택하세요');
    				return false;
    			}
    			
    			if(confirm("선택한 게시물을 삭제 하시겠습니까?")) {
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
		<div class="content-container">
			<div>
				<button type="button" onclick="location.href='${pageContext.request.contextPath}/notice/list.do'">공지사항</button>
				<button type="button" onclick="location.href='${pageContext.request.contextPath}/qna/list.do'">문의사항</button>
			</div>
		
			<div class="body-title">
				<h3><i class="bi bi-clipboard-check"></i>공지사항</h3>
			</div>
			<div class="body-main">
			<form name="listForm" method="post">
				<div class="row board-list-header">
					<div class="col-auto-me-auto">
					<c:if test="${sessionScope.member.userRoll==1}">
						<button type="button" class="btn btn-light" id="btnDeleteList" title="삭제">
							<i class="bi bi-trash3-fill"></i>
						</button>
					</c:if>
					<c:if test="${sessionScope.member.userRoll==1}">
						<p class="form-control-plaintext">
							${dataCount}개(${page}/${total_page}페이지)
						</p>
					</c:if>
					</div>
					<div class="col-auto">
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
						<th class="name"> 작성자 </th> 
						<th class="date">작성일</th>
						<th class="hit">조회수</th>
					  </tr>
					</thead>
					
					<tbody>
						<c:forEach var="dto" items="${listNotice}">
						  <tr>
						  	<c:if test="${sessionScope.member.userRoll==1}">
						  	  <td>
						  	  	<input type="checkbox" name="ids" value="${dto.id}">
						  	  </td>
						  	</c:if>
						  	<td><span class="badge bg-primary">공지</span></td>
						  	<td class="left">
						  	  <a href="${articleUrl}&id=${dto.id}" class="text-reset">${dto.subject}</a>
						  	</td>
						  	<td>${sessionScope.member.userNickname}</td>
						  	<td style="width: 150px;">${dto.createdDate}</td>
						  	<td>${dto.hitCount}</td>
						  </tr>
						</c:forEach>
						
						<c:forEach var="dto" items="${listNotice}">
						  <tr>
						    <c:if test="${sessionScope.member.userRoll==1}">
						      <td>
						      	<input type="checkbox" class="form-check-input" name="ids" value="${dto.id}">
						      </td>
						    </c:if>
						    <td>${dataCount - (page-1) * size - status.index}</td>
						    <td class="left">
						    	<a href="${articleUrl}&id=${dto.id}" class="text-reset">${dto.subject}</a>
						    	<c:if test="${dto.gap<1}"><img src="${pageContext.request.contextPath}/resource/picture/new.gif"></c:if>
						    </td>
						    <td>${sessionScope.member.userNickname}</td>
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
			
			<div class="row board-list-footer">
			  <div class="col">
			  	<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/notice/list.do';"><i class="bi bi-arrow-clockwise"></i></button>
			  </div>
			  <div class="col-6 text-center">
			    <form class="row" name="searchForm" action="${pageContext.request.contextPath}/notice/list.do" method="post">
			      <div class="col-auto p-1">
			        <select name="condition" class="form-select">
			        	<option value="all"      ${condition=="all"?"selected='selected'":"" }>제목+내용</option>
						<option value="createdDate"  ${condition=="createdDate"?"selected='selected'":"" }>등록일</option>
						<option value="subject"  ${condition=="subject"?"selected='selected'":"" }>제목</option>
						<option value="content"  ${condition=="content"?"selected='selected'":"" }>내용</option>
			        </select>
			      </div>
			      <div class="col-auto p-1">
			        <input type="text" name="keyword" value="${keyword}" class="form-control">
			      </div>
			      <div class="col-auto p-1">
			      	<input type="hidden" name="size" value="${size}">
			      	<button type="button" class="btn btn-light" onclick="searchList()"><i class="bi bi-search"></i></button>
			      </div>
			    </form>
			  </div>
			  <div>
			  	<c:if test="${sessionScope.member.userRoll==1}">
			  	  <button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/notice/write.do';">글올리기</button>
			  	</c:if>
			  </div>
			</div>
		</div>
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
