<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix = "c" uri ="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">
<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<style type="text/css">
body {
  background-color: #FFFFFF;
  color: #000000;
  font-family: Arial, sans-serif;
}
.main-container {
	padding-bottom: 50px;
}

.members-form {
  width: 400px;
  background: #fefeff;
  padding: 50px 30px 20px;
  box-shadow: 0 0 15px 0 rgba(2, 59, 109, 0.1);
  margin: 0 auto;
}

.members-form .row {
  margin-bottom: 1.5rem;
  
}

.members-form label {
  display: block;
  font-weight: 500;
  margin-bottom: 0.5rem;
  font-family: Verdana, sans-serif;
  color: #FFCD12;
}

.members-form input {
  display: block;
  width: 100%;
  padding: 6px 4px;
}

.members-form button {
  padding: 10px 30px;
}

.members-title {
  text-align: center; 
  margin-bottom: 20px;
  font-family: Arial, sans-serif;
}

.member-row{
   text-align: center;
   margin-bottom: 1.5rem;  
}

.info-box {
  text-align: center;
}

.error-message {
   color: red;
   font-size: 12px;
   margin-top: 5px;
}
</style>



<script type="text/javascript">
function sendOk() {
	const f = document.pwdForm;

	str = f.email.value.trim();
	
    if (!str) {
        showErrorMsg(f.email, "이메일을 입력해주세요.");
        return;
    } else {
        hideErrorMsg(f.email);
    }

    if (!validateEmail(str)) {
        showErrorMsg(f.email, "올바른 이메일 주소를 입력해주세요.");
        return;
    } else {
        hideErrorMsg(f.email);
    }

	
	f.action = "${pageContext.request.contextPath}/member/pwdFind_ok.do";
	f.submit();
    
    function validateEmail(email) {
		 return /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(email);
	}
	
}

</script>
</head>
<body>
<div class="whole-container">

	<header>
		<jsp:include page="/WEB-INF/views/fragment/menubar.jsp"/>
	</header>

	<div class="main-container shadow-lg">
		<div class="content-container">
		
		
		<div class="members-form">
			<div class="members-title">
				<h3>임시비밀번호 발급</h3>
			</div>
			<div class="info-box">
				<form name="pwdForm" method="post">
				    <div class="member-row">
				     이메일을 입력해주세요 
				    </div>
				<div class="row">
						<input name="email" type="email" class="form-control" placeholder="이메일 입력">
					<p class="error-message"></p>
					</div>
          			<div class="row">
						<button type="button" class="btn btn-secondary" onclick="sendOk();">확인</button>
					</div>
					<div class="row">
						<button type="button" class="btn btn-secondary" onclick="location.href='${pageContext.request.contextPath}/';">취소하기</button>
					</div>
				</form>
			</div>
		</div>

		</div>
	</div>
</div>
<footer>
	<jsp:include page="/WEB-INF/views/fragment/footer.jsp"/>
</footer>
</body>
</html>