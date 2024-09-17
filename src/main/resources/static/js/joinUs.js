let resultMessageEle = $("#resultMessage");
let phoneEle = $("#phone");
let vatEle = $("#vat");
let form = $("#form")



$(form).submit(e => {
    e.preventDefault();
    let phone = $(phoneEle).val();
    let vat = $(vatEle).val();
    let formData = new FormData(e.target);

    //確認電話
    if (!phoneCheck(phone)) {
        $(resultMessageEle).text("連絡電話錯誤")
        return
    }

    //確認統編
    if (!vatCheck(vat)) {
        $(resultMessageEle).text("統一編號錯誤")
        return
    }

    fetch("/registerstore/register", {
        method: "POST",
        body: formData
    }).then(res => res.json()).then(store => {
        if (store.successful) {
            $(resultMessageEle).text("已收到加入申請，請耐心等待回復");
            $(".form-control").each((index, item) => {
                $(item).val("");
            })
        } else {
            $(resultMessageEle).text(store.message)
        }
    })
});
