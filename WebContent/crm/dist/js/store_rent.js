$(function(){
    initStoreData();
})
function initStoreData() {
    doManager("storeManager", "findStoreRentDataByCity",null,function(data, textStatus, XMLHttpRequest) {
        if (data.result) {
            var allData = JSON.parse(data.data);
            //全国月店数据
            var storeData=allData.storeData;
            var mothStoreData_str="";
            for(var a=0;a<storeData.length;a++){
                mothStoreData_str+="<tr><td>"+storeData[a].city_name+"</td><td>"+storeData[a].city_total+"</td><td></td></tr>"


            }












            //全国星店数据分析
            var starStoreData_str="";
            var starStoreData=allData.starStoreData;
            for(var a=0;a<starStoreData.length;a++){
                starStoreData_str+=("<tr><td>"+starStoreData[a].city_name+"</td><td>"+starStoreData[a].city_count+"</td>" +
                    "<td>"+starStoreData[a].jizu_area_total+"</td><td>"+starStoreData[a].jizu_area_avg+"</td>" +
                    "<td>"+starStoreData[a].jizu_area_rental+"</td><td>"+starStoreData[a].usable_area_total+"</td>" +
                    "<td>"+starStoreData[a].usable_area_avg+"</td><td>"+starStoreData[a].usable_area_rental+"</td></tr>");
            }
            $("#city_store").append(starStoreData_str);
            

            
        }
    },false);
}








