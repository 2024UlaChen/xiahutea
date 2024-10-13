// Cache DOM elements
let searchBtn = document.getElementById('searchBtn');                   //搜尋按鈕
let productSearch = document.getElementById('productNameInput');        //搜尋商品
let storeSearchEle = document.getElementById("searchInput2");           //搜尋店家
let categorySearchEle = document.getElementById("searchCategory")       //搜尋分類
let productTableBody = document.querySelector('#myTable tbody');
let tbInnerHTML = productTableBody.innerHTML;




document.addEventListener("DOMContentLoaded", function () {
    init()
    // searchBtn.click();
    searchProductsList("/products/searchproductlist?page=0");
})

function init() {
    fetch('/totalusers', {
        method: 'GET', // 請求方法是 GET
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json(); // 將返回的結果解析為 JSON 格式
        })
        .then(data => {
            //console.log('TotalUserDTO:', data); // 在控制台中顯示 TotalUserDTO

            // 根據獲取到的 TotalUserDTO 處理邏輯
            if (data && data.userTypeId === 1) {
                // 如果角色是 store，隱藏搜尋框
                document.getElementById('searchInput2').style.display = 'none';
                // document.getElementById('label-searchInput2').style.display = 'none';
                var tableHeaderRow = document.querySelector("#myTable thead tr");

                // // Create a new <th> element
                // var newTh = document.createElement("th");
                // newTh.style.width = "10%"; // Set width
                // newTh.textContent = "店家名稱"; // Set the text for the new column
                //
                // // Get the current "商品編號" <th> element
                // var productNumberTh = tableHeaderRow.querySelector("th:nth-child(1)"); // Select the first <th>
                //
                // // Get the current "商品編號" <th> element
                // var productNumberTh = tableHeaderRow.querySelector("th:nth-child(1)"); // Select the first <th>
                //
                // // Insert the new <th> before "商品編號"
                // tableHeaderRow.insertBefore(newTh, productNumberTh);
            }

            if (data && data.userTypeId === 3) {
                // 隱藏新增按鈕
                document.getElementById('add').style.display = 'none';
                var tableHeaderRow = document.querySelector("#myTable thead tr");

                // // Create a new <th> element
                // var newTh = document.createElement("th");
                // newTh.style.width = "10%"; // Set width
                // newTh.textContent = "店家名稱"; // Set the text for the new column
                //
                // // Get the current "商品編號" <th> element
                // var productNumberTh = tableHeaderRow.querySelector("th:nth-child(1)"); // Select the first <th>
                //
                // // Insert the new <th> before "商品編號"
                // tableHeaderRow.insertBefore(newTh, productNumberTh);
            }
        })
        .catch(error => {
            console.error('Fetch error:', error);
        });
}

function searchProductsList(url) {
    return new Promise((resolve, reject) => {
        fetch(url)
            .then(res => res.json())
            .then(data => {
                console.log(url)
                productTableBody.innerHTML = '';
                productList = data.content;

                //填充表格內容
                productList.forEach(product => {
                    const statusBadge = product.productStatus
                        ? `<span class="badge badge-success on-shelf">上架</span>`
                        : `<span class="badge badge-danger off-shelf">下架</span>`;

                    const rowHTML = `
                        <tr>
                            <td>${product.storeName}</td> <!-- 店家名稱 -->
                            <td>${product.productId}</td> <!-- 類別名稱 -->
                            <td>${product.productCategoryName}</td>
                            <td>${product.productName}</td>
                            <td>$${product.productPrice}</td>
                            <td>${product.size}</td>
                            <td>${statusBadge}</td>
                            <td class="project-actions text-right">
                                <a class="btn btn-edit btn-sm update_btn" href="addProducts.html?productId=${product.productId}&type=E" data-id="${product.productId}">
                                    <i class="fas fa-pencil-alt"></i> 編輯
                                </a>
                            </td>
                        </tr>`;
                    // 將 HTML 插入表格
                    productTableBody.insertAdjacentHTML('beforeend', rowHTML);
                });
                resolve();

                tbInnerHTML = productTableBody.innerHTML

                // 分頁
                let pageHtml = ``;
                let currentPage = data.number; // 當前頁數（從0開始）
                let totalPages = data.totalPages;
                let maxVisiblePages = 10; // 每次最多顯示10個頁數按鈕

                // 計算顯示的頁碼範圍
                let startPage = Math.max(0, currentPage - Math.floor(maxVisiblePages / 2));
                let endPage = Math.min(totalPages, startPage + maxVisiblePages);

                if (endPage - startPage < maxVisiblePages) {
                    startPage = Math.max(0, endPage - maxVisiblePages);
                }

                pageHtml +=
                    `<li class="paginate_button page-item previous ${currentPage === 0 ? 'disabled' : ''}" id="example2_previous">
                        <a href="#" data-dt-idx="${currentPage - 1}" tabindex="0" class="page-link">Previous</a>
                    </li>`

                for (let i = startPage; i < endPage; i++) {
                    if (currentPage === i) {
                        pageHtml += `
                            <li class="paginate_button page-item active">
                                <a href="#" data-dt-idx="${i + 1}" tabindex="0" class="page-link">${i + 1}</a>
                            </li>
                        `;
                    } else {
                        pageHtml += `
                            <li class="paginate_button page-item">
                                <a href="#" data-dt-idx="${i + 1}" tabindex="0" class="page-link">${i + 1}</a>
                            </li>
                        `;
                    }
                }

                pageHtml +=
                    `<li class="paginate_button page-item next ${currentPage === totalPages - 1 ? 'disabled' : ''}" id="example2_next">
                        <a href="#" data-dt-idx="${currentPage + 1}" tabindex="0" class="page-link">Next</a>
                    </li>`

                if (totalPages > 0) {
                    // 如果有資料，才會顯示分頁按鈕
                    $(".pagination").eq(0).html(pageHtml);

                } else {
                    // 如果沒資料，不會顯示分頁按鈕
                    $(".pagination").eq(0).html("");
                }

            })
            .catch(error => {
                console.error('Error fetching product list:', error);
                reject(error); // 如果有错误，拒绝 promise
            });
    })

}

// Event listener for search button click
searchBtn.addEventListener("click", async function () {
    let productCategoryName = categorySearchEle.value.trim();
    let productName = productSearch.value.trim();
    let storeName = storeSearchEle.value.trim();
    let url = "/products/searchproductlist?page=0";
    if (storeName) {
        url += "&storeName=" + storeName;
    }
    if (productCategoryName) {
        url += "&categoryName=" + productCategoryName;
    }
    if (productName) {
        url += "&productName=" + productName;
    }
    await searchProductsList(url);

    if (!tbInnerHTML) {
        swal.fire("查無資料!", "請確認查詢資訊", "warning");
    }
});

document.querySelectorAll(".form-control.mr-2").forEach(function (element) {
    element.addEventListener('keyup', function (e) {
        if (e.which == 13) {
            e.preventDefault(); // 防止表單提交或其他預設行為
            searchBtn.click();// 呼叫搜尋函數
        }
    });
});


// 使用 fetch 從後端獲取 TotalUserDTO
$('#add').on('click', function () {
    window.location.href = "addProducts.html"
})

$(document).on("click", "a.page-link", function (e) {
    e.preventDefault();
    let newPage = $(".paginate_button.page-item.active").children("a").attr("data-dt-idx");
    console.log(newPage)
    let jumpPage = $(this).attr("data-dt-idx");
    console.log(jumpPage)

    let url = "/products/searchproductlist"

    //依頁數
    if ($(this).closest("li").is(":first-child")) {
        url += "?page=" + (newPage - 1 - 1);
    } else if ($(this).closest("li").is(":last-child")) {
        url += "?page=" + (newPage - 1 + 1);
    } else {
        url += "?page=" + (jumpPage - 1);
    }

    // 依搜尋
    let productCategoryName = categorySearchEle.value.trim();
    let productName = productSearch.value.trim();
    let storeName = storeSearchEle.value.trim();

    if (storeName) {
        url += "&storeName=" + storeName;
    }
    if (productCategoryName) {
        url += "&categoryName=" + productCategoryName;
    }
    if (productName) {
        url += "&productName=" + productName;
    }
    searchProductsList(url);


})







