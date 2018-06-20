//格式化日期格式
Date.prototype.format=function(fmt) {        
    var o = {        
    "M+" : this.getMonth()+1, //月份        
    "d+" : this.getDate(), //日        
    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时        
    "H+" : this.getHours(), //小时        
    "m+" : this.getMinutes(), //分        
    "s+" : this.getSeconds(), //秒        
    "q+" : Math.floor((this.getMonth()+3)/3), //季度        
    "S" : this.getMilliseconds() //毫秒        
    };        
    var week = {        
    "0" : "\u65e5",        
    "1" : "\u4e00",        
    "2" : "\u4e8c",        
    "3" : "\u4e09",        
    "4" : "\u56db",        
    "5" : "\u4e94",        
    "6" : "\u516d"       
    };        
    if(/(y+)/.test(fmt)){        
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));        
    }        
    if(/(E+)/.test(fmt)){        
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "\u661f\u671f" : "\u5468") : "")+week[this.getDay()+""]);        
    }        
    for(var k in o){        
        if(new RegExp("("+ k +")").test(fmt)){        
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));        
        }        
    }        
    return fmt;        
} 


/*var myChart1 = echarts.init(document.getElementById('main1'));
var myChart2 = echarts.init(document.getElementById('main2'));
var myChart3 = echarts.init(document.getElementById('main3'));
var myChart4 = echarts.init(document.getElementById('main4'));
var myChart5 = echarts.init(document.getElementById('main5'));
var myChart6 = echarts.init(document.getElementById('main6'));
var myChartCard = echarts.init(document.getElementById('maincard'));
var myChart7 = echarts.init(document.getElementById('main7'));
var myChartEmp = echarts.init(document.getElementById('mainemp'));
var myChartDianzhang = echarts.init(document.getElementById('maindianzhang'));
var myChartEmpDianzhang = echarts.init(document.getElementById('mainempdianzhang'));
var myChartGuoanxia = echarts.init(document.getElementById('mainguoanxia'));
var myChartEmpGuoanxia = echarts.init(document.getElementById('mainempguoanxia'));
var myChartZhuanyuan = echarts.init(document.getElementById('mainzhuanyuan'));
var myChartEmpZhuanyuan = echarts.init(document.getElementById('mainempzhuanyuan'));
//var myChart8 = echarts.init(document.getElementById('main8'));
var myChart9 = echarts.init(document.getElementById('main9'));
var myChart10 = echarts.init(document.getElementById('main10'));
var myChartMainstoreType = echarts.init(document.getElementById('mainstoreType'));
var myChartMainCooperative = echarts.init(document.getElementById('mainCooperative'));
var myChart11 = echarts.init(document.getElementById('main11'));
var myChart12 = echarts.init(document.getElementById('main12'));
var myChart13 = echarts.init(document.getElementById('main13'));
var myChart14 = echarts.init(document.getElementById('main14'));
var myChart15 = echarts.init(document.getElementById('main15'));
var myChart16 = echarts.init(document.getElementById('main16'));
var myChart17 = echarts.init(document.getElementById('main17'));
var myChart18 = echarts.init(document.getElementById('main18'));
var myChart19 = echarts.init(document.getElementById('main19'));
var myChart20 = echarts.init(document.getElementById('main20'));
var myChart21 = echarts.init(document.getElementById('main21'));
var myChart22 = echarts.init(document.getElementById('main22'));
var myChart23 = echarts.init(document.getElementById('main23'));
var myChart24 = echarts.init(document.getElementById('main24'));
var myChart26 = echarts.init(document.getElementById('main26'));*/
var myChart18 = echarts.init(document.getElementById('main18'));
var myChart19 = echarts.init(document.getElementById('main19'));
var myChart20 = echarts.init(document.getElementById('main20'));
var myChart21 = echarts.init(document.getElementById('main21'));
var myChart24 = echarts.init(document.getElementById('main24'));
var myChart26 = echarts.init(document.getElementById('main26'));

//x轴近六周时间轴
var six_week_data_array = new Array();
var self_obj={};
var cooperative_obj = {};

//城市总的行政区县、已有门店开业的区县数量
/*var data = [
  {name: '海门', value: 9},
  {name: '鄂尔多斯', value: 12},
  {name: '招远', value: 12},
  {name: '舟山', value: 12},
  {name: '齐齐哈尔', value: 14},
  {name: '盐城', value: 15},
  {name: '赤峰', value: 16},
  {name: '青岛', value: 18},
  {name: '乳山', value: 18},
  {name: '金昌', value: 19},
  {name: '泉州', value: 21},
  {name: '莱西', value: 21},
  {name: '日照', value: 21},
  {name: '胶南', value: 22},
  {name: '南通', value: 23},
  {name: '拉萨', value: 24},
  {name: '云浮', value: 24},
  {name: '梅州', value: 25},
  {name: '文登', value: 25},
  {name: '上海', value: 25},
  {name: '攀枝花', value: 25},
  {name: '威海', value: 25},
  {name: '承德', value: 25},
  {name: '厦门', value: 26},
  {name: '汕尾', value: 26},
  {name: '潮州', value: 26},
  {name: '丹东', value: 27},
  {name: '太仓', value: 27},
  {name: '曲靖', value: 27},
  {name: '烟台', value: 28},
  {name: '福州', value: 29},
  {name: '瓦房店', value: 30},
  {name: '即墨', value: 30},
  {name: '抚顺', value: 31},
  {name: '玉溪', value: 31},
  {name: '张家口', value: 31},
  {name: '阳泉', value: 31},
  {name: '莱州', value: 32},
  {name: '湖州', value: 32},
  {name: '汕头', value: 32},
  {name: '昆山', value: 33},
  {name: '宁波', value: 33},
  {name: '湛江', value: 33},
  {name: '揭阳', value: 34},
  {name: '荣成', value: 34},
  {name: '连云港', value: 35},
  {name: '葫芦岛', value: 35},
  {name: '常熟', value: 36},
  {name: '东莞', value: 36},
  {name: '河源', value: 36},
  {name: '淮安', value: 36},
  {name: '泰州', value: 36},
  {name: '南宁', value: 37},
  {name: '营口', value: 37},
  {name: '惠州', value: 37},
  {name: '江阴', value: 37},
  {name: '蓬莱', value: 37},
  {name: '韶关', value: 38},
  {name: '嘉峪关', value: 38},
  {name: '广州', value: 38},
  {name: '延安', value: 38},
  {name: '太原', value: 39},
  {name: '清远', value: 39},
  {name: '中山', value: 39},
  {name: '昆明', value: 39},
  {name: '寿光', value: 40},
  {name: '盘锦', value: 40},
  {name: '长治', value: 41},
  {name: '深圳', value: 41},
  {name: '珠海', value: 42},
  {name: '宿迁', value: 43},
  {name: '咸阳', value: 43},
  {name: '铜川', value: 44},
  {name: '平度', value: 44},
  {name: '佛山', value: 44},
  {name: '海口', value: 44},
  {name: '江门', value: 45},
  {name: '章丘', value: 45},
  {name: '肇庆', value: 46},
  {name: '大连', value: 47},
  {name: '临汾', value: 47},
  {name: '吴江', value: 47},
  {name: '石嘴山', value: 49},
  {name: '沈阳', value: 50},
  {name: '苏州', value: 50},
  {name: '茂名', value: 50},
  {name: '嘉兴', value: 51},
  {name: '长春', value: 51},
  {name: '胶州', value: 52},
  {name: '银川', value: 52},
  {name: '张家港', value: 52},
  {name: '三门峡', value: 53},
  {name: '锦州', value: 54},
  {name: '南昌', value: 54},
  {name: '柳州', value: 54},
  {name: '三亚', value: 54},
  {name: '自贡', value: 56},
  {name: '吉林', value: 56},
  {name: '阳江', value: 57},
  {name: '泸州', value: 57},
  {name: '西宁', value: 57},
  {name: '宜宾', value: 58},
  {name: '呼和浩特', value: 58},
  {name: '成都', value: 58},
  {name: '大同', value: 58},
  {name: '镇江', value: 59},
  {name: '桂林', value: 59},
  {name: '张家界', value: 59},
  {name: '宜兴', value: 59},
  {name: '北海', value: 60},
  {name: '西安', value: 61},
  {name: '金坛', value: 62},
  {name: '东营', value: 62},
  {name: '牡丹江', value: 63},
  {name: '遵义', value: 63},
  {name: '绍兴', value: 63},
  {name: '扬州', value: 64},
  {name: '常州', value: 64},
  {name: '潍坊', value: 65},
  {name: '重庆', value: 66},
  {name: '台州', value: 67},
  {name: '南京', value: 67},
  {name: '滨州', value: 70},
  {name: '贵阳', value: 71},
  {name: '无锡', value: 71},
  {name: '本溪', value: 71},
  {name: '克拉玛依', value: 72},
  {name: '渭南', value: 72},
  {name: '马鞍山', value: 72},
  {name: '宝鸡', value: 72},
  {name: '焦作', value: 75},
  {name: '句容', value: 75},
  {name: '北京', value: 79},
  {name: '徐州', value: 79},
  {name: '衡水', value: 80},
  {name: '包头', value: 80},
  {name: '绵阳', value: 80},
  {name: '乌鲁木齐', value: 84},
  {name: '枣庄', value: 84},
  {name: '杭州', value: 84},
  {name: '淄博', value: 85},
  {name: '鞍山', value: 86},
  {name: '溧阳', value: 86},
  {name: '库尔勒', value: 86},
  {name: '安阳', value: 90},
  {name: '开封', value: 90},
  {name: '济南', value: 92},
  {name: '德阳', value: 93},
  {name: '温州', value: 95},
  {name: '九江', value: 96},
  {name: '邯郸', value: 98},
  {name: '临安', value: 99},
  {name: '兰州', value: 99},
  {name: '沧州', value: 100},
  {name: '临沂', value: 103},
  {name: '南充', value: 104},
  {name: '天津', value: 105},
  {name: '富阳', value: 106},
  {name: '泰安', value: 112},
  {name: '诸暨', value: 112},
  {name: '郑州', value: 113},
  {name: '哈尔滨', value: 114},
  {name: '聊城', value: 116},
  {name: '芜湖', value: 117},
  {name: '唐山', value: 119},
  {name: '平顶山', value: 119},
  {name: '邢台', value: 119},
  {name: '德州', value: 120},
  {name: '济宁', value: 120},
  {name: '荆州', value: 127},
  {name: '宜昌', value: 130},
  {name: '义乌', value: 132},
  {name: '丽水', value: 133},
  {name: '洛阳', value: 134},
  {name: '秦皇岛', value: 136},
  {name: '株洲', value: 143},
  {name: '石家庄', value: 147},
  {name: '莱芜', value: 148},
  {name: '常德', value: 152},
  {name: '保定', value: 153},
  {name: '湘潭', value: 154},
  {name: '金华', value: 157},
  {name: '岳阳', value: 169},
  {name: '长沙', value: 175},
  {name: '衢州', value: 177},
  {name: '廊坊', value: 193},
  {name: '菏泽', value: 194},
  {name: '合肥', value: 229},
  {name: '武汉', value: 273},
  {name: '大庆', value: 279}
];

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
  '大庆':[125.03,46.58]
};

var convertData = function (data) {
  var res = [];
  for (var i = 0; i < data.length; i++) {
    var geoCoord = geoCoordMap[data[i].name];
    if (geoCoord) {
      res.push({
        name: data[i].name,
        value: geoCoord.concat(data[i].value)
      });
    }
  }
  return res;
};

function renderItem(params, api) {
  var coords = [
    [116.7,39.53],
    [103.73,36.03],
    [112.91,27.87],
    [120.65,28.01],
    [119.57,39.95]
  ];
  var points = [];
  for (var i = 0; i < coords.length; i++) {
    points.push(api.coord(coords[i]));
  }
  var color = api.visual('color');

  return {
    type: 'polygon',
    shape: {
      points: echarts.graphic.clipPointsByRect(points, {
        x: params.coordSys.x,
        y: params.coordSys.y,
        width: params.coordSys.width,
        height: params.coordSys.height
      })
    },
    style: api.style({
      fill: color,
      stroke: echarts.color.lift(color)
    })
  };
}

option1 = {
  backgroundColor: '#404a59',
  title: {
    text: '全国主要城市空气质量',
    subtext: 'data from PM25.in',
    sublink: 'http://www.pm25.in',
    left: 'center',
    textStyle: {
      color: '#fff'
    }
  },
  tooltip : {
    trigger: 'item'
  },
  bmap: {
    center: [104.114129, 37.550339],
    zoom: 5,
    roam: true,
    mapStyle: {
      styleJson: [
        {
          "featureType": "water",
          "elementType": "all",
          "stylers": {
            "color": "#044161"
          }
        },
        {
          "featureType": "land",
          "elementType": "all",
          "stylers": {
            "color": "#004981"
          }
        },
        {
          "featureType": "boundary",
          "elementType": "geometry",
          "stylers": {
            "color": "#064f85"
          }
        },
        {
          "featureType": "railway",
          "elementType": "all",
          "stylers": {
            "visibility": "off"
          }
        },
        {
          "featureType": "highway",
          "elementType": "geometry",
          "stylers": {
            "color": "#004981"
          }
        },
        {
          "featureType": "highway",
          "elementType": "geometry.fill",
          "stylers": {
            "color": "#005b96",
            "lightness": 1
          }
        },
        {
          "featureType": "highway",
          "elementType": "labels",
          "stylers": {
            "visibility": "off"
          }
        },
        {
          "featureType": "arterial",
          "elementType": "geometry",
          "stylers": {
            "color": "#004981"
          }
        },
        {
          "featureType": "arterial",
          "elementType": "geometry.fill",
          "stylers": {
            "color": "#00508b"
          }
        },
        {
          "featureType": "poi",
          "elementType": "all",
          "stylers": {
            "visibility": "off"
          }
        },
        {
          "featureType": "green",
          "elementType": "all",
          "stylers": {
            "color": "#056197",
            "visibility": "off"
          }
        },
        {
          "featureType": "subway",
          "elementType": "all",
          "stylers": {
            "visibility": "off"
          }
        },
        {
          "featureType": "manmade",
          "elementType": "all",
          "stylers": {
            "visibility": "off"
          }
        },
        {
          "featureType": "local",
          "elementType": "all",
          "stylers": {
            "visibility": "off"
          }
        },
        {
          "featureType": "arterial",
          "elementType": "labels",
          "stylers": {
            "visibility": "off"
          }
        },
        {
          "featureType": "boundary",
          "elementType": "geometry.fill",
          "stylers": {
            "color": "#029fd4"
          }
        },
        {
          "featureType": "building",
          "elementType": "all",
          "stylers": {
            "color": "#1a5787"
          }
        },
        {
          "featureType": "label",
          "elementType": "all",
          "stylers": {
            "visibility": "off"
          }
        }
      ]
    }
  },
  series : [
    {
      name: 'pm2.5',
      type: 'scatter',
      coordinateSystem: 'bmap',
      data: convertData(data),
      symbolSize: function (val) {
        return val[2] / 10;
      },
      label: {
        normal: {
          formatter: '{b}',
          position: 'right',
          show: false
        },
        emphasis: {
          show: true
        }
      },
      itemStyle: {
        normal: {
          color: '#fff'
        }
      }
    },
    {
      name: 'Top 5',
      type: 'effectScatter',
      coordinateSystem: 'bmap',
      data: convertData(data.sort(function (a, b) {
        return b.value - a.value;
      }).slice(0, 6)),
      symbolSize: function (val) {
        return val[2] / 10;
      },
      showEffectOn: 'emphasis',
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
          color: '#f4e925',
          shadowBlur: 10,
          shadowColor: '#333'
        }
      },
      zlevel: 1
    },
    {
      type: 'custom',
      coordinateSystem: 'bmap',
      renderItem: renderItem,
      itemStyle: {
        normal: {
          opacity: 0.5
        }
      },
      animation: false,
      silent: true,
      data: [0],
      z: -10
    }
  ]
};*/
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
		    '乌兰察布':[113.125152, 41.035476],
		    '黔东南苗族侗族自治州':[107.982859, 26.583442]
		  };
var max = 480, min = 9; // todo
var maxSize4Pin = 100, minSize4Pin = 20;
var convertData = function (data) {
  var res = [];
  for (var i = 0; i < data.length; i++) {
    var geoCoord = geoCoordMap[data[i].name];
    if (geoCoord) {
      res.push({
        name: data[i].name,
        value: geoCoord.concat(data[i].value)
      });
    }
  }
  return res;
};
option1 = {
		    title: {
		      text: '城市分布',
		      x: 'center',
		      textStyle: {
		        color: '#424242'
		      }
		    },
		    /*tooltip: {
		      trigger: 'item',
		      formatter: function (params) {
		        if(typeof(params.value)[2] == "undefined"){
		          return params.name + ' : ' + params.value;
		        }else{
		          return params.name + ' : ' + params.value[2];
		        }
		      }
		    },
		    visualMap: {
		     min: 0,
		     max: 1500,
		     left: '-100%',
		     top: 'bottom',
		     text: ['High', 'Low'],
		     seriesIndex: [1],
		     inRange: {
		     color: ['#e0ffff', '#006edd']
		     },
		     calculable: false
		     },*/
		    visualMap: {
		      show: false,
		      min: 0,
		      max: 500,
		      left: 'left',
		      top: 'bottom',
		      text: ['高', '低'], // 文本，默认为数值文本
		      calculable: true,
		      seriesIndex: [1],
		      inRange: {
		        // color: ['#3B5077', '#031525'] // 蓝黑
		        // color: ['#ffc0cb', '#800080'] // 红紫
		        // color: ['#3C3B3F', '#605C3C'] // 黑绿
		        //color: ['#0f0c29', '#302b63', '#24243e'] // 黑紫黑
		        //color: ['#23074d', '#cc5333'] // 紫红
		        // color: ['#00467F', '#A5CC82'] // 蓝绿
		        // color: ['#1488CC', '#2B32B2'] // 浅蓝
		        // color: ['#00467F', '#A5CC82'] // 蓝绿
		        // color: ['#00467F', '#A5CC82'] // 蓝绿
		        // color: ['#00467F', '#A5CC82'] // 蓝绿
		        // color: ['#00467F', '#A5CC82'] // 蓝绿
				color: ['#F00','#BE77FF'] // 蓝绿
		      }
		    },
		    geo: {
		      map: 'china',
		      roam: true,
		      // scaleLimit:{
		      //   max:'1.2',
		      //   min:'0.7'
		      // },
		      label: {
		        normal: {
		          show: false,
		        },
		        emphasis: {
		          show: false,
		        }
		      },
		      roam:false,
		      itemStyle: {
		        normal: {
		          borderColor: 'rgba(0, 0, 0, 0.2)'
		        },
		        emphasis: {
		          areaColor: null,
		          shadowOffsetX: 0,
		          shadowOffsetY: 0,
		          shadowBlur: 20,
		          borderWidth: 0,
		          shadowColor: 'rgba(0, 0, 0, 0.5)'
		        }
		      }
		    },
		    series : [
		      {
		        name: '17年已进入城市',
		        type: 'scatter',
		        coordinateSystem: 'geo',
		        data: [],
		        symbolSize: function (val) {
		          return val[2] / 10;
		        },
		        label: {
		          normal: {
		            formatter: '{b}',
		            position: 'right',
		            show: true
		          },
		          emphasis: {
		            show: true
		          }
		        },
		        itemStyle: {
		          normal: {
		            color: '#f00'
		          }
		        }
		      },
		      {
		        type: 'map',
		        map: 'china',
		        geoIndex: 0,
		        aspectScale: 0.75, //长宽比
		        showLegendSymbol: false, // 存在legend时显示
		        label: {
		          normal: {
		            show: false
		          },
		          emphasis: {
		            show: false,
		            textStyle: {
		              color: '#FFF'
		            }
		          }
		        },
		        roam: true,
		        itemStyle: {
		          normal: {
		            areaColor: '#031525',
		            borderColor: '#FFFFFF',
		          },
		          emphasis: {
		            areaColor: '#2B91B7'
		          }
		        },
		        animation: false,
		        data: []
		      },
		      {
		        name: '18年新进入城市',
		        type: 'effectScatter',
		        coordinateSystem: 'geo',
		        data: [],
		        symbolSize: function (val) {
		          return val[2] / 10;
		        },
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
		            color: '#FF8C00',
		            shadowBlur: 10,
		            shadowColor: '#05C3F9'
		          }
		        },
		        zlevel: 1
		      },

		    ]
		  };

		  //城市总的街道、已有门店开业的街道数量
		  /* var dataBeast=[660, 841, 521, 820, 578, 727, 598, 792];
		  var dataBeauty=[541, 513, 792, 701, 660, 729, 782, 660];
		  var xAxisData=['北京','上海','天津','广州','深圳','贵阳','昆明','沈阳']; */
		  var dataTotal=function(){
		    var data=[];
		    for(var i=0;i<dataBeauty.length;i++){
		      data.push(dataBeauty[i]+dataBeast[i]);
		    }
		    return data;
		  }
		  option9 = {
				    //color:['#019aba','#7a201f','#11565d'],
				    title: {
				      text: '已开城市覆盖情况(区域)',
				      textStyle: {
				        x: 'center',
				        textStyle: {color: '#424242'}
				      },
				    },
				    legend:{
				      right:'0',
				      textStyle: {
				        color: '#666',
				        fontSize: 12,
				      },
				      data:['已布点区域','未布点区域','覆盖率'],
				    },
				    tooltip:{
				      show:true,
				      trigger: 'axis',
				      axisPointer: {
				        type:'cross',
				        crossStyle:{color:'#ddd',},
				      },
				    },
				    grid:{
				      left:5,
				      right:5,
				      top:60,
				      bottom:50,
				      containLabel:true,
				    },
				    xAxis: {
				      show:true,
				      axisLabel:{
				        interval:0,
				        rotate:-30,
				        margin: 30,
				        textStyle:{
				          color:'#666',
				          align: 'center'
				        },
				      },
				      axisTick:{
				        alignWithLabel:true,
				        lineStyle:{
				          color:'#666',

				        },
				      },
				      data:[],
				    },
				    yAxis: [
				      {
				        type:'value',
				        nameTextStyle:{
				          color:'#666',
				        },
				        axisLabel:{
				          textStyle:{
				            color:'#666',
				          },
				        },
				        axisTick:{
				          alignWithLabel:true,
				          lineStyle:{
				            color:'#666',

				          },
				        },
				        splitLine:{
				          show:false,
				        },
				      },
				      {
				        type:'value',
				        name: '百分比',
				        min:0,
				        nameTextStyle:{
				          color:'#666',
				        },
				        axisLabel:{
				          textStyle:{
				            color:'#666',
				          },
				        },
				        axisTick:{
				          alignWithLabel:true,
				          lineStyle:{
				            color:'#666',

				          },
				        },
				        splitLine:{
				          show:false,
				        },
				      },
				    ],
				    series: [
				      {
				        type: 'bar',
				        name:'已布点区域',
				        stack:'布点区域',
				        yAxisIndex:0,
				        data:[],
				        label: {
				          normal: {
				            show:false,
				            position: 'insideTop',
				            offset:[0,-5],
				            textStyle:{
				              color:'#fff',
				            },
				          },
				          emphasis: {
				            textStyle:{
				              color:'#fff',
				            },
				          },
				        },
				      },
				      {
				        type: 'bar',
				        name:'未布点区域',
				        stack:'布点区域',
				        yAxisIndex:0,
				        data:[],
				        label: {
				          normal: {
				            show:false,
				            position: 'insideTop',
				            offset:[0,-5],
				            textStyle:{
				              color:'#fff',
				            },
				          },
				          emphasis: {
				            textStyle:{
				              color:'#fff',
				            },
				          },
				        },
				      },
				      {
				        type: 'line',
				        name:'覆盖率',
				        yAxisIndex:1,
				        data:[],
				        label: {
				          normal: {
				            show:true,
				            position: 'insideTop',
				            offset: [0,-30],
				            textStyle:{
				              color:'#000',
				            },
				          },
				          emphasis: {
				            textStyle:{
				              color:'#fff',
				            },
				          },
				        },
				        // symbol:'image://../imgs/point1.png',
				        symbolSize:8,
				        itemStyle: {
				          normal: {
				             "color": "#01B3D7",
				            barBorderRadius: 0,
				            label: {
				              show:true,
				              position: "top",
				              formatter: '{c}% '
				            }
				          }
				        },
				        lineStyle: {
				          normal: {
				            color: '#01B3D7',
				            width: 1,

				          },
				        },
				      },
				    ]
				  };

				  //城市总的街道、已有门店开业的街道数量
				   option15 = {
				    color:['#01949b','#eba954','#e71010'],
				    title: {
				      text: '已开城市覆盖情况(街道)',
				      textStyle: {
				        x: 'center',
				        textStyle: {color: '#424242'}
				      },
				    },
				    legend:{
				      right:'0',
				      textStyle: {
				        color: '#666',
				        fontSize: 12,
				      },
				      data:['已布点街道','未布点街道','覆盖率'],
				    },
				    tooltip:{
				      show:true,
				      trigger: 'axis',
				      axisPointer: {
				        type:'cross',
				        crossStyle:{color:'#ddd',},
				      },
				    },
				    grid:{
				      left:5,
				      right:5,
				      top:60,
				      bottom:50,
				      containLabel:true,
				    },
				    xAxis: {
				      show:true,
				      axisLabel:{
				        interval:0,
				        rotate:-30,
				        margin: 30,
				        textStyle:{
				          color:'#666',
				          align: 'center'
				        },
				      },
				      axisTick:{
				        alignWithLabel:true,
				        lineStyle:{
				          color:'#666',

				        },
				      },
				      data:[],
				    },
				    yAxis: [
				      {
				        type:'value',
				        nameTextStyle:{
				          color:'#666',
				        },
				        axisLabel:{
				          textStyle:{
				            color:'#666',
				          },
				        },
				        axisTick:{
				          alignWithLabel:true,
				          lineStyle:{
				            color:'#666',

				          },
				        },
				        splitLine:{
				          show:false,
				        },
				      },
				      {
				        type:'value',
				        name: '百分比',
				        min:0,
				        nameTextStyle:{
				          color:'#666',
				        },
				        axisLabel:{
				          textStyle:{
				            color:'#666',
				          },
				        },
				        axisTick:{
				          alignWithLabel:true,
				          lineStyle:{
				            color:'#666',

				          },
				        },
				        splitLine:{
				          show:false,
				        },
				      },
				    ],
				    series: [
				      {
				        type: 'bar',
				        name:'已布点街道',
				        stack:'布点区域',
				        yAxisIndex:0,
				        data:[],
				        label: {
				          normal: {
				            show:false,
				            position: 'insideTop',
				            offset:[0,-5],
				            textStyle:{
				              color:'#fff',
				            },
				          },
				          emphasis: {
				            textStyle:{
				              color:'#fff',
				            },
				          },
				        },
				      },
				      {
				        type: 'bar',
				        name:'未布点街道',
				        stack:'布点区域',
				        yAxisIndex:0,
				        data:[],
				        label: {
				          normal: {
				            show:false,
				            position: 'insideTop',
				            offset:[0,-5],
				            textStyle:{
				              color:'#fff',
				            },
				          },
				          emphasis: {
				            textStyle:{
				              color:'#fff',
				            },
				          },
				        },
				      },
				      {
				        type: 'line',
				        name:'覆盖率',
				        yAxisIndex:1,
				        data:[],
				        label: {
				          normal: {
				            show:true,
				            position: 'insideTop',
				            offset: [0,-30],
				            textStyle:{
				              color:'#000',
				            },
				          },
				          emphasis: {
				            textStyle:{
				              color:'#fff',
				            },
				          },
				        },
				        // symbol:'image://../imgs/point1.png',
				        symbolSize:8,
				        itemStyle: {
				          normal: {
				             "color": "#e71010",
				            barBorderRadius: 0,
				            label: {
				              show:true,
				              position: "top",
				              formatter: '{c}% '
				            }
				          }
				        },
				        lineStyle: {
				          normal: {
				            color: '#e71010',
				            width: 0.8,

				          },
				        },
				      },
				    ]
				  };

				  //网络业态分布--城市公司门店数量
				  option2 = {
				    title: {text: '城市公司门店数量',textAlign:'left',textStyle:{fontSize:"16",fontWeight:"normal"}},
				    tooltip: {
				      trigger: 'axis',
				      axisPointer: {
				        type: 'shadow',
				        textStyle: {color: '#fff'}
				      },
				    },
				    color:["#317fcc","#805acc"],
				    grid: {
				    	borderWidth: 0,
					      right:40,
					      top: 35,
					      bottom: 50,
				      textStyle: {color: '#fff'}
				    },
				    legend: {
				      right:0,
				      data: ["自营店","合作店"]
				    },
				    calculable: true,
				    xAxis: [{
				      type: 'category',
				      axisTick: {show: true},
				      axisLine: {
				        lineStyle: {
				          color: '#90979c'
				        }
				      },
				      axisLabel: {
				        interval: 0,
				        rotate:-20,
				        margin: 15,
				      },
				      data: [],
				    }],
				    yAxis: [{
				      type: 'value',
				      splitLine: {show: true},
				      axisLine: {
				        lineStyle: {color: '#90979c'}
				      },
				      axisTick: {show: false},
				      axisLabel: {interval: 0,},
				      splitArea: {show: false},
				    }],
				    series: [{
				        name:'自营店',
				        type:'bar',
				        data:[],
				        itemStyle: {
				        	emphasis: { barBorderRadius: 30 },
				        	normal: {
				        		 barBorderRadius: [3,3,3,3],
				                label: {
				                  show: true,
				                  formatter: function(p) {
				                    return p.value > 0 ? (p.value) : '';
				                  }
				                }
				              }
				        },
				        smooth: true
				      },
				      {
				        name:'合作店',
				        type:'bar',
				        data:[],
				        itemStyle: {
				        	normal: {
				        		barBorderRadius: [3,3,3,3],
				                label: {
				                  show: true,
				                  formatter: function(p) {
				                    return p.value > 0 ? (p.value) : '';
				                  }
				                }
				              }
				        },
				        smooth: true
				      }]
				  }

				  //网络业态分布--全国门店数量
				  option10 = {
						  title: {text: '全国门店数量',textAlign:'left',textStyle:{fontSize:"16",fontWeight:"normal"}},
						    tooltip: {
						      trigger: 'axis',
						      axisPointer: {
						        type: 'shadow',
						        textStyle: {color: '#fff'}
						      },
						    },
						    color:['#c27cbf'],
						    grid: {
						      borderWidth: 0,
						      right:30,
						      top: 35,
						      bottom: 50,
						      textStyle: {color: '#fff'}
						    },
						    legend: {
						      top: '30',
						      left:"left",
						      y:"8%",
						      itemWidth:18,
						      itemHeight:12,
						      textStyle: {color: '#90979c',fontSize:14},
						    },
						    calculable: true,
						    xAxis: [{
						      type: 'category',
						      axisLine: {
						        lineStyle: {
						          color: '#90979c'
						        }
						      },
						      splitLine: {
						        show: false
						      },
						      axisTick: {
						        show: false
						      },
						      splitArea: {
						        show: false
						      },
						      axisLabel: {
						        interval: 0,
						        rotate:-20,
						        margin: 15,
						      },
						      data: ["校园店","生活中心店","街道月店","经营星店","药店","独立微超","合作店","合作点","前置仓","城市仓"],
						    }],
						    yAxis: [{
						      type: 'value',
						      splitLine: {show: true},
						      boundaryGap: [0, 0.1],
						      axisLine: {
						        lineStyle: {color: '#90979c'}
						      },
						      axisTick: {show: false},
						      axisLabel: {interval: 0,},
						      splitArea: {show: false},
						    }],
						    series: [{
						        name:'门店数量',
						        type:'bar',
						        data:[],
						        itemStyle: {
						        	normal: {
						        		barBorderRadius: [3,3,3,3],
						                label: {
						                  show: true,
						                  formatter: function(p) {
						                    return p.value > 0 ? (p.value) : '';
						                  }
						                }
						              }
						        },
						        smooth: true
						      },]
				  }
				  
				//网络业态分布--全国门店数量
				  optionMainstoreType = {
						  title: {text: '各城市自营月店数量',textAlign:'left',textStyle:{fontSize:"16",fontWeight:"normal"}},
						    tooltip: {
						      trigger: 'axis',
						      axisPointer: {
						        type: 'shadow',
						        textStyle: {color: '#fff'}
						      },
						    },
						    color:['#df416d'],
						    grid: {
						      borderWidth: 0,
						      right:40,
						      top: 40,
						      bottom: 50,
						      textStyle: {color: '#fff'}
						    },
						    legend: {
						      top: '30',
						      left:"left",
						      y:"8%",
						      itemWidth:18,
						      itemHeight:12,
						      textStyle: {color: '#90979c',fontSize:14},
						    },
						    calculable: true,
						    xAxis: [{
						      type: 'category',
						      axisLine: {
						        lineStyle: {
						          color: '#90979c'
						        }
						      },
						      splitLine: {
						        show: false
						      },
						      axisTick: {
						        show: false
						      },
						      splitArea: {
						        show: false
						      },
						      axisLabel: {
						        interval: 0,
						        rotate:-20,
						        margin: 15,
						      },
						      data: [],
						    }],
						    yAxis: [{
						      type: 'value',
						      splitLine: {show: true},
						      axisLine: {
						        lineStyle: {color: '#90979c'}
						      },
						      axisTick: {show: false},
						      axisLabel: {interval: 0,},
						      splitArea: {show: false},
						    }],
						    series: [{
						        name:'门店数量',
						        type:'bar',
						        data:[],
						        itemStyle: {
						        	normal: {
						        		barBorderRadius: [3,3,3,3],
						                label: {
						                  show: true,
						                  formatter: function(p) {
						                    return p.value > 0 ? (p.value) : '';
						                  }
						                }
						              }
						        },
						        smooth: true
						      },]
				  }
				  
				//网络业态分布--全国门店数量
				  optionMainCooperative = {
						  title: {text: '合作店业态分布',textAlign:'left',textStyle:{fontSize:"16",fontWeight:"normal"}},
						    tooltip: {
						      trigger: 'axis',
						      axisPointer: {
						        type: 'shadow',
						        textStyle: {color: '#fff'}
						      },
						    },
						    color:["#488850","#d2802b","#393b99"],
						    legend: {
						        data:['数码连锁店','超市连锁店','广电营业厅'],
						        right:0
						      },
						    grid: {
						      borderWidth: 0,
						      right:30,
						      top: 40,
						      bottom: 50,
						      textStyle: {color: '#fff'}
						    },
						    calculable: true,
						    xAxis: [{
						      type: 'category',
						      axisLine: {
						        lineStyle: {
						          color: '#90979c'
						        }
						      },
						      splitLine: {
						        show: false
						      },
						      axisTick: {
						        show: false
						      },
						      splitArea: {
						        show: false
						      },
						      axisLabel: {
						        interval: 0,
						        rotate:-20,
						        margin: 15,
						      },
						      data: [],
						    }],
						    yAxis: [{
						      type: 'value',
						      splitLine: {show: true},
						      axisLine: {
						        lineStyle: {color: '#90979c'}
						      },
						      axisTick: {show: false},
						      axisLabel: {interval: 0,},
						      splitArea: {show: false},
						    }],
						    series: [{
						        name:'数码连锁店',
						        type:'bar',
						        stack: 'sum',
						        barWidth : 50,
						        data:[],
						        itemStyle: {
						        	normal: {
						        		barBorderRadius: [3,3,3,3],
						                label: {
						                  show: true,
						                  formatter: function(p) {
						                    return p.value > 0 ? (p.value) : '';
						                  }
						                }
						              }
						        },
						        smooth: true
						      },
						      {
							        name:'超市连锁店',
							        type:'bar',
							        stack: 'sum',
							        barWidth : 50,
							        data:[],
							        itemStyle: {
							        	normal: {
							        		barBorderRadius: [3,3,3,3],
							                label: {
							                  show: true,
							                  formatter: function(p) {
							                    return p.value > 0 ? (p.value) : '';
							                  }
							                }
							              }
							        },
							        smooth: true
							      },
							      {
								        name:'广电营业厅',
								        type:'bar',
								        stack: 'sum',
								        barWidth : 50,
								        data:[],
								        itemStyle: {
								        	normal: {
								        		barBorderRadius: [3,3,3,3],
								                label: {
								                  show: true,
								                  formatter: function(p) {
								                    return p.value > 0 ? (p.value) : '';
								                  }
								                }
								              }
								        },
								        smooth: true
								      },]
				  }


//网路拓展趋势--近一年
var option3 = {
  title: {text: '近一年情况走势',textAlign:'left',textStyle:{fontSize:"16",fontWeight:"normal"}},
  tooltip : {trigger: 'axis',},
  legend: {
    data:['总量','自营店','合作店'],
    right:0,
  },
  grid: {
    top: '12%',
    left: '1%',
    right: '10%',
    bottom: '3%',
    containLabel: true
  },
  xAxis : [
    {
      type : 'category',
      boundaryGap : false,
      data : [],
      splitLine: {show: false},
      axisTick:{show: false},
    }
  ],
  yAxis : [
    {
      type : 'value',
      splitLine: {show: false},
      axisTick:{show: false},
    }
  ],
  series : [
    {
      name:'总量',
      type:'line',
      areaStyle: {normal: {color:'#EEC8C6'}},
      data:[],
      itemStyle: {normal: {
          label: {
              show: true,
              formatter: function(p) {
                return p.value > 0 ? (p.value) : '';
              }
            }
          }},
      smooth: true
    },
    {
      name:'自营店',
      type:'line',
      areaStyle: {normal: {color:'#3E5361'}},
      data:[],
      itemStyle: {
      	normal: {
              label: {
                show: true,
                formatter: function(p) {
                  return p.value > 0 ? (p.value) : '';
                }
              }
            }
      },
      smooth: true
    },
    {
      name:'合作店',
      type:'line',
      areaStyle: {normal: {color:'#91BDC2'}},
      data:[],
      itemStyle: {
      	normal: {
              label: {
                show: true,
                formatter: function(p) {
                  return p.value > 0 ? (p.value) : '';
                }
              }
            }
      },
      smooth: true
    }
  ]
};

//网路拓展趋势--近6周
var option11 = {
  title: {text: '近六周情况走势',textAlign:'left',textStyle:{fontSize:"16",fontWeight:"normal"}},
  tooltip : {trigger: 'axis',},
  legend: {
    data:['总量','自营店','合作店'],
    right:0,
  },
  grid: {
    top: '12%',
    left: '1%',
    right: '7%',
    bottom: '3%',
    containLabel: true
  },
  xAxis : [
    {
      type : 'category',
      boundaryGap : false,
      data : six_week_data_array,
      splitLine: {show: false},
      axisTick:{show: false},
    }
  ],
  yAxis : [
    {
      type : 'value',
      splitLine: {show: false},
      axisTick:{show: false},
    }
  ],
  series : [
    {
      name:'总量',
      type:'line',
      areaStyle: {normal: {color:'#EEC8C6'}},
      data:[],
      itemStyle: {normal: {
          label: {
              show: true,
              formatter: function(p) {
                return p.value > 0 ? (p.value) : '';
              }
            }
          }},
      smooth: true
    },
    {
      name:'自营店',
      type:'line',
      areaStyle: {normal: {color:'#3E5361'}},
      data:[],
      itemStyle: {
      	normal: {
              label: {
                show: true,
                formatter: function(p) {
                  return p.value > 0 ? (p.value) : '';
                }
              }
            }
      },
      smooth: true
    },
    {
      name:'合作店',
      type:'line',
      areaStyle: {normal: {color:'#91BDC2'}},
      data:[],
      itemStyle: {
      	normal: {
              label: {
                show: true,
                formatter: function(p) {
                  return p.value > 0 ? (p.value) : '';
                }
              }
            }
      },
      smooth: true
    }
  ]
};

//已上会通过数量、累计签约数量
var xData = function() {
  var data = [];
  for (var i = 1; i < 13; i++) {
    data.push(i + "月份");
  }
  return data;
}();

option4 = {
  title: {
    text: '城市公司选址动态',
    textAlign:'left',textStyle:{fontSize:"16",fontWeight:"normal"}
  },
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'shadow',
      textStyle: {
        color: '#fff'
      }
    },
  },
  grid: {
    borderWidth: 0,
    top: 105,
    bottom: 42,
    textStyle: {
      color: '#fff'
    }
  },
  legend: {
    top: '8%',
    right: '5%',
    textStyle: {
      color: '#90979c',
    },
    data: ['已上会通过数量','累计签约数量']
  },
  calculable: true,
  xAxis: [{
    type: 'category',
    axisLine: {
      lineStyle: {
        color: '#90979c'
      }
    },
    splitLine: {
      show: false
    },
    axisTick: {
      show: false
    },
    splitArea: {
      show: false
    },
    axisLabel: {
      interval: 0,
    },
    data: [],
  }],
  yAxis: [{
    type: 'value',
    splitLine: {
      show: true
    },
    axisLine: {
      lineStyle: {
        color: '#90979c'
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
//   dataZoom: [
//            /*   {
//     show: true,
//     height: 20,
//     xAxisIndex: [0],
//     bottom: 20,
//     start: 20,
//     end: 100,

//   },  */
//   {
//     type: 'inside',
//     show: true,
//     height: 15,
//     start: 1,
//     end: 35
//   }],
  series: [{
    name: '已上会通过数量',
    type: 'bar',
    stack: '总量',
    barMaxWidth: 35,
    barGap: '10%',
    itemStyle: {
      normal: {
        color: '#5b9bd5',
        label: {
          show: true,
          textStyle: {
            color: '#000'
          },
          position: 'insideTop',
          formatter: function(p) {
            return p.value > 0 ? (p.value) : '';
          }
        }
      }
    },
    data: [],
  },
    {
      name: '累计签约数量',
      type: 'bar',
      stack: '总量',
      itemStyle: {
        normal: {
          color: '#ed7d31',
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
      data: []
    }
  ]
}

//近六周的选址签约数量
 var title = "近六周的选址签约数量";
/* var legendData= ['投诉量','解决量','投诉解决率'];
var serieData = [];
var metaDate = [
  [120, 140, 100, 120, 300, 230, 130, 170,140, 120, 300, 230],
  [200, 120, 300, 200, 170, 300, 200, 180,200, 190, 300, 200],
  [100,200, 140, 300, 200, 180, 100, 300, 230, 130, 100 ,300,]
] */
/* for(var v=0; v < legendData.length ; v++){
  var serie = {
    name:legendData[v],
    type: 'line',
    symbol:"circle",
    symbolSize:10,
    data: metaDate[v]
  };
  serieData.push(serie)
} */
//var colors = ["#036BC8","#4A95FF","#5EBEFC","#2EF7F3","#FFFFFF"];
var option5 = {
  title : {text: title,textAlign:'left',textStyle:{fontSize:"16",fontWeight:"normal"}},
  legend: {
    show:true,left:"right",data:[],y:"8%",
    itemWidth:18,itemHeight:12,textStyle:{fontSize:14},
  },
  //color:colors,
  grid: {left: '2%',top:"30%",bottom: "7%",right:"5%",containLabel: true},
  tooltip : { trigger: 'axis',axisPointer : { type : 'shadow'}},
  xAxis: [
    {
      type: 'category',
      axisLine: { show: true,lineStyle:{ color:'#6173A3' }},
      axisLabel:{interval:0,textStyle:{color:'#9ea7c4',fontSize:12} },
      axisTick : {show: false},
      data:six_week_data_array,
    },
  ],
  yAxis: [
    {
      axisTick : {show: false},
      splitLine: {show:true},
      axisLabel:{textStyle:{color:'#9ea7c4',fontSize:14} },
      axisLine: { show: true,lineStyle:{ color:'#6173A3'}},
    },
  ],
  series:[]
};

//统计“筹备中”门店数量
option6 = {
  title : {text: '筹备中门店数量',textAlign:'left',textStyle:{fontSize:"16",fontWeight:"normal"}},
  color: ['#3398DB'],
  tooltip : {
    trigger: 'axis',
    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
      type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
    }
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '5%',
    top: '20%',
    containLabel: true
  },
  xAxis : [
    {
      type : 'category',
      data : [],
      axisLine: { show: true,lineStyle:{ color:'#6173A3' }},
      axisLabel:{interval:0,textStyle:{color:'#9ea7c4',fontSize:12} },
      axisTick : {show: false},
    }
  ],
  yAxis : [
    {
  	  name:'门店数量',
  	  boundaryGap: [0, 0.2],
      axisTick : {show: false},
      splitLine: {show:true},
      axisLabel:{textStyle:{color:'#9ea7c4',fontSize:14} },
      axisLine: { show: true,lineStyle:{ color:'#6173A3'}},
    }
  ],
  series : [
    {
      name:'门店数量',
      type:'bar',
      barWidth: '40%',
      data:[]
    },

  ],
  label: {
    normal: {
      show: true,
      position: 'top',
      formatter: '{c}'
    }
  },
  itemStyle: {
    normal: {

      color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
        offset: 0,
        color: 'rgba(17, 168,171, 1)'
      }, {
        offset: 1,
        color: 'rgba(17, 168,171, 0.1)'
      }]),
      shadowColor: 'rgba(0, 0, 0, 0.1)',
      shadowBlur: 10
    }
  }
};
//证照
optionCard = {
		    title : {text: '门店证照统计',textAlign:'left',textStyle:{fontSize:"16",fontWeight:"normal"}},
		    tooltip: {
		        trigger: 'axis',
		        axisPointer: {
		            type: 'shadow'
		        }
		    },
		    legend: {
		    	 show:true,left:"right", data: ['门店数量', '双证齐全', '仅营业照', '无证照'],y:"8%",
		         itemWidth:18,itemHeight:12,textStyle:{fontSize:14},
		       
		    },
		    grid: {
		        left: '3%',
		        right: '5.6%',
		        bottom: '3%',
		        containLabel: true
		      },
		    /* toolbox: {
		        show: true,
		        orient: 'vertical',
		        left: 'right',
		        top: 'center',
		        feature: {
		            mark: {show: true},
		            dataView: {show: true, readOnly: false},
		            magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
		            restore: {show: true},
		            saveAsImage: {show: true}
		        }
		    }, */
		    calculable: true,
		    xAxis: [
		        {
		            type: 'category',
		            axisTick: {show: true},
		            data: []
		        }
		    ],
		    yAxis: [
		        {
		            type: 'value'
		        }
		    ],
		    series: [
		        {
		            name: '门店数量',
		            type: 'bar',
		            barGap: 0,
		            itemStyle: {
			        	normal: {
			        		 barBorderRadius: [3,3,3,3],
			        		 label: {
					                normal: {
					                  show: true,
					                  position: 'top',
					                  formatter: '{c}'
					                }
					         },
			              }
			        },
		            data: []
		        },
		        {
		            name: '双证齐全',
		            type: 'bar',
		            itemStyle: {
			        	normal: {
			        		 barBorderRadius: [3,3,3,3],
			        		 label: {
					                normal: {
					                  show: true,
					                  position: 'top',
					                  formatter: '{c}'
					                }
					         },
			              }
			        },
		            data: []
		        },
		        {
		            name: '仅营业照',
		            type: 'bar',
		            itemStyle: {
			        	normal: {
			        		 barBorderRadius: [3,3,3,3],
			        		 label: {
					                normal: {
					                  show: true,
					                  position: 'top',
					                  formatter: '{c}'
					                }
					         },
			              }
			        },
		            data: []
		        },
		        {
		            name: '无证照',
		            type: 'bar',
		            itemStyle: {
			        	normal: {
			        		 barBorderRadius: [3,3,3,3],
			        		 label: {
					                normal: {
					                  show: true,
					                  position: 'top',
					                  formatter: '{c}'
					                }
					         },
			              }
			        },
		            data: []
		        }
		    ]
		};

//人员动态
option7 = {
  title: {
    text: '近六周总人员动态',textAlign:'left',textStyle:{fontSize:"16",fontWeight:"normal"},
  },
  tooltip: {trigger: 'axis'},
  legend: {
    data:['离职数量','入职数量'],left:"right",y:"8%",
    itemWidth:18,itemHeight:12,textStyle:{fontSize:14},
  },
  grid: {
    left: '3%',
    right: '5.6%',
    bottom: '3%',
    top:'20%',
    containLabel: true
  },
  xAxis: [{
    type: 'category',
    boundaryGap: false,
    data: six_week_data_array
  }],
  yAxis: 
  	{
          axisTick : {show: false},
          splitLine: {show:true},
          axisLabel:{textStyle:{color:'#9ea7c4',fontSize:14} },
          axisLine: { show: true,lineStyle:{ color:'#6173A3'}},
        },
  series: [
    {
      name:'离职数量',
      type:'line',
      data:[],
      itemStyle: {
          normal: {
        	  color : new echarts.graphic.LinearGradient(0, 0, 1, 1, [{
	            offset: 0,
	            color: "#e99998"
	          }, {
	            offset: 1,
	            color: '#c5403d'
	          }]),
            label: {
              show: true,
              formatter: function(p) {
                return p.value > 0 ? (p.value) : '';
              }
            }
          }
      }
    },
    {
      name:'入职数量',
      type:'line',
      data:[],
      itemStyle: {
          normal: {
        	  color : new echarts.graphic.LinearGradient(0, 0, 1, 1, [{
  	            offset: 0,
  	            color: "#646a6d"
  	          }, {
  	            offset: 1,
  	            color: '#256c9d'
  	          }]),
            label: {
              show: true,
              formatter: function(p) {
                return p.value > 0 ? (p.value) : '';
              }
            }
          }
      }
    }
  ]
};
	//人员动态：总数
optionEmp = {
  title: {
    text: '近六周总人数统计',textAlign:'left',textStyle:{fontSize:"16",fontWeight:"normal"}
  },
  legend: {
      data:['总人数'],left:"right",y:"8%",
      itemWidth:18,itemHeight:12,textStyle:{fontSize:14},
    },
    color:["#8780a9"],
  tooltip: {trigger: 'axis'},
  grid: {
    left: '3%',
    right: '5.6%',
    bottom: '3%',
    top:'20%',
    containLabel: true
  },
  xAxis: [{
    type: 'category',
    boundaryGap: false,
    data: six_week_data_array
  }],
  yAxis: {
    type: 'value',
    boundaryGap: [0, 0.1],
    splitLine: {show:true},
    //interval: 10,
  },
  series: [
    {
      name:'总人数',
      type:'line',
      stack: '总量',
      data:[],
      itemStyle: {
          normal: {
	    	  color : new echarts.graphic.LinearGradient(0, 0, 1, 1, [{
	            offset: 0,
	            color: "#8780a9"
	          }, {
	            offset: 1,
	            color: 'rgba(138,43,226,0.8)'
	          }]),
	          label: {
	              show: true,
	              formatter: function(p) {
	                return p.value > 0 ? (p.value) : '';
	              }
	          }
          }
      }
    },
  ]
};

//人员动态
optiondianzhang = {
  title: {
    text: '近六周店长动态',textAlign:'left',textStyle:{fontSize:"16",fontWeight:"normal"},
  },
  color:["#e78888","#555a76"],
  tooltip: {trigger: 'axis'},
  legend: {
    data:['离职数量','入职数量'],left:"right",y:"8%",
    itemWidth:18,itemHeight:12,textStyle:{fontSize:14},
  },
  grid: {
    left: '3%',
    right: '5.6%',
    bottom: '3%',
    top:'20%',
    containLabel: true
  },
  xAxis: [{
    type: 'category',
    boundaryGap: false,
    data: six_week_data_array
  }],
  yAxis: 
  	{
          axisTick : {show: false},
          splitLine: {show:true},
          axisLabel:{textStyle:{color:'#9ea7c4',fontSize:14} },
          axisLine: { show: true,lineStyle:{ color:'#6173A3'}},
        },
  series: [
    {
      name:'离职数量',
      type:'line',
      data:[],
      itemStyle: {
          normal: {
            label: {
              show: true,
              formatter: function(p) {
                return p.value > 0 ? (p.value) : '';
              }
            }
          }
      }
    },
    {
      name:'入职数量',
      type:'line',
      data:[],
      itemStyle: {
          normal: {
            label: {
              show: true,
              formatter: function(p) {
                return p.value > 0 ? (p.value) : '';
              }
            }
          }
      }
    }
  ]
};
	//人员动态：总数
optionEmpdianzhang = {
  title: {
    text: '近六周店长数统计',textAlign:'left',textStyle:{fontSize:"16",fontWeight:"normal"}
  },
  legend: {
      data:['总人数'],left:"right",y:"8%",
      itemWidth:18,itemHeight:12,textStyle:{fontSize:14},
    },
    color:["#036BC8"],
  tooltip: {trigger: 'axis'},
  grid: {
    left: '3%',
    right: '5.6%',
    bottom: '3%',
    top:'20%',
    containLabel: true
  },
  xAxis: [{
    type: 'category',
    boundaryGap: false,
    data: six_week_data_array
  }],
  yAxis: {
    type: 'value',
    boundaryGap: [0, 0.1],
    splitLine: {show:true},
    //interval: 10,
  },
  series: [
    {
      name:'总人数',
      type:'line',
      stack: '总量',
      data:[],
      itemStyle: {
          normal: {
        	  color : new echarts.graphic.LinearGradient(0, 0, 1, 1, [{
  	            offset: 0,
  	            color: "#8780a9"
  	          }, {
  	            offset: 1,
  	            color: '#036BC8'
  	          }]),
            label: {
              show: true,
              formatter: function(p) {
                return p.value > 0 ? (p.value) : '';
              }
            }
          }
      }
    },
  ]
};

//人员动态
optionguoanxia = {
  title: {
    text: '近六周国安侠动态',textAlign:'left',textStyle:{fontSize:"16",fontWeight:"normal"},
  },
  color:["#d06e92","#639d92"],
  tooltip: {trigger: 'axis'},
  legend: {
    data:['离职数量','入职数量'],left:"right",y:"8%",
    itemWidth:18,itemHeight:12,textStyle:{fontSize:14},
  },
  grid: {
    left: '3%',
    right: '5.6%',
    bottom: '3%',
    top:'20%',
    containLabel: true
  },
  xAxis: [{
    type: 'category',
    boundaryGap: false,
    data: six_week_data_array
  }],
  yAxis: 
  	{
          axisTick : {show: false},
          splitLine: {show:true},
          axisLabel:{textStyle:{color:'#9ea7c4',fontSize:14} },
          axisLine: { show: true,lineStyle:{ color:'#6173A3'}},
        },
  series: [
    {
      name:'离职数量',
      type:'line',
      data:[],
      itemStyle: {
          normal: {
            label: {
              show: true,
              formatter: function(p) {
                return p.value > 0 ? (p.value) : '';
              }
            }
          }
      }
    },
    {
      name:'入职数量',
      type:'line',
      data:[],
      itemStyle: {
          normal: {
            label: {
              show: true,
              formatter: function(p) {
                return p.value > 0 ? (p.value) : '';
              }
            }
          }
      }
    }
  ]
};
	//人员动态：总数
optionEmpguoanxia = {
  title: {
    text: '近六周国安侠数统计',textAlign:'left',textStyle:{fontSize:"16",fontWeight:"normal"}
  },
  legend: {
      data:['总人数'],left:"right",y:"8%",
      itemWidth:18,itemHeight:12,textStyle:{fontSize:14},
    },
    color:["#036BC8"],
  tooltip: {trigger: 'axis'},
  grid: {
    left: '3%',
    right: '5.6%',
    bottom: '3%',
    top:'20%',
    containLabel: true
  },
  xAxis: [{
    type: 'category',
    boundaryGap: false,
    data: six_week_data_array
  }],
  yAxis: {
    type: 'value',
    boundaryGap: [0, 0.1],
    splitLine: {show:true},
    //interval: 10,
  },
  series: [
    {
      name:'总人数',
      type:'line',
      stack: '总量',
      data:[],
      itemStyle: {
          normal: {
        	  color : new echarts.graphic.LinearGradient(0, 0, 1, 1, [{
  	            offset: 0,
  	            color: "#036BC8"
  	          }, {
  	            offset: 1,
  	            color: 'rgba(138,43,226,0.8)'
  	          }]),
            label: {
              show: true,
              formatter: function(p) {
                return p.value > 0 ? (p.value) : '';
              }
            }
          }
      }
    },
  ]
};

//人员动态
optionzhuanyuan = {
  title: {
    text: '近六周专员动态',textAlign:'left',textStyle:{fontSize:"16",fontWeight:"normal"},
  },
  tooltip: {trigger: 'axis'},
  legend: {
    data:['离职数量','入职数量'],left:"right",y:"8%",
    itemWidth:18,itemHeight:12,textStyle:{fontSize:14},
  },
  grid: {
    left: '3%',
    right: '5.6%',
    bottom: '3%',
    top:'20%',
    containLabel: true
  },
  xAxis: [{
    type: 'category',
    boundaryGap: false,
    data: six_week_data_array
  }],
  yAxis: 
  	{
          axisTick : {show: false},
          splitLine: {show:true},
          axisLabel:{textStyle:{color:'#9ea7c4',fontSize:14} },
          axisLine: { show: true,lineStyle:{ color:'#6173A3'}},
        },
  series: [
    {
      name:'离职数量',
      type:'line',
      data:[],
      itemStyle: {
          normal: {
            label: {
              show: true,
              formatter: function(p) {
                return p.value > 0 ? (p.value) : '';
              }
            }
          }
      }
    },
    {
      name:'入职数量',
      type:'line',
      data:[],
      itemStyle: {
          normal: {
            label: {
              show: true,
              formatter: function(p) {
                return p.value > 0 ? (p.value) : '';
              }
            }
          }
      }
    }
  ]
};
	//人员动态：总数
optionEmpzhuanyuan = {
  title: {
    text: '近六周专员数统计',textAlign:'left',textStyle:{fontSize:"16",fontWeight:"normal"}
  },
  legend: {
      data:['总人数'],left:"right",y:"8%",
      itemWidth:18,itemHeight:12,textStyle:{fontSize:14},
    },
    color:["#91bee9"],
  tooltip: {trigger: 'axis'},
  grid: {
    left: '3%',
    right: '5.6%',
    bottom: '3%',
    top:'20%',
    containLabel: true
  },
  xAxis: [{
    type: 'category',
    boundaryGap: false,
    data: six_week_data_array
  }],
  yAxis: {
    type: 'value',
    boundaryGap: [0, 0.1],
    splitLine: {show:true},
    //interval: 10,
  },
  series: [
    {
      name:'总人数',
      type:'line',
      stack: '总量',
      data:[],
      itemStyle: {
          normal: {
        	  color : new echarts.graphic.LinearGradient(0, 0, 1, 1, [{
  	            offset: 0,
  	            color: "#91bee9"
  	          }, {
  	            offset: 1,
  	            color: 'rgba(138,43,226,0.8)'
  	          }]),
            label: {
              show: true,
              formatter: function(p) {
                return p.value > 0 ? (p.value) : '';
              }
            }
          }
      }
    },
  ]
};

//人员动态
optionzongguan = {
  title: {
    text: '近六周专员动态',textAlign:'left',textStyle:{fontSize:"16",fontWeight:"normal"},
  },
  tooltip: {trigger: 'axis'},
  legend: {
    data:['离职数量','入职数量'],left:"right",y:"8%",
    itemWidth:18,itemHeight:12,textStyle:{fontSize:14},
  },
  grid: {
    left: '3%',
    right: '5.6%',
    bottom: '3%',
    top:'20%',
    containLabel: true
  },
  xAxis: [{
    type: 'category',
    boundaryGap: false,
    data: six_week_data_array
  }],
  yAxis: 
  	{
          axisTick : {show: false},
          splitLine: {show:true},
          axisLabel:{textStyle:{color:'#9ea7c4',fontSize:14} },
          axisLine: { show: true,lineStyle:{ color:'#6173A3'}},
        },
  series: [
    {
      name:'离职数量',
      type:'line',
      data:[],
      itemStyle: {
          normal: {
            label: {
              show: true,
              formatter: function(p) {
                return p.value > 0 ? (p.value) : '';
              }
            }
          }
      }
    },
    {
      name:'入职数量',
      type:'line',
      data:[],
      itemStyle: {
          normal: {
            label: {
              show: true,
              formatter: function(p) {
                return p.value > 0 ? (p.value) : '';
              }
            }
          }
      }
    }
  ]
};
	//人员动态：总数
optionEmpzongguan = {
  title: {
    text: '近六周专员数统计',textAlign:'left',textStyle:{fontSize:"16",fontWeight:"normal"}
  },
  legend: {
      data:['总人数'],left:"right",y:"8%",
      itemWidth:18,itemHeight:12,textStyle:{fontSize:14},
    },
    color:["#91bee9"],
  tooltip: {trigger: 'axis'},
  grid: {
    left: '3%',
    right: '5.6%',
    bottom: '3%',
    top:'20%',
    containLabel: true
  },
  xAxis: [{
    type: 'category',
    boundaryGap: false,
    data: six_week_data_array
  }],
  yAxis: {
    type: 'value',
    boundaryGap: [0, 0.1],
    splitLine: {show:true},
    //interval: 10,
  },
  series: [
    {
      name:'总人数',
      type:'line',
      stack: '总量',
      data:[],
      itemStyle: {
          normal: {
        	  color : new echarts.graphic.LinearGradient(0, 0, 1, 1, [{
  	            offset: 0,
  	            color: "#91bee9"
  	          }, {
  	            offset: 1,
  	            color: 'rgba(138,43,226,0.8)'
  	          }]),
            label: {
              show: true,
              formatter: function(p) {
                return p.value > 0 ? (p.value) : '';
              }
            }
          }
      }
    },
  ]
};


//人员编制动态
/* option8 = {
  title: {
    text: '人员编制动态',textAlign:'left',textStyle:{fontSize:"16",fontWeight:"normal"}
  },
  tooltip: {trigger: 'axis'},
  legend: {
    data: ['线上人员', '门店人员（含店长）', '专员']
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  xAxis: [{
    type: 'category',
    data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
  }],
  yAxis: [{
    type: 'value',
    //name: '投诉举报数',
    axisLabel: {
      formatter: '{value}'
    }
  }],
  series: [{
    name: '线上人员',
    type: 'bar',
    itemStyle:{
      normal:{color:'#01949B'},
    },
    markPoint : {
      data : [
        {type : 'max', name : '最大值'},
        {type : 'min', name : '最小值'}
      ]
    },
     //markLine : {
      //data : [
       // {type : 'average', name : '平均值'}
      //]
    },
    data: [2031, 1793, 3640, 2593, 4377,3201, 2275, 3289, 3356,2859,4244,3945]
  }, {
    name: '门店人员（含店长）',
    type: 'bar',
    itemStyle:{
      normal:{color:'#EBA954'},
    },
    markPoint : {
      data : [
        {type : 'max', name : '最大值'},
        {type : 'min', name : '最小值'}
      ]
    },
    //markLine : {
     // data : [
      //  {type : 'average', name : '平均值'}
      //]
    //},
    data: [1043, 1456, 1900, 1200, 2100,1870, 980, 1569, 1130, 1490,2300, 2210]
  }, {
    name: '专员',
    type: 'bar',
    itemStyle:{
      normal:{color:'#C23531'},
    },
    markPoint : {
      data : [
        {type : 'max', name : '最大值'},
        {type : 'min', name : '最小值'}
      ]
    },
    // markLine : {
    //  data : [
      //  {type : 'average', name : '平均值'}
     // ]
    //},
    data: [787, 571, 999, 341, 231,812, 735, 231,322,712,1230, 870]
  }]
}; */

//18年门店拓展进度
option12 = {
  title: {
    text: '近六周门店拓展进度',
    textAlign: 'left',
    textStyle:{fontSize:"16",fontWeight:"normal"}
  },
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      lineStyle: {
        color: '#ddd'
      }
    },
    /* textStyle: {
      color: '#7588E4',
    }, */
    extraCssText: 'box-shadow: 0 0 5px rgba(0,0,0,0.3)'
  },
  legend: {
    right: '3%',
    data: ['自营店','合作店']
  },
  grid: {
    left: '2%',
    right: '3.5%',
    bottom: '3%',
    containLabel: true
  },
  xAxis: [{
    type: 'category',
    //data: ['00:00','2:00','4:00','6:00','8:00','10:00','12:00','14:00','16:00','18:00','20:00',"22:00"],
    data:six_week_data_array,
    boundaryGap: false,
    splitLine: {
      show: true,
      interval: 'auto',
      lineStyle: {color: '#D4DFF5'}
    },
    axisTick: {show: false},
    axisLine: {lineStyle: {color: '#609ee9'}},
    axisLabel: {textStyle: {fontSize: 14}}
  }],
  yAxis: [{
    type: 'value',
    name: '门店数量',
    boundaryGap: [0, 0.1],
    splitLine: {lineStyle: {color: ['#D4DFF5']}},
    axisTick: {show: false},
    axisLine: {lineStyle: {color: '#609ee9'}},
    axisLabel: {textStyle: {fontSize: 14}}
    
  }],
  series: [{
    name: '自营店',
    type: 'line',
    smooth: true,
    showSymbol: false,
    symbol: 'circle',
    symbolSize: 6,
    data: [],
    areaStyle: {
      normal: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
          offset: 0,
          color: 'rgba(199, 237, 250,0.5)'
        }, {
          offset: 1,
          color: 'rgba(199, 237, 250,0.2)'
        }], false)
      }
    },
    itemStyle: {
      normal: {
        label: {
          show: true,
          position: 'top'
        },
        color: '#f7b851'
      }
    },
    lineStyle: {
      normal: {width: 3}
    }
  }, {
    name: '合作店',
    type: 'line',
    smooth: true,
    showSymbol: false,
    symbol: 'circle',
    symbolSize: 6,
    data: [],
    areaStyle: {
      normal: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
          offset: 0,
          color: 'rgba(216, 244, 247,1)'
        }, {
          offset: 1,
          color: 'rgba(216, 244, 247,1)'
        }], false)
      }
    },
    itemStyle: {
      normal: {
      	label: {
              show: true,
              position: 'top'
            },
        color: '#58c8da'
      }
    },
    lineStyle: {
      normal: {width: 3}
    }
  }]
};

//18年自营店拓展进度
option13 = {
  title: {
    text: '18年自营店拓展进度',textAlign:'left',textStyle:{fontSize:"16",fontWeight:"normal"}
  },
  color:["#f72929","#529ea8","#9bbcc8"],
  tooltip : {
      trigger: 'axis',
      axisPointer : {            // 坐标轴指示器，坐标轴触发有效
          type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
      },
      formatter: function (params)
      {
    	  var value_param = 0;
    	  if(params[2].value == 0){
    		  value_param = typeof(self_obj[params[2].name]) != 'undefined' ? self_obj[params[2].name] : params[2].value
    	  }
          return params[2].name + '<br/>'
          		 + params[0].marker+params[0].seriesName + ' : ' +  params[0].value + '%<br/>'
                 + params[2].marker+params[2].seriesName + ' : ' + (params[1].value + (params[2].value == 0 ? value_param : params[2].value)) + '<br/>'
                 + params[1].marker+params[1].seriesName + ' : ' + params[1].value;
      }
  },
  grid: {
    left: '1%',
    right: '2%',
    bottom: '3%',
    containLabel: true
  },
  legend: {
    right:'0',
    data: ['达标率', '任务目标', '已完成']
  },
  xAxis: [{
    type: 'category',
    axisTick: {
      alignWithLabel: true
    },
    data:[],
    //data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
  }],
  yAxis: [{
    type: 'value',
    name: '门店数量',
    boundaryGap: [0, 0.1],
    min: 0,
    position: 'left',
    axisLabel: {
      formatter: '{value} '
    },
    splitLine: {
        show: false
    }
  }, {
    type: 'value',
    name: '百分比',
    min: 0,
    position: 'right',
    axisLabel: {
      formatter: '{value} '
    },
    splitLine: {
        show: false
    }
  }],
  series: [{
    name: '达标率',
    type: 'line',
    label: {
      normal: {
        show: true,
        position: 'top',
      }
    },
    yAxisIndex: 1,
    lineStyle: {
      normal: {
        width: 1.5,
        //shadowColor: 'rgba(0,0,0,0.4)',
        shadowBlur: 10,
        shadowOffsetY: 10
      }
    },
    itemStyle: {
        normal:{
          label: {
            show: true,
            position: 'top',
            formatter: '{c}% ',
          },
        }

      },
    data: []
  }, 
    {
    name: '已完成',
    type: 'bar',
    stack: 'sum',
    barCategoryGap: '50%',
    yAxisIndex: 0,
    itemStyle: {
        normal: {
            color: '#9bbcc8',
            barBorderColor: '#9bbcc8'
        }
    },
    label: {
      normal: {
        show: true,
        position:"insideTop"
      }
    },
    data: []
  },
  {
      name: '任务目标',
      type: 'bar',
      stack: 'sum',
      yAxisIndex: 0,
      itemStyle: {
          normal: {
        	  //barBorderRadius: [5,5,5,5]
          }
      },
      label: {
        normal: {
          show: true,
          position: 'top',
          formatter: function (params) {
        	  var value_param = 0;
        	  if(params.value == 0){
        		  value_param = typeof(self_obj[params.name]) != 'undefined' ? self_obj[params.name] : params.value;
        	  }
              for (var i = 0, l = option13.xAxis[0].data.length; i < l; i++) {
                  if (option13.xAxis[0].data[i] == params.name) {
                      return option13.series[1].data[i] + (params.value == 0 ? value_param : params.value);
                  }
              }
          },
        }
      },
      data: []
    },]
};

//18年合作店拓展进度
option14 = {
  title: {
    text: '18年合作店拓展进度',textAlign:'left',textStyle:{fontSize:"16",fontWeight:"normal"}
  },
  color:["#f72929","#529ea8","#9bbcc8"],
  tooltip : {
      trigger: 'axis',
      axisPointer : {            // 坐标轴指示器，坐标轴触发有效
          type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
      },
      formatter: function (params)
      {
    	  var value_param = 0;
		  if(params[2].value == 0){
			  value_param = typeof(cooperative_obj[params[2].name]) != 'undefined' ? cooperative_obj[params[2].name] : params[2].value
		  }
          return params[2].name + '<br/>'
          		 + params[0].marker+params[0].seriesName + ' : ' +  params[0].value + '%<br/>'
                 + params[2].marker+params[2].seriesName + ' : ' + (params[1].value + (params[2].value == 0 ? value_param : params[2].value)) + '<br/>'
                 + params[1].marker+params[1].seriesName + ' : ' + params[1].value;
      }
  },
  grid: {
    left: '1%',
    right: '2%',
    bottom: '3%',
    containLabel: true
  },
  legend: {
    right:'0',
    data: ['达标率', '任务目标', '已完成']
  },
  xAxis: [{
    type: 'category',
    axisTick: {
      alignWithLabel: true
    },
    data:[]
    //data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
  }],
  yAxis: [{
    type: 'value',
    name: '门店数量',
    boundaryGap: [0, 0.1],
    min: 0,
    position: 'left',
    axisLabel: {
      formatter: '{value} '
    },
    splitLine: {
        show: false
    }
  }, {
    type: 'value',
    boundaryGap: [0, 0.1],
    name: '百分比',
    min: 0,
    position: 'right',
    axisLabel: {
      formatter: '{value} '
    },
    splitLine: {
        show: false
    }
  }],
  series: [{
    name: '达标率',
    type: 'line',
    label: {
      normal: {
        show: true,
        position: 'top',
      }
    },
    lineStyle: {
      normal: {
        width: 1.5,
        shadowColor: 'rgba(0,0,0,0.4)',
        shadowBlur: 10,
        shadowOffsetY: 10
      }
    },
    yAxisIndex: 1,
    itemStyle: {
        normal:{
          label: {
            show: true,
            position: 'top',
            formatter: '{c}% ',
          },
        }

      },
    data: []
  }, {
      name: '已完成',
      stack: 'sum',
      barCategoryGap: '50%',
      type: 'bar',
      yAxisIndex: 0,
      itemStyle: {
          normal: {
              color: '#9bbcc8',
              barBorderColor: '#9bbcc8'
          }
      },
      label: {
        normal: {
          show: true,
          position: 'insideTop'
        }
      },
      data: []
    }, {
        name: '任务目标',
        stack: 'sum',
        type: 'bar',
        yAxisIndex: 0,
        label: {
          normal: {
            show: true,
            position: 'top',
            formatter: function (params) {
            	var value_param = 0;
	          	  if(params.value == 0){
	          		  value_param = typeof(cooperative_obj[params.name]) != 'undefined' ? cooperative_obj[params.name] : params.value;
	          	  }
                for (var i = 0, l = option14.xAxis[0].data.length; i < l; i++) {
                    if (option14.xAxis[0].data[i] == params.name) {
                        return option14.series[1].data[i] + (params.value == 0 ? value_param : params.value);
                    }
                }
            },
          }
        },
        data: []
      }]
};

//全国门店划片情况
option16 = {
  title: {
    text: '全国划片门店数',textAlign:'left',textStyle:{fontSize:"16"}
  },
  tooltip : {
    trigger: 'axis',
    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
      type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
    }
  },
  grid: {
    left: '1%',
    right: '8%',
    bottom: '3%',
    top: '18%',
    containLabel: true
  },
  xAxis: [{
    //data: ['北京\n1333', '广州\n110', '贵阳\n314', '昆明\n208', '上海\n652', '沈阳\n90', '天津\40', '长沙\n1'],
	  data:[],
    axisTick: {show: false},
    interval:0
  }],
  yAxis: {
    axisTick: {show: false}
  },
  series: [{
    type: 'bar',
    barWidth: 18,
    itemStyle:{
      normal:{
        color:new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
          offset: 0,
          color: '#00b0ff'
        }, {
          offset: 0.8,
          color: '#7052f4'
        }], false)
      }
    },
    //data: [125, 10, 24, 21, 52, 9, 12, 40,1]
    data:[]
  }]
};

//全国划片片区数
option17 = {
  title: {
    text: '全国划片片区数',textAlign:'left',textStyle:{fontSize:"16"}
  },
  tooltip : {
    trigger: 'axis',
    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
      type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
    }
  },
  grid: {
    left: '1%',
    right: '8%',
    bottom: '3%',
    top: '18%',
    containLabel: true
  },
  xAxis: [{
	  data:[],
    //data: ['北京\n1333', '广州\n110', '贵阳\n314', '昆明\n208', '上海\n652', '沈阳\n90', '天津\40', '长沙\n1'],
    axisTick: {show: false}
  }],
  yAxis: {
    axisTick: {show: false}
  },
  series: [{
    type: 'bar',
    barWidth: 18,
    itemStyle:{
      normal:{
        color:new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
          offset: 0,
          color: '#00b0ff'
        }, {
          offset: 0.8,
          color: '#7052f4'
        }], false)
      }
    },
    //data: [125, 10, 24, 21, 52, 9, 12, 40,1]
    data:[]
  }]
};

//活动区域覆盖
option18 = {
  title: {
    text: '活动区域覆盖',textAlign:'left',textStyle:{fontSize:"16"}
  },
  tooltip : {
    trigger: 'axis',
    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
      type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
    }
  },
  grid: {
    left: '1%',
    right: '1%',
    bottom: '3%',
    top: '18%',
    containLabel: true
  },
  xAxis: [{
    //data: ['\n1333', '文娱类活动\n110', '公益类活动\n314', '志愿者活动\n208', '活动小计\n652'],
	data:[],
    axisTick: {show: false},
    axisLabel: {
        interval: 0,
        rotate:40
      },
  }],
  yAxis: {
    axisTick: {show: false}
  },
  series: [{
    type: 'bar',
    barWidth: 18,
    label: {
      normal: {
        show: true,
        position: 'top'
      }
    },
    itemStyle:{
      normal:{
        color:new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
          offset: 0,
          color: '#01949b'
        }, {
          offset: 0.8,
          color: '#55dee5'
        }], false)
      }
    },
    //data: [125, 10, 24, 21, 52]
    data:[]
  }]
};

//单场活动人数
option19 = {
  title: {
    text: '单场活动人数',textAlign:'left',textStyle:{fontSize:"16"}
  },
  tooltip : {trigger: 'axis',},
  grid: {
    left: '1%',
    right: '1%',
    bottom: '3%',
    top: '18%',
    containLabel: true
  },
  xAxis: [{
    type: 'category',
    data:[],
    //data: ['北京\n36', '上海\n8', '天津\n47', '广州\n202', '深圳\n239', '云南\n40', '贵州\n70', '辽宁\n81', '全国\n44'],
    axisTick: {show: false},
  	axisLabel: {
      interval: 0,
      rotate:40
      /*formatter:function(value,index)  
      {  
          debugger  
          if (index % 2 != 0) {  
              return '\n\n' + value;  
          }  
          else {  
              return value;  
          }  
      }  */
    },
  }],
  yAxis: {
    type: 'value',
    axisTick: {show: false}
  },
  series: [{
    //data: [820, 932, 901, 934, 1290, 1330, 1320, 820, 932],
	data:[],
    type: 'line',
    label: {
      normal: {
        show: true,
        position: 'top'
      }
    },
  }]
};

//店均社区关键人数量
option20 = {
  title: {
    text: '店均社区关键人数量',textAlign:'left',textStyle:{fontSize:"16"}
  },
  color: ['#3398DB'],
  tooltip : {
    trigger: 'axis',
    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
      type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
    }
  },
  grid: {
    left: '1%',
    right: '2%',
    bottom: '3%',
    top: '14%',
    containLabel: true
  },
  xAxis : [
    {
      type : 'category',
      //data: ['北京\n36', '上海\n8', '天津\n47', '广州\n202', '深圳\n239', '云南\n40', '贵州\n70', '辽宁\n81', '合计\n44'],
      data:[],
      axisTick: {
        alignWithLabel: true
      }
    }
  ],
  yAxis : [
    {
      axisTick: {
        alignWithLabel: true
      }
    }
  ],
  series : [
    {
      type:'bar',
      barWidth: '40%',
      data:[]
    },

  ],
};

//社区关键人构成分析
option21 = {
  title: {
    text: '社区关键人构成分析',textAlign:'left',textStyle:{fontSize:"16"}
  },
  tooltip : {
    trigger: 'item',
    formatter: "{b} : {d}%"
  },
  legend: {
    orient: 'vertical',
    left: 'right',
    top: '10%',
    data: ['政府从业人员','普通居民','民间组织人员','社区商户']
  },
  series : [
    {
      type: 'pie',
      radius : '75%',
      center: ['50%', '55%'],
      data:[
        {value:0, name:'政府从业人员',itemStyle: {normal:{color: '#EA9294'}}},
        {value:0, name:'普通居民',itemStyle: {normal:{color: '#949494'}}},
        {value:0, name:'民间组织人员',itemStyle: {normal:{color: '#A4D2D5'}}},
        {value:0, name:'社区商户',itemStyle: {normal:{color: '#F1C0B1'}}},
      ],
      labelLine: {
        normal: {
          show: false
        }
      },
      label: {
        normal: {
          position: 'inner',
          formatter: '{d}%',

          textStyle: {
            color: '#fff',
            fontWeight: 'bold',
            fontSize: 14
          }
        }
      },
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

// 客流趋势
option22 = {
  title: {
    text:"近30日客流趋势",x: '5%', y: '0%',textStyle:{fontSize:"16"},
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
    right:0,
    orient:'vertical',
  },
  grid: {
    left: '1%',
    right: '2%',
    bottom: '3%',
    top: '18%',
    containLabel: true
  },
  xAxis : [
    {
      type : 'category',
      boundaryGap : false,
      //data : ['1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30'],
      splitLine: {
        show: false
      },
      axisTick:{
        show: false
      },
      axisLabel:{
        show: true,
        interval: 2,
      }
    }
  ],
  yAxis : [
    {
      type : 'value',
      splitLine: {
        show: false
      },
      axisTick:{
        show: false
      },
      axisLabel:{
        show: true,
      }
    }
  ],
  series : [
    {
      name:'新增用户',
      type:'line',
      stack: '总量',
      areaStyle: {normal: {color:'#ff3064'}},
      //data:[120, 132, 101, 134, 90, 230, 210, 120, 132, 101, 134, 90, 230, 210, 120, 132, 101, 134, 90, 230, 210,120, 132, 101, 134, 90, 230, 210, 120, 132],
      itemStyle: {
        normal:{
          color:'#ff3064'
        }
      },
      smooth: true
    },
    {
      name:'消费用户',
      type:'line',
      stack: '总量',
      areaStyle: {normal: {color:'#30d7c7'}},
      //data:[220, 182, 191, 234, 290, 330, 310, 220, 182, 191, 234, 290, 330, 310, 220, 182, 191, 234, 290, 330, 310, 220, 182, 191, 234, 290, 330, 310, 220, 182],
      itemStyle: {
        normal:{
          color:'#30d7c7'
        }
      },
      smooth: true
    }
  ]
};

// 客流分析
option23 = {
  title: {
    text:"近7日客流分析",x: '5%', y: '0%',textStyle:{fontSize:"16"},
  },
  tooltip : {
      trigger: 'axis',
      formatter:function(params)//数据格式
            {
            var relVal = params[0]['name']+"<br/>";
            relVal += params[0]['marker']+params[0]['seriesName']+ ' : ' + String(params[0]['value']);
	        relVal+="%<br/>";
            relVal += params[2]['marker']+params[2]['seriesName']+ ' : ' + String(params[2]['value']);
	        relVal+="<br/>";
            relVal += params[1]['marker']+params[1]['seriesName']+ ' : ' + String(params[1]['value']);
	        relVal+="<br/>";
             return relVal;
        }
    },
  legend: {
    data:['复购率','消费用户量','新增用户量'],
    right:0,
    orient:'vertical',
  },
  grid: {
    left: '1%',
    right: '2%',
    bottom: '3%',
    top: '20%',
    containLabel: true
  },
  xAxis: {
    type: 'category',
    //data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
    splitLine: {
      show: false
    },
    axisTick:{
      show: false
    },
    axisLabel:{
      show: true,
    },
  },
  yAxis: [
     {
	    type: 'value',
	    position: 'left',
	    splitLine: {
	      show: false
	    },
        boundaryGap: [0, 0.1],
	    axisTick:{
	      show: false
	    },
	    axisLabel:{
	      show: true,
	    },
	},
   {
     type : 'value',
     position: 'right',
     splitLine: {
       show: false
     },
     axisLine: {
       show: true
     },
     axisTick:{
       show: false
     },
     axisLabel:{
       show: true
     }
   }
 ],
  series: [
    {
      name:'复购率',
      //data: [820, 932, 901, 934, 1290, 1330, 1320],
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
      //data: [181, 435, 234, 653, 235, 765, 130],
      type: 'bar',
      yAxisIndex: 0,
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
      //data: [546, 843, 356, 865, 455, 688, 464],
      type: 'bar',
      stack: '总量',
      yAxisIndex: 0,
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

//微信群
option24 = {
  tooltip: {
    trigger: 'axis',
    axisPointer: {type: 'cross'}
  },
  legend: {
    data:['店均微信群数量','单个微信群人数','活跃人群占比']
  },
  grid: {
    left: '1%',
    right: '5%',
    bottom: '3%',
    top: '18%',
    containLabel: true
  },
  xAxis: [
    {
      type: 'category',
      axisLabel: {  
    	     interval:0 
    	  }  ,
      //data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
      data:[]
    }
  ],
  yAxis: [
    {
      type: 'value',
      name: '人数',
      position: 'left',
      axisLabel: {
        formatter: '{value}'
      },
      boundaryGap: [0, 0.1],
      splitLine:{show: false}
    },
    {
      type: 'value',
      name: '个数',
      boundaryGap: [0, 0.1],
      position: 'right',
      axisLabel: {
        formatter: '{value}'
      },
      splitLine:{show: false}
    },
    {
      type: 'value',
      name: '百分比',
      position: 'right',
      offset: 70,
      axisLine: {
        show: true
      },
      splitLine:{show: false}
    }
  ],
  series: [
    {
      name:'店均微信群数量',
      type:'bar',
      data:[]
    },
    {
      name:'单个微信群人数',
      type:'bar',
      yAxisIndex: 1,
      data:[]
    },
    {
      name:'活跃人群占比',
      type:'line',
      yAxisIndex: 2,
      itemStyle: {
          normal: {
            barBorderRadius: 0,
            label: {
              show:true,
              position: "top",
              formatter: '{c}% '
            }
          }
        },
      data:[]
    }
  ]
};

//K线图--分时
var xData = function() {
  var data = [];
  for (var i = 0; i < 24; i++) {
    data.push(i + "时");
  }
  return data;
}();

option26 = {
  //backgroundColor: "#f55a44",
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      textStyle: {
        color: '#f55a44'
      }

    },
  },
  grid: {
    borderWidth: '0',
    left: '8%',
    right: '5%',
    bottom: '30%',
    top: '10%',

  },
  //calculable: true,
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
      show: false
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
      textStyle:{color:'#f55a44'},
      borderColor:"#90979c",
      //backgroundColor:'#fff'
    }],
  series: [{
    name: "总数",
    type: "line",
    stack: "总量",
    smooth: true,
    showAllSymbol: true,
    symbol: 'circle',
    symbolSize: 15,
    itemStyle: {
      normal: {
        color: '#f55a44',
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
   /* data: [
      1036,
      3693,
      2962,
      3810,
      2519,
      1915,
      1748,
      4675,
      6209,
      4323,
      2865,
      4298,
      1036,
      3693,
      2962,
      3810,
      2519,
      1915,
      1748,
      4675,
      6209,
      4323,
      2865,
      4298
    ]*/
    data:[]
  },
  ]
}


/*myChart1.setOption(option1);
myChart2.setOption(option2);
myChart3.setOption(option3);
myChart4.setOption(option4);
myChart5.setOption(option5);
myChart6.setOption(option6);
myChartCard.setOption(optionCard);
myChart7.setOption(option7);
myChartEmp.setOption(optionEmp);
myChartDianzhang.setOption(optiondianzhang);
myChartEmpDianzhang.setOption(optionEmpdianzhang);
myChartGuoanxia.setOption(optionguoanxia);
myChartEmpGuoanxia.setOption(optionEmpguoanxia);
myChartZhuanyuan.setOption(optionzhuanyuan);
myChartEmpZhuanyuan.setOption(optionEmpzhuanyuan);
// myChart8.setOption(option8);
myChart9.setOption(option9);
myChart10.setOption(option10);
myChartMainstoreType.setOption(optionMainstoreType);
myChartMainCooperative.setOption(optionMainCooperative);
myChart11.setOption(option11);
myChart12.setOption(option12);
myChart13.setOption(option13);
myChart14.setOption(option14);
myChart15.setOption(option15);
myChart16.setOption(option16);
myChart17.setOption(option17);
myChart18.setOption(option18);
myChart19.setOption(option19);
myChart20.setOption(option20);
myChart21.setOption(option21);
myChart22.setOption(option22);
myChart23.setOption(option23);
myChart24.setOption(option24);
myChart26.setOption(option26);*/
myChart18.setOption(option18);
myChart19.setOption(option19);
myChart20.setOption(option20);
myChart21.setOption(option21);
myChart24.setOption(option24);
myChart26.setOption(option26);


function chartresize2(){
  var temp2 = new initchart2();
  temp2.resize();
}
var chart27;
var chart27_option;
var xData = [];
var yData = [];
var initchart2 = function(){
    chart27 = echarts.init(document.getElementById('main27'));

    //K线图--日
    /*var xData = function() {
      var data = [];
      for (var i = 1; i < 8; i++) {
        data.push(i + "天");
      }
      return data;
    }();*/

    chart27_option = {
      //backgroundColor: "#f55a44",
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          textStyle: {
            color: '#f55a44'
          }

        },
      },
      grid: {
        borderWidth: '0',
        left: '8%',
        right: '5%',
        bottom: '30%',
        top: '10%',

      },
      //calculable: true,
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
       /* axisLabel: {
          interval: 0,

        },*/
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
       start: '80',
       end: '100',
       handleIcon: 'path://M306.1,413c0,2.2-1.8,4-4,4h-59.8c-2.2,0-4-1.8-4-4V200.8c0-2.2,1.8-4,4-4h59.8c2.2,0,4,1.8,4,4V413z',
       handleSize: '110%',
       handleStyle:{
       color:"#d3dee5",
       },
       textStyle:{color:'#f55a44'},
       borderColor:"#90979c",
       //backgroundColor:'#fff'
       }],
      series: [{
        name: "总数",
        type: "line",
        stack: "总量",
        symbolSize:15,
        symbol:'circle',
        itemStyle: {
          normal: {
            color: '#f55a44',
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
        /*data: [
          1036,
          3693,
          2962,
          3810,
          2519,
          1915,
          1748,
          4675,
          6209,
          4323,
          2865,
          4298
        ]*/
        data:yData
      },
      ]
    }

    chart27.setOption(chart27_option);
    $(function(){
        window.onresize = function(){
          chart27.resize();
        }
      })
    this.resize = function size(){
      var chart27div=document.getElementById('main27');
      var tabcontent = document.getElementById('tab-content');
      width=tabcontent.offsetWidth;
      //height=tabcontent.offsetHeight;
      chart27div.style.width=width+'px';
      chart27.resize(width);
    }
  }


function chartresize4(){
    var temp4 = new initchart4();
    temp4.resize();
  }
var chart28;
var chart28_option;
var xData_week = new Array();
var weekData = [];
var initchart4 = function(){
  chart28 = echarts.init(document.getElementById('main28'));

  //K线图--周
  var xData = function() {
    var data = [];
    for (var i = 1; i < 25; i++) {
      data.push('第' +i + "周");
    }
    return data;
  }();

  chart28_option = {
    //backgroundColor: "#f55a44",
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        textStyle: {
          color: '#f55a44'
        }

      },
    },
    grid: {
      borderWidth: '0',
      left: '8%',
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
      start: '0',
      end: '100',
      handleIcon: 'path://M306.1,413c0,2.2-1.8,4-4,4h-59.8c-2.2,0-4-1.8-4-4V200.8c0-2.2,1.8-4,4-4h59.8c2.2,0,4,1.8,4,4V413z',
      handleSize: '110%',
      handleStyle:{
        color:"#d3dee5",
      },
      textStyle:{color:'#f55a44'},
      borderColor:"#90979c",
      //backgroundColor:'#fff'
    }],
    series: [{
      name: "总数",
      type: "line",
      stack: "总量",
      symbolSize:15,
      symbol:'circle',
      itemStyle: {
        normal: {
          color: '#f55a44',
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
      /*data: [
        1036,
        3693,
        2962,
        3810,
        2519,
        1915,
        1748,
        4675,
        6209,
        4323,
        2865,
        4298
      ]*/
      data:weekData
    },
    ]
  }

  chart28.setOption(chart28_option);
  $(function(){
	    window.onresize = function(){
	      chart28.resize();
	    }
	  })
  this.resize = function size(){
    var chart28div=document.getElementById('main28');
    var tabcontent = document.getElementById('tab-content');
    width=tabcontent.offsetWidth;
    //height=tabcontent.offsetHeight;
    chart28div.style.width=width+'px';
    chart28.resize(width);
  }
}


function chartresize3(){
  var temp3 = new initchart3();
  temp3.resize();
}
var yData_month_target = [];
var yData_month = [];
var xData_month = [];
var chart29;
var chart29_option;
var initchart3 = function(){
	chart29 = echarts.init(document.getElementById('main29'));

  //K线图--月
  /*var xData = function() {
    var data = [];
    for (var i = 1; i < 25; i++) {
      data.push(i + "月");
    }
    return data;
  }();*/

  chart29_option = {
    //backgroundColor: "#f55a44",
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        textStyle: {
          color: '#f55a44'
        }

      },
    },
    grid: {
      borderWidth: '0',
      left: '8%',
      right: '5%',
      bottom: '30%',
      top: '10%',

    },
    legend: {
      x: '4%',
      top: '11%',
      textStyle: {
        color: '#90979c',
      },
      data: ['女', '男', '平均']
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
        show: false
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
      end: '100',
      handleIcon: 'path://M306.1,413c0,2.2-1.8,4-4,4h-59.8c-2.2,0-4-1.8-4-4V200.8c0-2.2,1.8-4,4-4h59.8c2.2,0,4,1.8,4,4V413z',
      handleSize: '110%',
      handleStyle:{
        color:"#d3dee5",
      },
      textStyle:{color:'#f55a44'},
      borderColor:"#90979c",
      //backgroundColor:'#fff'
    }],
    series: [{
      name: "总数",
      type: "line",
      symbolSize:15,
      symbol:'circle',
      itemStyle: {
        normal: {
          color: '#f55a44',
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
      /*data: [
        1036,
        3693,
        2962,
        3810,
        2519,
        1915,
        1748,
        4675,
        6209,
        4323,
        2865,
        4298
      ]*/
      data:yData_month
    },{
      name: "目标值",
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
      /*data: [
        1336,
        3363,
        2392,
        3380,
        2351,
        1315,
        1438,
        4735,
        6249,
        4253,
        2655,
        4598
      ]*/
      data:yData_month_target
    },
    ]
  }



  chart29.setOption(chart29_option);
  $(function(){
	    window.onresize = function(){
	      chart29.resize();
	    }
	  })

  this.resize = function size(){
    var chart29div=document.getElementById('main29');
    var tabcontent = document.getElementById('tab-content');
    width=tabcontent.offsetWidth;
    chart29div.style.width=width+'px';
    chart29.resize(width);
  }
}


var citycover2017= new Array();//2017年覆盖城市
var citycover2018=new Array();//2018年覆盖城市
var screenlogin=getUrlParamByKey("su");
var target = 0;
$(function(){	
	loginShow();
});

function showData(){
	getsixweek();
	getUser();
	getCityNet();
	findStoreNetDate();
	oneyearorsixweek();
	storeDevelopmentProgress();
	progressOfNetworkConstruction();
	humanresourseInfo();
	customerInfo();
	areaInfo();
	GMVInfo();
	storeActivitiesInfo();
	getStoreCoverPerson();
	//employeeCustomer();
	getStoreResources();
}

function getsixweek(){
	doManager("dynamicManager", "getsixWeekDate",null,function(data, textStatus, XMLHttpRequest) {
		if (data.result) {
			var data_time = JSON.parse(data.data);
			six_week_data_array.push(data_time.week1);
			six_week_data_array.push(data_time.week2);
			six_week_data_array.push(data_time.week3);
			six_week_data_array.push(data_time.week4);
			six_week_data_array.push(data_time.week5);
			six_week_data_array.push(data_time.week6);
		}
  	},false);
}

function getUser(){
	doManager("UserManager", "getCurrentUserDTO",null,function(data, textStatus, XMLHttpRequest) {
		if (data.result) {
			var employeeID="";
			var curr_user = JSON.parse(data.data);
			if(curr_user == null){
				urlStr = "../logout.do";
				window.location.href = urlStr;
			}
		    if(curr_user.usergroup.code == "ZBXXWLBJJSZ"){
		    	$("#pfedit_button").show();
		    	$("#pfoperate_buttoon").show();
		    	$("#resources_edit").show();
		    }
		}
  	},false);
}

function loginShow(){
	
	if(screenlogin!=null&&screenlogin!=''&&screenlogin!=undefined){
		var reObj = new PMSRequestObject("userManager", "isScreenUser", [ screenlogin ]);
	    var callback = function callback(data, textStatus, XMLHttpRequest) {
	    	//window.parent.location=getRootPath() + "/crm/index_city_net.html";
	    	var stateObject = {};
	    	var newUrl = "/daqWeb/crm/index_city_net.html";
	    	//target = 1;
	    	history.replaceState(stateObject,null,newUrl);
	    	showData();
		};
	    var failureCallback = function failureCallback(data, textStatus, XMLHttpRequest) {
			alert("登录信息错误，请确认输入或联系管理员!");
			return false;
		}
	    var url = "../login.do";
	    $$.ajax(url, "requestString=" + reObj.toJsonString(), callback, failureCallback); 
	}else{
		showData();
	}
}

//获取城市城市网络体系概况
function getCityNet(){
	var myChart1 = echarts.init(document.getElementById('main1'));
	var myChart9 = echarts.init(document.getElementById('main9'));
	var myChart15 = echarts.init(document.getElementById('main15'));
	myChart1.setOption(option1);
	myChart9.setOption(option9);
	myChart15.setOption(option15);
	
	  doManager("storeManager","findStoreCityDynamic",null,function(data,textStatus,XmlHttpRequest){
		if (data.result) {  
			var province = [];
			doManager("storeManager", "getAllOpenProvinces",[],
		            function(data, textStatus, XMLHttpRequest) {
		                if (data.result) {
		                    var resultJson= JSON.parse(data.data);
		                    $.each(eval(resultJson['province']), function (idx, val) {
			    				var object = new Object();
			    				object['name'] = val['name'].replace("市","").replace("省","").replace("自治区","");
			    				if(val['selected']=='true'){
			    				object['value']=1000;
			    				}
			    				province.push(object);
			     			});
		                }
		            },false);
			var jsonData = $.fromJSON(data.data);
			$("#city").text(jsonData.coverCity);
			$("#county").text(jsonData.coverProvincecounty);
			$("#town").text(jsonData.coverProvincetown);
			
			/*if(jsonData.townsize==0){
				$("#weekupdate").text("本周暂无变化");
			}else{
				$("#weekupdate").text("新增覆盖"+jsonData.countysize+"个区域（"+(jsonData.countyNames.length>6?jsonData.countyNames.substring(0,6)+"..":jsonData.countyNames)+"），新增覆盖"+jsonData.townsize+"个街道（"+(jsonData.townNames.length>8?jsonData.townNames.substring(0,8)+"..":jsonData.townNames)+"）")
			}*/
			//jsonData.townsize==0 && 
			if(jsonData.storesize==0){
				$("#weekupdate").text("本周暂无变化");
			}else{
				var appendStr = "";
				//if(jsonData.citysize!=0)appendStr += "新增覆盖"+jsonData.citysize+"城市（"+jsonData.countyNames+"）；";
				//if(jsonData.countysize!=0)appendStr += "新增覆盖"+jsonData.countysize+"个区域（"+(jsonData.countyNames.length>6?jsonData.countyNames.substring(0,6)+"..":jsonData.countyNames)+"）；";
				if(jsonData.storesize!=0)appendStr += "新增覆盖"+jsonData.storesize+"个门店（"+(jsonData.storenames)+"）；";
				//if(jsonData.townsize!=0)appendStr += "新增覆盖"+jsonData.townsize+"个街道（"+(jsonData.townNames.length>8?jsonData.townNames.substring(0,8)+"..":jsonData.townNames)+"）；";
				$("#weekupdate").text(appendStr);
			}
			var list2017=jsonData.list2017;
			for(var i=0;i<list2017.length;i++){
				citycover2017[i]={name:list2017[i].city_name,value:100};
			}
			option1.series[0].data=convertData(citycover2017);
			var list2018=jsonData.list2018;
			for(var j=0;j<list2018.length;j++){
				citycover2018[j]={name:list2018[j].city_name,value:190};
			}
			option1.series[0].data=convertData(citycover2017);
			option1.series[1].data=province;
			option1.series[2].data=convertData(citycover2018);
			myChart1.setOption(option1);
			//区域覆盖情况
			var citydata=jsonData.cityDataqingkuang;
			var dataBeast=new Array();//已覆盖区域
			var dataBeauty=new Array();//未覆盖区域
			var xAxisData=new Array();//城市横坐标
			var xData=new Array();//覆盖率(区域)
			var dataBeastTown=new Array();//已覆盖街道
			var dataBeautyTown=new Array();//未覆盖街道
			var xDataTown=new Array();//覆盖率(街道)
			for(var m=0;m<citydata.length;m++){
				 xAxisData[m]=citydata[m].cityName.length>4?citydata[m].cityName.substring(0,5)+'\n'+citydata[m].cityName.substring(5,citydata[m].cityName.length):citydata[m].cityName;
				dataBeast[m]=citydata[m].coverCountyCounty;
				dataBeauty[m]=citydata[m].notcoverCountyCounty;
				xData[m]=parseFloat(((parseInt(citydata[m].coverCountyCounty)/parseInt(citydata[m].countytotal))*100).toFixed(2));
				dataBeastTown[m]=citydata[m].coverTownCount;
				dataBeautyTown[m]=citydata[m].notcoverTownCount;
				xDataTown[m]=parseFloat(((parseInt(citydata[m].coverTownCount)/parseInt(citydata[m].towntotal))*100).toFixed(2));
			}
			option9.xAxis.data=xAxisData;
			option9.series[0].data=dataBeast;
			option9.series[1].data=dataBeauty;
			option9.series[2].data=xData;
			myChart9.setOption(option9);
			option15.xAxis.data=xAxisData;
			option15.series[0].data=dataBeastTown;
			option15.series[1].data=dataBeautyTown;
			option15.series[2].data=xDataTown;
			myChart15.setOption(option15);
		}},false); 
}

//获取网络业态分布数据
function findStoreNetDate(){
	var myChart2 = echarts.init(document.getElementById('main2'));
	var myChart10 = echarts.init(document.getElementById('main10'));
	var myChartMainstoreType = echarts.init(document.getElementById('mainstoreType'));
	var myChartMainCooperative = echarts.init(document.getElementById('mainCooperative'));
	myChart2.setOption(option2);
	myChart10.setOption(option10);
	myChartMainstoreType.setOption(optionMainstoreType);
	myChartMainCooperative.setOption(optionMainCooperative);
	doManager("storeManager","findStoreCityOnline",null,function(data,textStatus,XmlHttpRequest){
		if (data.result) {
			var jsonData = $.fromJSON(data.data);
			var storeNature = jsonData.storeNature;
			var storeTypeInfo = jsonData.storeTypeInfo;
			var storeTypeNatureOfCity = jsonData.storeTypeNatureOfCity;
			var hezuodian = jsonData.hezuodian;
			var biaodata = jsonData.biao;
			if(storeNature.length > 0){
				var citynameArray = new Array();
				var cooperative_count_array = new Array();
				var self_count_array = new Array();
				var  natureInfo = "";
				 for(var i = 0; i < storeNature.length; i++){
					var cityInfo = storeNature[i];
					var cityname = cityInfo.city_name.length>4?cityInfo.city_name.substring(0,5)+'\n'+cityInfo.city_name.substring(5,cityInfo.city_name.length):cityInfo.city_name;
					var self_count = cityInfo.self_count;
					var cooperative_count = cityInfo.cooperative_count;
					citynameArray.push(cityname);
					cooperative_count_array.push(cooperative_count);
					self_count_array.push(self_count);
					var str_info = cityname+"共计<span class='text-red'>"+(cooperative_count+self_count)+"</span>家；"
					natureInfo += str_info;
				 }
				 $("#storeInfoOfCity").append(natureInfo);
				 option2.series[0].data=self_count_array;
			     option2.series[1].data=cooperative_count_array;
			     option2.xAxis[0].data=citynameArray;
			     myChart2.setOption(option2);
			}
			if(storeTypeInfo.length > 0){
				var main10_data = new Array();
				var storeType = storeTypeInfo[0];
				main10_data.push(storeType.校园店);
				main10_data.push(storeType.生活中心店);
				main10_data.push(storeType.街道月店);
				main10_data.push(storeType.经营星店);
				main10_data.push(storeType.药店);
				main10_data.push(storeType.独立微超);
				main10_data.push(storeType.合作店);
				main10_data.push(storeType.合作点);
				main10_data.push(storeType.前置仓);
				main10_data.push(storeType.城市仓);
				var china_store_count = storeType.校园店+storeType.生活中心店+storeType.街道月店+storeType.经营星店+storeType.药店+storeType.独立微超+storeType.合作店+storeType.合作点;
				$("#china_store_count").html(china_store_count);
				$("#cooperative_count").html(storeType.合作店+storeType.合作点);
				 option10.series[0].data=main10_data;
			     myChart10.setOption(option10);
			}
			if(storeTypeNatureOfCity.length > 0){
				var mainstoreType_x = new Array();
				var mainstoreType_data = new Array();
				for(var i = 0; i < storeTypeNatureOfCity.length; i++){
					var storeType = storeTypeNatureOfCity[i];
					var cityname =storeType.city_name.length>4?storeType.city_name.substring(0,5)+'\n'+storeType.city_name.substring(5,storeType.city_name.length):storeType.city_name;;
					var count = storeType.count;
					mainstoreType_x.push(cityname);
					mainstoreType_data.push(count);
				}
				optionMainstoreType.series[0].data=mainstoreType_data;
				optionMainstoreType.xAxis[0].data=mainstoreType_x;
				myChartMainstoreType.setOption(optionMainstoreType);
			}
			var mainCooperative_data1 = new Array();//数码连锁店
			var mainCooperative_data2 = new Array();//超市连锁店
			var mainCooperative_data3 = new Array();//广店营业厅
			var mainCooperative_datax = new Array();//广店营业厅
			if(hezuodian.length > 0){
				for(var i = 0;i<hezuodian.length;i++){
					var mainCooperative = hezuodian[i];
					var cityname = mainCooperative.city_name;
					if(mainCooperative.数码连锁店 !=0 || mainCooperative.超市连锁店 != 0 || mainCooperative.广电营业厅 != 0){
						mainCooperative_datax.push(cityname);
						mainCooperative_data1.push(mainCooperative.数码连锁店);
						mainCooperative_data2.push(mainCooperative.超市连锁店);
						mainCooperative_data3.push(mainCooperative.广电营业厅);
					}
				}
				optionMainCooperative.xAxis[0].data=mainCooperative_datax;
				optionMainCooperative.series[0].data=mainCooperative_data1;
				optionMainCooperative.series[1].data=mainCooperative_data2;
				optionMainCooperative.series[2].data=mainCooperative_data3;
				myChartMainCooperative.setOption(optionMainCooperative);
			}
			if(biaodata.length > 0){
				//门店业态表数据（table）
				var storeString='';
				var quguoziying=0;
				var quanguoshenghuo=0;
				var quanguoyuedian=0;
				var quanguoxingdian=0;
				var quanguoxiaoyuan=0;
				var quanguoweichao=0;
				var quanguoyaodian=0;
				var quanguoqita=0;
				var quanguo2018qianzhicangmubiao=0;
				var quanguo2018hezuomubiao=0;
				var quanguonewqianzhicangstore=0;
				var quanguonewhezuostore=0;
				
				for(var i=0;i<biaodata.length;i++){
					var citystore=biaodata[i].shenghuo+biaodata[i].yuedian+biaodata[i].xingdian+biaodata[i].xiaoyuan+biaodata[i].weichao+biaodata[i].yaodian+biaodata[i].qita;
					quguoziying+=citystore;
					  quanguoshenghuo+=biaodata[i].shenghuo;
			          quanguoyuedian+=biaodata[i].yuedian;
			          quanguoxingdian+=biaodata[i].xingdian;
			          quanguoxiaoyuan+=biaodata[i].xiaoyuan;
			          quanguoweichao+=biaodata[i].weichao;
			          quanguoyaodian+=biaodata[i].yaodian;
			          quanguoqita+=biaodata[i].qita;
			          quanguo2018qianzhicangmubiao+=biaodata[i].qianzhicangmubiao;
			          quanguo2018hezuomubiao+=biaodata[i].hezuomubiao;
			          quanguonewhezuostore+=biaodata[i].hezuocount;
			          quanguonewqianzhicangstore+=biaodata[i].qianzhicangcount;
					storeString+='<tr><td>'+biaodata[i].city_name+'</td><td class="text-red">'+citystore+'</td><td>'+biaodata[i].shenghuo+'</td>'+
	                '<td>'+biaodata[i].yuedian+'</td><td>'+biaodata[i].xingdian+'</td><td>'+biaodata[i].xiaoyuan+'</td><td>'+biaodata[i].weichao+'</td><td>'+biaodata[i].yaodian+'</td><td>'+biaodata[i].qita+'</td>'+
	                '<td class="text-red">'+biaodata[i].qianzhicangmubiao+'</td><td>'+biaodata[i].qianzhicangcount+'</td><td class="text-red">'+biaodata[i].hezuomubiao+'</td><td>'+biaodata[i].hezuocount+'</td></tr>';
				}
				$("#storenet tr:eq(1)").after('<tr class="text-red"><th>全国</th><th>'+quguoziying+'</th><th>'+quanguoshenghuo+'</th><th>'+quanguoyuedian+'</th><th>'+quanguoxingdian+'</th>'+
		                  '<th>'+quanguoxiaoyuan+'</th><th>'+quanguoweichao+'</th><th>'+quanguoyaodian+'</th><th>'+quanguoqita+'</th><th>'+quanguo2018qianzhicangmubiao+'</th><th>'+quanguonewqianzhicangstore+'</th><th>'+quanguo2018hezuomubiao+'</th><th>'+quanguonewhezuostore+'</th></tr>'+storeString);
			}	
			
			
		}});
}

function oneyearorsixweek(){
	var myChart3 = echarts.init(document.getElementById('main3'));
	var myChart11 = echarts.init(document.getElementById('main11'));
	myChart3.setOption(option3);
	myChart11.setOption(option11);
	doManager("storeManager","findStoreNetTozhan",null,function(data,textStatus,XmlHttpRequest){
		if (data.result) {
			var jsonData = $.fromJSON(data.data);
			var oneyear=jsonData.oneyear;
			var yeardataonle=new Array();//年拓展
			var yeardataother=new Array();
			var yeardatadate=new Array();
			var yeartotal=new Array();
			yeardataonle[0]=oneyear[0].month01;
	        yeardataonle[1]=oneyear[0].month02;
	        yeardataonle[2]=oneyear[0].month03;
	        yeardataonle[3]=oneyear[0].month04;
	        yeardataonle[4]=oneyear[0].month05;
	        yeardataonle[5]=oneyear[0].month06;
	        yeardataonle[6]=oneyear[0].month07;
	        yeardataonle[7]=oneyear[0].month08;
	        yeardataonle[8]=oneyear[0].month09;
	        yeardataonle[9]=oneyear[0].month10;
	        yeardataonle[10]=oneyear[0].month11;
	        yeardataonle[11]=oneyear[0].month12;
	        yeardataother[0]=oneyear[1].month01;
	        yeardataother[1]=oneyear[1].month02;
	        yeardataother[2]=oneyear[1].month03;
	        yeardataother[3]=oneyear[1].month04;
	        yeardataother[4]=oneyear[1].month05;
	        yeardataother[5]=oneyear[1].month06;
	        yeardataother[6]=oneyear[1].month07;
	        yeardataother[7]=oneyear[1].month08;
	        yeardataother[8]=oneyear[1].month09;
	        yeardataother[9]=oneyear[1].month10;
	        yeardataother[10]=oneyear[1].month11;
	        yeardataother[11]=oneyear[1].month12;
	        yeardatadate[0]=oneyear[2].month01;
	        yeardatadate[1]=oneyear[2].month02;
	        yeardatadate[2]=oneyear[2].month03;
	        yeardatadate[3]=oneyear[2].month04;
	        yeardatadate[4]=oneyear[2].month05;
	        yeardatadate[5]=oneyear[2].month06;
	        yeardatadate[6]=oneyear[2].month07;
	        yeardatadate[7]=oneyear[2].month08;
	        yeardatadate[8]=oneyear[2].month09;
	        yeardatadate[9]=oneyear[2].month10;
	        yeardatadate[10]=oneyear[2].month11;
	        yeardatadate[11]=oneyear[2].month12;
	        yeartotal[0]=parseInt(oneyear[1].month01)+parseInt(oneyear[0].month01);
	        yeartotal[1]=parseInt(oneyear[1].month02)+parseInt(oneyear[0].month02);
	        yeartotal[2]=parseInt(oneyear[1].month03)+parseInt(oneyear[0].month03);
	        yeartotal[3]=parseInt(oneyear[1].month04)+parseInt(oneyear[0].month04);
	        yeartotal[4]=parseInt(oneyear[1].month05)+parseInt(oneyear[0].month05);
	        yeartotal[5]=parseInt(oneyear[1].month06)+parseInt(oneyear[0].month06);
	        yeartotal[6]=parseInt(oneyear[1].month07)+parseInt(oneyear[0].month07);
	        yeartotal[7]=parseInt(oneyear[1].month08)+parseInt(oneyear[0].month08);
	        yeartotal[8]=parseInt(oneyear[1].month09)+parseInt(oneyear[0].month09);
	        yeartotal[9]=parseInt(oneyear[1].month10)+parseInt(oneyear[0].month10);
	        yeartotal[10]=parseInt(oneyear[1].month11)+parseInt(oneyear[0].month11);
	        yeartotal[11]=parseInt(oneyear[1].month12)+parseInt(oneyear[0].month12);
	        option3.series[0].data=yeartotal;
	        option3.series[1].data=yeardataonle;
	        option3.series[2].data=yeardataother;
	        option3.xAxis[0].data=yeardatadate;
	        myChart3.setOption(option3);
	        var sixweek=jsonData.sixweek;
			var weekonle=new Array();//周情况
			var weekother=new Array();
			var weektotal=new Array();
			var weekdate=new Array();
			weekonle[0]=sixweek[0].week1;
			weekonle[1]=sixweek[0].week2;
			weekonle[2]=sixweek[0].week3;
			weekonle[3]=sixweek[0].week4;
			weekonle[4]=sixweek[0].week5;
			weekonle[5]=sixweek[0].week6;
			weekother[0]=sixweek[1].week1;
			weekother[1]=sixweek[1].week2;
			weekother[2]=sixweek[1].week3;
			weekother[3]=sixweek[1].week4;
			weekother[4]=sixweek[1].week5;
			weekother[5]=sixweek[1].week6;
			weektotal[0]=parseInt(sixweek[1].week1)+parseInt(sixweek[0].week1);
			weektotal[1]=parseInt(sixweek[1].week2)+parseInt(sixweek[0].week2);
			weektotal[2]=parseInt(sixweek[1].week3)+parseInt(sixweek[0].week3);
			weektotal[3]=parseInt(sixweek[1].week4)+parseInt(sixweek[0].week4);
			weektotal[4]=parseInt(sixweek[1].week5)+parseInt(sixweek[0].week5);
			weektotal[5]=parseInt(sixweek[1].week6)+parseInt(sixweek[0].week6);
			 option11.series[0].data=weektotal;
		        option11.series[1].data=weekonle;
		        option11.series[2].data=weekother;
		        myChart11.setOption(option11);
		}});
	
}

	
	var storeDevelopmentProgress = function(){
		var myChart12 = echarts.init(document.getElementById('main12'));
		var myChart13 = echarts.init(document.getElementById('main13'));
		var myChart14 = echarts.init(document.getElementById('main14'));
		myChart12.setOption(option12);
		myChart13.setOption(option13);
		myChart14.setOption(option14);
		
		var dateArray = new Array();
		var citynameArrayx1 = new Array();
		var citynameArrayx2 = new Array();
		var cooperative_completeArray = new Array();
		var cooperative_taskArray = new Array();
		var cooperative_rateArray = new Array();
		var self_completeArray = new Array();
		var self_support_taskArry = new Array();
		var self_rateArray = new Array();
		var cooperativeStoreArray = [0,0,0,0,0,0];
		var selfStoreArray = [0,0,0,0,0,0];
		doManager("storeManager","finyStoreCountOfCity",null,function(data,textStatus,XmlHttpRequest){
			if (data.result) {
				var jsonData = $.fromJSON(data.data);
				//合作店近六周
				var cooperativeStore=jsonData.cooperativeStore;
				//自营店近六周
				var selfStore = jsonData.selfStore;
				if(selfStore.length>0){
					var weektimeArray = new Array();
					var vauleParam = {};
					for(var z = 0; z < selfStore.length; z++){
						var self_store =  selfStore[z];
						var weektime = self_store.week_time;
						var storeCount = self_store.count;					
						var index = jQuery.inArray(weektime,six_week_data_array);
						selfStoreArray[index] = storeCount;
					}
				}
				option12.series[0].data=selfStoreArray;
				if(cooperativeStore.length>0){
					var weektimeArray = new Array();
					var vauleParam = {};
					for(var z = 0; z < cooperativeStore.length; z++){
						var cooperative_store =  cooperativeStore[z];
						var weektime = cooperative_store.week_time;
						var storeCount = cooperative_store.count;
						var index = jQuery.inArray(weektime,six_week_data_array);
						cooperativeStoreArray[index] = storeCount;
					}
				}
				option12.series[1].data=cooperativeStoreArray;
				
				var preString = "";
				var nowString = "";
				//全国门店的数据2017
				var cooperative_task2017 = 0,cooperative2017 = 0, self_task2017 = 0,self2017 = 0,after2017 = 0;
				var cooperative_task2018 = 0,cooperative2018 = 0, self_task2018 = 0,self2018 = 0,after2018 = 0;
				//当年内门店拓展进度
				var storeNatureOfCityByYear = jsonData.storeNatureByYear;
				for(var i = 0; i < storeNatureOfCityByYear.length; i++){
					var storeNatureByYear = storeNatureOfCityByYear[i];
					var cityname = storeNatureByYear.cityname;

					//合作店
					var cooperative_complete = storeNatureByYear.cooperative_complete;
					//包含展示合作店目标值
					var cooperative_task_content = 0;
					var cooperative_task = storeNatureByYear.cooperative_task;
					cooperative_task_content = cooperative_task - cooperative_complete;
					if(cooperative_task_content < 0){
						cooperative_obj[cityname]=cooperative_task_content;
						cooperative_task_content = 0;				
					}
					var cooperative_rate = 0;
					if(cooperative_task > 0){
						cooperative_rate = (cooperative_complete/cooperative_task)*100;
					}
					//if(cooperative_complete != 0){
						citynameArrayx2.push(cityname);
						cooperative_completeArray.push(cooperative_complete);
						cooperative_taskArray.push(cooperative_task_content);
						cooperative_rateArray.push(cooperative_rate.toFixed(2));
					//}
					//自营店
					var self_complete = storeNatureByYear.self_complete;
					
					//包含展示自营店目标值
					var self_support_task_content = 0;
					var self_support_task = storeNatureByYear.self_support_task;
					self_support_task_content = self_support_task-self_complete;
					if(self_support_task_content < 0){
						self_obj[cityname]=self_support_task_content;
						self_support_task_content = 0;				
					}
					var self_rate = 0;
					if(self_support_task > 0){
						self_rate = (self_complete/self_support_task)*100;
					}
					//if(self_complete != 0){
						self_completeArray.push(self_complete);
						citynameArrayx1.push(cityname);
						self_support_taskArry.push(self_support_task_content);
						self_rateArray.push(self_rate.toFixed(2));
					//}
					if(storeNatureByYear.create_year == '2017'){
						cooperative_task2017 += cooperative_task;
						cooperative2017 += cooperative_complete;
						self2017 += self_complete;
						self_task2017 += self_support_task;
						preString += '<tr><td title="'+cityname+'">'+cityname+'</td><td class="text-red">'+self_support_task+'</td><td>'+self_complete+'</td><td>'+self_rate.toFixed(2)+'%</td>'
								+'<td class="text-red">'+cooperative_task+'</td><td>'+cooperative_complete+'</td><td>'+cooperative_rate.toFixed(2)+'%</td></tr>';
						
					}else{
						cooperative_task2018 += cooperative_task;
						cooperative2018 += cooperative_complete;
						self2018 += self_complete;
						self_task2018 += self_support_task;
						nowString += '<tr><td title="'+cityname+'">'+cityname+'</td><td class="text-red">'+self_support_task+'</td><td>'+self_complete+'</td><td>'+self_rate.toFixed(2)+'%</td>'
								+'<td class="text-red">'+cooperative_task+'</td><td>'+cooperative_complete+'</td><td>'+cooperative_rate.toFixed(2)+'%</td></tr>';
					}
					if(i == storeNatureOfCityByYear.length - 1 ){
						var rate1_2017 = 0;
						var rate2_2017 = 0;
						var rate1_2018 = 0;
						var rate2_2018 = 0;
						if(self_task2017 != 0){
							rate1_2017 = (self2017/self_task2017)*100
						}
						if(cooperative_task2017 != 0){
							rate2_2017 = (cooperative2017/cooperative_task2017)*100
						}
						if(self_task2018 != 0){
							rate1_2018 = (self2018/self_task2018)*100
						}
						if(cooperative_task2018 != 0){
							rate2_2018 = (cooperative2018/cooperative_task2018)*100
						}
						preString += '<tr class="text-red"><td>全国</td><td>'+self_task2017+'</td><td>'+self2017+'</td><td>'+rate1_2017.toFixed(2)+'%</td>'
								+'<td class="text-red">'+cooperative_task2017+'</td><td>'+cooperative2017+'</td><td>'+rate2_2017.toFixed(2)+'%</td></tr>';
						nowString += '<tr class="text-red"><td>全国</td><td>'+self_task2018+'</td><td>'+self2018+'</td><td>'+rate1_2018.toFixed(2)+'%</td>'
								+'<td class="text-red">'+cooperative_task2018+'</td><td>'+cooperative2018+'</td><td>'+rate2_2018.toFixed(2)+'%</td></tr>';
					}
				}
				
				option13.series[0].data=self_rateArray;
				option13.series[2].data=self_support_taskArry;
				option13.series[1].data=self_completeArray;
				option13.xAxis[0]["data"] = citynameArrayx1;
				
				option14.series[0].data=cooperative_rateArray;
				option14.series[2].data=cooperative_taskArray;
				option14.series[1].data=cooperative_completeArray;
				option14.xAxis[0]["data"] = citynameArrayx2;
				
				myChart12.setOption(option12);
				myChart13.setOption(option13);
				myChart14.setOption(option14);
				
				$("#preopenstore").append(preString);
				$("#newopenstore").append(nowString);

			}
		});
	}
	var progressOfNetworkConstruction = function(){
		var myChart4 = echarts.init(document.getElementById('main4'));
		var myChart5 = echarts.init(document.getElementById('main5'));
		var myChart6 = echarts.init(document.getElementById('main6'));
		var myChartCard = echarts.init(document.getElementById('maincard'));
		myChart4.setOption(option4);
		myChart5.setOption(option5);
		myChart6.setOption(option6);
		myChartCard.setOption(optionCard);
		doManager("storexpandManager","progressOfNetworkConstruction",null,function(data,textStatus,XmlHttpRequest){
			if (data.result) {
				var jsonData = $.fromJSON(data.data);
				var contractAndthroughByYear = jsonData.contractAndthroughByYear;
				var cityArray = new Array();
				var contractQuantityArray = new Array();
				var throughQuantityArray = new Array();
				var contractQuantitySum = 0;
				if(contractAndthroughByYear.length > 0){
					for(var i = 0; i < contractAndthroughByYear.length; i++){
						var cityData = contractAndthroughByYear[i];
						var cityname = cityData.city_name;
						cityArray.push(cityname);
						var contractQuantity = cityData.contract_quantity;
						contractQuantityArray.push(contractQuantity);
						contractQuantitySum += contractQuantity;
						var throughQuantity = cityData.through_quantity;
						throughQuantityArray.push(throughQuantity);
					}
				}else{
					
				}
				option4.series[0].data=throughQuantityArray;
				option4.series[1].data=contractQuantityArray;
				option4.xAxis[0]["data"] = cityArray;
				myChart4.setOption(option4);
				var citynameArray = new Array();
				var throughByWeek = jsonData.throughByWeek;
				if(throughByWeek.length > 0){
					for(var i = 0; i < throughByWeek.length; i++){
						var cityweekArray = new Array();
						var cityname = throughByWeek[i].city_name;
						citynameArray.push(cityname);
						cityweekArray.push(throughByWeek[i].week1);
						cityweekArray.push(throughByWeek[i].week2);
						cityweekArray.push(throughByWeek[i].week3);
						cityweekArray.push(throughByWeek[i].week4);
						cityweekArray.push(throughByWeek[i].week5);
						cityweekArray.push(throughByWeek[i].week6);
						option5.series.push({
						      name:cityname,
						      type: 'line',
						      symbol:"circle",
						      symbolSize:10,
						      data: cityweekArray,
						      itemStyle: {
						            normal: {
						              label: {
						                show: true,
						                formatter: function(p) {
						                  return p.value > 0 ? (p.value) : '';
						                }
						              }
						            }
						        },
						      
						});
					}
					option5.legend.data = citynameArray;
				}
				myChart5.setOption(option5);
				//筹备中门店
				var storeStateCount = jsonData.storeStateCount;
				var main6CitynameArray = new Array();
				var main6DataArray = new Array();
				var preStoreSum = 0;
				if(storeStateCount.length > 0){
					for(var i = 0; i < storeStateCount.length; i++){
						main6CitynameArray.push(storeStateCount[i].city_name);
						main6DataArray.push(storeStateCount[i].count);
						preStoreSum += storeStateCount[i].count;
					}
				}
				option6.series[0].data=main6DataArray;
				option6.xAxis[0]["data"] = main6CitynameArray;
				myChart6.setOption(option6);
				var cardString = "";
				var storeCardBycity = jsonData.storeCardBycity;
				var business_licenseTotal = 0,double_cardTotal = 0,no_cardTotal = 0,store_countTotal = 0;
				var storeCountArray = new Array(),double_cardArray = new Array(),business_licenseArray = new Array(),no_cardArray = new Array();
				var cardCitynameArray = new Array();
				var double_card_rateTotal = 0;
				var business_card_rateTotal = 0;
				var no_card_rateTotal = 0;
				if(storeCardBycity.length > 0){
					for(var i = 0; i < storeCardBycity.length; i++){
						var storeCard = storeCardBycity[i];
						var business_license =  storeCard.business_license;
						business_licenseArray.push(business_license);
						var cityname = storeCard.city_name;
						cardCitynameArray.push(cityname);
						var double_card = storeCard.double_card;
						double_cardArray.push(double_card);
						var no_card = storeCard.no_card;
						no_cardArray.push(no_card);
						var store_count = storeCard.store_count;
						storeCountArray.push(store_count);
						var double_card_rate = (double_card/store_count)*100;
						business_licenseTotal += business_license;
						double_cardTotal += double_card;
						no_cardTotal += no_card;
						store_countTotal += store_count;

						//cardString += '<tr><td title="'+cityname+'">'+cityname+'</td><td>'+store_count+'</td><td>'+double_card+'</td><td>'+business_license+'</td><td>'+no_card+'</td><td>'+double_card_rate.toFixed(2)+'%</td></tr>';
						if(i == storeCardBycity.length-1){
							double_card_rateTotal = (double_cardTotal/store_countTotal)*100;
							business_card_rateTotal = (business_licenseTotal/store_countTotal)*100;
							no_card_rateTotal = (no_cardTotal/store_countTotal)*100;
							cardString += '<tr class="text-red"><td>合计</td><td>'+store_countTotal+'</td><td>'+double_cardTotal+'</td><td>'+business_licenseTotal+'</td><td>'+no_cardTotal+'</td><td>'+double_card_rateTotal.toFixed(2)+'%</td></tr>';
						}
					}
					$("#storecard").append(cardString);
					 optionCard.series[0].data=storeCountArray;
					 optionCard.series[1].data=double_cardArray;
					 optionCard.series[2].data=business_licenseArray;
					 optionCard.series[3].data=no_cardArray; 
					optionCard.xAxis[0]["data"] = cardCitynameArray;
					myChartCard.setOption(optionCard);
				}
				/*$("#signedCount").html(contractQuantitySum);
				$("#preStoreCount").html(preStoreSum);
				$("#openCount").html(store_countTotal);
				$("#doubleCardCount").html(double_card_rateTotal.toFixed(2));
				$("#businessCardCount").html(business_card_rateTotal.toFixed(2));
				$("#noCardCount").html(no_card_rateTotal.toFixed(2));*/
			}
		});
	}
	var humanresourseInfo = function(){
		var myChart7 = echarts.init(document.getElementById('main7'));
		var myChartEmp = echarts.init(document.getElementById('mainemp'));
		var myChartDianzhang = echarts.init(document.getElementById('maindianzhang'));
		var myChartEmpDianzhang = echarts.init(document.getElementById('mainempdianzhang'));
		var myChartGuoanxia = echarts.init(document.getElementById('mainguoanxia'));
		var myChartEmpGuoanxia = echarts.init(document.getElementById('mainempguoanxia'));
		var myChartZhuanyuan = echarts.init(document.getElementById('mainzhuanyuan'));
		var myChartEmpZhuanyuan = echarts.init(document.getElementById('mainempzhuanyuan'));
		var myChartZongguan = echarts.init(document.getElementById('mainzongguan'));
		var myChartEmpZongguan = echarts.init(document.getElementById('mainempzongguan'));
		myChart7.setOption(option7);
		myChartEmp.setOption(optionEmp);
		myChartDianzhang.setOption(optiondianzhang);
		myChartEmpDianzhang.setOption(optionEmpdianzhang);
		myChartGuoanxia.setOption(optionguoanxia);
		myChartEmpGuoanxia.setOption(optionEmpguoanxia);
		myChartZhuanyuan.setOption(optionzhuanyuan);
		myChartEmpZhuanyuan.setOption(optionEmpzhuanyuan);
		myChartZongguan.setOption(optionzongguan);
		myChartEmpZongguan.setOption(optionEmpzongguan);
		doManager("humanresourcesManager","getEmployeeInfoByWeek",null,function(data,textStatus,XmlHttpRequest){
			if (data.result) {
				var jsonData = $.fromJSON(data.data);
				//总人数
				var queryHumanTotal = jsonData.queryHumanTotal;
				var humanTotalArray = new Array();
				if(queryHumanTotal.length >0){
					humanTotalArray.push(queryHumanTotal[0].week1);
					humanTotalArray.push(queryHumanTotal[0].week2);
					humanTotalArray.push(queryHumanTotal[0].week3);
					humanTotalArray.push(queryHumanTotal[0].week4);
					humanTotalArray.push(queryHumanTotal[0].week5);
					humanTotalArray.push(queryHumanTotal[0].week6);
				}
				optionEmp.series[0].data=humanTotalArray;
				optionEmp.yAxis.min = (queryHumanTotal[0].week1*0.98).toFixed(0);
				myChartEmp.setOption(optionEmp);
				
				var queryLeaveHuman = jsonData.queryLeaveHuman;
				var leaveHumanArray = [0,0,0,0,0,0];
				if(queryLeaveHuman.length > 0){
					for(var z = 0; z < queryLeaveHuman.length; z++){
						var humanInfo =  queryLeaveHuman[z];
						var weektime = humanInfo.week_time;
						var humancount = humanInfo.count;	
						var index = jQuery.inArray(weektime,six_week_data_array);
						leaveHumanArray[index] = humancount;
					}
				}
				option7.series[0].data=leaveHumanArray;
				var queryToPostHuman = jsonData.queryToPostHuman;
				var toPostHumanArray = [0,0,0,0,0,0];
				if(queryToPostHuman.length > 0){
					for(var z = 0; z < queryToPostHuman.length; z++){
						var humanInfo =  queryToPostHuman[z];
						var weektime = humanInfo.week_time;
						var humancount = humanInfo.count;
						var index = jQuery.inArray(weektime,six_week_data_array);
						toPostHumanArray[index] = humancount;
					}
				}
				option7.series[1].data=toPostHumanArray;
				myChart7.setOption(option7);
				
				//店长
				var queryDianzhangTotal = jsonData.queryDianzhangTotal;
				var dianzhangTotalArray = new Array();
				if(queryHumanTotal.length >0){
					dianzhangTotalArray.push(queryDianzhangTotal[0].a);
					dianzhangTotalArray.push(queryDianzhangTotal[0].b);
					dianzhangTotalArray.push(queryDianzhangTotal[0].c);
					dianzhangTotalArray.push(queryDianzhangTotal[0].d);
					dianzhangTotalArray.push(queryDianzhangTotal[0].e);
					dianzhangTotalArray.push(queryDianzhangTotal[0].f);
				}
				optionEmpdianzhang.series[0].data=dianzhangTotalArray;
				optionEmpdianzhang.yAxis.min = (queryDianzhangTotal[0].a*0.9).toFixed(0);
				myChartEmpDianzhang.setOption(optionEmpdianzhang);
				
				var queryLeaveDianzhang = jsonData.queryLeaveDianzhang;
				var leaveDianzhangArray = [0,0,0,0,0,0];
				if(queryLeaveDianzhang.length > 0){
					for(var z = 0; z < queryLeaveDianzhang.length; z++){
						var humanInfo =  queryLeaveDianzhang[z];
						var weektime = humanInfo.week_time;
						var humancount = humanInfo.count;	
						var index = jQuery.inArray(weektime,six_week_data_array);
						leaveDianzhangArray[index] = humancount;
					}
				}
				optiondianzhang.series[0].data=leaveDianzhangArray;
				var queryToPostDianzhang = jsonData.queryToPostDianzhang;
				var toPostDianzhangArray = [0,0,0,0,0,0];
				if(queryToPostDianzhang.length > 0){
					for(var z = 0; z < queryToPostDianzhang.length; z++){
						var humanInfo =  queryToPostDianzhang[z];
						var weektime = humanInfo.week_time;
						var humancount = humanInfo.count;
						var index = jQuery.inArray(weektime,six_week_data_array);
						toPostDianzhangArray[index] = humancount;
					}
				}
				optiondianzhang.series[1].data=toPostDianzhangArray;
				myChartDianzhang.setOption(optiondianzhang);
				
				//国安侠
				var queryGuoanxiaTotal = jsonData.queryGuoanxiaTotal;
				var guoanxiaTotalArray = new Array();
				if(queryGuoanxiaTotal.length >0){
					guoanxiaTotalArray.push(queryGuoanxiaTotal[0].a);
					guoanxiaTotalArray.push(queryGuoanxiaTotal[0].b);
					guoanxiaTotalArray.push(queryGuoanxiaTotal[0].c);
					guoanxiaTotalArray.push(queryGuoanxiaTotal[0].d);
					guoanxiaTotalArray.push(queryGuoanxiaTotal[0].e);
					guoanxiaTotalArray.push(queryGuoanxiaTotal[0].f);
				}
				optionEmpguoanxia.series[0].data=guoanxiaTotalArray;
				optionEmpguoanxia.yAxis.min = (queryGuoanxiaTotal[0].a*0.98).toFixed(0);
				myChartEmpGuoanxia.setOption(optionEmpguoanxia);
				
				var queryLeaveGuoanxia = jsonData.queryLeaveGuoanxia;
				var leaveGuoanxiaArray = [0,0,0,0,0,0];
				if(queryLeaveGuoanxia.length > 0){
					for(var z = 0; z < queryLeaveGuoanxia.length; z++){
						var humanInfo =  queryLeaveGuoanxia[z];
						var weektime = humanInfo.week_time;
						var humancount = humanInfo.count;	
						var index = jQuery.inArray(weektime,six_week_data_array);
						leaveGuoanxiaArray[index] = humancount;
					}
				}
				optionguoanxia.series[0].data=leaveGuoanxiaArray;
				var queryToPostGuoanxia = jsonData.queryToPostGuoanxia;
				var toPostGuoanxiaArray = [0,0,0,0,0,0];
				if(queryToPostGuoanxia.length > 0){
					for(var z = 0; z < queryToPostGuoanxia.length; z++){
						var humanInfo =  queryToPostGuoanxia[z];
						var weektime = humanInfo.week_time;
						var humancount = humanInfo.count;
						var index = jQuery.inArray(weektime,six_week_data_array);
						toPostGuoanxiaArray[index] = humancount;
					}
				}
				optionguoanxia.series[1].data=toPostGuoanxiaArray;
				myChartGuoanxia.setOption(optionguoanxia);
				
				//专员
				var queryZhuanyuanTotal = jsonData.queryZhuanyuanTotal;
				var zhuanyuanTotalArray = new Array();
				if(queryZhuanyuanTotal.length >0){
					zhuanyuanTotalArray.push(queryZhuanyuanTotal[0].a);
					zhuanyuanTotalArray.push(queryZhuanyuanTotal[0].b);
					zhuanyuanTotalArray.push(queryZhuanyuanTotal[0].c);
					zhuanyuanTotalArray.push(queryZhuanyuanTotal[0].d);
					zhuanyuanTotalArray.push(queryZhuanyuanTotal[0].e);
					zhuanyuanTotalArray.push(queryZhuanyuanTotal[0].f);
				}
				optionEmpzhuanyuan.series[0].data=zhuanyuanTotalArray;
				optionEmpzhuanyuan.yAxis.min = (queryZhuanyuanTotal[0].a*0.98).toFixed(0);
				myChartEmpZhuanyuan.setOption(optionEmpzhuanyuan);
				
				var queryLeaveZhuanyuan = jsonData.queryLeaveZhuanyuan;
				var leaveZhuanyuanArray = [0,0,0,0,0,0];
				if(queryLeaveZhuanyuan.length > 0){
					for(var z = 0; z < queryLeaveZhuanyuan.length; z++){
						var humanInfo =  queryLeaveZhuanyuan[z];
						var weektime = humanInfo.week_time;
						var humancount = humanInfo.count;	
						var index = jQuery.inArray(weektime,six_week_data_array);
						leaveZhuanyuanArray[index] = humancount;
					}
				}
				optionzhuanyuan.series[0].data=leaveZhuanyuanArray;
				var queryToPostZhuanyuan = jsonData.queryToPostZhuanyuan;
				var toPostZhuanyuanArray = [0,0,0,0,0,0];
				if(queryToPostZhuanyuan.length > 0){
					for(var z = 0; z < queryToPostZhuanyuan.length; z++){
						var humanInfo =  queryToPostZhuanyuan[z];
						var weektime = humanInfo.week_time;
						var humancount = humanInfo.count;
						var index = jQuery.inArray(weektime,six_week_data_array);
						toPostZhuanyuanArray[index] = humancount;
					}
				}
				optionzhuanyuan.series[1].data=toPostZhuanyuanArray;
				myChartZhuanyuan.setOption(optionzhuanyuan);
				
				
				
				//综合管理副店长
				var queryZongguanTotal = jsonData.queryZongguanTotal;
				var zongguanTotalArray = new Array();
				if(queryZongguanTotal.length >0){
					zongguanTotalArray.push(queryZongguanTotal[0].a);
					zongguanTotalArray.push(queryZongguanTotal[0].b);
					zongguanTotalArray.push(queryZongguanTotal[0].c);
					zongguanTotalArray.push(queryZongguanTotal[0].d);
					zongguanTotalArray.push(queryZongguanTotal[0].e);
					zongguanTotalArray.push(queryZongguanTotal[0].f);
				}
				optionEmpzongguan.series[0].data=zongguanTotalArray;
				optionEmpzongguan.yAxis.min = (queryZongguanTotal[0].a*0.98).toFixed(0);
				myChartEmpZongguan.setOption(optionEmpzongguan);
				
				var queryLeaveZongguan = jsonData.queryLeaveZongguan;
				var leaveZongguanrray = [0,0,0,0,0,0];
				if(queryLeaveZongguan.length > 0){
					for(var z = 0; z < queryLeaveZongguan.length; z++){
						var humanInfo =  queryLeaveZongguan[z];
						var weektime = humanInfo.week_time;
						var humancount = humanInfo.count;	
						var index = jQuery.inArray(weektime,six_week_data_array);
						leaveZongguanrray[index] = humancount;
					}
				}
				optionzongguan.series[0].data=leaveZongguanrray;
				var queryToPostZongguan = jsonData.queryToPostZongguan;
				var toPostZongguanArray = [0,0,0,0,0,0];
				if(queryToPostZongguan.length > 0){
					for(var z = 0; z < queryToPostZongguan.length; z++){
						var humanInfo =  queryToPostZongguan[z];
						var weektime = humanInfo.week_time;
						var humancount = humanInfo.count;
						var index = jQuery.inArray(weektime,six_week_data_array);
						toPostZongguanArray[index] = humancount;
					}
				}
				optionzongguan.series[1].data=toPostZongguanArray;
				myChartZongguan.setOption(optionzongguan);
			}
		});
		doManager("humanresourcesManager","getWeekPoint",null,function(data,textStatus,XmlHttpRequest){
			if (data.result) {
				var jsonData = $.fromJSON(data.data);
				var queryWeekPoint = jsonData.queryWeekPoint;
				if(queryWeekPoint.length > 0){
					var stringdata = "";
					for (var i = 0; i < queryWeekPoint.length; i++){
						var weekdata = queryWeekPoint[i];
						var chouyunhezuo = weekdata.chouyunhezuo;
						var chouziying = weekdata.chouziying;
						var cityname = weekdata.cityname;
						var dianzhang = weekdata.dianzhang;
						var fudianzhang = weekdata.fudianzhang;
						var fuwu = weekdata.fuwu;
						var guoanxia = weekdata.guoanxia;
						var leavecount = weekdata.leavecount;
						var topostcount = weekdata.topostcount;
						var yunhezuo = weekdata.yunhezuo;
						var yunziying = weekdata.yunziying;
						var zonghe = weekdata.zonghe;
						var personCount = dianzhang+fudianzhang+guoanxia+zonghe;
						var storeCount = yunziying+yunhezuo;
						var avgPerson = 0;
						if(storeCount != 0){
							avgPerson = personCount/storeCount;
						}
						stringdata += '<tr><td title="'+cityname+'">'+cityname+'</td><td>'+yunziying+'</td><td>'+yunhezuo+'</td><td>'+chouziying+'</td><td>'+chouyunhezuo+'</td><td>'+dianzhang+'</td><td>'+fudianzhang+'</td>'+
						'<td>'+guoanxia+'</td><td>'+fuwu+'</td><td>'+zonghe+'</td><td  class="text-red">'+personCount+'</td><td>'+avgPerson.toFixed(2)+'</td><td>'+topostcount+'</td><td>'+leavecount+'</td></tr>';
					}
					$("#weekcount").append(stringdata);
				}
			}
		});
	}
	
	var areaInfo = function(){
		var myChart16 = echarts.init(document.getElementById('main16'));
		var myChart17 = echarts.init(document.getElementById('main17'));
		myChart16.setOption(option16);
		myChart17.setOption(option17);
		 doManager("AreaManager", "selectAllArea",null,
		            function(data, textStatus, XMLHttpRequest) {
		                if (data.result) {
		                    var resultJson = JSON.parse(data.data);
		                    var storeArray = new Array();
		                    var cityAreaArray = new Array();
		                    var xDataArray16 = new Array();
		                    var xDataArray17 = new Array();
		                    var areaArray = resultJson.queryAreaCountByCity;
		                    var areaAllInfo = resultJson.queryAllAreaCount;
		                    var areaData = areaAllInfo[0];
		                    var storeCount = areaData.store_count;
		                    var weekCount = areaData.thisweek_count-areaData.preweek_count < 0 ? 0 : areaData.thisweek_count-areaData.preweek_count;
		                    var empCount = areaData.emp_count;
		                    var areaCount = areaData.area_count;
		                    var date = new Date();
		                    $("#nowdate").html(date.format("yyyy-MM-dd"));
		                    $("#storeCount").html(storeCount);
		                    $("#weekCount").html(weekCount);
		                    $("#areaCount").html(areaCount);
		                    $("#empCount").html(empCount);
		                    if(areaArray.length >0){
		                    	var stringData = "";
		                    	for(var i = 0; i < areaArray.length; i++){
		                    		var cityareaData =  areaArray[i];
		                    		var area_count = cityareaData.area_count;
		                    		var city_name = cityareaData.city_name;
		                    		var employee_count = cityareaData.employee_count;
		                    		var store_count = cityareaData.store_count;
		                    		xDataArray16.push(city_name+"\n"+store_count);
		                    		storeArray.push(store_count);
		                    		cityAreaArray.push(area_count);
		                    		xDataArray17.push(city_name+"\n"+area_count);
		                    		stringData+='<tr><td bgcolor="#ff0">'+city_name+'</td><td>'+store_count+'</td><td>'+area_count+'</td><td>'+employee_count+'</td></tr>';
		                    		if(i == areaArray.length - 1){
		                    			stringData+='<tr><td bgcolor="#ff0">'+'合计'+'</td><td>'+storeCount+'</td><td>'+areaCount+'</td><td>'+empCount+'</td></tr>';
		                    		}
		                    	}
		                    }
		                    $("#areaInfo").append(stringData);
		                    //option11.xAxis[0].data
		                    option16.xAxis[0]["data"] = xDataArray16;
		                    option17.xAxis[0]["data"] = xDataArray17;
		                    option16.series[0].data=storeArray;
		    				myChart16.setOption(option16);
		    				option17.series[0].data=cityAreaArray;
		    				myChart17.setOption(option17);
		                    
		                }
		         });
	}
	
	var customerInfo = function(){

		var myChart22 = echarts.init(document.getElementById('main22'));
		var myChart23 = echarts.init(document.getElementById('main23'));
		myChart22.setOption(option22);
		myChart23.setOption(option23);
		var reqestParameter = {
	        }
		//7天
        doManager("DynamicManager", "getWeekCustomerOrderRate",[reqestParameter],
            function(data, textStatus, XMLHttpRequest) {
                if (data.result) {
                    var resultJson = JSON.parse(data.data);
                    var data = [];
                  	var data1 = [];
                    var data2 = [];
                    var data3 = [];
                    $.each(eval(resultJson['lst_data']), function (idx, val) {
                    	var rate = "";
                    	var str = "";
                    	if(parseInt(val['pay_cus_count'])==0){
                    		rate = 0;
                    	}else{
                    		str = (((parseInt(val['pay_cus_count'])-parseInt(val['new_cus_count']))/parseInt(val['pay_cus_count'])*100)+'');
                    		rate = str.substring(0,str.lastIndexOf(".")+2);
                    	}
                    	data.push(val['week_date']);
                    	data1.push(val['new_cus_count']);
                		data2.push(val['pay_cus_count']);
                		data3.push(rate);
                    });
                    option23.xAxis.data = data.reverse();
                    option23.series[0].data = data3.reverse();
                    option23.series[1].data = data1.reverse();
                    option23.series[2].data = data2.reverse();
                	//customerNewChartOption.title.text="近7天客流趋势";
                	option23.yAxis[1].min = 0;
                	myChart23.setOption(option23);
                    
                }
         });
		
		// 查询拉新和消费用户量 30天
        doManager("DynamicManager", "getNewMonthUserCount",[reqestParameter],
            function(data, textStatus, XMLHttpRequest) {
                if (data.result) {
                    var resultJson = JSON.parse(data.data);
                    var data = [];
                  	var data1 = [];
                    var data2 = [];
                    $.each(eval(resultJson.new_month_userCount['lst_data']), function (idx, val) {
                    	data.push(val['week_date']);
                    	data1.push(val['new_cus_count']);
                		data2.push(val['pay_cus_count']);
                    });
                    option22.xAxis[0].data = data.reverse();
                    option22.series[0].data = data1.reverse();
                    option22.series[1].data = data2.reverse();
                    option22.title.text="近30天客流趋势";
                    myChart22.setOption(option22);
                }
            });
     }
	
	var GMVInfo = function(){
		var shareChartStatDto=null;
		chartresize2();
		chartresize3();
		chartresize4();
		search_manual_k();
		
	}
	
	var hourData = [];  //0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
	  function initHourData(){
	 		doManager("chartStatManager","queryTurnoverByHour",[shareChartStatDto],
	 			function(data,textStatus,XmlHttpRequest){
	 				if (data.result) {
	 					var nowHour = new Date().getHours();
	 					var hourData=new Array(nowHour+1).join(0).split('');
	 					var jsonData = $.fromJSON(data.data);
	 					$(jsonData).each(function(index,element){
	 						hourData[parseInt(element.time)]=element.turnover;
	                     });
	 					//option26.xAxis[0].data=nowHour;
	 					option26.series[0].data=hourData;
	 					myChart26.setOption(option26);
	 				}
	 		});
	 	} 
	  
	  function initDayData(){
		  var curDay = getCurDay();
		  doManager("chartStatManager","queryTurnoverByDay",[shareChartStatDto],
				function(data,textStatus,XmlHttpRequest){
					if (data.result) {
						var jsonData = $.fromJSON(data.data);
						yData=new Array(curDay+1).join(0).split('');
						$(jsonData).each(function(index,element){
							xData[index]=element.day_time;
							yData[index]=element.turnover;
		                });
						/*chart27_option.xAxis[0].data=xData;
						chart27_option.series[0].data=yData;*/
						chart27.setOption(chart27_option);
					}
			});
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
	  
//	  var weekData = [];  //0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
	  function initWeekData(){
		  weekData = new Array(xData_week.length+1).join(0).split('');
			doManager("chartStatManager","queryTurnoverByWeek",[shareChartStatDto],
				function(data,textStatus,XmlHttpRequest){
					if (data.result) {
						var jsonData = $.fromJSON(data.data);
						$(jsonData).each(function(index,element){
							var dateTemp = new Date(parseInt(element.week_time)).format("yyyy-MM-dd");
							var indexTemp = jQuery.inArray(dateTemp,xData_week);
							weekData[indexTemp]=element.week_amount;
	                    });
						chart28.setOption(chart28_option);
					}
			});
		} 
	  
	  Date.prototype.toLocaleString = function() {
	      return this.getFullYear() + "-" + (this.getMonth() + 1) + "-" + this.getDate();
	  }; 
	  
	  function initMonthData(){
		  var now = new Date();
		  var curMonth = now.getFullYear()+'-'+(now.getMonth()+1);
		    var months = getYearAndMonth('2017-12-1',curMonth);
			doManager("chartStatManager","queryTurnoverByMonth",[shareChartStatDto],
				function(data,textStatus,XmlHttpRequest){
					if (data.result) {
						var jsonData = $.fromJSON(data.data);
						xData_month = months;
						yData_month = new Array(months.length+1).join(0).split('');
						$(jsonData).each(function(index,element){
							var indexTemp = jQuery.inArray(element.month_time,months);
							yData_month[indexTemp]=element.month_amount;
	               		});
						chart29_option.xAxis[0].data=xData_month;
						chart29_option.series[0].data=yData_month;
					}
			});
			
			doManager("chartStatManager","queryTargetByMonth",[shareChartStatDto],
				function(data,textStatus,XmlHttpRequest){
					if (data.result) {
						var jsonData = $.fromJSON(data.data);
						yData_month_target = new Array(months.length+1).join(0).split('');
						$(jsonData).each(function(index,element){
							var indexTemp = jQuery.inArray(element.month_time,months);
							yData_month_target[indexTemp]=element.month_amount;
	               		});
						chart29_option.series[1].data=yData_month_target;
						chart29.setOption(chart29_option);
					}
			});
		} 
	  
	  
	//搜索
		function search_manual_k(){
			  xData_week = xData_week_temp();
		 	  shareChartStatDto = getShareParam();
			  //分时GMV
			  hourData = [];
			  initHourData();
//	 		  setInterval(initHourData, 5000);
			  //日GMV
			  xData = [];
	   		  yData = [];
			  initDayData();
			  //周GMV
			  weekData = [];
			  initWeekData();
			  //月GMV
			  xData_month = [];
			  yData_month = [];
			  yData_month_target = [];
			  initMonthData();
			  
	    }
		
		var iCheckStr = "cb";
		function getShareParam(){
			 /*var cLabel=0;
			 var smallBLabel=0;
			 if(iCheckStr=='cb') {
				 cLabel=1;
				 smallBLabel=1;
			 }
			 var storeno = $("#store_id_manual").val()==""?null:$("#store_id_manual").val();
			  var cityName = $("#citySelect  option:selected").val();
			  if(cityName!="" && cityName!=undefined){
			    if(cityName=="黔东南苗族侗族自治州"){
			    	cityName="黔东南州";
			  	}else{
			    	cityName=cityName+"市";
			    	}
			  }else{
			    cityName = null;
			  }
		      var deptname = $("#deptSelect option:selected").html()==""?null:$("#deptSelect option:selected").html();
		      if(deptname!="" && deptname=="全部事业群"){
		    	  deptname = null;
		      }
		      var channelname = $("#channelSelect option:selected").html()==""?null:$("#channelSelect option:selected").html();
		      if(channelname!="" && channelname=="全部频道"){
		    	  channelname = null;
		      }*/
			  
			  return shareChartStatDto = {				
					storeno:null,
					cityname:null,
					deptname:null,
					channelname:null,
					cLabel:1,
					smallBLabel:1,
					maxBLabel:0
				}
		}
		
		
		function getCurDay(){
			  var dateArr = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);  
			  var date = new Date();  
			  var day = date.getDate();  
			  var month = date.getMonth(); //getMonth()是从0开始  
			  var year = date.getFullYear();  
			  var result = 0;  
			  for ( var i = 0; i < month; i++) {  
			      result += dateArr[i];  
			  }  
			  result += day;  
			  //判断是否闰年  
			  if (month > 1 && (year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {  
			      result += 1;  
			  }  
			  return result;
		  }
		
		
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
		
	var storeActivitiesInfo = function(){
		doManager("storeActivitiesManager","getNewStoreActivtiesInfo",null,
				function(data,textStatus,XmlHttpRequest){
			if(data.result){
				var jsonData = $.fromJSON(data.data);
				var newActivitiesInfo = jsonData.newActivitiesInfo;
				var findConVillageCountOfCity = jsonData.findConVillageCountOfCity;
				var citynameArray = new Array();
				var number_of_activitiy_array = new Array();
				var x_data_option18 = new Array();
				var activities_sum_array = new Array();
				var count_sum = 0;
				//去掉覆盖社区
				/*if(findConVillageCountOfCity.length > 0){
					for(var i = 0;i < findConVillageCountOfCity.length; i++){
						var villageInfo = findConVillageCountOfCity[i];
						var count = villageInfo.count;
						var cityname = villageInfo.city_name;
						$('#pf-fre1 tr').eq(0).find("th").each(function(z,t){
							if(cityname == $(t).html()){
								var m = z-1;
								count_sum += count;
								$('#pf-fre1 tr').eq(1).find("td:eq("+m+")").text(count);
							}
						});
					}
				}
				$('#pf-fre1 tr').eq(1).find("td:last").text(count_sum);
				activities_sum_array.push(count_sum);*/
				//x_data_option18.push('覆盖社区');
				if(newActivitiesInfo.length > 0){
					var secondTr = $("#pf-fre1 tr").eq(1);
					var table =document.getElementById("pf-fre1");
				    var cellnumber = table.rows[0].cells.length;
				    var numbers_of_activities_sum = 0;
				    var numbers_of_single_activitie_sum = 0;
				    var public_welfare_activities_sum = 0;
				    var recreational_activities_sum = 0;
				    var volunteer_activity_sum = 0;
				    var avtivities_count_sum = 0;
				    var store_independent_activitie_sum = 0,store_numbers_of_activities_sum=0,
					store_numbers_of_single_activitie_sum=0,store_independent_activitie_price_sum=0,total_activities_count_sum=0;
				    for(var i = 0; i < newActivitiesInfo.length; i++){
						var avtivities = newActivitiesInfo[i];
						var numbers_of_activities = avtivities.numbers_of_activities;
						numbers_of_activities_sum += numbers_of_activities;
						var numbers_of_single_activitie = avtivities.numbers_of_single_activitie;
						numbers_of_single_activitie_sum += numbers_of_single_activitie;
						var public_welfare_activities = avtivities.public_welfare_activities;
						public_welfare_activities_sum += public_welfare_activities;
						var recreational_activities = avtivities.recreational_activities;
						recreational_activities_sum += recreational_activities;
						var volunteer_activity = avtivities.volunteer_activity;
						volunteer_activity_sum += volunteer_activity;
						var avtivities_count = avtivities.avtivities_count;
						avtivities_count_sum += avtivities_count;
						
						
						var store_independent_activitie = avtivities.store_independent_activitie;
						store_independent_activitie_sum += store_independent_activitie;
						var store_numbers_of_activities = avtivities.store_numbers_of_activities;
						var store_numbers_of_single_activitie = avtivities.store_numbers_of_single_activitie;
						var store_independent_activitie_price = avtivities.store_independent_activitie_price;
						var total_activities_count = avtivities.total_activities_count;
						store_numbers_of_activities_sum += store_numbers_of_activities;
						store_numbers_of_single_activitie_sum += store_numbers_of_single_activitie;
						store_independent_activitie_price_sum += store_independent_activitie_price;
						total_activities_count_sum += total_activities_count;
						var cityname = avtivities.cityname;
						citynameArray.push(cityname.length >4?cityname.substring(0,5)+'\n'+cityname.substring(5,cityname.length):cityname);
						number_of_activitiy_array.push(numbers_of_single_activitie);
						$("#pf-fre1").find("tr:eq(0)").children("th:gt(0):lt("+(cellnumber-2)+")").each(function(x,t){
				    	    var col_index = x;
				    	    if(cityname == $(t).html()){
				    	    	$(t).parent().nextAll().each(function(z,t){
						    	    if(z==0)$(t).find("td:eq("+col_index+")").text(recreational_activities);
						    	    if(z==1)$(t).find("td:eq("+col_index+")").text(public_welfare_activities);
						    	    if(z==2)$(t).find("td:eq("+col_index+")").text(volunteer_activity);
						    	    if(z==3)$(t).find("td:eq("+col_index+")").text(avtivities_count);
						    	    if(z==4)$(t).find("td:eq("+col_index+")").text(numbers_of_activities);
						    	    if(z==5)$(t).find("td:eq("+col_index+")").text(numbers_of_single_activitie);
						    	    if(z==6)$(t).find("td:eq("+col_index+")").text(store_independent_activitie);
						    	    if(z==7)$(t).find("td:eq("+col_index+")").text(store_numbers_of_activities);
						    	    if(z==8)$(t).find("td:eq("+col_index+")").text(store_numbers_of_single_activitie);
						    	    if(z==9)$(t).find("td:eq("+col_index+")").text(store_independent_activitie_price);
						    	    if(z==10)$(t).find("td:eq("+col_index+")").text(total_activities_count);
					       		});
				    	    }
				       });
						if(i == newActivitiesInfo.length - 1){
							
							 $('#pf-fre1 tr').each(function(x,t){
								  if(x == 1)$(t).find("td:last").text(recreational_activities_sum);
								  if(x == 2)$(t).find("td:last").text(public_welfare_activities_sum);
								  if(x == 3)$(t).find("td:last").text(volunteer_activity_sum);
								  if(x == 4)$(t).find("td:last").text(avtivities_count_sum);
								  if(x == 5)$(t).find("td:last").text(numbers_of_activities_sum);
								  if(x == 6)$(t).find("td:last").text(numbers_of_single_activitie_sum);
								  if(x == 7)$(t).find("td:last").text(store_independent_activitie_sum);
								  if(x == 8)$(t).find("td:last").text(store_numbers_of_activities_sum);
								  if(x == 9)$(t).find("td:last").text(store_numbers_of_single_activitie_sum);
								  if(x == 10)$(t).find("td:last").text(store_independent_activitie_price_sum);
								  if(x == 11)$(t).find("td:last").text(total_activities_count_sum);
								  
							 });
							 
							  activities_sum_array.push(recreational_activities_sum);
							  activities_sum_array.push(public_welfare_activities_sum);
							  activities_sum_array.push(volunteer_activity_sum);
							  activities_sum_array.push(avtivities_count_sum);
							  x_data_option18.push('文娱类活动');
							  x_data_option18.push('公益类活动');
							  x_data_option18.push('志愿者活动');
							  x_data_option18.push('活动小计');
						}
					}
				    option18.series[0].data=activities_sum_array;
					option18.xAxis[0]["data"] = x_data_option18;
					myChart18.setOption(option18);
					option19.series[0].data=number_of_activitiy_array.reverse();
					option19.xAxis[0]["data"] = citynameArray.reverse();
					myChart19.setOption(option19);
				}
			}
		});
	}
	
	
	function getStoreCoverPerson(){
		doManager("storeCoverPersonManager","getNewStoreCoverPerson",null,
				function(data,textStatus,XmlHttpRequest){
			if(data.result){
				var jsonData = $.fromJSON(data.data);
				var storeCoverPersonInfo = jsonData.storeCoverPersonInfo;
				var storeCountOfcity = jsonData.storeCountOfcity;
				var storeCountOfArea = jsonData.storeCountOfArea;
				var areacount = 0
				if(storeCountOfArea.length >0){
					var areacountInfo = storeCountOfcity[0];
					areacount = areacountInfo.count;
				}
				
				var count_sum = 0;
				if(storeCountOfcity.length > 0){
					for(var i = 0;i < storeCountOfcity.length; i++){
						var cityinfo = storeCountOfcity[i];
						var count = cityinfo.count;
						var cityname = cityinfo.city_name;
						$('#pfoperate1_1 tr').eq(0).find("th").each(function(z,t){
							if(cityname == $(t).html()){
								var m = z-1;
								count_sum += count;
								$('#pfoperate1_1 tr').eq(1).find("td:eq("+m+")").text(count);
							}
						});
					}
				}
				$('#pfoperate1_1 tr').eq(1).find("td:eq(13)").text(count_sum);
				var arearate = (areacount/count_sum)*100
				$('#pfoperate1_1 tr').eq(1).find("td:last").text(arearate.toFixed(2));
				var citynameArray = new Array();
				//店均社区关键人数量
				var avg_community_person_array = new Array();
				//店均微信群数量
				var avg_wechant_crowd_array = new Array();
				//单个微信群人数
				var crowd_person_count_array = new Array();
				//活跃人群占比
				var wechant_accounted_for_crowd_array = new Array();
				//社区关键人构成分析：饼状图
				var dataArray = new Array();
				//合计值存放
				var shequguanjianren;//店均社区关键人
				var dianjunwexinqun;
				var dangeweixinqun;
				var huoyuerenqun;
				if(storeCoverPersonInfo.length > 0){
					var table1 =document.getElementById("pfoperate1");
			        var table1_1 =document.getElementById("pfoperate1_1");
			        var cellnumber1 = table1.rows[0].cells.length;
			        var cellnumber1_1 = table1_1.rows[0].cells.length;
				    for(var i = 0; i < storeCoverPersonInfo.length; i++){
						var personInfo = storeCoverPersonInfo[i];
						//城市
						var cityname = personInfo.cityname;
						//社区关键人表
						var community_person = personInfo.community_person;//社区关键人数量
						var avg_community_person = personInfo.avg_community_person;//店均社区关键人数量
						var civil_servants = personInfo.civil_servants;//政府从业人员
						var general_person = personInfo.general_person;//普通居民
						var folk_organization = personInfo.folk_organization;//民间组织人员
						var community_businesses = personInfo.community_businesses;//社区商户
						//微信群表
						var wechant_crowd = personInfo.wechant_crowd;//微信群数量
						var avg_wechant_crowd = personInfo.avg_wechant_crowd;//店均微信群数量
						var crowd_persons_count = personInfo.crowd_persons_count;//微信群内客户容量
						var crowd_person_count = personInfo.crowd_person_count;//单个微信群人数
						var interactive_person_count = personInfo.interactive_person_count;//与门店人员有互动的客户数量
						var interactive_person_count_store = personInfo.interactive_person_count_store;//单个微信群互动人数
						var wechant_accounted_for_crowd = personInfo.wechant_accounted_for_crowd;//微信互动活跃人群占比
						if(cityname == '合计'){
							option21.series[0].data[0].value = civil_servants;
							option21.series[0].data[1].value = general_person;
							option21.series[0].data[2].value = folk_organization;
							option21.series[0].data[3].value = community_businesses;
							//店均社区关键人数量
							shequguanjianren = avg_community_person;
							dianjunwexinqun = avg_wechant_crowd;
							dangeweixinqun = crowd_person_count;
							huoyuerenqun = wechant_accounted_for_crowd;
						}
						if(cityname != '环比（%）' && cityname != '合计'){
							citynameArray.push(cityname.length >4?cityname.substring(0,5)+'\n'+cityname.substring(5,cityname.length):cityname);
							avg_community_person_array.push(avg_community_person);
							avg_wechant_crowd_array.push(avg_wechant_crowd);
							crowd_person_count_array.push(crowd_person_count);
							wechant_accounted_for_crowd_array.push(wechant_accounted_for_crowd);
						}
						$("#pfoperate1").find("tr:eq(0)").children("th:gt(0):lt("+(cellnumber1-1)+")").each(function(x,t){
				    	    var col_index = x;
				    	    if(cityname == $(t).html()){
				    	    	if(cityname == '环比（%）'){
				    	    		$(t).parent().nextAll().each(function(z,t){
							    	    if(z==0)$(t).find("td:eq("+col_index+")").text(community_person/100);
							    	    if(z==1)$(t).find("td:eq("+col_index+")").text(avg_community_person/100);
							    	    if(z==2)$(t).find("td:eq("+col_index+")").text(civil_servants/100);
							    	    if(z==3)$(t).find("td:eq("+col_index+")").text(general_person/100);
							    	    if(z==4)$(t).find("td:eq("+col_index+")").text(folk_organization/100);
							    	    if(z==5)$(t).find("td:eq("+col_index+")").text(community_businesses/100);
						       		});
				    	    	}else{
				    	    		$(t).parent().nextAll().each(function(z,t){
							    	    if(z==0)$(t).find("td:eq("+col_index+")").text(community_person);
							    	    if(z==1)$(t).find("td:eq("+col_index+")").text(avg_community_person);
							    	    if(z==2)$(t).find("td:eq("+col_index+")").text(civil_servants);
							    	    if(z==3)$(t).find("td:eq("+col_index+")").text(general_person);
							    	    if(z==4)$(t).find("td:eq("+col_index+")").text(folk_organization);
							    	    if(z==5)$(t).find("td:eq("+col_index+")").text(community_businesses);
						       		});
				    	    	}
				    	    }
				       });
						$("#pfoperate1_1").find("tr:eq(0)").children("th:gt(0):lt("+(cellnumber1_1-1)+")").each(function(x,t){
				    	    var col_index = x;
				    	    if(cityname == $(t).html()){
				    	    	if(cityname == '环比（%）'){
				    	    		$(t).parent().nextAll().each(function(z,t){
							    	    if(z==1)$(t).find("td:eq("+col_index+")").text(wechant_crowd/100);
							    	    if(z==2)$(t).find("td:eq("+col_index+")").text(avg_wechant_crowd/100);
							    	    if(z==3)$(t).find("td:eq("+col_index+")").text(crowd_persons_count/100);
							    	    if(z==4)$(t).find("td:eq("+col_index+")").text(crowd_person_count/100);
							    	    if(z==5)$(t).find("td:eq("+col_index+")").text(interactive_person_count/100);
							    	    if(z==6)$(t).find("td:eq("+col_index+")").text(interactive_person_count_store/100);
							    	    if(z==7)$(t).find("td:eq("+col_index+")").text(wechant_accounted_for_crowd/100);
						       		});
				    	    	}else{
				    	    		$(t).parent().nextAll().each(function(z,t){
							    	    if(z==1)$(t).find("td:eq("+col_index+")").text(wechant_crowd);
							    	    if(z==2)$(t).find("td:eq("+col_index+")").text(avg_wechant_crowd);
							    	    if(z==3)$(t).find("td:eq("+col_index+")").text(crowd_persons_count);
							    	    if(z==4)$(t).find("td:eq("+col_index+")").text(crowd_person_count);
							    	    if(z==5)$(t).find("td:eq("+col_index+")").text(interactive_person_count);
							    	    if(z==6)$(t).find("td:eq("+col_index+")").text(interactive_person_count_store);
							    	    if(z==7)$(t).find("td:eq("+col_index+")").text(wechant_accounted_for_crowd);
						       		});
				    	    	}
				    	    }
				       });
						
					}
				    citynameArray.push("合计");
				    avg_community_person_array.push(shequguanjianren);
				    avg_wechant_crowd_array.push(dianjunwexinqun);
				    crowd_person_count_array.push(dangeweixinqun);
				    wechant_accounted_for_crowd_array.push(huoyuerenqun);
				    option20.series[0].data=avg_community_person_array;
					option20.xAxis[0]["data"] = citynameArray;
					myChart20.setOption(option20);
					option24.series[0].data=avg_wechant_crowd_array;
					option24.series[1].data=crowd_person_count_array;
					option24.series[2].data=wechant_accounted_for_crowd_array;
					option24.xAxis[0]["data"] = citynameArray;
					myChart24.setOption(option24);
					myChart21.setOption(option21);
				}
			}
		});		
	}
	
	function employeeCustomer(){
		var myChart40 = echarts.init(document.getElementById('main40'));
		var citynameArray = new Array();
		var option40 = {
		  title : {text: "国安侠人均消费人数周结",textAlign:'left',textStyle:{fontSize:"16",fontWeight:"normal"}},
		  legend: {
		        type: 'scroll',
		        orient: 'vertical',
		        right: 30,
		        top: 30,
		        bottom: 20,
		        data:citynameArray
		  },
		  //color:colors,
		  grid: {left: '2%',top:"12%",bottom: "15%",right:"20%",containLabel: true},
		  tooltip : { trigger: 'axis',axisPointer : { type : 'shadow'}},
		  xAxis: [
		    {
		      type: 'category',
		      axisLine: { show: true,lineStyle:{ color:'#6173A3' }},
		      axisLabel:{interval:0,textStyle:{color:'#9ea7c4',fontSize:12} },
		      axisTick : {show: false},
		      data:[],
		    },
		  ],
		  yAxis: [
		    {
		      axisTick : {show: false},
		      splitLine: {show:true},
		      axisLabel:{textStyle:{color:'#9ea7c4',fontSize:14} },
		      axisLine: { show: true,lineStyle:{ color:'#6173A3'}},
		    },
		  ],
		  series:[]
		};
		myChart40.setOption(option40);
		doManager("dynamicManager","getsixMonthCustomer",null,
				function(data,textStatus,XmlHttpRequest){
			if(data.result){
				var cityDataArray = new Array();
				var month = new Date().getMonth()+1;
				var datetime = new Date().getDate();
				var monthArray = getmonthArray();
				var yearmonth = new Date().format("yyyyMM");
				var jsonData = $.fromJSON(data.data).sixMonthCustomer;
				var appendstr = "";
				$("#month_1").html(month-5);$("#month_2").html(month-4);$("#month_3").html(month-3);$("#month_4").html(month-2);$("#month_5").html(month-1);$("#month_6").html(month);
				$("#month_6_1").html(month);$("#one").html(month-3+'月第'+Math.ceil(datetime/7)+'周');$("#two").html(month-2+'月第'+Math.ceil(datetime/7)+'周');$("#three").html(month-1+'月第'+Math.ceil(datetime/7)+'周');
				$("#four").html(month+'月第'+Math.ceil(datetime/7)+'周');
				var xdata = [month-3+'月第'+Math.ceil(datetime/7)+'周',month-2+'月第'+Math.ceil(datetime/7)+'周',month-1+'月第'+Math.ceil(datetime/7)+'周',month+'月第'+Math.ceil(datetime/7)+'周'];
				ydate = [];
				if(jsonData != null &&jsonData.length >0){
					for (var i = 0; i <jsonData.length; i++){
						var index = jQuery.inArray(jsonData[i].cityname,citynameArray);
						if(index == -1){
							var cityDate = {
									"cityname":jsonData[i].cityname,
									"rate":""
								};
							var weekDate = { name:jsonData[i].cityname,
								      type:'line'};
							citynameArray.push(jsonData[i].cityname);
							ydate.push(weekDate);
							cityDataArray.push(cityDate);
						}
					}
					for(var i = 0; i <jsonData.length; i++){
						var empCustomer = jsonData[i];
						var cityname = empCustomer.cityname;
						var index = jQuery.inArray(jsonData[i].cityname,citynameArray);
						var cityData = cityDataArray[index];
						var weekData = ydate[index];
						cityData[empCustomer.month] = empCustomer.customer_count;
						if(empCustomer.week_cus_count != null){
							cityData[empCustomer.month+"_week"] = empCustomer.week_cus_count;
						}
						if(yearmonth == empCustomer.month){
							cityData[empCustomer.month+"_new"] = empCustomer.customer_new_count;
							cityData["rate"] = empCustomer.customer_new_count/empCustomer.customer_count*100;
						}
						
					}
					for(var i = 0; i <cityDataArray.length; i++){
						var cityData = cityDataArray[i];
						appendstr += '<tr><td bgcolor="#ff0">'+cityData.cityname+'</td><td>'
						+(typeof(cityData[monthArray[0]]) == "undefined" ? '' : cityData[monthArray[0]])+'</td><td>'
						+(typeof(cityData[monthArray[1]]) == "undefined" ? '' : cityData[monthArray[1]])+'</td><td>'
						+(typeof(cityData[monthArray[2]]) == "undefined" ? '' : cityData[monthArray[2]])+'</td><td>'
						+(typeof(cityData[monthArray[3]]) == "undefined" ? '' : cityData[monthArray[3]])+'</td><td>'
						+(typeof(cityData[monthArray[4]]) == "undefined" ? '' : cityData[monthArray[4]])+'</td><td>'
						+(typeof(cityData[monthArray[5]]) == "undefined" ? '' : cityData[monthArray[5]])+'</td><td>'
						+(typeof(cityData[monthArray[6]]) == "undefined" ? '' : cityData[monthArray[6]])+'</td><td>'+cityData["rate"].toFixed(2)+'</td><td>'
						+(typeof(cityData[monthArray[7]]) == "undefined" ? '' : cityData[monthArray[7]])+'</td><td>'
						+(typeof(cityData[monthArray[8]]) == "undefined" ? '' : cityData[monthArray[8]])+'</td><td>'
						+(typeof(cityData[monthArray[9]]) == "undefined" ? '' : cityData[monthArray[9]])+'</td><td>'
						+(typeof(cityData[monthArray[10]]) == "undefined" ? '' : cityData[monthArray[10]])+'</td></tr>'
						var weekdate = ydate[i];
						weekdate.data = [(typeof(cityData[monthArray[7]]) == "undefined" ? 0 : cityData[monthArray[7]]),(typeof(cityData[monthArray[8]]) == "undefined" ? 0 : cityData[monthArray[8]]),(typeof(cityData[monthArray[9]]) == "undefined" ? 0 : cityData[monthArray[9]]),(typeof(cityData[monthArray[10]]) == "undefined" ? 0 : cityData[monthArray[10]])];
					}
					$("#empcustomertotal").after(appendstr);
					option40.xAxis[0].data = xdata;
					option40.series = ydate;
					myChart40.setOption(option40);
				}
			}
		});
		
	}
	
	function getmonthArray (){
		var monthArray = new Array();
		var date1 = new Date();
		date1.setMonth(date1.getMonth()-5);
		var month1 = date1.format("yyyyMM");
		var date2 = new Date();
		date2.setMonth(date2.getMonth()-4);
		var month2 = date2.format("yyyyMM");
		var date3 = new Date();
		date3.setMonth(date3.getMonth()-3);
		var month3 = date3.format("yyyyMM");
		var date4 = new Date();
		date4.setMonth(date4.getMonth()-2);
		var month4 = date4.format("yyyyMM");
		var date5 = new Date();
		date5.setMonth(date5.getMonth()-1);
		var month5 = date5.format("yyyyMM");
		var date6 = new Date();
		date6.setMonth(date6.getMonth());
		var month6 = date6.format("yyyyMM");
		monthArray.push(month1+"");
		monthArray.push(month2+"");
		monthArray.push(month3+"");
		monthArray.push(month4+"");
		monthArray.push(month5+"");
		monthArray.push(month6+"");
		monthArray.push(month6+"_new");
		monthArray.push(month3+"_week");
		monthArray.push(month4+"_week");
		monthArray.push(month5+"_week");
		monthArray.push(month6+"_week");
		return monthArray;
	}
	
	function deleteRow(id,index){
		var snapinfo_table = document.getElementById(id); 
		var snapinfo_tr_num = snapinfo_table.rows.length;  
	      for(var i=snapinfo_tr_num-1;i>index;i--){  
	    	  snapinfo_table.deleteRow(i);  
	      }
	}
	
//门店资源统计
	function getStoreResources(){
		doManager("storeResourcesManager","findStoreResourcesByType",null,
				function(data,textStatus,XmlHttpRequest){
			if(data.result){
				/* var snapinfo_table = document.getElementById("storeresources");  
			      var snapinfo_tr_num = snapinfo_table.rows.length;  
			      for(var i=snapinfo_tr_num-1;i>0;i--){  
			    	  snapinfo_table.deleteRow(i);  
			      }*/
				deleteRow("storeresources",0);
				deleteRow("outstoreresources",1);
				deleteRow("storeresources_1",0);
				deleteRow("outstoreresources_1",1);
				var jsonData = $.fromJSON(data.data);
				var storeStr = "";
				var storeinputStr = "";
				var outStoreStr = "";
				var outStoreinputStr = "";
				if(typeof(jsonData.storeResource) != 'undefined'){
					var storeResource = jsonData.storeResource;
					for(var i = 0; i < storeResource.length; i++){
						var info = storeResource[i];
						storeStr += '<tr><td>'+(i+1)+'</td><td>'+info.cityname+'</td><td>'+info.storecount+'</td><td>'+info.home_screen+'</td><td>'+info.home_auxiliary_screen+'</td>'
							+'<td>'+info.outdoor_electronic_screen+'</td><td>'+info.roll_up+'</td><td>'+info.posters+'</td><td>'+info.central_pile_head+'</td>'
							+'<td>'+info.floor_pile_head+'</td><td>'+info.indoor_interactive_area+'</td></tr>';
						storeinputStr += '<tr><td>'+(i+1)+'</td><td>'+info.cityname+'</td><td><input type="text" value="'+info.storecount+'" class="form-control"></td><td><input type="text" value="'+info.home_screen+'" class="form-control"></td>'
							+'<td><input type="text" value="'+info.home_auxiliary_screen+'" class="form-control"></td><td><input type="text" value="'+info.outdoor_electronic_screen+'" class="form-control"></td><td><input type="text" value="'+info.roll_up+'" class="form-control"></td>'
							+'<td><input type="text" value="'+info.posters+'" class="form-control"></td><td><input type="text" value="'+info.central_pile_head+'" class="form-control"></td><td><input type="text" value="'+info.floor_pile_head+'" class="form-control"></td>'
							+'<td><input type="text" value="'+info.indoor_interactive_area+'" class="form-control"></td></tr>';
					}
				}else{
					var storeResourceCity = jsonData.storeResourceCity;
					for(var i = 0; i < storeResourceCity.length; i ++){
						var cityname = storeResourceCity[i].city_name;
						var storecount = storeResourceCity[i].storecount;
						storeStr += '<tr><td>'+(i+1)+'</td><td>'+cityname+'</td><td>'+storecount+'</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>';
						storeinputStr += '<tr><td>'+(i+1)+'</td><td>'+cityname+'</td><td><input type="text" value="'+storecount+'" class="form-control"></td><td><input type="text" value="0" class="form-control"></td>'
							+'<td><input type="text" value="0" class="form-control"></td><td><input value="0" type="text" class="form-control"></td><td><input type="text" value="0" class="form-control"></td>'
							+'<td><input type="text" value="0" class="form-control"></td><td><input value="0" type="text" class="form-control"></td><td><input type="text" value="0" class="form-control"></td>'
							+'<td><input type="text" value="0" class="form-control"></td></tr>';
					}
				}
				if(typeof(jsonData.outStoreResource) != 'undefined'){
					var outStoreResource = jsonData.outStoreResource;
					for(var i = 0; i < outStoreResource.length; i++){
						var info = outStoreResource[i];
						outStoreStr += '<tr><td>'+(i+1)+'</td><td>'+info.cityname+'</td><td>'+info.storecount+'</td><td>'+info.frame_number+'</td><td>'+info.associated_community_count+'</td><td>'+info.open_community_count+'</td>'
						+'<td>'+info.closed_community_count+'</td><td>'+info.activity_area_count+'</td><td>'+info.charge_for_site_count+'</td><td>'+info.free_for_site_count+'</td><td>'+info.site_area+'</td>'
						+'<td>'+info.associated_community_town_count+'</td></tr>';
						outStoreinputStr += '<tr><td>'+(i+1)+'</td><td>'+info.cityname+'</td><td><input type="text" value="'+info.storecount+'" class="form-control"></td><td><input type="text" value="'+info.frame_number+'" class="form-control"></td>'
						+'<td><input value="'+info.associated_community_count+'" type="text" class="form-control"></td><td><input type="text" value="'+info.open_community_count+'" class="form-control"></td><td><input type="text" value="'+info.closed_community_count+'" class="form-control"></td>'
						+'<td><input type="text" value="'+info.activity_area_count+'" class="form-control"></td><td><input type="text" value="'+info.charge_for_site_count+'" class="form-control"></td><td><input type="text" value="'+info.free_for_site_count+'" class="form-control"></td>'
						+'<td><input type="text" value="'+info.site_area+'" class="form-control"></td><td><input type="text" value="'+info.associated_community_town_count+'" class="form-control"></td></tr>';
					}
				}else{
					var outStoreResourceCity = jsonData.outStoreResourceCity;
					for(var i = 0; i < outStoreResourceCity.length; i ++){
						var cityname = outStoreResourceCity[i].city_name;
						var storecount = outStoreResourceCity[i].storecount;
						outStoreStr += '<tr><td>'+(i+1)+'</td><td>'+cityname+'</td><td>'+storecount+'</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>';
						outStoreinputStr += '<tr><td>'+(i+1)+'</td><td>'+cityname+'</td><td><input type="text" value="'+storecount+'" class="form-control"></td><td><input type="text" value="0" class="form-control"></td>'
							+'<td><input type="text" value="0" class="form-control"></td><td><input type="text" value="0" class="form-control"></td><td><input type="text" value="0" class="form-control"></td>'
							+'<td><input type="text" value="0" class="form-control"></td><td><input type="text" value="0" class="form-control"></td><td><input type="text" value="0" class="form-control"></td>'
							+'<td><input type="text" value="0" class="form-control"></td><td><input type="text" value="0" class="form-control"></td></tr>';
					}
				}
				$("#storeresources").append(storeStr);
				$("#outstoreresources").append(outStoreStr);
				$("#storeresources_1").append(storeinputStr);
				$("#outstoreresources_1").append(outStoreinputStr);
			}
		});
	}
	