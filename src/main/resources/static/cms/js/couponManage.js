document.addEventListener("DOMContentLoaded", function () {
    //**************************************定義變數及標籤**********************
    // 每頁顯示的行數及當前頁面
    const rowsPerPage = 10;
    let currentPage = 1;
    let tableBody_el = document.getElementsByClassName('coupon-table')[0]
    let result_count_el = document.getElementById("result-count")
    let pagination_el = document.getElementById('pagination');
    let tbody_el = document.querySelector('.coupon-table tbody');
    //上下頁按鈕
    let btn_prev_el = document.getElementById('prev-btn');
    let btn_next_el = document.getElementById('next-btn');

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

    //**************************************修改表格資料*****************************
    // document.addEventListener('click', function (event) {
    //     if (event.target.classList.contains('td-edit')) {
    //         const row = event.target.closest('tr');
    //         const data = {
    //             title: row.cells[0].textContent,
    //             id: row.cells[1].textContent,
    //             discount: row.cells[2].textContent,
    //             amount: row.cells[3].textContent,
    //             status: row.cells[4].textContent,
    //             startDate :row.cells[5].textContent,
    //             endDate:row.cells[6].textContent,
    //             //TODO 還要再加入優惠券描述但不是從cell來
    //         };
    //         localStorage.setItem('couponData', JSON.stringify(data));
    //         window.location.href = 'couponEdit.html';
    //     }
    // });
    //**************************************FUNCTION區*****************************
    //計算當前頁面要顯示的幾筆資料
    function renderTable(page) {
        const start = (page - 1) * rowsPerPage;
        const end = start + rowsPerPage;
        let rows = Array.from(tableBody_el.getElementsByTagName('tr')); // 更新行數資料
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
    //渲染表格function
    function showAllCoupon(coupons) {
        tbody_el.innerHTML = '';
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
                    <button type="button" class="td-edit" data-id="${coupon.id}">修改</button>
                </td>
            `;
            tbody_el.appendChild(row);
            })
        // 初始顯示第一頁並創建分頁按鈕，顯示總筆數
        let rows = Array.from(tableBody_el.getElementsByTagName('tr'));
        let totalRows = rows.length;
        let totalPages = Math.ceil(totalRows / rowsPerPage);
        result_count_el.textContent = `共計 ${totalRows - 1} 筆`;
        renderTable(currentPage);
        }
    });

