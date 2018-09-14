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

/**
 * 检测人工成本数据并记录编辑的门店
 *
 * **/
var editStore = new Set();
function checkCostLabor(t){
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
 * 获取人工成本
 * **/
function getCostLabor(){

    var index = layer.load(0,{
        shade: [0.2,'#333']
    });
    var storeNo=$("#storeNo_labor").val()==""?null:$("#storeNo_labor").val();
    var storeName=$("#storeName_labor").val()==""?null:$("#storeName_labor").val();
    var year = $("#year_labor").val();
    $("#labor_tb_1").find("tr:gt(1)").remove();
    $("#labor_tb_2").find("tr:gt(1)").remove();
    doManager('costLaborManager','queryCostLabor',[storeNo,storeName,year,null],function (data) {


        if(data.result){
            var costLabor = JSON.parse(data.data).labor;


            var labor_td_sum = "";
            var uniform_charge_sum=0;

            for(var i=0;i<costLabor.length;i++){
                $("#labor_tb_1").append("<tr><td style='text-align: center;background-color:#A9A9A9'>"+(i+1)+"</td><td style='text-align: center;background-color:#A9A9A9'>"+costLabor[i].storeNo+"</td><td style='background-color:#A9A9A9'>"+costLabor[i].storeName+"</td></tr>");
                var labor_td = "";

                for(var j=1;j<=12;j++){

                    var emolument =  costLabor[i]["emolument"+j]==null?"":costLabor[i]["emolument"+j];
                    var uniform_amortize = costLabor[i]["uniform_amortize"+j]==null?"":costLabor[i]["uniform_amortize"+j];
                    var accommodation = costLabor[i]["accommodation"+j]==null?"":costLabor[i]["accommodation"+j];
                    var subtotal = costLabor[i]["subtotal"+j]==null?"":costLabor[i]["subtotal"+j];
                    var rc_tag ="_"+j+"_"+i;
                    labor_td=labor_td+"<td><input type='text' onkeyup='checkCostLabor(this)' id='emolument"+rc_tag+"' value='"+emolument+"'/></td><td><input type='text' onkeyup='checkCostLabor(this)' id='uniformAmortize"+rc_tag+"' value='"+uniform_amortize+"'/></td><td><input type='text' onkeyup='checkCostLabor(this)' id='accommodation"+rc_tag+"' value='"+accommodation+"'></td><td><input readonly type='text' onkeyup='showTip(this)' style='background-color: #e8e8e8' id='subtotal"+rc_tag+"' value='"+subtotal+"'></td>";



                }
                var uniform_charge = costLabor[i].uniform_charge==null?"":costLabor[i].uniform_charge;
                var storeNo=costLabor[i].storeNo;
                var storeName = costLabor[i].storeName;

                $("#labor_tb_2").append("<tr id='"+storeNo+"' editable='false'>"+labor_td+"<td><input type='text' value='"+uniform_charge+"'></td><input type='hidden' value='"+storeName+"' name='storeName'/></tr>");
                uniform_charge_sum = uniform_charge_sum+parseFloat(costLabor[i].uniform_charge==null?0:costLabor[i].uniform_charge);

            }
            for(var m=1;m<=12;m++){
                var emolument_sum =0;
                var uniform_amortize_sum = 0;
                var accommodation_sum = 0;
                var subtotal_sum = 0;
                var emolumentId = "emolument_"+m+"_";
                $("input[id^='"+emolumentId+"']").each(function () {
                    emolument_sum+=parseFloat($(this).val()==""?0:$(this).val());

                })

                var uniformAmortizeId ="uniformAmortize_"+m+"_";
                $("input[id^='"+uniformAmortizeId+"']").each(function () {
                    uniform_amortize_sum+=parseFloat($(this).val()==""?0:$(this).val());

                })

                var  accommodationId = "accommodation_"+m+"_";
                $("input[id^='"+accommodationId+"']").each(function () {
                    accommodation_sum+=parseFloat($(this).val()==""?0:$(this).val());

                })

                var subtotalId = "subtotal_"+m+"_";
                $("input[id^='"+subtotalId+"']").each(function () {
                    subtotal_sum+=parseFloat($(this).val()==""?0:$(this).val());

                })

                labor_td_sum=labor_td_sum+"<td><input type='text' readonly style='background-color: #e8e8e8' onkeyup='checkCostLabor()' id='emolumentSum"+m+"' value='"+emolument_sum.toFixed(2)+"'/></td><td><input type='text' readonly style='background-color: #e8e8e8' onkeyup='checkCostLabor()' id='uniformAmortizeSum"+m+"' value='"+uniform_amortize_sum.toFixed(2)+"'/></td><td><input type='text' readonly style='background-color: #e8e8e8' onkeyup='checkCostLabor()' id='accommodationSum"+m+"' value='"+accommodation_sum.toFixed(2)+"'></td><td><input readonly type='text' style='background-color: #e8e8e8' id='subtotalSum"+m+"' value='"+subtotal_sum.toFixed(2)+"'></td>";

            }

            $("#labor_tb_1").append("<tr><td  colspan='3' style='text-align: center;background-color:#A9A9A9'>合计</td></tr>");
            $("#labor_tb_2").append("<tr>"+labor_td_sum+"<td><input style='background-color: #e8e8e8' readonly value='"+uniform_charge_sum.toFixed(2)+"'/></td></tr>");
            layer.close(index);



        }
    });

}
/**
 * 导出人工成本
 *
 * **/
function   exportCostLabor(){
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
 * 保存人工成本
 *
 * **/
function saveCostLabor(){
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