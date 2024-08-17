document.addEventListener("DOMContentLoaded", function (){
    //抓取標籤
    let title_el = document.getElementById("title");
    let description_el = document.getElementById("description");


    //placeholder處理
    title_el.addEventListener("focus", function () {
        // 清空 placeholder
        title_el.placeholder = "";
    });
    title_el.addEventListener("blur", function () {
        // 如果輸入框為空，恢復 placeholder
        if (title_el.value === "") {
            title_el.placeholder = "可輸入60字元單位";
        }
    });
    description_el.addEventListener("focus", function () {
        // 清空 placeholder
        description_el.placeholder = "";
    });
    description_el.addEventListener("blur", function () {
        // 如果輸入框為空，恢復 placeholder
        if (description_el.value === "") {
            description_el.placeholder = "可輸入255字元單位";
        }
    });



})