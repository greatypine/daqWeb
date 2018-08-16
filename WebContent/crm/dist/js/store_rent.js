var myChart1 = echarts.init(document.getElementById('main1'));
var myChart2 = echarts.init(document.getElementById('main2'));
var myChart3 = echarts.init(document.getElementById('main3'));
var myChart4 = echarts.init(document.getElementById('main4'));
var myChart5 = echarts.init(document.getElementById('main5'));
var myChart6 = echarts.init(document.getElementById('main6'));
var myChart7 = echarts.init(document.getElementById('main7'));
var myChart8 = echarts.init(document.getElementById('main8'));
var myChart9 = echarts.init(document.getElementById('main9'));
var myChart10 = echarts.init(document.getElementById('main10'));
var myChart11 = echarts.init(document.getElementById('main11'));
var myChart12 = echarts.init(document.getElementById('main12'));
var markLineSetting4 = {
    symbol: 'none',
    lineStyle: {
        normal: {
            opacity: 0.5
        }
    },
    data: [{
        type: 'average',
        name: '平均值',
        label: {
            normal:{
                position:'left'
            }
         }
    }]
};
option1 = {
    title: {
        text: '门店数量占比',
        left: 'center'
    },
    tooltip: {
        trigger: 'item',
        formatter: "{b} : {c} ({d}%)"
    },
    legend: {
        // orient: 'vertical',
        // top: 'middle',
        bottom: 10,
        left: 'center',
        data: []
    },
    series: [
        {
            type: 'pie',
            radius: '55%',
            center: ['50%', '50%'],
            selectedMode: 'single',
            label: {
                normal: {
                    position: 'out', //标签的位置
                    formatter: "{b} : {d}%"
                }
            },
            data: [],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
};

option2 = {
    title: {
        text: '计租面积占比',
        left: 'center'
    },
    tooltip: {
        trigger: 'item',
        formatter: "{b} : {c} ({d}%)"
    },
    legend: {
        // orient: 'vertical',
        // top: 'middle',
        bottom: 10,
        left: 'center',
        data: []
    },
    series: [
        {
            type: 'pie',
            radius: '50%',
            center: ['50%', '50%'],
            selectedMode: 'single',
            label: {
                normal: {
                    position: 'out', //标签的位置
                    formatter: "{b} : {d}%"
                }
            },
            data: [],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
};

option3 = {
    title: {
        text: '使用面积总和',
        left: 'center'
    },
    tooltip: {
        trigger: 'item',
        formatter: "{b} : {c} ({d}%)"
    },
    legend: {
        // orient: 'vertical',
        // top: 'middle',
        bottom: 10,
        left: 'center',
        data: []
    },
    series: [
        {
            type: 'pie',
            radius: '50%',
            center: ['50%', '50%'],
            selectedMode: 'single',
            label: {
                normal: {
                    position: 'out', //标签的位置
                    formatter: "{b} : {d}%"
                }
            },
            data: [],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
};

option4 = {
    title: {
        text: '单店平均计租面积',
        x: 'center'
    },
    color: ['#3398DB'],
    tooltip: {
        trigger: 'axis',
        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: [
        {
            type: 'category',
            data: [],
            axisTick: {
                alignWithLabel: true
            }
        }
    ],
    yAxis: [

        {
            type: 'value',
            name: "单位：㎡"
        }
    ],
    series: [
        {
            name: '单店平均计租面积',
            type: 'bar',
            barWidth: '45%',
            data: [],
            markLine: markLineSetting4,
            label: {
                normal: {
                    show: true
                }
            }
        }
    ]
};
option5 = {
    title: {
        text: '单店平均使用面积',
        x: 'center'
    },
    color: ['#3398DB'],
    tooltip: {
        trigger: 'axis',
        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: [
        {
            type: 'category',
            data: [],
            axisTick: {
                alignWithLabel: true
            }
        }
    ],
    yAxis: [

        {
            type: 'value',
            name: "单位：㎡"
        }
    ],
    series: [
        {
            name: '单店平均使用面积',
            type: 'bar',
            barWidth: '45%',
            data: [],
            markLine: markLineSetting4,
            label: {
                normal: {
                    show: true
                }
            }
        }
    ]
};
option6 = {
    title: {
        text: '计租面积平均使用率',
        x: 'center'
    },
    color: ['#3398DB'],
    tooltip: {
        trigger: 'axis',
        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: [
        {
            type: 'category',
            data: [],
            axisTick: {
                alignWithLabel: true
            }
        }
    ],
    yAxis: [

        {
            type: 'value',
            name: "单位：%"
        }
    ],
    series: [
        {
            name: '计租面积平均使用率',
            type: 'bar',
            barWidth: '45%',
            data: [],
            markLine: markLineSetting4,
            label: {
                normal: {
                    show: true
                }
            }
        }
    ]
};
option7 = {
    title: {
        text: '计租面积平均单价',
        x: 'center'
    },
    color: ['#3398DB'],
    tooltip: {
        trigger: 'axis',
        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: [
        {
            type: 'category',
            data: [],
            axisTick: {
                alignWithLabel: true
            }
        }
    ],
    yAxis: [

        {
            type: 'value',
            name: "单位：元"
        }
    ],
    series: [
        {
            name: '计租面积平均单价',
            type: 'bar',
            barWidth: '45%',
            data: [],
            markLine: markLineSetting4,
            label: {
                normal: {
                    show: true
                }
            }
        }
    ]
};
option8 = {
    title: {
        text: '使用面积平均单价',
        x: 'center'
    },
    color: ['#3398DB'],
    tooltip: {
        trigger: 'axis',
        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: [
        {
            type: 'category',
            data: [],
            axisTick: {
                alignWithLabel: true
            }
        }
    ],
    yAxis: [

        {
            type: 'value',
            name: "单位：元"
        }
    ],
    series: [
        {
            name: '使用面积平均单价',
            type: 'bar',
            barWidth: '45%',
            data: [],
            markLine: markLineSetting4,
            label: {
                normal: {
                    show: true
                }
            }
        }
    ]
};
option9 = {
    title: {
        text: '中介成交店铺数量占比',
        left: 'center'
    },
    tooltip: {
        trigger: 'item',
        formatter: "{b} : {c} ({d}%)"
    },
    legend: {
        // orient: 'vertical',
        // top: 'middle',
        bottom: 10,
        left: 'center',
        data: []
    },
    series: [
        {
            type: 'pie',
            radius: '50%',
            center: ['50%', '50%'],
            selectedMode: 'single',
            label: {
                normal: {
                    position: 'out', //标签的位置
                    formatter: "{b} : {c} ({d}%)"
                }
            },
            data: [],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
};
option10 = {
    title: {
        text: '中介费金额占比',
        left: 'center'
    },
    tooltip: {
        trigger: 'item',
        formatter: "{b} : {c}万元 ({d}%)"
    },
    legend: {
        // orient: 'vertical',
        // top: 'middle',
        bottom: 10,
        left: 'center',
        data: []
    },
    series: [
        {
            type: 'pie',
            radius: '50%',
            center: ['50%', '50%'],
            selectedMode: 'single',
            label: {
                normal: {
                    position: 'out', //标签的位置
                    formatter: "{b} : {c} ({d}%)"
                }
            },
            data: [],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
};
option11 = {
    title: {
        text: '单店平均中介费',
        x: 'center'
    },
    color: ['#3398DB'],
    tooltip: {
        trigger: 'axis',
        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: [
        {
            type: 'category',
            data: [],
            axisTick: {
                alignWithLabel: true
            }
        }
    ],
    yAxis: [

        {
            type: 'value',
            name: "单位：元"
        }
    ],
    series: [
        {
            name: '单店平均中介费',
            type: 'bar',
            barWidth: '45%',
            data: [],
            markLine: markLineSetting4,
            label: {
                normal: {
                    show: true
                }
            }
        }
    ]
};
option12 = {
    title: {
        text: '付款方式',
        left: 'center'
    },
    tooltip: {
        trigger: 'item',
        formatter: "{b} : {c} ({d}%)"
    },
    legend: {
        // orient: 'vertical',
        // top: 'middle',
        bottom: 10,
        left: 'center',
        data: []
    },
    series: [
        {
            type: 'pie',
            radius: '55%',
            center: ['50%', '50%'],
            selectedMode: 'single',
            label: {
                normal: {
                    position: 'out', //标签的位置
                    formatter: "{b} : {c} ({d}%)"
                }
            },
            data: [],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
};
myChart1.setOption(option1);
myChart2.setOption(option2);
myChart3.setOption(option3);
myChart4.setOption(option4);
myChart5.setOption(option5);
myChart6.setOption(option6);
myChart7.setOption(option7);
myChart8.setOption(option8);
myChart9.setOption(option9);
myChart10.setOption(option10);
myChart11.setOption(option11);
myChart12.setOption(option12);


$(function () {
    initStoreData();
})
function initStoreData() {
    doManager("storeManager", "findStoreRentDataByCity", null, function (data, textStatus, XMLHttpRequest) {
        if (data.result) {
            var allData = JSON.parse(data.data);
            //全国月店数量数据分析(门店数量)
            var storeData = allData.storeData;//门店数据库查询数据
            var city_data = new Array();   //城市数组
            var store_math_data_array = new Array();//城市门店数量
            var store_jizu_area_array = new Array();//计租面积
            var store_usable_area_array = new Array();//计租面积
            var unit_store_jizu_array = new Array();//单店平均计租面积图
            var unit_store_usable_array = new Array();//单店平均使用面积图
            var store_area_PCT_array = new Array();//计租面积平均使用率图
            var store_jizu_unit_price = new Array();//计租面积平均单价(图)
            var store_usable_unit_price = new Array();//使用面积平均单价(图)
            var store_zhongjie_array = new Array();//中介成交店铺数量占比(图)
            var store_zhongjie_money_array = new Array();//中介费金额占比（图）
            var store_zhongjie_unit_money_array = new Array();//单店平均中介费（图）
            var store_payment_method_array = new Array();//支付方式（图）

            var mothStoreData_str = "";//门店数量字符串
            var store_jizu_str_table = "";//计租面积表
            var store_usable_str_table = "";//计租面积表
            var store_payment_method_str = "";//支付方式（表）
            var xAxisData = new Array();//柱形图城市名字过长
            for (var a = 0; a < storeData.length; a++) {
                xAxisData[a] = storeData[a].city_name.length > 5 ? storeData[a].city_name.substring(0, 5) + '\n' + storeData[a].city_name.substring(5, storeData[a].city_name.length) : storeData[a].city_name;
                if (a == 0) {
                    //门店数量表
                    mothStoreData_str += "<tr><td class='text-red' style='font-weight:bold;'>" + storeData[a].city_name + "</td>" +
                        "<td>" + storeData[a].city_total + "</td>" +
                        "<td>" + storeData[a].store_PCT + "%</td></tr>";
                    //计租面积表
                    store_jizu_str_table += "<tr><td class='text-red' style='font-weight:bold;'>" + storeData[a].city_name + "</td>" +
                        "<td>" + storeData[a].jizu_area + "</td>" +
                        "<td>" + storeData[a].jizu_PCT + "%</td></tr>";
                    //使用面积表
                    store_usable_str_table += "<tr><td class='text-red' style='font-weight:bold;'>" + storeData[a].city_name + "</td>" +
                        "<td>" + storeData[a].usable_area + "</td>" +
                        "<td>" + storeData[a].usable_PCT + "%</td></tr>";
                } else if (a == 1 || a == 2) {
                    //门店数量表
                    mothStoreData_str += "<tr><td style='font-weight:bold;'>" + storeData[a].city_name + "</td>" +
                        "<td>" + storeData[a].city_total + "</td>" +
                        "<td>" + storeData[a].store_PCT + "%</td></tr>";
                    //计租面积表
                    store_jizu_str_table += "<tr><td style='font-weight:bold;'>" + storeData[a].city_name + "</td>" +
                        "<td>" + storeData[a].jizu_area + "</td>" +
                        "<td>" + storeData[a].jizu_PCT + "%</td></tr>";
                    //使用面积表
                    store_usable_str_table += "<tr><td style='font-weight:bold;'>" + storeData[a].city_name + "</td>" +
                        "<td>" + storeData[a].usable_area + "</td>" +
                        "<td>" + storeData[a].usable_PCT + "%</td></tr>";
                } else {
                    //门店数量表
                    mothStoreData_str += "<tr><td>" + storeData[a].city_name + "</td>" +
                        "<td>" + storeData[a].city_total + "</td>" +
                        "<td>" + storeData[a].store_PCT + "%</td></tr>";
                    //计租面积表
                    store_jizu_str_table += "<tr><td>" + storeData[a].city_name + "</td>" +
                        "<td>" + storeData[a].jizu_area + "</td>" +
                        "<td>" + storeData[a].jizu_PCT + "%</td></tr>";
                    //使用面积表
                    store_usable_str_table += "<tr><td>" + storeData[a].city_name + "</td>" +
                        "<td>" + storeData[a].usable_area + "</td>" +
                        "<td>" + storeData[a].usable_PCT + "%</td></tr>";
                }
                //给门店饼图赋值
                city_data[a] = storeData[a].city_name;
                var obj = {"value": storeData[a].city_total, "name": storeData[a].city_name};
                store_math_data_array[a] = obj;

                //计租面积饼图
                var obj = {"value": storeData[a].jizu_area, "name": storeData[a].city_name};
                store_jizu_area_array[a] = obj;

                //使用面积饼图
                var obj = {"value": storeData[a].usable_area, "name": storeData[a].city_name};
                store_usable_area_array[a] = obj;
                //单店平均计租面积(柱形图)
                unit_store_jizu_array[a] = {"value": storeData[a].jizu_avg_area, "name": storeData[a].city_name};
                //单店平均使用面积柱形图
                unit_store_usable_array[a] = storeData[a].usable_avg_area;
                //计租面积平均使用率(图)
                store_area_PCT_array[a] = storeData[a].store_avg_PCT;
                //计租面积平均单价(图)
                store_jizu_unit_price[a] = storeData[a].city_avg_rental_jizu;
                //使用面积平均单价(图)
                store_usable_unit_price[a] = storeData[a].city_avg_rental_usable;

                //中介成交店铺数量占比(图)
                var obj = {"value": storeData[a].store_zhongjie, "name": storeData[a].city_name};
                store_zhongjie_array[a] = obj;

                //中介费总额占比(图)
                var obj = {"value": storeData[a].agency_fee_total, "name": storeData[a].city_name};
                store_zhongjie_money_array[a] = obj;

                //单店平均中介费(图)
                var obj = {"value": storeData[a].store_price, "name": storeData[a].city_name};
                store_zhongjie_unit_money_array[a] = obj;
            }

            //给门店数量表赋值
            mothStoreData_str += "<tr style='font-weight:bold;'><th>总计:</th><th>" + allData.totalData.china_store_total + "</th><th>100%</th></tr>";
            $("#city_store_yue").append(mothStoreData_str);
            //全国月店数量数据分析(门店数量饼图)
            option1.legend.data = city_data;
            option1.series[0].data = store_math_data_array;
            myChart1.setOption(option1);
            //给门店计租面积表赋值
            store_jizu_str_table += "<tr style='font-weight:bold;'><th>总计:</th><th>" + allData.totalData.china_jizu_total + "</th><th>100%</th></tr>";
            $("#store_jizu_table").append(store_jizu_str_table);
            //全国月店数量数据分析(计租面积饼图)
            option2.legend.data = city_data;
            option2.series[0].data = store_jizu_area_array;
            myChart2.setOption(option2);
            //给门店使用面积表赋值
            store_usable_str_table += "<tr style='font-weight:bold;'><th>总计:</th><th>" + allData.totalData.china_usable_total + "</th><th>100%</th></tr>";
            $("#store_usable_table").append(store_usable_str_table);
            //全国月店数量数据分析(使用面积饼图)
            option3.legend.data = city_data;
            option3.series[0].data = store_usable_area_array;
            myChart3.setOption(option3);
            //单店平均计租面积(柱状图)
            option4.xAxis[0].data = xAxisData;
            option4.series[0].data = unit_store_jizu_array;
            myChart4.setOption(option4);

            //单店平均使用面积(柱状图)
            option5.xAxis[0].data = xAxisData;
            option5.series[0].data = unit_store_usable_array;
            myChart5.setOption(option5);

            //单店平均使用面积(柱状图)
            option6.xAxis[0].data = xAxisData;
            option6.series[0].data = store_area_PCT_array;
            myChart6.setOption(option6);

            //计租面积平均单价(柱状图)
            option7.xAxis[0].data = xAxisData;
            option7.series[0].data = store_jizu_unit_price;
            myChart7.setOption(option7);

            //使用面积平均单价(柱状图)
            option8.xAxis[0].data = xAxisData;
            option8.series[0].data = store_usable_unit_price;
            myChart8.setOption(option8);
            console.log("---------" + allData.totalData.china_avg_PCT + "---------------" + allData.totalData.china_avg_rental_jizu + "--------------" + allData.totalData.china_avg_rental_usable + "---------------");

            //中介成交店铺数量(图)
            option9.legend.data = city_data;
            option9.series[0].data = store_zhongjie_array;
            myChart9.setOption(option9);


            //中介成交店铺数量(图)
            option10.legend.data = city_data;
            option10.series[0].data = store_zhongjie_money_array;
            myChart10.setOption(option10);

            option11.xAxis[0].data = xAxisData;
            option11.series[0].data = store_zhongjie_unit_money_array;
            myChart11.setOption(option11);
            //支付方式(表)
            var mapPaymentData = allData.mapPaymentData;
            store_payment_method_str = "<tr><td>月付:</td><td>" + mapPaymentData.mouthPayment + "</td></tr>" +
                "<tr><td>季付:</td><td>" + mapPaymentData.quarterlyPayment + "</td></tr>" +
                "<tr><td>半年付:</td><td>" + mapPaymentData.semiAnnualPayment + "</td></tr>" +
                "<tr><td>年付:</td><td>" + mapPaymentData.annualPayment + "</td></tr>";
            store_payment_method_str += "<tr class='text-red'><th>总计:</th><th>" + allData.totalData.china_store_total + "</th></tr>";
            $("#store_payment_method").append(store_payment_method_str);
            //支付方式(图)
            store_payment_method_array[0] = {"value": mapPaymentData.mouthPayment, "name": "月付"};
            store_payment_method_array[1] = {"value": mapPaymentData.quarterlyPayment, "name": "季付"}
            store_payment_method_array[2] = {"value": mapPaymentData.semiAnnualPayment, "name": "半年付"}
            store_payment_method_array[3] = {"value": mapPaymentData.annualPayment, "name": "年付"}
            var ccc = ["月付", "季付", "半年付", "年付"];
            option12.legend.data = ccc;
            option12.series[0].data = store_payment_method_array;
            myChart12.setOption(option12);
            var storePaymentData = allData.storePaymentData;
            var storePaymentData_str = "";
            for (var b = 0; b < storePaymentData.length; b++) {
                storePaymentData_str += "<tr><td>" + storePaymentData[b].city_name + "</td>" +
                    "<td>" + (parseInt(storePaymentData[b].mouthPayment) + parseInt(storePaymentData[b].quarterlyPayment) + parseInt(storePaymentData[b].semiAnnualPayment) + parseInt(storePaymentData[b].annualPayment)) + "</td>" +
                    "<td>" + storePaymentData[b].mouthPayment + "</td>" +
                    "<td>" + storePaymentData[b].quarterlyPayment + "</td>" +
                    "<td>" + storePaymentData[b].semiAnnualPayment + "</td>" +
                    "<td>" + storePaymentData[b].annualPayment + "</td>" +
                    "</tr>";
            }
            $("#store_payment_method_info").append(storePaymentData_str);

            //全国星店数据分析
            var starStoreData_str = "";
            var starStoreData = allData.starStoreData;
            var star_total = 0;
            var star_jizu_area_total = 0;
            var star_jizu_area_unit = 0;
            var star_jizu_avg_rental = 0;
            var star_usable_area_total = 0;
            var star_usable_area_unit = 0;
            var star_usable_avg_rental = 0;
            for (var a = 0; a < starStoreData.length; a++) {
                star_total += parseInt(starStoreData[a].city_count);
                star_jizu_area_total += parseFloat(starStoreData[a].jizu_area_total);
                star_jizu_area_unit += parseFloat(starStoreData[a].jizu_area_avg);
                star_jizu_avg_rental += parseFloat(starStoreData[a].jizu_area_rental);
                star_usable_area_total += parseFloat(starStoreData[a].usable_area_total);
                star_usable_area_unit += parseFloat(starStoreData[a].usable_area_avg);
                star_usable_avg_rental += parseFloat(starStoreData[a].usable_area_rental);
                starStoreData_str += ("<tr><td>" + starStoreData[a].city_name + "</td><td>" + starStoreData[a].city_count + "</td>" +
                "<td>" + starStoreData[a].jizu_area_total + "</td><td>" + starStoreData[a].jizu_area_avg + "</td>" +
                "<td>" + starStoreData[a].jizu_area_rental + "</td><td>" + starStoreData[a].usable_area_total + "</td>" +
                "<td>" + starStoreData[a].usable_area_avg + "</td><td>" + starStoreData[a].usable_area_rental + "</td></tr>");
            }
            starStoreData_str += "<tr class='bg-light-blue'><th>总计/平均值</th><th>" + star_total + "</th>" +
                "<th>" + star_jizu_area_total.toFixed(2) + "</th><th>" + parseFloat(star_jizu_area_unit / starStoreData.length).toFixed(2) + "</th>" +
                "<th>" + parseFloat(star_jizu_avg_rental / starStoreData.length).toFixed(2) + "</th><th>" + star_usable_area_total.toFixed(2) + "</th>" +
                "<th>" + parseFloat(star_usable_area_unit / starStoreData.length).toFixed(2) + "</th><th>" + parseFloat(star_usable_avg_rental / starStoreData.length).toFixed(2) + "</th></tr>"
            $("#star_store_analyze").append(starStoreData_str);
            var starStoreData_str_ss = "";
            var starStoreData1 = allData.starStoreInfo;
            for (var a = 0; a < starStoreData1.length; a++) {
                starStoreData_str_ss += ("<tr><td>" + starStoreData1[a].county_name + "</td><td>" + starStoreData1[a].town_name + "</td>" +
                "<td>" + starStoreData1[a].rent_area + "</td><td>" + starStoreData1[a].usable_area + "</td>" +
                "<td>" + starStoreData1[a].use_PRC + "</td><td>" + starStoreData1[a].rental + "</td>" +
                "<td>" + starStoreData1[a].usable_rental + "</td><td>" + starStoreData1[a].agency_fee + "</td>" +
                "<td>" + starStoreData1[a].rent_free + "</td><td>" + starStoreData1[a].payment_method + "</td>" +
                "</tr>");
            }
            $("#star_store_info").append(starStoreData_str_ss);


            //全国校园店数据分析
            var schoolStoreData_str = "";
            var schoolStoreData = allData.schoolStoreData;
            var school_total = 0;
            var school_jizu_area_total = 0;
            var school_jizu_area_unit = 0;
            var school_jizu_avg_rental = 0;
            var school_usable_area_total = 0;
            var school_usable_area_unit = 0;
            var school_usable_avg_rental = 0;
            for (var a = 0; a < schoolStoreData.length; a++) {
                school_total += parseInt(schoolStoreData[a].city_count);
                school_jizu_area_total += parseFloat(schoolStoreData[a].jizu_area_total);
                school_jizu_area_unit += parseFloat(schoolStoreData[a].jizu_area_avg);
                school_jizu_avg_rental += parseFloat(schoolStoreData[a].jizu_area_rental);
                school_usable_area_total += parseFloat(schoolStoreData[a].usable_area_total);
                school_usable_area_unit += parseFloat(schoolStoreData[a].usable_area_avg);
                school_usable_avg_rental += parseFloat(schoolStoreData[a].usable_area_rental);
                schoolStoreData_str += ("<tr><td>" + schoolStoreData[a].city_name + "</td><td>" + schoolStoreData[a].city_count + "</td>" +
                "<td>" + schoolStoreData[a].jizu_area_total + "</td><td>" + schoolStoreData[a].jizu_area_avg + "</td>" +
                "<td>" + schoolStoreData[a].jizu_area_rental + "</td><td>" + schoolStoreData[a].usable_area_total + "</td>" +
                "<td>" + schoolStoreData[a].usable_area_avg + "</td><td>" + schoolStoreData[a].usable_area_rental + "</td></tr>");
            }
            schoolStoreData_str += "<tr class='bg-light-blue'><th>总计/平均值</th><th>" + school_total + "</th>" +
                "<th>" + school_jizu_area_total.toFixed(2) + "</th><th>" + parseFloat(school_jizu_area_unit / schoolStoreData.length).toFixed(2) + "</th>" +
                "<th>" + parseFloat(school_jizu_avg_rental / schoolStoreData.length).toFixed(2) + "</th><th>" + school_usable_area_total.toFixed(2) + "</th>" +
                "<th>" + parseFloat(school_usable_area_unit / schoolStoreData.length).toFixed(2) + "</th><th>" + parseFloat(school_usable_avg_rental / schoolStoreData.length).toFixed(2) + "</th></tr>"
            $("#school_store_analyze").append(schoolStoreData_str);
            var schoolStoreData_str_ss = "";
            var schoolStoreData1 = allData.schoolStoreInfo;
            for (var a = 0; a < schoolStoreData1.length; a++) {
                schoolStoreData_str_ss += ("<tr><td>" + schoolStoreData1[a].city_name + "</td><td>" + schoolStoreData1[a].store_name + "</td>" +
                "<td>" + schoolStoreData1[a].rent_area + "</td><td>" + schoolStoreData1[a].usable_area + "</td>" +
                "<td>" + schoolStoreData1[a].use_PRC + "</td><td>" + schoolStoreData1[a].rental + "</td>" +
                "<td>" + schoolStoreData1[a].usable_rental + "</td><td>" + schoolStoreData1[a].agency_fee + "</td>" +
                "<td>" + schoolStoreData1[a].rent_free + "</td><td>" + schoolStoreData1[a].payment_method + "</td>" +
                "</tr>");
            }
            $("#school_store_info").append(schoolStoreData_str_ss);

        }
    }, false);
}


//数组对象排序(降序排序)
var comparedesc = function (prop) {
    return function (obj1, obj2) {
        var val1 = obj1[prop];
        var val2 = obj2[prop];
        if (val1 < val2) {
            return -1;
        } else if (val1 > val2) {
            return 1;
        } else {
            return 0;
        }
    }
}
//数组对象排序(升序排序)
var compareasc = function (prop) {
    return function (obj1, obj2) {
        var val1 = obj1[prop];
        var val2 = obj2[prop];
        if (!isNaN(Number(val1)) && !isNaN(Number(val2))) {
            val1 = Number(val1);
            val2 = Number(val2);
        }
        if (val1 < val2) {
            return -1;
        } else if (val1 > val2) {
            return 1;
        } else {
            return 0;
        }
    }
}









