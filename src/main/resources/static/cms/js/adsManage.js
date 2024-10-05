document.addEventListener("DOMContentLoaded", function () {
    //**************************************定義變數及標籤**********************
    // 每頁顯示的行數及當前頁面
    const rowsPerPage = 10;
    let currentPage = 1;
    // 所有表格行
    let tableBody_el = document.getElementById('tablebody')
    let tableBody = document.getElementsByClassName("ads-table")[0];
    let rows = Array.from(tableBody.getElementsByTagName('tr'));
    let totalRows = rows.length;
    let totalPages = Math.ceil(totalRows / rowsPerPage);
    let result_count_el = document.getElementById("result-count")
    let pagination_el = document.getElementById('pagination');
    //上下頁按鈕
    let btn_prev_el = document.getElementById('prev-btn');
    let btn_next_el = document.getElementById('next-btn');
    //查詢按鈕
    let find_ads_el = document.getElementById('find-ads');
    //用來存後端抓到的全部廣告
    let ads;
    let filteredAds;
    //用來存抓到的店家
    let onestore
    let stores;
    //用來存用戶ID、roletype
    let userid;
    let roletypeid;
    //RADIO
    let active_el = document.getElementById('active')
    let no_active_el = document.getElementById('no-active')

    //抓角色類型
    fetch(`/advertise/manage/getrole`, {
        })
        .then(response => {
            if (!response.ok) {
                return response.json().then(errorData => {
                    throw new Error(errorData.message);
                });
            }
            return response.json();
        })
        .then(totaluser => {
            if (totaluser == null) {
                Swal.fire({
                    title: '查無此使用者資料',
                    text: '查無此使用者資料 ID=',
                    icon: 'error'
                });
                return;
            }
            console.log('user Details:', totaluser);
            userid = totaluser.userId;
            roletypeid =totaluser.userTypeId;
            //管理員和商家分別顯示
            if (totaluser.userTypeId === 1) {
                getstoread(userid)
            } else if (totaluser.userTypeId === 3) {
                getallads();
            }else{
                Swal.fire({
                    title: '請登入商家或管理員',
                    text: '請登入商家或管理員',
                    icon: 'info'
                })
                    .then(() => {
                        // 確認後跳轉到登入頁面或其他指定頁面
                        window.location.href = 'backstageLogin.html';
                    });
                }
            })
    //************************************依照角色類型抓資料-店家**********************
    function getstoread(userid){
        fetch(`/advertise/manage/${userid}`,{
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(errorData => {
                        throw new Error(errorData.message);
                    });
                }
                return response.json();
            })
            .then(data=>{
                filteredAds = data.filter(ad => ad.roleTypeId === roletypeid);
                findonestorename(userid)
            })
    }
    //************************************依照角色類型抓資料-管理員*********************
    function getallads() {
        fetch('/advertise/manage/all', {})
            .then(response => {
                if (!response.ok) {
                    return response.json().then(errorData => {
                        throw new Error(errorData.message);
                    });
                }
                return response.json();
            })
            .then(data => {
                ads = data;
                // 提取所有的 ads_total_users_id
                let totalUserIds = ads.map(ad => ad.adsTotalUserid);
                //抓所有商家+渲染
                findstorename(totalUserIds)
            })
            .catch(error => {
                Swal.fire({
                    title: '取得數據異常',
                    text: error.message,
                    icon: 'error'
                });
            })
    }
    //**************************************抓取單一商家名稱&渲染**************************
    function findonestorename(userid) {
        fetch(`/advertise/manage/findstore/${userid}`, {
        }).then(response => {
            if (!response.ok) {
                return response.json().then(errorData => {
                    throw new Error(errorData.message);
                });
            }
            return response.json();
        })
            .then(data => {
                onestore = data;
                //渲染資料
                showstoreads(filteredAds);
            })
            .catch(error => {
                Swal.fire({
                    title: '取得數據異常',
                    text: error.message,
                    icon: 'error'
                });
            })
    }

    //**************************************抓取全部商家名稱&渲染全部**************************
    function findstorename(userids) {
        fetch(`/advertise/manage/findstore`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userids)
        }).then(response => {
            if (!response.ok) {
                return response.json().then(errorData => {
                    throw new Error(errorData.message);
                });
            }
            return response.json();
        })
            .then(data => {
                stores = data;
                //渲染資料
                showAllads(ads);
            })
            .catch(error => {
                Swal.fire({
                    title: '取得數據異常',
                    text: error.message,
                    icon: 'error'
                });
            })
    }
    //**************************************渲染頁面(單店家)*****************************
    function showstoreads(ads) {
        tableBody_el.innerHTML = '';
        ads.forEach(ad => {
            const adStatus = ad.isactive ? '啟用' : '未啟用';
            const isdisplay = ad.homeDisplay ? '是' : '否';
            // 根據 ads_total_users_id 找到對應的 store
            let storeName = '無對應商家';
            if (onestore) {
                storeName = onestore.storeName;
            }
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${storeName}</td>
                <td>${ad.title}</td>
                <td>${ad.description}</td>
                <td>${ad.startTime}</td>
                <td>${ad.endTime}</td>
                <td>${isdisplay}</td>
                <td>${adStatus}</td>
                <td>
                    <button type="button" class="td-edit" data-id="${ad.adsId}">修改</button>
                </td>
            `;
            tableBody_el.appendChild(row);
        })
        // 初始顯示第一頁並創建分頁按鈕，顯示總筆數
        rows = Array.from(tableBody_el.getElementsByTagName('tr'));
        totalRows = rows.length;
        totalPages = Math.ceil(totalRows / rowsPerPage);
        result_count_el.textContent = `共計 ${totalRows} 筆`;
        renderTable(currentPage, totalPages);
    }

    //**************************************渲染頁面(全)*****************************
    function showAllads(ads) {
        tableBody_el.innerHTML = '';
        ads.forEach(ad => {
            const adStatus = ad.isactive ? '啟用' : '未啟用';
            const isdisplay = ad.homeDisplay ? '是' : '否';
            // 根據 ads_total_users_id 找到對應的 store
            let storeName = '無對應商家';
            let store = stores.find(s => s.storeId === ad.adsTotalUserid); // 根據 storeId 對應
            if (store) {
                storeName = store.storeName;
            }
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${storeName}</td>
                <td>${ad.title}</td>
                <td>${ad.description}</td>
                <td>${ad.startTime}</td>
                <td>${ad.endTime}</td>
                <td>${isdisplay}</td>
                <td>${adStatus}</td>
                <td>
                    <button type="button" class="td-edit" data-id="${ad.adsId}">修改</button>
                </td>
            `;
            tableBody_el.appendChild(row);
        })
        // 初始顯示第一頁並創建分頁按鈕，顯示總筆數
        rows = Array.from(tableBody_el.getElementsByTagName('tr'));
        totalRows = rows.length;
        totalPages = Math.ceil(totalRows / rowsPerPage);
        result_count_el.textContent = `共計 ${totalRows} 筆`;
        renderTable(currentPage, totalPages);
    }

    //**************************************搜尋功能*****************************
    find_ads_el.addEventListener('click', function () {
        const titleInput = document.getElementById('input-ads-title').value.trim();
        const storenameInput = document.getElementById('input-ads-id').value.trim();
        const statusChecked = document.querySelector('input[name="check-active"]:checked');
        let filteredads = ads;  // 使用全部ads 來進行篩選

        // 篩選條件
        if (titleInput) {
            filteredads = filteredads.filter(ad => ad.title.includes(titleInput));
        }
        console.log("filteredads:", filteredads)
        if (storenameInput) {
            // 從 stores 中找到符合輸入名稱的商家
            const filteredStores = stores.filter(store => store.storeName.includes(storenameInput));
            // 提取符合條件的商家 storeId 列表
            const storeIds = filteredStores.map(store => store.storeId);
            // 根據 storeId 來篩選廣告
            filteredads = filteredads.filter(ad => storeIds.includes(ad.adsTotalUserid));
        }
        if (statusChecked) {
            const isActive = statusChecked.id === 'active';
            filteredads = filteredads.filter(ad => ad.isactive === isActive);
        }
        // 清空表格並顯示篩選結果
        // let filtereradsresult = filteredads.map(ad => ad.adsTotalUserid);
        showAllads(filteredads);
    })

    //**************************************修改表格資料*****************************
    document.addEventListener('click', function (e) {
        if (e.target.classList.contains('td-edit')) {
            let adId = e.target.getAttribute('data-id');
            if (adId && adId !== 'undefined') {
                window.location.href = `adsEdit.html?adId=${adId}`;
            } else {
                window.location.href = `adsEdit.html`;
            }
        }
    });
    //**************************************表格分頁*****************************
    // 初始顯示第一頁並創建分頁按鈕，顯示總筆數
    result_count_el.textContent = `共計 ${totalRows - 1} 筆`;
    renderTable(currentPage);

    btn_prev_el.addEventListener("click", prevPage);
    btn_next_el.addEventListener("click", nextPage);

    //計算當前頁面要顯示的幾筆資料
    function renderTable(page) {
        const start = (page - 1) * rowsPerPage;
        const end = start + rowsPerPage;
        rows.forEach((row, index) => {
            row.style.display = (index >= start && index < end) ? '' : 'none';
        });
        pagination_el.textContent = `第 ${currentPage} / ${totalPages} 頁`;
    }

    //上下頁
    function prevPage() {
        if (currentPage > 1) {
            currentPage--;
            renderTable(currentPage);
        }
    }

    function nextPage() {
        if (currentPage < totalPages) {
            currentPage++;
            renderTable(currentPage);
        }
    }

    //**************************************點擊取消勾選*****************************
    // const radios = document.querySelectorAll('input[type="radio"]');
    //
    // radios.forEach(radio => {
    //     radio.addEventListener('click', function() {
    //         // 檢查是否為已選中的 radio，若是則取消選中
    //         if (this.checked) {
    //             this.checked = false;
    //         } else {
    //             // 若點擊其他 radio，則保持正常行為
    //             this.checked = true;
    //         }
    //     });
    // });
})


