let main = new Vue({
    el: ".main",
    data() {
        return {
            username: "test",
            collapseBtnClick: false,
            isCollapse: true,
            user:{
                username:"",
            }
        };

    },
    methods: {
        logout(){
            //注销登录方法
            window.parent.show.location.href = "/logout";
            //刷新当前页面
            window.location.replace("/login/login.html");
        },
        carportBuy() {
            window.parent.show.location.href = "medicineManger.html";
        },
        carportSell() {
            window.parent.show.location.href = "medicineManger.html";
        },
        appointer() {
            window.parent.show.location.href = "medicineManger.html";
        },
        appointed() {
            window.parent.show.location.href = "medicineManger.html";
        },
        bill() {
            window.parent.show.location.href = "medicineManger.html";
        },
        complaint() {
            window.parent.show.location.href = "medicineManger.html";
        },
        goodsList() {
            window.parent.show.location.href = "medicineManger.html";
        },
        carportAdd() {
            window.parent.show.location.href = "medicineManger.html";
        },
        personal() {
            window.parent.show.location.href = "medicineManger.html";
        },
        userMessage() {
            window.parent.show.location.href = "medicineMangerIndex.html";
        },
        myInfo() {
            window.parent.show.location.href = "medicineManger.html";
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
