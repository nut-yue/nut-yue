let main = new Vue({
    el: ".main",
    data() {
        return {
            form: {},
            showData: {
                tenantryId: "",
                tenantryContractNum: "",
                tenantryDealPrice: "",
                tenantryUserName: "",
                tenantryStartTime: "",
                tenantryEndTime: "",
                tenantryLinkman: "",
                tenantryLinkmanTel: "",
                tenantryContact: "",
                tenantryPhone: "",
                tenantryProfession: "",
                tenantryOriginalId: ""
            },
            updateData: {
                tenantryId: "",
                tenantryContractNum: "",
                tenantryDealPrice: "",
                tenantryUserName: "",
                tenantryStartTime: "",
                tenantryEndTime: "",
                tenantryLinkman: "",
                tenantryLinkmanTel: "",
                tenantryContact: "",
                tenantryPhone: "",
                tenantryProfession: "",
                tenantryOriginalId: ""
            },
            formLabelWidth: '120px',
            billNum: "100",
            billOut: "200000",
            billIn: "52000000",
            billAll: "50000000",
            date: "", //时间
            stateDate: "",//开始时间
            endDate: "", //结束时间
            name: "",  //企业名称
            currentPage: 1, //当前页数
            pageSize: 10, //每页显条数
            total: 0, //总条数
            comps: [], //企业下拉表
            company_Id: "",//选择框的企业的id
            companyName: "",
            companyId: '',
            tableData: [{
                tenantryDealPrice: "",
                tenantryUserName: "",
                tenantryStartTime: "1",
                tenantryEndTime: "",
                tenantryContractNum: "",
                tenantryLinkman: "",
                tenantryLinkmanTel: "",
                tenantryContact: "",
                tenantryPhone: ""
            }],
            centerFromVisible: false,
            dialogFormVisible: false,
            dialogPrimaryVisible: false,
            options: [{
                value: '外部合同',
                label: '外部合同'
            }, {
                value: '租户合同',
                label: '租户合同'
            }],
            cars: [], //车位
            selectCart: [], //选择的车位
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
        }
    },
    methods: {
        init() {
            $.ajax({
                type: "get",
                url: "../../tenantry/showAllTenantry",
                data: {
                    "tenantryUserName": main.name,
                    "currentPage": main.currentPage,
                    "pageSize": main.pageSize,
                    "startTime": main.stateDate,
                    "endTime": main.endDate,
                },
                dataType: "json",
                success: function (resp) {
                    main.tableData = resp.data.tenantryPage.records;
                    main.total = resp.data.tenantryPage.total;
                }
            })
        },

        find() {
            if (main.date != null) {
                main.stateDate = main.date[0];
                main.endDate = main.date[1];
                main.init();
                return;
            }
            main.stateDate = "";
            main.endDate = "";
            //重新发送ajax
            main.init();
        },
        handleSizeChange(val) {
            main.pageSize = val;
            main.init();
        },
        handleCurrentChange(val) {
            if (main.name != "") {
                main.currentPage = 1;
                main.init();
                return;
            }
            main.currentPage = val;
            main.init();
        },
        //查看
        show(index, row) {
            main.showData = {};
            $.ajax({
                type: "get",
                url: "../../tenantry/tenantryInfo",
                data: {
                    "tenantryId": row.tenantryId,
                },
                dataType: "json",
                success: function (resp) {
                    main.showData = resp.data.tenantry;
                    main.updateData.tenantryOriginalId = resp.data.tenantry.tenantryId;
                    console.log(resp.data.tenantry);
                    if (resp.data.tenantry.companyBean != null) {
                        main.companyName = resp.data.tenantry.companyBean.companyName;
                        main.companyId = resp.data.tenantry.companyBean.companyId;
                    }

                }
            })
        },

        //添加
        addTenatry(formName) {
            if (main.company_Id == "") {
                main.$message({
                    message: '请选择企业',
                    type: 'warning'
                });
            }
            if (main.selectCart.toString() == "") {
                main.$message({
                    message: '请选择车位',
                    type: 'warning'
                });
            }
            for (let i = 0; i < main.comps.length; i++) {
                if (main.comps[i].value == main.company_Id) {
                    main.showData.tenantryUserName = main.comps[i].label;
                }
            }

            $.ajax({
                type: "POST",
                url: "../../tenantry/addTenantry",
                data: {
                    "tenantryContractNum": main.showData.tenantryContractNum,
                    "tenantryDealPrice": main.showData.tenantryDealPrice,
                    "tenantryUserName": main.showData.tenantryUserName,
                    "tenantryStartTime": main.showData.tenantryStartTime,
                    "tenantryEndTime": main.showData.tenantryEndTime,
                    "tenantryLinkman": main.showData.tenantryLinkman,
                    "tenantryLinkmanTel": main.showData.tenantryLinkmanTel,
                    "tenantryContact": main.showData.tenantryContact,
                    "tenantryPhone": main.showData.tenantryPhone,
                    "tenantryProfession": main.showData.tenantryProfession,
                    "companyId": main.company_Id,
                    "ids": main.selectCart.toString()
                },
                dataType: "json",
                success: function (resp) {
                    if (resp.code == 200) {
                        main.$message({
                            message: '合作愉快',
                            type: 'success'
                        });
                        main.dialogFormVisible = false
                        main.showData = {};
                        main.init();
                    }
                }
            });

        },

        //查询所有的企业 空闲车位
        listCompanyAndCart() {
            main.showData = {};
            main.company_Id = "";
            main.selectCart = [];

            // //查询所有空闲车位
            // $.ajax({
            //     type: "get",
            //     url: "../../Parkspace/all1",
            //     data: {
            //         "currentPage": 1,
            //         "pageSize": 200,
            //         "parkspaceStatus": "空闲",
            //         "parkspaceAddress": ""
            //     },
            //     dataType: "json",
            //     success: function (resp) {
            //         const cartArr = resp.data.parkspace.records;
            //         let cars = [];
            //         for (let i = 0; i < cartArr.length; i++) {
            //             cars[i] = {value: cartArr[i].parkspaceId, label: cartArr[i].parkspaceAddress};
            //         }
            //         main.cars = cars;
            //     }
            // });
            main.list();
            main.init();
        },
        selectCarts() {
            main.selectCart = [];
            //查询所有空闲车位
            $.ajax({
                type: "get",
                url: "../../Parkspace/all",
                data: {
                    "parkspaceStatus": "空闲",
                    "endDate": main.showData.tenantryEndTime,
                    "tenantryId": ""
                },
                dataType: "json",
                success: function (resp) {
                    const cartArr = resp.data.parkspace;
                    let cars = [];
                    for (let i = 0; i < cartArr.length; i++) {
                        cars[i] = {
                            value: cartArr[i].parkspaceId, label: cartArr[i].parkspaceAddress
                                + cartArr[i].parkspaceNumber
                        };
                    }
                    main.cars = cars;
                }
            });
        },

        all() {
            main.showData = {};
            main.company_Id = "";
            main.selectCart = [];
            //查询所有空闲车位
            $.ajax({
                type: "get",
                url: "../../Parkspace/all",
                data: {
                    "parkspaceStatus": "空闲",
                    "endDate": main.updateData.tenantryEndTime,
                    "tenantryId": main.updateData.tenantryOriginalId
                },
                dataType: "json",
                success: function (resp) {
                    const cartArr = resp.data.parkspace;
                    let cars = [];
                    for (let i = 0; i < cartArr.length; i++) {
                        cars[i] = {
                            value: cartArr[i].parkspaceId, label: cartArr[i].parkspaceAddress
                                + cartArr[i].parkspaceNumber
                        };
                    }
                    main.cars = cars;
                }
            });
        },
        //查询所有企业
        list() {
            $.ajax({
                type: "get",
                url: "../../company/listCompany",
                data: {
                    currentPage: 1,
                    pageSize: 100,
                    name: ""
                },
                dataType: "json",
                success: function (resp) {
                    const companys = resp.data.page.records;
                    let coms = [];
                    for (let i = 0; i < companys.length; i++) {
                        coms[i] = {value: companys[i].companyId, label: companys[i].companyName}
                    }
                    main.comps = coms;
                }
            });
        },

        //续约
        extension() {
            for (let i = 0; i < main.comps.length; i++) {
                if (main.comps[i].value == main.company_Id) {
                    main.updateData.tenantryUserName = main.comps[i].label;
                }
            }
            console.log(main.comps);
            console.log(main.updateData.tenantryUserName);
            $.ajax({
                type: "post",
                url: "../../tenantry/renewal",
                data: {
                    "tenantryContractNum": main.updateData.tenantryContractNum,
                    "tenantryDealPrice": main.updateData.tenantryDealPrice,
                    "tenantryUserName": main.companyName,
                    "tenantryStartTime": main.updateData.tenantryStartTime,
                    "tenantryEndTime": main.updateData.tenantryEndTime,
                    "tenantryLinkman": main.updateData.tenantryLinkman,
                    "tenantryLinkmanTel": main.updateData.tenantryLinkmanTel,
                    "tenantryContact": main.updateData.tenantryContact,
                    "tenantryPhone": main.updateData.tenantryPhone,
                    "tenantryProfession": main.updateData.tenantryProfession,
                    "tenantryOriginalId": main.updateData.tenantryOriginalId, //员合同id；
                    "companyId": main.companyId,
                    "ids": main.selectCart.toString()
                },
                dataType: "json",
                success: function (resp) {
                    if (resp.code == 200) {
                        main.$message({
                            message: '合作愉快',
                            type: 'success'
                        });
                        main.updateData = {};
                        main.init();
                    }
                }
            });
        },

        //解约
        delet(tenantryId) {
            this.$confirm('此操作将永久解约, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                $.ajax({
                    type: "post",
                    url: "../../tenantry/break",
                    data: {
                        _method: 'put',
                        "tenantryId": tenantryId,
                    },
                    dataType: "json",
                    success: function (resp) {
                        main.init();
                    }
                });
                main.$message({
                    type: 'success',
                    message: '解约成功!'
                });
            }).catch(function () {
                main.$message({
                    type: 'info',
                    message: '已取消解约'
                });
            });
        },


    }
});
main.init();