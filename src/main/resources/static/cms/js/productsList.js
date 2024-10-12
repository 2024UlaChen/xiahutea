// Cache DOM elements
var searchBtn = document.getElementById('searchBtn');                   //搜尋按鈕
var productSearch = document.getElementById('productNameInput');        //搜尋商品
var storeSearchEle = document.getElementById("searchInput2");           //搜尋店家
var categorySearchEle = document.getElementById("searchCategory")       //搜尋分類
const productTableBody = document.querySelector('#myTable tbody');


var paginationControls = document.getElementById('paginationControls'); //分頁


document.addEventListener("DOMContentLoaded", function () {
    init()
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
            console.log('TotalUserDTO:', data); // 在控制台中顯示 TotalUserDTO

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

    fetch(url)
        .then(res => res.json())
        .then(data => {
            console.log(data)
            if (data.content.length === 0) {
                //sweetAlert
                swal.fire("查無資料!", "請確認查詢資訊", "warning");
            }

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

            // 分頁
            let pageHtml = ``;
            let i = 0;
            pageHtml +=
                `<li class="paginate_button page-item previous disabled" id="example2_previous">
                        <a href="#" data-dt-idx="0" tabindex="0" class="page-link">Previous</a>
                    </li>`
            for (i; i < data.totalPages; i++) {
                if (data.number == i) {
                    pageHtml += `
                        <li class="paginate_button page-item active">
                            <a href="#" data-dt-idx="${i + 1}" tabindex="0" class="page-link">
                                ${i + 1}
                            </a>
                        </li>
                        `
                } else {
                    pageHtml += `
                            <li class="paginate_button page-item">
                                <a href="#" data-dt-idx="${i + 1}" tabindex="0" class="page-link">
                                    ${i + 1}
                                </a>
                            </li>
                            `
                }
            }
            pageHtml +=
                `<li class="paginate_button page-item next" id="example2_next">
                            <a href="#" data-dt-idx="${i}" tabindex="0" class="page-link">
                                Next
                            </a>
                     </li>`


            if (data.totalPages > 0) {
                //如果有資料，才會顯示分頁按鈕
                $(".pagination").eq(0).html(pageHtml);

                //依所在頁數限制 previous & next 按鈕能不能點擊
                if (data.number - 1 < 0) {
                    $("#example2_previous").add("disabled");
                } else {
                    $("#example2_previous").removeClass("disabled");
                }
                if (data.number + 1 == i) {
                    $("#example2_next").addClass("disabled");
                } else {
                    $("#example2_next").removeClass("disabled");
                }
            } else {
                //如果沒資料，不會顯示分頁按鈕
                $(".pagination").eq(0).html("");
            }

        })
}

// Event listener for search button click
searchBtn.addEventListener("click", function () {
    var productCategoryName = categorySearchEle.value.trim();
    var productName = productSearch.value.trim();
    var storeName = storeSearchEle.value.trim();
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
    console.log(url);
    searchProductsList(url);
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
    let jumpPage = $(this).attr("data-dt-idx");

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
    console.log(url);
    searchProductsList(url);


})


// function getCategoryNameById(categoryId) {
//     return fetch(`/categories/${categoryId}`)
//         .then(response => response.json())
//         .then(category => category.categoryName)
//         .catch(error => {
//             console.error('获取分类名称时出错:', error);
//             return '未知分类'; // 如果出错，返回一个默认值
//         });
// }


// =========獲取商品分類選項====

// let categoryOptions = document.querySelector('.productcategory');
// fetch('/categories/all')
//     .then(response => {
//         if (response.ok) {
//             return response.json();
//         } else {
//             throw new Error('分類加載失敗');function getStoreNameById(storeId) {
//                 return fetch(`/store/getInfo/${storeId}`)  // 假设这是你的 API 路径
//                     .then(response => {
//                         if (!response.ok) {
//                             throw new Error('网络响应错误');
//                         }
//                         return response.json();  // 解析响应为 JSON
//                     })
//                     .then(store => store.storeName)  // 返回店铺名称
//                     .catch(error => {
//                         console.error('获取店铺名称时出错:', error);
//                         return '未知店铺';  // 如果出错返回默认值
//                     });
//             }
//         }
//     })
//     .then(categories => {
//         // Clear existing options
//         categoryOptions.innerHTML = '';
//
//         categories.forEach(category => {
//             const option = document.createElement('option');
//             option.value = category.categoryId;
//             option.text = category.categoryName;
//             categoryOptions.appendChild(option);  // Fix here
//         });
//     })
//     .catch(error => {
//         console.error('Error loading categories:', error);
//
//
//     });
//


// function getStoreNameById(storeId) {
//     return fetch(`/store/getInfo/${storeId}`)  // 假设这是你的 API 路径
//         .then(response => {
//             if (!response.ok) {
//                 throw new Error('网络响应错误');
//             }
//             return response.json();  // 解析响应为 JSON
//         })
//         .then(store => store.storeName)  // 返回店铺名称
//         .catch(error => {
//             console.error('获取店铺名称时出错:', error);
//             return '未知店铺';  // 如果出错返回默认值
//         });
// }

// // Event listener for input in the product name search field
// productSearch.addEventListener("input", function() {
//     var productName = productSearch.value.trim();
//     if (!productName) {
//         fetchProducts('', '', 0); // Clear search and show all products
//     }
// });


//
// // Fetch search results by category and product name
// function searchfetchProducts(queryId, queryName) {
//     const url = `/products/search?productCategoryId=${encodeURIComponent(queryId)}&productName=${encodeURIComponent(queryName)}`;
//
//     fetch(url)
//         .then(response => {
//             if (!response.ok) {
//                 throw new Error('Network response was not ok');
//             }
//             return response.json(); // Parse JSON
//         })
//         .then(data => {
//             if (data.length === 0) {
//                 console.log('No matching products found');
//             } else {
//                 updateProductTable(data);
//             }
//             paginationControls.innerHTML = ''; // Hide pagination on search results
//         })
//         .catch(error => {
//             console.error('Fetch error:', error);
//         });
// }

// Fetch products with pagination
// function fetchProducts(productCategoryId, productName, page) {
//     const url = `/products/paginated?page=${page}&size=10` +
//         `${productCategoryId ? `&categoryId=${encodeURIComponent(productCategoryId)}` : ''}` +
//         `${productName ? `&productName=${encodeURIComponent(productName)}` : ''}`;
//
//     fetch(url)
//         .then(response => {
//             if (!response.ok) {
//                 throw new Error('Network response was not ok');
//             }
//             return response.json(); // Parse JSON
//         })
//         .then(data => {
//             // updateProductTable(data.content); // Update table data
//             // updatePageControls(data.totalPages, page); // Update pagination
//         })
//         .catch(error => {
//             console.error('Fetch error:', error);
//         });
// }


// // Update the product table
// function updateProductTable(products) {
//     productTableBody.innerHTML = ''; // Clear table content
//
//     products.map(product => {
//         // 使用 Promise.all 同時獲取分類名稱和店鋪名稱
//         return Promise.all([
//             getCategoryNameById(product.productCategoryId), // 獲取分類名稱
//             getStoreNameById(product.productStoreId) // 獲取店鋪名稱
//         ]).then(([categoryName, storeName]) => {
//             const newRow = document.createElement('tr');
//
//             const statusBadge = product.productStatus
//                 ? `<span class="badge badge-success on-shelf">上架</span>`
//                 : `<span class="badge badge-danger off-shelf">下架</span>`;
//
//             newRow.innerHTML = `
//             <td>${storeName}</td> <!-- 店家名稱 -->
//             <td>${categoryName}</td> <!-- 類別名稱 -->
//             <td>${product.productId}</td>
//             <td>${product.productName}</td>
//             <td>$${product.productPrice}</td>
//             <td>${product.size}</td>
//             <td>${statusBadge}</td>
//             <td class="project-actions text-right">
//                 <a class="btn btn-edit btn-sm update_btn" href="addProducts.html?productId=${product.productId}&type=E" data-id="${product.productId}">
//                     <i class="fas fa-pencil-alt"></i> 編輯
//                 </a>
//             </td>
//         `;
//             productTableBody.appendChild(newRow);
//         });
//     }).then(() => {
//         // 當所有 Promise 完成後，可以執行其他操作
//     }).catch(error => {
//         console.error('Error updating product table:', error);
//     });
//
//
// }

// Update pagination controls
// function updatePageControls(totalPages, currentPage) {
//     paginationControls.innerHTML = ''; // Clear existing pagination controls
//
//     const nav = document.createElement('nav');
//     nav.setAttribute('aria-label', 'Page navigation example');
//     const ul = document.createElement('ul');
//     ul.className = 'pagination justify-content-end';
//
//     // Previous button
//     const prevLi = document.createElement('li');
//     prevLi.className = 'page-item ' + (currentPage === 0 ? 'disabled' : '');
//     const prevLink = document.createElement('a');
//     prevLink.className = 'page-link';
//     prevLink.href = '#';
//     prevLink.textContent = 'Previous';
//     if (currentPage > 0) {
//         prevLink.addEventListener('click', () => {
//             fetchProducts(document.querySelector('.productcategory').value, productSearch.value.trim(), currentPage - 1); // Load previous page
//         });
//     }
//     prevLi.appendChild(prevLink);
//     ul.appendChild(prevLi);
//
//     // Page number buttons
//     for (let i = 0; i < totalPages; i++) {
//         const li = document.createElement('li');
//         li.className = 'page-item ' + (i === currentPage ? 'active' : '');
//         const link = document.createElement('a');
//         link.className = 'page-link';
//         link.href = '#';
//         link.textContent = i + 1;
//         link.addEventListener('click', () => {
//             fetchProducts(document.querySelector('.productcategory').value, productSearch.value.trim(), i); // Load specific page
//         });
//         li.appendChild(link);
//         ul.appendChild(li);
//     }
//
//     // Next button
//     const nextLi = document.createElement('li');
//     nextLi.className = 'page-item ' + (currentPage === totalPages - 1 ? 'disabled' : '');
//     const nextLink = document.createElement('a');
//     nextLink.className = 'page-link';
//     nextLink.href = '#';
//     nextLink.textContent = 'Next';
//     if (currentPage < totalPages - 1) {
//         nextLink.addEventListener('click', () => {
//             fetchProducts(document.querySelector('.productcategory').value, productSearch.value.trim(), currentPage + 1); // Load next page
//         });
//     }
//     nextLi.appendChild(nextLink);
//     ul.appendChild(nextLi);
//
//     nav.appendChild(ul);
//     paginationControls.appendChild(nav);
// }

// Load the first page of products on initial page load
// fetchProducts('', '', 0);








