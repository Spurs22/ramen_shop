<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Recipe</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">

	<style>
		.product-container {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            grid-auto-rows: 340px;
            padding: 20px;
            gap: 30px;
            height: 90%;
			overflow: auto;
        }

        .product-item {
            width: 100%;
            height: 100%;
            border-radius: 40px;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            align-items: start;
            gap: 5px;
            padding: 25px 30px 30px 30px;
            transition: 0.5s;
            background: #ffffff;
            border: 1px solid #DFE2E6;
        }

        .product-img {
            width: 100%;
            height: 150px;
            object-fit: cover;
            border-radius: 20px;
			border: 1px solid lightgrey;
			margin: 10px 0;
        }

        .product-item:hover {
            cursor: pointer;
            background: #e8e8e8;
            /*filter: brightness(95%);*/
        }

        .sub-menu button {
            border-radius: 5px;
        }

        .sub-menu {
            width: 100%;
            height: 40px;
            display: flex;
            flex-direction: row;
            gap: 5px;
            justify-content: space-between;
			margin-bottom: 20px;
			padding: 0 20px;
        }

		a {
			text-decoration: none;
			color: black;
		}

        .search-box {
            border: 1px solid #DFE2E6;
            border-radius: 5px;
            width: 150px;
            padding: 0 5px;
        }
        
        select:hover {cursor: pointer;}
        select option:hover {cursor: pointer;}
        
	</style>
</head>
<script>
    let menuIndex = 3
</script>
<body>
<div class="whole-container">

	<header>
		<jsp:include page="/WEB-INF/views/fragment/menubar.jsp"/>
	</header>

	<div class="main-container shadow-lg">
			<div class="sub-menu">
				<form name="radioForm" action="${pageContext.request.contextPath}/recipe/list.do" method="post">
					<div class="btn-group" role="group" aria-label="Basic radio toggle button group">
						<input type="radio" class="btn-check" name="btnradio" id="btnradio1" value="btnradio1" autocomplete="off" onclick="clickBtnradio(this)" checked>
						<label class="btn btn-outline-primary" for="btnradio1">최신순</label>
		
						<input type="radio" class="btn-check" name="btnradio" id="btnradio2" value="btnradio2" autocomplete="off" onclick="clickBtnradio(this)">
						<label class="btn btn-outline-primary" for="btnradio2">조회순</label>
						
						<input type="radio" class="btn-check" name="btnradio" id="btnradio3" value="btnradio3" autocomplete="off" onclick="clickBtnradio(this)">
						<label class="btn btn-outline-primary" for="btnradio3">좋아요순</label>
					</div>
				</form>
				
				<div>
					<button type="button" class="btn btn-success" onclick="location.href='${pageContext.request.contextPath}/recipe/write.do';">글올리기</button>
					
				</div>
				
			</div>
			
			<div class="sub-menu">
				<form name="searchForm" action="${pageContext.request.contextPath}/recipe/list.do" method="post" style="display: flex; flex-direction: row; height: 100%; width: 100%; gap: 8px;">
					<button type="button" class="btn btn-primary" onclick="location.href='${pageContext.request.contextPath}/recipe/list.do';" title="새로고침" style="height: 100%;"><i class="fa-solid fa-arrow-rotate-right"></i></button>
					<select name="condition" id="conditionval" class="form-select" style="width: 125px;" onchange="clickCondition();">
						<option value="all"  			${condition=="all"?"selected='selected'":"" }>제목+내용</option>
						<option value="nickname" 	    ${condition=="nickname"?"selected='selected'":"" }>작성자</option>
						<%--<option value="created_date"	${condition=="createdDate"?"selected='selected'":"" }>등록일</option> --%>
						<option value="subject" 		${condition=="subject"?"selected='selected'":"" }>제목</option>
						<option value="content"			${condition=="content"?"selected='selected'":"" }>내용</option>
					</select>
					<input type="text" name="keyword" id="searchInput" value="${keyword}" class="form-control" style="width: 370px;">
					<button type="button" class="btn btn-primary">검색</button>
				</form>
			</div>
			
		<div class="content-container" style="">
			<div class="product-container" id="resultForm">
				<c:forEach var="recipe" items="${list}">
					<a class="product-item shadow" href="${pageContext.request.contextPath}/recipe/recipe.do?id=${recipe.id}" style="">

						<div style="display: flex; flex-direction: row;justify-content: space-between; width: 100%">
							<div style="font-size: 14px"><i class="fa-solid fa-eye"></i> ${recipe.hitCount}</div>
							<div style="font-size: 14px;">${recipe.createdDate}</div>
						</div>

						<div style="display: flex; flex-direction: row; justify-content: space-between; width: 100%">
							<div style="font-size: 14px;">작성자 : ${recipe.nickname}</div>
							<div style="font-size: 14px"><i class="fa-solid fa-heart" style="color: red;"></i> ${recipe.recipeLikeCount}</div>
						</div>

						<img class="product-img" src="${pageContext.request.contextPath}/resource/picture/${recipe.picture == null ? "default2.png" : recipe.picture}">
						<div style="font-weight: bold; text-align: center; width: 100%; margin-top: 5px">${recipe.subject}</div>
					</a>
				</c:forEach>
			</div>
	
		</div>
	</div>
</div>

<script>
    $(document).ready(function () {
        selectMenu(menuIndex)
    })
</script>
<script type="text/javascript">
	let btnradio = document.getElementsByName('btnradio');
	
	let selectedBtnradio = 'btnradio1';
	let selectedCondition = 'all';
	
	function clickBtnradio(button) {
		selectedBtnradio = button.value
		getList()
	}

	$(document).ready(function() {
		$("#searchInput").on("input", function() {
			getList()
		});
	});
	
	function getList() {
		let keyword = $('#searchInput').val().trim();
		let condition = $('#conditionval').val();
		
		$.ajax({
			url: "${pageContext.request.contextPath}/recipe/search.do",
			type: "GET",
			dataType: "json",
			data: {btnradio: selectedBtnradio, condition: condition, keyword: keyword},
			success: function(data) {
				console.log(data);
				
				let resultForm = $('#resultForm');
				resultForm.empty();
				
				console.log(Array.isArray(data));
				
				$.each(data, function(i, list) {
					 let userCardTemplate = `
						<a class="product-item shadow" href="${pageContext.request.contextPath}/recipe/recipe.do?id=`+list.id+`" style="">

							<div style="display: flex; flex-direction: row;justify-content: space-between; width: 100%">
								<div style="font-size: 14px"><i class="fa-solid fa-eye"></i> `+list.hitCount+`</div>
								<div style="font-size: 14px;">`+list.createdDate+`</div>
							</div>
	
							<div style="display: flex; flex-direction: row; justify-content: space-between; width: 100%">
								<div style="font-size: 14px;">작성자 : `+list.nickname+`</div>
								<div style="font-size: 14px"><i class="fa-solid fa-heart" style="color: red;"></i> `+list.recipeLikeCount+`</div>
							</div>
	
							<img class="product-img" src="${pageContext.request.contextPath}/resource/picture/`+list.picture+` == null ? 'default2.png' : list.picture}">
							<div style="font-weight: bold; text-align: center; width: 100%; margin-top: 5px">`+list.subject+`</div>
						</a>
                     `;

                	 resultForm.append(userCardTemplate);
				});
			},
			error: function(xhr, status, error) {
				console.error(error);
			}
		});
	}
</script>
</body>
</html>
