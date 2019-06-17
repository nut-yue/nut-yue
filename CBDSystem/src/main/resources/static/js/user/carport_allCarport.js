/*岳超*/
// 创建VUE对象
let carport = new Vue({
    // 绑定根节点
    el: ".main",
    // 数据
    data() {
        return {
            // 表单数据
            form: {
                rentStartDate: "",
                rentEndDate:"",
                rentPrice:"",
                salePrice:"",
                carportId:"",
                carportAddress:"",
                carportStatus:"",
                carportProperty:"",
                reservationContent:"",
                carportNumber:""
            },pickerOptions: {
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
                }]
            },
            options: [{
                value: '发布出租信息中',
                label: '发布出租信息中'
            }, {
                value: '发布出售信息中',
                label: '发布出售信息中'
            },{
                value: '待发布',
                label: '待发布'
            },{
                value: '空闲',
                label: '空闲'
            },{
                value: '已出租',
                label: '已出租'
            },{
                value: '审核中',
                label: '审核中'
            }],
            value1: '',
            value2: '',
            value4:"",
            formLabelWidth: '120px',
            showCarport: [],
            timeValue: '',
            carportAddress: "",
            carportStatus:"",
            currentPage: 1,
            pageSize: 10,
            total:0,
            // 查看详情模态框
            centerDialogVisible: false,
            // 发布出租模态框
            rentDialogVisible:false,
            // 发布出售模态框
            saleDialogVisible:false,
            // 查看出租信息详情模态框
            rentInfoDialogVisible:false,
            // 查看出售信息详情模态框
            saleInfoDialogVisible:false,
            // 查看已租赁等状态车位详情模态框
            otherInfoDialogVisible:false,
            // 已经出租车位信息展示模态框
            alreadyRentDialogVisible:false,
            valuePrice:""
        }
    },
    // 方法
    methods: {
        // 初始化
        init(){
            $.ajax({
                type:'get',
                url:"../../carport/listCarport",
                data:{"currentPage":this.currentPage,
                      "pageSize":this.pageSize,
                      // 传1后台判断
                      "userId":1,
                      "state":this.value4},
                success:function (datas) {
                    console.log(datas);
                    if(datas.code===200){
                        carport.total=datas.data.carports.total;
                        carport.showCarport=datas.data.carports.records;
                    }
                }
            })
        },
        // 查询
        find() {
            this.currentPage=1;
            carport.init();
        },
        // 模态框展示方法
        showInfo(o) {
            // 根据当前车位状态决定模态框内容
            if('待发布'===o.carportStatus){
                this.centerDialogVisible = true;
                this.form.carportAddress=o.carportAddress;
                this.form.carportProperty=o.carportProperty;
                this.form.carportNumber=o.carportNumber;
                this.form.carportId=o.carportId;
            } else if(o.carportStatus==='发布出租信息中'){
                this.form.carportAddress=o.carportAddress;
                this.form.carportProperty=o.carportProperty;
                this.form.carportNumber=o.carportNumber;
                this.form.carportStatus=o.carportStatus;
                this.form.carportId=o.carportId;
                $.ajax({
                    type:'get',
                    url:"../../carport/getCarport",
                    data:{"carportId":o.carportId},
                    success:function (datas) {
                        console.log(datas);
                        if(datas.code===200){
                            carport.form.rentPrice = datas.data.carport.rentBean.rentPrice;
                            carport.form.rentStartDate =  datas.data.carport.rentBean.rentStartDate;
                            carport.form.rentEndDate = datas.data.carport.rentBean.rentEndDate;
                        }
                    }
                });
                this.rentInfoDialogVisible = true;
            } else if('发布出售信息中'===o.carportStatus){
                this.form.carportAddress=o.carportAddress;
                this.form.carportProperty=o.carportProperty;
                this.form.carportNumber=o.carportNumber;
                this.form.carportStatus=o.carportStatus;
                this.form.carportId=o.carportId;
                $.ajax({
                    url:"../../carport/getCarport",
                    type:'get',
                    data:{"carportId":o.carportId},
                    success:function (datas) {
                        console.log(datas);
                        if(datas.code===200){
                            carport.form.salePrice = datas.data.carport.saleBean.salePrice;
                        }
                    }
                });
                this.saleInfoDialogVisible = true;
            } else if(o.carportStatus==="已出租"){
                this.form.carportAddress=o.carportAddress;
                this.form.carportProperty=o.carportProperty;
                this.form.carportNumber=o.carportNumber;
                this.form.carportStatus=o.carportStatus;
                this.form.carportId=o.carportId;
                $.ajax({
                    type:'get',
                    url:"../../transaction/findByCar",
                    data:{"carportId":o.carportId},
                    success:function (datas) {
                        console.log(datas);
                        if(datas.code===200){
                            carport.form.rentPrice = datas.data.trans.transactionPrice;
                            carport.form.rentStartDate = datas.data.trans.transactionTime;
                            carport.form.rentEndDate = datas.data.trans.transactionEndTime;
                        }
                    }
                });
                this.alreadyRentDialogVisible = true;
            } else {
                this.form.carportAddress=o.carportAddress;
                this.form.carportProperty=o.carportProperty;
                this.form.carportNumber=o.carportNumber;
                this.form.carportId=o.carportId;
                this.otherInfoDialogVisible = true;
            }
        },
        // 每页显示条数变化
        handleSizeChange(val) {
            if (this.value4 != "") {
                this.currentPage = 1;
            }
            console.log(`每页 ${val} 条`);
            this.pageSize = val;
            carport.init();
        },
        // 页数变化
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
            if (this.value4 != "") {
                this.currentPage = 1;
            }
            this.currentPage = val;
            carport.init();
        },
        // 打开发布出租信息
        rent(){
            this.value2="";
            this.form.rentPrice="";
            this.centerDialogVisible=false;
            this.rentDialogVisible = true;
        },
        // 打开发布出售信息
        sell(){
            this.form.salePrice="";
            this.centerDialogVisible=false;
            this.saleDialogVisible = true;
        },
        // 发布出租
        confirmRent(){
            $.ajax({
                type:'post',
                url:"../../rent/saveRent",
                data:{"rentStartDate":this.value2[0],
                    "rentEndDate":this.value2[1],
                    "rentPrice":this.form.rentPrice,
                    "carportId":this.form.carportId},
                success:function (datas) {
                    if(datas.code===200){
                        carport.$message.success('发布成功');
                    } else {
                        carport.$message.error('发布失败');
                    }
                    carport.init();
                    carport.rentDialogVisible=false;
                }
            });
        },
        // 发布出售
        confirmSell(){
            $.ajax({
                type:'post',
                url:"../../user/addSale",
                data:{
                    "salePrice":this.form.salePrice,
                    "carportId":this.form.carportId},
                dataType:'json',
                success:function (datas) {
                    if(datas.code===200){
                        carport.$message.success('发布成功');
                    } else {
                        carport.$message.error('发布失败');
                    }
                    carport.init();
                    carport.saleDialogVisible=false;
                }
            });
        },
        // 下架
        checkout(){
            console.log(carport.form.carportStatus);
            $.ajax({
                url:"../../carport/soldOut",
                type:'post',
                data:{_method:"PUT",
                    "carportId":carport.form.carportId,
                    "carportStatus":carport.form.carportStatus},
                success:function (datas) {
                    if(datas.code===200){
                        carport.$message.success('下架成功');
                    } else {
                        carport.$message.error('下架失败');
                    }
                    carport.init();
                    carport.rentInfoDialogVisible=false;
                    carport.saleInfoDialogVisible=false;
                }
            });

        },
        back(){
            this.alreadyRentDialogVisible=false;
            this.otherInfoDialogVisible=false;
        }
    }
});
// 页面初始化
carport.init();



