const cmsQueryMemberNameTxt = document.querySelector("#cmsQueryMemberName");
const cmsQueryMemberIdTxt = document.querySelector("#cmsQueryMemberId");
const cmsQueryMemberCellphoneTxt = document.querySelector("#cmsQueryMemberCellphone");
const cmsQueryMemberStatusTxt = document.querySelector("#cmsQueryMemberStatus");

const queryList = document.querySelectorAll("#cmsQueryBar input");

const memberList = document.querySelector("#memberList");

// button setting
const queryBtn = document.querySelector("#cmsMemberQuery");
const downloadBtn = document.querySelector("#cmsMemberDownload");

// input檢核
function isCheckedSuccess(target) {
    target.previousElementSibling.textContent = "";
    target.previousElementSibling.classList.remove("checkInValid");
    return true;
}

function isCheckedFalse(target) {
    target.previousElementSibling.classList.add("checkInValid");
    return false;
}

function phoneValid(phone) {
    cmsQueryMemberCellphoneTxt.addEventListener("input", function () {
        if (phone.value.length === 0) {
            isCheckedSuccess(this);
        } else {
            if (isNaN(phone.value)) {
                this.previousElementSibling.textContent = "僅接受數字";
                isCheckedFalse(this);
            } else if (!phone.value.startsWith("09")) {
                this.previousElementSibling.textContent = "開頭必須是09";
                isCheckedFalse(this);
            } else {
                isCheckedSuccess(this);
            }
        }
    })
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
}

queryBtn.addEventListener("click", function () {
    if ((cmsQueryMemberNameTxt.value.trim() === "" && cmsQueryMemberIdTxt.value.trim() === "" &&
        cmsQueryMemberCellphoneTxt.value.trim() === "" && cmsQueryMemberStatusTxt.value === "default")) {
        Swal.fire("查詢至少需輸入一個條件", "", "error");
    } else {
        let queryMemberList = "";
        let queryStatus = (cmsQueryMemberStatusTxt.value === "default") ? null : cmsQueryMemberStatusTxt.value;
        fetch(`manage`, {
            method: "POST",
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({
                customerId: cmsQueryMemberIdTxt.value.trim(),
                nickname: cmsQueryMemberNameTxt.value.trim(),
                customerPhone: cmsQueryMemberCellphoneTxt.value.trim(),
                validStatus: queryStatus,
            }),
        }).then(res => res.json()).then(data => {
            $.each(data, function (index, item) {
                let statusClassName = (item.validStatus) ? "statusIsValid" : "statusInValid";
                let statusChiName = (item.validStatus) ? "生效" : "停權";
                queryMemberList += `
                <tr data-listindex="${index}">
                    <td>
                        <span>${item.nickname}</span>
                    </td>
                    <td data-memberid="${item.customerId}">
                        <span>${item.customerId}</span>
                    </td>
                    <td>
                        <span>${item.customerEmail}</span>
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
    // TODO - add js vialation
    // else if (!(MemberIdValid(cmsQueryMemberIdTxt) && phoneValid(cmsQueryMemberCellphoneTxt))) {
    //     console.log(MemberIdValid(cmsQueryMemberIdTxt));
    //     console.log(MemberIdValid(cmsQueryMemberIdTxt));
    //
    //     Swal.fire("請更正查詢資料", "", "error");
    // }


})


function getCmsMemberList() {
    let queryMemberList = "";
    fetch(`manage`)
        .then(res => res.json())
        .then(data => {
            $.each(data, function (index, item) {
                let statusClassName = (item.validStatus) ? "statusIsValid" : "statusInValid";
                let statusChiName = (item.validStatus) ? "生效" : "停權";
                queryMemberList += `
                <tr data-listindex="${index}">
                    <td>
                        <span>${item.nickname}</span>
                    </td>
                    <td data-memberid="${item.customerId}">
                        <span>${item.customerId}</span>
                    </td>
                    <td>
                        <span>${item.customerEmail}</span>
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
        })
}


//TODO
function getCmsMemberInfoById(memberid) {
    fetch(`manage/` + memberid)
        .then(res => res.json())
        .then(data => {

            sessionStorage.setItem("memberDetail", JSON.stringify(data));
            location.href="../cms/memberEdit.html";
        });
}

//TODO
memberList.addEventListener("click", function (e) {
    if (e.target.classList.contains("btnEdit")) {
        let nowItem = e.target.closest("tr");

        // memberId
        e.target.closest("tr").querySelectorAll("td").forEach(item => {
            if (item.dataset.memberid !== undefined) {
                getCmsMemberInfoById(item.dataset.memberid);
            }
        })
    }
})

document.addEventListener("DOMContentLoaded", function () {
    getCmsMemberList();
})

MemberIdValid(cmsQueryMemberIdTxt);
phoneValid(cmsQueryMemberCellphoneTxt);