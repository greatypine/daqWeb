
function getFlowFunction(instanceId, divId) {
	doWFInitDocument(divId);
	$("#flowInstanceId").val(instanceId);
	$$.executeSearch('submitStepQuery', 'conditionsDiv');
}

function getFlowFunctionTwo(moduleCode, sheetId, divId) {
	doWFInitDocument(divId);
	var obj = {
		moduleCode:moduleCode,
		sheetId:sheetId
	}

	doManager("WFInstanceManager", "getFlowInstanceByPara", obj, function(data, textStatus, XMLHttpRequest){
		if (data.result) {
			var jsdata = $.fromJSON(data.data);
			if(jsdata!=null&&jsdata!="null"){
				$("#flowInstanceId").val(jsdata.id);
				$$.executeSearch('submitStepQuery', 'conditionsDiv');
			}
		}
	});
}

function doWFInitDocument(divId){
	var div = "<div id='conditionsDiv' style='display:hidden'><form>" +
	"<input id='flowInstanceId' name='flowInstanceId' type='hidden' value='' />" +
	"</form></div>" +
	"<div id='toDoListQuery2'  searchDiv='conditionsDiv' titleAlign= 'center' fnRender='renderColumns' showsearch='false' usecache='true' showdisplay='false' showprint='false' showpaging='false' configbutton='false' queryid='submitStepQuery'></div>";
	
	var retendElement = $(div);
	$("#" + divId).append(retendElement);
	

}
var COLUMNS = {
		"advice": function(aData, iColumn, sColumnName, map){
			var value = map[sColumnName];
			return "<div style='word-break:break-all;word-wrap:break-word;width:200px;' >" + value + "</div>";
		},
		"posName2": function(aData, iColumn, sColumnName, map){
			var value = map[sColumnName];
			return "<div style='word-break:break-all;word-wrap:break-word;width:200px;' >" + value + "</div>";
		}
	}