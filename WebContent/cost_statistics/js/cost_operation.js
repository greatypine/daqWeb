

var regex_zb = new RegExp("^(ZB|zb)\w*");//总部角色
var regex_cs = new RegExp("^(CS|cs)\w*");//城市级别
/**
 * 查询人工成本城市
 * @type {null}
 */
var lst_select_operation_city=null;
var operationCityNameArray=new Array();
var operationCityIdMap = {};
function getOperationCity(t){

    operationCityIdMap=new Array();
    $("#operation_city").empty();

    $("#operation_store").empty();
    $("#store_id_operation").val("");
    $("#store_name_operation").val("");

    var str_name = $(t).val();
    if(!(event.keyCode >= 37 && event.keyCode <= 40) && event.keyCode != 13){
        $("#city_id_operation").val("");

        if(str_name == null || str_name == "" || str_name.indexOf('\'') > -1){
            return;
        }

        if(regex_zb.test(userGroupCode)){

            doManager('dynamicManager','selectAllCity',null,function(data){
                if(data.result){

                    lst_select_operation_city = JSON.parse(data.data);

                    for(i=0;i<lst_select_operation_city.length;i++){
                        operationCityNameArray.push(lst_select_operation_city[i].cityname);
                        operationCityIdMap[lst_select_operation_city[i].cityname] = lst_select_operation_city[i].id;
                    }
                    var autoComplete = new AutoComplete("city_name_operation","operation_city",operationCityNameArray);
                    autoComplete.start(event);
                    $("#operation_city").attr("style","width: 150px;z-index: 99999;left: 6.6%;top: 18.4%;");
                }
            },false);


        }else if(regex_cs.test(userGroupCode)||userGroupCode=="GLY"){

            doManager("StoreManager", "getCityNameOfCSZJ",[userId,null],
                function(data, textStatus, XMLHttpRequest) {
                    if (data.result) {
                        lst_select_operation_city = JSON.parse(data.data).citylist;

                        for(i=0;i<lst_select_operation_city.length;i++){
                            operationCityNameArray.push(lst_select_operation_city[i].name);
                            operationCityIdMap[lst_select_operation_city[i].name] = lst_select_operation_city[i].ctid;
                        }
                        var autoComplete = new AutoComplete("city_name_operation","operation_city",operationCityNameArray);
                        autoComplete.start(event);
                        $("#operation_city").attr("style","width: 150px;z-index: 99999;left: 6.6%;top: 18.4%;");
                    }
                },false);
        }

    }

}

/**
 * 选择城市
 * @param t
 */
function selectOperationCity(t){
    var temp_city = document.getElementById("city_name_operation").value.replace(/(\s*)|(\s*)/g,'').replace(/[ ]/g,'');
    $("#city_id_operation").val(operationCityIdMap[temp_city]);
}



/**-----------------------------------搜索门店------------------------------------------------------**/

/**
 * 查询门店
 * @type {null}
 */
var lst_select_operation_store=null;
var operationStoreNameArray=new Array();
var operationStoreIdMap = {};
function getOperationStore(t){

    operationStoreNameArray=new Array();
    $("#operation_store").empty();
    var str_name = $(t).val();
    if(!(event.keyCode >= 37 && event.keyCode <= 40) && event.keyCode != 13){
        $("#store_id_operation").val("");

        if(str_name == null || str_name == "" || str_name.indexOf('\'') > -1){
            return;
        }
        var city_id  = $("#city_id_operation").val()==""?null:$("#city_id_operation").val();
        var city_name =  $("#city_name_operation").val();
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

                lst_select_operation_store = JSON.parse(data.data).storelist;

                for(var i=0;i<lst_select_operation_store.length;i++){
                    operationStoreNameArray.push(lst_select_operation_store[i].name);
                    operationStoreIdMap[lst_select_operation_store[i].name] = lst_select_operation_store[i].storeno;
                }
                var autoComplete = new AutoComplete("store_name_operation","operation_store",operationStoreNameArray);
                autoComplete.start(event);
                $("#operation_city").attr("style","width: 150px;z-index: 99999;left: 23%;top: 18.4%;");
            }else{

            }
        });
    }

}

/**
 * 选择门店
 * @param t
 */
function selectOperationStore(t){
    var temp_store = document.getElementById("store_name_operation").value.replace(/(\s*)|(\s*)/g,'').replace(/[ ]/g,'');
    $("#store_id_operation").val(operationStoreIdMap[temp_store]);
}





/**
 * 计算运营费用累计
 * @param t
 */
function calculateYearCharge(t){
    checkFloatDataValid(t);
    $(t).parent().parent().attr("editable",true);
    var id = $(t).attr("id").split("_");


    var dailyOffice = $("#dailyOffice_"+id[1]).val();//日常办公
    var rent = $("#rent_"+id[1]).val();//仓储型星店房租
    var barrelledWater = $("#barrelledWater_"+id[1]).val();//桶装水
    var storeInsurance = $("#storeInsurance_"+id[1]).val();//门店保险
    var carMaintain = $("#carMaintain_"+id[1]).val();//电动车维修
    var shoppingBag = $("#shoppingBag_"+id[1]).val();//购物袋
    var garbageBag = $("#garbageBag_"+id[1]).val();//垃圾袋
    var extinguisher = $("#extinguisher_"+id[1]).val();//灭火器
    var backpack = $("#backpack_"+id[1]).val();//背包、车载包
    var helmet = $("#helmet_"+id[1]).val();//头盔、护膝、手套
    var greenPlants = $("#greenPlants_"+id[1]).val();//绿植
    var tray = $("#tray_"+id[1]).val();//托盘
    var storageMaterials = $("#storageMaterials_"+id[1]).val();//仓储物资
    var activityFee = $("#activityFee_"+id[1]).val();//活动费
    var decorationMaintain = $("#decorationMaintain_"+id[1]).val();//门店装修及维修费
    // var yearCharge = $("#yearCharge_"+id[1]).val();//年运营费
    // var monthCharge = $("#monthCharge_"+id[1]).val();//月运营费

    if(dailyOffice==""&&rent==""&&barrelledWater==""&&storeInsurance==""&&carMaintain==""&&shoppingBag==""&&garbageBag==""&&extinguisher==""&&backpack==""&&helmet==""&&greenPlants==""&&tray==""&&storageMaterials==""&&activityFee==""&&decorationMaintain==""){
        $("#yearCharge_"+id[1]).val("");
        $("#monthCharge_"+id[1]).val("");
    }else{

        dailyOffice = dailyOffice==""?0:parseFloat(dailyOffice);
        rent = rent==""?0:parseFloat(rent);
        barrelledWater = barrelledWater==""?0:parseFloat(barrelledWater);
        storeInsurance = storeInsurance==""?0:parseFloat(storeInsurance);
        carMaintain = carMaintain==""?0:parseFloat(carMaintain);
        shoppingBag = shoppingBag==""?0:parseFloat(shoppingBag);
        garbageBag = garbageBag==""?0:parseFloat(garbageBag);
        extinguisher = extinguisher==""?0:parseFloat(extinguisher);
        backpack = backpack==""?0:parseFloat(backpack);
        helmet = helmet==""?0:parseFloat(helmet);
        greenPlants = greenPlants==""?0:parseFloat(greenPlants);
        tray = tray==""?0:parseFloat(tray);
        storageMaterials = storageMaterials==""?0:parseFloat(storageMaterials);
        activityFee = activityFee==""?0:parseFloat(activityFee);
        decorationMaintain = decorationMaintain==""?0:parseFloat(decorationMaintain);

        var total_year= dailyOffice+rent+barrelledWater+storeInsurance+carMaintain+shoppingBag+garbageBag+extinguisher+backpack+helmet+greenPlants+tray+storageMaterials+activityFee+decorationMaintain;
        $("#yearCharge_"+id[1]).val(total_year.toFixed(2));
        $("#monthCharge_"+id[1]).val((total_year/12).toFixed(2));
    }
}

/**
 * 查询运营成本
 */
function getCostOperation(){

    var store_cost_tr = $("#operation_tr_2").nextAll("tr[editable='true']");
    if(store_cost_tr.length>0){//有数据修改
        $$.showConfirm_cost("提示","是否需要保存改变的数据？",function () {
            saveCostOperation();

        },function(){

            $("#labor_tr_2").nextAll("tr[editable='true']").each(function(){
                $(this).attr("editable","false");
            });
            searchCostOperation();
        });

    }else if(store_cost_tr.length==0){//没有数据修改
        searchCostOperation();
    }
}


/**
 * 获取运营成本
 * **/
function searchCostOperation(){

    var index = layer.load(0,{
        shade: [0.2,'#333']
    });

    var year = $("#year_operation").val();
    $("#operation_save_date").val(year);
    var cityId = $("#city_id_operation").val();
    var cityName = $("#city_name_operation").val();
    if(cityId==""&&cityName!=""){
        cityId="-10000";
    }

    var storeId = $("#store_id_operation").val();
    var storeName = $("#store_name_operation").val();

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
        userId:userId,
        role:role,
        year:year
    }

    $("#operation_tb_2").find("tr:gt(0)").remove();

    doManager('costOperationManager','queryCostOperation',costDto,function (data) {


        if(data.result){
            var costOperation = JSON.parse(data.data).operation;

            for(var i=0;i<costOperation.length;i++){
                var cityName = costOperation[i].city_name==null?"":costOperation[i].city_name;
                var storeNo = costOperation[i].store_no==null?"":costOperation[i].store_no;
                var storeName = costOperation[i].store_name==null?"":costOperation[i].store_name;
                var daily_office = costOperation[i].daily_office==null?"":costOperation[i].daily_office;//日常办公
                var rent = costOperation[i].rent==null?"":costOperation[i].rent;//仓店房租
                var barrelled_water = costOperation[i].barrelled_water==null?"":costOperation[i].barrelled_water;//桶装水
                var store_insurance = costOperation[i].store_insurance==null?"":costOperation[i].store_insurance;//桶装水
                var car_maintain = costOperation[i].car_maintain==null?"":costOperation[i].car_maintain;//电动车维修
                var shopping_bag = costOperation[i].shopping_bag==null?"":costOperation[i].shopping_bag;//购物袋
                var garbage_bag = costOperation[i].garbage_bag==null?"":costOperation[i].garbage_bag;//垃圾袋
                var extinguisher = costOperation[i].extinguisher==null?"":costOperation[i].extinguisher;//灭火器
                var backpack = costOperation[i].backpack==null?"":costOperation[i].backpack;//背包
                var helmet = costOperation[i].helmet==null?"":costOperation[i].helmet;//头盔、护膝、手套
                var greenPlants = costOperation[i].greenPlants==null?"":costOperation[i].greenPlants;//绿植
                var tray = costOperation[i].tray==null?"":costOperation[i].tray;//托盘
                var storage_materials = costOperation[i].storage_materials==null?"":costOperation[i].storage_materials;//仓储物资
                var activity_fee = costOperation[i].activity_fee==null?"":costOperation[i].activity_fee;//活动费
                var decoration_maintain = costOperation[i].decoration_maintain==null?"":costOperation[i].decoration_maintain;//门店装修及维修费
                var year_charge = costOperation[i].year_charge==null?"":costOperation[i].year_charge;//运营费/年
                var month_charge = costOperation[i].month_charge==null?"":costOperation[i].month_charge;//运营费/月

                var operation_td =
                    "<td style='text-align: center;background-color:#A9A9A9'>"+(i+1)+"</td><td style='text-align: center;background-color:#A9A9A9'>"+storeNo+"</td><td style='background-color:#A9A9A9'><p>"+storeName+"</p></td>"+
                    "<td><input type='text' style='width: 100%'  onkeyup='calculateYearCharge(this)'  id='dailyOffice_"+i+"' value='"+daily_office+"'/></td>" +
                    "<td><input type='text'     style='width: 100%' onkeyup='calculateYearCharge(this)' id='rent_"+i+"'  value='"+rent+"'/></td>" +
                    "<td><input type='text'     style='width: 100%' onkeyup='calculateYearCharge(this)' id='barrelledWater_"+i+"'    value='"+barrelled_water+"'/></td>"+
                    "<td><input type='text'     style='width: 100%' onkeyup='calculateYearCharge(this)' id='storeInsurance_"+i+"'    value='"+store_insurance+"'/></td>"+
                    "<td><input type='text'     style='width: 100%' onkeyup='calculateYearCharge(this)' id='carMaintain_"+i+"'    value='"+car_maintain+"'/></td>"+
                    "<td><input type='text'     style='width: 100%' onkeyup='calculateYearCharge(this)' id='shoppingBag_"+i+"'    value='"+shopping_bag+"'/></td>"+
                    "<td><input type='text'     style='width: 100%' onkeyup='calculateYearCharge(this)' id='garbageBag_"+i+"'    value='"+garbage_bag+"'/></td>"+
                    "<td><input type='text'     style='width: 100%' onkeyup='calculateYearCharge(this)' id='extinguisher_"+i+"'    value='"+extinguisher+"'/></td>"+
                    "<td><input type='text'     style='width: 100%' onkeyup='calculateYearCharge(this)' id='backpack_"+i+"'    value='"+backpack+"'/></td>"+
                    "<td><input type='text'     style='width: 100%' onkeyup='calculateYearCharge(this)' id='helmet_"+i+"'    value='"+helmet+"'/></td>"+
                    "<td><input type='text'     style='width: 100%' onkeyup='calculateYearCharge(this)' id='greenPlants_"+i+"'    value='"+greenPlants+"'/></td>"+
                    "<td><input type='text'     style='width: 100%' onkeyup='calculateYearCharge(this)' id='tray_"+i+"'    value='"+tray+"'/></td>"+
                    "<td><input type='text'     style='width: 100%' onkeyup='calculateYearCharge(this)' id='storageMaterials_"+i+"'    value='"+storage_materials+"'/></td>"+
                    "<td><input type='text'     style='width: 100%' onkeyup='calculateYearCharge(this)' id='activityFee_"+i+"'    value='"+activity_fee+"'/></td>"+
                    "<td><input type='text'     style='width: 100%' onkeyup='calculateYearCharge(this)' id='decorationMaintain_"+i+"'    value='"+decoration_maintain+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8;width: 100%' readonly id='yearCharge_"+i+"'    value='"+year_charge+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8;width: 100%' readonly id='monthCharge_"+i+"'    value='"+month_charge+"'/></td>";

                    $("#operation_tb_2").append("<tr id='"+storeNo+"' editable='false'>"+operation_td+"<input type='hidden'  id='storeName' value='"+storeName+"'/><input type='hidden'  id='cityName' value='"+cityName+"'/></tr>");
            }

            layer.close(index);

        }
    });

}
/**
 * 导出运营成本
 *
 * **/
function   exportCostOperation(){

    var cityId = $("#city_id_operation").val();
    var cityName = $("#city_name_operation").val();
    var year = $("#year_operation").val();
    if(cityId==""&&cityName!=""){
        cityId="-10000";
    }

    var storeId = $("#store_id_operation").val();
    var storeName = $("#store_name_operation").val();

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
        userId:userId,
        role:role,
        year:year
    }
    doManager('costOperationManager','exportCostOperation',costDto,function (data) {
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
            $.showMessage('提示',"请稍后重新请求！");
        }

    },false);
}

/**
 *
 * 保存运营成本
 *
 * **/
function saveCostOperation(){

    var store_cost_tr = $("#operation_tr_2").nextAll("tr[editable='true']");
    var year = $("#operation_save_date").val();
    var costOperationArray = [];

    for(var i=0;i<store_cost_tr.length;i++){
        var cityName = $(store_cost_tr[i]).find("input[id='cityName']").val();//门店名称
        var storeNo= $(store_cost_tr[i]).attr("id");//门店编号
        var storeName = $(store_cost_tr[i]).find("input[id='storeName']").val();//门店名称
        var dailyOffice = $(store_cost_tr[i]).find("input[id^='dailyOffice_']").val();//日常办公
        var rent = $(store_cost_tr[i]).find('input[id^="rent_"]').val();//仓储型星店房租
        var barrelledWater = $(store_cost_tr[i]).find('input[id^="barrelledWater_"]').val();//桶装水
        var storeInsurance = $(store_cost_tr[i]).find('input[id^="storeInsurance_"]').val();//门店保险
        var carMaintain = $(store_cost_tr[i]).find('input[id^="carMaintain_"]').val();//电动车维修
        var shoppingBag = $(store_cost_tr[i]).find('input[id^="shoppingBag_"]').val();//购物袋
        var garbageBag = $(store_cost_tr[i]).find('input[id^="garbageBag_"]').val();//垃圾袋
        var extinguisher = $(store_cost_tr[i]).find('input[id^="extinguisher_"]').val();//灭火器
        var backpack = $(store_cost_tr[i]).find('input[id^="backpack_"]').val();//背包、车载包
        var helmet = $(store_cost_tr[i]).find('input[id^="helmet_"]').val();//头盔、护膝、手套
        var greenPlants = $(store_cost_tr[i]).find('input[id^="greenPlants_"]').val();//绿植
        var tray = $(store_cost_tr[i]).find('input[id^="tray_"]').val();//托盘
        var storageMaterials = $(store_cost_tr[i]).find('input[id^="storageMaterials_"]').val();//仓储物资
        var activityFee = $(store_cost_tr[i]).find('input[id^="activityFee_"]').val();//活动费
        var decorationMaintain = $(store_cost_tr[i]).find('input[id^="decorationMaintain_"]').val();//门店装修及维修费
        var yearCharge = $(store_cost_tr[i]).find('input[id^="yearCharge_"]').val();//年运营费
        var monthCharge = $(store_cost_tr[i]).find('input[id^="monthCharge_"]').val();//月运营费

        var costOperation = {
            cityName:cityName,
            year:year,
            storeNo:storeNo,
            storeName:storeName,
            dailyOffice:dailyOffice,
            rent:rent,
            barrelledWater:barrelledWater,
            storeInsurance:storeInsurance,
            carMaintain:carMaintain,
            shoppingBag:shoppingBag,
            garbageBag:garbageBag,
            extinguisher:extinguisher,
            backpack:backpack,
            helmet:helmet,
            greenPlants:greenPlants,
            tray:tray,
            storageMaterials:storageMaterials,
            activityFee:activityFee,
            decorationMaintain:decorationMaintain,
            yearCharge:yearCharge,
            monthCharge:monthCharge
        }
        costOperationArray.push(costOperation);

    }

    doManager('costOperationManager','saveCostOperation',[costOperationArray],function (data) {
        if(data.result){
            var result= JSON.parse(data.data);

            if(result.status=='success'){
                $$.showMessage('提示',"保存成功！");
                $("#operation_tr_2").nextAll("tr[editable='true']").each(function () {
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