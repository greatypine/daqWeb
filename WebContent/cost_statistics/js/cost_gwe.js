


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
        calculateAmortizeMoney(t);
    }
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
        coldChain=coldChain==""?0:parseFloat(coldChain);
        safeBox=safeBox==""?0:parseFloat(safeBox);
        capsuleGoodsShelf==""?0:parseFloat(capsuleGoodsShelf);
        shoppingGoodsShelf==""?0:parseFloat(shoppingGoodsShelf);
        if(coldChain==""&&safeBox==""&&capsuleGoodsShelf==""&&shoppingGoodsShelf==""){
            $("#machineTotal_"+id[1]).val("");
            $("#machineAmortize_"+id[1]).val("");
        }else{
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
        $("#amortizeMoney_"+id[1]).val("");
        $("input[id='amortizeMoneyMonth_"+id[1]+"']").each(function () {
            $(this).val("");
        })

    }else{

        electronicsAmortize = electronicsAmortize==""?0:parseFloat(electronicsAmortize);
        electricCarsAmortize = electricCarsAmortize==""?0:parseFloat(electricCarsAmortize);
        machineAmortize = machineAmortize==""?0:parseFloat(machineAmortize);
        var total= parseFloat(electronicsAmortize)+parseFloat(electricCarsAmortize)+parseFloat(machineAmortize);
        $("#amortizeMoney_"+id[1]).val(parseFloat(total).toFixed(2));
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
        $("#total_"+id[1]).val("");
    }else{

        electronicsTotal = electronicsTotal==""?0:parseFloat(electronicsTotal);
        electricCars = electricCars==""?0:parseFloat(electricCars);
        machineTotal = machineTotal==""?0:parseFloat(machineTotal);
        var total= parseFloat(electronicsTotal)+parseFloat(electricCars)+parseFloat(machineTotal);
        $("#total_"+id[1]).val(parseFloat(total).toFixed(2));
    }
}




/**
 * 获取固定资产
 * **/
function getCostGWE(){

    var index = layer.load(0,{
        shade: [0.2,'#333']
    });
    var storeNo=$("#storeNo_GWE").val()==""?null:$("#storeNo_GWE").val();
    var storeName=$("#storeName_GWE").val()==""?null:$("#storeName_GWE").val();


    $("#GWE_tb_1").find("tr:gt(0)").remove();
    $("#GWE_tb_2").find("tr:gt(0)").remove();

    doManager('costGWEManager','queryCostGWE',[storeNo,storeName],function (data) {


        if(data.result){
            var costGWE = JSON.parse(data.data).GWE;

            for(var i=0;i<costGWE.length;i++){
                var storeNo = costGWE[i].store_no==null?"":costGWE[i].store_no;
                var storeName = costGWE[i].store_name==null?"":costGWE[i].store_name;
                var amortize_money = costGWE[i].amortize_money==null?"":costGWE[i].amortize_money;//月摊销成本
                var total = costGWE[i].total==null?"":costGWE[i].total;//固定资产
                var aio = costGWE[i].aio==null?"":costGWE[i].aio;//装修单多功能一体机

                var mobile_phone = costGWE[i].mobile_phone==null?"":costGWE[i].mobile_phone;//手机
                var iPad = costGWE[i].iPad==null?"":costGWE[i].iPad;//ipad
                var cash_register = costGWE[i].cash_register==null?"":costGWE[i].cash_register;//收银机
                var computer = costGWE[i].computer==null?"":costGWE[i].computer;//电脑
                var scanner_gun = costGWE[i].scanner_gun==null?"":costGWE[i].scanner_gun;//扫描枪
                var electronics_total = costGWE[i].electronics_total==null?"":costGWE[i].electronics_total;//电子类合计
                var electronics_amortize = costGWE[i].electronics_amortize==null?"":costGWE[i].electronics_amortize;//摊销金额（36个月）
                var electric_cars = costGWE[i].electric_cars==null?"":costGWE[i].electric_cars;//电动车
                var electric_cars_amortize = costGWE[i].electric_cars_amortize==null?"":costGWE[i].electric_cars_amortize;//摊销金额（48个月）
                var cold_chain = costGWE[i].cold_chain==null?"":costGWE[i].cold_chain;//冷链设备
                var safe_box = costGWE[i].safe_box==null?"":costGWE[i].safe_box;//保险柜
                var capsule_goods_shelf = costGWE[i].capsule_goods_shelf==null?"":costGWE[i].capsule_goods_shelf;//微仓货架
                var shopping_goods_shelf = costGWE[i].shopping_goods_shelf==null?"":costGWE[i].shopping_goods_shelf;//卖场货架
                var machine_total = costGWE[i].machine_total==null?"":costGWE[i].machine_total;//机器设备合计
                var machine_amortize = costGWE[i].machine_amortize==null?"":costGWE[i].machine_amortize;//摊销金额（120个月）


                $("#GWE_tb_1").append("<tr><td style='text-align: center;background-color:#A9A9A9'>"+(i+1)+"</td><td style='text-align: center;background-color:#A9A9A9'>"+storeNo+"</td><td style='background-color:#A9A9A9'><p>"+storeName+"</p></td></tr>");

                var GWE_td = "<td><input type='text'  style='background-color: #e8e8e8' readonly   id='amortizeMoney_"+i+"' value='"+amortize_money+"'/></td>" +
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



                $("#GWE_tb_2").append("<tr id='"+storeNo+"' editable='false'>"+GWE_td+"<input type='hidden'  id='storeName' value='"+storeName+"'/></tr>");
            }

            layer.close(index);

        }
    });

}
/**
 * 导出固定资产
 *
 * **/
function   exportCostGWE(){
    var storeNo=$("#storeNo_GWE").val()==""?null:$("#storeNo_GWE").val();
    var storeName=$("#storeName_GWE").val()==""?null:$("#storeName_GWE").val();
    doManager('costGWEManager','exportCostGWE',[storeNo,storeName],function (data) {
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
 * 保存固定资产
 *
 * **/
function saveCostGWE(){
    var year = $("#year_gwe").val();
    var store_cost_tr = $("#gwe_tr_2").nextAll("tr[editable='true']");
    var costGWEArray = [];
    for(var i=0;i<store_cost_tr.length;i++){
        var storeNo= $(store_cost_tr[i]).attr("id");//门店编号
        var storeName = $(store_cost_tr[i]).find("input[id='storeName']").val();//门店名称
        var electricCharges = $(store_cost_tr[i]).find("input[id^='electricCharge_']").val();//电费
        var waterRates = $(store_cost_tr[i]).find('input[id^="waterRate_"]').val();//水费

        for(var m=0;m<electricCharges.length;m++){
            var costGWE = {
                storeNo:storeNo,
                storeName:storeName,
                year:year
            }
            var ecv = $(electricCharges[m]).val();//电费
            var wrv = $(waterRates[n]).val();//水费
            var month= $(electricCharges[m]).attr("id").split("_")[1];//月份
            costGWE["electricityFee"]=ecv;
            costGWE["waterFee"]=wrv;
            costGWE["month"]=month;
            costGWEArray.push(costGWE);
        }

    }

    doManager('costGWEManager','saveCostGWE',[costGWEArray],function (data) {
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