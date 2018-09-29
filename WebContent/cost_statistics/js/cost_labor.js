/**
 * 弹出提示
 */
function showTip(t){
    var id = $(t).attr("id")
    layer.tips('员工薪酬+工服摊销+住宿星店房租',"#"+id, {
        tips: [1, '#0ACC4C'],
        time: 1000
    });
}


var regex_zb = new RegExp("^(ZB|zb)\w*");//总部角色
var regex_cs = new RegExp("^(CS|cs)\w*");//城市级别
/**
 * 查询人工成本城市
 * @type {null}
 */
var lst_select_labor_city=null;
var laborCityNameArray=new Array();
var laborCityIdMap = {};
function getLaborCity(t){

    laborCityIdMap=new Array();
    $("#labor_city").empty();

    $("#labor_store").empty();
    $("#store_id_labor").val("");
    $("#store_name_labor").val("");

    var str_name = $(t).val();
    if(!(event.keyCode >= 37 && event.keyCode <= 40) && event.keyCode != 13){
        $("#city_id_labor").val("");

        if(str_name == null || str_name == "" || str_name.indexOf('\'') > -1){
            return;
        }




        if(regex_zb.test(userGroupCode)){

            doManager('dynamicManager','selectAllCity',null,function(data){
                if(data.result){

                    lst_select_labor_city = JSON.parse(data.data);

                    for(i=0;i<lst_select_labor_city.length;i++){
                        laborCityNameArray.push(lst_select_labor_city[i].cityname);
                        laborCityIdMap[lst_select_labor_city[i].cityname] = lst_select_labor_city[i].id;
                    }
                    var autoComplete = new AutoComplete("city_name_labor","labor_city",laborCityNameArray);
                    autoComplete.start(event);
                    $("#labor_city").attr("style","width: 150px;z-index: 99999;left: 5.7%;top: 23.4%;");
                }
            },false);


        }else if(regex_cs.test(userGroupCode)||userGroupCode=="GLY"){

            doManager("StoreManager", "getCityNameOfCSZJ",[userId,null],
                function(data, textStatus, XMLHttpRequest) {
                    if (data.result) {
                        lst_select_labor_city = JSON.parse(data.data).citylist;

                        for(i=0;i<lst_select_labor_city.length;i++){
                            laborCityNameArray.push(lst_select_labor_city[i].name);
                            laborCityIdMap[lst_select_labor_city[i].name] = lst_select_labor_city[i].ctid;
                        }
                        var autoComplete = new AutoComplete("city_name_labor","labor_city",laborCityNameArray);
                        autoComplete.start(event);
                        $("#labor_city").attr("style","width: 150px;z-index: 99999;left: 5.7%;top: 23.4%;");
                    }
                },false);
        }

    }

}

/**
 * 选择城市
 * @param t
 */
function selectLaborCity(t){
    var temp_city = document.getElementById("city_name_labor").value.replace(/(\s*)|(\s*)/g,'').replace(/[ ]/g,'');
    $("#city_id_labor").val(laborCityIdMap[temp_city]);
}



/**-----------------------------------搜索门店------------------------------------------------------**/

/**
 * 查询门店
 * @type {null}
 */
var lst_select_labor_store=null;
var laborStoreNameArray=new Array();
var laborStoreIdMap = {};
function getLaborStore(t){

    laborStoreNameArray=new Array();
    $("#labor_store").empty();
    var str_name = $(t).val();
    if(!(event.keyCode >= 37 && event.keyCode <= 40) && event.keyCode != 13){
        $("#store_id_labor").val("");

        if(str_name == null || str_name == "" || str_name.indexOf('\'') > -1){
            return;
        }
        var city_id  = $("#city_id_labor").val()==""?null:$("#city_id_labor").val();
        var city_name =  $("#city_name_labor").val();
        if(city_id==null&&city_name!=""){
            city_id=-10000;
        }
        var target=0;
        if(regex_zb.test(userGroupCode)){//总部
            target=0;
        }else if(regex_cs.test(userGroupCode)||userGroupCode=="GLY"){//城市
            target=1;
        }

        doManager('dynamicManager','getStoreByCity',[target,userId,city_id,str_name],function(data){
            if(data.result){

                lst_select_labor_store = JSON.parse(data.data).storelist;

                for(var i=0;i<lst_select_labor_store.length;i++){
                    laborStoreNameArray.push(lst_select_labor_store[i].name);
                    laborStoreIdMap[lst_select_labor_store[i].name] = lst_select_labor_store[i].storeno;
                }
                var autoComplete = new AutoComplete("store_name_labor","labor_store",laborStoreNameArray);
                autoComplete.start(event);
                $("#labor_store").attr("style","width: 150px;z-index: 99999;left: 20.4%;top: 23.4%;");
            }else{

            }
        });
    }

}

/**
 * 选择门店
 * @param t
 */
function selectLaborStore(t){
    var temp_store = document.getElementById("store_name_labor").value.replace(/(\s*)|(\s*)/g,'').replace(/[ ]/g,'');
    $("#store_id_labor").val(laborStoreIdMap[temp_store]);
}


/**
 * 工服年费
 * @param t
 */
function checkUniformCharge(t){

    checkFloatDataValid(t);
    $(t).parent().parent().attr("editable",true);
    var id = $(t).attr("id").split("_");
    var uniformCharge = $("#uniformCharge_"+id[1]).val();
    var uniformAmortize = $("#uniformAmortize_"+id[1]).val(parseFloat(uniformCharge/12).toFixed(2));
    checkCostLabor(t);
}

/**
 * 检测人工成本数据并记录编辑的门店
 *
 * **/
var editStore = new Set();
function checkCostLabor(t){
        checkFloatDataValid(t);
        $(t).parent().parent().attr("editable",true);
        var id=$(t).attr("id");
        var rowNum = id.split("_")[1];

        var emolument = $("#emolument_"+rowNum).val();
        var uniformAmortize = $("#uniformAmortize_"+rowNum).val();
        var accommodation = $("#accommodation_"+rowNum).val();
        var subtotal="";
        if(emolument==""&&uniformAmortize==""&&accommodation==""){
            $("#subtotal_"+rowNum).val("");
        }else{
             emolument = $("#emolument_"+rowNum).val()==""?0:$("#emolument_"+rowNum).val();
             uniformAmortize = $("#uniformAmortize_"+rowNum).val()==""?0:$("#uniformAmortize_"+rowNum).val();
             accommodation = $("#accommodation_"+rowNum).val()==""?0:$("#accommodation_"+rowNum).val();
            $("#subtotal_"+rowNum).val(parseFloat(emolument)+parseFloat(uniformAmortize)+parseFloat(accommodation));
        }

}



/**
 * 获取人工成本
 * **/
function getCostLabor(){


    var store_cost_tr = $("#labor_tr_2").nextAll("tr[editable='true']");
    if(store_cost_tr.length>0){//有数据修改
        $$.showConfirm_cost("提示","是否需要保存改变的数据？",function () {
            saveCostLabor();

        },function(){

            $("#labor_tr_2").nextAll("tr[editable='true']").each(function(){
                $(this).attr("editable","false");
            });
            searchLabor();
        });

    }else if(store_cost_tr.length==0){//没有数据修改
        searchLabor();
    }

}

/**
 * 查询人工成本
 */
function searchLabor(){

    var index = layer.load(0,{
        shade: [0.2,'#333']
    });

    var storeName=$("#storeName_labor").val()==""?null:$("#storeName_labor").val();
    var showYear = $("#year_labor").val().split("-")[0];
    var showMonth=$("#year_labor").val().split("-")[1];
    var showDate = showYear+"年"+parseInt(showMonth)+"月";

    $("#labor_year_month").val($("#year_labor").val());
    $("#emolument_title").html(showDate);
    $("#uniformAmortize_title").html(showDate);
    $("#accommodation_title").html(showDate);

    $("#labor_tb_2").find("tr:gt(0)").remove();

    var cityId = $("#city_id_labor").val();
    var cityName = $("#city_name_labor").val();
    if(cityId==""&&cityName!=""){
        cityId="-10000";
    }

    var storeId = $("#store_id_labor").val();
    var storeName = $("#store_name_labor").val();

    if(storeId==""&&storeName!=""){
        storeId=-10000;
    }

    var role="zb"
    if(regex_zb.test(userGroupCode)){
        role=="zb";
    }else if(regex_cs.test(userGroupCode)||userGroupCode=="GLY"){
        role="cs";
    }
    var costDto= {
        cityId:cityId,
        storeNo:storeId,
        year:showYear,
        month:showMonth,
        userId:userId,
        role:role
    }
    doManager('costLaborManager','queryCostLabor',costDto,function (data) {


        if(data.result){
            var costLabor = JSON.parse(data.data).labor;


            var labor_td_sum = "";
            var uniform_charge_sum=0;

            for(var i=0;i<costLabor.length;i++){

                var emolument =  costLabor[i]["emolument"]==null?"":costLabor[i]["emolument"];
                var uniform_amortize = costLabor[i]["uniform_amortize"]==null?"":costLabor[i]["uniform_amortize"];
                var accommodation = costLabor[i]["accommodation"]==null?"":costLabor[i]["accommodation"];
                var subtotal="";
                if(emolument==""&&uniform_amortize==""&&accommodation==""){
                    subtotal = "";
                }else{
                    emolument = emolument==""?0:parseFloat(emolument);
                    uniform_amortize=uniform_amortize == ""?0:parseFloat(uniform_amortize);
                    accommodation=accommodation == ""?0:parseFloat(accommodation);
                    subtotal= (emolument+uniform_amortize+accommodation).toFixed(2);
                }
                var rc_tag ="_"+i;
                var cityName = costLabor[i].city_name;
                var storeNo=costLabor[i].storeNo;
                var storeName = costLabor[i].storeName;
                var labor_td="<td style='text-align: center;background-color:#A9A9A9'>"+(i+1)+"</td><td style='text-align: center;background-color:#A9A9A9'>"+costLabor[i].storeNo+"</td><td style='background-color:#A9A9A9'>"+costLabor[i].storeName+"</td>"+
                    "<td><input type='text' style='width: 100%;background-color: #e8e8e8;' readonly onkeyup='checkCostLabor(this)' id='uniformAmortize"+rc_tag+"' value='"+uniform_amortize+"'/></td><td><input type='text' style='width: 100%;' onkeyup='checkCostLabor(this)' id='emolument"+rc_tag+"' value='"+emolument+"'/></td><td><input type='text' style='width: 100%;' onkeyup='checkCostLabor(this)' id='accommodation"+rc_tag+"' value='"+accommodation+"'></td><td><input readonly type='text'  onkeyup='showTip(this)' style='background-color: #e8e8e8;width: 100%;' id='subtotal"+rc_tag+"' value='"+subtotal+"'></td>";

                $("#labor_tb_2").append("<tr id='"+storeNo+"' editable='false'>"+labor_td+"<input type='hidden' value='"+storeName+"' id='storeName'/><input type='hidden' value='"+cityName+"' id='cityName'/></tr>");
            }

            layer.close(index);

        }
    });
}
/**
 * 导出人工成本
 *
 * **/
function   exportCostLabor(){


    var showYear = $("#year_labor").val().split("-")[0];
    var showMonth=$("#year_labor").val().split("-")[1];

    var cityId = $("#city_id_labor").val();
    var cityName = $("#city_name_labor").val();
    if(cityId==""&&cityName!=""){
        cityId="-10000";
    }

    var storeId = $("#store_id_labor").val();
    var storeName = $("#store_name_labor").val();

    if(storeId==""&&storeName!=""){
        storeId=-10000;
    }

    var role="zb"
    if(regex_zb.test(userGroupCode)){
        role=="zb";
    }else if(regex_cs.test(userGroupCode)||userGroupCode=="GLY"){
        role="cs";
    }
    var costDto= {
        cityId:cityId,
        storeNo:storeId,
        year:showYear,
        month:showMonth,
        userId:userId,
        role:role
    }



    doManager('costLaborManager','exportCostLabor',costDto,function (data) {
        if(data.result){
            var result= JSON.parse(data.data);
            if(result.status=='success'){
                 window.location.href=result.data;
            }else if(result.status=="null"){

                $$.showMessage('提示',"请先确认是否有符合条件的数据，重新请求！");
                return;
            }else{

                $$.showMessage('提示',"请稍后重新请求！");
                return;
            }

        }else{
            $$.showMessage('提示',"请稍后重新请求！");
        }

    },false);
}

/**
 *
 * 保存人工成本
 *
 * **/
function saveCostLabor(){
       var year_month = $("#labor_year_month").val();

       var store_cost_tr = $("#labor_tr_2").nextAll("tr[editable='true']");
       var costLaborArray = [];
       for(var i=0;i<store_cost_tr.length;i++){
           var cityName = $(store_cost_tr[i]).find("input[id='cityName']").val();
           var storeNo= $(store_cost_tr[i]).attr("id");
           var storeName = $(store_cost_tr[i]).find("input[id='storeName']").val();
           var emolument = $(store_cost_tr[i]).find('input[id^="emolument_"]').val();
           var uniformAmortize = $(store_cost_tr[i]).find('input[id^="uniformAmortize_"]').val();
           var accommodation = $(store_cost_tr[i]).find('input[id^="accommodation_"]').val();
           var subtotal = $(store_cost_tr[i]).find('input[id^="subtotal_"]').val();
           var costLabor = {
               cityName:cityName,
               storeNo:storeNo,
               storeName:storeName,
               year:year_month.split("-")[0],
               month:parseInt(year_month.split("-")[1]),
               emolument:emolument,
               uniformAmortize:uniformAmortize,
               accommodation:accommodation,
               subtotal:subtotal
           };

               costLaborArray.push(costLabor);

       }

        doManager('costLaborManager','saveCostLabor',[costLaborArray],function (data) {
            if(data.result){
                var result= JSON.parse(data.data);

                if(result.status=='success'){
                    $$.showMessage('提示',"保存成功！");
                    $("#labor_tr_2").nextAll("tr[editable='true']").each(function(){
                        $(this).attr("editable","false");
                    })
                    return;
                }else if(result.status=="fail"){

                    $$.showMessage('提示',"保存失败！");
                    return;
                }else{

                    $$.showMessage('提示',"请稍后重新请求！");
                    return;
                }
            }else{
                $.showMessage('提示',"请稍后重新请求！");
            }

        },false);

}

/**
 * 导出员工薪酬
 */
function exportCostEmolument(){



    var showYear = $("#year_labor").val().split("-")[0];
    var showMonth=$("#year_labor").val().split("-")[1];

    var cityId = $("#city_id_labor").val();
    var cityName = $("#city_name_labor").val();
    if(cityId==""&&cityName!=""){
        cityId="-10000";
    }

    var storeId = $("#store_id_labor").val();
    var storeName = $("#store_name_labor").val();

    if(storeId==""&&storeName!=""){
        storeId=-10000;
    }

    var role="zb"
    if(regex_zb.test(userGroupCode)){
        role=="zb";
    }else if(regex_cs.test(userGroupCode)||userGroupCode=="GLY"){
        role="cs";
    }
    var costDto= {
        cityId:cityId,
        storeNo:storeId,
        year:showYear,
        month:showMonth,
        userId:userId,
        role:role
    }



    doManager('costLaborManager','exportCostEmolument',costDto,function (data) {
        if(data.result){
            var result= JSON.parse(data.data);
            if(result.status=='success'){
                window.location.href=result.data;
            }else if(result.status=="null"){

                $$.showMessage('提示',"请先确认是否有符合条件的数据，重新请求！");
                return;
            }else{

                $$.showMessage('提示',"请稍后重新请求！");
                return;
            }

        }else{
            $$.showMessage('提示',"请稍后重新请求！");
        }

    },false);
}