<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">

	<style>
        .rating-container {
            width: 100%;
            display: flex;
            flex-direction: row;
            align-items: center;
            border-radius: 5px;
        }

        .rating-container-bundle {
            width: 100%;
            /*margin: 20px 0;*/
            /*border-radius: 10px;*/
            border: 1px solid #b7b7b7;
			padding: 10px;
			background: #FFFFFF;
            /*padding: 0 15px;*/
            /*height: 100px;*/
            /*display: flex;*/
            flex-direction: row;
            align-items: start;
            justify-content: center;
        }

        .rating-label {
            font-size: 16px;
            font-weight: 450;
			margin-right: 15px;

            /*width: 60px;*/
            /*text-decoration-line: underline;*/
            /*text-decoration: #a8a8a8 underline 1.5px;*/
            /*background: #cbcbcb;*/
            /*border: 1px solid gray;*/
            color: #333333;
        }

        .align-top {
            justify-content: start;
            align-items: start;
        }

        #comment-btn-container {
            margin-top: 15px;
        }

        .flex-row-container-inner {
            width: 100%;
            display: flex;
            flex-direction: row;
            align-items: center;
            padding: 5%;
            gap: 35px;
        }

        .flex-col-container {
            display: flex;
            flex-direction: column;
            align-items: start;
            border-radius: 5px;
            background: rgba(246, 246, 246, 0.54);
            padding: 5%;
            gap: 15px;

        }


        .starBundle {
            display: grid;
			width: 180px;
            grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
            grid-auto-rows: 40px;
            justify-content: center;
            align-items: center;
            text-align: center;
			font-size: 20px;
			margin-left: 100px;
        }

        .rate:hover {
            cursor: pointer;
            scale: 108%;
        }
		.rate {
            color: #F0974E;
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

			<div class="container-fluid mt-5 mb-5">
				<form method="post" action=""
					  class="flex-col-container shadow align-top" name="commentForm" id="commentForm">

					<div style="height: 130px; display: flex; flex-direction: row; gap: 20px">
						<img src="${pageContext.request.contextPath}/resource/picture/1.png" style="height: 100%; aspect-ratio: 1/1 object-fit: cover; border: 1px solid black">
						<div style="display: flex; flex-direction: column; height: 100%; justify-content: center">
							<div style="font-weight: 600; font-size: 18px">상품명</div>
							<div>상품 가격</div>
							<div>구매 개수</div>
						</div>
					</div>

					<div class="comment-input" style="height: 100px; width: 100%" id="expand">
						<textarea name="content" class="w-100 h-100" style="width: 100%; padding: 10px" placeholder="리뷰를 작성해주세요."></textarea>
					</div>


					<!--            <div class="p-3 row row-cols-1 row-cols-sm-1 row-cols-md-2 row-cols-lg-2 row-cols-xl-3 gap-container">-->
					<div class="rating-container-bundle " id="rate-container">
						<div class="rating-container">
							<div class="rating-label">평점을 매겨주세요</div>
							<input type="hidden" name="taste" id="taste-rate" value="0">
							<div class="starBundle">
								<i class="fa-regular fa-star rate"></i>
								<i class="fa-regular fa-star rate"></i>
								<i class="fa-regular fa-star rate"></i>
								<i class="fa-regular fa-star rate"></i>
								<i class="fa-regular fa-star rate"></i>
							</div>
						</div>
					</div>

					<div id="comment-btn-container" class="w-100" style="text-align: right">
						<button class="btn btn-danger" type="reset" id="foldBtn" style="width: 100px; height: 45px">Cancel</button>
						<button class="btn btn-success" type="button" onclick="" style="width: 100px; height: 45px; margin-left: 5px">Submit</button>
					</div>
				</form>
			</div>

		</div>
	</div>
</div>

<script>
    $(document).ready(function () {
        selectMenu(menuIndex)
    })
</script>

<script>
    let comment = document.getElementsByClassName('comment-input')[0];
    let ratingCon = document.getElementsByClassName('rating-container-bundle')[0];
    let commentBtnCon = document.getElementById('comment-btn-container')
    let ratingBtn = document.getElementById('rating-btn');
    let ratingBtn_status = false;

    let starBundles = document.getElementsByClassName('starBundle')[0];
    let STAR_COLOR_DEFAULT = '#F0974E'
    let STAR_COLOR_HOVER = '#dc843b'
    let taste = document.getElementById('taste-rate');
    let price = document.getElementById('price-rate');
    let distance = document.getElementById('distance-rate');
    let ratingArr = [taste, price, distance];
    let commentForm = document.getElementById('commentForm');

    // let starBundle = document.getElementsByClassName('rate');
    // let rating=0, lastStarIsFull

    let rating = -1;
    let lastStarIsFull = true

    function submitComment() {

        // 평점 댓글일 때,
        if (ratingBtn_status === true) {
            for (const rate of ratingArr) {
                if (rate.value === '0') {
                    alert('점수를 입력해주세요.')
                    return
                }
            }

            if (!commentForm.content.value) {
                if (!confirm('내용이 없습니다. 평점만 등록하시겠습니까?')) {
                    return
                }
            }
        } else {
            // 일반 댓글일 때,
            // 내용이 없을 때,
            if (!commentForm.content.value) {
                alert('내용이 없습니다. 내용을 입력해 주세요.')
                commentForm.content.focus();
                return;
            } else {
                if (!confirm('평점 없이 댓글만 등록합니다.')) {
                    return
                }
            }
        }
        commentForm.submit();
    }

    function getRating() {
        for (let i = 0; i < 3; i++) {
            // result.push(rating[i] + (lastStarIsFull[i]?1:0.5))
            ratingArr[i].value = rating[i] + (lastStarIsFull[i] ? 1 : 0.5);
        }

        // alert('맛 : ' + ratingArr[0].value + ' 가격 : ' + ratingArr[1].value + ' 거리 : ' + ratingArr[2].value)
    }

	let starBundle = starBundles.getElementsByTagName('i');
	for (let i = 0; i < 5; i++) {
		starBundle[i].addEventListener('mousemove', function (e) {
			// 별 원상복구
			resetStar(starBundle)

			for (let j = 0; j <= i; j++) {
				starBundle[j].classList.replace('fa-regular', 'fa-solid')
				starBundle[j].style.color = STAR_COLOR_HOVER
			}

			let rect = starBundle[i].getBoundingClientRect();
			// 클릭한 x좌표
			let x = e.clientX - rect.left;

			if (x < rect.width / 2) {
				// 왼쪽 클릭한 경우
				starBundle[i].classList.replace('fa-star', 'fa-star-half-stroke');
			} else {
				// 오른쪽 클릭한 경우
				starBundle[i].classList.replace('fa-regular', 'fa-solid')
			}
		});

		// 마우스 땠을때 초기화
		starBundle[i].addEventListener('mouseout', function (e) {
			resetStar(starBundle)

			if (rating === -1) return;

			for (let j = 0; j <= rating; j++) {
				starBundle[j].classList.replace('fa-regular', 'fa-solid')
			}

			// lastStarIsFull이 true라면 마지막 별을 변경
			if (!lastStarIsFull) {
				starBundle[rating].classList.replace('fa-star', 'fa-star-half-stroke');
			}
		});

		// 실제로 클릭했을 때 이벤트
		starBundle[i].addEventListener('click', function (e) {
			// 현재 별 등급을 유지
			// i가 눌렸다면 i+1점
			// i-1까지 꽉찬 별, i번째 별이 반별인지 꽉찬 별인지만 적용한다.
			rating = i
			lastStarIsFull = true
			lastStarIsFull = !starBundle[i].classList.contains('fa-star-half-stroke')

			getRating();
		});
	}

	function resetStar(starBundle) {
		for (const star of starBundle) {
			star.classList.replace('fa-solid', 'fa-regular')
			star.classList.replace('fa-star-half-stroke', 'fa-star');
			star.style.color = STAR_COLOR_DEFAULT
		}
	}

</script>


</body>
</html>
