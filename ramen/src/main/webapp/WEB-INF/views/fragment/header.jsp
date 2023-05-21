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

	.header-top {
		margin-bottom: 20px;
	}
</style>
</head>
	<div class="header-top">
		<div class="header-left">
			&nbsp;
		</div>
		<div class="header-right">
			<c:if test="${member != null}">
<%--				<div style="margin-right: 10px">--%>
<%--					<span style="font-weight: 650">${member.nickName}</span> 님 환영합니다!--%>
<%--				</div>--%>
			</c:if>

            <c:if test="${empty sessionScope.member}">
                <a href="${pageContext.request.contextPath}/member/login.do" title="로그인"><i class="fa-solid fa-arrow-right-to-bracket"></i></a>
				&nbsp;
                <a href="${pageContext.request.contextPath}/member/member.do" title="회원가입"><i class="fa-solid fa-user-plus"></i></a>
            </c:if>
            <c:if test="${not empty sessionScope.member}">
            	<a href="${pageContext.request.contextPath}/member/pwd.do?mode=update" title="내정보수정"><i class="fa-solid fa-user-gear"></i></a>
            	&nbsp;
				<a href="${pageContext.request.contextPath}/member/logout.do" title="로그아웃"><i class="fa-solid fa-arrow-right-from-bracket"></i></a>
            	<a href="${pageContext.request.contextPath}/cart/list.do" title="장바구니"><i class="fa-solid fa-cart-shopping"></i></a>
            </c:if>
            <c:if test="${sessionScope.member.userRoll == 1}">
            	&nbsp;
				<a href="#" title="관리자"><i class="fa-solid fa-gear"></i></a>
            </c:if>
		</div>
	</div>
