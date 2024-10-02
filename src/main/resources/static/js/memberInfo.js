const memberInfoName = document.querySelector("#memberInfoName");
const memberInfoMoney = document.querySelector("#memberInfoMoney");
const memberInfoPhone = document.querySelector("#memberInfoPhone");
const memberInfoBirthday = document.querySelector("#memberInfoBirthday");
const memberInfoSex = document.querySelector("#memberInfoSex");
const memberInfoEmail = document.querySelector("#memberInfoEmail");

const DEFAULT_IMG = "img/userIcons.jpg";
//ABOUT  IMG

const uploadImg = document.querySelector("#uploadImg");
const photoBlock = document.querySelector(".memberPhoto");
const memberPhotoInput = document.querySelector("#memberPhotoInput");
const asidePhoto = document.querySelector("#asidePhoto");
const asideName = document.querySelector("#asideName");
// BTN
const memberInfoSaveBtn = document.querySelector("#memberInfoSave");

// TIP
const emailTip = document.querySelector("#emailTip");

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
            uploadImg.setAttribute("src", `${fileData}`);
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

function changeDateFormat(date) {
    const hyphen = "-";
    const slash = "/";
    if (date === null)
        return "";
    if (date.includes(hyphen))
        return date.replaceAll(hyphen, slash);
    if (date.includes(slash))
        return date.replaceAll(slash, hyphen);
}

document.addEventListener("DOMContentLoaded", function () {
    const sessionDetail = JSON.parse(sessionStorage.getItem("memberData"));
    fetch(`member`, {
        method: "POST",
        // headers: {'Content-Type': 'application/json'},
    }).then(res => res.json())
        .then(data => {
            console.log(data);
            if (data.successful) {
                memberInfoName.value = nullToEmpty(data.nickname);
                memberInfoMoney.value = nullToEmpty(data.customerMoney);
                memberInfoPhone.value = data.customerPhone;
                memberInfoBirthday.value = changeDateFormat(data.birthday);
                memberInfoSex.value = data.sex;
                memberInfoEmail.value = nullToEmpty(data.customerEmail);

                let userImg = (nullToEmpty(data.customerImg) === "") ? DEFAULT_IMG : `data:image/png;base64,${data.customerImg}`;
                uploadImg.setAttribute("src", userImg);
                photoBlock.style.backgroundImage = `url(${userImg})`;
                asidePhoto.setAttribute("src", userImg);
                asideName.innerText = nullToEmpty(data.nickname);
            }
        })    // asidePhoto.src = fileData;
})

function checkIsEmpty(...args) {
    let result = false;
    args.forEach((item) => {
        if (item.value.trim().length === 0) {
            result = true;
            return result;
        }
    })
    return result;
}

memberInfoSaveBtn.addEventListener("click", function () {
    console.log(uploadImg.getAttribute("src"));
    console.log(memberInfoSex.value);
    console.log(memberInfoBirthday.value);

    if (checkIsEmpty(memberInfoName, memberInfoBirthday, memberInfoSex, memberInfoEmail)) {
        Swal.fire("任一資料不可為空，請再確認", "", "error");
    }else{

    }

})