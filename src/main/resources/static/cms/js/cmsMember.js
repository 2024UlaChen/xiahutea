const cmsQueryMemberNameTxt = document.querySelector("#cmsQueryMemberName");
const cmsQueryMemberIdTxt = document.querySelector("#cmsQueryMemberId");
const cmsQueryMemberCellphoneTxt = document.querySelector("#cmsQueryMemberCellphone");
const cmsQueryMemberStatusTxt = document.querySelector("#cmsQueryMemberStatus");


const memberList = document.querySelector("#memberList");

// button setting
const queryBtn = document.querySelector("#cmsMemberQuery");
const downloadBtn = document.querySelector("#cmsMemberDownload");
const pagePreBtn = document.querySelector("#pagePre");
const pageNextBtn = document.querySelector("#pageNext");
const pageDetail = document.querySelector("#pageDetail");
// TIP
const matchTip = "(支援模糊搜尋)";
let defaultPageNo = 0;


// input檢核
function isCheckedSuccess(target) {
    target.previousElementSibling.textContent = "";
    target.previousElementSibling.classList.remove("checkInValid");
    target.classList.remove("inputCheckInValid");
}

function isCheckedFalse(target) {
    target.previousElementSibling.classList.add("checkInValid");
    target.classList.add("inputCheckInValid");
}

function phoneValid(phone) {
    cmsQueryMemberCellphoneTxt.addEventListener("input", function () {
        if (phone.value.length === 0) {
            this.previousElementSibling.textContent = matchTip;
            isCheckedSuccess(this);
        } else {
            if (isNaN(phone.value)) {
                this.previousElementSibling.textContent = "僅接受數字";
                isCheckedFalse(this);
            } else {
                this.previousElementSibling.textContent = matchTip;
                isCheckedSuccess(this);
            }
        }
    })
    return !isNaN(phone.value);
}

function MemberIdValid(memberId) {
    memberId.addEventListener("input", function () {
        if (isNaN(memberId.value)) {
            this.previousElementSibling.textContent = "僅接受數字";
            isCheckedFalse(this);
        } else {
            isCheckedSuccess(this);
        }
    })
    return !isNaN(memberId.value);
}


function nullToEmpty(data) {
    return (data == null) ? "" : data;
}

function setPage(totalPage, pageNo) {
    let pageNumList = "";
    for (let i = 0; i < totalPage; i++) {
        let addCssName = (i === parseInt(pageNo)) ? "now" : "";
        pageNumList += `<button class="btnPage pageNum ${addCssName}" data-page="${i}">${i + 1}</button>`;
    }
    pageDetail.innerHTML = pageNumList;
    addPageBtnCss(totalPage, pageNo);
}

function addPageBtnCss(totalPage, pageNo) {
    pagePreBtn.classList.remove("off");
    pageNextBtn.classList.remove("off");
    if (parseInt(totalPage) === 1) {
        pagePreBtn.classList.add("off");
        pageNextBtn.classList.add("off");
    } else if (parseInt(totalPage) === 2) {
        if (parseInt(pageNo) === 0) {
            pagePreBtn.classList.add("off");
            pageNextBtn.classList.remove("off");
        } else {
            pagePreBtn.classList.remove("off");
            pageNextBtn.classList.add("off");
        }
    } else {
        if (parseInt(pageNo) === 0) {
            pagePreBtn.classList.add("off");
        }
        if (parseInt(pageNo) === (parseInt(totalPage) - 1)) {
            pageNextBtn.classList.add("off");
        }
    }
}

function getCmsMemberList(pageNo) {
    let queryMemberList = "";
    fetch(`manage/all?searchPageNo=` + pageNo)
        .then(res => res.json())
        .then(data => {
            if (data.data.memberData.length === 0) {
                Swal.fire({
                    icon: "error",
                    title: "非管理員權限，無法檢視",
                    showConfirmButton: false,
                    timer: 1500
                }).then(() => {
                    location.replace("../cms/backstageLogin.html");
                })
            } else {
                setPage(data.data.totalPage, pageNo);
                $.each(data.data.memberData, function (index, item) {
                    let statusClassName = (item.validStatus) ? "statusInValid" : "statusIsValid";
                    let statusChiName = (item.validStatus) ? "停權" : "生效";
                    queryMemberList += `
                <tr data-listindex="${index}">
                    <td>
                        <span>${item.nickname}</span>
                    </td>
                    <td data-memberid="${item.customerId}">
                        <span>${item.customerId}</span>
                    </td>
                    <td>
                        <span>${nullToEmpty(item.customerEmail)}</span>
                    </td>
                    <td>
                        <span>${item.customerPhone}</span>
                    </td>
                    <td>
                        <span class="badge badge-success memberStatus ${statusClassName}">${statusChiName}</span>
                    </td>
                    <td>
                        <button class="btn btnEdit">
                            <i class="fas fa-pencil-alt"> </i> 修改
                        </button>
                    </td>
                </tr>
                `;
                });
                memberList.innerHTML = queryMemberList;
            }
        })
}


function getCmsMemberInfoById(memberid) {
    fetch(`manage/detail`, {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            customerId: memberid,
        }),
    }).then(res => res.json())
        .then(data => {
            if (data.successful) {
                // sessionStorage.setItem("cmsMemberDetail", JSON.stringify(data));
                location.href = "../cms/memberEdit.html";
            } else {
                console.log("get member error");
            }
        });
}

memberList.addEventListener("click", function (e) {
    if (e.target.classList.contains("btnEdit")) {
        e.target.closest("tr").querySelectorAll("td").forEach(item => {
            if (item.dataset.memberid !== undefined) {
                getCmsMemberInfoById(item.dataset.memberid);
            }
        })
    }
})

document.addEventListener("DOMContentLoaded", function () {
    getCmsMemberList(0);
    cmsQueryMemberNameTxt.previousElementSibling.textContent = matchTip;
})

MemberIdValid(cmsQueryMemberIdTxt);
phoneValid(cmsQueryMemberCellphoneTxt);

function getQueryData(pageNo) {
    let queryMemberList = "";
    let queryStatus = (cmsQueryMemberStatusTxt.value === "default") ? null : cmsQueryMemberStatusTxt.value;
    fetch('manage?searchPageNo=' + pageNo, {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            customerId: cmsQueryMemberIdTxt.value.trim(),
            nickname: cmsQueryMemberNameTxt.value.trim(),
            customerPhone: cmsQueryMemberCellphoneTxt.value.trim(),
            validStatus: queryStatus
        }),
    }).then(res => res.json()).then(resData => {
        setPage(resData.data.totalPage, pageNo);
        let data = resData.data.memberData;
        $.each(data, function (index, item) {
            let statusClassName = (item.validStatus) ? "statusInValid" : "statusIsValid";
            let statusChiName = (item.validStatus) ? "停權" : "生效";
            queryMemberList += `
                <tr data-listindex="${index}">
                    <td>
                        <span>${item.nickname}</span>
                    </td>
                    <td data-memberid="${item.customerId}">
                        <span>${item.customerId}</span>
                    </td>
                    <td>
                        <span>${nullToEmpty(item.customerEmail)}</span>
                    </td>
                    <td>
                        <span>${item.customerPhone}</span>
                    </td>
                    <td>
                        <span class="badge badge-success memberStatus ${statusClassName}">${statusChiName}</span>
                    </td>
                    <td>
                        <button class="btn btnEdit">
                            <i class="fas fa-pencil-alt"> </i> 修改
                        </button>
                    </td>
                </tr>
                `;
        });
        memberList.innerHTML = queryMemberList;
    });
}

queryBtn.addEventListener("click", function () {
    if (!(MemberIdValid(cmsQueryMemberIdTxt) && phoneValid(cmsQueryMemberCellphoneTxt))) {
        Swal.fire("請確認ID或電話資料是否有誤", "", "error");
    } else {
        getQueryData(0);
    }
})
//底下page搜尋
pageDetail.addEventListener("click", function (e) {
    let targetPage = e.target.getAttribute("data-page");
    let i = 0;
    pageDetail.querySelectorAll(".pageNum").forEach((item, index) => {
        item.classList.remove("now");
        if (index === parseInt(targetPage)) {
            item.classList.add("now");
        }
        i = index;
    })
    getQueryData(targetPage);
})

pagePreBtn.addEventListener("click", function (e) {
    if (!this.classList.contains("off")) {
        let nowPage = 0;
        pageDetail.querySelectorAll(".pageNum").forEach((item, index) => {
            if (item.classList.contains("now")) {
                nowPage = item.getAttribute("data-page");
            }
        })
        getQueryData(parseInt(nowPage) - 1);
    }
})

pageNextBtn.addEventListener("click", function (e) {
    if (!this.classList.contains("off")) {
        let nowPage = 0;
        pageDetail.querySelectorAll(".pageNum").forEach((item, index) => {
            if (item.classList.contains("now")) {
                nowPage = item.getAttribute("data-page");
            }
        })
        getQueryData(parseInt(nowPage) + 1);
    }
})