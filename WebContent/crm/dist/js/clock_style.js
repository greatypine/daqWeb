  //时钟-24小时
  var timerID = null;
  var timerRunning = false;
  function stopclock(){
      if(timerRunning)
          clearTimeout(timerID);
      timerRunning = false;
  }
  function startclock(){
      stopclock();
      showtime();
  }
  
  function showtime(){
	    var now = new Date();
	    var hours = now.getHours();
	    var minutes = now.getMinutes();
	    var seconds = now.getSeconds();
	    //var timeValue = "" + ((hours > 12) ? hours - 12 : hours)
	    var timeValue = ""+hours;
	    timeValue  += ((minutes < 10) ? ":0" : ":") + minutes;
	    timeValue  += ((seconds < 10) ? ":0" : ":") + seconds;
	    /**
	    if (hours>=6 && hours<=12)
	       {timeValue  += ("上午")}
	    if(hours>12 && hours<=18)
	       {timeValue += ("下午")}
	    if(hours>18 && hours <=24)
	       {timeValue +=("晚上")}
	    if(hours<6)
	       {timeValue += ("深夜")}
	    */
	    $("#face").html(timeValue);
	    timerID = setTimeout("showtime()",1000);
	    timerRunning = true;
	}