function getUser(){
    $.ajax({
        url:'member',
        method:'post',
        success:function (data){

            if(!data.successful){
                console.log('未登入');
                $('.header-user-links').empty();
                let str =`<li>
                                <a href="login.html">
                                  <span style="color:red">
                                    <i class="fa fa-sign-in" aria-hidden="true" ></i>
                                      <span>登入/註冊</span>
                                  </span>
                                </a>
                            </li>`;
                document.querySelector('.header-user-links').insertAdjacentHTML('afterbegin',str);
                
                document.querySelector('#searchBarLoyalty').remove();
                document.querySelector('#searchBarOrder').remove();
                document.querySelector('#searchBarCoupon').remove();


            }else{

                document.querySelector('#userName').textContent = data.nickname;
                document.querySelector('#userLogo').src =`data:image/png;base64,${data.customerImg}`;

            }

        }
    });//取得登入
}

function clearSearchBar(){
    //clear search
    document.querySelector('#clearSearch').addEventListener('click',function (e){
        e.preventDefault();
        document.querySelector('#user-enter-keyword').value='';
    });
}

function SearchBar(){
    document.querySelector('#forSearchStore').addEventListener('click',function (e){
        //停止預設行為
        e.preventDefault();
        let uservalue = (document.querySelector("#user-enter-keyword").value).trim();
        //導向首頁
        console.log(uservalue);

        //導向後剩下的指令全部停止 所以將資料放在sessionStrage


        if(uservalue !==""){
            console.log(uservalue);

            sessionStorage.setItem("queryStr",uservalue);

            location.href='../homePage.html';



        }



    })
}

function queryStr(){
    if(sessionStorage.getItem("queryStr")){
        let data = sessionStorage.getItem("queryStr");
        setTimeout(function (){
            getStoreList("/xiaHuTea/search",data,"POST");

            setTimeout(function (){
                getDistance(0);
            },1000);

        },500);


        sessionStorage.removeItem("queryStr");
    }
}


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




function logOutSkip(){
    const tag = document.querySelector('#logOut');
    if(tag){
        tag.addEventListener('click',function (){
            Swal.fire({
                title: "確認登出?",
                showCancelButton: true,
                confirmButtonColor: "#73B4BA",
                cancelButtonColor: "#6c757d",
                confirmButtonText: "登出",
                cancelButtonText: "取消",
            }).then((result) => {
                if (result.isConfirmed) {
                    fetch("member/logout")
                        .then(res => res.text())
                        .then(data => {
                            sessionStorage.removeItem("memberData");
                            Swal.fire({
                                icon: "success",
                                title: "已登出",
                                showConfirmButton: false,
                                timer: 1000
                            }).then(() => {
                                location.replace("../login.html");
                            })
                        })

                }
            });
        });
    }
}

