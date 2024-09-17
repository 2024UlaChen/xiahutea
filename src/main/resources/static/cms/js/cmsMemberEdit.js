//table all element
const cmsMemberNameTxt = document.querySelector("#cmsMemberName");
const cmsMemberSex = document.querySelector("#cmsSex");
const cmsMemberBirthdayTxt = document.querySelector("#cmsMemberBirthday");
const cmsMemberEmailTxt = document.querySelector("#cmsMemberEmail");
const cmsMemberPhoneTxt = document.querySelector("#cmsMemberPhone");
const cmsMemberCity = document.querySelector("#cmsMemberAddress");
const cmsMemberDistrict = document.querySelector("#cmsMemberDistrict");
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


document.addEventListener("DOMContentLoaded", () => {
    let sessionDetail = JSON.parse(sessionStorage.getItem("memberDetail"));
    cmsMemberBirthdayTxt.value = sessionDetail.birthday.replaceAll("/", "-");
    CmsMemberCreateDateTxt.value = sessionDetail.createDate.replaceAll("/", "-");
    cmsMemberCarrierTxt.value = sessionDetail.customerCarrier;
    cmsMemberEmailTxt.value = sessionDetail.customerEmail;
    // CmsMemberCreateDateTxt.value = sessionDetail.customerId;
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

    console.log(sessionDetail);
})
cancelBtn.addEventListener("click", function () {
    Swal.fire({
        title: "確定取消當前編輯?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#d33",
        cancelButtonColor: "#6c757d",
        confirmButtonText: "是，取消",
        cancelButtonText: "否"
    }).then((result) => {
        if (result.isConfirmed) {
            location.href = "./memberManage.html"
        }
    });
});

addrCollapseBtn.addEventListener("click", function () {
    const collapseIcon = this.firstElementChild;

    if (collapseIcon.classList.contains("fa-plus")) {

    }
    collapseIcon.classList.toggle("fa-plus");
    collapseIcon.classList.toggle("fa-minus");
    cmsAddrList.classList.toggle("hidden");


})
cmsMemberIsValidTxt.addEventListener("click", function (e) {
    console.log(e.target);
})
