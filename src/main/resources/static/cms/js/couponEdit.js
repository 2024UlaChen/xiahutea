document.addEventListener("DOMContentLoaded",function () {
    //定義標籤
    let coupon_title_el = document.getElementById("input-coupon-title")
    let coupon_id_el = document.getElementById("input-coupon-id")
    let input_coupon_id_el =document.getElementById('input-coupon-id')
    let coupon_start_date_el = document.getElementById('coupon-start-date')
    let coupon_end_date_el = document.getElementById('coupon-end-date')
    let no_limit_el = document.getElementById("no-limit")
    let is_limit_el = document.getElementById("is-limit")
    let limit_amount_el = document.getElementById("limit-amount")
    let active_el = document.getElementById("active")
    let no_active_el = document.getElementById("no-active")
    let coupon_discount_el = document.getElementById("input-coupon-discount")
    let coupon_detail_el = document.getElementById("coupon-detail")
    let coupon_form_el = document.getElementById("coupon-form")
    let response_message_el = document.getElementById('response-message')
    let couponStatus;
    let startDate;
    let endDate;


    //***********************************接收localstorage資料*********************

    const couponData = JSON.parse(localStorage.getItem('couponData'));
    if (couponData) {
        coupon_title_el.value = couponData.title;
        coupon_id_el.value = couponData.id;
        coupon_start_date_el.value = couponData.startDate;
        coupon_end_date_el.value = couponData.endDate;
        coupon_discount_el.value = couponData.discount;
        if (couponData.status === '啟用') {
            active_el.checked = true;
        } else {
            no_active_el.checked = true;
        }
    }

    localStorage.clear();

    //***********************************檢查placeholder*********************
    coupon_detail_el.addEventListener('input', checkDetailContent);
    coupon_detail_el.addEventListener('blur', checkDetailContent);

    checkDetailContent();
    //***********************************日期選擇限制*************************
    coupon_start_date_el.addEventListener('change', function () {
        var startDate = this.value;
        coupon_end_date_el.setAttribute('min', startDate);
    });
    coupon_end_date_el.addEventListener('change', function () {
        var endDate = this.value;
        coupon_start_date_el.setAttribute('max', endDate);
    });
    //**********************************選擇限制張數*************************
    is_limit_el.addEventListener("click", function () {
        limit_amount_el.disabled = false;
        limit_amount_el.focus();
    })
    no_limit_el.addEventListener("click", function () {
        limit_amount_el.value = "";
        limit_amount_el.disabled = true;
    });
    //********************************表單必填選項未填寫*************************
    coupon_form_el.addEventListener('submit', function (event) {
        event.preventDefault();
        let activeChecked = active_el.checked;
        let noactiveChecked = no_active_el.checked;
        let noLimitChecked = no_limit_el.checked;
        let isLimitChecked = is_limit_el.checked;
        //檢查是否有填寫各欄位
        if (coupon_title_el.value === '') {
            swal({
                title: '錯誤',
                text: '請填寫優惠券標題',
                icon: 'error'
            });
            return;
        }
        if (coupon_detail_el.value === '') {
            swal({
                title: '錯誤',
                text: '請填寫優惠券說明',
                icon: 'error'
            });
            return;
        }
        // 檢查是否至少選擇了一個選項
        if (!noLimitChecked && !isLimitChecked) {
            swal({
                title: '錯誤',
                text: '請選擇有效張數的選項',
                icon: 'error'
            });
            return;
        }
        if (!activeChecked && !noactiveChecked) {
            swal({
                title: '錯誤',
                text: '請選擇是否啟用',
                icon: 'error'
            });
            return;
        }
        if (activeChecked.checked && (limit_amount_el.value === '' || limit_amount_el.value === '0')) {
            swal({
                title: '錯誤',
                text: '請填寫有限張數',
                icon: 'error'
            });
            return;
        }
        //處理起始時間、結束時間
        // if (coupon_start_date_el.value) {
        //     startDate = coupon_start_date_el.value + ' 00:00:00';
        // }
        // if (coupon_end_date_el.value) {
        //     endDate = coupon_end_date_el.value + ' 00:00:00';
        // }
        //判斷是否啟用
        if (active_el.checked) {
            couponStatus = true;
        } else if (no_active_el.checked) {
            couponStatus = false;
        }
        response_message_el.textContent = '';

        fetch('/coupon/save', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                couponId:input_coupon_id_el.value,
                couponName: coupon_title_el.value,
                couponRule: coupon_detail_el.value,
                discount: coupon_discount_el.value,
                couponStatus: couponStatus,
                createDate: coupon_start_date_el.value,
                expiredDate: coupon_end_date_el.value
            })
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(errorData => {
                        throw new Error(errorData.message);
                    });
                }
                return response.json();
            })
            .then(body => {
                if (body.successful) {
                    swal({
                        title: '提交成功',
                        text: '已成功新增/修改資料',
                        icon: 'success'
                    });
                } else {
                    swal({
                        title: '提交失敗',
                        text: body.message,
                        icon: 'error'
                    });
                }
            })
            .catch(error => {
                swal({
                    title: '網路交換數據異常',
                    text: error.message,
                    icon: 'error'
                });
                // // 表單提交邏輯
                // Swal.fire({
                //     icon: 'success',
                //     title: '成功',
                //     text: '表單已提交。',
                // });
            });
    })
        //*******************************準備表單資料發送 AJAX 請求************************
        // const formData = new FormData(document.getElementById('coupon-form'));
        // fetch('coupon/save', {
        //     method: 'POST',
        //     body: formData
        // })
        //     .then(response => {
        //         // 驗證伺服器回應的狀態碼
        //         if (!response.ok) {
        //             throw new Error(`伺服器錯誤: ${response.status}`);
        //         }
        //         return response.json(); // 假設伺服器返回的是 JSON
        //     })
        //     .then(data => {
        //         if (data.success) { // 假設伺服器回應中有一個 success 字段來標示成功
        //             document.getElementById('response-message').textContent = '表單提交成功！';
        //         } else {
        //             document.getElementById('response-message').textContent = '伺服器處理失敗，請稍後再試。';
        //         }
        //     })
        //     .catch(error => {
        //         document.getElementById('response-message').textContent = `提交失敗: ${error.message}`;
        //         console.error('Error:', error);
        //     });
        //*************************************檢查內容並更新 placeholder****************************
        function checkDetailContent() {
            const detailData = coupon_detail_el.value.trim();
            if (detailData === "") {
                coupon_detail_el.placeholder = "請輸入優惠券說明....";
            } else {
                coupon_detail_el.placeholder = "";
            }
        }
    })



