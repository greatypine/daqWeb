//格式化金额
function outputMoney(number) {
    number = number.replace(/\,/g, "");
    if (number == "") return "";
    var flag = false;
    if(String(number).indexOf(".")==-1){
    	flag = true;
    }
    if (number < 0) {
    	if(flag){
    		return '-' + outputDollars(Math.floor(Math.abs(number) - 0) + '');
    	}else{
    		return '-' + outputDollars(Math.floor(Math.abs(number) - 0) + '') + outputCents(Math.abs(number) - 0);
    	}
    } else {
    	if(flag){
        	return outputDollars(Math.floor(number - 0) + '');
    	}else{
        	return outputDollars(Math.floor(number - 0) + '') + outputCents(number - 0);
    	}
    }
}
function outputDollars(number) {
    if (number.length <= 3) {
        return (number == '' ? '0' : number);
    } else {
        var mod = number.length % 3;
        var output = (mod == 0 ? '' : (number.substring(0, mod)));
        for (i = 0; i < Math.floor(number.length / 3); i++) {
            if ((mod == 0) && (i == 0)) {
                output += number.substring(mod + 3 * i, mod + 3 * i + 3);
            } else {
                output += ',' + number.substring(mod + 3 * i, mod + 3 * i + 3);
            }
        }
        return (output);
    }
}
function outputCents(amount) {
    amount = Math.round(((amount) - Math.floor(amount)) * 100);
    return (amount < 10 ? '.0' + amount : '.' + amount);
}

//时间
var now = new Date(); //当前日期
var nowDayOfWeek = now.getDay(); //今天本周的第几天
var nowDay = now.getDate(); //当前日
var nowMonth = now.getMonth(); //当前月
var nowYear = now.getYear(); //当前年
nowYear += (nowYear < 2000) ? 1900 : 0; //
var sundate = new Date();
sundate.setDate(sundate.getDay() == 0 ? sundate.getDate() - 6 : sundate.getDate() - (-3 - sundate.getDay()));

//格式化日期：yyyy-MM-dd
function formatDate(date) {
    var myyear = date.getFullYear();
    var mymonth = date.getMonth() + 1;
    var myweekday = date.getDate();
    if (mymonth < 10) {
        mymonth = "0" + mymonth;
    }
    if (myweekday < 10) {
        myweekday = "0" + myweekday;
    }
    return (myyear + "-" + mymonth + "-" + myweekday);
}

//获得本周的开始日期
function getWeekStartDate() {
    var weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek+4);
    return formatDate(weekStartDate);
}



//获得本周的结束日期
function getWeekEndDate() {
    var weekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek)+4);
    return formatDate(weekEndDate);
}


//获得上周的开始日期
function getLastWeekStartDate() {
    var weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek - 7+4);
    return formatDate(weekStartDate);
}


//获得上周的结束日期
function getLastWeekEndDate() {
    var weekEndDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek - 1+4);
    return formatDate(weekEndDate);
}

//获得下周开始时间
function getNextWeekStartDate(){
	var weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek+7+4);
    return formatDate(weekStartDate);
}

//获得下周结束时间
function getNextWeekEndDate(){
	var weekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek)+7+4);
    return formatDate(weekEndDate);
}
//判断是否为数字
function isNumber(val){
    var regPos = /^\d+(\.\d+)?$/; //非负浮点数
    var regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
    if(regPos.test(val) || regNeg.test(val)){
        return true;
    }else{
        return false;
    }

}

//获得近7日的日期(不包含当日)
function getSevenTime(index){
    var s="";
    var d=new Date();
    var year=d.getFullYear();
    var mon1=d.getMonth()+1;
    var day1=d.getDate();
    d.setDate(d.getDate()-(index+1));
    var mon2=d.getMonth()+1;
    var day2=d.getDate()-1;
    var vd=new Array();//存储近7天数据
    if(mon2<mon1){
        var daycount=getCountDays(year,mon2);
        for(var i=day2;i<daycount;i++){
            var days=parseInt(i)+1;
            vd.push(addzero(mon2)+"-"+addzero(days));
        }
        for(var i=1;i<day1;i++){
            var days=parseInt(i)+1;
            vd.push(addzero(mon1)+"-"+addzero(days));
        }
    }else{
        for(var i=day2;i<day1;i++){
            var days=parseInt(i)+1;
            vd.push(addzero(mon2)+"-"+addzero(days));
        }
    }
    return vd.splice(1,vd.length-2);
}
 
//判断一个月有多少天
function getCountDays(year, month) {
    var curDate = new Date(year, month - 1);
    /* 获取当前月份 */
    var curMonth = curDate.getMonth();
    /* 生成实际的月份: 由于curMonth会比实际月份小1, 故需加1 */
    curDate.setMonth(curMonth + 1);
    /* 将日期设置为0, 这里为什么要这样设置, 我不知道原因, 这是从网上学来的 */
    curDate.setDate(0);
    /* 返回当月的天数 */
 
    return curDate.getDate();
}
//小于10滴天数,前面添加上0
function addzero(num){
    if(parseInt(num)<10){
        return "0"+num;
    }else{
        return num;
    }
}
//从对象数组中查询某元素的位置
//用法:var index = findArray(datas, {value: element.cityname}); datas[index].name
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
function formatDate(date) {
    var myyear = date.getFullYear();
    var mymonth = date.getMonth()+1;
    var myweekday = date.getDate();

    if(mymonth < 10){
        mymonth = "0" + mymonth;
    }
    if(myweekday < 10){
        myweekday = "0" + myweekday;
    }
    return (myyear+"/"+mymonth + "/" + myweekday);
}
   //获得某月的天数
function getMonthDays(myMonth){
	var now = new Date();  
	var nowYear = now.getFullYear(); //当前年
    var monthStartDate = new Date(nowYear, myMonth, 1);
    var monthEndDate = new Date(nowYear, myMonth + 1, 1);
    var   days   =   (monthEndDate   -   monthStartDate)/(1000   *   60   *   60   *   24);
    return   days;
}
//获取上个月一号的日期
function findLastFirstDay(){
	var now = new Date();  
	var nowYear = now.getFullYear(); //当前年
	var lastMonthDate = new Date();  //上月日期
    lastMonthDate.setDate(1);
    lastMonthDate.setMonth(lastMonthDate.getMonth()-1);
    var lastYear = lastMonthDate.getYear();
    var lastMonth = lastMonthDate.getMonth();
	var getLastMonthStartDate = new Date(nowYear, lastMonth, 1);
	var getLastMonthStartDate = formatDate(getLastMonthStartDate);
	return getLastMonthStartDate;
}
//获取上个月最后一天的日期
function findLastEndDay(){
	var now = new Date();  
	var nowYear = now.getFullYear(); //当前年
	var lastMonthDate = new Date();  //上月日期
    lastMonthDate.setDate(1);
    lastMonthDate.setMonth(lastMonthDate.getMonth()-1);
    var lastYear = lastMonthDate.getYear();
    var lastMonth = lastMonthDate.getMonth();
	var getLastMonthEndDate = new Date(nowYear, lastMonth, getMonthDays(lastMonth));
    var getLastMonthEndDate = formatDate(getLastMonthEndDate);
    return getLastMonthEndDate;
}
//获取当月开始时间
function findTodayFirstDay(){
	var now = new Date();  
	var nowYear = now.getFullYear(); //当前年
	var nowMonth = now.getMonth();//当前月
	var getMonthStartDate = new Date(nowYear, nowMonth, 1);
	var getMonthStartDate =  formatDate(getMonthStartDate);
	return getMonthStartDate;
}
//获取当前月昨天的日期
function findTodayYesterdayDay(){
  var day1 = new Date();
  day1.setTime(day1.getTime()-24*60*60*1000);
  var getMonthYesterdayDay = day1.getFullYear()+"/" + ((day1.getMonth()+1)<10?('0'+(day1.getMonth()+1)):(day1.getMonth()+1)) + "/" + (day1.getDate()<10?('0'+day1.getDate()):day1.getDate());
  return getMonthYesterdayDay;
}