var pageStatusInfo = {};
var mapChart;
var statisticExtendInfo;
var timerId;
var refreshId;
// 城市排名(GMV)柱状图
var cityRankChartGmv;
var cityRankGmvOption;
//近7日门店毛利散点图
var cityProfitRangeChart;
var cityProfitRangeOption;
//城市排名(GMV)矩形图
var cityUserGMV;
var cityUserOption;
// 门店排名(订单量)排名柱状图
var storeRankChartOrder;
var storeRankOrderOption;
// 门店排名(GMV)排名柱状图
var storeRankChartGmv;
var storeRankGmvOption;
var guoanManRankChartGMV;
// 柱状图属性：国安侠(GMV)排名
var guoanManRankGMVOption;
//事业群排名(GMV)排名柱状图
var businessDepRankChartGMV;
var businessDepRankGMVOption;
//商品销售云图
var commodityRankChartGMV;
var commodityRankOption;
//拉新用户,消费用户图
var customerNewChart;
var customerNewChartOption;
//本周客流分析
var turnoverCustomerOrderChart;
var turnoverCustomerOrderOption;
var openedProvinceMunicipality;
var openedCity;
var timer_china_beat;//城市闪动的定时
var timer_china_beat2;//城市大图闪动的定时
var fullScreenChart;
var beatData;
var screenlogin=getUrlParamByKey("su");
layer.config({
	  extend: 'skin/crmskin/style.css' //加载新皮肤
  });
function loginShow(){
	
	if(screenlogin!=null&&screenlogin!=''&&screenlogin!=undefined){
        var b = new Base64();
        screenlogin=b.decode(screenlogin);
        var obj=JSON.parse(screenlogin);
		var reObj = new PMSRequestObject("userManager", "isAppScreenUser", [obj.code,obj.employeeId,obj.password]);
	    var callback = function callback(data, textStatus, XMLHttpRequest) {
	    	localStorage.clear();
	    	window.parent.location=getRootPath() + "/crm/index_headquarters.html";
		};
	    var failureCallback = function failureCallback(data, textStatus, XMLHttpRequest) {
			alert("登录信息错误，请确认输入或联系管理员!");
			return false;
		}
	    var url = "../login.do";
	    $$.ajax(url, "requestString=" + reObj.toJsonString(), callback, failureCallback); 
	}
	
}
$(document).ready(function () {
	//获取屏幕宽度(设置当日营业额显示样式)
	getScreenWidth();
	//获得闪图数据
	getBeatJson();
	//首次进入清除缓存
	clearFirstCache();
	loginShow();
	//鼠标放概要统计展开
	showMoreSummaryStatistics();
	getStoreKindsNumber();
	//页面长期打开的情况下,早晨7点定时刷新
	startRefreshPage();
    var startTime = new Date().getTime();
    // 获取请求参数
    var requestParameters = getReauestParameters();
    //console.log('\trequest parameter.');
    //console.log(requestParameters);
    // 初始化页面元素
    initPageElements();
	//设置当前登陆人
	initcurruser();
    // 设置页面初始显示参数
    pageStatusInfo = initPageStatusInfo(requestParameters);
	//菜单根据权限是否显示
	menuShowByRole();
    //console.log('\tinit page parameter');
    //console.log(pageStatusInfo);
    // 显示页面统计数据
    showPageContent(pageStatusInfo);
    // 地图事件绑定处理
    bindMapEvents();
    initSwiper();
    // 解除装载状态
    setLoadingMark(false);
    //console.log('show whole page in ' + (new Date().getTime() - startTime) + ' millisecond');
    initClick();
    //点击大屏幕数据进入到监控页
    // $(".data_new").click(function(){
    // 	gotolive();
    // });
});

const CACHE_HEADER_STATISTIC = 'statistic_';
const CACHE_HEADER_HISTORY_DATA = 'historyData_';
const CACHE_HEADER_OPEN_CARD_USER = 'openCardUser_';
const CACHE_HEADER_CUSTOMER_COUNT_DATA = 'customerData_';
const CACHE_HEADER_CITY_RANK_GVM = 'cityRankGmv_';
const CACHE_HEADER_PROFIT_STORE = 'cityProfitStore_';
const CACHE_HEADER_TWOTWOONE_RANK_GVM = 'twoTwoOneRankGmv_';
const CACHE_HEADER_STORE_RANK_GMV = 'storeRankGmv_';
const CACHE_HEADER_STORE_RANK_ORDER = 'storeRankOrder_';
const CACHE_HEADER_GUOAN_MAN_RANK_GMV = 'guoanManRankGmv_';
const CACHE_HEADER_COMMODITY_RANK = 'commodityRank_';
const CACHE_HEADER_CHANNEL_RANK_GMV = 'channelRankGmv_';
const CACHE_HEADER_CHANNEL_RANK_ORDER = 'channelRankOrder_';
const CACHE_HEADER_BUSINESS_DEP_RANK_GMV = 'businessDepRankGmv_';
const CACHE_HEADER_CUSTOMER_CONSUME_MONTH = 'customerConsume_';
const CACHE_HEADER_TURNOVER_CUSTOMER_MONTH = 'turnoverCustomer_';
const CACHE_HEADER_STORE_SERVICE_RANGE = 'storeServiceRange_';
const CACHE_HEADER_CITYUSER_RANK = 'cityUserRank_';
const SHOW_LEVEL_CHINA = 'china';
const SHOW_LEVEL_PROVINCE = 'province';
const SHOW_LEVEL_CITY = 'city';

// 显示页面
var showPageContent = function (pageStatusInfo) {
    clearInterval(timerId);
    //切换门店种类个数
    getStoreKindsNumber();
	//获得上月某日订单量和用户量
    getLastMonthOrderCustomerCount(pageStatusInfo);
    // 显示历史数据
    getHistoryData(pageStatusInfo);
    // 显示本月新增开卡用户和历史总社员数
    getOpenCardUser(pageStatusInfo);
    //查询当月毛利
    showsumofcurmonthprofit(pageStatusInfo);
    //查询昨日毛利
    showsumofYesterdayprofit(pageStatusInfo);
    getDailyData();
    // 显示统计概要
    pageStatusInfo.currentPage=1;
    getStatisticInfo(pageStatusInfo);

    // 显示地图
    showMap(pageStatusInfo);
    // 显示门店毛利订单量近7日散点图
    getProfitRangeForStoreWeek(pageStatusInfo);

    // 显示城市排名(GMV)-修改为近七日GMV走势图
    //getCityRankDataGmv(pageStatusInfo);
    
    // 显示近七日221GMV走势
    getTwoTwoOneRankGmv(pageStatusInfo);
    
    // 显示门店排名(GMV)
    getStoreRankDataGmv(pageStatusInfo);

    // 显示门店排名(新用户量)-->显示门店排名(消费用户量)
    getStoreRankDataOrder(pageStatusInfo);

    // 显示国安侠排名(GMV)
    getGuoanManRankDataGMV(pageStatusInfo);

    // 显示商品排名(销量)
    getCommodityRankData(pageStatusInfo);

    // 显示频道排名(GMV)
    getChannelRankGmvData(pageStatusInfo);

    // 显示频道排名(订单量)
    //getChannelRankOrderData(pageStatusInfo);

    // 显示事业群排名
    getBusinessDepRankDataGmv(pageStatusInfo);
    
    //显示拉新消费用户
    //showCustomerNew();
    getCustomerNewAndConsumeMonthUser(pageStatusInfo);

	//显示本周客流分析
	//showTurnoverCustomerOrder();
	getTurnoverCustomerOrder(pageStatusInfo);
	
	//显示城市用户分布
	//showCityUser();
	getCityUser(pageStatusInfo);
	
    // 设置导航
    showCrumbs();
    //获得昨日门店毛利
    getYesterdayStorePorfit(pageStatusInfo);
    //获得近7日门店毛利
    getStorePorfitForSevenday(pageStatusInfo);
    //获得近30日门店毛利
    getStorePorfitForThirtyday(pageStatusInfo);
    //查询昨日门店社员开卡数
    getStoreYesterdayMember(pageStatusInfo);
    //查询近7日门店社员开卡数
    getStoreSevendayMember(pageStatusInfo);
    //查询近30日门店社员开卡数
    getStoreThirtydayMember(pageStatusInfo);
    //查询昨日门店商品销售排名
    getYesterdayStoreProduct(pageStatusInfo);
    //查询近7日门店商品销售排名
    getStoreProductSevenDay(pageStatusInfo);
    //查询近30日门店商品销售排名
    getStoreProductThirtyDay(pageStatusInfo);
    
};
// 设置页面初始状态信息
var initPageStatusInfo = function (reqData) {
    // 复制请求参数
    var pageStatusInfo = jQuery.extend(true, {}, reqData);
    // 判定页面显示级别&设置导航路径
    if (!reqData.cityId && !reqData.provinceId) {
        pageStatusInfo['showLevel'] = SHOW_LEVEL_CHINA;
    } else if (reqData.cityId) {
        pageStatusInfo['showLevel'] = SHOW_LEVEL_CITY;;
    } else if (reqData.provinceId) {
        pageStatusInfo['showLevel'] = SHOW_LEVEL_PROVINCE
    }
    // 设置系统时间
    setCurrentDate(pageStatusInfo);
    $.fn.extend(pageStatusInfo, {
        getCacheKey : function () {
            if (this.showLevel == SHOW_LEVEL_CHINA) {
                return SHOW_LEVEL_CHINA;
            } else if (this.showLevel == SHOW_LEVEL_PROVINCE) {
                return 'province_' + pageStatusInfo.provinceId;
            } else  if (this.showLevel == SHOW_LEVEL_CITY) {
                return 'city_' + pageStatusInfo.cityId;
            }
        }
    });
    return pageStatusInfo;
}
// 初始化页面元素
var initPageElements = function () {
    // 初始化地图
    mapChart = echarts.init(document.getElementById('main9'));
    // 初始化城市排名
    cityRankChartGmv = echarts.init(document.getElementById('main1'));
    // 初始化近7日门店散点图
    cityProfitRangeChart = echarts.init(document.getElementById('main13'));
    //社员7日走势
    turnoverCustomerOrderChart = echarts.init(document.getElementById('main2'));
    //初始化城市排名矩形图
    cityUserGMV = echarts.init(document.getElementById('main12'));
    // 初始化门店(GMV)排名显示图
    storeRankChartGmv = echarts.init(document.getElementById('main3'));
    // 初始化门店(订单量)排名显示图
    storeRankChartOrder = echarts.init(document.getElementById('main7'));
    // 初始化事业群排名(GMV)显示图
	businessDepRankChartGMV = echarts.init(document.getElementById('main4'));
	guoanManRankChartGMV = echarts.init(document.getElementById('main5'));
	 // 初始化城市商品排名显示图
    commodityRankChart = echarts.init(document.getElementById('main6'));
	cityRankGmvOption = {
    title:[
      //{text:"城市排名（GMV:元）",x: '2%', y: '0%',textStyle:{color:"#efefef",fontSize:"16"}},
      {x: '2%', y: '0%',textStyle:{color:"#efefef",fontSize:"16"}},
    ],
    legend: {
            show: 'true',
            data: [],
            right: '3%',
            top: '6%',
     },
    tooltip : {
      trigger: 'axis',
      position: function (point, params, dom, rect, size) {
        // 固定在顶部
        return [point[0], '10%'];
      },
      formatter:function(params){//数据格式
            var relVal = params[0].name+"<br/>";
            relVal += params[0]['marker']+params[0]['seriesName']+ ' : ' + changeMoneyByDigit(String(params[0]['value']),1);
            if(params.length>1){
            	for(var i=0;i<params.length-1;i++){
            		relVal += "<br/>"+params[i+1]['marker']+params[i+1]['seriesName']+ ' : ' + changeMoneyByDigit(String(params[i+1]['value']),1);
            	}
            }
            return relVal;
       }
    },
    calculable : true,
    grid: {
      left: '3%',
      right: '5%',
      top: '19%',
      height: 280, //设置grid高度
      containLabel: true
    },
    xAxis : [
      {
        type : 'category',
        data : ['社区一','上海上海','天津天','社区一','上海上海','天津天'],
        boundaryGap : false,
       // max:4,
        axisLine: {
          lineStyle: {
            color: '#ccc'
          }
        },
        /*axisLabel:{
          "show": true,
          interval: 1,
          //rotate:45,//倾斜度 -90 至 90 默认为0
          textStyle:{
            color:"#fff"
          }
        }*/
        axisLabel: {
          interval: 0,
          textStyle:{
            color:"#fff"
          }
          /*
          formatter:function(value)
          {
            //debugger
            var ret = "";//拼接加\n返回的类目项
            var maxLength = 3;//每项显示文字个数
            var valLength = value.length;//X轴类目项的文字个数
            var rowN = Math.ceil(valLength / maxLength); //类目项需要换行的行数
            if (rowN > 1)//如果类目项的文字大于3,
            {
              for (var i = 0; i < rowN; i++) {
                var temp = "";//每次截取的字符串
                var start = i * maxLength;//开始截取的位置
                var end = start + maxLength;//结束截取的位置
                //这里也可以加一个是否是最后一行的判断，但是不加也没有影响，那就不加吧
                temp = value.substring(start, end) + "\n";
                ret += temp; //凭借最终的字符串
              }
              return ret;
            }
            else {
              return value;
            }
          }
          */
        }

      }
    ],
    yAxis : [
      {
        splitLine: {show: false},
        axisLine: {
          lineStyle: {
            color: '#ccc'
          }
        },
        axisLabel: {
            margin: 2,
            formatter: function (value, index) {
            	var axisData = Math.abs(value);
                if (axisData >= 10000 && axisData < 10000000) {
                    value = value / 10000 + "万";
                } else if (axisData >= 10000000&&axisData < 100000000) {
                    value = value / 10000000 + "千万";
                }else if(axisData >= 100000000){
                	value = value / 100000000 + "亿";
                }
                return value;
            }
        }
      }
    ],
    dataZoom: [{
                  type: 'inside',
                  start:80,
                  end: 100
                 },
                 {
	               show: true,
	               type: 'slider',
	               y: '90%',
	               start: 8,
	               end: 30
                 }],
    series : [
      {
        cursor: 'default',
        //name:'一街坊、八街坊东、八街西、永定路社区社区部',
        name: '北京',
        type:'line',
        data:[],
        smooth: true,
        showAllSymbol: true,
        symbol: 'emptyCircle',
        symbolSize: 10,
        itemStyle : {  
            normal : {  
                color:'#FF346E',  //圈圈的颜色
                lineStyle:{  
                    color:'#FF346E'  //线的颜色
                } 
            }  
        },
        markPoint: {
          itemStyle: {
            normal: {
	             borderWidth: 1,            // 标注边线线宽，单位px，默认为1
	             label: {
                        show: true,
                        formatter: function(value) { 
						   return changeMoneyByDigit(value.value,1); 
						} 
	               }
	          }
          },
          data: [
            {type: 'max', name: '最大值'},
            {type: 'min', name: '最小值'}
          ]
        },
        /*'#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
           '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
           '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'*/
      },
      {
        cursor: 'default',
        //name:'一街坊、八街坊东、八街西、永定路社区社区部',
        name: '上海',        
        type:'line',
        data:[],
        smooth: true,
        showAllSymbol: true,
        symbol: 'emptyCircle',
        symbolSize: 10,
        itemStyle : {  
            normal : {  
                color:'#FF6B00',  //圈圈的颜色
                lineStyle:{  
                    color:'#FF6B00'  //线的颜色
                }  
            }
        },
        markPoint: {
          itemStyle: {
            normal: {
	             borderWidth: 1,            // 标注边线线宽，单位px，默认为1
	             label: {
                        show: true,
                        formatter: function(value) { 
						   return changeMoneyByDigit(value.value,1); 
						} 
	               }
	          }
          },
          data: [
            {type: 'max', name: '最大值'},
            {type: 'min', name: '最小值'}
          ]
        },

      },
      {
        cursor: 'default',
        //name:'一街坊、八街坊东、八街西、永定路社区社区部',
        name: '天津',        
        type:'line',
        data:[],
        smooth: true,
        showAllSymbol: true,
        symbol: 'emptyCircle',
        symbolSize: 10,
        itemStyle : {  
            normal : {  
                color:'#0135F7',  //圈圈的颜色
                lineStyle:{  
                    color:'#0135F7'  //线的颜色
                }  
            }
        },
        markPoint: {
          itemStyle: {
            normal: {
	             borderWidth: 1,            // 标注边线线宽，单位px，默认为1
	             label: {
                        show: true,
                        formatter: function(value) { 
						   return changeMoneyByDigit(value.value,1); 
						} 
	               }
	          }
          },
          data: [
            {type: 'max', name: '最大值'},
            {type: 'min', name: '最小值'}
          ]
        },

      }
    ]
  };
  cityProfitRangeOption = {
    //backgroundColor: '#e1e1e1',
    title: {
      text: "7日门店毛利分布",
      subtext: '圈的大小代表用户量',
      left: "10",
      y: "10",
      textStyle: {
        color: "rgba(255,255,255,0.8)"
      },
     // 副标题文本样式设置
     subtextStyle: {
      fontSize: 14,
      color: '#d9db1f'
      }
      
    },
    color: [
      "#0ad852","#4ed80e","#15bb86","#0ad896","#d9db1f","#d97520","#d63537"
    ],
    legend: [
     {
	      y: 'top',
	      top:'3%',
	      data: titleTH,
	      x:'center',
	      textStyle: {
	        color: 'rgba(255,255,255,0.8)',
	        fontSize: 16
	      }
      },
      {
	      y: 'top',
	      data: titleTH,
	      top:'7%',
	      x:'center',
	      textStyle: {
	        color: 'rgba(255,255,255,0.8)',
	        fontSize: 16
	      }
      }
    ],
    grid: {
      //x: '10%',
      //x2: 80,
      y: '28%',
      y2: '20%',
      bottom:'10%',
      left:'10%',
      top:'18%'
    },
    tooltip: {
      padding: 10,
      backgroundColor: '#222',
      borderColor: '#777',
      borderWidth: 1,
      formatter: function (obj) {
        var value = obj.value;
        return '<div style="border-bottom: 1px solid rgba(255,255,255,.3); font-size: 18px;padding-bottom: 7px;margin-bottom: 7px">'
          + '毛利：' + value[1]
          + '</div>'
          + schema[0].text + '：' + value[3] + '<br>'
          + schema[1].text + '：' + value[1] + '<br>'
          + schema[2].text + '：' + value[0] + '<br>'
          + schema[3].text + '：' + value[2] + '<br>'
          + schema[4].text + '：' + value[4] + '<br>'
          + schema[5].text + '：' + value[5] + '<br>';
      }
    },
    xAxis: {
      type: 'value',
      name: 'GMV',
      nameGap: 16,
      nameTextStyle: {
        color: 'rgba(255,255,255,0.8)',
        fontSize: 14
      },
      min: function(value){
  	   	 return parseInt(value.min);
  	  },
  	  max: function(value){
  	   	 return parseInt(value.max);
  	  },
      //max: 31,
      splitLine: {
        show: false
      },
      axisLine: {
        lineStyle: {
          color: 'rgba(255,255,255,0.8)'
        }
      }
    },
    yAxis: {
      type: 'value',
      name: '毛利',
      nameLocation: 'end',
      nameGap: 20,
      nameTextStyle: {
        color: 'rgba(255,255,255,0.8)',
        fontSize: 16
      },
      axisLine: {
        lineStyle: {
          color: 'rgba(255,255,255,0.8)'
        }
      },
      splitLine: {
        show: false
      }
    },
    /*visualMap: [
      {
        left: 'right',
        top: '10%',
        dimension: 2,
        min: 0,
        max: 250,
        itemWidth: 30,
        itemHeight: 150,
        calculable: true,
        precision: 0.1,
        text: ['圆形大小：PM2.5'],
        textGap: 30,
        textStyle: {
          color: 'rgba(255,255,255,0.8)'
        },
        inRange: {
          symbolSize: [10, 70]
        },
        outOfRange: {
          symbolSize: [10, 70],
          color: ['rgba(255,255,255,.2)']
        },
        controller: {
          inRange: {
            color: ['#c23531']
          },
          outOfRange: {
            color: ['rgba(255,255,255,0.8)']
          }
        }
      },
      {
        left: 'right',
        bottom: '5%',
        dimension: 6,
        min: 0,
        max: 50,
        itemHeight: 150,
        calculable: true,
        precision: 0.1,
        text: ['明暗：二氧化硫'],
        textGap: 30,
        textStyle: {
          color: 'rgba(255,255,255,0.8)'
        },
        inRange: {
          colorLightness: [1, 0.5]
        },
        outOfRange: {
          color: ['rgba(255,255,255,.2)']
        },
        controller: {
          inRange: {
            color: ['#c23531']
          },
          outOfRange: {
            color: ['#666']
          }
        }
      }
    ],*/
    series: [
      {
        name: '1',
        type: 'scatter',
        itemStyle: {
          normal: {
            opacity: 0.8,
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowOffsetY: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        },
        data: dataBJ,
        symbolSize: function (val) {
          	return val[0] * 0.13;
        },

      },
      {
        name: '2',
        type: 'scatter',
        itemStyle: {
          normal: {
            opacity: 0.8,
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowOffsetY: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        },
        data: dataSH,
        symbolSize: function (val) {
          return val[0] * 0.13;
        },

      },
      {
        name: '3',
        type: 'scatter',
        itemStyle: {
          normal: {
            opacity: 0.8,
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowOffsetY: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        },
        data: dataUH,
        symbolSize: function (val) {
          return val[0] * 0.13;
        },

      },
      {
        name: '4',
        type: 'scatter',
        itemStyle: {
          normal: {
            opacity: 0.8,
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowOffsetY: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        },
        data: dataVH,
        symbolSize: function (val) {
          return val[0] * 0.13;
        },

      },
      {
        name: '5',
        type: 'scatter',
        itemStyle: {
          normal: {
            opacity: 0.8,
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowOffsetY: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        },
        data: dataWH,
        symbolSize: function (val) {
          return val[0] * 0.13;
        },

      },
      {
        name: '6',
        type: 'scatter',
        itemStyle: {
          normal: {
            opacity: 0.8,
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowOffsetY: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        },
        data: dataXH,
        symbolSize: function (val) {
          return val[0] * 0.13;
        },

      },
      {
        name: '7',
        type: 'scatter',
        itemStyle: {
          normal: {
            opacity: 0.8,
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowOffsetY: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        },
        data: dataYH,
        symbolSize: function (val) {
          return val[0] * 0.13;
        },

      }

    ]
  };
  cityProfitRangeChart.on('click', function (params) {
	  	var dataArr = [];
	  	dataArr = params.data;
	  	var storename = dataArr[3];
	  	var storeno = dataArr[6];
	  	var dayTime = dataArr[5];
	  	var cityId = dataArr[7];
	  	var cityName = dataArr[4];
	  	dayTime = dayTime.replace(/-/g,"/");
	  	var redirectTag = "profit";
	  	var target=pageStatusInfo.targets; 
	    if(target==0){
			url = "dynamicData_profit_analysis.html?t="+encode64('0')+"&c="+encode64(cityId)+"&s=&e=&rt="+encode64(redirectTag)+"&time="+encode64(dayTime+"-"+dayTime)+"&so="+encode64(storeno)+"&sn="+encode64(storename)+"&cn="+encode64(cityName);
		}else if(target==1){
			url = "dynamicData_profit_analysis.html?t="+encode64('1')+"&c="+encode64(cityId)+"&s=&e=&rt="+encode64(redirectTag)+"&time="+encode64(dayTime+"-"+dayTime)+"&so="+encode64(storeno)+"&sn="+encode64(storename)+"&cn="+encode64(cityName);
		}
		window.open(url,"dynamicData_profit_analysis");
	});
  	// 客流分析
  turnoverCustomerOrderOption = {
    title: {
      text:"社员7日走势",x: '5%', y: '0%',textStyle:{color:"#efefef",fontSize:"16"},
    },
    tooltip : {
      trigger: 'axis',
      formatter:function(params)//数据格式
            {
            var relVal = params[0].name+"<br/>";
            if(params.length>1){
            	for(var i=0;i<params.reverse().length;i++){
            		relVal += "<br/>"+params[i]['marker']+params[i]['seriesName']+ ' : ' + changeMoneyByDigit(String(params[i]['value']),1);
            	}
            }else if(params.length=1){
            		relVal += params[0]['marker']+params[0]['seriesName']+ ' : ' + changeMoneyByDigit(String(params[0]['value']),1);
            }
            return relVal;
        },
    },
    legend: {
      data:[/*'累计社员人数',*/'新增社员人数'],
      textStyle:{color:"#efefef",fontSize:"12"},
      right:0,
      orient:'vertical',
      padding: 20
    },
    grid: {
      top: '28%',
      left: '3%',
      right: '2%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      //data: ['星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期日'],
      splitLine: {
        show: false
      },
      axisLine: {
        show: true,
        lineStyle:{color:'#ffc203'},
      },
      axisTick:{
        show: false
      },
      axisLabel:{
        show: true,
        textStyle: {color:'#fff'}
      },
    },
    yAxis: [
    	{
          type: 'value',
          position: 'left',
	      splitLine: {
	        show: false
	      },
	      axisLine: {
	        show: false
	      },
	      axisTick:{
	        show: false
	      },
	      axisLabel:{
	        show: true,
	        textStyle: {color:'#fff'}
	      }
       },
      {
          type: 'value',
          position: 'right',
	      splitLine: {
	        show: false
	      },
	      axisLine: {
	        show: false
	      },
	      axisTick:{
	        show: false
	      },
	      axisLabel:{
	        show: true,
	        textStyle: {color:'#fff'}
	      }
       }
    ],
    series: [
      {
        name:'北京',
        cursor:'pointer',
        type: 'bar',
        yAxis: 1,
        stack: '总量',
        label: {
          show: true,
          position: 'top',
          formatter: '{c} ',
          textStyle:{
            color:"#ff3064"
          }
        },
        itemStyle: {
          normal: {
            color: "#ff3064",
            label: {
              show: false,
              textStyle: {
                color: "#fff"
              },
              position: "top",

            }
          }
        },

      },
	  {
        name:'天津',
        cursor: 'default',
        type: 'bar',
        yAxis: 1,
        stack: '总量',
        label: {
          show: true,
          position: 'top',
          formatter: '{c} ',
          textStyle:{
            color:"#30d7c7"
          }
        },
        itemStyle: {
          normal: {
            color: "#30d7c7",
            label: {
              show: false,
              textStyle: {
                color: "#30d7c7"
              },
              position: "top",

            }
          }
        },
      },
	  {
        name:'上海',
        cursor: 'default',
        type: 'bar',
        yAxis: 1,
        stack: '总量',
        label: {
          show: true,
          position: 'top',
          formatter: '{c} ',
          textStyle:{
            color:"#DF7B2D"
          }
        },
        itemStyle: {
          normal: {
            color: "#DF7B2D",
            label: {
              show: false,
              textStyle: {
                color: "#DF7B2D"
              },
              position: "top",

            }
          }
        },
      }
    ]
  };
      // 事件绑定
    turnoverCustomerOrderChart.on('click', function (params){
	  var cId = encode64(pageStatusInfo.cityId==""?'':pageStatusInfo.cityId);
	  var cName = encode64(pageStatusInfo.cityName==""?'':pageStatusInfo.cityName);
	  var nowdays = new Date();
      var year = turnoverCustomerOrderOption.xAxis.extdata[params.dataIndex];
	  var dayTime = year+"/"+turnoverCustomerOrderOption.xAxis.data[params.dataIndex].replace("-","/");
	  var flagBar = encode64("1");//标记是从总部页面柱状图跳入
	  //var role = curr_user.usergroup.code;
	  var target=pageStatusInfo.targets; 
	  var url = "" ; 
	  if(target==0){ 
	  		url = "user_member_view.html?t="+encode64(0)+"&r=&c="+cId+"&cn="+cName+"&e="+encode64(curr_user.id)+"&beTi="+encode64(dayTime)+"&endT="+encode64(dayTime)+"&fb="+flagBar; 
	  }else if(target==1){
	  	 	url = "user_member_view.html?t="+encode64(1)+"&r=&c="+cId+"&cn="+cName+"&e="+encode64(curr_user.id)+"&beTi="+encode64(dayTime)+"&endT="+encode64(dayTime)+"&fb="+flagBar; 
	  }
	  window.open(url,"user_member_view");
    });
    cityUserOption = {
	 title:[
    		{x: '10%', y: '3%',textStyle:{color:"#efefef",fontSize:"16"}},
		],
      tooltip: {
          trigger: 'item',
          //formatter: "{b}"  //显示tooltip时，只显示name
          formatter: function(value){ 
      		var city_name = String(value.data.name).split("\n")[0];
      		var city_data = String(value.data.name).split("\n")[1];
			return city_name+"\n"+changeMoney(city_data); 
		  },
      },

      calculable: false,
      series: [{
          type: 'treemap',
          width: '100%',
          height: '65%',
          itemStyle: {
              normal: {
                  label: {
                      show: true,
                      //formatter: "{b}",
                      formatter: function(value){ 
                      		var city_name = String(value.data.name).split("\n")[0];
                      		var city_data = String(value.data.name).split("\n")[1];
							return city_name+"\n"+changeMoney(city_data); 
					  },
                      fontSize: 15
                  },

                  borderWidth: 1
              },
              emphasis: {
                  label: {
                      show: true
                  }
              }
          },
          label: {
              normal: {
                  textStyle: {
                      fontSize: 15,
                      //formatter: "{b}"
                  }
              }
          },
          breadcrumb: {//关闭面包屑路径
              show: false
          },
          roam: false,//关闭平移拖动
          nodeClick: false,//关闭节点点击
          silent: false,//关闭鼠标事件
      }]
  };
  // 事件绑定
    cityUserGMV.on('click', function (params) {
    	var cityId = "";
    	cityId = params.data.city_id;
        pageStatusInfo.cityId = cityId;
        pageStatusInfo.cityName = params.data.name.split("\n")[0];
        pageStatusInfo.showLevel = SHOW_LEVEL_CITY;
        // 重置页面显示
        showPageContent(pageStatusInfo);
    });
    // 柱状图属性：门店(GMV)排名
    storeRankGmvOption = {
    title:[
		     {x: '5%', y: '0%',textStyle:{color:"#efefef",fontSize:"16"}},
		  ],
    tooltip : {
      trigger: 'axis',
      axisPointer: { // 坐标轴指示器，坐标轴触发有效
        "type": "shadow" // 默认为直线，可选为："line" | "shadow"
      }
    },
    calculable : true,
    grid: {
      left: '-3%',
      right: '4%',
      top: '12%',
      height: 230, //设置grid高度
      containLabel: true
    },
    xAxis : [
      {
        type : 'category',
        data : [],
        position: 'top',
        splitLine: {
          show: false
        },
        max: 4,
        axisLine: {
          show: false
        },
        axisTick:{
          show: false
        },
        axisLabel:{
          "show": true,
          rotate:45,//倾斜度 -90 至 90 默认为0
          textStyle: {color:'#fff'},
          formatter: function(value) {
            if ((typeof(value)!="undefined")&&(value.length > 5)) {
              return value.substring(value.indexOf('-')+1,value.indexOf('-')+1+4)+'...';
            } else {
              return value;
            }
          }
        }
      }
    ],
    yAxis : [
      {
        inverse: true,
        type : 'value',
        splitLine: {
          show: false
        },
        axisLine: {
          show: false
        },
        axisTick:{
          show: false
        },
        axisLabel:{
          /*textStyle:{
           color:'#f00'
           }*/
          "show": false
        }
      }
    ],
    series : [
      {
        name:'GMV',
        type:'bar',
        data:[],
        index:['a', 'b'] ,
        barWidth : 30,//柱图宽度
        barMaxWidth:30,//最大宽度
        barGap: 10,
        barCategoryGap: 10,
        itemStyle: {
          normal: {
            color: function(params3) {
              // build a color map as your need.
              var colorList3 = [
                '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
              ];
              return colorList3[params3.dataIndex];
            },
            borderWidth: 0,
            barBorderRadius: [10, 10, 10, 10],
            label: {
                show: true,
                position: 'bottom',
                formatter: '{c}',
                /*rotate:45,//倾斜度 -90 至 90 默认为0
                textStyle:{
                  color:"#fff"
                }*/
              }
          }
        }

      }
    ]
  };
    // 事件绑定
    storeRankChartGmv.on('click', function (params){
	  var store_id = encode64(storeRankGmvOption.xAxis[0].extdata[params.dataIndex]['storeId']);
	  var pId = encode64(pageStatusInfo.provinceId==""?'':pageStatusInfo.provinceId);
	  var cId = encode64(storeRankGmvOption.xAxis[0].extdata[params.dataIndex]['cityId']);
	  var cName = encode64(storeRankGmvOption.xAxis[0].extdata[params.dataIndex]['cityName']);
	  var flag = encode64("1");//标记是从门店跳入
	  var url = "current_shopkeeper.html?p="+pId+"&c="+cId+"&s="+store_id+"&cn="+cName+"&ln="+encode64(curr_user.name)+"&f="+flag+"&fs="+encode64(pageStatusInfo.targets);
	     //window.open("current_shopkeeper.html?s="+store_id+"&p=1&c=2","_self");
		  window.location.href=url; 
    });
    // 柱状图属性：门店(订单量)排名
    storeRankOrderOption = {
        title:[
      {x: '3%', y: '0',textStyle:{color:"#efefef",fontSize:"16"}},
    ],
    tooltip : {
      trigger: 'axis',
      axisPointer: { // 坐标轴指示器，坐标轴触发有效
        "type": "shadow" // 默认为直线，可选为："line" | "shadow"
      }
    },
    calculable : true,
    grid: {
      left: '0',
      right: '4%',
      top: '12%',
      height: 230, //设置grid高度
      containLabel: true
    },
    xAxis : [
      {
        type : 'category',
        data : [],
        max: 4,
        splitLine: {
          show: false
        },
        axisLine: {
          show: false
        },
        axisTick:{
          show: false
        },
        axisLabel:{
          "show": true,
          rotate:45,//倾斜度 -90 至 90 默认为0
          textStyle:{
            color:"#fff"
          },
          formatter: function(value) {
            if ((typeof(value)!="undefined")&&(value.length > 5)) {
              return value.substring(value.indexOf('-')+1,value.indexOf('-')+1+4)+'...';
            } else {
              return value;
            }
          }
        }
      }
    ],
    yAxis : [
      {
        type : 'value',
        splitLine: {
          show: false
        },
        axisLine: {
          show: false
        },
        axisTick:{
          show: false
        },
        axisLabel:{
          /*textStyle:{
           color:'#f00'
           }*/
          show: false
        }
      }
    ],
    series : [
      {
        name:'GMV',
        type:'bar',
        data:[],
        index:['a', 'b'] ,
        barWidth : 30,//柱图宽度
        barMaxWidth:30,//最大宽度
        barGap: 10,
        barCategoryGap: 10,
        itemStyle: {
          normal: {
            color: function(params3) {
              // build a color map as your need.
              var colorList3 = [
                '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
              ];
              return colorList3[params3.dataIndex];
            },
            borderWidth: 0,
            barBorderRadius: [10, 10, 10, 10],
            label: {
              show: true,
              position: 'top',
              formatter: '{c}'
            }
          }
        }
      }
    ]
  };
 
    // 事件绑定
    storeRankChartOrder.on('click', function (params){
       var store_id_genmini = storeRankOrderOption.xAxis[0].extdata[params.dataIndex]['storeId'];
       var store_id = "";
       doManager("dynamicManager", "queryPlatformidByStoreId",[store_id_genmini],
            function(data, textStatus, XMLHttpRequest) {
                if (data.result) {
                    var resultJson = JSON.parse(data.data);
                    $.each(eval(resultJson['storeList']), function (idx, val) {
                    	store_id = val['store_id'];
                    });
                }
            },false);
	  var pId = encode64(pageStatusInfo.provinceId==""?'':pageStatusInfo.provinceId);
	  var cId = encode64(storeRankOrderOption.xAxis[0].extdata[params.dataIndex]['cityId']);
	  var cName = encode64(storeRankOrderOption.xAxis[0].extdata[params.dataIndex]['cityName']);
	  var flag = encode64("1");//标记是从门店跳入
	  var url = "current_shopkeeper.html?p="+pId+"&c="+cId+"&s="+encode64(store_id)+"&cn="+cName+"&ln="+encode64(curr_user.name)+"&f="+flag+"&fs="+encode64(pageStatusInfo.targets);
	     //window.open("current_shopkeeper.html?s="+store_id+"&p=1&c=2","_self");
		  window.location.href=url; 
    });
   // 柱状图属性：国安侠(GMV)排名
    guoanManRankGMVOption = {
        title:[
      {x: '3%', y: '0%',textStyle:{color:"#efefef",fontSize:"16"}},
    ],
    grid: [
      {x: '20%', y: '7%',},
    ],
    tooltip: {
      formatter: '{b} ({c})',
      show: "true",
      trigger: 'axis',
      axisPointer: { // 坐标轴指示器，坐标轴触发有效
        type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
      }
    },
    xAxis: [
      {
        axisTick: {show:false},
        axisLabel: {show:false},
        splitLine: {show:false},
        axisLine: {show:false }
      },{}
    ],
    yAxis: [
      {
        gridIndex: 0,
        interval:0,
        data:[],
        axisTick: {show:false},
        axisLabel: {
          show: true,
          margin:65,
          textStyle:{
            color:'#fff',
            align:'left',
            baseline:'middle'
          },
          formatter: function(value) {
            if ((typeof(value)!="undefined")&&(value.length > 6)) {
              return value.substring(0, value.indexOf('-'));
            } else {
              return value;
            }
          }

        },
        splitLine: {show:false},
        axisLine: {
          show:false
        },
      }
    ],
    series: [

      {
      	cursor: 'pointer',
        name: 'GMV',
        type: 'bar',xAxisIndex: 0,yAxisIndex: 0,
        //barWidth:'55%',
        barWidth : 35,//柱图宽度
        barMaxWidth:35,//最大宽度
        itemStyle:{
          normal:{
            barBorderRadius: 50,
            borderWidth: 0,
            color: function(params2) {
              // build a color map as your need.
              var colorList2 = [
                //'#ff6600','#ca702d','#418ba1','#129bd3','#a0b6a4',
               // '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
                '#ffad43','#ffa940','#ffa13a','#ff8222','#ff6600',
                '#ff6e0a','#ff720e','#ff720e','#ff9430','#ff6600',
              ];
              return colorList2[params2.dataIndex];
            },
          },
        },
        label:{normal:{show:true, position:"right",textStyle:{color:"#9EA7C4"}}},
        data: [],
      },
    ]
  };
       // 事件绑定
    guoanManRankChartGMV.on('click', function (params){
	  var cName = guoanManRankGMVOption.yAxis[0].data[params.dataIndex].split('-')[1];
	  var employeeNO = guoanManRankGMVOption.xAxis[0].extdata[params.dataIndex];
	  var employeeName = guoanManRankGMVOption.yAxis[0].data[params.dataIndex].split('-')[0];
	  var cityno = guoanManRankGMVOption.xAxis[1].extdata[params.dataIndex];
	  var s1 = "";
      //昨天日期
      if(pageStatusInfo['currentDay']==1){
      	s1 = findLastFirstDay();
      	s2 = findLastEndDay();
      }else{
      	s1 = findTodayFirstDay();
      	s2 = findTodayYesterdayDay();
      }
	  var dayTime = s1+"-"+s2
	  var target=pageStatusInfo.targets;
	  var url = "";
	  if(target==0){
	  	url = "searchData_view.html?t="+encode64(0)+"&e=&cn="+encode64(cName)+"&time="+encode64(dayTime)+"&co="+encode64(cityno)+"&rt="+encode64("employee")+"&eo="+encode64(employeeNO);
	  }else if(target==1){
	  	url = "searchData_view.html?t="+encode64(1)+"&e=&cn="+encode64(cName)+"&time="+encode64(dayTime)+"&co="+encode64(cityno)+"&rt="+encode64("employee")+"&eo="+encode64(employeeNO);
	  }
	  window.open(url);
    });
    // 柱状图属性：事业群排名
    businessDepRankGMVOption = {
        title : {
      //subtext: '纯属虚构',
      x: '2%', y: '2%',
      textStyle:{color:"#efefef",fontSize:"16"}
    },
    tooltip : {
      trigger: 'item',
      formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
      x : 'right',
      y : '80%',
      //orient: 'vertical',
      data:[],
      textStyle:{color:"#fff"}
    },
    series : [
      {
        name:'事业群',
        type:'pie',
        radius : [20, 90],
        center : ['50%', '45%'],
        roseType : 'radius',
        color: ['#f3b822', '#49dff0', '#034079', '#6f81da', '#13de86'],
        label: {
          normal: {
            show: false
          },
          emphasis: {
            show: false
          }
        },
        lableLine: {
          normal: {
            show: false
          },
          emphasis: {
            show: true
          }
        },
        data: []
      }
    ]
  };
    // 事件绑定
    businessDepRankChartGMV.on('click', function (params){
        var depName=encode64(businessDepRankGMVOption.legend.data[params.dataIndex]);
        window.open("index_BU.html?depname="+depName);
    });
    var maskImage = new Image();
    maskImage.src = '../crm/sjfx-group_files/180209101040.png';
    // 柱状图属性：商品排名
    commodityRankOption = {
        title: {
        	 x: 'left',
             textStyle:{color:"#efefef",fontSize:"16"}
        },
        tooltip: {
        	formatter:function(param,ticket,callback){
        		$("#main6 >div").eq(1)[0].style.cursor="default";
        		return param.data.tooltips+" : "+param.data.value;
        	}
        },
        series: [{
            type : 'wordCloud',  //类型为字符云
                shape : 'pentagon',  //平滑
                gridSize : 2, //网格尺寸
                size : ['80%','80%'],
                left:'5%',
                width:'80%',
                sizeRange : [ 12, 50 ],
                rotationRange : [ -90, 90 ], //旋转范围
                maskImage:maskImage,
                textStyle : {
                    normal : {
                        fontFamily:'sans-serif',
                        color: function () {
                            var colors = ['#fda67e', '#81cacc', '#cca8ba', "#88cc81", "#82a0c5", '#fddb7e', '#735ba1', '#bda29a', '#6e7074', '#546570', '#c4ccd3'];
                            return colors[parseInt(Math.random() * 10)];
                        }
                    },
                    emphasis : {
                        shadowBlur : 5,  //阴影距离
                        shadowColor : '#333'  //阴影颜色
                    }
                },
                data:[],
        }]
    };
    commodityRankChart.on('click', function (params){
    var cName = params.data['city_name'];
    var cityno = params.data['cityno'];
    if(cName.indexOf('市')>0){
    	cName = cName.replace('市','');
    }
    if(cName.indexOf('黔东南州')>0){
    	cName='黔东南苗族侗族自治州';
    }
    if(cityno.length==3){
    	cityno = '0'+cityno;
    }
    	//昨天日期
      if(pageStatusInfo['currentDay']==1){
      	s1 = findLastFirstDay();
      	s2 = findLastEndDay();
      }else{
      	s1 = findTodayFirstDay();
      	s2 = findTodayYesterdayDay();
      }
	  var dayTime = s1+"-"+s2
      var product_id=encode64(params.data['product_id']);
      var product_name=encode64(params.data['tooltips']);
      var target=pageStatusInfo.targets;
	  var url = "";
	  if(target==0){
	  	url = "searchDataItem_view.html?t="+encode64(0)+"&e=&cn="+encode64(cName)+"&time="+encode64(dayTime)+"&co="+encode64(cityno)+"&rt="+encode64("product")+"&product_id="+product_id+"&product_name="+product_name;
	  }else if(target==1){
	  	url = "searchDataItem_view.html?t="+encode64(1)+"&e=&cn="+encode64(cName)+"&time="+encode64(dayTime)+"&co="+encode64(cityno)+"&rt="+encode64("product")+"&product_id="+product_id+"&product_name="+product_name;
	  }
      window.open(url);
    });
    //设置鼠标移入菜单显示
    //setMouseOverShow();
};
// 设置系统时间
var setCurrentDate = function (pageStatusInfo) {
    var date = new Date();
    var curYear = date.getFullYear(); //当前年
    var curMonth = date.getMonth() + 1;//当前月
    var curDay = date.getDate();//当前日
    pageStatusInfo['currentYear'] = curYear;
    pageStatusInfo['currentDay'] = curDay;
    pageStatusInfo['currentMonth_'] = curMonth;
    if(curDay==1){
    	pageStatusInfo['currentMonth'] = curMonth-1;
    }else{
    	pageStatusInfo['currentMonth'] = curMonth;
    }
};
// 鼠标进入显示菜单
var setMouseOverShow = function () {
    $(".sidebar-menu > li").mouseenter(function () {
        $(this).children(".nav_dropdown").show();
        $(this).addClass("sidebar_active");

        $(".nav_dropdown li").removeClass("nav_mark_bg");
    });
    $(".sidebar-menu > li").mouseleave(function () {
        $(this).children(".nav_dropdown").hide();
        $(this).removeClass("sidebar_active");
    });
    $(".nav_dropdown > li").mouseenter(function () {
        $(this).children(".nav_dropm").show();
    });
    $(".nav_dropdown > li").mouseleave(function () {
        $(this).children(".nav_dropm").hide();
    });

    $(".nav_dropdown li").hover(function () {
        $(".nav_dropdown li").removeClass("nav_mark_bg");
        $(this).addClass("nav_mark_bg");
    });
};
// 获取统计数据

var getStatisticInfo = function (pageStatusInfo) {
	var current_page = pageStatusInfo.currentPage;
    //var cacheKey = CACHE_HEADER_STATISTIC+current_page+"_"+ pageStatusInfo.getCacheKey();
    // 从缓存获取数据
//    var statisticData = JsCache.get(cacheKey);
//    if (statisticData) {
//        //console.log('show summary statistic data base on js cache.')
//        showStatisticInfo(statisticData);
//        statisticExtendInfo = statisticData;
//    } else {
        // 准备服务端数据请求参数
    	var pageInfo = new Object();
 	    pageInfo.currentPage = current_page;
 	    pageInfo.recordsPerPage = 10;
 	    
        var dynamicDto = {
            cityId: pageStatusInfo.cityId,
            cityName: "",
            provinceId: pageStatusInfo.provinceId,
            target: function () {
                if (pageStatusInfo.showLevel == SHOW_LEVEL_CHINA || pageStatusInfo.showLevel == SHOW_LEVEL_CITY) {
                    return 3;
                } else if (pageStatusInfo.showLevel == SHOW_LEVEL_PROVINCE) {
                    return 1;
                }
            }()
        };
        //console.log('\trequest: summary statistic. ');
        //console.log(dynamicDto);
        var startTime = new Date().getTime();
        doManager("dynamicManager", "getAllCountOfCityStoreGax", [dynamicDto,pageInfo],
            function (data, textStatus, XMLHttpRequest) {
                if (data.result) {
                    var resultJson = JSON.parse(data.data);
                    showStatisticInfo(resultJson);
                    statisticExtendInfo = resultJson;
                    createEmployeePage();
                    //JsCache.set(cacheKey, resultJson);不放入缓存
                }
            }, false);
        //console.log('request summary statistic data from server in ' + (new Date().getTime() - startTime) + ' millisecond');
//    }
};

//切换页面获取数据
var getEmployeePerPageData = function(pageStatusInfo){
	var current_page = pageStatusInfo.currentPage;
   
    // 准备服务端数据请求参数
	var pageInfo = new Object();
    pageInfo.currentPage = current_page;
    pageInfo.recordsPerPage = 10;
    var name = $("#gax_search_name").val();
	var city = $("#gax_search_city").val();
	var store = $("#gax_search_storename").val();
    var dynamicDto = {
        cityId: pageStatusInfo.cityId,
        cityName: city,
        provinceId: pageStatusInfo.provinceId,
        storeName:store,
        employeeName:name,
        target: function () {
            if (pageStatusInfo.showLevel == SHOW_LEVEL_CHINA || pageStatusInfo.showLevel == SHOW_LEVEL_CITY) {
                return 3;
            } else if (pageStatusInfo.showLevel == SHOW_LEVEL_PROVINCE) {
                return 1;
            }
        }()
    };
   
    var startTime = new Date().getTime();
    doManager("dynamicManager", "getAllCountOfCityStoreGax", [dynamicDto,pageInfo],
        function (data, textStatus, XMLHttpRequest) {
            if (data.result) {
                var resultJson = JSON.parse(data.data);
                statisticExtendInfo = resultJson;
                //JsCache.set(cacheKey, resultJson);
            }
        }, false);
    //console.log('request summary statistic data from server in ' + (new Date().getTime() - startTime) + ' millisecond');
    
}

//创建门店人员弹窗分页
function  createEmployeePage(){
	var employeeCount = statisticExtendInfo.employee == null ? 0 : statisticExtendInfo.employee.pageinfo.totalRecords;
	   //加载分页
    layui.use('laypage', function(){
    	  var laypage = layui.laypage;
    	  
    	  //执行一个laypage实例
    	  laypage.render({
    	    elem: 'employee_page', //注意，这里的 test1 是 ID，不用加 # 号
    	    count: employeeCount, //数据总数，从服务端得到
    	    limit:10,
    	    limits:[10,20,50,100],
    	    groups:3,
    	    prev:"上一页",
    	    next:"下一页",
    	    first:"首页",
    	    last:"末页",
    	    jump:function(obj, first){
    	        //obj包含了当前分页的所有参数，比如：
//    	        //console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
//    	        //console.log(obj.limit); //得到每页显示的条数
    	        
    	        //首次不执行
    	        if(!first){
    	        	pageStatusInfo.currentPage=obj.curr;
    	        	getEmployeePerPageData(pageStatusInfo);//切换页面后获取数据
    	        }
	        	var human_list2 = statisticExtendInfo.employee.data;
	        	var infoStr="";
	        	for(var i=0;i<human_list2.length;i++){
	                var employee_name = human_list2[i].name;
	                var employee_no = human_list2[i].employee_no;
	                var employee_storename = human_list2[i].storeName;  //门店
	                var employee_citySelect = human_list2[i].city_name;   //城市
	                infoStr = infoStr+"<tr style='cursor:pointer'  id='tr_"+employee_no+"' onclick='goEmployee(this)'><td>"+parseInt(i+1)+"</td><td>"+employee_no+"</td><td>"+employee_name+"</td><td>"+employee_citySelect+"</td><td>"+employee_storename+"</td></tr>"
	            }
	            $("#employee_detail_title").nextAll().remove();
	            $("#employee_detail_title").after(infoStr);
	            $("#employee_detail_title").nextAll("tr:even").css("background-color","aliceblue");
	            $("#employee_detail_title").nextAll("tr:odd").css("background-color","mintcream");
    	        
    	      }
    	  });
     });
}



// 显示概要统计
var showStatisticInfo = function (statisticData) {
    var cityCount = statisticData.cityCountList == null ? 0 : statisticData.cityCountList.length;
    var storeCount = statisticData.storeCountList == null ? 0 : statisticData.storeCountList.length;
    var storeKeeperCount = statisticData.storeKeeper == null ? 0 : statisticData.storeKeeper.length;
    var employeeCount = statisticData.employee == null ? 0 : statisticData.employee.pageinfo.totalRecords;
	var dtmd = $("<dt id='storeT'>门店</dt>");
	var ddmd = $("<dd><a href='javascript:initStore();' style='color:#00c0ef' id='store_total'>载入中...</a></dd>");
	var dtdz= $("<dt id='storeKeeperT'>店长</dt>");
	var dddz = $("<dd><a href='javascript:initStoreKeeper();' style='color:#00c0ef' id='storeKeeper_total'>载入中...</a></dd>");
	var dtmdry= $("<dt id='storePeoleT'>门店人员</dt>");
	var ddmdry = $("<dd><a href='javascript:initEmployeeInfo();' style='color:#00c0ef' id='employee_total'>载入中...</a></dd>");
    $("#gaiyaotongji").empty();
    $("#gaiyaotongji").append(dtmd).append(ddmd).append(dtdz).append(dddz).append(dtmdry).append(ddmdry);
    if(pageStatusInfo.targets==0){
    	var dtcs = $("<dt id='cityT'>").html("城市");
    	var ddcs = $("<dd id='cityD'><a href='javascript:initCityInfo();' style='color:#00c0ef' id='city_total'>载入中...</a></dd>");
    	$("#gaiyaotongji").prepend(ddcs).prepend(dtcs);
    	$("#city_total").html(cityCount + "<span>个</span>");
	    $("#store_total").html(storeCount + "<span>个</span>");
	    $("#storeKeeper_total").html(storeKeeperCount + "<span>人</span>");
	    $("#employee_total").html(employeeCount + "<span>人</span>");
    }else if(pageStatusInfo.targets==1){
    	$("#store_total").html(storeCount + "<span>个</span>");
	    $("#storeKeeper_total").html(storeKeeperCount + "<span>人</span>");
	    $("#employee_total").html(employeeCount + "<span>人</span>");
    	var dtxq = $("<dt>").html("小区");
    	var ddxq = $("<dd id='tinyVillage_total'>").html("载入中...");
    	var dtsq = $("<dt>").html("社区");
    	var ddsq = $("<dd id='village_total'>").html("载入中...");
    	var dtjd = $("<dt>").html("街道");
    	var ddjd = $("<dd id='town_total'>").html("载入中...");
    	$("#gaiyaotongji").append(dtxq).append(ddxq).append(dtsq).append(ddsq).append(dtjd).append(ddjd);
    	getTotalOfCity();
    }
    
};
// 获取历史数据
var getHistoryData = function (pageStatusInfo) {
	
    var cacheKey = CACHE_HEADER_HISTORY_DATA + pageStatusInfo.getCacheKey();
    // 从缓存获取数据
    var historyData = JsCache.get(cacheKey);
    if (historyData) {
        //console.log('show history data base on js cache.')
        showHistoryData(historyData);
    } else {
    
        // 准备服务端数据请求参数
        var reqestParameter = {
            month:pageStatusInfo.currentMonth,
            year:pageStatusInfo.currentYear,
            provinceId:pageStatusInfo.provinceId,
            cityId:pageStatusInfo.cityId

        }
        //console.log('\trequest: history data');
        //console.log(reqestParameter);
        var startTime = new Date().getTime();
        // 查询当月累计营业额
        doManager("dynamicManager", "queryTradeSumByMonth",[reqestParameter],
            function(data, textStatus, XMLHttpRequest) {
                if (data.result) {
                    var resultJson= JSON.parse(data.data);
                    var historyDataFromServer = {'curMonthTurnover':resultJson.cur_order_amount,'customer_count':resultJson.customer_count,
                    'month_order_count':resultJson.month_order_count,'last_order_count':resultJson.last_order_count
                    ,'last_customer_count':resultJson.last_customer_count};
                    // 查询历史累计营业额
                    doManager("dynamicManager", "queryTradeSumOfHistory",[reqestParameter],
                        function(data, textStatus, XMLHttpRequest) {
                            if (data.result) {
                                var resultJson= JSON.parse(data.data);
                                historyDataFromServer['historyTurnover'] = resultJson.history_order_amount;
                                historyDataFromServer['history_customer_count'] = resultJson.history_customer_count;
                                historyDataFromServer['last_history_customer_count'] = resultJson.last_history_customer_count;
                                historyDataFromServer['history_order_count'] = resultJson.history_order_count;
                                doManager("dynamicManager", "getSumOfCurYear",[reqestParameter],
		                        function(data, textStatus, XMLHttpRequest) {
		                            if (data.result) {
		                            	var resultJson= JSON.parse(data.data);
		                            	historyDataFromServer['year_gmv_sum'] = resultJson.year_gmv_sum[0].year_sum_gmv;
		                            	historyDataFromServer['year_sum_order'] = resultJson.year_gmv_sum[0].year_sum_order;
		                         	}
		                         }
		                         ,false);
                                showHistoryData(historyDataFromServer);
                                JsCache.set(cacheKey, historyDataFromServer);
                            }
                        },false);
                }
            },false);
        //console.log('request history data from server in ' + (new Date().getTime() - startTime) + ' millisecond');
    }
};
// 显示历史数据
var showHistoryData = function (historyData) {
	/*
    $("#tradesumofcurmonthHid").html(parseInt(historyData.curMonthTurnover));
    $("#tradesumofhistoryHid").html(parseInt(historyData.historyTurnover));
    //$("#tradesumoflastmonthCustmomerHid").html(parseInt(historyData.last_customer_count));//上月用户量
    $("#tradesumoflasthistoryCustmomerHid").html(parseInt(historyData.last_history_customer_count));
    //$("#tradesumoflastmonthOrderHid").html(parseInt(historyData.last_order_count==null?'0':historyData.last_order_count));
    $("#tradesumofyearHid").html(parseInt(historyData.year_gmv_sum==null?'0':historyData.year_gmv_sum));
    */
    $("#tradesumOrderofyearHid").html(parseInt(historyData.year_sum_order==null?'0':historyData.year_sum_order));
    $("#tradesumofhistoryCustmomerHid").html(parseInt(historyData.history_customer_count));
    $("#tradesumofhistoryOrderHid").html(parseInt(historyData.history_order_count==null?'0':historyData.history_order_count));
    $("#tradesumofmonthOrderHid").html(parseInt(historyData.month_order_count==null?'0':historyData.month_order_count));
    $("#tradesumofmonthCustmomerHid").html(parseInt(historyData.customer_count));
    $("#tradesumofcurmonths").html(changeMoney(parseInt(historyData.curMonthTurnover)));
    $("#tradesumofCurYears").html(changeMoney(parseInt(historyData.year_gmv_sum==null?'0':historyData.year_gmv_sum)));
    $("#tradesumofhistorys").html(changeMoney(parseInt(historyData.historyTurnover)+(parseInt(historyData.year_gmv_sum==null?'0':historyData.year_gmv_sum))));
};
// 获取历史数据
var getOpenCardUser = function (pageStatusInfo) {
	/*
    var cacheKey = CACHE_HEADER_OPEN_CARD_USER + pageStatusInfo.getCacheKey();
    // 从缓存获取数据
    var openCardData = JsCache.get(cacheKey);
    if (openCardData) {
        //console.log('show open card base on js cache.')
        showOpenCardUser(openCardData);
    } else {
    */
        // 准备服务端数据请求参数
        var reqestParameter = {
            month:pageStatusInfo.currentMonth,
            year:pageStatusInfo.currentYear,
            provinceId:pageStatusInfo.provinceId,
            cityId:pageStatusInfo.cityId

        }
        //console.log('\trequest: open card ');
        //console.log(reqestParameter);
        var startTime = new Date().getTime();
        // 查询当月新增社员数和历史总社员数
        doManager("communityMembersManager", "getNewMembersCount",[reqestParameter],
            function(data, textStatus, XMLHttpRequest) {
                if (data.result) {
                    var resultJson= JSON.parse(data.data);
                    showOpenCardUser(resultJson);
                    //JsCache.set(cacheKey, resultJson);
                }
            },false);
        //console.log('request open card from server in ' + (new Date().getTime() - startTime) + ' millisecond');
    //}
};
// 显示历史数据
var showOpenCardUser = function (openCardData) {
	$("#openCardUserMonthcountHid").html(openCardData['newMemberCount']);
	$("#openCardUserHistorycountHid").html(openCardData['historyCount']);
	$("#openCardUserMonthcount").html(openCardData['newMemberCount']);
	$("#openCardUserHistorycount").html(openCardData['historyCount']);
};
var getLastMonthOrderCustomerCount = function(pageStatusInfo){
	var cacheKey = CACHE_HEADER_CUSTOMER_COUNT_DATA + pageStatusInfo.getCacheKey();
    // 从缓存获取数据
    var customerData = JsCache.get(cacheKey);
    if (customerData) {
        //console.log('show customer order data base on js cache.')
        showCustomerData(customerData);
    } else {
        // 准备服务端数据请求参数
        var reqestParameter = {
            month:pageStatusInfo.currentMonth,
            year:pageStatusInfo.currentYear,
            provinceId:pageStatusInfo.provinceId,
            cityId:pageStatusInfo.cityId

        }
        //console.log('\trequest: history data');
        //console.log(reqestParameter);
        var startTime = new Date().getTime();
        // 查询上月当日用户量和订单量
        doManager("dynamicManager", "queryMonthAndLastMonthTodayCustomerOrderCount",[reqestParameter],
            function(data, textStatus, XMLHttpRequest) {
                if (data.result) {
                	var custmomerDataFromServer = {};
                	var resultJson= JSON.parse(data.data);
                	custmomerDataFromServer['last_customer_count'] = resultJson.last_customer_count;
                	custmomerDataFromServer['last_order_count'] = resultJson.last_order_count;
                    showCustomerData(custmomerDataFromServer);
                    JsCache.set(cacheKey, custmomerDataFromServer);
                }
            });
        //console.log('request customer order data from server in ' + (new Date().getTime() - startTime) + ' millisecond');
    }
}
// 显示历史数据
var showCustomerData = function (customerData) {
    $("#tradesumoflastmonthCustmomerHid").html(parseInt(customerData.last_customer_count));//上月用户量
    $("#tradesumoflastmonthOrderHid").html(parseInt(customerData.last_order_count==null?'0':customerData.last_order_count));
};
// 设置地图显示
var setMapData = function (pageStatusInfo, params) {
    if (params) {
        if (pageStatusInfo['showLevel'] == SHOW_LEVEL_CHINA) {
            if (params.data.citycode) {
            	if(isMunicipality(params.name)){
            		$.each(eval(openedProvinceMunicipality), function (idx, val) {
	    				if(params.data['name']==val['name']){
		    				province_id = val['province_id'];
					        pageStatusInfo.cityId = province_id;
					        pageStatusInfo.cityName = params.data['name'];
					        pageStatusInfo.showLevel = SHOW_LEVEL_CITY;
	    				}
	     			});
            	}else{
            		$.each(eval(openedCity), function (idx, val) {
	    				if(val['name'].indexOf(params.data['name'])>-1){
		    				cityId = val['cityId'];
					        pageStatusInfo.cityId = cityId;
					        pageStatusInfo.cityName = params.data['name'];
					        pageStatusInfo.showLevel = SHOW_LEVEL_CITY;
	    				}
	     			});
            	}
                // 重置页面显示
    			showPageContent(pageStatusInfo);
            } else if(params.data['province_id']){
            	if(isMunicipality(params.name)){
            		 pageStatusInfo['showLevel'] = SHOW_LEVEL_CITY;
	                 pageStatusInfo['cityId'] = params.data['province_id'];
	                 pageStatusInfo['cityName'] = params.name;
            	}else{
            		 pageStatusInfo['showLevel'] = SHOW_LEVEL_PROVINCE;
	                 pageStatusInfo['provinceName'] = params.name;
	                 pageStatusInfo['provinceId'] = params.data['province_id'];
            	}
                // 重置页面显示
    			showPageContent(pageStatusInfo);
            }else if(params.data['cityId']){
        			pageStatusInfo['showLevel'] = SHOW_LEVEL_CITY;
		            pageStatusInfo['cityId'] = params.data['cityId'];
		            pageStatusInfo['cityName'] = params.name;
		            // 重置页面显示
    			showPageContent(pageStatusInfo);
            }
        }else if (pageStatusInfo['showLevel'] == SHOW_LEVEL_PROVINCE&&params.data['cityId']) {
        			pageStatusInfo['showLevel'] = SHOW_LEVEL_CITY;
		            pageStatusInfo['cityId'] = params.data['cityId'];
		            pageStatusInfo['cityName'] = params.name;
		            // 重置页面显示
    				showPageContent(pageStatusInfo);
        }
    }


};
// 显示地图
var showMap = function (pageStatusInfo) {
	var province = [];
	doManager("dynamicManager", "getAllOpenProvinces",[],
            function(data, textStatus, XMLHttpRequest) {
                if (data.result) {
                    var resultJson= JSON.parse(data.data);
                    $.each(eval(resultJson['province']), function (idx, val) {
	    				var object = new Object();
	    				object['province_id'] = val['province_id'];
	    				object['name'] = val['name'].replace("市","").replace("省","").replace("自治区","");
	    				//object['type'] = val['type'];
	    				if(val['selected']=='true'){
	    					object['selected'] = true;
	    					var object2 = new Object();
	    					object2['areaColor'] = '#fcdc30';
	    					var object3 = new Object();
	    					object3['emphasis'] = object2;
	    					object['itemStyle'] = object3;
	    				}
	    				province.push(object);
	     			});
                }
            },false);
    openedProvinceMunicipality = province;
    /**
    openedProvinceMunicipality = [
        {
            name: '广东', province_id: "19", selected: true, itemStyle: {
                emphasis: {
                    areaColor: '#fcdc30'
                }
            }
        },
        {
            name: '北京', province_id: "1", selected: true, itemStyle: {
                emphasis: {
                    areaColor: '#fcdc30'
                }
            }
        },
        {
            name: '天津', province_id: "3", selected: true, itemStyle: {
                emphasis: {
                    areaColor: '#fcdc30'
                }
            }
        },
        {
            name: '上海', province_id: "2", selected: true, itemStyle: {
                emphasis: {
                    areaColor: '#fcdc30'
                }
            }
        },
        {
            name: '辽宁', province_id: "6", selected: true, itemStyle: {
                emphasis: {
                    areaColor: '#fcdc30'
                }
            }
        },
        {
            name: '贵州', province_id: "24", selected: true, itemStyle: {
                emphasis: {
                    areaColor: '#fcdc30'
                }
            }
        },
        {
            name: '云南', province_id: "25", selected: true, itemStyle: {
                emphasis: {
                    areaColor: '#fcdc30'
                }
            }
        },
        {
            name: '内蒙古', province_id: "5", selected: true, itemStyle: {
                emphasis: {
                    areaColor: '#fcdc30'
                }
            }
        },
        {
            name: '湖南', province_id: "18", selected: true, itemStyle: {
                emphasis: {
                    areaColor: '#fcdc30'
                }
            }
        }
    ];
    */
    var citylist = [];
	doManager("dynamicManager", "selectAllCity",[],
            function(data, textStatus, XMLHttpRequest) {
                if (data.result) {
                    var resultJson= JSON.parse(data.data);
                    $.each(eval(resultJson), function (idx, val) {
                    	if(isMunicipality(val['cityname']) == false){
		    				var object = new Object();
		    				object['cityId'] = val['id'];
		    				if(String(val['cityname']).indexOf("自治州")<0){
		    					object['name'] = String(val['cityname'])+"市";
		    				}else{
		    					object['name'] = val['cityname'];
		    				}
		    				//object['type'] = val['type'];
	    					object['selected'] = true;
	    					var object2 = new Object();
	    					object2['areaColor'] = '#fcdc30';
	    					var object3 = new Object();
	    					object3['emphasis'] = object2;
	    					object['itemStyle'] = object3;
		    				citylist.push(object);
                    	}
	     			});
                }
            },false);
    openedCity = citylist;
    /*
    openedCity = [
        {
            name: '重庆市'
        }, {name: '北京市', cityId: 1}, {
            name: '天津市', selected: true, cityId: 3, itemStyle: {
                emphasis: {
                    areaColor: '#fcdc30'
                }
            }
        }, {
            name: '上海市', selected: true, cityId: 2, itemStyle: {
                emphasis: {
                    areaColor: '#fcdc30'
                }
            }
        }, {name: '香港'}, {name: '澳门'}, {name: '巴音郭楞蒙古自治州'}, {name: '和田地区'}, {name: '哈密地区'}, {name: '阿克苏地区'}, {name: '阿勒泰地区'}, {name: '喀什地区'}, {
            name: '塔城地区'
        }, {name: '昌吉回族自治州'}, {name: '克孜勒苏柯尔克孜自治州'}, {name: '吐鲁番地区'}, {name: '伊犁哈萨克自治州'}, {name: '博尔塔拉蒙古自治州'}, {name: '乌鲁木齐市'}, {name: '克拉玛依市'}, {name: '阿拉尔市'}, {name: '图木舒克市'}, {name: '五家渠市'}, {name: '石河子市'}, {name: '那曲地区'}, {name: '阿里地区'}, {name: '日喀则地区'}, {name: '林芝地区'}, {
            name: '昌都地区'
        }, {name: '山南地区'}, {name: '拉萨市'}, {name: '呼伦贝尔市'}, {name: '阿拉善盟'}, {name: '锡林郭勒盟'}, {name: '鄂尔多斯市'}, {name: '赤峰市'}, {name: '巴彦淖尔市'}, {name: '通辽市'}, {
            name: '乌兰察布市', cityId: 9, selected: true, itemStyle: {
                emphasis: {
                    areaColor: '#fcdc30',
                }
            }
        }, {name: '兴安盟'}, {
            name: '包头市'
        }, {name: '呼和浩特市'}, {name: '乌海市'}, {name: '海西蒙古族藏族自治州'}, {name: '玉树藏族自治州'}, {name: '果洛藏族自治州'}, {name: '海南藏族自治州'}, {name: '海北藏族自治州'}, {name: '黄南藏族自治州'}, {name: '海东地区'}, {name: '西宁市'}, {name: '甘孜藏族自治州'}, {name: '阿坝藏族羌族自治州'}, {name: '凉山彝族自治州'}, {name: '绵阳市'}, {name: '达州市'}, {
            name: '广元市'
        }, {name: '雅安市'}, {name: '宜宾市'}, {name: '乐山市'}, {name: '南充市'}, {name: '巴中市'}, {name: '泸州市'}, {name: '成都市'}, {name: '资阳市'}, {name: '攀枝花市'}, {name: '眉山市'}, {name: '广安市'}, {
            name: '德阳市'
        }, {name: '内江市'}, {name: '遂宁市'}, {name: '自贡市'}, {name: '黑河市'}, {name: '大兴安岭地区'}, {name: '哈尔滨市'}, {name: '齐齐哈尔市'}, {name: '牡丹江市'}, {name: '绥化市'}, {name: '伊春市'}, {name: '佳木斯市'}, {
            name: '鸡西市'
        }, {name: '双鸭山市'}, {name: '大庆市'}, {name: '鹤岗市'}, {name: '七台河市'}, {name: '酒泉市'}, {name: '张掖市'}, {name: '甘南藏族自治州'}, {name: '武威市'}, {name: '陇南市'}, {name: '庆阳市'}, {name: '白银市'}, {
            name: '定西市'
        }, {name: '天水市'}, {name: '兰州市'}, {name: '平凉市'}, {name: '临夏回族自治州'}, {name: '金昌市'}, {name: '嘉峪关市'}, {name: '普洱市'}, {name: '红河哈尼族彝族自治州'}, {name: '文山壮族苗族自治州'}, {name: '曲靖市'}, {name: '楚雄彝族自治州'}, {name: '大理白族自治州'}, {name: '临沧市'}, {name: '迪庆藏族自治州'}, {name: '昭通市'}, {
            name: '昆明市', selected: true, cityId: 6, itemStyle: {
                emphasis: {
                    areaColor: '#fcdc30',
                }
            }
        }, {name: '丽江市'}, {name: '西双版纳傣族自治州'}, {name: '保山市'}, {name: '玉溪市'}, {name: '怒江傈僳族自治州'}, {name: '德宏傣族景颇族自治州'}, {name: '百色市'}, {name: '河池市'}, {name: '桂林市'}, {name: '南宁市'}, {name: '柳州市'}, {
            name: '崇左市'
        }, {name: '来宾市'}, {name: '玉林市'}, {name: '梧州市'}, {name: '贺州市'}, {name: '钦州市'}, {name: '贵港市'}, {name: '防城港市'}, {name: '北海市'}, {name: '怀化市'}, {name: '永州市'}, {name: '邵阳市'}, {
            name: '郴州市'
        }, {name: '常德市'}, {name: '湘西土家族苗族自治州'}, {name: '衡阳市'}, {name: '岳阳市'}, {name: '益阳市'}, {
        	name: '长沙市', selected: true, cityId: 11, itemStyle: {
                emphasis: {
                    areaColor: '#fcdc30',
                }
            }
        }, {name: '株洲市'}, {name: '张家界市'}, {name: '娄底市'}, {name: '湘潭市'}, {name: '榆林市'}, {
            name: '延安市'
        }, {name: '汉中市'}, {name: '安康市'}, {name: '商洛市'}, {name: '宝鸡市'}, {name: '渭南市'}, {name: '咸阳市'}, {name: '西安市'}, {name: '铜川市'}, {name: '清远市'}, {name: '韶关市'}, {name: '湛江市'}, {
            name: '梅州市'
        }, {name: '河源市'}, {name: '肇庆市'}, {name: '惠州市'}, {name: '茂名市'}, {name: '江门市'}, {name: '阳江市'}, {name: '云浮市'}, {
            name: '广州市', selected: true, cityId: 8, itemStyle: {
                emphasis: {
                    areaColor: '#fcdc30',
                }
            }
        }, {name: '汕尾市'}, {name: '揭阳市'}, {name: '珠海市'}, {
            name: '佛山市'
        }, {name: '潮州市'}, {name: '汕头市'}, {
            name: '深圳市', cityId: 7, selected: true, itemStyle: {
                emphasis: {
                    areaColor: '#fcdc30',
                }
            }
        }, {name: '东莞市'}, {name: '中山市'}, {name: '延边朝鲜族自治州'}, {name: '吉林市'}, {name: '白城市'}, {name: '松原市'}, {name: '长春市'}, {name: '白山市'}, {
            name: '通化市'
        }, {name: '四平市'}, {name: '辽源市'}, {name: '承德市'}, {name: '张家口市'}, {name: '保定市'}, {name: '唐山市'}, {name: '沧州市'}, {name: '石家庄市'}, {name: '邢台市'}, {name: '邯郸市'}, {name: '秦皇岛市'}, {
            name: '衡水市'
        }, {name: '廊坊市'}, {name: '恩施土家族苗族自治州'}, {name: '十堰市'}, {name: '宜昌市'}, {name: '襄樊市'}, {name: '黄冈市'}, {name: '荆州市'}, {name: '荆门市'}, {name: '咸宁市'}, {name: '随州市'}, {name: '孝感市'}, {
            name: '武汉市'
        }, {name: '黄石市'}, {name: '神农架林区'}, {name: '天门市'}, {name: '仙桃市'}, {name: '潜江市'}, {name: '鄂州市'}, {name: '遵义市'}, {
            name: '黔东南苗族侗族自治州', selected: true, cityId: 10, itemStyle: {
                emphasis: {
                    areaColor: '#fcdc30',
                }
            }
        }, {name: '毕节地区'}, {name: '黔南布依族苗族自治州'}, {name: '铜仁地区'}, {name: '黔西南布依族苗族自治州'}, {name: '六盘水市'}, {name: '安顺市'}, {
            name: '贵阳市', selected: true, cityId: 5, itemStyle: {
                emphasis: {
                    areaColor: '#fcdc30',
                }
            }
        }, {
            name: '烟台市'
        }, {name: '临沂市'}, {name: '潍坊市'}, {name: '青岛市'}, {name: '菏泽市'}, {name: '济宁市'}, {name: '德州市'}, {name: '滨州市'}, {name: '聊城市'}, {name: '东营市'}, {name: '济南市'}, {name: '泰安市'}, {
            name: '威海市'
        }, {name: '日照市'}, {name: '淄博市'}, {name: '枣庄市'}, {name: '莱芜市'}, {name: '赣州市'}, {name: '吉安市'}, {name: '上饶市'}, {name: '九江市'}, {name: '抚州市'}, {name: '宜春市'}, {name: '南昌市'}, {
            name: '景德镇市'
        }, {name: '萍乡市'}, {name: '鹰潭市'}, {name: '新余市'}, {name: '南阳市'}, {name: '信阳市'}, {name: '洛阳市'}, {name: '驻马店市'}, {name: '周口市'}, {name: '商丘市'}, {name: '三门峡市'}, {name: '新乡市'}, {
            name: '平顶山市'
        }, {name: '郑州市'}, {name: '安阳市'}, {name: '开封市'}, {name: '焦作市'}, {name: '许昌市'}, {name: '濮阳市'}, {name: '漯河市'}, {name: '鹤壁市'}, {name: '大连市'}, {name: '朝阳市'}, {name: '丹东市'}, {
            name: '铁岭市'
        }, {
            name: '沈阳市', selected: true, cityId: 4, itemStyle: {
                emphasis: {
                    areaColor: '#fcdc30',
                }
            }
        }, {name: '抚顺市'}, {name: '葫芦岛市'}, {name: '阜新市'}, {name: '锦州市'}, {name: '鞍山市'}, {name: '本溪市'}, {name: '营口市'}, {name: '辽阳市'}, {name: '盘锦市'}, {name: '忻州市'}, {
            name: '吕梁市'
        }, {name: '临汾市'}, {name: '晋中市'}, {name: '运城市'}, {name: '大同市'}, {name: '长治市'}, {name: '朔州市'}, {name: '晋城市'}, {name: '太原市'}, {name: '阳泉市'}, {name: '六安市'}, {name: '安庆市'}, {
            name: '滁州市'
        }, {name: '宣城市'}, {name: '阜阳市'}, {name: '宿州市'}, {name: '黄山市'}, {name: '巢湖市'}, {name: '亳州市'}, {name: '池州市'}, {name: '合肥市'}, {name: '蚌埠市'}, {name: '芜湖市'}, {name: '淮北市'}, {
            name: '淮南市'
        }, {name: '马鞍山市'}, {name: '铜陵市'}, {name: '南平市'}, {name: '三明市'}, {name: '龙岩市'}, {name: '宁德市'}, {name: '福州市'}, {name: '漳州市'}, {name: '泉州市'}, {name: '莆田市'}, {name: '厦门市'}, {
            name: '丽水市'
        }, {name: '杭州市'}, {name: '温州市'}, {name: '宁波市'}, {name: '舟山市'}, {name: '台州市'}, {name: '金华市'}, {name: '衢州市'}, {name: '绍兴市'}, {name: '嘉兴市'}, {name: '湖州市'}, {name: '盐城市'}, {
            name: '徐州市'
        }, {name: '南通市'}, {name: '淮安市'}, {name: '苏州市'}, {name: '宿迁市'}, {name: '连云港市'}, {name: '扬州市'}, {name: '南京市'}, {name: '泰州市'}, {name: '无锡市'}, {name: '常州市'}, {name: '镇江市'}, {
            name: '吴忠市'
        }, {name: '中卫市'}, {name: '固原市'}, {name: '银川市'}, {name: '石嘴山市'}, {name: '儋州市'}, {name: '文昌市'}, {name: '乐东黎族自治县'}, {name: '三亚市'}, {name: '琼中黎族苗族自治县'}, {name: '东方市'}, {name: '海口市'}, {
            name: '万宁市'
        }, {name: '澄迈县'}, {name: '白沙黎族自治县'}, {name: '琼海市'}, {name: '昌江黎族自治县'}, {name: '临高县'}, {name: '陵水黎族自治县'}, {name: '屯昌县'}, {name: '定安县'}, {name: '保亭黎族苗族自治县'}, {name: '五指山市'}];
        */
    // 各省地图数据
    var provinces = {
        '上海': '../crm/dist/geometryCouties/data-1482909900836-H1BC_1WHg.json',
        '河北': '../crm/dist/geometryCouties/data-1482909799572-Hkgu_yWSg.json',
        '湖北': '../crm/dist/geometryCouties/data-1482909799572-Hksa_hubei.json',
        // '山西': '/asset/get/s/data-1482909909703-SyCA_JbSg.json',
        '内蒙古': '../crm/dist/geometryCouties/data-1482909841923-rkqqdyZSe.json',
        '辽宁': '../crm/dist/geometryCouties/data-1482909836074-rJV9O1-Hg.json',
        // '吉林': '/asset/get/s/data-1482909832739-rJ-cdy-Hx.json',
        // '黑龙江': '/asset/get/s/data-1482909803892-Hy4__J-Sx.json',
        '江苏': '../crm/dist/geometryCouties/data-1482909823260-HkDtOJZBx.json',
        // '浙江': '/asset/get/s/data-1482909960637-rkZMYkZBx.json',
        // '安徽': '/asset/get/s/data-1482909768458-HJlU_yWBe.json',
        // '福建': '/asset/get/s/data-1478782908884-B1H6yezWe.json',
        // '江西': '/asset/get/s/data-1482909827542-r12YOJWHe.json',
        '山东': '../crm/dist/geometryCouties/data-1482909892121-BJ3auk-Se.json',
        // '河南': '/asset/get/s/data-1482909807135-SJPudkWre.json',
        // '湖北': '/asset/get/s/data-1482909813213-Hy6u_kbrl.json',
        // '湖南': '/asset/get/s/data-1482909818685-H17FOkZSl.json',
        '广东': '../crm/dist/geometryCouties/data-1482909784051-BJgwuy-Sl.json',
        '广西': '../crm/dist/geometryCouties/data-1482909787648-SyEPuJbSg.json',
        // '海南': '/asset/get/s/data-1482909796480-H12P_J-Bg.json',
        '四川': '../crm/dist/geometryCouties/data-1482909931094-H17eKk-rg.json',
        '贵州': '../crm/dist/geometryCouties/data-1482909791334-Bkwvd1bBe.json',
        '云南': '../crm/dist/geometryCouties/data-1482909957601-HkA-FyWSx.json',
        // '西藏': '/asset/get/s/data-1482927407942-SkOV6Qbrl.json',
        // '陕西': '/asset/get/s/data-1508990012125-SyVBnCCab.json',
        // '甘肃': '/asset/get/s/data-1482909780863-r1aIdyWHl.json',
        // '青海': '/asset/get/s/data-1482909853618-B1IiOyZSl.json',
        // '宁夏': '/asset/get/s/data-1482909848690-HJWiuy-Bg.json',
        // '新疆': '/asset/get/s/data-1482909952731-B1YZKkbBx.json',
        '北京': '../crm/dist/geometryCouties/data-1482818963027-Hko9SKJrg.json',
        '天津': '../crm/dist/geometryCouties/data-1482909944620-r1-WKyWHg.json',
        '湖南':'../crm/dist/geometryCouties/data-1482946957601-HkA-HN.json',
        // '重庆': '/asset/get/s/data-1482909775470-HJDIdk-Se.json',
        // '香港': '/asset/get/s/data-1461584707906-r1hSmtsx.json',
        // '澳门': '/asset/get/s/data-1482909771696-ByVIdJWBx.json'
    };

    // 地图显示属性设置
    var mapOption = {
        tooltip: {
            trigger: 'item',
            formatter: "{b}"
        },
        animation: false,//设置切换时全国和省时不带闪动
        geo: {
	        map: 'china',
	        label: {
	            emphasis: {
	                show: true
	            }
	        },
	        //是否可以点击鼠标、滚轮缩放
	        roam: false,
	    },
        series:
            [
            ]
    };
	$("#mask").hide();
    if (pageStatusInfo['showLevel'] == SHOW_LEVEL_CHINA) {
    	mapOption.series.splice(0,3);
        var item0 = mapSeries();
        mapOption.series.push(item0);
    	mapOption.geo.map = SHOW_LEVEL_CHINA;
        mapOption.series[0].mapType = SHOW_LEVEL_CHINA;
        mapOption.series[0].data = openedProvinceMunicipality;
        var item1 = lineSeries();
        mapOption.series.push(item1);
        var item2 = citySeries();
        mapOption.series.push(item2);
        timer_china_beat = setInterval("nocease()","2000");
        mapChart.setOption(mapOption,true);
        $("#mapHeight").hide();
        $("#main9").show();
    } else if (pageStatusInfo['showLevel'] == SHOW_LEVEL_PROVINCE) {
    	clearInterval(timer_china_beat); 
        var mapType = pageStatusInfo['provinceName'];
        mapOption.geo.map = mapType;
        clearInterval(timer_china_beat); 
        mapOption.series.splice(0,3);
        var item0 = mapSeries();
        mapOption.series.push(item0);
        mapOption.series[0].mapType = mapType;
        mapOption.series[0].data = openedCity;
        $.get(provinces[mapType], function(geoJson) {
            echarts.registerMap(mapType, geoJson);
            mapChart.setOption(mapOption,true);
        });
        $("#mapHeight").hide();
        $("#main9").show();
    } else if(pageStatusInfo['showLevel'] == SHOW_LEVEL_CITY) {
    	clearInterval(timer_china_beat);
        // 切换高德专用显示容器
        drawGaodeMap(pageStatusInfo);
        $("#main9").hide();
        $("#mapHeight").show();
    }
};
var mapSeries = function(){
	var item = {
                    type: 'map',
                    coordinateSystem: 'geo',
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: false
                        }
                    },
                    effect: {
		                show: true,
		                period: 6,
		                trailLength: 0,
		            },
                    itemStyle: {
	                    normal: {
	                    areaColor: '#fff',
	                    //borderColor: '#fff'
                  	},
	                  emphasis: {
	                    areaColor: '#fff'
	                  }
                    },
                    animation: false
                }
      return item;
}
var lineSeries = function(){
	var item = {
                name: 'rode',
                type: 'lines',
                coordinateSystem: 'geo',
                effect: {
                  show: true,
                  period: 6,
                  trailLength: 0,
                },
                lineStyle: {
                  normal: {
                    color: 'rgba(0,0,0,0)',
                    width: 1,
                    opacity: 0.4,
                    curveness: 0.2
                  }
                }
  	}
	return item;
}
var citySeries = function(){
	var item = {
                name: 'city',
                type: 'effectScatter',
                coordinateSystem: 'geo',
                symbolSize: 15,
				showEffectOn: 'render',
				animation: true,
				//symbol: 'emptyCircle',
				rippleEffect: {
				  brushType: 'stroke'
				},
				hoverAnimation: true,
				label: {
				  normal: {
				    formatter: '{b}',
				    position: 'right',
				    show: true
				  }
				},
				itemStyle: {
				  normal: {
				    color: '#fb8800',
				    shadowBlur: 10,
				    shadowColor: '#FFFFFF'
				  }
				},
              }
      return item;
}
var geoCoordMap = {
    '海门':[121.15,31.89],
    '鄂尔多斯':[109.781327,39.608266],
    '招远':[120.38,37.35],
    '舟山':[122.207216,29.985295],
    '齐齐哈尔':[123.97,47.33],
    '盐城':[120.13,33.38],
    '赤峰':[118.87,42.28],
    '青岛':[120.33,36.07],
    '乳山':[121.52,36.89],
    '金昌':[102.188043,38.520089],
    '泉州':[118.58,24.93],
    '莱西':[120.53,36.86],
    '日照':[119.46,35.42],
    '胶南':[119.97,35.88],
    '南通':[121.05,32.08],
    '拉萨':[91.11,29.97],
    '云浮':[112.02,22.93],
    '梅州':[116.1,24.55],
    '文登':[122.05,37.2],
    '上海':[121.48,31.22],
    '攀枝花':[101.718637,26.582347],
    '威海':[122.1,37.5],
    '承德':[117.93,40.97],
    '厦门':[118.1,24.46],
    '汕尾':[115.375279,22.786211],
    '潮州':[116.63,23.68],
    '丹东':[124.37,40.13],
    '太仓':[121.1,31.45],
    '曲靖':[103.79,25.51],
    '烟台':[121.39,37.52],
    '福州':[119.3,26.08],
    '瓦房店':[121.979603,39.627114],
    '即墨':[120.45,36.38],
    '抚顺':[123.97,41.97],
    '玉溪':[102.52,24.35],
    '张家口':[114.87,40.82],
    '阳泉':[113.57,37.85],
    '莱州':[119.942327,37.177017],
    '湖州':[120.1,30.86],
    '汕头':[116.69,23.39],
    '昆山':[120.95,31.39],
    '宁波':[121.56,29.86],
    '湛江':[110.359377,21.270708],
    '揭阳':[116.35,23.55],
    '荣成':[122.41,37.16],
    '连云港':[119.16,34.59],
    '葫芦岛':[120.836932,40.711052],
    '常熟':[120.74,31.64],
    '东莞':[113.75,23.04],
    '河源':[114.68,23.73],
    '淮安':[119.15,33.5],
    '泰州':[119.9,32.49],
    '南宁':[108.33,22.84],
    '营口':[122.18,40.65],
    '惠州':[114.4,23.09],
    '江阴':[120.26,31.91],
    '蓬莱':[120.75,37.8],
    '韶关':[113.62,24.84],
    '嘉峪关':[98.289152,39.77313],
    '广州':[113.23,23.16],
    '延安':[109.47,36.6],
    '太原':[112.53,37.87],
    '清远':[113.01,23.7],
    '中山':[113.38,22.52],
    '昆明':[102.73,25.04],
    '寿光':[118.73,36.86],
    '盘锦':[122.070714,41.119997],
    '长治':[113.08,36.18],
    '深圳':[114.07,22.62],
    '珠海':[113.52,22.3],
    '宿迁':[118.3,33.96],
    '咸阳':[108.72,34.36],
    '铜川':[109.11,35.09],
    '平度':[119.97,36.77],
    '佛山':[113.11,23.05],
    '海口':[110.35,20.02],
    '江门':[113.06,22.61],
    '章丘':[117.53,36.72],
    '肇庆':[112.44,23.05],
    '大连':[121.62,38.92],
    '临汾':[111.5,36.08],
    '吴江':[120.63,31.16],
    '石嘴山':[106.39,39.04],
    '沈阳':[123.38,41.8],
    '苏州':[120.62,31.32],
    '茂名':[110.88,21.68],
    '嘉兴':[120.76,30.77],
    '长春':[125.35,43.88],
    '胶州':[120.03336,36.264622],
    '银川':[106.27,38.47],
    '张家港':[120.555821,31.875428],
    '三门峡':[111.19,34.76],
    '锦州':[121.15,41.13],
    '南昌':[115.89,28.68],
    '柳州':[109.4,24.33],
    '三亚':[109.511909,18.252847],
    '自贡':[104.778442,29.33903],
    '吉林':[126.57,43.87],
    '阳江':[111.95,21.85],
    '泸州':[105.39,28.91],
    '西宁':[101.74,36.56],
    '宜宾':[104.56,29.77],
    '呼和浩特':[111.65,40.82],
    '成都':[104.06,30.67],
    '大同':[113.3,40.12],
    '镇江':[119.44,32.2],
    '桂林':[110.28,25.29],
    '张家界':[110.479191,29.117096],
    '宜兴':[119.82,31.36],
    '北海':[109.12,21.49],
    '西安':[108.95,34.27],
    '金坛':[119.56,31.74],
    '东营':[118.49,37.46],
    '牡丹江':[129.58,44.6],
    '遵义':[106.9,27.7],
    '绍兴':[120.58,30.01],
    '扬州':[119.42,32.39],
    '常州':[119.95,31.79],
    '潍坊':[119.1,36.62],
    '重庆':[106.54,29.59],
    '台州':[121.420757,28.656386],
    '南京':[118.78,32.04],
    '滨州':[118.03,37.36],
    '贵阳':[106.71,26.57],
    '无锡':[120.29,31.59],
    '本溪':[123.73,41.3],
    '克拉玛依':[84.77,45.59],
    '渭南':[109.5,34.52],
    '马鞍山':[118.48,31.56],
    '宝鸡':[107.15,34.38],
    '焦作':[113.21,35.24],
    '句容':[119.16,31.95],
    '北京':[116.46,39.92],
    '徐州':[117.2,34.26],
    '衡水':[115.72,37.72],
    '包头':[110,40.58],
    '绵阳':[104.73,31.48],
    '乌鲁木齐':[87.68,43.77],
    '枣庄':[117.57,34.86],
    '杭州':[120.19,30.26],
    '淄博':[118.05,36.78],
    '鞍山':[122.85,41.12],
    '溧阳':[119.48,31.43],
    '库尔勒':[86.06,41.68],
    '安阳':[114.35,36.1],
    '开封':[114.35,34.79],
    '济南':[117,36.65],
    '德阳':[104.37,31.13],
    '温州':[120.65,28.01],
    '九江':[115.97,29.71],
    '邯郸':[114.47,36.6],
    '临安':[119.72,30.23],
    '兰州':[103.73,36.03],
    '沧州':[116.83,38.33],
    '临沂':[118.35,35.05],
    '南充':[106.110698,30.837793],
    '天津':[117.2,39.13],
    '富阳':[119.95,30.07],
    '泰安':[117.13,36.18],
    '诸暨':[120.23,29.71],
    '郑州':[113.65,34.76],
    '哈尔滨':[126.63,45.75],
    '聊城':[115.97,36.45],
    '芜湖':[118.38,31.33],
    '唐山':[118.02,39.63],
    '平顶山':[113.29,33.75],
    '邢台':[114.48,37.05],
    '德州':[116.29,37.45],
    '济宁':[116.59,35.38],
    '荆州':[112.239741,30.335165],
    '宜昌':[111.3,30.7],
    '义乌':[120.06,29.32],
    '丽水':[119.92,28.45],
    '洛阳':[112.44,34.7],
    '秦皇岛':[119.57,39.95],
    '株洲':[113.16,27.83],
    '石家庄':[114.48,38.03],
    '莱芜':[117.67,36.19],
    '常德':[111.69,29.05],
    '保定':[115.48,38.85],
    '湘潭':[112.91,27.87],
    '金华':[119.64,29.12],
    '岳阳':[113.09,29.37],
    '长沙':[113,28.21],
    '衢州':[118.88,28.97],
    '廊坊':[116.7,39.53],
    '菏泽':[115.480656,35.23375],
    '合肥':[117.27,31.86],
    '武汉':[114.31,30.52],
    '大庆':[125.03,46.58],
    '乌兰察布':[114.21,41.74],
    '黔东南苗族侗族自治州':[107.38,26.55]
};
//根据data得到每个data中城市的坐标
var convertData = function (data) {
    var res = [];
    for (var i = 0; i < data.length; i++) {
        var fromCoord = geoCoordMap[data[i]['name']];//获取城市的坐标 source
        var toCoord = geoCoordMap[data[i]['value']];//获取城市的坐标 destination
        if (fromCoord && toCoord) {
            res.push({
                fromName: data[i]['name'],
                toName: data[i]['value'],
                coords: [fromCoord, toCoord]
            });
        }
    }
    return res;
};
//根据data得到放射光标效果图。如果起始城市没有值的话，就只显示目的城市
var convertData1 = function (data) {
    var res = [];
    for (var i = 0; i < data.length; i++) {
        var geoCoord = geoCoordMap[data[i]['name']];
        var geoCoord1 = geoCoordMap[data[i]['value']];
        if (geoCoord) 
        {
            res.push({
                name: data[i]['name'],
                value: geoCoord.concat(data[i]['value']),
                selected:data[i]['selected'],
                cityId:data[i]['cityId'],
                province_id:data[i]['province_id'],
                citycode:data[i]['citycode'],
                keys:'beatPic'
            });
        }
        if(geoCoord1)
        {
            res.push({
                name: data[i]['value'],
                value: geoCoord1.concat(data[i]['name']),
                selected:data[i]['selected'],
                cityId:data[i]['cityId'],
                province_id:data[i]['province_id'],
                citycode:data[i]['citycode'],
                keys:'beatPic'
            })
        }
    }
    return res;
};
//定义数据
var dataArray=new Array();
dataArray[0]=[{name:'上海',value:'广州'}];
dataArray[1]=[{name:'北京',value:'深圳'}];
dataArray[2]=[{name:'深圳',value:'天津'}];
dataArray[3]=[{name:'天津',value:'沈阳'}];
dataArray[4]=[{name:'沈阳',value:'贵阳'}];
dataArray[5]=[{name:'贵阳',value:'昆明'}];
dataArray[6]=[{name:'昆明',value:'乌兰察布'}];
dataArray[7]=[{name:'乌兰察布',value:'黔东南苗族侗族自治州'}];
dataArray[8]=[{name:'黔东南苗族侗族自治州',value:'上海'}];
dataArray[9]=[{name:'上海',value:'长沙'}];

//一直要执行的函数
function nocease(){
    var datas = [];
    getBeatData(datas,beatData);
    //随机取1-5
    //datas=dataArray[Math.floor(Math.random() * dataArray.length + 1)-1];
    var option = mapChart.getOption();
    if(datas[0].name)
    {
    option.series[1].data = convertData(datas);
    option.series[2].data = convertData1(datas);
    }
    else{
    option.series[1].data = null;
    option.series[2].data = convertData1(datas); 
    }
    mapChart.setOption(option,true);
}
function getBeatJson(){
		doManager("dynamicManager", "getDailyFirstOrderCity",[], function (data, textStatus, XMLHttpRequest) {
	            if (data.result) {
	                var jsonData = JSON.parse(data.data);
	                beatData = jsonData;
	            }
	   }, false);
}
//一直要执行的函数
function nocease2(){
	var datas = [];
    getBeatData(datas,beatData);
    //随机取1-5
    //datas=dataArray[Math.floor(Math.random() * dataArray.length + 1)-1];
    var option = fullScreenChart.getOption();
   if(datas[0].name)
    {
    option.series[1].data = convertData(datas);
    option.series[2].data = convertData1(datas);
    }
    else{
    option.series[1].data = null;
    option.series[2].data = convertData1(datas); 
    }
    fullScreenChart.setOption(option,true);
}
var getBeatData = function(datas,jsonData){
	for(var i=1;i<jsonData['daily'].length;i++){
		var object = new Object();
		object.name = jsonData['daily'][i]['city_name'];
		object.value = jsonData['daily'][i-1]['city_name'];
		object.citycode = jsonData['daily'][i]['cityno'];
		object.selected = true;
		datas.push(object);
	};
	$.each(datas, function (idx, val) {
		if(isMunicipality(val['name']) == true){
	    	var index = findArray(openedProvinceMunicipality, {name: val['name']});
			val['province_id'] = openedProvinceMunicipality[index].province_id;
		}else{
			var index = findArray(openedCity, {name: val['name']});
			val['cityId'] = openedCity[index].cityId;
		}
	  });
}
// 画高德地图
var drawGaodeMap = function (pageStatusInfo) {
    //初始化地图对象，加载地图
    var amap = new AMap.Map("mapContainer", {
        resizeEnable: true,
        zoom: 15
    });
    // 设置地图中心
    amap.setCity(pageStatusInfo.cityName);
    // 获取地图显示数据
    var storeServiceRange = getGaodeMapData(pageStatusInfo);
    var cityId = pageStatusInfo.cityId;
    var cityName = pageStatusInfo.cityName;
    /*
    $.each(storeServiceRange, function (idx, val) {
        var serviceCoordinates = val.vertex;
        var storeId = val.store_id;
        var storeNo = val.storeNo;
        var storeName = val.storeName;
        var storePosition = val.position;//门店位置
            //展示门店位置==
		var storePositionMarker = new AMap.Marker({ //添加自定义点标记
	        map: amap,
	        position: storePosition, //基点位置
	        icon:"../aboutMap/dist/img/splogo.png",
	        offset: new AMap.Pixel(-10, -10), //相对于基点的偏移位置
	        extData:{"storeName":storeName,"storeId":storeId}
	       // draggable: true,  //是否可拖动
	        //content: '<div class="marker-route marker-marker-bus-from">'+storeName+'</div>'   //自定义点标记覆盖物内容
	    });
        storePositionMarker.on("mouseover",function(e){
			//var storeName = e.target.getExtData().storeName;
			//e.target.setTitle(storeName);
			e.target.getExtData().marker.show();
		});
        storePositionMarker.on("click",function(e){
			  var storeId = e.target.getExtData().storeId;
			  var provinceId = pageStatusInfo.provinceId == ""?"":pageStatusInfo.provinceId;
			  var url = "current_shopkeeper.html?p=" + encode64(provinceId) + "&c=" + encode64(cityId) + "&s=" + encode64(storeId) + "&cn=" + encode64(cityName) + "&zm=" + encode64(amap.getZoom()) + "&ln="+encode64(curr_user.name)+"&f=" + encode64("1")+"&fs="+encode64(pageStatusInfo.targets);
			  window.location.href=url; 
		});
    });
    */
        $.each(storeServiceRange, function (idx, val) {
        var serviceCoordinates = val.vertex;
        var storeId = val.store_id;
        var storeNo = val.storeNo;
        var storeName = val.storeName;
        var storePosition = val.position;//门店位置
        //服务范围多边形
        var servicePolygon = new AMap.Polygon({
            map: amap,
            zIndex:2000,
            strokeColor: "#5c69cc", //线颜色
            strokeOpacity: 0.9, //线透明度
            strokeWeight: 2,    //线宽
            fillColor: "#046ad3", //填充色
            fillOpacity: 0.1,//填充透明度
            path: serviceCoordinates
            // extData:{"storeName":storeName,"marker":storeMarker,"storeId":storeId}
        });
        var storePositionMarker = new AMap.Marker({ //添加自定义点标记
            map: amap,
            position: storePosition, //基点位置
            icon:"../aboutMap/dist/img/splogo.png",
            offset: new AMap.Pixel(-10, -10), //相对于基点的偏移位置
            extData:{"storeName":storeName,"storeId":storeId}
            // draggable: true,  //是否可拖动
            //content: '<div class="marker-route marker-marker-bus-from">'+storeName+'</div>'   //自定义点标记覆盖物内容
        });
        var storeMarker = new AMap.Marker({ //添加自定义点标记
            visible: false,
            map: amap,
            zIndex:1000,
            position: servicePolygon.getBounds().getCenter(), //基点位置
            content: '<div class="marker-route marker-marker-bus-from">' + storeName + '</div>',
            extData:{"storeName":storeName,"marker":storeMarker,"storeId":storeId}
        });
        servicePolygon.setExtData({"storeName": storeName, "marker": storeMarker, "storeId": storeId});
        storePositionMarker.setExtData({"storeName":storeName,"storeId":storeId,"marker": storeMarker,});
        servicePolygon.on("mouseover", function (e) {
                e.target.getExtData().marker.show();
        });
        servicePolygon.on("mouseout", function (e) {
            e.target.getExtData().marker.hide();
        });
        // 点击事件
        servicePolygon.on("click", function (e) {
            var storeId = e.target.getExtData().storeId;
            var provinceId = pageStatusInfo.provinceId == ""?"":pageStatusInfo.provinceId;
            var url = "current_shopkeeper.html?p=" + encode64(provinceId) + "&c=" + encode64(cityId) + "&s=" + encode64(storeId) + "&cn=" + encode64(cityName) + "&zm=" + encode64(amap.getZoom()) + "&ln="+encode64(curr_user.name)+"&ln="+encode64(curr_user.name)+"&f=" + encode64("1")+"&fs="+encode64(pageStatusInfo.targets);
            window.location.href = url;
        });
        storePositionMarker.on("mouseover",function(e){
            e.target.getExtData().marker.show();
        });
        storePositionMarker.on("mouseout",function(e){
            e.target.getExtData().marker.hide();
        });
        storePositionMarker.on("click",function(e){
            var storeId = e.target.getExtData().storeId;
            var provinceId = pageStatusInfo.provinceId == ""?"":pageStatusInfo.provinceId;
            var url = "current_shopkeeper.html?p=" + encode64(provinceId) + "&c=" + encode64(cityId) + "&s=" + encode64(storeId) + "&cn=" + encode64(cityName) + "&zm=" + encode64(amap.getZoom()) + "&ln="+encode64(curr_user.name)+"&f=" + encode64("1")+"&fs="+encode64(pageStatusInfo.targets);
            window.location.href=url;
        });
        storeMarker.on("click",function(e){
			  var storeId = e.target.getExtData().storeId;
			  var provinceId = pageStatusInfo.provinceId == ""?"":pageStatusInfo.provinceId;
			  var url = "current_shopkeeper.html?p=" + encode64(provinceId) + "&c=" + encode64(cityId) + "&s=" + encode64(storeId) + "&cn=" + encode64(cityName) + "&zm=" + encode64(amap.getZoom()) + "&ln="+encode64(curr_user.name)+"&f=" + encode64("1")+"&fs="+encode64(pageStatusInfo.targets);
			  window.location.href=url; 
		});
    });
    // 设置缩放级别
    if (pageStatusInfo.zoomLevel) {
        amap.setZoom(pageStatusInfo.zoomLevel);
    }
}
// 获取高德地图数据
var getGaodeMapData = function (pageStatusInfo) {
    var cacheKey = CACHE_HEADER_STORE_SERVICE_RANGE + pageStatusInfo.getCacheKey();
    // 从缓存获取数据
    var storeServiceRange = JsCache.get(cacheKey);
    if (storeServiceRange) {
        //console.log('show store service range base on js cache.')
    } else {
        var cityId = pageStatusInfo.cityId;
        doManager("mongoDBManager", "getAllStoreServiceAreaOfContry", [cityId], function (data, textStatus, XMLHttpRequest) {
            if (data.result) {
                var jsonData = JSON.parse(data.data);
                if (jsonData.code == 200) {
                    JsCache.set(cacheKey, jsonData.data);
                    storeServiceRange = jsonData.data;
                }
            }
        }, false);
    }
    return storeServiceRange;
};
// 近七日GMV走势图
var getProfitRangeForStoreWeek = function (pageStatusInfo) {
    var cacheKey = CACHE_HEADER_PROFIT_STORE + pageStatusInfo.getCacheKey();
    // 从缓存获取数据
    var cityProfitStore = JsCache.get(cacheKey);
    if (cityProfitStore) {
        //console.log('show city profit Store graph base on js cache.')
        showProfitRangeForStoreWeek(cityProfitStore);
    } else {
        // 准备服务端数据请求参数
        var reqestParameter = {
            month:pageStatusInfo.currentMonth,
            year:pageStatusInfo.currentYear,
            provinceId:pageStatusInfo.provinceId,
            cityId:pageStatusInfo.cityId
        }
        //console.log('\trequest: city profit Store. ');
        //console.log(reqestParameter);
        // 近七日GMV走势图
        var startTime = new Date().getTime();
        doManager("massOrderItemManager", "getProfitRangeForStoreWeek",[reqestParameter],
            function(data, textStatus, XMLHttpRequest) {
                if (data.result) {
                    var resultJson = JSON.parse(data.data);
                    showProfitRangeForStoreWeek(resultJson);
                    JsCache.set(cacheKey, resultJson);
                }
            });
        //console.log('request city rank gmv data from server in ' + (new Date().getTime() - startTime) + ' millisecond');
    }
};
// 显示近7天GMV走势
var showProfitRangeForStoreWeek = function (profitStoreRange) {
  	var data = [];
  	var data2 = [];
  	var listcount=eval(profitStoreRange['lst_data']).length;
	dataBJ.splice(0,dataBJ.length);
	dataSH.splice(0,dataSH.length);
	dataUH.splice(0,dataUH.length);
	dataVH.splice(0,dataVH.length);
	dataWH.splice(0,dataWH.length);
	dataXH.splice(0,dataXH.length);
	dataYH.splice(0,dataYH.length);
	titleTH.splice(0,titleTH.length);
    $.each(eval(profitStoreRange['lst_data']), function (idx, element) {
    	if(element.customer_count==undefined){
    		data.pushNoRepeat(element.customer_count);
    	}else{
    		data.pushNoRepeat(element.customer_count);
    	}
    	if(element.order_sign_date==undefined){
    		titleTH.pushNoRepeat(element.order_sign_date.substring(5,element.order_sign_date.length));
    	}else{
    		titleTH.pushNoRepeat(element.order_sign_date.substring(5,element.order_sign_date.length));
    	}
    });
    $.each(eval(titleTH), function (idxi, ele) {
    	$.each(eval(profitStoreRange['lst_data']), function (idx, element) {
    		var real_profit = (element.total_profit - element.return_profit - element.order_fee).toFixed(2);
	    	var temp = [];
			temp.push(element.gmv_price.toFixed(2));
	    	temp.push(real_profit);
			temp.push(element.customer_count);
			temp.push(element.store_name);
			temp.push(element.city_name);
			temp.push(element.order_sign_date);
			temp.push(element.store_code);
			temp.push(element.cityid);
			var isBJTJSH = false;
			if(element.store_city_code=="0010"||element.store_city_code=="0021"||element.store_city_code=="0022"){
				isBJTJSH = true;
			}
			if(!(element.order_sign_date=="2018-12-18"&&element.store_name=="金融街店")&&isBJTJSH){
				if(element.order_sign_date.indexOf(ele)>0&&idxi==0){
				dataBJ.push(temp);
				}else if(element.order_sign_date.indexOf(ele)>0&&idxi==1){
					dataSH.push(temp);
				}else if(element.order_sign_date.indexOf(ele)>0&&idxi==2){
					dataUH.push(temp);
				}else if(element.order_sign_date.indexOf(ele)>0&&idxi==3){
					dataVH.push(temp);
				}else if(element.order_sign_date.indexOf(ele)>0&&idxi==4){
					dataWH.push(temp);
				}else if(element.order_sign_date.indexOf(ele)>0&&idxi==5){
					dataXH.push(temp);
				}else if(element.order_sign_date.indexOf(ele)>0&&idxi==6){
					dataYH.push(temp);
				}
			}
    	});
    
    });
    var result = [];
	result.push(titleTH.slice(4,7));
	result.push(titleTH.slice(0,4));
    cityProfitRangeOption.series[0].data = dataBJ;
    cityProfitRangeOption.series[0].name = titleTH[0];
    cityProfitRangeOption.series[1].data = dataSH;
    cityProfitRangeOption.series[1].name = titleTH[1];
    cityProfitRangeOption.series[2].data = dataUH;
    cityProfitRangeOption.series[2].name = titleTH[2];
    cityProfitRangeOption.series[3].data = dataVH;
    cityProfitRangeOption.series[3].name = titleTH[3];
    cityProfitRangeOption.series[4].data = dataWH;
    cityProfitRangeOption.series[4].name = titleTH[4];
    cityProfitRangeOption.series[5].data = dataXH;
    cityProfitRangeOption.series[5].name = titleTH[5];
    cityProfitRangeOption.series[6].data = dataYH;
    cityProfitRangeOption.series[6].name = titleTH[6];
    cityProfitRangeOption.legend[0].data = result[0];
    cityProfitRangeOption.legend[1].data = result[1];
    var max = Math.max.apply(null, data);
    var min = Math.min.apply(null, data);
    var average = (max - min)/listcount;
    if(average<1){
    	average = average*0.35;
    }else if(average>=1&&average<=10){
    	average = average*0.18;
    }else if(average>10&&average<=100){
    	average = average*0.07;
    }else if(average>100&&average<1000){
    	average = average*0.05;
    }
	cityProfitRangeOption.series[0].symbolSize = function(val){
		if(val[2]<50){
			return val[2]*average*6;
		}else{
			return val[2]*average;
		}
	};
	cityProfitRangeOption.series[1].symbolSize = function(val){
		if(val[2]<50){
			return val[2]*average*6;
		}else{
			return val[2]*average;
		}
	};
	cityProfitRangeOption.series[2].symbolSize = function(val){
		if(val[2]<50){
			return val[2]*average*6;
		}else{
			return val[2]*average;
		}
	};
	cityProfitRangeOption.series[3].symbolSize = function(val){
		if(val[2]<50){
			return val[2]*average*6;
		}else{
			return val[2]*average;
		}
	};
	cityProfitRangeOption.series[4].symbolSize = function(val){
		if(val[2]<50){
			return val[2]*average*6;
		}else{
			return val[2]*average;
		}
	};
	cityProfitRangeOption.series[5].symbolSize = function(val){
		if(val[2]<50){
			return val[2]*average*6;
		}else{
			return val[2]*average;
		}
	};
	cityProfitRangeOption.series[6].symbolSize = function(val){
		if(val[2]<50){
			return val[2]*average*6;
		}else{
			return val[2]*average;
		}
	};
	cityProfitRangeChart.setOption(cityProfitRangeOption,true);
};
// 近七日GMV走势图
var getCityRankDataGmv = function (pageStatusInfo) {
    var cacheKey = CACHE_HEADER_CITY_RANK_GVM + pageStatusInfo.getCacheKey();
    // 从缓存获取数据
    var cityRankDataGmv = JsCache.get(cacheKey);
    if (cityRankDataGmv) {
        //console.log('show city rank graph base on js cache.')
        showCityRankGmv(cityRankDataGmv);
    } else {
        // 准备服务端数据请求参数
        var reqestParameter = {
            month:pageStatusInfo.currentMonth,
            year:pageStatusInfo.currentYear,
            provinceId:pageStatusInfo.provinceId,
            cityId:pageStatusInfo.cityId
        }
        //console.log('\trequest: city rank gmv. ');
        //console.log(reqestParameter);
        // 近七日GMV走势图
        var startTime = new Date().getTime();
        doManager("dynamicManager", "getCityGMVRangeForWeek",[reqestParameter],
            function(data, textStatus, XMLHttpRequest) {
                if (data.result) {
                    var resultJson = JSON.parse(data.data);
                    showCityRankGmv(resultJson);
                    JsCache.set(cacheKey, resultJson);
                }
            });
        //console.log('request city rank gmv data from server in ' + (new Date().getTime() - startTime) + ' millisecond');
    }
};
// 显示近7天GMV走势
var showCityRankGmv = function (cityRankDataGmv) {
  	var data = [];
    var data1 = [];
    $.each(eval(cityRankDataGmv['lst_data']), function (idx, val) {
    	data.push(val['week_date']);
    	data1.push(val['week_gmv']);
    });
	cityRankGmvOption.xAxis[0].data = data.reverse();
    cityRankGmvOption.series[0].data = data1.reverse();
    cityRankGmvOption.title[0].text = "近30日毛利走势";
    cityRankChartGmv.setOption(cityRankGmvOption,true);
};
var getTwoTwoOneRankGmv = function(pageStatusInfo){
	    var cacheKey = CACHE_HEADER_TWOTWOONE_RANK_GVM + pageStatusInfo.getCacheKey();
    // 从缓存获取数据
    var twoTwoOneRankDataGmv = JsCache.get(cacheKey);
    if (twoTwoOneRankDataGmv) {
        //console.log('show twotwoone rank graph base on js cache.')
        showTwoTwoOneRankGmv(twoTwoOneRankDataGmv);
    } else {
        // 准备服务端数据请求参数
        var reqestParameter = {
            month:pageStatusInfo.currentMonth,
            year:pageStatusInfo.currentYear,
            provinceId:pageStatusInfo.provinceId,
            cityId:pageStatusInfo.cityId
        }
        //console.log('\trequest: city rank gmv. ');
        //console.log(reqestParameter);
        // 近七日221GMV走势图-修改为近七日毛利走势图
        var startTime = new Date().getTime();
        //doManager("dynamicManager", "getTwoTwoOneGMVRangeForWeek",[reqestParameter],
        doManager("massOrderItemManager", "getProfitRangeForWeek",[reqestParameter],
            function(data, textStatus, XMLHttpRequest) {
                if (data.result) {
                    var resultJson = JSON.parse(data.data);
                    showTwoTwoOneRankGmv(resultJson);
                    JsCache.set(cacheKey, resultJson);
                }
            });
        //console.log('request twotwoone rank gmv data from server in ' + (new Date().getTime() - startTime) + ' millisecond');
    }

}
// 显示近7天221GMV走势-修改为近30天毛利走势
var showTwoTwoOneRankGmv = function (twoTwoOneRankDataGmv) {
  	var data = [];
    var data1 = [];
    var data2 = [];
    var data3 = [];
    var data4 = [];
    $.each(eval(twoTwoOneRankDataGmv['lst_data']), function (idx, val) {
    	var real_profit = (val['total_profit'] - val['return_profit'] - val['order_fee']).toFixed(2);
    	data.push(val['week_date']);
    	data1.push(real_profit);
    });
    $.each(eval(twoTwoOneRankDataGmv['lst_data_bj']), function (idx, val) {
    	var real_profit = (val['total_profit'] - val['return_profit'] - val['order_fee']).toFixed(2);
    	data.push(val['week_date']);
    	data2.push(real_profit);
    });
    $.each(eval(twoTwoOneRankDataGmv['lst_data_tj']), function (idx, val) {
    	var real_profit = (val['total_profit'] - val['return_profit'] - val['order_fee']).toFixed(2);
    	//data.push(val['week_date']);
    	data3.push(real_profit);
    });
    $.each(eval(twoTwoOneRankDataGmv['lst_data_sh']), function (idx, val) {
    	var real_profit = (val['total_profit'] - val['return_profit'] - val['order_fee']).toFixed(2);
    	//data.push(val['week_date']);
    	data4.push(real_profit);
    });
	cityRankGmvOption.legend.data.slice(0,cityRankGmvOption.legend.data.length);
	cityRankGmvOption.xAxis[0].data = data.reverse();
	if(pageStatusInfo.provinceId==""&&pageStatusInfo.cityId==""){
		if(data2.length>0){
	    	cityRankGmvOption.series[0].data = data2.reverse();
	    	cityRankGmvOption.series[0].name = "北京";
	    	var dataLegend = new Object();
	    	dataLegend.name = "北京";
	    	var textStyle = new Object();
	    	textStyle.color = "#fff";
	    	dataLegend.textStyle = textStyle;
	    	cityRankGmvOption.legend.data.push(dataLegend);
	    }
	    if(data3.length>0){
		    cityRankGmvOption.series[1].data = data3.reverse();
		    cityRankGmvOption.series[1].name = "天津";
		    var dataLegend = new Object();
	    	dataLegend.name = "天津";
	    	var textStyle = new Object();
	    	textStyle.color = "#fff";
	    	dataLegend.textStyle = textStyle;
	    	cityRankGmvOption.legend.data.push(dataLegend);
	    }
	    if(data4.length>0){
		    cityRankGmvOption.series[2].data = data4.reverse();
		    cityRankGmvOption.series[2].name = "上海";
		    var dataLegend = new Object();
	    	dataLegend.name = "上海";
	    	var textStyle = new Object();
	    	textStyle.color = "#fff";
	    	dataLegend.textStyle = textStyle;
	    	cityRankGmvOption.legend.data.push(dataLegend);
	    }
	}else{
    var dataLegend = new Object();
    if(pageStatusInfo.cityName!=""){
		dataLegend.name = pageStatusInfo.cityName;
		cityRankGmvOption.series[0].name = pageStatusInfo.cityName;
    }else if(pageStatusInfo.cityName==""&&pageStatusInfo.provinceName!=""){
		dataLegend.name = pageStatusInfo.provinceName;
		cityRankGmvOption.series[0].name = pageStatusInfo.provinceName;
    }
	var textStyle = new Object();
	textStyle.color = "#fff";
	dataLegend.textStyle = textStyle;
	cityRankGmvOption.legend.data.push(dataLegend);
	cityRankGmvOption.series[0].data = [];
	cityRankGmvOption.series[0].data = data1.reverse();
	cityRankGmvOption.series[1].data = [];
	cityRankGmvOption.series[1].name = [];
	cityRankGmvOption.series[2].data = [];
	cityRankGmvOption.series[2].name = [];
	}
    cityRankGmvOption.title[0].text = "近30日毛利走势";
    cityRankChartGmv.setOption(cityRankGmvOption,true);
};
// 获取门店排名(GMV)数据
var getStoreRankDataGmv = function (pageStatusInfo) {
    var cacheKey = CACHE_HEADER_STORE_RANK_GMV + pageStatusInfo.getCacheKey();
    // 从缓存获取数据
    var storeRankDataGmv = JsCache.get(cacheKey);
    if (storeRankDataGmv) {
        //console.log('show store rank gmv graph base on js cache.')
        showStoreRankGmv(storeRankDataGmv);
    } else {
        // 准备服务端数据请求参数
        // 准备服务端数据请求参数
        var reqestParameter = {
            month:pageStatusInfo.currentMonth,
            year:pageStatusInfo.currentYear,
            provinceId:pageStatusInfo.provinceId,
            cityId:pageStatusInfo.cityId
        }
        //console.log('\trequest: store rank gmv. ');
        //console.log(reqestParameter);
        // 全国门店(GMV)排名
        var startTime = new Date().getTime();
        doManager("dynamicManager", "getLastMonthStoreRankingTop10",[reqestParameter,pageStatusInfo.pageInfo,null],
            function(data, textStatus, XMLHttpRequest) {
                if (data.result) {
                    var resultJson = JSON.parse(data.data);
                    showStoreRankGmv(resultJson);
                    //console.log(resultJson);
                    JsCache.set(cacheKey, resultJson);
                }
            });
        //console.log('request store rank data gmv from server in ' + (new Date().getTime() - startTime) + ' millisecond');
    }
};
// 显示门店排名(GMV)
var showStoreRankGmv = function (storeRankDataGmv) {
    var xAxis = [];
    var series = [];
    var extData = [];
    $.each(eval(storeRankDataGmv['gmv']), function (idx, val) {
        xAxis.push(val['city_name']+"-"+val['store_name']);
        series.push(parseInt(val['store_pesgmv']));
        //xAxis.push(val['store_id']);
        //xAxis.push(val['city_name']);
        extData.push({'storeId':parseInt(val['store_id']),'cityId':val['city_id'],'cityName':val['city_name']});
    });
    storeRankGmvOption.xAxis[0].data = xAxis;
    storeRankGmvOption.xAxis[0].extdata = extData;
    storeRankGmvOption.series[0].data = series;
    storeRankGmvOption.title[0].text="门店排名("+pageStatusInfo['currentMonth']+"月GMV)";
    storeRankChartGmv.setOption(storeRankGmvOption);
};
// 获取门店排名(订单量)数据--修改为门店排名(用户量)
var getStoreRankDataOrder = function (pageStatusInfo) {
    var cacheKey = CACHE_HEADER_STORE_RANK_ORDER + pageStatusInfo.getCacheKey();
    // 从缓存获取数据
    var storeRankDataOrder = JsCache.get(cacheKey);
    if (storeRankDataOrder) {
        //console.log('show store rank order graph base on js cache.')
        showStoreRankOrder(storeRankDataOrder);
    } else {
        // 准备服务端数据请求参数
        // 准备服务端数据请求参数
        var reqestParameter = {
            month:pageStatusInfo.currentMonth,
            year:pageStatusInfo.currentYear,
            provinceId:pageStatusInfo.provinceId,
            cityId:pageStatusInfo.cityId
        }
        //console.log('\trequest: store rank order. ');
        //console.log(reqestParameter);
        var startTime = new Date().getTime();
        // 门店排名(用户量)
        //doManager("dynamicManager", "queryCityOrderRankingTop10",[reqestParameter,pageStatusInfo.pageInfo,null],
        doManager("OrderManager", "queryCustomerCount",[reqestParameter,pageStatusInfo.pageInfo,null],
            function(data, textStatus, XMLHttpRequest) {
                if (data.result) {
                    var resultJson = JSON.parse(data.data);
                    showStoreRankOrder(resultJson);
                    JsCache.set(cacheKey, resultJson);
                }
            });
        //console.log('request store rank  order data from server in ' + (new Date().getTime() - startTime) + ' millisecond');
    }
};
// 显示门店排名(订单量)--修改为门店排名(用户量)
var showStoreRankOrder = function (storeRankDataOrder) {
    var xAxis = [];
    var series = [];
    var extData = [];
    $.each(eval(storeRankDataOrder['gmv']), function (idx, val) {
        //xAxis.push(val['city_name']+"-"+val['store_name']);
        var cityId = "";
        var cityname = "";
        $.each(eval(storeRankDataOrder['cityList']), function (idx, val2) {
        	if(val['city_code'].length == 3){
        		if(val2['cityno'].indexOf("00")>-1&&(val['city_code'] == val2['cityno'].substring(1,val2['cityno'].length))){
        			xAxis.push(val2['cityname']+"-"+val['store_name']);
        			cityId = val2['id'];
        			cityname = val2['cityname'];
        		}
        	}else if(val['city_code'].length > 3&&(val['city_code'] == val2['cityno'])){
        		xAxis.push(val2['cityname']+"-"+val['store_name']);
        		cityId = val2['id'];
        		cityname = val2['cityname'];
        	}
        });
        extData.push({'storeId':val['store_id'],'cityId':cityId,'cityName':cityname});
        series.push(parseInt(val['customer_count']));
    });
    storeRankOrderOption.title[0].text="门店排名("+pageStatusInfo['currentMonth']+"月消费用户量)";
    storeRankOrderOption.xAxis[0].data = xAxis;
    storeRankOrderOption.xAxis[0].extdata = extData;
    storeRankOrderOption.series[0].data = series;
    storeRankChartOrder.setOption(storeRankOrderOption);
};
// 获取国安侠排名(GMV)数据
var getGuoanManRankDataGMV = function (pageStatusInfo) {
    var cacheKey = CACHE_HEADER_GUOAN_MAN_RANK_GMV + pageStatusInfo.getCacheKey();
    // 从缓存获取数据
    var guoanManRankDataGMV = JsCache.get(cacheKey);
    if (guoanManRankDataGMV) {
        //console.log('show guoan man rank gmv graph base on js cache.')
        showGuoanManRankGmv(guoanManRankDataGMV);
    } else {
        // 准备服务端数据请求参数
        var reqestParameter = {
            month:pageStatusInfo.currentMonth,
            year:pageStatusInfo.currentYear,
            provinceId:pageStatusInfo.provinceId,
            cityId:pageStatusInfo.cityId
        }
        //console.log('\trequest: guoan man rank gmv. ');
        //console.log(reqestParameter);
        // 国安侠排名(GMV)
        var startTime = new Date().getTime();
        var pageInfo = new Object();//初始化分页第一页(查询五条)
		pageInfo.currentPage = "1";
		pageInfo.recordsPerPage = "8";
        doManager("dynamicManager", "queryAreaTradeByEmp",[reqestParameter,pageInfo,null],
            function(data, textStatus, XMLHttpRequest) {
                if (data.result) {
                    var resultJson = JSON.parse(data.data);
                    showGuoanManRankGmv(resultJson);
                    JsCache.set(cacheKey, resultJson);
                }
            });
        //console.log('request guoan man gmv data from server in ' + (new Date().getTime() - startTime) + ' millisecond');
    }
};
// 显示国安侠排名(GMV)
var showGuoanManRankGmv = function (guoanManRankDataGMV) {
 var guoanManRankChartGMV = echarts.init(document.getElementById('main5'));
  var xAxis = [];
    var series = [];
    var extData = [];
    var extData2 = [];
    $.each(eval(guoanManRankDataGMV['gmv']), function (idx, val) {
        xAxis.push(val['employee_a_name']+"-"+val['city_name']+"-"+val['store_name']);
        series.push(parseInt(val['pesgmv']));
        extData.push(val['employee_a_no']);
        extData2.push(val['cityno']);
    });
    guoanManRankGMVOption.yAxis[0].data = xAxis.reverse();
    guoanManRankGMVOption.xAxis[0].extdata = extData.reverse();
    guoanManRankGMVOption.xAxis[1].extdata = extData2.reverse();
    guoanManRankGMVOption.series[0].data = series.reverse();
    guoanManRankGMVOption.title[0].text="国安侠排名("+pageStatusInfo['currentMonth']+"月GMV)";
    guoanManRankChartGMV.setOption(guoanManRankGMVOption);
    // 事件绑定
};
// 获取商品排名数据
var getCommodityRankData = function (pageStatusInfo) {
    var cacheKey = CACHE_HEADER_COMMODITY_RANK + pageStatusInfo.getCacheKey();
    // 从缓存获取数据
    var commodityRankData = JsCache.get(cacheKey);
    if (commodityRankData) {
        //console.log('show commodity rank graph base on js cache.')
        showCommodityRank(commodityRankData);
    } else {
        // 准备服务端数据请求参数
        // 准备服务端数据请求参数
        var reqestParameter = {
            month:pageStatusInfo.currentMonth,
            year:pageStatusInfo.currentYear,
            provinceName:pageStatusInfo.provinceName,
            cityName:pageStatusInfo.cityName
        }
        //console.log('\trequest: commodity rank. ');
        //console.log(reqestParameter);
        var startTime = new Date().getTime();
        var pageInfo = new Object();//初始化分页第一页(查询五条)
		pageInfo.currentPage = "1";
		pageInfo.recordsPerPage = "80";
        doManager("dynamicManager", "queryProductCityOrder",[reqestParameter,pageInfo,null],
            function(data, textStatus, XMLHttpRequest) {
                if (data.result) {
                    var resultJson = JSON.parse(data.data);
                    showCommodityRank(resultJson);
                    //console.log(resultJson);
                    JsCache.set(cacheKey, resultJson);
                }
            });
        //console.log('request commodity rank data from server in ' + (new Date().getTime() - startTime) + ' millisecond');
    }
};
// 显示商品排名
var showCommodityRank = function (commodityRankData) {
	var maskImage = new Image();
    maskImage.src = '../crm/sjfx-group_files/180209101040.png';
    if(commodityRankData['gmv'].length>2){
	  	var commodityDataStr = '{"dataCloud":[';
	    $.each(eval(commodityRankData['gmv']), function (idx, val) {
	    	var ifContains = true;
	    	if(val['product_name'].indexOf('工资')!=-1 || val['product_name'].indexOf('过账')!=-1 ||
	    	val['product_name'].indexOf('工程')!=-1){
	    		ifContains = false;
	    	}
	    	if(ifContains){
	    		commodityDataStr+='{"name":"'+(val['product_name']+'').substring(0,6)+'","value":"'+val['product_count']+'","product_id":"'+val['product_id']+
	    			'","cityno":"'+val['cityno']+'","city_name":"'+val['city_name']+'","tooltips":"'+val['product_name']+'"},';
	    	}
	    });
	    if(commodityRankData['gmv'].length>0){
	    	commodityDataStr = commodityDataStr.substring(0,commodityDataStr.lastIndexOf(","));
	    }
	    commodityDataStr+="]}";
	    var commodityDataObject = JSON.parse(formatString(commodityDataStr));
	    maskImage.onload = function() {
			commodityRankOption.series[0].maskImage;
			commodityRankChart.setOption(commodityRankOption);//生成云图
		};
	    commodityRankOption.series[0].data = commodityDataObject.dataCloud;
    }
    
    commodityRankOption.title.text="商品销售云图("+pageStatusInfo['currentMonth']+"月销量)";
    commodityRankChart.setOption(commodityRankOption,true);
    // 事件绑定
};
var exchageValue = function(commodityRankData){
	$.each(eval(commodityRankData['gmv']), function (idx, val) {
        xAxis.push(val['product_name']);
        series.push(parseInt(val['product_count']));
    });
}
//获取频道排名(GMV)数据
var getChannelRankGmvData = function (pageStatusInfo) {
    var cacheKey = CACHE_HEADER_CHANNEL_RANK_GMV + pageStatusInfo.getCacheKey();
    // 从缓存获取数据
    var channelRankDataGmv = JsCache.get(cacheKey);
    if (channelRankDataGmv) {
        //console.log('show channel rank gmv graph base on js cache.')
        showChannelRankGmv(channelRankDataGmv);
    } else {
        // 准备服务端数据请求参数
        // 准备服务端数据请求参数
        var reqestParameter = {
            month:pageStatusInfo.currentMonth,
            year:pageStatusInfo.currentYear,
            provinceId:pageStatusInfo.provinceId,
            cityId:pageStatusInfo.cityId
        }
        //console.log('\trequest: channel rank gmv . ');
        //console.log(reqestParameter);
        // 频道排名(GMV)排名
        var startTime = new Date().getTime();
        doManager("dynamicManager", "queryTradeByChannelName",[reqestParameter,pageStatusInfo.pageInfo,null],
            function(data, textStatus, XMLHttpRequest) {
                if (data.result) {
                    var resultJson = JSON.parse(data.data);
                    showChannelRankGmv(resultJson);
                    //console.log(resultJson);
                    JsCache.set(cacheKey, resultJson);
                }
            });
        //console.log('request channel rank gmv data from server in ' + (new Date().getTime() - startTime) + ' millisecond');
    }
};
// 显示频道排名(GMV)
var showChannelRankGmv = function (channelRankDataGmv) {
 // 初始化频道排名(GMV)显示图
    var channelRankChartGMV = echarts.init(document.getElementById('main8'))
    // 柱状图属性：频道(GMV)排名
    var channelRankGMVOption = {
        title:[
		   {x: '3%', y: '0%',textStyle:{color:"#efefef",fontSize:"16"}},
		  ],
    tooltip : {
      trigger: 'axis',
      axisPointer: { // 坐标轴指示器，坐标轴触发有效
        "type": "shadow" // 默认为直线，可选为："line" | "shadow"
      }
    },
    calculable : true,
    grid: {
      left: '-12%',
      right: '4%',
      top: '12%',
      height: 230, //设置grid高度
      containLabel: true
    },
    xAxis : [
    {
      type : 'category',
      data : [],
      max: 4,
      position: 'top',
      splitLine: {
        show: false
      },
      axisLine: {
        show: false
      },
      axisTick:{
        show: false
      },
      axisLabel:{
    	  show: true,
          rotate:45,//倾斜度 -90 至 90 默认为0
          textStyle:{
            color:'#fff'
          },
          formatter: function(value) {
            if ((typeof(value)!="undefined")&&(value.length > 5)) {
              return value.substring(0, 5) + "...";
            } else {
              return value;
            }
          }
      }
    }
  ],
    yAxis : [
    {
      inverse: true,
      type : 'value',
      splitLine: {
        show: false
      },
      axisLine: {
        show: false
      },
      axisTick:{
        show: false
      },
      axisLabel:{
        /*textStyle:{
         color:'#f00'
         }*/
        "show": false
      }
    }
  ],
    series : [
    {
      name:'GMV',
      type:'bar',
      data:[],
      index:['a', 'b'] ,
      barWidth : 30,//柱图宽度
      barMaxWidth:30,//最大宽度
      barGap: 10,
      barCategoryGap: 10,
      cursor: 'default',
      itemStyle: {
        normal: {
          color: function(params1) {
            // build a color map as your need.
            var colorList1 = [
              //'#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
              '#0399d9','#00a7de','#00c3e7','#00ddf0','#13d4b2',
             // '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
            ];
            return colorList1[params1.dataIndex];
          },
          borderWidth: 0,
          barBorderRadius: [10, 10, 10, 10],
          label: {
            show: true,
            position: 'bottom',
            formatter: '{c}'
          }
        }
      }
    }
  ]
  };
    var xAxis = [];
    var series = [];
    var extData = [];
    $.each(eval(channelRankDataGmv['gmv']), function (idx, val) {
        xAxis.push(val['channel_name']);
        series.push(parseInt(val['order_amount']));
        //extData.push(parseInt(val['store_id']));
    });
    channelRankGMVOption.xAxis[0].data = xAxis;
    channelRankGMVOption.xAxis[0].extdata = extData;
    channelRankGMVOption.series[0].data = series;
    channelRankGMVOption.title[0].text="频道排名("+pageStatusInfo['currentMonth']+"月GMV)";
    channelRankChartGMV.setOption(channelRankGMVOption);
    // 事件绑定
};
//获取频道排名(订单量)数据
var getChannelRankOrderData = function (pageStatusInfo) {
    var cacheKey = CACHE_HEADER_CHANNEL_RANK_ORDER + pageStatusInfo.getCacheKey();
    // 从缓存获取数据
    var channelRankOrderData = JsCache.get(cacheKey);
    if (channelRankOrderData) {
        //console.log('show channel rank order graph base on js cache.')
        showChannelRankOrder(channelRankOrderData);
    } else {
        // 准备服务端数据请求参数
        var reqestParameter = {
            month:pageStatusInfo.currentMonth,
            year:pageStatusInfo.currentYear,
            provinceId:pageStatusInfo.provinceId,
            cityId:pageStatusInfo.cityId
        }
        //console.log('\trequest: channel rank order. ');
        //console.log(reqestParameter);
        var startTime = new Date().getTime();
        // 全国频道(订单量)排名
        doManager("dynamicManager", "queryOrderCountByChannelName",[reqestParameter,pageStatusInfo.pageInfo,null],
            function(data, textStatus, XMLHttpRequest) {
                if (data.result) {
                    var resultJson = JSON.parse(data.data);
                    showChannelRankOrder(resultJson);
                    //console.log(resultJson);
                    JsCache.set(cacheKey, resultJson);
                }
            });
        //console.log('request channel rank order data from server in ' + (new Date().getTime() - startTime) + ' millisecond');

    }
};
// 显示频道排名(订单量)
var showChannelRankOrder = function (channelRankOrderData) {
// 初始化频道排名(订单量)显示图
    var channelRankOrderChart = echarts.init(document.getElementById('main8'))
    // 柱状图属性：频道(订单量)排名
    var channelRankOrderOption = {
        title:[
      {x: '3%', y: '0',textStyle:{color:"#efefef",fontSize:"16"}},
    ],
    tooltip : {
      trigger: 'axis',
      axisPointer: { // 坐标轴指示器，坐标轴触发有效
        "type": "shadow" // 默认为直线，可选为："line" | "shadow"
      }
    },
    calculable : true,
    grid: {
      left: '-10%',
      right: '4%',
      top: '12%',
      height: 230, //设置grid高度
      containLabel: true
    },
    xAxis : [
      {
        type : 'category',
        data : [],
        max: 4,
        splitLine: {
          show: false
        },
        axisLine: {
          show: false
        },
        axisTick:{
          show: false
        },
        axisLabel:{
        	show: true,
            rotate:45,//倾斜度 -90 至 90 默认为0
            textStyle:{
              color:'#fff'
            },
            formatter: function(value) {
            if ((typeof(value)!="undefined")&&(value.length > 5)) {
              return value.substring(0, 5) + "...";
            } else {
              return value;
            }
          }
        }
      }
    ],
    yAxis : [
      {
        type : 'value',
        splitLine: {
          show: false
        },
        axisLine: {
          show: false
        },
        axisTick:{
          show: false
        },
        axisLabel:{
          /*textStyle:{
           color:'#f00'
           }*/
          "show": false
        }
      }
    ],
    series : [
      {
        name:'GMV',
        type:'bar',
        data: [],
        cursor: 'default',
        itemStyle: {
          normal: {
            color: function(params1) {
              // build a color map as your need.
              var colorList1 = [
                //'#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
                '#0399d9','#00a7de','#00c3e7','#00ddf0','#13d4b2',
                // '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
              ];
              return colorList1[params1.dataIndex];
            },
            barWidth : 30,//柱图宽度
            barMaxWidth:30,//最大宽度
            barGap: 13,
            barCategoryGap: 13,
            barBorderRadius: [10, 10, 10, 10],
            label: {
              show: true,
              position: 'top',
              formatter: '{c}'
            }
          }
        }
      }
    ]
  };
  var xAxis = [];
    var series = [];
    var extData = [];
    $.each(eval(channelRankOrderData['gmv']), function (idx, val) {
        xAxis.push(val['channel_name']);
        series.push(parseInt(val['order_count']));
        //extData.push(parseInt(val['store_id']));
    });
    channelRankOrderOption.xAxis[0].data = xAxis;
    channelRankOrderOption.xAxis[0].extdata = extData;
    channelRankOrderOption.series[0].data = series;
    channelRankOrderOption.title[0].text="频道排名("+pageStatusInfo['currentMonth']+"月订单量)";
    channelRankOrderChart.setOption(channelRankOrderOption);
    // 事件绑定
};
// 获取事业群排名数据
var getBusinessDepRankDataGmv = function (pageStatusInfo) {
    var cacheKey = CACHE_HEADER_BUSINESS_DEP_RANK_GMV + pageStatusInfo.getCacheKey();
    // 从缓存获取数据
    var businessDepRankDataGMV = JsCache.get(cacheKey);
    if (businessDepRankDataGMV) {
        //console.log('show business dep gmv rank graph base on js cache.')
        showBusinessDepRankGmv(businessDepRankDataGMV);
    } else {
        // 准备服务端数据请求参数
        // 准备服务端数据请求参数
        var reqestParameter = {
            month:pageStatusInfo.currentMonth,
            year:pageStatusInfo.currentYear,
            provinceId:pageStatusInfo.provinceId,
            cityId:pageStatusInfo.cityId
        }
        //console.log('\trequest: business dep gmv. ');
        //console.log(reqestParameter);
        var startTime = new Date().getTime();
        // 事业群排名
        doManager("dynamicManager", "queryTradeByDepName",[reqestParameter,null,null],
            function(data, textStatus, XMLHttpRequest) {
                if (data.result) {
                    var resultJson = JSON.parse(data.data);
                    showBusinessDepRankGmv(resultJson);
                    //console.log(resultJson);
                    JsCache.set(cacheKey, resultJson);
                }
            });
        //console.log('request business dep rank gmv data from server in ' + (new Date().getTime() - startTime) + ' millisecond');
    }
};
// 显示事业群排名
var showBusinessDepRankGmv = function (businessDepRankDataGMV) {
    var xAxis = [];
    var series = [];
    $.each(eval(businessDepRankDataGMV['gmv']), function (idx, val) {
    	var depmap = {};
		depmap["value"]=parseInt(val['order_amount']);
		depmap["name"]=val['dep_name'];
		series.push(depmap);
		xAxis.push(val['dep_name']);
    });
    businessDepRankGMVOption.legend.data = xAxis;
    businessDepRankGMVOption.series[0].data = series;
    businessDepRankGMVOption.title.text="事业群排名("+pageStatusInfo['currentMonth']+"月GMV)";
    businessDepRankChartGMV.setOption(businessDepRankGMVOption);
};
var getCustomerNewAndConsumeMonthUser = function(pageStatusInfo){
	var cacheKey = CACHE_HEADER_CUSTOMER_CONSUME_MONTH + pageStatusInfo.getCacheKey();
    // 从缓存获取数据
    var customerNewAndConsume = JsCache.get(cacheKey);
    if (customerNewAndConsume) {
        //console.log('show customer consume graph base on js cache.')
        showCustomerNew(customerNewAndConsume);
    } else {
        // 准备服务端数据请求参数
        // 准备服务端数据请求参数
        var reqestParameter = {
            month:pageStatusInfo.currentMonth,
            year:pageStatusInfo.currentYear,
            provinceId:pageStatusInfo.provinceId,
            cityId:pageStatusInfo.cityId
        }
        //console.log('\trequest: customer consume. ');
        //console.log(reqestParameter);
        var startTime = new Date().getTime();
        // 查询拉新和消费用户量
        doManager("dynamicManager", "getNewMonthUserCount",[reqestParameter],
            function(data, textStatus, XMLHttpRequest) {
                if (data.result) {
                    var resultJson = JSON.parse(data.data);
                    showCustomerNew(resultJson);
                    //console.log(resultJson);
                    JsCache.set(cacheKey, resultJson);
                }
            });
        //console.log('request customer consume data from server in ' + (new Date().getTime() - startTime) + ' millisecond');
       }
}
var showCustomerNew = function(customerNewAndConsume){
  customerNewChart = echarts.init(document.getElementById('main11'));
	// 客流趋势
  customerNewChartOption = {
    title: {
      x: '5%', y: '0%',textStyle:{color:"#efefef",fontSize:"16"},
    },
    tooltip : {
      trigger: 'axis',
      formatter:function(params)//数据格式
            {
            var relVal = params[0]['name']+"<br/>";
             $.each(eval(params.reverse()), function (idx, val) {
	            relVal += val['marker']+val['seriesName']+ ' : ' + String(val['value']);
	            relVal+="<br/>";
             });
             return relVal;
        }
    },
    legend: {
      data:['消费用户','新增用户'],
      textStyle:{color:"#efefef",fontSize:"12"},
      right:0,
      orient:'vertical',
    },
    grid: {
      top: '20%',
      left: '3%',
      right: '3%',
      bottom: '3%',
      containLabel: true
    },
    xAxis : [
      {
        type : 'category',
        boundaryGap : false,
        splitLine: {
          show: false
        },
        axisLine: {
          show: false
        },
        axisTick:{
          show: false
        },
        axisLabel:{
          show: true,
          interval: 4,
          textStyle: {color:'#fff'}
        }
      }
    ],
    yAxis : [
      {
        type : 'value',
        splitLine: {
          show: false
        },
        axisLine: {
          show: false
        },
        axisTick:{
          show: false
        },
        axisLabel:{
          show: true,
          textStyle: {color:'#fff'}
        }
      }
    ],
    series : [
      {
        name:'新增用户',
        cursor: 'default',
        type:'line',
        stack: '总量',
        areaStyle: {normal: {color:'#ff3064'}},
        itemStyle: {
          normal:{
            color:'#ff3064'
          }
        },
        smooth: true
      },
      {
        name:'消费用户',
        cursor: 'default',
        type:'line',
        stack: '总量',
        areaStyle: {normal: {color:'#30d7c7'}},
        itemStyle: {
          normal:{
            color:'#30d7c7'
          }
        },
        smooth: true
      }
    ]
  };
  	var data = [];
  	var data1 = [];
    var data2 = [];
    $.each(eval(customerNewAndConsume.new_month_userCount['lst_data']), function (idx, val) {
    	data.push(val['week_date']);
    	data1.push(val['new_cus_count']);
		data2.push(val['pay_cus_count']);
    });
	customerNewChartOption.xAxis[0].data = data.reverse();
	customerNewChartOption.series[0].data = data1.reverse();
	customerNewChartOption.series[1].data = data2.reverse();
	customerNewChartOption.title.text="近30天客流趋势";
  	customerNewChart.setOption(customerNewChartOption,true);
  
}
var getTurnoverCustomerOrder = function(pageStatusInfo){
	var cacheKey = CACHE_HEADER_TURNOVER_CUSTOMER_MONTH + pageStatusInfo.getCacheKey();
    // 从缓存获取数据
    var turnoverCustomer = JsCache.get(cacheKey);
    if (turnoverCustomer) {
        //console.log('show turnover customer graph base on js cache.')
        showTurnoverCustomerOrder(turnoverCustomer);
    } else {
        // 准备服务端数据请求参数
        // 准备服务端数据请求参数
        var reqestParameter = {
            month:pageStatusInfo.currentMonth,
            year:pageStatusInfo.currentYear,
            provinceId:pageStatusInfo.provinceId,
            cityId:pageStatusInfo.cityId
        }
        //console.log('\trequest: turnover customer. ');
        //console.log(reqestParameter);
        var startTime = new Date().getTime();
        //近7日新增社员趋势
        doManager("communityMembersManager", "getMembersWeekCount",[reqestParameter],
            function(data, textStatus, XMLHttpRequest) {
                if (data.result) {
                    var resultJson = JSON.parse(data.data);
                    showTurnoverCustomerOrder(resultJson);
                    //console.log(resultJson);
                    JsCache.set(cacheKey, resultJson);
                }
            });
        //console.log('request turnover customer data from server in ' + (new Date().getTime() - startTime) + ' millisecond');
       }
}
var showTurnoverCustomerOrder = function(turnoverCustomer){
  	var data = [];
  	var data1 = [];
    var data2 = [];
    var data3 = [];
    var data4 = [];
    var data5 = [];
    var data6 = [];
    $.each(eval(turnoverCustomer['week_new_data']), function (idx, val) {
    	data.push(val['crtime']);
    	data1.push(val['newcount']);
    	data3.push(val['year_date']);
    });
    $.each(eval(turnoverCustomer['lst_data_bj']), function (idx, val) {
    	data4.push(val['newcount']);
    });
    $.each(eval(turnoverCustomer['lst_data_tj']), function (idx, val) {
    	data5.push(val['newcount']);
    });
    $.each(eval(turnoverCustomer['lst_data_sh']), function (idx, val) {
    	data6.push(val['newcount']);
    });
	turnoverCustomerOrderOption.xAxis.data = data.reverse();
	turnoverCustomerOrderOption.xAxis.extdata = data3.reverse();
	if(pageStatusInfo.provinceId==""&&pageStatusInfo.cityId==""){
		if(data6.length>0){
		    turnoverCustomerOrderOption.series[2].data = data6.reverse();
		    turnoverCustomerOrderOption.series[2].name = "上海";
		    var dataLegend = new Object();
	    	dataLegend.name = "上海";
	    	var textStyle = new Object();
	    	textStyle.color = "#fff";
	    	dataLegend.textStyle = textStyle;
	    	turnoverCustomerOrderOption.legend.data.push(dataLegend);
	    }
	    if(data5.length>0){
		    turnoverCustomerOrderOption.series[1].data = data5.reverse();
		    turnoverCustomerOrderOption.series[1].name = "天津";
		    var dataLegend = new Object();
	    	dataLegend.name = "天津";
	    	var textStyle = new Object();
	    	textStyle.color = "#fff";
	    	dataLegend.textStyle = textStyle;
	    	turnoverCustomerOrderOption.legend.data.push(dataLegend);
	    }
		if(data4.length>0){
	    	turnoverCustomerOrderOption.series[0].data = data4.reverse();
	    	turnoverCustomerOrderOption.series[0].name = "北京";
	    	var dataLegend = new Object();
	    	dataLegend.name = "北京";
	    	var textStyle = new Object();
	    	textStyle.color = "#fff";
	    	dataLegend.textStyle = textStyle;
	    	turnoverCustomerOrderOption.legend.data.push(dataLegend);
	    }
	}else{
		var dataLegend = new Object();
	    if(pageStatusInfo.cityName!=""){
			dataLegend.name = pageStatusInfo.cityName;
			turnoverCustomerOrderOption.series[0].name = pageStatusInfo.cityName;
	    }else if(pageStatusInfo.cityName==""&&pageStatusInfo.provinceName!=""){
			dataLegend.name = pageStatusInfo.provinceName;
			turnoverCustomerOrderOption.series[0].name = pageStatusInfo.provinceName;
	    }
		var textStyle = new Object();
		textStyle.color = "#fff";
		dataLegend.textStyle = textStyle;
		turnoverCustomerOrderOption.legend.data.push(dataLegend);
		turnoverCustomerOrderOption.series[0].data = [];
		turnoverCustomerOrderOption.series[0].data = data1.reverse();
		turnoverCustomerOrderOption.series[1].data = [];
		turnoverCustomerOrderOption.series[1].name = [];
		turnoverCustomerOrderOption.series[2].data = [];
		turnoverCustomerOrderOption.series[2].name = [];
	
	}
	//turnoverCustomerOrderOption.series[0].data = data1.reverse();
	//turnoverCustomerOrderOption.series[1].data = data2;
	//customerNewChartOption.title.text="社员7日走势";
  	turnoverCustomerOrderChart.setOption(turnoverCustomerOrderOption,true);
}
// 获取城市用户量分布数据
var getCityUser = function (pageStatusInfo) {
    var cacheKey = CACHE_HEADER_CITYUSER_RANK + pageStatusInfo.getCacheKey();
    // 从缓存获取数据
    var CityUserData = JsCache.get(cacheKey);
    if (CityUserData) {
        //console.log('show cityUser rank graph base on js cache.')
        showCityUser(CityUserData);
    } else {
        // 准备服务端数据请求参数
        // 准备服务端数据请求参数
        var reqestParameter = {
            month:pageStatusInfo.currentMonth,
            year:pageStatusInfo.currentYear,
            provinceId:pageStatusInfo.provinceId,
            cityId:pageStatusInfo.cityId
        }
        //console.log('\trequest: cityuser. ');
        //console.log(reqestParameter);
        var startTime = new Date().getTime();
        // 全国用户排名
        doManager("dynamicManager", "getLastMonthCityRankingTop10",[reqestParameter,null,null],
            function(data, textStatus, XMLHttpRequest) {
                if (data.result) {
                    var resultJson = JSON.parse(data.data);
                    showCityUser(resultJson);
                    JsCache.set(cacheKey, resultJson);
                }
            });
        //console.log('request cityUser rank graph data from server in ' + (new Date().getTime() - startTime) + ' millisecond');
    }
};
var showCityUser = function(CityUserData){
	var cityUserDataStr = '[';
	var cityUserStrArr = [];
    $.each(eval(CityUserData['gmv']), function (idx, val) {
    	cityUserDataStr+='{"name":"'+(val['city_name']+'')+"\\n"+(val['gmv_sum']==null?'':val['gmv_sum'])+'","value":"'+val['gmv_sum']+'","city_id":"'+val['city_id']+'","province_id":"'+val['province_id']+'"},';
    });
    if(eval(CityUserData['gmv']).length>0){
    	cityUserDataStr = cityUserDataStr.substring(0,cityUserDataStr.lastIndexOf(","));
    }
    cityUserDataStr+="]";
    var colorArray = ["#c33430","#2e4454","#B7B7B7","#D8BFD8","#FFB6C1","#d38c4c","#d48363","#A2B5CD","#d39c5c"];
    var cityUserDataObject = JSON.parse(cityUserDataStr);
     $.each(eval(cityUserDataObject), function (idx, val) {
     	var object = new Object();
     	if(val['name'].indexOf('北京')>-1){
     		object.color = colorArray[0];
     	}else if(val['name'].indexOf('上海')>-1){
     		object.color = colorArray[6];
     	}else if(val['name'].indexOf('天津')>-1){
     		object.color = colorArray[4];
     	}else if(val['province_id']==19){
     		object.color = colorArray[3];
     	}else if(val['province_id']=='24'){
     		object.color = colorArray[5];
     	}else if(val['province_id']=='6'){
     		object.color = colorArray[2];
     	}else if(val['province_id']=='18'){
     		object.color = colorArray[1];
     	}else if(val['province_id']=='25'){
     		object.color = colorArray[7];
     	}else if(val['province_id']=='5'){
     		object.color = colorArray[8];
     	}
     	var object2 = new Object();
     	object2.normal = object;
     	val['itemStyle'] = object2;
     	cityUserStrArr.push(val);
     });
	cityUserOption.series[0].data = cityUserStrArr;
	cityUserOption.title[0].text = "城市GMV分布("+pageStatusInfo['currentMonth']+"月)";
	cityUserGMV.setOption(cityUserOption,true);
}
//获取当日累计营业额(元)数据
var getDailyData = function(){
        chonfu();
        timerId = setInterval(chonfu,3000);
}
 function chonfu(){
 		setCurrentDate(pageStatusInfo);
 		var currentDateInfo = pageStatusInfo['currentYear']+"-"+pageStatusInfo['currentMonth_']+"-"+pageStatusInfo['currentDay'];
    	 //查询当日累计营业额
    	var dynamicDto = {
    			beginDate:currentDateInfo,
    			endDate:currentDateInfo,
    			month:pageStatusInfo['currentMonth_'],
	  			year:pageStatusInfo['currentYear'],
	  			provinceId:pageStatusInfo["provinceId"],
				cityId:pageStatusInfo["cityId"]
    	}
     var startTime = new Date().getTime();
     doManager("dynamicManager", "getDailyStoreTotlePrice",[dynamicDto],
  			function(data, textStatus, XMLHttpRequest) {
  			//1
  				if (data.result) { 
  					 var resultJson= JSON.parse(data.data);
  					 var dailyData = JSON.parse(resultJson.daily);
  					 var totle_price = dailyData[0].storecur_all_price==null?"0":dailyData[0].storecur_all_price;
  					 var daily_user_count = dailyData[0].customer_count==null?"0":dailyData[0].customer_count;
  					 var daily_order_count = dailyData[0].checked_order_count==null?"0":dailyData[0].checked_order_count;
  					$("#totle_price").html(totle_price);
  					day_curr = totle_price;
  					
  					var totalprice = totle_price+'';

  					if(totalprice.indexOf(".")>0){
  						totalprice = totalprice.substring(0,totalprice.lastIndexOf("."));
  					}
  					if(totalprice.length>8){
  						dojob(totalprice.substring(0,totalprice.length)); 
  					}else{
  						totalprice = "000000000"+totalprice;
  						dojob(totalprice.substring(totalprice.length-9,totalprice.length)); 
  					}
  					
  					//var tradesumofhistory = $("#tradesumofhistoryHid").text();//历史营业额
  					var tradesumofcustomerMonth = $("#tradesumofmonthCustmomerHid").text();//本月用户量
  					var tradesumoflastmonthCustmomer = $("#tradesumoflastmonthCustmomerHid").text();//上月今天用户量
  					var tradesumofcustomerHistory = $("#tradesumofhistoryCustmomerHid").text();//历史用户量
  					var tradesumoflastmonthcustomerHistory = $("#tradesumoflasthistoryCustmomerHid").text();//截止到上月历史用户量
  					var tradesumofmonthOrder = $("#tradesumofmonthOrderHid").text();//本月订单量
  					var tradesumofhistoryOrder = $("#tradesumofhistoryOrderHid").text();//历史订单量
  					var tradesumoflastmonthOrder = $("#tradesumoflastmonthOrderHid").text();//上月今天订单量
  					var openCardUserMonthcountHid = $("#openCardUserMonthcountHid").text();
					var openCardUserHistorycountHid = $("#openCardUserHistorycountHid").text();
					var tradesumOrderofyearHid_ = $("#tradesumOrderofyearHid").text();//当年成交订单量
  					//var tradesumofyear = $("#tradesumofyearHid").text();//上月订单
  					//$("#tradesumofcurmonths").html(changeMoney(parseInt(tradesumofcurmonth)+parseInt(totalprice)));
  					//$("#tradesumofhistorys").html(changeMoney(parseInt(tradesumofhistory)+parseInt(totalprice)));
  					$("#tradesumofmonthCustmomer").html(parseInt(tradesumofcustomerMonth)+parseInt(daily_user_count));
  					$("#tradesumofhistoryCustmomer").html(parseInt(tradesumofcustomerHistory)+parseInt(daily_user_count));
  					//$("#tradesumofhistoryCustmomer").html(parseInt(1630694));
  					$("#tradesumofmonthOrder").html(parseInt(tradesumofmonthOrder)+parseInt(daily_order_count));
  					$("#tradesumofhistoryOrder").html(parseInt(tradesumofhistoryOrder)+parseInt(tradesumOrderofyearHid_)+parseInt(daily_order_count));
  					//$("#openCardUserMonthcount").html(openCardUserMonthcountHid);
  					//$("#openCardUserHistorycount").html(openCardUserHistorycountHid);
  					var order_count_rate;
  					if(parseInt(tradesumoflastmonthOrder)==0){
  						order_count_rate = 0;
  						$("#order_count_ratio").html(0);
  					}else{
  						if(tradesumoflastmonthOrder==""){
	  						order_count_rate = "";
	  						$("#order_count_ratio").html('');
  						}else{
	  						order_count_rate = parseInt(((parseInt(daily_order_count)-parseInt(tradesumoflastmonthOrder))/parseInt(tradesumoflastmonthOrder))*100);
	  						$("#order_count_ratio").html(order_count_rate+'%');
  						}
  					}
  					/*if(order_count_rate<0){
  						$("#sign_4").attr("class","fa fa-long-arrow-down");
						$("#sing_id4").attr("class","col-sm-2 decline");
						$("#order_count_ratio").attr("class","decline");
						$("#tradesumofmonthOrder").removeClass("gaugetb2").addClass("gaugetb3");
						$("#sing_id_4").removeClass("gaugetb2").addClass("gaugetb3");
  					}else{
  						$("#sign_4").attr("class","fa fa-long-arrow-up");
						$("#sing_id4").attr("class","col-sm-2 increase");
						$("#order_count_ratio").attr("class","increase");
						$("#tradesumofmonthOrder").removeClass("gaugetb3").addClass("gaugetb2");
						$("#sing_id_4").removeClass("gaugetb3").addClass("gaugetb2");
  					}
  					*/
  					if(order_count_rate<0){
  						$("#sign_4").attr("class","fa fa-long-arrow-down");
  						$("#sing_id4").attr("class","col-sm-2 decline");
  						$("#order_count_ratio").attr("class","decline");
  					}else{
  						$("#sign_4").attr("class","fa fa-long-arrow-up");
  						$("#sing_id4").attr("class","col-sm-2 increase");
  						$("#order_count_ratio").attr("class","increase");
  					}
  					var customer_month_rate;
  					if(parseInt(tradesumoflastmonthCustmomer)==0){
  						customer_month_rate = 0;
  						$("#customer_month_ratio").html(0);
  					}else{
  						if(tradesumoflastmonthCustmomer==""){
  							customer_month_rate = "";
  							$("#customer_month_ratio").html('');
  						}else{
  							customer_month_rate = parseInt((parseInt(daily_user_count)-parseInt(tradesumoflastmonthCustmomer))/parseInt(tradesumoflastmonthCustmomer)*100);
  							$("#customer_month_ratio").html(customer_month_rate+'%');
  						}
  					}
  					/*
  					if(customer_month_rate<0){
  							$("#sign_1").attr("class","fa fa-long-arrow-down");
  							$("#sing_id").attr("class","col-sm-2 decline");
  							$("#customer_month_ratio").attr("class","decline");
  							$("#tradesumofmonthCustmomer").removeClass("gaugetb2").addClass("gaugetb3");
  							$("#sing_id_1").removeClass("gaugetb2").addClass("gaugetb3");
  					}else{
  							$("#sign_1").attr("class","fa fa-long-arrow-up");
  							$("#sing_id").attr("class","col-sm-2 increase");
  							$("#customer_month_ratio").attr("class","increase");
  							$("#tradesumofmonthCustmomer").removeClass("gaugetb3").addClass("gaugetb2");
  							$("#sing_id_1").removeClass("gaugetb3").addClass("gaugetb2");
  					}
  					*/
  					if(customer_month_rate<0){
  							$("#sign_1").attr("class","fa fa-long-arrow-down");
  							$("#sing_id").attr("class","col-sm-2 decline");
  							$("#customer_month_ratio").attr("class","decline");
  					}else{
  							$("#sign_1").attr("class","fa fa-long-arrow-up");
  							$("#sing_id").attr("class","col-sm-2 increase");
  							$("#customer_month_ratio").attr("class","increase");
  					}
  					var order_historey_rate;
  					if(parseInt(tradesumoflastmonthOrder)==0){
  						order_historey_rate = 0;
  						$("#order_history_ratio").html(0);
  					}else{
  						order_historey_rate = parseInt((parseInt(tradesumofhistoryOrder)+parseInt(daily_order_count)-parseInt(tradesumoflastmonthOrder))/parseInt(tradesumoflastmonthOrder)*100);
  						$("#order_history_ratio").html(order_historey_rate+'%');
  					}
  					/*
  					if(order_historey_rate<0){
  							$("#sign_3").attr("class","fa fa-long-arrow-down");
  							$("#sing_id3").attr("class","col-sm-2 decline");
  							$("#order_history_ratio").attr("class","decline");
  							$("#tradesumofhistoryOrder").removeClass("gaugetb2").addClass("gaugetb3");
  							$("#sing_id_3").removeClass("gaugetb2").addClass("gaugetb3");
  					}else{
  							$("#sign_3").attr("class","fa fa-long-arrow-up");
  							$("#sing_id3").attr("class","col-sm-2 increase");
  							$("#order_history_ratio").attr("class","increase");
  							$("#tradesumofhistoryOrder").removeClass("gaugetb3").addClass("gaugetb2");
  							$("#sing_id_3").removeClass("gaugetb3").addClass("gaugetb2");
  					}
  					*/
  					var history_customer_rate;
  					if(parseInt(tradesumoflastmonthCustmomer)==0){
  						history_customer_rate = 0;
  						$("#customer_history_ratio").html(0);
  					}else{
  						history_customer_rate = parseInt((parseInt(tradesumofcustomerHistory)-parseInt(tradesumoflastmonthcustomerHistory))/parseInt(tradesumoflastmonthcustomerHistory)*100);
  						$("#customer_history_ratio").html(history_customer_rate+'%');
  					}
  					/*
  					if(history_customer_rate>0){
  							$("#sign_2").attr("class","fa fa-long-arrow-up");
  							$("#sing_id2").removeClass("decline").addClass("increase");
  							$("#customer_history_ratio").attr("class","increase");
  							$("#tradesumofhistoryCustmomer").removeClass("gaugetb3").addClass("gaugetb2");
  							$("#sing_id_2").removeClass("gaugetb3").addClass("gaugetb2");
  					}else{
  							$("#sign_2").attr("class","fa fa-long-arrow-down");
  							$("#sing_id2").removeClass("decline").addClass("decline");
  							$("#customer_history_ratio").attr("class","decline");
  							$("#tradesumofhistoryCustmomer").removeClass("gaugetb2").addClass("gaugetb3");
  							$("#sing_id_2").removeClass("gaugetb2").addClass("gaugetb3");
  					}*/
  					//$("#tradesumofCurYears").html(changeMoney(parseInt(tradesumofyear)+parseInt(totalprice)));
  				}
  		});
  		
    //console.log('chonfu method in ' + (new Date().getTime() - startTime) + ' millisecond');
    }
    var ns=[];
    var changeno="";
    var srcnumber = "";
    function dojob(now_number){
    		 //$(".dataStatistics").html("");
    	  	 var ths = $(".dataStatistics");//解决this指向问题
    	        var html = '<div class="digit">' +
    		      '  <div class="digit_top">' +
    		      '    <span class="digit_wrap"></span>' +
    		      '  </div>' +
    		      '  <div class="shadow_top"></div>' +
    		      '  <div class="digit_bottom">' +
    		      '    <span class="digit_wrap"></span>' +
    		      '  </div>' +
    		      '  <div class="shadow_bottom"></div>' +
    		      '</div>';
    	          
    		      var htmlactive = '<div class="digit active">' +
    		      '  <div class="digit_top">' +
    		      '    <span class="digit_wrap"></span>' +
    		      '  </div>' +
    		      '  <div class="shadow_top"></div>' +
    		      '  <div class="digit_bottom">' +
    		      '    <span class="digit_wrap"></span>' +
    		      '  </div>' +
    		      '  <div class="shadow_bottom"></div>' +
    		      '</div>';
    	        var htmlprevious = '<div class="digit previous active">' +
    		      '  <div class="digit_top">' +
    		      '    <span class="digit_wrap"></span>' +
    		      '  </div>' +
    		      '  <div class="shadow_top"></div>' +
    		      '  <div class="digit_bottom">' +
    		      '    <span class="digit_wrap"></span>' +
    		      '  </div>' +
    		      '  <div class="shadow_bottom"></div>' +
    		      '</div>';
    		      
    	          var nowNums=now_number.split("");
    	          
    	          changeno="";
    	          for(var i=0;i<9;i++){
    	        	  if(nowNums[i]!=ns[i]){
    	        		  ns[i]=nowNums[i];
    	        		  changeno+=i+"";
    	        	  }
    	          }
    	        //alert(changeno); 
    	          var isbefore = true;//标记第一位是否0,0显示空
    	          var countsign = 1;
    		      ths.find('.digit_set').each(function(index,obj) {
    		    	    $(this).empty();
    		    	    if(srcnumber!=now_number&&changeno.indexOf(index)>-1){//如果相等  样式不变 
    		    	    	$(this).append(htmlactive);
    		    	    	$(this).append(htmlprevious);
    		    	    }else{
    		    	    	$(this).append(html);
    		    	    }
    		    	    
    		            var nowNumber = nowNums[index]; 
    		            if(nowNumber==0&&isbefore){
    		            	nowNumber="";
    		            	$(this).hide();
    		            }else{
    		            	isbefore=false;
    		            	$(this).show();
    		            }
    		            if(index==8&&nowNumber==""){
    		            	nowNumber="0";
    		            	$(this).show();
    		            }
    		            countsign++;
    		            if(nowNumber!=""){
    		            	$(this).find('.digit_wrap').append(nowNumber);
    		            }
    		      });
    		      srcnumber=now_number;
    	  		  if(parseInt(now_number)<1000000){
    	  			  $("#dou1").hide();
    	  			  if(parseInt(now_number)<1000){
    	  				$("#dou2").hide();
    	  			  }else{
    	  				$("#dou2").show();
    	  			  }
    	  		  }else{
    	  			$("#dou1").show();
    	  			$("#dou2").show();
    	  		  } 
    	}
    
// 显示导航信息
var showCrumbs = function () {
    var crumbsMenu = $('.crumbs');
    crumbsMenu.empty();
    var chinaItem = $('<a id="chinaItem">');
    var provinceItem = $('<a>');
    var cityItem = $('<a>');
    chinaItem.html('全国')
    crumbsMenu.css('padding-left','100px');
    crumbsMenu.append(chinaItem);
    if (pageStatusInfo.showLevel == SHOW_LEVEL_CHINA) {
        chinaItem.css('background','none');
        chinaItem.css('cursor', 'default');
    }
    if (pageStatusInfo.showLevel == SHOW_LEVEL_PROVINCE) {
        // 设置全国样式和事件
        chinaItem.css('cursor', 'pointer');
        chinaItem.on('click', function () {
            pageStatusInfo.cityId = '';
            pageStatusInfo.cityName = '';
            pageStatusInfo.provinceId = '';
            pageStatusInfo.provinceName = '';
            pageStatusInfo.showLevel = SHOW_LEVEL_CHINA;
            showPageContent(pageStatusInfo);
        });
        // 设置省菜单
        if (pageStatusInfo.provinceName) {
            provinceItem.html(pageStatusInfo.provinceName);
            provinceItem.css('background','none');
            provinceItem.css('cursor', 'default');
            var lineItem = $('<span>');
            lineItem.attr("id","line");
            crumbsMenu.append(lineItem);
            crumbsMenu.append(provinceItem);
        }
    }
    if (pageStatusInfo.showLevel == SHOW_LEVEL_CITY) {
        // 设置全国样式和事件
        if(pageStatusInfo.targets==0){
	        chinaItem.css('cursor', 'pointer');
	        chinaItem.on('click', function () {
	            pageStatusInfo.cityId = '';
	            pageStatusInfo.cityName = '';
	            pageStatusInfo.provinceId = '';
	            pageStatusInfo.provinceName = '';
	            pageStatusInfo.showLevel = SHOW_LEVEL_CHINA;
	            showPageContent(pageStatusInfo);
	        });
        }else{
        	$("#chinaItem").css('background','none');
        	$("#chinaItem").css('cursor', 'default');
        }
        // 非直辖市
        if (isMunicipality(pageStatusInfo.cityName) == false) {
            if (pageStatusInfo.provinceName) {
            	var lineItem = $('<span>');
		        lineItem.attr("id","line");
				crumbsMenu.append(lineItem);
                provinceItem.html(pageStatusInfo.provinceName)
                provinceItem.css('cursor', 'pointer');
                provinceItem.on('click', function () {
                    pageStatusInfo.cityId = '';
                    pageStatusInfo.cityName = '';
                    pageStatusInfo.showLevel = SHOW_LEVEL_PROVINCE;
                    showPageContent(pageStatusInfo);
                });
                crumbsMenu.append(provinceItem);
            }
        }
        // 设置城市菜单
        cityItem.html(pageStatusInfo.cityName)
        cityItem.css('background','none');
        cityItem.css('cursor', 'default');
        var lineItem = $('<span>');//设置大于号图标
        lineItem.attr("id","line_");
		crumbsMenu.append(lineItem);
        crumbsMenu.append(cityItem);
    }
    $("#line").html('&nbsp;>&nbsp;');
	$("#line").attr("style","color:#f6f6f6")
	$("#line_").html('&nbsp;>&nbsp;');
	$("#line_").attr("style","color:#f6f6f6");
	$("#line_").css('cursor', 'default');
	$("#line").css('cursor', 'default');
};
// 获取请求参数
var getReauestParameters = function () {
	var targets = "";
	var cityId = "";
	var cityName = "";
	var provinceId = "";
	initcurruser();
	initCustomerInfo();//菜单 判断非总部不显示 
	var regex_cs = new RegExp("^(CS|cs)\w*");//城市级别
	if(screenlogin!=null&&screenlogin!=''&&screenlogin!=undefined){
	}else{
		if(curr_user==null){
			urlStr = "../logout.do";
			window.location.href = urlStr;
		}
	}
  	if(regex_cs.test(curr_user.usergroup.code)||curr_user.usergroup.code=="PTYYZXYCDDZ"){
  		targets = 1;
  		var cityId_ = (decode64(getUrlParamByKey("c")) == 'null'||decode64(getUrlParamByKey("c")) == null) ? '' : decode64(getUrlParamByKey("c"));
			var cityName_ = (decode64(getUrlParamByKey("cn")) == 'null'||decode64(getUrlParamByKey("cn")) == null) ? '' : decode64(getUrlParamByKey("cn"));
			var cityStr = getCityId();
		    // 城市ID
		    cityId = (cityId_!='')?cityId_:cityStr.split(";")[0];;
		    // 城市名称
		    cityName = (cityName_!='')?cityName_:cityStr.split(";")[1];
		    var span = $("<span style='font-size:12px'>&nbsp;&nbsp;<span style='color:#3c8dbc;cursor:pointer' onclick='showMoreCity()'>切换</span></span>");
		    $("#currentCity").empty();
		    $("#currentCity").before(cityName);
		    $("#currentCity").after(span);
  	}
  	var regex_zb = new RegExp("^(ZB|zb)\w*");//总部角色
    var regex_gly = new RegExp("^(GLY|gly)\w*");//管理员
  	if(regex_zb.test(curr_user.usergroup.code)||regex_gly.test(curr_user.usergroup.code)){
		targets = 0;
		// 城市ID
		    cityId = (decode64(getUrlParamByKey("c")) == 'null'||decode64(getUrlParamByKey("c")) == null) ? '' : decode64(getUrlParamByKey("c"));
		    // 省份ID
		    provinceId = (decode64(getUrlParamByKey("p"))=='null'||decode64(getUrlParamByKey("p"))==null) ? '' : decode64(getUrlParamByKey("p"));
		    // 城市名称
		    cityName = (decode64(getUrlParamByKey("cn")) == 'null'||decode64(getUrlParamByKey("cn")) == null) ? '' : decode64(getUrlParamByKey("cn"));
		    $("#currentCity").empty();
		    var gengduo = $('<span class="pull-right" style="font-size: 12px;cursor:pointer;color: #747474;" id="net_more">更多</span>');
		    $("#gaiyao").append(gengduo);
		    $("#currentCity").html("线下网络体系");
	}
    // 城市ID
    // 缩放级别
    var zoomLevel = (decode64(getUrlParamByKey("zm")) == 'null'||decode64(getUrlParamByKey("zm")) == null) ? '' : decode64(getUrlParamByKey("zm"));
    // 是否是门店进入
    var isFromStore = (decode64(getUrlParamByKey("f")) == 'null'||decode64(getUrlParamByKey("f")) == 'null') ? '' : decode64(getUrlParamByKey("f"));
    var pageInfo = new Object();//初始化分页第一页(查询五条)
    pageInfo.currentPage = "1";
    pageInfo.recordsPerPage = "5";
    var rtnData = {
        "cityId": cityId,
        "provinceId": provinceId,
        "cityName": cityName,
        "zoomLevel": zoomLevel,
        "isFromStore": isFromStore,
        "pageInfo":pageInfo,
		"targets":targets
    };
    return rtnData;
};

	  //获取城市总监的城市名称
  var cityId = "";
  var curCity= "";
  var more_city = "<option value=''>请选择</option>";
	var getCityId = function(){
		 doManager("StoreManager", "getCityNameOfCSZJ",[curr_user.id,null],
 				function(data, textStatus, XMLHttpRequest) {
 					if (data.result) {
 						var cityinfo= JSON.parse(data.data);
 						var cityList = cityinfo.citylist;
 						if(cityList==null){
 							crm_alert(0,"该账号未分配城市！");
 							return;
 						}
 						var real_current_city = cityinfo.city;
 						if(cityList!=null&&cityList.length>0){
							for(var i=0;i<cityList.length;i++){
								more_city=more_city+"<option value='"+cityList[i].ctid+"'>"+cityList[i].name+"</option>"
								real_current_city = cityList[i];
							}
						$("#switch_city").append(more_city);
						}
						var city_more = "<span style='font-size:14px'><span   style='color:#00c0ef;cursor:pointer' onclick='showMoreCity()'>切换</span></span>"
   						$(".text-yellow").append(city_more);
 						curCity = cityinfo.city.name;
 						cityId = cityinfo.city.ctid;
 					}
 			},false);
 			return cityId+";"+curCity;
}
// 是否直辖市
var isMunicipality = function (cityName) {
    const municipalityIds = ['北京', '天津', '上海', '重庆', '北京市', '天津市', '上海市', '重庆市'];
    if ($.inArray(cityName, municipalityIds) != -1) {
        return true;
    } else {
        return false;
    }
};
// 设置加载中图标
var setLoadingMark = function (bShow) {
    if (bShow == true) {
        $("#process_div").show();
        $("#process_div_pic").show();
    } else {
        $("#process_div").hide();
        $("#process_div_pic").hide();
    }
};
// 地图事件绑定处理
var bindMapEvents = function () {
    var isClickChart;
    // 事件绑定处理
    mapChart.on('click',function(params){
    	if(params.data['selected']){
    		if(params.data['keys']){
    			var cityId = "";
    			var province_id = "";
    			if(isMunicipality(params.data['name'])==true){//直辖市
    			//if(isMunicipality=='true'){//直辖市
	    			$.each(eval(openedProvinceMunicipality), function (idx, val) {
	    				if(params.data['name']==val['name']){
		    				province_id = val['province_id'];
					        pageStatusInfo.cityId = province_id;
					        pageStatusInfo.cityName = params.data['name'];
					        pageStatusInfo.showLevel = SHOW_LEVEL_CITY;
	    				}
	     			});
	     			 // 重置页面显示
		        	showPageContent(pageStatusInfo);
    			}else{
    				$.each(eval(openedCity), function (idx, val) {
	    				if(val['name'].indexOf(params.data['name'])>-1){
		    				cityId = val['cityId'];
					        pageStatusInfo.cityId = cityId;
					        pageStatusInfo.cityName = params.data['name'];
					        pageStatusInfo.showLevel = SHOW_LEVEL_CITY;
	    				}
	     			});
			    	 // 重置页面显示
		        	showPageContent(pageStatusInfo);
    			}
    		}else{
        		setMapData(pageStatusInfo, params);
    		}
    	}
    });

    // 设置全屏显示处理
    $('#fullScreen').on('click', function () {
        isClickChart = false;
        // 准备全屏显示
        $("#mask").empty();
        $("#mask").attr("style","background-color: rgba(0,0,0,0.7)");
        var maskHeader = $("<div>").attr("id","mask-header").attr("style","width: 1483px;");
        var maskBody = $("<div>").attr("id","mask-body").attr("style","width: 100%;");
        var goout = $("<button>").attr("class","btn btn-primary").html("退出").attr("onclick","goout()");//加退出按钮
        $("#mask").append(maskBody);
        maskHeader.append(goout);
        $("#mask-body").before(maskHeader);
        $("#mask-body").width($(window).width());
        $("#mask-body").height($(window).height());
        // 判断显示全屏状态
        if (pageStatusInfo.showLevel == SHOW_LEVEL_CHINA || pageStatusInfo.showLevel == SHOW_LEVEL_PROVINCE) {
        	if(pageStatusInfo.showLevel == SHOW_LEVEL_CHINA){
        		timer_china_beat2 = setInterval("nocease2()","2000");
        	}
            fullScreenChart = echarts.init(document.getElementById("mask-body"));
            fullScreenChart.setOption(mapChart.getOption(), true);
            // 设置页面显示数据
            fullScreenChart.on('click', function (params) {
                if(params.data['selected']){
                	isClickChart = true;
                	setMapData(pageStatusInfo, params);
                }
            });
        } else if (pageStatusInfo.showLevel == SHOW_LEVEL_CITY) {
        	clearInterval(timer_china_beat2);
            $("#mask-body").html($("#mapContainer"));
        }
        // 显示大图
        $("#mask").css("display", "block");
    });
        // 设置全屏显示处理
    $('#fullScreenSwiper').on('click', function () {
        isClickChart = false;
        // 准备全屏显示
        $("#mask").empty();
        $("#mask").attr("style","background-color: rgba(0,0,0,0.9)");
        var maskHeader = $("<div>").attr("id","mask-header").attr("style","width: 1483px;");
        var maskBody = $("<div>").attr("id","mask-body").attr("style","width: 100%;");
        var goout = $("<button>").attr("class","btn btn-primary").html("退出").attr("onclick","goout2()").attr("style","margin-top:20px;");//加退出按钮
        $("#mask").append(maskBody);
        maskHeader.append(goout);
        $("#mask-body").before(maskHeader);
        $("#mask-body").width($(window).width());
        $("#mask-body").height($(window).height());
        fullScreenChart = echarts.init(document.getElementById("mask-body"));
        fullScreenChart.setOption(cityProfitRangeChart.getOption(), true);
        fullScreenChart.on('click', function (params) {
        		var dataArr = [];
			  	dataArr = params.data;
			  	var storename = dataArr[3];
			  	var storeno = dataArr[6];
			  	var dayTime = dataArr[5];
			  	var cityId = dataArr[7];
			  	var cityName = dataArr[4];
			  	dayTime = dayTime.replace(/-/g,"/");
			  	var redirectTag = "profit";
			  	var target=pageStatusInfo.targets; 
			    if(target==0){
					url = "dynamicData_profit_analysis.html?t="+encode64('0')+"&c="+encode64(cityId)+"&s=&e=&rt="+encode64(redirectTag)+"&time="+encode64(dayTime+"-"+dayTime)+"&so="+encode64(storeno)+"&sn="+encode64(storename)+"&cn="+encode64(cityName);
				}else if(target==1){
					url = "dynamicData_profit_analysis.html?t="+encode64('1')+"&c="+encode64(cityId)+"&s=&e=&rt="+encode64(redirectTag)+"&time="+encode64(dayTime+"-"+dayTime)+"&so="+encode64(storeno)+"&sn="+encode64(storename)+"&cn="+encode64(cityName);
				}
				window.open(url,"dynamicData_profit_analysis");
        });
        // 显示大图
        $("#mask").css("display", "block");
    });
    // 事件绑定
    /*
    $("#mask").on('click', function () {
        if (isClickChart == false) {
            $("#mask").hide();
        if (pageStatusInfo.showLevel == SHOW_LEVEL_CITY) {
                $("#mapHeight").html($("#mask-body"));
            }
        }
    });*/
};
var goout = function(){
	$("#mask").hide();
	clearInterval(timer_china_beat2); 
    if (pageStatusInfo.showLevel == SHOW_LEVEL_CITY) {
        $("#mapHeight").html($("#mask-body"));
    }
}
var goout2 = function(){
	$("#mask").hide();
}
//初始化城市数据
var initCityInfo = function () {
    var citylist = statisticExtendInfo.cityCountList;
    loadIndex = layer.load(0, {
        shade: [0.1, '#393D49'] //0.1透明度的黑色背景
    });
    var infoStr = "";

    for (var i = 0; i < citylist.length; i++) {
        var cityname = citylist[i].city_name;
        var cityId = citylist[i].id;
        infoStr = infoStr + "<tr style='cursor:pointer'  onclick='aboutCityData(this)' id='tr_" + cityId + "'><td>" + parseInt(i + 1) + "</td><td>" + cityname + "</td>/tr>";
    }
    $("#city_detail_title").nextAll().remove();
    $("#city_detail_title").after(infoStr);
    $("#city_detail_title").nextAll("tr:even").css("background-color", "aliceblue");
    $("#city_detail_title").nextAll("tr:odd").css("background-color", "mintcream");
    showCityInfo();
};
//展示城市
var showCityInfo = function () {
    layer.close(loadIndex);
    layer.open({
        type: 1,
        closeBtn:1,
        title: ['城市信息', 'color:#fff'],
        shadeClose: true,
        shade: [0.1, '#393D49'],
        moveOut: false,
        skin: 'layer-ext-crmskin',
        area: ['700px', '290px'],
        offset: ['100px', '400px'],
        content: $("#city_detail_div"),
        success: function (layer, index) {

        },
        cancel: function (index, layero) {
            layer.close(index);
        }
    });
};
//初始门店数据
var initStore = function(){
    resetSearch_store();
    var storelist = statisticExtendInfo.storeCountList;
    var storeInfo="";
    for(var i=0;i<storelist.length;i++){
        var store_name = storelist[i].name;
        var store_id = storelist[i].store_id;
        var storeNo = storelist[i].storeno==null?"":storelist[i].storeno;
        var cityId = storelist[i].cityId;
        var cityName = storelist[i].city_name;
        storeInfo = storeInfo+"<tr style='cursor:pointer' onclick='goStore(this)'  id='tr_"+store_id+"_"+cityId+"' ><td>"+parseInt(i+1)+"</td><td>"+storeNo+"</td><td>"+store_name+"</td><td>"+cityName+"</td></tr>";
    }
    $("#store_detail_title").nextAll().empty();
    $("#store_detail_title").after(storeInfo);
    $("#store_detail_title").nextAll("tr:even").css("background-color","aliceblue");
    $("#store_detail_title").nextAll("tr:odd").css("background-color","mintcream");
    showStoreInfo();

};
//展示门店列表
var showStoreInfo = function(){
    layer.open({
        type: 1,
        closeBtn:1,
        title:  ['门店信息','color:#fff'],
        shadeClose: true,
        shade:[0.1, '#393D49'],
        moveOut:false,
        skin: 'layer-ext-crmskin',
        area: ['700px','290px'],
        offset: ['100px','400px'],
        content:$("#store_detail_div"),
        success:function(layer,index){

        },
        cancel: function(index, layero){
            layer.close(index);
        }
    });
};
//门店弹窗-搜索重置
var resetSearch_store = function(){
    $("#store_search_name").val("");
    $("#store_search_storeno").val("");
    $("#store_detail_title").nextAll().each(function(i,t){
        $(t).children("td:eq(0)").html(i+1);
    });
    $("#store_detail_title").nextAll().show();
};
//跳转到门店
	  function goStore(t){
		  var storeId =   $(t).attr("id").split("_")[1];
		  var cityId = $(t).attr("id").split("_")[2];
		  var cityName = $(t).children("td:eq(3)").text();
		  var flag = encode64("1");
		  var URL="current_shopkeeper.html?"+"s="+encode64(storeId)+"&c="+encode64(cityId)+"&cn="+encode64(cityName)+"&p=&ln="+encode64(curr_user.name)+"&f="+flag+"&fs="+encode64(pageStatusInfo.targets);
		  window.open(URL,"_self"); 
	  }
	  
	  //跳转到emoloyeeInfo.html
	  function goEmployee(t){
		  var employeeNo =   $(t).attr("id").split("_")[1];
		  
		  var URL="employeeInfo.html?"+"employee_no="+encode64(employeeNo)+"&flag=0"+"&#tab_1";
		  window.open(URL); 
	  }
//初始化店长
var initStoreKeeper = function(){
    resetSearch_storeKeeper();
    var infoStr="";
    var human_list1 = statisticExtendInfo.storeKeeper;
    for(var i=0;i<human_list1.length;i++){
        var employee_name = human_list1[i].keeperName==null?"":human_list1[i].keeperName;
        var employee_no = human_list1[i].employeeId==null?"":human_list1[i].employeeId;
        var employee_storename = human_list1[i].storeName;  //门店
        var employee_citySelect = human_list1[i].city_name;   //城市
        var employee_phone = human_list1[i].mobilephone==null?"":human_list1[i].mobilephone;
        var uid =  human_list1[i].id;
        infoStr = infoStr+"<tr style='cursor:pointer'  id='tr_"+employee_no+"_"+uid+"' onclick='goStoreKeeper(this)'><td>"+parseInt(i+1)+"</td><td>"+employee_no+"</td><td>"+employee_name+"</td><td>"+employee_citySelect+"</td><td>"+employee_storename+"</td><td>"+employee_phone+"</td></tr>";
    }

    $("#storeKeeper_detail_title").nextAll().remove();
    $("#storeKeeper_detail_title").after(infoStr);
    $("#storeKeeper_detail_title").nextAll("tr:even").css("background-color","aliceblue");
    $("#storeKeeper_detail_title").nextAll("tr:odd").css("background-color","mintcream");
    showStoreKeeper();
};

//展示门店店长列表
var showStoreKeeper = function(){
    layer.open({
        type: 1,
        closeBtn:1,
        title:  ['店长信息','color:#fff'],
        shadeClose: true,
        shade:[0.1, '#393D49'],
        moveOut:false,
        skin: 'layer-ext-crmskin',
        area: ['700px','290px'],
        offset: ['100px','400px'],
        content:$("#storeKeeper_detail_div"),
        success:function(layer,index){

        },
        cancel: function(index, layero){
            layer.close(index);
        }
    });
};
//跳转到门店
var goStore = function(t){
      var storeId =   $(t).attr("id").split("_")[1];
	  var cityId = $(t).attr("id").split("_")[2];
	  var cityName = $(t).children("td:eq(3)").text();
	  var flag = encode64("1");
	  var URL="current_shopkeeper.html?"+"s="+encode64(storeId)+"&c="+encode64(cityId)+"&cn="+encode64(cityName)+"&p=&ln="+encode64(curr_user.name)+"&f="+flag+"&fs="+encode64(pageStatusInfo.targets);
	  window.open(URL,"_self"); 
};
//点击城市展示城市相关数据
var aboutCityData = function(t){
    layer.closeAll();
    var cityId = $(t).attr("id").split("_")[1];
    cityName = $(t).children("td:eq(1)").text();
    pageStatusInfo.cityId = cityId;
    pageStatusInfo.cityName = cityName;
    pageStatusInfo.showLevel = SHOW_LEVEL_CITY;
    showPageContent(pageStatusInfo);
};
//弹窗-搜索重置
function resetSearch_storeKeeper(){
    $("#storeKeeper_search_name").val("");
    $("#storeKeeper_search_employeeNo").val("");
    $("#storeKeeper_detail_title").nextAll().each(function(i,t){
        $(t).children("td:eq(0)").html(i+1);
    });
    $("#storeKeeper_detail_title").nextAll().show();
}

//跳转到manageToStoreKeeper.html
function goStoreKeeper(t){

    var id =   $(t).attr("id").split("_")[2];
    var employee = $(t).attr("id").split("_")[1];
    var URL="storekeeper_user.html?"+"userId="+encode64(id)+"&employeeNo="+encode64(employee);
    window.open(URL);
}
  //弹窗-搜索
function searchEmployee_storeKeeper(){
	var name = $("#storeKeeper_search_name").val();
	var employeeNo = $("#storeKeeper_search_employeeNo").val();
	$("#storeKeeper_detail_title").nextAll().show();
	$("#storeKeeper_detail_title").nextAll().each(function(i,t){
	var c_employeeNo =  $(t).children("td:eq(1)").text();
	if(c_employeeNo.indexOf(employeeNo)<0){
	 		$(t).hide();
	 }else{
	 		$(t).show();
	 	}
	  });
	 $("#storeKeeper_detail_title").nextAll("tr:visible").each(function(i,t){
 		 var c_name =  $(t).children("td:eq(2)").text();
 		 if(c_name.indexOf(name)<0){
 			 $(t).hide();
 		 }else{
 			 $(t).show();
 		 }
	  })
	  
 	   $("#storeKeeper_detail_title").nextAll("tr:visible").each(function(i,t){
	    	$(t).children("td:eq(0)").html(i+1);
	   })
  }
  //弹窗-搜索重置
  function resetSearch_storeKeeper(){
	   $("#storeKeeper_search_name").val("");
	   $("#storeKeeper_search_employeeNo").val("");
	   $("#storeKeeper_detail_title").nextAll().each(function(i,t){
	    	$(t).children("td:eq(0)").html(i+1);
	   })
	   $("#storeKeeper_detail_title").nextAll().show();
  }
  
	  
	function initEmployeeInfo(){
	    resetSearch();
	    pageStatusInfo.currentPage=1;
      	getEmployeePerPageData(pageStatusInfo);//切换页面后获取数据
      	createEmployeePage();
        showEmployeeInfo();
	}
	
	  //员工弹窗-搜索
	  function searchEmployee(){
		pageStatusInfo.currentPage=1;
      	getEmployeePerPageData(pageStatusInfo);//切换页面后获取数据
      	createEmployeePage();
	  }
	  
	  
//员工弹窗-搜索重置
function resetSearch_employee(){
	resetSearch();
	pageStatusInfo.currentPage=1;
  	getEmployeePerPageData(pageStatusInfo);//切换页面后获取数据
  	createEmployeePage();
}
	  
//员工弹窗-搜索重置
function resetSearch(){
    $("#gax_search_name").val("");
    $("#gax_search_city").val("");
    $("#gax_search_storename").val("");
    $("#employee_detail_title").nextAll().each(function(i,t){
        $(t).children("td:eq(0)").html(i+1);
    });
    $("#employee_detail_title").nextAll().show();
}

//门店弹窗-搜索
	  function searchEmployee_store(){
		  var storeNo = $("#store_search_storeno").val();
		  var name = $("#store_search_name").val();
		  $("#store_detail_title").nextAll().show();
		  $("#store_detail_title").nextAll().each(function(i,t){
		 		 var c_storeNo =  $(t).children("td:eq(1)").text();
		 		 if(c_storeNo.indexOf(storeNo)<0){
		 			 $(t).hide();
		 		 }else{
		 			 $(t).show();
		 		 }
		  })
		  $("#store_detail_title").nextAll("tr:visible").each(function(i,t){
		 		 var c_name =  $(t).children("td:eq(2)").text();
		 		 if(c_name.indexOf(name)<0){
		 			 $(t).hide();
		 		 }else{
		 			 $(t).show();
		 		 }
		  })
		  
	 	   $("#store_detail_title").nextAll("tr:visible").each(function(i,t){
		    	$(t).children("td:eq(0)").html(i+1);
		   })
	  }
	  //门店弹窗-搜索重置
	  function resetSearch_store(){
		   $("#store_search_name").val("");
		   $("#store_search_storeno").val("");
		   $("#store_detail_title").nextAll().each(function(i,t){
		    	$(t).children("td:eq(0)").html(i+1);
		   })
		   $("#store_detail_title").nextAll().show();
	  }
	  
	  
	  function goCountry(){
	  	pageStatusInfo.cityId = '';
        pageStatusInfo.cityName = '';
        pageStatusInfo.provinceId = '';
        pageStatusInfo.provinceName = '';
        pageStatusInfo.showLevel = SHOW_LEVEL_CHINA;
        //点击总部首页
        showPageContent(pageStatusInfo);
	  }  
	  
	  function goStatistics(){
		  crm_alert(0,"根据2018年新派单和新绩效规则，数据统计页面将于（1月18日至1月26日）整体进行调整。<br> 如需查询新规则派单结果，请临时移步至  【数据档案】--【订单档案】页面。");
	  }
//展示员工
//员工弹窗打开状态	  
	  
function showEmployeeInfo(){
    layer.open({
        type: 1,
        title:  ['员工信息','color:#fff'],
        shadeClose: true,
        shade:[0.1, '#393D49'],
        closeBtn:1,
        moveOut:false,
        skin: 'layer-ext-crmskin',
        area: ['700px','382px'],
        offset: ['100px','400px'],
        content:$("#employee_detail_div"),
        success:function(layer,index){

        },
        cancel: function(index, layero){
            layer.close(index);
           
        }
    });
  
}
var curr_user;
  function initcurruser(){
	    
  		//取得当前登录人的门店
		doManager("UserManager", "getCurrentUserDTO",null,
    				function(data, textStatus, XMLHttpRequest) {
    					if (data.result) {
    						var employeeID="";
    						 curr_user = JSON.parse(data.data);
    						 var zw = curr_user.zw==null?"":curr_user.zw;
    						 //$("#store_name").html(curr_user.store_name);
    						 $("#employee_no").html(curr_user.employeeId);
    						 $('span[class="hidden-xs"]').html(curr_user.name);
    						 $("#card_name").html(curr_user.name);
    						 if(curr_user.employeeId==null||curr_user.employeeId=="null"){
    							 employeeID = "";
    						 }else{
    							 employeeID = curr_user.employeeId;
    						 }
    						 $("#card_no").html(zw+" "+employeeID);
                            //暂时控制管理员查看221gmv，以后删除（高宝磊）
    						 if(curr_user.usergroup.code=="GLY"){
                                $("#221_gmv").show();
                                 $("#jssj").hide();

                            }else{
                                $("#221_gmv").hide();
                                 if(curr_user.usergroup.code=="ZBCWJSZ"||curr_user.usergroup.code=="CSCWJSZ"||curr_user.usergroup.code=="ZBSYQCWJSZ"){
                                    $("#jssj").show();
                                     $("#left-showa2").hide();
                                     $("#left-showa3").hide();
                                     $("#left-showa4").hide();
                                 }else if(curr_user.usergroup.code=="ZBJYGLCJSZ"){
                                     $("#left-showa1").show();
                                     $("#left-showa2").show();
                                     $("#left-showa3").show();
                                     $("#left-showa4").show();
                                     $("#jssj").show();
                                 }else{
                                     $("#jssj").hide();
                                 }
                            }
    					}
    			},false);
  	}
  
  //实时数据监控
  function gotok(){
	  var role = curr_user.usergroup.code;
	  var url = "";
	  var target=pageStatusInfo.targets;
	  if(target==0){
	  	url = "index_K.html?t="+encode64('0')+"&s=&sn=&c=&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&cn=";
	  }else if(target==1){
	  	url = "index_K.html?t="+encode64(1)+"&s=&sn=&c=cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&#ff";
	  }
	  window.open(url,"index_K"); 
  }

  function gotogax_work(){
      var role = curr_user.usergroup.code;
      var url = "";
      var target=pageStatusInfo.targets;
      if(target==0){
          url = "gax_working_load.html?t="+encode64('0')+"&s=&sn=&c=&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&cn=";
      }else if(target==1){
          url = "gax_working_load.html?t="+encode64(1)+"&s=&sn=&c=cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&#ff";
      }
      window.open(url,"index_gax_work");
  }
  
  //221战略运营页
  function goto221(){
  	  var target=pageStatusInfo.targets;
  	  var url = "";
  	  if(target==0){
	  	url = "221_store.html?t="+encode64('0');
	  }else if(target==1){
	  	url = "221_store.html?t="+encode64('1');
	  }
	  window.open(url,"221");
  }
  
  //518直播间
  function gotolive(){
	  window.open("518.html","518");
  }
  
  //用户分析
  // function goto_user_k(){
	//   var role = curr_user.usergroup.code;
	//   var url = "";
	//   var target=pageStatusInfo.targets;
	//   if(target==0){
	//   	url = "index_K_user.html?t="+encode64('0')+"&s=&sn=&c=&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&cn=";
	//   }else if(target==1){
	//   	url = "index_K_user.html?t="+encode64(1)+"&s=&sn=&c=cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&#ff";
	//   }
	//   window.open(url,"index_K_user");
  // }
  
  //营业额统计
  function goToTurnoverStat(){
	  var role = curr_user.usergroup.code;
	  var url = "";
	  var target=pageStatusInfo.targets;
	  if(target==0){
	  	url = "turnoverStat_view.html?t="+encode64('0')+"&s=&sn=&c=&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&cn=";
	  }else if(target==1){
	  	url = "turnoverStat_view.html?t="+encode64(1)+"&s=&sn=&c=cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&#ff";
	  }
	  window.open(url,"turnoverStat_view"); 
  }
  
  //用户运营
  function goToUserOperationStat(){
	  var role = curr_user.usergroup.code;
	  var url = "";
	  var target=pageStatusInfo.targets;
	  if(target==0){
	  	url = "userOperationStat_view.html?t="+encode64('0')+"&s=&sn=&c=&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&cn=";
	  }else if(target==1){
	  	url = "userOperationStat_view.html?t="+encode64(1)+"&s=&sn=&c=cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&#ff";
	  }
	  window.open(url,"userOperationStat_view"); 
  }
  
  //合作社社员统计
  function goToUserMember(){ 
	  var role = curr_user.usergroup.code;
	  var url = "";
	  var target=pageStatusInfo.targets;
	  if(target==0){
	  	url = "user_member_view.html?t="+encode64('0')+"&s=r="+encode64(role)+"&c=&cn=&e="+encode64(curr_user.id);
	  }else if(target==1){
	  	url = "user_member_view.html?t="+encode64(1)+"&s=&c="+ encode64(pageStatusInfo.cityId)+"&cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&#fg";
	  }
	  window.open(url,"user_member_view");
  }

//合作社社员统计
function goToDataFiles(){
    var role = curr_user.usergroup.code;
    var url = "";
    var target=pageStatusInfo.targets;
    if(target==0){
        url = "data_files.html?t="+encode64('0')+"&s=r="+encode64(role)+"&c=&cn=&e="+encode64(curr_user.id);
    }else if(target==1){
        url = "data_files.html?t="+encode64(1)+"&s=&c="+ encode64(pageStatusInfo.cityId)+"&cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&#fg";
    }
    window.open(url,"user_member_view");
}


//线下网络运营
  function goToCityNet(){
  	  var role = curr_user.usergroup.code;
  	  var url = "";
  	  var target=pageStatusInfo.targets;
  	  if(target==0){
  	  	url = "index_city_net.html";
  	  }else if(target==1){
  	    url = "index_city_net.html";
      }
  	  window.open(url,"index_city_net"); 
  }
    //门店租赁统计
    function goToStoreRent(){
        var role = curr_user.usergroup.code;
        var url = "";
        var target=et=pageStatusInfo.targets;
        if(target==0){
            url = "store_rent_info.html";
        }
        window.open(url,"store_rent_info");
    }

  //交易额 （按门店） 
  function goToStoretrade(){
	  var role = curr_user.usergroup.code;
	  var url = "";
	  var target=pageStatusInfo.targets;
	  if(target==0){
	  	url = "dynamicData_storetrade_analysis.html?t="+encode64('0')+"&s=r="+encode64(role)+"&c=&cn=&e="+encode64(curr_user.id)+"&#fg";
	  }else if(target==1){
	  	url = "dynamicData_storetrade_analysis.html?t="+encode64(1)+"&s=&c="+ encode64(pageStatusInfo.cityId)+"&cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&#fg";
	  }
	  window.open(url,"_blank");
  }
  
  //交易额 （按片区） 
  function goToAreaGMVToSend() {
	      var role = curr_user.usergroup.code;
	      var url = "";
		  var target=pageStatusInfo.targets;
		  if(target==0){
		  	url = "dynamicData_area_GMV.html?t="+encode64('0')+"&s=&cn=&r="+encode64(role)+"&c=&e="+encode64(curr_user.id)+"&#fg";
		  }else if(target==1){
		  	url = url = "dynamicData_area_GMV.html?t="+encode64(1)+"&s=r="+encode64(role)+"&c="+encode64(pageStatusInfo.cityId)+"&cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&#fg";
		  }
	      window.open(url,"_blank");
  }
  
  //付费用户(按门店)
  function goToNewaddcus(){
  	  var url = "";
	  var target=pageStatusInfo.targets;
	  if(target==0){
	  	url = "dynamicData_newaddcus_analysis.html?t="+encode64('0')+"&s=&c=&cn=&e="+encode64(curr_user.id)+"&#ff";
	  }else if(target==1){
	  	url = "dynamicData_newaddcus_analysis.html?t="+encode64(1)+"&s=&c="+ encode64(pageStatusInfo.cityId)+"&cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&#ff";
	  }
	  window.open(url,"_blank");
  }

  //门店毛利
  function goToStoreprofit(){
      var url = "";
      var target=pageStatusInfo.targets;
      if(target==0){
          url = "dynamicData_storeprofit_analysis.html?t="+encode64('0')+"&s=&c=&cn=&e="+encode64(curr_user.id)+"&#ff";
      }else if(target==1){
          url = "dynamicData_storeprofit_analysis.html?t="+encode64(1)+"&s=&c="+ encode64(pageStatusInfo.cityId)+"&cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&#ff";
      }
      window.open(url,"_blank");
  }


    function goToGaxMaoli() {
        var role = curr_user.usergroup.code;
        var url = "";
        var target=pageStatusInfo.targets;
        if(target==0){
            url = "dynamicData_gax_analysis.html?t="+encode64('0')+"&s=&cn=&r="+encode64(role)+"&c=&e="+encode64(curr_user.id)+"&#fg";
        }else if(target==1){
            url = url = "dynamicData_gax_analysis.html?t="+encode64(1)+"&s=r="+encode64(role)+"&c="+encode64(pageStatusInfo.cityId)+"&cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&#fg";
        }else if(target==2){
            url = url = "dynamicData_gax_analysis.html?t="+encode64(1)+"&s=r="+encode64(role)+"&c="+encode64(pageStatusInfo.cityId)+"&cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&so="+encode64(currentStore.storeno)+"&e="+encode64(EMPLOYEE_NO)+"&sn="+encode64(currentStore.name)+"&#fg";
        }
        window.open(url,"_blank");
    }

  //事业群毛利
  function goToDeptprofit() {
      var role = curr_user.usergroup.code;
      var url = "";
      var target=pageStatusInfo.targets;
      if(target==0){
          url = "dynamicData_deptprofit_analysis.html?t="+encode64('0')+"&cn=&r="+encode64(role)+"&c=&dn=&e="+encode64(curr_user.id);
      }else if(target==1){
          url =  "dynamicData_deptprofit_analysis.html?t="+encode64(1)+"&r="+encode64(role)+"&c="+encode64(pageStatusInfo.cityId)+"&cn="+encode64(pageStatusInfo.cityName)+"&dn=&e="+encode64(curr_user.id);
      }
      window.open(url,"_blank");
  }
  
  //城市毛利
  function goToCityprofit(){
      var url = "";
      var target=pageStatusInfo.targets;
      if(target==0){
          url = "dynamicData_cityprofit_analysis.html?t="+encode64('0')+"&s=&c=&cn=&e="+encode64(curr_user.id)+"&#ff";
      }else if(target==1){
          url = "dynamicData_cityprofit_analysis.html?t="+encode64(1)+"&s=&c="+ encode64(pageStatusInfo.cityId)+"&cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&#ff";
      }
      window.open(url,"_blank");
  }

  //毛利统计
  function goToProfitStat(){
      var url = "";
      var target=pageStatusInfo.targets;
      if(target==0){
          url = "dynamicData_profit_analysis.html?t="+encode64('0')+"&s=&c=&cn=&e="+encode64(curr_user.id)+"&#ff";
      }else if(target==1){
          url = "dynamicData_profit_analysis.html?t="+encode64(1)+"&s=&c="+ encode64(pageStatusInfo.cityId)+"&cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&#ff";
      }
      window.open(url,"_blank");
  }

//用户行为分析
function gotobehavior(){
    // var role = curr_user.usergroup.code;
    var url = "";
    var target=pageStatusInfo.targets;
    if(target==0){
        url = "user_behavior.html";
    }
    window.open(url,"user_behavior");
}

function reportFiledown(){
    var url = "";
    var target=pageStatusInfo.targets;
    if(target==0){
        url = "report_filedown.html";
    }
    window.open(url,"user_behavior");
}


  /**
   * 片区内拉新GMV
   */
  function goToNewAddCustomerGMVToSend() {
	  var url = "";
	  var target=pageStatusInfo.targets;
	  if(target==0){
	  	url = "dynamicData_newAddCustomer_GMV.html?t="+encode64('0')+"&s=&c=&cn&e="+encode64(curr_user.id)+"&#ff";
	  }else if(target==1){
	  	url = "dynamicData_newAddCustomer_GMV.html?t="+encode64(1)+"&s=&c="+ encode64(pageStatusInfo.cityId)+"&cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&#ff";
	  }
	  window.open(url,"_blank");
  }
  
  //重点产品gmv(门店)
  function goToGMVInfo(){
  	  var url = "";
	  var target=pageStatusInfo.targets;
	  if(target==0){
	  	url = "dynamicData_gmv_analysis.html?t="+encode64('0')+"&s=&c=&cn=&e="+encode64(curr_user.id)+"&#fg";
	  }else if(target==1){
	  	url = "dynamicData_gmv_analysis.html?t="+encode64(1)+"&s=&c="+ encode64(pageStatusInfo.cityId)+"&cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&#fg";
	  }
	  window.open(url,"dynamicData_gmv_analysis"); 
  }
  
  //重点产品gmv(片区)
  function goToEmphasesProductsGMVToSend() {
	  //crm_alert(0,"尚未开放！");
      var store_name=$("#more_storeDiv").find('select option:selected').text();
      var url = "";
	  var target=pageStatusInfo.targets;
	  if(target==0){
	  	url = "dynamicData_emphasesProducts_GMV.html?t="+encode64('0')+"&s=&c=&cn=&e="+encode64(curr_user.id)+"&#fg";
	  }else if(target==1){
	  	url = "dynamicData_emphasesProducts_GMV.html?t="+encode64(1)+"&s=&c="+ encode64(pageStatusInfo.cityId)+"&cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&#fg";
	  }
      window.open(url,"dynamicData_emphasesProducts_GMV");
  }
  //事业部（服务专员）交易额
  function goToStoreTradeOfDept(){
	  var role = curr_user.usergroup.code;
	  var url = "";
	  var target=pageStatusInfo.targets;
	  if(target==0){
	  	url = "dynamicData_dept_gmv.html?t="+encode64('0')+"&s=&sn=&c=&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&cn=";
	  }else if(target==1){
	  	url = "dynamicData_dept_gmv.html?t="+encode64(1)+"&s=&sn=&c="+ encode64(pageStatusInfo.cityId)+"&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&cn="+encode64(pageStatusInfo.cityName);
	  }
	  window.open(url,"abnormal_order"); 
	 
  }
  
  //送单量
//  function goToSendorders(){
//	  var url = "";
//	  var target=pageStatusInfo.targets;
//	  if(target==0){
//	  	url = "dynamicData_sendorders_analysis.html?t="+encode64('0')+"&s=&c=&cn=&e="+encode64(curr_user.id)+"&#ff";
//	  }else if(target==1){
//	  	url = "dynamicData_sendorders_analysis.html?t="+encode64(1)+"&s=&c="+ encode64(pageStatusInfo.cityId)+"&cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&#ff";
//	  }
//	  window.open(url,"dynamicData_sendorders_analysis");
//  }
  
  function goToSendOrders() {
      var role = curr_user.usergroup.code;
      var url = "";
	  var target=pageStatusInfo.targets;
	  if(target==0){
	  	url = "dynamicData_employee_sendOrders.html?t="+encode64('0')+"&s=&cn=&r="+encode64(role)+"&c=&e="+encode64(curr_user.id)+"&#fg";
	  }else if(target==1){
	  	url = url = "dynamicData_employee_sendOrders.html?t="+encode64(1)+"&s=r="+encode64(role)+"&c="+encode64(pageStatusInfo.cityId)+"&cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&#fg";
	  }
      window.open(url,"_blank");
}
  
  
  //好评次数
  function goToRewardtimes(){
	  var url = "";
	  var target=pageStatusInfo.targets;
	  if(target==0){
	  	url = "dynamicData_rewardtimes_analysis.html?t="+encode64('0')+"&s=&c=&cn=&e="+encode64(curr_user.id)+"&#ff";
	  }else if(target==1){
	  	url = "dynamicData_rewardtimes_analysis.html?t="+encode64(1)+"&s=&c="+ encode64(pageStatusInfo.cityId)+"&cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&#ff";
	  }
	  window.open(url,"_blank");
  }
  //拜访记录
  function goToRelation(){
	  var url = "";
	  var target=pageStatusInfo.targets;
	  if(target==0){
	  	url = "dynamicData_relation_analysis.html?t="+encode64('0')+"&s=&c=&cn=&e="+encode64(curr_user.id)+"&#fg";
	  }else if(target==1){
	  	url = "dynamicData_relation_analysis.html?t="+encode64(1)+"&s=&c="+ encode64(pageStatusInfo.cityId)+"&cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&#fg";
	  }
	  window.open(url,"dynamicData_relation_analysis");
  }
  
 //用户画像
  function goToCustomer(){
	  var url = "";
	  var target=pageStatusInfo.targets;
	  if(target==0){
	  	url = "dynamicData_customer_analysis.html?t="+encode64('0')+"&s=&c=&cn=&e="+encode64(curr_user.id)+"&#fg";
	  }else if(target==1){
	  	url = "dynamicData_customer_analysis.html?t="+encode64(1)+"&s=&c="+ encode64(pageStatusInfo.cityId)+"&cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&#fg";
	  }
	 window.open(url,"dynamicData_customer_analysis");
  }
  
  function initCustomerInfo(){
	  if(curr_user!=null&&curr_user.usergroup.code.indexOf('ZB')!=0){
		  //如果不是总部 隐藏 用户画像
		  // $("#user_model").hide();
	  }
  }
  //用户画像（新）
  function goTosjfxIndex(){
	  var url = "sjfx-index.html";
	  window.open(url,"sjfx-index");
  }

  //用户总览
  function goToUserAnalysis(){
      var url = "user_analysis.html";
      window.open(url,"user_analysis");
  }
 
//快递代送
  function goToExpressToSend(){
	  var url = "";
	  var target=pageStatusInfo.targets;
	  if(target==0){
	  	url = "dynamicData_express_send.html?t="+encode64('0')+"&s=&c=&cn=&e="+encode64(curr_user.id)+"&#ff";
	  }else if(target==1){
	  	url = "dynamicData_express_send.html?t="+encode64(1)+"&s=&c="+ encode64(pageStatusInfo.cityId)+"&cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&#ff";
	  }
      window.open(url,"dynamicData_express_send");
  }
  
  function goToAbnormalOrderResult(){
	  //t:职务 s:门店ID c:城市ID
	  var role = curr_user.usergroup.code;
	  var url = "";
	  var target=pageStatusInfo.targets;
	  if(target==0){
	  	url = "abnormal_order_result.html?t="+encode64('0')+"&s=&sn=&c=&cn=&e="+encode64(curr_user.id)+"&r="+encode64(role);
	  }else if(target==1){
	  	url = "abnormal_order_result.html?t="+encode64(1)+"&s=&sn=&c="+ encode64(pageStatusInfo.cityId)+"&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&cn="+encode64(pageStatusInfo.cityName);
	  }
	  window.open(url,"abnormal_orde_result"); 
  }
  
  //异常订单
  function goToAbnormalOrder(){
	  //t:职务 s:门店ID c:城市ID
	  var role = curr_user.usergroup.code;
	  var url = "";
	  var target=pageStatusInfo.targets;
	  if(target==0){
	  	url = "abnormal_order.html?t="+encode64('0')+"&s=&sn=&c=&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&cn=";
	  }else if(target==1){
	  	url = "abnormal_order.html?t="+encode64(1)+"&s=&sn=&c="+ encode64(pageStatusInfo.cityId)+"&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&cn="+encode64(pageStatusInfo.cityName);
	  }
	  window.open(url,"_blank");
  }
  
  function useData(){
	  crm_alert(0,"尚未开放！");
  }
  
  function aboutCustomerInfo(){
	  crm_alert(0,"尚未开放！");
  }
  
  function aboutOrderInfo(){
	  crm_alert(0,"尚未开放！");
  }
  
  //订单档案
  function searchData(){
	  try{
		  saveAccessModuleLog("数据动态系统","订单档案",returnCitySN,0,"");
	  }catch(error){}
	  var role = curr_user.usergroup.code;
	  var url = "";
	  var target=pageStatusInfo.targets;
	  if(target==0){
	  	url = "searchData_view.html?t="+encode64('0')+"&s=&sn=&c=&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&cn=";
	  }else if(target==1){
	  	url = "searchData_view.html?t="+encode64(1)+"&e="+encode64(curr_user.id)+"&cn="+encode64(pageStatusInfo.cityName);
	  }
	  window.open(url,"searchData_view");
  }
  //商品销售档案
  function searchItemData(){
	  try{
		  saveAccessModuleLog("数据动态系统","商品销售档案",returnCitySN,0,"");
	  }catch(error){}
	  var role = curr_user.usergroup.code;
	  var url = "";
	  var target=pageStatusInfo.targets;
	  if(target==0){
	  	url = "searchDataItem_view.html?t="+encode64('0')+"&s=&sn=&c=&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&cn=";
	  }else if(target==1){
	  	url = "searchDataItem_view.html?t="+encode64(1)+"&e="+encode64(curr_user.id)+"&cn="+encode64(pageStatusInfo.cityName);
	  }
	  window.open(url,"searchDataItem_view");
  }

    //社员档案
    function goToMemberDataStat(){
        var role = curr_user.usergroup.code;
        var url = "";
        var target=pageStatusInfo.targets;
        if(target==0){
            url = "memberData_list.html?t="+encode64('0')+"&so=&s=&sn=&c=&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&cn=";
        }else if(target==1){
            url = "memberData_list.html?t="+encode64(1)+"&so=&s=&sn=&c=cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&#ff";
        }
        window.open(url,"memberData_list");
    }

  //用户档案
  function goToUserProfileStat(){
	  var role = curr_user.usergroup.code;
	  var url = "";
	  var target=pageStatusInfo.targets;
	  if(target==0){
	  	url = "userProfile_view.html?t="+encode64('0')+"&s=&sn=&c=&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&cn=";
	  }else if(target==1){
	  	url = "userProfile_view.html?t="+encode64(1)+"&s=&sn=&c=cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&#ff";
	  }
	  window.open(url,"userProfile_view"); 
   }
  
  //数据查找-员工数据
  function searchEmployeeData(){
	  try{
		  saveAccessModuleLog("数据动态系统","员工档案",returnCitySN,0,"");
	  }catch(error){}
	  var role = curr_user.usergroup.code;
	  //总部与城市的区分
	  var target=pageStatusInfo.targets;
	  var url = "humanresources_list.html?t="+encode64(target)+"&s=&sn=&c=&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&cn=";
	  window.open(url,"humanresources_list");
  }
  
  
  //数据运营-运营数据-门店运营new
  function searchStoreOperationData(){
	  var role = curr_user.usergroup.code;
	  var target=pageStatusInfo.targets;
	  var url = "searchStoreOperation_view.html?t="+encode64(target)+"&s=&sn=&c=&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&cn=";
	  window.open(url,"store_operation");
  }
/*  
  //数据运营-运营数据-线下网络
  function goToOfflineNetwork(){
  	  var role = curr_user.usergroup.code;
  	  //总部与城市的区分
	  var target=pageStatusInfo.targets;
	  var url = "searchOfflineNetwork_view.html?t="+encode64(target)+"&s=&sn=&c=&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&cn=";
	  window.open(url,"offline_network");
  }
*/  
  //数据查找-片区数据
  function searchArea(){
  	  var url = "dynamicData_areaInfo.html";
	  window.open(url,"_blank");
  }
  
  //数据查找-小区数据
  function searchTinyvillageData(){
  	  var url = "";
	  var target=pageStatusInfo.targets;
	  if(target==0){
	  	url = "dynamicData_tinyvillage.html?t="+encode64('0')+"&s=&c=&cn=&e="+encode64(curr_user.id);
	  }else if(target==1){
	  	url = "dynamicData_tinyvillage.html?t="+encode64(target)+"&c="+ encode64(pageStatusInfo.cityId)+"&cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id);
	  }
	  window.open(url,"dynamicData_tinyvillage");
  }
  //数据查找-小区数据
  function searchStoreData(){
  	  var url = "";
	  var target=pageStatusInfo.targets;
	  if(target==0){
	  	url = "dynamicData_storeInfo.html?t="+encode64('0')+"&s=&c=&cn=&e="+encode64(curr_user.id);
	  }else if(target==1){
	  	url = "dynamicData_storeInfo.html?t="+encode64(target)+"&c="+ encode64(pageStatusInfo.cityId)+"&cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id);
	  }
	  window.open(url);
  }
  
  //数据地图-热力图-营业额热力图
  function gotogmvheat(){
	  var url = "../aboutMap/data_index.html?t=1&nav=heat&type=yye&s=&c=&cn=&e=";
	  window.open(url,"data_index");  
  }
  //数据地图-热力图-订单热力图
  function gotoorderheat(){
	  var url = "../aboutMap/data_index.html?t=1&nav=heat&type=ddl&s=&c=&cn=&e=";
	  window.open(url,"data_index");  
  }
  //数据地图-热力图-用户热力图
  function gotocustomerheat(){
	  var url = "../aboutMap/data_index.html?t=1&nav=heat&type=yhl&s=&c=&cn=&e=";
	  window.open(url,"data_index");  
  }
  
  
  //数据地图-热力图-营业额热力图
  function gotogmvheatxq(){
	  var url = "../aboutMap/data_index.html?t=1&nav=village_heat&type=yye&s=&c=&cn=&e=";
	  window.open(url,"data_index");  
  }
  //数据地图-热力图-订单热力图
  function gotoorderheatxq(){
	  var url = "../aboutMap/data_index.html?t=1&nav=village_heat&type=ddl&s=&c=&cn=&e=";
	  window.open(url,"data_index");  
  }
  //数据地图-热力图-用户热力图
  function gotocustomerheatxq(){
	  var url = "../aboutMap/data_index.html?t=1&nav=village_heat&type=yhl&s=&c=&cn=&e=";
	  window.open(url,"data_index");  
  }
  
  
  
  //数据地图-覆盖范围-门店覆盖范围
  function goToMapView(){
	  var role = curr_user.usergroup.code;
	  var url = "../aboutMap/data_index.html?t=1&nav=store&s=&c=&cn=&e=";
	  window.open(url,"data_index"); 
  }
  //数据地图-覆盖范围-片区分布图
  function goToTinyArea(){
	  var role = curr_user.usergroup.code;
	  var url = "../aboutMap/data_index.html?t=1&nav=tiny&s=&c=&cn=&e=";
	  window.open(url,"data_index"); 
  }
  //数据地图-动态图-国安侠分布
  function gotoemployeefb(){
	  var url = "../aboutMap/data_index.html?t=1&nav=emp&s=&c=&cn=&e=";
	  window.open(url,"data_index");  
  }
  
//数据模型-社区模型-基础数据模型  //全国
  function goToMemberAnalysis(){
	  var role = curr_user.usergroup.code;
	  var url = "";
	  var target=pageStatusInfo.targets;
	  if(target==0){
	  	url = "member_analysis.html?t="+encode64('0')+"&s=r="+encode64(role)+"&c=&cn=&e="+encode64(curr_user.id)+"&#fg";
	  }else if(target==1){
		  url = "member_analysis_city.html?t="+encode64(1)+"&s=&c="+ encode64(pageStatusInfo.cityId)+"&cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&#fg";
	  }
	  window.open(url,"member_analysis");  
  }

//数据下载-财务中心数据下载 //全国
function goToFileDownload(){
    var role = curr_user.usergroup.code;
    var url = "";
    var target=pageStatusInfo.targets;
    if(target==0){
        url = "download_file.html?t="+encode64('0')+"&s=r="+encode64(role)+"&c=&cn=&e="+encode64(curr_user.id)+"&#fg";
    }else if(target==1){
        url = "download_file.html?t="+encode64(1)+"&s=&c="+ encode64(pageStatusInfo.cityId)+"&cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&#fg";
    }
    window.open(url,"download_file");
}


//数据模型-优易模型-基础数据模型  //全国
function goToYouyiInfo(){
    var role = curr_user.usergroup.code;
    var url = "";
    var target=pageStatusInfo.targets;
    if(target==0){
        url = "youyi_product.html?t="+encode64('0')+"&s=r="+encode64(role)+"&c=&cn=&e="+encode64(curr_user.id)+"&#fg";
    }else if(target==1){
        url = "youyi_product.html?t="+encode64(1)+"&s=&c="+ encode64(pageStatusInfo.cityId)+"&cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&#fg";
    }
    window.open(url,"youyi_product");
}
    //社员简报  //全国
    function goToMemberInfo(){
        var role = curr_user.usergroup.code;
        var url = "";
        var target=pageStatusInfo.targets;
        if(target==0){
            url = "member_info.html?t="+encode64('0')+"&s=r="+encode64(role)+"&c=&cn=&e="+encode64(curr_user.id)+"&#fg";
        }else if(target==1){
            url = "member_info.html?t="+encode64(1)+"&s=&c="+ encode64(pageStatusInfo.cityId)+"&cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&#fg";
        }
        window.open(url,"member_info");
    }
  //数据模型-社区模型-基础数据模型  //全国
  function toToBaseDataModule(){
  	  var url = "";
	  var target=pageStatusInfo.targets;
	  if(target==0){
	  	url = "../aboutMap/china_city_dataview.html";
	  }else if(target==1){
	  	var citystr = "city";
	  	url="../aboutMap/china_city_dataview.html?c="+encode64(pageStatusInfo.cityId)+"&iscity="+encode64(citystr)+"&cn="+encode64(pageStatusInfo.cityName);
	  }
	  window.open(url,"iframe");  
  }
  
  
  //XX排名 总部
  function searchRank(str){
		var targets = encode64(pageStatusInfo.targets);
		var targets_ = pageStatusInfo.targets;
		var provinceName = encode64('');
		var provinceId = encode64('');
		var cityId = "";
		var cityName= "";
		if(targets_==0){
			cityId=encode64('');
			cityName=encode64('');
		}else if(targets_==1){
			cityId=encode64(pageStatusInfo.cityId);
		    cityName=encode64(pageStatusInfo.cityName);

		}
	    var type= "";
	    var url = "";
	  	if(str==1){
	  		type=encode64("city_gmv");
	  		url = "headquarters_rank.html?"+"p="+provinceId+"&cs="+cityId+"&tps="+type+"&fs="+targets+"&cn="+cityName;
	  	}else if(str==2){
	  		type=encode64("store_gmv");
	  		url = "headquarters_rank.html?"+"p="+provinceId+"&cs="+cityId+"&tps="+type+"&fs="+targets+"&cn="+cityName;
	  	}else if(str==4){
	  		type=encode64("guoanxia_gmv");
	  		url = "headquarters_rank.html?"+"p="+provinceId+"&cs="+cityId+"&tps="+type+"&cn="+cityName+"&fs="+targets;
	  	}else if(str==5){
	  		type=encode64("commodity_gmv");
	        url = "headquarters_rank.html?p="+provinceId+"&pn="+provinceName+"&cs="+cityId+"&cn="+cityName+"&tps="+type+"&fs="+targets;
	  	}else if(str==6){
	  		type=encode64("businessDep_gmv");
	  		url = "headquarters_rank.html?"+"p="+provinceId+"&cs="+cityId+"&tps="+type+"&fs="+targets+"&cn="+cityName;
	  	}else if(str==7){
	  		type=encode64("channel_gmv");
	  		url = "headquarters_rank.html?"+"p="+provinceId+"&cs="+cityId+"&tps="+type+"&fs="+targets+"&cn="+cityName;
	  	}
	    window.open(url);
	  }
  
  //跳转公海 订单界面 
  function goToPublicOrder(){
	  //t:职务 s:门店ID c:城市ID
	  var role = curr_user.usergroup.code;
	  //总部与城市的区分
	  var target=pageStatusInfo.targets;
	  if(target==1){
		  //如果是城市
		  var cityName=((pageStatusInfo.cityName ==""||typeof(pageStatusInfo.cityName) == "undefined") ? '' : pageStatusInfo.cityName);
	  	  var cityId=(pageStatusInfo.cityId =="" ? '' : pageStatusInfo.cityId);
	  	  var url = "public_order.html?t="+encode64("1")+"&s=&sn=&c="+ encode64(cityId)+"&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&cn="+encode64(cityName);
		  window.open(url,"public_order"); 
	  }else{
		  var url = "public_order.html?t="+encode64("0")+"&s=&sn=&c=&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&cn=";
		  window.open(url,"public_order"); 
	  }
	  
  }
  //退出
  function doLogout() { 
		
		layer.confirm('确定退出？', {
			  	title:['提示','color:#fff'],
			  	offset:['100px','550px'],
			  	move:false,
			  	closeBtn:0,
			  	skin:'layer-ext-crmskin',
				btn: ['确定','取消'], //按钮
			}, function(){
				urlStr = "../logout.do";
				window.location.href = urlStr;
			}, function(){
			  
			});
	}
var initClick = function(){
	var targets = encode64(pageStatusInfo.targets);
	$("#city_gmv_more").on('click',function(){
    	var type=encode64("city_gmv");
    	var cityId=encode64(pageStatusInfo.cityId =="" ? '' : pageStatusInfo.cityId);
    	var cityName=encode64((pageStatusInfo.cityName ==""||typeof(pageStatusInfo.cityName) == "undefined") ? '' : pageStatusInfo.cityName);
        var url = "headquarters_rank.html?"+"p=&tps="+type+"&cs="+cityId+"&fs="+targets+"&pn=&cn="+cityName;
        window.open(url);
    });
    $("#store_gmv_more").on('click',function(){
    	var type=encode64("store_gmv");
    	var cityId=encode64(pageStatusInfo.cityId =="" ? '' : pageStatusInfo.cityId);
    	var cityName=encode64((pageStatusInfo.cityName ==""||typeof(pageStatusInfo.cityName) == "undefined") ? '' : pageStatusInfo.cityName);
        var url = "headquarters_rank.html?"+"p=&tps="+type+"&cs="+cityId+"&fs="+targets+"&pn=&cn="+cityName;
        window.open(url);
    });
    $("#store_order_more").on('click',function(){
    	var type=encode64("store_order");
    	var cityId=encode64(pageStatusInfo.cityId =="" ? '' : pageStatusInfo.cityId);
    	var cityName=encode64((pageStatusInfo.cityName ==""||typeof(pageStatusInfo.cityName) == "undefined") ? '' : pageStatusInfo.cityName);
        var url = "headquarters_rank.html?"+"p=&tps="+type+"&cs="+cityId+"&fs="+targets+"&pn=&cn="+cityName;
        window.open(url);
    });
    $("#guoanxia_gmv_more").on('click',function(){
    	var type=encode64("guoanxia_gmv");
    	var cityId=encode64(pageStatusInfo.cityId =="" ? '' : pageStatusInfo.cityId);
    	var cityName=encode64((pageStatusInfo.cityName ==""||typeof(pageStatusInfo.cityName) == "undefined") ? '' : pageStatusInfo.cityName);
        var url = "headquarters_rank.html?"+"p=&tps="+type+"&cs="+cityId+"&fs="+targets+"&pn=&cn="+cityName;
        window.open(url);
    });
    $("#commodity_gmv_more").on('click',function(){
    	var type=encode64("commodity_gmv");
    	var cityName=encode64((pageStatusInfo.cityName ==""||typeof(pageStatusInfo.cityName) == "undefined") ? '' : pageStatusInfo.cityName);
    	var cityId=encode64(pageStatusInfo.cityId =="" ? '' : pageStatusInfo.cityId);
        var url = "headquarters_rank.html?p=&pn=&cs="+cityId+"&cn="+cityName+"&tps="+type+"&fs="+targets;
        window.open(url);
    });
    $("#businessDep_gmv_more").on('click',function(){
    	var type=encode64("businessDep_gmv");
    	var cityId=encode64(pageStatusInfo.cityId =="" ? '' : pageStatusInfo.cityId);
    	var cityName=encode64((pageStatusInfo.cityName ==""||typeof(pageStatusInfo.cityName) == "undefined") ? '' : pageStatusInfo.cityName);
        var url = "headquarters_rank.html?"+"p=&tps="+type+"&cs="+cityId+"&fs="+targets+"&pn=&cn="+cityName;
        window.open(url);
    });
    $("#channel_gmv_more").on('click',function(){
    	var type=encode64("channel_gmv");
    	var cityId=encode64(pageStatusInfo.cityId =="" ? '' : pageStatusInfo.cityId);
    	var cityName=encode64((pageStatusInfo.cityName ==""||typeof(pageStatusInfo.cityName) == "undefined") ? '' : pageStatusInfo.cityName);
        var url = "headquarters_rank.html?"+"p=&tps="+type+"&cs="+cityId+"&fs="+targets+"&pn=&cn="+cityName;
        window.open(url);
    });
    $("#channel_order_more").on('click',function(){
    	var type=encode64("channel_order");
    	var cityId=encode64(pageStatusInfo.cityId =="" ? '' : pageStatusInfo.cityId);
    	var cityName=encode64((pageStatusInfo.cityName ==""||typeof(pageStatusInfo.cityName) == "undefined") ? '' : pageStatusInfo.cityName);
        var url = "headquarters_rank.html?"+"p=&tps="+type+"&cs="+cityId+"&targets="+targets+"&pn=&cn="+cityName;
        window.open(url);
    });
    $("#gmv_rank_more").on('click',function(){
    	var role = curr_user.usergroup.code;
	   var url = "";
	   var target=pageStatusInfo.targets;
	   if(target==0){
	  	 url = "index_K.html?t="+encode64('0')+"&s=&sn=&c=&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&cn=";
	   }else if(target==1){
	  	 url = "index_K.html?t="+encode64(1)+"&s=&sn=&c=cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&#ff";
	   }
	   window.open(url,"index_K");
    });
    $("#net_more").on('click',function(){
        var url = "index_city_net.html";
        window.open(url);
    });
    $("#turnover_customer_more").on('click',function(){
       goToMemberAnalysis();
    });
    $("#customer_analysis_more").on('click',function(){
        goToUserMember();
    });
}
function outputcents(amount) {//小数部分(两位)
    amount = Math.round(((amount) - Math.floor(amount)) * 100);
    return (amount < 10 ? '.0' + amount : '.' + amount);
}
function doCurMonjob(str){
	var outerDiv = $("<div style='font-size: 18px;font-family: roboto-thin; margin: 0 auto; color: #f7f7f7;'>").attr("class","text-center");
	var string_s = str.substring(0,str.indexOf(".")).split(";");
	for(var i=0;i<string_s.length;i++){
		if(string_s[i]==','){
			var span = $("<span style='width:15px;display: inline-block;'>").html(string_s[i]);
			outerDiv.append(span);
		}else{
			var span = $("<span style='width:15px;display: inline-block;'>").html(string_s[i]);
			outerDiv.append(span);
		}
	}
	$("#tradesumofcurmonth").after(outerDiv);
}
function doHistoryjob(str){
	var outerDiv = $("<div style='font-size: 18px;font-family: roboto-thin; margin: 0 auto; color: #f7f7f7;'>").attr("class","mt-number-animate text-center");
	var string_s = str.substring(0,str.indexOf(".")).split(";");
	for(var i=0;i<string_s.length;i++){
		if(string_s[i]==','){
			var span = $("<span style='width:15px;display: inline-block;'>").html(string_s[i]);
			outerDiv.append(span);
		}else{
			var span = $("<span style='width:15px;display: inline-block;'>").attr("class","mt-number-animate-span").html(string_s[i]);
			outerDiv.append(span);
		}
	}
	$("#tradesumofhistory").after(outerDiv);
}






/**
 * 访问系统功能统计
 * @businessType
 * @modulefunc
 * @returnCitySN
 * @usertype
 * @token
 */
function saveAccessModuleLog(businessType,modulefunc,returnCitySN,usertype,token){
		var loguser={};
		loguser.citynames=returnCitySN.cname;
		loguser.loginip=returnCitySN.cip;
		loguser.token=token;
		loguser.businessType=businessType;
		loguser.modulefunc=modulefunc;
		loguser.usertype=usertype;
		doManager("UserAccessModuleLogManager","saveAccessModuleLog",[loguser], function(data, textStatus, XMLHttpRequest){
		},false); 
	}

//产品运营-数据排名(总部)-
function searchProductData(){
	  var url = "dynamicProduct_Info.html?t=MQ==&s=&c=MQ==&cn=JXU1MzE3JXU0RUFD&e=MTAwMDQw&target="+encode64(0);
	  window.open(url,"dynamicData_areaInfo");
  }

function js_alert(){
	crm_alert(0,"近期开放，敬请期待");
}

function js_alert_temporary(){
    crm_alert(0,"由于近期将频繁清洗订单收入数据，订单档案功能临时关闭至11月2日。");
}

  //切换城市
  function showMoreCity(){
	 
	  layer.open({
		  type: 1,
   		  title:  ['更多城市', 'color:#fff'],
   		  shadeClose: true,
   		  shade:[0.1, '#393D49'],
   		  moveOut:false,
   		  skin: 'layer-ext-crmskin',
   		  area: ['250px','100px'],
   		  offset: ['200px','600px'],
   		  content:$("#more_cityDiv"),
   		  success:function(layer,index){
   			 $("#layui-layer-iframe1").attr("style","width:50px");
   		  },
   		  cancel: function(index, layero){ 
   			 layer.close(index);
   			}  
   		});  
  }
  function changeCity(){
	  
	  var city_id = $("#switch_city").val();
	  var index=$("#switch_city")[0].selectedIndex ;
	  var cityname = $("#switch_city")[0].options[index].text;
	  var URL="?"+"c="+encode64(city_id)+"&cn="+encode64(cityname);
	  window.location.href="index_headquarters.html"+URL;
  }
    //获取城市的住户数、小区、街道
  function getTotalOfCity(){
	  doManager("StoreManager", "getAllStoreOfCSZJ",[curr_user.id,pageStatusInfo.cityId],
				function(data, textStatus, XMLHttpRequest) {
					if (data.result) {
						var resultJson= JSON.parse(data.data);
						$("#tinyVillage_total").html(resultJson.tinyVillage);
						$("#village_total").html(resultJson.village);
						$("#town_total").html(resultJson.town);
					}
			},false);
	    
  }
  function changeMoney (x) {
    x=x/10000;
    var y = '';
    if(parseInt(x)/10000<=1){
    	y=ForDight(x,2)+"万";
    }else if(parseInt(x)/10000>1){
    	x=x/10000+'';
    	y=ForDight(x,2)+"亿";
    }
    return y;
}
function ForDight(str,How){  
    Dight = Math.round(str*Math.pow(10,How))/Math.pow(10,How);  
    return Dight;  
 }
function changeMoneyByDigit (x,t) {
	var regin = x;
    var s=x/10000;
    var y = '';
    if(parseInt(s)/10000<=1&&parseInt(s)/10000>0){
    	y=ForDight(s,t)+"万";
    }else if(parseInt(s)/10000==0){
    	y=ForDight(regin,t);
    }else if(parseInt(s)/10000>1){
    	s=x/10000+'';
    	y=ForDight(s,t)+"亿";
    }else if(regin<0&&Math.abs(parseInt(s)/10000)<=1){
    	y=ForDight(s,t)+"万";
    }else if(regin<0&&Math.abs(parseInt(s)/10000)==0){
    	y=ForDight(regin,t);
    }else if(regin<0&&Math.abs(parseInt(s)/10000)>1){
    	s=x/10000+'';
    	y=ForDight(s,t)+"亿";
    }
    return y;
}
//跳转事业群gmv
function goToDeptGMV(){
	  var role = curr_user.usergroup.code;
      var url = "";
	  var target=pageStatusInfo.targets;
	  if(target==0){
	  	url = "dynamicData_department_gmv.html?t="+encode64('0')+"&cn=&r="+encode64(role)+"&c=&dn=&e="+encode64(curr_user.id);
	  }else if(target==1){
	  	url =  "dynamicData_department_gmv.html?t="+encode64(1)+"&r="+encode64(role)+"&c="+encode64(pageStatusInfo.cityId)+"&cn="+encode64(pageStatusInfo.cityName)+"&dn=&e="+encode64(curr_user.id);
	  }
      window.open(url,"_blank");
}

//跳转事业群用户
function goToDeptConsumer(){
	  var role = curr_user.usergroup.code;
      var url = "";
	  var target=pageStatusInfo.targets;
	  if(target==0){
	  	url = "dynamicData_department_consumer.html?t="+encode64('0')+"&cn=&r="+encode64(role)+"&c=&dn=&e="+encode64(curr_user.id);
	  }else if(target==1){
	  	url =  "dynamicData_department_consumer.html?t="+encode64(1)+"&r="+encode64(role)+"&c="+encode64(pageStatusInfo.cityId)+"&cn="+encode64(pageStatusInfo.cityName)+"&dn=&e="+encode64(curr_user.id);
	  }
      window.open(url,"_blank");
}

function findArray(array, feature, all) {
    var all = arguments[2] ? arguments[2] : true;
    for(var index in array){
        var cur = array[index];
        if(feature instanceof Object){
            var allRight = true;
            for(var key in feature){
                var value = feature[key];
                if(cur[key].indexOf(value)>-1) return index;
                if(all && cur[key] != value){
                    allRight = false;
                    break;
                }
            }
            if(allRight) return index;
        }else{
            if(cur == feature){
                return index;
            }
        }
    }
    return -1;
}
function showMoreSummaryStatistics(){
	  $(".info_head").mouseover(function(){
	    $(this).css('width','35%');
	    $(this).find("dl").css('width','50%');
	    $("#info_head_dl").show();
	  });
	  $(".info_head").mouseleave(function(){
	    $(this).css('width','25%');
	    $(this).find("dl:first").css('width','100%');
	    $("#info_head_dl").hide();
	  });
	  $(".info_bottom").mouseover(function(){
	    $(this).css('width','50%');
	    $(this).find(".info_bottom_left").show();
	    $(this).find(".info_bottom_dl").removeClass("col-sm-6").addClass("col-sm-3");
	  });
	  $(".info_bottom").mouseleave(function(){
	    $(this).css('width','25%');
	    $(this).find(".info_bottom_right").removeClass("col-sm-3").addClass("col-sm-6");
	    $(this).find(".info_bottom_left").hide();
	  });
}
function getStoreKindsNumber(){
	   var reqestParameter = {
            month:pageStatusInfo.currentMonth,
            year:pageStatusInfo.currentYear,
            provinceId:pageStatusInfo.provinceId,
            cityId:pageStatusInfo.cityId
        }
        // 门店种类个数
        var startTime = new Date().getTime();
        doManager("dynamicManager", "getStoreKindCount",[reqestParameter],
            function(data, textStatus, XMLHttpRequest) {
                if (data.result) {
                    var resultJson = JSON.parse(data.data);
                    $("#info_head_dl").empty();
                    $.each(eval(resultJson['kind']), function (idx, val) {
                    	var dt = $("<dt>").html(val['storetypename']);
                    	var dd = $("<dd>").html(val['store_kind_count']);
                    	$("#info_head_dl").append(dt).append(dd);
                    });
                }
            });
}
function menuShowByRole(){
	/*
	  if(pageStatusInfo.targets==0){
		$("#city_net").show();
	  }else if(pageStatusInfo.targets==1){
		  $("#city_net").hide();
	  }
	  */
    if(pageStatusInfo.targets==0){
        $("#store_rent").show();
    }else if(pageStatusInfo.targets==1){
        $("#store_rent").hide();
    }
}
function  clearCache(){
	localStorage.clear();
	window.parent.location=getRootPath() + "/crm/index_headquarters.html";
}

//社员邀请统计
function goToMemberInvitation(){
	  var role = curr_user.usergroup.code;
      var url = "";
	  var target=pageStatusInfo.targets;
	  if(target==0){
	  	url = "dynamicData_member_invitation.html?t="+encode64('0')+"&s=&cn=&r="+encode64(role)+"&c=&e="+encode64(curr_user.id);
	  }else if(target==1){ 
	  	url  = "dynamicData_member_invitation.html?t="+encode64(1)+"&s=r="+encode64(role)+"&c="+encode64(pageStatusInfo.cityId)+"&cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id);
	  }
      window.open(url,"_blank");
}

function refreshCurrentData(){
        var date=new Date();
        var h=date.getHours();
        var m=date.getMinutes();
        var s=date.getSeconds();
        if((h==7&&m==0&&s==0)||(h==12&&m==0&&s==0)){
		    localStorage.clear();
	        showPageContent(pageStatusInfo);  
        }
        return true;
}
function  clearFirstCache(){
	localStorage.clear();
}




//221GMV统计
function goTo221GMV(){

    var role = curr_user.usergroup.code;
    var url = "";
    var target=pageStatusInfo.targets;
    if(target==0){
        url = "dynamicData_gmv_tto.html?t="+encode64('0')+"&c=&cn=&s=&e="+encode64(curr_user.id)+"&r="+encode64(role)
    }else if(target==1){
        url = "dynamicData_gmv_tto.html?t="+encode64(1)+"&s=r="+encode64(role)+"&c="+encode64(pageStatusInfo.cityId)+"&cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id);;
    }

    window.open(url,"_blank");
}

function settingClockByTimeOut(_args1, _args2) {
    var _type = 0,
        timeFn, _flag = true,
        ms = _args2,
        callBackFn = _args1,
        self = this;
    this.getTimeOut = function() {
        var _callee = arguments.callee;
        if (_flag) { //内部错误,内部强制中断，终止递归
            if (_type == 0) { //外部终止递归
                timeFn = setTimeout(function() {
                    //console.log("定时任务开始执行："+new Date().getSeconds());
                    _flag = callBackFn();
                    //console.log("定时任务结束执行："+new Date().getSeconds());
                    _callee();
                }, ms);
            } else {
                if (timeFn) clearTimeout(timeFn);
                console.error(500, "定时器已终止,外部终止...");
            }
        } else {
            if (timeFn) clearTimeout(timeFn);
            console.error(500, "定时器已终止,回调函数出现错误或内部强制终止...");
        }
    };
    this.close = function(_args1) {
        _type = _args1 || 1;
    };
    self.getTimeOut();
}
function startRefreshPage() {
    refreshId = new settingClockByTimeOut(refreshCurrentData,1000);
}
//去除 空格,回车符,全角符
function formatString(s) {
    if (s != null) {
		  s = String(s);
          s = s.replace(/([\s\u3000]*|[\r\n\u3000]*)/ig,''); 
    }
    return s;
}
function showTooltip(){
	$("#attention").show();
}
function hideTooltip(){
	$("#attention").hide();
}
function showTooltip2(){
	$("#attention2").show();
}
function hideTooltip2(){
	$("#attention2").hide();
}
function showTooltip3(){
	$("#attention3").show();
}
function hideTooltip3(){
	$("#attention3").hide();
}
function showTooltip4(){
	$("#attention4").show();
}
function hideTooltip4(){
	$("#attention4").hide();
}
function showTooltip5(){
	$("#attention5").show();
}
function hideTooltip5(){
	$("#attention5").hide();
}
function getScreenWidth(){
/*
  var screenWidth = screen.width;
  var screenHeight = screen.height;
  if(screenWidth>=1280){//1600
  	$(".text-muted").removeClass("text-muted2").addClass("text-muted1");
  }else{
  	$(".text-muted").removeClass("text-muted1").addClass("text-muted2");
  }
  */
  $(".text-muted").removeClass("text-muted2").addClass("text-muted1");
}
function showsumofcurmonthprofit(pageStatusInfo){
	var order_month_profit;
	var reqestParameter = {
            month:pageStatusInfo.currentMonth,
            year:pageStatusInfo.currentYear,
            provinceId:pageStatusInfo.provinceId,
            cityId:pageStatusInfo.cityId
    }
	doManager("massOrderItemManager", "queryMonthprofit",[reqestParameter],
       function(data, textStatus, XMLHttpRequest) {
	        if (data.result) {
	            var resultJson = JSON.parse(data.data);
	            var jData = JSON.parse(resultJson.gmv)[0];
	            var real_profit = (jData.total_profit - jData.return_profit - jData.order_fee-jData.baosun).toFixed(2);
	            
	            order_month_profit = real_profit;
	            order_month_profit = order_month_profit+"";
	        }else{
	        	order_month_profit = "0";
	        }
		$("#tradesumofcurmonthsprofit").html(changeMoneyByDigit(order_month_profit,2));
    });
    return order_month_profit;
}
function showsumofYesterdayprofit(pageStatusInfo){
	var order_yesterday_profit;
	var reqestParameter = {
            month:pageStatusInfo.currentMonth,
            year:pageStatusInfo.currentYear,
            provinceId:pageStatusInfo.provinceId,
            cityId:pageStatusInfo.cityId
    }
	doManager("massOrderItemManager", "queryYesterdayprofit",[reqestParameter],
       function(data, textStatus, XMLHttpRequest) {
	        if (data.result) {
	            var resultJson = JSON.parse(data.data);
	            var jData = JSON.parse(resultJson.gmv)[0];
	            var real_profit;
	            if(typeof(jData.total_profit)!="undefined"){
		            real_profit = (jData.total_profit - jData.return_profit - jData.order_fee).toFixed(2);
		            
		            order_yesterday_profit = real_profit;
		            order_yesterday_profit = order_yesterday_profit+"";
	            }else{
	            	order_yesterday_profit = "0";
	            }
	        }else{
	        	order_yesterday_profit = "0";
	        }
		$("#tradesumofyesprofit").html(changeMoneyByDigit(order_yesterday_profit,2));
    });
    return order_yesterday_profit;
}


//下载列表
function download_table(){

    doManager('treportFiledownManager','selectReportFileDowns',["null"],function(data){
        if(data.result){
            var dataresult= JSON.parse(data.data);
            if(dataresult != undefined && dataresult != null && dataresult.length){
                var objtr = $("#head_tr");
                document.getElementById("head_tr").innerHTML = "";
//                objtr.append("<tr> <td class='downtr'>名称</td>  <td class='downtr'>状态</td> <td class='downtr'>操作</td></tr>");
                var html = "";
                for(var i=0; i<dataresult.length; i++){
                    var file_filename = dataresult[i].filename;
                    var file_status = dataresult[i].mark1;
                    var file_id = dataresult[i].id;
                    if(file_status == 0){
                        file_status = "正在生成文件...";
                    }else{
                        file_status = "已生成文件";
                    }
                    var file_username = dataresult[i].username;

                    var file_enfilename = dataresult[i].url;
                    var file_times = dataresult[i].downTimes;
                    var heml_name = dataresult[i].tableLogic;
                    if(heml_name == "DDDA"){
                        heml_name = "订单档案";
                    }else if(heml_name == "SPXSDA"){
                        heml_name = "商品销售档案"
                    }

                    var url = dataresult[i].url;
                    if(dataresult[i].mark1 == 0){
//                        html = html + "<tr><td class='downtr'>"+ file_filename +"</td><td class='downtr'> " + file_status +"</td><td class='downtr'><a class='downa' style='color:gray;' disabled='true'>下载</a></td></tr>";
                        html = html + "<dl class='divcontent'><dt class='downleft'><img src='dist/img/xlsx.png'/></dt><dd class='downright'><div style='padding-top: 1px;'><div class='downrightdiv'><span style='color:#1a73e8;'>"+ heml_name +"</span></div><div class='downrightdiv'><span class='downrightspan'>文件名</span><span>：</span><span style='color:#1a73e8;'>"+ file_filename +"</span><span class='xlsx'>.csv</span></div><div class='downrightdiv'><span class='downrightspan'>状 &nbsp;&nbsp;态</span><span>：</span><span>" + file_status +"</span><a class='downrighta' style='color:gray;' disabled='true'>下载</a></div></div></dd> </dl>"
                    }else{
//                        html = html + "<tr><td class='downtr'>"+ file_filename +"</td><td class='downtr'> " + file_status +"</td><td class='downtr'><a class='downa' href='"+ url+"' onclick='addTimes("+file_id+","+file_times+")'>下载</a></td></tr>";
                        html = html + "<dl class='divcontent'><dt class='downleft'><img src='dist/img/xlsx.png'/></dt><dd class='downright'><div style='padding-top: 1px;'><div class='downrightdiv'><span style='color:#1a73e8;'>"+ heml_name +"</span></div><div class='downrightdiv'><span class='downrightspan'>文件名</span><span>：</span><span style='color:#1a73e8;'>"+ file_filename +"</span><span class='xlsx'>.csv</span></div><div class='downrightdiv'><span class='downrightspan'>状 &nbsp;&nbsp;态</span><span>：</span><span>" + file_status +"</span><a class='downrighta' href='"+ url+"'>下载</a></div></div></dd> </dl>"

                    }

                }
                //objtr.clean();


                objtr.append(html);
            }
            $("#dialog2").css("display","block");
        }
    })
}

function reportFiledown(){
    var url = "";

    url = "filedown_list.html";

    window.open(url,"filedown_list");
}


$(document).bind('click',function(e){
    var e = e || window.event; //浏览器兼容性
    var elem = e.target || e.srcElement;
    while (elem) { //循环判断至跟节点，防止点击的是div子元素
        if (elem.id && elem.id=='dialog2') {
            return;
        }
        elem = elem.parentNode;
    }
    var showPanel = document.getElementById("dialog2");
    showPanel.style.display = "none";
});

//打开下载中心"模态框"
$(".dialog_open2").click(function(){
    dialogOpen2()
});
//关闭下载中心"模态框"
$(".dialog_remove2").click(function(){
    dialogRemove2()
})
//遮罩层
var  _$mask = $("#mask1");
var $dialog2 = $("#dialog2");

/**
 * 打开下载中心模态框
 * @param animationSwitch 切换模态框的动画 true:淡入 false：滑入
 * @returns {*|{opacity}} void方法
 */

function dialogOpen2(){
    if(animationSwitch = true){
        _$mask.fadeIn(200, function() {
            $dialog2.fadeIn(600);
            $(".newimg").hide()
        })
    }else{
        _$mask.fadeIn(200, function() {
            $dialog2.stop().slideDown(200);
        });
    }
}

/**
 * 关闭下载中心模态框
 * @param animationSwitch 切换模态框的动画 true:淡出 false：滑出
 * @returns {*|{opacity}} void方法
 */

function dialogRemove2(){
    if(animationSwitch = true){
        _$mask.fadeOut(200, function() {
            $dialog2.fadeOut(600);
        })
    }else{
        _$mask.fadeOut(200, function() {
            $dialog2.stop().slideUp(200)
        });
    }
}
function initSwiper(){
  var swiper = new Swiper('.swiper-container', {
    autoplay : {
      delay:5000,
      disableOnInteraction : false,
    },
    //autoplay:false,
    pagination: {
      el: '.swiper-pagination',
      clickable: true,
    },
    navigation: {
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev',
    },
    watchOverflow: true,//因为仅有1个slide，swiper无效
    allowTouchMove: false
  });
  $('.swiper-slide').mouseenter(function () {
    swiper.autoplay.stop();
  })
  $('.swiper-slide').mouseleave(function () {
    swiper.autoplay.start();
  })
}
//散点图
  var dataBJ = [
  ];

  var dataSH = [
  ];
  var titleTH = [
  ];
  var dataUH = [
  ];
  var dataVH = [
  ];
  var dataWH = [
  ];
  var dataXH = [
  ];
  var dataYH = [
  ];

    var schema = [
    {name: 'store', index: 0, text: '门店'},
    {name: 'profit', index: 1, text: '毛利'},
    {name: 'gmv', index: 2, text: 'GMV'},
    {name: 'user', index: 3, text: '用户量'},
    {name: 'city', index: 4, text: '城市'},
    {name: 'month', index: 5, text: '日期'}
  ];
  Array.prototype.pushNoRepeat = function(){
    for(var i=0; i<arguments.length; i++){
      var ele = arguments[i];
      if(this.indexOf(ele) == -1){
          this.push(ele);
      }
  }
};
var getYesterdayStorePorfit = function(pageStatusInfo){
	var reqestParameter = {
            month:pageStatusInfo.currentMonth,
            year:pageStatusInfo.currentYear,
            provinceId:pageStatusInfo.provinceId,
            cityId:pageStatusInfo.cityId
    }
	doManager("massOrderItemManager", "queryYesterdayprofitForStore",[reqestParameter],
       function(data, textStatus, XMLHttpRequest) {
	        if (data.result) {
	            var resultJson = JSON.parse(data.data);
	            var profit_store_num = "profit_store_5";
	            $("#"+profit_store_num).empty();
	            var th_tr = $('<tr class="ranking_h"/>');
	            var th_td_1 = $('<th style="width:20%">排名</th>');
	            var th_td_2 = $('<th style="width:30%">门店名称</th>');
	            var th_td_3 = $('<th style="width:30%">毛利(元)</th>');
	            var th_td_4 = $('<th style="width:20%">趋势</th>');
	            th_tr.append(th_td_1).append(th_td_2).append(th_td_3).append(th_td_4);
	            var profit_store_num = "profit_store_5";
	            $("#"+profit_store_num).append(th_tr);
	            createTableProfitData(resultJson,profit_store_num);
	        }
    });

}
var getStorePorfitForSevenday = function(pageStatusInfo){
	var reqestParameter = {
            month:pageStatusInfo.currentMonth,
            year:pageStatusInfo.currentYear,
            provinceId:pageStatusInfo.provinceId,
            cityId:pageStatusInfo.cityId
    }
	doManager("massOrderItemManager", "queryprofitForStoreSevenDay",[reqestParameter],
       function(data, textStatus, XMLHttpRequest) {
	        if (data.result) {
	            var resultJson = JSON.parse(data.data);
	            var profit_store_num = "profit_store_3";
	            $("#"+profit_store_num).empty();
	            var th_tr = $('<tr class="ranking_h"/>');
	            var th_td_1 = $('<th style="width:20%">排名</th>');
	            var th_td_2 = $('<th style="width:30%">门店名称</th>');
	            var th_td_3 = $('<th style="width:30%">毛利(元)</th>');
	            var th_td_4 = $('<th style="width:20%">趋势</th>');
	            th_tr.append(th_td_1).append(th_td_2).append(th_td_3).append(th_td_4);
	            $("#"+profit_store_num).append(th_tr);
	            createTableProfitData(resultJson,profit_store_num);
	        }
    });

}
var getStorePorfitForThirtyday = function(pageStatusInfo){
	var reqestParameter = {
            month:pageStatusInfo.currentMonth,
            year:pageStatusInfo.currentYear,
            provinceId:pageStatusInfo.provinceId,
            cityId:pageStatusInfo.cityId
    }
	doManager("massOrderItemManager", "queryprofitForStoreThirtyday",[reqestParameter],
       function(data, textStatus, XMLHttpRequest) {
	        if (data.result) {
	            var resultJson = JSON.parse(data.data);
	            var profit_store_num = "profit_store_1";
	            $("#"+profit_store_num).empty();
	            var th_tr = $('<tr class="ranking_h"/>');
	            var th_td_1 = $('<th style="width:20%">排名</th>');
	            var th_td_2 = $('<th style="width:30%">门店名称</th>');
	            var th_td_3 = $('<th style="width:30%">毛利(元)</th>');
	            var th_td_4 = $('<th style="width:20%">趋势</th>');
	            th_tr.append(th_td_1).append(th_td_2).append(th_td_3).append(th_td_4);
	            $("#"+profit_store_num).append(th_tr);
	            createTableProfitData(resultJson,profit_store_num);
	        }
    });

}
var getStoreYesterdayMember = function(pageStatusInfo){
	var reqestParameter = {
            provinceId:pageStatusInfo.provinceId,
            cityId:pageStatusInfo.cityId
    }
	doManager("massOrderItemManager", "getStoreYesterdayMember",[reqestParameter],
       function(data, textStatus, XMLHttpRequest) {
	        if (data.result) {
	            var resultJson = JSON.parse(data.data);
	            var member_num = "member_6";
	            $("#"+member_num).empty();
	            var th_tr = $('<tr class="ranking_h"/>');
	            var th_td_1 = $('<th style="width:20%">排名</th>');
	            var th_td_2 = $('<th style="width:30%">门店名称</th>');
	            var th_td_3 = $('<th style="width:30%">社员开卡数</th>');
	            var th_td_4 = $('<th style="width:20%">趋势</th>');
	            th_tr.append(th_td_1).append(th_td_2).append(th_td_3).append(th_td_4);
	            $("#"+member_num).append(th_tr);
	            createTableMemberData(resultJson,member_num);
	        }
    });
}
var getStoreSevendayMember = function(pageStatusInfo){
	var reqestParameter = {
            provinceId:pageStatusInfo.provinceId,
            cityId:pageStatusInfo.cityId
    }
	doManager("massOrderItemManager", "getStoreSevendayMember",[reqestParameter],
       function(data, textStatus, XMLHttpRequest) {
	        if (data.result) {
	            var resultJson = JSON.parse(data.data);
	            var member_num = "member_4";
	            $("#"+member_num).empty();
	            var th_tr = $('<tr class="ranking_h"/>');
	            var th_td_1 = $('<th style="width:20%">排名</th>');
	            var th_td_2 = $('<th style="width:30%">门店名称</th>');
	            var th_td_3 = $('<th style="width:30%">社员开卡数</th>');
	            var th_td_4 = $('<th style="width:20%">趋势</th>');
	            th_tr.append(th_td_1).append(th_td_2).append(th_td_3).append(th_td_4);
	            $("#"+member_num).append(th_tr);
	            createTableMemberData(resultJson,member_num);
	        }
    });
}
var getStoreThirtydayMember = function(pageStatusInfo){
	var reqestParameter = {
            provinceId:pageStatusInfo.provinceId,
            cityId:pageStatusInfo.cityId
    }
	doManager("massOrderItemManager", "getStoreThirtydayMember",[reqestParameter],
       function(data, textStatus, XMLHttpRequest) {
	        if (data.result) {
	            var resultJson = JSON.parse(data.data);
	            var member_num = "member_2";
	            $("#"+member_num).empty();
	            var th_tr = $('<tr class="ranking_h"/>');
	            var th_td_1 = $('<th style="width:20%">排名</th>');
	            var th_td_2 = $('<th style="width:30%">门店名称</th>');
	            var th_td_3 = $('<th style="width:30%">社员开卡数</th>');
	            var th_td_4 = $('<th style="width:20%">趋势</th>');
	            th_tr.append(th_td_1).append(th_td_2).append(th_td_3).append(th_td_4);
	            $("#"+member_num).append(th_tr);
	            createTableMemberData(resultJson,member_num);
	        }
    });
}
var getYesterdayStoreProduct = function(pageStatusInfo){
	var reqestParameter = {
            month:pageStatusInfo.currentMonth,
            year:pageStatusInfo.currentYear,
            provinceId:pageStatusInfo.provinceId,
            cityId:pageStatusInfo.cityId
    }
	doManager("massOrderItemManager", "getYesterdayStoreProduct",[reqestParameter],
       function(data, textStatus, XMLHttpRequest) {
	        if (data.result) {
	            var resultJson = JSON.parse(data.data);
	            var product_num = "product_3";
	            $("#"+product_num).empty();
	            var th_tr = $('<tr class="ranking_h"/>');
	            var th_td_1 = $('<th style="width:20%">排名</th>');
	            var th_td_2 = $('<th style="width:30%">商品名称</th>');
	            var th_td_3 = $('<th style="width:20%">门店名称</th>');
	            var th_td_4 = $('<th style="width:10%">销量</th>');
	            var th_td_5 = $('<th style="width:20%">趋势</th>');
	            th_tr.append(th_td_1).append(th_td_2).append(th_td_3).append(th_td_4).append(th_td_5);
	            $("#"+product_num).append(th_tr);
	            createTableProductData(resultJson,product_num);
	        }
    });

}
var getStoreProductSevenDay = function(pageStatusInfo){
	var reqestParameter = {
            month:pageStatusInfo.currentMonth,
            year:pageStatusInfo.currentYear,
            provinceId:pageStatusInfo.provinceId,
            cityId:pageStatusInfo.cityId
    }
	doManager("massOrderItemManager", "getStoreProductSevenDay",[reqestParameter],
       function(data, textStatus, XMLHttpRequest) {
	        if (data.result) {
	            var resultJson = JSON.parse(data.data);
	            var product_num = "product_2";
	            $("#"+product_num).empty();
	            var th_tr = $('<tr class="ranking_h"/>');
	            var th_td_1 = $('<th style="width:20%">排名</th>');
	            var th_td_2 = $('<th style="width:30%">商品名称</th>');
	            var th_td_3 = $('<th style="width:20%">门店名称</th>');
	            var th_td_4 = $('<th style="width:10%">销量</th>');
	            var th_td_5 = $('<th style="width:20%">趋势</th>');
	            th_tr.append(th_td_1).append(th_td_2).append(th_td_3).append(th_td_4).append(th_td_5);
	            $("#"+product_num).append(th_tr);
	            createTableProductData(resultJson,product_num);
	        }
    });

}
var getStoreProductThirtyDay = function(pageStatusInfo){
	var reqestParameter = {
            month:pageStatusInfo.currentMonth,
            year:pageStatusInfo.currentYear,
            provinceId:pageStatusInfo.provinceId,
            cityId:pageStatusInfo.cityId
    }
	doManager("massOrderItemManager", "getStoreProductThirtyDay",[reqestParameter],
       function(data, textStatus, XMLHttpRequest) {
	        if (data.result) {
	            var resultJson = JSON.parse(data.data);
	            var product_num = "product_1";
	            $("#"+product_num).empty();
	            var th_tr = $('<tr class="ranking_h"/>');
	            var th_td_1 = $('<th style="width:20%">排名</th>');
	            var th_td_2 = $('<th style="width:30%">商品名称</th>');
	            var th_td_3 = $('<th style="width:20%">门店名称</th>');
	            var th_td_4 = $('<th style="width:10%">销量</th>');
	            var th_td_5 = $('<th style="width:20%">趋势</th>');
	            th_tr.append(th_td_1).append(th_td_2).append(th_td_3).append(th_td_4).append(th_td_5);
	            $("#"+product_num).append(th_tr);
	            createTableProductData(resultJson,product_num);
	        }
    });

}
function createTableProfitData(resultJson,profit_store_num){
	var lstLength = resultJson['lst_data'].length;
	            $.each(eval(resultJson['lst_data']), function (idx, val) {
	            	if(idx==0){
	            		var th_tr = $("<tr/>");
	            		var th_td_1 = $('<td><span class="text_big1"></span></td>');
	            		var th_td_2 = $('<td class="text-red" title="'+val['store_name']+'">'+val['store_name']+'</td>');
	            		var th_td_3 = $('<td>'+val['maoli']+'</td>');
	            		var th_td_4;
	            		if(val['rank']>=0){
	            			th_td_4 = $('<td class="text_up"><i class="fa fa-long-arrow-up"></i><span>'+val['rank']+'</span></td>');
	            		}else if(val['rank']<0){
	            			th_td_4 = $('<td class="text_down"><i class="fa fa-long-arrow-down"></i><span>'+val['rank']+'</span></td>');
	            		}else{
	            			th_td_4 = $('<td class="text_up"><i class="fa fa-long-arrow-up"></i><span>'+0+'</span></td>');
	            		}
	            		th_tr.append(th_td_1).append(th_td_2).append(th_td_3).append(th_td_4);
	            		$("#"+profit_store_num).append(th_tr);
	            	}else if(idx==1){
	            		var th_tr = $("<tr/>");
	            		var th_td_1 = $('<td><span class="text_big2"></span></td>');
	            		var th_td_2 = $('<td class="text-red" title="'+val['store_name']+'">'+val['store_name']+'</td>');
	            		var th_td_3 = $('<td>'+val['maoli']+'</td>');
	            		var th_td_4;
	            		if(val['rank']>=0){
	            			th_td_4 = $('<td class="text_up"><i class="fa fa-long-arrow-up"></i><span>'+val['rank']+'</span></td>');
	            		}else if(val['rank']<0){
	            			th_td_4 = $('<td class="text_down"><i class="fa fa-long-arrow-down"></i><span>'+val['rank']+'</span></td>');
	            		}else{
	            			th_td_4 = $('<td class="text_up"><i class="fa fa-long-arrow-up"></i><span>'+0+'</span></td>');
	            		}
	            		th_tr.append(th_td_1).append(th_td_2).append(th_td_3).append(th_td_4);
	            		$("#"+profit_store_num).append(th_tr);
	            	}else if(idx==2){
	            		var th_tr = $("<tr/>");
	            		var th_td_1 = $('<td><span class="text_big3"></span></td>');
	            		var th_td_2 = $('<td class="text-red" title="'+val['store_name']+'">'+val['store_name']+'</td>');
	            		var th_td_3 = $('<td>'+val['maoli']+'</td>');
	            		var th_td_4;
	            		if(val['rank']>=0){
	            			th_td_4 = $('<td class="text_up"><i class="fa fa-long-arrow-up"></i><span>'+val['rank']+'</span></td>');
	            		}else if(val['rank']<0){
	            			th_td_4 = $('<td class="text_down"><i class="fa fa-long-arrow-down"></i><span>'+val['rank']+'</span></td>');
	            		}else{
	            			th_td_4 = $('<td class="text_up"><i class="fa fa-long-arrow-up"></i><span>'+0+'</span></td>');
	            		}
	            		th_tr.append(th_td_1).append(th_td_2).append(th_td_3).append(th_td_4);
	            		$("#"+profit_store_num).append(th_tr);
	            	}else if(idx>2&&idx<=4){
	            		var th_tr = $("<tr/>");
	            		var th_td_1 = $('<td>'+(idx+1)+'</td>');
	            		var th_td_2 = $('<td title="'+val['store_name']+'">'+val['store_name']+'</td>');
	            		var th_td_3 = $('<td>'+val['maoli']+'</td>');
	            		var th_td_4;
	            		if(val['rank']>=0){
	            			th_td_4 = $('<td class="text_up"><i class="fa fa-long-arrow-up"></i><span>'+val['rank']+'</span></td>');
	            		}else if(val['rank']<0){
	            			th_td_4 = $('<td class="text_down"><i class="fa fa-long-arrow-down"></i><span>'+val['rank']+'</span></td>');
	            		}else{
	            			th_td_4 = $('<td class="text_up"><i class="fa fa-long-arrow-up"></i><span>'+0+'</span></td>');
	            		}
	            		th_tr.append(th_td_1).append(th_td_2).append(th_td_3).append(th_td_4);
	            		$("#"+profit_store_num).append(th_tr);
	            	}else if(idx==5){
	            		var th_tr_ = $('<tr/>');
            			var th_td_1 = $('<td colspan="4">---</td>');
            			th_tr_.append(th_td_1);
            			$("#"+profit_store_num).append(th_tr_);
	            	}else if(idx>=lstLength-5&&idx<=lstLength){
	            		var th_tr = $("<tr/>");
	            		var th_td_1 = $('<td>'+(idx+1)+'</td>');
	            		var th_td_2 = $('<td title="'+val['store_name']+'">'+val['store_name']+'</td>');
	            		var th_td_3 = $('<td>'+val['maoli']+'</td>');
	            		var th_td_4;
	            		if(val['rank']>=0){
	            			th_td_4 = $('<td class="text_up"><i class="fa fa-long-arrow-up"></i><span>'+val['rank']+'</span></td>');
	            		}else if(val['rank']<0){
	            			th_td_4 = $('<td class="text_down"><i class="fa fa-long-arrow-down"></i><span>'+val['rank']+'</span></td>');
	            		}else{
	            			th_td_4 =  $('<td class="text_up"><i class="fa fa-long-arrow-up"></i><span>'+0+'</span></td>');;
	            		}
	            		th_tr.append(th_td_1).append(th_td_2).append(th_td_3).append(th_td_4);
	            		$("#"+profit_store_num).append(th_tr);
	            	}
	            });
	            var th_tr_more = $('<tr/>');
	            var profit_store_url = "" ;
	            var betime = resultJson['betime'].replace(/-/g,"/");
	            var entime = resultJson['entime'].replace(/-/g,"/");
	            var cityId = encode64(pageStatusInfo.cityId==""?'':pageStatusInfo.cityId);
	  			var cityName = encode64(pageStatusInfo.cityName==""?'':pageStatusInfo.cityName);
	  			var target=pageStatusInfo.targets; 
	  			var redirectTag = "profit";
	  			console.log(cityName);
	            if(profit_store_num=='profit_store_1'){//30
	            	if(target==0){
						profit_store_url = "dynamicData_profit_analysis.html?t="+encode64('0')+"&e="+encode64(curr_user.id)+"&c="+cityId+"&s=&rt="+encode64(redirectTag)+"&time="+encode64(betime+"-"+entime)+"&so=&sn=&cn="+cityName;
					}else if(target==1){
						profit_store_url = "dynamicData_profit_analysis.html?t="+encode64('1')+"&e="+encode64(curr_user.id)+"&c="+cityId+"&s=&rt="+encode64(redirectTag)+"&time="+encode64(betime+"-"+entime)+"&so=&sn=&cn="+cityName;
					}
	            }else if(profit_store_num=='profit_store_5'){//昨天
	            	if(target==0){
						profit_store_url = "dynamicData_profit_analysis.html?t="+encode64('0')+"&e="+encode64(curr_user.id)+"&c="+cityId+"&s=&rt="+encode64(redirectTag)+"&time="+encode64(betime+"-"+entime)+"&so=&sn=&cn="+cityName;
					}else if(target==1){
						profit_store_url = "dynamicData_profit_analysis.html?t="+encode64('1')+"&e="+encode64(curr_user.id)+"&c="+cityId+"&s=&rt="+encode64(redirectTag)+"&time="+encode64(betime+"-"+entime)+"&so=&sn=&cn="+cityName;
					}
	            }else if(profit_store_num=='profit_store_3'){//7
	            	if(target==0){
						profit_store_url = "dynamicData_profit_analysis.html?t="+encode64('0')+"&e="+encode64(curr_user.id)+"&c="+cityId+"&s=&rt="+encode64(redirectTag)+"&time="+encode64(betime+"-"+entime)+"&so=&sn=&cn="+cityName;
					}else if(target==1){
						profit_store_url = "dynamicData_profit_analysis.html?t="+encode64('1')+"&e="+encode64(curr_user.id)+"&c="+cityId+"&s=&rt="+encode64(redirectTag)+"&time="+encode64(betime+"-"+entime)+"&so=&sn=&cn="+cityName;
					}
	            }
    			var th_td_more = $('<td colspan="5"><a href="#" onclick="openProfitUrl(\''+profit_store_url+'\')">查看更多</a></td>');
    			th_tr_more.append(th_td_more);
    			$("#"+profit_store_num).append(th_tr_more);

}
function createTableMemberData(resultJson,member_num){
	var lstLength = resultJson['lst_data'].length;
	            $.each(eval(resultJson['lst_data']), function (idx, val) {
	            	if(idx==0){
	            		var th_tr = $("<tr/>");
	            		var th_td_1 = $('<td><span class="text_big1"></span></td>');
	            		var th_td_2 = $('<td class="text-red" title="'+val['store_name']+'">'+val['store_name']+'</td>');
	            		var th_td_3 = $('<td>'+val['nowcount']+'</td>');
	            		var th_td_4;
	            		if(val['rank']>=0){
	            			th_td_4 = $('<td class="text_up"><i class="fa fa-long-arrow-up"></i><span>'+val['rank']+'</span></td>');
	            		}else if(val['rank']<0){
	            			th_td_4 = $('<td class="text_down"><i class="fa fa-long-arrow-down"></i><span>'+val['rank']+'</span></td>');
	            		}else{
	            			th_td_4 = $('<td class="text_up"><i class="fa fa-long-arrow-up"></i><span>'+0+'</span></td>');
	            		}
	            		th_tr.append(th_td_1).append(th_td_2).append(th_td_3).append(th_td_4);
	            		$("#"+member_num).append(th_tr);
	            	}else if(idx==1){
	            		var th_tr = $("<tr/>");
	            		var th_td_1 = $('<td><span class="text_big2"></span></td>');
	            		var th_td_2 = $('<td class="text-red" title="'+val['store_name']+'">'+val['store_name']+'</td>');
	            		var th_td_3 = $('<td>'+val['nowcount']+'</td>');
	            		var th_td_4;
	            		if(val['rank']>=0){
	            			th_td_4 = $('<td class="text_up"><i class="fa fa-long-arrow-up"></i><span>'+val['rank']+'</span></td>');
	            		}else if(val['rank']<0){
	            			th_td_4 = $('<td class="text_down"><i class="fa fa-long-arrow-down"></i><span>'+val['rank']+'</span></td>');
	            		}else{
	            			th_td_4 = $('<td class="text_up"><i class="fa fa-long-arrow-up"></i><span>'+0+'</span></td>');
	            		}
	            		th_tr.append(th_td_1).append(th_td_2).append(th_td_3).append(th_td_4);
	            		$("#"+member_num).append(th_tr);
	            	}else if(idx==2){
	            		var th_tr = $("<tr/>");
	            		var th_td_1 = $('<td><span class="text_big3"></span></td>');
	            		var th_td_2 = $('<td class="text-red" title="'+val['store_name']+'">'+val['store_name']+'</td>');
	            		var th_td_3 = $('<td>'+val['nowcount']+'</td>');
	            		var th_td_4;
	            		if(val['rank']>=0){
	            			th_td_4 = $('<td class="text_up"><i class="fa fa-long-arrow-up"></i><span>'+val['rank']+'</span></td>');
	            		}else if(val['rank']<0){
	            			th_td_4 = $('<td class="text_down"><i class="fa fa-long-arrow-down"></i><span>'+val['rank']+'</span></td>');
	            		}else{
	            			th_td_4 = $('<td class="text_up"><i class="fa fa-long-arrow-up"></i><span>'+0+'</span></td>');
	            		}
	            		th_tr.append(th_td_1).append(th_td_2).append(th_td_3).append(th_td_4);
	            		$("#"+member_num).append(th_tr);
	            	}else if(idx>2&&idx<=4){
	            		var th_tr = $("<tr/>");
	            		var th_td_1 = $('<td>'+(idx+1)+'</td>');
	            		var th_td_2 = $('<td title="'+val['store_name']+'">'+val['store_name']+'</td>');
	            		var th_td_3 = $('<td>'+val['nowcount']+'</td>');
	            		var th_td_4;
	            		if(val['rank']>=0){
	            			th_td_4 = $('<td class="text_up"><i class="fa fa-long-arrow-up"></i><span>'+val['rank']+'</span></td>');
	            		}else if(val['rank']<0){
	            			th_td_4 = $('<td class="text_down"><i class="fa fa-long-arrow-down"></i><span>'+val['rank']+'</span></td>');
	            		}else{
	            			th_td_4 = $('<td class="text_up"><i class="fa fa-long-arrow-up"></i><span>'+0+'</span></td>');
	            		}
	            		th_tr.append(th_td_1).append(th_td_2).append(th_td_3).append(th_td_4);
	            		$("#"+member_num).append(th_tr);
	            	}else if(idx==5){
	            		var th_tr_ = $('<tr/>');
            			var th_td_1 = $('<td colspan="4">---</td>');
            			th_tr_.append(th_td_1);
            			$("#"+member_num).append(th_tr_);
	            	}else if(idx>=lstLength-5&&idx<=lstLength){
	            		var th_tr = $("<tr/>");
	            		var th_td_1 = $('<td>'+(idx+1)+'</td>');
	            		var th_td_2 = $('<td title="'+val['store_name']+'">'+val['store_name']+'</td>');
	            		var th_td_3 = $('<td>'+val['nowcount']+'</td>');
	            		var th_td_4;
	            		if(val['rank']>=0){
	            			th_td_4 = $('<td class="text_up"><i class="fa fa-long-arrow-up"></i><span>'+val['rank']+'</span></td>');
	            		}else if(val['rank']<0){
	            			th_td_4 = $('<td class="text_down"><i class="fa fa-long-arrow-down"></i><span>'+val['rank']+'</span></td>');
	            		}else{
	            			th_td_4 =  $('<td class="text_up"><i class="fa fa-long-arrow-up"></i><span>'+0+'</span></td>');;
	            		}
	            		th_tr.append(th_td_1).append(th_td_2).append(th_td_3).append(th_td_4);
	            		$("#"+member_num).append(th_tr);
	            	}
	            });
	            var th_tr_more = $('<tr/>');
	            var member_url = "" ;
	            var betime = resultJson['betime'].replace(/-/g,"/");
	            var entime = resultJson['entime'].replace(/-/g,"/");
	            var cId = encode64(pageStatusInfo.cityId==""?'':pageStatusInfo.cityId);
	  			var cName = encode64(pageStatusInfo.cityName==""?'':pageStatusInfo.cityName);
	  			var flagBar = encode64("2");
	            if(member_num=='member_2'){//30
	            	member_url = "user_member_view.html?t="+encode64(0)+"&r=&c="+cId+"&cn="+cName+"&e="+encode64(curr_user.id)+"&beTi="+encode64(betime)+"&endT="+encode64(entime)+"&fb="+flagBar; 
	            }else if(member_num=='member_6'){//昨天
	            	dayTime = findTodayYesterdayDay().replace("-","/");;
	            	member_url = "user_member_view.html?t="+encode64(0)+"&r=&c="+cId+"&cn="+cName+"&e="+encode64(curr_user.id)+"&beTi="+encode64(betime)+"&endT="+encode64(entime)+"&fb="+flagBar; 
	            }else if(member_num=='member_4'){//7
	            	member_url = "user_member_view.html?t="+encode64(0)+"&r=&c="+cId+"&cn="+cName+"&e="+encode64(curr_user.id)+"&beTi="+encode64(betime)+"&endT="+encode64(entime)+"&fb="+flagBar; 
	            }
    			var th_td_more = $('<td colspan="5"><a href="#" onclick="openMemberUrl(\''+member_url+'\')">查看更多</a></td>');
    			th_tr_more.append(th_td_more);
    			$("#"+member_num).append(th_tr_more);

}
function createTableProductData(resultJson,product_num){
	var lstLength = resultJson['lst_data'].length;
	var count = resultJson['count'];
	            $.each(eval(resultJson['lst_data']), function (idx, val) {
	            	if(idx==0){
	            		var th_tr = $("<tr/>");
	            		var th_td_1 = $('<td><span class="text_big1"></span></td>');
	            		var th_td_2 = $('<td class="text-yellow" title="'+val['product_name']+'">'+val['product_name']+'<em><img src="dist/img/hot-r.png"> </em></td>');
	            		var th_td_3 = $('<td title="'+val['store_name']+'">'+val['store_name']+'</td>');
	            		var th_td_4 = $('<td title="'+val['store_name']+'">'+val['product_gmv']+'</td>');
	            		var th_td_5;
	            		if(val['rank']>=0){
	            			th_td_5 = $('<td class="text_up"><i class="fa fa-long-arrow-up"></i><span>'+val['rank']+'</span></td>');
	            		}else if(val['rank']<0){
	            			th_td_5 = $('<td class="text_down"><i class="fa fa-long-arrow-down"></i><span>'+val['rank']+'</span></td>');
	            		}else{
	            			th_td_5 = $('<td class="text_up"><i class="fa fa-long-arrow-up"></i><span>'+0+'</span></td>');
	            		}
	            		th_tr.append(th_td_1).append(th_td_2).append(th_td_3).append(th_td_4).append(th_td_5);
	            		$("#"+product_num).append(th_tr);
	            	}else if(idx==1){
	            		var th_tr = $("<tr/>");
	            		var th_td_1 = $('<td><span class="text_big2"></span></td>');
	            		var th_td_2 = $('<td class="text-yellow" title="'+val['product_name']+'">'+val['product_name']+'</td>');
	            		var th_td_3 = $('<td title="'+val['store_name']+'">'+val['store_name']+'</td>');
	            		var th_td_4 = $('<td title="'+val['store_name']+'">'+val['product_gmv']+'</td>');
	            		var th_td_5;
	            		if(val['rank']>=0){
	            			th_td_5 = $('<td class="text_up"><i class="fa fa-long-arrow-up"></i><span>'+val['rank']+'</span></td>');
	            		}else if(val['rank']<0){
	            			th_td_5 = $('<td class="text_down"><i class="fa fa-long-arrow-down"></i><span>'+val['rank']+'</span></td>');
	            		}else{
	            			th_td_5 = $('<td class="text_up"><i class="fa fa-long-arrow-up"></i><span>'+0+'</span></td>');
	            		}
	            		th_tr.append(th_td_1).append(th_td_2).append(th_td_3).append(th_td_4).append(th_td_5);
	            		$("#"+product_num).append(th_tr);
	            	}else if(idx==2){
	            		var th_tr = $("<tr/>");
	            		var th_td_1 = $('<td><span class="text_big3"></span></td>');
	            		var th_td_2 = $('<td class="text-yellow" title="'+val['product_name']+'">'+val['product_name']+'</td>');
	            		var th_td_3 = $('<td title="'+val['store_name']+'">'+val['store_name']+'</td>');
	            		var th_td_4 = $('<td title="'+val['store_name']+'">'+val['product_gmv']+'</td>');
	            		var th_td_5;
	            		if(val['rank']>=0){
	            			th_td_5 = $('<td class="text_up"><i class="fa fa-long-arrow-up"></i><span>'+val['rank']+'</span></td>');
	            		}else if(val['rank']<0){
	            			th_td_5 = $('<td class="text_down"><i class="fa fa-long-arrow-down"></i><span>'+val['rank']+'</span></td>');
	            		}else{
	            			th_td_5 = $('<td class="text_up"><i class="fa fa-long-arrow-up"></i><span>'+0+'</span></td>');
	            		}
	            		th_tr.append(th_td_1).append(th_td_2).append(th_td_3).append(th_td_4).append(th_td_5);
	            		$("#"+product_num).append(th_tr);
	            	}else if(idx>2&&idx<=4){
	            		var th_tr = $("<tr/>");
	            		var th_td_1 = $('<td>'+(idx+1)+'</td>');
	            		var th_td_2 = $('<td title="'+val['product_name']+'">'+val['product_name']+'</td>');
	            		var th_td_3 = $('<td title="'+val['store_name']+'">'+val['store_name']+'</td>');
	            		var th_td_4 = $('<td title="'+val['store_name']+'">'+val['product_gmv']+'</td>');
	            		var th_td_5;
	            		if(val['rank']>=0){
	            			th_td_5 = $('<td class="text_up"><i class="fa fa-long-arrow-up"></i><span>'+val['rank']+'</span></td>');
	            		}else if(val['rank']<0){
	            			th_td_5 = $('<td class="text_down"><i class="fa fa-long-arrow-down"></i><span>'+val['rank']+'</span></td>');
	            		}else{
	            			th_td_5 = $('<td class="text_up"><i class="fa fa-long-arrow-up"></i><span>'+0+'</span></td>');
	            		}
	            		th_tr.append(th_td_1).append(th_td_2).append(th_td_3).append(th_td_4).append(th_td_5);
	            		$("#"+product_num).append(th_tr);
	            		if(idx==4){
	            			var th_tr_ = $('<tr/>');
	            			var th_td_1 = $('<td colspan="5">---</td>');
	            			th_tr_.append(th_td_1);
	            			$("#"+product_num).append(th_tr_);
	            		}
	            	}else if(idx>4){
	            		var th_tr = $("<tr/>");
	            		var th_td_1 = $('<td>'+(count-idx)+'</td>');
	            		var th_td_2 = $('<td title="'+val['product_name']+'">'+val['product_name']+'</td>');
	            		var th_td_3 = $('<td title="'+val['store_name']+'">'+val['store_name']+'</td>');
	            		var th_td_4 = $('<td title="'+val['store_name']+'">'+val['product_gmv']+'</td>');
	            		var th_td_5;
	            		if(val['rank']>=0){
	            			th_td_5 = $('<td class="text_up"><i class="fa fa-long-arrow-up"></i><span>'+val['rank']+'</span></td>');
	            		}else if(val['rank']<0){
	            			th_td_5 = $('<td class="text_down"><i class="fa fa-long-arrow-down"></i><span>'+val['rank']+'</span></td>');
	            		}else{
	            			th_td_5 = $('<td class="text_up"><i class="fa fa-long-arrow-up"></i><span>'+0+'</span></td>');
	            		}
	            		th_tr.append(th_td_1).append(th_td_2).append(th_td_3).append(th_td_4).append(th_td_5);
	            		$("#"+product_num).append(th_tr);
	            	}
	            });
	            var th_tr_more = $('<tr/>');
	            var th_td_more = "";
	            var product_url = "";
	            if(product_num=='product_1'){//30
	            	product_url = "ranking.html?type="+encode64('yesterday')+"&cs="+encode64(pageStatusInfo.cityId)+"&ps="+encode64(pageStatusInfo.provinceId);
	            }else if(product_num=='product_3'){//昨天
	            	product_url = "ranking.html?type="+encode64('thirty')+"&cs="+encode64(pageStatusInfo.cityId)+"&ps="+encode64(pageStatusInfo.provinceId);
	            }else if(product_num=='product_2'){//7
	            	product_url = "ranking.html?type="+encode64('seven')+"&cs="+encode64(pageStatusInfo.cityId)+"&ps="+encode64(pageStatusInfo.provinceId);
	            }
    			//var th_td_more = $('<td colspan="5"><a href="'+product_url+'">查看更多</a></td>');
    			//var th_td_more = $('<td colspan="5"><a href="#">查看更多</a></td>');
    			var th_td_more = $('<td colspan="5"></td>');
    			th_tr_more.append(th_td_more);
    			$("#"+product_num).append(th_tr_more);

}
function openProfitUrl(url){
	window.open(url,"dynamicData_profit_analysis");
}
function openMemberUrl(url){
	window.open(url,"user_member_view");
}