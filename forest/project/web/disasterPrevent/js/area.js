$(function () {
    layui.use('table', function(){
        var table = layui.table;
        table.render({
            elem: '#demo'
            ,height: 350
            ,url: '../json/data.json' //数据接口
            ,page: true //开启分页
            ,cols: [[ //表头
                {field: 'id', title: 'ID',  sort: true, fixed: 'left'}
                ,{field: 'username', title: '区域名称' }
                ,{field: 'sex', title: '林种',  sort: true}
                ,{field: 'city', title: '地类'}
                ,{field: 'sign', title: '优势树种'}
            ]]
        });
    });
    $(".btn").click(function () {
        window.location.href="addArea.html";
    })
})