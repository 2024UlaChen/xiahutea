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

function getAddress(memberId) {
    let addrList = "";
    fetch(`manage/address/` + memberId)
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

function loadCmsMemberInfo() {
    let sessionDetail = JSON.parse(sessionStorage.getItem("cmsMemberDetail"));
    console.log(sessionDetail);
    cmsMemberBirthdayTxt.value = nullToEmpty(sessionDetail.birthday).replaceAll("/", "-");
    CmsMemberCreateDateTxt.value = nullToEmpty(sessionDetail.createDate).replaceAll("/", "-");
    cmsMemberCarrierTxt.value = sessionDetail.customerCarrier;
    cmsMemberEmailTxt.value = sessionDetail.customerEmail;
    cmsMemberMoney.value = sessionDetail.customerMoney;
    cmsMemberPhoneTxt.value = sessionDetail.customerPhone;
    cmsMemberRemarkTxt.value = sessionDetail.customerRemark;
    cmsMemberNameTxt.value = sessionDetail.nickname;
    cmsMemberIsValidTxt.checked = sessionDetail.validStatus;
    if (sessionDetail.sex === "female") {
        cmsMemberSex.value = "女性";
    } else if (sessionDetail.sex === "male") {
        cmsMemberSex.value = "男性";
    } else {
        cmsMemberSex.value = "不提供";
    }
    getAddress(sessionDetail.customerId);
}

document.addEventListener("DOMContentLoaded", () => {
    loadCmsMemberInfo();
})

cancelBtn.addEventListener("click", function () {
    Swal.fire({
        title: "確定取消當前編輯?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#73B4BA",
        cancelButtonColor: "#6c757d",
        confirmButtonText: "是，取消",
        cancelButtonText: "否"
    }).then((result) => {
        if (result.isConfirmed) {
            sessionStorage.removeItem("cmsMemberDetail");
            location.href = "./memberManage.html"
        }
    });
});

function updateMemberInfo() {
    let changeSessionDetail = JSON.parse(sessionStorage.getItem("cmsMemberDetail"));
    const memberId = changeSessionDetail.customerId;
    if (changeSessionDetail.customerRemark === cmsMemberRemarkTxt.value && changeSessionDetail.validStatus === cmsMemberIsValidTxt.checked) {
        Swal.fire("與目前設定無差異", "", "error");
    } else {
        let updateData = {
            "customerRemark": cmsMemberRemarkTxt.value,
            "validStatus": cmsMemberIsValidTxt.checked
        };
        fetch(`manage/${memberId}`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json-patch+json'
            },
            body: JSON.stringify(updateData)
        })
            .then(response => response.json())
            .then(data => {
                if (data.successful) {
                    fetch(`manage/${memberId}`)
                        .then(res => res.json())
                        .then(data => {
                            sessionStorage.removeItem("cmsMemberDetail");
                            sessionStorage.setItem("cmsMemberDetail", JSON.stringify(data));
                        })
                } else {
                    //TODO

                }
                // sessionStorage.removeItem("cmsMemberDetail");
                // sessionStorage.setItem("cmsMemberDetail", JSON.stringify(data));
                // loadCmsMemberInfo();
            })
        Swal.fire("已儲存!", "", "success");
    }
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

