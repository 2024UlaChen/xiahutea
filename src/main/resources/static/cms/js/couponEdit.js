document.addEventListener("DOMContentLoaded",function (){
    //定義標籤
    let coupon_start_date_el  = document.getElementById('coupon-start-date')
    let coupon_end_date_el  = document.getElementById('coupon-end-date')
    let no_limit_el = document.getElementById("no-limit")
    let is_limit_el = document.getElementById("is-limit")
    let limit_amount_el = document.getElementById("limit-amount")
    document.getElementById('end-date')







    //按鈕事件綁定
    //日期選擇限制
    coupon_start_date_el.addEventListener('change', function () {
        var startDate = this.value;
        coupon_end_date_el.setAttribute('min', startDate);
    });
    coupon_end_date_el.addEventListener('change', function () {
        var endDate = this.value;
        coupon_start_date_el.setAttribute('max', endDate);
    });
    //選擇限制張數
    is_limit_el.addEventListener("click",function (){
        limit_amount_el.disabled = false;
        limit_amount_el.focus();
    })
    no_limit_el.addEventListener("click",function (){
        limit_amount_el.value="";
        limit_amount_el.disabled = true;
    })







})