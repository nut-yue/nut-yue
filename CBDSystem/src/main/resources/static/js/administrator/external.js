let main = new Vue({
    el: ".main",
    data() {
        return {
            url: "/upload/companyImg",
            fileList2: [],
            addCarportVisible: false,
            form: {},
            showData: {
                externalId: "",
                externalNo: "",
                externalUnit: "",
                externaLinkman: "",
                externaLinkmanTel: "",
                externalContact: "",
                externalPhone: "",
                externalAddress: "",
                externalffectivedate: "",
                externalDeadline: "",
                externalPrice: "",
                externalCopy: ""
            },
            stallBeans: [],
            carportForm: {
                //地址
                address: "",
                //区域名
                areaName: "",
                //起始编号
                startNumber: "",
                //结束编号
                endNumber: "",
                //图片地址
                imageAddress: "",
            },
            updateData: {
                //修改数据
                externalNo: "",
                externalUnit: "",
                externaLinkman: "",
                externaLinkmanTel: "",
                externalContact: "",
                externalPhone: "",
                externalAddress: "",
                externalffectivedate: "",
                externalDeadline: "",
                externalPrice: "",
                externalCopy: "",
                originalBean: {
                    externalId: ""
                }
            },
            formLabelWidth: '120px',
            date: "", //时间
            stateDate: "",//开始时间
            endDate: "", //结束时间
            status: "",  //状态
            currentPage: 1, //当前页数
            pageSize: 10, //每页显条数
            total: 0,
            tableData: [{}],
            options: [{
                value: '已生效',
                label: '已生效'
            }, {
                value: '未生效',
                label: '未生效'
            }, {
                value: '已过期',
                label: '已过期'
            }, {
                value: '失效',
                label: '失效'
            }],
            centerFromVisible: false,
            dialogFormVisible: false,
            dialogPrimaryVisible: false,
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
                url: "../../external/list",
                data: {
                    "externalContractStatus": main.status,
                    "currentPage": main.currentPage,
                    "pageSize": main.pageSize,
                    "startDate": main.stateDate,
                    "endStartDate": main.endDate,
                },
                dataType: "json",
                success: function (resp) {
                    main.tableData = resp.data.page.records;
                    main.total = resp.data.page.total;
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
            if (main.status != "") {
                main.currentPage = 1;
                main.init();
                return;
            }
            main.currentPage = val;
            main.init();
        },

        handleRemove(file, fileList2) {

        },
        handlePreview(file) {
        },
        //查看
        show(index, row) {
            console.log(row);
            $.ajax({
                type: "get",
                url: "../../external/getOne",
                data: {
                    "id": row.externalId,
                },
                dataType: "json",
                success: function (resp) {
                    main.showData = resp.data.external;
                    main.updateData.originalBean.externalId = resp.data.external.externalId;
                }
            });
        },
        //续约
        update(formName) {

            $.ajax({
                type: "post",
                url: "../../external/contract",
                data: JSON.stringify(main.updateData),
                dataType: "json",
                contentType: 'application/json;charset=utf-8',
                success: function (resp) {
                    main.$message({
                        message: '解约成功',
                        type: 'success'
                    });
                    main.updateData = {
                        externalNo: "",
                        externalUnit: "",
                        externaLinkman: "",
                        externaLinkmanTel: "",
                        externalContact: "",
                        externalPhone: "",
                        externalAddress: "",
                        externalffectivedate: "",
                        externalDeadline: "",
                        externalPrice: "",
                        externalCopy: "",
                        originalBean: {
                            externalId: ""
                        }
                    };
                    main.init();
                }
            });
        },
        //添加
        add(formName) {
            console.log(main.showData);
            $.ajax({
                type: "post",
                url: "../../external/saveExternalAndParkspaces",
                data: JSON.stringify({
                    "externalId": main.showData.externalId,
                    "externalNo": main.showData.externalNo,
                    "externalUnit": main.showData.externalUnit,
                    "externaLinkman": main.showData.externaLinkman,
                    "externaLinkmanTel": main.showData.externaLinkmanTel,
                    "externalContact": main.showData.externalContact,
                    "externalPhone": main.showData.externalPhone,
                    "externalAddress": main.showData.externalAddress,
                    "externalffectivedate": main.showData.externalffectivedate,
                    "externalDeadline": main.showData.externalDeadline,
                    "externalPrice": main.showData.externalPrice,
                    "externalCopy": main.showData.externalCopy,
                    "stallBeans": main.stallBeans
                }),
                dataType: "json",
                contentType: 'application/json;charset=utf-8',
                success: function (resp) {
                    main.$message({
                        message: '添加成功',
                        type: 'success'
                    });

                    main.init();
                }
            });
        },
        showAdd() {
            main.showData = {};
        },
        //解约
        delet(index, row) {
            $.ajax({
                type: "post",
                url: "../../external/cancel",
                data: JSON.stringify(row),
                dataType: "json",
                contentType: 'application/json;charset=utf-8',
                success: function (resp) {
                    main.$message({
                        message: '解约成功',
                        type: 'success'
                    });
                    main.init();
                }
            });
        },
        addCarport(formName) {
            main.$refs.uploadFile.submit();
            main.form = {};
            main.addCarportVisible = false
        },
        onSuccessDoc(response, file, fileList2) {
            main.carportForm.imageAddress = response.data.path
            let carport = Object.assign({}, main.carportForm);
            main.stallBeans = [...main.stallBeans, carport];
            this.$refs.uploadFile.clearFiles();
        },
        handleClose(tag) {
            this.stallBeans.splice(this.stallBeans.indexOf(tag), 1);
            console.log(main.stallBeans);
        }
    }
});
main.init();