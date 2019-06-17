
/*岳超*/
let main = new Vue({
    el: ".main",
    data() {
        return {
            form: {
                tenantryContractNum: "",
                tenantryDealPrice:"",
                tenantryStartTime:"",
                tenantryEndTime:"",
                tenantryContractStatus:""
            },
            money:{
                getMoney:"",
                payMoney:"",
                dealSum:"",
                getMoneySum:""
            },
            formLabelWidth: '120px',
            billNum: "",
            billOut: "",
            billIn: "",
            billAll: "",
            centerDialogVisible:false,
            time:"",
            currentPage: 1,
            pageSize:10,
            total:1000,
            tableData: [{
                billDate: '',
                billTime: '',
                billMoney: ''
            }],
            options: [{
                value: '外部合约',
                label: '外部合约'
            }, {
                value: '租户合约',
                label: '租户合约'
            }],
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
            value7: '',
            value4: ''
        }
    },
    methods: {
        timeNotNull(){
            $.ajax({
                type: "get",
                url: "../../companyBill/getCBDBillPage",
                <!--id是controller中通过sessiond得到-->
                data: {"currentPage": main.currentPage, "pageSize": main.pageSize,"type":main.value4,"endTime":main.value7[1],"starTime":main.value7[0],},
                dataType: "json",
                success: function (resultData) {
                    main.tableData = resultData.data.page.records;
                    main.total = resultData.data.page.total;
                }
            });
        },
        timeNull(){
            $.ajax({
                type: "get",
                url: "../../companyBill/getCBDBillPage",
                <!--id是controller中通过sessiond得到-->
                data: {"currentPage": main.currentPage, "pageSize": main.pageSize,"type":main.value4},
                dataType: "json",
                success: function (resultData) {
                    main.tableData = resultData.data.page.records;
                    main.total = resultData.data.page.total;
                }
            });
        },
        getcbdMoney(){
            $.ajax({
                type: "get",
                url: "../../companyBill/cbdMoney",
                data: {},
                dataType: "json",
                success: function (resultData) {
                    main.money=resultData.data.count;
                }
            });
        },
        initList() {
            $.ajax({
                type: "get",
                url: "../../companyBill/getCBDBillPage",
                <!--id是controller中通过sessiond得到-->
                data: {"currentPage": main.currentPage, "pageSize": main.pageSize},
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData);
                    if (resultData.data.page.records!= null) {
                        main.tableData = resultData.data.page.records;
                        main.total = resultData.data.page.total;
                    }
                    //  console.log(resultData.data.page.records);
                    else {
                        main.$message.error('没有记录');
                    }
                }
            });
        },
        handleEdit(index, row) {
            console.log(row);
            $.ajax({
                type: "get",
                url: "../../companyBill/getBillById",
                data: {"id": row.pactId,"billType":row.companyBillPactType},
                dataType: "json",
                success: function (resultData) {
                    if (resultData.data.bill != null)
                    main.form=resultData.data.bill;
                    else {
                        main.form.tenantryContractNum="";
                        main.form.tenantryDealPrice="";
                        main.form.tenantryStartTime="";
                        main.form.tenantryEndTime="";
                        main.form.tenantryContractStatus="";
                        main.$message.error('获取数据失败');
                    }
                }
            });
            main.centerDialogVisible=true;
           // main.getcbdMoney();
        },
        find(){
            this.currentPage=1;
            if(main.value7!=null){
              main.timeNotNull();
            }
            else {
            main.timeNull();
            }
        },
        handleSizeChange(val) {
            main.pageSize = val;
            if(main.value7!=null){
               main.timeNotNull();
            }
            else {
                main.timeNull();
            }

            //console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
            main.currentPage = val;
            if(main.value7!=null){
              main.timeNotNull();
            }
            else {
              main.timeNull();
            }
        }
    }
});
main.initList();
main.getcbdMoney();