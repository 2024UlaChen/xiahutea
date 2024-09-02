document.addEventListener("DOMContentLoaded", function () {
    let nowLocation = window.location.pathname;

    let memberAsideUl = document.querySelectorAll(".member-list li a");
    memberAsideUl.forEach((el) => {
        if (nowLocation.indexOf(el.getAttribute("href")) !== -1) {
            el.closest("li").classList.add("member-list-active");
        }
    })
});