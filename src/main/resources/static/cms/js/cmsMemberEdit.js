//table all element
const cmsMemberNameTxt = document.querySelector("#cmsMemberName");
const cmsMemberSex = document.querySelector("#cmsSex");
const cmsMemberBirthdayTxt = document.querySelector("#cmsMemberBirthday");
const cmsMemberEmailTxt = document.querySelector("#cmsMemberEmail");
const cmsMemberPhoneTxt = document.querySelector("#cmsMemberPhone");
const cmsMemberCarrierTxt = document.querySelector("#cmsMemberCarrier");
const cmsMemberIsValidTxt = document.querySelector("#cmsMemberIsValid");
const CmsMemberCreateDateTxt = document.querySelector("#CmsMemberCreateDate");
const cmsMemberMoney = document.querySelector("#cmsMemberMoney");
const cmsMemberRemarkTxt = document.querySelector("#cmsMemberRemark");
const cmsAddrList = document.querySelector("#cmsAddrList");

// button setting
const cancelBtn = document.querySelector("#cmsMemberCancel");
const saveBtn = document.querySelector("#cmsMemberSave");
const addrCollapseBtn = document.querySelector("#cmsMemberAddrCollapse");

function getAddress() {
    let addrList = "";
    fetch(`manage/address`)
        .then(res => res.json())
        .then(data => {
            $.each(data, function (index, item) {
                let addrNum = index + 1;
                addrList += `
                    <li>
                        <label>地址${addrNum}</label>
                        <input type="text" class="form-control" disabled value=${item.customerAddress}>
                    </li>
                `;
            });
            cmsAddrList.innerHTML = addrList;
            cmsAddrList.classList.add("hidden");
        })
}

addrCollapseBtn.addEventListener("click", function () {
    const collapseIcon = this.firstElementChild;
    cmsAddrList.classList.toggle("hidden");
    collapseIcon.classList.toggle("fa-minus");
    collapseIcon.classList.toggle("fa-plus");
})

function nullToEmpty(data) {
    return (data == null) ? "" : data;
}

function changeDateFormat(date) {
    const SLASH = "/";
    const HYPHEN = "-";
    if (date === "")
        return date;
    if (date.includes(SLASH))
        return date.replaceAll(SLASH, HYPHEN)
    if (date.includes(HYPHEN))
        return date.replaceAll(HYPHEN, SLASH)
}

function loadCmsMemberInfo() {
    fetch(`manage/detail`)
        .then(res => res.json())
        .then(data => {
            console.log(data);
            // let sessionDetail = JSON.parse(sessionStorage.getItem("cmsMemberDetail"));
            cmsMemberBirthdayTxt.value = nullToEmpty(data.birthday);
            CmsMemberCreateDateTxt.value = nullToEmpty(data.createDate);
            cmsMemberCarrierTxt.value = data.customerCarrier;
            cmsMemberEmailTxt.value = data.customerEmail;
            cmsMemberMoney.value = data.customerMoney;
            cmsMemberPhoneTxt.value = data.customerPhone;
            cmsMemberRemarkTxt.value = data.customerRemark;
            cmsMemberNameTxt.value = data.nickname;
            cmsMemberIsValidTxt.checked = data.validStatus;
            if (data.sex === "female") {
                cmsMemberSex.value = "女性";
            } else if (data.sex === "male") {
                cmsMemberSex.value = "男性";
            } else if (data.sex === "none") {
                cmsMemberSex.value = "不提供";
            } else {
                cmsMemberSex.value = nullToEmpty(data.sex);
            }
        });
    getAddress();
}

document.addEventListener("DOMContentLoaded", () => {
    loadCmsMemberInfo();
})

cancelBtn.addEventListener("click", function () {
    // sessionStorage.removeItem("cmsMemberDetail");
    location.href = "./memberManage.html"
});

function updateMemberInfo() {
    fetch(`manage/detail`)
        .then(res => res.json())
        .then(data => {
            // let changeSessionDetail = JSON.parse(sessionStorage.getItem("cmsMemberDetail"));
            const memberId = data.customerId;
            if (data.customerRemark === cmsMemberRemarkTxt.value && data.validStatus === cmsMemberIsValidTxt.checked) {
                Swal.fire("與目前設定無差異", "", "error");
            } else {
                let updateData = {
                    "customerRemark": cmsMemberRemarkTxt.value,
                    "validStatus": cmsMemberIsValidTxt.checked
                };
                fetch(`manage/update`, {
                    method: 'POST',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify(updateData)
                })
                    .then(response => response.json())
                    .then(data => {
                        console.log(data);
                    });
                Swal.fire("已儲存!", "", "success");
            }
        });

}

saveBtn.addEventListener("click", function () {
    Swal.fire({
        title: "確定要變更資料?",
        icon: "question",
        showCancelButton: true,
        confirmButtonText: "儲存",
        cancelButtonText: "取消",
        confirmButtonColor: "#73B4BA",
        cancelButtonColor: "#6c757d",
    }).then((result) => {
        if (result.isConfirmed) {
            updateMemberInfo();
        }
    });
})

