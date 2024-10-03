function getUser(){
    $.ajax({
        url:'member',
        method:'post',
        success:function (data){
            if(!data){
                //console.log('未登入');
                document.querySelector('#userName').textContent = '';

            }else{
                console.log(data);
                document.querySelector('#userName').textContent = data.nickname;
                document.querySelector('#userLogo').src =`data:image/png;base64,${data.customerImg}`;

            }

        }
    });//取得登入
}