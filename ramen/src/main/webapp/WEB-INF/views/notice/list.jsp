<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">

	<style>

	</style>
</head>
<script>
    let menuIndex = 8
    
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
    
    <c:if test="${sessionScope.member.memberId=='admin'}">
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
			<button type="button" class="btn" id="btnCategory"> ${category==1 ? "공지사항" : (category==2? "FAQ" : "문의사항")} </button> 
			<form name="listForm" method="post">
				<table>
					<tr>
						<td>
							<c:if test="${sessioninfoScope.member.memberId=='admin'}">
								<button type="button" class="btn" id="btnDeleteList">삭제</button>
							</c:if>
							<c:if test="${sessioninfoScope.member.memberId=='admin'}">
								${dataCount}개(${page}/${total_page} 페이지)
							</c:if>
						</td>
						<td align="right">
							<c:if test="${dataCount!=0 }">
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
						</td>
					</tr>
				</table>
				
				<table>
					<thead>
						<tr>
							<c:if test="${sessionScope.member.memberId=='admin'}">
								<th class="chk">
									<input type="checkbox" name="chkAll" id="chkAll">        
								</th>
							</c:if>
							<th class="num">번호</th>
							<th class="subject">제목</th>
							<th class="name">작성자</th>
							<th class="date">작성일</th>
							<th class="hit">조회수</th>
						</tr>
					</thead>
					
					
				</table>
				
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
