let main = new Vue({
    el: "#info",
    data: function () {
        return {
            tableData: [{
                date: '2016-05-02',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1518 弄',
                transactionPrice:'50W'
            }]
        }
    }
});


var E = window.wangEditor
var editor = new E('#editor')
editor.create()