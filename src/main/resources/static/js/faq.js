// 問題開合
$("a.link_title").on("click", function (e) {
    e.preventDefault();
    $(this).closest("li").toggleClass("-on");
    $(this).closest("li").find("div.inner_block").slideToggle();
});


//問題搜尋
function query_faq() {
    // 關鍵字
    let input_val = $("input#faq-input").val();
    // 問題卡片 list
    let uls = document.getElementsByClassName("faq-list");

    //找到符合關鍵字的問題
    for (let u = 0; u < uls.length; u++) {

        let lis = uls[u].getElementsByTagName("li")
        let flag = true;
        for (let i = 0; i < lis.length; i++) {
            let title = lis[i].firstElementChild.lastElementChild.innerText;
            let txtValue = lis[i].lastElementChild.innerText;
            if (txtValue.indexOf(input_val) > -1) {
                lis[i].style.display = "";
                flag = false;
            } else if (title.indexOf(input_val) > -1) {
                lis[i].style.display = "";
                flag = false;
            } else {
                lis[i].style.display = "none";
            }
        }
        if (flag) {
            uls[u].closest("div").style.display = "none"
        } else {
            uls[u].closest("div").style.display = ""
        }
    }
}

$("a#search").on("click", (e) => {
    e.preventDefault();
    query_faq();
})
$(document).on("keyup", function (e) {
    $("a#search").click();
})



