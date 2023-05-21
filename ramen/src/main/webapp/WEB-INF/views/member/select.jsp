<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix = "c" uri ="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">

<style type="text/css">
<style type="text/css">
body {
  background-color: #FFFFFF;
  color: #000000;
  font-family: Arial, sans-serif;
}

.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh; 
}

.members-form {
  width: 400px;
  background: #fefeff;
  padding: 50px 30px 20px;
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
  color: #FFCD12;
}

.members-form input {
  display: block;
  width: 100%;
  padding: 6px 4px;
}

.members-form button {
  padding: 10px 30px;
  font-size: 15px;
  width: 100%;
  background-color: #FFBB00;
  color: #FFFFFF;
  border: none;
  cursor: pointer;
}

.members-form button:hover {
  background-color: #FF9900;
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

.btnConfirm {
  width: 100%;
  margin-bottom: 10px;
}

.cancel-btn {
  width: 100%;
    border: 2px solid #FFFFFF; 
 }
</style>
</head>
<body>
<main>
	<div class="container">
		<div class="members-form">
			<div class="members-title">
				<h3>계정설정 변경</h3>
			</div>
			<div class="info-box">
				<form name="pwdForm" method="post">
				    <div class="member-row">
				       원하는 메뉴를 선택해주세요
				    </div>
					<div class="row">
						<button type="button" class="btnConfirm" onclick="location.href='${pageContext.request.contextPath}/member/pwd.do?mode=update'">내 정보변경</button>
					</div>
          			<div class="row">
						<button type="button" class="btnConfirm" onclick="location.href='${pageContext.request.contextPath}/';">회원탈퇴</button>
					</div>
					<div class="row">
						<button type="button" class="cancel-btn" onclick="location.href='${pageContext.request.contextPath}/';">취소하기</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</main>





</body>
</html>