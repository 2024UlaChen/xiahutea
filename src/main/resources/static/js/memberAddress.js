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
                let addrNum = item.customerAddressId;
                addrList += `
                            <li data-addressID="${addrNum}">
                                <i class="fa fa-map-marker" aria-hidden="true"></i>
                                <span>${item.customerAddress}</span>
                                <button class="editAddr" data-address-edit="${addrNum}">
                                    <i class="fa fa-pencil editAddr"></i>
                                </button>
                                <button class="delAddr" data-address-delete="${addrNum}">
                                    <i class="fa fa-trash delAddr"></i>
                                </button>
                            </li>
                `;
            });
            addrList += `<hr>`
            // console.log(addrList);
            memberAddressUl.innerHTML = addrList;
            // cmsAddrList.classList.add("hidden");
        })
}

async function editAddr() {
    const {value: fruit} = await Swal.fire({
        title: "Select field validation",
        input: "select",
        inputOptions: {
            Fruits: {
                apples: "Apples",
                bananas: "Bananas",
                grapes: "Grapes",
                oranges: "Oranges"
            },
            Vegetables: {
                potato: "Potato",
                broccoli: "Broccoli",
                carrot: "Carrot"
            },
            icecream: "Ice cream"
        },
        inputPlaceholder: "Select a fruit",
        showCancelButton: true,
        inputValidator: (value) => {
            return new Promise((resolve) => {
                if (value === "oranges") {
                    resolve();
                } else {
                    resolve("You need to select oranges :)");
                }
            });
        }
    });
    if (fruit) {
        Swal.fire(`You selected: ${fruit}`);
    }
}


document.addEventListener("DOMContentLoaded", function () {
    if (sessionDetail === null || sessionDetail.data.customerId === null || sessionDetail.data.customerId === undefined) {
    } else {
        getAddress(sessionDetail.data.customerId);
    }
})


document.addEventListener("click", function (e) {
    let parentBtn = e.target.closest("button");

    if (e.target.classList.contains("editAddr") ) {
        // data-address-edit="${addrNum}
        editAddr();
    } else if (e.target.classList.contains("delAddr") ) {
        console.log(2);

        e.target.closest("tr").querySelectorAll("td").forEach(item => {
            if (item.dataset.memberid !== undefined) {
                getCmsMemberInfoById(item.dataset.memberid);
            }
        })
    }else{
        e.stopPropagation();
    }

})