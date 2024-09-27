const defaultPwdTip = "6-16位英數混合，英文需區分大小寫";
const pwdRegex = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z0-9]{6,16}$/;

const oldPwdInput = document.querySelector("#oldPwd");
const newPwdInput = document.querySelector("#newPwd");
const NewCheckPwdInput = document.querySelector("#NewCheckPwd");
const oldPwdTip = document.querySelector("#oldPwdTip");
const newPwdTip = document.querySelector("#newPwdTip");
const newCheckPwdTip = document.querySelector("#newCheckPwdTip");

const settingBtn = document.querySelector("#settingBtn");


function isCheckedSuccessOrDefult(target) {
    let targetDiv = target.closest(".block");
    let targetTxt = targetDiv.closest("div").nextElementSibling;
    targetDiv.classList.remove("checkInValid");
    targetTxt.classList.remove("checkInValidTxt");
}

function isCheckedFalse(target) {
    console.log(target)
    let targetDiv = target.closest(".block");
    let targetTxt = targetDiv.closest("div").nextElementSibling;
    targetDiv.classList.add("checkInValid");
    targetTxt.classList.add("checkInValidTxt");
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


pwdValid(oldPwdInput, oldPwdTip);
pwdValid(newPwdInput, newPwdTip);
pwdValid(NewCheckPwdInput, newCheckPwdTip);

// DOM
document.addEventListener("DOMContentLoaded", function () {
    oldPwdTip.innerText = defaultPwdTip;
    newPwdTip.innerText = defaultPwdTip;
    newCheckPwdTip.innerText = defaultPwdTip;
})

settingBtn.addEventListener("click", function () {
    if (checkIsEmpty(oldPwdInput.value, newPwdInput, value, NewCheckPwdInput.value)) {
        Swal.fire("任一資料不可為空，請再確認", "", "error");
    } else if (!checkPwdMatch(newPwdInput, NewCheckPwdInput, newCheckPwdTip)) {
        Swal.fire("新密碼與再次輸入不同，請再確認", "", "error");
    } else if (!pwdValid(oldPwdInput, oldPwdTip) || !pwdValid(newPwdInput, newPwdTip) ||
        !pwdValid(NewCheckPwdInput, newCheckPwdTip)) {
        Swal.fire("確認資料格式是否正確", "", "error");
    }else{
        console.log(123);
    }
})