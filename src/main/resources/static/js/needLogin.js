function needLogin() {
    console.log("hello");
    if (confirm("로그인이 필요합니다. 로그인하시겠습니까?") == true) {
        window.location.href = '/login';
    } else {
        window.location.href = location.href;
    }
}