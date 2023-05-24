<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
	.reply-table {
  width: 100%;
  border-collapse: collapse;
}

.reply-table td {
  padding: 10px;
}

.reply-table span {
  display: inline-block;
}

.reply-table .deleteReply {
  float: right;
  cursor: pointer;
}

.reply-table tr:nth-child(odd) {
  background-color: #f9f9f9;
}

.reply-table tr:nth-child(even) {
  background-color: #ffffff;
}

.reply-table td:nth-child(1) {
  text-align: left;
  width: 30%;
}

.reply-table td:nth-child(2) {
  text-align: right;
  width: 10%;
}

.reply-table td:nth-child(2) .deleteReply {
  margin-left: 10px;
}

.reply-table td:nth-child(2) span {
  display: none;
}

.reply-table td:nth-child(2) span:first-child {
  display: inline-block;
}

.reply-table td:nth-child(2) span:last-child {
  display: inline-block;
  font-size: 12px;
  color: #999999;
}

.reply-table td[colspan="2"] {
  text-align: left;
}

</style>

<table class="reply-table">
	<c:forEach var="vo" items="${listReply}">
		<tr>
			<td>
				<span>${vo.nickname}</span>&nbsp;<span style="font-size: 12px;">(${vo.createdDate})</span>
			</td>
			<td>
				<c:choose>
					<c:when test="${sessionScope.member.userNickname == vo.nickname || sessionScope.member.userRoll == 1}">
						<span class='deleteReply' data-replyNum='${vo.id}'>삭제</span>
					</c:when>
					<c:otherwise>
						<span style="cursor: pointer;" onclick="noAccess();">삭제</span>
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