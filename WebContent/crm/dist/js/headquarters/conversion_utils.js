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
    var weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek);
    return formatDate(weekStartDate);
}



//获得本周的结束日期
function getWeekEndDate() {
    var weekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek));
    return formatDate(weekEndDate);
}


//获得上周的开始日期
function getLastWeekStartDate() {
    var weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek - 7);
    return formatDate(weekStartDate);
}


//获得上周的结束日期
function getLastWeekEndDate() {
    var weekEndDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek - 1);
    return formatDate(weekEndDate);
}

//获得下周开始时间
function getNextWeekStartDate(){
	var weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek+7);
    return formatDate(weekStartDate);
}

//获得下周结束时间
function getNextWeekEndDate(){
	var weekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek)+7);
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