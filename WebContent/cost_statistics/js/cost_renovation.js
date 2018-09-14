


/**
 * 计算装修施工
 * @param t
 */
function calculateDecorateCost(t){
    checkFloatDataValid(t);
    $(t).parent().parent().attr("editable",true);
    var id = $(t).attr("id").split("_");
    var structureAcreage = $("#structureAcreage_"+id[1]).val();
    var GWEUnitPrice = $("#GWEUnitPrice_"+id[1]).val();
    if(structureAcreage!=""&&GWEUnitPrice!=""){
        $("#decorateCost_"+id[1]).val((parseFloat(structureAcreage)*parseFloat(GWEUnitPrice)).toFixed(2));
        $("#lightBox_"+id[1]).val(parseFloat(structureAcreage)*123.52.toFixed(2));
        $("#airConditioner_"+id[1]).val((parseFloat(structureAcreage)*300).toFixed(2));
        $("#airConditionerSurcharge_"+id[1]).val((parseFloat(structureAcreage)*300*0.048).toFixed(2));
        $("#design_"+id[1]).val((parseFloat(structureAcreage)*130).toFixed(2));


    }else {
        $("#decorateCost_"+id[1]).val("");
        if(structureAcreage!=""){
            $("#lightBox_"+id[1]).val(parseFloat(structureAcreage)*123.52.toFixed(2));
            $("#airConditioner_"+id[1]).val((parseFloat(structureAcreage)*300).toFixed(2));
            $("#airConditionerSurcharge_"+id[1]).val((parseFloat(structureAcreage)*300*0.048).toFixed(2));
            $("#design_"+id[1]).val((parseFloat(structureAcreage)*130).toFixed(2));
        }

    }


    calculateWholeProcessManage(t);

    calculateTotal(t);

}

/**
 * 计算全过程管理
 * @param t
 */
function calculateWholeProcessManage(t){
    var id = $(t).attr("id").split("_");
    var decorateCost = $("#decorateCost_"+id[1]).val();
    var design =  $("#design_"+id[1]).val();
    var processManageSurcharge =  $("#processManageSurcharge_"+id[1]).val();
    var airConditionerSurcharge = $("#airConditionerSurcharge_"+id[1]).val();
    if(decorateCost!=""&&design!=""&&processManageSurcharge!=""&&airConditionerSurcharge!=""){
        $("#wholeProcessManager_"+id[1]).val(((parseFloat(decorateCost)+parseFloat(design))*0.055+parseFloat(processManageSurcharge)+parseFloat(airConditionerSurcharge)).toFixed(2));
    }else{
        $("#wholeProcessManager_"+id[1]).val("");
    }

}


/**
 * 计算过程管理
 * @param t
 */
function calculateProcessManage(t){

    checkFloatDataValid(t);
    $(t).parent().parent().attr("editable",true);
    var id = $(t).attr("id").split("_");
    var businessScreen = $("#businessScreen_"+id[1]).val();
    var furniture = $("#furniture_"+id[1]).val();
    var lightBox = $("#lightBox_"+id[1]).val();
    if(businessScreen==""||furniture==""||lightBox==""){
        $("#processManage_"+id[1]).val("");
        $("#processManageSurcharge_"+id[1]).val("");
    }else{
        $("#processManage_"+id[1]).val((parseFloat(businessScreen)+parseFloat(furniture)+parseFloat(lightBox)).toFixed(2));
        $("#processManageSurcharge_"+id[1]).val(((parseFloat(businessScreen)+parseFloat(furniture)+parseFloat(lightBox))*0.035).toFixed(2));
    }
    calculateWholeProcessManage(t);
    calculateTotal(t);
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
 * 获取水电燃气费
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


            var rent_td_sum = "";
            var uniform_charge_sum=0;

            for(var i=0;i<costGWE.length;i++){
                var storeNo = costGWE[i].store_no==null?"":costGWE[i].store_no;
                var storeName = costGWE[i].store_name==null?"":costGWE[i].store_name;
                var decoration_company = costGWE[i].decoration_company==null?"":costGWE[i].decoration_company;//装修公司
                var structure_acreage = costGWE[i].structure_acreage==null?"":costGWE[i].structure_acreage;//建筑面积
                var GWE_unit_price = costGWE[i].GWE_unit_price==null?"":costGWE[i].GWE_unit_price;//装修单价
                var decorateCost = costGWE[i].decorate_cost==null?"":costGWE[i].decorate_cost;//装修施工
                var business_screen = costGWE[i].business_screen==null?"":costGWE[i].business_screen;//商业展屏
                var furniture = costGWE[i].furniture==null?"":costGWE[i].furniture;//家具
                var light_box = costGWE[i].light_box==null?"":costGWE[i].light_box;//灯箱
                var process_manage = costGWE[i].process_manage==null?"":costGWE[i].process_manage;//过程管理
                var process_manage_surcharge = costGWE[i].process_manage_surcharge==null?"":costGWE[i].process_manage_surcharge;//过程管理费
                var air_conditioner = costGWE[i].air_conditioner==null?"":costGWE[i].air_conditioner;//空调设备
                var air_conditioner_surcharge = costGWE[i].air_conditioner_surcharge==null?"":costGWE[i].air_conditioner_surcharge;//空调设备费
                var design = costGWE[i].design==null?"":costGWE[i].design;//设计
                var total = costGWE[i].total==null?"":costGWE[i].total;//单店总装修花销
                var amortize_month = costGWE[i].amortize_month==null?"":costGWE[i].amortize_month;//摊销月
                var amortize_money = costGWE[i].amortize_money==null?"":costGWE[i].amortize_money;//摊销金额
                var completed_date = costGWE[i].completed_date==null?"":costGWE[i].completed_date;//竣工日期
                var contract_date = costGWE[i].contract_date==null?"":costGWE[i].contract_date;//合同日期


                $("#GWE_tb_1").append("<tr><td style='text-align: center;background-color:#A9A9A9'>"+(i+1)+"</td><td style='text-align: center;background-color:#A9A9A9'>"+storeNo+"</td><td style='background-color:#A9A9A9'><p>"+storeName+"</p></td></tr>");

                var GWE_td = "<td><input type='text'     id='decorationCompany_"+i+"' value='"+decoration_company+"'/></td>" +
                    "<td><input type='text'     onkeyup='calculateDecorateCost(this)' id='structureAcreage_"+i+"'  value='"+structure_acreage+"'/></td>" +
                    "<td><input type='text'     onkeyup='calculateDecorateCost(this)' id='GWEUnitPrice_"+i+"'    value='"+GWE_unit_price+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='decorateCost_"+i+"'    value='"+decorateCost+"'/></td>"+
                    "<td><input type='text'     onkeyup='calculateProcessManage(this)' id='businessScreen_"+i+"'    value='"+business_screen+"'/></td>"+
                    "<td><input type='text'     onkeyup='calculateProcessManage(this)' id='furniture_"+i+"'    value='"+furniture+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='lightBox_"+i+"'    value='"+light_box+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='processManage_"+i+"'    value='"+process_manage+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='processManageSurcharge_"+i+"'    value='"+process_manage_surcharge+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='airConditioner_"+i+"'    value='"+air_conditioner+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='airConditionerSurcharge_"+i+"'    value='"+air_conditioner_surcharge+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='design_"+i+"'    value='"+design+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='wholeProcessManager_"+i+"'    value='"+total+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='total_"+i+"'    value='"+total+"'/></td>"+
                    "<td><input type='text'     onkeyup='calculateAmortizeMoney(this)' id='amortizeMonth_"+i+"'    value='"+amortize_month+"'/></td>"+
                    "<td><input type='text'     style='background-color: #e8e8e8' id='amortizeMoney_"+i+"'    value='"+amortize_money+"'/></td>"+
                    "<td><input type='text'     id='completedDate_"+i+"'    value='"+completed_date+"'/></td>"+
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
                    "<td><input type='text'     style='background-color: #e8e8e8' readonly id='amortizeMoneyMonth_"+i+"'    value='"+amortize_money+"'/></td>"+
                    "<td><input type='text'     id='contractDate_"+i+"'    value='"+contract_date+"'/></td>";


                $("#GWE_tb_2").append("<tr id='"+storeNo+"' editable='false'>"+GWE_td+"<input type='hidden'  id='storeName' value='"+storeName+"'/></tr>");

                laydate.render({//月份控件
                    elem:"#completedDate_"+i,
                    value:completed_date==""?"":new Date(completed_date),
                    format: 'yyyy/MM/dd',
                    btns: ['confirm','now',"clear"],
                    theme: '#428bca',
                    done: function(value, date, endDate){
                        console.log(value)
                        $($(this)[0].elem.selector).parent().parent().attr("editable",true);
                    }
                });

                laydate.render({//月份控件
                    elem:"#contractDate_"+i,
                    value:contract_date==""?"":new Date(contract_date),
                    format: 'yyyy/MM/dd',
                    btns: ['confirm','now',"clear"],
                    theme: '#428bca',
                    change: function(value, date, endDate){
                        // console.log(value); //得到日期生成的值，如：2017-08-18
                        // console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
                        // console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
                        $($(this)[0].elem.selector).parent().parent().attr("editable",true);
                    }
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
 * 保存装修摊销
 *
 * **/
function saveCostGWE(){

    var store_cost_tr = $("#GWE_tr_2").nextAll("tr[editable='true']");
    var costGWEArray = [];
    for(var i=0;i<store_cost_tr.length;i++){
        var storeNo= $(store_cost_tr[i]).attr("id");
        var storeName = $(store_cost_tr[i]).find("input[id='storeName']").val();
        var decorationCompany = $(store_cost_tr[i]).find("input[id^='decorationCompany_']").val();//装修公司
        var structureAcreage = $(store_cost_tr[i]).find('input[id^="structureAcreage_"]').val();//建筑面积
        var GWEUnitPrice = $(store_cost_tr[i]).find('input[id^="GWEUnitPrice_"]').val();//单方造价
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
        var costGWE = {

            storeNo:storeNo,
            storeName:storeName,
            decorationCompany:decorationCompany,
            structureAcreage:structureAcreage,
            GWEUnitPrice:GWEUnitPrice,
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
        costGWEArray.push(costGWE);

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