function importJs(jsFilePath) {
	document.write('<script language="javascript" src="' + jsFilePath + '"></script>');
}
importJs("../bizbase/js/userDialog.js");
importJs("../scripts/userCheck.js");

//是否可以终止
var isFinish=true;
//是否选择下一步
var isChoiseNext=true;
//默认都是0，如果选择了下一步流程，则变成1，如果点击终止流程，则为2
var operType=0;
//定义了一个常量，代表是抄送
var COPYTYPE=1;
/**
 * 赵彬彬添加的工作流审批对话框组件
 *param1:managerName
 *param2:methodName
 *param3:divId
 *parma4:提交之后跳转的页面
 */
function dialogSubmit(parm1, parm2, parm3, parm4,templateCode) {
    var isPass = $("input[name='ispassed']:checked").val();
    var div = $("<div id='workFlowId'></div>");
    if (isPass == 1) {
        div.html("确定通过？");
    }
    else {
        div.html("确定拒绝？");
    }
    div.dialog({
        bgiframe: true,
        title: "提示",
        width: 320,
        buttons: {
            "确定": function () {
                if ($("#memo").val() == null || $("#memo").val() == "") {
                    alert("请填写处理意见!");
                }
                else {
                    doSubmit(parm1, parm2, parm3, parm4,templateCode);
                }
                $(this).dialog("close");
            },
            "取消": function () {
                $(this).dialog("close");
            }
        },
        modal: true
    });
}
/**
 * 
 * @param {Object} parm1 manager
 * @param {Object} parm2 method
 * @param {Object} parm3 toDoDivId
 * @param {Object} parm4 html
 * @param {Object} parm5 flowInstatnceId
 */
function initTodoAllowDiv(parm1, parm2, parm3, parm4,parm5,parm6) {
            var divHtml=
                    '<div width="100%">'  +
						'<div id="skipDiv">' +
                        '<table class="layout" cellpadding="0" cellspacing="0" border="1" style="width:100%">'+
                        '<tr>'+
                        '<td align="right" colspan="2" style="width:10%">'+
                            '下一环节：</td><td align="left" colspan="2">'+initNextSetpSelect(parm5)+
                        '</tr>'+
                        '</table>'+
                        '</div>'+
                        '<div id="stopDiv">' +
                        '<table class="layout" cellpadding="0" cellspacing="0" border="1" style="width:100%">'+
                        '<tr>'+
                        '<td align="right" colspan="2" style="width:10%">'+
                        '终止当前流程：</td><td  align="left" colspan="2"><input class="pmsRadio" type="checkbox" id="isFinish" name="isFinish" onclick="selectStop()"/>终止流程'+
                        '</tr>'+
                        '</table>'+
                        '</div>' +
                        '<div id="allowDiv">' +
                        '<table class="layout" cellpadding="0" cellspacing="0" border="0" bordercolor="red"  style="width:100%">' +
                            '<tr>' +
                                '<td align="right" colspan="2" style="width:10%">' +
                                    '处理结果：</td><td  align="left" colspan="2"><input class="pmsRadio" type="radio" id="ispassed" name="ispassed" value="1" checked="checked" onclick="clickIsPassed()"/>'+'同意'
                                              +'<input class="pmsRadio" type="radio" id="ispassed" name="ispassed" value="0" onclick="clickIsPassed()"/>'+'拒绝'
                                    +'<span>&nbsp;&nbsp;</span><button class="buttonu" value="提交" onclick="dialogSubmit('+'\''+parm1+'\''+','+'\''+ parm2+'\''+','+'\''+ parm3+'\''+','+'\''+ parm4+'\''+','+'\''+ parm6+'\''+')">&nbsp;提交</button>' +
                                '</td>' +
                            '</tr>' +
                            '<tr>' +
                            '   <td align="right" colspan="2" style="width:10%">处理意见：</td><td align="left" colspan="2">' +
                            '   <textarea name="memo" id="memo"  rows="4" maxlength="1000" style="width:100%"  onkeyup="return DjCheckMaxlength(this);">同意</textarea>' +
                            '   </td>' +
                            '</tr>' +
							'<tr>'+
									'<td align="right" colspan="2" style="width:10%">' +
                                	'抄送人：</td><td  align="left" colspan="2">'+
									'<div id="selectDiv" class="inputtext_dis" style="width:100%;height:auto!important; height:30px; min-height:30px; overflow:auto;float: left;">'+
									'<button class="buttonu" style="height:30px; float:right" onclick="checkUser(1,doSelect)">选择</button></td></div>' +
                            '</tr>'+
                        '</table>' +
                        '</div>'
                    '</div>';
            $("#" + parm3).append(divHtml);
            if (from != "WF" || toDoType == "1" || isBack == "1") {
                $("#" + parm3).hide();
            }
			initAllDivHiddenOrShow(parm5);
			if(toDoType==COPYTYPE){
				$("#workFlowTableId").append('<div style="text-align:right;width:100%;height:auto!important; height:30px; min-height:30px; overflow:auto;float: left;"><button class="buttonu" onclick="clickCopyConfirmButton('+'\''+parm4+'\''+')">确认</button><div>');	
			}
}
//初始化界面的div是否显示
function initAllDivHiddenOrShow(instanceId){
	doManager("WFInstanceManager", "getCurrentStepInstance", instanceId, function(data, textStatus, XMLHttpRequest){
	    if (data.result) {
			// isFinish: 是否可终止 1 是 0 否 <br>
	 		// isChoiseNext: 是否可选择后续节点 1 是 0 否 <br>
			var currentStep = $.fromJSON(data.data);
			if(currentStep.isFinish==0){
				$("#stopDiv").hide();
				isFinish=false;
			}
			if(currentStep.isChoiseNext==0){
				$("#skipDiv").hide();
				isChoiseNext=false;
			}
	    }
	    else {
	        alert("取得当前步骤出错！");
	    }
    }, false);
}
//选择终止
function selectStop(){
    if($("#isFinish").attr("checked")){
		if(isChoiseNext){
	        $("#skipDiv").hide();
			$("#nextStepSelect").get(0).selectedIndex=0;
		}
    }   else{
		if(isChoiseNext){
	        $("#skipDiv").show();
		}
    }
}
//选择是否通过
function clickIsPassed(){
    if($("input[name='ispassed']:checked").val()=='0'){
		if($("#memo").val()=='同意'){
			$("#memo").val("");
		}
		if(isChoiseNext){
	       $("#skipDiv").hide();
		}
		if(isFinish){
	       $("#stopDiv").hide();
		   $("#isFinish").attr("checked",false);
		   $("#nextStepSelect").get(0).selectedIndex=0;
		}
    }
    else{
		$("#memo").val("同意");
       if(isChoiseNext){
	       $("#skipDiv").show();
		}
		if(isFinish){
	       $("#stopDiv").show();
		}
    }
}
//初始化接下来流程的select
function initNextSetpSelect(instatceId){
	var selectHtml="<select id='nextStepSelect' onchange='selectChange()'>";
	doManager("WFInstanceManager", "getNextStepList", instatceId, function(data, textStatus, XMLHttpRequest){
                        if (data.result) {
							var stepList = $.fromJSON(data.data);
							if(stepList!=null&&stepList.length!=0){
								for(var i = 0; i < stepList.length; i++){
									selectHtml+="<option value='"+stepList[i].id+"'>"+stepList[i].stepName+"</option>";   
								}
							}
                        }
                        else {
                            alert("取得流程步骤下一环节出错！");
                        }
                    }, false);
	selectHtml+="</select>";
	return selectHtml;
}
//下一步流程变换了
function selectChange(){
	operType=1;
}
function doSubmit(parm1, parm2, parm3, parm4,templateCode) {
	//如果选择了终止流程，则OperType=2
	if ($("#isFinish").attr("checked")) {
		operType=2;
	}
    var obj = {
        flowInstanceId: flowInstanceId,//前台传入
        //operId: '99999',//operId,这里应该是名称，而且会出现多条，后台需要改进,因为没法找寻 先制空处理
        //toOperId: '99999',//operstring,这里应该是名称，而且会出现多条，后台需要改进,因为没法找寻 先制空处理
        sheetIds: KsheetId+",",//前台传入
        isPassed: $("input[name='ispassed']:checked").val(),
        memo: $("#memo").val(),
        toOperId: toOperId,
		toDoType:toDoType,
        operType: operType,
		nextStepId:$("#nextStepSelect").val(),
		templateCode:templateCode
    };
    doManager(parm1, parm2, [obj], function (data, textStatus, XMLHttpRequest) {
        if (data.result) {
			var userInf = $.fromJSON(getSelectObjJson());
			//如果选择了抄送人，则需要调用工作流为抄送人发送抄送待办
			if(userInf.userIds!=""){
				var userIds=userInf.userIds;
				var userNames=userInf.userNames;
				var copyObj={
					flowInstanceId:flowInstanceId,
					sendId:getCurrentUserId(),
					receiverIds:userIds,
					receiverNames:userNames
				};
				//抄送
				doManager("WFInstanceManager", "addCopyInfo", [copyObj], function (datas, textStatus, XMLHttpRequest) {
			        if (datas.result) {
			            top.appFrame.location = parm4;
			        }
			        else {
			            alert("发送抄送失败!");
			        }
			    },false);
			}else{
	            top.appFrame.location = parm4;
			}
        }
        else {
            alert("提交失败!");
        }
    },false);
}
//限制审批意见只能输入1000长度
function DjCheckMaxlength(oInObj)
{
    var iMaxLen = parseInt(oInObj.getAttribute('maxlength'));
    var iCurLen = oInObj.value.length;

    if ( oInObj.getAttribute && iCurLen > iMaxLen )
    {
        oInObj.value = oInObj.value.substring(0, iMaxLen);
    }
}
/**
 * 获取当前登录人的ID
 */
function getCurrentUserId(){
    var userid = "";
    doManager("userManager", "getCurrentUserDTO", "", function(data, textStatus, XMLHttpRequest){
        if (data.result == true) {
            if (data.data != "null") {
                var returnObj = $.fromJSON(data.data);
                userid = returnObj.id;
            }
        }
        else {
            $$.showMessage("${system.info}", "取得登录人信息出错!");
        }
    }, false);
    return userid;
}
//查看抄送记录时点击确认接收按钮，消除该条抄送待办
function clickCopyConfirmButton(parm4){
	var div = $("<div id='workFlowId'></div>");
    div.html("确认将会消除抄送待办，是否确认？");
    div.dialog({
        bgiframe: true,
        title: "提示",
        width: 320,
        buttons: {
            "确定": function () {
				var copyObj={
					flowInstanceId:flowInstanceId,
					receiverIds:getCurrentUserId()
				};
				doManager("WFInstanceManager", "readCopyInfo", [copyObj], function (datas, textStatus, XMLHttpRequest) {
			        if (datas.result) {
			            top.appFrame.location = parm4;
			        }
			        else {
			            alert("消除抄送失败!");
			        }
			    },false);
                $(this).dialog("close");
            },
            "取消": function () {
                $(this).dialog("close");
            }
        },
        modal: true
    });
}
