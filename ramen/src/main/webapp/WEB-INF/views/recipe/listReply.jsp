<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<table>
	<c:forEach var="vo" items="${listReply}">
		<tr>
			<td>
				<span>${vo.nickname}</span><span>(${vo.createdDate})</span>
			</td>
			<td>
				<c:choose>
					<c:when test="${sessionScope.member.userNickname == vo.nickname || sessionScope.member.userNickname == 'admin'}">
						<span class='deleteReply' data-replyNum='${vo.id}'>삭제</span>
					</c:when>
					<c:otherwise>
						<span>삭제</span>
					</c:otherwise>
				</c:choose>
			</td>		
		</tr>
		<tr>
			<td colspan="2">
				<span>${vo.content}</span>
			</td>
		</tr>
	</c:forEach>
</table>