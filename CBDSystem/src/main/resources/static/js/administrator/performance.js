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
                url: "../../performance/num",
                success:getData
            })
        }
    }
});
main.init();
// getData()

//获取ajax数据的方法
function getData(resp) {

    let week = resp.data.map.week;
    let weekDate = resp.data.map.weekDate;
    option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                crossStyle: {
                    color: '#999'
                }
            }
        },
        toolbox: {
            feature: {
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        legend: {
            data:['平均响应时间']
        },
        xAxis: [
            {
                type: 'category',
                data: weekDate,
                axisPointer: {
                    type: 'shadow'
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '毫秒',
                min: 0,
                max: 500,
                interval: 100,
                axisLabel: {
                    formatter: '{value} ms'
                }
            }
        ],
        series: [
            {
                name:'平均响应时间',
                type:'bar',
                // yAxisIndex: 1,
                data:week
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