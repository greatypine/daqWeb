//创建日历DOM--------------------------------------------------------------------------------------------------------------
 function createCalendar(parentid,callback,ymd,cmcallback){
  //结构
  closeCalendar();
  var calendar=document.getElementById(parentid).appendChild(document.createElement("div"));  
  var title=calendar.appendChild(document.createElement("div"));
      var close=title.appendChild(document.createElement("div"));
      var titleText=title.appendChild(document.createElement("div"));
  var head=calendar.appendChild(document.createElement("div"));
      var lastYear=head.appendChild(document.createElement("div"));
      var lastMonth=head.appendChild(document.createElement("div"));
      var thisYearMonth=head.appendChild(document.createElement("div"));
      var nextMonth=head.appendChild(document.createElement("div"));
      var nextYear=head.appendChild(document.createElement("div"));
  var week=calendar.appendChild(document.createElement("div"));
  var body=calendar.appendChild(document.createElement("div"));
  //动作
      lastMonth.onclick=function(){changeMonth(-1,callback,cmcallback);};
      nextMonth.onclick=function(){changeMonth(1,callback,cmcallback);};
      lastYear.onclick=function(){changeYear(-1,callback,cmcallback);};
      nextYear.onclick=function(){changeYear(1,callback,cmcallback);};
  //属性
  calendar.setAttribute("id","calendar");
  //样式
  calendar.className="calendar";
  title.className="calendarTitle";
  close.className="calendarClose";
  head.className="calendarHead";
  lastMonth.className="calendarLastMonth";
  nextMonth.className="calendarNextMonth";
  lastYear.className="calendarLastYear";
  nextYear.className="calendarNextYear";
  thisYearMonth.className="calendarYearMonth";
  //内容
  titleText.innerHTML="";
  
  //星期
  var aryWeek=new Array("日","一","二","三","四","五","六");
  for(i=0;i<7;i++){
    var weekday=week.appendChild(document.createElement("div"));
        weekday.className="calendarWeekday";
        if(i==0||i==6){weekday.className="calendarWeekend";}
        weekday.innerHTML=aryWeek[i];
   }

  //计算日历
  if(ymd) {			//ymd格式为yyyy-MM-dd
  	var yMd = ymd.split("-");
	showCalendar(yMd[0],Number(yMd[1])-1,callback);
  }else {
  	var now=new Date();
  	showCalendar(now.getFullYear(),now.getMonth(),callback);
  }
 }

//根据年份、月份计算日历-------------------------------------------------------------------------------------------------------
function showCalendar(y,m,callback){
	var calendar=document.getElementById("calendar");
	var head=calendar.childNodes[1].childNodes[2];
    head.innerHTML=y+"年"+(m+1)+"月";
	calendar.setAttribute("year",y);
	calendar.setAttribute("month",m);
	calendar=calendar.childNodes[3];
	calendar.innerHTML="";
	var firstDayOfMonth=new Date(y,m,1);
	var firstWeekDay=firstDayOfMonth.getDay();;
	var cMonth=firstDayOfMonth.getMonth();
	var WeekNo=0;
	var MonthDay=1;
	var Today=new Date();

	while(MonthDay>=0){
		var cDate=new Date(y,m,MonthDay);
		if(cMonth==cDate.getMonth()){
	    	var cWeekDiv=calendar.appendChild(document.createElement("div"));
			cWeekDiv.style.clear="both";
			for(i=0;i<7;i++){
				var cWeekdayDiv=cWeekDiv.appendChild(document.createElement("div"));
				cWeekdayDiv.className="calendarDay";
				if(i==0||i==6){
					cWeekdayDiv.className="calendarWeekendDay";
				}
				if(WeekNo==0){
					if(i>=firstWeekDay){
						MonthDay++;
					}
				}else{     
					MonthDay++;
				}

				if(MonthDay>0){
					cDate=new Date(y,m,MonthDay-1);
					if(cMonth==cDate.getMonth()){
						cWeekdayDiv.innerHTML=cDate.getDate();
						var cm = m+1;
						if(cm<10) {
							cm = "0"+cm;
						}
						var cmo = MonthDay-1;
						if(cmo<10) {
							cmo = "0"+cmo;
						}
						cWeekdayDiv.setAttribute("id","calendardiv"+y+"-"+cm+"-"+cmo);
						if(cDate<=Today) {
							cWeekdayDiv.style.cursor='pointer';
							cWeekdayDiv.setAttribute("Date",cDate);
							cWeekdayDiv.setAttribute("name","calendardiv");
							cWeekdayDiv.onclick=function(){
								if (callback && typeof(callback) == 'function') {
									var d=new Date(this.getAttribute("Date"));
									var tcm = d.getMonth()+1;
									if(tcm<10) {
										tcm = "0"+tcm;
									}
									var tcmo = d.getDate();
									if(tcmo<10) {
										tcmo = "0"+tcmo;
									}
									var txt=d.getFullYear()+"-"+tcm+"-"+tcmo;
									callback(txt);
								}
							};
							cWeekdayDiv.onmouseover=function(){
								overClendar(this);
							};
							cWeekdayDiv.onmouseout=function(){
								outClendar(this);
							};
						}else {
							cWeekdayDiv.style.cursor='default';
							cWeekdayDiv.style.color='#AAAAAA';
						}
						if(cDate.getFullYear()==Today.getFullYear()&&cDate.getMonth()==Today.getMonth()&&cDate.getDate()==Today.getDate()){
							cWeekdayDiv.className="calendarToday";
						}
					}
				}
			}
			WeekNo++;
		}else{
			break;
		}
	}
}
//改变月份年份------------------------------------------------------------------------------------------------
 function changeMonth(d,callback,cmcallback){
  var calendar=document.getElementById("calendar");
  var year=calendar.getAttribute("year");
  var month=calendar.getAttribute("month");
  month=Number(month)+Number(d);
  if(month<0){month=11;year=Number(year)-1;}
  if(month>11){month=0;year=Number(year)+1;}
  showCalendar(year,month,callback);
  if (callback && typeof(callback) == 'function') {
  	cmcallback(d);
  }
 }
 function changeYear(d,callback,cmcallback){
  var calendar=document.getElementById("calendar");
  var year=calendar.getAttribute("year");
  var month=calendar.getAttribute("month");
  year=Number(year)+d;
  showCalendar(year,Number(month),callback);
  if (callback && typeof(callback) == 'function') {
  	cmcallback(d);
  }
 }

//鼠标动作------------------------------------------------------------------------------------------------
 function overClendar(obj){
  obj.setAttribute("oldClass",obj.className);
  obj.className="calendarOver"; 
 }
 function outClendar(obj){
  obj.className=obj.getAttribute("oldClass"); 
 }
	
	function clearClendar(){
		var calDivs = $("div[name='calendardiv']");
		for(var i=0;i<calDivs.length;++i) {
			calDivs[i].style.background="#fafafa";
		}
	}


//隐藏日历----------------------------------------------------------------------------------------------- 
 function closeCalendar(){
  var calendar=document.getElementById("calendar");
  if(calendar)document.body.removeChild(calendar);
 }