const memberInfoName = document.querySelector("#memberInfoName");
const memberInfoMoney = document.querySelector("#memberInfoMoney");
const memberInfoPhone = document.querySelector("#memberInfoPhone");
const memberInfoBirthday = document.querySelector("#memberInfoBirthday");
const memberInfoSex = document.querySelector("#memberInfoSex");

//ABOUT  IMG
const photoBlock = document.querySelector(".memberPhoto");
const memberPhotoInput = document.querySelector("#memberPhotoInput");
const asidePhoto = document.querySelector("#asidePhoto");

// BTN
const memberInfoSaveBtn = document.querySelector("#memberInfoSave");


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


function nullToEmpty(data) {
    return (data == null) ? "" : data;
}
function changeDateFormat(date){
    const hyphen ="-";
    const slash = "/";
    if(date.includes(hyphen))
        return date.replaceAll(hyphen,slash);
    if(date.includes(slash))
        return date.replaceAll(slash,hyphen);
}
document.addEventListener("DOMContentLoaded", function () {
    const sessionDetail = JSON.parse(sessionStorage.getItem("memberData"));
    fetch(`member`, {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
    }).then(res => res.json())
        .then(data => {
            console.log(data);
            if (data.successful) {
                memberInfoName.value = nullToEmpty(data.nickname);
                memberInfoMoney.value = data.customerMoney;
                memberInfoPhone.value = data.customerPhone;
                memberInfoBirthday.value = changeDateFormat(data.birthday);
                memberInfoSex.value = data.sex;
                photoBlock.style.backgroundImage=`url(${data.customerImg})`;
            }
        })    // asidePhoto.src = fileData;
})

memberInfoSaveBtn.addEventListener("click",function(){

})