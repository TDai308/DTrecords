let deleteButton = document.getElementsByClassName("delete__button");
let ovalNotification = document.getElementsByClassName("oval__notification");
let NoButton = document.getElementsByClassName("button__red__with-a");
for (let i=0; i<deleteButton.length;i++) {
    deleteButton[i].addEventListener("click", function () {
        ovalNotification[i].style.display = "block";
    })
    NoButton[i+1].addEventListener("click", function () {
        ovalNotification[i].style.display = "none";
    })
}