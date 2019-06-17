
let vap=new Vue({
        el:'#salesContract',
        data() {
            return {
                activeNames: ['1'],
                // trueName:'张三',
                // trueName1:'李四',
                userRealName:'张三',
                userRealName1:'李四',
                userIdCard:'123456789',
                userIdCard1:'987654321',
                date:'2019-10-01',
                date1:'2019-10-01'
            };
        },
        methods: {
            handleChange(val) {
                console.log(val);
            }
        }
    });
