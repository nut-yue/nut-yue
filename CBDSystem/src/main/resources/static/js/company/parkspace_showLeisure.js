//数据的展示
let main = new Vue({
    el: ".main",
    data() {
        return {
            isShow:true,
            form: {
            },
            formLabelWidth: '120px',
            showCarport: [],
            timeValue: '',
            carportAddress: "",
            currentPage: 1,
            pageSize: 10,
            total: 10,
            centerDialogVisible: false
        }
    },
    methods: {
        showPage(){
            $.ajax({
                type: "get",
                url: "../../Parkspace/all1",
                data: {
                        "currentPage":main.currentPage,
                        "pageSize":main.pageSize,
                        "parkspaceStatus":"空闲",
                        "parkspaceAddress":main.carportAddress},
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData);
                    main.showCarport=resultData.data.parkspace.records;
                    main.total=resultData.data.parkspace.total;
                }
            })
        },
        find() {
            main.showPage(); //点击
        },
        showInfo(o) {
            console.log(o);
            main.centerDialogVisible=true;
            main.form=o;
        },
        order(o){
            console.log(o);
            this.isShow=false;
            this.centerDialogVisible=true;

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
});
main.showPage();