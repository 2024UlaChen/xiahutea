let stompClient = null;

// todo
const url = `${location.pathname.substring(0, location.pathname.indexOf('/', 1) + 1)}websocket-endpoint`;

// function connect() {
//     let socket = new SockJS('/websocket-endpoint');
//     stompClient = Stomp.over(socket);
//     stompClient.connect({}, () => {
//
//         stompClient.subscribe('/store/notifications', function (resp) {
//             showNotification(JSON.parse(resp.body));
//         });
//     });
// }


const data = [
    {
        orderId: 101,
        date: '2024/09/26 11:50'
    },
    {
        orderId: 102,
        date: '2024/09/26 11:50'
    }
];

showNotification(data);

function showNotification(data) {

    let alertHtml;
    alertHtml =`
        <a class="nav-link" data-toggle="dropdown" href="#">
            <i class="far fa-bell"></i>
            <span class="badge badge-warning navbar-badge" id="notificationAlert">${data.length}</span>
        </a>
        <div class="dropdown-menu dropdown-menu-lg dropdown-menu-right" id="notification">
        <!-- JS       -->
        </div>
    `;
    document.getElementById("notificationArea").innerHTML = alertHtml;

    // 顯示推播通知，可以是訂單或爭議消息
    let notificationHtml= "";
    for(i = 0; i < data.length; i++){
        notificationHtml += `
                <div class="dropdown-divider"></div>
                <a href="#" class="dropdown-item">
                  <i class="fas fa-envelope mr-2"></i> 新訂單 ${data[i].orderId}，請安排出單 
                  <span class="float-right text-muted text-sm">${data[i].date}</span>
                </a>
        `;
    }
    document.getElementById("notification").innerHTML = notificationHtml;
}
// window.onload = connect;