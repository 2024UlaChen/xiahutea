document.addEventListener("DOMContentLoaded", function () {
    let nowLocation = window.location.pathname;

    let memberAsideUl = document.querySelectorAll(".member-list li a");
    memberAsideUl.forEach((el) => {
        if (nowLocation.indexOf(el.getAttribute("href")) !== -1) {
            el.closest("li").classList.add("member-list-active");
            el.style.color = "#73B4BA";
        } else {
            el.addEventListener("mouseover", function () {
                el.style.color = "#FAC792";
                el.closest("li").style.backgroundColor = "#F8F9FA";
                el.closest("li").style.borderLeft = "solid 5px #FAC792";
            });
            el.addEventListener("mouseout", function () {
                el.style.color = "initial";
                el.closest("li").style.backgroundColor = "white";
                el.closest("li").style.borderLeft = "solid 5px white";
            })
        }
    })

    // 載具
    const memberCarrierBarcode = document.querySelector("#memberCarrierBarcode");
    const memberCarrierText = document.querySelector("#memberCarrierText");
    let memberCarrierTextValue = "/XXX-AAA";
    memberCarrierText.innerText = memberCarrierTextValue;

    function getCarrier() {
        JsBarcode(memberCarrierBarcode, memberCarrierTextValue, {
            displayValue: false
        });
    };
    getCarrier();
});

const editMemberCarrier = document.querySelector(".memberCarrierEdit");


