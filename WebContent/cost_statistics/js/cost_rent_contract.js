var regex_zb = new RegExp("^(ZB|zb)\w*");//总部角色
var regex_cs = new RegExp("^(CS|cs)\w*");//城市级别
/**
 * 查询人工成本城市
 * @type {null}
 */
var lst_select_rentContract_city=null;

function getRentContractCity(t){



        if(regex_zb.test(userGroupCode)){

            doManager('dynamicManager','selectAllCity',null,function(data){
                if(data.result){

                    lst_select_rentContract_city = JSON.parse(data.data);

                    var option = "";
                    for( var i=0;i<lst_select_rentContract_city.length;i++){
                        option=option+"<option value='"+lst_select_rentContract_city[i].id+"'>"+lst_select_rentContract_city[i].cityname+"</option>";
                    }
                    $("#city_id_rentContract").append(option);
                    if(flag=="search"){
                        $("#city_id_rentContract").val(cur_city_id);
                    }
                }
            });


        }else if(regex_cs.test(userGroupCode)||userGroupCode=="GLY"){

            doManager("StoreManager", "getCityNameOfCSZJ",[userId,null],
                function(data, textStatus, XMLHttpRequest) {
                    if (data.result) {
                        lst_select_rentContract_city = JSON.parse(data.data);

                        var option = "";
                        for( var i=0;i<lst_select_rentContract_city.length;i++){
                            option=option+"<option value='"+lst_select_rentContract_city[i].ctid+"'>"+lst_select_rentContract_city[i].name+"</option>";
                        }
                        $("#city_id_rentContract").append(option);
                        if(flag=="search"){
                            $("#city_id_rentContract").val(cur_city_id);
                        }
                    }
                });
        }



}

// /**
//  * 选择城市
//  * @param t
//  */
// function selectRentContractCity(t){
//     var temp_city = document.getElementById("city_name_rentContract").value.replace(/(\s*)|(\s*)/g,'').replace(/[ ]/g,'');
//     $("#city_id_rentContract").val(rentContractCityIdMap[temp_city]);
// }



/**-----------------------------------搜索门店------------------------------------------------------**/

/**
 * 查询门店
 * @type {null}
 */
var lst_select_rentContract_store=null;
var rentContractStoreNameArray=new Array();
var rentContractStoreIdMap = {};
function getRentContractStore(t){

    rentContractStoreNameArray=new Array();
    $("#rentContract_store").empty();
    var str_name = $(t).val();
    if(!(event.keyCode >= 37 && event.keyCode <= 40) && event.keyCode != 13){
        $("#store_id_rentContract").val("");

        if(str_name == null || str_name == "" || str_name.indexOf('\'') > -1){
            return;
        }
        var city_id  = $("#city_id_rentContract").val()==""?null:$("#city_id_rentContract").val();
        // var city_name =  $("#city_name_rentContract").val();
        // if(city_id==null&&city_name!=""){
        //     city_id=-10000;
        // }
        var target=0;
        if(regex_zb.test(userGroupCode)){//总部
            target=0;
        }else if(regex_cs.test(userGroupCode)||userGroupCode=="GLY"){//城市
            target=1;
        }

        doManager('dynamicManager','getStoreByCity',[target,userId,city_id,str_name],function(data){
            if(data.result){

                lst_select_rentContract_store = JSON.parse(data.data).storelist;

                for(var i=0;i<lst_select_rentContract_store.length;i++){
                    rentContractStoreNameArray.push(lst_select_rentContract_store[i].name);
                    rentContractStoreIdMap[lst_select_rentContract_store[i].name] = lst_select_rentContract_store[i].storeno;
                }
                var autoComplete = new AutoComplete("store_name_rentContract","rentContract_store",rentContractStoreNameArray);
                autoComplete.start(event);
                $("#rentContract_store").attr("style","width: 150px;z-index: 99999;left: 26.3%;top: 23.1%;");
            }else{

            }
        });
    }

}

/**
 * 选择门店
 * @param t
 */
function selectRentContractStore(t){
    var temp_store = document.getElementById("store_name_rentContract").value.replace(/(\s*)|(\s*)/g,'').replace(/[ ]/g,'');
    $("#store_id_rentContract").val(rentContractStoreIdMap[temp_store]);
}


/**
 * 检测年租金
 * @param t
 */
function checkEveryYearRent(t){

    checkFloatDataValid(t);
    $(t).parent().parent().attr("editable",true);
    var index = $(t).attr("id").split("_")[1];
    var firstRent = $("#firstYearRent_"+index).val();
    var sendRent = $("#secondYearRent_"+index).val();
    var thirdRent = $("#thirdYearRent_"+index).val();
    var fourRent = $("#fourthYearRent_"+index).val();
    var fifthRent = $("#fifthYearRent_"+index).val();
    var deposit = $("#deposit_"+index).val();
    var agencyFee = $("#agencyFee_"+index).val();

    if(firstRent==""&&sendRent==""&&thirdRent==""&&fourRent==""&&fifthRent==""&&deposit==""&&agencyFee==""){
        $("#leaseUnitPrice_"+index).val("");
        $("#contractGrandTotal_"+index).val("");
        $("#rentalMonth_"+index).val("");
    }else{
        firstRent = firstRent==""?0:firstRent;
        sendRent = sendRent==""?0:sendRent;
        thirdRent = thirdRent==""?0:thirdRent;
        fourRent = fourRent==""?0:fourRent;
        fifthRent = fifthRent==""?0:fifthRent;
        deposit = deposit==""?0:deposit;
        agencyFee = agencyFee==""?0:agencyFee;
        var rentTotal = parseFloat(firstRent)+parseFloat(sendRent)+parseFloat(thirdRent)+parseFloat(fourRent)+parseFloat(fifthRent);
        $("#contractGrandTotal_"+index).val(rentTotal+parseFloat(deposit)+parseFloat(agencyFee));
        $("#rentalMonth_"+index).val(((rentTotal+parseFloat(deposit)+parseFloat(agencyFee))/60).toFixed(2));
        var structureAcreage =  $("#structureAcreage_"+index).val();
        if(structureAcreage!=""){
            $("#leaseUnitPrice_"+index).val((rentTotal/parseFloat(structureAcreage)/5/365).toFixed(2));
        }

    }

}

/**
 *检测面积
 *
 * */
function  checkStructureAcreage(t){
    checkFloatDataValid(t);
    $(t).parent().parent().attr("editable",true);
    var index = $(t).attr("id").split("_")[1];
    var structureAcreage =  $("#structureAcreage_"+index).val();
    var firstRent = $("#firstYearRent_"+index).val();
    var sendRent = $("#secondYearRent_"+index).val();
    var thirdRent = $("#thirdYearRent_"+index).val();
    var fourRent = $("#fourthYearRent_"+index).val();
    var fifthRent = $("#fifthYearRent_"+index).val();
    if(structureAcreage==""){
        $("#leaseUnitPrice_"+index).val("")
    }else{
        if(firstRent==""&&sendRent==""&&thirdRent==""&&fourRent==""&&fifthRent==""){

        }else{
            firstRent = firstRent==""?0:firstRent;
            sendRent = sendRent==""?0:sendRent;
            thirdRent = thirdRent==""?0:thirdRent;
            fourRent = fourRent==""?0:fourRent;
            fifthRent = fifthRent==""?0:fifthRent;

            var rentTotal = parseFloat(firstRent)+parseFloat(sendRent)+parseFloat(thirdRent)+parseFloat(fourRent)+parseFloat(fifthRent);

            if(structureAcreage!=""){
                $("#leaseUnitPrice_"+index).val((rentTotal/parseFloat(structureAcreage)/5/365).toFixed(2));
            }
        }


    }
}


/**
 * 获取租金
 * **/
function getCostRentContract(){


    var store_cost_tr = $("#rentContract_tr_2").nextAll("tr[editable='true']");
    if(store_cost_tr.length>0){//有数据修改
        $$.showConfirm_cost("提示","是否需要保存改变的数据？",function () {
            searchCostRentContract();

        },function(){

            $("#rentContract_tr_2").nextAll("tr[editable='true']").each(function(){
                $(this).attr("editable","false");
            });
            searchCostRentContract();
        });

    }else if(store_cost_tr.length==0){//没有数据修改
        searchCostRentContract();
    }

}


/**
 * 获取租金
 * */
function searchCostRentContract(){

    var index = layer.load(0,{
        shade: [0.2,'#333']
    });
    var storeNo=$("#storeNo_rent").val()==""?null:$("#storeNo_rent").val();
    var storeName=$("#storeName_rent").val()==""?null:$("#storeName_rent").val();
    $("#rentContract_tb_2").find("tr:gt(0)").remove();

    var cityId="";
    if(flag=="search"){
        cityId = cur_city_id;
    }else{
        cityId = $("#city_id_rentContract").val();
    }

    var storeId = $("#store_id_rentContract").val();
    var storeName = $("#store_name_rentContract").val();

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
        role:role
    }
    doManager('costRentContractManager','queryCostRentContract',costDto,function (data) {


        if(data.result){
            var costRent = JSON.parse(data.data).rentContract;


            var rent_td_sum = "";
            var uniform_charge_sum=0;

            for(var i=0;i<costRent.length;i++){
                var cityName = costRent[i].city_name==null?"":costRent[i].city_name;
                var storeNo = costRent[i].storeNo==null?"":costRent[i].storeNo;
                var storeName = costRent[i].storeName==null?"":costRent[i].storeName;
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

                var lease_start_date = costRent[i].lease_start_date==null?"":costRent[i].lease_start_date.split("-")[0];//起租日含免租期
                var lease_stop_date = costRent[i].lease_start_date==null?"":costRent[i].lease_start_date.split("-")[1];//到租日
                var free_lease_start_date = costRent[i].free_lease_start_date==null?"":costRent[i].free_lease_start_date.split("-")[1];//起租日不含免租期
                var rentalMonth = contract_grand_total==""?"":parseFloat(contract_grand_total/60).toFixed(2);
                $("#rentContract_tb_1").append("<tr></tr>");

                var rentContract_td =
                    "<td style='text-align: center;background-color:#A9A9A9;width: 100%'>"+(i+1)+"</td><td style='text-align: center;background-color:#A9A9A9;width: 100%'>"+storeNo+"</td><td style='background-color:#A9A9A9;width: 100%'><p>"+storeName+"</p></td>"+
                    "<td><input style='background-color: #e8e8e8;width: 100%;' type='text' readonly id='lease_start_date_"+i+"'  value='"+lease_start_date+"'/></td>" +
                    "<td><input style='background-color: #e8e8e8;width: 100%;' type='text'  id='free_lease_start_date_"+i+"' readonly value='"+free_lease_start_date+"'/></td>"+
                    "<td><input style='background-color: #e8e8e8;width: 100%;' type='text' readonly id='lease_stop_date_"+i+"'  value='"+lease_stop_date+"'/></td>" +
                    "<td><input style='width: 100%;' type='text'  onkeyup='checkEveryYearRent(this)' id='firstYearRent_"+i+"' value='"+first_year_rent+"'/></td>" +
                    "<td><input style='width: 100%;' type='text' id='secondYearRent_"+i+"' onkeyup='checkEveryYearRent(this)'  value='"+second_year_rent+"'/></td>" +
                    "<td><input style='width: 100%;' type='text'  id='thirdYearRent_"+i+"' onkeyup='checkEveryYearRent(this)' value='"+third_year_rent+"'/></td>" +
                    "<td><input style='width: 100%;' type='text' id='fourthYearRent_"+i+"' onkeyup='checkEveryYearRent(this)' value='"+fourth_year_rent+"'/></td>" +
                    "<td><input style='width: 100%;' type='text'  id='fifthYearRent_"+i+"' onkeyup='checkEveryYearRent(this)'  value='"+fifth_year_rent+"'/></td>" +
                    "<td><input style='background-color: #e8e8e8;width: 100%;' readonly type='text' onkeyup='checkStructureAcreage(this)' id='structureAcreage_"+i+"'  value='"+structure_acreage+"'/></td>" +
                    "<td><input style='background-color: #e8e8e8;width: 100%;' type='text' readonly id='leaseUnitPrice_"+i+"'   value='"+lease_unit_price+"'/></td>" +
                    "<td><input style='width: 100%;' type='text'  id='deposit_"+i+"' onkeyup='checkEveryYearRent(this)' value='"+deposit+"'/></td>" +
                    "<td><input style='background-color: #e8e8e8;width: 100%;' readonly type='text' onkeyup='checkEveryYearRent(this)' id='agencyFee_"+i+"'  value='"+agency_fee+"'/></td>" +
                    "<td><input type='text' readonly style='background-color: #e8e8e8;width: 100%' id='contractGrandTotal_"+i+"'  value='"+contract_grand_total+"'/></td>"+
                    "<td><input type='text' readonly style='background-color: #e8e8e8;width: 100%' id='rentalMonth_"+i+"'  value='"+rentalMonth+"'/></td>";

                $("#rentContract_tb_2").append("<tr id='"+storeNo+"' editable='false'>"+rentContract_td+"<input type='hidden'  id='storeName' value='"+storeName+"'/><input type='hidden'  id='cityName' value='"+cityName+"'/></tr>");
            }

            layer.close(index);

        }
    });

}


function exportCostRentContract(){

    var index = layer.load(0,{
        shade: [0.2,'#333']
    });
    var storeNo=$("#storeNo_rent").val()==""?null:$("#storeNo_rent").val();
    var storeName=$("#storeName_rent").val()==""?null:$("#storeName_rent").val();

    var cityId = $("#city_id_rentContract").val();


    var storeId = $("#store_id_rentContract").val();
    var storeName = $("#store_name_rentContract").val();

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
        role:role
    }
    doManager('costRentContractManager','exportCostRentContract',costDto,function (data) {
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
        layer.closeAll();
    });

}


/**
 * 检测录入非整数数字数据有效性
 * @param t
 */
function checkFloatDataValid(t){

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
 *
 * 保存租金
 *
 * **/
function saveCostRentContract(){

    var saveResult = "";
    var store_cost_tr = $("#rentContract_tr_2").nextAll("tr[editable='true']");
    var costRentContractArray = [];

    for(var i=0;i<store_cost_tr.length;i++){
        var cityName = $(store_cost_tr[i]).find("input[id='cityName']").val();
        var storeNo= $(store_cost_tr[i]).attr("id");
        var storeName = $(store_cost_tr[i]).find("input[id='storeName']").val();
        var contractGrandTotal = $(store_cost_tr[i]).find('input[id^="contractGrandTotal_"]').val();
        var rentalMonth = $(store_cost_tr[i]).find('input[id^="rentalMonth_"]').val();
        var structure_acreage = $(store_cost_tr[i]).find('input[id^="structureAcreage_"]').val();//建筑面积
        var lease_unit_price = $(store_cost_tr[i]).find('input[id^="leaseUnitPrice_"]').val();//租赁单价
        var first_year_rent = $(store_cost_tr[i]).find('input[id^="firstYearRent_"]').val();
        var second_year_rent = $(store_cost_tr[i]).find('input[id^="secondYearRent_"]').val();
        var third_year_rent = $(store_cost_tr[i]).find('input[id^="thirdYearRent_"]').val();
        var fourth_year_rent = $(store_cost_tr[i]).find('input[id^="fourthYearRent_"]').val();
        var fifth_year_rent = $(store_cost_tr[i]).find('input[id^="fifthYearRent_"]').val();
        var deposit = $(store_cost_tr[i]).find('input[id^="deposit_"]').val();//押金
        var agency_fee = $(store_cost_tr[i]).find('input[id^="agencyFee_"]').val();//中介费
        var lease_start_date = $(store_cost_tr[i]).find('input[id^="lease_start_date_"]').val();//起租日含免租期
        var lease_stop_date = $(store_cost_tr[i]).find('input[id^="lease_stop_date_"]').val();//到租日
        var free_lease_start_date = $(store_cost_tr[i]).find('input[id^="free_lease_start_date_"]').val();//起租日不含免租期

        var costRentContract = {
            cityName:cityName,
            storeNo:storeNo,
            storeName:storeName,
            contractGrandTotal:contractGrandTotal,
            rentalMonth:rentalMonth,
            firstYearRent:first_year_rent,
            secondYearRent:second_year_rent,
            thirtYearRent:third_year_rent,
            fourthYearRent:fourth_year_rent,
            fifthYearRent:fifth_year_rent,
            structureAcreage:structure_acreage,
            leaseUnitPrice:lease_unit_price,
            deposit:deposit,
            agencyFee:agency_fee,
            freeLeaseStartDate:free_lease_start_date,
            leaseStartDate:lease_start_date,
            leaseStopDate:lease_stop_date

        }
        costRentContractArray.push(costRentContract);

    }

    doManager('costRentContractManager','saveCostRentContract',[costRentContractArray],function (data) {
        if(data.result){
            var result= JSON.parse(data.data);

            if(result.status=='success'){
                // $$.showMessage('提示',"保存成功！");
                // return;
                $("#rentContract_tr_2").nextAll("tr[editable='true']").each(function () {
                    $(this).attr("editable","false");
                })
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