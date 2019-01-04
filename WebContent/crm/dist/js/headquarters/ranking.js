var pageStatusInfo = {};
$(document).ready(function () {
	var requestParameters = getReauestParameters();
	pageStatusInfo = initPageStatusInfo(requestParameters);
	initTableData();
});
var initPageStatusInfo = function (reqData) {
    // 复制请求参数
    var pageStatusInfo = jQuery.extend(true, {}, reqData);
    return pageStatusInfo;
}
// 获取请求参数
var getReauestParameters = function () {
	    // 城市ID
	    var cityId = (decode64(getUrlParamByKey("cs")) == 'null'||decode64(getUrlParamByKey("cs")) == null) ? '' : decode64(getUrlParamByKey("cs"));
	    // 省份ID
	    var provinceId = (decode64(getUrlParamByKey("ps"))=='null'||decode64(getUrlParamByKey("ps"))==null) ? '' : decode64(getUrlParamByKey("ps"));
		var type = (decode64(getUrlParamByKey("type"))=='null'||decode64(getUrlParamByKey("type"))==null) ? '' : decode64(getUrlParamByKey("type"));
	    // 缩放级别
	    var rtnData = {
	        "cityId": cityId,
	        "provinceId": provinceId,
	        "type":type
	    };
	    return rtnData;
};
var initTableData =function(cur_page){
	var type = pageStatusInfo.type;
	if(type=='yesterday'){
		method = "getProductYesteryRank";
	}else if(type=='seven'){
		method = "getProductSevendayRank";
	}else if(type=='thirty'){
		method = "getProductthirtydayRank";
	}
		// 准备服务端数据请求参数
    var reqestParameter = {
        provinceId:pageStatusInfo.provinceId,
        cityId:pageStatusInfo.cityId
    }
    var pageInfo = new Object();
    if(typeof(cur_page) == "undefined"){
    	cur_page=1;
    }
    pageInfo.currentPage = cur_page;
    pageInfo.recordsPerPage = autoPageRecordes;
    $("#totalRecord_can").html("0");
   doManager("massOrderItemManager", method,[reqestParameter],
	   function(data, textStatus, XMLHttpRequest) {
	        if (data.result) {
	            var resultJson = JSON.parse(data.data);
				 $("#auto_tbody").find("tr:gt(0)").remove(); 
                   totalPage_auto = result.total_pages;
	        	   $("#totalRecord_can").html(result.pageinfo.totalRecords);
	        	   if(!isCreatePage){
	        	   		$("#page_auto").createPage({
	        	 	  	    pageCount:totalPage_auto,
	        	 	  	    current:pageInfo.currentPage,
	        	 	  	    backFn:function(p){
	        	 	  	    	$("#tabFlag").val("1");
	        	 	  	    	initTableData(p);
	        	 	  	    }
	        	 	  	});
	        	 	  	createTable();
	        	   }
	        }
	});
    
}
