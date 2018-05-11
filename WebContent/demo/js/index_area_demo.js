var STORE_DAILY_TOTAL_GMV= 33467;
var AREA_ORDER_AMOUNT = Math.round((1.1-Math.random())*300);
var AREA_HOUSE_AMOUNT = 502;


//获取近期日gmv
function getRecentlySevenDay_gmv(){
    var key=[];
	var value = [];
	var curDate = new Date();
    
	for(var i=0;i<8;i++){
		var preDate = new Date(curDate.getTime() - i*24*60*60*1000); //前i天
		var c_year = preDate.getFullYear(); 
		var c_month = preDate.getMonth()+1;
		var c_day = preDate.getDate();
		var dateStr = c_year+"-"+c_month+"-"+c_day;
		key.push(dateStr);
		value.push(((1.1-Math.random())*10000).toFixed(2));
	}
	
	var kv = {"xAxis":key,"series":value};
	return kv;
	
}


//获取近期日用户量(拉新，消费)
function getRecentlySevenDay_customer(){
    var key=[];
	var value0 = [];
	var value1 = [];
	var curDate = new Date();
    
	for(var i=0;i<8;i++){
		var preDate = new Date(curDate.getTime() - i*24*60*60*1000); //前i天
		var c_year = preDate.getFullYear(); 
		var c_month = preDate.getMonth()+1;
		var c_day = preDate.getDate();
		var dateStr = c_year+"-"+c_month+"-"+c_day;
		key.push(dateStr);
		var laxin = Math.round((1.1-Math.random())*50);
		value0.push(laxin);
		value1.push(laxin+Math.round((1.1-Math.random())*50));
	}
	
	var kv = {"xAxis":key,"series0":value0,"series1":value1};
	return kv;
	
}

//获取事业群当月的gmv
var deptArray = ['家务事','老年之家','国安优易','公共','商业创新','旅游','健康'];
function getDepartment_gmv(){
    var deptNV = new Array();
	
	for(var i=0;i<deptArray.length;i++){
		var vnMap = {};
		var v = ((1.1-Math.random())*10000).toFixed(2);
		vnMap["value"] = v;
		vnMap["name"] = deptArray[i];
		deptNV.push(vnMap);
	}
	
	return deptNV;
}

//获取频道当月gmv
var channelArray = ['家修','养老餐','日用百货','社区书屋','休闲食品','安心出游','医药服务'];
function getChannel_gmv(){
    var channelNV = new Array(); 
	
	for(var i=0;i<channelArray.length;i++){
		var vnMap = {};
		var v = ((1.1-Math.random())*10000).toFixed(2);
		vnMap["value"] = v;
		vnMap["name"] = channelArray[i];
		channelNV.push(vnMap);
	}
	
	return channelNV;
}


//获取事业群当月的消费用户
function getDepartment_consumer(){
    var deptNV = new Array();
	
	for(var i=0;i<deptArray.length;i++){
		var vnMap = {};
		var v = Math.round((1.1-Math.random())*100);
		vnMap["value"] = v;
		vnMap["name"] = deptArray[i];
		deptNV.push(vnMap);
	}
	
	return deptNV;
}

//获取频道当月消费用户
function getChannel_consumer(){
    var channelNV = new Array(); 
	
	for(var i=0;i<channelArray.length;i++){
		var vnMap = {};
		var v = Math.round((1.1-Math.random())*60);
		vnMap["value"] = v;
		vnMap["name"] = channelArray[i];
		channelNV.push(vnMap);
	}
	
	return channelNV;
}

//片区
var areas = [{"store_id":9900287,"employee_a_no":"TJ000258","create_user_id":107623,"create_time":1505389690000,"employee_a_name":"朱盼雨","employee_no":null,"employee_name":null,"employee_b_name":"顾磊","version":20,"update_time":1525347837000,"area_no":"0022Y003000018","update_user":"王强","update_user_id":107623,"town_id":4522,"name":"南北大街社区","id":2632,"create_user":"王强","status":0,"employee_b_no":"TJ000363"},{"store_id":9900287,"employee_a_no":"TJ000303","create_user_id":107623,"create_time":1505389766000,"employee_a_name":"顾晓磊","employee_no":null,"employee_name":null,"employee_b_name":"侍文元","version":20,"update_time":1525347491000,"area_no":"0022Y003000019","update_user":"王强","update_user_id":107623,"town_id":4522,"name":"前程里社区","id":2633,"create_user":"王强","status":0,"employee_b_no":"TJ000259"},{"store_id":9900287,"employee_a_no":"TJ000380","create_user_id":107623,"create_time":1505389835000,"employee_a_name":"杨红宇","employee_no":null,"employee_name":null,"employee_b_name":"马维博","version":25,"update_time":1525347476000,"area_no":"0022Y003000020","update_user":"王强","update_user_id":107623,"town_id":4522,"name":"科艺里社区 ","id":2634,"create_user":"王强","status":0,"employee_b_no":"TJ000352"},{"store_id":9900287,"employee_a_no":"TJ000259","create_user_id":107623,"create_time":1505390476000,"employee_a_name":"侍文元","employee_no":null,"employee_name":null,"employee_b_name":"顾晓磊","version":31,"update_time":1525347457000,"area_no":"0022Y003000021","update_user":"王强","update_user_id":107623,"town_id":4522,"name":"滦水里社区","id":2635,"create_user":"王强","status":0,"employee_b_no":"TJ000303"},{"store_id":9900287,"employee_a_no":"TJ000363","create_user_id":107623,"create_time":1505390540000,"employee_a_name":"顾磊","employee_no":null,"employee_name":null,"employee_b_name":"朱盼雨","version":19,"update_time":1525422604000,"area_no":"0022Y003000022","update_user":"王强","update_user_id":107623,"town_id":4522,"name":"新城小区 ","id":2636,"create_user":"王强","status":0,"employee_b_no":"TJ000258"},{"store_id":9900287,"employee_a_no":"TJ000677","create_user_id":107623,"create_time":1505390809000,"employee_a_name":"任罡毅","employee_no":null,"employee_name":null,"employee_b_name":"顾晓磊","version":20,"update_time":1525347682000,"area_no":"0022Y003000023","update_user":"王强","update_user_id":107623,"town_id":4522,"name":"麦格理社区 景兴里","id":2637,"create_user":"王强","status":0,"employee_b_no":"TJ000303"},{"store_id":9900287,"employee_a_no":"TJ000526","create_user_id":107623,"create_time":1509329415000,"employee_a_name":"李斌","employee_no":null,"employee_name":null,"employee_b_name":"顾磊","version":17,"update_time":1525347567000,"area_no":"0022Y003000026","update_user":"王强","update_user_id":107623,"town_id":null,"name":"龙都花园 景兴西里","id":6568,"create_user":"王强","status":0,"employee_b_no":"TJ000363"},{"store_id":9900287,"employee_a_no":"TJ000565","create_user_id":107623,"create_time":1511772641000,"employee_a_name":"张春篪","employee_no":null,"employee_name":null,"employee_b_name":"任罡毅","version":22,"update_time":1525347357000,"area_no":"0022Y003000027","update_user":"王强","update_user_id":107623,"town_id":null,"name":"云广新里 重华里社区","id":7076,"create_user":"王强","status":0,"employee_b_no":"TJ000677"},{"store_id":9900287,"employee_a_no":"TJ000167","create_user_id":107623,"create_time":1511774627000,"employee_a_name":"李天赐","employee_no":null,"employee_name":null,"employee_b_name":"顾磊","version":10,"update_time":1525349019000,"area_no":"0022Y003000029","update_user":"王强","update_user_id":107623,"town_id":null,"name":"美好里  美化里社区","id":7087,"create_user":"王强","status":0,"employee_b_no":"TJ000363"},{"store_id":9900287,"employee_a_no":"TJ000352","create_user_id":107623,"create_time":1525348175000,"employee_a_name":"马维博","employee_no":null,"employee_name":null,"employee_b_name":"杨红宇","version":3,"update_time":1525422583000,"area_no":"0022Y003000034","update_user":"王强","update_user_id":107623,"town_id":null,"name":"海景公寓  鹤望里社区","id":9941,"create_user":"王强","status":0,"employee_b_no":"TJ000380"}]

//服务范围
var storeService=[[117.24063,39.099182],[117.237604,39.097501],[117.235982,39.098013],[117.232256,39.098692],[117.228367,39.099549],[117.227214,39.100248],[117.222713,39.101814],[117.219859,39.102213],[117.218982,39.101906],[117.220353,39.09648],[117.220417,39.091156],[117.220323,39.090642],[117.219989,39.090135],[117.219714,39.089549],[117.219652,39.088141],[117.219144,39.086753],[117.218628,39.085447],[117.2187,39.084136],[117.224027,39.084214],[117.22396,39.08572],[117.224001,39.088458],[117.224032,39.092915],[117.226382,39.092119],[117.228678,39.090988],[117.23239,39.089241],[117.235554,39.087575],[117.239405,39.086491],[117.240023,39.086954],[117.240083,39.086884],[117.240201,39.086942],[117.241027,39.087633],[117.242095,39.08822],[117.242498,39.088474],[117.243097,39.088924],[117.243869,39.089724],[117.244471,39.09009],[117.243741,39.090923],[117.242818,39.093829],[117.241896,39.096734]];
