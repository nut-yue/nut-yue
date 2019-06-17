//数据的展示
//被预言者id
var appointedUserBean;
//车位id
var carportId;
//车辆详细信息
var info;
let personal = new Vue({
    el: ".main",
    data() {
        return {
            showCarport:[],
            isShow:true,
            form:{
                carportAddress:"",
                carportNumber:"",
                carportProperty:"",
                userRealName:""
            },
            form2:{},
            formLabelWidth: '120px',
            showCarport: [{
                img: "22.jpg",
                carportBean:{
                    carportAddress:""
                },
                salePrice: ""
            }],
            timeValue: '',
            carportAddress: "",
            currentPage: 1,
            pageSize: 10,
            total: 10,
            centerDialogVisible: false
        }
    },
    methods: {
        init(){
            $.ajax({
                type: "get",
                url: "/user/findAllSaleInfo",
                data: {
                    "carportAddress":personal.carportAddress,
                    "currentPage": personal.currentPage,
                    "pageSize": personal.pageSize,
                    // "state":"发布出售信息中"
                },
                dataType: "json",
                success: function (resultData) {

                      console.log(resultData.data);
                    // personal.showCarport.img=""; //显示图片
                     personal.showCarport = resultData.data.findAllSaleInfo.records;
                     personal.total = resultData.data.findAllSaleInfo.total;
                }
            });
        },
        find() {
            console.log(this.carportAddress);
            //条件查询
            $.ajax({
                type: "get",
                url: "/user/findAllSaleInfo",
                data: {
                    "carportAddress":personal.carportAddress,
                    "currentPage": 1,
                    "pageSize": personal.pageSize,
                },
                dataType: "json",
                success: function (resultData) {
                     // console.log(resultData.data.findAllSaleInfo);
                    // personal.showCarport.img=""; //显示图片
                    personal.showCarport = resultData.data.findAllSaleInfo.records;
                    personal.total = resultData.data.findAllSaleInfo.total;
                    personal.currentPage=1;
                }
            });

        },
        showInfo(o) {
            // 查看详情
            //  console.log(o.saleId);
            personal.isShow=true;
            $.ajax({
                type: "get",
                url: "/user/getSale",
                data: {
                    "saleId": o.saleId
                },
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData.data.getSale);
                    //车辆id
                    carportId=resultData.data.getSale.carportBean.carportId;
                    //被预约者id
                    appointedUserBean=resultData.data.getSale.carportBean.userBean.userId;

                     personal.form = resultData.data.getSale.carportBean;
                     personal.form.userRealName=resultData.data.getSale.carportBean.userBean.userRealName;
                }
            });
            this.centerDialogVisible=true;
        },
        order(o){
            personal.isShow=false;
            personal.centerDialogVisible=true;

        },
        add(){
            //获取输入框的内容
            // console.log(personal.form2.name);
            $.ajax({
                type: "get",
                url: "/user/addReservation",
                data: {
                    "reservationType":"买",
                    "reservationContent":personal.form2.name,//预约内容
                    "userId":appointedUserBean,//被预约者id
                    "carportId":carportId,//车位id
                },
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData);
                    appointedUserBean=0;
                    carportId=0;
                    personal.form2.name="";
                    //预约之后刷新表格
                    personal.init();
                }
            });
            personal.centerDialogVisible = false;



        //发送预约的短消息
            var websocket = null;
            //判断当前浏览器是否支持WebSocket
            if ('WebSocket' in window) {
                websocket = new WebSocket("ws://127.0.0.1:8080/webSocket/cbd/rest/" + -1);
            }
            else {
                alert("对不起！你的浏览器不支持webSocket")
            }
            //连接发生错误的回调方法
            websocket.onerror = function () {};
            //连接成功建立的回调方法
            websocket.onopen = function (event) {
                websocket.send("买车位的预约消息="+appointedUserBean);
            };
            websocket.onmessage = function (event) { };

            //连接关闭的回调方法
            websocket.onclose = function () {};
            //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，
            // 防止连接还没断开就关闭窗口，server端会抛异常。
            window.onbeforeunload = function () {};
            //关闭连接
            function closeWebSocket() {websocket.close();}

        },
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
            $.ajax({
                type: "get",
                url: "/user/findAllSaleInfo",
                data: {
                    "carportAddress":personal.carportAddress,
                    "currentPage":1,
                    "pageSize": val,
                    // "state":"发布出售信息中"
                },
                dataType: "json",
                success: function (resultData) {
                    // console.log(resultData.data.findAllSaleInfo.records);
                    // personal.showCarport.img=""; //显示图片
                    personal.showCarport = resultData.data.findAllSaleInfo.records;
                    personal.total = resultData.data.findAllSaleInfo.total;
                }
            });

        },
        handleCurrentChange(val) {
            // console.log(`当前页: ${val}`);
            $.ajax({
                type: "get",
                url: "/user/findAllSaleInfo",
                data: {
                    "carportAddress":personal.carportAddress,
                    "currentPage": val,
                    "pageSize": personal.pageSize,
                    // "state":"发布出售信息中"
                },
                dataType: "json",
                success: function (resultData) {
                    // console.log(resultData.data.findAllSaleInfo.records);
                    // personal.showCarport.img=""; //显示图片
                    personal.showCarport = resultData.data.findAllSaleInfo.records;
                    personal.total = resultData.data.findAllSaleInfo.total;

                }
            });
        }
    }
});
//初始化数据
personal.init();