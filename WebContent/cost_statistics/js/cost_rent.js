/**
 * 弹出提示
 */
// function showTip(t){
//     var id = $(t).attr("id")
//     layer.tips('员工薪酬+工服摊销+住宿星店房租',"#"+id, {
//         tips: [1, '#0ACC4C'],
//         time: 1000
//     });
// }

/**
 * 检测租金成本数据并记录编辑的门店
 *
 * **/
var editStore = new Set();
function checkCostRent(t){
    //eidtStore.push($(t).parent().parent().attr("id"));
    $(t).parent().parent().attr("editable",true);
    //人工成本录入
    var reg = /^[1-9]+(.[0-9]{2})?$/;
    var v= $(t).val();
    if(!reg.test(v)&&v!=""&&v!="."){

        t.value = t.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符
        t.value = t.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的
        t.value = t.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
        t.value = t.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数
        if(t.value.indexOf(".")< 0 && t.value !=""){//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额
            t.value= parseFloat(t.value);
        }

        $(t).val(t.value);
    }else if(v=="."){
        $(t).val("");
    }
    var id=$(t).attr("id");
    var colNum = id.split("_")[1];
    var rowNum = id.split("_")[2];
    var emolument = $("#emolument_"+colNum+"_"+rowNum).val();
    var uniformAmortize = $("#uniformAmortize_"+colNum+"_"+rowNum).val();
    var accommodation = $("#accommodation_"+colNum+"_"+rowNum).val();
    var subtotal="";
    if(emolument==""&&uniformAmortize==""&&accommodation==""){
        $("#subtotal_"+colNum+"_"+rowNum).val("");
    }else{
        emolument = $("#emolument_"+colNum+"_"+rowNum).val()==""?0:$("#emolument_"+colNum+"_"+rowNum).val();
        uniformAmortize = $("#uniformAmortize_"+colNum+"_"+rowNum).val()==""?0:$("#uniformAmortize_"+colNum+"_"+rowNum).val();
        accommodation = $("#accommodation_"+colNum+"_"+rowNum).val()==""?0:$("#accommodation_"+colNum+"_"+rowNum).val();
        $("#subtotal_"+colNum+"_"+rowNum).val(parseFloat(emolument)+parseFloat(uniformAmortize)+parseFloat(accommodation));
    }

}

/**
 * 检测月物业费录入
 * @param t
 */
function  checkPropertyFee(t) {

    $(t).parent().parent().attr("editable",true);

    var reg = /^[1-9]+(.[0-9]{2})?$/;
    var v= $(t).val();
    if(!reg.test(v)&&v!=""&&v!="."){

        t.value = t.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符
        t.value = t.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的
        t.value = t.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
        t.value = t.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数
        if(t.value.indexOf(".")< 0 && t.value !=""){//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额
            t.value= parseFloat(t.value);
        }

        $(t).val(t.value);
    }else if(v=="."){
        $(t).val("");
    }

    var id = t.id.split("_");
    var propertyDeadLine = $("#propertyDeadLine_"+id[1]).val();
    if(propertyDeadLine==""){
        $("#propertyFeeYear_"+id[1]).val("");
    }else if(propertyDeadLine!=""&&t.value!=""){
        var property_fee_year = parseFloat(t.value)*parseInt(propertyDeadLine);
        $("#propertyFeeYear_"+id[1]).val(property_fee_year.toFixed(2));
    }else if(t.value==""){
        $("#propertyFeeYear_"+id[1]).val("");
    }



}


/**
 * 检测录入数据的有效性
 * @param t
 */
function checkContentValid(t){

    $(t).parent().parent().attr("editable",true);

    var reg = /^[1-9]+(.[0-9]{2})?$/;
    var v= $(t).val();
    if(!reg.test(v)&&v!=""&&v!="."){

        t.value = t.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符
        t.value = t.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的
        t.value = t.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
        t.value = t.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数
        if(t.value.indexOf(".")< 0 && t.value !=""){//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额
            t.value= parseFloat(t.value);
        }

        $(t).val(t.value);
    }else if(v=="."){
        $(t).val("");
    }
}

/**
 * 检测年租金
 * @param t
 */
function checkEveryYearRent(t){

    $(t).parent().parent().attr("editable",true);

    var reg = /^[1-9]+(.[0-9]{2})?$/;
    var v= $(t).val();
    if(!reg.test(v)&&v!=""&&v!="."){

        t.value = t.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符
        t.value = t.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的
        t.value = t.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
        t.value = t.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数
        if(t.value.indexOf(".")< 0 && t.value !=""){//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额
            t.value= parseFloat(t.value);
        }

        $(t).val(t.value);
    }else if(v=="."){
        $(t).val("");
    }

    var index = t.id.split("_")[1];
    var firstRent = $("#firstYearRent_"+index).val()==""?0:$("#firstYearRent_"+index).val();
    var sendRent = $("#secondYearRent_"+index).val()==""?0:$("#secondYearRent_"+index).val();
    var thirdRent = $("#thirdYearRent_"+index).val()==""?0:$("#thirdYearRent_"+index).val();
    var fourRent = $("#fourthYearRent_"+index).val()==""?0:$("#fourthYearRent_"+index).val();
    var fifthRent = $("#fifthYearRent_"+index).val()==""?0:$("#fifthYearRent_"+index).val();

    $("#contractGrandTotal_"+index).val(parseFloat(firstRent)+parseFloat(sendRent)+parseFloat(thirdRent)+parseFloat(fourRent)+parseFloat(fifthRent));
}


/**
 * 检测物业期限
 * @param t
 */
function checkPropertyDeadLine(t){

    $(t).parent().parent().attr("editable",true);
    t.value = t.value.replace(/[^\d]/g,"");  //清除“数字”和“.”以外的字符
    var id = t.id.split("_");
    if(t.value!=""&&parseInt(t.value)<=12&&parseInt(t.value)>=0){

        var propertyFee = $("#propertyFee_"+id[1]).val();
        if(propertyFee==""){
            $("#propertyFeeYear_"+id[1]).val("");
        }else if(propertyFee!=""&&t.value!=""){
            var property_fee_year = parseFloat(t.value)*parseInt(propertyFee);
            $("#propertyFeeYear_"+id[1]).val(property_fee_year);
        }
    }else{
        $(t).val("");
        $("#propertyFeeYear_"+id[1]).val("");
    }

}



/**
 * 获取租金成本
 * **/
function getCostRent(){

    var index = layer.load(0,{
        shade: [0.2,'#333']
    });
    var storeNo=$("#storeNo_rent").val()==""?null:$("#storeNo_rent").val();
    var storeName=$("#storeName_rent").val()==""?null:$("#storeName_rent").val();
    var year = $("#year_rent").val();
    $("#rt1").html(year+"年月租金");
    $("#rt2").html(year+"年物业月费用");
    $("#rt3").html(year+"年每月费用");
    $("#rent_tb_1").find("tr:gt(0)").remove();
    $("#rent_tb_2").find("tr:gt(0)").remove();

    doManager('costRentManager','queryCostRent',[storeNo,storeName,year],function (data) {


        if(data.result){
            var costRent = JSON.parse(data.data).rent;


            var rent_td_sum = "";
            var uniform_charge_sum=0;

            for(var i=0;i<costRent.length;i++){

                var storeNo = costRent[i].store_no==null?"":costRent[i].store_no;
                var storeName = costRent[i].store_name==null?"":costRent[i].store_name;
                var addr = costRent[i].addr==null?"":costRent[i].addr;
                var rent_monthly = costRent[i].rent_monthly==null?"":costRent[i].rent_monthly;//月租
                var cost_monthly = costRent[i].cost_monthly==null?"":costRent[i].cost_monthly;//月成本
                var contract_grand_total = costRent[i].contract_grand_total==null?"":costRent[i].contract_grand_total;//合同总金额
                var structure_acreage = costRent[i].structure_acreage==null?"":costRent[i].structure_acreage;//建筑面积
                var lease_unit_price = costRent[i].lease_unit_price==null?"":costRent[i].lease_unit_price;//租赁单价
                var first_year_rent = costRent[i].first_year_rent==null?"":costRent[i].first_year_rent;
                var second_year_rent = costRent[i].second_year_rent==null?"":costRent[i].second_year_rent;
                var third_year_rent = costRent[i].third_year_rent==null?"":costRent[i].third_year_rent;
                var fourth_year_rent = costRent[i].fourth_year_rent==null?"":costRent[i].fourth_year_rent;
                var fifth_year_rent = costRent[i].fifth_year_rent==null?"":costRent[i].fifth_year_rent;
                var deposit = costRent[i].deposit==null?"":costRent[i].deposit;//押金
                var agency_fee = costRent[i].agency_fee==null?"":costRent[i].agency_fee;//中介费
                var property_fee_year = "";//年物业费
                if(costRent[i].property_fee!=null&&costRent[i].property_deadline!=null){
                    property_fee_year = parseFloat(costRent[i].property_fee)*parseFloat(costRent[i].property_deadline);
                }
                var property_fee = costRent[i].property_fee==null?"":costRent[i].property_fee;//月物业费
                var property_deadline = costRent[i].property_deadline==null?"":costRent[i].property_deadline;//租期
                var lease_start_date = costRent[i].lease_start_date==null?"":costRent[i].lease_start_date;//起租日不含免租期
                var lease_stop_date = costRent[i].lease_stop_date==null?"":costRent[i].lease_stop_date;//到租日
                var free_lease_start_date = costRent[i].free_lease_start_date==null?"":costRent[i].free_lease_start_date;//起租日含免租期

                $("#rent_tb_1").append("<tr><td style='text-align: center;background-color:#A9A9A9'>"+(i+1)+"</td><td style='text-align: center;background-color:#A9A9A9'>"+storeNo+"</td><td style='background-color:#A9A9A9'><p>"+storeName+"</p></td><td style='background-color:#A9A9A9' onmouseover='showWholeContent(this)'><p>"+addr+"</p></td></tr>");

                var labor_td = "<td><input type='text' readonly  style='background-color: #e8e8e8' value='"+rent_monthly+"'/></td><td><input type='text' readonly style='background-color: #e8e8e8'  value='"+property_fee+"'/></td><td><input type='text' readonly style='background-color: #e8e8e8'  value='"+cost_monthly+"'/></td>";

                for(var j=1;j<=12;j++){
                    labor_td=labor_td+"<td><input type='text'  readonly  style='background-color: #e8e8e8' value='"+cost_monthly+"'/></td>";
                }

                labor_td=labor_td+"<td><input type='text' readonly style='background-color: #e8e8e8' id='contractGrandTotal_"+i+"'  value='"+contract_grand_total+"'/></td><td><input type='text' onkeyup='checkContentValid(this)'  value='"+structure_acreage+"'/></td><td><input type='text'  onkeyup='checkContentValid(this)'  value='"+lease_unit_price+"'/></td><td><input type='text'  onkeyup='checkEveryYearRent(this)' id='firstYearRent_"+i+"' value='"+first_year_rent+"'/></td><td><input type='text' id='secondYearRent_"+i+"' onkeyup='checkEveryYearRent(this)'  value='"+second_year_rent+"'/></td><td><input type='text'  id='thirdYearRent_"+i+"' onkeyup='checkEveryYearRent(this)' value='"+third_year_rent+"'/></td><td><input type='text' id='fourthYearRent_"+i+"' onkeyup='checkEveryYearRent(this)' value='"+fourth_year_rent+"'/></td><td><input type='text'  id='fifthYearRent_"+i+"' onkeyup='checkEveryYearRent(this)'  value='"+fifth_year_rent+"'/></td><td><input type='text'  onkeyup='checkContentValid(this)' value='"+deposit+"'/></td><td><input type='text' onkeyup='checkContentValid(this)'   value='"+agency_fee+"'/></td><td><input type='text' id='propertyFeeYear_"+i+"'  readonly style='background-color: #e8e8e8' value='"+property_fee_year+"'/></td><td><input type='text' id='propertyDeadLine_"+i+"'  onkeyup='checkPropertyDeadLine(this)'  value='"+ property_deadline+"'/></td><td><input type='text' id='propertyFee_"+i+"' onkeyup='checkPropertyFee(this)'   value='"+property_fee+"'/></td><td><input type='text'   value='"+lease_start_date+"'/></td><td><input type='text'   value='"+lease_stop_date+"'/></td><td><input type='text'   value='"+free_lease_start_date+"'/></td>";

                $("#rent_tb_2").append("<tr id='"+storeNo+"' editable='false'>"+labor_td+"</tr>");


            }
            // for(var m=1;m<=12;m++){
            //     var emolument_sum =0;
            //     var uniform_amortize_sum = 0;
            //     var accommodation_sum = 0;
            //     var subtotal_sum = 0;
            //     var emolumentId = "emolument_"+m+"_";
            //     $("input[id^='"+emolumentId+"']").each(function () {
            //         emolument_sum+=parseFloat($(this).val()==""?0:$(this).val());
            //
            //     })
            //
            //     var uniformAmortizeId ="uniformAmortize_"+m+"_";
            //     $("input[id^='"+uniformAmortizeId+"']").each(function () {
            //         uniform_amortize_sum+=parseFloat($(this).val()==""?0:$(this).val());
            //
            //     })
            //
            //     var  accommodationId = "accommodation_"+m+"_";
            //     $("input[id^='"+accommodationId+"']").each(function () {
            //         accommodation_sum+=parseFloat($(this).val()==""?0:$(this).val());
            //
            //     })
            //
            //     var subtotalId = "subtotal_"+m+"_";
            //     $("input[id^='"+subtotalId+"']").each(function () {
            //         subtotal_sum+=parseFloat($(this).val()==""?0:$(this).val());
            //
            //     })
            //
            //     labor_td_sum=labor_td_sum+"<td><input type='text' readonly style='background-color: #e8e8e8' onkeyup='checkCostLabor()' id='emolumentSum"+m+"' value='"+emolument_sum.toFixed(2)+"'/></td><td><input type='text' readonly style='background-color: #e8e8e8' onkeyup='checkCostLabor()' id='uniformAmortizeSum"+m+"' value='"+uniform_amortize_sum.toFixed(2)+"'/></td><td><input type='text' readonly style='background-color: #e8e8e8' onkeyup='checkCostLabor()' id='accommodationSum"+m+"' value='"+accommodation_sum.toFixed(2)+"'></td><td><input readonly type='text' style='background-color: #e8e8e8' id='subtotalSum"+m+"' value='"+subtotal_sum.toFixed(2)+"'></td>";
            //
            // }
            //
            // $("#labor_tb_1").append("<tr><td  colspan='3' style='text-align: center;background-color:#A9A9A9'>合计</td></tr>");
            // $("#labor_tb_2").append("<tr>"+labor_td_sum+"<td><input style='background-color: #e8e8e8' readonly value='"+uniform_charge_sum.toFixed(2)+"'/></td></tr>");
            layer.close(index);



        }
    },false);

}
/**
 * 导出租金成本
 *
 * **/
function   exportCostRent(){
    var storeNo=$("#storeNo_labor").val()==""?null:$("#storeNo_labor").val();
    var storeName=$("#storeName_labor").val()==""?null:$("#storeName_labor").val();
    var year = $("#year_labor").val();

    doManager('costLaborManager','exportCostLabor',[storeNo,storeName,year,null],function (data) {
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
 * 保存租金成本
 *
 * **/
function saveCostRent(){
    var year = $("#year_labor").val();
    var store_cost_tr = $("#labor_tr_2").nextAll("tr[editable='true']");
    var costLaborArray = [];
    for(var i=0;i<store_cost_tr.length;i++){
        var storeNo= $(store_cost_tr[i]).attr("id");
        var storeName = $(store_cost_tr[i]).find("input:hidden").val();
        for(var j=1;j<=12;j++){
            var emolument = $(store_cost_tr[i]).find('input[id^="emolument_'+j+'_"]').val();
            var uniformAmortize = $(store_cost_tr[i]).find('input[id^="uniformAmortize_'+j+'_"]').val();
            var accommodation = $(store_cost_tr[i]).find('input[id^="accommodation_'+j+'_"]').val();
            var subtotal = $(store_cost_tr[i]).find('input[id^="subtotal_'+j+'_"]').val();
            var costLabor = {
                storeNo:storeNo,
                storeName:storeName,
                year:year,
                month:j,
                emolument:emolument,
                uniformAmortize:uniformAmortize,
                accommodation:accommodation,
                subtotal:subtotal
            };

            costLaborArray.push(costLabor);
        }
    }

    doManager('costLaborManager','saveCostLabor',[costLaborArray],function (data) {
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