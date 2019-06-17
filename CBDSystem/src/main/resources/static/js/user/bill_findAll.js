let main = new Vue({
    el: "#root",
    data() {
        return {
            form: { //模态框的数据
                time: ""
            },
            formLabelWidth: '120px',
            dealSum: 0,//总笔数
            payMoney: 0, //支出
            getMoney: 0,//收入
            getMoneySum: 0,//总金额
            centerDialogVisible: false,
            date: "", //时间
            stateDate: "",//开始时间
            endDate: "", //结束时间
            billType: "",  //类型
            currentPage: 1, //当前页数
            pageSize: 10, //每页显条数
            total: 0, //总条数
            complaint: "", //投诉内容
            complaintFlag: true,
            tableData: [],
            options: [{
                value: '个人租车账单',
                label: '个人租车账单'
            }, {
                value: '个人购车账单',
                label: '个人购车账单'
            }],
            biilInfo:{
                billDate: "",
                billId: 2,
                billMoney: 0,
                billRemark: "",
                billTime: "",
                billType: "",
                respondentUserId: '', //被投诉的id,
                userId:'',//投诉的id
                userName:''
            },
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
                }]
            },
        }
    },
    methods: {
        init() {
            $.ajax({
                type: "get",
                url: "../../bill/getBillAll",
                data: {
                    "billType": main.billType,
                    "currentPage": main.currentPage,
                    "pageSize": main.pageSize,
                    "oldDate": main.stateDate,
                    "newDate": main.endDate,
                },
                dataType: "json",
                success: function (resp) {
                    main.tableData = resp.data.page.records;
                    main.total = resp.data.page.total;

                }
            })
        //计算金额
            $.ajax({
                type: "get",
                url: "../../bill/getMoney",
                data: {
                },
                dataType: "json",
                success: function (resp) {
                    console.log(resp);
                    main.dealSum = resp.data.result.dealSum
                    main.payMoney = resp.data.result.payMoney;
                    main.getMoney = resp.data.result.getMoney;
                    main.getMoneySum = resp.data.result.getMoneySum;
                }
            })
        },
        handleEdit(index, row) {
            console.log(row);
            main.centerDialogVisible = true;
            $.ajax({
                type: "get",
                url: "../../bill/getBill",
                data: {
                    "id": row.billId
                },
                dataType: "json",
                success: function (resp) {
                    console.log(resp);
                    main.biilInfo = resp.data.result;
                    // 买方 就是乙方 给钱方 拥有投诉的能力
                    main.biilInfo.respondentUserId = resp.data.result.partlyB.userId;
                    main.biilInfo.userId = resp.data.result.partlyA.userId;
                    main.biilInfo.userName = resp.data.result.partlyA.userRealName;
                }
            })

            //查询投诉
            $.ajax({
                type: "get",
                url: "../../complaint/getByBillId",
                data: {
                    "billId": row.billId
                },
                dataType: "json",
                success: function (resp) {
                    if (resp.code != 404) {
                        main.complaint = resp.data.complaint.complaintDescription;
                        main.complaintFlag = false;
                    }
                }
            })

        },
        find(){
            if (main.date != null) {
                main.stateDate = main.date[0];
                main.endDate = main.date[1];
                main.init();
                return;
            }
            main.stateDate = "";
            main.endDate = "";
            //重新发送ajax
            main.init();
        },
        //添加投诉
        addComplain() {
            // 投诉描述	complaintDescription
            // 投诉类型	complaintTypes
            // 计费账单ID	billId
            // 投诉时间	complaintDate
            // 投诉方ID	userId
            // 被投诉方ID	respondentUserId
            $.ajax({
                type: "post",
                url: "../../complaint/save",
                data: {
                    billId: main.biilInfo.billId,
                    complaintDescription: main.complaint,
                    complaintTypes: main.biilInfo.billType,
                    userId: main.biilInfo.userId,
                    respondentUserId: main.biilInfo.respondentUserId,
                },
                dataType: "json",
                success: function (resp) {
                    if (resp.code == 200) {
                        main.$message({
                            message: '恭喜你，投诉成功',
                            type: 'success'
                        });
                    }
                }
            })
        },


        handleSizeChange(val) {
            main.pageSize = val;
            main.init();
        },
        handleCurrentChange(val) {
            main.currentPage = val;
            main.init();
        }
    }
})
main.init();
