// 從網址抓參數
function getParameter(parameter){
    let getUrlString = location.href;
    let url = new URL(getUrlString);
    return url.searchParams.get(parameter)
}
// 從後端抓資料渲染到前端
function init(){
    let storeId = getParameter("storeId");
    console.log(storeId)
    fetch(`/registerstore/registerStoreDetail?storeId=${storeId}`)
        .then(res => res.json())
        .then(data => {
            $("#storeName").val(data.storeName);
            $("#storeAddress").val(data.storeAddress);
            $("#vat").val(data.vat);
            $("#contactPerson").val(data.contactPerson);
            $("#contactPhone").val(data.contactPhone);
            $("#email").val(data.email);
            $("#storeRemark").val(data.storeRemark);
            $("#owner").val(data.contactPerson);
            if(data.storeStatus==0){
                $("#storeStatus").val(0);
            }else{
                $("#storeStatus").val(4);
            }
        })
}

$("#print").on("click",e=>{
    e.preventDefault();
    window.print();
})

//一進入就抓資料
$(function () {
    init();
})