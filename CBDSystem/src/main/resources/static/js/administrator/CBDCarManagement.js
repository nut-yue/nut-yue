<!--周陆成-->
let add = new Vue({
    el: '#findCompany',
    data() {
        return {
            url: "/upload/companyImg",
            fileList2: [],
            form: {
                time: "",
                parkspaceAddress: "",
                parkspaceRegionNumber: "",
                parkspaceNumber: "",
                parkspaceStatus: ""

            },
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
            formLabelWidth: '120px',
            //主页面
            showCarport: [],

            //弹窗
            // centerDialogVisible:false,
            dialogTableVisible: false,
            //分页
            currentPage: 1,
            timeValue: '',
            carportAddress: "",
            pageSize: 10,
            total: 100,
            centerDialogVisible: false,
            //计算数量
            num1: 1
        }
    },
    methods: {
        showInfo(o) {
            console.log(o);
            $.ajax({
                type: "get",
                url: "../../Parkspace/findById",
                data:  {"parkspaceId": o.parkspaceId,},
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData);
                    add.form=resultData.data.parkspace;

                }
            })

            this.centerDialogVisible = true;
        },
        showAll() {
            $.ajax({
                type: "get",
                url: "../../Parkspace/all1",
                data: {"currentPage": add.currentPage, "pageSize": add.pageSize,},
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData);
                    add.showCarport = resultData.data.parkspace.records;
                    add.total = resultData.data.parkspace.total;

                }
            })
        },


        addCBD() {
            console.log();
            this.dialogTableVisible = true;
        },
        handleEdit(index, row) {
            console.log(index, row);
        },
        handleDelete(index, row) {
            console.log(index, row);
        },

        //主分页
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
            add.pageSize=val;
            add.showAll();
        },
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
            add.currentPage=val;
            add.showAll();
        },
        handleChange(value) {
            console.log(value);
        },
        //添加
        handleAdd(value) {
            console.log(value);
            this.$refs({
                title: '成功',
                message: '添加成功',
                type: 'success'
            });
        },

        handleRemove(file, fileList2) {

        },
        handlePreview(file) {
        },
        addCarport() {
            console.log(add.carportForm)
            this.$refs.uploadFile.submit();
            // main.carportForm = {};
        },
        onSuccessDoc(response, file, fileList2) {
            console.log(add.carportForm)
            add.carportForm.imageAddress = response.data.path
            // let carport= Object.assign({}, main.carportForm);
            // main.stallBeans=[...main.stallBeans,carport];
            $.ajax({
                type: "post",
                url: "../../Parkspace/saveParkspaces",
                data: add.carportForm,
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData);
                    add.showAll();

                }
            })
            this.$refs.uploadFile.clearFiles();
        },
        handleClose(tag){
            // this.stallBeans.splice(this.stallBeans.indexOf(tag), 1);
            // console.log(main.stallBeans);
        }
    }

})
add.showAll();