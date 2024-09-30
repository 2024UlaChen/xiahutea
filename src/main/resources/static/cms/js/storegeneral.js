myLoad(".storeheader", "storeheader.html")
myLoad(".storeaside", "storeaside.html")
myLoad(".storefooter", "storefooter.html")

function myLoad(target, url) {
    $.ajax({
        type: "GET",
        url: url,
        async: false,
        dataType: "text",
        success: function (response) {
            $(target).html(response);
        },
        error: function (thrownError) {
            console.log(thrownError);
        }
    });
}

$(function () {
    let showStore = $("li.nav-item.show-store")
    let showAdmin = $("li.nav-item.show-admin")
    fetch("/totalusers")
        .then(res => res.json())
        .then(data => {
            userTypeId = data.userTypeId;
            if (userTypeId === 3) {
                $.each(showStore, function (index, item) {
                    $(item).addClass("d-none")
                })
            } else {
                $.each(showAdmin, function (index, item) {
                    $(item).addClass("d-none")
                })
            }
        })
        .catch(error => {
            $(".sidebar").eq(0).addClass("d-none")
            console.log($("div.storeaside").next())
            let containEle = $("div.storeaside").next().children();
            $.each(containEle,function (index,item){
                $(item).addClass("d-none")
            })
        });
})

$("li#logout").on("click", e => {
    Swal.fire({
        title: "登出",
        text: "請確認是否登出",
        showCancelButton: true
    }).then(function(result) {
        if (result.value) {
            fetch("/store/logout")
            location.href = "./backstageLogin.html"
        }
        else {

        }
    });
})



