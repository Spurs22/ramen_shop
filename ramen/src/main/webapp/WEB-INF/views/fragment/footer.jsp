<%@ page contentType="text/html; charset=UTF-8" %>

<style>
    footer {
        background-color: #272727;
        border-top: 1px solid #333;
    }
    footer .inner {
        padding: 40px 0 60px 0
    }
    footer .menu {
        display: flex;
        justify-content: center;
    }
    footer .menu li {
        position: relative;
    }
    footer .menu li::before {
        content: "";
        width: 3px;
        height: 3px;
        background-color: #555;
        position: absolute;
        top: 0;
        bottom: 0;
        right: -1px;
        margin: auto;
    }
    footer .menu li:last-child::before {
        display: none;
    }
    footer .menu li a {
        color: #ccc;
        font-size: 12px;
        font-weight: 700;
        padding: 15px;
        display: block;
    }
    footer .menu li a.green {
        color: #669900
    }
    footer .btn-group {
        margin-top: 20px;
        display: flex;
        justify-content: center;
    }
    footer .btn-group .btn {
        font-size: 12px;
        margin-right: 10px;
    }
    footer .btn-group .btn:last-child {
        margin-right: 0px;
    }
    footer .info {
        margin-top: 30px;
        text-align: center;
    }
    footer .info span {
        margin-right: 20px;
        color: #999;
        font-size: 12px;
    }
    footer .info span:last-child {
        margin-right: 0;
    }
    footer .copyright {
        color: #999;
        font-size: 12px;
        text-align: center;
        margin-top: 12px;
    }
    footer .logo {
        margin: 30px auto 0;
    }
</style>

<div class="inner">
	<ul class="menu">
		<li><a href="javascript:void(0)">개인정보처리방침</a></li>
		<li><a href="javascript:void(0)">영상정보처리기기 운영관리 방침</a></li>
		<li><a href="javascript:void(0)">홈페이지 이용약관</a></li>
		<li><a href="javascript:void(0)">위치정보 이용약관</a></li>
		<li><a href="javascript:void(0)">스타벅스 카드 이용약관</a></li>
		<li><a href="javascript:void(0)">윤리경영 핫라인</a></li>
	</ul>

	<div class="btn-group">
		<a href="javascript:void(0)" class="btn btn--white">찾아오시는 길</a>
		<a href="javascript:void(0)" class="btn btn--white">신규입점제의</a>
		<a href="javascript:void(0)" class="btn btn--white">사이트 맵</a>
	</div>

	<div class="info">
		<span>사업자등록번호 111-11-11111</span>
		<span>(주)섞었더 라면 코리아 대표이사 ooo</span>
		<span>TEL : 02) 1234-5678 / FAX : 02) 1234-5678</span>
		<span>개인정보 책임자 : ooo</span>
	</div>

	<p class="copyright">
		&copy; <span class="this-year"></span> if mixed ramen Company. All Rights Reseved.
	</p>
</div>
