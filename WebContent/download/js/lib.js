function getRootPath(){
	var curWwwPath=window.document.location.href;
	var pathName=window.document.location.pathname;
	var pos=curWwwPath.indexOf(pathName);
	var localhostPaht=curWwwPath.substring(0,pos);
	var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	return(localhostPaht+projectName);
}
var baseUrl = getRootPath();

/*!
 * import js files and css files.
 */
function importJs(jsFilePath) {
	document.write('<script language="javascript" src="' + jsFilePath + '"></script>');
}
importJs("../scripts/lib/jquery/jquery-1.5.js");
importJs("../scripts/common/common-map.js");
importJs("../scripts/lib/jquery/ui/jquery-ui-1.8.9.custom.min.js");
importJs("../scripts/lib/jquery/datatables/jquery.dataTables.js");
importJs("../scripts/lib/jquery/timepicker/jquery-ui-timepicker-addon.js");
//输入框自动完成
importJs("../scripts/lib/jquery/autocomplete/jquery.autocomplete.js");
importJs("../scripts/lib/jquery/thickbox/thickbox-compressed.js");
//jquery.metadata.js 验证用，使validate="required:true"等等有效
importJs("../scripts/lib/jquery/validate/lib/jquery.metadata.js");
//jquery.validate.js 验证用
importJs("../scripts/lib/jquery/validate/jquery.validate.js");
//jquery.tooltip.js 验证用，鼠标悬浮框效果
importJs("../scripts/lib/jquery/tooltip/jquery.tooltip.js");
importJs("../scripts/lib/jquery/ztree/jquery-ztree-2.4.js");
importJs("../scripts/common/common-core.js");
importJs("../scripts/common/common-tool.js");
importJs("../scripts/common/common-expand.js");
importJs("../scripts/common/common-dict.js");
importJs("../scripts/common/common-tool.js");
importJs("../scripts/common/common-form.js");
importJs("../scripts/common/common-table.js");
importJs("js/common-validation-bizbase.js");
//common-validation.js 验证用
importJs("../scripts/common/common-validation.js");
//common-messagedialog.js 对话框
importJs("../scripts/common/common-messagedialog.js");
//common-processingdialog.js 提交后显示“处理中。。。”的div
importJs("../scripts/common/common-processingdialog.js");
importJs("js/bidCommon.js");
importJs("js/acl.js");
importJs(baseUrl+"/scripts/common/qualcommon.js");
importJs("../scripts/lib/jquery/ui/jquery.ui.datepicker-zh-CN.js")
//jquery.tooltip.css 验证用，鼠标悬浮框效果
//最后引入
importJs("../scripts/default-ready.js");
var PMS_READY = [ "$pmspage.initialize", "$$.run", "$form.run" ];

