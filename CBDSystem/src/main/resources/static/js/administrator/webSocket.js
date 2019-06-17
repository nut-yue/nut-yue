$(function () {
    var websocket = null;
    let id = window.location.search.split('=')[1];

    //发送消息
    var message = $("#text").val();

    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://127.0.0.1:8080/webSocket/cbd/admin/" + id);
    }
    else {
        alert("对不起！你的浏览器不支持webSocket")
    }
    //连接发生错误的回调方法
    websocket.onerror = function () {
        setMessageInnerHTML("error");
    };
    //连接成功建立的回调方法
    websocket.onopen = function (event) {
        setMessageInnerHTML("加入连接");
    };
    websocket.onmessage = function (event) {
        console.log(event.data)
        setMessageInnerHTML(event.data);
    };


    //连接关闭的回调方法
    websocket.onclose = function () {
        setMessageInnerHTML("断开连接");
    };
    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，
    // 防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {};

    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        $("#msg").append(innerHTML + "<br/>")
    };

    //关闭连接
    function closeWebSocket() {
        websocket.close();
    }
});