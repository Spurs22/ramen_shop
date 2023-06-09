<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix = "c" uri ="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">
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
  min-height: 100px; 
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

</style>
</head>
<body>
<div class="whole-container">

	<header>
		<jsp:include page="/WEB-INF/views/fragment/menubar.jsp"/>
	</header>

	<div class="main-container shadow-lg">
		<div class="content-container">
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
						<button type="button" class="btn btn-secondary" onclick="location.href='${pageContext.request.contextPath}/member/pwd.do?mode=update'">내 정보변경</button>
					</div>
          			<div class="row">
						<button type="button" class="btn btn-secondary" onclick="location.href='${pageContext.request.contextPath}/member/delete.do?mode=delete'">회원탈퇴</button>
					</div>
					<div class="row">
						<button type="button" class="btn btn-secondary" onclick="location.href='${pageContext.request.contextPath}/';">취소하기</button>
					</div>
				</form>
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