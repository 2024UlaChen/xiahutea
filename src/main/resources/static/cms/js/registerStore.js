let vatEle = $("#vat");
let storenameEle = $("#storename");
let submitEle = $("#submit")
let storeStatus = $("#storeStatus")


//建立一個搜尋註冊店家列表的方法
function searchRegisterStore(url) {
    fetch(url).then(res => res.json())
        .then(core => {
            console.log(core);
            if (core.data.content.length === 0) {
                //sweetAlert
                swal.fire("查無資料!", "請確認查詢資訊", "warning");
            }

            //表格內容
            let tbodyHtml = ``;
            $.each(core.data.content, function (index, registerStore) {
                let flag = registerStore.storeStatus == 0;
                let storeStatus = flag ? "申請中" : "拒絕申請"
                let registerDay = moment(registerStore.registerDay).format("YYYY/MM/DD");
                tbodyHtml += `
                        <tr>
                            <td>${index + 1}</td>
                            <td>${registerDay}</td>
                            <td>
                                <a href="./registerStoreDetail.html?storeId=${registerStore.storeId}"   storeId="${registerStore.storeId}">
                                    ${registerStore.storeId}
                                </a>
                            </td>
                            <td>${registerStore.storeName}</td>
                            <td>${registerStore.vat}</td>
                            <td>${storeStatus}</td>
                        </tr>`
            });
            $("tbody").html(tbodyHtml);

            // 分頁
            let pageHtml = ``;
            let currentPage = core.data.number; // 當前頁數（從0開始）
            let totalPages = core.data.totalPages;
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

}

//進入頁面就先搜尋全部
$(function () {
    let url = "/registerstore/registerstorelist?";
    url += "storeStatus=10&page=0"
    searchRegisterStore(url);
})

//依條件搜尋
$(submitEle).on("click", e => {
    // e.preventDefault();
    let url = "/registerstore/registerstorelist?";
    url += "page=0";
    // 依店家狀態查詢
    url += "&storeStatus=" + $(storeStatus).val();
    // 依統編查詢
    if ($(vatEle).val()) {
        url += "&vat=" + $(vatEle).val();
    }
    //依店家名稱查詢
    if ($(storenameEle).val()) {
        url += "&storeName=" + $(storenameEle).val();
    }
    console.log(url);
    searchRegisterStore(url);
})

//點選頁碼
$(document).on("click", "a.page-link", function (e) {
    e.preventDefault();
    let newPage = $(".paginate_button.page-item.active").children("a").attr("data-dt-idx");
    let jumpPage = $(this).attr("data-dt-idx");
    let url = "/registerstore/registerstorelist?";

    //依頁數
    if ($(this).closest("li").is(":first-child")) {
        url += "page=" + (newPage - 1 - 1);
    } else if ($(this).closest("li").is(":last-child")) {
        url += "page=" + (newPage - 1 + 1);
    } else {
        url += "page=" + (jumpPage - 1);
    }
    // 依店家狀態查詢
    url += "&storeStatus=" + $(storeStatus).val();
    // 依統編查詢
    if ($(vatEle).val()) {
        url += "&vat=" + $(vatEle).val();
    }
    //依店家名稱查詢
    if ($(storenameEle).val()) {
        url += "&storeName=" + $(storenameEle).val();
    }
    searchRegisterStore(url);
})

$(".form-control").keydown(e => {
    if(e.which===13){
        $(submitEle).click()
    }
})

$(".inputEle").keyup(e => {
    let flag = true;
    let inputEles = $(".inputEle");
    inputEles.each(function (){
        if($(this).val().length > 0){
            flag = false;
        }
    });
    if(flag){$(submitEle).click()}
})

