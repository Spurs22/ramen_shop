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
#content {
   display: flex;
   justify-content: center;
   border: 1px solid #fff;
   height: 700px;
   width: 100%;
   background-color: #fff; 
}
.sign-up form {
   width: 50%;
   max-width: 40%;
   margin: 0 auto;
   background-color: #ffffff;
   padding: 30px;
   border-radius: 10px;
   box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.3);  
}
  .sign-up label {
   font-size: 18px;
   font-weight: bold;
   color: #333333;
  }
  
  .sign-up input[type="text"],
 
  select {
   width: 100%;
   padding: 10px;
   margin-bottom: 20px;
   border: none;
   border-bottom: 2px solid #e6e6e6;
   background-color: #f8f8f8;
   color: #333333;
  }
  
  .sign-up button[type="button"] {
   display: block;
   width: 100%;
   padding: 10px;
   border: none;
   background-color: #FF9900; 
   color: #ffffff;
   font-size: 18px;
   font-weight: bold;
   border-radius: 5px;
   cursor: pointer;
   transition: background-color 0.3s ease;
}
  
  .sign-up button[type="button"]:hover {
   background-color: #FFCD12;
  }
  
  .sign-up .error-message {
   color: red;
   font-size: 12px;
   margin-top: 5px;
  }
  
  .sign-up{
   margin-top: 75px;
   
  }
</style>

<script type="text/javascript">
function memberOk() {
	const f = document.memberForm;
	let str;

	str = f.userId.value;
	if( !/^[a-z][a-z0-9_]{4,9}$/i.test(str) ) { 
		alert("아이디를 다시 입력 하세요. ");
		f.userId.focus();
		return;
	}

	str = f.userPwd.value;
	if( !/^(?=.*[a-z])(?=.*[!@#$%^*+=-]|.*[0-9]).{5,10}$/i.test(str) ) { 
		alert("패스워드를 다시 입력 하세요. ");
		f.userPwd.focus();
		return;
	}

	if( str !== f.userPwd2.value ) {
        alert("패스워드가 일치하지 않습니다. ");
        f.userPwd.focus();
        return;
	}
	
    str = f.userName.value;
    if( !/^[가-힣]{2,5}$/.test(str) ) {
        alert("이름을 다시 입력하세요. ");
        f.userName.focus();
        return;
    }

    str = f.birth.value;
    if( !str ) {
        alert("생년월일를 입력하세요. ");
        f.birth.focus();
        return;
    }
    
    str = f.tel1.value;
    if( !str ) {
        alert("전화번호를 입력하세요. ");
        f.tel1.focus();
        return;
    }

    str = f.tel2.value;
    if( !/^\d{3,4}$/.test(str) ) {
        alert("숫자만 가능합니다. ");
        f.tel2.focus();
        return;
    }

    str = f.tel3.value;
    if( !/^\d{4}$/.test(str) ) {
    	alert("숫자만 가능합니다. ");
        f.tel3.focus();
        return;
    }
    
    str = f.email1.value.trim();
    if( !str ) {
        alert("이메일을 입력하세요. ");
        f.email1.focus();
        return;
    }

    str = f.email2.value.trim();
    if( !str ) {
        alert("이메일을 입력하세요. ");
        f.email2.focus();
        return;
    }

   	f.action = "${pageContext.request.contextPath}/member/${mode}_ok.do";
    f.submit();
}

function changeEmail() {
    const f = document.memberForm;
	    
    let str = f.selectEmail.value;
    if(str !== "direct") {
        f.email2.value = str; 
        f.email2.readOnly = true;
        f.email1.focus(); 
    }
    else {
        f.email2.value = "";
        f.email2.readOnly = false;
        f.email1.focus();
    }
}

</script>
</head>
<body>

<header>
    <jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
</header>
	
<main>
	   <div id="content" style="display: flex; justify-content: center;">
        <div class="container sign-up">
          <form action="#" method="POST">
            <div class="form-group">
              <label for="name">이름</label>
              <input type="text" id="name" name="id" placeholder="이름을 입력하세요">
              <p class="error-message"></p>
            </div>
            <div class="form-group">
              <label for="nickname">닉네임</label>
              <input type="text" id="nickname" name="nickname" placeholder="닉네임을 입력하세요">
              <p class="error-message"></p>
            </div>
            <div class="form-group">
              <label for="password">비밀번호</label>
              <input type="text" id="password" name="password" placeholder="비밀번호를 입력하세요">
              <p class="error-message"></p>
            </div>
            <div class="form-group">
              <label for="tell">전화번호</label>
              <input type="text" id="tell" name="tell" placeholder="전화번호를 입력하세요">
              <p class="error-message"></p>
            </div>
             <div class="form-group">
              <label for="tell">우편번호</label>
              <input type="text" id="tell" name="tell" placeholder="우편번호를 입력하세요">
              <p class="error-message"></p>
            </div>
                <div class="form-group">
              <label for="tell">주소1</label>
              <input type="text" id="tell" name="tell" placeholder="주소1를 입력하세요">
              <p class="error-message"></p>
            </div>
                <div class="form-group">
              <label for="tell">주소2</label>
              <input type="text" id="tell" name="tell" placeholder="주소2를 입력하세요">
              <p class="error-message"></p>
            </div>
    
            <button type="button" onclick="memberOk()">회원가입</button>
            &nbsp;&nbsp;
            <button type="button" onclick="javascript:location.href='${pageContext.request.contextPath}/';">취소하기</button>
          </form>
        </div>
        
      </div>
</main>

<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
    function daumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var fullAddr = ''; // 최종 주소 변수
                var extraAddr = ''; // 조합형 주소 변수

                // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    fullAddr = data.roadAddress;

                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    fullAddr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
                if(data.userSelectedType === 'R'){
                    //법정동명이 있을 경우 추가한다.
                    if(data.bname !== ''){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있을 경우 추가한다.
                    if(data.buildingName !== ''){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                    fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('zip').value = data.zonecode; //5자리 새우편번호 사용
                document.getElementById('addr1').value = fullAddr;

                // 커서를 상세주소 필드로 이동한다.
                document.getElementById('addr2').focus();
            }
        }).open();
    }
</script>

</body>
</html>