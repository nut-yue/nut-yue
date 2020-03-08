let app = new Vue({
    el: '#app', // 绑定根元素
    data() {
        return {
            currentPage4: 1,
            input: '',
            form: { // 搜索条件
                goodsName: '',
                price: '',
                stock: '',
                currentPage: 1,
                pageSize: 8
            },
            medicineBean: {
                goodsName: '',
                price: '',
                stock: '',
                info: ''
            },
            dialogFormVisible: false,
            dialogType: '',
            tableData:[]
        }
    },
    methods: {
        // 页面初始化请求列表数据
        render(params) {
            let responseData;
            $.ajax({
                type: params.type,
                url: params.url,
                async: false,
                dataType:'json',
                beforeSend: function (xmlHttpRequest) {
                    xmlHttpRequest.setRequestHeader('Authorization','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1ODMyNDM0NzgsInVzZXJuYW1lIjoibnV0LXl1ZSJ9.GRQ3dm_UZ-YbNAjKzBPTdxbuwv0BbBrp_iX1EmwJWyo');
                },
                headers:{'Content-Type':'application/json;charset=utf8','Authorization':'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1ODMyNDM0NzgsInVzZXJuYW1lIjoibnV0LXl1ZSJ9.GRQ3dm_UZ-YbNAjKzBPTdxbuwv0BbBrp_iX1EmwJWyo'},
                data: params.data,
                success: function (res) {
                    responseData = res;
                }
            })
            return responseData;
        },
        handleShow(index, row) {
            this.dialogFormVisible = true
            this.dialogType = 'read';
            this.medicineBean = row;
            $("#infoDialog").find('input[type=text],select,input[type=hidden]').each(function () {
                $(this).attr("readonly", "readonly");
            })
        },
        handleEdit(index, row) {   // 编辑
            this.dialogFormVisible = true
            this.dialogType = 'update';
            this.medicineBean = row;
            $("#infoDialog").find('input[type=text],select,input[type=hidden]').each(function () {
                $(this).removeAttr("readonly");
            })
        },
        update(formName) {
            let params = {
                url: '/medicine/updateMedicine',
                data: this.medicineBean,
                type: 'put'
            }
            let res = this.render(params);
            if (res.code === 200) {
                this.$message.success("修改成功");
            } else {
                this.$message.error("修改失败");
            }
            this.dialogFormVisible = false;
            init();
        },
        handleDelete(index, row) {  // 删除
            console.log(index, row);
            let ids = row.goodsId;
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                let params = {
                    url: '/goods/removeBatch',
                    data: {_method: "delete", ids: ids},
                    type: 'delete'
                }
                res = this.render(params);
                console.log(res);
                if (res.code === 200) {
                    this.$message.success("删除成功");
                    init();
                } else {
                    this.$message.error("删除失败");
                }
            }).catch(() => {
                    this.$message.info('已取消删除')
            })
        },
        handleSizeChange(val) { // 分页选择显示条数大小改变触发
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) { // 页码改变时触发
            console.log(`当前页: ${val}`);
        },
        submit() { // 提交表单
            let params = {
                url: '/goods/list',
                data: this.form,
                type: 'get'
            }
            let res = this.render(params)
            app.tableData = res.data.list;
        },
        dialogShow(dialogType) {
            this.dialogFormVisible = true
            this.dialogType = dialogType;
            if (this.medicineBean.medicineName != null) {
                $("#infoDialog").find('input[type=text],select,input[type=hidden]').each(function () {
                    $(this).val('');
                })
                $("#infoDialog").find('input[type=text],select,input[type=hidden]').each(function () {
                    $(this).removeAttr("readonly");
                })
            }
        },
        add(formName) { // 提交新增请求
            let params = {
                url: '/medicine/add',
                data: this.medicineBean,
                type: 'post'
            }
            console.log(this.medicineBean.validity)
            let res = this.render(params);
            console.log(res)
            if (res.code === 200) {
                this.$message.success("添加成功");
            } else {
                this.$message.error("添加失败");
            }
            this.dialogFormVisible = false;
            init();
        },
        inclusion() { // 提交导入请求

        },
        output() { // 提交导出请求
            window.open("/goods/export");
        }
    }
});

function init() {
    let params = {
        url: '/goods/list',
        data: {'pageSize': 8, 'currentPage': 1},
        type: 'get',
    }
    let res = app.render(params);
    app.tableData = res.data.list;
}

init();
