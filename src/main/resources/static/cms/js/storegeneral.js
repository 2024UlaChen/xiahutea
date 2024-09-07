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

