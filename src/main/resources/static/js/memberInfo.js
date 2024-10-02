const EMAILREGEX = /\S+@\S+\.\S+/;

const memberInfoName = document.querySelector("#memberInfoName");
const memberInfoMoney = document.querySelector("#memberInfoMoney");
const memberInfoPhone = document.querySelector("#memberInfoPhone");
const memberInfoBirthday = document.querySelector("#memberInfoBirthday");
const memberInfoSex = document.querySelector("#memberInfoSex");
const memberInfoEmail = document.querySelector("#memberInfoEmail");

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
        //顯示畫面
        reader.readAsDataURL(file);
        reader.onload = function (event) {
            const fileData = event.target.result;
            photoBlock.style.backgroundImage = `url(${fileData})`;
            uploadImg.setAttribute("src", `${fileData}`);
        };
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



document.addEventListener("DOMContentLoaded", function () {
    fetch(`member`, {
        method: "POST",
        // headers: {'Content-Type': 'application/json'},
    }).then(res => res.json())
        .then(data => {
            if (data.successful) {
                memberInfoName.value = nullToEmpty(data.nickname);
                memberInfoMoney.value = nullToEmpty(data.customerMoney);
                memberInfoPhone.value = data.customerPhone;
                memberInfoBirthday.value = data.birthday;

                memberInfoSex.value = data.sex;
                memberInfoEmail.value = nullToEmpty(data.customerEmail);
                let userImg = (nullToEmpty(data.customerImg) === "") ? "" : `data:image/png;base64,${data.customerImg}`;
                uploadImg.setAttribute("src", userImg);
                photoBlock.style.backgroundImage = `url(${userImg})`;
                asidePhoto.setAttribute("src", userImg);
                asideName.innerText = nullToEmpty(data.nickname);
            }
        })
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
    if (checkIsEmpty(memberInfoName, memberInfoBirthday, memberInfoSex, memberInfoEmail)) {
        Swal.fire("任一資料不可為空，請再確認", "", "error");
    } else if (!EMAILREGEX.test(memberInfoEmail.value)) {
        Swal.fire("email格式錯誤，請再確認", "", "error");
    } else {
        const sessionDetail = JSON.parse(sessionStorage.getItem("memberData"));
        Swal.fire({
            title: 'Updating...',
            icon: 'info',
            showConfirmButton: false,
            allowOutsideClick: false,
        });
        fetch(`member/update`, {
            method: "POST",
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({
                customerId: sessionDetail.data.customerId,
                nickname: memberInfoName.value.trim(),
                customerEmail: memberInfoEmail.value.trim(),
                birthday: memberInfoBirthday.value,
                sex: memberInfoSex.value,
            }),
        }).then(res => res.json())
            .then(data => {
                if (data.successful) {
                    if (memberPhotoInput.files.length !== 0) {
                        const formData = new FormData();
                        formData.append('customerId', sessionDetail.data.customerId);
                        formData.append('img', memberPhotoInput.files[0]);
                        fetch(`member/updateImg`, {
                            method: "POST",
                            body: formData
                        }).then(res => res.json()).then(data => {
                            if (data.successful) {
                                Swal.close();
                                console.log("img change")
                                location.reload();
                            } else {
                                console.log("img error")
                            }
                        })
                    } else {
                        console.log("img no change")
                        location.reload();
                    }
                    console.log("other change")
                } else {
                    console.log("other error")
                }
            })
    }

})