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
option1 = {
    title: [{
        text: '门店数量占比',
        x: 'center',
        top: '2%',
        textBaseline: 'middle',
        textStyle: {
            color: '#666',
            fontWeight: 'normal',
            fontSize: 16
        }
    }],
    tooltip: {
        trigger: 'item',
        formatter: "{b}: {c} ({d}%)"
    },
    legend: {
        bottom: 10,
        left: 'center',
        data: []
    },
    series: [{
        type: 'pie',
        selectedMode: 'single',
        radius: ['40%', '75%'],
        //color: ['#AF89D6', '#59ADF3', '#FF999A', '#FFCC67','#FCC667','#CC5962'],

        label: {
            normal: {
                position: 'inside', //标签的位置
                formatter: '{b}\n{d}%'

            }
        },
        labelLine: {
            normal: {
                show: false
            }
        },
        data: []
    }]
};

option2 = {
    title: [{
        text: '计租面积占比',
        x: 'center',
        top: '2%',
        textBaseline: 'middle',
        textStyle: {
            color: '#666',
            fontWeight: 'normal',
            fontSize: 16
        }
    }],
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)",
    },
    legend: {
        bottom: 10,
        left: 'center',
        data: []
    },
    series: [
        {name: '计租面积占比',
            type:'pie',
            hoverAnimation: false,
            legendHoverLink:false,
            radius: ['30%', '32%']
            //color: ['#915872', '#3077b7', '#9a8169', '#3f8797','#5b8144','#307889','#9c6a79']
            ,
            label: {
                normal: {
                    position: 'inner'
                }
            },
            labelLine: {
                normal: {
                    show: false
                },
            },
            data:[]
        },
        {
            type:'pie',
            radius: ['32%', '75%'],
            //color: ['#d74e67', '#0092ff', '#eba954', '#21b6b9','#60a900','#01949b',' #f17677'],
            label: {
                normal: {
                    position: 'inside', //标签的位置
                    formatter: '{b}\n{d}%'
                },

            },
            data:[
            ]
        }
    ]
};

option3 = {
    title: [{
        text: '使用面积总和',
        x: 'center',
        top: '2%',
        textBaseline: 'middle',
        textStyle: {
            color: '#666',
            fontWeight: 'normal',
            fontSize: 16
        }
    }],
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
    },
    legend: {
        bottom: 10,
        left: 'center',
        data: []
    },
    //color: ['#fd9173', '#f36f8a', '#5f71d2', '#42a4eb', '#4ac7f5'],
    series: [{
        name: '使用面积总和',
        type: 'pie',
        clockwise: false, //饼图的扇区是否是顺时针排布
        minAngle: 20, //最小的扇区角度（0 ~ 360）
        center: ['50%', '50%'], //饼图的中心（圆心）坐标
        radius: ['30%', '75%'], //饼图的半径
        avoidLabelOverlap: true, ////是否启用防止标签重叠
        itemStyle: { //图形样式
            normal: {
                borderColor: '#1e2239',
                borderWidth: 1.5,
            },
        },
        label: { //标签的位置
            normal: {
                show: true,
                position: 'inside', //标签的位置
                formatter: "{b}\n{d}%",
                textStyle: {
                    color: '#fff',
                }
            },
            emphasis: {
                show: true,
                textStyle: {
                    fontWeight: 'bold'
                }
            }
        },
        data: [
        ]
    }, {
        name: '',
        type: 'pie',
        clockwise: false,
        silent: true,
        minAngle: 20, //最小的扇区角度（0 ~ 360）
        center: ['50%', '50%'], //饼图的中心（圆心）坐标
        radius: ['0%', '27%'], //饼图的半径
        itemStyle: { //图形样式
            normal: {
                borderColor: '#1e2239',
                borderWidth: 1.5,
                opacity: 0.21,
            }
        },
        label: { //标签的位置
            normal: {
                show: false,
            }
        },
        data: [
        ]
    }]
};

option4 = {
    title: {
        text: '单店平均计租面积',
        x:'center'
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow'
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        top:'8%',
        containLabel: true
    },
    xAxis: {
        type: 'value',
        boundaryGap: [0, 0.01]
    },
    yAxis: {
        type: 'category',
        data: []
    },
    series: [
        {
            name: '',
            type: 'bar',
            data: []
        }
    ]
};
option5 = {
    title: {
        text: '单店平均使用面积',
        x:'center'
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow'
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        top:'8%',
        containLabel: true
    },
    xAxis: {
        type: 'value',
        boundaryGap: [0, 0.01]
    },
    yAxis: {
        type: 'category',
        data: []
    },
    series: [
        {
            name: '',
            type: 'bar',
            data: []
        }
    ]
};
option6 = {
    title: {
        text: '计租面积平均使用率',
        x:'center'
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow'
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        top:'8%',
        containLabel: true
    },
    xAxis: {
        type: 'value',
        boundaryGap: [0, 0.01]
    },
    yAxis: {
        type: 'category',
        data: []
    },
    series: [
        {
            name: '',
            type: 'bar',
            data: []
        }
    ]
};
option7 = {
    title: {
        text: '计租面积平均单价',
        x:'center'
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow'
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        top:'8%',
        containLabel: true
    },
    xAxis: {
        type: 'value',
        boundaryGap: [0, 0.01]
    },
    yAxis: {
        type: 'category',
        data: []
    },
    series: [
        {
            name: '',
            type: 'bar',
            data: []
        }
    ]
};
option8 = {
    title: {
        text: '使用面积平均单价',
        x:'center'
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow'
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        top:'8%',
        containLabel: true
    },
    xAxis: {
        type: 'value',
        boundaryGap: [0, 0.01]
    },
    yAxis: {
        type: 'category',
        data: []
    },
    series: [
        {
            name: '',
            type: 'bar',
            data: []
        }
    ]
};
option9 = {
    title: [{
        text: '中介成交店铺数量占比',
        x: 'center',
        top: '2%',
        textBaseline: 'middle',
        textStyle: {
            color: '#666',
            fontWeight: 'normal',
            fontSize: 16
        }
    }],
    tooltip: {
        trigger: 'item',
        formatter: "{b}: {c} ({d}%)"
    },
    legend: {
        bottom: 10,
        left: 'center',
        data: []
    },
    series: [{
        type: 'pie',
        selectedMode: 'single',
        radius: ['40%', '75%'],
        //color: ['#AF89D6', '#59ADF3', '#FF999A', '#FFCC67','#FCC667','#CC5962'],

        label: {
            normal: {
                position: 'inside', //标签的位置
                formatter: '{b}\n{d}%'

            }
        },
        labelLine: {
            normal: {
                show: false
            }
        },
        data: []
    }]
};
option10 = {
    title: [{
        text: '中介费金额占比',
        x: 'center',
        top: '2%',
        textBaseline: 'middle',
        textStyle: {
            color: '#666',
            fontWeight: 'normal',
            fontSize: 16
        }
    }],
    tooltip: {
        trigger: 'item',
        formatter: "{b}: {c} ({d}%)"
    },
    legend: {
        bottom: 10,
        left: 'center',
        data: []
    },
    series: [{
        type: 'pie',
        selectedMode: 'single',
        radius: ['40%', '75%'],
        //color: ['#AF89D6', '#59ADF3', '#FF999A', '#FFCC67','#FCC667','#CC5962'],

        label: {
            normal: {
                position: 'inside', //标签的位置
                formatter: '{b}\n{d}%'

            }
        },
        labelLine: {
            normal: {
                show: false
            }
        },
        data: []
    }]
};
option11 = {
    title: {
        text: '平均单店中介费',
        x:'center'
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow'
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        top:'8%',
        containLabel: true
    },
    xAxis: {
        type: 'value',
        boundaryGap: [0, 0.01]
    },
    yAxis: {
        type: 'category',
        data: []
    },
    series: [
        {
            name: '',
            type: 'bar',
            data: []
        }
    ]
};
option12 = {
    title: [{
        text: '付款方式',
        x: 'center',
        top: '2%',
        textBaseline: 'middle',
        textStyle: {
            color: '#666',
            fontWeight: 'normal',
            fontSize: 16
        }
    }],
    tooltip: {
        trigger: 'item',
        formatter: "{b}: {c} ({d}%)"
    },
    legend: {
        bottom: 10,
        left: 'center',
        data: []
    },
    series: [{
        type: 'pie',
        selectedMode: 'single',
        radius: ['40%', '75%'],
        //color: ['#AF89D6', '#59ADF3', '#FF999A', '#FFCC67','#FCC667','#CC5962'],

        label: {
            normal: {
                position: 'inside', //标签的位置
                formatter: '{b}\n{d}%'

            }
        },
        labelLine: {
            normal: {
                show: false
            }
        },
        data: []
    }]
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




$(function(){
    initStoreData();
})
function initStoreData() {
    doManager("storeManager", "findStoreRentDataByCity",null,function(data, textStatus, XMLHttpRequest) {
        if (data.result) {
            var allData = JSON.parse(data.data);
            //全国月店数量数据分析(门店数量)
            var storeData=allData.storeData;//门店数据库查询数据
            var city_data = new Array();   //城市数组
            var store_math_data_array=new Array();//城市门店数量
            var store_jizu_area_array=new Array();//计租面积
            var store_jizu_area_array_notName=new Array();//计租面积
            var store_usable_area_array=new Array();//计租面积
            var unit_store_jizu_array=new Array();//单店平均计租面积图
            var unit_store_usable_array=new Array();//单店平均使用面积图
            var store_area_PCT_array=new Array();//计租面积平均使用率图
            var store_jizu_unit_price=new Array();//计租面积平均单价(图)
            var store_usable_unit_price=new Array();//使用面积平均单价(图)
            var store_zhongjie_array=new Array();//中介成交店铺数量占比(图)
            var store_zhongjie_money_array=new Array();//中介费金额占比（图）
            var store_zhongjie_unit_money_array=new Array();//单店平均中介费（图）
            var store_payment_method_array=new Array();//支付方式（图）

            var mothStoreData_str="";//门店数量字符串
            var store_jizu_str_table="";//计租面积表
            var store_usable_str_table="";//计租面积表
            var unit_store_jizu_str="";//单店平均计租面积
            var unit_store_usable_str="";//单店平均使用面积
            var store_area_PCT_str="";//计租面积平均使用率
            var store_jizu_unit_price_str="";//计租面积平均单价(表)
            var store_usable_unit_price_str="";//使用面积平均单价(表)
            var store_zhongjie_str="";//中介成交店铺数量占比(表)
            var store_zhongjie_money_str="";//中介费金额占比（表）
            var store_zhongjie_unit_money_str="";//单店平均中介费（表）
            var store_payment_method_str="";//支付方式（表）
            for(var a=0;a<storeData.length;a++){
                //门店数量表
                mothStoreData_str+="<tr><td>"+storeData[a].city_name+"</td>" +
                    "<td>"+storeData[a].city_total+"</td>" +
                    "<td>"+storeData[a].store_PCT+"%</td></tr>";
                //给门店饼图赋值
                city_data[a]=storeData[a].city_name;
                var obj={"value":storeData[a].city_total,"name":storeData[a].city_name};
                store_math_data_array[a]=obj;

                //计租面积表
                store_jizu_str_table+="<tr><td>"+storeData[a].city_name+"</td>" +
                    "<td>"+storeData[a].jizu_area+"</td>" +
                    "<td>"+storeData[a].jizu_PCT+"%</td></tr>";
                //计租面积饼图
                var obj={"value":storeData[a].jizu_area,"name":storeData[a].city_name};
                var obj1={"value":storeData[a].jizu_area,"name":""}
                store_jizu_area_array[a]=obj;
                store_jizu_area_array_notName[a]=obj1;
                //使用面积表
                store_usable_str_table+="<tr><td>"+storeData[a].city_name+"</td>" +
                    "<td>"+storeData[a].usable_area+"</td>" +
                    "<td>"+storeData[a].usable_PCT+"%</td></tr>";
                //使用面积饼图
                var obj={"value":storeData[a].usable_area,"name":storeData[a].city_name};
                store_usable_area_array[a]=obj;
                //单店平均计租面积
                unit_store_jizu_str+="<tr><td>"+storeData[a].city_name+"</td><td>"+storeData[a].jizu_avg_area+"</td></tr>";
                //单店平均计租面积(柱形图)
                unit_store_jizu_array[a]=storeData[a].jizu_avg_area;
                //单店平均使用面积
                unit_store_usable_str+="<tr><td>"+storeData[a].city_name+"</td><td>"+storeData[a].usable_avg_area+"</td></tr>";
                //单店平均使用面积柱形图
                unit_store_usable_array[a]=storeData[a].usable_avg_area;
                //计租面积平均使用率(表)
                store_area_PCT_str+="<tr><td>"+storeData[a].city_name+"</td><td>"+storeData[a].store_avg_PCT+"%</td></tr>";
                //计租面积平均使用率(图)
                store_area_PCT_array[a]=storeData[a].store_avg_PCT;
                //计租面积平均单价(表)
                store_jizu_unit_price_str+="<tr><td>"+storeData[a].city_name+"</td><td>"+storeData[a].city_avg_rental_jizu+"</td></tr>";
                //计租面积平均单价(图)
                store_jizu_unit_price[a]=storeData[a].city_avg_rental_jizu;
                //使用面积平均单价(表)
                store_usable_unit_price_str+="<tr><td>"+storeData[a].city_name+"</td><td>"+storeData[a].city_avg_rental_usable+"</td></tr>";
                //使用面积平均单价(图)
                store_usable_unit_price[a]=storeData[a].city_avg_rental_usable;
                //中介成交店铺数量(表)
                store_zhongjie_str+="<tr><td>"+storeData[a].city_name+"</td><td>"+storeData[a].store_zhongjie+"</td></tr>";
                //中介成交店铺数量占比(图)
                var obj={"value":storeData[a].store_zhongjie,"name":storeData[a].city_name};
                store_zhongjie_array[a]=obj;
                //中介费总额(表)
                store_zhongjie_money_str+="<tr><td>"+storeData[a].city_name+"</td><td>"+storeData[a].agency_fee_total+"</td></tr>";
                //中介费总额占比(图)
                var obj={"value":storeData[a].agency_fee_total,"name":storeData[a].city_name};
                store_zhongjie_money_array[a]=obj;
                //单店平均中介费(表)
                store_zhongjie_unit_money_str+="<tr><td>"+storeData[a].city_name+"</td><td>"+storeData[a].store_price+"</td></tr>";
                //单店平均中介费(图)
                var obj={"value":storeData[a].store_price,"name":storeData[a].city_name};
                store_zhongjie_unit_money_array[a]=obj;
            }

            //给门店数量表赋值
            mothStoreData_str+="<tr class='text-red'><th>总计:</th><th>"+allData.totalData.china_store_total+"</th><th>100%</th></tr>";
            $("#city_store_yue").append(mothStoreData_str);
            //全国月店数量数据分析(门店数量饼图)
            option1.legend.data=city_data;
            option1.series[0].data=store_math_data_array;
            myChart1.setOption(option1);
            //给门店计租面积表赋值
            store_jizu_str_table+="<tr class='text-red'><th>总计:</th><th>"+allData.totalData.china_jizu_total+"</th><th>100%</th></tr>";
            $("#store_jizu_table").append(store_jizu_str_table);
            //全国月店数量数据分析(计租面积饼图)
            option2.legend.data=city_data;
            option2.series[0].data=store_jizu_area_array_notName;
            option2.series[1].data=store_jizu_area_array;
            myChart2.setOption(option2);
            //给门店使用面积表赋值
            store_usable_str_table+="<tr class='text-red'><th>总计:</th><th>"+allData.totalData.china_usable_total+"</th><th>100%</th></tr>";
            $("#store_usable_table").append(store_usable_str_table);
            //全国月店数量数据分析(使用面积饼图)
            option3.legend.data=city_data;
            option3.series[0].data=store_usable_area_array;
            option3.series[1].data=store_usable_area_array;
            myChart3.setOption(option3);
            //给单店平均计租面积表赋值
            unit_store_jizu_str+="<tr class='text-red'><th>全国平均:</th><th>"+allData.totalData.china_avg_jizu+"</th></tr>"
            $("#unit_store_jizu").append(unit_store_jizu_str);
            //单店平均计租面积(柱状图)
            var unit_city_data=city_data;
            unit_city_data[storeData.length]="全国平均";
            unit_store_jizu_array[storeData.length]=allData.totalData.china_avg_jizu;
            option4.yAxis.data=unit_city_data;
            console.log(unit_store_jizu_array);
            option4.series[0].data=unit_store_jizu_array;
            myChart4.setOption(option4);
            //给单店平均使用面积表赋值
            unit_store_usable_str+="<tr class='text-red'><th>全国平均:</th><th>"+allData.totalData.china_avg_usable+"</th></tr>"
            $("#unit_store_usable").append(unit_store_usable_str);
            //单店平均使用面积(柱状图)
            unit_store_usable_array[storeData.length]=allData.totalData.china_avg_usable;
            option5.yAxis.data=unit_city_data;
            option5.series[0].data=unit_store_usable_array;
            myChart5.setOption(option5);

            //计租面积平均使用率
            store_area_PCT_str+="<tr class='text-red'><th>全国平均:</th><th>"+allData.totalData.china_avg_PCT+"%</th></tr>"
            $("#store_jizu_PCT").append(store_area_PCT_str);
            //单店平均使用面积(柱状图)
            store_area_PCT_array[storeData.length]=allData.totalData.china_avg_PCT;
            option6.yAxis.data=unit_city_data;
            option6.series[0].data=store_area_PCT_array;
            myChart6.setOption(option6);
            //计租面积平均单价（表）
            store_jizu_unit_price_str+="<tr class='text-red'><th>全国平均:</th><th>"+allData.totalData.china_avg_rental_jizu+"</th></tr>"
            $("#store_jizu_unit_price").append(store_jizu_unit_price_str);
            //计租面积平均单价(柱状图)
            store_jizu_unit_price[storeData.length]=allData.totalData.china_avg_rental_jizu;
            option7.yAxis.data=unit_city_data;
            option7.series[0].data=store_jizu_unit_price;
            myChart7.setOption(option7);
            //使用面积平均单价（表）
            store_usable_unit_price_str+="<tr class='text-red'><th>全国平均:</th><th>"+allData.totalData.china_avg_rental_usable+"</th></tr>"
            $("#store_usable_unit_price").append(store_usable_unit_price_str);
            //使用面积平均单价(柱状图)
            store_usable_unit_price[storeData.length]=allData.totalData.china_avg_rental_usable;
            option8.yAxis.data=unit_city_data;
            option8.series[0].data=store_usable_unit_price;
            myChart8.setOption(option8);

            //中介成交店铺数量（表）
            store_zhongjie_str+="<tr class='text-red'><th>总计:</th><th>"+allData.totalData.china_agent_store+"</th></tr>"
            $("#store_zhongjie").append(store_zhongjie_str);
            //中介成交店铺数量(图)
            option9.legend.data=city_data;
            option9.series[0].data=store_zhongjie_array;
            myChart9.setOption(option9);

            //中介费总额（表）
            store_zhongjie_money_str+="<tr class='text-red'><th>总计:</th><th>"+allData.totalData.china_agent_price+"</th></tr>"
            $("#store_zhongjie_money").append(store_zhongjie_money_str);
            //中介成交店铺数量(图)
            option10.legend.data=city_data;
            option10.series[0].data=store_zhongjie_money_array;
            myChart10.setOption(option10);
            //门店平均中介费（表）
            store_zhongjie_unit_money_str+="<tr class='text-red'><th>全国平均:</th><th>"+allData.totalData.china_avg_agent_price+"</th></tr>"
            $("#store_zhongjie_unit_money").append(store_zhongjie_unit_money_str);
            //门店平均中介费(柱状图)
            store_zhongjie_unit_money_array[storeData.length]=allData.totalData.china_avg_agent_price;
            option11.yAxis.data=unit_city_data;
            option11.series[0].data=store_zhongjie_unit_money_array;
            myChart11.setOption(option11);
            //支付方式(表)
            var mapPaymentData=allData.mapPaymentData;
            store_payment_method_str="<tr><td>月付:</td><td>"+mapPaymentData.mouthPayment+"</td></tr>" +
                "<tr><td>季付:</td><td>"+mapPaymentData.quarterlyPayment+"</td></tr>" +
                "<tr><td>半年付:</td><td>"+mapPaymentData.semiAnnualPayment+"</td></tr>" +
                "<tr><td>年付:</td><td>"+mapPaymentData.annualPayment+"</td></tr>";
            store_payment_method_str+="<tr class='text-red'><th>总计:</th><th>"+allData.totalData.china_store_total+"</th></tr>";
            $("#store_payment_method").append(store_payment_method_str);
            //支付方式(图)
            store_payment_method_array[0]={"value":mapPaymentData.mouthPayment,"name":"月付"};
            store_payment_method_array[1]={"value":mapPaymentData.quarterlyPayment,"name":"季付"}
            store_payment_method_array[2]={"value":mapPaymentData.semiAnnualPayment,"name":"半年付"}
            store_payment_method_array[3]={"value":mapPaymentData.annualPayment,"name":"年付"}
            var ccc=["月付","季付","半年付","年付"];
            option12.legend.data=ccc;
            option12.series[0].data=store_payment_method_array;
            myChart12.setOption(option12);
            var storePaymentData=allData.storePaymentData;
            var  storePaymentData_str="";
            for(var b=0;b<storePaymentData.length;b++){
                storePaymentData_str+="<tr><td>"+storePaymentData[b].city_name+"</td>" +
                    "<td>"+(parseInt(storePaymentData[b].mouthPayment)+parseInt(storePaymentData[b].quarterlyPayment)+parseInt(storePaymentData[b].semiAnnualPayment)+parseInt(storePaymentData[b].annualPayment))+"</td>" +
                    "<td>"+storePaymentData[b].annualPayment+"</td>" +
                    "<td>"+storePaymentData[b].semiAnnualPayment+"</td>" +
                    "<td>"+storePaymentData[b].quarterlyPayment+"</td>" +
                    "<td>"+storePaymentData[b].mouthPayment+"</td></tr>";
            }
            $("#store_payment_method_info").append(storePaymentData_str);

            //全国星店数据分析
            var starStoreData_str="";
            var starStoreData=allData.starStoreData;
            var star_total=0;
            var star_jizu_area_total=0;
            var star_jizu_area_unit=0;
            var star_jizu_avg_rental=0;
            var star_usable_area_total=0;
            var star_usable_area_unit=0;
            var star_usable_avg_rental=0;
            for(var a=0;a<starStoreData.length;a++){
                 star_total+=parseInt(starStoreData[a].city_count);
                 star_jizu_area_total+=parseFloat(starStoreData[a].jizu_area_total);
                 star_jizu_area_unit+=parseFloat(starStoreData[a].jizu_area_avg);
                 star_jizu_avg_rental+=parseFloat(starStoreData[a].jizu_area_rental);
                 star_usable_area_total+=parseFloat(starStoreData[a].usable_area_total);
                 star_usable_area_unit+=parseFloat(starStoreData[a].usable_area_avg);
                 star_usable_avg_rental+=parseFloat(starStoreData[a].usable_area_rental);
                starStoreData_str+=("<tr><td>"+starStoreData[a].city_name+"</td><td>"+starStoreData[a].city_count+"</td>" +
                    "<td>"+starStoreData[a].jizu_area_total+"</td><td>"+starStoreData[a].jizu_area_avg+"</td>" +
                    "<td>"+starStoreData[a].jizu_area_rental+"</td><td>"+starStoreData[a].usable_area_total+"</td>" +
                    "<td>"+starStoreData[a].usable_area_avg+"</td><td>"+starStoreData[a].usable_area_rental+"</td></tr>");
            }
            starStoreData_str+="<tr class='bg-light-blue'><th>总计/平均值</th><th>"+star_total+"</th>" +
            "<th>"+star_jizu_area_total.toFixed(2)+"</th><th>"+parseFloat(star_jizu_area_unit/starStoreData.length).toFixed(2)+"</th>" +
                "<th>"+parseFloat(star_jizu_avg_rental/starStoreData.length).toFixed(2)+"</th><th>"+star_usable_area_total.toFixed(2)+"</th>" +
                "<th>"+parseFloat(star_usable_area_unit/starStoreData.length).toFixed(2)+"</th><th>"+parseFloat(star_usable_avg_rental/starStoreData.length).toFixed(2)+"</th></tr>"
            $("#star_store_analyze").append(starStoreData_str);
            var starStoreData_str_ss="";
            var starStoreData1=allData.starStoreInfo;
            for(var a=0;a<starStoreData1.length;a++){
                starStoreData_str_ss+=("<tr><td>"+starStoreData1[a].county_name+"</td><td>"+starStoreData1[a].town_name+"</td>" +
                "<td>"+starStoreData1[a].rent_area+"</td><td>"+starStoreData1[a].usable_area+"</td>" +
                "<td>"+starStoreData1[a].use_PRC+"</td><td>"+starStoreData1[a].rental+"</td>" +
                "<td>"+starStoreData1[a].usable_rental+"</td><td>"+starStoreData1[a].agency_fee+"</td>" +
                "<td>"+starStoreData1[a].payment_method+"</td><td>"+starStoreData1[a].rent_free+"</td>" +
                "</tr>");
            }
            $("#star_store_info").append(starStoreData_str_ss);


            //全国校园店数据分析
            var schoolStoreData_str="";
            var schoolStoreData=allData.schoolStoreData;
            var school_total=0;
            var school_jizu_area_total=0;
            var school_jizu_area_unit=0;
            var school_jizu_avg_rental=0;
            var school_usable_area_total=0;
            var school_usable_area_unit=0;
            var school_usable_avg_rental=0;
            for(var a=0;a<schoolStoreData.length;a++){
                school_total+=parseInt(schoolStoreData[a].city_count);
                school_jizu_area_total+=parseFloat(schoolStoreData[a].jizu_area_total);
                school_jizu_area_unit+=parseFloat(schoolStoreData[a].jizu_area_avg);
                school_jizu_avg_rental+=parseFloat(schoolStoreData[a].jizu_area_rental);
                school_usable_area_total+=parseFloat(schoolStoreData[a].usable_area_total);
                school_usable_area_unit+=parseFloat(schoolStoreData[a].usable_area_avg);
                school_usable_avg_rental+=parseFloat(schoolStoreData[a].usable_area_rental);
                schoolStoreData_str+=("<tr><td>"+schoolStoreData[a].city_name+"</td><td>"+schoolStoreData[a].city_count+"</td>" +
                "<td>"+schoolStoreData[a].jizu_area_total+"</td><td>"+schoolStoreData[a].jizu_area_avg+"</td>" +
                "<td>"+schoolStoreData[a].jizu_area_rental+"</td><td>"+schoolStoreData[a].usable_area_total+"</td>" +
                "<td>"+schoolStoreData[a].usable_area_avg+"</td><td>"+schoolStoreData[a].usable_area_rental+"</td></tr>");
            }
            schoolStoreData_str+="<tr class='bg-light-blue'><th>总计/平均值</th><th>"+school_total+"</th>" +
                "<th>"+school_jizu_area_total.toFixed(2)+"</th><th>"+parseFloat(school_jizu_area_unit/schoolStoreData.length).toFixed(2)+"</th>" +
                "<th>"+parseFloat(school_jizu_avg_rental/schoolStoreData.length).toFixed(2)+"</th><th>"+school_usable_area_total.toFixed(2)+"</th>" +
                "<th>"+parseFloat(school_usable_area_unit/schoolStoreData.length).toFixed(2)+"</th><th>"+parseFloat(school_usable_avg_rental/schoolStoreData.length).toFixed(2)+"</th></tr>"
            $("#school_store_analyze").append(schoolStoreData_str);
            var schoolStoreData_str_ss="";
            var schoolStoreData1=allData.schoolStoreInfo;
            for(var a=0;a<schoolStoreData1.length;a++){
                schoolStoreData_str_ss+=("<tr><td>"+schoolStoreData1[a].county_name+"</td><td>"+schoolStoreData1[a].town_name+"</td>" +
                "<td>"+schoolStoreData1[a].rent_area+"</td><td>"+schoolStoreData1[a].usable_area+"</td>" +
                "<td>"+schoolStoreData1[a].use_PRC+"</td><td>"+schoolStoreData1[a].rental+"</td>" +
                "<td>"+schoolStoreData1[a].usable_rental+"</td><td>"+schoolStoreData1[a].agency_fee+"</td>" +
                "<td>"+schoolStoreData1[a].rent_free+"</td><td>"+schoolStoreData1[a].payment_method+"</td>" +
                "</tr>");
            }
            $("#school_store_info").append(schoolStoreData_str_ss);





            
        }
    },false);
}








