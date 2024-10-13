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
            userId = data.userId;
            if (userTypeId === 3) {
                $.each(showStore, function (index, item) {
                    $(item).addClass("d-none")
                })
            } else if (userTypeId === 1) {
                $.each(showAdmin, function (index, item) {
                    $(item).addClass("d-none")
                })
                let imgformat = getImgFormatByBase64(data.logo)
                $("#userimg").attr("src",`data:image/${imgformat};base64,${data.logo}`);
                $("#username").text(data.data);
                $("#storeInfo").attr("href","./storeInfo.html?storeid=" + userId);
                $("#storeInfoEditLoyaltyCard").attr("href","./storeInfoEditLoyaltyCard.html?storeid=" + userId);
                $("#storeInfoEditPassword").attr("href","./storeInfoEditPassword.html?storeid=" + userId);

                // console.log(data.data)

            } else {
                $(".sidebar").eq(0).addClass("d-none")
                let containEle = $("div.storeaside").next().children();
                $.each(containEle,function (index,item){
                    $(item).addClass("d-none")
                })
            }
        })
        // .catch(error => {
        //     $(".sidebar").eq(0).addClass("d-none")
        //     console.log($("div.storeaside").next())
        //     let containEle = $("div.storeaside").next().children();
        //     $.each(containEle,function (index,item){
        //         $(item).addClass("d-none")
        //     })
        // });
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

function getImgFormatByBase64(base64Img){
    if(base64Img !=null){
        let imgFormat;
        if (base64Img.startsWith("iVBORw0KGgo")) {
            imgFormat = "png"; // PNG 圖片的 base64 開頭
        } else if (base64Img.startsWith("/9j/")) {
            imgFormat = "jpeg"; // JPEG 圖片的 base64 開頭
        }else if (base64Img.startsWith("R0lGODlh")) {
            imgFormat = "gif"; // JPEG 圖片的 base64 開頭
        }else if (base64Img.startsWith("Qk0")) {
            imgFormat = "bmp"; // JPEG 圖片的 base64 開頭
        }else if (base64Img.startsWith("UklGR")) {
            imgFormat = "webp"; // JPEG 圖片的 base64 開頭
        }else if (base64Img.startsWith("PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmci")) {
            imgFormat = "svg+xml"; // JPEG 圖片的 base64 開頭
        }
        else {
            console.error("不支持的圖片格式");
        }
        return imgFormat;
    }

}



