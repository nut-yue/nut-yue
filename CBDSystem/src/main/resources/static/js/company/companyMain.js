let main = new Vue({
    el: ".main",
    data() {
        return {
            username:"test",
            collapseBtnClick: false,
            isCollapse: true,
            user:{
                username:""
            }
        };
    },
    methods:{
        inits(){
            $.ajax({
                type: "post",
                url: "/company/getCompany",
                dataType: "json",
                success: function (resp) {
                    console.log(resp);
                    main.user.username= resp.data.admin.companyName;
                }
            })
        },
        logout(){
            //注销登录方法
            window.parent.show.location.href = "/logout";
            //刷新当前页面
            window.location.replace("../login.html");
        },
        rentCarport(){
            window.parent.show.location.href="rentCarport_show.html";
        },
        parkSpaceShow(){
            window.parent.show.location.href="parkSpace_showLeisure.html";
        },
        shopMessage(){
            window.parent.show.location.href="companyBill.html";
        },
        companyMessage(){
            window.parent.show.location.href="company_update.html";
        },
        collapseStatus() {
            this.collapseBtnClick = this.isCollapse;
            this.isCollapse = !this.isCollapse;
        },
        collapseOpen() {
            if (this.collapseBtnClick) return;
            this.isCollapse = false;
        },
        collapseClose() {
            if (this.collapseBtnClick) return;
            this.isCollapse = true;
        }
    }
});
main.inits();