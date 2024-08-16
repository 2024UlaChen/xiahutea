document.addEventListener("DOMContentLoaded", function (){
    //**************************************定義變數及標籤**********************
    const rowsPerPage = 5; // 每頁顯示的行數
    let currentPage = 1;
    // 所有表格行
    const tableBody = document.getElementsByClassName("coupon-table")[0];
    const rows = Array.from(tableBody.getElementsByTagName('tr'));
    const totalRows = rows.length;
    const totalPages = Math.ceil(totalRows / rowsPerPage);

    //**************************************事件綁定**********************
    // 初始化顯示第一頁
    showPage(currentPage);

    //上下頁按鈕
    let btn_prev_el = document.getElementById('prev-btn');
    let btn_next_el = document.getElementById('next-btn');

    // 上一頁按鈕
    btn_prev_el.addEventListener('click', function () {
        if (currentPage > 1) {
            currentPage--;
            showPage(currentPage);
        }
    });
    // 下一頁按鈕
    btn_next_el.addEventListener('click', function () {
        if (currentPage < totalPages) {
            currentPage++;
            showPage(currentPage);
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
        // 更新按鈕狀態
        document.getElementById('prev-btn').disabled = page === 1;
        document.getElementById('next-btn').disabled = page === totalPages;
    }
})