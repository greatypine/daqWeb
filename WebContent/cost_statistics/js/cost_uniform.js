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


var regex_zb = new RegExp("^(MDCBGLZB|mdcbglzb)\w*");//成本管理总部角色
var regex_cs = new RegExp("^(MDCBGLCS|mdcbglcs)\w*");//成本管理城市级别
/**
 * 查询工服成本城市
 * @type {null}
 */
var lst_select_uniform_city=null;

function getUniformCity(){


        if(regex_zb.test(userGroupCode)){

            doManager('dynamicManager','selectAllCity',null,function(data){
                if(data.result){

                    lst_select_uniform_city = JSON.parse(data.data);

                    var option = "";
                    for( var i=0;i<lst_select_uniform_city.length;i++){
                        option=option+"<option value='"+lst_select_uniform_city[i].id+"'>"+lst_select_uniform_city[i].cityname+"</option>";
                    }
                    $("#city_id_uniform").append(option);
                    if(flag=="search"){
                        $("#city_id_uniform").val(cur_city_id);
                    }
                }
            });


        }else if(regex_cs.test(userGroupCode)||userGroupCode=="GLY"){

            doManager("StoreManager", "getCityNameOfCSZJ",[userId,null],
                function(data, textStatus, XMLHttpRequest) {
                    if (data.result) {
                        lst_select_uniform_city = JSON.parse(data.data).citylist;

                        var option = "";
                        for( var i=0;i<lst_select_uniform_city.length;i++){
                            option=option+"<option value='"+lst_select_uniform_city[i].ctid+"'>"+lst_select_uniform_city[i].name+"</option>";
                        }
                        $("#city_id_uniform").append(option);
                        if(flag=="search"){
                            $("#city_id_uniform").val(cur_city_id);
                        }
                    }
                });
        }



}

// /**
//  * 选择城市
//  * @param t
//  */
// function selectUniformCity(t){
//     var temp_city = document.getElementById("city_name_uniform").value.replace(/(\s*)|(\s*)/g,'').replace(/[ ]/g,'');
//     $("#city_id_uniform").val(uniformCityIdMap[temp_city]);
// }



/**-----------------------------------搜索门店------------------------------------------------------**/

/**
 * 查询门店
 * @type {null}
 */
var lst_select_uniform_store=null;
var uniformStoreNameArray=new Array();
var uniformStoreIdMap = {};
function getUniformStore(t){

    uniformStoreNameArray=new Array();
    $("#uniform_store").empty();
    var str_name = $(t).val();
    if(!(event.keyCode >= 37 && event.keyCode <= 40) && event.keyCode != 13){
        $("#store_id_uniform").val("");

        if(str_name == null || str_name == "" || str_name.indexOf('\'') > -1){
            return;
        }
        var city_id  = $("#city_id_uniform").val()==""?null:$("#city_id_uniform").val();

        var target=0;
        if(regex_zb.test(userGroupCode)){//总部
            target=0;
        }else if(regex_cs.test(userGroupCode)||userGroupCode=="GLY"){//城市
            target=1;
        }

        doManager('dynamicManager','getStoreByCity',[target,userId,city_id,str_name],function(data){
            if(data.result){

                lst_select_uniform_store = JSON.parse(data.data).storelist;

                for(var i=0;i<lst_select_uniform_store.length;i++){
                    uniformStoreNameArray.push(lst_select_uniform_store[i].name);
                    uniformStoreIdMap[lst_select_uniform_store[i].name] = lst_select_uniform_store[i].storeno;
                }
                var autoComplete = new AutoComplete("store_name_uniform","uniform_store",uniformStoreNameArray);
                autoComplete.start(event);
                $("#uniform_store").attr("style","width: 150px;z-index: 99999;left: 26.3%;top: 22.3%;");
            }else{

            }
        });
    }

}

/**
 * 选择门店
 * @param t
 */
function selectUniformStore(t){
    var temp_store = document.getElementById("store_name_uniform").value.replace(/(\s*)|(\s*)/g,'').replace(/[ ]/g,'');
    $("#store_id_uniform").val(uniformStoreIdMap[temp_store]);
}


/**
 * 工服年费
 * @param t
 */
function checkUniformCharge(t){

    checkFloatDataValid(t);
    $(t).parent().parent().attr("editable",true);
    var id = $(t).attr("id").split("_");
    var uniformCharge = $("#uniformCharge_"+id[1]).val();
    if(uniformCharge!=""){
        $("#uniformOfAmortize_"+id[1]).val(parseFloat(uniformCharge/12).toFixed(2));
    }else{
        $("#uniformOfAmortize_"+id[1]).val("");
    }


}

/**
 * 检测工服成本数据并记录编辑的门店
 *
 * **/
var editStore = new Set();
function checkCostUniform(t){
    checkFloatDataValid(t);
    $(t).parent().parent().attr("editable",true);
    var id=$(t).attr("id");
    var rowNum = id.split("_")[1];

    var emolument = $("#emolument_"+rowNum).val();
    var uniformAmortize = $("#uniformAmortize_"+rowNum).val();
    var accommodation = $("#accommodation_"+rowNum).val();
    var subtotal="";
    if(emolument==""&&uniformAmortize==""&&accommodation==""){
        $("#subtotal_"+rowNum).val("");
    }else{
        emolument = $("#emolument_"+rowNum).val()==""?0:$("#emolument_"+rowNum).val();
        uniformAmortize = $("#uniformAmortize_"+rowNum).val()==""?0:$("#uniformAmortize_"+rowNum).val();
        accommodation = $("#accommodation_"+rowNum).val()==""?0:$("#accommodation_"+rowNum).val();
        $("#subtotal_"+rowNum).val(parseFloat(emolument)+parseFloat(uniformAmortize)+parseFloat(accommodation));
    }

}



/**
 * 获取工服成本
 * **/
function getCostUniform(f){


    var store_cost_tr = $("#uniform_tr_2").nextAll("tr[editable='true']");
    if(store_cost_tr.length>0){//有数据修改
        $$.showConfirm_cost("提示","是否需要保存改变的数据？",function () {
            saveCostUniform(f);

        },function(){

            $("#uniform_tr_2").nextAll("tr[editable='true']").each(function(){
                $(this).attr("editable","false");
            });
            searchUniform(f);
        });

    }else if(store_cost_tr.length==0){//没有数据修改
        searchUniform(f);
    }

}

/**
 * 查询工服成本
 */
function searchUniform(f){

    var index = layer.load(0,{
        shade: [0.2,'#333']
    });

    var storeName=$("#storeName_uniform").val()==""?null:$("#storeName_uniform").val();
    var showYear = $("#year_uniform").val();
    $("#uniform_save_date").val(showYear);

    $("#uniform_tb_2").find("tr:gt(0)").remove();

    var cityId="";
    if(f=="auto"){
        cityId = cur_city_id;
    }else{
        cityId = $("#city_id_uniform").val();
    }

    var storeId = $("#store_id_uniform").val();
    var storeName = $("#store_name_uniform").val();

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
    doManager('costUniformManager','queryCostUniform',costDto,function (data) {


        if(data.result){
            var costuniform = JSON.parse(data.data).uniform;


            var uniform_td_sum = "";
            var uniform_charge_sum=0;

            for(var i=0;i<costuniform.length;i++){

                var cityName = costuniform[i].city_name;
                var storeNo=costuniform[i].store_no;
                var storeName = costuniform[i].store_name;
                var uniform_amortize = costuniform[i]["uniform_amortize"]==null?"":costuniform[i]["uniform_amortize"];
                var uniform_charge = costuniform[i].uniform_charge==null?"":costuniform[i].uniform_charge;
                var rc_tag ="_"+i;
                var uniform_td=
                           "<td style='text-align: center;background-color:#A9A9A9'>"+(i+1)+"</td><td style='text-align: center;background-color:#A9A9A9'>"+storeNo+"</td><td style='background-color:#A9A9A9'>"+storeName+"</td>"+
                           "<td><input type='text' style='width: 100%;' onkeyup='checkUniformCharge(this)' id='uniformCharge"+rc_tag+"' value='"+uniform_charge+"'/></td><td><input type='text' style='background-color: #e8e8e8;width: 100%;' readonly onkeyup='checkCostuniform(this)' id='uniformOfAmortize"+rc_tag+"' value='"+uniform_amortize+"'/></td>";
                $("#uniform_tb_2").append("<tr id='"+storeNo+"' editable='false'>"+uniform_td+"<input type='hidden' value='"+storeName+"' id='storeName'/><input type='hidden' value='"+cityName+"' id='cityName'/></tr>");

            }

            layer.close(index);

        }
    });
}
/**
 * 导出工服成本
 *
 * **/
function   exportCostUniform(){


    var showYear = $("#year_uniform").val().split("-")[0];
    var showMonth=$("#year_uniform").val().split("-")[1];

    var cityId = $("#city_id_uniform").val();


    var storeId = $("#store_id_uniform").val();
    var storeName = $("#store_name_uniform").val();

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



    doManager('costUniformManager','exportCostUniform',costDto,function (data) {
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
 * 保存工服成本
 *
 * **/
function saveCostUniform(){
    var saveResult = "";
    var year = $("#uniform_save_date").val();
    var store_cost_tr = $("#uniform_tr_2").nextAll("tr[editable='true']");
    var costuniformArray = [];
    for(var i=0;i<store_cost_tr.length;i++){
        var cityName = $(store_cost_tr[i]).find("input[id='cityName']").val();
        var storeNo= $(store_cost_tr[i]).attr("id");
        var storeName = $(store_cost_tr[i]).find("input[id='storeName']").val();
        var uniformAmortize = $(store_cost_tr[i]).find('input[id^="uniformOfAmortize_"]').val();
        var uniformCharge = $(store_cost_tr[i]).find('input[id^="uniformCharge_"]').val();
        var costuniform = {
            cityName:cityName,
            storeNo:storeNo,
            storeName:storeName,
            year:year,
            uniformAmortize:uniformAmortize,
            uniformCharge:uniformCharge
        };

        costuniformArray.push(costuniform);

    }

    doManager('costUniformManager','saveCostUniform',[costuniformArray],function (data) {
        if(data.result){
            var result= JSON.parse(data.data);

            if(result.status=='success'){
                // $$.showMessage('提示',"保存成功！");
                $("#uniform_tr_2").nextAll("tr[editable='true']").each(function(){
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