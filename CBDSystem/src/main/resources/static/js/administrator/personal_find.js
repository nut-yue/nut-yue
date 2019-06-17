let main = new Vue({
    el: '#rentCarport',
    data() {
        return {
            tableData: [],
            form: {
                personalContractNum: '',
                personalDate: '',
                personalPrice: '',
                carportBean: {
                    carportAddress: '',
                },
                sellUserBean: {
                    userRealName: ''
                },
                buyUserUserBean: {
                    userRealName: ''
                }
            },
            input: '',
            dialogVisible: false,
            dialogFormVisible1: false,
            formLabelWidth1: "180px",
            currentPage: 1,
            pageSize: 10,
            total: 100
        }
    },
    methods: {
        initList() {
            $.ajax({
                type: "get",
                url: "../../personal/listAllPersonal",
                data: {"currentPage": main.currentPage, "pageSize": main.pageSize},
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData)
                        main.tableData = resultData.data.page.records;
                        main.total = resultData.data.page.total;
                }
            })

        },
        handleSizeChange(val) {
            main.pageSize = val;
            main.initList();
        },
        handleCurrentChange(val) {
            main.currentPage = val;
            main.initList();
        },
        findDetail(index, row) {
            main.dialogVisible = true;
            $.ajax({
                type: "get",
                url: "../../personal/getCBDPersonal",
                data: {"personalId": row.personalId},
                dataType: "json",
                success: function (resultData) {
                    if(resultData.code==200){
                        main.tableData = resultData.data.bean.records;
                        main.total = resultData.data.bean.total;
                        main.form = resultData.data.bean;
                        main.initList();
                    }else{
                        main.initList();
                        main.$message.error('获取数据错误')
                    }
                }
            })
        }, back() {
            main.dialogVisible = false;
        }
    }
});
main.initList();