<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">

	<style>
        .main-banner {
			width: 100%;
			min-height: 200px;
			background: #ffeadd;	
			font-weight: 700;
			font-size: 30px;
			text-align: center;
			border: 1px solid #DFE2E6;
			border-radius: 5px;
        }

        .today-recipe {
            width: 100%;
            min-height: 300px;
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
				<div class="container-label">배너</div>

			</div>

			<div class="today-recipe">
				<div class="container-label">
					오늘의 레시피
				</div>
				<div class="recipe-container">
					<div class="recipe selected-recipe">
						recipe1
						<div class="rank-label"><div style="margin: auto 0">1</div></div>
					</div>
					<div class="recipe">
						recipe2
						<div class="rank-label">2</div>
					</div>
					<div class="recipe">
						recipe3
						<div class="rank-label">3</div>
					</div>
					<div class="recipe">
						recipe4
						<div class="rank-label">4</div>
					</div>
				</div>
			</div>

			<div class="main-banner">
				<div class="container-label">배너</div>
			</div>

			<div class="main-banner">
				<div class="container-label">배너</div>
			</div>

			<div class="main-banner">
				<div class="container-label">배너</div>
			</div>
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
