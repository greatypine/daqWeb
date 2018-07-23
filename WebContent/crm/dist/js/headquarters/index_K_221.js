Date.prototype.format = function() {  
    var s = '';  
    var mouth = (this.getMonth() + 1)>=10?(this.getMonth() + 1):('0'+(this.getMonth() + 1));  
    var day = this.getDate()>=10?this.getDate():('0'+this.getDate());  
    s += this.getFullYear() + '-'; // 获取年份。  
    s += mouth + "-"; // 获取月份。  
    s += day; // 获取日。  
    return (s); // 返回日期。  
};

  autodivheight();
  function autodivheight(){ //函数：获取尺寸
	//获取浏览器窗口高度
	var winHeight=0;
	if (window.innerHeight)
	  winHeight = window.innerHeight;
	else if ((document.body) && (document.body.clientHeight))
	  winHeight = document.body.clientHeight;
	//通过深入Document内部对body进行检测，获取浏览器窗口高度
	if (document.documentElement && document.documentElement.clientHeight)
	  winHeight = document.documentElement.clientHeight;
	//DIV高度为浏览器窗口的高度
// 	document.getElementById("main1").style.height= winHeight/2 - 60  +"px";
	document.getElementById("main4").style.height= winHeight/2 - 60  +"px";
	document.getElementById("main5").style.height= winHeight/2 - 60  +"px";
	//document.getElementById("main6").style.height= winHeight/2 - 60  +"px";
	document.getElementById("main7").style.height= winHeight/2 - 60  +"px";
	//document.getElementById("main2").style.height= winHeight/2 - 60  +"px";
	document.getElementById("main3").style.height= winHeight/2 - 60  +"px";
    //document.getElementById("data_module1").style.height= (winHeight/2 - 132)/2 +"px";
    //document.getElementById("data_module2").style.height= (winHeight/2 - 132)/2 +"px";
// 	document.getElementById("data_module3").style.height= (winHeight/2 - 45)/2  +"px";
// 	document.getElementById("data_module4").style.height= (winHeight/2 - 45)/2 - 15  +"px";
  }
  window.onresize=autodivheight; //浏览器窗口发生变化时同时变化DIV高度

 var storeNo = decode64(getUrlParamByKey("so"));//门店no
 var target = decode64(getUrlParamByKey("t"));//执行人级别 
 var storeName=decode64(getUrlParamByKey("sn"));//门店名称
 var cityname = decode64(getUrlParamByKey("cn"));//城市名称
 var screenlogin=getUrlParamByKey("su");
 //var ta = getUrlParamByKey("ta");
 var storeNo_ = (decode64(getUrlParamByKey("so")) == 'null'||decode64(getUrlParamByKey("so")) == null) ? '' : decode64(getUrlParamByKey("so"));//门店no
 var ordertype = (decode64(getUrlParamByKey("ordertype")) == 'null'||decode64(getUrlParamByKey("ordertype")) == null) ? '' : decode64(getUrlParamByKey("ordertype"));//商品服务团购 全部:0;商品1服务2团购3
 var storeName_ = (decode64(getUrlParamByKey("store_name")) == 'null'||decode64(getUrlParamByKey("store_name")) == null) ? '' : decode64(getUrlParamByKey("store_name"));//商品服务团购 全部:0;商品1服务2团购3
 function loginShow(){
		
		if(screenlogin!=null&&screenlogin!=''&&screenlogin!=undefined){
			var reObj = new PMSRequestObject("userManager", "isScreenUser", [ screenlogin ]);
		    var callback = function callback(data, textStatus, XMLHttpRequest) {
		    	//window.parent.location=getRootPath() + "/crm/index_K.html?pls=0";
		    	var stateObject = {};
		    	var newUrl = "/daqWeb/crm/index_K.html?ta=0";
		    	history.pushState(stateObject,null,newUrl);
			};
		    var failureCallback = function failureCallback(data, textStatus, XMLHttpRequest) {
				alert("登录信息错误，请确认输入或联系管理员!");
				return false;
			}
		    var url = "../login.do";
		    $$.ajax(url, "requestString=" + reObj.toJsonString(), callback, failureCallback); 
		}else{
			getUser();
		}
		
	}

//   var myChart1 = echarts.init(document.getElementById('main1'));
  //var myChart2 = echarts.init(document.getElementById('main2'));
  var myChart3 = echarts.init(document.getElementById('main3'));

  //K线图
  function randomData(data_value) {
      now = new Date(+now + 5000);
      return {
          name: now.toString(),
          value: [
              now,
              data_value
              //Math.round(value)
          ]
      }
  }
 
  var data = [];
  var now = new Date();
  now.setMinutes (now.getMinutes () - 7);//设置起始时间为当前时间的前2分钟
  
  var option1 = {
      backgroundColor: '#f55a44',

      tooltip: {
          trigger: 'axis',
          formatter: function (params) {
              params = params[0];
              var date = new Date(params.name);
              return date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds() + ' 成交额 ' + params.value[1];
          },
          axisPointer: {
              animation: false
          }
      },
      legend: {
          y: '10%',
          data: ['GMV', ]
      },
      grid: {
          left: '2%',
          right: '2%',
          bottom: '5%',
          top: '15%',
          containLabel: true
      },
      xAxis: {
          type: 'time',
          splitLine: {
              show: false
          },
          axisLine: {
              lineStyle: {color: '#fff'}
          },
      },
      yAxis: [{
          name: 'GMV',
          type: 'value',
          boundaryGap: [0, '100%'],
          min: function(value){
        	   	 return parseInt(value.min);
          	   },
          axisLine: {
              lineStyle: {color: '#fff'}
          },
          splitLine: {
              show: false
          },
          axisLabel: {
              color: '#fff'
          },
          axisTick: {
              show: false
          },
      },
      ],
      series: [{
          name: '营业额',
          type: 'line',
          showSymbol: false,
          hoverAnimation: false,
          data: data,
          lineStyle: {
              normal: {
                  color: '#fff'
              }
          },
          markPoint : {
			  data : [
				  {type : 'max', name: '最大值'},
			  ],
			  itemStyle:{normal: {color:'#b18b25'}},
			  emphasis: {
			      itemStyle:{normal: {color:'#000'}}
			  }
		  },
      }]
  };

  var lastData = 11;
  var axisData;
  var timeTicket;
//     clearInterval(timeTicket);
//   timeTicket = setInterval(loadtime, 5000);
  function loadtime (){
//       lastData += Math.random() * ((Math.round(Math.random() * 10) % 2) == 0 ? 1 : -1);
//       lastData = lastData.toFixed(1) - 0;
//       axisData = (new Date()).toLocaleTimeString().replace(/^\D*/,'');
      
//       doManager("chartStatManager","queryDayTurnover",[shareChartStatDto],
//   			function(data,textStatus,XmlHttpRequest){
//   				if (data.result) {
//   					var jsonData = $.fromJSON(data.data);
//   					real_value = jsonData.day_amount;
//   				}
//   		});
      
//       data.push(randomData(real_value));
//       data.splice(0,1);
//       myChart1.setOption({
//           series: [{
//               data: data
//           }]
//       });
  }
  
  //营业额分布
//   var online = 0;
//   var offline = 0;
  var option2 = {
	backgroundColor:'#5e5e5e',
	title : {
	  text: '营业额分布（2018年）',
	  textStyle: {
		color: '#fff'
	  },
	  x:'center'
	},
	tooltip: {
	  trigger: 'item',
// 	  formatter: "{b}: {c} ({d}%)",
	  formatter:function(a){
		  return a.data.name+": "+changeMoney(parseInt(a.data.value))+" ("+a.percent+"%)";
	  },
	  position: 'bottom'
	},
	legend: {
	  bottom: 10,
	  left: 'center',
	  textStyle: {
		color: '#fff'
	  },
	  data:['线上营业额','到店营业额']
	},
	series: [
	  {
		type:'pie',
		radius: ['50%', '70%'],
		avoidLabelOverlap: false,
		label: {
		  normal: {
			show: false,
			position: 'center'
		  },
		  emphasis: {
			show: true,
			textStyle: {
			  fontSize: '20',
			  fontWeight: 'bold'
			}
		  }
		},
		labelLine: {
		  normal: {
			show: false
		  }
		},
		data:[
		  {
			value:0,
			name:'线上营业额',
			itemStyle: {
			  normal: {
				color:'#f55a44'
			  },
			},

		  },
		  {
			value:0,
			name:'到店营业额',
			itemStyle: {
			  normal: {
				color:'#fff'
			  },
			},

		  }
		]
	  }
	]
  };

  //门店GMV散点图
  var dataBJ = [];

  var schema = [
    {name: 'order', index: 0, text: '订单量'},
    {name: 'gmv', index: 1, text: 'GMV'},
    {name: 'city', index: 2, text: '城市'},
    {name: 'store', index: 3, text: '门店'},
    {name: 'month', index: 3, text: '月份'}
  ];


  var itemStyle = {
    normal: {
      opacity: 0.8,
      shadowBlur: 10,
      shadowOffsetX: 0,
      shadowOffsetY: 0,
      shadowColor: 'rgba(0, 0, 0, 0.5)'
    }
  };

  option3 = {
    backgroundColor: '#e1e1e1',
    title: {
      text: "GMV门店分布",
      left: "10",
      y: "10",
      textStyle: {
        color: "#666"
      }
    },
    color: [
      '#dd4444', '#fec42c', '#80F1BE'
    ],
    legend: {
      y: 'top',
      data: ['门店GMV', '上海', '广州'],
      textStyle: {
        color: '#666',
        fontSize: 16
      }
    },
    grid: {
      //x: '10%',
      //x2: 80,
      y: '28%',
      y2: '20%',
      bottom:'10%',
      left:'5%'
    },
    tooltip: {
      padding: 10,
      backgroundColor: '#222',
      borderColor: '#777',
      borderWidth: 1,
      formatter: function (obj) {
        var value = obj.value;
        return '<div style="border-bottom: 1px solid rgba(255,255,255,.3); font-size: 18px;padding-bottom: 7px;margin-bottom: 7px">'
          + schema[1].text + '：' + value[1] + '万元</div>'
          + schema[0].text + '：' + value[0] + '<br>'
          + schema[2].text + '：' + value[2] + '<br>'
          + schema[3].text + '：' + value[3] + '<br>'
          + schema[4].text + '：' + value[4] + '<br>';
      }
    },
    xAxis: {
      type: 'value',
      name: '订单量',
      nameGap: 16,
      nameTextStyle: {
        color: '#666',
        fontSize: 14
      },
      min: function(value){
  	   	 return parseInt(value.min);
  	   },
      splitLine: {
        show: false
      },
      axisLine: {
        lineStyle: {
          color: '#666'
        }
      }
    },
    yAxis: {
      type: 'value',
      name: '门店GMV（万元）',
      nameLocation: 'end',
      nameGap: 20,
      min: function(value){
 	   	 return parseInt(value.min);
   	   },
      nameTextStyle: {
        color: '#666',
        fontSize: 16
      },
      axisLine: {
        lineStyle: {
          color: '#666'
        }
      },
      splitLine: {
        show: false
      }
    },
    visualMap: [
      {
        left: 'right',
        top: '10%',
        dimension: 1,
        min: 0,
        max: 500,
        itemWidth: 30,
        itemHeight: 100,
        calculable: true,
        precision: 0.1,
        text: ['圆形大小：门店GMV'],
        textGap: 30,
        textStyle: {
          color: '#666'
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
            color: ['#666']
          }
        }
      },
      {
        left: 'right',
        bottom: '5%',
        dimension: 0,
        min: 0,
        max: 5000,
        itemHeight: 100,
        calculable: true,
        precision: 0.1,
        text: ['明暗：订单量'],
        textGap: 30,
        textStyle: {
          color: '#666'
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
    ],
    series: [
      {
        name: '门店GMV',
        type: 'scatter',
        itemStyle: itemStyle,
        cursor:'default',
        data: dataBJ,
      },
    ]
  };
/*
  myChart3.on('click', function (params) {
	  	var dataArr = [];
	  	dataArr = params.data;
	  	var dataParam = getShareParam();
	  	var storename = dataArr[3];
	  	var deptname = dataParam.deptname==null?"":dataParam.deptname;
	  	var cityname = dataArr[2];
	  	var dayTime = dataArr[4];
	  	var storeno = dataArr[5];
	  	var cityno = dataArr[6];
	  	var redirectTag = "scatterplot";
	  	var channelname = dataParam.channelname==null?"":dataParam.channelname;
	    if(target==0){
			url = "searchData_view.html?t="+encode64('0')+"&s=&c=&e=&r=&dn="+encode64(deptname)+"&channel="+encode64(channelname)+"&cn="+encode64(cityname)+"&so="+encode64(storeno)+"&sn="+encode64(storename)+"&time="+encode64(dayTime)+"&co="+encode64(cityno)+"&rt="+encode64(redirectTag);
		}else if(target==1){
			url = "searchData_view.html?t="+encode64(1)+"&e=&dn="+encode64(deptname)+"&channel="+encode64(channelname)+"&cn="+encode64(cityname)+"&so="+encode64(storeno)+"&sn="+encode64(storename)+"&time="+encode64(dayTime)+"&co="+encode64(cityno)+"&rt="+encode64(redirectTag);
		}
		window.open(url,"searchData_view");
	});
*/  
//   myChart1.setOption(option1,true);
  //myChart2.setOption(option2,true);
  myChart3.setOption(option3,true);

  var chart4;
  var chart4_option;
  function chartresize1(){
	var temp1 = new initchart1();
	temp1.resize();
  }
  var initchart1 = function(){
	chart4 = echarts.init(document.getElementById('main4'));

    //K线图--分时
    var xData = function() {
      var data = [];
      for (var i = 0; i < 24; i++) {
        data.push(i + "时");
      }
      return data;
    }();

    chart4_option = {
      backgroundColor: "#f55a44",
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          textStyle: {
            color: '#fff'
          }

        },
      },
      grid: {
        borderWidth: '0',
        left: '5%',
        right: '5%',
        bottom: '30%',
        top: '10%',

      },
      calculable: true,
      xAxis: [{
        type: 'category',
        boundaryGap: false,
        axisLine: {
          lineStyle: {
            color: '#333'
          }
        },
        splitLine: {
          show: false
        },
        axisTick: {
          show: true
        },
        splitArea: {
          show: false
        },
        axisLabel: {
          interval: 0,

        },
        data: xData,
      }],
      yAxis: [{
        type: 'value',
        splitLine: {
          show: false
        },
        axisLine: {
          lineStyle: {
            color: '#333'
          }
        },
        axisTick: {
          show: false
        },
        axisLabel: {
          interval: 1,

        },
        splitArea: {
          show: false
        },

      }],
      dataZoom: [{
        show: true,
        height: '30',
        xAxisIndex: [0],
        bottom: '30',
        start: '0',
        end: '100',
        handleIcon: 'path://M306.1,413c0,2.2-1.8,4-4,4h-59.8c-2.2,0-4-1.8-4-4V200.8c0-2.2,1.8-4,4-4h59.8c2.2,0,4,1.8,4,4V413z',
        handleSize: '110%',
        handleStyle:{
          color:"#d3dee5",
        },
        textStyle:{color:'#fff'},
        borderColor:"#90979c",
        //backgroundColor:'#fff'
      }],
      series: [{
        name: "总数",
        type: "line",
        stack: "总量",
		smooth: true,
		showAllSymbol: true,
		symbol: 'emptyCircle',
		symbolSize: 15,
        itemStyle: {
          normal: {
            color: '#fff',
            barBorderRadius: 0,
            label: {
              show: true,
              position: 'top',
              formatter: function(p) {
                return p.value > 0 ? (p.value) : '';
              }
            }
          }
        },
        data:hourData
      },
      ]
    }


   chart4.setOption(chart4_option);
	$(function(){
	  window.onresize = function(){
		chart4.resize();
		/*chart5.resize();
		chart6.resize();*/
	  }
	})
	this.resize = function size(){
	  var chart4div=document.getElementById('main4');
	  var tabcontent = document.getElementById('tab-content');
	  width=tabcontent.offsetWidth;
	  chart4div.style.width=width+'px';
	  chart4.resize(width);
	}
  }
     var xData = [];
   var yData = [];
   var yData_jy = [];
   var chart5;
   var chart5_option;
  function chartresize2(){
    //console.log("chartresize");
    var temp2 = new initchart2();
    temp2.resize();
  }
  var initchart2 = function(){
  	chart5 = echarts.init(document.getElementById('main5'));

    //K线图--日
    var xData = function() {
      var data = [];
      //var fistDay = getFirstDayOfYear();
      var fistDay = "2018-07-01";
      var curDay = now.getFullYear() + "-" + (now.getMonth() + 1) + "-" + now.getDate();
      data = getAllDate(fistDay,curDay);
      return data;
    }();
  	
    chart5_option = {
      backgroundColor: "#f55a44",
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          textStyle: {
            color: '#fff'
          }

        },
      },
      grid: {
        borderWidth: '0',
        left: '5%',
        right: '5%',
        bottom: '30%',
        top: '10%',

      },
      legend: {
				x: '78%',
				top: '1%',
				data: ['日总数', '经营指标']
	  },
      calculable: true,
      xAxis: [{
        type: 'category',
        boundaryGap: false,
        axisLine: {
          lineStyle: {
            color: '#333'
          }
        },
        splitLine: {
          show: false
        },
        axisTick: {
          show: true
        },
        splitArea: {
          show: false
        },
//         axisLabel: {
//           interval: 0,
//         },
        data: xData,
      }],
      yAxis: [{
        type: 'value',
        splitLine: {
          show: false
        },
        axisLine: {
          lineStyle: {
            color: '#333'
          }
        },
        axisTick: {
          show: false
        },
        axisLabel: {
          interval: 0,

        },
        splitArea: {
          show: false
        },

      }],
      dataZoom: [{
        show: true,
        height: '30',
        xAxisIndex: [0],
        bottom: '30',
        start: '0',
        end: '100',
        handleIcon: 'path://M306.1,413c0,2.2-1.8,4-4,4h-59.8c-2.2,0-4-1.8-4-4V200.8c0-2.2,1.8-4,4-4h59.8c2.2,0,4,1.8,4,4V413z',
        handleSize: '110%',
        handleStyle:{
          color:"#d3dee5",
        },
        textStyle:{color:'#fff'},
        borderColor:"#90979c",
        //backgroundColor:'#fff'
      }],
      series: [{
        name: "日总数",
        type: "line",
        stack: "总量",
        symbolSize:10,
        symbol:'circle',
        itemStyle: {
          normal: {
            color: '#fff',
            barBorderRadius: 0,
            label: {
              show: true,
              position: 'top',
              formatter: function(p) {
                return p.value > 0 ? (p.value) : '';
              }
            }
          }
        },
        data:yData
      },{
		name: "经营指标",
		type: "line",
		symbolSize:10,
		symbol:'circle',
		itemStyle: {
			normal: {
              color: '#000',
              barBorderRadius: 0,
              lineStyle:{
                width:2,
                type:'dotted'  //'dotted'虚线 'solid'实线
              }
            }
		},
		data:yData_jy
		}
      ]
    }

    chart5.setOption(chart5_option);
    $(function(){
      window.onresize = function(){
        chart5.resize();
      }
    })
    this.resize = function size(){
      var chart5div=document.getElementById('main5');
      var tabcontent = document.getElementById('tab-content');
      width=tabcontent.offsetWidth;
      //height=tabcontent.offsetHeight;
      chart5div.style.width=width+'px';
      chart5.resize(width);
    }
    
  	chart5.on('click', function (params) {
	  	var dayTime = "";
	  	dayTime = params.name;
	  	var dataParam = getShareParam();
	  	var storename = $("#store_name_manual").val()==null?"":$("#store_name_manual").val();
	  	var deptname = dataParam.deptname==null?"":dataParam.deptname;
	  	var cityname = dataParam.cityname==null?"":dataParam.cityname;
	  	var storeno = dataParam.storeno==null?"":dataParam.storeno;
	  	var channelname = dataParam.channelname==null?"":dataParam.channelname;
	  	var redirectTag = "day";
	    if(target==0){
			url = "searchData_view.html?t="+encode64('0')+"&s=&c=&e=&r=&dn="+encode64(deptname)+"&channel="+encode64(channelname)+"&cn="+encode64(cityname)+"&so="+encode64(storeno)+"&sn="+encode64(storename)+"&time="+encode64(dayTime)+"&rt="+encode64(redirectTag);
		}else if(target==1){
			url = "searchData_view.html?t="+encode64(1)+"&e=&dn="+encode64(deptname)+"&channel="+encode64(channelname)+"&cn="+encode64(cityname)+"&so="+encode64(storeno)+"&sn="+encode64(storename)+"&time="+encode64(dayTime)+"&rt="+encode64(redirectTag);
		}
		window.open(url,"searchData_view");
	});
  }
	var chart7 ;
	var chart7_option;
	var xData_week=[];
	function chartresize4(){
		//console.log("chartresize");
		var temp4 = new initchart4();
		temp4.resize();
	}
	var initchart4 = function(){
		chart7 = echarts.init(document.getElementById('main7'));

// 		//K线图--周

		chart7_option = {
			backgroundColor: "#f55a44",
			tooltip: {
				trigger: 'axis',
				axisPointer: {
					textStyle: {
						color: '#fff'
					}

				},
			},
			grid: {
				borderWidth: '0',
				left: '5%',
				right: '5%',
				bottom: '30%',
				top: '10%',

			},
			calculable: true,
			xAxis: [{
				type: 'category',
				boundaryGap: false,
				axisLine: {
					lineStyle: {
						color: '#333'
					}
				},
				splitLine: {
					show: false
				},
				axisTick: {
					show: true
				},
				splitArea: {
					show: false
				},
				axisLabel: {
					interval: 0,

				},
				data: xData_week,
			}],
			yAxis: [{
				type: 'value',
				splitLine: {
					show: false
				},
				axisLine: {
					lineStyle: {
						color: '#333'
					}
				},
				axisTick: {
					show: false
				},
				axisLabel: {
					interval: 0,

				},
				splitArea: {
					show: false
				},
				
			}],
			dataZoom: [{
			 show: true,
			 height: '30',
			 xAxisIndex: [0],
			 bottom: '30',
			 start: '10',
			 end: '100',
			 handleIcon: 'path://M306.1,413c0,2.2-1.8,4-4,4h-59.8c-2.2,0-4-1.8-4-4V200.8c0-2.2,1.8-4,4-4h59.8c2.2,0,4,1.8,4,4V413z',
			 handleSize: '110%',
			 handleStyle:{
			 color:"#d3dee5",
			 },
			 textStyle:{color:'#fff'},
			 borderColor:"#90979c",
			 //backgroundColor:'#fff'
			 }],
			series: [{
				name: "总数",
				type: "line",
				stack: "总量",
				symbolSize:10,
				symbol:'circle',
				itemStyle: {
					normal: {
						color: '#fff',
						barBorderRadius: 0,
						label: {
							show: true,
							position: 'top',
							formatter: function(p) {
								return p.value > 0 ? (p.value) : '';
							}
						}
					}
				},
				data: weekData
			},
			]
		}

		chart7.setOption(chart7_option);
		$(function(){
			window.onresize = function(){
				chart7.resize();
			}
		})
		this.resize = function size(){
			var chart7div=document.getElementById('main7');
			var tabcontent = document.getElementById('tab-content');
			width=tabcontent.offsetWidth;
			//height=tabcontent.offsetHeight;
			chart7div.style.width=width+'px';
			chart7.resize(width);
		}
	}
	var yData_month = [];
	var yData_month_target = [];
	var chart6;
	var chart6_option;
	function chartresize3(){
		//console.log("chartresize");
		var temp3 = new initchart3();
		temp3.resize();
	}
	var initchart3 = function(){
		chart6 = echarts.init(document.getElementById('main6'));

		//K线图--月
		var xData_month = function() {
			var data = [];
			var curMonth = now.getFullYear()+'-12-31';
			data = getYearAndMonth('2017-12-1',curMonth);
			return data;
		}();

		chart6_option = {
			backgroundColor: "#f55a44",
			tooltip: {
				trigger: 'axis',
				axisPointer: {
					textStyle: {
						color: '#fff'
					}

				},
			},
			grid: {
				borderWidth: '0',
				left: '6%',
				right: '5%',
				bottom: '30%',
				top: '10%',

			},
			legend: {
				x: '78%',
				top: '1%',
				data: ['月总数', '经营指标']
			},


			calculable: true,
			xAxis: [{
				type: 'category',
				boundaryGap: false,
				axisLine: {
					lineStyle: {
						color: '#333'
					}
				},
				splitLine: {
					show: false
				},
				axisTick: {
					show: true
				},
				splitArea: {
					show: false
				},
				axisLabel: {
					interval: 0,

				},
				data: xData_month,
			}],
			yAxis: [{
				type: 'value',
				splitLine: {
					show: false
				},
				axisLine: {
					lineStyle: {
						color: '#333'
					}
				},
				axisTick: {
					show: false
				},
				axisLabel: {
					interval: 0,

				},
				splitArea: {
					show: false
				},

			}],
			dataZoom: [{
				show: true,
				height: '30',
				xAxisIndex: [0],
				bottom: '30',
				start: '0',
				end: '45',
				handleIcon: 'path://M306.1,413c0,2.2-1.8,4-4,4h-59.8c-2.2,0-4-1.8-4-4V200.8c0-2.2,1.8-4,4-4h59.8c2.2,0,4,1.8,4,4V413z',
				handleSize: '110%',
				handleStyle:{
					color:"#d3dee5",
				},
				textStyle:{color:'#fff'},
				borderColor:"#90979c",
				//backgroundColor:'#fff'
			}],
			series: [{
				name: "月总数",
				type: "line",
				symbolSize:10,
				symbol:'circle',
				itemStyle: {
					normal: {
						color: '#fff',
						barBorderRadius: 0,
						label: {
							show: true,
							position: 'top',
							formatter: function(p) {
								return p.value > 0 ? (p.value) : '';
							}
						}
					}
				},
				data:yData_month
			},
			{
				name: "经营指标",
				type: "line",
				symbolSize:10,
				symbol:'circle',
				itemStyle: {
					normal: {
	                  color: '#000',
	                  barBorderRadius: 0,
	                  lineStyle:{
	                    width:2,
	                    type:'dotted'  //'dotted'虚线 'solid'实线
	                  }
	                }
				},
				data:yData_month_target
			},
			]
		}


		chart6.setOption(chart6_option);
		$(function(){
			window.onresize = function(){
				chart6.resize();
			}
		})
		this.resize = function size(){
			var chart6div=document.getElementById('main6');
			var tabcontent = document.getElementById('tab-content');
			width=tabcontent.offsetWidth;
			//height=tabcontent.offsetHeight;
			chart6div.style.width=width+'px';
			chart6.resize(width);
		}
	}
	
	var xData_week_temp = function() {
		var dataTemp = [];
		doManager("chartStatManager","getDateByWeek",null,
	   			function(data,textStatus,XmlHttpRequest){
	   				if (data.result) {
	   					var jsonData = $.fromJSON(data.data);
	   					dataTemp = jsonData;
	   				}
	   		},false);	
		return dataTemp;
	};
  
  var shareChartStatDto=null; //全局参数变量	
  var shareChartStatDto1=null;
  var shareChartStatDto2=null;
  var shareChartStatDto3=null;
  var storeCount = "";
  $(function(){
  	  queryTwoTwoOneStoreCount();
  	  //初始化门店下拉框
  	  initStoreSelectBy221();
	  loginShow();
	  if(target==0){//总部
	  	initallcity(); 
	  	initallstore();
	  	initallstorecontrast();
	  }else if(target==1){//城市
	  	initcurcity();
	  	$("#citySelect option[value='"+cityname+"']").attr('selected',true);
	  	$("#citySelectContrast option[value='"+cityname+"']").attr('selected',true);
	  	initStoreSelectcomplate();
	  	initStoreSelectcomplateContrast();
	  }else if(target==2) {//门店
	  	initcurcity();
	  	$("#citySelect").prop("disabled", true);
		$("#citySelect option[value='"+cityname+"']").attr('selected',true);
		$("#citySelectContrast").prop("disabled", true);
		$("#citySelectContrast option[value='"+cityname+"']").attr('selected',true);
	  	initStoreSelectcomplate();
	  	initStoreSelectcomplateContrast();
	  	$("#store_id_manual").val(storeNo);
	  	$("#store_name_manual").val(storeName);
		$("#store_name_manual").attr("readonly",true);		
		$("#store_id_manual_contrast").val(storeNo);
	  	$("#store_name_manual_contrast").val(storeName);
		$("#store_name_manual_contrast").attr("readonly",true);
	  }else{
		  initallcity(); 
		  initallstore();
		  initallstorecontrast();
	  }
	  
	    var autoComplete = new AutoComplete("store_name_manual","auto",array);
	  	document.getElementById("store_name_manual").onkeyup = function(event){
	  		autoComplete.start(event);
	  		if($("#store_name_manual").val().trim() == ""){
				$("#store_id_manual").val("");
			}
	  		for(var key in storeJson){
	  			if(document.getElementById("store_name_manual").value==key){
	  				document.getElementById("store_id_manual").value=storeJson[key];
	  			}
	  		}
	  	}
	  	$("#auto").on("click",function(event){
			for(var key in storeJson){
				if(document.getElementById("store_name_manual").value==key){
					document.getElementById("store_id_manual").value=storeJson[key];
				}
			}
		});
	  	
	  	//悬浮框事业群频道城市门店联动
	  	var autoCompleteContrast = new AutoComplete("store_name_manual_contrast","auto_contrast",array_contrast);
	  	document.getElementById("store_name_manual_contrast").onkeyup = function(event){
	  		autoCompleteContrast.start(event);
	  		if($("#store_name_manual_contrast").val().trim() == ""){
				$("#store_id_manual_contrast").val("");
			}
	  		for(var key in storeJsonContrast){
	  			if(document.getElementById("store_name_manual_contrast").value==key){
	  				document.getElementById("store_id_manual_contrast").value=storeJson[key];
	  			}
	  		}
	  	}
	  	$("#auto_contrast").on("click",function(event){
			for(var key in storeJsonContrast){
				if(document.getElementById("store_name_manual_contrast").value==key){
					document.getElementById("store_id_manual_contrast").value=storeJson[key];
				}
			}
		});
	  	
	  	
	  xData_week = xData_week_temp();
	  initRealData();
	  chartresize1();
	  chartresize2();
	  chartresize3();
	  chartresize4();
	  search_manual_k();
	  
	  
	  timeTicket=setInterval(loadtime, 5000);
	  
	  $("#search-btn2").click(function(){
		 excuteChart();
	 }); 
	 
	 $("#citySelect").change(function(){
		 $('#cbLabel').children("div").addClass('checked'); 
		 iCheckStr = $('#cbClick').val();
		 excuteChart();
	 });
	 //商品iCheck
	 $('#cbClick').on('ifChecked', function(event){ //ifChecked 事件应该在插件初始化之前绑定 
		 iCheckStr = $(this).val();
		 excuteChart();
	 });
	 $('#cbClick').on('ifUnchecked', function(event){
		 iCheckStr = "";
		 excuteChart();
	 });
	 $('#cbClick').iCheck({
	  	checkboxClass: 'icheckbox_minimal-red'
	 });
	 //服务iCheck
	 $('#maxbClick').on('ifChecked', function(event){ //ifChecked 事件应该在插件初始化之前绑定 
		 iCheckMaxStr = $(this).val();
		 excuteChart();
	 });
	 $('#maxbClick').on('ifUnchecked', function(event){
		 iCheckMaxStr = "";
		 excuteChart();
	 });
	 $('#maxbClick').iCheck({
	  	checkboxClass: 'icheckbox_minimal-red'
	 });
	 //团购iCheck
	 $('#thirdbClick').on('ifChecked', function(event){ //ifChecked 事件应该在插件初始化之前绑定 
		 iCheckMaxStr = $(this).val();
		 excuteChart();
	 });
	 $('#thirdbClick').on('ifUnchecked', function(event){
		 iCheckMaxStr = "";
		 excuteChart();
	 });
	 $('#thirdbClick').iCheck({
	  	checkboxClass: 'icheckbox_minimal-red'
	 });
	  //＋对比
	  $("#search-btn-add").click(function(){
		  var labelName1 = $("#label_manual_first").html();
		  var labelName2 = $("#label_manual_second").html();
		  var labelName3 = $("#label_manual_third").html();
		  var labelName4 = $("#label_manual_fourth").html();
		  var labelName5 = $("#label_manual_fifth").html();
		  var lable_first = document.getElementById("warning_text");
	      lable_first.style.display = "block";
	    
		  var shareChartStatDtoTemp = getShareParam();
		  var storename = $("#store_name_manual").val()==null?'':$("#store_name_manual").val();
	      var deptName = shareChartStatDtoTemp.deptname==null?'全部事业群':shareChartStatDtoTemp.deptname;
	      var channelName = shareChartStatDtoTemp.channelname==null?'全部频道':shareChartStatDtoTemp.channelname;
	      var cityName = shareChartStatDtoTemp.cityname==null?'全部城市':shareChartStatDtoTemp.cityname;
	      var storeNo = shareChartStatDtoTemp.storeno==null?'':shareChartStatDtoTemp.storeno;
	      var nameStrTemp = deptName+" "+channelName+" "+cityName+" "+storename;
	      if($.trim(nameStrTemp)==$.trim(nameStr1Html) || $.trim(nameStrTemp)==$.trim(nameStr2Html) || $.trim(nameStrTemp)==$.trim(nameStr3Html) 
	    		  || $.trim(nameStrTemp)==$.trim(nameStr4Html) || $.trim(nameStrTemp)==$.trim(nameStr5Html)){//对比条件重复过滤
	    	document.body.className = "skin-blue sidebar-mini"; 
    	  	return;
    	  }
	      if(labelName1.length==0){
		    	var lable_flag = document.getElementById("label_first_group");
		    	lable_flag.style.display = "block";
		    	var lable_reset = document.getElementById("label_first_reset");
				lable_reset.style.display = "block";
		    	shareChartStatDto1 = shareChartStatDtoTemp;
		    	nameStr1Html = nameStrTemp;
		    	$("#label_manual_first").html(nameStr1Html);
		    	nameStr1 = nameStr1Html+" "+storeNo;
		    }else if(labelName2.length==0){
		    	var lable_flag = document.getElementById("label_second_group");
		    	lable_flag.style.display = "block";
		    	var lable_reset = document.getElementById("label_second_reset");
				lable_reset.style.display = "block";
		    	shareChartStatDto2 = shareChartStatDtoTemp;
				nameStr2Html = nameStrTemp;
		    	$("#label_manual_second").html(nameStr2Html);
		    	nameStr2 = nameStr2Html+" "+storeNo;
		    }else if(labelName3.length==0){
		    	var lable_flag = document.getElementById("label_third_group");
		    	lable_flag.style.display = "block";
		    	var lable_reset = document.getElementById("label_third_reset");
				lable_reset.style.display = "block";
		    	shareChartStatDto3 = shareChartStatDtoTemp;
				nameStr3Html = nameStrTemp;
		    	$("#label_manual_third").html(nameStr3Html);
		    	nameStr3 = nameStr3Html+" "+storeNo;
		    }else if(labelName4.length==0){
		    	var lable_flag = document.getElementById("label_fourth_group");
		    	lable_flag.style.display = "block";
		    	var lable_reset = document.getElementById("label_fourth_reset");
				lable_reset.style.display = "block";
		    	shareChartStatDto4 = shareChartStatDtoTemp;
				nameStr4Html = nameStrTemp;
		    	$("#label_manual_fourth").html(nameStr4Html);
		    	nameStr4 = nameStr4Html+" "+storeNo;
		    }else if(labelName5.length==0){
		    	var lable_flag = document.getElementById("label_fifth_group");
		    	lable_flag.style.display = "block";
		    	var lable_reset = document.getElementById("label_fifth_reset");
				lable_reset.style.display = "block";
		    	shareChartStatDto5 = shareChartStatDtoTemp;
				nameStr5Html = nameStrTemp;
		    	$("#label_manual_fifth").html(nameStr5Html);
		    	nameStr5 = nameStr5Html+" "+storeNo;
		    }       
	      document.body.className = "skin-blue sidebar-mini"; 
	  });
	  
	  var nameStr1="";
	  var nameStr2="";
	  var nameStr3="";
	  var nameStr4="";
	  var nameStr5="";
	  var nameStr1Html="";
	  var nameStr2Html="";
	  var nameStr3Html="";
	  var nameStr4Html="";
	  var nameStr5Html="";
	  
	  $("#search-btn-contrast").click(function(){
			var storename = $("#store_name_manual_contrast").val()==null?'':$("#store_name_manual_contrast").val();
			var labelName1 = $("#label_manual_first").html();
			var labelName2 = $("#label_manual_second").html();
			var labelName3 = $("#label_manual_third").html();
			var labelName4 = $("#label_manual_fourth").html();
			var labelName5 = $("#label_manual_fifth").html();
			var lable_first = document.getElementById("warning_text");
	    	lable_first.style.display = "block";
	    	
	    	var shareChartStatDtoTemp = getShareParamContrast();
	    	var deptName = shareChartStatDtoTemp.deptname==null?'全部事业群':shareChartStatDtoTemp.deptname;
	    	var channelName = shareChartStatDtoTemp.channelname==null?'全部频道':shareChartStatDtoTemp.channelname;
	    	var cityName = shareChartStatDtoTemp.cityname==null?'全部城市':shareChartStatDtoTemp.cityname;
	    	var storeNo = shareChartStatDtoTemp.storeno==null?'':shareChartStatDtoTemp.storeno;
	    	var nameStrTemp = deptName+" "+channelName+" "+cityName+" "+storename;
	    	if($.trim(nameStrTemp)==$.trim(nameStr1Html) || $.trim(nameStrTemp)==$.trim(nameStr2Html) || $.trim(nameStrTemp)==$.trim(nameStr3Html) 
	    			|| $.trim(nameStrTemp)==$.trim(nameStr4Html) || $.trim(nameStrTemp)==$.trim(nameStr5Html)){//对比条件重复过滤
	    		return;
	    	}
		    if(labelName1.length==0){
		    	var lable_flag = document.getElementById("label_first_group");
		    	lable_flag.style.display = "block";
		    	var lable_reset = document.getElementById("label_first_reset");
				lable_reset.style.display = "block";
		    	shareChartStatDto1 = shareChartStatDtoTemp;
				nameStr1Html = nameStrTemp;
		    	$("#label_manual_first").html(nameStr1Html);
		    	nameStr1 = nameStr1Html+" "+storeNo;
		    }else if(labelName2.length==0){
		    	var lable_flag = document.getElementById("label_second_group");
		    	lable_flag.style.display = "block";
		    	var lable_reset = document.getElementById("label_second_reset");
				lable_reset.style.display = "block";
		    	shareChartStatDto2 = shareChartStatDtoTemp;
				nameStr2Html = nameStrTemp;
		    	$("#label_manual_second").html(nameStr2Html);
		    	nameStr2 = nameStr2Html+" "+storeNo;
		    }else if(labelName3.length==0){
		    	var lable_flag = document.getElementById("label_third_group");
		    	lable_flag.style.display = "block";
		    	var lable_reset = document.getElementById("label_third_reset");
				lable_reset.style.display = "block";
		    	shareChartStatDto3 = shareChartStatDtoTemp;
				nameStr3Html = nameStrTemp;
		    	$("#label_manual_third").html(nameStr3Html);
		    	nameStr3 = nameStr3Html+" "+storeNo;
		    }else if(labelName4.length==0){
		    	var lable_flag = document.getElementById("label_fourth_group");
		    	lable_flag.style.display = "block";
		    	var lable_reset = document.getElementById("label_fourth_reset");
				lable_reset.style.display = "block";
		    	shareChartStatDto4 = shareChartStatDtoTemp;
				nameStr4Html = nameStrTemp;
		    	$("#label_manual_fourth").html(nameStr4Html);
		    	nameStr4 = nameStr4Html+" "+storeNo;
		    }else if(labelName5.length==0){
		    	var lable_flag = document.getElementById("label_fifth_group");
		    	lable_flag.style.display = "block";
		    	var lable_reset = document.getElementById("label_fifth_reset");
				lable_reset.style.display = "block";
		    	shareChartStatDto5 = shareChartStatDtoTemp;
				nameStr5Html = nameStrTemp;
		    	$("#label_manual_fifth").html(nameStr5Html);
		    	nameStr5 = nameStr5Html+" "+storeNo;
		    }
		 });
	  
	  $("#label_first_reset").click(function(){
		  nameStr1 = "";
		  nameStr1Html = "";
		  $("#label_manual_first").html("");
		  $("#channelSelectContrast").empty();
		  initallchannelcontrast();
		  var labelName1 = $("#label_manual_first").html();
		  var labelName2 = $("#label_manual_second").html();
		  var labelName3 = $("#label_manual_third").html();
		  var labelName4 = $("#label_manual_fourth").html();
		  var labelName5 = $("#label_manual_fifth").html();
		  var lable_reset = document.getElementById("label_first_group");
		  lable_reset.style.display = "none";
		  if(labelName1.length==0 && labelName2.length==0 && labelName3.length==0 
				  && labelName4.length==0 && labelName5.length==0){
			var lable_first = document.getElementById("warning_text");
  			lable_first.style.display = "none";
		  }
	  });
	  $("#label_second_reset").click(function(){
		  nameStr2 = "";
		  nameStr2Html = "";
		  $("#label_manual_second").html("");
		  $("#channelSelectContrast").empty();
		  initallchannelcontrast();
		  var labelName1 = $("#label_manual_first").html();
		  var labelName2 = $("#label_manual_second").html();
		  var labelName3 = $("#label_manual_third").html();
		  var labelName4 = $("#label_manual_fourth").html();
		  var labelName5 = $("#label_manual_fifth").html();
		  var lable_reset = document.getElementById("label_second_group");
		  lable_reset.style.display = "none";
		  if(labelName1.length==0 && labelName2.length==0 &&labelName3.length==0 
				  && labelName4.length==0 && labelName5.length==0){
			var lable_first = document.getElementById("warning_text");
  			lable_first.style.display = "none";
		  }
	  });
	  $("#label_third_reset").click(function(){
		  nameStr3 = "";
		  nameStr3Html = "";
		  $("#label_manual_third").html("");
		  $("#channelSelectContrast").empty();
		  initallchannelcontrast();
		  var labelName1 = $("#label_manual_first").html();
		  var labelName2 = $("#label_manual_second").html();
		  var labelName3 = $("#label_manual_third").html();
		  var labelName4 = $("#label_manual_fourth").html();
		  var labelName5 = $("#label_manual_fifth").html();
		  var lable_reset = document.getElementById("label_third_group");
		  lable_reset.style.display = "none";
		  if(labelName1.length==0 && labelName2.length==0 &&labelName3.length==0 
				  && labelName4.length==0 && labelName5.length==0){
			var lable_first = document.getElementById("warning_text");
  			lable_first.style.display = "none";
		  }
	  });
	  $("#label_fourth_reset").click(function(){
		  nameStr4 = "";
		  nameStr4Html = "";
		  $("#label_manual_fourth").html("");
		  $("#channelSelectContrast").empty();
		  initallchannelcontrast();
		  var labelName1 = $("#label_manual_first").html();
		  var labelName2 = $("#label_manual_second").html();
		  var labelName3 = $("#label_manual_third").html();
		  var labelName4 = $("#label_manual_fourth").html();
		  var labelName5 = $("#label_manual_fifth").html();
		  var lable_reset = document.getElementById("label_fourth_group");
		  lable_reset.style.display = "none";
		  if(labelName1.length==0 && labelName2.length==0 &&labelName3.length==0 
				  && labelName4.length==0 && labelName5.length==0){
			var lable_first = document.getElementById("warning_text");
  			lable_first.style.display = "none";
		  }
	  });
	  $("#label_fifth_reset").click(function(){
		  nameStr5 = "";
		  nameStr5Html = "";
		  $("#label_manual_fifth").html("");
		  $("#channelSelectContrast").empty();
		  initallchannelcontrast();
		  var labelName1 = $("#label_manual_first").html();
		  var labelName2 = $("#label_manual_second").html();
		  var labelName3 = $("#label_manual_third").html();
		  var labelName4 = $("#label_manual_fourth").html();
		  var labelName5 = $("#label_manual_fifth").html();
		  var lable_reset = document.getElementById("label_fifth_group");
		  lable_reset.style.display = "none";
		  if(labelName1.length==0 && labelName2.length==0 &&labelName3.length==0 
				  && labelName4.length==0 && labelName5.length==0){
			var lable_first = document.getElementById("warning_text");
  			lable_first.style.display = "none";
		  }
	  });
	  $("#contrast").click(function(){
		  var url = "";
		  if(target==0){
		  	url = "index_K_contrast.html?t="+encode64('0')+"&nameStr1="+encode64(nameStr1)+"&nameStr2="+encode64(nameStr2)+"&nameStr3="+encode64(nameStr3)+"&nameStr4="+encode64(nameStr4)+"&nameStr5="+encode64(nameStr5)+"&#ff";
		  }else if(target==1){
		  	url = "index_K_contrast.html?t="+encode64(1)+"&s=&sn=&c=cn="+encode64(pageStatusInfo.cityName)+"&e=&r=&nameStr1="+encode64(nameStr1)+"&nameStr2="+encode64(nameStr2)+"&nameStr3="+encode64(nameStr3)+"&nameStr4="+encode64(nameStr4)+"&nameStr5="+encode64(nameStr5)+"&#ff";
		  }else{
			  url = "index_K_contrast.html?t="+encode64('0')+"&nameStr1="+encode64(nameStr1)+"&nameStr2="+encode64(nameStr2)+"&nameStr3="+encode64(nameStr3)+"&nameStr4="+encode64(nameStr4)+"&nameStr5="+encode64(nameStr5)+"&#ff";
		  }
		  window.open(url,"index_K_contrast"); 
	  });
	  
  })
  
  function getUser(){
	doManager("UserManager", "getCurrentUserDTO",null,function(data, textStatus, XMLHttpRequest) {
		if (data.result) {
			var employeeID="";
			var curr_user = JSON.parse(data.data);
			if(curr_user == null){
				urlStr = "../logout.do";
				window.location.href = urlStr;
			}
		}
  	},false);
}
  
  //初始化实时图数据
//   var real_value;
  function initRealData(){
// 	  var storeno = $("#store_id_manual").val()==""?null:$("#store_id_manual").val();
// 	  var cityName = $("#citySelect  option:selected").val();
// 	  if(cityName!="" && cityName!=undefined){
// 	    if(cityName=="黔东南苗族侗族自治州"){
// 	    	cityName="黔东南";
// 	  	}
// 	  }else{
// 	    cityName = null;
// 	  }
//       var deptname = $("#deptSelect option:selected").html()==""?null:$("#deptSelect option:selected").html();
// 		if(deptname!="" && deptname=="全部事业群"){
// 	    	  deptname = null;
// 	      }
// 	  var channelname = $("#channel_name_manual").val()==""?null:$("#channel_name_manual").val();
	  
// 	  var chartStatDto=null;
// 		chartStatDto = {
// 			storeno:storeno,
// 			cityname:cityName,
// 			deptname:deptname,
// 			channelname:channelname	
// 		}
//       doManager("chartStatManager","queryDayTurnover",[chartStatDto],
// 		function(datax,textStatus,XmlHttpRequest){
// 			if (datax.result) {
// 				var jsonData = $.fromJSON(datax.data);
// 				real_value = jsonData.day_amount;
// 			}
//  	   },false);
	  
// 	  for (var i = 0; i < 40; i++) {
// 	      data.push(randomData(real_value));//循环执行randomData,并将结果放入data数组
// 	  }
// 	  myChart1.setOption({
//           series: [{
//               data: data
//           }]
//       });
  }
  
  var curDayGMV = '0';
  function inintCurDayData(){
	  doManager("chartStatManager","queryDayTurnover",[shareChartStatDto],
			function(datax,textStatus,XmlHttpRequest){
				if (datax.result) {
					var jsonData = $.fromJSON(datax.data);
					if(Object.keys(jsonData).length!=0){
						curDayGMV = jsonData.day_amount;
// 						real_value = jsonData.day_amount;
						$("#curDayGMV").html(parseFloat(curDayGMV).toLocaleString());
					}else{
						$("#curDayGMV").html('0');
					}
				}
		},false);
// 	  myChart1.setOption({
//           series: [{
//               data: data
//           }]
//       });
  }
  
  var curMonthData = '0';
  function initCurMonthData(){
		doManager("chartStatManager","queryMonthTurnover",[shareChartStatDto],
			function(data,textStatus,XmlHttpRequest){
				if (data.result) {
					var jsonData = $.fromJSON(data.data);
					if(Object.keys(jsonData).length!=0){
						curMonthData = jsonData.month_amount;
						$("#curMonthGMV").html(changeMoney(parseInt(curMonthData)));
					}else{
						$("#curMonthGMV").html('0万');
					}
				}
		});
	} 
  
  var hourData = [];  //0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
  function initHourData(){
  	  var productCheck = $("#cbClick").is(':checked');
	  var serviceCheck = $("#maxbClick").is(':checked');
	  var buyCheck = $("#thirdbClick").is(':checked');
	  var searchstr = "";
	  if(productCheck){
	  	searchstr+="1,"
	  }
	  if(serviceCheck){
	  	searchstr+="2,"
	  }
	  if(buyCheck){
	  	searchstr+="3,"
	  }
	  searchstr = searchstr.substring(0,searchstr.lastIndexOf(","));
	  var reqestParameter = {
        cityName: "北京",
        storeNo: shareChartStatDto.storeno,
        searchstr: searchstr
      }
 		doManager("communityMembersManager","queryTwoTwoOneGMVByHour",[reqestParameter],
 			function(data,textStatus,XmlHttpRequest){
 				if (data.result) {
 					var nowHour = now.getHours();
 					hourData=new Array(nowHour+2).join(0).split('');
 					var jsonData = $.fromJSON(data.data);
 					if(jsonData.twoTwoOneHourData.length!=0){
 						$(jsonData.twoTwoOneHourData).each(function(index,element){
 	 						hourData[parseInt(element.time)]=element.turnover;
 	                     });
 						chart4_option.series[0].data=hourData;
 						chart4.setOption(chart4_option);
 					}else{
 						hourData = [];
 						chart4_option.series[0].data=hourData;
 						chart4.setOption(chart4_option);
 					}
 				}
 		});
 	} 
  
  function initDayData(){
	  var fistDay = "2018-07-01";
	  var curDay = now.getFullYear() + "-" + (now.getMonth() + 1) + "-" + now.getDate();
	  xData = getAllDate(fistDay,curDay);
	  var productCheck = $("#cbClick").is(':checked');
	  var serviceCheck = $("#maxbClick").is(':checked');
	  var buyCheck = $("#thirdbClick").is(':checked');
	  var searchstr = "";
	  var storeNo = $("#store_id_manual").val();
	  if(storeNo_ == ""){
	  	storeNo = shareChartStatDto.storeno;
	  }
	  if(productCheck){
	  	searchstr+="1,"
	  }
	  if(serviceCheck){
	  	searchstr+="2,"
	  }
	  if(buyCheck){
	  	searchstr+="3,"
	  }
	  /*
	  if(ordertype == '0'){
	  	searchstr="1,2,3"
	  }*/
	  searchstr = searchstr.substring(0,searchstr.lastIndexOf(","));
	  var reqestParameter = {
        cityName: "北京",
        storeNo: storeNo,
        searchstr: searchstr
      }
	  doManager("communityMembersManager","queryTwoTwoOneGMVByDay",[reqestParameter],
			function(data,textStatus,XmlHttpRequest){
				if (data.result) {
					yData=new Array(xData.length+1).join(0).split('');
					yData_jy=new Array(xData.length+1).join(0).split('');
					var jsonData = $.fromJSON(data.data);
					if(jsonData.twoTwoOneDayData.length!=0){
						$(jsonData.twoTwoOneDayData).each(function(index,element){
							var indexTemp = jQuery.inArray(element.day_time,xData);
							yData[indexTemp]=element.turnover;
		                });
		                if(storeNo){
			                $(yData).each(function(index,element){
			                	if(productCheck&&serviceCheck){
									yData_jy[index]=10000;
			                	}else if(productCheck){
			                		yData_jy[index]=5000;
			                	}else if(serviceCheck){
			                		yData_jy[index]=5000;
			                	}else if(!productCheck&&!serviceCheck){
			                		yData_jy[index]=0;
			                	}else{
			                		yData_jy[index]=5000;
			                	}
			                });
		                }else{
		                	$(yData).each(function(index,element){
		                		if(productCheck&&serviceCheck){
									yData_jy[index]=10000*storeCount;
			                	}else if(productCheck){
			                		yData_jy[index]=5000*storeCount;
			                	}else if(serviceCheck){
			                		yData_jy[index]=5000*storeCount;
			                	}else if(!productCheck&&!serviceCheck){
			                		yData_jy[index]=0;
			                	}else{
			                		yData_jy[index]=5000*storeCount;
			                	}
		                	});
		                }
						chart5_option.series[0].data=yData;
						chart5_option.series[1].data=yData_jy;
						chart5_option.xAxis[0].data=xData;
						chart5.setOption(chart5_option);
					}else{
						yData = [];
						chart5_option.series[0].data=yData;
						chart5_option.series[1].data=yData_jy;
						chart5_option.xAxis[0].data=xData;
						chart5.setOption(chart5_option);
					}
				}
		});
  }
  
  var weekData = [];  //0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
  function initWeekData(){
	  weekData = new Array(xData_week.length+1).join(0).split('');
	  var productCheck = $("#cbClick").is(':checked');
	  var serviceCheck = $("#maxbClick").is(':checked');
	  var buyCheck = $("#thirdbClick").is(':checked');
	  var searchstr = "";
	  if(productCheck){
	  	searchstr+="1,"
	  }
	  if(serviceCheck){
	  	searchstr+="2,"
	  }
	  if(buyCheck){
	  	searchstr+="3,"
	  }
	  searchstr = searchstr.substring(0,searchstr.lastIndexOf(","));
	  var reqestParameter = {
        cityName: "北京",
        storeNo: shareChartStatDto.storeno,
        searchstr: searchstr
      }
		doManager("communityMembersManager", "queryTwoTwoOneGMVByWeek",[reqestParameter],
            function(data, textStatus, XMLHttpRequest) {
               if (data.result) {
					var jsonData = $.fromJSON(data.data);
					if(jsonData.twoTwoOneData.length!=0){
						$(jsonData.twoTwoOneData).each(function(index,element){
							var dateTemp = new Date(parseInt(element.week_time)).format("yyyy-MM-dd");
							var indexTemp = jQuery.inArray(dateTemp,xData_week);
							weekData[indexTemp]=element.week_amount;
	                    });
						chart7_option.series[0].data=weekData;
						chart7.setOption(chart7_option);
					}else{
						weekData = [];
						chart7_option.series[0].data=weekData;
						chart7.setOption(chart7_option);
					}
				}
            });
	} 
  
  Date.prototype.toLocaleString = function() {
      return this.getFullYear() + "-" + (this.getMonth() + 1) + "-" + this.getDate();
  }; 
  
  function initMonthData(){
	    var curMonth = now.getFullYear()+'-'+(now.getMonth()+1);
	    var months = getYearAndMonth('2017-12-1',curMonth);
		yData_month = new Array(months.length+1).join(0).split('');
		doManager("chartStatManager","queryTurnoverByMonth",[shareChartStatDto],
			function(data,textStatus,XmlHttpRequest){
				if (data.result) {
					var jsonData = $.fromJSON(data.data);
					if(jsonData.length!=0){
						$(jsonData).each(function(index,element){
							var indexTemp = jQuery.inArray(element.month_time,months);
							yData_month[indexTemp]=element.month_amount;
	               		});
						chart6_option.series[0].data=yData_month;
					}else{
						yData_month = [];
						chart6_option.series[0].data=yData_month;
					}
				}
		},false);
		
		doManager("chartStatManager","queryTargetByMonth",[shareChartStatDto],
			function(data,textStatus,XmlHttpRequest){
				if (data.result) {
					var jsonData = $.fromJSON(data.data);
					if(jsonData.length!=0){
						yData_month_target = new Array(12).join(0).split('');
						var curMonth = now.getFullYear()+'-12-31';
						var monthData = getYearAndMonth('2017-12-1',curMonth);
						$(jsonData).each(function(index,element){
							var indexTemp = jQuery.inArray(element.month_time,monthData);
							yData_month_target[indexTemp]=element.month_amount;
	               		});
						chart6_option.series[1].data=yData_month_target;
						chart6.setOption(chart6_option);
					}else{//没有数据时不显示
						yData_month_target = [];
						chart6_option.series[1].data=yData_month_target;
						chart6.setOption(chart6_option);
					}
				}
		});
		$("#process_div").hide();
		$("#process_div_pic").hide();
	} 
  
  function initOnlineOfflineTurnover(){
		doManager("chartStatManager","queryOnlineOfflineTurnover",[shareChartStatDto],
			function(data,textStatus,XmlHttpRequest){
				if (data.result) {
					var jsonData = $.fromJSON(data.data);
					option2.series[0].data[1].value = jsonData.offline_amount;
					option2.series[0].data[0].value = jsonData.online_amount;
					myChart2.setOption(option2,true);
				}
		});
	}
  
  function initScatterplot(){
      var productCheck = $("#cbClick").is(':checked');
	  var serviceCheck = $("#maxbClick").is(':checked');
	  var buyCheck = $("#thirdbClick").is(':checked');
	  var searchstr = "";
	  if(productCheck){
	  	searchstr+="1,"
	  }
	  if(serviceCheck){
	  	searchstr+="2,"
	  }
	  if(buyCheck){
	  	searchstr+="3,"
	  }
	  var storeNo = $("#store_id_manual").val();
	  if(storeNo_ == ""){
	  	storeNo = shareChartStatDto.storeno;
	  }
	  searchstr = searchstr.substring(0,searchstr.lastIndexOf(","));
	  var reqestParameter = {
        cityName: "北京",
        storeNo: storeNo,
        searchstr: searchstr
      }
	  doManager("communityMembersManager","queryDataOfScatterplot",[reqestParameter],
			function(data,textStatus,XmlHttpRequest){
				if (data.result) {
					var jsonData = $.fromJSON(data.data);
					if(jsonData.twoTwoOneScatterplotData.length!=0){
						$(jsonData.twoTwoOneScatterplotData).each(function(index,element){
							var temp = [];
							temp.push(element.order_count);
							temp.push((element.order_amount/10000).toFixed(2));
							temp.push(element.city_name);
							temp.push(element.store_name);
							temp.push(element.time);
							temp.push(element.storeno);
							temp.push(element.cityno);
							dataBJ.push(temp);
	                 	});
						option3.series[0].data = dataBJ;
						myChart3.setOption(option3,true);
					}else{
						dataBJ = [];
						option3.series[0].data = dataBJ;
						myChart3.setOption(option3,true);	
					}
				}
		});
  }
	
	
  
  function initallcity(){
	   var effectiveCity = [];
	   doManager("chartStatManager","queryContainsStoreDistCityList",null,
		    function(data,textStatus,XmlHttpRequest){
				if (data.result) {
					var jsonData = $.fromJSON(data.data);
					$(jsonData).each(function(index,element){
						effectiveCity.push(element.cityname);
                   });
				}
		},false);
	  
		doManager("distCityCodeManager","queryAllDistCityList",null,
		    function(data,textStatus,XmlHttpRequest){
				if (data.result) {
					var jsonData = $.fromJSON(data.data);
					//$("#citySelect").append("<option value=''>全部城市</option>");
					//$("#citySelectContrast").append("<option value=''>全部城市</option>");
					$(jsonData).each(function(index,element){
						var indexTemp = jQuery.inArray(element.cityname,effectiveCity);
						if(indexTemp>=0){
							if(element.cityname == '北京'){
								$("#citySelect").append('<option value="'+element.cityname+'">'+element.cityname+'</option>');
							}
						}
                   });
				}
		});
	} 
   
  var array=new Array();
	//初始化所有门店 
	function initallstore() {
		doManager("StoreManager", "findStoreAll", null,
			function(data, textStatus, XMLHttpRequest) {
				if (data.result) {
					var jsonData = $.fromJSON(data.data);
					array = new Array();
					var storeString = "";
					for(i=0;i<jsonData.length;i++){
						array.push(jsonData[i].name);
						if(i == 0){
							storeString = "{'"+jsonData[i].name+"'"+":"+"'"+jsonData[i].storeno+"'"+",";
						}else if(i == jsonData.length-1){
							storeString = storeString+"'"+jsonData[i].name+"'"+":"+"'"+jsonData[i].storeno+"'"+"}";
						}else{
							storeString = storeString+"'"+jsonData[i].name+"'"+":"+"'"+jsonData[i].storeno+"'"+",";
						}
					}
					storeJson=$.fromJSON(storeString);
					var autoComplete = new AutoComplete("store_name_manual","auto",array);
					document.getElementById("store_name_manual").onkeyup = function(event){
						autoComplete.start(event);
						if($("#store_name_manual").val().trim() == ""){
							$("#store_id_manual").val("");
						}
						document.getElementById("store_name_manual").value=document.getElementById("store_name_manual").value.replace(/(\s*)|(\s*)/g,'').replace(/[ ]/g,'');
					}
				}
			}, false);
	}
	
   function initcurcity(){
	   var effectiveCity = [];
	   doManager("chartStatManager","queryContainsStoreDistCityList",null,
		    function(data,textStatus,XmlHttpRequest){
				if (data.result) {
					var jsonData = $.fromJSON(data.data);
					$(jsonData).each(function(index,element){
						effectiveCity.push(element.cityname);
                   });
				}
		},false);
	   
		doManager("userManager","getCurrentUserCity",null,
		    function(data,textStatus,XmlHttpRequest){
				if (data.result) {
					var jsonData = $.fromJSON(data.data);
					$(jsonData).each(function(index,element){
						var indexTemp = jQuery.inArray(element.cityname,effectiveCity);
						if(indexTemp>=0){
							if(element.cityname == '北京'){
								$("#citySelect").append('<option value="'+element.cityname+'">'+element.cityname+'</option>');
							}
							//$("#citySelectContrast").append('<option value="'+element.cityname+'">'+element.cityname+'</option>');
						}
                   });
					
				}
		},false);
	}
  
   $("#citySelect").change(function(){
		$("#store_id_manual").val("");
		$("#store_name_manual").val("");
		initStoreSelectcomplate();
	});
   
   var array_contrast=new Array();
 	//初始化所有门店 
 	function initallstorecontrast() {
 		doManager("StoreManager", "findStoreAll", null,
 			function(data, textStatus, XMLHttpRequest) {
 				if (data.result) {
 					var jsonData = $.fromJSON(data.data);
 					array_contrast = new Array();
 					var storeString = "";
 					for(i=0;i<jsonData.length;i++){
 						array_contrast.push(jsonData[i].name);
 						if(i == 0){
 							storeString = "{'"+jsonData[i].name+"'"+":"+"'"+jsonData[i].storeno+"'"+",";
 						}else if(i == jsonData.length-1){
 							storeString = storeString+"'"+jsonData[i].name+"'"+":"+"'"+jsonData[i].storeno+"'"+"}";
 						}else{
 							storeString = storeString+"'"+jsonData[i].name+"'"+":"+"'"+jsonData[i].storeno+"'"+",";
 						}
 					}
 					storeJson=$.fromJSON(storeString);
 					var autoCompleteContrast = new AutoComplete("store_name_manual_contrast","auto_contrast",array_contrast);
 					document.getElementById("store_name_manual_contrast").onkeyup = function(event){
 						autoCompleteContrast.start(event);
 						if($("#store_name_manual_contrast").val().trim() == ""){
 							$("#store_id_manual_contrast").val("");
 						}
 						document.getElementById("store_name_manual_contrast").value=document.getElementById("store_name_manual_contrast").value.replace(/(\s*)|(\s*)/g,'').replace(/[ ]/g,'');
 					}
 				}
 			}, false);
 	}
   
   $("#citySelectContrast").change(function(){
		$("#store_id_manual_contrast").val("");
		$("#store_name_manual_contrast").val("");
		initStoreSelectcomplateContrast();
	});
	
   var storeJson;
	function initStoreSelectcomplate(){
		var citySelect = $("#citySelect  option:selected").val();
		$("#auto").val("");
		if(citySelect==""){
			initallstore();
		}else{
			//根据城市查询门店 信息
			var cityname = "'"+citySelect+"'";
			doManager("StoreManager","findStoreByCityData",[cityname],
			    function(data,textStatus,XmlHttpRequest){
					if (data.result) {
						var jsonData = $.fromJSON(data.data);
						array=new Array();
						var storeString = "";
						$(jsonData).each(function(index,element){
							var storeName = element.name;
							var storeid = element.storeno;
							array.push(storeName);
							if(index == 0){
	 							storeString = "{'"+storeName+"'"+":"+"'"+storeid+"'"+",";
	 						}else if(index == jsonData.length-1){
	 							storeString = storeString+"'"+storeName+"'"+":"+"'"+storeid+"'"+"}";
	 						}else{
	 							storeString = storeString+"'"+storeName+"'"+":"+"'"+storeid+"'"+",";
	 						}
						});
						storeJson=$.fromJSON(storeString);
						var autoCompleteName = new AutoComplete("store_name_manual","auto",array);
						document.getElementById("store_name_manual").onkeyup = function(event){
							autoCompleteName.start(event);
							if($("#store_name_manual").val().trim() == ""){
								$("#store_id_manual").val("");
							}
							document.getElementById("store_name_manual").value=document.getElementById("store_name_manual").value.replace(/(\s*)|(\s*)/g,'').replace(/[ ]/g,'');
						}
					}
			});
		}
		
	}
	
	var storeJsonContrast;
	function initStoreSelectcomplateContrast(){
		var citySelect = $("#citySelectContrast  option:selected").val();
		$("#auto_contrast").val("");
		if(citySelect==""){
			initallstorecontrast();
		}else{
			//根据城市查询门店 信息
			var cityname = "'"+citySelect+"'";
			doManager("StoreManager","findStoreByCityData",[cityname],
			    function(data,textStatus,XmlHttpRequest){
					if (data.result) {
						var jsonData = $.fromJSON(data.data);
						array_contrast=new Array();
						var storeString = "";
						$(jsonData).each(function(index,element){
							var storeName = element.name;
							var storeid = element.storeno;
							array_contrast.push(storeName);
							if(index == 0){
	 							storeString = "{'"+storeName+"'"+":"+"'"+storeid+"'"+",";
	 						}else if(index == jsonData.length-1){
	 							storeString = storeString+"'"+storeName+"'"+":"+"'"+storeid+"'"+"}";
	 						}else{
	 							storeString = storeString+"'"+storeName+"'"+":"+"'"+storeid+"'"+",";
	 						}
						});
						storeJson=$.fromJSON(storeString);
						var autoCompleteName = new AutoComplete("store_name_manual_contrast","auto_contrast",array_contrast);
						document.getElementById("store_name_manual_contrast").onkeyup = function(event){
							autoCompleteName.start(event);
							if($("#store_name_manual_contrast").val().trim() == ""){
								$("#store_id_manual_contrast").val("");
							}
							document.getElementById("store_name_manual_contrast").value=document.getElementById("store_name_manual_contrast").value.replace(/(\s*)|(\s*)/g,'').replace(/[ ]/g,'');
						}
					}
			});
		}
		
	}
  
	var iCheckStr = "cb";
	var iCheckMaxStr = "";
	function getShareParam(){
		 var cLabel=0;
		 var maxBLabel=0;
		 var smallBLabel=0;
		 if(iCheckStr=='cb') {
			 cLabel=1;
			 smallBLabel=1;
		 }
		 if(iCheckMaxStr=='maxb') {
			 maxBLabel=1;
		 }
		 var storeno = $("#store_id_manual").val()==""?null:$("#store_id_manual").val();
		  var cityName = $("#citySelect  option:selected").val();
		  if(cityName!="" && cityName!=undefined){
		    if(cityName=="黔东南苗族侗族自治州"){
		    	cityName="黔东南";
		  	}
		  }else{
		    cityName = null;
		  }
		  return shareChartStatDto = {				
				storeno:storeno,
				cityname:cityName,
				cLabel:cLabel,
				maxBLabel:maxBLabel,
				smallBLabel:smallBLabel
			}
	}
	
	function getShareParamContrast(){
		 var storeno = $("#store_id_manual_contrast").val()==""?null:$("#store_id_manual_contrast").val();
		  var cityName = $("#citySelectContrast  option:selected").val();
		  if(cityName!="" && cityName!=undefined){
		    if(cityName=="黔东南苗族侗族自治州"){
		    	cityName="黔东南";
		  	}
		  }else{
		    cityName = null;
		  }
	      var deptname = $("#deptSelectContrast option:selected").html()==""?null:$("#deptSelectContrast option:selected").html();
	      if(deptname!="" && deptname=="全部事业群"){
	    	  deptname = null;
	      }
	      var channelname = $("#channelSelectContrast option:selected").html()==""?null:$("#channelSelectContrast option:selected").html();
	      if(channelname!="" && channelname=="全部频道"){
	    	  channelname = null;
	      }
		  
		  return shareChartStatDto = {
				storeno:storeno,
				cityname:cityName,
				deptname:deptname,
				channelname:channelname
			}
	}
	
	//搜索
	function search_manual_k(){
		clearInterval(timeTicket);
		var number_str = $("#store_id_manual").val(); 
  	    var storeName = $("#store_name_manual").val();
  	  	if(storeName!=""){
  	  		if(storeName_){
  	  		
  	  		}else{
  	  			if($.inArray(storeName, array) == -1){
				crm_alert(0,"该门店不存在！");
				timeTicket = setInterval(loadtime, 5000);
				return;
				}
  	  		}
	    }
  	    if(number_str==""&&storeName!=""){
	    	number_str=-10000;
	    }
		
		 $("#process_div").show();
	 	 $("#process_div_pic").show();
	 	 
	 	  
	 	  shareChartStatDto = getShareParam();
	 	  data = [];
		  initRealData();
		  //实时营业额
		  curDayGMV = 0.00;
		  inintCurDayData();
// 		  setInterval(inintCurDayData, 5000);
		  //当月营业额
		  curMonthData = 0.00;
		  initCurMonthData();
// 		  setInterval(initCurMonthData, 5000);
		  //分时GMV
		  hourData = [];
		  //initHourData();
// 		  setInterval(initHourData, 5000);
		  //日GMV
   		  yData = [];
		  initDayData();
		  //周GMV
		  weekData = [];
		  //initWeekData();
		  //月GMV
		  yData_month = [];
		  yData_month_target = [];
		  initMonthData();
		  //占比营业额
		  //initOnlineOfflineTurnover();
		  //门店GMV散点分布
		  dataBJ = [];
		  initScatterplot();		  
    }
	
	function excuteChart(){
		chartresize1();
	  	chartresize2();
	  	chartresize3();
	  	chartresize4();
	  	search_manual_k();
// 	  	timeTicket=setInterval(loadtime, 5000);
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
  //获取当前日期为今年的第几天
//   function getCurDay(){
// 	  var dateArr = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);  
// 	  var date = new Date();  
// 	  var day = date.getDate();  
// 	  var month = date.getMonth(); //getMonth()是从0开始  
// 	  var year = date.getFullYear();  
// 	  var result = 0;  
// 	  for ( var i = 0; i < month; i++) {  
// 	      result += dateArr[i];  
// 	  }  
// 	  result += day;  
// 	  //判断是否闰年  
// 	  if (month > 1 && (year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {  
// 	      result += 1;  
// 	  }  
// 	  return result;
//   }
  
  //获取当年第一天
  function getFirstDayOfYear() {
	 var nowTime = new Date();
	 nowTime.setDate(1);
	 nowTime.setMonth(0);
     return timeFormat(nowTime);
  }
   //日期格式化，返回值形式为yy-mm-dd
   function timeFormat(date) {
       if (!date || typeof(date) === "string") {
           this.error("参数异常，请检查...");
      }
      var y = date.getFullYear(); //年
      var m = date.getMonth() + 1; //月
      var d = date.getDate(); //日
      return y + "-" + m + "-" + d;
   }
  //获取两个时间段内的月份数据
  function getYearAndMonth(start, end) {
	    var result = [];
	    var starts = start.split('-');
	    var ends = end.split('-');
	    var staYear = parseInt(starts[0]);
	    var staMon = parseInt(starts[1]);
	    var endYear = parseInt(ends[0]);
	    var endMon = parseInt(ends[1]);
	    while (staYear <= endYear) {
	        if (staYear === endYear) {
	            while (staMon < endMon) {
	                staMon++;
	                result.push(staYear+"-"+staMon);
	            }
	            staYear++;
	        } else {
	            staMon++;
	            if (staMon > 12) {
	                staMon = 1;
	                staYear++;
	            }
	            result.push(staYear+"-"+staMon);
	        }
	    }
	    return result;
	}
  
  //获取当年所有的日期
  function getAllDate(begin, end) {  
	  var result = [];
      var ab = begin.split("-");  
      var ae = end.split("-");  
      var db = new Date();  
      db.setUTCFullYear(ab[0], ab[1] - 1, ab[2]);  
      var de = new Date();  
      de.setUTCFullYear(ae[0], ae[1] - 1, ae[2]);  
      var unixDb = db.getTime();  
      var unixDe = de.getTime();  
      for (var k = unixDb; k <= unixDe;) {  
          result.push((new Date(parseInt(k))).format());
          k = k + 24 * 60 * 60 * 1000;  
      }  
      return result;
  } 
  
  function excuteRefresh(){
	  location.reload();
  }
  function toIndex(){
  		if(target==0){
			window.location.href="index_K.html?t="+encode64('0')+"&s=&sn=&c=&e=&r=&cn=&so="+encode64(storeNo_)+"&ordertype="+encode64(ordertype)+"&store_name="+encode64(storeName_);
		}else if(target==1){
			window.location.href="index_K.html?t="+encode64('1')+"&s=&sn=&c=&e=&r=&cn=&so="+encode64(storeNo_)+"&ordertype="+encode64(ordertype)+"&store_name="+encode64(storeName_);
		}
  }
  function queryTwoTwoOneStoreCount(){
  	 var reqestParameter = {
        cityName: "北京",
        storeNo: "",
        searchstr: ""
      }
  	 doManager("communityMembersManager","queryTwoTwoOneStoreCount",[reqestParameter],
			function(data,textStatus,XmlHttpRequest){
			  if (data.result) {
				var jsonData = $.fromJSON(data.data);
				storeCount = jsonData.twoTwoOneStoreCountData;
			}
		});
  }
  function initStoreSelectBy221(){
  	if(storeNo_!=null&&storeNo_!=""){
  		$("#store_id_manual").val(storeNo_);
  		$("#store_name_manual").val(storeName_);
  		var dutoDiv = $("<div class='auto_out on'>");
  		var strong = $("<strong>").html(storeName_);
  		dutoDiv.append(strong);
  		$("#auto").append(dutoDiv);
  	}
  	if(ordertype!=null&&ordertype!=""){
  	  if(ordertype=='0'){
	  	  $("#cbClick").prop("checked", true);
	  	  $("#maxbClick").prop("checked", true);
	  	  $("#thirdbClick").prop("checked", true);
  	  }else if(ordertype=='1'){
  	  	  $("#cbClick").prop("checked", true);
	  	  $("#maxbClick").prop("checked", false);
	  	  $("#thirdbClick").prop("checked", false);
  	  }else if(ordertype=='2'){
  	  	  $("#cbClick").prop("checked", false);
	  	  $("#maxbClick").prop("checked", true);
	  	  $("#thirdbClick").prop("checked", false);
  	  }else if(ordertype=='2'){
  	  	  $("#cbClick").prop("checked", false);
	  	  $("#maxbClick").prop("checked", false);
	  	  $("#thirdbClick").prop("checked", true);
  	  }
  	}
  }