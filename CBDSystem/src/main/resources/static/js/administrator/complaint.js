let main = new Vue({
    el: ".main",
    data() {
        return {
            form: {
                time: "",
                adminName: "",
                userRealName: "",
                userIdCard: "",
                userProfession: "",
                userPhone: "",
                complaintDescription: "",
                billType: "",
                adminName1: "",
                userRealName1: "",
                userIdCard1: "",
                userProfession1: "",
                userPhone1: "",
                transactionTime: "",
                transactionEndTime: "",
                carportAddress: "",
                carportNumber: "",
            },
            formLabelWidth: '120px',
            centerDialogVisible: false,
            time: "",
            currentPage: 1,
            pageSize: 10,
            total: 0,
            tableData: [],
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
            value7: ''
        }
    },
    methods: {
        handleEdit(index, row) {

            // console.log(row);
            $.ajax({
                type: "get",
                url: "../../complaint/getOne",
                data: {"id": row.complaintId,},
                dataType: "json",
                success: function (resultData) {
                    main.form.adminName = resultData.data.complaint.userBean.adminBean.adminName;
                    main.form.userRealName = resultData.data.complaint.userBean.userRealName;
                    main.form.userIdCard = resultData.data.complaint.userBean.userIdCard;
                    main.form.userProfession = resultData.data.complaint.userBean.userProfession;
                    main.form.userPhone = resultData.data.complaint.userBean.userPhone;
                    main.form.complaintDescription = resultData.data.complaint.complaintDescription;

                    main.form.adminName1 = resultData.data.complaint.respondentUserBean.adminBean.adminName;
                    main.form.userRealName1 = resultData.data.complaint.respondentUserBean.userRealName;
                    main.form.userIdCard1 = resultData.data.complaint.respondentUserBean.userIdCard;
                    main.form.userProfession1 = resultData.data.complaint.respondentUserBean.userProfession;
                    main.form.userPhone1 = resultData.data.complaint.respondentUserBean.userPhone;


                    complaintId = resultData.data.complaint.complaintId;
                    userId = resultData.data.complaint.userBean.userId;

                    billId = resultData.data.complaint.billBean.billId;
                    main.findTransaction(billId);
                }
            })

            main.centerDialogVisible = true;
        },

        showAll() {
            $.ajax({
                type: "get",
                url: "../../complaint/findAll",
                data: {"userId": -1, "currentPage": main.currentPage, "pageSize": main.pageSize},
                dataType: "json",
                success: function (resultData) {
                    // console.log(resultData);
                    main.tableData = resultData.data.page.records;
                    main.total = resultData.data.page.total;

                }
            })
        },

        findTransaction(billId) {
            console.log(billId);
            $.ajax({
                type: "get",
                url: "../../bill/getBill",
                data: {
                    "id": billId,
                },
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData.data);
                    // carportId=resultData.data.result.carportId;
                    main.form.billType = resultData.data.result.billType;
                    main.form.carportAddress = resultData.data.result.carportBean.carportAddress;
                    // main.billinfo(carportId);
                }

            })

        },
        billinfo() {
            console.log(carportId);
            $.ajax({
                type: "get",
                url: "../../carport/getCarport",
                data: {
                    "carportId": carportId,
                },
                dataType: "json",
                success: function (resultData) {

                    // console.log(resultData);
                    main.form.carportAddress = resultData.data.carport.carportAddress;
                    main.form.carportNumber = resultData.data.carport.carportNumber;
                }

            })
        },
        find() {
            if (main.value7 == null) {
                main.showAll();
            } else {
                $.ajax({
                    type: "get",
                    url: "../../complaint/findAll",
                    data: {
                        "userId": -1,
                        "currentPage": main.currentPage,
                        "pageSize": main.pageSize,
                        "oldDate": main.value7[0],
                        "newDate": main.value7[1]
                    },
                    dataType: "json",
                    success: function (resultData) {
                        console.log(resultData);
                        main.tableData = resultData.data.page.records;
                        main.total = resultData.data.page.total;
                    }
                })
            }
        },
        //审批投诉
        accraditation() {
            console.log(userId +
                '  =================' + complaintId);
            $.ajax({
                type: "get",
                url: "../../complaint/update",
                data: {
                    "complaintId": complaintId,
                    "complaintStatus": "已处理",
                    "userId": userId,
                },
                dataType: "json",
                success: function (resultData) {
                    //  console.log(resultData);
                    if (resultData.message == "ok") {
                        main.centerDialogVisible = false;
                        main.showAll();
                    }
                }
            })
        },


        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
            main.pageSize = val;

            if (main.value7[0] != null || main.value7[1] != null) {
                main.find()
            } else {
                main.showAll();
            }

        },
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
            main.currentPage = val;
            main.showAll();
        },

        //驳回投诉
        delet() {

            $.ajax({
                type: "get",
                url: "../../complaint/update",
                data: {
                    "complaintId": complaintId,
                    "complaintStatus": "已驳回",
                    "userId": userId,
                },
                dataType: "json",
                success: function (resultData) {
                    //  console.log(resultData);
                    main.centerDialogVisible = false;
                    main.showAll();
                }
            })
        },
    }
})

main.showAll();
var complaintId = 0;
var complaintStatus = 0;
var userId = 0;
var billId = 0;
var carportId = 0;