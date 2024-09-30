const photoBlock = document.querySelector(".memberPhoto");
const memberPhotoInput = document.querySelector("#memberPhotoInput");
const asidePhoto = document.querySelector("#asidePhoto");
let uploadImg;
photoBlock.addEventListener("click", function () {
    memberPhotoInput.click();
})

memberPhotoInput.addEventListener("change", function (event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function (event) {
            const fileData = event.target.result;
            photoBlock.style.backgroundImage = `url(${fileData})`;
        };
        reader.readAsDataURL(file);
    }
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


document.addEventListener("DOMContentLoaded", function () {
    const sessionDetail = JSON.parse(sessionStorage.getItem("memberData"));
    fetch(`member/` + sessionDetail.data.customerId)
        .then(res => res.text())
        .then(data => {
            console.log(data);
        })    // asidePhoto.src = fileData;
})