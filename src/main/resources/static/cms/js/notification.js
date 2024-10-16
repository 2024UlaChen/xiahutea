document.addEventListener("DOMContentLoaded", function() {
    const orders = getOrdersFromStorage();
    const unreadOrder = getUnreadOrder();
    let stompClient = null;

    for (let order of orders) {
        showNotification(order);
    }
    //----------------------------------------------------------
    // 判斷權限
    fetch(`/totalusers`)
        .then(resp => resp.json())
        .then(totalUserDTO => {
            console.log(totalUserDTO)
            if(totalUserDTO.userTypeId === 1){
                const storeId = totalUserDTO.userId;
                // 店家權限檢視
                initWebSocketStore(storeId);
            }else if(totalUserDTO.userTypeId === 3){
                initWebSocketAdmin();
            }
        })
        .catch((error) => {
            console.log("totalUserDTO 錯誤原因:" + error);
        });
    //----------------------------------------------------------
    // 連線WebSocket
    function initWebSocketStore(storeId) {
        let socket = new SockJS(`/websocket-endpoint`);  // ←端⼝
        stompClient = Stomp.over(socket);
        stompClient.connect({}, () => {
            console.log("WebSocket 連接成功")
            stompClient.subscribe(`/store/notifications/${storeId}`, function (resp) {  // ←訊息交換器
                const message = JSON.parse(resp.body);
                console.log("收到訂單通知:", message);  // 檢查收到的內容
                if (message.type === 1) {
                    sendOrderNotification(message);
                }
            });
        });
    }
    function initWebSocketAdmin() {
        let socket = new SockJS(`/websocket-endpoint`);  // ←端⼝
        stompClient = Stomp.over(socket);
        stompClient.connect({}, () => {
            console.log("WebSocket 連接成功")
            stompClient.subscribe(`/store/notifications`, function (resp) {  // ←訊息交換器
                const message = JSON.parse(resp.body);
                console.log("收到訂單通知:", message);  // 檢查收到的內容
                if (message.type === 2) {
                    sendDisputeNotification(message);
                }
            });
        });
    }
    function sendOrderNotification(message){
        const orders = getOrdersFromStorage();
        const unreadOrder = getUnreadOrder();

        orders.push(message);
        unreadOrder.push(message.orderId);

        sessionStorage.setItem('orders', JSON.stringify(orders));
        setUnreadOrder(unreadOrder);
        showOrder(message);
    }
    function sendDisputeNotification(message){
        const orders = getOrdersFromStorage();
        const unreadOrder = getUnreadOrder();

        orders.push(message);
        unreadOrder.push(message.orderId);

        sessionStorage.setItem('orders', JSON.stringify(orders));
        setUnreadOrder(unreadOrder);
        showDispute(message);
    }
//----------------------------------------------------------
    // 點了鈴鐺後 清空通知數量
    let notificationAlert = document.getElementById("notificationAlert");
    notificationAlert.addEventListener("click", function () {
        let bellIcon = document.querySelector(".fa-bell");
        let existingAlert = bellIcon.nextElementSibling;

        if (existingAlert) {
            existingAlert.style.display = 'none';
        }
        setUnreadOrder([]); // 清空未讀訂單ID列表
    });
});
//----------------------------------------------------------

function showNotification(data) {
    if (data.type === 1) {
        showOrder(data);
    } else if (data.type === 2) {
        showDispute(data);
    }
}
//----------------------------------------------------------
// 訂單通知內容
function showOrder(data) {
    const unreadOrder = getUnreadOrder();

    // Header alert
    let alertHtml = '';
    if (unreadOrder.length > 0) {
        alertHtml = `<span class="badge badge-warning navbar-badge">${unreadOrder.length}</span>`;
    }

    // 鈴鐺
    let bellIcon = document.querySelector(".fa-bell");

    // 檢查bellNum元素，顯示數字或更新數字，不顯示0
    let bellNum = bellIcon.nextElementSibling;
    if (bellNum && bellNum.classList.contains("badge")) {
        bellNum.innerHTML = unreadOrder.length > 0 ? unreadOrder.length : ''; // 只顯示大於0的數字
        if (unreadOrder.length === 0) {
            bellNum.style.display = 'none'; // 隱藏鈴鐺數字=0
        }
    } else {
        if (unreadOrder.length > 0) {
            bellIcon.insertAdjacentHTML('afterend', alertHtml);
        }
    }

    // 訂單通知內容
    let notificationHtml = `
        <div class="dropdown-divider"></div>
        <a href="./storeOrderDetail.html?orderId=${data.id}" class="dropdown-item">
            <i class="fas fa-envelope mr-2"></i> 新訂單 ${data.id}，請安排出貨 
            <span class="float-right text-muted text-sm">${new Date(data.createDatetime).toLocaleString()}</span>
        </a>
    `;

    // 最新的通知放最上面
    const notificationContainer = document.getElementById("notification");
    notificationContainer.insertAdjacentHTML('afterbegin', notificationHtml);

    // 點了鈴鐺 清空bellNum
    let notificationAlert = document.getElementById("notificationAlert");
    notificationAlert.addEventListener("click", function (){
        if (bellNum) {
            bellNum.style.display = 'none';
        }
        setUnreadOrder([]); // 清空未讀數字
    });
}
// 爭議通知內容
function showDispute(data) {
    const unreadOrder = getUnreadOrder();

    // Header alert
    let alertHtml = '';
    if (unreadOrder.length > 0) {
        alertHtml = `<span class="badge badge-warning navbar-badge">${unreadOrder.length}</span>`;
    }

    // 鈴鐺
    let bellIcon = document.querySelector(".fa-bell");

    // 檢查bellNum元素，顯示數字或更新數字，不顯示0
    let bellNum = bellIcon.nextElementSibling;
    if (bellNum && bellNum.classList.contains("badge")) {
        bellNum.innerHTML = unreadOrder.length > 0 ? unreadOrder.length : ''; // 只顯示大於0的數字
        if (unreadOrder.length === 0) {
            bellNum.style.display = 'none'; // 隱藏鈴鐺數字=0
        }
    } else {
        if (unreadOrder.length > 0) {
            bellIcon.insertAdjacentHTML('afterend', alertHtml);
        }
    }

    // 訂單通知內容
    let notificationHtml = `
        <div class="dropdown-divider"></div>
        <a href="./storeDisputeDetail.html?disputeOrderId=${data.id}" class="dropdown-item">
            <i class="fas fa-envelope mr-2"></i> 新爭議 ${data.id}，請進行確認 
            <span class="float-right text-muted text-sm">${new Date(data.createDatetime).toLocaleString()}</span>
        </a>
    `;

    // 最新的通知放最上面
    const notificationContainer = document.getElementById("notification");
    notificationContainer.insertAdjacentHTML('afterbegin', notificationHtml);

    // 點了鈴鐺 清空bellNum
    let notificationAlert = document.getElementById("notificationAlert");
    notificationAlert.addEventListener("click", function (){
        if (bellNum) {
            bellNum.style.display = 'none';
        }
        setUnreadOrder([]); // 清空未讀數字
    });
}


//----------------------------------------------------------
function getOrdersFromStorage() {
    const orders = sessionStorage.getItem('orders');
    return orders ? JSON.parse(orders) : [];
}

// 取得未讀通知
function getUnreadOrder() {
    const unreadOrder = localStorage.getItem('unreadOrder');
    return unreadOrder ? JSON.parse(unreadOrder) : [];
}
// 設定未讀通知
function setUnreadOrder(unreadOrder) {
    localStorage.setItem('unreadOrder', JSON.stringify(unreadOrder));
}