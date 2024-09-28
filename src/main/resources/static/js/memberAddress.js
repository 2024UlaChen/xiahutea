const memberAddressUl = document.querySelector("#memberAddress");
const addressSavingBtn = document.querySelector(".addressSaving");

const sessionDetail = JSON.parse(sessionStorage.getItem("memberData"));

function getAddress(memberId) {
    let addrList = "";
    fetch(`member/address`, {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            customerId: memberId
        }),
    }).then(res => res.json())
        .then(data => {
            $.each(data.data, function (index, item) {
                console.log(index);
                console.log(item.customerAddress);
                console.log(item.customerId);

                let addrNum = item.customerAddressId;
                addrList += `
                            <li data-addressID="${addrNum}">
                                <i class="fa fa-map-marker" aria-hidden="true"></i>
                                <span>${item.customerAddress}</span>
                                <button class="editAddr" data-address-edit="${addrNum}">
                                    <i class="fa fa-pencil"></i>
                                </button>
                                <button class="delAddr" data-address-delete="${addrNum}">
                                    <i class="fa fa-trash"></i>
                                </button>
                            </li>
                `;
            });
            console.log(addrList);
            // cmsAddrList.innerHTML = addrList;
            // cmsAddrList.classList.add("hidden");
        })
}

document.addEventListener("DOMContentLoaded", function () {
    if (sessionDetail === null || sessionDetail.data.customerId === null || sessionDetail.data.customerId === undefined) {

    } else {
        getAddress(sessionDetail.data.customerId);
    }
})


document.addEventListener("click",function(e){
    if (e.target.classList.contains("editAddr")) {
        e.target.closest("tr").querySelectorAll("td").forEach(item => {
            if (item.dataset.memberid !== undefined) {
                getCmsMemberInfoById(item.dataset.memberid);
            }
        })
    }
    if (e.target.classList.contains("delAddr")) {
        e.target.closest("tr").querySelectorAll("td").forEach(item => {
            if (item.dataset.memberid !== undefined) {
                getCmsMemberInfoById(item.dataset.memberid);
            }
        })
    }

})