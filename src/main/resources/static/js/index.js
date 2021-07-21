let productMenu = document.getElementById("product_menu");
let productMenuIcon = document.getElementById("product_menu__icon");
let searchInput = document.getElementById("s");

let linkSearch="/all-vinyl-product?sortOption=vinylIDdesc";

productMenu.onmouseover = function () {
    productMenuIcon.classList.add("fa-angle-up");
    productMenuIcon.classList.remove("fa-angle-down");
}

productMenu.onmouseout = function () {
    productMenuIcon.classList.remove("fa-angle-up");
    productMenuIcon.classList.add("fa-angle-down");
}

let blogMenu = document.getElementById("blog_menu");
let blogMenuIcon = document.getElementById("blog_menu__icon");

blogMenu.onmouseover = function () {
    blogMenuIcon.classList.add("fa-angle-up");
    blogMenuIcon.classList.remove("fa-angle-down");
}

blogMenu.onmouseout = function () {
    blogMenuIcon.classList.remove("fa-angle-up");
    blogMenuIcon.classList.add("fa-angle-down");
}

var counter = 1;
if (counter === 1) {
    document.getElementById('radio1').checked = true;
}

setInterval(function () {
    counter++;
    if (counter > 2) {
        counter=1
    }
    document.getElementById('radio'+counter).checked = true;
},5000);

function setAction() {
    if (searchInput.value!=='') {
        var search = searchInput.value;
        while (search.charAt(search.length - 1) === ' ') {
            search = search.substring(0,search.length-1);
        }
        linkSearch = "/all-vinyl-product?s="+search+"&sortOption=vinylIDdesc";
    } else {
        linkSearch = "/all-vinyl-product?sortOption=vinylIDdesc";
    }
}

function goToLocation() {
    setAction();
    location = linkSearch;
}

function searchEnter(e) {
    switch (e.keyCode) {
        case 13:
            goToLocation();
            break;
    }
}