var pageStatusInfo = {};
	$(function(){
		// 获取请求参数
	    var requestParameters = getReauestParameters();
		pageStatusInfo = initPageStatusInfo(requestParameters);
		getPageRankData();
		//设置查询口径(城市/省/全国)
		setCityType();
	});
	// 设置系统时间
	var setCurrentDate = function (pageStatusInfo) {
	    var date = new Date();
	    var curYear = date.getFullYear(); //当前年
	    var curMonth = date.getMonth() + 1;//当前月
	    var curDay = date.getDate();//当前日
	    pageStatusInfo['currentYear'] = curYear;
	    pageStatusInfo['currentDay'] = curDay;
	    if(curDay==1){
	    	pageStatusInfo['currentMonth'] = curMonth-1;
	    }else{
	    	pageStatusInfo['currentMonth'] = curMonth;
	    }
	};
	  var autoPageRecordes=10;
  	  var manualPageRecordes=10;
  	  var isCreatePage=false;
  	  var progress_ = "";
  	  var progress_end = "";
  //切换每页显示的数据量
 function chnagePageRecords(t){
	 if(t==1){
		 autoPageRecordes  = $("#pageRecords_auto").val();
		 getPageRankData(1);
	 }else if(t==2){
		 $("#page_manual").empty();
		 manualPageRecordes  = $("#pageRecords_manual").val();
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
	    // 缩放级别
	    var rtnData = {
	        "cityId": cityId,
	        "provinceId": provinceId,
	        "typeRank":typeOfRank,
	        cityName: cityName,
	        provinceName: provinceName,
	        target:''
	    };
	    return rtnData;
	};
	var setCityType = function(){
		var cityId = pageStatusInfo.cityId;
		var provinceId = pageStatusInfo.provinceId;
		var provinceName = pageStatusInfo.provinceName;
		var cityName = pageStatusInfo.cityName;
		if(provinceId!=''&&cityId==''){
			$("#cityType").html(provinceName);
		}else if(cityId!=''&&provinceId==''){
			$("#cityType").html(cityName);
		}else if(provinceId==''&&cityId==''){
			$("#cityType").html('全国');
		}else if(provinceId!=''&&cityId!=''){
			$("#cityType").html(cityName);
		}
	}
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
	var getPageRankData = function(cur_page){//获取页面排名数据
	var typeOfRank = pageStatusInfo.typeRank;
	//var typeOfRank = "channel_order";
	var method = "";
	var managerName = "dynamicManager";
	var type_from = "";
	var type_title = "";
	var type_title2 = "";
	if(typeOfRank == 'city_gmv'){//城市(GMV)排名
		method = "getLastMonthCityRankingTop10";
		type_title = "城市("+pageStatusInfo['currentMonth']+"月GMV)排名";
		type_title2 = "城市排名";
		type_from = "0";
	}else if(typeOfRank == 'store_gmv'){//门店(GMV)排名
		method = "getLastMonthStoreRankingTop10";
		type_title = "门店("+pageStatusInfo['currentMonth']+"月GMV)排名";
		type_title2 = "门店排名";
		type_from = "1";
	}else if(typeOfRank == 'store_order'){//门店(订单量)排名--修改为门店排名(用户量)
		//method = "queryCityOrderRankingTop10";
		method = "queryCustomerCount";
		managerName = "OrderManager";
		type_title = "门店("+pageStatusInfo['currentMonth']+"月新用户量)排名";
		type_title2 = "门店排名";
		type_from = "2";
	}else if(typeOfRank == 'guoanxia_gmv'){//国安侠(GMV)排名
		method = "queryAreaTradeByEmp";
		type_title = "国安侠("+pageStatusInfo['currentMonth']+"月GMV)排名";
		type_title2 = "国安侠排名";
		type_from = "3";
	}else if(typeOfRank == 'commodity_gmv'){//商品(GMV)排名
		method = "queryProductCityOrder";
		type_title = "商品("+pageStatusInfo['currentMonth']+"月销售量)排名";
		type_title2 = "商品排名";
		type_from = "4";
	}else if(typeOfRank == 'businessDep_gmv'){//事业群(GMV)排名
		method = "queryTradeByDepName";
		type_title = "事业群("+pageStatusInfo['currentMonth']+"月GMV)排名";
		type_title2 = "事业群排名";
		type_from = "5";
	}else if(typeOfRank == 'channel_gmv'){//频道(GMV)排名
		method = "queryTradeByChannelName";
		type_title = "频道("+pageStatusInfo['currentMonth']+"月GMV)排名";
		type_from = "6";
	}else if(typeOfRank == 'channel_order'){//频道(订单量)排名
		method = "queryOrderCountByChannelName";
		type_title = "频道("+pageStatusInfo['currentMonth']+"月订单量)排名";
		type_title2 = "频道排名";
		type_from = "7";
	}
	$("#typeRankName").html(type_title);
	$("#title_sum").html(type_title2);
	$("head >title").html(type_title);
	pageStatusInfo.target = type_from;
	// 准备服务端数据请求参数
    var reqestParameter = {
        month:pageStatusInfo.currentMonth,
        year:pageStatusInfo.currentYear, 
        provinceId:pageStatusInfo.provinceId,
        cityId:pageStatusInfo.cityId,
        cityName:pageStatusInfo.cityName,
        provinceName:pageStatusInfo.provinceName
    }
    var pageInfo = new Object();
    if(typeof(cur_page) == "undefined"){
    	cur_page=1;
    }
    var parameterArray = [reqestParameter,pageInfo];
    if(type_from == "0"||type_from == "1"||type_from == "2"||type_from == "3"||type_from == "4"){
    	parameterArray = [reqestParameter,pageInfo,"1"];
    }else{
    	parameterArray = [reqestParameter,pageInfo,null];
    }
    pageInfo.currentPage = cur_page;
    pageInfo.recordsPerPage = autoPageRecordes;
    $("#totalRecord_can").html("0");
    doManager(managerName, method,parameterArray,
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
	        	 	  	    	getPageRankData(p);
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
                	}else if(type_from == '2'){
                		var name_title = $("<th>").attr("width","20%").html("门店名称");
                		var gmv_title = $("<th>").attr("width","25%").html("用户量（人）");
                	}else if(type_from == '3'){
                		var name_title = $("<th>").attr("width","20%").html("国安侠");
                		var gmv_title = $("<th>").attr("width","25%").html("GMV（元）");
                	}else if(type_from == '4'){
                		var name_title = $("<th>").attr("width","20%").html("商品名称");
                		var gmv_title = $("<th>").attr("width","25%").html("销售量（件）");
                	}else if(type_from == '5'){
                		number_title = $("<th>排名</th>").attr("width","10%");
                		var name_title = $("<th>").attr("width","20%").html("事业群名称");
                		var gmv_title = $("<th>").attr("width","25%").html("GMV（元）");
                	}else if(type_from == '6'){
                		number_title = $("<th>排名</th>").attr("width","10%");
                		var name_title = $("<th>").attr("width","20%").html("频道名称");
                		var gmv_title = $("<th>").attr("width","25%").html("GMV（元）");
                	}else if(type_from == '7'){
                		number_title = $("<th>排名</th>").attr("width","10%");
                		var name_title = $("<th>").attr("width","20%").html("频道名称");
                		var gmv_title = $("<th>").attr("width","25%").html("订单量（元）");
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
                    	}else if(type_from == '2'){
                    		data_name = val['store_name'];
                    		data_value = val['customer_count'];
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
                    	}else if(type_from == '7'){
                    		data_name = val['channel_name'];
                    		data_value = val['order_count'];
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
                		var td_city_rank = $("<td>").html(city_rank);
                		td_number.append(strong);
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
	var typeOfRank = pageStatusInfo.typeRank;
	// 准备服务端数据请求参数
    var reqestParameter = {
        month:pageStatusInfo.currentMonth,
        year:pageStatusInfo.currentYear,
        provinceId:pageStatusInfo.provinceId,
        cityId:pageStatusInfo.cityId,
        cityName:pageStatusInfo.cityName,
        provinceName:pageStatusInfo.provinceName,
        target:pageStatusInfo.target
    }
    parameterArray = [reqestParameter,"1"]
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