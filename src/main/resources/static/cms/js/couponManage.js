document.addEventListener("DOMContentLoaded", function () {
    //**************************************定義變數及標籤**********************
    // 每頁顯示的行數及當前頁面
    const rowsPerPage = 10;
    let currentPage = 1;
    let tableBody_el = document.getElementById('tablebody')
    let result_count_el = document.getElementById("result-count")
    let pagination_el = document.getElementById('pagination');
    // let tbody_el = document.querySelector('.coupon-table tbody');
    let rows = [];
    let totalRows = 0;
    let totalPages = 0;

    //上下頁按鈕
    let btn_prev_el = document.getElementById('prev-btn');
    let btn_next_el = document.getElementById('next-btn');

    //查詢按鈕
    let find_coupon_el = document.getElementById('find-coupon');

    //進入頁面先抓優惠券列表
    fetch('/coupon/manage')
        .then(response => {
            if (!response.ok) {
                return response.json().then(errorData => {
                    throw new Error(errorData.message);
                });
            }
            return response.json();
        })
        .then(data=>{
            showAllCoupon(data)
        })
        .catch(error => {
            swal({
                title: '取得數據異常',
                text: error.message,
                icon: 'error'
            });
        })
    //表格分頁按鈕事件綁定
    btn_prev_el.addEventListener("click", prevPage);
    btn_next_el.addEventListener("click", nextPage);

    //**************************************搜尋功能*****************************
    find_coupon_el.addEventListener('click',function (){

    })
    //**************************************修改表格資料*****************************
    document.addEventListener('click', function (e) {
        if (e.target.classList.contains('td-edit')){
            let couponId = e.target.getAttribute('data-id');
            if (couponId && couponId !== 'undefined') {
                window.location.href = `couponEdit.html?couponId=${couponId}`;
            } else {
                window.location.href = `couponEdit.html`;
            }

        }
    });
    //**************************************FUNCTION區*****************************
    //渲染表格function
    function showAllCoupon(coupons) {
        tableBody_el.innerHTML = '';
        coupons.forEach(coupon => {
            const couponStatus = coupon.couponStatus ? '啟用' : '未啟用';
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${coupon.couponId}</td>
                <td>${coupon.couponName}</td>
                <td>${coupon.discount}</td>
                <td>${couponStatus}</td>
                <td>${coupon.createDate}</td>
                <td>${coupon.expiredDate}</td>
                <td>
                    <button type="button" class="td-edit" data-id="${coupon.couponId}">修改</button>
                </td>
            `;
            tableBody_el.appendChild(row);
            })
        // 初始顯示第一頁並創建分頁按鈕，顯示總筆數
         rows = Array.from(tableBody_el.getElementsByTagName('tr'));
         totalRows = rows.length;
         totalPages = Math.ceil(totalRows / rowsPerPage);
        result_count_el.textContent = `共計 ${totalRows} 筆`;
        renderTable(currentPage,totalPages);
        }
    //計算當前頁面要顯示的幾筆資料
    function renderTable(currentPage,totalPages) {
        const start = (currentPage - 1) * rowsPerPage;
        const end = start + rowsPerPage;
        rows.forEach((row, index) => {
            row.style.display = (index >= start && index < end) ? '' : 'none';
        });
        pagination_el.textContent = `第 ${currentPage} / ${totalPages} 頁`;
        // 設定按鈕狀態
        btn_prev_el.disabled = currentPage === 1;
        btn_next_el.disabled = currentPage === totalPages;
    }
    //上下頁
    function prevPage() {
        if (currentPage > 1) {
            currentPage--;
            renderTable(currentPage,totalPages);
        }
    }
    function nextPage() {
        if (currentPage < totalPages) {
            currentPage++;
            renderTable(currentPage,totalPages);
        }
    }
});

