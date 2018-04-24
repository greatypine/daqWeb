/**
 * 李洋添加的工作流审批对话框组件
 * 针对审批界面在树节点页面的情况
 *param1:managerName
 *param2:methodName
 *param3:divId
 *parma4:提交之后跳转的页面
 */
function dialogSubmit(parm1, parm2, parm3, parm4) {
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
                    doSubmit(parm1, parm2, parm3, parm4);
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


function initTodoAllowDiv(parm1, parm2, parm3, parm4) {
    var divHtml='<div width="100%">' +
        '<table class="layout" cellpadding="0" cellspacing="0" border="0" bordercolor="red"  style="width:100%">' +
        '<tr>' +
        '<td align="left" colspan="2">' +
        '处理结果：<input class="pmsRadio" type="radio" id="ispassed" name="ispassed" value="1" checked="checked"/>同意' +
        '<input class="pmsRadio" type="radio" id="ispassed" name="ispassed" value="0"/>拒绝'+
        '</td></tr>' +
        '<tr><td align="left" colspan="2">处理意见:<textarea name="memo" id="memo" style="width: 100%; height: 88px;" rows="4" maxlength="1000" onkeyup="return DjCheckMaxlength(this);"></textarea></td></tr>' +
        '<tr><td align="right" colspan="4"><button class="buttonu" value="提交" onclick="dialogSubmit('+'\''+parm1+'\''+','+'\''+ parm2+'\''+','+'\''+ parm3+'\''+','+'\''+ parm4+'\''+')">&nbsp;提交</button></td></tr>' +
        '</table></div>';
    $("#" + parm3).append(divHtml);
    if (from != "WF" || toDoType == "1" || isBack == "1") {
        $("#" + parm3).hide();
    }
}
function doSubmit(parm1, parm2, parm3, parm4) {
    var obj = {
        flowInstanceId: flowInstanceId,//前台传入
        //operId: '99999',//operId,这里应该是名称，而且会出现多条，后台需要改进,因为没法找寻 先制空处理
        //toOperId: '99999',//operstring,这里应该是名称，而且会出现多条，后台需要改进,因为没法找寻 先制空处理
        sheetIds: KsheetId+",",//前台传入
        isPassed: $("input[name='ispassed']:checked").val(),
        memo: $("#memo").val(),
        toOperId: toOperId,
        operType: toDoType
    }
    doManager(parm1, parm2, obj, function (data, textStatus, XMLHttpRequest) {
        if (data.result) {
            window.parent.location = parm4;
        }
        else {
            alert("提交失败!");
        }
    });
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