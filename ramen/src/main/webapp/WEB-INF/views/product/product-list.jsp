<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kun
  Date: 2023/05/14
  Time: 2:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
</head>
<body>

<c:forEach var="post" items="${posts}">
	<div>
		<p>${post.productId}</p>
		<p>${post.writerId}</p>
		<p>${post.content}</p>
		<p>${post.createdDate}</p>
		<p>${post.hitCount}</p>
		<p>${post.rating}</p>
	</div>
</c:forEach>

</body>
</html>
