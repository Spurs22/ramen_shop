<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<table>
	<c:forEach var="vo" items="${listReply}">
		<tr>
			<td>
				<span>${vo.nickname}</span><span>(${vo.created_date})</span>
			</td>
			<td>
				
			</td>		
		</tr>
		<tr>
			<td colspan="2">
				<span>${vo.content}</span>
			</td>
		</tr>
	</c:forEach>
</table>