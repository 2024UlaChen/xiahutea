document.addEventListener("DOMContentLoaded", function() {
    let stompClient = null;
    const url = `${location.pathname.substring(0, location.pathname.indexOf('/', 1) + 1)}websocket-endpoint`;



    const data = [
        { orderId: 101, date: '2024/09/26 11:50' },
        { orderId: 102, date: '2024/09/26 11:50' },
        { orderId: 105, date: '2024/09/26 11:50' },
        { orderId: 108, date: '2024/09/26 11:50' }
    ];


    function showNotification(data) {
        // Header alert
        let alertHtml = `
            <a class="nav-link" id="notificationAlert" data-toggle="dropdown" href="#">
                <i class="far fa-bell"></i>
                <span class="badge badge-warning navbar-badge">${data.length}</span>
            </a>
            <div class="dropdown-menu dropdown-menu-lg dropdown-menu-right" id="notification">
            </div>
        `;
        document.getElementById("notificationArea").innerHTML = alertHtml;

        // 通知列表
        let notificationHtml = "";
        for(let i = 0; i < data.length; i++){
            notificationHtml += `
                <div class="dropdown-divider"></div>
                <a href="./storeOrderDetail.html?orderId=${data[i].orderId}" class="dropdown-item">
                    <i class="fas fa-envelope mr-2"></i> 新訂單 ${data[i].orderId}，請安排出單 
                    <span class="float-right text-muted text-sm">${data[i].date}</span>
                </a>
            `;
        }
        document.getElementById("notification").innerHTML = notificationHtml;

        // 鈴鐺數字
        let alertNum = document.getElementsByClassName("badge")[0];
        let notificationAlert = document.getElementById("notificationAlert");
        notificationAlert.addEventListener("click", function (){
            // 點了鈴鐺 清空數據數組
            // data.splice(0, data.length);
            data.length = 0;
            alertNum.innerText = 0;
            alertNum.classList.add("-off");
            updateNotification();
        });


        checkAlertNum();
        function checkAlertNum() {
            if (parseInt(alertNum.innerText) === 0) {
                alertNum.classList.add("-off");
            } else {
                alertNum.classList.remove("-off");
            }
        }


        function updateNotification() {
            let notificationBadge = document.querySelector("#notificationAlert .navbar-badge");
            if (notificationBadge) {
                notificationBadge.textContent = data.length;
            } else {
                let alertHtml = `
                    <a class="nav-link" id="notificationAlert" data-toggle="dropdown" href="#">
                        <i class="far fa-bell"></i>
                        <span class="badge badge-warning navbar-badge">${data.length}</span>
                    </a>
                    <div class="dropdown-menu dropdown-menu-lg dropdown-menu-right" id="notification">
                    </div>
                `;
                document.getElementById("notificationArea").innerHTML = alertHtml;
            }
        }
    }


    function addNewNotification(newNotification) {
        data.push(newNotification);
        showNotification(data);
    }
    showNotification(data);

    // Uncomment below to enable WebSocket connection
    // function connect() {
    //     let socket = new SockJS('/websocket-endpoint');
    //     stompClient = Stomp.over(socket);
    //     stompClient.connect({}, () => {
    //         stompClient.subscribe('/store/notifications', function (resp) {
    //             showNotification(JSON.parse(resp.body));
    //         });
    //     });
    // }
    // window.onload = connect;
});