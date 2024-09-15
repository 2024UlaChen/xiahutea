const cmsQueryMemberNameTxt = document.querySelector("#cmsQueryMemberName");
const cmsQueryMemberAccountTxt = document.querySelector("#cmsQueryMemberAccount");
const cmsQueryMemberCellphoneTxt = document.querySelector("#cmsQueryMemberCellphone");
const cmsQueryMemberStatusTxt = document.querySelector("#cmsQueryMemberStatus");
const memberList = document.querySelector("#memberList");

// button setting
const queryBtn = document.querySelector("#cmsMemberQuery");
const downloadBtn = document.querySelector("#cmsMemberDownload");
queryBtn.addEventListener("click", function () {

})


function getCmsMemberList() {
    let queryMemberList = "";
    fetch(`manage`)
        .then(res => res.json())
        .then(data => {
            $.each(data, function (index, item) {
                let statusClassName = (item.validStatus) ? "valid" : "inValid";
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

function getCmsMemberInfoById(memberid) {
    fetch(`manage/` + memberid)
        .then(res => res.json())
        .then(data => {

        });
}

document.addEventListener("DOMContentLoaded", function () {
    getCmsMemberList();
})

memberList.addEventListener("click", function (e) {
    if (e.target.classList.contains("btnEdit")) {
        let nowItem = e.target.closest("tr");

        // memberId
        e.target.closest("tr").querySelectorAll("td").forEach(item => {
            if (item.dataset.memberid !== undefined) {
                getCmsMemberInfoById(item.dataset.memberid);
                // location.href="../cms/memberEdit.html";
            }
        })
        // console.log(e.target.closest("tr td").getAttribute("data-memberid"));
    }
})
