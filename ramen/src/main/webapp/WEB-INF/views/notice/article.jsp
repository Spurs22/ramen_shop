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

<script type="text/javascript">
    let menuIndex = 4
    
    <c:if test="${sessionScope.member.memberId=='1'}">
    function deleteNotice() {
        if(confirm("게시글을 삭제 하시 겠습니까 ? ")) {
            let query = "id=${dto.id}&${query}";
            let url = "${pageContext.request.contextPath}/notice/delete.do?" + query;
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
			<div>
				<h2>${category==1 ? "공지사항" : (category==2? "FAQ" : "문의사항")} </h2>
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
