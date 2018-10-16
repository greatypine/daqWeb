

var regex_zb = new RegExp("^(ZBMDCBGL|zbmdcbgl)\w*");//成本管理总部角色
var regex_cs = new RegExp("^(CSMDCBGL|csmdcbgl)\w*");//城市级别
/**
 * 查询人工成本城市
 * @type {null}
 */
var lst_select_fixedAsset_city=null;

function getFixedAssetCity(){


        if(regex_zb.test(userGroupCode)){

            doManager('dynamicManager','selectAllCity',null,function(data){
                if(data.result){

                    lst_select_fixedAsset_city = JSON.parse(data.data);

                    var option = "";
                    for( var i=0;i<lst_select_fixedAsset_city.length;i++){
                        option=option+"<option value='"+lst_select_fixedAsset_city[i].id+"'>"+lst_select_fixedAsset_city[i].cityname+"</option>";
                    }
                    $("#city_id_fixedAsset").append(option);
                    if(flag=="search"){
                        $("#city_id_fixedAsset").val(cur_city_id);
                    }
                }
            });


        }else if(regex_cs.test(userGroupCode)||userGroupCode=="GLY"){

            doManager("StoreManager", "getCityNameOfCSZJ",[userId,null],
                function(data, textStatus, XMLHttpRequest) {
                    if (data.result) {
                        lst_select_fixedAsset_city = JSON.parse(data.data).citylist;

                        var option = "";
                        for( var i=0;i<lst_select_fixedAsset_city.length;i++){
                            option=option+"<option value='"+lst_select_fixedAsset_city[i].ctid+"'>"+lst_select_fixedAsset_city[i].name+"</option>";
                        }
                        $("#city_id_fixedAsset").append(option);
                        if(flag=="search"){
                            $("#city_id_fixedAsset").val(cur_city_id);
                        }
                    }
                });
        }

}

// /**
//  * 选择城市
//  * @param t
//  */
// function selectFixedAssetCity(t){
//     var temp_city = document.getElementById("city_name_fixedAsset").value.replace(/(\s*)|(\s*)/g,'').replace(/[ ]/g,'');
//     $("#city_id_fixedAsset").val(fixedAssetCityIdMap[temp_city]);
// }



/**-----------------------------------搜索门店------------------------------------------------------**/

/**
 * 查询门店
 * @type {null}
 */
var lst_select_fixedAsset_store=null;
var fixedAssetStoreNameArray=new Array();
var fixedAssetStoreIdMap = {};
function getFixedAssetStore(t){

    fixedAssetStoreNameArray=new Array();
    $("#fixedAsset_store").empty();
    var str_name = $(t).val();
    if(!(event.keyCode >= 37 && event.keyCode <= 40) && event.keyCode != 13){
        $("#store_id_fixedAsset").val("");

        if(str_name == null || str_name == "" || str_name.indexOf('\'') > -1){
            return;
        }
        var city_id  = $("#city_id_fixedAsset").val()==""?null:$("#city_id_fixedAsset").val();

        var target=0;
        if(regex_zb.test(userGroupCode)){//总部
            target=0;
        }else if(regex_cs.test(userGroupCode)||userGroupCode=="GLY"){//城市
            target=1;
        }

        doManager('dynamicManager','getStoreByCity',[target,userId,city_id,str_name],function(data){
            if(data.result){

                lst_select_fixedAsset_store = JSON.parse(data.data).storelist;

                for(var i=0;i<lst_select_fixedAsset_store.length;i++){
                    fixedAssetStoreNameArray.push(lst_select_fixedAsset_store[i].name);
                    fixedAssetStoreIdMap[lst_select_fixedAsset_store[i].name] = lst_select_fixedAsset_store[i].storeno;
                }
                var autoComplete = new AutoComplete("store_name_fixedAsset","fixedAsset_store",fixedAssetStoreNameArray);
                autoComplete.start(event);
                $("#fixedAsset_store").attr("style","width: 150px;z-index: 99999;left:29.2%;top: 22.3%;");

            }else{

            }
        });
    }

}

/**
 * 选择门店
 * @param t
 */
function selectFixedAssetStore(t){
    var temp_store = document.getElementById("store_name_fixedAsset").value.replace(/(\s*)|(\s*)/g,'').replace(/[ ]/g,'');
    $("#store_id_fixedAsset").val(fixedAssetStoreIdMap[temp_store]);
}



/**
 * 计算电子类合计
 * @param t
 */
function calculateElectronicsTotal(t){
    checkFloatDataValid(t);
    $(t).parent().parent().attr("editable",true);
    var id = $(t).attr("id").split("_");
    var aio = $("#aio_"+id[1]).val();
    var iPad = $("#iPad_"+id[1]).val();
    var cashRegister = $("#cashRegister_"+id[1]).val();
    var computer = $("#computer_"+id[1]).val();
    var scannerGun = $("#scannerGun_"+id[1]).val();
    var mobilePhone = $("#mobilePhone_"+id[1]).val();


    if(aio==""&&iPad==""&&cashRegister==""&&computer==""&&scannerGun==""&&mobilePhone==""){
        $("#electronicsTotal_"+id[1]).val("");
        $("#electronicsAmortize_"+id[1]).val("");
    }else {
        aio = aio==""?0:parseFloat(aio);
        iPad = iPad==""?0:parseFloat(iPad);
        cashRegister = cashRegister==""?0:parseFloat(cashRegister);
        computer = computer==""?0:parseFloat(computer);
        scannerGun = scannerGun==""?0:parseFloat(scannerGun);
        mobilePhone = mobilePhone==""?0:parseFloat(mobilePhone);
        var total = parseFloat(aio)+parseFloat(iPad)+parseFloat(cashRegister)+parseFloat(computer)+parseFloat(scannerGun)+parseFloat(mobilePhone);
        $("#electronicsTotal_"+id[1]).val(total);
        $("#electronicsAmortize_"+id[1]).val(parseFloat(total/36).toFixed(2));

    }
    calculateAmortizeMoney(t);
    calculateTotal(t);

}

/**
 * 计算电动车摊销
 * @param t
 */
function calculateElectricCarsAmortize(t){

    checkFloatDataValid(t);
    $(t).parent().parent().attr("editable",true);
    var id = $(t).attr("id").split("_");
    var electricCars = $("#electricCars_"+id[1]).val();
    if(electricCars!=""){
        $("#electricCarsAmortize_"+id[1]).val(parseFloat(electricCars/48).toFixed(2));
        calculateAmortizeMoney(t);
    }else{
        $("#electricCarsAmortize_"+id[1]).val("");
    }
    calculateTotal(t);

}



/**
 * 计算机器设备合计
 * @param t
 */
function calculateMachineTotal(t){
    checkFloatDataValid(t);
    var id = $(t).attr("id").split("_");

    var coldChain = $("#coldChain_"+id[1]).val();
    var safeBox =  $("#safeBox_"+id[1]).val();
    var capsuleGoodsShelf =  $("#capsuleGoodsShelf_"+id[1]).val();
    var shoppingGoodsShelf = $("#shoppingGoodsShelf_"+id[1]).val();
    if(coldChain==""&&safeBox==""&&capsuleGoodsShelf==""&&shoppingGoodsShelf==""){
        $("#machineTotal_"+id[1]).val("");
        $("#machineAmortize_"+id[1]).val("");
    }else{

        if(coldChain==""&&safeBox==""&&capsuleGoodsShelf==""&&shoppingGoodsShelf==""){
            $("#machineTotal_"+id[1]).val("");
            $("#machineAmortize_"+id[1]).val("");
        }else{
            coldChain=coldChain==""?0:parseFloat(coldChain);
            safeBox=safeBox==""?0:parseFloat(safeBox);
            capsuleGoodsShelf=capsuleGoodsShelf==""?0:parseFloat(capsuleGoodsShelf);
            shoppingGoodsShelf=shoppingGoodsShelf==""?0:parseFloat(shoppingGoodsShelf);
            var total = parseFloat(coldChain)+parseFloat(safeBox)+parseFloat(capsuleGoodsShelf)+parseFloat(shoppingGoodsShelf);
            $("#machineTotal_"+id[1]).val(total);
            $("#machineAmortize_"+id[1]).val(parseFloat(total/120).toFixed(2));
        }

        calculateAmortizeMoney(t);
        calculateTotal(t);
        
    }

}


/**
 * 计算月摊销成本
 * @param t
 */
function calculateAmortizeMoney(t){

    var id = $(t).attr("id").split("_");
    var electronicsAmortize = $("#electronicsAmortize_"+id[1]).val();
    var electricCarsAmortize = $("#electricCarsAmortize_"+id[1]).val();
    var machineAmortize = $("#machineAmortize_"+id[1]).val();
    if(electronicsAmortize==""&&electricCarsAmortize==""&&machineAmortize==""){
        $("#fixedAssetAmortizeMoney_"+id[1]).val("");
        $("input[id='amortizeMoneyMonth_"+id[1]+"']").each(function () {
            $(this).val("");
        })

    }else{

         electronicsAmortize = electronicsAmortize==""?0:parseFloat(electronicsAmortize);
         electricCarsAmortize = electricCarsAmortize==""?0:parseFloat(electricCarsAmortize);
         machineAmortize = machineAmortize==""?0:parseFloat(machineAmortize);
         var total= parseFloat(electronicsAmortize)+parseFloat(electricCarsAmortize)+parseFloat(machineAmortize);
        $("#fixedAssetAmortizeMoney_"+id[1]).val(parseFloat(total).toFixed(2));
        $("input[id='amortizeMoneyMonth_"+id[1]+"']").each(function () {
            $(this).val(parseFloat(total).toFixed(2));
        })
    }

}

/**
 * 计算总成本
 * @param t
 */
function calculateTotal(t){
    var id = $(t).attr("id").split("_");
    var electronicsTotal = $("#electronicsTotal_"+id[1]).val();
    var electricCars = $("#electricCars_"+id[1]).val();
    var machineTotal = $("#machineTotal_"+id[1]).val();
    if(electronicsTotal==""&&electricCars==""&&machineTotal==""){
        $("#fixedAssetTotal_"+id[1]).val("");

    }else{

        electronicsTotal = electronicsTotal==""?0:parseFloat(electronicsTotal);
        electricCars = electricCars==""?0:parseFloat(electricCars);
        machineTotal = machineTotal==""?0:parseFloat(machineTotal);
        var total= parseFloat(electronicsTotal)+parseFloat(electricCars)+parseFloat(machineTotal);
        $("#fixedAssetTotal_"+id[1]).val(parseFloat(total).toFixed(2));
    }
}

/**
 * 查询固定资产
 */
function getCostFixedAsset(f){

    var store_cost_tr = $("#fixedAsset_tr_2").nextAll("tr[editable='true']");
    if(store_cost_tr.length>0){//有数据修改
        $$.showConfirm_cost("提示","是否需要保存改变的数据？",function () {
            saveCostFixedAsset("single");

        },function(){

            $("#labor_tr_2").nextAll("tr[editable='true']").each(function(){
                $(this).attr("editable","false");
            });
            searchCostFixedAsset(f);
        });

    }else if(store_cost_tr.length==0){//没有数据修改
        searchCostFixedAsset(f);
    }
}


/**
 * 获取固定资产
 * **/
function searchCostFixedAsset(f){

    var index = layer.load(0,{
        shade: [0.2,'#333']
    });


    var cityId="";
    if(f=="auto"){
        cityId = cur_city_id;
    }else{
        cityId = $("#city_id_fixedAsset").val();
    }

    var storeId = $("#store_id_fixedAsset").val();
    var storeName = $("#store_name_fixedAsset").val();

    if(storeId==""&&storeName!=""){
        storeId=-10000;
    }

    var estate = $("#storeEstate_fixedAsset").val();
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


    $("#fixedAsset_tb_1").find("tr:gt(0)").remove();
    $("#fixedAsset_tb_2").find("tr:gt(0)").remove();

    doManager('costFixedAssetManager','queryCostFixedAsset',costDto,function (data) {


        if(data.result){
            var costFixedAsset = JSON.parse(data.data).fixedAsset;

            for(var i=0;i<costFixedAsset.length;i++){
                var cityName = costFixedAsset[i].city_name==null?"":costFixedAsset[i].city_name;
                var storeNo = costFixedAsset[i].store_no==null?"":costFixedAsset[i].store_no;
                var storeName = costFixedAsset[i].store_name==null?"":costFixedAsset[i].store_name;
                var estate = costFixedAsset[i].estate==null?"":costFixedAsset[i].estate;
                var amortize_money = costFixedAsset[i].amortize_money==null?"":costFixedAsset[i].amortize_money;//月摊销成本
                var total = costFixedAsset[i].total==null?"":costFixedAsset[i].total;//固定资产
                var aio = costFixedAsset[i].aio==null?"":costFixedAsset[i].aio;//装修单多功能一体机

                var mobile_phone = costFixedAsset[i].mobile_phone==null?"":costFixedAsset[i].mobile_phone;//手机
                var iPad = costFixedAsset[i].iPad==null?"":costFixedAsset[i].iPad;//ipad
                var cash_register = costFixedAsset[i].cash_register==null?"":costFixedAsset[i].cash_register;//收银机
                var computer = costFixedAsset[i].computer==null?"":costFixedAsset[i].computer;//电脑
                var scanner_gun = costFixedAsset[i].scanner_gun==null?"":costFixedAsset[i].scanner_gun;//扫描枪
                var electronics_total = costFixedAsset[i].electronics_total==null?"":costFixedAsset[i].electronics_total;//电子类合计
                var electronics_amortize = costFixedAsset[i].electronics_amortize==null?"":costFixedAsset[i].electronics_amortize;//摊销金额（36个月）
                var electric_cars = costFixedAsset[i].electric_cars==null?"":costFixedAsset[i].electric_cars;//电动车
                var electric_cars_amortize = costFixedAsset[i].electric_cars_amortize==null?"":costFixedAsset[i].electric_cars_amortize;//摊销金额（48个月）
                var cold_chain = costFixedAsset[i].cold_chain==null?"":costFixedAsset[i].cold_chain;//冷链设备
                var safe_box = costFixedAsset[i].safe_box==null?"":costFixedAsset[i].safe_box;//保险柜
                var capsule_goods_shelf = costFixedAsset[i].capsule_goods_shelf==null?"":costFixedAsset[i].capsule_goods_shelf;//微仓货架
                var shopping_goods_shelf = costFixedAsset[i].shopping_goods_shelf==null?"":costFixedAsset[i].shopping_goods_shelf;//卖场货架
                var machine_total = costFixedAsset[i].machine_total==null?"":costFixedAsset[i].machine_total;//机器设备合计
                var machine_amortize = costFixedAsset[i].machine_amortize==null?"":costFixedAsset[i].machine_amortize;//摊销金额（120个月）




                var FixedAsset_td =
                    "<td style='text-align: center;background-color:#e8e8e8'>"+(i+1)+"</td><td style='text-align: center;background-color:#e8e8e8'>"+storeNo+"</td><td style='background-color:#e8e8e8'><p>"+storeName+"</p></td><td style='background-color:#e8e8e8;text-align: center'><p>"+estate+"</p></td>"+
                    "<td><input type='text'     onkeyup='calculateElectronicsTotal(this)' id='aio_"+i+"'    value='"+aio+"'/></td>"+
                    "<td><input type='text'     onkeyup='calculateElectronicsTotal(this)' id='mobilePhone_"+i+"'    value='"+mobile_phone+"'/></td>"+
                    "<td><input type='text'     onkeyup='calculateElectronicsTotal(this)' id='iPad_"+i+"'    value='"+iPad+"'/></td>"+
                    "<td><input type='text'     onkeyup='calculateElectronicsTotal(this)' id='cashRegister_"+i+"'    value='"+cash_register+"'/></td>"+
                    "<td><input type='text'     onkeyup='calculateElectronicsTotal(this)' id='computer_"+i+"'    value='"+computer+"'/></td>"+
                    "<td><input type='text'     onkeyup='calculateElectronicsTotal(this)' id='scannerGun_"+i+"'    value='"+scanner_gun+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='electronicsTotal_"+i+"'    value='"+electronics_total+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='electronicsAmortize_"+i+"'    value='"+electronics_amortize+"'/></td>"+
                    "<td><input type='text'     onkeyup='calculateElectricCarsAmortize(this)' id='electricCars_"+i+"'    value='"+electric_cars+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='electricCarsAmortize_"+i+"'    value='"+electric_cars_amortize+"'/></td>"+
                    "<td><input type='text'     onkeyup='calculateMachineTotal(this)' id='coldChain_"+i+"'    value='"+cold_chain+"'/></td>"+
                    "<td><input type='text'     onkeyup='calculateMachineTotal(this)' id='safeBox_"+i+"'    value='"+safe_box+"'/></td>"+
                    "<td><input type='text'     onkeyup='calculateMachineTotal(this)' id='capsuleGoodsShelf_"+i+"'    value='"+capsule_goods_shelf+"'/></td>"+
                    "<td><input type='text'     onkeyup='calculateMachineTotal(this)' id='shoppingGoodsShelf_"+i+"'    value='"+shopping_goods_shelf+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='machineTotal_"+i+"'    value='"+machine_total+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='machineAmortize_"+i+"'    value='"+machine_amortize+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='fixedAssetAmortizeMoney_"+i+"' value='"+amortize_money+"'/></td>" +
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='fixedAssetTotal_"+i+"'  value='"+total+"'/></td>" ;
                $("#fixedAsset_tb_2").append("<tr id='"+storeNo+"' editable='false'>"+FixedAsset_td+"<input type='hidden'  id='storeName' value='"+storeName+"'/><input type='hidden'  id='cityName' value='"+cityName+"'/></tr>");
            }

            layer.close(index);

        }
    });

}
/**
 * 导出固定资产
 *
 * **/
function   exportCostFixedAsset(){

    var cityId = $("#city_id_fixedAsset").val();


    var storeId = $("#store_id_fixedAsset").val();
    var storeName = $("#store_name_fixedAsset").val();

    if(storeId==""&&storeName!=""){
        storeId=-10000;
    }

    var estate = $("#storeEstate_fixedAsset").val();
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
    doManager('costFixedAssetManager','exportCostFixedAsset',costDto,function (data) {
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
 * 保存固定资产
 *
 * **/
function saveCostFixedAsset(ac){

    var  saveResult="";
    var store_cost_tr = $("#fixedAsset_tr_2").nextAll("tr[editable='true']");
    var costFixedAssetArray = [];
    for(var i=0;i<store_cost_tr.length;i++){
        var cityName = $(store_cost_tr[i]).find("input[id='cityName']").val();//城市名称
        var storeNo= $(store_cost_tr[i]).attr("id");//门店编号
        var storeName = $(store_cost_tr[i]).find("input[id='storeName']").val();//门店名称
        var amortizeMoney = $(store_cost_tr[i]).find("input[id^='fixedAssetAmortizeMoney_']").val();//月摊销
        var total = $(store_cost_tr[i]).find('input[id^="fixedAssetTotal_"]').val();//合计
        var aio = $(store_cost_tr[i]).find('input[id^="aio_"]').val();//多功能一体机
        var mobilePhone = $(store_cost_tr[i]).find('input[id^="mobilePhone_"]').val();//手机
        var iPad = $(store_cost_tr[i]).find('input[id^="iPad_"]').val();//ipad
        var cashRegister = $(store_cost_tr[i]).find('input[id^="cashRegister_"]').val();//收银机
        var computer = $(store_cost_tr[i]).find('input[id^="computer_"]').val();//电脑
        var scannerGun = $(store_cost_tr[i]).find('input[id^="scannerGun_"]').val();//扫描枪
        var electronicsTotal = $(store_cost_tr[i]).find('input[id^="electronicsTotal_"]').val();//电子设备合计
        var electronicsAmortize = $(store_cost_tr[i]).find('input[id^="electronicsAmortize_"]').val();//电子设备摊销
        var electricCars = $(store_cost_tr[i]).find('input[id^="electricCars_"]').val();//电动车
        var electricCarsAmortize = $(store_cost_tr[i]).find('input[id^="electricCarsAmortize_"]').val();//电动车摊销
        var coldChain = $(store_cost_tr[i]).find('input[id^="coldChain_"]').val();//冷链设备
        var safeBox = $(store_cost_tr[i]).find('input[id^="safeBox_"]').val();//保险柜
        var capsuleGoodsShelf = $(store_cost_tr[i]).find('input[id^="capsuleGoodsShelf_"]').val();//微仓货架
        var shoppingGoodsShelf = $(store_cost_tr[i]).find('input[id^="shoppingGoodsShelf_"]').val();//卖场货架
        var machineTotal = $(store_cost_tr[i]).find('input[id^="machineTotal_"]').val();//设备合计
        var machineAmortize = $(store_cost_tr[i]).find('input[id^="machineAmortize_"]').val();//设备摊销

        var costFixedAsset = {
             cityName:cityName,
             storeNo:storeNo,
             storeName:storeName,
             amortizeMoney:amortizeMoney,
             total:total,
             aio:aio,
             mobilePhone:mobilePhone,
             iPad:iPad,
             cashRegister:cashRegister,
             computer:computer,
             scannerGun:scannerGun,
             electronicsTotal:electronicsTotal,
             electronicsAmortize:electronicsAmortize,
             electricCars:electricCars,
             electricCarsAmortize:electricCarsAmortize,
             coldChain:coldChain,
             safeBox:safeBox,
             capsuleGoodsShelf:capsuleGoodsShelf,
             shoppingGoodsShelf:shoppingGoodsShelf,
             machineTotal:machineTotal,
             machineAmortize:machineAmortize
        }
        costFixedAssetArray.push(costFixedAsset);

    }

    doManager('costFixedAssetManager','saveCostFixedAsset',[costFixedAssetArray],function (data) {
        if(data.result){
            var result= JSON.parse(data.data);

            if(result.status=='success'){
                $("#fixedAsset_tr_2").nextAll("tr[editable='true']").each(function () {
                    $(this).attr("editable","false");
                });
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