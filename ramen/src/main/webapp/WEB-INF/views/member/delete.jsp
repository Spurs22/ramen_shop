<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix = "c" uri ="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>

<style type="text/css">
.main-container {
	padding-bottom: 0;
	padding-top: 0;
}
body {
  background-color: #FFFFFF;
  color: #000000;
  font-family: Arial, sans-serif;
}


.members-form {
  width: 400px;
  background: #fefeff;
  padding: 50px 30px 20px;
  box-shadow: 0 0 15px 0 rgba(2, 59, 109, 0.1);
  margin: 0 auto;
}

.members-form button {
  padding: 10px 30px;
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

.form-control{
  display: block;
  width: 100%;
  padding: 6px 4px;
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

.btn {
	padding: 5px 0;
}

.message {
   color: red;
   font-size: 12px;
   margin-top: 5px;
}



</style>

<script type="text/javascript">
function sendOk() {
	const f = document.pwdForm;

	let str = f.password.value;
	if(!str) {
		alert("패스워드를 입력하세요. ");
		f.password.focus();
		return;
	}

	f.action = "${pageContext.request.contextPath}/member/delete_ok.do";
	f.submit();
}

</script>


<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
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
				<h3>회원탈퇴</h3>
			</div>
			<div class="info-box">
				<form name="pwdForm" method="post">
				    <div class ="member-row">
				       패스워드를 입력해주세요
				    </div>
					<div class="row">
						<input name="memberId" type="hidden" class="form-control" readonly="readonly"
							value="${sessionScope.member.memberId}">
					</div>
					<div class="row">
						<input name="password" type="password" class="form-control" style="width: 100%;" placeholder="패스워드">
					</div>
					<div class="row">
        		   <p class = "message"> 회원탈퇴시 개인정보 및 모든데이터는 삭제됩니다. </p>
						<button type="button" class="btn btn-secondary" onclick="sendOk();">확인</button>
						<input type="hidden" name="mode" value="${mode}">
					</div>
					<div class="row">
						<button type="button" class="btn btn-outline-secondary" onclick="javascript:location.href='${pageContext.request.contextPath}/';">취소하기</button>
					</div>
					<div class="row">
						${message}
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