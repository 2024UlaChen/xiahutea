document.addEventListener("DOMContentLoaded", function () {
    //抓取標籤
    let title_el = document.getElementById("title")
    let description_el = document.getElementById("description")
    let start_date_el = document.getElementById("start_date")
    let end_date_el = document.getElementById("end_date")
    let file_input_el = document.getElementById("imageUpload")
    let preview_container_el = document.getElementById('previewContainer')
    let btn_remove_el = document.getElementById("removeButton")
    let check_homedisplay_el = document.getElementById("check-homedisplay")
    let check_active_el = document.getElementById("check-active")
    let ads_form_el = document.getElementById('ads-form')
    let ads_ids_el = document.getElementById('ads-ids')
    let ishomedisplay;
    let isActive;

    // 取得 URL 中的 adId 參數
    let urlParams = new URLSearchParams(window.location.search);
    let adId = urlParams.get('adId');
    console.log("adid:",adId)
    //********************************確認是否為修改跳轉頁面**********************
    if (adId) {
        // 發送 GET 請求獲取優惠券資料
        fetch(`/advertise/edit/${adId}`,{
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(errorData => {
                        throw new Error(errorData.message);
                    });
                }
                return response.json();
            })
            .then(data=> {
                if(data.adsId ===null){
                    Swal.fire({
                        title: '查無此廣告資料',
                        text: '查無此廣告資料',
                        icon: 'error'
                    });
                    return;
                }
                console.log("ad:",data)
                ads_ids_el.value = data.adsId;
                title_el.value = data.title;
                description_el.textContent = data.description;
                start_date_el.value = formatToDatetimeLocal(data.startTime);
                end_date_el.value = formatToDatetimeLocal(data.endTime);
                if(data.homeDisplay === true){
                    check_homedisplay_el.checked = true;
                }
                if(data.isactive ===true){
                    check_active_el.checked = true;
                }
                // 將 Base64 圖片顯示在 img 標籤中
                if (data.imgUrl) {
                    // 創建一個新的 img 元素來預覽圖片
                    let img = document.createElement('img');
                    img.src = 'data:image/jpeg;base64,' + data.imgUrl;
                    img.classList.add('previewImage');
                    preview_container_el.innerHTML = ''; // 清空之前的預覽
                    preview_container_el.appendChild(img); // 將新圖片添加到預覽容器中
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
    //***********************************日期選擇限制******************************
    start_date_el.addEventListener('change', function () {
        let startDate = this.value;
        end_date_el.setAttribute('min', startDate);
    });
    end_date_el.addEventListener('change', function () {
        let endDate = this.value;
        start_date_el.setAttribute('max', endDate);
    });
    //***********************************placeholder處理******************************
    title_el.addEventListener("focus", function () {
        // 清空 placeholder
        title_el.placeholder = "";
    });
    title_el.addEventListener("blur", function () {
        // 如果輸入框為空，恢復 placeholder
        if (title_el.value === "") {
            title_el.placeholder = "可輸入60字元單位";
        }
    });
    description_el.addEventListener("focus", function () {
        // 清空 placeholder
        description_el.placeholder = "";
    });
    description_el.addEventListener("blur", function () {
        // 如果輸入框為空，恢復 placeholder
        if (description_el.value === "") {
            description_el.placeholder = "可輸入255字元單位";
        }
    });
    ///***********************************圖片上傳預覽******************************
    file_input_el.addEventListener('change', function(event) {
        let files = event.target.files;
        preview_container_el.innerHTML = '';

        let file = files[0]
        let reader = new FileReader();

        reader.onload = function(e) {
            let img = document.createElement('img');
            img.src = e.target.result;
            img.classList.add('previewImage');
            preview_container_el.appendChild(img);
        };
        reader.readAsDataURL(file);
    })

    btn_remove_el.addEventListener("click",function (){
        preview_container_el.innerHTML="";
        file_input_el.value="";
    })

    //***********************************上傳表單******************************
    ads_form_el.addEventListener('submit',function (event){
        event.preventDefault();
        let adfile = file_input_el.files[0];  // 選擇的圖片文件
        //檢查是否有填寫各欄位
        if (title_el.value === '') {
            Swal.fire({
                title: '錯誤',
                text: '請填寫廣告標題',
                icon: 'error'
            });
            return;
        }
        if (description_el.value === '') {
            Swal.fire({
                title: '錯誤',
                text: '請填寫廣告描述',
                icon: 'error'
            });
            return;
        }
        if(!start_date_el.value ||!end_date_el.value) {
            Swal.fire({
                title: '錯誤',
                text: '請填寫日期區間',
                icon: 'error'
            });
            return;
        }
        if(file_input_el.files.length ===0){
            Swal.fire({
                title: '錯誤',
                text: '請選擇圖片',
                icon: 'error'
            });
            return;
        }

        if(check_homedisplay_el.checked){
            ishomedisplay = 1
        }else {
            ishomedisplay = 0
        }
        if(check_active_el.checked){
            isActive = 1
        }else {
            isActive = 0
        }

        let formData = new FormData();
        formData.append('imageUpload', adfile);  // 文件
        formData.append('adsId', ads_ids_el.value);  // 其他表單字段
        formData.append('title', title_el.value);
        formData.append('description', description_el.value);
        formData.append('homeDisplay', ishomedisplay);
        formData.append('isActive', isActive);
        formData.append('startTime', formatToBackendFormat(start_date_el.value));
        formData.append('endTime', formatToBackendFormat(end_date_el.value));
        formData.append('adsTotalUserid',"1"); //先用假的
        formData.append('roleTypeId',"1");

        fetch('/advertise/save',{
            method:'POST',
            body: formData
            // headers: {
            //     'Content-Type': 'application/json'
            // },

            // body: JSON.stringify({
            //     adsId:ads_ids_el.value,
            //     title: title_el.value,
            //     description: description_el.textContent,
            //
            //     homeDisplay:ishomedisplay,
            //     isactive:isActive,
            //     startTime: formatToBackendFormat(start_date_el.value),
            //     endTime: formatToBackendFormat(end_date_el.value)
            }).then(response => {
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
                            window.location.href = 'adsEdit.html';
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
            });
        })
    // 將 yyyy/MM/dd HH:mm 格式轉換為 YYYY-MM-DDTHH:MM 格式
    function formatToBackendFormat(dateStr) {
        const [datePart, timePart] = dateStr.split('T');
        const [year, month, day] = datePart.split('-');
        return `${year}/${month}/${day} ${timePart}`;
    }
    // 將 yyyy/MM/dd HH:mm 格式轉換為 YYYY-MM-DDTHH:MM 格式，因為annotation@JsonFormat
    function formatToDatetimeLocal(dateStr) {
        const [datePart, timePart] = dateStr.split(' ');
        const [year, month, day] = datePart.split('/');
        return `${year}-${month}-${day}T${timePart}`;
    }
})



