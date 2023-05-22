<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
	<jsp:include page="/WEB-INF/views/fragment/static-header.jsp"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">

	<style>
        hr {
            border: 2px solid gray;
        }

        table {
            width: 100%;
            text-align: center;
        }

        th {
            font-size: 15px;
            text-align: center;
            padding-top: 10px;
            padding-bottom: 10px;
            border-top: 2px solid gray;
            border-bottom: 1px solid gray;
        }


        .itemtd {
            border-bottom: 1px solid gray;
            text-align: center;
        }

        .title {
            font-weight: bold;
            font-size: 20px;
            padding-top: 20px;
        }

        .item1 {
            width: 30%;
        }

        .item2 {
            width: 20%;
        }

        td {
            text-align: left;
        }


        .final {
            width: 40%;
            height: 380px;
            padding: 20px;
            background: #fff;
            border: 1px solid #d3d3d3;
            box-sizing: border-box;
        }

        .final table tr > td {
            padding: 7px;
        }

        .final button {
            width: 100%;
            height: 40px;
            font-size: 15px;
            border-width: 1px;
            border-color: #d81818;
            border-bottom-color: #9e1212;
            background: #ed2f2f;
            color: #fff;
            box-sizing: border-box;
            margin-top: 10px;
            margin-bottom: 10px;
            border-radius: 0;
        }

        .final button:hover {
            background: #d81818;
        }

        .delivery table tr > td:nth-child(1) {
            width: 100px;
            background: #f2f2f2;
            border-bottom: 1px solid lightgray;
        }

        .delivery table tr > td {
            padding: 8px;
            text-align: center;
        }

        .postBtn {
            font-size: 10px;
            border-radius: 5px;
            width: 85px;
			border: 1px black solid;
			height: 100%;
        }

        .flex-row-container {
            display: flex;
            flex-direction: row;
            gap: 10px;
			width: 100%;
			align-content: center;
			align-items: center;
			min-height: 30px;
        }

        .delivery-label {
            width: 80px;
        }

        .tel-input {
            height: 100%;
			width: 25%;
        }

		hr {
			padding: 0;
			margin-top: 5px;
			margin-bottom: 10px;
		}

		.form-control {
			border-radius: 3px;
		}
	</style>
</head>

<body>
<div class="whole-container">

	<header>
		<jsp:include page="/WEB-INF/views/fragment/menubar.jsp"/>
	</header>

	<div class="main-container shadow-lg">
		<div class="content-container">
			<form name="orderForm" method="post">
				<p class="title"> 주문 결제</p>
				<hr>
				<table>
					<thead>
					<tr>
						<th class="item1">상품이미지</th>
						<th class="item2">품명</th>
						<th class="item2">판매가</th>
						<th class="item2">총수량</th>
						<th class="item1">소계</th>
					</tr>
					</thead>

					<tbody>
					<c:forEach var="cart" items="${list}">
					<tr>
						<td class="itemtd"><img class="product-img"
												src="${pageContext.request.contextPath}/resource/picture/${cart.picture}"
												style="height: 100px;"></td>
						<td class="itemtd">${cart.productName}</td>
						<td class="itemtd">${cart.price}</td>
						<td class="itemtd">${cart.quantity}</td>
						<td class="itemtd">
								${cart.price*cart.quantity}
							<input type="hidden" name="items" value="${cart.productId}">
						</td>
					</tr>
					</c:forEach>
				</table>
				<div style="display: flex; flex-direction: row; gap: 15px; width: 100%; margin-top: 15px">
					<div style="display: flex; flex-direction: column; width: 60%; gap: 15px; ">
						<p class="title">배송정보</p>
						<hr>
						<div class="flex-row-container">
							<div class="delivery-label ">받는 사람</div>
							<input class="form-control" type="text" name="receiveName" id="receiveName" maxlength="7" style="flex: 1">
						</div>

						<div class="flex-row-container">
							<div class="delivery-label">배송지</div>
							<input type="text" name="zip" id="zip" maxlength="7"
								   class="form-control" value="${order.postNum}"
								   readonly="readonly" style="background: #ececec; flex: 1">
							<button type="button" class="btn btn-outline-secondary postBtn" onclick="daumPostcode();">우편번호검색</button>
						</div>
						<div class="flex-row-container">
							<div class="delivery-label">도로명 /<br> 상세주소</div>
							<div style="display: flex; flex-direction: column; gap: 5px; flex: 1">
								<input type="text" name="zip1" id="zip1" maxlength="7"
									   style="background: #ececec;"
									   class="form-control" value="${order.address1}"
									   readonly="readonly">
								<input type="text" name="zip2" id="zip2" maxlength="7"
									   class="form-control" value="${order.address2}">
							</div>
						</div>

						<div class="flex-row-container">
							<div class="delivery-label">전화번호</div>

							<select name="tel1" class="form-control" style="height: 100%; flex: 1">
								<option value="">선 택</option>
								<option value="010"
								${dto.tel1=="010" ? "selected='selected'" : ""}>010
								</option>
								<option value="02"
								${dto.tel1=="02"  ? "selected='selected'" : ""}>02
								</option>
								<option value="031"
								${dto.tel1=="031" ? "selected='selected'" : ""}>031
								</option>
								<option value="032"
								${dto.tel1=="032" ? "selected='selected'" : ""}>032
								</option>
								<option value="033"
								${dto.tel1=="033" ? "selected='selected'" : ""}>033
								</option>
								<option value="041"
								${dto.tel1=="041" ? "selected='selected'" : ""}>041
								</option>
								<option value="042"
								${dto.tel1=="042" ? "selected='selected'" : ""}>042
								</option>
								<option value="043"
								${dto.tel1=="043" ? "selected='selected'" : ""}>043
								</option>
								<option value="044"
								${dto.tel1=="044" ? "selected='selected'" : ""}>044
								</option>
								<option value="051"
								${dto.tel1=="051" ? "selected='selected'" : ""}>051
								</option>
								<option value="052"
								${dto.tel1=="052" ? "selected='selected'" : ""}>052
								</option>
								<option value="053"
								${dto.tel1=="053" ? "selected='selected'" : ""}>053
								</option>
								<option value="054"
								${dto.tel1=="054" ? "selected='selected'" : ""}>054
								</option>
								<option value="055"
								${dto.tel1=="055" ? "selected='selected'" : ""}>055
								</option>
								<option value="061"
								${dto.tel1=="061" ? "selected='selected'" : ""}>061
								</option>
								<option value="062"
								${dto.tel1=="062" ? "selected='selected'" : ""}>062
								</option>
								<option value="063"
								${dto.tel1=="063" ? "selected='selected'" : ""}>063
								</option>
								<option value="064"
								${dto.tel1=="064" ? "selected='selected'" : ""}>064
								</option>
								<option value="070"
								${dto.tel1=="070" ? "selected='selected'" : ""}>070
								</option>
							</select>

							<input type="text" name="tel2" maxlength="4"
								   class="tel-input form-control" value="${dto.tel2}">

							<input type="text" name="tel3" maxlength="4"
								   class="tel-input form-control" value="${dto.tel3}">
						</div>
<%--						<button type="reset" class="btn btn-outline-dark" id="btnReset">다시입력</button>--%>
					</div>

					<div class="final">
						<p class="title">최종 결제 정보</p>
						<hr>
						<table>
							<tr>
								<td>총</td>
								<td>${dataCount}건</td>
							</tr>
							<tr>
								<td>상품금액</td>
								<td>${totalPrice}</td>
							</tr>
							<tr>
								<td>할인금액</td>
								<td>0</td>
							</tr>
							<tr>
								<td>배송비</td>
								<td>0</td>
							</tr>
							<tr>
								<td>전체주문금액</td>
								<td>${totalPrice}</td>
							</tr>
						</table>
						<br>
						<button type="button" class="btn" id="btnOrder">결제</button>
					</div>

				</div>
			</form>
		</div>
	</div>
</div>

<script>

    $(function () {

        $("#btnOrder").click(function () {
            const f = document.orderForm;

            // 이름 2-5자 체크
            let str;
            str = f.receiveName.value;
            if (!/^[가-힣]{2,5}$/.test(str)) {
                alert("이름을 다시 입력하세요.");
                f.receiveName.focus();
                return;
            }

            // 배송지 체크
            str = f.zip.value;
            if (!str) {
                alert("배송지를 입력하세요.");
                daumPostcode();
                return;
            }

            str = f.zip2.value;
            if (!str) {
                alert("상세주소를 입력하세요.");
                f.zip3.focus();
                return;
            }

            // 전화번호 체크
            str = f.tel1.value;
            if (!str) {
                alert("전화번호를 입력하세요. ");
                f.tel1.focus();
                return;
            }

            str = f.tel2.value;
            if (!/^\d{3,4}$/.test(str)) {
                alert("숫자[3-4자리]만 가능합니다. ");
                f.tel2.focus();
                return;
            }

            str = f.tel3.value;
            if (!/^\d{4}$/.test(str)) {
                alert("숫자[4자리]만 가능합니다. ");
                f.tel3.focus();
                return;
            }


            if (confirm("결제 하시겠습니까 ?")) {
                const f = document.orderForm;
                var IMP = window.IMP; // 생략가능
                IMP.init('imp00258645'); // 'iamport' 대신 부여받은 "가맹점 식별코드"를 사용
                var msg;

                IMP.request_pay({
                    pg: 'kakaopay',
                    pay_method: 'card',
                    merchant_uid: 'merchant_' + new Date().getTime(),
                    name: 'ramen 결제',
                    amount: ${totalPrice},
                    buyer_email: 'tkstpqpfldk9@naver.com',
                    buyer_name: '김지윤',
                    buyer_tel: '010-5498-3867',
                    buyer_addr: '서울시',
                    buyer_postcode: '123-456',
                }, function (rsp) {
                    if (rsp.success) {
                        //[1] 서버단에서 결제정보 조회를 위해 jQuery ajax로 imp_uid 전달하기
                        jQuery.ajax({
                            url: "/payments/complete", //cross-domain error가 발생하지 않도록 주의해주세요
                            type: 'POST',
                            dataType: 'json',
                            data: {
                                imp_uid: rsp.imp_uid
                                //기타 필요한 데이터가 있으면 추가 전달
                            }
                        }).done(function (data) {
                            //[2] 서버에서 REST API로 결제정보확인 및 서비스루틴이 정상적인 경우
                            if (everythings_fine) {
                                msg = '결제가 완료되었습니다.';
                                msg += '\n고유ID : ' + rsp.imp_uid;
                                msg += '\n상점 거래ID : ' + rsp.merchant_uid;
                                msg += '\n결제 금액 : ' + rsp.paid_amount;
                                msg += '카드 승인번호 : ' + rsp.apply_num;

                                alert(msg);
                            } else {
                                //[3] 아직 제대로 결제가 되지 않았습니다.
                                //[4] 결제된 금액이 요청한 금액과 달라 결제를 자동취소처리하였습니다.
                            }
                        });
                        //성공시 이동할 페이지
                        f.action = "${pageContext.request.contextPath}/order/order_ok.do";
                        f.submit();
                    } else {
                        msg = '결제에 실패하였습니다.';
                        msg += '에러내용 : ' + rsp.error_msg;
                        //실패시 이동할 페이지
                        location.href = "${pageContext.request.contextPath}/cart/list.do";
                        alert(msg);
                    }
                });
            }
        });

        $("#btnReset").click(function () {
            if (confirm("다시 입력 하시겠습니까 ?")) {
                return true;
           } else {
                return false;
            }
        });


    });
</script>
<script>
    $(document).ready(function () {
        selectMenu(menuIndex)
    })
</script>
<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>


<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
    function daumPostcode() {
        new daum.Postcode({
            oncomplete: function (data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var fullAddr = ''; // 최종 주소 변수
                var extraAddr = ''; // 조합형 주소 변수

                // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    fullAddr = data.roadAddress;

                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    fullAddr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
                if (data.userSelectedType === 'R') {
                    //법정동명이 있을 경우 추가한다.
                    if (data.bname !== '') {
                        extraAddr += data.bname;
                    }
                    // 건물명이 있을 경우 추가한다.
                    if (data.buildingName !== '') {
                        extraAddr += (extraAddr !== '' ? ', '
                            + data.buildingName : data.buildingName);
                    }
                    // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                    fullAddr += (extraAddr !== '' ? ' (' + extraAddr + ')'
                        : '');
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('zip').value = data.zonecode; //5자리 새우편번호 사용
                document.getElementById('zip1').value = fullAddr;

                // 커서를 상세주소 필드로 이동한다.
                document.getElementById('zip2').focus();
            }
        }).open();
    }
</script>
</body>
</html>
