var pageStatusInfo = {};
var select_data = "";
var select_data2 = "";
	$(function(){
		// 获取请求参数
	    var requestParameters = getReauestParameters();
		pageStatusInfo = initPageStatusInfo(requestParameters);
		initCityData();
		initGMVDate();
		getPageRankData();
	});
	// 设置系统时间
	var setCurrentDate = function (pageStatusInfo) {
	    var date = new Date();
	    var curYear = date.getFullYear(); //当前年
	    var curMonth = date.getMonth() + 1;//当前月
	    var curDay = date.getDate();//当前日
	    pageStatusInfo['currentYear'] = curYear;
	    if(curDay==1){
	    	pageStatusInfo['currentMonth'] = curMonth-1;
	    }else{
	    	pageStatusInfo['currentMonth'] = curMonth;
	    }
	    pageStatusInfo['currentDay'] = curDay;
	};
	  var autoPageRecordes=10;
  	  var manualPageRecordes=10;
  	  var isCreatePage=false;
  	  var progress_ = "";
  	  var progress2_ = "";
  	  var progress_end = "";
  	  var progress_end2 = "";
  //切换每页显示的数据量
 function chnagePageRecords(t){
 	var citySelected = $("#citySelected").val();
 	var citySelectedName = $("#citySelectedName").val();
 	var currentMonth = $("#currentMonth").val();
	 if(t==1){
		 autoPageRecordes  = $("#pageRecords_auto").val();
		 getPageRankData(1,citySelected,citySelectedName,currentMonth);
	 }else if(t==2){
		 $("#page_manual").empty();
		 manualPageRecordes  = $("#pageRecords_manual").val();
	 }
 }
 function chnagePageRecords2(t){
	 var citySelected = $("#citySelected").val();
 	var citySelectedName = $("#citySelectedName").val();
 	var currentMonth = $("#currentMonth2").val();
	 if(t==1){
		 autoPageRecordes  = $("#pageRecords_auto2").val();
		 getPageOtherRankData(1,citySelected,citySelectedName,currentMonth);
	 }else if(t==2){
		 $("#page_manual").empty();
		 manualPageRecordes  = $("#pageRecords_manual2").val();
	 }
 }
	// 获取请求参数
	var getReauestParameters = function () {
	    // 城市ID
	    var cityId = (decode64(getUrlParamByKey("cs")) == 'null'||decode64(getUrlParamByKey("cs")) == null) ? '' : decode64(getUrlParamByKey("cs"));
	    // 省份ID
	    var provinceId = (decode64(getUrlParamByKey("p"))=='null'||decode64(getUrlParamByKey("p"))==null) ? '' : decode64(getUrlParamByKey("p"));
	    var typeOfRank = (decode64(getUrlParamByKey("tps"))=='null'||decode64(getUrlParamByKey("tps"))==null) ? '' : decode64(getUrlParamByKey("tps"));
	    var cityName = (decode64(getUrlParamByKey("cn")) == 'null'||decode64(getUrlParamByKey("cn")) == null) ? '' : decode64(getUrlParamByKey("cn"));
	    var provinceName = (decode64(getUrlParamByKey("pn")) == 'null'||decode64(getUrlParamByKey("pn")) == null) ? '' : decode64(getUrlParamByKey("pn"));
	    var targets = (decode64(getUrlParamByKey("fs")) == 'null'||decode64(getUrlParamByKey("fs")) == null) ? '' : decode64(getUrlParamByKey("fs"));
	    // 缩放级别
	    var rtnData = {
	        "cityId": cityId,
	        "provinceId": provinceId,
	        "typeRank":typeOfRank,
	        cityName: cityName,
	        provinceName: provinceName,
	        target:'',
	        targets:targets,
	        target2:'',
	        select_data:cityId,
	        select_data2:cityId,
	        select_data_city_name:cityName,
	        select_data_city_name2:cityName
	    };
	    return rtnData;
	};
	var initPageStatusInfo = function (reqData) {
	    // 复制请求参数
	    var pageStatusInfo = jQuery.extend(true, {}, reqData);
	    // 设置系统时间
	    setCurrentDate(pageStatusInfo);
	    return pageStatusInfo;
	}
	//查询上
    var currentPage_auto= 1;
    var totalPage_auto = 1;
	var getPageRankData = function(cur_page,cityId_,cityname_,month){//获取页面排名数据
	var typeOfRank = pageStatusInfo.typeRank;
	//var typeOfRank = "channel_order";
	var method = "";
	var type_from = "";
	var type_title = "";
	var cityId = (cityId_==null||cityId_=="") ? pageStatusInfo.cityId : cityId_;
	var cityName = (cityname_==null||cityname_=="") ? pageStatusInfo.cityName : cityname_;
	var inputElement_tab1 = $("<input id='store_id_manual_dept' name='store_id_manual_dept' type='hidden' bidTableFlag='true' value=''>")
	//var inputElement2_tab1 = $("<input type='text' class='form-control'  placeholder='门店' id='store_name_manual_dept' style='border-radius: 6px; box-shadow: 0 1px 1px rgba(0,0,0,0.3);'>")
	var divElement2_tab1 = $("<div class='auto hidden' id='auto_dept' style='width: 397px;z-index: 99999;'>");
	var inputElement_tab2 = $("<input id='store_id_manual_dept' name='store_id_manual_dept' type='hidden' bidTableFlag='true' value=''>")
	//var inputElement2_tab2 = $("<input type='text' class='form-control'  placeholder='门店' id='store_name_manual_dept' style='border-radius: 6px; box-shadow: 0 1px 1px rgba(0,0,0,0.3);'>")
	var divElement2_tab2 = $("<div class='auto hidden' id='auto_dept' style='width: 397px;z-index: 99999;'>");
	if(cityId_==0&&cityname_=='全部城市'){
		cityId = "";
		cityName="";	
	}
	var requestMonth = "";
	if(month!=null&&month!=""){
		requestMonth = month;
	}else{
		requestMonth = pageStatusInfo.currentMonth;
	}
	var search_title1 = "查询口径："+requestMonth+"月GMV（截止到昨日24点）";
	$("#store_dep").empty();
	if(typeOfRank == 'city_gmv'){//城市(GMV)排名
		method = "getLastMonthCityRankingTop10";
		type_title = "城市排名";
		type_from = "0";
		$("#nav-tabs").empty();
		$("#store_dep").remove();
		$("#store_dep").append(inputElement_tab1);
	}else if(typeOfRank == 'store_gmv'||typeOfRank == 'store_order'){//门店排名
		method = "getLastMonthStoreRankingTop10";
		type_title = "门店排名";
		type_from = "1";
		$("#nav-tabs").empty();
		var li_Element = "";
		var li2_Element = "";
		var tabFlag = $("#tabFlag").val();
		if(typeOfRank == 'store_gmv'||tabFlag=='1'){
			li_Element = $("<li class='active'>");
			li2_Element = $("<li class='' >");
		}else if(typeOfRank == 'store_order'||tabFlag=='2'){
			li_Element = $("<li>");
			li2_Element = $("<li class='active'>");
			$("#tab_1").removeClass("active");
			$("#tab_2").addClass("active");
		}
		var a_Element = $("<a href='#tab_1' data-toggle='tab' aria-expanded='false'>").html("门店GMV");
		var b_Element = $("<a href='#tab_2' data-toggle='tab' aria-expanded='true'>").html("门店消费用户量");
		li_Element.append(a_Element);
		li2_Element.append(b_Element);
		$("#nav-tabs").append(li_Element).append(li2_Element);
		getPageOtherRankData(1);
		$("#store_dep").append(inputElement_tab1).append(divElement2_tab1);
		$("#store_dep2").append(inputElement_tab2).append(divElement2_tab2);
		pageStatusInfo.target2 = 2;
	}else if(typeOfRank == 'guoanxia_gmv'){//国安侠(GMV)排名
		method = "queryAreaTradeByEmp";
		type_title = "国安侠排名";
		type_from = "3";
		$("#store_dep").append(inputElement_tab1).append(divElement2_tab1);
	}else if(typeOfRank == 'commodity_gmv'){//商品(GMV)排名
		search_title1 = "查询口径："+pageStatusInfo['currentMonth']+"月销量（截止到昨日24点）";
		method = "queryProductCityOrder";
		type_title = "商品排名";
		type_from = "4";
		$("#store_dep").append(inputElement_tab1);
	}else if(typeOfRank == 'businessDep_gmv'){//事业群(GMV)排名
		method = "queryTradeByDepName";
		type_title = "事业群排名";
		type_from = "5";
		$("#store_dep").append(inputElement_tab1);
	}else if(typeOfRank == 'channel_gmv'){//频道(GMV)排名
		method = "queryTradeByChannelName";
		type_title = "频道排名";
		type_from = "6";
		$("#nav-tabs").empty();
		var li_Element = $("<li class='active'>");
		var li2_Element = $("<li class=''>");
		var a_Element = $("<a href='#tab_1' data-toggle='tab' aria-expanded='true'>").html("频道GMV");
		var b_Element = $("<a href='#tab_2' data-toggle='tab' aria-expanded='false'>").html("频道订单量");
		li_Element.append(a_Element);
		li2_Element.append(b_Element);
		$("#nav-tabs").append(li_Element).append(li2_Element);
		getPageOtherRankData();
		$("#store_dep").append(inputElement_tab1);
		$("#store_dep2").append(inputElement_tab2);
		pageStatusInfo.target2 = 7;
	}
	$("#search-title").html(search_title1);
	$("#typeRankName").html(type_title);
	$("#title_sum").html(type_title);
	$("head >title").html(type_title);
	pageStatusInfo.target = type_from;
	// 准备服务端数据请求参数
    var reqestParameter = {
        month:requestMonth,
        year:pageStatusInfo.currentYear, 
        provinceId:pageStatusInfo.provinceId,
        cityId:cityId,
        cityName:cityName,
        provinceName:pageStatusInfo.provinceName
    }
    var pageInfo = new Object();
    if(typeof(cur_page) == "undefined"){
    	cur_page=1;
    }
    pageInfo.currentPage = cur_page;
    pageInfo.recordsPerPage = autoPageRecordes;
    $("#totalRecord_can").html("0");
    var parameterArray = [reqestParameter,pageInfo];
    if(type_from == "0"||type_from == "1"||type_from == "2"||type_from == "3"||type_from == "4"){
    	parameterArray = [reqestParameter,pageInfo,"1"];
    }else{
    	parameterArray = [reqestParameter,pageInfo,null];
    }
    doManager("dynamicManager", method,parameterArray,
            function(data, textStatus, XMLHttpRequest) {
                if (data.result) {
                   var result = JSON.parse(data.data);
                   $("#auto_tbody").find("tr:gt(0)").remove(); 
                   totalPage_auto = result.total_pages;
	        	   $("#totalRecord_can").html(result.pageinfo.totalRecords);
	        	   if(!isCreatePage){
	        	   		$("#page_auto").createPage({
	        	 	  	    pageCount:totalPage_auto,
	        	 	  	    current:pageInfo.currentPage,
	        	 	  	    backFn:function(p){
	        	 	  	    	$("#tabFlag").val("1");
	        	 	  	    	getPageRankData(p,cityId_,cityname_,requestMonth);
	        	 	  	    }
	        	 	  	});
	        	   }
                	var tr_element = $("<tr id='th_title'>");
                	var number_title = $("<th>所在城市排名</th>").attr("width","10%");
                	var rank_title = $("<th>").attr("width","40%");
                    var resultJson = JSON.parse(data.data);
                    var tbody = $("<tbody>");
                    var city_rank_title = $("<th>全国排名</th>").attr("width","10%")
                    if(type_from == '0'){
                    	var name_title = $("<th>").attr("width","20%").html("城市名称");
                		var gmv_title = $("<th>").attr("width","25%").html("GMV（元）");
                	}else if(type_from == '1'){
                		var name_title = $("<th>").attr("width","20%").html("门店名称");
                		var gmv_title = $("<th>").attr("width","25%").html("GMV（元）");
                	}else if(type_from == '3'){
                		var name_title = $("<th>").attr("width","30%").html("国安侠");
                		var gmv_title = $("<th>").attr("width","25%").html("GMV（元）");
                	}else if(type_from == '4'){
                		var name_title = $("<th>").attr("width","30%").html("商品名称");
                		var gmv_title = $("<th>").attr("width","25%").html("销售量（件）");
                	}else if(type_from == '5'){
                		number_title = $("<th>排名</th>").attr("width","10%");
                		var name_title = $("<th>").attr("width","30%").html("事业群名称");
                		var gmv_title = $("<th>").attr("width","25%").html("GMV（元）");
                	}else if(type_from == '6'){
                		number_title = $("<th>排名</th>").attr("width","10%");
                		var name_title = $("<th>").attr("width","30%").html("频道名称");
                		var gmv_title = $("<th>").attr("width","25%").html("GMV（元）");
                	}
                	if(type_from == '5'||type_from == '6'||type_from == '7'){
                    	tr_element.append(number_title).append(name_title).append(rank_title).append(gmv_title);
                	}else{
                    	tr_element.append(number_title).append(city_rank_title).append(name_title).append(rank_title).append(gmv_title);
                	}
                	tbody.append(tr_element);
                	$("#rank_table").empty();
                    $.each(eval(resultJson['gmv']), function (idx, val) {
                    	var data_name = "";
                    	var data_value = "";
                    	var data_value_format = "";
                    	var city_rank = "";
                    	city_rank = val['rank'];
                    	var rank = val['cityrank'];
                    	if(type_from == '0'){
                    		data_name = val['city_name'];
                    		data_value = val['gmv_sum'];
                    	}else if(type_from == '1'){
                    		data_name = val['store_name'];
                    		data_value = val['store_pesgmv'];
                    	}else if(type_from == '3'){
                    		data_name = val['employee_a_name'];
                    		data_value = val['pesgmv'];
                    	}else if(type_from == '4'){
                    		data_name = val['product_name'];
                    		data_value = val['product_count'];
                    	}else if(type_from == '5'){
                    		data_name = val['dep_name'];
                    		data_value = val['order_amount'];
                    	}else if(type_from == '6'){
                    		data_name = val['channel_name'];
                    		data_value = val['order_amount'];
                    	}
                    	data_value_format = outputMoney(String(data_value));
                		var tr_parent = $("<tr>");
                		var td_number = "";
                		var color = "";
                		var span_gmv = "";
                		var div_rank_2 = "";
                		if(idx == 0){
                			color = "red";
                		}else if(idx == 1){
                			color = "green";
                		}else if(idx == 2){
                			color = "aqua";
                		}
                		if(color!=""&&pageInfo.currentPage==1){
                			if(idx==0){
                				progress_ = (parseInt(data_value)/0.9);
                			}
                			progress_end = (data_value/progress_)*100+'%';
                			td_number = $("<td>").attr("class","text-"+color);
                			span_gmv = $("<span>").attr("class","badge bg-"+color).html(data_value_format);
                			if(color == 'red'){
                				div_rank_2 = $("<div>").attr("class","progress-bar progress-bar-danger").attr("style","width: "+progress_end);
                			}else if(color == 'green'){
                				div_rank_2 = $("<div>").attr("class","progress-bar progress-bar-success").attr("style","width: "+progress_end);
                			}else if(color == 'aqua'){
                				div_rank_2 = $("<div>").attr("class","progress-bar progress-bar-info").attr("style","width: "+progress_end);
                			}
                		}else{
                			progress_end = (data_value/progress_)*100+'%';
                			td_number = $("<td>");
                			span_gmv = $("<span>").html(data_value_format);
                			div_rank_2 = $("<div>").attr("class","progress-bar progress-bar-default").attr("style","width: "+progress_end);
                		}
                		var strong = "";
                		if(type_from == '5'||type_from == '6'||type_from == '7'){
                			strong = $("<strong>").attr("style","font-size: 16px;").html((((typeof(pageInfo.currentPage) == "undefined")? '1' :pageInfo.currentPage) -1)*pageInfo.recordsPerPage+(idx+1));
                		}else{
                			strong = $("<strong>").attr("style","font-size: 16px;").html(rank);
                		}
                		td_number.append(strong);
                		var td_city_rank = $("<td>").html(city_rank);
                		var td_name = $("<td>").html(data_name);
                		var td_rank = $("<td>");
                		var div_rank = $("<div>").attr("class","progress progress-sm progress-striped active");
                		div_rank.append(div_rank_2);
                		td_rank.append(div_rank);
                		var td_gmv = $("<td>").append(span_gmv);
                		if(type_from == '5'||type_from == '6'||type_from == '7'){
                			tr_parent.append(td_number).append(td_name).append(td_rank).append(td_gmv);
                		}else{
                			tr_parent.append(td_number).append(td_city_rank).append(td_name).append(td_rank).append(td_gmv);
                		}
                		tbody.append(tr_parent);
                    });
                        $("#rank_table").append(tbody);
                }
     },false);
	
}
var export_data = function(){
 	$("#process_div").show();
	$("#process_div_pic").show();
	var cityId_o = pageStatusInfo.select_data;
	var cityName_o = pageStatusInfo.select_data_city_name;
	var typeOfRank = pageStatusInfo.typeRank;
	var s_date = $("#crm_date_gmv").val();
	var export_month = "";
	if(s_date!=null&&s_date!=""){
		export_month = s_date;
	}else{
		export_month = pageStatusInfo.currentMonth;
	}
	// 准备服务端数据请求参数
    var reqestParameter = {
        month:export_month,
        year:pageStatusInfo.currentYear,
        provinceId:pageStatusInfo.provinceId,
        cityId:cityId_o,
        cityName:cityName_o,
        provinceName:pageStatusInfo.provinceName,
        target:pageStatusInfo.target
    }
    var parameterArray = [reqestParameter];
    if(typeOfRank == 'store_gmv'){
    	parameterArray = [reqestParameter,"1"]
    }else{
    	parameterArray = [reqestParameter,null]
    }
	doManager('dynamicManager','exportData',parameterArray,function(data){
	if(data.result){
  	   var result= JSON.parse(data.data);
       if(result.status=='success'){
    	   window.location.href=result.data;
       }else{
    	   crm_alert(0,"请先确认是否有符合条件的数据，重新请求！");
    	   $("#process_div").hide();
	       $("#process_div_pic").hide();
    	   return;
       }
  
  }else{
   crm_alert(5,"请稍后重新请求！");
  }
  $("#process_div").hide();
  $("#process_div_pic").hide();
  });
}
var export_data2 = function(){
 	$("#process_div").show();
	$("#process_div_pic").show();
	var cityId_o = pageStatusInfo.select_data2;
	var typeOfRank = pageStatusInfo.typeRank;
	var s_date = $("#crm_date_gmv2").val();
	var export_month = "";
	if(s_date!=null&&s_date!=""){
		export_month = s_date;
	}else{
		export_month = pageStatusInfo.currentMonth;
	}
	// 准备服务端数据请求参数
    var reqestParameter = {
        month:export_month,
        year:pageStatusInfo.currentYear,
        provinceId:pageStatusInfo.provinceId,
        cityId:cityId_o,
        cityName:pageStatusInfo.cityName,
        provinceName:pageStatusInfo.provinceName,
        target:pageStatusInfo.target2
    }
    var parameterArray = [reqestParameter];
    if(typeOfRank=='businessDep_gmv'||typeOfRank == 'channel_gmv'){
    	parameterArray = [reqestParameter,"1"]
    }else{
    	parameterArray = [reqestParameter,null]
    }
	doManager('dynamicManager','exportData',parameterArray,function(data){
	if(data.result){
  	   var result= JSON.parse(data.data);
       if(result.status=='success'){
    	   window.location.href=result.data;
       }else{
    	   crm_alert(0,"请先确认是否有符合条件的数据，重新请求！");
    	   $("#process_div").hide();
	       $("#process_div_pic").hide();
    	   return;
       }
  
  }else{
   crm_alert(5,"请稍后重新请求！");
  }
  $("#process_div").hide();
  $("#process_div_pic").hide();
  });
}
var getPageOtherRankData = function(cur_page,cityId_,cityname_,month){//获取页面排名数据
	var typeOfRank = pageStatusInfo.typeRank;
	var method = "";
	var managerName = "dynamicManager";
	var type_from = "";
	var type_title = "";
	var cityId = (cityId_==null||cityId_=="") ? pageStatusInfo.cityId : cityId_;
	var cityName = (cityname_==null||cityname_=="") ? pageStatusInfo.cityName : cityname_;
	var type_title = "";
	if(typeOfRank == 'store_gmv'||typeOfRank == 'store_order'){//门店(订单量)排名
		managerName = "OrderManager";
		method = "queryCustomerCount";
		type_from = "2";
		type_title = "门店排名";
	}else if(typeOfRank == 'channel_gmv'){//频道(GMV)排名
		method = "queryOrderCountByChannelName";
		type_from = "6";
		type_title = "频道排名";
	}
	$("#typeRankName").html(type_title);
	if(cityId_==0&&cityname_=='全部城市'){
		cityId = "";
		cityName="";	
	}
	var requestMonth = "";
	if(month!=null&&month!=""){
		requestMonth = month;
	}else{
		requestMonth = pageStatusInfo.currentMonth;
	}
	var search_title2 = "";
	if(type_from == '2'){
		search_title2 = "查询口径："+requestMonth+"月消费用户量（实时）";
	}else{
		search_title2 = "查询口径："+requestMonth+"月订单量（截止到昨日24点）";
	}
	$("#search-title2").html(search_title2);
	$("#title_sum").html(type_title);
	$("head >title").html(type_title);
	// 准备服务端数据请求参数
    var reqestParameter = {
        month:requestMonth,
        year:pageStatusInfo.currentYear, 
        provinceId:pageStatusInfo.provinceId,
        cityId:cityId,
        cityName:cityName,
        provinceName:pageStatusInfo.provinceName
    }
    var pageInfo = new Object();
    if(typeof(cur_page) == "undefined"){
    	cur_page=1;
    }
    var parameterArray = [reqestParameter,pageInfo,null];
    if(type_from == '2'){
    	parameterArray = [reqestParameter,pageInfo,"1"]
    }
    pageInfo.currentPage = cur_page;
    pageInfo.recordsPerPage = autoPageRecordes;
    $("#totalRecord_can2").html("0");
    doManager(managerName, method,parameterArray,
            function(data, textStatus, XMLHttpRequest) {
                if (data.result) {
                   var result = JSON.parse(data.data);
                   $("#auto_tbody").find("tr:gt(0)").remove(); 
                   totalPage_auto = result.total_pages;
	        	   $("#totalRecord_can2").html(result.pageinfo.totalRecords);
	        	   if(!isCreatePage){
	        	   		$("#page_auto2").createPage({
	        	 	  	    pageCount:totalPage_auto,
	        	 	  	    current:pageInfo.currentPage,
	        	 	  	    backFn:function(p){
	        	 	  	    	$("#tabFlag").val("2");
	        	 	  	    	getPageOtherRankData(p,cityId_,cityname_,requestMonth);
	        	 	  	    }
	        	 	  	});
	        	   }
                	var tr_element = $("<tr id='th_title'>");
                	var number_title = $("<th>所在城市排名</th>").attr("width","10%");
                	var rank_title = $("<th>").attr("width","35%");
                    var resultJson = JSON.parse(data.data);
                    var tbody = $("<tbody>");
                   if(type_from == '2'){
                   		var city_rank_title = $("<th>").attr("width","10%").html("全国排名");
                		var name_title = $("<th>").attr("width","20%").html("门店名称");
                		var gmv_title = $("<th>").attr("width","25%").html("用户量（人）");
                	}else if(type_from == '6'){
                		number_title = $("<th>排名</th>").attr("width","10%");
                		var name_title = $("<th>").attr("width","20%").html("频道名称");
                		var gmv_title = $("<th>").attr("width","25%").html("订单量（元）");
                	}
                	if(type_from == '6'){
                    	tr_element.append(number_title).append(city_rank_title).append(name_title).append(rank_title).append(gmv_title);
                	}else{
                    	tr_element.append(number_title).append(city_rank_title).append(name_title).append(rank_title).append(gmv_title);
                	}
                	tbody.append(tr_element);
                	$("#rank2_table").empty();
                    $.each(eval(resultJson['gmv']), function (idx, val) {
                    	var city_rank = "";
                    	var data_name = "";
                    	var data_value = "";
                    	var city_rank = val['rank'];
                    	var rank = val['cityrank'];
                    	if(type_from == '2'){
                    		data_name = val['store_name'];
                    		data_value = val['customer_count'];
                    	}else if(type_from == '6'){
                    		data_name = val['channel_name'];
                    		data_value = val['order_count'];
                    	}
                		var tr_parent = $("<tr>");
                		var td_number = "";
                		var color = "";
                		var span_gmv = "";
                		var div_rank_2 = "";
                		if(idx == 0){
                			color = "red";
                		}else if(idx == 1){
                			color = "green";
                		}else if(idx == 2){
                			color = "aqua";
                		}
                		if(color!=""&&pageInfo.currentPage==1){
                			if(idx==0){
                				progress2_ = (parseInt(data_value)/0.9);
                			}
                			progress_end2 = (data_value/progress2_)*100+'%';
                			td_number = $("<td>").attr("class","text-"+color);
                			span_gmv = $("<span>").attr("class","badge bg-"+color).html(data_value);
                			if(color == 'red'){
                				div_rank_2 = $("<div>").attr("class","progress-bar progress-bar-danger").attr("style","width: "+progress_end2);
                			}else if(color == 'green'){
                				div_rank_2 = $("<div>").attr("class","progress-bar progress-bar-success").attr("style","width: "+progress_end2);
                			}else if(color == 'aqua'){
                				div_rank_2 = $("<div>").attr("class","progress-bar progress-bar-info").attr("style","width: "+progress_end2);
                			}
                		}else{
                			progress_end2 = (data_value/progress2_)*100+'%';
                			td_number = $("<td>");
                			span_gmv = $("<span>").html(data_value);
                			div_rank_2 = $("<div>").attr("class","progress-bar progress-bar-default").attr("style","width: "+progress_end2);
                		}
                		var strong = $("<strong>").attr("style","font-size: 16px;").html((((typeof(pageInfo.currentPage) == "undefined")? '1' :pageInfo.currentPage) -1)*pageInfo.recordsPerPage+(idx+1));
                		var strong = "";
                		if(type_from == '6'){
                			strong = $("<strong>").attr("style","font-size: 16px;").html((((typeof(pageInfo.currentPage) == "undefined")? '1' :pageInfo.currentPage) -1)*pageInfo.recordsPerPage+(idx+1));
                		}else if(type_from == '2'){
                			strong = $("<strong>").attr("style","font-size: 16px;").html(rank);
                		}
                		td_number.append(strong);
                		var td_city_rank = $("<td>").html(city_rank);
                		var td_name = $("<td>").html(data_name);
                		var td_rank = $("<td>");
                		var div_rank = $("<div>").attr("class","progress progress-sm progress-striped active");
                		div_rank.append(div_rank_2);
                		td_rank.append(div_rank);
                		var td_gmv = $("<td>").append(span_gmv);
                		if(type_from == '2'){
                			tr_parent.append(td_number).append(td_city_rank).append(td_name).append(td_rank).append(td_gmv);
                		}else{
                			tr_parent.append(td_number).append(td_name).append(td_rank).append(td_gmv);
                		}
                		tbody.append(tr_parent);
                    });
                        $("#rank2_table").append(tbody);
                }
     },false);
}
var initCityData = function(){
	if(pageStatusInfo.targets==0){//总部
    	initallcity(); 
    }else if(pageStatusInfo.targets==1) {//城市
    	initcurcity();
    }
}
function initallcity(){
 		doManager("DistCityCodeManager","queryAllDistCityList",null,
 		    function(data,textStatus,XmlHttpRequest){
 				if (data.result) {
 					var jsonData = $.fromJSON(data.data);
 					$("#citySelect").append("<option value='0'>全部城市</option>");
 					$("#citySelect2").append("<option value='0'>全部城市</option>");
 					$(jsonData).each(function(index,element){
 						$("#citySelect").append('<option value="'+element.id+'">'+element.cityname+'</option>');
 						$("#citySelect2").append('<option value="'+element.id+'">'+element.cityname+'</option>');
                     });
 				}
 		});
 	}
 function initcurcity(){
 		doManager("userManager","getCurrentUserCity",null,
 		    function(data,textStatus,XmlHttpRequest){
 				if (data.result) {
 					var jsonData = $.fromJSON(data.data);
 					var IndexSelected = "";
 					$(jsonData).each(function(index,element){
 						if(pageStatusInfo.cityName == element.cityname){
 							IndexSelected = index;
 						}
 						$("#citySelect").append('<option value="'+element.cityid+'">'+element.cityname+'</option>');
 						$("#citySelect2").append('<option value="'+element.cityid+'">'+element.cityname+'</option>');
                     });
 					 $("#citySelect")[0].selectedIndex = IndexSelected;
 					 $("#citySelect2")[0].selectedIndex = IndexSelected;
 					
 				}
 		});
 	}
 function search_data2(){
 	var cityId = $("#citySelect2").val();
 	var index=$("#citySelect2")[0].selectedIndex ;
 	var cityname = $("#citySelect2")[0].options[index].text;
 	var s_date = $("#crm_date_gmv2").val();
 	$("#citySelected2").val(cityId);
 	$("#citySelectedName2").val(cityname);
 	$("#tabFlag").val("2");
 	if(cityId!=0){
 		pageStatusInfo.select_data2 = cityId;
 		pageStatusInfo.select_data_city_name2=cityname;
 	}else{
 		pageStatusInfo.select_data2 = "";
 		pageStatusInfo.select_data_city_name2="";
 	}
 	getPageOtherRankData(1,cityId,cityname,s_date)
 }
  function search_data(){
 	var cityId = $("#citySelect").val();
 	var index=$("#citySelect")[0].selectedIndex ;
 	var cityname = $("#citySelect")[0].options[index].text;
 	$("#citySelected").val(cityId);
 	$("#citySelectedName").val(cityname);
 	var s_date = $("#crm_date_gmv").val();
 	$("#tabFlag").val("1");
 	if(cityId!=0){
 		pageStatusInfo.select_data = cityId;
 		pageStatusInfo.select_data_city_name=cityname;
 	}else{
 		pageStatusInfo.select_data = "";
 		pageStatusInfo.select_data_city_name="";
 	}
 	getPageRankData(1,cityId,cityname,s_date);
 }
 function resetSerachParam(str){
 	if(str==1){
 		$("#citySelect").val('');
 	}else if(str==2){
 		$("#citySelect2").val('');
 	}
 }
   var initGMVDate = function(){
   	  var typeOfRank = pageStatusInfo.typeRank;
	  var c_year = pageStatusInfo.curYear; //当前年
	  var c_month = pageStatusInfo.currentMonth;//当前月
	  $("#crm_date_gmv").val(parseInt(c_month));
	  $("#crm_date_gmv2").val(parseInt(c_month));
	  var date_option = "";
	  for(var i=1;i<=c_month;i++){
		  var year_month = i;
		  var y_m =i+"月";
		  if(i==(c_month)){//上一个月
			  date_option=date_option+"<option value='"+i+"' selected>"+y_m+"</option>";
		  }else if(c_month==1){//当前月是一月
			  date_option=date_option+"<option value='"+i+"' selected>"+y_m+"</option>";
		  }else{
			  date_option=date_option+"<option value='"+i+"' >"+y_m+"</option>";
		  }
	  }
	  if(typeOfRank == 'commodity_gmv'){
   	  	 date_option="<option value='"+c_month+"' selected>"+(c_month+'月')+"</option>";
   	  }
	  $("#crm_date_gmv").append(date_option);
	  $("#crm_date_gmv2").append(date_option);
	  if(c_month!=null){
		  $("#crm_date_gmv").val(parseInt(c_month));
		  $("#crm_date_gmv2").val(parseInt(c_month));
	  }
  }
 var  reloadGMVByDate = function (){
	  var s_date = $("#crm_date_gmv").val();
	  var cityId = $("#citySelect").val();
 	  var index=$("#citySelect")[0].selectedIndex ;
 	  var cityname = $("#citySelect")[0].options[index].text;
	  $("#currentMonth").val(s_date);
	  $("#tabFlag").val("1");
 	  getPageRankData(1,cityId,cityname,s_date);
  }
 var  reloadGMVByDate2 = function (){
	  var s_date = $("#crm_date_gmv2").val();
	  var cityId = $("#citySelect2").val();
 	  var index=$("#citySelect2")[0].selectedIndex ;
 	  var cityname = $("#citySelect2")[0].options[index].text;
	  $("#currentMonth2").val(s_date);
	  $("#tabFlag").val("2");
 	  getPageOtherRankData(1,cityId,cityname,s_date);
  }