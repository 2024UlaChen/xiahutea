const defaultPhoneTip = "只支援台灣手機號碼";
const defaultPwdTip = "6-16位英數混合，英文需區分大小寫";
const pwdRegex = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z0-9]{6,16}$/;
const phoneRegex = /^09[0-9]{8}$/;

// element
const toggleLink = document.querySelector("#toggleLink");
const forgetPwdLink = document.querySelector("#forgetPwd");
const memberPhoneInput = document.querySelector("#memberPhone");
const pwdInput = document.querySelector("#pwd");
const rePwdInput = document.querySelector("#rePwd");
const memberNameInput = document.querySelector("#memberName");

// TIP
const phoneTipTxt = document.querySelector("#PhoneTip");
const pwdTipTxt = document.querySelector("#pwdTip");
const rePwdTipTxt = document.querySelector("#rePwdTip");

const registerForm = document.querySelector(".registerForm");
// button
const btnLogin = document.querySelector(".btnLogin");
const btnRegister = document.querySelector(".btnRegister");

// toggle Register / login
toggleLink.addEventListener("click", function () {
    let toggleBlock = toggleLink.closest("div").querySelector("span");
    if (this.classList.contains("logging")) {
        //註冊的時候
        toggleBlock.innerText = "還沒登入？";
        toggleLink.innerText = "立即登入";
        phoneTipTxt.innerText = defaultPhoneTip;
        pwdTipTxt.innerText = defaultPwdTip;
        rePwdTipTxt.innerText = defaultPwdTip;
    } else {
        // 登入的時候
        toggleBlock.innerText = "沒有帳號？";
        toggleLink.innerText = "立即註冊";
    }
    this.classList.toggle("logging");
    registerForm.classList.toggle("hidden");
    btnLogin.classList.toggle("hidden");
    btnRegister.classList.toggle("hidden");
    forgetPwdLink.classList.toggle("hidden");
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

// phone check
function phoneValid(phone) {
    memberPhoneInput.addEventListener("input", function () {
        let phoneValue = phone.value;
        console.log(phoneValue);
        if (phoneValue.length === 0) {
            phoneTipTxt.innerText = defaultPhoneTip;
            isCheckedSuccessOrDefult(this);
            return;
        }
        if (phoneRegex.test(phoneValue) && phoneValue.length === 10) {
            isCheckedSuccessOrDefult(this);
            phoneTipTxt.innerText = defaultPhoneTip;
        } else {
            if (phoneValue.indexOf(" ") !== -1) {
                phoneTipTxt.innerText = "不可包含空白字元";
                isCheckedFalse(this);
            } else if (isNaN(phoneValue)) {
                phoneTipTxt.innerText = "僅接受數字";
                isCheckedFalse(this);
            } else {
                phoneTipTxt.innerText = "開頭必須是09，共10位數";
                isCheckedFalse(this);
            }
        }
    })
}

// password check
function pwdValid(pwd, tip) {
    pwd.addEventListener("input", function () {
        let pwdValue = pwd.value;
        console.log(pwdValue);
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
}

//pwd & rePwd check
function checkPwd(pwd, rePwd, rePwdTipTxt) {

}

// 忘記密碼
forgetPwdLink.addEventListener("click", function () {
    location.href = "../forgotPassword.html";
})

//DOMLOAD
document.addEventListener("DOMContentLoaded", function () {
    phoneTipTxt.innerText = defaultPhoneTip;
    pwdTipTxt.innerText = defaultPwdTip;
    rePwdTipTxt.innerText = defaultPwdTip;
})

phoneValid(memberPhoneInput);
pwdValid(pwdInput, pwdTipTxt);
pwdValid(rePwdInput, rePwdTipTxt);

function checkIsEmpty(...args) {
    args.forEach((item, index) => {
        console.log(item.value);
    })
}

//LOGIN
btnLogin.addEventListener("click", function () {
    if (checkIsEmpty(memberPhoneInput, pwdInput)) {
        Swal.fire("請輸入手機與密碼", "", "error");
    }
})

//REGISTER
btnRegister.addEventListener("click", function () {
    if (pwdInput.value.length === 0 || pwdInput.value.length === 0) {
        Swal.fire("請輸入手機與密碼", "", "error");
    }
})