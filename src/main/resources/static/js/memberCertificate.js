const defaultVerifyCodeTip = "請輸入手機驗證";
const verifyCodeRegex = /^[A-Z0-9]{6}$/;

// element
const memberVerifyCodeTip = document.querySelector("#memberVerifyCodeTip");
const memberVerifyCode = document.querySelector("#memberVerifyCode");

// button
const checkVerifyCodeBtn = document.querySelector("#checkVerifyCode");
const reGetVerifyCodeBtn = document.querySelector("#reGetVerifyCode");

//DOMLOAD
document.addEventListener("DOMContentLoaded", function () {
    memberVerifyCodeTip.innerText = defaultVerifyCodeTip;
})

// input檢核
function isCheckedSuccessOrDefult(target) {
    let targetDiv = target.closest(".block");
    let targetTxt = targetDiv.closest("div").nextElementSibling;
    targetDiv.classList.remove("checkInValid");
    targetTxt.classList.remove("checkInValidTxt");
}

function isCheckedFalse(target) {
    let targetDiv = target.closest(".block");
    let targetTxt = targetDiv.closest("div").nextElementSibling;
    targetDiv.classList.add("checkInValid");
    targetTxt.classList.add("checkInValidTxt");
}

//checkVerifyCode
function verifyCodeValid(code) {
    code.addEventListener("input", function () {
        let memberVerifyCodeValue = code.value;
        if (memberVerifyCodeValue.length === 0) {
            memberVerifyCodeTip.innerText = defaultVerifyCodeTip;
            isCheckedSuccessOrDefult(code);
            return;
        }
        if (verifyCodeRegex.test(memberVerifyCodeValue) && memberVerifyCodeValue.length === 6) {
            isCheckedSuccessOrDefult(code);
            memberVerifyCodeTip.innerText = defaultVerifyCodeTip;
        } else {
            if (memberVerifyCodeValue.indexOf(" ") !== -1) {
                memberVerifyCodeTip.innerText = "不可包含空白字元";
                isCheckedFalse(code);
            } else if (memberVerifyCodeValue.length !== 6) {
                memberVerifyCodeTip.innerText = "長度須為六碼";
                isCheckedFalse(code);
            } else {
                memberVerifyCodeTip.innerText = "格式錯誤";
                isCheckedFalse(code);
            }
        }
    })
    return verifyCodeRegex.test(code.value) && code.value.length === 6;
}

verifyCodeValid(memberVerifyCode);


checkVerifyCodeBtn.addEventListener("click", function () {
    if (!verifyCodeRegex.test(memberVerifyCode.value)) {
        Swal.fire("驗證碼格式錯誤，請重新輸入", "", "error");
    } else {
        let sessionDetail = JSON.parse(sessionStorage.getItem("memberData"));
        console.log(sessionDetail);
        fetch(`member/register/check`, {
            method: "POST",
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({
                customerId: sessionDetail.customerId,
                verifyCode: memberVerifyCode.value.trim()
                // verifyCode: "A12345"
            }),
        }).then(res => res.json()).then(data => {
            console.log(data);
            if (data.successful) {
                Swal.fire({
                    icon: "success",
                    title: "註冊成功",
                    showConfirmButton: false,
                    timer: 1500
                }).then(()=>{
                    location.href = "../homePage.html";
                })
            } else {
                Swal.fire("驗證碼失敗，請重新輸入", "", "error");
            }
        });
    }
})

//reGetVerifyCode
reGetVerifyCodeBtn.addEventListener("click", function () {
    memberVerifyCode.value = "";
    let sessionDetail = JSON.parse(sessionStorage.getItem("memberData"));
    console.log(sessionDetail);
    fetch(`member/register/update`, {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            customerId: sessionDetail.customerId,
        }),
    }).then(res => res.json()).then(data => {
        console.log(data);
        if (data.successful) {
            Swal.fire("已重新發送驗證碼");
        } else {
            Swal.fire("重新發送驗證碼失敗，請聯絡客服", "", "error");
        }
    })

})

