/**
 * Created with PyCharm.
 * User: Administrator
 * Date: 14-9-11
 * Time: 上午11:18
 * To change this template use File | Settings | File Templates.
 */
/* 共用 js 模块 *

/* 时间插件 使用 */

          
      
(function($){
    $.setStartTime = function(){
        $('#startDate').datepicker({
            dateFormat: "yy/mm/dd",
            maxDate: "+d",
            onClose : function(dateText, inst) {
                $( "#endDate" ).datepicker( "show" );
            },
			onSelect:function(dateText, inst) {
                $( "#endDate" ).datepicker( "option","minDate",dateText );
            },
        });
        $('#startDateFirst').datepicker({
            dateFormat: "yy/mm/dd",
            maxDate: "+d",
            onClose : function(dateText, inst) {
                $( "#endDateFirst" ).datepicker( "show" );
            },
			onSelect:function(dateText, inst) {
                $( "#endDateFirst" ).datepicker( "option","minDate",dateText );
            },
        });
        $('#startDateLast').datepicker({
            dateFormat: "yy/mm/dd",
            maxDate: "+d",
            onClose : function(dateText, inst) {
                $( "#endDateLast" ).datepicker( "show" );
            },
			onSelect:function(dateText, inst) {
                $( "#endDateLast" ).datepicker( "option","minDate",dateText );
            },
        });
    };
    $.setEndTime = function(){
        $("#endDate").datepicker({
            dateFormat: "yy/mm/dd",
            maxDate: "+d",
			defaultDate : new Date(),
            onClose : function(dateText, inst) {
                if (dateText < $("input[name=startDate]").val()){
                  $( "#endDate" ).datepicker( "show" );
				    alert("结束日期不能小于开始日期！");
					//$("#endDate").val(newdate)
                }
            }
        });
        $("#endDateFirst").datepicker({
            dateFormat: "yy/mm/dd",
            maxDate: "+d",
			defaultDate : new Date(),
            onClose : function(dateText, inst) {
                if (dateText < $("input[name=startDateFirst]").val()){
                  $( "#endDateFirst" ).datepicker( "show" );
				    alert("结束日期不能小于开始日期！");
					//$("#endDate").val(newdate)
                }
            }
        });
        $("#endDateLast").datepicker({
            dateFormat: "yy/mm/dd",
            maxDate: "+d",
			defaultDate : new Date(),
            onClose : function(dateText, inst) {
                if (dateText < $("input[name=startDateLast]").val()){
                  $( "#endDateLast" ).datepicker( "show" );
				    alert("结束日期不能小于开始日期！");
					//$("#endDate").val(newdate)
                }
            }
        });
    };
    $.date = function(){
        $('.date').datepicker(
            $.extend({showMonthAfterYear:true}, $.datepicker.regional['zh-CN'],
                {'showAnim':'','dateFormat':'yy/mm/dd','changeMonth':'true','changeYear':'true',
                    'showButtonPanel':'true'}
            ));
    };
    $.datepickerjQ = function(){
       $("#time_endbg").on("click",function(){
           $("#ui-datepicker-css").slideDown(500);
           $("#first-ui-datepicker-css").slideUp(500);
           $("#last-ui-datepicker-css").slideUp(500);
        });
        $(".ui-kydtype li").on("click",function(){
            $(".ui-kydtype li").removeClass("on").filter($(this)).addClass("on");
        });
        $("#ui-datepicker-quick input").on("click",function(){
            var thisAlt = $(this).attr("alt");
            var dateList = timeConfig(thisAlt);
            $("#regist_date").val(dateList);
            $("#ui-datepicker-css").slideUp(500);
			$("#ui-datepicker-div").css("display","none")
        });
        $("#ui-close-date").on("click",function(){
            $("#ui-datepicker-css").slideUp(500);
			$("#ui-datepicker-div").css("display","none")
        });
        $("#startDate").on("click",function(){
            $("#endDate").attr("disabled",false);
        });
        
        $("#first_time_endbg").on("click",function(){
            $("#first-ui-datepicker-css").slideDown(500);
            $("#ui-datepicker-css").slideUp(500);
            $("#last-ui-datepicker-css").slideUp(500);
         });
        $("#first-ui-datepicker-quick input").on("click",function(){
            var thisAlt = $(this).attr("alt");
            var dateList = timeConfig(thisAlt);
            $("#first_date").val(dateList);
            $("#first-ui-datepicker-css").slideUp(500);
			$("#ui-datepicker-div").css("display","none")
        });
        $("#first-ui-close-date").on("click",function(){
            $("#first-ui-datepicker-css").slideUp(500);
			$("#ui-datepicker-div").css("display","none")
        });
		 $("#startDateFirst").on("click",function(){
            $("#endDateFirst").attr("disabled",false);
        });
		 
		$("#last_time_endbg").on("click",function(){
           $("#last-ui-datepicker-css").slideDown(500);
           $("#ui-datepicker-css").slideUp(500);
           $("#first-ui-datepicker-css").slideUp(500);
        });
        $("#last-ui-datepicker-quick input").on("click",function(){
            var thisAlt = $(this).attr("alt");
            var dateList = timeConfig(thisAlt);
            $("#last_date").val(dateList);
            $("#last-ui-datepicker-css").slideUp(500);
			$("#ui-datepicker-div").css("display","none")
        });
        $("#last-ui-close-date").on("click",function(){
            $("#last-ui-datepicker-css").slideUp(500);
			$("#ui-datepicker-div").css("display","none")
        });
		 $("#startDateLast").on("click",function(){
            $("#endDateLast").attr("disabled",false);
        });
	
    }
	
})(jQuery);

$(function(){
    $.setStartTime();
    $.setEndTime();
    $.datepickerjQ();

    var nowDate = new Date();
    var month = nowDate.getMonth()+1;
    var date = nowDate.getDate();
    timeStr = nowDate.getFullYear() + '/' + (month < 10 ? '0' + month : '' + month) + '/' + (date < 10 ? '0' + date : '' + date);
    var startDateStr = "2015/12/01";
    $("#regist_date").attr("value",startDateStr +'-'+ timeStr)
    $("#startDate").attr("value",startDateStr)
    $("#endDate").attr("value",timeStr)
    
    $("#first_date").attr("value",startDateStr +'-'+ timeStr)
    $("#startDateFirst").attr("value",startDateStr)
    $("#endDateFirst").attr("value",timeStr)
    
    $("#last_date").attr("value",startDateStr +'-'+ timeStr)
    $("#startDateLast").attr("value",startDateStr)
    $("#endDateLast").attr("value",timeStr)
});


function timeConfig(time){
    //快捷菜单的控制
    var nowDate = new Date();
    var month = nowDate.getMonth()+1;
    var date = nowDate.getDate();
    timeStr = '-' + nowDate.getFullYear() + '/' + (month < 10 ? '0' + month : '' + month) + '/' + (date < 10 ? '0' + date : '' + date);
    nowDate.setDate(nowDate.getDate()+parseInt(time));
    var end_month = nowDate.getMonth()+1;
    var end_date = nowDate.getDate();
    var endDateStr = nowDate.getFullYear() + '/'+  (end_month < 10 ? '0' + end_month : '' + end_month) + '/' + (end_date < 10 ? '0' + end_date : '' + end_date);
    if(time == -1){
        endDateStr += '-' + endDateStr;
    }else{
        endDateStr += timeStr;
    }
    return endDateStr;
}

function datePickers(){
    //自定义菜单
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    var dateList = startDate +'-'+ endDate;
    $("#regist_date").val(dateList);
    $('#data_choice').html("自定义时期");
}
function datePickersFirst(){
    //自定义菜单
    var startDate = $("#startDateFirst").val();
    var endDate = $("#endDateFirst").val();
    var dateList = startDate +'-'+ endDate;
    $("#first_date").val(dateList);
    $('#first_data_choice').html("自定义时期");
}
function datePickersLast(){
    //自定义菜单
    var startDate = $("#startDateLast").val();
    var endDate = $("#endDateLast").val();
    var dateList = startDate +'-'+ endDate;
    $("#last_date").val(dateList);
    $('#last_data_choice').html("自定义时期");
}
$(function(){
    $("#Confirm").on("click",function(){
        $(this).parents("#ui-datepicker-css").slideUp(500);
    });
    $("#Cancel").on("click",function(){
        $(this).parents("#ui-datepicker-css").slideUp(500);
    });
    
    $("#ConfirmFirst").on("click",function(){
        $(this).parents("#first-ui-datepicker-css").slideUp(500);
    });
    $("#CancelFirst").on("click",function(){
        $(this).parents("#first-ui-datepicker-css").slideUp(500);
    });
    
    $("#ConfirmLast").on("click",function(){
        $(this).parents("#last-ui-datepicker-css").slideUp(500);
    });
    $("#CancelLast").on("click",function(){
        $(this).parents("#last-ui-datepicker-css").slideUp(500);
    });
});