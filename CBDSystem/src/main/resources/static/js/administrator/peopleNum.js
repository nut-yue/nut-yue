// 基于准备好的dom，初始化echarts实例
let myChart = echarts.init(document.getElementById('main'));
let main = new Vue({
    el: "#root",
    data() {
        return {
            days: 0,
            weeks: 0,
            months: 0,
        }
    },
    methods: {
        init () {
            $.ajax({
                type: "get",
                url: "../../peopleNum/num",
                success:getData
            })
        }
    }
});
main.init();


//获取ajax数据的方法
function getData(resp) {
    console.log(resp);
    let day = resp.data.map.day
    let week = resp.data.map.weeks
    let month = resp.data.map.months
    main.days = sum(day);
    main.weeks = sum(week);
    main.months = sum(month);
    option = {
        title: {
            text: '系统在线人数统计'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['一天内', '一周内', '一月内']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['00:00','3:00', '6:00', '9:00', '12:00', '15:00', '18:00', '21:00']
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: '一天内',
                type: 'line',
                stack: '总量',
                data: day
            },
            {
                name: '一周内',
                type: 'line',
                stack: '总量',
                data: week
            },
            {
                name: '一月内',
                type: 'line',
                stack: '总量',
                data: month
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}
//获取数组的总和
function sum(arr) {
    let sum = 0
    for (let i = 0; i < arr.length; i++) {
        sum += arr[i];
    }
    return sum;
}