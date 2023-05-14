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
            width: 150px;
            /*background: #f6e5cc;*/
            border: 1px solid black;
            border-bottom: none;
            border-radius: 8px 8px 0 0;
            background: #eeeeee;
            padding-top: 4px;
        }

        .menubar-item:hover:not(.selected-menu) {
            filter: brightness(80%);
            cursor: pointer;
            background: #eeeeee;
            transition: 0.5s;
        }

        .logo {
            height: 150px;
        }

        .selected-menu {
            /*border-bottom: white 5px solid;*/
            z-index: 3;
            height: 90%;
            background: white;
        }

    </style>
</head>


<!--    <img class="logo" src="../../static/picture/logo.png">-->

    <div class="menubar">
        <div class="menubar-item" onclick="">
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
        <div class="menubar-item" onclick="">
            <span>
                레시피
            </span>
        </div>
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
