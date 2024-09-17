let vatEle = $("#vat");
let storenameEle = $("#storename");
let submitEle = $("#submit")
let storeStatus = $("#storeStatus")


//建立一個搜尋註冊店家列表的方法
function searchRegisterStore(url) {
    fetch(url).then(res => res.json())
        .then(core => {
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

            //分頁

            let pageHtml = ``;
            let i = 0;
            pageHtml +=
                `<li class="paginate_button page-item previous disabled" id="example2_previous">
                        <a href="#" data-dt-idx="0" tabindex="0" class="page-link">Previous</a>
                    </li>`
            for (i; i < core.data.totalPages; i++) {
                if (core.data.number == i) {
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


            if (core.data.totalPages > 0) {
                //如果有資料，才會顯示分頁按鈕
                $(".pagination").eq(0).html(pageHtml);

                //依所在頁數限制 previous & next 按鈕能不能點擊
                if (core.data.number - 1 < 0) {
                    $("#example2_previous").add("disabled");
                } else {
                    $("#example2_previous").removeClass("disabled");
                }
                if (core.data.number + 1 == i) {
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

//進入頁面就先搜尋全部
$(function () {
    let url = "/registerstore/registerstorelist?";
    url += "storeStatus=10&page=0"
    searchRegisterStore(url);
})

//依條件搜尋
$(submitEle).on("click", e => {
    e.preventDefault();
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

