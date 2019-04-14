
layui.use('table', function(){
    var table = layui.table;
    table.render({
        elem: '#demo'
        ,height: 500
        ,url: '../jsons/log.json' //数据接口
        ,page: true //开启分页
        ,cols: [[ //表头
            {field: 'log', title: '日志内容', width:580}
            ,{field: 'date', title: '日期', width:500, sort: true}
        ]]
    });

});

