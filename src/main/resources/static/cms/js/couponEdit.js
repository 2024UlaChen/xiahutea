document.addEventListener("DOMContentLoaded",function () {
    //定義標籤
    let coupon_title_el = document.getElementById("input-coupon-title")
    let coupon_id_el = document.getElementById("input-coupon-id")
    let input_coupon_id_el =document.getElementById('input-coupon-id')
    let coupon_start_date_el = document.getElementById('coupon-start-date')
    let coupon_end_date_el = document.getElementById('coupon-end-date')
    // let no_limit_el = document.getElementById("no-limit")
    // let is_limit_el = document.getElementById("is-limit")
    // let limit_amount_el = document.getElementById("limit-amount")
    let active_el = document.getElementById("active")
    let no_active_el = document.getElementById("no-active")
    let coupon_discount_el = document.getElementById("input-coupon-discount")
    let coupon_detail_el = document.getElementById("coupon-detail")
    let coupon_form_el = document.getElementById("coupon-form")
    let response_message_el = document.getElementById('response-message')
    let couponStatus;
    let startDate;
    let endDate;
    // 取得 URL 中的 couponId 參數
    let urlParams = new URLSearchParams(window.location.search);
    let couponId = urlParams.get('couponId');

    //********************************確認是否為修改跳轉頁面**********************
    if (couponId) {
        // 發送 GET 請求獲取優惠券資料
        fetch(`/coupon/edit/${couponId}`)
            .then(response => {
                if (!response.ok) {
                    return response.json().then(errorData => {
                        throw new Error(errorData.message);
                    });
                }
                return response.json();
            })
            .then(data=> {
                coupon_id_el.value = data.couponId;
                coupon_title_el.value = data.couponName;
                coupon_detail_el.textContent = data.couponRule;
                coupon_discount_el.value = data.discount;
                coupon_start_date_el.value = formatToDatetimeLocal(data.createDate);
                coupon_end_date_el.value = formatToDatetimeLocal(data.expiredDate);
                if(data.couponStatus === true){
                    active_el.checked = true;
                }else{
                    no_active_el.checked = true;
                }
            })
            .catch(error => {
                Swal.fire({
                    title: '取得數據異常',
                    text: error.message,
                    icon: 'error'
                });
            });
    }
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
    // is_limit_el.addEventListener("click", function () {
    //     limit_amount_el.disabled = false;
    //     limit_amount_el.focus();
    // })
    // no_limit_el.addEventListener("click", function () {
    //     limit_amount_el.value = "";
    //     limit_amount_el.disabled = true;
    // });
    //********************************表單送出*********************************
    coupon_form_el.addEventListener('submit', function (event) {
        event.preventDefault();
        let activeChecked = active_el.checked;
        let noactiveChecked = no_active_el.checked;
        // let noLimitChecked = no_limit_el.checked;
        // let isLimitChecked = is_limit_el.checked;
        //檢查是否有填寫各欄位
        if (coupon_title_el.value === '') {
            Swal.fire({
                title: '錯誤',
                text: '請填寫優惠券標題',
                icon: 'error'
            });
            return;
        }
        if (coupon_detail_el.value === '') {
            Swal.fire({
                title: '錯誤',
                text: '請填寫優惠券說明',
                icon: 'error'
            });
            return;
        }
        // 檢查是否至少選擇了一個選項
        // if (!noLimitChecked && !isLimitChecked) {
        //     swal({
        //         title: '錯誤',
        //         text: '請選擇有效張數的選項',
        //         icon: 'error'
        //     });
        //     return;
        // }
        if (!activeChecked && !noactiveChecked) {
            Swal.fire({
                title: '錯誤',
                text: '請選擇是否啟用',
                icon: 'error'
            });
            return;
        }
        // if (activeChecked.checked && (limit_amount_el.value === '' || limit_amount_el.value === '0')) {
        //     swal({
        //         title: '錯誤',
        //         text: '請填寫有限張數',
        //         icon: 'error'
        //     });
        //     return;
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
                createDate: formatToBackendFormat(coupon_start_date_el.value),
                expiredDate: formatToBackendFormat(coupon_end_date_el.value)
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
                    Swal.fire({
                        title: '提交成功',
                        text: '已成功新增/修改資料',
                        icon: 'success',
                        confirmButtonText: '確定' // 可以設定確認按鈕的文本
                    }).then((result) => {
                        // result.isConfirmed 表示用戶點擊了確認按鈕
                        if (result.isConfirmed) {
                            window.location.href = 'couponEdit.html';
                        }
                    });
                } else {
                    Swal.fire({
                        title: '提交失敗',
                        text: body.message,
                        icon: 'error'
                    });

                }
            })
            .catch(error => {
                Swal.fire({
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
        //*************************************檢查內容並更新 placeholder****************************
        function checkDetailContent() {
            const detailData = coupon_detail_el.value.trim();
            if (detailData === "") {
                coupon_detail_el.placeholder = "請輸入優惠券說明....";
            } else {
                coupon_detail_el.placeholder = "";
            }
        }
        // 將 YYYY-MM-DDTHH:MM 格式轉換為 yyyy/MM/dd HH:mm 格式
        function formatToBackendFormat(dateStr) {
            const [datePart, timePart] = dateStr.split('T');
            const [year, month, day] = datePart.split('-');
            return `${year}/${month}/${day} ${timePart}`;
        }
        // 將 yyyy/MM/dd HH:mm 格式轉換為 YYYY-MM-DDTHH:MM 格式
        function formatToDatetimeLocal(dateStr) {
            const [datePart, timePart] = dateStr.split(' ');
            const [year, month, day] = datePart.split('/');
            return `${year}-${month}-${day}T${timePart}`;
        }
    })



