let main = new Vue({
    el: ".main",
    data() {
        return {
            dialogVisible:true,
            username:"test",
            collapseBtnClick: false,
            isCollapse: true,
            isPersonage:"display:none", //用户管理
            isComplaints:"display:none",//投诉管理
            isContract:"display:none",//合约管理
            isStall:"display:none", //车位管理
            isPermission:"display:none",//权限操作管理
            user:{
                username:"",
            }
        };
    },
    methods:{
        inits(){
            $.ajax({
                type: "post",
                url: "/administrator/getAdministrator",
                dataType: "json",
                success: function (resp) {
                    // console.log(resp);
                    main.user.username= resp.data.admin.administratorName;
                }
            })
        },
        logout(){
            //注销登录方法
            window.parent.show.location.href = "/logout";
            //刷新当前页面
            window.location.replace("../login.html");
        },
        init(){
            //初始化权限方法
            $.ajax({
                type: "get",
                url: "/role/getPower",
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData.data);

                    $.each(resultData.data.role, function (i, datass) {
                        if(datass.powerContent=="personage"){
                            //用户管理
                            main.isPersonage="display:block";
                        }else if(datass.powerContent=="complaints"){
                            //投诉管理
                            main.isComplaints="display:block";
                        }else if(datass.powerContent=="contract"){
                            //合约管理
                            main.isContract="display:block";
                        }else if(datass.powerContent=="stall"){
                            //车位管理
                            main.isStall="display:block";
                        }else if(datass.powerContent=="permission"){
                            //权限操作管理
                            main.isPermission="display:block";
                        }
                    });
                }
            });
        },
        showCompany(){
            window.parent.show.location.href="findCompanyAll.html";
        },
        approveCarport(){
            window.parent.show.location.href="approveCarport_add.html";
        },
        personal(){
            window.parent.show.location.href="personal_find.html";
        },
        complaint(){
            window.parent.show.location.href="complaint.html";
        },
        external(){
            window.parent.show.location.href="external.html";
        },
        tenantry(){
            window.parent.show.location.href="tenantry.html";
        },
        carportMessage(){
            window.parent.show.location.href="CBDCarManagement.html";
        },
        showBill(){
            window.parent.show.location.href="bill_showBill.html";
        },
        showLog(){
            window.parent.show.location.href="log_show.html";
        },
        adminMessage(){
            window.parent.show.location.href="updateAdministator.html";
        },
        showAdmin(){
            window.parent.show.location.href="administrator.html";
        },
        loginNum(){
            window.parent.show.location.href="peopleNum.html";
        },
        respTime(){
            window.parent.show.location.href="performance.html";
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
})
//初始化权限
main.init();
//初始化姓名
main.inits();