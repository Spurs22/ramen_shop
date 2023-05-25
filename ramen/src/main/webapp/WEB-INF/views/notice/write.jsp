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
<style type="text/css">

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
			<main>
			  <div class="container">
			  	<div class="body-container">
			  	  <div class="body-title">
			  	  	<h3><i class="bi bi-clipboard-check"></i>공지사항</h3>
			  	  </div>
			  	  
			  	  <div class="body-main">
			  	    <form name="noticeForm" method="post" enctype="multipart/form-data">
			  	      <table class="table write-from mt-5">
			  	        <tr>
			  	          <td class="table-light col-sm-2" scope="row">제목</td>
			  	          <td>
			  	            <input type="text" name="subject" class="form-control" value="${dto.subject}">
			  	          </td>
			  	        </tr>
			  	        
			  	        <tr>
			  	          <td class="table-light col-sm-2" scope="row">공지여부</td>
			  	          <td>
			  	            <input type="checkbox" class="form-check-input" name="notice" id="notice" value="1" ${dto.notice == 1 ? "checked='checked' ":""}>
			  	            <label class="form-check-label" for="notice">공지</label>
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
			  	      
			  	      <table class="table table-boarderless">
			  	        <tr>
			  	          <td class="text-center">
			  	            <button type="button" class="btn btn-dark" onclick="sendOk();">${mode=='update'?'수정완료':'등록하기'}&nbsp;<i class="bi bi-check2"></i></button>
			  	            <button type="reset" class="btn btn-light">다시입력</button>
			  	            <button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/notice/list.do';">
			  	              ${mode=='update'?'수정취소':'등록취소'}&nbsp;<i class="bi bi-x"></i>
			  	            </button>
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
