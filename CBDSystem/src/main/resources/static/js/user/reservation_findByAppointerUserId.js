let main = new Vue({
    el: ".main",
    data() {
        return {
            form: {
                reservationContent:"1",
                reservationDeal:"2",
                reservationType:"3",
                appointedUserName:"4",
                appointerUserName:"4",
                carportAddress:"6",
            },
            formLabelWidth: '120px',
            centerDialogVisible:false,
            time:"",
            value4: '',
            currentPage: 0,
            pageSize:5,
            total:100,
            tableData: [],
            options: [{
                value: '买车位',
                label: '买车位'
            }, {
                value: '租车位',
                label: '租车位'
            }],
            value7: ''
        }
    },
    methods: {
        initList() {
            $.ajax({
                type: "get",
                url: "../../user/listSelfReservation",
                data: { "currentPage": main.currentPage, "pageSize": main.pageSize},
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData);
                    main.tableData = resultData.data.listSelfReservation.records;
                    main.total = resultData.data.listSelfReservation.total;
                    console.log('==============>'+resultData.data.listSelfReservation.total)
                }
            })
        },
        handleEdit(index, row) {
            console.log(row.reservationId);
            main.centerDialogVisible=true;
            $.ajax({
                type: "get",
                url: "../../user/getReservation",
                data: { "reservationId":row.reservationId},
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData);
                    main.form=resultData.data.getReservation;
                    main.form.appointedUserName
                        =resultData.data.getReservation.appointedUserBean.userRealName;
                    main.form.appointerUserName
                        =resultData.data.getReservation.appointerUserBean.userRealName;
                    main.form.carportAddress
                        =resultData.data.getReservation.carportBean.carportAddress;
                }
            })

        },
        find(){
            console.log(this.value4);
            console.log(this.value7);
            main.currentPage=0;
            $.ajax({
                type: "get",
                url: "../../user/listSelfReservation",
                data: { "reservationContent":this.value7,"reservationType":this.value4,"currentPage": main.currentPage, "pageSize": main.pageSize},
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData);
                    main.tableData = resultData.data.listSelfReservation.records;
                    main.total = resultData.data.listSelfReservation.total;
                }
            })

        },
        handleSizeChange(val) {
            this.pageSize=val;
            console.log(`每页 ${val} 条`);
            main.initList();
        },
        handleCurrentChange(val) {
            this.currentPage=val;
            console.log(`当前页: ${val}`);
            main.initList();
        }
    }
});
main.initList();
