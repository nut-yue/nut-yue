
// 创建VUE对象
let admin=new Vue({
    // 绑定根节点
    el:'#admin',
    // 数据
    data() {
        return {
            labelPosition: 'right',
            // 表单数据
            form: {
                administratorName: '',
                administratorPhone:"",
                administratorId:"",
                adminName:"",
                powerContent:"",
                adminId:"",
                oldPwd:"",
                newPwd:"",
                rePwd:"",
                adminPassword: '',
                adminPasswords: ''
            },
            // 修改信息模态框
            updateDialogVisible:false,
            updateInfo:false
        }
    },
    // 方法
    methods:{
        // 初始化
        init(){
            $.ajax({
                url:'../../administrator/findById',
                type:'get',
                dataType:'json',
                success:function (datas) {
                    console.log(datas);
                    admin.form=datas.data.administrator;
                    admin.form.adminName=datas.data.administrator.adminBean.adminName;
                    admin.form.adminId=datas.data.administrator.adminBean.adminId;
                }
            })
        },
        // 打开修改基本信息模态框
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
                url:'../../administrator/updateAdministrator',
                data:{_method:'put',
                    "administratorPhone":this.form.administratorPhone,
                    "administratorId":this.form.administratorId},
                dataType:'json',
                success:function (datas) {
                    if(datas.code===200){
                        admin.$message.success('修改成功');
                    } else {
                        admin.$message.error('修改失败');
                    }
                    admin.init();
                    admin.updateDialogVisible=false;
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
                        admin.$message.success('修改成功');
                    } else {
                        admin.$message.error('修改失败');
                    }
                    admin.init();
                    admin.updateDialogVisible=false;
                }
            })
        },
        back(){
            admin.init();
            this.updateDialogVisible=false;
        }
    }
});
// 初始化页面数据
admin.init();