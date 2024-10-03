function getUser(){
    $.ajax({
        url:'member',
        method:'post',
        success:function (data){
            if(!data){
                //console.log('未登入');
                document.querySelector('#userName').textContent = '';

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
        getStoreList("/xiaHuTea/search",data,"POST");

        setTimeout(function (){
            getDistance(0);
        },1000);

        sessionStorage.removeItem("queryStr");
    }
}