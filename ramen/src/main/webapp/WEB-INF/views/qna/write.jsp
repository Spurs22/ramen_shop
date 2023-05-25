<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/board2.css" type="text/css">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

	<style type="text/css">
	.body-main { max-width: 800px; padding-top: 15px; }

	.table-form td { padding: 7px 0; }
	.table-form p { line-height: 200%; }
	.table-form tr:first-child { border-top: 2px solid #212529; }
	.table-form tr > td:first-child { width: 110px; text-align: center; background: #f8f8f8; }
	.table-form tr > td:nth-child(2) { padding-left: 10px; }
	
	.table-form input[type=text], .table-form input[type=file], .table-form textarea { width: 96%; }
	</style>
</head>
<script type="text/javascript">
    let menuIndex = 4
    
    function sendOk() {
        const f = document.boardForm;
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

        f.action = "${pageContext.request.contextPath}/qna/${mode}_ok.do";
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
			    
			    <div class="body-main">
					<form name="boardForm" method="post">
						<table class="table write-form mt-5">
							<tr> 
								<td class="table-light col-sm-2" scope="row">제목</td>
								<td> 
									<input type="text" name="subject" class="form-control" value="${dto.subject}">
								</td>
							</tr>
							
							<tr> 
								<td class="table-light col-sm-2" scope="row">작성자명</td>
								<td> 
									<p class="form-control-plaintext">${dto.nickname}</p>
								</td>
							</tr>
							
							<tr> 
								<td class="table-light col-sm-2" scope="row">내용</td>
								<td> 
									<textarea name="content" id="content" class="form-control">${dto.content}</textarea>
								</td>
							</tr>
						</table>
						
						<table class="table table-borderless">
							<tr> 
								<td class="text-center">
									<button type="button" class="btn btn-dark" onclick="sendOk();">${mode=='update'?'수정완료':(mode=='reply'? '답변완료':'등록하기')}</button>
									<button type="reset" class="btn btn-light">다시입력</button>
									<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/qna/list.do';">${mode=='update'?'수정취소':(mode=='reply'? '답변취소':'등록취소')}</button>
									<c:if test="${mode=='update'}">
										<input type="hidden" name="id" value="${dto.id}">
										<input type="hidden" name="page" value="${page}">
									</c:if>
									<c:if test="${mode=='reply'}">
										<input type="hidden" name="groupNum" value="${dto.groupNum}">
										<input type="hidden" name="orderNo" value="${dto.orderNo}">
										<input type="hidden" name="depth" value="${dto.depth}">
										<input type="hidden" name="parent" value="${dto.id}">
										<input type="hidden" name="page" value="${page}">
									</c:if>
								</td>
							</tr>
						</table>
					</form>
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
