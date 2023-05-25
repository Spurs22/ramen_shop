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
		<span>사업자등록번호 201-81-21515</span>
		<span>(주)스타벅스 코리아 대표이사 이석구</span>
		<span>TEL : 02) 3015-1100 / FAX : 02) 3015-1106</span>
		<span>개인정보 책임자 : 강기원</span>
	</div>

	<p class="copyright">
		&copy; <span class="this-year"></span> Starbucks Coffee Company. All Rights Reseved.
	</p>
</div>
