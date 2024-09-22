const defaultVerifyCodeTip = "請輸入手機驗證";
const verifyCodeRegex = /^(?=.*[A-Z])(?=.*\d)[A-Z0-9]{6}$/;

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
            }else{
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
    }
    // else {
    //     fetch(`member/login`, {
    //         method: "POST",
    //         headers: {'Content-Type': 'application/json'},
    //         body: JSON.stringify({
    //             customerPhone: memberPhoneInput.value.trim(),
    //             customerPassword: pwdInput.value.trim()
    //         }),
    //     }).then(res => res.json()).then(data => {
    //         if (data.successful) {
    //             sessionStorage.setItem("cmsMemberDetail", JSON.stringify(data));
    //             location.href = "../homePage.html";
    //         } else {
    //             Swal.fire(data.message, "", "error");
    //         }
    //     });
    // }
})

//reGetVerifyCode
reGetVerifyCodeBtn.addEventListener("click", function () {
    if ((checkIsEmpty(memberNameInput, memberPhoneInput, pwdInput, rePwdInput))) {
        Swal.fire("請輸入手機 & 密碼 & 姓名", "", "error");
    } else if (!(phoneValid(memberPhoneInput) && pwdValid(pwdInput, pwdTipTxt) && pwdValid(rePwdInput, rePwdTipTxt))) {
        Swal.fire("手機與密碼有誤，請重新確認", "", "error");
    } else if (!checkPwdMatch(pwdInput, rePwdInput, rePwdTipTxt)) {
        Swal.fire("新密碼與再次輸入不同", "", "error");
    } else {
        fetch(`member/register`, {
            method: "POST",
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({
                customerPhone: memberPhoneInput.value.trim(),
                customerPassword: pwdInput.value.trim(),
                nickname: memberNameInput.value.trim()
            }),
        }).then(res => res.json()).then(data => {
            console.log(data);
            console.log(data.successful);
        })
    }
})

