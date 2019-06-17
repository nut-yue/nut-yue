let main = new Vue({
    el: ".main",
    data() {
        return {
            form: {
                carportId:'',
                carportAddress: "",
                carportNumber: "",
                carportProperty: "",
                carportPropertyDoc: "",
                carportStatus: "",
                userBean:{
                    userRealName: "",
                    userAddress: "",
                    userIdCard: "",
                    userPhone: ""
                }
            },
            centerDialogVisible:false,
            formLabelWidth: '120px',
            currentPage: 1,
            pageSize:10,
            total:100,
            tableData: []
        }
    },
    methods: {
        initList() {
            $.ajax({
                type: "get",
                url: "../../carport/listCarport",
                data: {"userId":0,"currentPage": main.currentPage, "pageSize": main.pageSize,"state":"审核中"},
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData.data);
                    main.tableData = resultData.data.carports.records;
                    main.total = resultData.data.carports.total;
                }
            })
        },
        handleEdit(index, row) {
            console.log(row)
            main.centerDialogVisible=true;
            $.ajax({
                type: "get",
                url: "../../carport/getCarport",
                data: {"carportId":row.carportId},
                dataType: "json",
                success: function (resultData) {
                    main.form = resultData.data.carport;
                    carportId=resultData.data.carport.carportId;
                    carportStatus=resultData.data.carport.carportStatus;
                }
            })
        },
        handleSizeChange(val) {
            main.pageSize=val;
            main.initList();
        },
        handleCurrentChange(val) {
            main.currentPage=val;
            main.initList();
        },
        agree(){
            $.ajax({
                type: "post",
                url: "/carport/check",
                data: {
                    _method:"put",
                        "carportId":carportId,
                        "carportStatus":carportStatus,
            },
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData)
                    if(resultData.code!="200"){
                        alert("同意失败");
                    }else {
                        main.initList();
                    }
                }
            });
            main.centerDialogVisible=false;
        },
        refuse(){
            $.ajax({
                type: "post",
                url: "../../carport/deleteCarport",
                data: {_method:"delete","carportId":carportId},
                dataType: "json",
                success: function (resultData) {
                    if(resultData.message==="ok"){
                        main.initList();
                    }else {
                        alert("拒绝失败");
                    }
                }
            });
            main.centerDialogVisible=false;
        }
    }
});
var carportId = 0;
var carportStatus='';

main.initList();