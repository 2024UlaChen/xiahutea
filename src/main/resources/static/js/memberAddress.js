// console.log(window.DEFAULT_ADDRESS.data);
const citySet = new Set();
const addrSet = new Set();
const defaultCity = "台北市";
$.each(window.DEFAULT_ADDRESS.data, function (index, item) {
    citySet.add(item.city);
    addrSet.add(item.city + "-" + item.district);
})


const memberAddressUl = document.querySelector("#memberAddress");
const addrModalBody = document.querySelector("#addrModalBody");
const newAddrBtn = document.querySelector(".newAddr");
const saveAddrBtn = document.querySelector(".saveAddr");

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
                    <li data-addressId="${addrNum}">
                        <i class="fa fa-map-marker"></i>
                        <span>${item.customerAddress}</span>
                        <button class="editAddr" data-address-edit="${addrNum}" data-target="#addrModal" data-toggle="modal">
                            <i class="fa fa-pencil editAddr"></i>
                        </button>
                        <button class="delAddr" data-address-delete="${addrNum}">
                            <i class="fa fa-trash delAddr"></i>
                        </button>
                    </li>
                `;
            });
            addrList += `<hr>`
            memberAddressUl.innerHTML = addrList;
            // cmsAddrList.classList.add("hidden");
        })
}


function getDefaultCity(nowCity) {
    let cityList = "";
    let otherCityList = "";
    citySet.forEach((item) => {
        if (item === nowCity) {
            cityList += `<option value="${item}">${item}</option>`
        } else {
            otherCityList += `<option value="${item}">${item}</option>`
        }
    })
    cityList += otherCityList;
    return cityList;
}

function getDefaultDistrict(city) {
    let districtList = "";
    addrSet.forEach((item) => {
        if (item.includes(city)) {
            let modalDistrictList = item.split("-")[1];
            districtList += `<option value="${modalDistrictList}">${modalDistrictList}</option>`
        }
    })
    return districtList;
}

// 編輯現有地址
function editAddr(target) {
    let dataAddressId = target.closest("li").getAttribute("data-addressId");
    let nowAddr = target.closest("li").querySelector("span").innerText;
    let addrList = nowAddr.split("-");
    let modalBodyData = "";
    modalBodyData += `
            <span class="form-label"><span class="warn">*</span> 縣市區域 </span>
            <div>
                <div class="member-address col-sm-12 no-padding">
                    <select class="form-select col-lg-6 modalCity"
                            aria-label="Default select example" id="modalCity">
                        ${getDefaultCity(addrList[0])}
                    </select>
                    <select class="form-control-wrap form-select col-lg-6 modalDistrict"
                            aria-label="Default select example" id="modalDistrict">
                        <option selected>${addrList[1]}</option>
                        ${getDefaultDistrict(addrList[0])}
                    </select>
                </div>
            </div>
            <span class="form-label"><span class="warn">*</span> 詳細地址 </span>
            <div>
                <input title="detail address" type="text" name="your detail address" 
                size="100" id="nowDetailAddr" class="form-control" value="${addrList[2]}">
            <div>
    `;
    addrModalBody.innerHTML = modalBodyData;
    addrModalBody.setAttribute("data-addressId", dataAddressId);

}


document.addEventListener("DOMContentLoaded", function () {
    if (sessionDetail === null || sessionDetail.data.customerId === null || sessionDetail.data.customerId === undefined) {
    } else {
        getAddress(sessionDetail.data.customerId);
    }
})


document.addEventListener("click", function (e) {
    if (e.target.classList.contains("editAddr")) {
        editAddr(e.target);
    } else if (e.target.classList.contains("delAddr")) {
    } else {
        // e.stopPropagation();
    }
})

document.addEventListener("change", function (e) {
    let changeDistrict = document.querySelector("#modalDistrict");
    let changeDetailAddr = document.querySelector("#nowDetailAddr");

    if (e.target.classList.contains("modalCity")) {
        changeDetailAddr.value = "";
        changeDistrict.innerHTML = getDefaultDistrict(e.target.value);
    }
    if (e.target.classList.contains("modalDistrict")) {
        changeDetailAddr.value = "";
    }
})

// 新增地址
newAddrBtn.addEventListener("click", function () {
    let modalBodyData = "";
    modalBodyData += `
            <span class="form-label"><span class="warn">*</span> 縣市區域 </span>
            <div>
                <div class="member-address col-sm-12 no-padding">
                    <select class="form-select col-lg-6 modalCity"
                            aria-label="Default select example" id="modalCity">
                        ${getDefaultCity(defaultCity)}
                    </select>
                    <select class="form-control-wrap form-select col-lg-6 modalDistrict"
                            aria-label="Default select example" id="modalDistrict">
                            ${getDefaultDistrict(defaultCity)}
                    </select>
                </div>
            </div>
            <span class="form-label"><span class="warn">*</span> 詳細地址 </span>
            <div>
                <input title="detail address" type="text" name="your detail address"
                size="100" id="nowDetailAddr" class="form-control" >
            <div>
    `;
    addrModalBody.innerHTML = modalBodyData;
    addrModalBody.setAttribute("data-addressId", "default");
})

saveAddrBtn.addEventListener("click", function (e) {
    let modalBody = e.target.closest(".modal-content").querySelector("#addrModalBody");
    let saveCity = modalBody.querySelector("#modalCity").value;
    let saveDistrict = modalBody.querySelector("#modalDistrict").value;
    let saveDetailAddr = modalBody.querySelector("#nowDetailAddr").value;
    let finalAddr = saveCity + "-" + saveDistrict + "-" + saveDetailAddr;
    if (saveDetailAddr === "") {
        Swal.fire("詳細地址不可為空", "", "error");
    } else if (!saveDetailAddr.includes("路")) {
        Swal.fire("詳細地址必須包含路名", "", "error");
    } else {
        let sendDataAddressId = modalBody.getAttribute("data-addressId");
        if (sendDataAddressId === "default") {
            console.log(sendDataAddressId);
        } else {
            console.log(sendDataAddressId);
        }
    }
})