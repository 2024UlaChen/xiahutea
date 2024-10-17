async function fetchData() {
    const response = await fetch('manage/download');
    const data = await response.json();
    return data.data;
}

const birthdayAnalyzeBtn = document.querySelector("#birthdayAnalyze");
const sexAnalyzeBtn = document.querySelector("#sexAnalyze");
const validAnalyzeBtn = document.querySelector("#validAnalyze");
const analyzeBlock = document.querySelector('#analyzeBlock');


birthdayAnalyzeBtn.addEventListener("click", function () {

    fetchData().then(data => {
        let ageArr = [];
        data.forEach((item, index) => {
            if (item.birthday != null) {
                ageArr[index] = 2024 - parseInt(item.birthday.substring(0, 4));
            } else {
                ageArr[index] = "none";
            }

        })
        let countMap = new Map();
        ageArr.sort((a, b) => b - a);

        ageArr.forEach(item => {
                if (!countMap.has(item)) {
                    countMap.set(item, 1);
                } else {
                    let temp = parseInt(countMap.get(item));
                    temp++;
                    countMap.set(item, temp);
                }
            }
        );
        let ageCountArr = [], ageTitleArr = [];

        Array.from(countMap.keys()).forEach((item, index) => {
            ageTitleArr[index] = item === "none" ? "未填寫" : item;
            ageCountArr[index] = countMap.get(item);
        })

        let option = {
            color: [
                '#73B4BA', '#f1a253', '#ef8994'
            ],
            title: {
                text: '消費者-年紀分析'
            },
            tooltip: {},
            legend: {},
            xAxis: [
                {
                    type: 'category',
                    data: ageTitleArr
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: [
                {
                    name: '年紀',
                    type: 'bar',
                    data: ageCountArr,
                    markPoint: {
                        data: [
                            {type: 'max', data: 'Max'},
                        ]
                    },
                },
            ]

        };
        setChart(option);


    });
})

sexAnalyzeBtn.addEventListener("click", function () {

    fetchData().then(data => {
        let countMap = new Map();

        data.forEach(item => {
                if (!countMap.has(item.sex)) {
                    countMap.set(item.sex, 1);
                } else {
                    let temp = parseInt(countMap.get(item.sex));
                    temp++;
                    countMap.set(item.sex, temp);
                }
            }
        );

        let countArr = [];

        Array.from(countMap.keys()).forEach((item, index) => {
            let countObj = {};
            countObj.name = item === "none" ? "不提供" : item;
            countObj.value = countMap.get(item);
            countArr[index] = countObj;
        })

        let option = {
            color: [
                '#73B4BA', '#f1a253', '#ef8994'
            ],
            title: {
                text: '消費者-性別'
            },
            tooltip: {},
            legend: {},
            series: [
                {
                    radius: [50, 150],
                    roseType: 'area',
                    type: 'pie',
                    itemStyle: {
                        borderRadius: countArr.length
                    }, data: countArr
                }
            ]
        };
        setChart(option);

    });
})


validAnalyzeBtn.addEventListener("click", function () {

    fetchData().then(data => {
        let countMap = new Map();

        data.forEach(item => {
                if (!countMap.has(item.validStatus)) {
                    countMap.set(item.validStatus, 1);
                } else {
                    let temp = parseInt(countMap.get(item.validStatus));
                    temp++;
                    countMap.set(item.validStatus, temp);
                }
            }
        );

        let countArr = [];
        Array.from(countMap.keys()).forEach((item, index) => {
            let countObj = {};
            countObj.name = item ? "帳號停權" : "帳號生效";
            countObj.value = countMap.get(item);
            countArr[index] = countObj;
        })

        let option = {
            color: [
                '#73B4BA', '#f1a253', '#ef8994'
            ],
            title: {
                text: '消費者-停權'
            },
            tooltip: {},
            legend: {},
            series: [
                {
                    radius: [50, 150],
                    roseType: 'area',
                    type: 'pie',
                    itemStyle: {
                        borderRadius: countArr.length
                    }, data: countArr
                }
            ]
        };
        setChart(option);
    });
})

function setChart(option) {
    let myChart = echarts.init(analyzeBlock);
    myChart.clear();
    myChart.setOption(option);
}
