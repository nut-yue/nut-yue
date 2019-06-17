let main = new Vue({
    el: ".main",
    data() {
        return {
            formLabelWidth: '120px',
            currentPage: 1,
            pageSize: 10,
            total: 100,
            time: "",
            form: {
                time: "",
                complaintDate: "",
                billMoney: "",
                userProfession:"",
                complaintStatus: ""
            },
            tableData: [{
                date: '',
                respondentUserName: '',
                complaintStatus: '',
                address: '',
                complaintDescription: '',
                complaintTypes: ''
            }],
            centerDialogVisible: false,
            value7: "",
            value4: "",
            input: '',
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
        initList() {
            $.ajax({
                type: "get",
                url: "../../complaint/findAll",

                <!--id是controller中通过sessiond得到-->
                data: {"userId":0,"currentPage": main.currentPage, "pageSize": main.pageSize},
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData);
                    //  console.log(resultData.data.page.records);
                    main.tableData = resultData.data.page.records;
                    main.total = resultData.data.page.total;
                }
            });
        },
        handleEdit(index, row) {
            $.ajax({
                type: "get",
                url: "../../complaint/getOne",
                data: {"id": row.complaintId},
                dataType: "json",
                success: function (resultData) {
                    //console.log(resultData);
                    main.form=resultData.data.complaint;
                    main.form.billMoney=resultData.data.complaint.billBean.billMoney;
                    main.form.userProfession=resultData.data.complaint.respondentUserBean.userProfession;
                }
            });
            main.centerDialogVisible = true;
        },
        find() {
            if(main.value7!=null){
                this.currentPage=1;
                $.ajax({
                    type: "get",
                    url: "../../complaint/findAll",
                    <!--id是controller中通过sessiond得到-->
                    data: {"userId":0,"currentPage": main.currentPage, "pageSize": main.pageSize,"oldDate":main.value7[0],"newDate":main.value7[1]},
                    dataType: "json",
                    success: function (resultData) {
                        main.tableData = resultData.data.page.records;
                        main.total = resultData.data.page.total;
                    }
                });
            }
           else {
                this.currentPage=1;
                $.ajax({
                    type: "get",
                    url: "../../complaint/findAll",
                    <!--id是controller中通过sessiond得到-->
                    data: {"userId":0,"currentPage": main.currentPage, "pageSize": main.pageSize,},
                    dataType: "json",
                    success: function (resultData) {
                        main.tableData = resultData.data.page.records;
                        main.total = resultData.data.page.total;
                    }
                });
            }
            //console.log(main.value7[0]);
           // console.log(main.value7[1]);
        },
        handleSizeChange(val) {
            main.pageSize = val;
            if(main.value7!=null){
                $.ajax({
                    type: "get",
                    url: "../../complaint/findAll",
                    <!--id是controller中通过sessiond得到-->
                    data: {"userId":0,"currentPage": main.currentPage, "pageSize": main.pageSize,"oldDate":main.value7[0],"newDate":main.value7[1]},
                    dataType: "json",
                    success: function (resultData) {
                        main.tableData = resultData.data.page.records;
                        main.total = resultData.data.page.total;
                    }
                });
            }
            else {
                $.ajax({
                    type: "get",
                    url: "../../complaint/findAll",
                    <!--id是controller中通过sessiond得到-->
                    data: {"userId":-1,"currentPage": main.currentPage, "pageSize": main.pageSize,},
                    dataType: "json",
                    success: function (resultData) {
                        main.tableData = resultData.data.page.records;
                        main.total = resultData.data.page.total;
                    }
                });
            }
        },
        handleCurrentChange(val) {
            main.currentPage = val;
            if(main.value7!=null){
                $.ajax({
                    type: "get",
                    url: "../../complaint/findAll",
                    <!--id是controller中通过sessiond得到-->
                    data: {"userId":0,"currentPage": main.currentPage, "pageSize": main.pageSize,"oldDate":main.value7[0],"newDate":main.value7[1]},
                    dataType: "json",
                    success: function (resultData) {
                        main.tableData = resultData.data.page.records;
                        main.total = resultData.data.page.total;
                    }
                });
            }
            else {
                $.ajax({
                    type: "get",
                    url: "../../complaint/findAll",
                    <!--id是controller中通过sessiond得到-->
                    data: {"userId":0,"currentPage": main.currentPage, "pageSize": main.pageSize,},
                    dataType: "json",
                    success: function (resultData) {
                        main.tableData = resultData.data.page.records;
                        main.total = resultData.data.page.total;
                    }
                });
            }
        }
    },
});
$.ajax({
    type: "get",
    url: "../../complaint/findAll",

    <!--id是controller中通过sessiond得到-->
    data: {"userId":0,"currentPage": main.currentPage, "pageSize": main.pageSize},
    dataType: "json",
    success: function (resultData) {
        console.log(resultData);
        //  console.log(resultData.data.page.records);
        main.tableData = resultData.data.page.records;
        main.total = resultData.data.page.total;
    }
});