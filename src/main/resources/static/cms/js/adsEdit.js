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

    //***********************************接收localstorage資料*********************
    const adsData = JSON.parse(localStorage.getItem('adsData'));
    if(adsData){
        title_el.value = adsData.title;
        description_el.value = adsData.description;
        start_date_el.value = adsData.startDate;
        end_date_el.value = adsData.endDate;
    }
    if (adsData.homepageDisplay === '是') {
        check_homedisplay_el.checked = true;
    }
    if(adsData.status === '啟用'){
        check_active_el.checked = true;
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
    });
