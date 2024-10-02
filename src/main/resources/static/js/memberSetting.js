// ASIDE
const asidePhoto = document.querySelector("#asidePhoto");
const asideName = document.querySelector("#asideName");

const defaultPwdTip = "6-16位英數混合，英文需區分大小寫";
const pwdRegex = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z0-9]{6,16}$/;

const oldPwdInput = document.querySelector("#oldPwd");
const newPwdInput = document.querySelector("#newPwd");
const NewCheckPwdInput = document.querySelector("#NewCheckPwd");
const oldPwdTip = document.querySelector("#oldPwdTip");
const newPwdTip = document.querySelector("#newPwdTip");
const newCheckPwdTip = document.querySelector("#newCheckPwdTip");

const settingBtn = document.querySelector("#settingBtn");

function nullToEmpty(data) {
    return (data == null) ? "" : data;
}
function isCheckedSuccessOrDefult(target) {
    let targetDiv = target.closest(".settingBlock");
    let targetTxt = targetDiv.closest("div").nextElementSibling;
    targetDiv.classList.remove("checkInValid");
    targetTxt.classList.remove("checkInValidTxt");
}

function isCheckedFalse(target) {
    let targetDiv = target.closest(".settingBlock");
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
                isCheckedFalse(pwd);
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
function getUserImg(){
    fetch(`member`, {
        method: "POST",
    }).then(res => res.json())
        .then(data => {
            if (data.successful) {
                let userImg = (nullToEmpty(data.customerImg) === "") ? "" : `data:image/png;base64,${data.customerImg}`;
                asidePhoto.setAttribute("src", userImg);
                asideName.innerText = nullToEmpty(data.nickname);
            }
        })
}

// DOM
document.addEventListener("DOMContentLoaded", function () {
    oldPwdTip.innerText = defaultPwdTip;
    newPwdTip.innerText = defaultPwdTip;
    newCheckPwdTip.innerText = defaultPwdTip;
    getUserImg();
})
settingBtn.addEventListener("click", function () {
    if (checkIsEmpty(oldPwdInput, newPwdInput, NewCheckPwdInput)) {
        Swal.fire("任一資料不可為空，請再確認", "", "error");
    } else if (!checkPwdMatch(newPwdInput, NewCheckPwdInput, newCheckPwdTip)) {
        Swal.fire("新密碼與再次輸入不同，請再確認", "", "error");
    } else if (!pwdValid(oldPwdInput, oldPwdTip) || !pwdValid(newPwdInput, newPwdTip) ||
        !pwdValid(NewCheckPwdInput, newCheckPwdTip)) {
        Swal.fire("確認資料格式是否正確", "", "error");
    } else {
        let sessionDetail = JSON.parse(sessionStorage.getItem("memberData"));
        fetch(`member/setting/check`, {
            method: "POST",
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({
                customerId: sessionDetail.data.customerId,
                customerPassword: oldPwdInput.value.trim()
            }),
        }).then(res => res.json()).then(data => {
            if (data.successful) {
                // todo - jwt
                fetch(`member/setting/update`, {
                    method: "POST",
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify({
                        customerId: sessionDetail.data.customerId,
                        customerPassword: newPwdInput.value.trim()
                    }),
                }).then(res => res.json()).then(data => {
                    if (data.successful) {
                        // todo - jwt
                        Swal.fire("已更新密碼", "", "success");
                        oldPwdInput.value = "";
                        newPwdInput.value = "";
                        NewCheckPwdInput.value = "";
                    } else {
                        Swal.fire("set Type Error", "", "error");
                    }
                })
            } else {
                Swal.fire("Type Error", "", "error");
            }
        })
    }
})