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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">
<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>

<style type="text/css">
.main-container {
	padding-top: 0;
	padding-bottom: 0; 
}

.members-form {
  max-width: 360px;q
  margin: 0 auto;
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

.members-message {
  margin: 0 auto;
  padding: 20px 5px;
}

.members-message p {
  color: black;
}

.text-center {
  text-align: center;
}

.btit{
  color: #000000;
  text-align: center;
}
.divider {
  margin: 0;
  padding: 0;
  color: #333;
}

.link-btn {
  display: inline-block;
  padding: 5px 10px;
  margin: 0 0 0 5px;
  font-size: 14px;
  color: #333;
  text-decoration: none;
  border: none;
  background-color: transparent;
  cursor: pointer;
  transition: color 0.3s;
}

.link-btn:first-child {
  margin-left: 0;
}
</style>

<script type="text/javascript">
function sendLogin() {
	
    const f = document.loginForm;

    const emailInput = f.email.value.trim();
    if (!validateEmail(emailInput)) {
        alert("올바른 이메일 주소를 입력해주세요.");
        f.email.focus();
        return;
    }
 
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

	function validateEmail(email) {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return emailRegex.test(email);
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
	
<main>
	<div class="container body-container">
		<div class="body-main">
			<div style="margin: 0 -15px 50px -15px"></div>
			<form name="loginForm" method="post">
				<div class="members-form">
					<div class="row text-center">
  						<h3 class = "btit" ><i class="fa-solid fa-lock"></i> LOGIN</h3>
					</div>
					<div class="row">
						<label for="login-userId"> </label>
						<input name="email" type="text" class="form-control" id="login-userId" placeholder="이메일">
					</div>
					<div class="row">
						<label for="login-password"> </label>
						<input name="pwd" type="password" class="form-control" id="login-password" autocomplete="off"
							placeholder="비밀번호">
					</div>
					<div class="row text-center">
						<button type="button" class="btn btn-secondary" onclick="sendLogin();">Login</button>
					</div>
					<p class="text-center">
					 <p class="text-center">
						  <span style="display: flex; justify-content: center;">
						      <a href="${pageContext.request.contextPath}/member/member.do" class="link-btn">회원가입</a>
						  </span>
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
		</div>
	</div>
</div>
<footer>
	<jsp:include page="/WEB-INF/views/fragment/footer.jsp"/>
</footer>
</body>
</html>