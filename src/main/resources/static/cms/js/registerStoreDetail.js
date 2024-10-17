let storeStatusEle = $("#storeStatus");
let storeIdEle = $("#storeId")
let form = $("#form");
let phoneError = $("#phoneError");
let contactPhoneEle = $("#contactPhone")
let formControlEle = $(".form-control")
let urlStoreId = getParameter("storeId");

// 從網址抓參數
function getParameter(parameter){
    let getUrlString = location.href;
    let url = new URL(getUrlString);
    return url.searchParams.get(parameter)
}

// 從後端抓資料渲染到前端
function init(){

    fetch(`/registerstore/registerstoredetail?storeId=${urlStoreId}`)
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

            if(data.storeStatus === 0){
                $(storeStatusEle).val(0);
                $(storeStatusEle).removeAttr("disabled");
            }else{
                $(storeStatusEle).val(4);
                $(storeStatusEle).attr("disabled",true);
            }
        })
    $("#submitBtn").attr("disabled",true);
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
    $("input#vat").attr("disabled",true);
    $(storeIdEle).attr("disabled",true)
    $("#submitBtn").removeAttr("disabled");
})

//點擊送出按鈕
$(form).submit(e => {
    e.preventDefault();
    $(phoneError).text("")
    $(formControlEle).removeAttr("disabled")
    let contactPhone = $(contactPhoneEle).val();
    let formData = new FormData(e.target);

    //確認電話
    if (!phoneCheck(contactPhone)) {
        $(phoneError).text(" ***連絡電話錯誤***")
        return
    }

    //渲染前端
    fetch("/registerstore/edit", {
        method: "POST",
        body: formData
    }).then(res => res.json()).then(data => {
        if (!data.successful) {
            swal.fire("更新失敗!", data.message, "warning")
            return;
        }
        if(data.storeStatus === 1){
            swal.fire("更新成功",data.storeName + "已成為正式店家", "success").then((result)=>{
                location.replace("./registerStore.html")
                });
        }else if (data.storeStatus === 0){
            swal.fire("更新成功!", data.storeName + "尚在申請中", "success")
                .then(result =>{history.back()})
        }else{
            swal.fire("更新成功!", data.storeName + "被拒絕申請", "success")
                .then(result =>{history.back()})
        }

    })

    //鎖住資料
    $(formControlEle).attr("disabled",true);

    //如果狀態為申請中，就可以再變動
    if($(storeStatusEle).val() === "0") {
        $(storeStatusEle).removeAttr("disabled");
    }

    // 鎖住送出按鈕
    $("#submitBtn").attr("disabled",true);
})

//變更店家狀態即可送出
$(storeStatusEle).change(function(e){
    // console.log($(this).val());
    if ($(this).val() !== 0 && $(contactPhoneEle).attr("disabled") === "disabled" ){
        $("#submitBtn").removeAttr("disabled");
    }
})


