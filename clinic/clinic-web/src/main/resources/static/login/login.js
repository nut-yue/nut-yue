let login = new Vue({
    el: "#app",
    data() {
        return {
            username: "",
            password: ""
        }
    },
    methods: {
        login:function () {
            axios({
                method:'get',
                url:'/user/login',
                params:{username:this.username,password:this.password}
            }).then(function (res) {
                console.log(res);
                if(res.data.code==200){
                   window.location.href="../clinic/html/index.html"
                } else {
                    login.$message.error(res.data.message);
                }
            }).catch(err=>{
                login.$message.error('网络异常');
            })
        }
    }
})
