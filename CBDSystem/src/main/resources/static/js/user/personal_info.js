//数据的展示
let personal = new Vue({
    el: ".personal_info",
    data(){
        return {
            //没反应，所以舍弃了
            // icont:require("../../img/3.png")
        }
    },
    methods: {
        handleEdit(){
            //查看按钮
            window.location.href="personal.html";
        }
    }
});