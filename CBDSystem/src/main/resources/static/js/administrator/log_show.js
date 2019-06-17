
let main = new Vue({
    el: ".main",
    data() {
        return {
            currentPage: 1,
            pageSize:10,
            total:100,
            tableData: [],
            logType:'',
            logOperator:'',
            logContent:'',
            options: [{
                value: '前台登陆',
                label: '前台登陆'
            }, {
                value: '前台操作',
                label: '前台操作'
            },{
                value: '后台登陆',
                label: '后台登陆'
            }, {
                value: '后台操作',
                label: '后台操作'
            }]
        }
    },
    methods: {
        initList() {
            $.ajax({
                type: "get",
                url: "../../log/all",
                data: {"currentPage": main.currentPage, "pageSize": main.pageSize},
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData.data);
                    main.tableData = resultData.data.page.records;
                    main.total = resultData.data.page.total;
                }
            })
        },
        find(){
            $.ajax({
                type: "get",
                url: "../../log/all",
                data: {"logType":main.logType,
                    "logOperator":main.logOperator,
                    "logContent":main.logContent,
                    "currentPage": main.currentPage,
                    "pageSize": main.pageSize},
                dataType: "json",
                success: function (resultData) {
                    main.tableData = resultData.data.page.records;
                    main.total = resultData.data.page.total;
                }
            })
        },
        handleSizeChange(val) {
            main.currentPage=1;
            main.pageSize=val;
            if(main.logType!=null||main.logOperator!=null||main.logContent!=null){
                main.find();
            }else{
                main.initList();
            }
        },
        handleCurrentChange(val) {
            main.currentPage=val;
            if(main.logType!=null||main.logOperator!=null||main.logContent!=null){
                main.find();
            }else{
                main.initList();
            }
        }
    }
});
main.initList();


