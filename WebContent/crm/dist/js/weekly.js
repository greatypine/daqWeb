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

var myChart1 = echarts.init(document.getElementById('main1'));
var myChart2 = echarts.init(document.getElementById('main2'));
var myChart3 = echarts.init(document.getElementById('main3'));
var myChart4 = echarts.init(document.getElementById('main4'));
var myChart5 = echarts.init(document.getElementById('main5'));
var myChart6 = echarts.init(document.getElementById('main6'));
var myChartCard = echarts.init(document.getElementById('maincard'));
var myChart7 = echarts.init(document.getElementById('main7'));
var myChartEmp = echarts.init(document.getElementById('mainemp'));
//var myChart8 = echarts.init(document.getElementById('main8'));
var myChart9 = echarts.init(document.getElementById('main9'));
var myChart10 = echarts.init(document.getElementById('main10'));
var myChart11 = echarts.init(document.getElementById('main11'));
var myChart12 = echarts.init(document.getElementById('main12'));
var myChart13 = echarts.init(document.getElementById('main13'));
var myChart14 = echarts.init(document.getElementById('main14'));
var myChart15 = echarts.init(document.getElementById('main15'));
var myChart16 = echarts.init(document.getElementById('main16'));
var myChart17 = echarts.init(document.getElementById('main17'));
//var myChart18 = echarts.init(document.getElementById('main18'));
//var myChart19 = echarts.init(document.getElementById('main19'));
//var myChart20 = echarts.init(document.getElementById('main20'));
//var myChart21 = echarts.init(document.getElementById('main21'));
var myChart22 = echarts.init(document.getElementById('main22'));
var myChart23 = echarts.init(document.getElementById('main23'));
//var myChart24 = echarts.init(document.getElementById('main24'));
var myChart26 = echarts.init(document.getElementById('main26'));

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
				        rotate:-20,
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
				    //color:['#019aba','#7a201f','#11565d'],
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
				        rotate:-20,
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

				  //网络业态分布--自营店
				  option2 = {
				    title: {text: '自营店',textAlign:'left',textStyle:{fontSize:"16",fontWeight:"normal"}},
				    tooltip: {
				      trigger: 'axis',
				      axisPointer: {
				        type: 'shadow',
				        textStyle: {color: '#fff'}
				      },
				    },
				    grid: {
				      borderWidth: 0,
				      right:30,
				      top: 100,
				      bottom: 40,
				      textStyle: {color: '#fff'}
				    },
				    legend: {
				      top: '30',
				      textStyle: {color: '#90979c',},
				      data: []
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
				        rotate:-20
				      },
				      data: [],
				    }],
				    yAxis: [{
				      type: 'value',
				      splitLine: {show: false},
				      axisLine: {
				        lineStyle: {color: '#90979c'}
				      },
				      axisTick: {show: false},
				      axisLabel: {interval: 0,},
				      splitArea: {show: false},
				    }],
				    /*dataZoom: [{
				      show: true,
				      height: 20,
				      xAxisIndex: [0],
				      bottom: 30,
				      start: 0,
				      end: 50,
				      handleIcon: 'path://M306.1,413c0,2.2-1.8,4-4,4h-59.8c-2.2,0-4-1.8-4-4V200.8c0-2.2,1.8-4,4-4h59.8c2.2,0,4,1.8,4,4V413z',
				      handleSize: '110%',
				      handleStyle:{color:'#d3dee5',},
				      textStyle:{color:'#fff'},
				      borderColor:'#90979c'
				    }, {
				      type: 'inside',
				      show: true,
				      height: 15,
				      start: 1,
				      end: 35
				    }],*/
				    series: []
				  }

				  //网络业态分布--合作店
				  option10 = {
						  title: {text: '合作店',textAlign:'left',textStyle:{fontSize:"16",fontWeight:"normal"}},
						    tooltip: {
						      trigger: 'axis',
						      axisPointer: {
						        type: 'shadow',
						        textStyle: {color: '#fff'}
						      },
						    },
						    grid: {
						      borderWidth: 0,
						      right:30,
						      top: 100,
						      bottom: 40,
						      textStyle: {color: '#fff'}
						    },
						    legend: {
						      top: '30',
						      left:"left",
						      y:"8%",
						      itemWidth:18,
						      itemHeight:12,
						      textStyle: {color: '#90979c',fontSize:14},
						      data: []
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
						        rotate:-20
						      },
						      data: [],
						    }],
						    yAxis: [{
						      type: 'value',
						      splitLine: {show: false},
						      axisLine: {
						        lineStyle: {color: '#90979c'}
						      },
						      axisTick: {show: false},
						      axisLabel: {interval: 0,},
						      splitArea: {show: false},
						    }],
						    /*dataZoom: [{
						      show: true,
						      height: 20,
						      xAxisIndex: [0],
						      bottom: 30,
						      start: 0,
						      end: 50,
						      handleIcon: 'path://M306.1,413c0,2.2-1.8,4-4,4h-59.8c-2.2,0-4-1.8-4-4V200.8c0-2.2,1.8-4,4-4h59.8c2.2,0,4,1.8,4,4V413z',
						      handleSize: '110%',
						      handleStyle:{color:'#d3dee5',},
						      textStyle:{color:'#fff'},
						      borderColor:'#90979c'
						    }, {
						      type: 'inside',
						      show: true,
						      height: 15,
						      start: 1,
						      end: 35
						    }],*/
						    series: []
				  }

//网路拓展趋势--近一年
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
    top: 70,
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
      show: false
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
  grid: {left: '2%',top:"20%",bottom: "7%",right:"5%",containLabel: true},
  tooltip : { trigger: 'axis',axisPointer : { type : 'shadow'}},
  xAxis: [
    {
      type: 'category',
      axisLine: { show: true,lineStyle:{ color:'#6173A3' }},
      axisLabel:{interval:1,textStyle:{color:'#9ea7c4',fontSize:12} },
      axisTick : {show: false},
      data:[],
    },
  ],
  yAxis: [
    {
      axisTick : {show: false},
      splitLine: {show:false},
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
    {max:0,
  	  name:'门店数量',
      axisTick : {show: false},
      splitLine: {show:false},
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
		            label: {
		                normal: {
		                  show: true,
		                  position: 'top',
		                  formatter: '{c}'
		                }
		              },
		            data: []
		        },
		        {
		            name: '双证齐全',
		            type: 'bar',
		            label: {
		                normal: {
		                  show: true,
		                  position: 'top',
		                  formatter: '{c}'
		                }
		              },
		            data: []
		        },
		        {
		            name: '仅营业照',
		            type: 'bar',
		            label: {
		                normal: {
		                  show: true,
		                  position: 'top',
		                  formatter: '{c}'
		                }
		              },
		            data: []
		        },
		        {
		            name: '无证照',
		            type: 'bar',
		            label: {
		                normal: {
		                  show: true,
		                  position: 'top',
		                  formatter: '{c}'
		                }
		              },
		            data: []
		        }
		    ]
		};

//人员动态
option7 = {
  title: {
    text: '近六周人员动态',textAlign:'left',textStyle:{fontSize:"16",fontWeight:"normal"},
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
    containLabel: true
  },
  xAxis: [{
    type: 'category',
    boundaryGap: false,
    data: []
  }],
  yAxis: 
  	{
          axisTick : {show: false},
          splitLine: {show:false},
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
optionEmp = {
  title: {
    text: '近六周总人数统计',textAlign:'left',textStyle:{fontSize:"16",fontWeight:"normal"}
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
    containLabel: true
  },
  xAxis: [{
    type: 'category',
    boundaryGap: false,
    data: []
  }],
  yAxis: {
    type: 'value',
    splitLine: {show:false},
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
    data:[],
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
  color:["#f00","#61a0a8","#2f4554"],
  tooltip: {
    trigger: 'axis'
  },
  grid: {
    left: '1%',
    right: '2%',
    bottom: '3%',
    containLabel: true
  },
  legend: {
    right:'0',
    data: ['达标率', '已完成', '任务目标']
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
        width: 3,
        shadowColor: 'rgba(0,0,0,0.4)',
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
  }, {
      name: '任务目标',
      type: 'bar',
      stack: "总量",
      yAxisIndex: 0,
      label: {
        normal: {
          show: true,
          position: 'insideTop'
        }
      },
      data: []
    },
    {
    name: '已完成',
    type: 'bar',
    stack: "总量",
    yAxisIndex: 0,
    label: {
  	 
      normal: {/* 
      	textStyle: {
              color: '#f00',
             	distance:"1px"
            }, */
            position: 'top',
        show: true
      }
    },
    data: []
  }]
};

//18年合作店拓展进度
option14 = {
  title: {
    text: '18年合作店拓展进度',textAlign:'left',textStyle:{fontSize:"16",fontWeight:"normal"}
  },
  color:["#f00","#61a0a8","#2f4554"],
  tooltip: {trigger: 'axis'},
  grid: {
    left: '1%',
    right: '2%',
    bottom: '3%',
    containLabel: true
  },
  legend: {
    right:'0',
    data: ['达标率', '已完成', '任务目标']
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
    lineStyle: {
      normal: {
        width: 3,
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
    name: '任务目标',
    stack: "总量",
    type: 'bar',
    yAxisIndex: 0,
    label: {
      normal: {
        show: true,
        position: 'insideTop'
      }
    },
    itemStyle:{normal:{}},
    data: []
  }, {
      name: '已完成',
      stack: "总量",
      type: 'bar',
      yAxisIndex: 0,
      label: {
        normal: {
          show: true,
          position: 'top'
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
    right: '2%',
    bottom: '3%',
    top: '18%',
    containLabel: true
  },
  xAxis: {
    data: ['\n1333', '文娱类活动\n110', '公益类活动\n314', '志愿者活动\n208', '活动小计\n652'],
    axisTick: {show: false}
  },
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
    data: [125, 10, 24, 21, 52]
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
    right: '2%',
    bottom: '3%',
    top: '18%',
    containLabel: true
  },
  xAxis: {
    type: 'category',
    data: ['北京\n36', '上海\n8', '天津\n47', '广州\n202', '深圳\n239', '云南\n40', '贵州\n70', '辽宁\n81', '全国\n44'],
    axisTick: {show: false}
  },
  yAxis: {
    type: 'value',
    axisTick: {show: false}
  },
  series: [{
    data: [820, 932, 901, 934, 1290, 1330, 1320, 820, 932],
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
      data: ['北京\n36', '上海\n8', '天津\n47', '广州\n202', '深圳\n239', '云南\n40', '贵州\n70', '辽宁\n81', '合计\n44'],
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
      //name:'直接访问',
      type:'bar',
      barWidth: '40%',
      data:[36, 8, 47, 202, 239, 40, 70,81,44]
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
        {value:1959, name:'政府从业人员',itemStyle: {normal:{color: '#EA9294'}}},
        {value:1239, name:'普通居民',itemStyle: {normal:{color: '#949494'}}},
        {value:2844, name:'民间组织人员',itemStyle: {normal:{color: '#A4D2D5'}}},
        {value:1802, name:'社区商户',itemStyle: {normal:{color: '#F1C0B1'}}},
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
      data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
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
      splitLine:{show: false}
    },
    {
      type: 'value',
      name: '个数',
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
      axisLabel: {
        formatter: '{value} %'
      },
      splitLine:{show: false}
    }
  ],
  series: [
    {
      name:'店均微信群数量',
      type:'bar',
      data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
    },
    {
      name:'单个微信群人数',
      type:'bar',
      yAxisIndex: 1,
      data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
    },
    {
      name:'活跃人群占比',
      type:'line',
      yAxisIndex: 2,
      data:[2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
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

myChart1.setOption(option1);
myChart2.setOption(option2);
myChart3.setOption(option3);
myChart4.setOption(option4);
myChart5.setOption(option5);
myChart6.setOption(option6);
myChartCard.setOption(optionCard);
myChart7.setOption(option7);
myChartEmp.setOption(optionEmp);
// myChart8.setOption(option8);
myChart9.setOption(option9);
myChart10.setOption(option10);
myChart11.setOption(option11);
myChart12.setOption(option12);
myChart13.setOption(option13);
myChart14.setOption(option14);
myChart15.setOption(option15);
myChart16.setOption(option16);
myChart17.setOption(option17);
//myChart18.setOption(option18);
//myChart19.setOption(option19);
//myChart20.setOption(option20);
//myChart21.setOption(option21);
myChart22.setOption(option22);
myChart23.setOption(option23);
//myChart24.setOption(option24);
myChart26.setOption(option26);

function chartresize2(){
  //console.log("chartresize");
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
    //console.log("chartresize");
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
        show: false
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
  //console.log("chartresize");
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
$(function(){	 
	loginShow();
	getCityNet();
	findStoreNetDate();
	oneyearorsixweek();
	storeDevelopmentProgress();
	progressOfNetworkConstruction();
	humanresourseInfo();
	customerInfo();
	areaInfo();
	GMVInfo();
});

function loginShow(){
	
	if(screenlogin!=null&&screenlogin!=''&&screenlogin!=undefined){
		var reObj = new PMSRequestObject("userManager", "isScreenUser", [ screenlogin ]);
	    var callback = function callback(data, textStatus, XMLHttpRequest) {
	    	//window.parent.location=getRootPath() + "/crm/index_city_net.html";
	    	var stateObject = {};
	    	var newUrl = "/daqWeb/crm/index_city_net.html";
	    	history.replaceState(stateObject,null,newUrl);
		};
	    var failureCallback = function failureCallback(data, textStatus, XMLHttpRequest) {
			alert("登录信息错误，请确认输入或联系管理员!");
			return false;
		}
	    var url = "../login.do";
	    $$.ajax(url, "requestString=" + reObj.toJsonString(), callback, failureCallback); 
	}
	
}

//获取城市城市网络体系概况
function getCityNet(){
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
			if(jsonData.townsize==0){
				$("#weekupdate").text("本周暂无变化");
			}else{
				$("#weekupdate").text("新增覆盖"+jsonData.countysize+"个区域（"+(jsonData.countyNames.length>6?jsonData.countyNames.substring(0,6)+"..":jsonData.countyNames)+"），新增覆盖"+jsonData.townsize+"个街道（"+(jsonData.townNames.length>8?jsonData.townNames.substring(0,8)+"..":jsonData.townNames)+"）")
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
	doManager("storeManager","findStoreCityOnline",null,function(data,textStatus,XmlHttpRequest){
		if (data.result) {
			var jsonData = $.fromJSON(data.data);
			var ziyings=jsonData.ziyingdian;
			if(ziyings.length>0){
				var xdataArray = ['生活中心店','街道月店','经营星店','校园店','独立微超店','药店','前置仓','数码连锁店','超市连锁店','广店营业厅'];
				var legendArray = new Array();
				option2.xAxis[0]["data"] = xdataArray;
				for(var i = 0; i < ziyings.length; i++){
					var ziying = ziyings[i];
					var dataArray = new Array();
					var cityname = ziying.city_name;
					legendArray.push(cityname);
					var shenghuozhongxindian = ziying.生活中心店;
					var jiedaoyuedian = ziying.街道月店;
					var jingyingxingdian = ziying.经营星店;
					var xiaoyuandian = ziying.校园店;
					var duliweichao = ziying.独立微超;
					var yaodian = ziying.药店;
					var qianzhicang = ziying.前置仓;
					var shumaliansuodian = ziying.数码连锁店;
					var chaoshiliansuodian = ziying.超市连锁店;
					var guangdianyingyedian = ziying.广店营业厅;
					dataArray.push(shenghuozhongxindian);
					dataArray.push(jiedaoyuedian);
					dataArray.push(jingyingxingdian);
					dataArray.push(xiaoyuandian);
					dataArray.push(duliweichao);
					dataArray.push(yaodian);
					dataArray.push(qianzhicang);
					dataArray.push(shumaliansuodian);
					dataArray.push(chaoshiliansuodian);
					dataArray.push(guangdianyingyedian);
					option2.series.push({
					      name:cityname,
					      type: 'bar',
					      stack: "总量",
					      symbol:"circle",
					      symbolSize:10,
					      data: dataArray,
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
				option2.legend.data = legendArray;
			}
			
			myChart2.setOption(option2);
			//合作店数据
			var hezuo=jsonData.hezuodian;
			if(hezuo.length>0){
				var xdataArray = ['生活中心店','街道月店','经营星店','校园店','独立微超店','药店','前置仓','数码连锁店','超市连锁店','广店营业厅'];
				var legendArray = new Array();
				option10.xAxis[0]["data"] = xdataArray;
				var flag=0;
				for(var i = 0; i < hezuo.length; i++){
					var ziying = hezuo[i];
					var dataArray = new Array();
					var cityname = ziying.city_name;
					legendArray.push(cityname);
					var shenghuozhongxindian = ziying.生活中心店;
					var jiedaoyuedian = ziying.街道月店;
					var jingyingxingdian = ziying.经营星店;
					var xiaoyuandian = ziying.校园店;
					var duliweichao = ziying.独立微超;
					var yaodian = ziying.药店;
					var qianzhicang = ziying.前置仓;
					var shumaliansuodian = ziying.数码连锁店;
					var chaoshiliansuodian = ziying.超市连锁店;
					var guangdianyingyedian = ziying.广店营业厅;
					dataArray.push(shenghuozhongxindian);
					dataArray.push(jiedaoyuedian);
					dataArray.push(jingyingxingdian);
					dataArray.push(xiaoyuandian);
					dataArray.push(duliweichao);
					dataArray.push(yaodian);
					dataArray.push(qianzhicang);
					dataArray.push(shumaliansuodian);
					dataArray.push(chaoshiliansuodian);
					dataArray.push(guangdianyingyedian);
					option10.series.push({
					      name:cityname,
					      type: 'bar',
					      stack: "总量",
					      symbol:"circle",
					      symbolSize:10,
					      data: dataArray,
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
					flag+=Math.max.apply(null, dataArray);
				}
				if(flag<5){
					option10.yAxis[0].max = 5;
				}
				option10.legend.data = legendArray;
			}
			
			myChart10.setOption(option10);
			//门店业态表数据（table）
			var biaodata=jsonData.biao;
			var storeString='';
			var quguoziying=0;
			var quanguoshenghuo=0;
			var quanguoyuedian=0;
			var quanguoxingdian=0;
			var quanguoxiaoyuan=0;
			var quanguoweichao=0;
			var quanguoyaodian=0;
			var quanguoqita=0;
			var quanguo2018mubiao=0;
			var quanguonewstore=0;
			
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
		          quanguo2018mubiao+=biaodata[i].mubiao2018;
		          quanguonewstore+=biaodata[i].newstore;
				storeString+='<tr><td>'+biaodata[i].city_name+'</td><td class="text-red">'+citystore+'</td><td>'+biaodata[i].shenghuo+'</td>'+
                '<td>'+biaodata[i].yuedian+'</td><td>'+biaodata[i].xingdian+'</td><td>'+biaodata[i].xiaoyuan+'</td><td>'+biaodata[i].weichao+'</td><td>'+biaodata[i].yaodian+'</td><td>'+biaodata[i].qita+'</td>'+
                '<td class="text-red">'+biaodata[i].mubiao2018+'</td><td>'+biaodata[i].newstore+'</td></tr>';
			}
			$("#storenet tr:eq(1)").after('<tr class="text-red"><th>全国</th><th>'+quguoziying+'</th><th>'+quanguoshenghuo+'</th><th>'+quanguoyuedian+'</th><th>'+quanguoxingdian+'</th>'+
	                  '<th>'+quanguoxiaoyuan+'</th><th>'+quanguoweichao+'</th><th>'+quanguoyaodian+'</th><th>'+quanguoqita+'</th><th>'+quanguo2018mubiao+'</th><th>'+quanguonewstore+'</th></tr>'+storeString);
				
		}},false);
}

function oneyearorsixweek(){
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
			weekdate[0]=sixweek[2].week1;
			weekdate[1]=sixweek[2].week2;
			weekdate[2]=sixweek[2].week3;
			weekdate[3]=sixweek[2].week4;
			weekdate[4]=sixweek[2].week5;
			weekdate[5]=sixweek[2].week6;
			 option11.series[0].data=weektotal;
		        option11.series[1].data=weekonle;
		        option11.series[2].data=weekother;
		        option11.xAxis[0].data=weekdate;
		        myChart11.setOption(option11);
	        
	        
			
		}},false);
	
}

	
	var storeDevelopmentProgress = function(){
		
		var dateArray = new Array();
		var citynameArray = new Array();
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
				//六周时间轴
				var dateArray = new Array();
				var dateAndWeek = jsonData.dateAndWeek;
				for(i = dateAndWeek.length - 1; i >=0 ; i--){
					var dateOne = dateAndWeek[i];
					var datetimes = dateOne.date;
					dateArray.push(datetimes);
				}
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
						var index = jQuery.inArray(weektime,dateArray);
						weektimeArray.push(index);
						vauleParam[index+""] = storeCount;
					}
					
					if(weektimeArray.length>0){
						for(var i = 0; i < weektimeArray.length;i++){
							var index = weektimeArray[i]+"";
							var replace = selfStoreArray.splice(weektimeArray[i],1,vauleParam[index]);
						}
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
						var index = jQuery.inArray(weektime,dateArray);
						weektimeArray.push(index);
						vauleParam[index+""] = storeCount;
					}
					if(weektimeArray.length>0){
						for(var i = 0; i < weektimeArray.length;i++){
							var aa = weektimeArray[i]+"";
							var replace = cooperativeStoreArray.splice(weektimeArray[i],1,vauleParam[aa]);
						}
					}
				}
				option12.series[1].data=cooperativeStoreArray;
				if(Math.max.apply(null, selfStoreArray)<4&&Math.max.apply(null, cooperativeStoreArray)<4){
					option12.yAxis[0].max = 4;
				}
				
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
					citynameArray.push(cityname);
					//合作店
					var cooperative_complete = storeNatureByYear.cooperative_complete;
					cooperative_completeArray.push(cooperative_complete);
					var cooperative_task = storeNatureByYear.cooperative_task;
					cooperative_taskArray.push(cooperative_task);
					var cooperative_rate = 0;
					if(cooperative_task > 0){
						cooperative_rate = (cooperative_complete/cooperative_task)*100;
						cooperative_rateArray.push(cooperative_rate.toFixed(2));
					}else{
						cooperative_rateArray.push(cooperative_rate.toFixed(2));
					}
					//自营店
					var self_complete = storeNatureByYear.self_complete;
					self_completeArray.push(self_complete);
					var self_support_task = storeNatureByYear.self_support_task;
					self_support_taskArry.push(self_support_task);
					var self_rate = 0;
					if(self_support_task > 0){
						self_rate = (self_complete/cooperative_task)*100;
						self_rateArray.push(self_rate.toFixed(2));
					}else{
						self_rateArray.push(self_rate.toFixed(2));
					}
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
				
				
				option12.xAxis[0]["data"] = dateArray;
				
				if(Math.max.apply(null, self_support_taskArry)<10||self_support_taskArry.length==0){
					option13.yAxis[0].max = 10;
				}else{
					option13.yAxis[0].max = parseInt(Math.max.apply(null, self_support_taskArry)/0.7);
				} 
				if(Math.max.apply(null, self_rateArray)<10||self_rateArray.length==0){
					option13.yAxis[1].max = 10;
				}else{
					option13.yAxis[1].max = parseInt(Math.max.apply(null, self_rateArray)/0.7);
				} 
				if(Math.max.apply(null, cooperative_taskArray)<10||cooperative_taskArray.length==0){
					option14.yAxis[0].max = 10;
				}else{
					option14.yAxis[0].max = parseInt(Math.max.apply(null, cooperative_taskArray)/0.7);
				} 
				if(Math.max.apply(null, cooperative_rateArray)<10||cooperative_rateArray.length==0){
					option14.yAxis[1].max = 10;
				}else{
					option14.yAxis[1].max = parseInt(Math.max.apply(null, cooperative_rateArray)/0.7);
				} 
				
				option13.series[0].data=self_rateArray;
				option13.series[1].data=self_support_taskArry;
				option13.series[2].data=self_completeArray;
				option13.xAxis[0]["data"] = citynameArray;
				
				option14.series[0].data=cooperative_rateArray;
				option14.series[1].data=cooperative_taskArray;
				option14.series[2].data=cooperative_completeArray;
				option14.xAxis[0]["data"] = citynameArray;
				
				myChart12.setOption(option12);
				myChart13.setOption(option13);
				myChart14.setOption(option14);
				
				$("#preopenstore").append(preString);
				$("#newopenstore").append(nowString);

			}
		},false);
	}
	var progressOfNetworkConstruction = function(){
		doManager("storexpandManager","progressOfNetworkConstruction",null,function(data,textStatus,XmlHttpRequest){
			if (data.result) {
				var jsonData = $.fromJSON(data.data);
				var contractAndthroughByYear = jsonData.contractAndthroughByYear;
				var cityArray = new Array();
				var contractQuantityArray = new Array();
				var throughQuantityArray = new Array();
				var dateArray = new Array();
				if(contractAndthroughByYear.length > 0){
					for(var i = 0; i < contractAndthroughByYear.length; i++){
						var cityData = contractAndthroughByYear[i];
						var cityname = cityData.cityname;
						cityArray.push(cityname);
						var contractQuantity = cityData.contract_quantity;
						contractQuantityArray.push(contractQuantity);
						var throughQuantity = cityData.through_quantity;
						throughQuantityArray.push(throughQuantity);
					}
				}else{
					
				}
				option4.series[0].data=throughQuantityArray;
				option4.series[1].data=contractQuantityArray;
				option4.xAxis[0]["data"] = cityArray;
				myChart4.setOption(option4);
				var dateAndWeek = jsonData.dateAndWeek;
				for(i = dateAndWeek.length - 1; i >=0 ; i--){
					var dateOne = dateAndWeek[i];
					var datetimes = dateOne.date;
					dateArray.push(datetimes);
				}
				option5.xAxis[0]["data"] = dateArray;
				var citynameArray = new Array();
				var throughByWeek = jsonData.throughByWeek;
				if(throughByWeek.length > 0){
					for(var i = 0; i < throughByWeek.length; i++){
						var cityweekArray = new Array();
						var cityname = throughByWeek[i].cityname;
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
				if(storeStateCount.length > 0){
					for(var i = 0; i < storeStateCount.length; i++){
						main6CitynameArray.push(storeStateCount[i].city_name);
						main6DataArray.push(storeStateCount[i].count);
					}
				}
				if(Math.max.apply(null, main6DataArray)<10||main6DataArray.length==0){
					option6.yAxis[0].max = 10;
				}else{
					option6.yAxis[0].max = parseInt(Math.max.apply(null, main6DataArray)/0.7);
				}
				option6.series[0].data=main6DataArray;
				option6.xAxis[0]["data"] = main6CitynameArray;
				myChart6.setOption(option6);
				var cardString = "";
				var storeCardBycity = jsonData.storeCardBycity;
				var business_licenseTotal = 0,double_cardTotal = 0,no_cardTotal = 0,store_countTotal = 0;
				var storeCountArray = new Array(),double_cardArray = new Array(),business_licenseArray = new Array(),no_cardArray = new Array();
				var cardCitynameArray = new Array();
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

						cardString += '<tr><td title="'+cityname+'">'+cityname+'</td><td>'+store_count+'</td><td>'+double_card+'</td><td>'+business_license+'</td><td>'+no_card+'</td><td>'+double_card_rate.toFixed(2)+'%</td></tr>';
						if(i == storeCardBycity.length-1){
							var double_card_rateTotal = (double_cardTotal/store_countTotal)*100;
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
			}
		},false);
	}
	var humanresourseInfo = function(){
		doManager("humanresourcesManager","getEmployeeInfoByWeek",null,function(data,textStatus,XmlHttpRequest){
			if (data.result) {
				var jsonData = $.fromJSON(data.data);
				//六周时间轴
				var dateArray = new Array();
				var dateAndWeek = jsonData.dateAndWeek;
				for(i = dateAndWeek.length - 1; i >=0 ; i--){
					var dateOne = dateAndWeek[i];
					var datetimes = dateOne.date;
					dateArray.push(datetimes);
				}
				option7.xAxis[0]["data"] = dateArray;
				optionEmp.xAxis[0]["data"] = dateArray;
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
				optionEmp.yAxis.min = (queryHumanTotal[0].week1*1).toFixed(0);
				myChartEmp.setOption(optionEmp);
				
				var queryLeaveHuman = jsonData.queryLeaveHuman;
				var leaveHumanArray = [0,0,0,0,0,0];
				if(queryLeaveHuman.length > 0){
					var weektimeArray = new Array();
					var vauleParam = {};
					for(var z = 0; z < queryLeaveHuman.length; z++){
						var humanInfo =  queryLeaveHuman[z];
						var weektime = humanInfo.week_time;
						var humancount = humanInfo.count;	
						var index = jQuery.inArray(weektime,dateArray);
						weektimeArray.push(index);
						vauleParam[index+""] = humancount;
					}
					if(weektimeArray.length>0){
						for(var i = 0; i < weektimeArray.length;i++){
							var aa = weektimeArray[i]+"";
							var replace = leaveHumanArray.splice(weektimeArray[i],1,vauleParam[aa]);
						}
					}
				}
				option7.series[0].data=leaveHumanArray;
				var queryToPostHuman = jsonData.queryToPostHuman;
				var toPostHumanArray = [0,0,0,0,0,0];
				if(queryToPostHuman.length > 0){
					var weektimeArray = new Array();
					var vauleParam = {};
					for(var z = 0; z < queryToPostHuman.length; z++){
						var humanInfo =  queryToPostHuman[z];
						var weektime = humanInfo.week_time;
						var humancount = humanInfo.count;
						var index = jQuery.inArray(weektime,dateArray);
						weektimeArray.push(index);
						vauleParam[index+""] = humancount;
					}
					if(weektimeArray.length>0){
						for(var i = 0; i < weektimeArray.length;i++){
							var aa = weektimeArray[i]+"";
							var replace = toPostHumanArray.splice(weektimeArray[i],1,vauleParam[aa]);
						}
					}
				}
				option7.series[1].data=toPostHumanArray;
				myChart7.setOption(option7);
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
		 doManager("AreaManager", "selectAllArea",null,
		            function(data, textStatus, XMLHttpRequest) {
		                if (data.result) {
		                    var resultJson = JSON.parse(data.data);
		                    console.log(resultJson);
		                    var storeArray = new Array();
		                    var cityAreaArray = new Array();
		                    var xDataArray16 = new Array();
		                    var xDataArray17 = new Array();
		                    var areaArray = resultJson.queryAreaCountByCity;
		                    var areaAllInfo = resultJson.queryAllAreaCount;
		                    var areaData = areaAllInfo[0];
		                    var storeCount = areaData.store_count;
		                    var weekCount = areaData.week_count;
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
		var reqestParameter = {
	        }
		//7天
        doManager("DynamicManager", "getWeekCustomerOrderRate",[reqestParameter],
            function(data, textStatus, XMLHttpRequest) {
                if (data.result) {
                    var resultJson = JSON.parse(data.data);
                 //   console.log(resultJson);
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
                	if(Math.max.apply(null, data3)<10||data3.length==0){
                		option23.yAxis[1].max = 10;
                	}else{
                		option23.yAxis[1].max = parseInt(Math.max.apply(null, data3)/0.7);
                	}
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
        //console.log('request turnover customer data from server in ' + (new Date().getTime() - startTime) + ' millisecond');
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
	  
	  var weekData = [];  //0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
	  function initWeekData(){
			doManager("chartStatManager","queryTurnoverByWeek",[shareChartStatDto],
				function(data,textStatus,XmlHttpRequest){
					if (data.result) {
						var jsonData = $.fromJSON(data.data);
						$(jsonData).each(function(index,element){
//	 						weekData[parseInt(element.week_time)-1]=element.week_amount;
							weekData[index]=element.week_amount;
							xData_week[index]=new Date(element.week_time).toLocaleString();
	                    });
						//chart28_option.series[0].data=weekData;
						chart28.setOption(chart28_option);
					}
					$("#process_div").hide();
					$("#process_div_pic").hide();
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
			},false);
			
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
			},false);
		} 
	  
	  
	//搜索
		function search_manual_k(){
				 	  
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
					smallBLabel:1
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