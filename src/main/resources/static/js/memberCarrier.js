// ASIDE
const asidePhoto = document.querySelector("#asidePhoto");
const asideName = document.querySelector("#asideName");

const carrierEditBtn = document.querySelector("#carrierEdit");
const carrierDeleteBtn = document.querySelector("#carrierDelete");

const memberCarrierBarcode = document.querySelector("#memberCarrierBarcode");
const memberCarrierText = document.querySelector("#memberCarrierText");

// TODO - 改抓memberID
// const memberId = 2;
function getMemberId() {
    const sessionDetail = JSON.parse(sessionStorage.getItem("memberData"));
    return parseInt(sessionDetail.data.customerId);
}
function nullToEmpty(data) {
    return (data == null) ? "" : data;
}

document.addEventListener("DOMContentLoaded", function () {
    getCarrier(getMemberId());
    fetch(`member`, {
        method: "POST",
    }).then(res => res.json())
        .then(data => {
            if (data.successful) {
                let userImg = (nullToEmpty(data.customerImg) === "") ? "" : `data:image/png;base64,${data.customerImg}`;
                asidePhoto.setAttribute("src", userImg);
                asideName.innerText = nullToEmpty(data.nickname);
            }
        })
});

function getCarrier(memberId) {
    fetch(`member/carrier/` + memberId)
        .then(res => res.text())
        .then(data => {
            if (data.length !== 0) {
                memberCarrierText.classList.remove("checkInValid");
                carrierDeleteBtn.classList.remove("hidden")
                memberCarrierText.innerText = data;
                JsBarcode(memberCarrierBarcode, data, {
                    displayValue: false
                });
            } else {
                memberCarrierText.classList.add("checkInValid");
                memberCarrierText.innerText = "未設定";
                carrierDeleteBtn.classList.add("hidden");
                memberCarrierBarcode.innerHTML = "";
            }
        });
}

function updateCarrier(newCarrierTxt, memberId, type) {
    fetch(`member/carrier?type=` + encodeURIComponent(type), {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            customerCarrier: newCarrierTxt,
            customerId: memberId,
            type: type
        }),
    }).then(res => res.json()).then(data => {
        if (data.successful) {
            Swal.fire("已更新載具", "", "success");
            getCarrier(getMemberId());
        } else {
            Swal.fire({
                icon: "error",
                title: data.message,
            });
        }
    })
}

// TODO - input font size
carrierEditBtn.addEventListener("click", function () {
    let inputValueTxt = (memberCarrierText.innerText === "未設定" ? "" : memberCarrierText.innerText);
    Swal.fire({
        title: "請輸入載具條碼，輸入確認後會自動產生條碼",
        input: "text",
        inputValue: inputValueTxt,
        inputPlaceholder: "/1234567",
        showCancelButton: true,
        width: 350,
        confirmButtonText: `<span class="sweetAlertFont">更新載具</span>`,
        cancelButtonText: `<span class="sweetAlertFont">取消修改</span>`,
        customClass: {
            input: 'sweetAlertFont'
        },
        inputAttributes: {
            maxlength: "8"
        },
        inputValidator: (value) => {
            if (!value.match("^\/[A-Z0-9+-\\.]{7}$")) {
                return `<span class="sweetAlertFont checkInValid">載具格式錯誤，請確認</span>`;
            }
        }
    }).then((result) => {
        if (result.isConfirmed) {
            updateCarrier(result.value, getMemberId(), "update");
        }
    })
})


carrierDeleteBtn.addEventListener("click", function () {
    Swal.fire({
        title: "確定刪除載具?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#73B4BA",
        cancelButtonColor: "#6c757d",
        confirmButtonText: `<span class="sweetAlertFont">是，確定刪除</span>`,
        cancelButtonText: `<span class="sweetAlertFont">否</span>`,
    }).then((result) => {
        if (result.isConfirmed) {
            updateCarrier("", getMemberId(), "delete");
        }
    });
})