<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<head>
    <style>
        * {
            box-sizing: border-box;
        }

        .menubar {
            /*background: #bebebe;*/
            width: 100%;
            height: 50px;
            display: flex;
            flex-direction: row;
            text-align: center;
            align-items: end;
            gap: 2px;
        }
        .menubar-item {
            /*vertical-align: bottom;*/
            height: 70%;
            /*width: 110px;*/
            /*background: #f6e5cc;*/
            padding: 6px 15px 0px 15px;
            border: 1px solid #DFE2E6;
            border-bottom: none;
            border-radius: 8px 8px 0 0;
            background: #eeeeee;
            /*padding-top: 6px;*/
            font-size: 14px;
        }

        .menubar-item:hover:not(.selected-menu) {
            filter: brightness(80%);
            background: #eeeeee;
            transition: 0.5s;
            height: 80%;
        }

        .menubar-item:hover {
            cursor: pointer;
        }

        .logo {
            margin-top: 40px;
            width: 100%;
            height: 50px;
            margin-bottom: 55px;
            cursor: pointer;
        }

        .selected-menu {
            /*border-bottom: white 5px solid;*/
            z-index: 3;
            height: 90%;
            background: white;
        }

        .header-menu {
            width: 100%;
            display: flex;
            flex-direction: row;
            justify-content: end;
            height: 80px;
            gap: 15px;
        }
        .header-menu div {
            height: 25px;
        }

        .header-menu div:hover {
            cursor: pointer;
        }

    </style>
</head>


<!--    <img class="logo" src="../../static/picture/logo.png">-->
   <div class = "header-right">
       <jsp:include page="/WEB-INF/views/fragment/header.jsp" />
   </div>

    <div class="logo" onclick="location.href='${pageContext.request.contextPath}/home/'">
        <img style="height: 100%; margin: auto; display: block" src="${pageContext.request.contextPath}/resource/picture/logo2.png">
    </div>
    <div class="menubar">
        <div class="menubar-item" onclick="location.href='${pageContext.request.contextPath}/home/'">
            <div>
                메인
            </div>
            <div class="menubar-submenu">

            </div>
        </div>

        <div class="menubar-item" onclick="location.href='${pageContext.request.contextPath}/product/list'">
            <span>
                상품
            </span>
        </div>
        <div class="menubar-item" onclick="location.href='${pageContext.request.contextPath}/recipe/list.do'">
            <span>
                레시피
            </span>
        </div>

        <div class="menubar-item" onclick="location.href='${pageContext.request.contextPath}/notice/list.do'">
            <span>
                고객센터
            </span>
        </div>
        <c:if test="${sessionScope.member.userRoll == 0}">
            <div class="menubar-item" onclick="location.href='${pageContext.request.contextPath}/mypage/main.do'">
                <span>
                    마이페이지
                </span>
            </div>
        </c:if>
        <c:if test="${sessionScope.member.userRoll == 1}">
            <div class="menubar-item" onclick="location.href='${pageContext.request.contextPath}/admin/main.do'">
                <span>
                    관리자페이지
                </span>
            </div>
        </c:if>

    </div>

<!--<script>-->

<!--    let menuItems = document.getElementsByClassName('menubar-item');-->

<!--    function clickMenu(menu) {-->

<!--        for (const menuItem of menuItems) {-->
<!--            menuItem.classList.remove('selected-menu')-->
<!--        }-->

<!--        console.log(menu.innerHTML);-->
<!--        menu.classList.add('selected-menu')-->
<!--    }-->

<!--</script>-->
