let main = new Vue({
    el: ".main",
    data() {
        return {
            form: {
                personalId:"",
                personalContractNum: "",
                personalPrice:"",
                personalDate:"",
                personalBuyIsSigning:"",
                personalSellIsSigning:"",
                sellUserId:"",
                buyUserId:"",
                carportId:"",
                carportAddress:"",
                billId:"",
                sellUserName:"",
                buyUserName:""
                },
            options: [{
                value: '1',
                label: '买方合同'
            }, {
                value: '0',
                label: '买方合同'
            }, {
                value: '-1',
                label: '所有合同'
            }],
            formLabelWidth: '120px',
            billNum: "100",
            billOut: "200000",
            billIn: "52000000",
            billAll: "50000000",
            centerDialogVisible:false,
            time:"",
            value4: '',
            currentPage: 1,
            pageSize:10,
            total:100,
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
            },
            value7: '',
            value4: '',
            isShow:true,
            userId:0,//用来接收传过来的当前登录用户id
        }
    },
    methods: {
        init(){
            //初始化表格
            $.ajax({
                type: "get",
                url: "/personal/listPersonal",
                data: {
                    "currentPage": main.currentPage,
                    "pageSize": main.pageSize,
                },
                dataType: "json",
                success: function (resultData) {
                     // console.log(resultData.data.page.total);
                     main.tableData = resultData.data.page.records;
                    main.total = resultData.data.page.total;
                }
            });
        },
        handleEdit(index, row) {
            //查看详细信息
            console.log(row);
            $.ajax({
                type: "get",
                url: "/personal/getPersonal",
                data: {
                    "personalId": row.personalId,
                },
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData.data);
                    main.form.personalId=resultData.data.bean.personalId;
                    main.form.personalContractNum=resultData.data.bean.personalContractNum;
                    main.form.personalPrice=resultData.data.bean.personalPrice;
                    main.form.personalDate=resultData.data.bean.personalDate;
                    main.form.personalBuyIsSigning=resultData.data.bean.personalBuyIsSigning;
                    main.form.personalSellIsSigning=resultData.data.bean.personalSellIsSigning;
                    main.form.sellUserId=resultData.data.bean.sellUserBean.userId;
                    main.form.buyUserId=resultData.data.bean.buyUserUserBean.userId;
                    main.form.buyUserName=resultData.data.bean.buyUserUserBean.userRealName;
                    main.form.sellUserName=resultData.data.bean.sellUserBean.userRealName;
                    main.form.carportId=resultData.data.bean.carportBean.carportId;
                    main.form.billId=row.billId;
                    main.form.carportAddress=resultData.data.bean.carportBean.carportAddress;

                    //当前用户id
                    main.userId=resultData.data.userId;
                    //购买方id和签约状态
                    let buy=main.form.buyUserId;
                    let buyStatus=main.form.personalBuyIsSigning;
                    //出售方id和签约状态
                    let sell=main.form.sellUserId;
                    let sellStatus=main.form.personalSellIsSigning;
                    //判断当前用户是否购买方法
                    if(main.userId==buy){
                          //判断当前用户是否签约
                            if(buyStatus=='签约'){
                                //如果已签约，不显示签约按钮
                                main.isShow=false;
                            }
                    }else{
                        //判断当前用户是否签约
                        if(sellStatus=='签约'){
                            //如果已签约，不显示签约按钮
                            main.isShow=false;
                        }
                    }
                }
            });
            main.centerDialogVisible=true;
        },
        addTrue(){
            //同意
            console.log(main.form);
            let buy=main.form.personalBuyIsSigning;
            let sell=main.form.personalSellIsSigning;
            //判断登陆者是购买方
            if(main.userId==main.form.buyUserId){
                main.form.personalBuyIsSigning="签约";
                console.log("买方");
            }
            //判断登陆者是出售方
            if(main.userId==main.form.sellUserId){
                main.form.personalSellIsSigning="签约";
                console.log("卖方");
            }
            console.log(main.form);
            $.ajax({
                type: "get",
                url: "/personal/updateSelfPersonalSigning",
                data: main.form,
                //     {
                //
                //     personalContractNum:main.form.personalContractNum, /**合同编号*/
                //     personalPrice:main.form.personalPrice,/**成交价格*/
                //     personalDate:main.form.personalDate,/**生效日期*/
                //     personalBuyIsSigning:but,/**买方是否签约 0 未签约、 1签约*/
                //     personalSellIsSigning:sell,/**卖方是否签约 0 未签约、 1 签约*/
                //     sellUserId:main.form.sellUserBean.userId,/**车位出售方ID*/
                //     buyUserId:main.form.buyUserUserBean.userId,/**车位购买方ID*/
                //     carportId:main.form.carportBean.carportId,/**车位ID*/
                // },
                dataType: "json",
                success: function (resultData) {
                     console.log(resultData+"============="+1);
                }
            });
        },
        addFalse(){
            //拒绝
            console.log(main.userId);
            let buy=main.form.personalBuyIsSigning;
            let sell=main.form.personalSellIsSigning;
            //判断登陆者是购买方
            if(main.userId==main.form.buyUserId){
                main.form.personalBuyIsSigning="未签约";
            }
            //判断登陆者是出售方
            if(main.userId==main.form.sellUserId){
                main.form.personalSellIsSigning="未签约";
            }
            $.ajax({
                type: "get",
                url: "/personal/updatePersonalSigning",
                data: main.form,
                //     {
                //
                //     personalContractNum:main.form.personalContractNum, /**合同编号*/
                //     personalPrice:main.form.personalPrice,/**成交价格*/
                //     personalDate:main.form.personalDate,/**生效日期*/
                //     personalBuyIsSigning:but,/**买方是否签约 0 未签约、 1签约*/
                //     personalSellIsSigning:sell,/**卖方是否签约 0 未签约、 1 签约*/
                //     sellUserId:main.form.sellUserBean.userId,/**车位出售方ID*/
                //     buyUserId:main.form.buyUserUserBean.userId,/**车位购买方ID*/
                //     carportId:main.form.carportBean.carportId,/**车位ID*/
                // },
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData.data);
                }
            });
        },
        find(){
            //获取下拉选框的值
            let selected = main.value4;
            let buy = 0; //购买方
            let sell=0; //出售方
            //1是购买方合同，0是出售方合同，-1是所有合同
            if(selected==1){
                buy=1;
            }else if(selected==0){
                sell=1;
            }
            //条件查询
            $.ajax({
                type: "get",
                url: "/personal/listPersonalByCondition",
                data: {
                    "buyUserId": buy,
                    "sellUserId":sell,
                    "currentPage": main.currentPage,
                    "pageSize":main.pageSize,
                },
                dataType: "json",
                success: function (resultData) {
                    // console.log(resultData);
                    main.tableData = resultData.data.page.records;
                    main.total = resultData.data.page.total;
                }
            });
        },
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
            //获取下拉选框的值
            let selected = main.value4;
            let buy = 0; //购买方
            let sell=0; //出售方
            //1是出售方合同，0是购买方合同，-1是所有合同
            if(selected==1){
                buy=1;
            }else if(selected==0){
                sell=1;
            }
            $.ajax({
                type: "get",
                url: "/personal/listPersonalByCondition",
                data: {
                    "buyUserId": buy,
                    "sellUserId":sell,
                    "currentPage": val,
                    "pageSize": main.pageSize,
                },
                dataType: "json",
                success: function (resultData) {
                    // console.log(resultData.data.page.total);
                    main.tableData = resultData.data.page.records;
                    main.total = resultData.data.page.total;
                }
            });
        },
        handleCurrentChange(val) {
            // console.log(`当前页: ${val}`);
            //获取下拉选框的值
            let selected = main.value4;
            let buy = 0; //购买方
            let sell=0; //出售方
            //1是购买方合同，0是出售方合同，-1是所有合同
            if(selected==1){
                buy=1;
            }else if(selected==0){
                sell=1;
            }
            $.ajax({
                type: "get",
                url: "/personal/listPersonalByCondition",
                data: {
                    "buyUserId": buy,
                    "sellUserId":sell,
                    "currentPage": val,
                    "pageSize": main.pageSize,
                },
                dataType: "json",
                success: function (resultData) {
                    // console.log(resultData.data.page.total);
                    main.tableData = resultData.data.page.records;
                    main.total = resultData.data.page.total;
                }
            });

        }
    }
});
main.init();