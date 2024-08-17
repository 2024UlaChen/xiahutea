document.addEventListener("DOMContentLoaded", function (){
    //**************************************定義變數及標籤**********************
    // 每頁顯示的行數及當前頁面
    const rowsPerPage = 5;
    let currentPage = 1;
    // 所有表格行
    const tableBody = document.getElementsByClassName("coupon-table")[0];
    const rows = Array.from(tableBody.getElementsByTagName('tr'));
    const totalRows = rows.length;
    const totalPages = Math.ceil(totalRows / rowsPerPage);
    let result_count_el = document.getElementById("result-count")
    //上下頁按鈕
    let btn_prev_el = document.getElementById('prev-btn');
    let btn_next_el = document.getElementById('next-btn');
    let pagination_el = document.getElementById('pagination');

    //**************************************RUN*************************
    // 初始顯示第一頁並創建分頁按鈕，顯示總筆數
    result_count_el.textContent = `共計 ${rows.length-1} 筆`;
    showPage(currentPage);
    createPagination();



    //**************************************事件綁定**********************
    // 處理上一頁和下一頁按鈕點擊事件
    btn_prev_el.addEventListener('click', function() {
        if (currentPage > 1) {
            showPage(currentPage - 1);
        }
    });
    btn_next_el.addEventListener('click', function() {
        if (currentPage < totalPages) {
            showPage(currentPage + 1);
        }
    });

    //**************************************FUNCTION**********************
    // 顯示特定頁面的資料
    function showPage(page) {
        // 隱藏所有行
        rows.forEach(function(row) {
            row.style.display = 'none';
        });
        // 計算當前頁面要顯示的行範圍
        const start = (page - 1) * rowsPerPage;
        const end = start + rowsPerPage;
        // 顯示當前頁面的行
        rows.slice(start, end).forEach(function(row) {
            row.style.display = '';
        });
        // 更新按鈕可點擊狀態
        btn_prev_el.disabled = page === 1;
        btn_next_el.disabled = page === totalPages;
        // 更新當前頁面
        currentPage = page;
        updatePagination();
    }
    // 創建分頁按鈕
    function createPagination() {
        pagination_el.innerHTML = ''; // 清空之前的按鈕
        for (let i = 1; i <= totalPages; i++) {
            const button = document.createElement('button');
            button.textContent = i;
            button.classList.add('pagination-btn');

            // 為按鈕添加點擊事件
            button.addEventListener('click', function() {
                showPage(i);
            });

            // 高亮當前頁面按鈕
            if (i === currentPage) {
                button.classList.add('active');
            }
            pagination_el.appendChild(button);
        }
    }
    // 更新分頁按鈕的狀態
    function updatePagination() {
        const buttons = document.querySelectorAll('.pagination-btn');
        buttons.forEach(function(button) {
            button.classList.remove('active');
        });
        buttons[currentPage - 1].classList.add('active');
    }
})