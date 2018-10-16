
var storeColor={"筹备中":"#2fb5f5","待开业":"#f1c730","运营中":"#48ef1f","闭店":"#da4545"}
/**************************自定义confirm***********************************/
PMSApplication.prototype.showConfirm_cost = function(title, message, callback,cancel) {
    if (this.dialog == null){
        this.dialog = new PMSDialog(title, message);
    }
    this.dialog.confirm_cost(title, message, callback,cancel);
}

/**
 * 自定义询问窗口
 * @param title
 * @param content
 * @param callback
 * @param cancel
 */
PMSDialog.prototype.confirm_cost = function(title, content, callback,cancel) {
    if (title != null && this.title != title)
        this.setTitle(title);
    if (content != null && this.content != content)
        this.setContent(content);
    this.uiElement.dialog("destroy");
    this.uiElement.dialog({
        modal : true,
        resizable : false,
        title : this.title,
        buttons : {
            '是' : function() {
                $(this).dialog("close");
                $(this).dialog("destroy");
                if(callback && typeof callback == 'function') {
                    callback();
                }
            },
            '否' : function() {
                $(this).dialog("close");
                $(this).dialog("destroy");
                if(cancel && typeof cancel == 'function') {
                    cancel();
                }
            }
        }
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