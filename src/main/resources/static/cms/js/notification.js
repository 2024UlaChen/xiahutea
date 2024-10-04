document.addEventListener("DOMContentLoaded", function() {
    const orders = getOrdersFromStorage();
    const unreadOrder = getUnreadOrder();

    for (let order of orders) {
        showNotification(order);
    }

    let stompClient = null;

    // todo 待設定權限
    const url = `${location.pathname.substring(0, location.pathname.indexOf('/', 1) + 1)}websocket-endpoint`;

    let socket = new SockJS("/websocket-endpoint");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, () => {
        stompClient.subscribe('/store/notifications', function (resp) {
            const order = JSON.parse(resp.body);
            const orders = getOrdersFromStorage();
            const unreadOrder = getUnreadOrder();

            orders.push(order);
            unreadOrder.push(order.orderId);

            sessionStorage.setItem('orders', JSON.stringify(orders));
            setUnreadOrder(unreadOrder);
            showNotification(order);
        });
    });
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

function showNotification(order) {
    const unreadOrder = getUnreadOrder();

    // Header alert
    let alertHtml = '';
    if (unreadOrder.length > 0) {
        alertHtml = `
            <span class="badge badge-warning navbar-badge">${unreadOrder.length}</span>
        `;
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
        <a href="./storeOrderDetail.html?orderId=${order.orderId}" class="dropdown-item">
            <i class="fas fa-envelope mr-2"></i> 新訂單 ${order.orderId}，請安排出單 
            <span class="float-right text-muted text-sm">${new Date(order.orderCreateDatetime).toLocaleString()}</span>
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