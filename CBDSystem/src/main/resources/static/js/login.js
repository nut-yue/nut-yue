let login = new Vue({
    el: ".main",
    data() {
        let surePwd = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.form.password) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };
        let checkEmail = (rule, value, callback) => {
            const mailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
            if (!value) {
                return callback(new Error('邮箱不能为空'))
            }
            setTimeout(() => {
                if (mailReg.test(value)) {
                    callback()
                } else {
                    callback(new Error('请输入正确的邮箱格式'))
                }
            }, 100)
        }
        return {
            centerDialogVisible:false,
            loginForm: {
                username: "",
                password: ""
            },

            rules: {
                // username: [
                //     {required: true, message: '请输入用户名', trigger: 'change'},
                //     {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'change'}
                // ],
                // password: [
                //     {required: true, message: '请输入密码', trigger: 'change'},
                //     {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'change'}
                // ],
                // name: [
                //     {required: true, message: '请输入用户名', trigger: 'change'},
                //     {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'change'}
                // ],
                // surePwd: [
                //     {required: true, message: '请输入密码', trigger: 'change'},
                //     {validator: surePwd, trigger: 'change'}
                // ],
                // sex: [
                //     { required: true, message: '请选择性别', trigger: 'change' }
                // ],
                // profession: [
                //     {required: true, message: '请输入密码', trigger: 'change'},
                //     {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'change'}
                // ],
                // email: [
                //     {required: true, message: '请输入邮箱', trigger: 'change'},
                //     { validator: checkEmail, trigger: 'blur' }
                // ]
            },
            form: {
                adminBean:{
                    adminName: "",
                    adminPassword: "",
                },
                surePwd: "",
                userRealName:"",
                userAddress:"",
                userIdCard:"",
                userProfession: "",
                userEmailAddress: "",
                userPhone: "",
                register: "",
            },
            code: "",//验证码
            serviceCode: "",
        }
    },
    methods: {
        //提交方法
        login() {
            this.$refs.loginForm.validate(valid => {
                if (valid) {
                    $.ajax({
                        type: "get",
                        url: "/admin/getAdmin",
                        data:{
                        "adminName":login.loginForm.username,
                        "adminPassword":login.loginForm.password
                        },
                        dataType: "json",
                        success: function (resultData) {
                            if(resultData.code!=200){
                                login.$message({
                                    message: ''+resultData.message,
                                    type: 'error'
                                });
                                return ;
                            }
                            console.log(resultData.data.login.adminBean.adminRoleType);
                            //用户类型,/ 1是个人用户，2是企业用户，3是管理员用户
                            let type=resultData.data.login.adminBean.adminRoleType;
                            if(type!=null){
                                if(type==1){
                                    window.location.href = "user/userMain.html?id="+resultData.data.login.adminBean.adminId;
                                }else if(type==2){
                                    window.location.href = "company/companyMain.html?id="+resultData.data.login.adminBean.adminId;
                                }else if(type==3){
                                    window.location.href = "administrator/administratorMain.html?id="+resultData.data.login.adminBean.adminId;
                                }
                            }else{
                                    login.$message({
                                        message: '外星人？',
                                        type: 'error'
                                    });
                            }
                        }
                    })
                } else {
                    login.$message({
                        message: '验证未通过',
                        type: 'error'
                    });
                }

            })
        },
        register() {
            login.centerDialogVisible=true;
        },
        //发送短信
        sms() {
            $.ajax({
                type: "post",
                url: "/sms/send",
                data:{
                    mobile: login.form.userPhone
                },
                dataType: "json",
                success: function (resultData) {
                    if(resultData.code == 200) {
                        login.$message({
                            message: '短信发送成功',
                            type: 'success'
                        });
                        login.serviceCode = resultData.data.code;
                        return;
                    }
                }
            })
        },
        submit() {
            this.$refs.form.validate(valid=>{
                console.log(login.serviceCode + login.code +".......");
                if (login.serviceCode == login.code) {
                    if (valid) {
                        $.ajax({
                            type: "post",
                            url: "/user/addUser",
                            data: JSON.stringify(login.form),
                            dataType: "json",
                            contentType : 'application/json;charset=utf-8',
                            success: function (resultData) {
                                if(resultData.data.addUser==true){
                                    login.$message({
                                        message: '注册成功',
                                        type: 'success'
                                    });
                                    window.location.href = "login.html";
                                    return;
                                }
                                login.$message({
                                    message: '注册失败',
                                    type: 'success'
                                });
                            }
                        })
                    } else {
                        register.$message({
                            message: '验证未通过',
                            type: 'error'
                        });
                    }
                } else {
                    login.$message({
                        message: '验证码错误',
                        type: 'error'
                    });
                }
            })
        },
        back() {
            login.centerDialogVisible=false;
        }
    }
})