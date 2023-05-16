<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>spring</title>
<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>

<style type="text/css">
body {
  background-color: #FFFFFF;
  color: #FFFFFF;
  font-family: Arial, sans-serif;
}

.members-form {
  max-width: 360px;
  margin: 0 auto;
  background: #fefeff;
  padding: 30px 25px;
  box-shadow: 0 0 15px 0 rgba(2, 59, 109, 0.1);
}

.members-form .row {
  margin-bottom: 1.5rem;
}

.members-form label {
  display: block;
  font-weight: 500;
  margin-bottom: 0.5rem;
  font-family: Verdana, sans-serif;
  color: #023b6d;
}

.members-form input {
  display: block;
  width: 100%;
  padding: 7px 5px;
}

.members-form button {
  padding: 8px 30px;
  font-size: 15px;
  width: 97%;
  background-color: #FFBB00;
  color: #FFFFFF;
  border: none;
  cursor: pointer;
}

.members-form button:hover {
  background-color: #FF9900;
}

.members-message {
  margin: 0 auto;
  padding: 20px 5px;
}

.members-message p {
  color: #FFBB00;
}

.text-center {
  text-align: center;
}

.btit{
  color: #000000;
  text-align: center;
}
</style>

<script type="text/javascript">
function sendLogin() {
    const f = document.loginForm;

	let str = f.email.value;
    if(!str) {
        alert("이메일을 입력하세요. ");
        f.email.focus();
        return;
    }

    str = f.pwd.value;
    if(!str) {
        alert("패스워드를 입력하세요. ");
        f.pwd.focus();
        return;
    }

    f.action = "${pageContext.request.contextPath}/member/login_ok.do";
    f.submit();
    
    
    
}
</script>

</head>
<body>

<header>
   
</header>
	
<main>
	<div class="container body-container">
		<div class="body-title">
			<h2><i class="fa-solid fa-lock"></i> Login </h2>
		</div>
		
		<div class="body-main">
			<div style="margin: 0 -15px 50px -15px"></div>
			<form name="loginForm" method="post">
				<div class="members-form">
					<div class="row text-center">
  						<h3 class = "btit" >오늘도 반갑습니다!</h3>
					</div>
					<div class="row">
						<label for="login-userId"> </label>
						<input name="email" type="text" class="form-control" id="login-userId" placeholder="EMAIL">
					</div>
					<div class="row">
						<label for="login-password"> </label>
						<input name="pwd" type="password" class="form-control" id="login-password" autocomplete="off"
							placeholder="PASSWORD">
					</div>
					<div class="row text-center">
						<button type="button" class="btn btn-primary" onclick="sendLogin();">Login</button>
					</div>
					<p class="text-center">
						<a href="${pageContext.request.contextPath}/member/member.do" >회원가입</a> <span>|</span>
						<a href="${pageContext.request.contextPath}/">아이디 찾기</a> <span>|</span>
						<a href="${pageContext.request.contextPath}/">패스워드 찾기</a>
					</p>
				</div>
			</form>
			<div class="members-message">
				<p class="text-center">
					${message}
				</p>
			</div>
		</div>
	</div>
</main>

</body>
</html>