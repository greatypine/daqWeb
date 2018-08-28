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


function showWholeContent(t){
    var id = $(t).attr("id");
    var content = $(t).html();
    layer.tips(content,"#"+id, {
        tips: [1, '#0ACC4C'],
        time: 1000
    });
}

/**
 * 检测租金成本数据并记录编辑的门店
 *
 * **/
var editStore = new Set();
function checkcostRenovation(t){
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
    if(t.value!=""){
        if(propertyDeadLine!=""){
            var property_fee_year = parseFloat(t.value)*parseInt(propertyDeadLine);
            $("#propertyFeeYear_"+id[1]).val(property_fee_year.toFixed(2));
        }else{
            $("#propertyFeeYear_"+id[1]).val("");
        }

        $("#curYearPropertyFee_"+id[1]).val(t.value);
        var contractGrandTotal = $("#contractGrandTotal_"+id[1]).val()==""?0:$("#contractGrandTotal_"+id[1]).val();
        var rentMonthly = (parseFloat(contractGrandTotal)/60).toFixed(2);
        $("#rentMonthly_"+id[1]).val(rentMonthly);
        $("input[id='costMonthly_"+id[1]+"']").each(function () {
            $(this).val(parseFloat(rentMonthly)+parseFloat(t.value));
        })
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
    var contractGrandTotal = $("#contractGrandTotal_"+index).val();
    var rentMonthly = (parseFloat(contractGrandTotal)/60).toFixed(2);
    $("#rentMonthly_"+index).val(rentMonthly);
    var curYearPropertyFee = $("#curYearPropertyFee_"+index).val()==""?0:$("#curYearPropertyFee_"+index).val();
    $("input[id='costMonthly_"+index+"']").each(function () {
        $(this).val(parseFloat(rentMonthly)+parseFloat(curYearPropertyFee));
    })
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
 * 获取装修摊销
 * **/
function getCostRenovation(){

    var index = layer.load(0,{
        shade: [0.2,'#333']
    });
    var storeNo=$("#storeNo_rent").val()==""?null:$("#storeNo_rent").val();
    var storeName=$("#storeName_rent").val()==""?null:$("#storeName_rent").val();
    var year = $("#year_rent").val();

    $("#renovation_tb_1").find("tr:gt(0)").remove();
    $("#renovation_tb_2").find("tr:gt(0)").remove();

    doManager('costRenovationManager','queryCostRenovation',[storeNo,storeName,year],function (data) {


        if(data.result){
            var costRenovation = JSON.parse(data.data).rent;


            var rent_td_sum = "";
            var uniform_charge_sum=0;

            for(var i=0;i<costRenovation.length;i++){
                var storeNo = costRenovation[i].store_no==null?"":costRenovation[i].store_no;
                var storeName = costRenovation[i].store_name==null?"":costRenovation[i].store_name;
                var decoration_company = costRenovation[i].decoration_company==null?"":costRenovation[i].decoration_company;//装修公司
                var structure_acreage = costRenovation[i].structure_acreage==null?"":costRenovation[i].structure_acreage;//建筑面积
                var renovation_unit_price = costRenovation[i].renovation_unit_price==null?"":costRenovation[i].renovation_unit_price;//装修单价
                var business_screen = costRenovation[i].business_screen==null?"":costRenovation[i].business_screen;//商业展屏
                var furniture = costRenovation[i].furniture==null?"":costRenovation[i].furniture;//家具
                var light_box = costRenovation[i].light_box==null?"":costRenovation[i].light_box;//灯箱
                var process_manage = costRenovation[i].process_manage==null?"":costRenovation[i].process_manage;//过程管理
                var process_manage_surcharge = costRenovation[i].process_manage_surcharge==null?"":costRenovation[i].process_manage_surcharge;//过程管理费
                var air_conditioner = costRenovation[i].air_conditioner==null?"":costRenovation[i].air_conditioner;//空调设备
                var air_conditioner_surcharge = costRenovation[i].air_conditioner_surcharge==null?"":costRenovation[i].air_conditioner_surcharge;//空调设备费
                var design = costRenovation[i].design==null?"":costRenovation[i].design;//设计
                var deposit = costRenovation[i].total==null?"":costRenovation[i].total;//押金
                var agency_fee = costRenovation[i].amortize_month==null?"":costRenovation[i].amortize_month;//摊销月
                var amortize_money = costRenovation[i].amortize_money==null?"":costRenovation[i].amortize_money;//摊销金额
                var completed_date = costRenovation[i].completed_date==null?"":costRenovation[i].completed_date;//竣工日期
                var contract_date = costRenovation[i].contract_date==null?"":costRenovation[i].contract_date;//合同日期


                $("#renovation_tb_1").append("<tr><td style='text-align: center;background-color:#A9A9A9'>"+(i+1)+"</td><td style='text-align: center;background-color:#A9A9A9'>"+storeNo+"</td><td style='background-color:#A9A9A9'><p>"+storeName+"</p></td></tr>");

                var renovation_td = "<td><input type='text'   style='background-color: #e8e8e8' id='decorationCompany_"+i+"' value='"+decoration_company+"'/></td>" +
                                    "<td><input type='text'   style='background-color: #e8e8e8' id='structureAcreage_"+i+"'  value='"+structure_acreage+"'/></td>" +
                                    "<td><input type='text'   style='background-color: #e8e8e8' id='renovationUnitPrice_"+i+"'    value='"+cost_monthly+"'/></td>";

                for(var j=1;j<=12;j++){
                    labor_td=labor_td+"<td><input type='text' id='costMonthly_"+i+"'  readonly  style='background-color: #e8e8e8' value='"+cost_monthly+"'/></td>";
                }

                labor_td=labor_td+"<td><input type='text' readonly style='background-color: #e8e8e8' id='contractGrandTotal_"+i+"'  value='"+contract_grand_total+"'/></td><td><input type='text' onkeyup='checkContentValid(this)' id='structureAcreage_"+i+"'  value='"+structure_acreage+"'/></td><td><input type='text' id='leaseUnitPrice_"+i+"' onkeyup='checkContentValid(this)'  value='"+lease_unit_price+"'/></td><td><input type='text'  onkeyup='checkEveryYearRent(this)' id='firstYearRent_"+i+"' value='"+first_year_rent+"'/></td><td><input type='text' id='secondYearRent_"+i+"' onkeyup='checkEveryYearRent(this)'  value='"+second_year_rent+"'/></td><td><input type='text'  id='thirdYearRent_"+i+"' onkeyup='checkEveryYearRent(this)' value='"+third_year_rent+"'/></td><td><input type='text' id='fourthYearRent_"+i+"' onkeyup='checkEveryYearRent(this)' value='"+fourth_year_rent+"'/></td><td><input type='text'  id='fifthYearRent_"+i+"' onkeyup='checkEveryYearRent(this)'  value='"+fifth_year_rent+"'/></td><td><input type='text'  id='deposit_"+i+"' onkeyup='checkContentValid(this)' value='"+deposit+"'/></td><td><input type='text' onkeyup='checkContentValid(this)' id='agencyFee_"+i+"'  value='"+agency_fee+"'/></td><td><input type='text' id='propertyFeeYear_"+i+"'  readonly style='background-color: #e8e8e8' value='"+property_fee_year+"'/></td><td><input type='text' id='propertyDeadLine_"+i+"'  onkeyup='checkPropertyDeadLine(this)'  value='"+ property_deadline+"'/></td><td><input type='text' id='propertyFee_"+i+"' onkeyup='checkPropertyFee(this)'   value='"+property_fee+"'/></td><td><input type='text' readonly id='lease_start_date_"+i+"'  value='"+lease_start_date+"'/></td><td><input type='text' readonly id='lease_stop_date_"+i+"'  value='"+lease_stop_date+"'/></td><td><input type='text'  id='free_lease_start_date_"+i+"' readonly value='"+free_lease_start_date+"'/></td>";

                $("#rent_tb_2").append("<tr id='"+storeNo+"' editable='false'>"+labor_td+"<input type='hidden'  id='storeName' value='"+storeName+"'/><input type='hidden' id='addr' value='"+addr+"'/></tr>");

                laydate.render({//月份控件
                    elem:"#lease_start_date_"+i,
                    value:lease_start_date==""?"":new Date(lease_start_date),
                    format: 'yyyy/MM/dd',
                    btns: ['confirm','now',"clear"],
                    theme: '#428bca',
                    done: function(value, date, endDate){
                        console.log(value)
                        $($(this)[0].elem.selector).parent().parent().attr("editable",true);
                    }
                });

                laydate.render({//月份控件
                    elem:"#lease_stop_date_"+i,
                    value:lease_stop_date==""?"":new Date(lease_stop_date),
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

                laydate.render({//月份控件
                    elem:"#free_lease_start_date_"+i,
                    value:free_lease_start_date==""?"":new Date(free_lease_start_date),
                    format: 'yyyy/MM/dd',
                    btns: ['confirm','now',"clear"],
                    theme: '#428bca',
                    change: function(value, date, endDate){
                        $($(this)[0].elem.selector).parent().parent().attr("editable",true);
                    }
                });

            }

            layer.close(index);




        }
    },false);

}
/**
 * 导出租金成本
 *
 * **/
function   exportcostRenovation(){
    var storeNo=$("#storeNo_rent").val()==""?null:$("#storeNo_rent").val();
    var storeName=$("#storeName_rent").val()==""?null:$("#storeName_rent").val();
    var year = $("#year_rent").val();

    doManager('costRenovationManager','exportcostRenovation',[storeNo,storeName,year],function (data) {
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
function savecostRenovation(){

    var store_cost_tr = $("#rent_tr_2").nextAll("tr[editable='true']");
    var costRenovationArray = [];
    var year= $("#year_rent").val();
    for(var i=0;i<store_cost_tr.length;i++){
        var storeNo= $(store_cost_tr[i]).attr("id");
        var storeName = $(store_cost_tr[i]).find("input[id='storeName']").val();
        var addr = $(store_cost_tr[i]).find("input[id='addr']").val();
        var contractGrandTotal = $(store_cost_tr[i]).find('input[id^="contractGrandTotal_"]').val();
        var rent_monthly = $(store_cost_tr[i]).find('input[id^="rentMonthly_"]').val();//月租
        var structure_acreage = $(store_cost_tr[i]).find('input[id^="structureAcreage_"]').val();//建筑面积
        var lease_unit_price = $(store_cost_tr[i]).find('input[id^="leaseUnitPrice_"]').val();//租赁单价
        var first_year_rent = $(store_cost_tr[i]).find('input[id^="firstYearRent_"]').val();
        var second_year_rent = $(store_cost_tr[i]).find('input[id^="secondYearRent_"]').val();
        var third_year_rent = $(store_cost_tr[i]).find('input[id^="thirdYearRent_"]').val();
        var fourth_year_rent = $(store_cost_tr[i]).find('input[id^="fourthYearRent_"]').val();
        var fifth_year_rent = $(store_cost_tr[i]).find('input[id^="fifthYearRent_"]').val();
        var deposit = $(store_cost_tr[i]).find('input[id^="deposit_"]').val();//押金
        var agency_fee = $(store_cost_tr[i]).find('input[id^="agencyFee_"]').val();//中介费
        var property_fee = $(store_cost_tr[i]).find('input[id^="propertyFee_"]').val();//月物业费
        var property_deadline = $(store_cost_tr[i]).find('input[id^="propertyDeadLine_"]').val();//租期
        var lease_start_date = $(store_cost_tr[i]).find('input[id^="lease_start_date_"]').val();//起租日不含免租期
        var lease_stop_date = $(store_cost_tr[i]).find('input[id^="lease_stop_date_"]').val();//到租日
        var free_lease_start_date = $(store_cost_tr[i]).find('input[id^="free_lease_start_date_"]').val();//起租日含免租期

        var costRenovation = {
            year:year,
            storeNo:storeNo,
            storeName:storeName,
            addr:addr,
            contractGrandTotal:contractGrandTotal,
            firstYearRent:fifth_year_rent,
            secondYearRent:second_year_rent,
            thirtYearRent:third_year_rent,
            fourthYearRent:fourth_year_rent,
            fifthYearRent:fifth_year_rent,
            structureAcreage:structure_acreage,
            leaseUnitPrice:lease_unit_price,
            deposit:deposit,
            agencyFee:agency_fee,
            propertyFee:property_fee,
            propertyDeadline:property_deadline,
            freeLeaseStartDate:free_lease_start_date,
            leaseStartDate:lease_start_date,
            leaseStopDate:lease_stop_date

        }
        costRenovationArray.push(costRenovation);

    }

    doManager('costRenovationManager','savecostRenovation',[costRenovationArray],function (data) {
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