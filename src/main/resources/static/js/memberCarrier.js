// ASIDE
const asidePhoto = document.querySelector("#asidePhoto");
const asideName = document.querySelector("#asideName");

const carrierEditBtn = document.querySelector("#carrierEdit");
const carrierDeleteBtn = document.querySelector("#carrierDelete");
const carrierDownloadBtn = document.querySelector("#carrierDownload");

const memberCarrierBarcodeBlock = document.querySelector("#memberCarrierBarcodeBlock");

const memberCarrierText = document.querySelector("#memberCarrierText");

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
            console.log(data);
            if (data.length !== 0) {
                memberCarrierText.classList.remove("checkInValid");
                carrierDeleteBtn.classList.remove("hidden");
                carrierDownloadBtn.classList.remove("hidden");
                memberCarrierText.innerText = data;
                memberCarrierBarcodeBlock.innerHTML=`<canvas id="memberCarrierBarcode"></canvas>`
                JsBarcode(document.querySelector("#memberCarrierBarcode"), data, {
                    displayValue: false
                });
            } else {
                memberCarrierText.classList.add("checkInValid");
                memberCarrierText.innerText = "未設定";
                carrierDeleteBtn.classList.add("hidden");
                carrierDownloadBtn.classList.add("hidden");
                memberCarrierBarcodeBlock.innerHTML = "";

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
            Swal.fire({
                title: '已更新載具',
                icon: 'success',
                confirmButtonColor: '#73B4BA' // 設定確認按鈕顏色
            });
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
        title: "請輸入載具條碼" + "\n" + "輸入確認後會自動產生條碼",
        input: "text",
        inputValue: inputValueTxt,
        inputPlaceholder: "/1234567",
        showCancelButton: true,
        width: 350,
        confirmButtonColor: "#73B4BA",
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
carrierDownloadBtn.addEventListener("click", function () {
    let link = document.createElement('a');
    link.download = 'carrier.jpg';
    link.href = document.querySelector("#memberCarrierBarcode").toDataURL("image/jpeg");
    link.click();
})
