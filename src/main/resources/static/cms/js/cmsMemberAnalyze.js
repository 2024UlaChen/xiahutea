async function fetchData() {
    const response = await fetch('manage/download');
    const data = await response.json();
    return data.data;
}

const birthdayAnalyzeBtn = document.querySelector("#birthdayAnalyze");
document.addEventListener("DOMContentLoaded", function () {

    fetch("manage/download")
        .then(res => res.json())
        .then(data => {
            // let birthday = [];
            // let birthdayYearMonth
            // data.data.forEach((item) => {
            //     birthday.push(item.birthday);
            // })
            // birthday.forEach(item => {
            //     console.log(Math.min(item));
            // })


            let option = {
                title: {
                    text: '消費者資料分析'
                },
                tooltip: {},
                legend: {
                    data: ['生日']
                },
                xAxis: {
                    data: ['生日']
                },
                yAxis: {},
                series: [
                    {
                        name: '生日',
                        type: 'bar',
                        data: [10, 30, 20, 4, 5, 6, 7, 89, 1, 1, 1, 1]
                    }
                ]
            };

// 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

// 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        })


})

function updateOptionData() {
    let option = {
        title: {
            text: '消費者資料分析'
        },
        tooltip: {},
        legend: {
            data: ['生日']
        },
        xAxis: {
            data: ['生日']
        },
        yAxis: {},
        series: [
            {
                name: '生日',
                type: 'bar',
                data: [10, 30, 20, 4, 5, 6, 7, 89, 1, 1, 1, 1]
            }
        ]
    };
    return option;
}

function processData(data) {

    // TODO
    let femaleArr = data.filter(item => item.sex === "female");
    console.log(femaleArr);
}

birthdayAnalyzeBtn.addEventListener("click", function () {
    fetchData().then(data => {
        processData(data);
    });
})




