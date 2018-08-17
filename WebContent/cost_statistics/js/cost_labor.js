
    $("input").on("keyup",function(i,obj){

        var reg = /^[1-9]+(.[0-9]{2})?$/;
        var v= $(this).val();
        if(!reg.test(v)){
            this.value = this.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符
            this.value = this.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的
            this.value = this.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
            this.value = this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数
            if(this.value.indexOf(".")< 0 && this.value !=""){//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额
                this.value= parseFloat(this.value);
            }

            $(this).val(this.value);
        }

    });












/**
 * 获取人工成本
 * **/
function getCostLabor(){

    var storeNo=null;
    var storeName=null;
    var year = 2018;
    var month = null;

    doManager('costStatisticsManager','queryCostLabor',[storeNo,storeName,year,month],function (data) {
        if(data.result){
            var costLabor = JSON.parse(data.data).labor;


            for(var i=0;i<costLabor.length;i++){
                $("#labor_tb_1").append("<tr><td style='text-align: center;background-color:#A9A9A9'>"+(i+1)+"</td><td style='text-align: center;background-color:#A9A9A9'>"+costLabor[i].storeNo+"</td><td style='background-color:#A9A9A9'>"+costLabor[i].storeName+"</td></tr>");
                var labor_td="";
                for(var j=1;j<=12;j++){
                    var emolument =  costLabor[i]["emolument"+j]==null?"":costLabor[i]["emolument"+j];
                    var uniform_amortize = costLabor[i]["uniform_amortize"+j]==null?"":costLabor[i]["uniform_amortize"+j];
                    var accommodation = costLabor[i]["accommodation"+j]==null?"":costLabor[i]["accommodation"+j];
                    var subtotal = costLabor[i]["subtotal"+j]==null?"":costLabor[i]["subtotal"+j];
                    labor_td=labor_td+"<td><input type='text' value='"+emolument+"'/></td><td><input type='text' value='"+uniform_amortize+"'/></td><td><input type='text' value='"+accommodation+"'></td><td><input readonly type='text' value='"+subtotal+"'></td>";
                }

                $("#labor_tb_2").append("<tr>"+labor_td+"<td><input type='text' value='"+costLabor[i].uniform_charge+"'></td></tr>");
            }


        }
    },false);

}
/**
 * 导出人工成本
 *
 * **/
function   exportCostLabor(){
    var storeNo=$("#storeNo_labor").val()==""?null:$("#storeNo_labor").val();
    var storeName=$("#storeName_labor").val()==""?null:$("#storeName_labor").val();
    var year = $("#year_labor").val();

    doManager('costStatisticsManager','exportCostLabor',[storeNo,storeName,year,null],function (data) {
        if(data.result){
            var result= JSON.parse(data.data);
            if(result.status=='success'){
                // window.location.href=result.data;
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