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


var regex_zb = new RegExp("^(ZB|zb)\w*");//总部角色
var regex_cs = new RegExp("^(CS|cs)\w*");//城市级别
/**
 * 查询人工成本城市
 * @type {null}
 */
var lst_select_rent_city=null;

function getRentCity(){


        if(regex_zb.test(userGroupCode)){

            doManager('dynamicManager','selectAllCity',null,function(data){
                if(data.result){

                    lst_select_rent_city = JSON.parse(data.data);

                    var option = "";
                    for( var i=0;i<lst_select_rent_city.length;i++){
                        option=option+"<option value='"+lst_select_rent_city[i].id+"'>"+lst_select_rent_city[i].cityname+"</option>";
                    }
                    $("#city_id_rent").append(option);
                    if(flag=="search"){
                        $("#city_id_rent").val(cur_city_id);
                    }
                }
            });


        }else if(regex_cs.test(userGroupCode)||userGroupCode=="GLY"){

            doManager("StoreManager", "getCityNameOfCSZJ",[userId,null],
                function(data, textStatus, XMLHttpRequest) {
                    if (data.result) {
                        lst_select_rent_city = JSON.parse(data.data).citylist;

                        var option = "";
                        for( var i=0;i<lst_select_rent_city.length;i++){
                            option=option+"<option value='"+lst_select_rent_city[i].ctid+"'>"+lst_select_rent_city[i].name+"</option>";
                        }
                        $("#city_id_rent").append(option);
                        if(flag=="search"){
                            $("#city_id_rent").val(cur_city_id);
                        }
                    }
                });
        }



}

// /**
//  * 选择城市
//  * @param t
//  */
// function selectRentCity(t){
//     var temp_city = document.getElementById("city_name_rent").value.replace(/(\s*)|(\s*)/g,'').replace(/[ ]/g,'');
//     $("#city_id_rent").val(rentCityIdMap[temp_city]);
// }



/**-----------------------------------搜索门店------------------------------------------------------**/

/**
 * 查询门店
 * @type {null}
 */
var lst_select_rent_store=null;
var rentStoreNameArray=new Array();
var rentStoreIdMap = {};
function getRentStore(t){

    rentStoreNameArray=new Array();
    $("#rent_store").empty();
    var str_name = $(t).val();
    if(!(event.keyCode >= 37 && event.keyCode <= 40) && event.keyCode != 13){
        $("#store_id_rent").val("");

        if(str_name == null || str_name == "" || str_name.indexOf('\'') > -1){
            return;
        }
        var city_id  = $("#city_id_rent").val()==""?null:$("#city_id_rent").val();

        var target=0;
        if(regex_zb.test(userGroupCode)){//总部
            target=0;
        }else if(regex_cs.test(userGroupCode)||userGroupCode=="GLY"){//城市
            target=1;
        }

        doManager('dynamicManager','getStoreByCity',[target,userId,city_id,str_name],function(data){
            if(data.result){

                lst_select_rent_store = JSON.parse(data.data).storelist;

                for(var i=0;i<lst_select_rent_store.length;i++){
                    rentStoreNameArray.push(lst_select_rent_store[i].name);
                    rentStoreIdMap[lst_select_rent_store[i].name] = lst_select_rent_store[i].storeno;
                }
                var autoComplete = new AutoComplete("store_name_rent","rent_store",rentStoreNameArray);
                autoComplete.start(event);
                $("#rent_store").attr("style","width: 150px;z-index: 99999;left: 26.3%;top: 23.1%;");
            }else{

            }
        });
    }

}

/**
 * 选择门店
 * @param t
 */
function selectRentStore(t){
    var temp_store = document.getElementById("store_name_rent").value.replace(/(\s*)|(\s*)/g,'').replace(/[ ]/g,'');
    $("#store_id_rent").val(rentStoreIdMap[temp_store]);
}

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
    if(t.value!=""){
        if(propertyDeadLine!=""){
            var property_fee_month = parseFloat(t.value)/parseInt(propertyDeadLine);
            $("#propertyFeeMonth_"+id[1]).val(property_fee_month.toFixed(2));
        }else{
            $("#propertyFeeMonth_"+id[1]).val("");
            $("#costMonth_"+id[1]).val("");
        }


        var rentMonthly = $("#rentMonthly_"+id[1]).val();
        var propertyFeeMonth = $("#propertyFeeMonth_"+id[1]).val();
        if(rentMonthly!=""&&propertyFeeMonth!=""){
            $("#costMonth_"+id[1]).val((parseFloat(rentMonthly)+parseFloat(propertyFeeMonth)).toFixed(2));
        }else{
            $("#costMonth_"+id[1]).val("");
        }
    }else if(t.value==""){
        $("#propertyFeeYear_"+id[1]).val("");
        $("#costMonth_"+id[1]).val("");
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
 * 检测物业期限
 * @param t
 */
function checkPropertyDeadLine(t){

    $(t).parent().parent().attr("editable",true);
    t.value = t.value.replace(/[^\d]/g,"");  //清除“数字”和“.”以外的字符
    var id = t.id.split("_");
    if(t.value!=""&&parseInt(t.value)<=12&&parseInt(t.value)>=0){

        var propertyFee = $("#propertyFeeYear_"+id[1]).val();
        if(propertyFee==""){
            $("#propertyFeeMonth_"+id[1]).val("");
        }else if(propertyFee!=""&&t.value!=""){
            var property_fee_month = parseFloat(propertyFee)/parseInt(t.value);
            $("#propertyFeeMonth_"+id[1]).val(property_fee_month.toFixed(2));
            var rentMonthly = $("#rentMonthly_"+id[1]).val();
            if(rentMonthly!=""){
                $("#costMonth_"+id[1]).val((parseFloat(rentMonthly)+parseFloat(property_fee_month)).toFixed(2));
            }else{
                $("#costMonth_"+id[1]).val("");
            }
        }
    }else{
        $(t).val("");
        $("#propertyFeeMonth_"+id[1]).val("");
        $("#costMonth_"+id[1]).val("");
    }

}



/**
 * 获取租金成本
 * **/
function getCostRent(){


    var store_cost_tr = $("#rent_tr_2").nextAll("tr[editable='true']");
    if(store_cost_tr.length>0){//有数据修改
        $$.showConfirm_cost("提示","是否需要保存改变的数据？",function () {
            saveCostRent();

        },function(){

            $("#labor_tr_2").nextAll("tr[editable='true']").each(function(){
                $(this).attr("editable","false");
            });
            searchCostRent();
        });

    }else if(store_cost_tr.length==0){//没有数据修改
        searchCostRent();
    }



}

/**
 * 搜索租金成本
 * @param
 */
function searchCostRent(){

    var index = layer.load(0,{
        shade: [0.2,'#333']
    });

    var showYear = $("#year_property").val();

    $("#propertyFeeYear_title").html(showYear+"年");


    var cityId="";
    if(flag=="search"){
        cityId = cur_city_id;
    }else{
        cityId = $("#city_id_rent").val();
    }

    var storeId = $("#store_id_rent").val();
    var storeName = $("#store_name_rent").val();

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
        year:showYear,
        userId:userId,
        role:role

    }


    $("#property_tb_2").find("tr:gt(0)").remove();

    doManager('costRentManager','queryCostRent',costDto,function (data) {


        if(data.result){
            var costRent = JSON.parse(data.data).rent;
            for(var i=0;i<costRent.length;i++){

                var cityName = costRent[i].city_name==null?"":costRent[i].city_name;
                var storeNo = costRent[i].store_no==null?"":costRent[i].store_no;
                var storeName = costRent[i].store_name==null?"":costRent[i].store_name;
                var property_fee_year = costRent[i].property_fee_year==null?"":costRent[i].property_fee_year;//年物业费
                var property_fee_month = costRent[i].property_fee_month==null?"":costRent[i].property_fee_month;//月物业费
                var property_deadline = costRent[i].property_deadline==null?"":costRent[i].property_deadline;//租期
                var rent_td =
                    "<td style='text-align: center;background-color:#A9A9A9'>"+(i+1)+"</td><td style='text-align: center;background-color:#A9A9A9'>"+storeNo+"</td><td style='background-color:#A9A9A9'><p>"+storeName+"</p></td>"+
                    "<td><input type='text' id='propertyFeeYear_"+i+"' onkeyup='checkPropertyFee(this)'  style='width: 100%' value='"+property_fee_year+"'/></td>" +
                    "<td><input type='text' id='propertyDeadLine_"+i+"' onkeyup='checkPropertyDeadLine(this)' style='width: 100%'   value='"+property_deadline+"'/></td>" +
                    "<td><input type='text' id='propertyFeeMonth_"+i+"'  readonly style='background-color: #e8e8e8;width: 100%'  value='"+property_fee_month+"'/></td>";
                $("#property_tb_2").append("<tr id='"+storeNo+"' editable='false'>"+rent_td+"<input type='hidden'  id='storeName' value='"+storeName+"'/><input type='hidden' id='cityName' value='"+cityName+"'/></tr>");
            }

            layer.close(index);
        }
    });

}
/**
 * 导出租金成本
 *
 * **/
function   exportCostRent(){
    var showYear = $("#year_property").val();
    var cityId = $("#city_id_rent").val();


    var storeId = $("#store_id_rent").val();
    var storeName = $("#store_name_rent").val();

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
        year:showYear,
        userId:userId,
        role:role

    }

    doManager('costRentManager','exportCostRent',costDto,function (data) {
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


function   exportCostProperty(){
    var showYear = $("#year_property").val();
    var cityId = $("#city_id_rent").val();


    var storeId = $("#store_id_rent").val();
    var storeName = $("#store_name_rent").val();

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
        year:showYear,
        userId:userId,
        role:role

    }

    doManager('costRentManager','exportCostProperty',costDto,function (data) {
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
 * 保存租金成本
 *
 * **/
function saveCostRent(){

    var saveResult = "";
    var store_cost_tr = $("#property_tr_2").nextAll("tr[editable='true']");
    var costRentArray = [];
    var rdate= $("#year_property").val();

    for(var i=0;i<store_cost_tr.length;i++){
        var storeNo= $(store_cost_tr[i]).attr("id");
        var cityName = $(store_cost_tr[i]).find("input[id='cityName']").val();
        var storeName = $(store_cost_tr[i]).find("input[id='storeName']").val();
        var propertyFeeYear = $(store_cost_tr[i]).find('input[id^="propertyFeeYear_"]').val();//月物业费
        var property_deadline = $(store_cost_tr[i]).find('input[id^="propertyDeadLine_"]').val();//租期
        var propertyFeeMonth = $(store_cost_tr[i]).find('input[id^="propertyFeeMonth_"]').val();//月物业费


        var costRent = {
            cityName:cityName,
            year:rdate,
            storeNo:storeNo,
            storeName:storeName,
            propertyFeeYear:propertyFeeYear,
            propertyDeadline:property_deadline,
            propertyFeeMonth:propertyFeeMonth
        }
        costRentArray.push(costRent);

    }

    doManager('costRentManager','saveCostRent',[costRentArray],function (data) {
        if(data.result){
            var result= JSON.parse(data.data);

            if(result.status=='success'){
                // $$.showMessage('提示',"保存成功！");
                $("#rent_tr_2").nextAll("tr[editable='true']").each(function(){
                    $(this).attr("editable",false);
                })
                // return;
                saveResult="success";
            }else if(result.status=="fail"){

                // $$.showMessage('提示',"保存失败！");
                // return;
                saveResult="fail";
            }else{

                // $$.showMessage('提示',"请稍后重新请求！");
                // return;
                saveResult="fail";
            }
        }else{
            // $.showMessage('提示',"请稍后重新请求！");
            saveResult="fail";
        }

    },false);

    return saveResult;

}