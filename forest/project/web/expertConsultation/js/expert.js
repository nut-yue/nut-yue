$(function () {
    layui.use('table', function(){
        var table = layui.table;
        table.render({
            elem: '#demo'
            ,height: 350
            ,url: '../json/data.json' //数据接口
            ,page: true //开启分页
            ,cols: [[ //表头
                {field: 'id', title: '姓名', sort: true, fixed: 'left'}
                ,{field: 'username', title: '工作单位'}
                ,{field: 'sex', title: '专长',  sort: true}
                ,{field: 'city', title: '职务'}
                ,{field: 'sign', title: '电话'}
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
        window.location.href="addExpert.html";
    })
    $(".btns:eq(1)").click(function () {
        window.location.href="findExpertInformation.html";
    })
    $(".btns:eq(2)").click(function () {
        window.location.href="updateExpertInformation.html";
    })
    $(".btns:eq(3)").click(function () {
        window.location.href="updateEvent.html";
    })
})