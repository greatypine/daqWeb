var pageStatusInfo = {};
var autoPageRecordes = 10;
var reqestParameter;
$(document).ready(function () {
	var requestParameters = getReauestParameters();
	pageStatusInfo = initPageStatusInfo(requestParameters);
	$("#search_size_page").change(function(){
		var current_page = $("#current").val();
		var product_name = $("#search_store").val();
		$("#selectChange").val(true);
		selectProductInfoList(current_page,product_name);
	});
	$("#search_store_a").bind('click', function(event) {
    	var current_page = $("#current").val();
        var product_name = $("#search_store").val();
        $("#keyup").val(true);
		selectProductInfoList(current_page,product_name);
    });
	selectProductInfoList("1","");
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
var selectProductInfoList =function(cur_page,product_name){
	var type = pageStatusInfo.type;
	if(type=='yesterday'){
		method = "getProductYesteryRank";
		$("#title_sum").html("昨日商品排名");
		$("#page_title").html("昨日商品排名");
	}else if(type=='seven'){
		method = "getProductSevendayRank";
		$("#title_sum").html("近7日商品排名");
		$("#page_title").html("近7日商品排名");
	}else if(type=='thirty'){
		method = "getProductthirtydayRank";
		$("#title_sum").html("近30日商品排名");
		$("#page_title").html("近30日商品排名");
	}
		// 准备服务端数据请求参数
    reqestParameter = {
        provinceId:pageStatusInfo.provinceId,
        cityId:pageStatusInfo.cityId,
        searchstr:product_name
    }
	var dataSet = [];
 //每页显示多少条数据
 var dataSet = [];
 //每页显示多少条数据
 var limit = "10";
 var pageInfo = new Object();
 var autoPageRecordes = 10;
 if(typeof(cur_page) == "undefined"){
    	cur_page=1;
 }
var autoPageRecordes_=$("#search_size_page").val();
var keyup = $("#keyup").val();
var selectChange = $("#selectChange").val();
pageInfo.currentPage = cur_page;
if(keyup=="true"){
	pageInfo.currentPage = 1;
}
if(selectChange=="true"){
	pageInfo.currentPage = 1;
}
$("#selectChange").val("false");
var keyup = $("#keyup").val("false");
pageInfo.recordsPerPage = autoPageRecordes_;
doManager("massOrderItemManager",method, [reqestParameter,pageInfo],
    function (data, textStatus, XMLHttpRequest) {
        if (data.result) {
        		 var result = JSON.parse(data.data);
                 //总条数
                 var total = result.lst_data.pageinfo.totalRecords;
                 //每页显示条数
                 var size = result.lst_data.pageinfo.recordsPerPage;
                 //总页数
                 var pages = result.lst_data.total_pages;
                 //当前页数
                 var current = result.lst_data.pageinfo.currentPage;
                 //将分页信息保存到页面
                 $("#total").val(total);
                 $("#size").val(size);
                 $("#pages").val(pages);
                 $("#current").val(current);

                 //DataTables每页显示多少条数据
                 var iDisplayLength = size;
                 //服务器上总数据条数
                 var listSize = total;

                 //最后一页的时候这样放数据
                 if(pages==current){
                     iDisplayLength = total - size*(pages-1);
                     listSize = iDisplayLength*pages;
                 }

                 //序号
                 var no = (current - 1) * size;
 				 for(var i = 0;i < listSize;i++){
                     var arr = [];
                     no = no + 1;
                     if(i < result.lst_data.data.length){
                         arr[0] = (current-1)*size+(i+1);
                         arr[1] = result.lst_data.data[i].product_name;
                         arr[2] = result.lst_data.data[i].store_name;
                         arr[3] = result.lst_data.data[i].product_gmv;
                         arr[4] = result.lst_data.data[i].rank;
                         dataSet[i]=arr;
                     }else{
                         arr[0] = no;
                         arr[1] = "";
                         arr[2] = "";
                         arr[3] = "";
                         arr[4] = "0";
                         dataSet[i]=arr;
                     }
                 }
			$('#example1').DataTable( {
                     data: dataSet,
                     searching: false,
                     bLengthChange:false,
                     bFilter: false, //过滤功能
                     bSort: false,
                     deferRender: true,
                     bDestroy: true,
                     iDisplayStart: 0,
                     iDisplayLength:iDisplayLength,
                     columns: [
                      { title: "排名" },
 					  { title: "商品名称" },
                      { title: "门店名称" },
                      { title: "销量" },
                      { title: "趋势" }
                     ],
				oLanguage: {  
				       "sProcessing" : "正在加载中......",  
				       "sLengthMenu" : "_MENU_",  
				       "sZeroRecords" : "无记录",  
				       "sEmptyTable" : "表中无数据存在！",  
				       "sInfo" : "当前显示 "+((current - 1) * size+1)+" 到 "+(size*(current-1) + iDisplayLength)+" 条，共 "+total+"  条记录",  
				       "sInfoEmpty" : "没有数据",  
				       "sInfoFiltered" : "数据表中共为 _TOTAL_ 条记录",  
				       "sSearch" : " ",  
				       "oPaginate" : {  
				        "sFirst" : " 首页 ",  
				        "sPrevious" : " 上一页 ",  
				        "sNext" : " 下一页 ",  
				        "sLast" : " 末页 "  
				        }
						}
                 	}
				);
                 //分页
                 pagination(pageInfo.currentPage,pages);
				updateTableStyle(result,current);
		}
    }, false);
}


//分页
function pagination(current,pages) {
	var product_name = $("#search_store").val();
            //分页初始化部分
   //当前页大于1 上一页 按钮可用
            if((parseInt(current)) > 1){
                $("#example1_previous").removeClass("disabled");
            }
            //第一页按钮显示为未选中状态
            $(".paginate_button")[1].className="paginate_button";
   //当前页码 按钮被选中
            //大于8页
            if(pages>8){
                if(current<5){
                    $(".paginate_button")[(current)].className="paginate_button active";
                }else if(current>=5&&current<=(pages-4)){
                    $(".paginate_button")[1].innerHTML="<a href='#' aria-controls='example1' data-dt-idx='1' tabindex='0'>1</a>";
                    $(".paginate_button")[2].innerHTML="<a href='#' aria-controls='example1' tabindex='0'>...</a>";
                    $(".paginate_button").eq(2).addClass("disabled");
                    $(".paginate_button")[3].innerHTML="<a href='#' aria-controls='example1' data-dt-idx='"+(parseInt(current))+"' tabindex='0'>"+(parseInt(current))+"</a>";
                    $(".paginate_button")[4].innerHTML="<a href='#' aria-controls='example1' data-dt-idx='"+(parseInt(current)+1)+"' tabindex='0'>"+(parseInt(current)+1)+"</a>";
                    $(".paginate_button")[5].innerHTML="<a href='#' aria-controls='example1' data-dt-idx='"+(parseInt(current)+2)+"' tabindex='0'>"+(parseInt(current)+2)+"</a>";
					$(".paginate_button")[3].className="paginate_button active";
                }else if(current>(pages-4)){
                    $(".paginate_button")[1].innerHTML="<a href='#' aria-controls='example1' data-dt-idx='"+1+"' tabindex='0'>"+1+"</a>";
                    $(".paginate_button")[2].innerHTML="<a href='#' aria-controls='example1' tabindex='0'>...</a>";
                    $(".paginate_button").eq(2).addClass("disabled");
                    $(".paginate_button")[3].innerHTML="<a href='#' aria-controls='example1' data-dt-idx='"+(parseInt(pages-4))+"' tabindex='0'>"+(parseInt(pages)-4)+"</a>";
                    $(".paginate_button")[4].innerHTML="<a href='#' aria-controls='example1' data-dt-idx='"+(parseInt(pages)-3)+"' tabindex='0'>"+(parseInt(pages)-3)+"</a>";
                    $(".paginate_button")[5].innerHTML="<a href='#' aria-controls='example1' data-dt-idx='"+(parseInt(pages)-2)+"' tabindex='0'>"+(parseInt(pages)-2)+"</a>";
                    $(".paginate_button")[6].innerHTML="<a href='#' aria-controls='example1' data-dt-idx='"+(parseInt(pages)-1)+"' tabindex='0'>"+(parseInt(pages)-1)+"</a>";
					$(".paginate_button").eq(6).removeClass("disabled");
					if(current==(pages-4)){
                        $(".paginate_button")[3].className="paginate_button active";
                    }else if(current==(pages-3)){
                        $(".paginate_button")[4].className="paginate_button active";
                    }else if(current==(pages-2)){
                        $(".paginate_button")[5].className="paginate_button active";
                    }else if(current==(pages-1)){
                        $(".paginate_button")[6].className="paginate_button active";
                    }else if(current==pages){
                        $(".paginate_button")[7].className="paginate_button active";
						$("#example1_next").addClass("disabled");
                    }
                }
            //小于8页
            }else if(pages<8){
                $(".paginate_button")[current].className="paginate_button active";
				if($("#total").val()==0){
					$(".paginate_button")[2].remove();
					$(".paginate_button")[2].remove();
				}
				if(current==pages){
					$("#example1_next").addClass("disabled");
				}
            //等于8页
            }else if(pages==8){
                if(current<5){
                    $(".paginate_button")[current].className="paginate_button active";
                }else{
                    $(".paginate_button")[1].innerHTML="<a href='#' aria-controls='example1' data-dt-idx='"+(parseInt(current)-5)+"' tabindex='0'>"+(parseInt(current)-5)+"</a>";
                    $(".paginate_button")[2].innerHTML="<a href='#' aria-controls='example1' data-dt-idx='"+(parseInt(current)-4)+"' tabindex='0'>"+(parseInt(current)-4)+"</a>";
                    $(".paginate_button").eq(2).addClass("disabled");
                    $(".paginate_button")[3].innerHTML="<a href='#' aria-controls='example1' data-dt-idx='"+(parseInt(current)-3)+"' tabindex='0'>"+(parseInt(current)-3)+"</a>";
                    $(".paginate_button")[4].innerHTML="<a href='#' aria-controls='example1' data-dt-idx='"+(parseInt(current)-2)+"' tabindex='0'>"+(parseInt(current)-2)+"</a>";
                    $(".paginate_button")[5].innerHTML="<a href='#' aria-controls='example1' data-dt-idx='"+(parseInt(current)-1)+"' tabindex='0'>"+(parseInt(current)-1)+"</a>";
                    if(current==5){
                        $(".paginate_button")[3].className="paginate_button active";
                    }else if(current==6){
                        $(".paginate_button")[4].className="paginate_button active";
                    }else if(current==7){
                        $(".paginate_button")[5].className="paginate_button active";
                    }else if(current==8){
                        $(".paginate_button")[6].className="paginate_button active";
                    }
                }
            }

            //移除DataTables自带的 分页 事件
            $(".paginate_button").attr('onclick','').unbind('click');
            //添加 分页 事件
            $(".paginate_button").click(function(){
                var jumpPage = $(this.innerHTML).html().replace(/\s+/g,"");
                if(jumpPage=="上一页"){
                    if((parseInt(current)-1) > 1){
                      $("#example1_previous").removeClass("disabled");
                    }else{
                      $("#example1_previous").addClass("disabled");
					  if((parseInt(current)-1) < 1){
					  	return;
					  }
                    }
					searchProductInfoList(parseInt(current)-1,product_name);
                }else if(jumpPage=="下一页"){
                    if((parseInt(current)+1) < pages){
					  $("#example1_next").removeClass("disabled");
                    }else{
                      $("#example1_next").addClass("disabled");
					  if((parseInt(current)+1) > pages){
					  	return;
					  }
                    }
					searchProductInfoList((parseInt(current)+1),product_name);
                }else {
					if((parseInt(jumpPage)+1) < pages){
					  $("#example1_next").removeClass("disabled");
                    }else{
                      $("#example1_next").addClass("disabled");
                    }
					if((parseInt(jumpPage)-1) > 1){
                      $("#example1_previous").removeClass("disabled");
                    }else{
                      $("#example1_previous").addClass("disabled");
                    }
                    //获取要跳转到的页码
                    searchProductInfoList(jumpPage,product_name);
                }
            });
      }
      //触发搜索按钮
      function searchProductInfoList(current){
          var productName = $("#search_store").val();
          selectProductInfoList(current,productName);
      }
	var updateTableStyle = function(result,current){
		var example1 = $("#example1");
		var total_quantity = result.lst_data.lst_data_quantity;
		$.each(eval(result.lst_data.data), function (idx, val) {
	 	    var index = (current-1)*autoPageRecordes+(idx+1);
	 	    var tr;
	 	    var hot = false;
	 	    $.each(eval(total_quantity), function (idx2, val2) {
	 	    	if((val2['product_gmv'] == val['product_gmv'])&&val2['product_name']==val['product_name']){
	 	    		hot = true;
	 	    	}
	 	    });
	 	    if(index==1){
	 	    	$("#product_table tr:first").addClass("tr_bg1");
				$("#product_table tr:first td:first").empty();
				$("#product_table tr:first td:first").html('<span class="text_big1"></span>');
				$("#product_table tr:first td").eq(1).addClass('text-yellow');
				$("#product_table tr:first td").eq(1).attr("title",val['product_name']);
				if(hot==true){
				$("#product_table tr:first td").eq(1).html(val['product_name']+'<em><img src="dist/img/hot-r.png"></em>');
				}else{
					$("#product_table tr:first td").eq(1).html(val['product_name']);
				}
				$("#product_table tr:first td").eq(2).val(val['store_name']);
				$("#product_table tr:first td").eq(2).attr("title",val['store_name']);
				$("#product_table tr:first td").eq(3).val(val['product_gmv']);
				$("#product_table tr:first td").eq(4).empty();
				if(val['rank']>=0){
					$("#product_table tr:first td").eq(4).addClass("text_up");
					$("#product_table tr:first td").eq(4).html('<i class="fa fa-long-arrow-up">'+val['rank']+'</i>');
				}else{
					$("#product_table tr:first td").eq(4).addClass("text_down");
					$("#product_table tr:first td").eq(4).html('<i class="fa fa-long-arrow-down">'+Math.abs(val['rank'])+'</i>');
				}
	 	 	}else if(index==2){
	 	 		$("#product_table tr:eq(1)").addClass("tr_bg2");
				$("#product_table tr:eq(1) td").eq(0).empty();
				$("#product_table tr:eq(1) td").eq(0).html('<span class="text_big2"></span>');
				$("#product_table tr:eq(1) td").eq(1).addClass('text-yellow');
				$("#product_table tr:eq(1) td").eq(1).attr("title",val['product_name']);
				if(hot==true){
					$("#product_table tr:eq(1) td").eq(1).html(val['product_name']+'<em><img src="dist/img/hot-r.png"></em>');
				}else{
					$("#product_table tr:eq(1) td").eq(1).html(val['product_name']);
				}
				$("#product_table tr:eq(1) td").eq(2).val(val['store_name']);
				$("#product_table tr:eq(1) td").eq(2).attr("title",val['store_name']);
				$("#product_table tr:eq(1) td").eq(3).val(val['product_gmv']);
				$("#product_table tr:eq(1) td").eq(4).empty();
				if(val['rank']>=0){
					$("#product_table tr:eq(1) td").eq(4).addClass("text_up");
					$("#product_table tr:eq(1) td").eq(4).html('<i class="fa fa-long-arrow-up">'+val['rank']+'</i>');
				}else{
					$("#product_table tr:eq(1) td").eq(4).addClass("text_down");
					$("#product_table tr:eq(1) td").eq(4).html('<i class="fa fa-long-arrow-down">'+Math.abs(val['rank'])+'</i>');
				}
	 	 	}else if(index==3){
	 	 		$("#product_table tr:eq(2)").addClass("tr_bg3");
				$("#product_table tr:eq(2) td").eq(0).empty();
				$("#product_table tr:eq(2) td").eq(0).html('<span class="text_big3"></span>');
				$("#product_table tr:eq(2) td").eq(1).addClass('text-yellow');
				$("#product_table tr:eq(2) td").eq(1).attr("title",val['product_name']);
				if(hot==true){
					$("#product_table tr:eq(2) td").eq(1).html(val['product_name']+'<em><img src="dist/img/hot-r.png"></em>');
				}else{
					$("#product_table tr:eq(2) td").eq(1).html(val['product_name']);
				}
				$("#product_table tr:eq(2) td").eq(2).val(val['store_name']);
				$("#product_table tr:eq(2) td").eq(2).attr("title",val['store_name']);
				$("#product_table tr:eq(2) td").eq(3).val(val['product_gmv']);
				$("#product_table tr:eq(2) td").eq(4).empty();
				if(val['rank']>=0){
					$("#product_table tr:eq(2) td").eq(4).addClass("text_up");
					$("#product_table tr:eq(2) td").eq(4).html('<i class="fa fa-long-arrow-up">'+val['rank']+'</i>');
				}else{
					$("#product_table tr:eq(2) td").eq(4).addClass("text_down");
					$("#product_table tr:eq(2) td").eq(4).html('<i class="fa fa-long-arrow-down">'+Math.abs(val['rank'])+'</i>');
				}
	 	 	}else{
	 	 		if(hot==true){
	 	 			$('#product_table tr:eq('+idx+') td').eq(1).html(val['product_name']+'<em><img src="dist/img/hot-r.png"></em>');
	 	 		}else{
	 	 			$('#product_table tr:eq('+idx+') td').eq(1).html(val['product_name']);
	 	 		}
	 	 		$('#product_table tr:eq('+idx+') td').eq(1).attr("title",val['product_name']);
				$('#product_table tr:eq('+idx+') td').eq(2).attr("title",val['store_name']);
	 	 		if(val['rank']>=0){
					$('#product_table tr:eq('+idx+') td').eq(4).addClass("text_up");
					$('#product_table tr:eq('+idx+') td').eq(4).html('<i class="fa fa-long-arrow-up">'+val['rank']+'</i>');
				}else{
					$('#product_table tr:eq('+idx+') td').eq(4).addClass("text_down");
					$('#product_table tr:eq('+idx+') td').eq(4).html('<i class="fa fa-long-arrow-down">'+Math.abs(val['rank'])+'</i>');
				}
	 	 	}
	 	 });
	 }