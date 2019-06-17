// 创建VUE对象
let userInfo = new Vue({
        // 绑定根节点
        el: ".main",
        // 页面数据
        data: function () {
            return {
                labelPosition:'right',
                // 表单数据
                form: {
                    userId:"",
                    adminName: "",
                    adminId:"",
                    userRealName:"",
                    userAddress:"",
                    userIdCard:"",
                    adminPassword:"",
                    userProfession:"",
                    userEmailAddress:"",
                    newPwd:"",
                    rePwd:""

                },
                // 信息修改页面
                updateDialogVisible:false,
                updateInfo:false,
                <!--验证规则-->

            }
        },
        // 方法
        methods: {
            // 初始化
            init(){
                $.ajax({
                    url:'../../user/reputation',
                    type:'get',
                    dataType:'json',
                    success:function (datas) {
                        console.log(datas);
                        userInfo.form=datas.data.user;
                        userInfo.form.adminId = datas.data.user.adminBean.adminId;
                        userInfo.form.adminName = datas.data.user.adminBean.adminName;
                    }
                })
            },
            // 打开信息修改模态框
            info(){
                this.updateDialogVisible=true;
                this.updateInfo=true;
            },
            // 打开密码修改模态框
            password(){
                this.updateDialogVisible=true;
                this.updateInfo=false;
            },
            // 修改信息
            updateInfos(){
                $.ajax({
                    url:'../../user/updateUser',
                    type:'get',
                    data:{
                        "userId":this.form.userId,
                        "userAddress":this.form.userAddress,
                        "userProfession":this.form.userProfession,
                        "userEmailAddress":this.form.userEmailAddress},
                    dataType:'json',
                    success:function (datas) {
                        if(datas.code===200){
                            userInfo.$message.success('修改成功');
                        } else {
                            userInfo.$message.error('修改失败');
                        }
                        userInfo.init();
                        userInfo.updateDialogVisible=false;
                    }
                })
            },
            // 修改密码
            updatePassword(){
                $.ajax({
                    url: '../../admin/getAdmin',
                    type: 'post',
                    data: {
                        _method: 'put',
                        "id": this.form.adminId,
                        "newPwd": this.form.newPwd
                    },
                    dataType: 'json',
                    success: function (datas) {
                        if (datas.code === 200) {
                            userInfo.$message.success('修改成功');
                        } else {
                            userInfo.$message.error('修改失败');
                        }
                        userInfo.init();
                        userInfo.updateDialogVisible = false;
                    }
                });
            },
            back(){
                userInfo.init();
                this.updateDialogVisible=false;
            }
        }
});
// 初始化页面数据
userInfo.init();