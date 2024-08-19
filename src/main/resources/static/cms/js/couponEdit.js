document.addEventListener("DOMContentLoaded",function () {
    //定義標籤
    let coupon_start_date_el = document.getElementById('coupon-start-date')
    let coupon_end_date_el = document.getElementById('coupon-end-date')
    let no_limit_el = document.getElementById("no-limit")
    let is_limit_el = document.getElementById("is-limit")
    let limit_amount_el = document.getElementById("limit-amount")
    let coupon_detail_el = document.getElementById("coupon-detail")



    //按鈕事件綁定
    //textarea預設placeholder
    coupon_detail_el.addEventListener('input', checkDetailContent);
    coupon_detail_el.addEventListener('blur', checkDetailContent);
    checkDetailContent()
// 初始檢查
    checkDetailContent();
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
    is_limit_el.addEventListener("click", function () {
        limit_amount_el.disabled = false;
        limit_amount_el.focus();
    })
    no_limit_el.addEventListener("click", function () {
        limit_amount_el.value = "";
        limit_amount_el.disabled = true;
    });
    //表單必填選項未填寫
    document.getElementById('coupon-form').addEventListener('submit', function (event) {
        event.preventDefault();

        const noLimitChecked = document.getElementById('no-limit').checked;
        const isLimitChecked = document.getElementById('is-limit').checked;
        const activeChecked = document.getElementById('active').checked;
        const noactiveChecked = document.getElementById('no-active').checked;

        // 檢查是否至少選擇了一個選項
        if (!activeChecked && !noactiveChecked) {
            alert('請選擇是否啟用');
            return;
        }
        if (!noLimitChecked && !isLimitChecked) {
            alert('請選擇有效張數的選項。');
            return;
        }

})
    // 準備表單資料
    const formData = new FormData(document.getElementById('coupon-form'));

    // 發送 AJAX 請求
    fetch('your-server-endpoint-url', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            // 驗證伺服器回應的狀態碼
            if (!response.ok) {
                throw new Error(`伺服器錯誤: ${response.status}`);
            }
            return response.json(); // 假設伺服器返回的是 JSON
        })
        .then(data => {
            if (data.success) { // 假設伺服器回應中有一個 success 字段來標示成功
                document.getElementById('response-message').textContent = '表單提交成功！';
            } else {
                document.getElementById('response-message').textContent = '伺服器處理失敗，請稍後再試。';
            }
        })
        .catch(error => {
            document.getElementById('response-message').textContent = `提交失敗: ${error.message}`;
            console.error('Error:', error);
        });


    //*************************************FUNCTION*****************************************
    // 檢查內容並更新 placeholder
    function checkDetailContent() {
        const detailData = coupon_detail_el.value.trim();
        if (detailData === "") {
            coupon_detail_el.placeholder = "請輸入優惠券說明....";
        } else {
            coupon_detail_el.placeholder = "";
        }
    }
});


