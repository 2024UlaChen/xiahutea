//table all element
const cmsMemberNameTxt = document.querySelector("#cmsMemberName");
const cmsMemberSex = document.querySelector("#cmsSexList");
const cmsMemberBirthdayTxt = document.querySelector("#cmsMemberBirthday");
const cmsMemberEmailTxt = document.querySelector("#cmsMemberEmail");
const cmsMemberPhoneTxt = document.querySelector("#cmsMemberPhone");
const cmsMemberCity = document.querySelector("#cmsMemberCity");
const cmsMemberDistrict = document.querySelector("#cmsMemberDistrict");
const cmsMemberCarrierTxt = document.querySelector("#cmsMemberCarrier");
const cmsMemberIsValidTxt = document.querySelector("#cmsMemberIsValid");
const CmsMemberCreateDateTxt = document.querySelector("#CmsMemberCreateDate");
const cmsMemberMoney = document.querySelector("#cmsMemberMoney");
const cmsMemberImg = document.querySelector("#cmsMemberImg");
const cmsMemberRemarkTxt = document.querySelector("#cmsMemberRemark");
const cmsAddrList = document.querySelector("#cmsAddrList");


// button setting
const cancelBtn = document.querySelector("#cmsMemberCancel");
const saveBtn = document.querySelector("#cmsMemberSave");
const addrCollapseBtn = document.querySelector("#cmsMemberAddrCollapse");


document.addEventListener("DOMContentLoaded", () => {
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

    if(collapseIcon.classList.contains("fa-plus")){

    }
    collapseIcon.classList.toggle("fa-plus");
    collapseIcon.classList.toggle("fa-minus");
    cmsAddrList.classList.toggle("hidden");



})