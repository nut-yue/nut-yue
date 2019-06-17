//数据的展示
var appointedUserBean;
var carportId;
//详细信息
var info;
let personal = new Vue({
    el: ".main",
    data(){
        return {
            isShow:true,
            form: {
                carportBean:{
                    carportAddress:"",
                    carportNumber:"",
                    carportProperty:"",
                },
                rentStartDate:"",
                message:""
            },
            formLabelWidth: '120px',
            pickerOptions2: {
                shortcuts: [{
                    text: '最近一周',
                    onClick(picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                        picker.$emit('pick', [start, end]);
                    }
                }, {
                    text: '最近一个月',
                    onClick(picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                        picker.$emit('pick', [start, end]);
                    }
                }, {
                    text: '最近三个月',
                    onClick(picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                        picker.$emit('pick', [start, end]);
                    }
                }],
            },
            showCarport:[],
            timeValue: '',
            carportAddress:"",
            currentPage: 1,
            pageSize:8,
            total:10,
            centerDialogVisible:false
        }
    },
    methods: {
        init(){
            // 初始化数据
            $.ajax({
                type: "get",
                url: "/rent/listRent",
                data: {
                    "carportAddress":personal.carportAddress,
                    "currentPage": personal.currentPage,
                    "pageSize": personal.pageSize,
                },
                dataType: "json",
                success: function (resultData) {
                     // console.log(resultData.data.page.records);
                    // personal.showCarport.img=""; //显示图片
                    personal.showCarport = resultData.data.page.records;
                    personal.total = resultData.data.page.total;

                }
            });
        },
        find(){
            console.log(personal.carportAddress);
            // 初始化数据
            $.ajax({
                type: "get",
                url: "/rent/listRent",
                data: {
                    "carportAddress":personal.carportAddress,
                    "currentPage": personal.currentPage,
                    "pageSize": personal.pageSize,
                },
                dataType: "json",
                success: function (resultData) {
                    // console.log(resultData.data.page.records);
                    // personal.showCarport.img=""; //显示图片
                    personal.showCarport = resultData.data.page.records;
                    personal.total = resultData.data.page.total;

                }
            });
        },
        showInfo(o){
            //查看详情
            console.log(o);
            $.ajax({
                type: "get",
                url: "/rent/getOne",
                data: {
                    "rentId": o.rentId,
                },
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData.data);

                     appointedUserBean=resultData.data.rentBean.carportBean.userBean.userId //获取被预约者id
                     carportId=resultData.data.rentBean.carportBean.carportId;//获取车辆id
                    // personal.showCarp‘ort.img=""; //显示图片
                    personal.form = resultData.data.rentBean;

                }
            });
            this.centerDialogVisible=true;
        },
        add(){
            //添加预约信息
            //获取输入框的内容
            // console.log(personal.form2.name);
            $.ajax({
                type: "get",
                url: "/user/addReservation",
                data: {
                    "reservationType":"租",
                    "reservationContent":personal.form.message,//预约内容
                    "userId":appointedUserBean,//被预约者id
                    "carportId":carportId,//车位id
                },
                dataType: "json",
                success: function (resultData) {
                    // console.log(resultData);
                    appointedUserBean=0;
                    carportId=0;
                    personal.form.message="";
                    //预约成功之后刷新页面
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
                websocket.send("租车位的预约消息="+appointedUserBean);
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
        order(){
            personal.isShow=false;
            personal.centerDialogVisible=true;
        },
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
            $.ajax({
                type: "get",
                url: "/rent/listRent",
                data: {
                    "carportAddress":personal.carportAddress,
                    "currentPage":1,
                    "pageSize": val,
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
            console.log(`当前页: ${val}`);
            $.ajax({
                type: "get",
                url: "/rent/listRent",
                data: {
                    "carportAddress":personal.carportAddress,
                    "currentPage":val,
                    "pageSize": personal.pageSize,
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
personal.init();