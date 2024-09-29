myLoad(".storeheader","storeheader.html")
myLoad(".storeaside","storeaside.html")
myLoad(".storefooter","storefooter.html")

function myLoad(target,url){
    $.ajax({
        type: "GET",
        url: url,
        async:false,
        dataType: "text",
        success: function (response) {
            $(target).html(response);
        },
        error: function (thrownError) {
            console.log(thrownError);
        }
    });
}

$(function (){
    console.log("側邊欄更新")
    let showStore = $("li.nav-item.show-store")
    let showAdmin = $("li.nav-item.show-admin")
    fetch("/totalusers")
        .then(res => res.json())
        .then(data => {
            userTypeId = data.userTypeId;
            console.log(userTypeId);
            if(userTypeId === 3){
                $.each(showStore,function (index,item){
                    $(item).addClass("d-none")
                })
                console.log("管理員顯示")
            }else if (userTypeId === 1){
                $.each(showAdmin,function (index,item){
                    $(item).addClass("d-none")
                })
                console.log("店家顯示")
            }else{
                $("aside").addClass("d-none")
            }
        })
})
