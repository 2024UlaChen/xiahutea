document.addEventListener("DOMContentLoaded", function() {
    let stompClient = null;
    const url = `${location.pathname.substring(0, location.pathname.indexOf('/', 1) + 1)}websocket-endpoint`;

    let socket = new SockJS("/websocket-endpoint");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, () => {
        stompClient.subscribe('/store/notifications', function (resp) {
            showNotification(JSON.parse(resp.body));
        });
    });

    let notificationCount = 0; // 計算未讀通知數量

    function showNotification(data) {
        notificationCount++;

        // Header alert
        let alertHtml = `
            <span class="badge badge-warning navbar-badge">${notificationCount}</span>
        `;
        // 找到通知鈴鐺元素
        let bellIcon = document.querySelector(".fa-bell");

        // 檢查是否已經有 badge 元素存在，如果有則更新內容，否則插入新元素
        let existingBadge = bellIcon.nextElementSibling;
        if (existingBadge && existingBadge.classList.contains("badge")) {
            existingBadge.innerHTML = notificationCount;
        } else {
            bellIcon.insertAdjacentHTML('afterend', alertHtml);
        }

        // 訂單通知內容
        let notificationHtml = "";
        notificationHtml += `
            <div class="dropdown-divider"></div>
            <a href="./storeOrderDetail.html?orderId=${data.orderId}" class="dropdown-item">
                <i class="fas fa-envelope mr-2"></i> 新訂單 ${data.orderId}，請安排出單 
                <span class="float-right text-muted text-sm">${new Date(data.orderCreateDatetime).toLocaleString()}</span>
            </a>
        `;
        document.getElementById("notification").innerHTML += notificationHtml; // 使用 += 以便於添加新通知

        // 點了鈴鐺後 清空通知數量
        let notificationAlert = document.getElementById("notificationAlert");
        notificationAlert.addEventListener("click", function (){
            bellIcon.nextElementSibling.innerHTML = "";
        });
    }
});