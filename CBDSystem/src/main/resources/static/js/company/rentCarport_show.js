//数据的展示
let find = new Vue({
    el: ".main",
    data() {
        return {
            isShow:true,
            form: {
                parkspaceAddress:"",
                parkspaceNumber:"",
                tenantryBean:{
                    tenantryContractNum:"",
                    tenantryStartTime:"",
                    tenantryEndTime:"",
                },
            },
            formLabelWidth: '120px',
            showCarport: [],
            timeValue: '',
            carportAddress: "",
            currentPage: 1,
            pageSize: 10,
            total: 0,
            centerDialogVisible: false
        }
    },
    methods: {
        showPage(){
            $.ajax({
                type: "get",
                url: "../../Parkspace/all1",
                data: {
                    "currentPage":find.currentPage,
                    "pageSize":find.pageSize,
                    "parkspaceStatus":"已租赁",
                    "parkspaceAddress":find.carportAddress
                },
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData);
                    find.showCarport=resultData.data.parkspace.records;
                    find.total=resultData.data.parkspace.total;

                }
            })
        },
        findpage() {
            find.currentPage=1;
             find.showPage();
        },
        showInfo(o) {
            console.log(o);
            this.form.parkspaceAddress = o.parkspaceAddress;
            this.form.parkspaceNumber = o.parkspaceNumber;
            find.centerDialogVisible=true;
            $.ajax({
                type: "get",
                url: "../../Parkspace/findById",
                data: {
                    "parkspaceId":o.parkspaceId},
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData);
                    if (resultData.data.getParkspace!=0){
                         console.log(resultData.data.parkspace.tenantryBean);
                          find.form.tenantryBean=resultData.data.parkspace.tenantryBean;
                    }

                }
            })
        },
        order(o){
            this.isShow=false;
            this.centerDialogVisible=true;

        },
        handleSizeChange(val) {
            find.currentPage=1;
            console.log(`每页 ${val} 条`);
            find.pageSize=val;
            find.showPage(1);


        },
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
            find.currentPage=val;
            find.showPage(val);

        }
    }
});
find.showPage();