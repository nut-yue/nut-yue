let main = new Vue({
    el: ".main",
    data() {
        return {
            form: {
                tenantryContractNum: "",
                tenantryDealPrice: "",
                tenantryStartTime: "",
                tenantryEndTime: "",
                tenantryContractStatus: "",
            },
            formLabelWidth: '120px',
            billNum: "",
            billOut: "",
            centerDialogVisible:false,
            findTime: '',
            currentPage: 1,
            pageSize:10,
            total:0,
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
            }
        }
    },
    methods: {
        showPage(){
            $.ajax({
                type: "get",
                url: "../../companyBill/companyGetBillPage",
                data: {"currentPage":main.currentPage, "pageSize":main.pageSize,"starTime":main.findTime[0],"endTime":main.findTime[1]},
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData);
                    main.tableData=resultData.data.page.records;
                    main.total=resultData.data.page.total;
                }
            })
        },
        showCount(){
            $.ajax({
                type: "get",
                url: "../../companyBill/countMoney",
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData);
                    main.billNum=resultData.data.count.dealSum;
                    main.billOut=resultData.data.count.payMoney;
                }
            })
        },
        handleEdit(index, row) {
            console.log(row);
            $.ajax({
                type: "get",
                url: "../../companyBill/getBillById",
                data: {"id":row.pactId,"billType":"租户合约"},
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData);
                    main.form=resultData.data.bill;
                }
            })
            main.centerDialogVisible=true;
        },
        find(){
            console.log(this.findTime);
            main.currentPage=1;
            if(this.findTime===null){
                this.findTime='';
            }
            main.showPage();
        },
        handleSizeChange(val) {
            main.pageSize=val;
            main.showPage();
        },
        handleCurrentChange(val) {
            main.currentPage=val;
            main.showPage();
        }
    }
})
main.showPage();
main.showCount();