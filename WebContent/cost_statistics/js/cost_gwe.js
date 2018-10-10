


var regex_zb = new RegExp("^(ZB|zb)\w*");//总部角色
var regex_cs = new RegExp("^(CS|cs)\w*");//城市级别
/**
 * 查询人工成本城市
 * @type {null}
 */
var lst_select_gwe_city=null;

function getGWECity(){

        if(regex_zb.test(userGroupCode)){

            doManager('dynamicManager','selectAllCity',null,function(data){
                if(data.result){

                    lst_select_gwe_city = JSON.parse(data.data);

                    var option = "";
                    for( var i=0;i<lst_select_gwe_city.length;i++){
                        option=option+"<option value='"+lst_select_gwe_city[i].id+"'>"+lst_select_gwe_city[i].cityname+"</option>";
                    }
                    $("#city_id_gwe").append(option);
                    if(flag=="search"){
                        $("#city_id_gwe").val(cur_city_id);
                    }
                }
            });


        }else if(regex_cs.test(userGroupCode)||userGroupCode=="GLY"){

            doManager("StoreManager", "getCityNameOfCSZJ",[userId,null],
                function(data, textStatus, XMLHttpRequest) {
                    if (data.result) {
                        lst_select_gwe_city = JSON.parse(data.data).citylist;
                        var option = "";
                        for( var i=0;i<lst_select_gwe_city.length;i++){
                            option=option+"<option value='"+lst_select_gwe_city[i].ctid+"'>"+lst_select_gwe_city[i].name+"</option>";
                        }
                        $("#city_id_gwe").append(option);
                        if(flag=="search"){
                            $("#city_id_gwe").val(cur_city_id);
                        }
                    }
                });
        }

}

// /**
//  * 选择城市
//  * @param t
//  */
// function selectGWECity(t){
//     var temp_city = document.getElementById("city_name_gwe").value.replace(/(\s*)|(\s*)/g,'').replace(/[ ]/g,'');
//     $("#city_id_gwe").val(gweCityIdMap[temp_city]);
// }



/**-----------------------------------搜索门店------------------------------------------------------**/

/**
 * 查询门店
 * @type {null}
 */
var lst_select_gwe_store=null;
var gweStoreNameArray=new Array();
var gweStoreIdMap = {};
function getGWEStore(t){

    gweStoreNameArray=new Array();
    $("#gwe_store").empty();
    var str_name = $(t).val();
    if(!(event.keyCode >= 37 && event.keyCode <= 40) && event.keyCode != 13){
        $("#store_id_gwe").val("");

        if(str_name == null || str_name == "" || str_name.indexOf('\'') > -1){
            return;
        }
        var city_id  = $("#city_id_gwe").val()==""?null:$("#city_id_gwe").val();

        var target=0;
        if(regex_zb.test(userGroupCode)){//总部
            target=0;
        }else if(regex_cs.test(userGroupCode)||userGroupCode=="GLY"){//城市
            target=1;
        }

        doManager('dynamicManager','getStoreByCity',[target,userId,city_id,str_name],function(data){
            if(data.result){

                lst_select_gwe_store = JSON.parse(data.data).storelist;

                for(var i=0;i<lst_select_gwe_store.length;i++){
                    gweStoreNameArray.push(lst_select_gwe_store[i].name);
                    gweStoreIdMap[lst_select_gwe_store[i].name] = lst_select_gwe_store[i].storeno;
                }
                var autoComplete = new AutoComplete("store_name_gwe","gwe_store",gweStoreNameArray);
                autoComplete.start(event);
                $("#gwe_store").attr("style","width: 150px;z-index: 99999;left: 26.3%;top: 23.1%;");
            }else{

            }
        });
    }

}

/**
 * 选择门店
 * @param t
 */
function selectGWEStore(t){
    var temp_store = document.getElementById("store_name_gwe").value.replace(/(\s*)|(\s*)/g,'').replace(/[ ]/g,'');
    $("#store_id_gwe").val(gweStoreIdMap[temp_store]);
}

/**
 * 检测水电费录入
 * @param t
 */
function  checkCostGWE(t) {
    checkFloatDataValid(t);
    $(t).parent().parent().attr("editable",true);
}

/**
 * 获取水电费
 */
function getCostGWE(f) {


    var store_cost_tr = $("#gwe_tr_2").nextAll("tr[editable='true']");
    if(store_cost_tr.length>0){//有数据修改
        $$.showConfirm_cost("提示","是否需要保存改变的数据？",function () {
            saveCostGWE(f);

        },function(){

            $("#labor_tr_2").nextAll("tr[editable='true']").each(function(){
                $(this).attr("editable","false");
            });
            searchCostGWE(f);
        });

    }else if(store_cost_tr.length==0){//没有数据修改
        searchCostGWE(f);
    }
}


/**
 * 查询水电费
 * **/
function searchCostGWE(f){

    var index = layer.load(0,{
        shade: [0.2,'#333']
    });

    var showYear = $("#year_gwe").val().split("-")[0];
    var showMonth=$("#year_gwe").val().split("-")[1];
    $("#electricityFee_title").html(showYear+"年"+parseInt(showMonth)+"月");
    $("#waterFee_title").html(showYear+"年"+parseInt(showMonth)+"月");

    $("#gwe_save_date").val($("#year_gwe").val());

    $("#gwe_tb_2").find("tr:gt(0)").remove();

    var cityId="";
    if(f=="auto"){
        cityId = cur_city_id;
    }else{
        cityId = $("#city_id_gwe").val();
    }

    var storeId = $("#store_id_gwe").val();
    var storeName = $("#store_name_gwe").val();

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
        month:parseInt(showMonth),
        userId:userId,
        role:role
    }



    $("#gwe_tb_2").find("tr:gt(0)").remove();

    doManager('costGWEManager','queryCostGWE',costDto,function (data) {


        if(data.result){
            var costGWE = JSON.parse(data.data).gwe;

            for(var i=0;i<costGWE.length;i++){

                var cityName = costGWE[i].city_name==null?"":costGWE[i].city_name;
                var storeNo = costGWE[i].storeNo==null?"":costGWE[i].storeNo;
                var storeName = costGWE[i].storeName==null?"":costGWE[i].storeName;

                var water_fee = costGWE[i].water_fee==null?"":costGWE[i].water_fee;//水费
                var electricity_fee = costGWE[i].electricity_fee==null?"":costGWE[i].electricity_fee;//电费

                var GWE_td = "<td style='text-align: center;background-color:#A9A9A9'>"+(i+1)+"</td><td style='text-align: center;background-color:#A9A9A9'>"+storeNo+"</td><td style='background-color:#A9A9A9'><p>"+storeName+"</p></td>" +
                            "<td><input type='text'  style='width: 100%' onkeyup='checkCostGWE(this)'   id='waterFee_"+i+"' value='"+water_fee+"'/></td>" +
                            "<td><input type='text'     style='width: 100%'  onkeyup='checkCostGWE(this)' id='electricityFee_"+i+"'  value='"+electricity_fee+"'/></td>";

                $("#gwe_tb_2").append("<tr id='"+storeNo+"' editable='false'>"+GWE_td+"<input type='hidden'  id='storeName' value='"+storeName+"'/><input type='hidden'  id='cityName' value='"+cityName+"'/></tr>");
            }

            layer.close(index);

        }
    });

}
/**
 * 导出水电费
 *
 * **/
function   exportCostGWE(){

    var showYear = $("#year_gwe").val().split("-")[0];
    var showMonth=$("#year_gwe").val().split("-")[1];


    var cityId = $("#city_id_gwe").val();


    var storeId = $("#store_id_gwe").val();
    var storeName = $("#store_name_gwe").val();

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
        month:parseInt(showMonth),
        userId:userId,
        role:role
    }

    doManager('costGWEManager','exportCostGWE',costDto,function (data) {
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
function saveCostGWE(){
    var saveResult= "";
    var year_month = $("#gwe_save_date").val();
    var store_cost_tr = $("#gwe_tr_2").nextAll("tr[editable='true']");
    var costGWEArray = [];
    for(var i=0;i<store_cost_tr.length;i++){
        var storeNo= $(store_cost_tr[i]).attr("id");//门店编号
        var cityName = $(store_cost_tr[i]).find("input[id='cityName']").val();//门店名称
        var storeName = $(store_cost_tr[i]).find("input[id='storeName']").val();//门店名称
        var electricityFee = $(store_cost_tr[i]).find("input[id^='electricityFee_']").val();//电费
        var waterFee = $(store_cost_tr[i]).find('input[id^="waterFee_"]').val();//水费


        var costGWE = {
            cityName:cityName,
            storeNo:storeNo,
            storeName:storeName,
            year:year_month.split("-")[0],
            month:parseInt(year_month.split("-")[1]),
            electricityFee:electricityFee,
            waterFee:waterFee
        }

        costGWEArray.push(costGWE);


    }

    doManager('costGWEManager','saveCostGWE',[costGWEArray],function (data) {
        if(data.result){
            var result= JSON.parse(data.data);

            if(result.status=='success'){
                // $$.showMessage('提示',"保存成功！");
                $("#gwe_tr_2").nextAll("tr[editable='true']").each(function(){
                    $(this).attr("editable","false");
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