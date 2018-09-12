

// function showWholeContent(t){
//     var id = $(t).attr("id");
//     var content = $(t).html();
//     layer.tips(content,"#"+id, {
//         tips: [1, '#0ACC4C'],
//         time: 1000
//     });
// }


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
        aio = aio==""?0:parseFloat(id);
        iPad = iPad==""?0:parseFloat(iPad);
        cashRegister = cashRegister==""?0:parseFloat(cashRegister);
        computer = computer==""?0:parseFloat(computer);
        scannerGun = scannerGun==""?0:parseFloat(scannerGun);
        mobilePhone = mobilePhone==""?0:parseFloat(mobilePhone);
        var total = aio+iPad+cashRegister+computer+scannerGun+mobilePhone;
        $("#electronicsTotal_"+id[1]).val(total);
        $("#electronicsAmortize_"+id[1]).val(total/36);
    }

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
        $("#electricCarsAmortize_"+id[1]).val(parseFloat(electricCars)/48);
    }

}



/**
 * 计算机器设备合计
 * @param t
 */
function calculateMachineTotal(t){
    var id = $(t).attr("id").split("_");

    var coldChain = $("#coldChain_"+id[1]).val();
    var safeBox =  $("#safeBox_"+id[1]).val();
    var capsuleGoodsShelf =  $("#capsuleGoodsShelf_"+id[1]).val();
    var shoppingGoodsShelf = $("#shoppingGoodsShelf_"+id[1]).val();
    if(coldChain==""&&safeBox==""&&capsuleGoodsShelf==""&&shoppingGoodsShelf==""){
        $("#machineTotal_"+id[1]).val("");
    }else{
        coldChain=coldChain==""?0:parseFloat(coldChain);
        safeBox=safeBox==""?0:parseFloat(safeBox);
        capsuleGoodsShelf==""?0:parseFloat(capsuleGoodsShelf);
        shoppingGoodsShelf==""?0:parseFloat(shoppingGoodsShelf);
        var total = coldChain+safeBox+capsuleGoodsShelf+shoppingGoodsShelf;
        $("#machineTotal_"+id[1]).val(total);
        
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
    if(electronicsAmortize==""||electricCarsAmortize==""||machineAmortize==""){

    }else{

         electronicsAmortize = electronicsAmortize==""?0:parseFloat(electronicsAmortize);
         electricCarsAmortize = electricCarsAmortize==""?0:parseFloat(electricCarsAmortize);
         machineAmortize = machineAmortize==""?0:parseFloat(machineAmortize);
         var total= electronicsAmortize+electricCarsAmortize+machineAmortize;
        $("#amortizeMoney_"+id[1]).val(total);
        $("input[id='amortizeMoneyMonth_"+id[1]+"']").each(function () {
            $(this).val(total);
        })
    }

}

/**
 * 计算单店装修总花费
 * @param t
 */
function calculateTotal(t){
    var id = $(t).attr("id").split("_");
    var wholeProcessManager = $("#wholeProcessManager_"+id[1]).val();
    var design =  $("#design_"+id[1]).val();
    var processManage =  $("#processManage_"+id[1]).val();
    var airConditioner = $("#airConditioner_"+id[1]).val();
    var lightBox = $("#lightBox_"+id[1]).val();
    var decorateCost = $("#decorateCost_"+id[1]).val();
    var businessScreen = $("#businessScreen_"+id[1]).val();
    if(wholeProcessManager!=""&&design!=""&&processManage!=""&&airConditioner!=""&&lightBox!=""&&decorateCost!=""&&businessScreen!=""){
        $("#total_"+id[1]).val(parseFloat(wholeProcessManager)+parseFloat(design)+parseFloat(processManage)+parseFloat(airConditioner)+parseFloat(lightBox)+parseFloat(decorateCost)+parseFloat(businessScreen));
    }

}

/**
 * 计算摊销月度成本
 * @param t
 */
function calculateAmortizeMoney(t){
    var id = $(t).attr("id").split("_");
    $(t).parent().parent().attr("editable",true);
    t.value = t.value.replace(/[^\d]/g,"");  //清除“数字”以外的字符
    var total = $("#total_"+id[1]).val();
    if(t.value!=""&&total!=""&&t.value!="0"){
        var amortizeMoney = (parseFloat(total)/parseInt(t.value)).toFixed(2);
        $("#amortizeMoney_"+id[1]).val(amortizeMoney);
        $("input[id='amortizeMoneyMonth_"+id[1]+"']").each(function () {
            $(this).val(amortizeMoney);
        })
    }

}

/**
 * 获取固定资产
 * **/
function getCostFixedAsset(){

    var index = layer.load(0,{
        shade: [0.2,'#333']
    });
    var storeNo=$("#storeNo_FixedAsset").val()==""?null:$("#storeNo_FixedAsset").val();
    var storeName=$("#storeName_FixedAsset").val()==""?null:$("#storeName_FixedAsset").val();


    $("#fixedAsset_tb_1").find("tr:gt(0)").remove();
    $("#fixedAsset_tb_2").find("tr:gt(0)").remove();

    doManager('costFixedAssetManager','queryCostFixedAsset',[storeNo,storeName],function (data) {


        if(data.result){
            var costFixedAsset = JSON.parse(data.data).fixedAsset;

            for(var i=0;i<costFixedAsset.length;i++){
                var storeNo = costFixedAsset[i].store_no==null?"":costFixedAsset[i].store_no;
                var storeName = costFixedAsset[i].store_name==null?"":costFixedAsset[i].store_name;
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


                $("#fixedAsset_tb_1").append("<tr><td style='text-align: center;background-color:#A9A9A9'>"+(i+1)+"</td><td style='text-align: center;background-color:#A9A9A9'>"+storeNo+"</td><td style='background-color:#A9A9A9'><p>"+storeName+"</p></td></tr>");

                var FixedAsset_td = "<td><input type='text'  style='background-color: #e8e8e8' readonly   id='amortizeMoney_"+i+"' value='"+amortize_money+"'/></td>" +
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='total_"+i+"'  value='"+total+"'/></td>" +
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
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='amortizeMoneyMonth_"+i+"'    value='"+amortize_money+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='amortizeMoneyMonth_"+i+"'    value='"+amortize_money+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='amortizeMoneyMonth_"+i+"'    value='"+amortize_money+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='amortizeMoneyMonth_"+i+"'    value='"+amortize_money+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='amortizeMoneyMonth_"+i+"'    value='"+amortize_money+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='amortizeMoneyMonth_"+i+"'    value='"+amortize_money+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='amortizeMoneyMonth_"+i+"'    value='"+amortize_money+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='amortizeMoneyMonth_"+i+"'    value='"+amortize_money+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='amortizeMoneyMonth_"+i+"'    value='"+amortize_money+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='amortizeMoneyMonth_"+i+"'    value='"+amortize_money+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='amortizeMoneyMonth_"+i+"'    value='"+amortize_money+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='amortizeMoneyMonth_"+i+"'    value='"+amortize_money+"'/></td>";



                $("#fixedAsset_tb_2").append("<tr id='"+storeNo+"' editable='false'>"+FixedAsset_td+"<input type='hidden'  id='storeName' value='"+storeName+"'/></tr>");
            }

            layer.close(index);

        }
    },false);

}
/**
 * 导出装修摊销
 *
 * **/
function   exportCostFixedAsset(){
    var storeNo=$("#storeNo_FixedAsset").val()==""?null:$("#storeNo_FixedAsset").val();
    var storeName=$("#storeName_FixedAsset").val()==""?null:$("#storeName_FixedAsset").val();
    doManager('costFixedAssetManager','exportCostFixedAsset',[storeNo,storeName],function (data) {
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
 * 保存装修摊销
 *
 * **/
function saveCostFixedAsset(){

    var store_cost_tr = $("#FixedAsset_tr_2").nextAll("tr[editable='true']");
    var costFixedAssetArray = [];
    for(var i=0;i<store_cost_tr.length;i++){
        var storeNo= $(store_cost_tr[i]).attr("id");
        var storeName = $(store_cost_tr[i]).find("input[id='storeName']").val();
        var decorationCompany = $(store_cost_tr[i]).find("input[id^='decorationCompany_']").val();//装修公司
        var structureAcreage = $(store_cost_tr[i]).find('input[id^="structureAcreage_"]').val();//建筑面积
        var FixedAssetUnitPrice = $(store_cost_tr[i]).find('input[id^="FixedAssetUnitPrice_"]').val();//单方造价
        var decorateCost = $(store_cost_tr[i]).find('input[id^="decorateCost_"]').val();//装修施工费
        var businessScreen = $(store_cost_tr[i]).find('input[id^="businessScreen_"]').val();//商业展屏
        var furniture = $(store_cost_tr[i]).find('input[id^="furniture_"]').val();//家具
        var lightBox = $(store_cost_tr[i]).find('input[id^="lightBox_"]').val();//灯箱
        var processManage = $(store_cost_tr[i]).find('input[id^="processManage_"]').val();//过程管理
        var processManageSurcharge = $(store_cost_tr[i]).find('input[id^="processManageSurcharge_"]').val();//过程管理费
        var airConditioner = $(store_cost_tr[i]).find('input[id^="airConditioner_"]').val();//空调设备
        var airConditionerSurcharge = $(store_cost_tr[i]).find('input[id^="airConditionerSurcharge_"]').val();//空调设备费
        var design = $(store_cost_tr[i]).find('input[id^="design_"]').val();//设计
        var wholeProcessManager = $(store_cost_tr[i]).find('input[id^="wholeProcessManager_"]').val();//全过程管理
        var total = $(store_cost_tr[i]).find('input[id^="total_"]').val();//单店产值合计
        var amortizeMonth = $(store_cost_tr[i]).find('input[id^="amortizeMonth_"]').val();//摊销月份
        var amortizeMoney = $(store_cost_tr[i]).find('input[id^="amortizeMoney_"]').val();//摊销月度成本
        var completedDate = $(store_cost_tr[i]).find('input[id^="completedDate_"]').val();//竣工时间
        var contractDate = $(store_cost_tr[i]).find('input[id^="contractDate_"]').val();//合同签订日期
        var costFixedAsset = {

            storeNo:storeNo,
            storeName:storeName,
            decorationCompany:decorationCompany,
            structureAcreage:structureAcreage,
            FixedAssetUnitPrice:FixedAssetUnitPrice,
            decorateCost:decorateCost,
            businessScreen:businessScreen,
            furniture:furniture,
            lightBox:lightBox,
            processManage:processManage,
            processManageSurcharge:processManageSurcharge,
            wholeProcessManageSurcharge:wholeProcessManager,
            airConditioner:airConditioner,
            airConditionerSurcharge:airConditionerSurcharge,
            design:design,
            total:total,
            amortizeMonth:amortizeMonth,
            amortizeMoney:amortizeMoney,
            completedDate:completedDate,
            contractDate:contractDate

        }
        costFixedAssetArray.push(costFixedAsset);

    }

    doManager('costFixedAssetManager','saveCostFixedAsset',[costFixedAssetArray],function (data) {
        if(data.result){
            var result= JSON.parse(data.data);

            if(result.status=='success'){
                $$.showMessage('提示',"保存成功！");
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