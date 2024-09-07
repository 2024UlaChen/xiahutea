document.addEventListener("DOMContentLoaded", function () {
    let nowLocation = window.location.pathname;

    let memberAsideUl = document.querySelectorAll(".member-list li a");
    memberAsideUl.forEach((el) => {
        if (nowLocation.indexOf(el.getAttribute("href")) !== -1) {
            el.closest("li").classList.add("member-list-active");
            el.style.color = "#73B4BA";
        } else {
            el.addEventListener("mouseover", function () {
                el.style.color = "#FAC792";
                el.closest("li").style.backgroundColor = "#F8F9FA";
                el.closest("li").style.borderLeft = "solid 5px #FAC792";
            });
            el.addEventListener("mouseout", function () {
                el.style.color = "initial";
                el.closest("li").style.backgroundColor = "white";
                el.closest("li").style.borderLeft = "solid 5px white";
            })
        }
    })

    // memberCarrier
    const memberCarrierBarcode = document.querySelector("#memberCarrierBarcode");
    const memberCarrierText = document.querySelector("#memberCarrierText");
    let memberCarrierTextValue = "/XXX-AAA";
    memberCarrierText.innerText = memberCarrierTextValue;

    function getCarrier() {
        JsBarcode(memberCarrierBarcode, memberCarrierTextValue, {
            displayValue: false
        });
    };
    getCarrier();


    const carrierSaveBtn = document.querySelector("#carrierSaving");
    const carrierDelBtn = document.querySelector("#carrierDeleting");
    const carrierEditingCloseBtn = document.querySelector(".close");

    carrierSaveBtn.addEventListener("click", function () {
        carrierEditingCloseBtn.click();
        console.log("click");
        Swal.fire({
            position:"top",
            icon: "success",
            title: "Your work has been saved",
            showConfirmButton: false,
            timer: 1500
        });
    })

    // carrierDelBtn.addEventListener("click", function () {
    //
    //     const swalWithBootstrapButtons = Swal.mixin({
    //         customClass: {
    //             confirmButton: "btn btn-success",
    //             cancelButton: "btn btn-danger"
    //         },
    //         buttonsStyling: false
    //     });
    //     swalWithBootstrapButtons.fire({
    //         title: "刪除載具?",
    //         icon: "warning",
    //         showCancelButton: true,
    //         confirmButtonText: "刪除",
    //         cancelButtonText: "取消",
    //         reverseButtons: true
    //     }).then((result) => {
    //         if (result.isConfirmed) {
    //             swalWithBootstrapButtons.fire({
    //                 text: "已刪除載具",
    //                 icon: "success"
    //             });
    //         } else if (
    //             /* Read more about handling dismissals below */
    //             result.dismiss === Swal.DismissReason.cancel
    //         ) {
    //             swalWithBootstrapButtons.fire({
    //                 title: "Cancelled",
    //                 timer: 1000
    //             });
    //         }
    //     });
    //
    // });

});



