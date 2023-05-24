<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">

<style>
<style type="text/css">
.body-main {
	max-width: 700px;
	padding-top: 15px;
}

.table-form td { padding: 7px 0; }
.table-form p { line-height: 200%; }
.table-form tr:first-child { border-top: 2px solid #212529; }
.table-form tr > td:first-child { width: 110px; text-align: center; background: #f8f8f8; }
.table-form tr > td:nth-child(2) { padding-left: 10px; }

.table-form input[type=text], .table-form textarea { width: 96%; }
.table-form input[type=checkbox] { vertical-align: middle; }

</style>


</head>
<script type="text/javascript">
    let menuIndex = 4
</script>

<script type="text/javascript">
    function sendOk() {
        const f = document.noticeForm;
    	let str;
    	
        str = f.subject.value.trim();
        if(!str) {
            alert("제목을 입력하세요. ");
            f.subject.focus();
            return;
        }

        str = f.content.value.trim();
        if(!str) {
            alert("내용을 입력하세요. ");
            f.content.focus();
            return;
        }

        f.action = "${pageContext.request.contextPath}/notice/${mode}_ok.do";
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
			<form name="noticeForm" method="post" enctype="multipart/form-data">
			<table class="table table-border table-form">
					<tr>
						<td>제&nbsp;&nbsp;&nbsp;&nbsp;목</td>
						<td> 
							<input type="text" name="subject" maxlength="100" class="form-control" value="${dto.subject}">
						</td>
					</tr>
	
					<tr>
						<td>공지여부</td>
						<td> 
							<p><input type="checkbox" name="notice" value="1" ${dto.notice==1 ? "checked='checked' ":"" }> <label>공지</label></p>
						</td>
					</tr>
	
					<tr> 
						<td>작성자</td>
						<td> 
							<p>${sessionScope.member.userNickname}</p>
						</td>
					</tr>
					
					<tr> 
						<td valign="top">내&nbsp;&nbsp;&nbsp;&nbsp;용</td>
						<td> 
							<textarea name="content" class="form-control">${dto.content}</textarea>
						</td>
					</tr>
				</table>
					
				<table class="table">
					<tr> 
						<td align="center">
							<button type="button" class="btn" onclick="sendOk();">${mode=='update'?'수정완료':'등록하기'}</button>
							<button type="reset" class="btn">다시입력</button>
							<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/notice/list.do?size=${size}';">${mode=='update'?'수정취소':'등록취소'}</button>
							<input type="hidden" name="size" value="${size}">
							<c:if test="${mode=='update'}">
								<input type="hidden" name="id" value="${dto.id}">
								<input type="hidden" name="page" value="${page}">
							</c:if>
						</td>
					</tr>
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
