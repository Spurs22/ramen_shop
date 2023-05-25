<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">

	<style>
		.main-container {
			padding-bottom: 50px;
		}

        .main-banner {
			width: 100%;
			min-height: 280px;
			background: #f6f6f6;
			font-weight: 700;
			font-size: 30px;
			text-align: center;
			border: 1px solid #DFE2E6;
			border-radius: 5px;
        }

        .today-recipe {
            width: 100%;
            /*min-height: 300px;*/
            border: 1px solid #DFE2E6;
            border-radius: 5px;
			display: flex;
			flex-direction: column;
			margin-bottom: 20px;
        }

		.content-container {
			gap: 30px;
			overflow: auto;
			position: relative;
			padding: 50px;
        }

        .recipe-container {
            display: flex;
            flex-direction: row;
			gap: 10px;
			justify-content: space-between;
			align-items: end;
			width: 100%;
			flex: 1;
        }
		.recipe {
			width: 20%;
			height: 120px;
			border-radius: 5px;
			text-align: center;
			transition: 0.3s;
			position: relative;
            /*border: 1px solid #a7a7a8;*/
			background: lightgray;
		}

		/*.selected-recipe {*/
        /*    flex: 1;*/
        /*    height: 220px;*/
        /*}*/

		.recipe:hover {
            width: 200px;
            height: 220px;
            cursor: pointer;
			filter: brightness(90%);
        }

        .container-label {
			text-align: center;
			font-size: 25px;
			font-weight: 600;
			margin: 15px 0;
        }

		.rank-label {
			width: 50%;
            height: 35px;
			background: #ffffff;
			position: absolute;
			bottom: 0px;
			right: 50%;
			border-radius: 5px;
            border: 1px solid #a7a7a8;
			text-align: center;
            transform: translate(50%, 50%);
        }
        
          .header-right {
        display: flex;
        justify-content: flex-end;
        align-items: center;
    }

    .header-right a {
        margin-left: 10px;
    }

	.d-block {
		object-fit: cover;
	}
	
	.d-block:hover {
        cursor: pointer;
	}
	</style>
</head>
<script>
    let menuIndex = 1
</script>
<body>
<div class="whole-container">
	<header>	
		<jsp:include page="/WEB-INF/views/fragment/menubar.jsp"/>
	</header>

	<div class="main-container shadow-lg">
		<div class="content-container">

			<%--	내용	 --%>
			<div class="main-banner">
				<img class="w-100 h-100" src="${pageContext.request.contextPath}/resource/picture/00.jpg" style="object-fit: cover">
			</div>

			<div class="today-recipe">
				<div class="container-label">
					오늘의 레시피
				</div>

				<div id="carouselExampleIndicators" class="carousel carousel-dark slide" data-bs-ride="carousel">
					<div class="carousel-indicators">
						<button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
						<button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
						<button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
					</div>
					<div class="carousel-inner" style="height: 400px">
						<div class="carousel-item active">
							<img src="${pageContext.request.contextPath}/resource/picture/jjapaguri.png" class="d-block h-100 w-100" alt="...">
						</div>
						<div class="carousel-item">
							<img src="${pageContext.request.contextPath}/resource/picture/buldakgeti.png" class="d-block h-100 w-100" alt="...">
						</div>
						<div class="carousel-item">
							<img src="${pageContext.request.contextPath}/resource/picture/spabuldak.png" class="d-block h-100 w-100" alt="...">
						</div>
					</div>
					<button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
						<span class="carousel-control-prev-icon" aria-hidden="true"></span>
						<span class="visually-hidden">Previous</span>
					</button>
					<button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
						<span class="carousel-control-next-icon" aria-hidden="true"></span>
						<span class="visually-hidden">Next</span>
					</button>
				</div>
			</div>
		</div>
	</div>
</div>

<footer>
	<jsp:include page="/WEB-INF/views/fragment/footer.jsp"/>
</footer>


<script>
    $(document).ready(function () {
        selectMenu(menuIndex)
    })
</script>
</body>
</html>
