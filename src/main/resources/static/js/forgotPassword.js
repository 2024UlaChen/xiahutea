const defaultPhoneTip = "只支援台灣手機號碼";
const defaultPwdTip = "6-16位英數混合，英文需區分大小寫";
const defaultVerifyCodeTip = "請輸入手機驗證";
const forgetPwdPhoneTip = document.querySelector("#forgetPwdPhoneTip");
const resetPwdTip = document.querySelector("#resetPwdTip");
const resetRePwdTip = document.querySelector("#resetRePwdTip");
const resetVerifyCodeTip = document.querySelector("#resetVerifyCodeTip");

const titleTxt = document.querySelector("#title");
const resetPwdForm = document.querySelector("#resetPwdForm");

// input
const forgetPwdPhoneInput = document.querySelector("#forgetPwdPhone");
const resetPwdInput = document.querySelector("#resetPwd");
const resetRePwdInput = document.querySelector("#resetRePwd");
const resetVerifyCodeInput = document.querySelector("#resetVerifyCode");

// button
const resetPwdBtn = document.querySelector("#resetPwdBtn");
const sendReSetBtn = document.querySelector("#sendReSet");
const reGetVerifyCodeBtn = document.querySelector("#reGetVerifyCode");


const pwdRegex = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z0-9]{6,16}$/;
const phoneRegex = /^09[0-9]{8}$/;
const verifyCodeRegex = /^[A-Z0-9]{6}$/;

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

// phone check
function phoneValid(phone) {
    phone.addEventListener("input", function () {
        let phoneValue = phone.value;
        if (phoneValue.length === 0) {
            forgetPwdPhoneTip.innerText = defaultPhoneTip;
            isCheckedSuccessOrDefult(this);
            return;
        }
        if (phoneRegex.test(phoneValue) && phoneValue.length === 10) {
            isCheckedSuccessOrDefult(this);
            forgetPwdPhoneTip.innerText = defaultPhoneTip;
        } else {
            if (phoneValue.indexOf(" ") !== -1) {
                forgetPwdPhoneTip.innerText = "不可包含空白字元";
                isCheckedFalse(this);
            } else if (isNaN(phoneValue)) {
                forgetPwdPhoneTip.innerText = "僅接受數字";
                isCheckedFalse(this);
            } else {
                forgetPwdPhoneTip.innerText = "開頭必須是09，共10位數";
                isCheckedFalse(this);
            }
        }
    })
    return phoneRegex.test(phone.value) && phone.value.length === 10;
}


// password check
function pwdValid(pwd, tip) {
    pwd.addEventListener("input", function () {
        let pwdValue = pwd.value;
        if (pwdValue.length === 0) {
            tip.innerText = defaultPwdTip;
            isCheckedSuccessOrDefult(pwd);
            return;
        }
        if (pwdRegex.test(pwdValue) && pwdValue.length <= 16 && pwdValue.length >= 6) {
            isCheckedSuccessOrDefult(pwd);
            tip.innerText = defaultPwdTip;
        } else {
            if (pwdValue.indexOf(" ") !== -1) {
                tip.innerText = "不可包含空白字元";
                isCheckedFalse(this);
            } else if (pwdValue.length < 6) {
                tip.innerText = "密碼長度需大於6位";
                isCheckedFalse(pwd);
            } else if (isNaN(pwdValue)) {
                tip.innerText = "密碼必須包含數字";
                isCheckedFalse(pwd);
            } else if (!isNaN(pwdValue)) {
                tip.innerText = "密碼必須包含英文";
                isCheckedFalse(pwd);
            } else {
                tip.innerText = "密碼6-16位，僅能輸入英文與數字";
                isCheckedFalse(pwd);
            }
        }
    })
    return (pwdRegex.test(pwd.value) && pwd.value.length <= 16 && pwd.value.length >= 6);
}


//pwd & rePwd check
function checkPwdMatch(pwd, rePwd, rePwdTipTxt) {
    if (pwd.value !== rePwd.value) {
        rePwdTipTxt.textContent = "新密碼與再次輸入不同";
        return false;
    } else {
        rePwdTipTxt.textContent = defaultPwdTip;
        return true;
    }
}

//checkVerifyCode
function verifyCodeValid(code) {
    code.addEventListener("input", function () {
        let codeValue = code.value;
        if (codeValue.length === 0) {
            resetVerifyCodeTip.innerText = defaultVerifyCodeTip;
            isCheckedSuccessOrDefult(code);
            return;
        }
        if (verifyCodeRegex.test(codeValue) && codeValue.length === 6) {
            isCheckedSuccessOrDefult(code);
            resetVerifyCodeTip.innerText = defaultVerifyCodeTip;
        } else {
            if (codeValue.indexOf(" ") !== -1) {
                resetVerifyCodeTip.innerText = "不可包含空白字元";
                isCheckedFalse(code);
            } else if (codeValue.length !== 6) {
                resetVerifyCodeTip.innerText = "長度須為六碼";
                isCheckedFalse(code);
            } else {
                resetVerifyCodeTip.innerText = "格式錯誤";
                isCheckedFalse(code);
            }
        }
    })
    return verifyCodeRegex.test(code.value) && code.value.length === 6;
}

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

phoneValid(forgetPwdPhoneInput);
pwdValid(resetPwdInput, resetPwdTip);
pwdValid(resetRePwdInput, resetRePwdTip);
verifyCodeValid(resetVerifyCodeInput);

// DOM
document.addEventListener("DOMContentLoaded", function () {
    titleTxt.innerText = "忘記密碼";
    forgetPwdPhoneTip.innerText = defaultPhoneTip;
    resetPwdTip.innerText = defaultPwdTip;
    resetRePwdTip.innerText = defaultPwdTip;
    resetVerifyCodeTip.innerText = defaultVerifyCodeTip;
})
//RESET PWD

resetPwdBtn.addEventListener("click", function () {
    if (forgetPwdPhoneInput.value.length === 0) {
        Swal.fire("手機不可為空", "", "error");
    } else if (!phoneValid(forgetPwdPhoneInput)) {
        Swal.fire("確認手機格式是否正確", "", "error");
    } else {
        if (resetPwdForm.classList.contains("hidden")) {
            fetch(`member/register/update`, {
                method: "POST",
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    customerPhone: forgetPwdPhoneInput.value
                }),
            }).then(res => res.json()).then(data => {
                if (data.successful) {
                    Swal.fire({
                        title: "已重新發送驗證碼",
                        timer: 1500
                    }).then(() => {
                        titleTxt.innerText = "重設密碼";
                        resetPwdForm.classList.remove("hidden");
                        reGetVerifyCodeBtn.classList.remove("hidden");
                    })
                } else {
                    Swal.fire("請確認是否有註冊手機", "", "error");
                }
            });
        } else {
            // TODO use phone to get memberId=>check token => reset pwd
            if (checkIsEmpty(forgetPwdPhoneInput, resetPwdInput, resetRePwdInput, resetVerifyCodeInput)) {
                Swal.fire("任一資料不可為空，請再確認", "", "error");
            } else if (!checkPwdMatch(resetPwdInput, resetRePwdInput, resetRePwdTip)) {
                Swal.fire("新密碼與再次輸入不同，請再確認", "", "error");
            } else if (!phoneValid(forgetPwdPhoneInput) || !pwdValid(resetPwdInput, resetPwdTip) ||
                !pwdValid(resetRePwdInput, resetRePwdTip) || !verifyCodeValid(resetVerifyCodeInput)) {
                Swal.fire("確認資料格式是否正確", "", "error");
            } else {
                fetch(`member/register/check/FORGETPWD`, {
                    method: "POST",
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify({
                        customerPhone: forgetPwdPhoneInput.value,
                        customerPassword: resetPwdInput.value.trim(),
                        verifyCode: resetVerifyCodeInput.value.trim()
                    }),
                }).then(res => res.json()).then(data => {
                    console.log(data);
                    if (data.successful) {
                        Swal.fire({
                            icon: "success",
                            title: "更新密碼成功",
                            showConfirmButton: false,
                            timer: 1500
                        }).then(() => {
                            location.href = "../login.html";
                        })
                    } else {
                        Swal.fire("驗證失敗，請重新輸入", "", "error");
                        resetVerifyCodeInput.value = "";
                    }
                });
            }
        }
    }
})


//RE GET VERIFY CODE
reGetVerifyCodeBtn.addEventListener("click", function () {
    resetVerifyCodeInput.value = "";
    // TODO use phone to get memberId and reset verify code
    let sessionDetail = JSON.parse(sessionStorage.getItem("memberData"));
    console.log(sessionDetail);
    // TODO UPDATE PHONE CHECK NO MAPPED PHONE
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
