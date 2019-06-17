let main = new Vue({
    el: ".main",
    data() {
        return {
            form: {
                reservationDeal:"2",
                reservationType:"3",
                reputation:"4",
                value5: 3.7,
                appointerUserName:"5",
                carportAddress:"6"
            },
            reservationContent:"",
            carportAddress:"",
            formLabelWidth: '120px',
            centerDialogVisible:false,
            time:"",
            value4: '',
            currentPage: 0,
            pageSize:5,
            total:100,
            tableData: [],
            value7: ''
        }
    },
    methods: {
        init(){
            $.ajax({
                type: "get",
                url: "../../user/listReservation",
                data: { "currentPage": main.currentPage, "pageSize": main.pageSize},
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData);
                    main.tableData = resultData.data.listReservation.records;
                    main.total = resultData.data.listReservation.total;
                }
            })
        },
        handleEdit(index, row) {

            main.centerDialogVisible=true;
            $.ajax({
                type: "get",
                url: "../../user/getReservation",
                data: {"reservationId":row.reservationId},
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData);
                    main.form=resultData.data.getReservation;
                    main.form.value5=resultData.data.getReservation.appointerUserBean.userComplaint;
                    main.form.carportAddress=resultData.data.getReservation.carportBean.carportAddress;
                    main.form.appointerUserName=resultData.data.getReservation.appointerUserBean.userRealName;

                }
            })
        },
        sureReservation(index, row){
            console.log(row.carportBean.carportId);
            $.ajax({
                type: "get",
                url: "../../user/updateReservation",
                data: {"reservationDeal":"同意预约"
                    ,"reservationId":row.reservationId
                    , "carportId":row.carportBean.carportId
                    ,"userId":row.appointerUserBean.userId},
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData);
                    main.init();
                    if (resultData!=0){
                        main.open2();
                    }
                }
            })

        },
        refuseReservation(index, row){
            console.log(row.carportBean.carportId);
            $.ajax({
                type: "get",
                url: "../../user/updateReservation",
                data: {"reservationDeal":"拒绝预约"
                    ,"reservationId":row.reservationId
                    , "carportId":row.carportBean.carportId
                    ,"userId":row.appointerUserBean.userId},
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData);
                    main.init();
                    if (resultData!=0){
                        main.open3();
                    }
                }
            })

        },
        open2() {
            this.$message({
                message: '已同意他的预约',
                type: 'success'
            });
        },
        open3() {
            this.$message({
                message: '已拒绝他的预约',
                type: 'warning'
            });
        },
        find(){
            console.log(this.value4);
            console.log(this.value7);
            main.currentPage=0;
            $.ajax({
                type: "get",
                url: "../../user/listReservation",
                data: {"reservationContent":main.reservationContent, "currentPage": main.currentPage, "pageSize": main.pageSize},
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData);
                    main.tableData = resultData.data.listReservation.records;
                    main.total = resultData.data.listReservation.total;
                }
            })
        },
        handleSizeChange(val) {
            this.pageSize=val;
            console.log(`每页 ${val} 条`);
            main.init();
        },
        handleCurrentChange(val) {
            this.currentPage=val;
            console.log(`当前页: ${val}`);
            main.init();
        }
    }
});
main.init();
