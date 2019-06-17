let main = new Vue({
    el: ".main",
    data() {
        return {
            username: "test",
            collapseBtnClick: false,
            isCollapse: true,
            user:{
                username:"",
            }
        };

    },
    methods: {
        init(){
            $.ajax({
                type: "post",
                url: "/user/getUser",
                dataType: "json",
                success: function (resp) {
                    console.log(resp);
                    main.user.username= resp.data.admin.userRealName;
                }
            })
        },
        logout(){
            //注销登录方法
            window.parent.show.location.href = "/logout";
            //刷新当前页面
            window.location.replace("../login.html");
        },
        carportBuy() {
            window.parent.show.location.href = "carport_buy.html";
        },
        carportSell() {
            window.parent.show.location.href = "carport_sell.html";
        },
        appointer() {
            window.parent.show.location.href = "reservation_findByAppointerUserId.html";
        },
        appointed() {
            window.parent.show.location.href = "reservation_findByAppointedUserId.html";
        },
        bill() {
            window.parent.show.location.href = "bill_findAll.html";
        },
        complaint() {
            window.parent.show.location.href = "complaint_show.html";
        },
        myCarport() {
            window.parent.show.location.href = "carport_allCarport.html";
        },
        carportAdd() {
            window.parent.show.location.href = "carport_add.html";
        },
        personal() {
            window.parent.show.location.href = "personal.html";
        },
        userMessage() {
            window.parent.show.location.href = "findUserMessage.html";
        },
        myInfo() {
            window.parent.show.location.href = "user_update.html";
        },
        collapseStatus() {
            this.collapseBtnClick = this.isCollapse;
            this.isCollapse = !this.isCollapse;
        },
        collapseOpen() {
            if (this.collapseBtnClick) return;
            this.isCollapse = false;
        },
        collapseClose() {
            if (this.collapseBtnClick) return;
            this.isCollapse = true;
        }
    }
});
main.init();
$(function () {
    var websocket = null;
    let id = window.location.search.split('=')[1];

    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://127.0.0.1:8080/webSocket/cbd/user/" + id);
    }
    else {
        alert("对不起！你的浏览器不支持webSocket")
    }
    //连接发生错误的回调方法
    websocket.onerror = function () {};
    //连接成功建立的回调方法
    websocket.onopen = function (event) {};
    websocket.onmessage = function (event) {
        main.$notify.info({
            title: '消息',
            message: "你有一条未读消息："+event.data
        });
    };


    //连接关闭的回调方法
    websocket.onclose = function () {};
    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，
    // 防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        var is = confirm("确定关闭窗口？");
        if (is) {
            websocket.close();
        }
    };

    //关闭连接
    function closeWebSocket() {
        websocket.close();
    }
});