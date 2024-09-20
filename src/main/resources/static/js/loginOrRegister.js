// element
const toggleLink = document.querySelector("#toggleLink");

// register
toggleLink.addEventListener("click", function () {
    let toggleBlock = toggleLink.closest("div").querySelector("span");
    if (this.classList.contains("logging")) {
        toggleBlock.innerText = "還沒登入？";
        toggleLink.innerText = "立即登入";
    } else {
        toggleBlock.innerText = "沒有帳號？";
        toggleLink.innerText = "立即註冊";
    }
    this.classList.toggle("logging");
})

// input檢核
function isCheckedSuccess(target) {
    target.previousElementSibling.textContent = "";
    target.previousElementSibling.classList.remove("checkInValid");
    target.classList.remove("inputCheckInValid");
}

function isCheckedFalse(target) {
    target.previousElementSibling.classList.add("checkInValid");
    target.classList.add("inputCheckInValid");
}


function phoneValid(phone) {
    cmsQueryMemberCellphoneTxt.addEventListener("input", function () {
        if (phone.value.length === 0) {
            isCheckedSuccess(this);
        } else {
            if (isNaN(phone.value)) {
                this.previousElementSibling.textContent = "僅接受數字";
                isCheckedFalse(this);
            } else if (!phone.value.startsWith("09")) {
                this.previousElementSibling.textContent = "開頭必須是09";
                isCheckedFalse(this);
            } else {
                isCheckedSuccess(this);
            }
        }
    })
}


//DOMLOAD
document.addEventListener("DOMContentLoaded", function () {

})