let stompClient = null;

$(document).ready(function() {
    connect();
})

function showGreeting(message) {
    let socketAlert = $("#socketAlert");
    socketAlert.html(message);
    socketAlert.css('display', 'block');
    setTimeout(function() {
        socketAlert.css('display', 'none');
    }, 5000);
}

function connect() {
    let socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/user/topic/public', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        })
    })
}

function sendName(myId, postId) {
    stompClient.send("/app/recommend", {}, JSON.stringify({'senderId' : myId, 'postId': postId}));
}
