let storeStatusEle = $("#storeStatus");
let storeIdEle = $("#storeId")
let form = $("#form");
let phoneError = $("#phoneError");
let contactPhoneEle = $("#contactPhone")

// 從網址抓參數
function getParameter(parameter){
    let getUrlString = location.href;
    let url = new URL(getUrlString);
    return url.searchParams.get(parameter)
}

// 從後端抓資料渲染到前端
function init(){
    let urlStoreId = getParameter("storeId");
    fetch(`/registerstore/registerStoredetail?storeId=${urlStoreId}`)
        .then(res => res.json())
        .then(data => {
            $(storeIdEle).val(data.storeId);
            $("#storeName").val(data.storeName);
            $("#storeAddress").val(data.storeAddress);
            $("#vat").val(data.vat);
            $("#contactPerson").val(data.contactPerson);
            $("#contactPhone").val(data.contactPhone);
            $("#email").val(data.email);
            $("#storeRemark").val(data.storeRemark);
            $("#owner").val(data.contactPerson);

            if(data.storeStatus == 0){
                $(storeStatusEle).val(0);
                $(storeStatusEle).removeAttr("disabled");
            }else{
                $(storeStatusEle).val(4);
                $(storeStatusEle).attr("disabled",true);
            }
        })
}

//進入頁面就抓資料
$(function () {
    init();
})

//點擊列印按鈕
$("#print").on("click",e=>{
    e.preventDefault();
    window.print();
})

//點擊修改按鈕
$("#modifyBtn").on("click",e=>{
    e.preventDefault();
    $(".form-control").removeAttr("disabled")
    // $("textarea#storeRemark").removeAttr("disabled");
    $("input#vat").attr("disabled",true);
    $(storeIdEle).attr("disabled",true)
})

//點擊送出按鈕
$(form).submit(e => {
    e.preventDefault();
    $(phoneError).text("")
    $(".form-control").removeAttr("disabled")
    let contactPhone = $(contactPhoneEle).val();
    let formData = new FormData(e.target);

    //確認電話
    if (!phoneCheck(contactPhone)) {
        $(phoneError).text(" ***連絡電話錯誤***")
        return
    }

    fetch("/registerstore/edit", {
        method: "POST",
        body: formData
    }).then(res => res.json()).then(data => {
        console.log(data.successful);
        console.log(data.message);
        if (data.successful) {
            swal.fire("更新成功!", "", "success");
        } else {
            swal.fire("更新失敗!", data.message, "warning")
        }
    })
    $(".form-control").attr("disabled",true);

    if($(storeStatusEle).val() == 0) {
        $(storeStatusEle).removeAttr("disabled");
    }

})


//點擊寄信