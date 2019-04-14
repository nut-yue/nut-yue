$(function () {
    layui.use('table', function(){
        var table = layui.table;
        table.render({
            elem: '#demo'
            ,height: 350
            ,url: '../json/data.json' //数据接口
            ,page: true //开启分页
            ,cols: [[ //表头
                {field: 'id', title: 'ID', sort: true, fixed: 'left'}
                ,{field: 'username', title: '区域名称'}
                ,{field: 'sex', title: '林种',  sort: true}
                ,{field: 'city', title: '地类'}
                ,{field: 'sign', title: '优势树种'}
            ]]
        });
    });
    $(".btn:eq(0)").click(function () {
        window.location.href="addClass.html";
    })
    $(".btn:eq(1)").click(function () {
        window.location.href="showClass.html";
    })
    $(".btn:eq(2)").click(function () {
        window.location.href="updateClass.html";
    })
})