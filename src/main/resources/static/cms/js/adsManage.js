document.addEventListener("DOMContentLoaded", function () {
    //**************************************定義變數及標籤**********************
    // 每頁顯示的行數及當前頁面
    const rowsPerPage = 10;
    let currentPage = 1;
    // 所有表格行
    const tableBody = document.getElementsByClassName("ads-table")[0];
    const rows = Array.from(tableBody.getElementsByTagName('tr'));
    const totalRows = rows.length;
    const totalPages = Math.ceil(totalRows / rowsPerPage);
    let result_count_el = document.getElementById("result-count")
    let pagination_el = document.getElementById('pagination');
    //上下頁按鈕
    let btn_prev_el = document.getElementById('prev-btn');
    let btn_next_el = document.getElementById('next-btn');


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
        rows.forEach((row, index)=>{
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
    //渲染表格
    // fetch('your-api-endpoint')
    //     .then(response => response.json())
    //     .then(data => {
    //         var tbody = document.querySelector('.ads-table tbody');
    //         tbody.innerHTML = ''; // 清空現有的 tbody 內容
    //
    //         data.forEach(ad => {
    //             var row = document.createElement('tr');
    //             row.innerHTML = `
    //           <td>${ad.name}</td>
    //           <td>${ad.id}</td>
    //           <td>${ad.description}</td>
    //           <td>${ad.startDate}</td>
    //           <td>${ad.endDate}</td>
    //           <td>${ad.homepageDisplay ? '是' : '否'}</td>
    //           <td>${ad.enabled ? '啟用' : '停用'}</td>
    //           <td>
    //               <a href="adsEdit.html?id=${ad.id}">
    //                   <button type="button" id="td-edit">修改</button>
    //               </a>
    //           </td>
    //             tbody.appendChild(row);
    //         });
    //     })
});

