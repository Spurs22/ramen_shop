// let menuItems = document.getElementsByClassName('menubar-item');

function selectMenu(index) {

    // let menu = document.getElementById(menuName)
    let menu = document.getElementsByClassName('menubar-item');
    // for (const menuItem of menuItems) {
    //     menuItem.classList.remove('selected-menu')
    // }
    menu[index - 1].classList.add('selected-menu');
}

function clickMenu() {

}

