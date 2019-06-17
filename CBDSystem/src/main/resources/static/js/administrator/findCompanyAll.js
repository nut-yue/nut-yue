<!--周陆成-->
let findComp = new Vue({
    el: '#finCompany',
    data() {
        return {
            tableData: [],
            //模态框信息
            dialogFormVisible: false,
            dialogFormVisible1: false,

            value: "",
            form: {
                compname: "",
                Position: "",
                Contact: "",
                Phone: "",
                adminBean: {
                    adminname: "",
                    adminPwd: "",
                }
            },
            comName: '',
            comPosition: '',
            comContact: '',
            comPhone: '',
            formLabelWidth1: '120px',
            formInline: {
                user: ''
            },
            //分页 每次翻多少页
            currentPage: 1,
            pageSize: 10,
            total: 100
        }
    },
    methods: {
        initList() {
            $.ajax({
                type: "get",
                url: "../../company/listCompany",
                data: {
                    "currentPage": findComp.currentPage,
                    "pageSize": findComp.pageSize,
                    "messageIsRead": findComp.messageType
                },
                dataType: "json",
                success: function (resultData) {
                    if (resultData.code == 200) {
                        findComp.tableData = resultData.data.page.records;
                        findComp.total = resultData.data.page.total;
                    } else {
                        findComp.$message.error('获取数据失败');
                    }
                    console.log(resultData);
                    console.log(resultData.data.page.total);
                }
            })
        },
        handleEdit(index, row) {
            findComp.dialogFormVisible1 = true;
            console.log(row.companyId);
            $.ajax({
                type: "get",
                url: '../../company/findById',
                data: {"id": row.companyId},
                dataType: "json",
                success: function (resultData) {
                    if (resultData.code == 200) {
                        findComp.form = resultData.data.company;
                    } else {
                        findComp.initList();
                        findComp.$message.error('获取数据失败');
                    }
                    console.log(resultData);
                }
            })
        },
        handleDelete(index, row) {
            this.$alert('是否永久删除该消息?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                center: true

            }).then(() => {
                this.currentPage = 1,
                    // console.log(index, row);
                    $.ajax({
                        type: "get",
                        url: '../../company/removeCompany',
                        data: {"id": row.adminId},
                        dataType: "json",
                        success: function (resultData) {
                            if (resultData.code == 404) {
                                findComp.$message.error('删除失败，请查看该企业合同是否全部失效！');

                            }
                            findComp.initList();

                        }
                    })
                // this.$message({
                //     type: 'success',
                //     message: '删除成功!'
                // });
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });

        },
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
            findComp.pageSize = val;
            if (findComp.comName != null || findComp.comPosition != null || findComp.comContact != null || findComp.comPhone != null) {
                findComp.currentPage = 1;
                findComp.find();
            } else {
                findComp.initList();
            }
        },
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
            findComp.currentPage = val;
            if (findComp.comName != null || findComp.comPosition != null || findComp.comContact != null || findComp.comPhone != null) {
                findComp.find();
            } else {
                findComp.initList();
            }
        },
        onSubmit() {
            console.log('submit!');
        },
        find() {

            $.ajax({
                type: "get",
                url: '../../company/listCompanys',
                data: {
                    "companyName": findComp.comName,
                    "companyPosition": findComp.comPosition,
                    "companyContact": findComp.comContact,
                    "companyPhone": findComp.comPhone,
                    "currentPage": findComp.currentPage,
                    "pageSize": findComp.pageSize,
                },
                dataType: "json",
                success: function (resultData) {
                    findComp.tableData = resultData.data.page.records;
                    findComp.total = resultData.data.page.total;
                }
            })
        },
        submitForm() {
            $.ajax({
                type: "get",
                url: '../../company/saveAdministrator',
                data: {
                    "username": this.form.adminBean.adminname,
                    "pwd": this.form.adminBean.adminPwd,
                    "companyName": this.form.compname,
                    "companyPosition": this.form.Position,
                    "companyContact": this.form.Contact,
                    "companyPhone": this.form.Phone
                },
                dataType: "json",
                success: function (resultData) {
                    findComp.dialogFormVisible = false;
                    findComp.form.compname = "";
                    findComp.form.Position = "";
                    findComp.form.Contact = "";
                    findComp.form.Phone = "";
                    findComp.form.adminBean.adminname = "";
                    findComp.form.adminBean.adminPwd = "";
                    findComp.initList();

                    console.log(resultData);

                },
                error: function () {
                    alert("提交失败！");
                }
            })
        }
    }
});
findComp.initList();
