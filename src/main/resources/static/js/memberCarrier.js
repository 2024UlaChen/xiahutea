const carrierEditBtn = document.querySelector("#carrierEdit");
const carrierDeleteBtn = document.querySelector("#carrierDelete");

const memberCarrierBarcode = document.querySelector("#memberCarrierBarcode");
const memberCarrierText = document.querySelector("#memberCarrierText");


// carrierSaveBtn.addEventListener("click", function () {
//     carrierEditingCloseBtn.click();
//     console.log("click");
//     Swal.fire({
//         position: "top",
//         icon: "success",
//         title: "Your work has been saved",
//         showConfirmButton: false,
//         timer: 1500
//     });
// })


function getCarrier(memberId) {
    fetch(`member/carrier/` + memberId)
        .then(res => res.text())
        .then(data => {
            data = "/123-456";
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
                carrierDeleteBtn.classList.add("hidden")
            }
        });
};

document.addEventListener("DOMContentLoaded", function () {
    // TODO - change member id
    getCarrier(2);

});

function updateCarrier() {

}

// TODO - input font size
carrierEditBtn.addEventListener("click", function () {
    let inputValueTxt = (memberCarrierText.innerText === "未設定" ? "" : memberCarrierText.innerText);
    Swal.fire({
        title: "請輸入載具條碼，輸入確認後會自動產生條碼",
        input: "text",
        inputValue: inputValueTxt,
        inputPlaceholder: "/123-456",
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
            if (!value.match("^\/[0-9A-Z]{3}-[0-9A-Z]{3}$")) {
                return `<span class="sweetAlertFont checkInValid">載具格式錯誤，請確認</span>`;
            }
        }
    }).then((result) => {
        if (result.isConfirmed) {
            //TODO - update carrier
            Swal.fire("已更新載具", "", "success");
        }
    })

})


