
// 创建VUE对象
let company=new Vue({
        // 绑定根节点
        el:'#companyInfo',
        // 页面数据
        data() {
            return {
                labelPosition: 'right',
                // 表单数据
                form: {
                    companyId:'',
                    companyName: '',
                    companyPosition: '',
                    administratorName: '',
                    companyContact:"",
                    companyPhone:"",
                    adminId:"",
                    oldPwd:"",
                    newPwd:"",
                    rePwd:"",
                    adminPassword: '',
                    adminPasswords: ''
                },
                // 信息修改模态框
                updateDialogVisible:false,
                updateInfo:false
            }
        },
        // 方法
        methods:{
        // 初始化
        init(){
            $.ajax({
                url:'../../company/findById',
                type:'get',
                dataType:'json',
                success:function (datas) {
                    if(datas.code===200){
                        console.log(datas.data.company);
                        company.form=datas.data.company;
                        company.form.adminId=datas.data.company.adminBean.adminId;
                    } else {
                        company.$message.error('获取数据失败');
                    }

                }
            })
        },
        // 打开信息修改模态框
        info(){
            this.updateDialogVisible=true;
            this.updateInfo=true;
        },
        // 打开修改密码模态框
        password(){
            this.updateDialogVisible=true;
            this.updateInfo=false;
        },
        // 修改信息
        updateInfos(){
            $.ajax({
                type:'post',
                url:'../../company/updateCompany',
                data:{_method:'put',
                    "companyContact":this.form.companyContact,
                    "companyPhone":this.form.companyPhone,
                    "companyId":this.form.companyId},
                dataType:'json',
                success:function (datas) {
                    if(datas.code===200){
                        company.$message.success('修改成功');
                    } else {
                        company.$message.error('修改失败');
                    }
                    company.init();
                    company.updateDialogVisible=false;
                }
            })
        },
        // 修改密码
        updatePassword(){
            $.ajax({
                url:'../../admin/getAdmin',
                type:'post',
                data:{_method:'put',
                    "id":this.form.adminId,
                    "newPwd":this.form.newPwd},
                dataType:'json',
                success:function (datas) {
                    if(datas.code===200){
                        company.$message.success('修改成功');
                    } else {
                        company.$message.error('修改失败');
                    }
                    company.init();
                    company.updateDialogVisible=false;
                }
            })
        },
        back(){
            company.init();
            this.updateDialogVisible=false;
        }
    }
});
// 初始化页面
company.init();