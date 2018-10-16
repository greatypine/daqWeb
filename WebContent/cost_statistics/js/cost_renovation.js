

var regex_zb = new RegExp("^(ZBMDCBGL|zbmdcbgl)\w*");//成本管理总部角色
var regex_cs = new RegExp("^(CSMDCBGL|csmdcbgl)\w*");//城市级别
/**
 * 查询装修摊销城市
 * @type {null}
 */
var lst_select_renovation_city=null;

function getRenovationCity(){


        if(regex_zb.test(userGroupCode)){

            doManager('dynamicManager','selectAllCity',null,function(data){
                if(data.result){

                    lst_select_renovation_city = JSON.parse(data.data);

                    var option = "";
                    for( var i=0;i<lst_select_renovation_city.length;i++){
                        option=option+"<option value='"+lst_select_renovation_city[i].id+"'>"+lst_select_renovation_city[i].cityname+"</option>";
                    }
                    $("#city_id_renovation").append(option);
                    if(flag=="search"){
                        $("#city_id_renovation").val(cur_city_id);
                    }
                }
            });


        }else if(regex_cs.test(userGroupCode)||userGroupCode=="GLY"){

            doManager("StoreManager", "getCityNameOfCSZJ",[userId,null],
                function(data, textStatus, XMLHttpRequest) {
                    if (data.result) {
                        lst_select_renovation_city = JSON.parse(data.data).citylist;

                        var option = "";
                        for( var i=0;i<lst_select_renovation_city.length;i++){
                            option=option+"<option value='"+lst_select_renovation_city[i].ctid+"'>"+lst_select_renovation_city[i].name+"</option>";
                        }
                        $("#city_id_renovation").append(option);
                        if(flag=="search"){
                            $("#city_id_renovation").val(cur_city_id);
                        }
                    }
                });
        }



}

// /**
//  * 选择城市
//  * @param t
//  */
// function selectRenovationCity(t){
//     var temp_city = document.getElementById("city_name_renovation").value.replace(/(\s*)|(\s*)/g,'').replace(/[ ]/g,'');
//     $("#city_id_renovation").val(renovationCityIdMap[temp_city]);
// }



/**-----------------------------------搜索门店------------------------------------------------------**/

/**
 * 查询门店
 * @type {null}
 */
var lst_select_renovation_store=null;
var renovationStoreNameArray=new Array();
var renovationStoreIdMap = {};
function getRenovationStore(t){

    renovationStoreNameArray=new Array();
    $("#renovation_store").empty();
    var str_name = $(t).val();
    if(!(event.keyCode >= 37 && event.keyCode <= 40) && event.keyCode != 13){
        $("#store_id_renovation").val("");

        if(str_name == null || str_name == "" || str_name.indexOf('\'') > -1){
            return;
        }
        var city_id  = $("#city_id_renovation").val()==""?null:$("#city_id_renovation").val();

        var target=0;
        if(regex_zb.test(userGroupCode)){//总部
            target=0;
        }else if(regex_cs.test(userGroupCode)||userGroupCode=="GLY"){//城市
            target=1;
        }

        doManager('dynamicManager','getStoreByCity',[target,userId,city_id,str_name],function(data){
            if(data.result){

                lst_select_renovation_store = JSON.parse(data.data).storelist;

                for(var i=0;i<lst_select_renovation_store.length;i++){
                    renovationStoreNameArray.push(lst_select_renovation_store[i].name);
                    renovationStoreIdMap[lst_select_renovation_store[i].name] = lst_select_renovation_store[i].storeno;
                }
                var autoComplete = new AutoComplete("store_name_renovation","renovation_store",renovationStoreNameArray);
                autoComplete.start(event);
                $("#renovation_store").attr("style","width: 150px;z-index: 99999;left: 29.2%;top: 22.3%;");
            }else{

            }
        });
    }

}

/**
 * 选择门店
 * @param t
 */
function selectRenovationStore(t){
    var temp_store = document.getElementById("store_name_renovation").value.replace(/(\s*)|(\s*)/g,'').replace(/[ ]/g,'');
    $("#store_id_renovation").val(renovationStoreIdMap[temp_store]);
}




// /**
//  * 计算装修施工
//  * @param t
//  */
// function calculateDecorateCost(t){
//     checkFloatDataValid(t);
//     $(t).parent().parent().attr("editable",true);
//     var id = $(t).attr("id").split("_");
//     var structureAcreage = $("#structureAcreage_"+id[1]).val();
//     var renovationUnitPrice = $("#renovationUnitPrice_"+id[1]).val();
//     if(structureAcreage!=""&&renovationUnitPrice!=""){
//         $("#decorateCost_"+id[1]).val((parseFloat(structureAcreage)*parseFloat(renovationUnitPrice)).toFixed(2));
//         $("#lightBox_"+id[1]).val(parseFloat(structureAcreage)*123.52.toFixed(2));
//         $("#airConditioner_"+id[1]).val((parseFloat(structureAcreage)*300).toFixed(2));
//         $("#airConditionerSurcharge_"+id[1]).val((parseFloat(structureAcreage)*300*0.048).toFixed(2));
//         $("#design_"+id[1]).val((parseFloat(structureAcreage)*130).toFixed(2));
//
//
//     }else {
//         $("#decorateCost_"+id[1]).val("");
//         if(structureAcreage!=""){
//             $("#lightBox_"+id[1]).val((parseFloat(structureAcreage)*123.52).toFixed(2));
//             $("#airConditioner_"+id[1]).val((parseFloat(structureAcreage)*300).toFixed(2));
//             $("#airConditionerSurcharge_"+id[1]).val((parseFloat(structureAcreage)*300*0.048).toFixed(2));
//             $("#design_"+id[1]).val((parseFloat(structureAcreage)*130).toFixed(2));
//         }
//
//     }
//
//
//
//     calculateRenovationTotal(t);
//
// }

// /**
//  * 计算全过程管理
//  * @param t
//  */
// function calculateWholeProcessManage(t){
//     var id = $(t).attr("id").split("_");
//     var decorateCost = $("#decorateCost_"+id[1]).val();
//     var design =  $("#design_"+id[1]).val();
//
//     if(decorateCost==""&&design==""){
//         $("#wholeProcessManager_"+id[1]).val("");
//     }else{
//         decorateCost= decorateCost==""?0:parseFloat(decorateCost);
//         design=design==""?0:parseFloat(design);
//         $("#wholeProcessManager_"+id[1]).val(((decorateCost+design)*0.055).toFixed(2));
//
//     }
//
//     calculateRenovationTotal(t);//计算单店产值合计
//
// }


// /**
//  * 计算过程管理
//  * @param t
//  */
// function calculateProcessManage(t){
//
//     checkFloatDataValid(t);
//     $(t).parent().parent().attr("editable",true);
//     var id = $(t).attr("id").split("_");
//     var businessScreen = $("#businessScreen_"+id[1]).val();
//     var furniture = $("#furniture_"+id[1]).val();
//     var lightBox = $("#lightBox_"+id[1]).val();
//     if(businessScreen==""&&furniture==""&&lightBox==""){
//         $("#processManage_"+id[1]).val("");
//         $("#processManageSurcharge_"+id[1]).val("");
//     }else{
//         businessScreen=businessScreen==""?0:parseFloat(businessScreen);
//         furniture=furniture==""?0:parseFloat(furniture);
//         lightBox = lightBox==""?0:parseFloat(lightBox);
//         $("#processManage_"+id[1]).val((businessScreen+furniture+lightBox).toFixed(2));
//         $("#processManageSurcharge_"+id[1]).val(((businessScreen+furniture+lightBox)*0.035).toFixed(2));
//     }
//     calculateWholeProcessManage(t);
//     calculateRenovationTotal(t);
// }

/**
 * 计算单店装修总花费
 * @param t
 */
function calculateRenovationTotal(t){
    checkFloatDataValid(t);
    $(t).parent().parent().attr("editable",true);
    var id = $(t).attr("id").split("_");
    var wholeProcessManager = $("#wholeProcessManager_"+id[1]).val();
    var design =  $("#design_"+id[1]).val();
    var airConditioner = $("#airConditioner_"+id[1]).val();
    var lightBox = $("#lightBox_"+id[1]).val();
    var decorateCost = $("#decorateCost_"+id[1]).val();
    var businessScreen = $("#businessScreen_"+id[1]).val();
    var furniture = $("#furniture_"+id[1]).val();
    if(wholeProcessManager==""&&design==""&&airConditioner==""&&lightBox==""&&decorateCost==""&&businessScreen==""&&furniture==""){
        $("#total_"+id[1]).val("");
    }else{
        wholeProcessManager=wholeProcessManager==""?0:parseFloat(wholeProcessManager);
        design=design==""?0:parseFloat(design);

        airConditioner=airConditioner==""?0:parseFloat(airConditioner);
        lightBox=lightBox==""?0:parseFloat(lightBox);
        decorateCost=decorateCost==""?0:parseFloat(decorateCost);
        businessScreen=businessScreen==""?0:parseFloat(businessScreen);
        furniture = furniture==""?0:parseFloat(furniture);
        $("#total_"+id[1]).val((wholeProcessManager+design+airConditioner+lightBox+decorateCost+businessScreen+furniture).toFixed(2));

    }
    calcRenovationAmortizeMoney(t);

}

/**
 * 计算摊销月度成本
 * @param t
 */
function calculateRenovationAmortizeMoney(t){
    var id = $(t).attr("id").split("_");
    $(t).parent().parent().attr("editable",true);
    t.value = t.value.replace(/[^\d]/g,"");  //清除“数字”以外的字符
    var total = $("#total_"+id[1]).val();

    if(t.value!=""&&total!=""&&t.value!="0"){
        var amortizeMoney = (parseFloat(total)/parseInt(t.value)).toFixed(2);
        $("#amortizeMoney_"+id[1]).val(amortizeMoney);

    }

}

/**
 * 计算摊销月度成本-other
 * @param t
 */
function calcRenovationAmortizeMoney(t){
    var id = $(t).attr("id").split("_");
    var  amortizeMonth = $("#amortizeMonth_"+id[1]).val();
    var total = $("#total_"+id[1]).val();

    if(amortizeMonth!=""&&total!=""&&amortizeMonth!="0"){
        var amortizeMoney = (parseFloat(total)/parseInt(amortizeMonth)).toFixed(2);
        $("#amortizeMoney_"+id[1]).val(amortizeMoney);

    }

}


/**
 * 获取装修摊销
 */
function getCostRenovation(f){
    var store_cost_tr = $("#renovation_tr_2").nextAll("tr[editable='true']");
    if(store_cost_tr.length>0){//有数据修改
        $$.showConfirm_cost("提示","是否需要保存改变的数据？",function () {
            saveCostRenovation("single");

        },function(){

            $("#renovation_tr_2").nextAll("tr[editable='true']").each(function(){
                $(this).attr("editable","false");
            });
            searchCostRenovation(f);
        });

    }else if(store_cost_tr.length==0){//没有数据修改
        searchCostRenovation(f);
    }
}

function checkDecorationCompany(t){
    $(t).parent().parent().attr("editable",true);
}

/**
 * 查询装修摊销
 * **/
function searchCostRenovation(f){

    var index = layer.load(0,{
        shade: [0.2,'#333']
    });

    var cityId="";
    if(f=="auto"){
        cityId = cur_city_id;
    }else{
        cityId = $("#city_id_renovation").val();
    }

    var storeId = $("#store_id_renovation").val();
    var storeName = $("#store_name_renovation").val();

    if(storeId==""&&storeName!=""){
        storeId=-10000;
    }

    var estate = $("#storeEstate_renovation").val();
    var role="zb"
    if(regex_zb.test(userGroupCode)){
        role=="zb";
    }else if(regex_cs.test(userGroupCode)||userGroupCode=="GLY"){
        role="cs";
    }
    var costDto= {
        cityId:cityId,
        storeNo:storeId,
        userId:userId,
        estate:estate,
        role:role
    }



    $("#renovation_tb_2").find("tr:gt(0)").remove();

    doManager('costRenovationManager','queryCostRenovation',costDto,function (data) {


        if(data.result){
            var costRenovation = JSON.parse(data.data).renovation;


            var rent_td_sum = "";
            var uniform_charge_sum=0;

            for(var i=0;i<costRenovation.length;i++){
                var cityName = costRenovation[i].city_name==null?"":costRenovation[i].city_name;
                var storeNo = costRenovation[i].store_no==null?"":costRenovation[i].store_no;
                var storeName = costRenovation[i].store_name==null?"":costRenovation[i].store_name;
                var estate = costRenovation[i].estate==null?"":costRenovation[i].estate;
                // var decoration_company = costRenovation[i].decoration_company==null?"":costRenovation[i].decoration_company;//装修公司
                // var structure_acreage = costRenovation[i].structure_acreage==null?"":costRenovation[i].structure_acreage;//建筑面积
                // var renovation_unit_price = costRenovation[i].renovation_unit_price==null?"":costRenovation[i].renovation_unit_price;//装修单价
                var decorateCost = costRenovation[i].decorate_cost==null?"":costRenovation[i].decorate_cost;//装修施工
                var business_screen = costRenovation[i].business_screen==null?"":costRenovation[i].business_screen;//商业展屏
                var furniture = costRenovation[i].furniture==null?"":costRenovation[i].furniture;//家具
                var light_box = costRenovation[i].light_box==null?"":costRenovation[i].light_box;//灯箱
                // var process_manage = costRenovation[i].process_manage==null?"":costRenovation[i].process_manage;//过程管理
                // var process_manage_surcharge = costRenovation[i].process_manage_surcharge==null?"":costRenovation[i].process_manage_surcharge;//过程管理费
                var air_conditioner = costRenovation[i].air_conditioner==null?"":costRenovation[i].air_conditioner;//空调设备
                // var air_conditioner_surcharge = costRenovation[i].air_conditioner_surcharge==null?"":costRenovation[i].air_conditioner_surcharge;//空调设备费
                var design = costRenovation[i].design==null?"":costRenovation[i].design;//设计
                var whole_process_manage_surcharge = costRenovation[i].whole_process_manage_surcharge==null?"":costRenovation[i].whole_process_manage_surcharge;//全过程管理
                var total = costRenovation[i].total==null?"":costRenovation[i].total;//单店总装修花销
                var amortize_month = costRenovation[i].amortize_month==null?"":costRenovation[i].amortize_month;//摊销月
                var amortize_money = costRenovation[i].amortize_money==null?"":costRenovation[i].amortize_money;//摊销金额
                var completed_date = costRenovation[i].completed_date==null?"":costRenovation[i].completed_date;//竣工日期
                var contract_date = costRenovation[i].contract_date==null?"":costRenovation[i].contract_date;//合同日期

                var renovation_td =
                    "<td style='text-align: center;background-color:#e8e8e8'>"+(i+1)+"</td><td style='text-align: center;background-color:#e8e8e8'>"+storeNo+"</td><td style='background-color:#e8e8e8'><p>"+storeName+"</p></td><td style='text-align:center;background-color:#e8e8e8'><p>"+estate+"</p></td>"+
                    // "<td><input type='text'      onkeyup='checkDecorationCompany(this)' id='decorationCompany_"+i+"' value='"+decoration_company+"'/></td>" +
                    // "<td><input type='text'     onkeyup='calculateDecorateCost(this)' id='structureAcreage_"+i+"'  value='"+structure_acreage+"'/></td>" +
                    // "<td><input type='text'     onkeyup='calculateDecorateCost(this)' id='renovationUnitPrice_"+i+"'    value='"+renovation_unit_price+"'/></td>"+
                    "<td><input type='text'     onkeyup='calculateRenovationTotal(this)' id='decorateCost_"+i+"'    value='"+decorateCost+"'/></td>"+
                    "<td><input type='text'     onkeyup='calculateRenovationTotal(this)' id='businessScreen_"+i+"'    value='"+business_screen+"'/></td>"+
                    "<td><input type='text'     onkeyup='calculateRenovationTotal(this)' id='furniture_"+i+"'    value='"+furniture+"'/></td>"+
                    "<td><input type='text'     onkeyup='calculateRenovationTotal(this)' id='lightBox_"+i+"'    value='"+light_box+"'/></td>"+
                    // "<td><input type='text'      id='processManage_"+i+"'    value='"+process_manage+"'/></td>"+
                    // "<td><input type='text'     style='background-color: #e8e8e8' readonly id='processManageSurcharge_"+i+"'    value='"+process_manage_surcharge+"'/></td>"+
                    "<td><input type='text'     onkeyup='calculateRenovationTotal(this)' id='airConditioner_"+i+"'    value='"+air_conditioner+"'/></td>"+
                    // "<td><input type='text'     style='background-color: #e8e8e8' readonly id='airConditionerSurcharge_"+i+"'    value='"+air_conditioner_surcharge+"'/></td>"+
                    "<td><input type='text'     onkeyup='calculateRenovationTotal(this)' id='design_"+i+"'    value='"+design+"'/></td>"+
                    "<td><input type='text'     onkeyup='calculateRenovationTotal(this)' id='wholeProcessManager_"+i+"'    value='"+whole_process_manage_surcharge+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='total_"+i+"'    value='"+total+"'/></td>"+
                    "<td><input type='text'     onkeyup='calculateRenovationAmortizeMoney(this)' id='amortizeMonth_"+i+"'    value='"+amortize_month+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='amortizeMoney_"+i+"'    value='"+amortize_money+"'/></td>"+
                    "<td><input type='text'     style='cursor: pointer' id='completedDate_"+i+"' readonly onclick='initCompletedDate(this)'   value='"+completed_date+"'/></td>"+
                    "<td><input type='text'     style='cursor: pointer' id='contractDate_"+i+"'  readonly onclick='initContractDate(this)'  value='"+contract_date+"'/></td>";


                $("#renovation_tb_2").append("<tr id='"+storeNo+"' editable='false'>"+renovation_td+"<input type='hidden'  id='storeName' value='"+storeName+"'/><input type='hidden'  id='cityName' value='"+cityName+"'/></tr>");

                $( "#completedDate_"+i).datepicker({
                    dateFormat: 'yy/mm/dd',
                    changeMonth: true,
                    changeYear: true
                });

                $( "#contractDate_"+i).datepicker({
                    dateFormat: 'yy/mm/dd',
                    changeMonth: true,
                    changeYear: true
                });
            }

            layer.close(index);

        }
    });

}
/**
 * 导出装修摊销
 *
 * **/
function   exportCostRenovation(){
    var cityId = $("#city_id_renovation").val();


    var storeId = $("#store_id_renovation").val();
    var storeName = $("#store_name_renovation").val();

    if(storeId==""&&storeName!=""){
        storeId=-10000;
    }
    var estate = $("#storeEstate_renovation").val();
    var role="zb"
    if(regex_zb.test(userGroupCode)){
        role=="zb";
    }else if(regex_cs.test(userGroupCode)||userGroupCode=="GLY"){
        role="cs";
    }
    var costDto= {

        cityId:cityId,
        storeNo:storeId,
        userId:userId,
        estate:estate,
        role:role
    }
    doManager('costRenovationManager','exportCostRenovation',costDto,function (data) {
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
 * 保存装修摊销
 *
 * **/
function saveCostRenovation(ac){

    var  saveResult = "";
    var store_cost_tr = $("#renovation_tr_2").nextAll("tr[editable='true']");
    var costRenovationArray = [];
    for(var i=0;i<store_cost_tr.length;i++){
        var storeNo= $(store_cost_tr[i]).attr("id");
        var cityName = $(store_cost_tr[i]).find("input[id='cityName']").val();
        var storeName = $(store_cost_tr[i]).find("input[id='storeName']").val();
        // var decorationCompany = $(store_cost_tr[i]).find("input[id^='decorationCompany_']").val();//装修公司
        // var structureAcreage = $(store_cost_tr[i]).find('input[id^="structureAcreage_"]').val();//建筑面积
        // var renovationUnitPrice = $(store_cost_tr[i]).find('input[id^="renovationUnitPrice_"]').val();//单方造价
        var decorateCost = $(store_cost_tr[i]).find('input[id^="decorateCost_"]').val();//装修施工费
        var businessScreen = $(store_cost_tr[i]).find('input[id^="businessScreen_"]').val();//商业展屏
        var furniture = $(store_cost_tr[i]).find('input[id^="furniture_"]').val();//家具
        var lightBox = $(store_cost_tr[i]).find('input[id^="lightBox_"]').val();//灯箱
        // var processManage = $(store_cost_tr[i]).find('input[id^="processManage_"]').val();//过程管理
        // var processManageSurcharge = $(store_cost_tr[i]).find('input[id^="processManageSurcharge_"]').val();//过程管理费
        var airConditioner = $(store_cost_tr[i]).find('input[id^="airConditioner_"]').val();//空调设备
        // var airConditionerSurcharge = $(store_cost_tr[i]).find('input[id^="airConditionerSurcharge_"]').val();//空调设备费
        var design = $(store_cost_tr[i]).find('input[id^="design_"]').val();//设计
        var wholeProcessManager = $(store_cost_tr[i]).find('input[id^="wholeProcessManager_"]').val();//全过程管理
        var total = $(store_cost_tr[i]).find('input[id^="total_"]').val();//单店产值合计
        var amortizeMonth = $(store_cost_tr[i]).find('input[id^="amortizeMonth_"]').val();//摊销月份
        var amortizeMoney = $(store_cost_tr[i]).find('input[id^="amortizeMoney_"]').val();//摊销月度成本
        var completedDate = $(store_cost_tr[i]).find('input[id^="completedDate_"]').val();//竣工时间
        var contractDate = $(store_cost_tr[i]).find('input[id^="contractDate_"]').val();//合同签订日期
        var costRenovation = {
            cityName:cityName,
            storeNo:storeNo,
            storeName:storeName,
            // decorationCompany:decorationCompany,
            // structureAcreage:structureAcreage,
            // renovationUnitPrice:renovationUnitPrice,
            decorateCost:decorateCost,
            businessScreen:businessScreen,
            furniture:furniture,
            lightBox:lightBox,
            // processManage:processManage,
            // processManageSurcharge:processManageSurcharge,
            wholeProcessManageSurcharge:wholeProcessManager,
            airConditioner:airConditioner,
            // airConditionerSurcharge:airConditionerSurcharge,
            design:design,
            total:total,
            amortizeMonth:amortizeMonth,
            amortizeMoney:amortizeMoney,
            completedDate:completedDate,
            contractDate:contractDate

        }
        costRenovationArray.push(costRenovation);

    }

    doManager('costRenovationManager','saveCostRenovation',[costRenovationArray],function (data) {
        if(data.result){
            var result= JSON.parse(data.data);

            if(result.status=='success'){
                // $$.showMessage('提示',"保存成功！");
                $("#renovation_tr_2").nextAll("tr[editable='true']").each(function () {
                    $(this).attr("editable","false");
                })
                if(ac=="single"){
                    $$.showMessage('提示',"保存成功！");
                    return;
                }
                saveResult="success";
            }else if(result.status=="fail"){

                if(ac=="single"){
                    $$.showMessage('提示',"保存失败！");
                    return;
                }
                saveResult="fail";
            }else{

                if(ac=="single"){
                    $$.showMessage('提示',"保存失败！");
                    return;
                }
                saveResult="fail";
            }
        }else{
            if(ac=="single"){
                $$.showMessage('提示',"保存失败！");
                return;
            }
            saveResult="fail";
        }

    },false);

    return saveResult;

}