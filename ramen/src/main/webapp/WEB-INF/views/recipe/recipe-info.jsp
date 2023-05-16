<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">

	<style>
		tr > td {text-align: center;}
		tr > th {background-color: #DFE2E6; }
		.content-table > tr {padding: 15px;}
		
		.content-text {
			width: 100%; min-height: 200px; padding: 10px;
			resize: none;
			border-radius: 5px;
		}
		
		.content-text:focus {
 			 outline: none;
		}
		
		.pnbtn {
			color: black;
			text-decoration: none;
		}
		
		.pnbtn:hover {
			cursor: pointer;
		}
		
		.pnbtn:active {
			color: #777;
		}
	</style>
</head>
<script>
    let menuIndex = 3
</script>
<script>
    $(function(){
   	   $(".btnSendRecipeLike").click(function(){
   	      const $i = $(this).find("i");
   	      let isNoLike = $i.css("color") == "rgb(0, 0, 0)";
   	      let msg = isNoLike ? "게시글에 공감하십니까 ?" : "게시글 공감을 취소하시겠습니까 ?";      
   	      
   	      if(! confirm(msg)){
   	         return false;
   	      }
   	      
   	      let url = "${pageContext.request.contextPath}/recipe/like-recipe.do";
   	      let id = "${dto.id}";
   	      let qs = "id=" + id + "&isNoLike=" + isNoLike;
   	      
   	      const fn = function(data){
   	         let state = data.state;
   	         if(state === "true"){
   	            let color = "black";
   	            if( isNoLike ){
   	               color = "blue";
   	            }
   	            $i.css("color", color);
   	            
   	            let count = data.boardLikeCount;
   	            $("#boardLikeCount").text(count);
   	         } else if(state === "liked"){
   	            alert("좋아요는 한번만 가능합니다.");            
   	         }
   	      };
   	      
   	      ajaxFun(url, "post", qs, "json", fn);
   	   });
   	});

</script>
<body>
<div class="whole-container">

	<header>
		<jsp:include page="/WEB-INF/views/fragment/menubar.jsp"/>
	</header>

	<div class="main-container shadow-lg">
		<div class="content-container">
			<table class="content-table" style="width: 100%">
				<tr>
					<th style="width: 60%; padding-left: 20px">
						${dto.nickname}
					</th>
					<th style="text-align: right">
						조회수 : ${dto.hitCount} 회
					</th>
					<th style="text-align: right; padding-right: 20px">
						<button type="button" class="btn btnSendRecipeLike" title="좋아요"> <i class="fa-solid fa-heart" style="color:${isUserLike?'red':'black'}"></i>&nbsp;&nbsp;<span id="recipeLikeCount">${dto.recipeLikeCount}</span></button>
					</th>
				</tr>
				<tr>
					<td colspan=3>
						${dto.subject}
					</td>
				</tr>
				<tr>
					<td colspan=3>
						<img src="${pageContext.request.contextPath}/resource/picture/1.png">
					</td>
				</tr>
				<tr>
					<td colspan=3>
						<c:forEach var="recipe" items="${list}">
							${recipe.name} ${recipe.quantity} 개 <br> 
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td colspan=3 style="text-align: left;">
						<textarea class="content-text" readonly="readonly">${dto.content}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan=2 style="text-align: left">
						<c:choose>
		                     <c:when test="${sessionScope.member.nickname==dto.nickname}">
		                        <button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/recipe/update-recipe.do?id=${dto.id}';">수정</button>
		                     </c:when>
		                     <c:otherwise>
		                        <button type="button" class="btn" disabled="disabled">수정</button>
		                     </c:otherwise>
		                  </c:choose>
						<c:choose>
		                      <c:when test="${sessionScope.member.nickname==dto.nickname || sessionScope.member.userId=='admin'}">
		                         <button type="button" class="btn" onclick="deleteBoard();">삭제</button>
		                      </c:when>
		                      <c:otherwise>
		                         <button type="button" class="btn" disabled="disabled">삭제</button>
		                      </c:otherwise>
		                   </c:choose>
					</td>
					<td>
						${dto.createdDate}
					</td>
				</tr>
				<tr>
					<td style="text-align: left;">
						<c:if test="${not empty preReadDto}">
							<a class="pnbtn" href="${recipeUrl}${recipeUrl.contains('keyword') ? '&' : '?'}id=${preReadDto.id}"><i class="fa-solid fa-caret-left"></i>&nbsp;${preReadDto.subject}</a>
						</c:if>
					</td>
					<td>
					</td>
					<td style="text-align: right;">
						<c:if test="${not empty nextReadDto}">
							<a class="pnbtn" href="${recipeUrl}${recipeUrl.contains('keyword') ? '&' : '?'}id=${nextReadDto.id}">${nextReadDto.subject}&nbsp;<i class="fa-solid fa-caret-right"></i></a>
						</c:if>	
					</td>
				</tr>
			</table>
			
		</div>
	</div>
</div>

<script>
    $(document).ready(function () {
        selectMenu(menuIndex)
    })
</script>
</body>
</html>
