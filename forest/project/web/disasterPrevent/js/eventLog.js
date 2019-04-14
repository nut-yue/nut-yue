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
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#test1' //指定元素
        });
    });
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#test2' //指定元素
        });
    });
    $(".btns:eq(0)").click(function () {
        window.location.href="addEvent.html";
    })
    $(".btns:eq(1)").click(function () {
        window.location.href="showEvent.html";
    })
    $(".btns:eq(2)").click(function () {
        //申请专家会审
    })
    $(".btns:eq(3)").click(function () {
        window.location.href="updateEvent.html";
    })
})