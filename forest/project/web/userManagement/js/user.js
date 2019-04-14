$(function () {
    layui.use('table', function(){
        var table = layui.table;
        table.render({
            elem: '#demo'
            ,height: 400
            ,url: '../../json/data.json' //数据接口
            ,page: true //开启分页
            ,cols: [[ //表头
                {field: 'username', title: '用户名'}
                ,{field: 'pwd', title: '密码',sort: true}
                ,{field: 'leavel', title: '等级',}
                ,{field: 'realName', title: '真实姓名',}
            ]]
        })
    });
    $(".add").click(function () {
        window.location.href="addUser.html";
    })
    $(".classInfo").click(function () {
        window.location.href="updateUser.html";
    })
    $(".delete").click(function () {
    })
});


