var pageStatusInfo = {};
var mapChart;
var statisticExtendInfo;
var timerId;
// 城市排名(GMV)柱状图
var cityRankChartGmv;
var cityRankGmvOption;
//城市排名(GMV)矩形图
var cityUserGMV;
var cityUserOption;
// 门店排名(订单量)排名柱状图
var storeRankChartOrder;
var storeRankOrderOption;
// 门店排名(GMV)排名柱状图
var storeRankChartGmv;
var storeRankGmvOption;
//事业群排名(GMV)排名柱状图
var businessDepRankChartGMV;
var businessDepRankGMVOption;
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
		var reObj = new PMSRequestObject("userManager", "isScreenUser", [ screenlogin ]);
	    var callback = function callback(data, textStatus, XMLHttpRequest) {
	    	window.parent.location=getRootPath() + "/crm/index_headquarters_demo.html";
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
	//获得闪图数据
	getBeatJson();
	loginShow();
	//鼠标放概要统计展开
	showMoreSummaryStatistics();
	getStoreKindsNumber();
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
    // 解除装载状态
    setLoadingMark(false);
    //console.log('show whole page in ' + (new Date().getTime() - startTime) + ' millisecond');
    initClick();
    //点击大屏幕数据进入到监控页
    $(".data_new").click(function(){
    	//gotok();
	});
});

const CACHE_HEADER_STATISTIC = 'statistic_';
const CACHE_HEADER_HISTORY_DATA = 'historyData_';
const CACHE_HEADER_CUSTOMER_COUNT_DATA = 'customerData_';
const CACHE_HEADER_CITY_RANK_GVM = 'cityRankGmv_';
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
    //getLastMonthOrderCustomerCount(pageStatusInfo);
    // 显示历史数据
    getHistoryData(pageStatusInfo);
    getDailyData();

    // 显示统计概要
    pageStatusInfo.currentPage=1;
    getStatisticInfo(pageStatusInfo);

    // 显示地图
    showMap(pageStatusInfo);

    // 显示城市排名(GMV)
    getCityRankDataGmv(pageStatusInfo);
    
    // 显示门店排名(GMV)
    getStoreRankDataGmv(pageStatusInfo);

    // 显示门店排名(新用户量)
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
    //初始化城市排名矩形图
    cityUserGMV = echarts.init(document.getElementById('main12'));
    // 初始化门店(GMV)排名显示图
    storeRankChartGmv = echarts.init(document.getElementById('main3'));
    // 初始化门店(订单量)排名显示图
    storeRankChartOrder = echarts.init(document.getElementById('main7'));
    // 初始化事业群排名(GMV)显示图
	businessDepRankChartGMV = echarts.init(document.getElementById('main4'));
	cityRankGmvOption = {
    title:[
      //{text:"城市排名（GMV:元）",x: '2%', y: '0%',textStyle:{color:"#efefef",fontSize:"16"}},
      {x: '2%', y: '0%',textStyle:{color:"#efefef",fontSize:"16"}},
    ],
    tooltip : {
      trigger: 'axis',
      position: function (point, params, dom, rect, size) {
        // 固定在顶部
        return [point[0], '10%'];
      }
    },
    calculable : true,
    grid: {
      left: '3%',
      right: '5%',
      top: '17%',
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
        }
      }
    ],
    series : [
      {
        cursor: 'default',
        //name:'一街坊、八街坊东、八街西、永定路社区社区部',
        type:'line',
        data:[ 25.6, 76.7, 30.5,25.6, 76.7, 30.5],
        smooth: true,
        showAllSymbol: true,
        symbol: 'emptyCircle',
        symbolSize: 10,
        markPoint: {
          data: [
            {type: 'max', name: '最大值'},
            {type: 'min', name: '最小值'}
          ]
        },

      }
    ]
  };
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
	  var url = "current_shopkeeper_demo.html?p="+pId+"&c="+cId+"&s="+store_id+"&cn="+cName+"&ln="+encode64(curr_user.name)+"&f="+flag+"&fs="+encode64(pageStatusInfo.targets);
	     //window.open("current_shopkeeper_demo.html?s="+store_id+"&p=1&c=2","_self");
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
	  var url = "current_shopkeeper_demo.html?p="+pId+"&c="+cId+"&s="+encode64(store_id)+"&cn="+cName+"&ln="+encode64(curr_user.name)+"&f="+flag+"&fs="+encode64(pageStatusInfo.targets);
	     //window.open("current_shopkeeper_demo.html?s="+store_id+"&p=1&c=2","_self");
		  window.location.href=url; 
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
        cursor:'default',
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
  /*
    // 事件绑定
    businessDepRankChartGMV.on('click', function (params){
        var depName=encode64(businessDepRankGMVOption.legend.data[params.dataIndex]);
        window.open("index_BU.html?depname="+depName);
    });
    */
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
	                infoStr = infoStr+"<tr style='cursor:pointer'  id='tr_"+employee_no+"' /*onclick='goEmployee(this)'*/><td>"+parseInt(i+1)+"</td><td>"+employee_no+"</td><td>"+employee_name+"</td><td>"+employee_citySelect+"</td><td>"+employee_storename+"</td></tr>"
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
	var ddmd = $("<dd><a href='#' style='color:#FFFFFF;cursor:default' id='store_total'>载入中...</a></dd>");
	var dtdz= $("<dt id='storeKeeperT'>店长</dt>");
	var dddz = $("<dd><a href='#' style='color:#FFFFFF;cursor:default' id='storeKeeper_total'>载入中...</a></dd>");
	var dtmdry= $("<dt id='storePeoleT'>门店人员</dt>");
	var ddmdry = $("<dd><a href='#' style='color:#FFFFFF;cursor:default' id='employee_total'>载入中...</a></dd>");
    $("#gaiyaotongji").empty();
    $("#gaiyaotongji").append(dtmd).append(ddmd).append(dtdz).append(dddz).append(dtmdry).append(ddmdry);
    if(pageStatusInfo.targets==0){
    	var dtcs = $("<dt id='cityT'>").html("城市");
    	var ddcs = $("<dd id='cityD'><a href='#' style='color:#FFFFFF;cursor:default' id='city_total'>载入中...</a></dd>");
    	$("#gaiyaotongji").prepend(ddcs).prepend(dtcs);
    	$("#city_total").html("13" + "<span>个</span>");
	    $("#store_total").html("469" + "<span>个</span>");
	    $("#storeKeeper_total").html("953" + "<span>人</span>");
	    $("#employee_total").html("9380" + "<span>人</span>");
    }else if(pageStatusInfo.targets==1){
    	$("#store_total").html(469 + "<span>个</span>");
	    $("#storeKeeper_total").html(9380 + "<span>人</span>");
	    $("#employee_total").html(5434 + "<span>人</span>");
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
	showHistoryData();
};
// 显示历史数据
var showHistoryData = function () {
	/*
    $("#tradesumofcurmonthHid").html(parseInt(RandomNumByTime(7))+parseInt(RandomNum(10000,30000)));
    $("#tradesumofhistoryHid").html(parseInt(RandomNumByTime(7))+parseInt(RandomNum(10000,30000)));
    //$("#tradesumoflastmonthCustmomerHid").html(parseInt(historyData.last_customer_count));//上月用户量
    $("#tradesumoflasthistoryCustmomerHid").html(parseInt(RandomNumByTime(7))+parseInt(RandomNum(10000,30000)));
    //$("#tradesumoflastmonthOrderHid").html(parseInt(historyData.last_order_count==null?'0':historyData.last_order_count));
    $("#tradesumofyearHid").html(RandomNumByTime(7)+RandomNum(10000,30000)));
    */
    $("#tradesumofhistoryCustmomerHid").html(5479920);
    $("#tradesumofhistoryOrderHid").html(13074521);
    $("#tradesumofmonthOrderHid").html(434904);
    $("#tradesumofmonthCustmomerHid").html(211950);
    $("#tradesumofcurmonths").html(changeMoney(parseInt(6273.24*10000)));
    $("#tradesumofCurYears").html(changeMoney(parseInt(7.52*10000*10000)));
    $("#tradesumofhistorys").html(changeMoney(parseInt(53.97*10000*10000)));
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
                symbolSize: 20,
				showEffectOn: 'render',
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
				    shadowColor: '#fb8800'
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
    amap.setCity("天津");
    // 获取地图显示数据
    var storeServiceRange = getGaodeMapData(pageStatusInfo);
    var cityId = "3";
    var cityName = "天津";
    $.each(storeServiceRange, function (idx, val) {
        var serviceCoordinates = val.vertex;
        var storeId = val.store_id;
        var storeNo = val.storeNo;
        var storeName = val.storeName;
        //服务范围多边形
        var servicePolygon = new AMap.Polygon({
            map: amap,
            strokeColor: "#5c69cc", //线颜色
            strokeOpacity: 0.9, //线透明度
            strokeWeight: 2,    //线宽
            fillColor: "#046ad3", //填充色
            fillOpacity: 0.1,//填充透明度
            path: serviceCoordinates
            // extData:{"storeName":storeName,"marker":storeMarker,"storeId":storeId}
        });

        var storeMarker = new AMap.Marker({ //添加自定义点标记
            visible: false,
            map: amap,
            position: servicePolygon.getBounds().getCenter(), //基点位置
            content: '<div class="marker-route marker-marker-bus-from">' + storeName + '</div>',
            extData:{"storeName":storeName,"marker":storeMarker,"storeId":storeId}
        });
        servicePolygon.setExtData({"storeName": storeName, "marker": storeMarker, "storeId": storeId});
        servicePolygon.on("mouseover", function (e) {
                e.target.getExtData().marker.show();
        });
        servicePolygon.on("mouseout", function (e) {
            e.target.getExtData().marker.hide();
        });
        // 点击事件
        servicePolygon.on("click", function (e) {
            var storeId = "9900287";
            var provinceId = pageStatusInfo.provinceId == ""?"":pageStatusInfo.provinceId;
            var url = "current_shopkeeper_demo.html?p=" + encode64(provinceId) + "&c=" + encode64(cityId) + "&s=" + encode64(storeId) + "&cn=" + encode64(cityName) + "&zm=" + encode64(amap.getZoom()) + "&ln="+encode64(curr_user.name)+"&ln="+encode64(curr_user.name)+"&f=" + encode64("1")+"&fs="+encode64(pageStatusInfo.targets);
            window.location.href = url;
        });
        storeMarker.on("click",function(e){
			  var storeId = "9900287";
			  var provinceId = pageStatusInfo.provinceId == ""?"":pageStatusInfo.provinceId;
			  var url = "current_shopkeeper_demo.html?p=" + encode64(provinceId) + "&c=" + encode64(cityId) + "&s=" + encode64(storeId) + "&cn=" + encode64(cityName) + "&zm=" + encode64(amap.getZoom()) + "&ln="+encode64(curr_user.name)+"&f=" + encode64("1")+"&fs="+encode64(pageStatusInfo.targets);
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
    var cacheKey =  String(pageStatusInfo.getCacheKey()).split("_")[0];
    // 从缓存获取数据
    var storeServiceRange = JsCache.get(cacheKey);
    var cityId = pageStatusInfo.cityId;
    var serviceArea = tianjin_storeServiceDataOfCity();
    storeServiceRange = serviceArea.data;
    return storeServiceRange;
};
// 近七日GMV走势图
var getCityRankDataGmv = function (pageStatusInfo) {
    var cacheKey =  String(pageStatusInfo.getCacheKey()).split("_")[0];
    // 从缓存获取数据
        //console.log('show city rank graph base on js cache.')
        showCityRankGmv(indexData[cacheKey].rank_gmv);
};
// 显示近7天GMV走势
var showCityRankGmv = function (cityRankDataGmv) {
  	var data = getSevenTime(7);
    var data1 = [];
    $.each(eval(cityRankDataGmv['lst_data']), function (idx, val) {
    	//data.push(val['week_date']);
    	data1.push(val['week_gmv']);
    });
	cityRankGmvOption.xAxis[0].data = data;
    cityRankGmvOption.series[0].data = data1.reverse();
    cityRankGmvOption.title[0].text = "近7日GMV走势";
    cityRankChartGmv.setOption(cityRankGmvOption,true);
};
// 获取门店排名(GMV)数据
var getStoreRankDataGmv = function (pageStatusInfo) {
    var cacheKey =  String(pageStatusInfo.getCacheKey()).split("_")[0];
    // 从缓存获取数据
    showStoreRankGmv(indexData[cacheKey].store_gmv);
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
    var cacheKey = String(pageStatusInfo.getCacheKey()).split("_")[0];
    // 从缓存获取数据
    showStoreRankOrder(indexData[cacheKey].store_order_user_count);
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
    storeRankOrderOption.title[0].text="门店排名("+pageStatusInfo['currentMonth']+"月新用户量)";
    storeRankOrderOption.xAxis[0].data = xAxis;
    storeRankOrderOption.xAxis[0].extdata = extData;
    storeRankOrderOption.series[0].data = series;
    storeRankChartOrder.setOption(storeRankOrderOption);
};
// 获取国安侠排名(GMV)数据
var getGuoanManRankDataGMV = function (pageStatusInfo) {
    var cacheKey = String(pageStatusInfo.getCacheKey()).split("_")[0];
    // 从缓存获取数据
    showGuoanManRankGmv(indexData[cacheKey].guoanxia_rank_gmv);
};
// 显示国安侠排名(GMV)
var showGuoanManRankGmv = function (guoanManRankDataGMV) {
 var guoanManRankChartGMV = echarts.init(document.getElementById('main5'));
    // 柱状图属性：国安侠(GMV)排名
    var guoanManRankGMVOption = {
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
      },
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
      	cursor: 'default',
        name: 'GMV',
        type: 'bar',xAxisIndex: 0,yAxisIndex: 0,barWidth:'55%',
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
  var xAxis = [];
    var series = [];
    var extData = [];
    $.each(eval(guoanManRankDataGMV['gmv']), function (idx, val) {
        xAxis.push(val['employee_a_name']+"-"+val['city_name']+"-"+val['store_name']);
        series.push(parseInt(val['pesgmv']));
    });
    guoanManRankGMVOption.yAxis[0].data = xAxis.reverse();
    guoanManRankGMVOption.series[0].data = series.reverse();
    guoanManRankGMVOption.title[0].text="国安侠排名("+pageStatusInfo['currentMonth']+"月GMV)";
    guoanManRankChartGMV.setOption(guoanManRankGMVOption);
    // 事件绑定
};
// 获取商品排名数据
var getCommodityRankData = function (pageStatusInfo) {
    var cacheKey = String(pageStatusInfo.getCacheKey()).split("_")[0];
    // 从缓存获取数据
    showCommodityRank(indexData[cacheKey].product_rank_gmv);
};
// 显示商品排名
var showCommodityRank = function (commodityRankData) {
 // 初始化城市商品排名显示图
    var commodityRankChart = echarts.init(document.getElementById('main6'));
    var maskImage = new Image();
    maskImage.src = '../crm/sjfx-group_files/180209101040.png';
    // 柱状图属性：商品排名
    var commodityRankOption = {
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
    if(commodityRankData['gmv'].length>2){
	  	var commodityDataStr = '{"dataCloud":[';
	    $.each(eval(commodityRankData['gmv']), function (idx, val) {
	    	commodityDataStr+='{"name":"'+(val['product_name']+'').substring(0,6)+'","value":"'+val['product_count']+'","tooltips":"'+val['product_name']+'"},';
	    });
	    if(commodityRankData['gmv'].length>0){
	    	commodityDataStr = commodityDataStr.substring(0,commodityDataStr.lastIndexOf(","));
	    }
	    commodityDataStr+="]}";
	    var commodityDataObject = JSON.parse(commodityDataStr);
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
    var cacheKey =  String(pageStatusInfo.getCacheKey()).split("_")[0];
    showChannelRankGmv(indexData[cacheKey].channel_gmv);
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
    var cacheKey =  String(pageStatusInfo.getCacheKey()).split("_")[0];
    showBusinessDepRankGmv(indexData[cacheKey].dep_gmv);
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
	var cacheKey =  String(pageStatusInfo.getCacheKey()).split("_")[0];
    showCustomerNew(indexData[cacheKey]);
}
var showCustomerNew = function(customerNewAndConsume){
  customerNewChart = echarts.init(document.getElementById('main2'));
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
      right: '0',
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
  	var data = getSevenTime(30);
  	var data1 = [];
    var data2 = [];
    $.each(eval(customerNewAndConsume.new_month_userCount['lst_data']), function (idx, val) {
    	//data.push(val['week_date']);
    	data1.push(val['new_cus_count']);
		data2.push(val['pay_cus_count']);
    });
	customerNewChartOption.xAxis[0].data = data;
	customerNewChartOption.series[0].data = data1.reverse();
	customerNewChartOption.series[1].data = data2.reverse();
	customerNewChartOption.title.text="近30天客流趋势";
  	customerNewChart.setOption(customerNewChartOption,true);
  
}
var getTurnoverCustomerOrder = function(pageStatusInfo){
	var cacheKey =  String(pageStatusInfo.getCacheKey()).split("_")[0];
    showTurnoverCustomerOrder(indexData[cacheKey].new_week_userCount);
}
var showTurnoverCustomerOrder = function(turnoverCustomer){
  turnoverCustomerOrderChart = echarts.init(document.getElementById('main11'));
	// 客流分析
  turnoverCustomerOrderOption = {
    title: {
      text:"近7日客流分析",x: '5%', y: '0%',textStyle:{color:"#efefef",fontSize:"16"},
    },
    tooltip : {
      trigger: 'axis',
      formatter:function(params)//数据格式
            {
            var relVal = params[0]['name']+"<br/>";
            relVal += params[0]['marker']+params[0]['seriesName']+ ' : ' + String(params[0]['value']);
	        relVal+="<br/>";
            relVal += params[2]['marker']+params[2]['seriesName']+ ' : ' + String(params[2]['value']);
	        relVal+="<br/>";
            relVal += params[1]['marker']+params[1]['seriesName']+ ' : ' + String(params[1]['value']);
	        relVal+="<br/>";
             return relVal;
        }
    },
    legend: {
      data:['复购率','消费用户量','新增用户量'],
      textStyle:{color:"#efefef",fontSize:"12"},
      right:0,
      orient:'vertical',
    },
    grid: {
      top: '25%',
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
        name:'复购率',
        cursor: 'default',
        type: 'line',
        yAxisIndex: 1,
        lineStyle: {
          color:'#ffc203'
        },
        itemStyle: {
          normal:{
            color:'#ffc203',
            label: {
              show: true,
              position: 'top',
              formatter: '{c}% ',
            },
          }

        },

      },
      {
        name:'新增用户量',
        cursor: 'default',
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
              show: true,
              textStyle: {
                color: "#fff"
              },
              position: "insideTop",

            }
          }
        },

      },
	  {
        name:'消费用户量',
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
              show: true,
              textStyle: {
                color: "#30d7c7"
              },
              position: "top",

            }
          }
        },

      },
    ]
  };
  	var data = getSevenTime(7);
  	var data1 = [];
    var data2 = [];
    var data3 = [];
    $.each(eval(turnoverCustomer['lst_data']), function (idx, val) {
    	var rate = "";
    	var str = "";
    	if(parseInt(val['pay_cus_count'])==0){
    		rate = 0;
    	}else{
    		str = (((parseInt(val['pay_cus_count'])-parseInt(val['new_cus_count']))/parseInt(val['pay_cus_count'])*100)+'');
    		rate = str.substring(0,str.lastIndexOf(".")+2);
    	}
    	//data.push(val['week_date']);
    	data1.push(val['new_cus_count']);
		data2.push(val['pay_cus_count']);
		data3.push(rate);
    });
	turnoverCustomerOrderOption.xAxis.data = data;
	turnoverCustomerOrderOption.series[0].data = data3.reverse();
	turnoverCustomerOrderOption.series[1].data = data1.reverse();
	turnoverCustomerOrderOption.series[2].data = data2.reverse();
	//customerNewChartOption.title.text="近7天客流趋势";
	turnoverCustomerOrderOption.yAxis[1].min = 0;
	if(Math.max.apply(null, data3)<10||data3.length==0){
		turnoverCustomerOrderOption.yAxis[1].max = 10;
	}else{
		turnoverCustomerOrderOption.yAxis[1].max = parseInt(Math.max.apply(null, data3)/0.7);
	}
  	turnoverCustomerOrderChart.setOption(turnoverCustomerOrderOption,true);
}
// 获取城市用户量分布数据
var getCityUser = function (pageStatusInfo) {
    var cacheKey = String(pageStatusInfo.getCacheKey()).split("_")[0];
    // 从缓存获取数据
    //console.log('show cityUser rank graph base on js cache.')
    showCityUser(indexData[cacheKey].city_user_gmv);
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
        timerId = setInterval(chonfu,2000);
}
 function chonfu(){
  					var totalprice = parseInt(RandomNumByTime(5))*0.18+parseInt(changenum())+'';
  					if(totalprice.indexOf(".")>0){
  						totalprice = totalprice.substring(0,totalprice.lastIndexOf("."));
  					}
  					if(totalprice.length>8){
  						dojob(totalprice.substring(0,totalprice.length)); 
  					}else{
  						totalprice = "000000000"+totalprice;
  						dojob(totalprice.substring(totalprice.length-9,totalprice.length)); 
  					}
  					var tradesumofcustomerMonth = $("#tradesumofmonthCustmomerHid").text();//本月用户量
  					var tradesumoflastmonthCustmomer = $("#tradesumoflastmonthCustmomerHid").text();//上月今天用户量
  					var tradesumofcustomerHistory = $("#tradesumofhistoryCustmomerHid").text();//历史用户量
  					var tradesumofmonthOrder = $("#tradesumofmonthOrderHid").text();//本月订单量
  					var tradesumofhistoryOrder = $("#tradesumofhistoryOrderHid").text();//历史订单量
  					$("#tradesumofmonthCustmomer").html(parseInt(tradesumofcustomerMonth));
  					$("#tradesumofhistoryCustmomer").html(parseInt(tradesumofcustomerHistory));
  					$("#tradesumofmonthOrder").html(parseInt(tradesumofmonthOrder));
  					$("#tradesumofhistoryOrder").html(parseInt(tradesumofhistoryOrder));
  					$("#customer_month_ratio").html(25+'%');
  					$("#order_count_ratio").html(33+'%');
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
  	if(regex_zb.test(curr_user.usergroup.code)){
		targets = 0;
		// 城市ID
		    cityId = (decode64(getUrlParamByKey("c")) == 'null'||decode64(getUrlParamByKey("c")) == null) ? '' : decode64(getUrlParamByKey("c"));
		    // 省份ID
		    provinceId = (decode64(getUrlParamByKey("p"))=='null'||decode64(getUrlParamByKey("p"))==null) ? '' : decode64(getUrlParamByKey("p"));
		    // 城市名称
		    cityName = (decode64(getUrlParamByKey("cn")) == 'null'||decode64(getUrlParamByKey("cn")) == null) ? '' : decode64(getUrlParamByKey("cn"));
		    $("#currentCity").empty();
		    var gengduo = $('<span class="pull-right" style="font-size: 12px;cursor:pointer;color: #747474;display: none;" id="net_more">更多</span>');
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
  var curr_user;
  function initcurruser(){
  		//取得当前登录人的门店
		doManager("UserManager", "getCurrentUserDTO",null,
    				function(data, textStatus, XMLHttpRequest) {
    					if (data.result) {
    						var employeeID="";
    						 curr_user = JSON.parse(data.data);
    					}
    			},false);
  	}
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
        var store_id = "9900287";
        var storeNo = storelist[i].storeno==null?"":storelist[i].storeno;
        var cityId = storelist[i].cityId;
        var cityName = "天津";
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
		  var URL="current_shopkeeper_demo.html?"+"s="+encode64(storeId)+"&c="+encode64(cityId)+"&cn="+encode64(cityName)+"&p=&ln="+encode64(curr_user.name)+"&f="+flag+"&fs="+encode64(pageStatusInfo.targets);
		  window.open(URL,"_self"); 
	  }
	  
	  //跳转到emoloyeeInfo.html
	  function goEmployee(t){
		  var employeeNo =   $(t).attr("id").split("_")[1];
		  
		  var URL="../crm/employeeInfo.html?"+"employee_no="+encode64(employeeNo)+"&flag=0"+"&#tab_1";
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
        infoStr = infoStr+"<tr style='cursor:pointer'  id='tr_"+employee_no+"_"+uid+"' /*onclick='goStoreKeeper(this)'*/><td>"+parseInt(i+1)+"</td><td>"+employee_no+"</td><td>"+employee_name+"</td><td>"+employee_citySelect+"</td><td>"+employee_storename+"</td><td>"+employee_phone+"</td></tr>";
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
	  var URL="current_shopkeeper_demo.html?"+"s="+encode64(storeId)+"&c="+encode64(cityId)+"&cn="+encode64(cityName)+"&p=&ln="+encode64(curr_user.name)+"&f="+flag+"&fs="+encode64(pageStatusInfo.targets);
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
    var URL="../crm/storekeeper_user.html?"+"userId="+encode64(id)+"&employeeNo="+encode64(employee);
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
  
  //用户分析
  function goto_user_k(){
	  var role = curr_user.usergroup.code;
	  var url = "";
	  var target=pageStatusInfo.targets;
	  if(target==0){
	  	url = "index_K_user.html?t="+encode64('0')+"&s=&sn=&c=&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&cn=";
	  }else if(target==1){
	  	url = "index_K_user.html?t="+encode64(1)+"&s=&sn=&c=cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&#ff";
	  }
	  window.open(url,"index_K_user"); 
  }
  
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
//线下网络运营
  function goToCityNet(){
  	  var role = curr_user.usergroup.code;
  	  var url = "";
  	  var target=pageStatusInfo.targets;
  	  if(target==0){
  	  	url = "index_city_net.html";
  	  }
  	  window.open(url,"index_city_net"); 
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
	  window.open(url,"dynamicData_storetrade_analysis2");
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
	      window.open(url,"dynamicData_area_GMV");
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
	  window.open(url,"dynamicData_newaddcus_analysis");
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
	  window.open(url,"dynamicData_newAddCustomer_GMV");
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
      window.open(url,"dynamicData_employee_sendOrders");
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
	  window.open(url,"dynamicData_rewardtimes_analysis");
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
		  $("#user_model").hide();
	  }
  }
  //用户画像（新）
  function goTosjfxIndex(){
	  var url = "sjfx-index_demo.html";
	  window.open(url,"sjfx-index");
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
	  window.open(url,"abnormal_order"); 
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
  
  //用户档案
  // function goToUserProfileStat(){
	//   var role = curr_user.usergroup.code;
	//   var url = "";
	//   var target=pageStatusInfo.targets;
	//   if(target==0){
	//   	url = "userProfile_view.html?t="+encode64('0')+"&s=&sn=&c=&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&cn=";
	//   }else if(target==1){
	//   	url = "userProfile_view.html?t="+encode64(1)+"&s=&sn=&c=cn="+encode64(pageStatusInfo.cityName)+"&e="+encode64(curr_user.id)+"&r="+encode64(role)+"&#ff";
	//   }
	//   window.open(url,"userProfile_view");
  //  }
  
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
	  window.open(url,"dynamicData_areaInfo");
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
	  var url = "data_index_demo.html?t=1&nav=heat&type=yye&s=&c=&cn=&e=";
	  window.open(url,"data_index");  
  }
  //数据地图-热力图-订单热力图
  function gotoorderheat(){
	  var url = "data_index_demo.html?t=1&nav=heat&type=ddl&s=&c=&cn=&e=";
	  window.open(url,"data_index");  
  }
  //数据地图-热力图-用户热力图
  function gotocustomerheat(){
	  var url = "data_index_demo.html?t=1&nav=heat&type=yhl&s=&c=&cn=&e=";
	  window.open(url,"data_index");  
  }
  
  
  //数据地图-热力图-营业额热力图
  function gotogmvheatxq(){
	  var url = "data_index_demo.html?t=1&nav=village_heat&type=yye&s=&c=&cn=&e=";
	  window.open(url,"data_index");  
  }
  //数据地图-热力图-订单热力图
  function gotoorderheatxq(){
	  var url = "data_index_demo.html?t=1&nav=village_heat&type=ddl&s=&c=&cn=&e=";
	  window.open(url,"data_index");  
  }
  //数据地图-热力图-用户热力图
  function gotocustomerheatxq(){
	  var url = "data_index_demo.html?t=1&nav=village_heat&type=yhl&s=&c=&cn=&e=";
	  window.open(url,"data_index");  
  }
  
  
  
  //数据地图-覆盖范围-门店覆盖范围
  function goToMapView(){
	  var role = curr_user.usergroup.code;
	  var url = "data_index_demo.html?t=1&nav=store&s=&c=&cn=&e=";
	  window.open(url,"data_index"); 
  }
  //数据地图-覆盖范围-片区分布图
  function goToTinyArea(){
	  var role = curr_user.usergroup.code;
	  var url = "data_index_demo.html?t=1&nav=tiny&s=&c=&cn=&e=";
	  window.open(url,"data_index"); 
  }
  //数据地图-动态图-国安侠分布
  function gotoemployeefb(){
	  var url = "data_index_demo.html?t=1&nav=emp&s=&c=&cn=&e=";
	  window.open(url,"data_index");  
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
	  		url = "headquarters_rank.html?"+"p="+provinceId+"&c="+cityId+"&tps="+type+"&fs="+targets+"&cn="+cityName;
	  	}else if(str==2){
	  		type=encode64("store_gmv");
	  		url = "headquarters_rank.html?"+"p="+provinceId+"&c="+cityId+"&tps="+type+"&fs="+targets+"&cn="+cityName;
	  	}else if(str==4){
	  		type=encode64("guoanxia_gmv");
	  		url = "headquarters_rank.html?"+"p="+provinceId+"&c="+cityId+"&tps="+type+"&cn="+cityName+"&fs="+targets;
	  	}else if(str==5){
	  		type=encode64("commodity_gmv");
	        url = "headquarters_rank.html?p="+provinceId+"&pn="+provinceName+"&c="+cityId+"&cn="+cityName+"&tps="+type+"&fs="+targets;
	  	}else if(str==6){
	  		type=encode64("businessDep_gmv");
	  		url = "headquarters_rank.html?"+"p="+provinceId+"&c="+cityId+"&tps="+type+"&fs="+targets+"&cn="+cityName;
	  	}else if(str==7){
	  		type=encode64("channel_gmv");
	  		url = "headquarters_rank.html?"+"p="+provinceId+"&c="+cityId+"&tps="+type+"&fs="+targets+"&cn="+cityName;
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
	$("#city_gmv_more").on('click',function(){
    	var type=encode64("city_gmv");
    	var provinceId=encode64(pageStatusInfo.provinceId =="" ? '' : pageStatusInfo.provinceId);
    	var cityId=encode64(pageStatusInfo.cityId =="" ? '' : pageStatusInfo.cityId);
        var url = "headquarters_details.html?"+"p="+provinceId+"&c="+cityId+"&tps="+type+"&targets="+pageStatusInfo.targets;
        window.open(url);
    });
    $("#store_gmv_more").on('click',function(){
    	var type=encode64("store_gmv");
    	var provinceId=encode64(pageStatusInfo.provinceId =="" ? '' : pageStatusInfo.provinceId);
    	var cityId=encode64(pageStatusInfo.cityId =="" ? '' : pageStatusInfo.cityId);
        var url = "headquarters_details.html?"+"p="+provinceId+"&c="+cityId+"&tps="+type+"&targets="+pageStatusInfo.targets;
        window.open(url);
    });
    $("#store_order_more").on('click',function(){
    	var type=encode64("store_order");
    	var provinceId=encode64(pageStatusInfo.provinceId =="" ? '' : pageStatusInfo.provinceId);
    	var cityId=encode64(pageStatusInfo.cityId =="" ? '' : pageStatusInfo.cityId);
        var url = "headquarters_details.html?"+"p="+provinceId+"&c="+cityId+"&tps="+type+"&targets="+pageStatusInfo.targets;
        window.open(url);
    });
    $("#guoanxia_gmv_more").on('click',function(){
    	var type=encode64("guoanxia_gmv");
    	var provinceId=encode64(pageStatusInfo.provinceId =="" ? '' : pageStatusInfo.provinceId);
    	var cityId=encode64(pageStatusInfo.cityId =="" ? '' : pageStatusInfo.cityId);
        var url = "headquarters_details.html?"+"p="+provinceId+"&c="+cityId+"&tps="+type+"&targets="+pageStatusInfo.targets;
        window.open(url);
    });
    $("#commodity_gmv_more").on('click',function(){
    	var type=encode64("commodity_gmv");
    	var provinceName=encode64((pageStatusInfo.provinceName ==""||typeof(pageStatusInfo.provinceName) == "undefined") ? '' : pageStatusInfo.provinceName);
    	var cityName=encode64((pageStatusInfo.cityName ==""||typeof(pageStatusInfo.cityName) == "undefined") ? '' : pageStatusInfo.cityName);
    	var provinceId=encode64(pageStatusInfo.provinceId =="" ? '' : pageStatusInfo.provinceId);
    	var cityId=encode64(pageStatusInfo.cityId =="" ? '' : pageStatusInfo.cityId);
        var url = "headquarters_details.html?p="+provinceId+"&pn="+provinceName+"&c="+cityId+"&cn="+cityName+"&tps="+type+"&targets="+pageStatusInfo.targets;
        window.open(url);
    });
    $("#businessDep_gmv_more").on('click',function(){
    	var type=encode64("businessDep_gmv");
    	var provinceId=encode64(pageStatusInfo.provinceId =="" ? '' : pageStatusInfo.provinceId);
    	var cityId=encode64(pageStatusInfo.cityId =="" ? '' : pageStatusInfo.cityId);
        var url = "headquarters_details.html?"+"p="+provinceId+"&c="+cityId+"&tps="+type+"&targets="+pageStatusInfo.targets;
        window.open(url);
    });
    $("#channel_gmv_more").on('click',function(){
    	var type=encode64("channel_gmv");
    	var provinceId=encode64(pageStatusInfo.provinceId =="" ? '' : pageStatusInfo.provinceId);
    	var cityId=encode64(pageStatusInfo.cityId =="" ? '' : pageStatusInfo.cityId);
        var url = "headquarters_details.html?"+"p="+provinceId+"&c="+cityId+"&tps="+type+"&targets="+pageStatusInfo.targets;
        window.open(url);
    });
    $("#channel_order_more").on('click',function(){
    	var type=encode64("channel_order");
    	var provinceId=encode64(pageStatusInfo.provinceId =="" ? '' : pageStatusInfo.provinceId);
    	var cityId=encode64(pageStatusInfo.cityId =="" ? '' : pageStatusInfo.cityId);
        var url = "headquarters_details.html?"+"p="+provinceId+"&c="+cityId+"&tps="+type+"&targets="+pageStatusInfo.targets;
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
	  window.location.href="index_headquarters_demo.html"+URL;
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
      window.open(url,"dynamicData_dept_GMV");
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
      window.open(url,"dynamicData_dept_consumer");
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
                    	var dd = $("<dd>").html(RandomNum(10,25));
                    	$("#info_head_dl").append(dt).append(dd);
                    });
                }
            });
}
function menuShowByRole(){
	  if(pageStatusInfo.targets==0){
		$("#city_net").show();
	  }else if(pageStatusInfo.targets==1){
		  $("#city_net").hide();
	  }
}
function  clearCache(){
	localStorage.clear();
	window.parent.location=getRootPath() + "/demo/index_headquarters_demo.html";
}
function RandomNum(Min, Max) {
      var Range = Max - Min;
      var Rand = Math.random();
      if(Math.round(Rand * Range)==0){
        return Min + 1;
      }else if(Math.round(Rand * Max)==Max)
      {
        index++;
        return Max - 1;
      }else{
        var num = Min + Math.round(Rand * Range) - 1;
        return num;
      }
 }
 function RandomNumByTime(index_){
 	    var now = new Date();
	    return String(now.getTime()).substring(index_,String(now.getTime()).length);
 }
function randNum(n){
       return ( Math.floor ( Math.random ( ) * n + 1 ) );
}
 var initNo=85000;
function changenum(){
		var randNumval = randNum(50);
		initNo+=randNumval;
		return initNo;
   }