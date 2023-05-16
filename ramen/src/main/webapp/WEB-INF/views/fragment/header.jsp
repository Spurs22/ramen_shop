<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix = "c" uri ="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<head>
<style type="text/css">
    .header-right {
        display: flex;
        justify-content: flex-end;
        align-items: center;
    }

    .header-right a {
        margin-left: 10px;
        color: #000000;
    }
</style>
</head>
	<div class="header-top">
		<div class="header-left">
			&nbsp;
		</div>
		<div class="header-right">
            <c:if test="${empty sessionScope.member}">
                <a href="${pageContext.request.contextPath}/member/login.do" title="로그인"><i class="fa-solid fa-arrow-right-to-bracket"></i></a>
				&nbsp;
                <a href="${pageContext.request.contextPath}/member/member.do" title="회원가입"><i class="fa-solid fa-user-plus"></i></a>
            </c:if>
            <c:if test="${not empty sessionScope.member}">
            	<a href="#" title="알림"><i class="fa-regular fa-bell"></i></a>
            	&nbsp;
				<a href="${pageContext.request.contextPath}/member/logout.do" title="로그아웃"><i class="fa-solid fa-arrow-right-from-bracket"></i></a>
            </c:if>
            <c:if test="${sessionScope.member.userRoll == 1}">
            	&nbsp;
				<a href="#" title="관리자"><i class="fa-solid fa-gear"></i></a>
            </c:if>
		</div>
	</div>
