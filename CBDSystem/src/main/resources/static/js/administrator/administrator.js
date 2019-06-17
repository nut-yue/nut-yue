let main = new Vue({
    el: ".main",
    data() {
        return {
            pwdForm: {
                pwd:""
            },
            powerForm:{
            },
            addForm:{
                type:[],
                administratorName:"",
                administratorPhone:"",
                adminBean:{
                    adminName:"",
                    adminPassword:"",
                    adminRoleType:2,
                    roleBean:{
                        roleId:"",
                    }
                },
            },
            addPower:{
                type:[],
                administratorId:"",
            },
            formLabelWidth: '120px',
            powerVisible:false,
            pwdVisible:false,
            addVisible:false,
            currentPage: 1,
            pageSize:10,
            total:0,
            tableData: [],
            options: [],
            value: '',
            adminId:0
        }
    },
    methods: {
        showPage(){
            $.ajax({
                type: "get",
                url: "../../administrator/listAdministrator",
                data: {"currentPage":main.currentPage, "pageSize":main.pageSize},
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData);
                    main.tableData=resultData.data.page.records;
                    main.total=resultData.data.page.total;
                }
            })
        },
        showPower(){
            $.ajax({
                type: "get",
                url: "../../role/showAllRole",
                dataType: "json",
                success: function (resultData) {
                    main.options=resultData.data.showAllRole;
                }
            });
        },
        power(index, row) {
            // console.log(row);
            main.adminId=row.adminId;
            main.addPower.administratorId=row.administratorId;
            main.powerVisible=true;
        },
        pwd(index, row) {
            console.log(row);
            main.adminId=row.adminId;
            main.pwdVisible=true;
        },
        delAdmin(index, row) {
            console.log(row);
            $.ajax({
                type: "get",
                url: "/administrator/delAdministrator",
                data: {"id":row.administratorId},
                dataType: "json",
                success: function (resultData) {
                    //main.form=resultData.data.bill;
                    //刷新表格
                    main.showPage();
                    //判断是否删除成功
                    if(resultData.code==200){
                        main.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                        return;
                    }
                    main.$message({
                        message: '添加失败',
                        type: 'success'
                    });
                }
            });
            main.centerDialogVisible=true;
        },
        handleSizeChange(val) {
            main.pageSize=val;
            main.showPage();
        },
        handleCurrentChange(val) {
            main.currentPage=val;
            main.showPage();
        },
        addAdmin(){
            console.log(main.addForm);
            $.ajax({
                type: "post",
                url: "../../administrator/insertAdministrator",
                data: JSON.stringify(main.addForm),
                dataType: "json",
                contentType : 'application/json;charset=utf-8',
                success: function (resultData) {
                    main.$message({
                        message: '添加成功',
                        type: 'success'
                    });
                    main.addVisible=false;
                    main.showPage();
                }
            });
        },
        updatePower(){
            // console.log(main.value);
            console.log(main.addPower);
            $.ajax({
                type: "post",
                url: "/administrator/updateRole",
                data: JSON.stringify(main.addPower),
                dataType: "json",
                contentType : 'application/json;charset=utf-8',
                success: function (resultData) {
                    main.powerVisible = false;
                    if(resultData.code==200){
                        main.$message({
                            message: '修改成功',
                            type: 'success'
                        });
                        return;
                    }
                    main.$message({
                        message: '修改失败',
                        type: 'success'
                    });
                }
            });
        },
        updatePwd(){
            $.ajax({
                type: "put",
                url: "../../admin/getAdmin?id="+main.adminId+"&newPwd="+main.pwdForm.pwd,
                dataType: "json",
                success: function (resultData) {
                    main.$message({
                        message: '修改成功',
                        type: 'success'
                    });
                    main.pwdVisible=false;
                }
            });
        }
    }
})
main.showPage();
main.showPower();