
let vap=new Vue({
        el:'#operationBackgroundLog',
        data() {
            return {
                tableData: [{
                    personalId: '1',
                    personalContractNum: '停字20141120',
                    carportAddress:'XX路XX街道',
                    sellUserId:'张三',
                    buyUserId:'李四',
                    personalPrice:'4万'
                }, {
                    personalId: '2',
                    personalContractNum: '停字20141121',
                    carportAddress:'XX路XX街道',
                    sellUserId:'王五',
                    buyUserId:'赵六',
                    personalPrice:'3万'
                }]
            }
        },
        methods: {
            handleEdit(index, row) {
                console.log(index, row);
            },
            handleDelete(index, row) {
                console.log(index, row);
            }
        }
    });